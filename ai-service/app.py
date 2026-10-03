from __future__ import annotations
import json,math,os,re,time
from typing import List,Dict
import requests,numpy as np,faiss
from fastapi import FastAPI,HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel,Field
from rank_bm25 import BM25Okapi
from sentence_transformers import SentenceTransformer,CrossEncoder

SPRING_JOBS_URL=os.getenv("SPRING_JOBS_URL","http://localhost:8080/jobs")
LLM_BASE_URL=os.getenv("LLM_BASE_URL","http://localhost:11434/v1").rstrip("/")
LLM_API_KEY=os.getenv("LLM_API_KEY","ollama")
LLM_MODEL=os.getenv("LLM_MODEL","llama3.2:3b")
EMBEDDING_MODEL=os.getenv("EMBEDDING_MODEL","sentence-transformers/all-MiniLM-L6-v2")
RERANKER_MODEL=os.getenv("RERANKER_MODEL","BAAI/bge-reranker-base")
CONFIDENCE_THRESHOLD=float(os.getenv("CONFIDENCE_THRESHOLD","0.035"))

SKILLS=["java","python","c++","sql","mysql","postgresql","mongodb","spring","spring boot","hibernate","jpa","rest","rest api","fastapi","flask","django","javascript","typescript","react","node.js","node","html","css","git","docker","kubernetes","aws","azure","gcp","machine learning","deep learning","nlp","llm","rag","pandas","numpy","scikit-learn","tensorflow","pytorch","faiss","bm25","linux"]

def tok(s): return re.findall(r"[a-zA-Z0-9+#.]+",s.lower())

class Req(BaseModel):
    query:str=Field(min_length=2)
    profile:str=""
    top_k:int=Field(default=5,ge=1,le=20)

class GapReq(BaseModel):
    query:str=Field(min_length=2)
    profile:str=Field(min_length=2)
    top_k:int=Field(default=3,ge=1,le=10)

class Engine:
    def __init__(self):
        self.embed=SentenceTransformer(EMBEDDING_MODEL)
        self.rerank=CrossEncoder(RERANKER_MODEL)
        self.jobs=[];self.bm=None;self.index=None;self.loaded=False

    def sync(self):
        r=requests.get(SPRING_JOBS_URL,timeout=20);r.raise_for_status()
        self.jobs=[]
        for j in r.json():
            text=" | ".join(str(j.get(k,"") or "") for k in ["title","description","location","experience","jobType","category","salary"])
            self.jobs.append({"id":int(j["id"]),"title":str(j.get("title","")),"company":str(j.get("companyName","")),"location":str(j.get("location","")),"text":text})
        if not self.jobs: self.loaded=True;return 0
        self.bm=BM25Okapi([tok(x["text"]) for x in self.jobs])
        vec=self.embed.encode([x["text"] for x in self.jobs],normalize_embeddings=True,convert_to_numpy=True,show_progress_bar=False).astype("float32")
        self.index=faiss.IndexFlatIP(vec.shape[1]);self.index.add(vec);self.loaded=True
        return len(self.jobs)

    def ensure(self):
        if not self.loaded:self.sync()

    def bm25(self,q,n):
        scores=self.bm.get_scores(tok(q));return np.argsort(scores)[::-1][:n].tolist()

    def vector(self,q,n):
        v=self.embed.encode([q],normalize_embeddings=True,convert_to_numpy=True,show_progress_bar=False).astype("float32")
        _,ids=self.index.search(v,min(n,len(self.jobs)));return ids[0].tolist()

    def rrf(self,a,b):
        s={}
        for rank,idx in enumerate(a,1):s[idx]=s.get(idx,0)+1/(60+rank)
        for rank,idx in enumerate(b,1):s[idx]=s.get(idx,0)+1/(60+rank)
        return [i for i,_ in sorted(s.items(),key=lambda z:z[1],reverse=True)]

    def retrieve(self,q,k,pipeline):
        self.ensure()
        n=min(max(k*4,10),len(self.jobs))
        if pipeline=="bm25": ids=self.bm25(q,n);return [(i,float(n-r)) for r,i in enumerate(ids)]
        if pipeline=="vector": ids=self.vector(q,n);return [(i,float(n-r)) for r,i in enumerate(ids)]
        merged=self.rrf(self.bm25(q,n),self.vector(q,n))
        if pipeline=="hybrid": return [(i,float(len(merged)-r)) for r,i in enumerate(merged[:k])]
        pairs=[[q,self.jobs[i]["text"]] for i in merged[:n]]
        scores=self.rerank.predict(pairs)
        ranked=sorted(zip(merged[:n],scores),key=lambda z:float(z[1]),reverse=True)
        return [(i,float(s)) for i,s in ranked[:k]]

    def answer(self,q,p,items):
        if not items:return "I don't know based on the available job postings."
        context="\n\n".join(f"[Job {x['id']}] {x['title']} at {x['company']} — {x['text']}" for x in items)
        prompt=f"""Answer only from these job postings. If evidence is insufficient, say I don't know based on the available job postings. Cite important claims using [Job ID].
Candidate profile:
{p or "Not provided"}
Question:
{q}
Postings:
{context}"""
        try:
            r=requests.post(f"{LLM_BASE_URL}/chat/completions",headers={"Authorization":f"Bearer {LLM_API_KEY}","Content-Type":"application/json"},json={"model":LLM_MODEL,"messages":[{"role":"system","content":"Use only supplied job evidence."},{"role":"user","content":prompt}],"temperature":0.1},timeout=45)
            r.raise_for_status();return r.json()["choices"][0]["message"]["content"].strip()
        except Exception:
            x=items[0];return f"Top retrieved match: {x['title']} at {x['company']} [Job {x['id']}]. Review the posting before making a decision."

