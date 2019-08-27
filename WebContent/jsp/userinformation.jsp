<%@page import="com.czy.bean.User"%>
<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.UserDao"%>
<%@page import="com.czy.service.UserService"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String uname = (String) session.getAttribute("uname");
	String uid = String.valueOf(session.getAttribute("uid"));
%>

<%
	UserDao dao = DAOFactory.getUserServiceInstance();
	User user = dao.queryByName(uname);
	if (user != null) {

	}
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>Account information</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<br>
	<div class="content clearfix">
	<div class="w3-container w3-margin-top">
	
		<form class="w3-container w3-card-4" action="servlet/EditInfoServlet" method="get">
			<div class="w3-panel w3-padding-16">
				<h1>Account information</h1>
			</div>
			<br>
			<p>
				<label class="w3-text-grey">Username: </label> <input
					class="w3-input w3-border" type="text" value="<%=user.getUname()%>" disabled="true">
			</p>
			<p>
				<label class="w3-text-grey">Password: </label> <input
					class="w3-input w3-border" name="Password" type="password" value=<%=user.getPasswd()%>>
			</p>
			<p>
				<label class="w3-text-grey">Email: </label> <input
					class="w3-input w3-border" type="text"  name="Email" value=<%=user.getEmail()%>>
			</p>
						<p>
				<label class="w3-text-grey">Last Login Time: </label> <input
					class="w3-input w3-border" type="text"  value=<%=session.getAttribute("lastLoginTime")%> disabled="true">
			</p>
						<p>
				<label class="w3-text-grey">Balance: </label> <input
					class="w3-input w3-border" value=<%=user.getMoney()%> disabled="true">
			</p>
			<br>
			<p>
				<button type="submit"  class="w3-btn w3-padding w3-teal"
					style="width: 120px">Change &nbsp; &#10095;</button>
			</p>
			
		</form>
		</div></div>
</body>
</html>