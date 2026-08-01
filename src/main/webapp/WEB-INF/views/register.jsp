<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1.0">


<title>Register | Job Portal</title>



<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/common.css">


<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/register.css">



</head>



<body class="auth-body">



<div class="auth-container">



<!-- LEFT SIDE -->


<div class="auth-left">


<h1>

Create Account

</h1>




<p>

Join thousands of recruiters and job seekers using Job Portal.

</p>




<img
src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJNSJZGosNwAphWaoG39FuhlMkIF6myBUJs8BUaglVgQ&s=10"
alt="Register">



</div>





<!-- RIGHT SIDE -->


<div class="auth-right">



<div class="auth-card register-card">



<h2>

Register

</h2>





<form onsubmit="register(); return false;">





<div class="input-group">


<label>

Full Name

</label>



<input
type="text"
id="fullName"
placeholder="Enter Full Name"
required>



</div>






<div class="input-group">


<label>

Email

</label>



<input
type="email"
id="email"
placeholder="Enter Email"
required>



</div>






<div class="input-group">


<label>

Password

</label>



<input
type="password"
id="password"
placeholder="Enter Password"
required>



</div>






<div class="input-group">


<label>

Phone Number

</label>



<input
type="text"
id="phone"
placeholder="Enter Phone Number"
required>



</div>






<div class="input-group">


<label>

Role

</label>



<select
id="role"
onchange="toggleCompanyFields()">



<option value="CANDIDATE">

Applicant

</option>



<option value="RECRUITER">

Recruiter

</option>



</select>



</div>






<div
id="companyFields">



<hr>



<h3>

Company Details

</h3>
<div class="input-group">


<label>

Company Name

</label>



<input
type="text"
id="companyName"
placeholder="Company Name">



</div>





<div class="input-group">


<label>

Description

</label>



<textarea
id="description"
rows="4"
placeholder="Company Description"></textarea>



</div>





<div class="input-group">


<label>

Website

</label>



<input
type="text"
id="website"
placeholder="https://company.com">



</div>





<div class="input-group">


<label>

Location

</label>



<input
type="text"
id="location"
placeholder="City">



</div>





</div>





<button
type="submit"
class="primary-btn full-btn">


Register


</button>





</form>






<div class="divider">


</div>






<p class="bottom-text">


Already have an account?



<a href="${pageContext.request.contextPath}/login">


Login


</a>



</p>






<p class="bottom-text">


<a href="${pageContext.request.contextPath}/">


← Back to Home


</a>



</p>





</div>


</div>



</div>






<script
src="${pageContext.request.contextPath}/js/register.js"></script>





</body>


</html>