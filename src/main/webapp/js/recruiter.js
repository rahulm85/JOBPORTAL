var BASE_URL = "http://localhost:8080";

var companyId = sessionStorage.getItem("companyId");

window.onload = function () {

    if (companyId == null || companyId == "") {

        alert("Please login first");

        window.location.href = "/login";

        return;

    }

	loadJobs();
	loadApplications();

};

// ----------------------------
// LOAD RECRUITER JOBS
// ----------------------------

function loadJobs() {

    var xhr = new XMLHttpRequest();

    xhr.open("GET", BASE_URL + "/jobs/company/" + companyId, true);

    xhr.onreadystatechange = function () {

        if (xhr.readyState == 4 && xhr.status == 200) {

            var jobs = JSON.parse(xhr.responseText);

            console.log(jobs);

            var body = document.getElementById("jobsBody");

            body.innerHTML = "";

            var total = 0;

            var active = 0;


            for (var i = 0; i < jobs.length; i++) {

                var job = jobs[i];

                total++;


                if (job.active) {

                    active++;

                }


				body.innerHTML +=

				"<tr>" +

				"<td>" + job.title + "</td>" +

				"<td>" + job.location + "</td>" +

				"<td>₹ " + job.salary + "</td>" +

				"<td>" + job.jobType + "</td>" +

				"<td id='appCount" + job.id + "'>0</td>" +

				"<td>" +

				"<button class='edit-btn' onclick='fillJob(" + job.id + ")'>" +

				"Edit" +

				"</button>" +

				"</td>" +

				"<td>" +

				"<button class='delete-btn' onclick='deleteJob(" + job.id + ")'>" +

				"Delete" +

				"</button>" +

				"</td>" +

				"</tr>";
				loadApplicantCount(job.id, "appCount" + job.id);

            }


            document.getElementById("totalJobs").innerHTML = total;

            document.getElementById("activeJobs").innerHTML = active;


        }

    };


    xhr.send();

}
// ----------------------------
// CREATE JOB
// ----------------------------

