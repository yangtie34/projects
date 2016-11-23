<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="js/controller.js"></script>
</head>

<body ng-controller="controller">
	-------------------
	<div>{{data.name}}</div>
	<div ng-if="data.age < 18">未成年人禁止访问</div>
	
	<div>{{data.obj}}</div>
</body>
</html>