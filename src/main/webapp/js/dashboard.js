const BASE_URL = "http://localhost:8080";

window.onload = function () {

    const companyId = sessionStorage.getItem("companyId");

    if (!companyId) {
        alert("Company login required");
        return;
    }

    loadJobs(companyId);
};


function loadJobs(companyId) {

    fetch(`${BASE_URL}/jobs/company/${companyId}`)
        .then(response => response.json())
        .then(jobs => {

            console.log(jobs);

            const container = document.getElementById("jobContainer");

            container.innerHTML = "";

            if (jobs.length === 0) {
                container.innerHTML = "<p>No jobs posted yet</p>";
                return;
            }

            jobs.forEach(job => {

                container.innerHTML += `

                <div class="job-card">

                    <h3>${job.title}</h3>

                    <p>
                        <b>Location:</b> ${job.location}
                    </p>

                    <p>
                        <b>Salary:</b> ₹${job.salary}
                    </p>

                    <p>
                        <b>Type:</b> ${job.jobType}
                    </p>

                    <p>
                        <b>Category:</b> ${job.category}
                    </p>

                    <p>
                        <b>Vacancy:</b> ${job.vacancy}
                    </p>

                    <p>
                        <b>Deadline:</b> ${job.deadline}
                    </p>

                </div>

                `;
            });

        })
        .catch(error => {
            console.log("Error loading jobs:", error);
        });
}