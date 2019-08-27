<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.czy.dao.AppsDao"%>
<%@ page import="com.czy.dao.ShoppingCartDao"%>
<%@ page import="com.czy.dao.AlreadyBuyDao"%>
<%@ page import="com.czy.bean.Apps"%>
<%@ page import="com.czy.bean.ShoppingCart"%>
<%@ page import="com.czy.bean.AlreadyBuy"%>
<%@ page import="java.util.List"%>
<%@ page import="com.czy.factory.DAOFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	int aid = Integer.parseInt(request.getParameter("aid").trim());

	AppsDao dao = DAOFactory.getAppsServiceInstance();
	Apps apps = dao.queryById(aid);
	String name = apps.getAname();
	String photo = "app/" +apps.getUid()+"/"+ apps.getAphoto();		
	float price = 5;
	String described = apps.getDescribed();

	if(session.getAttribute("uid")==null){
		response.sendRedirect("login.jsp");
		return;
	}
	
	String suid = String.valueOf(session.getAttribute("uid"));
	int uid = Integer.parseInt(suid);
	ShoppingCartDao scdao = DAOFactory.getShoppingCartServiceInstance();
	String cart = scdao.getDesignateGoodsMs(uid, aid);
	
	AlreadyBuyDao abdao = DAOFactory.getAlreadyBuyServiceInstance();
	String abuy = abdao.getDesignateGoodsMs(uid, aid);
	
%>
<!DOCTYPE html>
<html>
<head>
<title>App details</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
img.big {
	height: 190px
}
p{
    overflow : hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
		<div class="w3-container">
		<div class="content clearfix">
			<div class="w3-row">
				<div class="w3-container w3-third">
						<ul class="w3-ul">
							<li data-thumb="<%=photo%>">
									<img class="big" src="<%=photo%>">
							</li>
						</ul>
				</div>
				<div class="w3-container w3-twothird">
					<div class="w3-panel w3-padding-24 w3-orange">
						<h1><%=name%></h1>
						<br>
						<p><%=described%></p>
						<label><%=price%> Nuts</label>
						<%
						if(!"".equals(abuy)){%>
							<a id="carthref" href="jsp/myapp.jsp"class="cart item_add" type= "button" class="w3-button w3-red">
							<button class="w3-button w3-red">You have purchased</button>
							</a>
						<%
						}
						else if(!"".equals(cart)){%>
							<a id="carthref" href="jsp/shoppingCart.jsp"class="cart item_add" type= "button" class="w3-button w3-red">
							<button class="w3-button w3-red">Already in shoppingcart</button>
							</a>
						<%
						}else{
						%>
						<a id="carthref" href="jsp/addToCart.jsp?aid=<%=aid%>&buyNumber=1"
							class="cart item_add"" type= "button" class="w3-button w3-red">
							<button class="w3-button w3-red">Add to shopping cart</button>
							</a>
						<%	
						}
						%>
					</div>
				</div>
			</div></div>
			<div class="clearfix"></div>
		</div>
</body>
</html>