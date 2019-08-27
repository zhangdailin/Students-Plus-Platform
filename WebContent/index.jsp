<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.czy.dao.AppsDao"%>
<%@ page import="com.czy.bean.Apps"%>
<%@ page import="com.czy.factory.DAOFactory"%>
<%@ page import="java.util.List"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>Student+</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<jsp:include page="./jsp/head.jsp"></jsp:include>

	<div class="box">
		<div class="header">
			<h2>WELCOME TO STUDENTS+</h2>
			<p>STUDENT QUALITY APP SHARING PLATFORM</p>

			<%
				if (session.getAttribute("uid") == null) {
			%>
			<a href="./jsp/register.jsp" class="btn btn-banner">Learn More</a>
			<%
				} else {
			%>
			<a href="./index.jsp" class="btn btn-banner">Learn More</a>
			<%
				}
			%>
		</div>
	</div>

	<div class="content clearfix">
		<%
					AppsDao dao = DAOFactory.getAppsServiceInstance();
					List<Apps> appsList = dao.getAllApps();
					if (appsList != null && appsList.size() > 0) {
						for (int i = 0; i < appsList.size(); i++) {
							if (i % 4 == 0) {
				%>
		<div class="content-top1">
			<%
						}
					%>
			<div class="content-item">
				<a href="jsp/AppsDescribed.jsp?aid=<%=appsList.get(i).getAid()%>">
					<img class="img-responsive" src=<%="images/app.jpg"%> />
					<h2><%=appsList.get(i).getAname()%></h2>
				</a>
				<p>5 Nuts</p>
				<div class="clearfix"></div>
			</div>
			<%
						if (i % 4 == 3) {
					%>
			<div class="clearfix"></div>
		</div>
		<%
					}
						}
					}
				%>
	</div>

</body>
</html>