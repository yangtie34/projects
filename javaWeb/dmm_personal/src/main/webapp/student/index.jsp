<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String root = request.getContextPath();
%>
<html lang="zh-CN" ng-app="app">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=request.getContextPath()%>/student/index.jsp"/> 
<jsp:include page="../static/base.jsp"></jsp:include>
<title>欢迎来到郑州轻工业学院</title>
<link rel="stylesheet" type="text/css" href="<%=root%>/student/css/rxs-style.css">
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body class="rxs-bg">
<div class="center-block col-md-8" style="float: none;padding: 0px;">
<%-- 这是页面的整体架子 --%>
	<div ng-view></div>
	</div>
</body>
</html>