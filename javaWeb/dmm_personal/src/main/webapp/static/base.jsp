<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<%
	String projectPath = request.getContextPath();
	int port = request.getServerPort();
	String ip = request.getServerName();
	String root = "http://"+ip+":"+port+projectPath;
%>
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<!-- angular and jquery -->
<script src="<%=root%>/static/jquery/jquery-1.9.1.min.js"></script>
<script src="<%=root%>/static/angular/angular.min.js"></script>
<script src="<%=root%>/static/angular/angular-route.min.js"></script>
<!-- bootstrap -->
<script src="<%=root%>/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/font-awesome-4.5.0/css/font-awesome.min.css" /> 
<!-- 自定义的angular模块 -->
<script src="<%=root%>/static/angular_expand/mobile/ng-system.min.js"></script>
<!-- 引用插件 -->
<%-- <script src="<%=root%>/static/echarts/echarts.min.js"></script>  echarts 不再统一引入，用到的页面单独引用--%>
<script src="<%=root%>/static/toastr/toastr.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/toastr/toastr.min.css" />