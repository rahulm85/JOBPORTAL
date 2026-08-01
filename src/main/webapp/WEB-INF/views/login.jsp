<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1.0">


<title>Login | Job Portal</title>



<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/common.css">


<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/login.css">



</head>



<body class="auth-body">



<div class="auth-container">



<!-- LEFT PANEL -->


<div class="auth-left">


<h1>

Welcome Back

</h1>



<p>

Login to continue your journey and explore thousands of opportunities.

</p>



<img
src="https://img.magnific.com/free-vector/man-search-hiring-job-online-from-laptop_1150-52728.jpg"
alt="Login">



</div>





<!-- RIGHT PANEL -->


<div class="auth-right">



<div class="auth-card">



<h2>

Login

</h2>





<form onsubmit="login(); return false;">



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





<button
type="submit"
class="primary-btn full-btn">


Login


</button>





</form>



<div class="divider">

OR

</div>
<p class="bottom-text">


Don't have an account?


<a href="${pageContext.request.contextPath}/register">


Register


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
src="${pageContext.request.contextPath}/js/login.js"></script>



</body>


</html>