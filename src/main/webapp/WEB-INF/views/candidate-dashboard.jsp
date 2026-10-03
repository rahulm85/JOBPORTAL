<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta name="viewport"
content="width=device-width, initial-scale=1.0">

<title>Candidate Dashboard</title>

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/common.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/candidate.css">
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/ai-search.css">

</head>


<body>


<div class="dashboard">


<!-- SIDEBAR -->

<div class="sidebar">

    <h2>
        JobPortal
    </h2>


    <a href="#dashboard">
        Dashboard
    </a>


    <a href="#jobs">
        Browse Jobs
    </a>


    <a href="#saved">
        Saved Jobs
    </a>


    <a href="#applications">
        My Applications
    </a>


    <button
    class="logout-btn"
    onclick="logout()">

        Logout

    </button>


</div>



<!-- MAIN CONTENT -->

<div class="content">


<div class="top-bar">

<h1>
Candidate Dashboard
</h1>

</div>



<!-- DASHBOARD CARDS -->


<div class="cards">


<div class="dashboard-card">

<h3>
Available Jobs
</h3>

<h1 id="totalJobs">
0
</h1>

</div>



<div class="dashboard-card">

<h3>
Saved Jobs
</h3>

<h1 id="savedCount">
0
</h1>

</div>



<div class="dashboard-card">

<h3>
Applications
</h3>

<h1 id="applicationCount">
0
</h1>

</div>



</div>





<!-- SEARCH -->


<div class="section">


<h2>
Search Jobs
</h2>


<div class="search-grid">


<input
id="searchTitle"
placeholder="Job Title"
onkeyup="searchJobs()">



<input
id="searchLocation"
placeholder="Location"
onkeyup="searchJobs()">



<input
id="searchCategory"
placeholder="Category"
onkeyup="searchJobs()">



</div>


</div>






<div class="section" id="ai-search">
<h2>AI Job Search & Skill Gap</h2>
<p class="ai-subtitle">Hybrid keyword + semantic job retrieval with reranking and skill-gap analysis.</p>
<div class="ai-panel">
<textarea id="aiProfile" rows="4" placeholder="Your profile: Java, Spring Boot, MySQL, REST APIs, Python, SQL, Git"></textarea>
<div class="ai-controls">
<input id="aiQuery" type="text" placeholder="Which Java backend roles fit my profile?">
<select id="aiTopK"><option value="5">Top 5</option><option value="10">Top 10</option></select>
<button class="primary-btn" onclick="runAISearch()">AI Search</button>
<button class="secondary-btn" onclick="runSkillGap()">Skill Gap</button>
</div>
<div id="aiStatus" class="ai-status"></div>
<div id="aiAnswer" class="ai-answer"></div>
<div id="aiResults" class="ai-results"></div>
</div>
</div>
<!-- BROWSE JOBS -->


<div
class="section"
id="jobs">


<h2>
Browse Jobs
</h2>


<table>


<thead>


<tr>

<th>Title</th>

<th>Company</th>

<th>Location</th>

<th>Salary</th>

<th>Experience</th>

<th>Job Type</th>

<th>Apply</th>

<th>Save</th>


</tr>


</thead>



<tbody id="jobsBody">


</tbody>



</table>



</div>
<!-- SAVED JOBS -->


<div
class="section"
id="saved">


<h2>
Saved Jobs
</h2>



<table>


<thead>


<tr>

<th>Job</th>

<th>Saved On</th>

<th>Remove</th>


</tr>


</thead>



<tbody id="savedJobsBody">


</tbody>



</table>



</div>





<!-- APPLICATIONS -->


<div
class="section"
id="applications">


<h2>
My Applications
</h2>



<table>


<thead>


<tr>

<th>Job</th>

<th>Status</th>

<th>Applied Date</th>


</tr>


</thead>



<tbody id="applicationBody">


</tbody>



</table>



</div>





</div> <!-- content end -->

</div> <!-- dashboard end -->


<!-- APPLY JOB MODAL -->

<!-- APPLY JOB MODAL -->

<div id="applyModal" class="modal" style="display:none;">

    <div class="modal-content">

        <span class="close" onclick="closeApplyModal()">
            &times;
        </span>

        <h2>Apply for Job</h2>


        <input type="hidden" id="applyJobId">


        <div class="form-group">

            <label>Full Name</label>

            <input type="text" id="candidateName" readonly>

        </div>



        <div class="form-group">

            <label>Email</label>

            <input type="email" id="candidateEmail" readonly>

        </div>



        <div class="form-group">

            <label>Phone</label>

            <input type="text" id="candidatePhone" readonly>

        </div>



       <div class="form-group">

<label>
Resume
</label>


<input 
type="file"
id="resumeFile"
accept=".pdf,.doc,.docx">

</div>


        <div class="form-group">

            <label>Cover Letter</label>

            <textarea 
                id="coverLetter"
                rows="5"
                placeholder="Write a short cover letter">
            </textarea>

        </div>



        <button 
            class="primary-btn"
            onclick="submitApplication()">

            Submit Application

        </button>


    </div>

</div>
<script
src="${pageContext.request.contextPath}/js/candidate.js"></script>

</body>

</html>