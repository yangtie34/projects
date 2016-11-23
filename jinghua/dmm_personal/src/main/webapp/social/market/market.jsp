<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/social/market/market.jsp"/>
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<jsp:include page="../../static/base.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="../css/rxs-style.css">
<title>跳蚤市场</title>
<script type="text/javascript" src="<%=root%>/static/jqform/jquery.form.js"></script>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body class="rxs-bg">
	<div ng-view></div>
</body>
</html>