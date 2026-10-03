var BASE_URL = "http://localhost:8080";
var appliedJobs = [];
var candidateId = sessionStorage.getItem("userId");
var selectedJobId = null;



window.onload = function () {


    if (!candidateId) {


        alert("Please login first");


        window.location.href = "/login";


        return;


    }


    loadJobs();


    loadSavedJobs();


    loadApplications();


};




// ---------------- AJAX FUNCTION ----------------


function sendRequest(method, url, data, callback) {


    var xhr = new XMLHttpRequest();


    xhr.open(method, url, true);



    xhr.setRequestHeader(
        "Content-Type",
        "application/json"
    );



    xhr.onreadystatechange = function () {


        if (xhr.readyState == 4) {


            if (xhr.status >= 200 && xhr.status < 300) {


                callback(xhr.responseText);


            }

            else {


                console.log(xhr.responseText);


            }


        }


    };



    if (data) {


        xhr.send(JSON.stringify(data));


    }

    else {


        xhr.send();


    }


}





// ---------------- LOAD ALL JOBS ----------------



function loadJobs() {


    sendRequest(


        "GET",


        BASE_URL + "/jobs",


        null,


        function(response){


            console.log("Jobs Response:", response);



            var jobs = JSON.parse(response);



            document.getElementById("totalJobs").innerText =
                jobs.length;



            var body =
            document.getElementById("jobsBody");



            body.innerHTML = "";


			for(var i=0;i<jobs.length;i++){

			    var job = jobs[i];

			    body.innerHTML +=
			    "<tr>" +
			    "<td>" + job.title + "</td>" +
			    "<td>" + job.companyName + "</td>" +
			    "<td>" + job.location + "</td>" +
			    "<td>₹ " + job.salary + "</td>" +
			    "<td>" + job.experience + "</td>" +
			    "<td>" + job.jobType + "</td>" +

			    "<td>" +
			    "<button class='primary-btn' id='applyBtn"+job.id+"' onclick='applyJob("+job.id+")'>Apply</button>" +
			    "</td>" +

			    "<td>" +
			    "<button class='secondary-btn' onclick='saveJob("+job.id+")'>Save</button>" +
			    "</td>" +

			    "</tr>";

			}


			// after loop
			for(var i=0;i<jobs.length;i++){

			    checkApplied(jobs[i].id);

			}

			        }

			    );

			}





// ---------------- APPLY JOB ----------------



function applyJob(jobId) {

    selectedJobId = jobId;

    sendRequest(
        "GET",
        BASE_URL + "/api/users/" + candidateId,
        null,
        function(response) {

            var user = JSON.parse(response);

            document.getElementById("candidateName").value = user.fullName;
            document.getElementById("candidateEmail").value = user.email;
            document.getElementById("candidatePhone").value = user.phone;

            document.getElementById("coverLetter").value = "";

            // clear previous resume
            document.getElementById("resumeFile").value = "";

           document.getElementById("applyModal").style.display = "flex";

        }
    );

}

// ---------------- SAVE JOB ----------------



function saveJob(jobId){


    var request = {

        candidateId: Number(candidateId),

        jobId: Number(jobId)

    };


    sendRequest(

        "POST",

        BASE_URL + "/saved-job",

        request,

        function(response){


            alert(response);


            loadSavedJobs();


        }

    );


}







// ---------------- SAVED JOBS ----------------



function loadSavedJobs(){


    sendRequest(

        "GET",

        BASE_URL + "/saved-jobs/" + candidateId,

        null,

        function(response){


            var savedJobs = JSON.parse(response);



            document.getElementById("savedCount").innerText =
                savedJobs.length;



            var body =
            document.getElementById("savedJobsBody");



            body.innerHTML = "";



            for(var i = 0; i < savedJobs.length; i++){


                var job = savedJobs[i];



                body.innerHTML +=


                "<tr>" +

                "<td>" + job.jobTitle + "</td>" +

                "<td>" + job.savedDate + "</td>" +

                "<td>" +

                "<button class='danger-btn' onclick='removeSavedJob("

                + job.id +

                ")'>Remove</button>" +

                "</td>" +

                "</tr>";



            }


        }

    );


}




// ---------------- APPLICATIONS ----------------



// ---------------- APPLICATIONS ----------------

function loadApplications() {

    sendRequest(

        "GET",

        BASE_URL + "/applications/candidate/" + candidateId,

        null,

        function(response) {

            var applications = JSON.parse(response);

            document.getElementById("applicationCount").innerText = applications.length;

            var body = document.getElementById("applicationBody");

            body.innerHTML = "";

            for (var i = 0; i < applications.length; i++) {

                var application = applications[i];

                body.innerHTML +=

                    "<tr>" +

                    "<td>" + application.jobTitle + "</td>" +

                    "<td>" + application.status + "</td>" +

                    "<td>" + application.appliedDate + "</td>" +

                    "</tr>";

            }

        }

    );

}


// ---------------- SEARCH JOBS ----------------



