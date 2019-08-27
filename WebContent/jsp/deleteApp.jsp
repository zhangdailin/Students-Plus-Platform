<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.AppsDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String Struid = (String) session.getAttribute("uid");
	int uid = 0;
	if (Struid != null) {
		uid = Integer.parseInt(Struid.trim());
	}
	int aid = Integer.parseInt((request.getParameter("aid")).trim());
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>Delete apps</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>
	<%
	AppsDao appDao = DAOFactory.getAppsServiceInstance();
		if (appDao.deleteApps(aid)) {
			response.sendRedirect("myapp.jsp");
		} else {
	%>
	Failed to delete the App. Please try again.
	<%
		}
	%>
</body>
</html>
