<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>学生异常</title>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/homeController.js"></script>
<script type="text/javascript" src="js/otherController.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/teacher/css/phone-common-style.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/teacher/css/jzhi-style.css">
<base href="<%=root%>/teacher/warning/warning.jsp"/>
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="center-block col-md-8" style="float: none;padding: 0px;">
 	<ng-view></ng-view>
</div>
</body>
</html>