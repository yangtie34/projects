<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/ky/project/progress/index.jsp"/>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>科研项目进度分析</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<div class="container-fluid" >
	<ng-view></ng-view>
</div>
</body>
</html>