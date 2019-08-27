<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.czy.factory.DAOFactory"%>
<%@page import="com.czy.dao.ShoppingCartDao"%>
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
<title>Add to Cart</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>
	<%
		String strUid = (String) session.getAttribute("uid");
		int uid = 0;
		if (strUid != null) {
			uid = Integer.parseInt(strUid.trim());
		}
		int aid = Integer.valueOf(request.getParameter("aid"));
		int number = Integer.valueOf(request.getParameter("buyNumber"));
		ShoppingCartDao dao = DAOFactory.getShoppingCartServiceInstance();
		boolean flag = dao.addGoods(uid, aid, number);
		if (flag) {
			response.sendRedirect("shoppingCart.jsp");
		} else {
	%>
	Failed to join your cart, please try again.
	<%
		}
	%>
</body>
</html>
