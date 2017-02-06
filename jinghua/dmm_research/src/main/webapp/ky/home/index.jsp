<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>首页</title>
<base href="<%=root%>/ky/home/index.jsp"/>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="container-fluid" >
	该页面填充科研主页，分析一些概况信息
	<ng-view></ng-view>
</div>
</body>
</html>