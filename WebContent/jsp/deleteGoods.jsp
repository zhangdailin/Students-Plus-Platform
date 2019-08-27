<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.ShoppingCartDao"%>
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
	int number = Integer.parseInt((request.getParameter("number")).trim());
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>Delete order</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>
	<%
		ShoppingCartDao scDao = DAOFactory.getShoppingCartServiceInstance();
		if (scDao.deleteGoods(uid, aid, number)) {
			response.sendRedirect("shoppingCart.jsp");
		} else {
	%>
	Failed to delete the order. Please try again.
	<%
		}
	%>
</body>
</html>
