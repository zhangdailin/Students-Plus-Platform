<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.czy.bean.Apps"%>
<%@page import="com.czy.dao.AppsDao"%>
<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.bean.AlreadyBuy"%>
<%@page import="com.czy.dao.AlreadyBuyDao"%>
<%@page import="java.util.*"%>
<%@page import="java.net.URLEncoder"%>

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
	String uname = (String) session.getAttribute("uname");
	int aid = Integer.parseInt((request.getParameter("aid")).trim());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Authorize Service</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    	<%
    		AppsDao AppsDao = DAOFactory.getAppsServiceInstance();
    		AlreadyBuyDao dao = DAOFactory.getAlreadyBuyServiceInstance();
    		Apps apps;
    		String toUrl;
    		String appUrl;
    		apps = AppsDao.queryById(aid);
    		toUrl = apps.getDestdir();
    		int j = toUrl.indexOf("webapps/") + "webapps/".length();
    		appUrl = toUrl.substring(j - 1, toUrl.length()).replace(".war", "");	
    		String authorizenumber=null;
    		if(AppsDao.getAuthorizenumber(uid,aid)!=null){
				apps = AppsDao.getAuthorizenumber(uid, aid);
				Cookie cookie = new Cookie("appverify", URLEncoder.encode(apps.getAuthorizenumber(), "utf-8"));
    			cookie.setMaxAge(60*60);
    			cookie.setPath(appUrl);
    			response.addCookie(cookie);
    			response.sendRedirect(appUrl);
    		} else {
    	%>
	Failed to visit app. Please try again.
	<%
		}
	%>
  </body>
</html>
