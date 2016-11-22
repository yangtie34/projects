<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html >
<html ng-app="app"> 
<head>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<base href="<%=root%>/social/liao/bar/index.jsp"/>
<link rel="stylesheet" type="text/css" href="../../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<!-- 插件引用 -->
<script type="text/javascript" src="<%=root%>/static/jqform/jquery.form.js"></script>
<script type="text/javascript" src="<%=root%>/static/jQuery-qqFace/js/jquery.qqFace.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/jQuery-qqFace/css/qqface.css" /> 
<title>聊吧广场</title>
</head>
<body class="rxs-bg">
			<div ng-view></div>
</body>
</html>