<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>User Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
table td {
	font-size: 19px;
}

label {
	cursor: pointer;
	margin-right: 1em;
}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div class="content clearfix">
		<div class="w3-container w3-margin-top">

			<form class="w3-container w3-card-4" action="servlet/RegisterServlet"
				method="post">
				<div class="w3-panel w3-padding-16">
					<h1>Registration Form</h1>
				</div>
				<p>
					<label>Username: </label> <input class="w3-input" type="text"
						style="width: 90%" required name="uname">
				</p>
				<p>
					<label>Password: </label> <input class="w3-input" type="password"
						style="width: 90%" required name="passwd">
				</p>
				<p>
					<label>Email: </label> <input class="w3-input" type="text"
						style="width: 90%" required name="Email">
				</p>
				<p>
					<button class="w3-button w3-section w3-teal w3-ripple">Register</button>
				</p>

			</form>
		</div>
	</div>
</body>
</html>