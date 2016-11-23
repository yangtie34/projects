<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../static/base.jsp"></jsp:include>
<title>教职工个人中心</title>
<base href="<%=root%>/teacher/index.jsp"/>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/teacher/css/phone-common-style.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/teacher/css/jzhi-style.css">
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="center-block col-md-8" style="float: none;padding: 0px;">
 <ng-view></ng-view>
</div>
</body>
</html>