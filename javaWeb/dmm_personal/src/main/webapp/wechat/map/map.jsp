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
<title>校园地图（微信内置地图）</title>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script>
<jsp:include page="../../static/base.jsp"></jsp:include>
<script type="text/javascript" src="js/controller.js"></script>
<base href="<%=root%>/wechat/map.jsp"/>
</head>
<body ng-controller="controller">
</body>
</html>