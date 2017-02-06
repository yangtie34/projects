<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/ky/award/award/index.jsp"/>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>高层次研究成果奖励</title>
<link href="../../css/keyan-style.css" rel="stylesheet">
<link href="../../css/cg-select-dropdown.css" rel="stylesheet">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<div class="container-fluid" >
	<ng-view></ng-view>
</div>
</body>
</html>