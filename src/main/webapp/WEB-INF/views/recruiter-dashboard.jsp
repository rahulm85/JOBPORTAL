<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1.0">


<title>Recruiter Dashboard</title>



<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/common.css">


<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/recruiter.css">



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



<a href="#create">

Create Job

</a>



<a href="#jobs">

My Jobs

</a>



<a href="#applications">

Applications

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

Recruiter Dashboard

</h1>


</div>





<!-- STATISTICS -->


<div class="cards">



<div class="dashboard-card">


<h3>

Total Jobs

</h3>



<h1 id="totalJobs">

0

</h1>



</div>





<div class="dashboard-card">


<h3>

Active Jobs

</h3>



<h1 id="activeJobs">

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






<!-- CREATE JOB -->


<div
class="section"
id="create">


<h2>

Create Job

</h2>



<div class="form-grid">


<input
id="title"
placeholder="Job Title">



<input
id="location"
placeholder="Location">



<input
id="salary"
type="number"
placeholder="Salary">



<input
id="experience"
type="number"
placeholder="Experience">



<input
id="jobType"
placeholder="Job Type">



<input
id="category"
placeholder="Category">



<input
id="vacancy"
type="number"
placeholder="Vacancy">



<input
id="deadline"
type="date">



</div>





<textarea
id="description"
rows="5"
placeholder="Job Description"></textarea>



<button
class="primary-btn"
onclick="createJob()">


Post Job


</button>



</div>
<!-- SEARCH JOBS -->


<div class="section">


<h2>

Search Jobs

</h2>



<input
id="searchJob"
placeholder="Search by Title"
onkeyup="searchJob()">



</div>






<!-- JOBS TABLE -->


<div
class="section"
id="jobs">


<h2>

Posted Jobs

</h2>




<table>


<thead>


<tr>


<th>Title</th>

<th>Location</th>

<th>Salary</th>

<th>Type</th>

<th>Applications</th>

<th>Edit</th>

<th>Delete</th>



</tr>


</thead>




<tbody id="jobsBody">



</tbody>




</table>



</div>






<!-- APPLICATIONS -->


<div
class="section"
id="applications">


<h2>

Applicants

</h2>




<table>



<thead>


<tr>


<th>Candidate</th>

<th>Job</th>

<th>Status</th>

<th>Resume</th>

<th>Accept</th>

<th>Reject</th>



</tr>



</thead>




<tbody id="applicationBody">



</tbody>



</table>



</div>





</div> <!-- content end -->



</div> <!-- dashboard end -->





<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>

<script
src="${pageContext.request.contextPath}/js/recruiter.js"></script>


</body>


</html>