function createJob() {

    var request = {

        companyId: Number(companyId),

        title: document.getElementById("title").value,

        description: document.getElementById("description").value,

        location: document.getElementById("location").value,

        salary: Number(document.getElementById("salary").value),

        experience: Number(document.getElementById("experience").value),

        jobType: document.getElementById("jobType").value,

        category: document.getElementById("category").value,

        vacancy: Number(document.getElementById("vacancy").value),

        deadline: document.getElementById("deadline").value

    };

    var xhr = new XMLHttpRequest();

    xhr.open("POST", BASE_URL + "/jobs", true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {

        if (xhr.readyState == 4) {

            alert(xhr.responseText);

            clearForm();

            loadJobs();

        }

    };

    xhr.send(JSON.stringify(request));

}

// ----------------------------
// CLEAR FORM
// ----------------------------

function clearForm() {

    document.getElementById("title").value = "";

    document.getElementById("description").value = "";

    document.getElementById("location").value = "";

    document.getElementById("salary").value = "";

    document.getElementById("experience").value = "";

    document.getElementById("jobType").value = "";

    document.getElementById("category").value = "";

    document.getElementById("vacancy").value = "";

    document.getElementById("deadline").value = "";

}	// ----------------------------
	// DELETE JOB
	// ----------------------------

	function deleteJob(id) {

	    if (!confirm("Delete this job?")) {
	        return;
	    }

	    var xhr = new XMLHttpRequest();

	    xhr.open("DELETE", BASE_URL + "/jobs/" + id, true);

	    xhr.onreadystatechange = function () {

	        if (xhr.readyState == 4) {

	            alert(xhr.responseText);

	            loadJobs();

	        }

	    };

	    xhr.send();

	}

	// ----------------------------
	// EDIT JOB
	// ----------------------------

	function fillJob(id) {

	    var xhr = new XMLHttpRequest();

	    xhr.open("GET", BASE_URL + "/jobs/" + id, true);

	    xhr.onreadystatechange = function () {

	        if (xhr.readyState == 4 && xhr.status == 200) {

	            var job = JSON.parse(xhr.responseText);

	            document.getElementById("title").value = job.title;
	            document.getElementById("description").value = job.description;
	            document.getElementById("location").value = job.location;
	            document.getElementById("salary").value = job.salary;
	            document.getElementById("experience").value = job.experience;
	            document.getElementById("jobType").value = job.jobType;
	            document.getElementById("category").value = job.category;
	            document.getElementById("vacancy").value = job.vacancy;
	            document.getElementById("deadline").value = job.deadline;

	            var button = document.querySelector(".primary-btn");

	            button.innerHTML = "Update Job";

	            button.onclick = function () {

	                updateJob(id);

	            };

	        }

	    };

	    xhr.send();

	}

	// ----------------------------
	// UPDATE JOB
	// ----------------------------

	function updateJob(id) {

	    var request = {

	        companyId: Number(companyId),
	        title: document.getElementById("title").value,
	        description: document.getElementById("description").value,
	        location: document.getElementById("location").value,
	        salary: Number(document.getElementById("salary").value),
	        experience: Number(document.getElementById("experience").value),
	        jobType: document.getElementById("jobType").value,
	        category: document.getElementById("category").value,
	        vacancy: Number(document.getElementById("vacancy").value),
	        deadline: document.getElementById("deadline").value

	    };

	    var xhr = new XMLHttpRequest();

	    xhr.open("PUT", BASE_URL + "/jobs/" + id, true);

	    xhr.setRequestHeader("Content-Type", "application/json");

	    xhr.onreadystatechange = function () {

	        if (xhr.readyState == 4) {

	            alert(xhr.responseText);

	            clearForm();

	            var button = document.querySelector(".primary-btn");

	            button.innerHTML = "Post Job";

	            button.onclick = createJob;

	            loadJobs();

	        }

	    };

	    xhr.send(JSON.stringify(request));

	}

	// ----------------------------
	// SEARCH JOB
	// ----------------------------

	function searchJob() {

	    var value = document.getElementById("searchJob").value.toLowerCase();

	    var rows = document.querySelectorAll("#jobsBody tr");

	    var i;

	    for (i = 0; i < rows.length; i++) {

	        if (rows[i].innerText.toLowerCase().indexOf(value) > -1) {

	            rows[i].style.display = "";

	        } else {

	            rows[i].style.display = "none";

	        }

	    }

	}

	// ----------------------------
	// LOGOUT
	// ----------------------------

	function logout() {

	    sessionStorage.clear();

	    window.location.href = "/login";

	}
	function loadApplicantCount(jobId, elementId){

	    var xhr = new XMLHttpRequest();

	    xhr.open(
	        "GET",
	        BASE_URL + "/applications/job/" + jobId,
	        true
	    );


	    xhr.onreadystatechange = function(){

	        if(xhr.readyState == 4 && xhr.status == 200){

	            var applicants = JSON.parse(xhr.responseText);

	            document.getElementById(elementId).innerHTML =
	                applicants.length;

	        }

	    };


	    xhr.send();

	}
	// ----------------------------
	// LOAD APPLICANTS
	// ----------------------------

	function loadApplications(){

	    var xhr = new XMLHttpRequest();

	    xhr.open(
	        "GET",
	        BASE_URL + "/applications/company/" + companyId,
	        true
	    );


	    xhr.onreadystatechange = function(){

	        if(xhr.readyState == 4 && xhr.status == 200){


	            var applications =
	            JSON.parse(xhr.responseText);


	            document.getElementById("applicationCount").innerHTML =
	            applications.length;


	            var body =
	            document.getElementById("applicationBody");


	            body.innerHTML = "";


	            for(var i=0;i<applications.length;i++){


	                var app = applications[i];


	                body.innerHTML +=

	                "<tr>" +

	                "<td>" + app.candidateName + "</td>" +

	                "<td>" + app.jobTitle + "</td>" +

	                "<td>" + app.status + "</td>" +

	                "<td>" +

	                "<button class='edit-btn'>" +
	                "View Resume" +
	                "</button>" +

	                "</td>" +

	                "<td>" +

	                "<button class='primary-btn' onclick='updateStatus("+app.id+",\"ACCEPTED\")'>" +
	                "Accept" +
	                "</button>" +

	                "</td>" +

	                "<td>" +

	                "<button class='delete-btn' onclick='updateStatus("+app.id+",\"REJECTED\")'>" +
	                "Reject" +
	                "</button>" +

	                "</td>" +

	                "</tr>";

	            }

	        }

	    };


	    xhr.send();

	}
	function updateStatus(id,status){


	    var xhr = new XMLHttpRequest();


	    xhr.open(
	        "PUT",
	        BASE_URL + "/application/" + id + "/status?status=" + status,
	        true
	    );


	    xhr.onreadystatechange=function(){

	        if(xhr.readyState==4){

	            alert(xhr.responseText);

	            loadApplications();

	        }

	    };


	    xhr.send();

	}