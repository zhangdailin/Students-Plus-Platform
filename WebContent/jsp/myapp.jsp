<%@page import="com.czy.bean.Apps"%>
<%@page import="com.czy.dao.AppsDao"%>
<%@page import="com.czy.bean.AlreadyBuy"%>
<%@page import="com.czy.dao.AlreadyBuyDao"%>
<%@page import="com.czy.bean.ShoppingCart"%>
<%@page import="java.util.List"%>
<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.ShoppingCartDao"%>
<%@ page language="java" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>My apps</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
img.normal {
	height: auto
}

img.small {
	height: 30px
}
</style>
</head>

<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div class="w3-container">

		<div class="content clearfix">
			<div class="w3-panel w3-padding-16">
				<h1>Uploaded Apps</h1>
			</div>
			<table class="w3-table-all">
				<tr>
					<th>Apps</th>
					<th>Upload time</th>
					<th>Description</th>
				</tr>
				<%
					String strUid = (String) session.getAttribute("uid");
					int uid = 0;
					if (strUid != null) {
						uid = Integer.parseInt(strUid.trim());
					}
					AppsDao dao = DAOFactory.getAppsServiceInstance();
					List<Apps> abList = dao.getMyApps(uid);
					if (abList != null & abList.size() > 0) {
						AppsDao appsDao = DAOFactory.getAppsServiceInstance();
						Apps apps;
						int aid;
						String photoPath;
						String uploadTime;
						String descript;
						String jarpath;
						String destdir;
						String toUrl;
						String appUrl;
						//System.out.println(abList.size());
						for (int i = 0; i < abList.size(); i++) {
							apps = abList.get(i);
							aid = apps.getAid();
							uploadTime = apps.getAdate();
							photoPath = "app/" + uid + "/" + apps.getAphoto();
							descript = apps.getDescribed();
							jarpath = apps.getJarpath();
							destdir = apps.getDestdir();
							toUrl = apps.getDestdir();
							int j = toUrl.indexOf("webapps/") + "webapps/".length();
							appUrl = toUrl.substring(j - 1, toUrl.length()).replace(".war", "");
				%>
				<tr>
					<td class="ring-in"><a href="<%=appUrl%>" class="at-in"> <img
							src="<%=photoPath%>" class="small" class="img-responsive" alt="">
					</a>
						<div class="sed">
							<h5>
								<%=apps.getAname()%></h5>
							<br>
						</div>
						<div class="clearfix"></div></td>
					<td><%=uploadTime%></td>
					<td><%=descript%></td>
				</tr>
				<%
					}
					}
				%>
			</table>
		</div>
		<br>

		<div class="content clearfix">
			<div class="w3-panel w3-padding-16">
				<h1>Subscription apps</h1>
			</div>
			<table class="w3-table-all">
				<tr>
					<th>App</th>
					<th>Price</th>
					<th>Subscribe time</th>
				</tr>
				<%
					String strUid2 = (String) session.getAttribute("uid");
					int uid2 = 0;
					if (strUid2 != null) {
						uid2 = Integer.parseInt(strUid2.trim());
					}
					AlreadyBuyDao dao2 = DAOFactory.getAlreadyBuyServiceInstance();
					List<AlreadyBuy> abList2 = dao2.getAllBuyGoods(uid);
					if (abList2 != null & abList2.size() > 0) {
						AppsDao AppsDao = DAOFactory.getAppsServiceInstance();
						Apps apps;
						AlreadyBuy ab;
						int aid;
						int number;
						String buyTime;
						String photoPath;
						String aname;
						float price;
						String destdir;

						for (int i = 0; i < abList2.size(); i++) {
							ab = abList2.get(i);
							aid = ab.getAid();
							number = ab.getNumber();
							buyTime = ab.getBuyTime();
							apps = AppsDao.queryById(aid);
							aname = apps.getAname();
							photoPath = "images/app.jpg";
							price = 5;
				%>

				<tr>
					<td class="ring-in"><a
						href="jsp/AuthorizeService.jsp?aid=<%=aid%>" class="at-in"> <img
							class="small" src="<%=photoPath%>" class="img-responsive" alt="">
					</a>
						<div class="sed">
							<h5>
								<%=aname%></h5>
							<br>
						</div>
						<div class="clearfix"></div></td>
					<td><%=price%> Nuts</td>
					<td><%=buyTime%></td>
				</tr>
				<%
					}
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>
