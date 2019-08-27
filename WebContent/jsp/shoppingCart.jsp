<%@page import="com.czy.bean.Apps"%>
<%@page import="com.czy.dao.AppsDao"%>
<%@page import="com.czy.service.AppService"%>
<%@page import="com.czy.bean.ShoppingCart"%>
<%@page import="java.util.List"%>
<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.ShoppingCartDao"%>
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
<title>Shopping cart</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
img.normal {
	height: auto
}

img.small {
	height: 50px
}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div class="content clearfix">
		<div class="check-out">
			<div class="w3-panel w3-padding-16">
				<h1>Apps Cart</h1>
			</div>

			<table class="w3-table-all">
				<tr>
					<th>Apps</th>
					<th>Price</th>
					<th>Delete Order</th>
				</tr>
				<%
					String strUid = (String) session.getAttribute("uid");
					int uid = 0;
					if (strUid != null) {
						uid = Integer.parseInt(strUid.trim());
					}
					ShoppingCartDao dao = DAOFactory.getShoppingCartServiceInstance();
					AppsDao appsDao = DAOFactory.getAppsServiceInstance();
					List<ShoppingCart> cartList = dao.getAllGoods(uid);
					float allTotalPrice = 0;
					if (cartList != null & cartList.size() > 0) {
						ShoppingCart cart;
						Apps apps;
						String photoPath;
						int number;
						float price;
						float totalPrice;
						int aid;
						String aname;
						for (int i = 0; i < cartList.size(); i++) {
							cart = cartList.get(i);
							apps = appsDao.queryById(cart.getAid());
							photoPath = "images/app.jpg";
							number = cart.getNumber();
							price = 5;
							aid = cart.getAid();
							aname = apps.getAname();
							totalPrice = price;
							allTotalPrice = allTotalPrice + totalPrice;
				%>
				<tr>
					<td class="ring-in"><a href="jsp/AppsDescribed.jsp?aid=<%=aid%>" class="at-in"> <img
							src="<%=photoPath%>" class="small" class="img-responsive" alt="">
					</a>
						<div class="sed">
							<h5><%=aname%></h5>
							<br>
							<p>Add to cart time: <%=cart.getSdate()%></p>
						</div>
						<div class="clearfix"></div></td>
					<td><%=price%> Nuts</td>
					<td><a
						href="jsp/deleteGoods.jsp?aid=<%=aid%>&number=<%=number%>">Delete</a></td>
				</tr>
				<%
					}
					}
				%>
			</table>
			<h3 size="24px">Total price: <%=allTotalPrice%> Nuts
			</h3>
			<%
				if (cartList != null & cartList.size() > 0) {
			%>

			<a href="jsp/buyGoods.jsp?allTotalPrice=<%=allTotalPrice%>" target="_blank"><button class="w3-bar-item w3-button w3-green w3-right">Pay</button></a>
			<%
				} else {
			%>
			<a class="to-buy"><button class="w3-bar-item w3-button w3-green w3-right">Pay</button></a>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>