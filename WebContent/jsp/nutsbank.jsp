<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.czy.dao.BankDao"%>
<%@ page import="com.czy.bean.Bank"%>
<%@ page import="com.czy.factory.DAOFactory"%>
<%@ page import="com.czy.db.BankDaoImpl"%>
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
<base href="<%=basePath%>">
<title>Nuts Bank</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

<jsp:include page="head.jsp"></jsp:include>
	<div class="content clearfix">
		<div class="w3-panel w3-padding-16">
			<h1>Transaction History</h1>
		</div>
		
		<table class="w3-table-all">
			<tr>
				<th>App name</th>
				<th>Cause</th>
				<th>Count</th>
				<th>Time</th>
			</tr>
			<%
					String strUid = (String) session.getAttribute("uid");
					int uid = 0;
					if (strUid != null) {
						uid = Integer.parseInt(strUid.trim());
					}

					BankDao dao = DAOFactory.getBankServiceInstance();
					List<Bank> baList = dao.getBankChange(uid);
					if (baList != null & baList.size() > 0) {
						String aname;
						String type;
						String date;
						float change;
						Bank bank;
						for (int i = 0; i < baList.size(); i++){
							bank = baList.get(i);
							aname = bank.getAname();
							type = bank.getType();
							date = bank.getDate();
							change = bank.getChange();
				%>
			<tr>
				<td><%=aname%></td>
				<td><%=type%></td>
				<td><%=change%> Nuts</td>
				<td><%=date%></td>
			</tr>
			<%			
						}
					}
				%>
		</table>
	</div>
</body>
</html>