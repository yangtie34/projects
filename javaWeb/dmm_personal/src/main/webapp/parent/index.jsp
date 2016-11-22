<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=request.getContextPath()%>/parent/index.jsp"/> 
<script type="text/javascript">
	var base = "<%=request.getContextPath()%>/";
</script>
<jsp:include page="../static/base.jsp"></jsp:include>
<script type="text/javascript"></script>
<title>家长主页</title>
<script type="text/javascript" src="js/index.js"></script>
</head>
<body ng-controller="controller">



</body>
</html>