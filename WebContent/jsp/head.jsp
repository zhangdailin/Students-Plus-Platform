<%@page import="com.czy.bean.User"%>
<%@page import="java.util.List"%>
<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.UserDao"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<%
	String uname = (String) session.getAttribute("uname");
	String uid = String.valueOf(session.getAttribute("uid"));
	UserDao dao = DAOFactory.getUserServiceInstance();
	User user = dao.queryByName(uname);
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css" media="all" >
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

</head>
<body>


	<div class="top">
	<div class="top1">
		<h1><a class="navbar-brand" href="index.jsp">Students+</a></h1>
        <ul>
        	<li><a href="index.jsp">HOME</a></li>
        	<li><a href="jsp/shoppingCart.jsp">SHOPPING CART</a></li>
        	<li><a href="jsp/nutsbank.jsp">NUTS BANK</a></li>
        	<li><a href="jsp/upload.jsp">UPLOAD APP</a></li>
        	<li><a href="jsp/myapp.jsp">MY APPS</a></li>
        	<li>
        </ul>
    </div>
</div>
	<section class="section w3-light-grey w3-hover-opacity">
	<div class="w3-container w3-center">
				<p class="log">
					<%
							if (uname != null) {
												out.print("<a><font size=6><font color=Black>" + uname + "</font></font>  Welcome!" + "</a>");
												out.print("<a href=\"jsp/userinformation.jsp\" target=\"_blank\">"
														+ "<font size=4>Account information</font>" + "</a>");
												out.print("<a href=\"servlet/LogoutServlet\">"
														+ "Logout" + "</a>");
											} else {
												out.print("<a href=\"jsp/login.jsp\"><font size=4>Login</font></a>");
												out.print("<a>or</a>");
												out.print("<a href=\"jsp/register.jsp\"><font size=4>Register</font></a>");
											}
						%>
				</p>
	</div>
	</section>
</body>
</html>

