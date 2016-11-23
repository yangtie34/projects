<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta name="apple-mobile-web-app-status-bar-style" content="yes" />
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>日常生活</title>
<!-- Bootstrap core CSS -->
<link rel="stylesheet" type="text/css" href="../css/phone-common-style.css">
<link rel="stylesheet" type="text/css" href="../css/jzhi-style.css">
<base href="<%=root%>/teacher/dailylife/index.jsp"/>
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>我的日常</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
	<div class="center-block col-md-8" style="float: none;padding: 0px;">
	  <ng-view></ng-view>
	 </div>
</body>
</html>