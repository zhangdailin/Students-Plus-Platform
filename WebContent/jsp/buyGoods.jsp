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
	//String StrUid = String.valueOf(session.getAttribute("uid"));
	String StrUid = (String) session.getAttribute("uid");
	int uid = 0;
	if (StrUid != null) {
		uid = Integer.parseInt(StrUid.trim());
	}
	float allTotalPrice = Float.valueOf((request.getParameter("allTotalPrice")).trim());
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>Following App</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>
	<%
		ShoppingCartDao scDao = DAOFactory.getShoppingCartServiceInstance();
		if (scDao.payAllGoods(uid, allTotalPrice)) {
			response.sendRedirect("myapp.jsp");
		} else {
	%>
	Payment failed, please try again.
	<%
		}
	%>
</body>
</html>