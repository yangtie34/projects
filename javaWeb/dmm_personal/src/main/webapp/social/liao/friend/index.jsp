<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html >
<html ng-app="app"> 
<head>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<base href="<%=root%>/social/liao/friend/index.jsp"/>
<link rel="stylesheet" type="text/css" href="../../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<title>我的好友</title>
</head>
<body class="rxs-bg">
			<div ng-view></div>
</body>
</html>