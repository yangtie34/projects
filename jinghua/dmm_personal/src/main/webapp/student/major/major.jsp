<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../static/base.jsp"></jsp:include>
<base href="<%=root%>/student/major/major.jsp"/>
<title>我的专业</title>
<link rel="stylesheet" type="text/css" href="../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body class="rxs-bg">
<div class="center-block col-md-8" style="float: none;padding: 0px;">
	<div ng-view></div>
	</div>
</body>
</html>