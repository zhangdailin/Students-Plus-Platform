<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.UserDao"%>
<%@page import="com.czy.bean.User"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>Upload</title>
<meta http-equiv="Content-Type" content="text/html" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>

<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div class="content clearfix">
	<div class="w3-container">
			<div class="w3-panel w3-padding-16">
				<h1>File Upload</h1>
			</div>

			<form method="post" action="servlet/UploadServlet" enctype="multipart/form-data">
				<table class="w3-table">
					<%
						String Struid = (String) session.getAttribute("uid");
						int uid = 0;
						if (Struid != null) {
							uid = Integer.parseInt(Struid.trim());
						}
						
						UserDao dao = DAOFactory.getUserServiceInstance();
						User user = dao.queryByuid(uid);
					%>
					<tr>
						<th>App Name:</th>
						<th><input type="text" name="name"></th>
					</tr>
					<tr>
						<th>App file:</th>
						<th><input type="file" name="file" accept=".war" size="60" /></th>
					<tr>
						<th>App description:</th>
						<th><textarea name="descript" style="width: 200px; height: 80px;" /></textarea></th>
					</tr>
					<tr>
						<th>App cover image:</th><th><input type="file" name="jpg"
							accept=".jpg" size="70" /></th>
					</tr>
					<tr>
						<th>Left times to upload: <%=user.getAppright()%></th>
						<th><button value="Upload"  class="w3-button w3-red">Upload</button></th>			
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>