function searchJobs(){



    var title =
    document.getElementById("searchTitle").value.toLowerCase();



    var location =
    document.getElementById("searchLocation").value.toLowerCase();



    var category =
    document.getElementById("searchCategory").value.toLowerCase();



    var rows =
    document.querySelectorAll("#jobsBody tr");



    for(var i=0;i<rows.length;i++){



        var row =
        rows[i].innerText.toLowerCase();



        if(
            row.includes(title) &&
            row.includes(location) &&
            row.includes(category)
        ){


            rows[i].style.display="";


        }

        else{


            rows[i].style.display="none";


        }


    }



}






// ---------------- LOGOUT ----------------



function logout(){



    sessionStorage.clear();



    window.location.href="/login";



}
function closeApplyModal() {

    document.getElementById("applyModal").style.display = "none";

}
function submitApplication() {


    var resume =
    document.getElementById("resumeFile").files[0];


    if(!resume){

        alert("Please upload your resume");

        return;

    }



    var formData = new FormData();


    formData.append(
        "candidateId",
        candidateId
    );


    formData.append(
        "jobId",
        selectedJobId
    );


    formData.append(
        "coverLetter",
        document.getElementById("coverLetter").value
    );


    formData.append(
        "resume",
        resume
    );



    var xhr = new XMLHttpRequest();


    xhr.open(
        "POST",
        BASE_URL + "/application",
        true
    );



    xhr.onload = function(){


        if(xhr.status >= 200 && xhr.status < 300){


            alert("Application submitted successfully");


            closeApplyModal();


            loadApplications();


        }

        else{


            alert(xhr.responseText);


        }


    };


    xhr.send(formData);


}
function removeSavedJob(id){


    sendRequest(

        "DELETE",

        BASE_URL + "/saved-job/" + id,

        null,

        function(response){


            alert(response);


            loadSavedJobs();


        }

    );


}			function checkApplied(jobId){

	    sendRequest(
	        "GET",
	        BASE_URL + "/application/check?candidateId="
	        + candidateId
	        + "&jobId="
	        + jobId,

	        null,

	        function(response){

	            if(response.trim() === "true"){

	                var btn = document.getElementById(
	                    "applyBtn" + jobId
	                );

	                if(btn){

	                    btn.innerText = "Applied";
	                    btn.disabled = true;
	                    btn.style.background = "gray";

	                }

	            }

	        }
	    );

	}
/* ---------------- AI JOB INTELLIGENCE ---------------- */
var AI_BASE_URL=window.location.protocol+"//"+window.location.hostname+":8000";
function aiText(v){var d=document.createElement("div");d.textContent=v==null?"":String(v);return d.innerHTML;}
function aiStatus(v){var e=document.getElementById("aiStatus");if(e)e.innerText=v||"";}
function runAISearch(){
 var q=document.getElementById("aiQuery").value.trim(),p=document.getElementById("aiProfile").value.trim(),k=Number(document.getElementById("aiTopK").value);
 if(!q){alert("Enter an AI job search question");return;}
 aiStatus("Searching with hybrid retrieval and reranking...");
 fetch(AI_BASE_URL+"/api/ai/search",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({query:q,profile:p,top_k:k})})
 .then(r=>r.ok?r.json():r.text().then(t=>{throw new Error(t||"AI service error")}))
 .then(d=>{
  aiStatus("Pipeline: "+d.pipeline+" | Latency: "+d.latency_ms+" ms");
  document.getElementById("aiAnswer").innerHTML="<h3>AI Answer</h3><p>"+aiText(d.answer)+"</p>";
  var out="<h3>Retrieved Jobs</h3>";
  d.results.forEach(x=>out+="<div class='ai-job-card'><strong>#"+x.rank+" "+aiText(x.title)+"</strong><span>"+aiText(x.company)+"</span><span>"+aiText(x.location)+"</span><small>Score: "+Number(x.score).toFixed(4)+" | Job ID: "+x.job_id+"</small><p>"+aiText(x.snippet)+"</p></div>");
  document.getElementById("aiResults").innerHTML=out;
 }).catch(e=>{aiStatus("AI service unavailable.");document.getElementById("aiAnswer").innerHTML="<p class='ai-error'>"+aiText(e.message)+"</p>";});
}
function runSkillGap(){
 var q=document.getElementById("aiQuery").value.trim(),p=document.getElementById("aiProfile").value.trim();
 if(!q||!p){alert("Enter both the target role/question and your profile");return;}
 aiStatus("Analyzing required and missing skills...");
 fetch(AI_BASE_URL+"/api/ai/skill-gap",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({query:q,profile:p,top_k:3})})
 .then(r=>r.ok?r.json():r.text().then(t=>{throw new Error(t||"Skill-gap service error")}))
 .then(d=>{
  aiStatus("Skill-gap latency: "+d.latency_ms+" ms");
  document.getElementById("aiAnswer").innerHTML="<h3>Skill Gap Analysis</h3><p><strong>Role:</strong> "+aiText(d.job_title||"No matching role")+"</p><p><strong>Matched:</strong> "+aiText(d.matched_skills.join(", ")||"None detected")+"</p><p><strong>Missing:</strong> "+aiText(d.missing_skills.join(", ")||"None detected")+"</p>";
 }).catch(e=>{aiStatus("AI service unavailable.");document.getElementById("aiAnswer").innerHTML="<p class='ai-error'>"+aiText(e.message)+"</p>";});
}
