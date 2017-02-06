<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>科研著作分析</title>
<base href="<%=root%>/ky/writing/work/index.jsp"/>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="container-fluid" >
	<ng-view></ng-view>
</div>
</body>
</html>