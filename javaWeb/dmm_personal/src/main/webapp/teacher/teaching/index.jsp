<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/teacher/teaching/index.jsp"/>
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>教学活动</title>
<script type="text/javascript" src="../../static/echarts/source/echarts-all.js"></script>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/teacher/css/phone-common-style.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/teacher/css/jzhi-style.css">
</head>
<body>
	<div class="center-block col-md-8" style="float: none;padding: 0px;">
  <ng-view></ng-view>
 </div>
</body>
</html>