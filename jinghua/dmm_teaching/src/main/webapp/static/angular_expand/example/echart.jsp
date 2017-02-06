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
    <!-- 导入组件javascript -->
<!--     <script type="text/javascript" src="../mobile/service/echartService.js"></script>
    <script type="text/javascript" src="../mobile/directives/echart.js"></script>
    <script type="text/javascript" src="../../echarts/echarts.min.js"></script> -->
    <script type="text/javascript" src="echart/controller.js"></script>
    <script src="<%=root%>/static/echarts/echarts.min.js"></script>
    <script src="<%=root%>/static/echarts/map/china.js"></script>
</head>
<body ng-controller="controller">
    <div echart config="columnChart" height="300"></div>
    <div echart config="columnChart1"></div>
    <div echart config="columnChart2"></div>
    <div echart config="columnChart3"></div> 
    <div echart config="columnChart4"></div>
    <div echart config="pieChart" height="300"></div>
    <div echart config="otherChart" ></div>
</body>
</html>
