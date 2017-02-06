<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>echart Angular </title>
<%
	String basePath = request.getContextPath();
    int port = request.getServerPort();
    String ip = request.getServerName();
    String root = "http://"+ip+":"+port+basePath;
%>
    <!-- 导入angualr基础配置JSP -->
    <script>
        var base = "<%=root %>";
    </script>
    <jsp:include page="../../base.jsp"></jsp:include>
    <script type="text/javascript" src="../pc/directives/echart2.js"></script>
    
    <!-- 导入组件javascript -->
    <script type="text/javascript" src="echart/controller.js"></script>
</head>
<body ng-controller="controller">
    <div echart2 config="columnChart" on-click="columnClick($params,123456)" height="300" style="width: 400px;"></div>
</body>
</html>
