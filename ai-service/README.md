# JOBPORTAL AI Job Search & Retrieval Evaluation

The existing Spring Boot + MySQL recruitment platform now has a Python AI service.

## Features
- BM25 lexical retrieval
- Sentence-transformer embeddings with FAISS
- Hybrid retrieval with Reciprocal Rank Fusion
- BGE cross-encoder reranking
- RAG answers grounded in retrieved job postings with job-ID citations
- Low-confidence "I don't know" guardrail
- Candidate skill-gap analysis
- FastAPI REST API
- Retrieval evaluation: Recall@5, MRR, nDCG@5 and latency
- Optional OpenAI-compatible LLM endpoint
- Docker support

## Run

1. Start the existing Spring Boot JOBPORTAL on port 8080.
2. Open ai-service and create a Python 3.11 virtual environment.
3. Install requirements.
4. Run: python app.py
5. Open the candidate dashboard.
6. Use AI Job Search & Skill Gap.

The first startup downloads the embedding and reranker models.

The service reads jobs from GET http://localhost:8080/jobs.

## LLM

Defaults target a local Ollama OpenAI-compatible endpoint:
LLM_BASE_URL=http://localhost:11434/v1
LLM_API_KEY=ollama
LLM_MODEL=llama3.2:3b

If no LLM is reachable, the service returns a grounded top-match fallback rather than inventing job facts.

## Evaluation

Create evaluation/questions.json from evaluation/questions.example.json and manually label relevant job IDs for 50–100 realistic questions.

Run:
python app.py --evaluate evaluation/questions.json

Do not claim a measured improvement until the evaluation has actually been run.

## Resume-safe description

Extended a Spring Boot recruitment platform with a Python RAG service using BM25, FAISS vector search, hybrid RRF retrieval and cross-encoder reranking; evaluated retrieval with Recall@5, MRR, nDCG@5 and latency, and exposed the AI layer through FastAPI.
