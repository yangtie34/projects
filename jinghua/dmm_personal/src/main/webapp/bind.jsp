<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String root = request.getContextPath();
	String openid =(String)request.getAttribute("openid");
	if(openid == null) response.sendRedirect(root+ "/index.jsp");
%>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="static/base.jsp"></jsp:include>
<title>微信绑定中心</title>
</head>
<body class="scrollable-content" >
	<div class="center-block col-md-6" style="float: none;">
		<a class="btn btn-primary btn-lg btn-block" href="<%=root %>/teacher/bind/bind.jsp?openid=<%=openid %>" style="padding:30px;">
		 	<i class="fa fa-graduation-cap fa-2x "></i> 教职工 
		</a>
		
		<br>
		
		<a class="btn btn-success btn-lg btn-block" href="<%=root %>/student/bind/bind.jsp?openid=<%=openid %>" style="padding:30px;">
			<i class="fa fa-users fa-2x"></i> 学&nbsp;&nbsp;&nbsp;生  
		</a>

		<br>
		
		<a class="btn btn-warning btn-lg btn-block"  href="<%=root %>/parent/bind/bind.jsp?openid=<%=openid %>" style="padding:30px;">
			<i class="fa fa-home fa-2x"></i> 家&nbsp;&nbsp;&nbsp;长 
		</a>
	</div>
</body>
</html>