engine=Engine()
api=FastAPI(title="JOBPORTAL AI Search Service",version="1.0.0")
api.add_middleware(CORSMiddleware,allow_origins=["*"],allow_credentials=False,allow_methods=["*"],allow_headers=["*"])

@api.get("/health")
def health():return {"status":"ok","indexed":engine.loaded,"job_count":len(engine.jobs)}

@api.post("/api/ai/sync")
def sync():
    try:return {"status":"ok","indexed_jobs":engine.sync()}
    except Exception as e:raise HTTPException(502,str(e))

@api.post("/api/ai/search")
def search(x:Req):
    try:
        engine.ensure();start=time.perf_counter()
        ranked=engine.retrieve(f"{x.query} {x.profile}".strip(),x.top_k,"hybrid-rerank")
        if not ranked:return {"answer":"I don't know based on the available job postings.","pipeline":"hybrid-rerank","latency_ms":0,"results":[]}
        sources=[engine.jobs[i] for i,_ in ranked]
        results=[{"rank":r,"job_id":j["id"],"title":j["title"],"company":j["company"],"location":j["location"],"score":s,"snippet":j["text"][:300]} for r,((i,s),j) in enumerate(zip(ranked,sources),1)]
        answer="I don't know based on the available job postings." if ranked[0][1]<CONFIDENCE_THRESHOLD else engine.answer(x.query,x.profile,sources)
        return {"answer":answer,"pipeline":"hybrid-rerank","latency_ms":round((time.perf_counter()-start)*1000,2),"results":results}
    except Exception as e:raise HTTPException(500,str(e))

@api.post("/api/ai/skill-gap")
def gap(x:GapReq):
    try:
        engine.ensure();start=time.perf_counter();ranked=engine.retrieve(x.query,x.top_k,"hybrid-rerank")
        if not ranked:return {"job_title":None,"matched_skills":[],"missing_skills":[],"latency_ms":0}
        j=engine.jobs[ranked[0][0]];role=j["text"].lower();profile=x.profile.lower()
        required=[s for s in SKILLS if s in role]
        return {"job_title":j["title"],"matched_skills":[s for s in required if s in profile],"missing_skills":[s for s in required if s not in profile],"latency_ms":round((time.perf_counter()-start)*1000,2)}
    except Exception as e:raise HTTPException(500,str(e))

def recall5(r,rel):return int(bool(set(r[:5])&set(rel)))
def mrr(r,rel):
    for i,v in enumerate(r,1):
        if v in rel:return 1/i
    return 0
def ndcg5(r,rel):
    dcg=sum(1/math.log2(i+1) for i,v in enumerate(r[:5],1) if v in rel)
    ideal=sum(1/math.log2(i+1) for i in range(1,min(5,len(rel))+1))
    return dcg/ideal if ideal else 0

def evaluate(path):
    qs=json.loads(open(path,encoding="utf-8").read());rows=[]
    for pipe in ["bm25","vector","hybrid","hybrid-rerank"]:
        a=[];b=[];c=[];lat=[]
        for q in qs:
            st=time.perf_counter();pairs=engine.retrieve(q["query"],10,pipe);lat.append((time.perf_counter()-st)*1000)
            ranked=[engine.jobs[i]["id"] for i,_ in pairs];rel=set(q["relevant_ids"])
            a.append(recall5(ranked,rel));b.append(mrr(ranked,rel));c.append(ndcg5(ranked,rel))
        n=max(1,len(qs));rows.append({"pipeline":pipe,"recall@5":round(sum(a)/n,4),"mrr":round(sum(b)/n,4),"ndcg@5":round(sum(c)/n,4),"avg_latency_ms":round(sum(lat)/n,2)})
    return rows

if __name__=="__main__":
    import argparse
    ap=argparse.ArgumentParser();ap.add_argument("--evaluate");args=ap.parse_args()
    if args.evaluate:
        engine.sync()
        print(json.dumps(evaluate(args.evaluate),indent=2))
    else:
        import uvicorn;uvicorn.run(api,host="0.0.0.0",port=8000)
