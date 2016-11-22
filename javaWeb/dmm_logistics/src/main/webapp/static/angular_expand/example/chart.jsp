<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>highchart Angular </title>

<%
	String basePath = request.getContextPath();
    int port = request.getServerPort();
    String ip = request.getServerName();
    String projectPath = "http://"+ip+":"+port+basePath;
%>
<meta charset="UTF-8">
    <!-- 导入angualr基础配置JSP -->
    <script>
        //其他非法路径-返回首页或指定url
        function ToHome(url){
            window.location.href = httpConfig.basePath+'/'+ (url ? url : 'index.html');
        }
        // 访问的数据不合法
        function Wrongful(){
            alert('您访问的数据不合法！');
        }
        var jxpg = angular.module('jxpg',['services']);
        var base = "<%=projectPath %>";
    </script>
    <!-- 导入组件javascript -->
    <script type="text/javascript" src="../pc/directives/cgChart.js"></script>
    <script type="text/javascript" src="../pc/service/highChartService.js"></script>
    <script type="text/javascript" src="../../framework/highchart/highcharts.js"></script>
    <script type="text/javascript" src="../../framework/highchart/highcharts-more.js"></script>
    <script type="text/javascript" src="highchart/controller.js"></script>
</head>
<body ng-controller="controller">
    <div cg-chart config="columnChart"></div>
    <div cg-chart config="pieChart"></div>
    <div cg-chart config="otherChart"></div>
</body>
</html>
