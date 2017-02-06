<%@page import="com.jhnu.syspermiss.util.SysConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<%
	String projectPath = request.getContextPath();
	int port = request.getServerPort();
	String ip = request.getServerName();
	String root = "http://"+ip+":"+port+projectPath;
	SysConfig sys = SysConfig.instance();
	String logoutUrl =  sys.getCasServerUrl() +"/logout?service="+sys.getCasServerLoginUrl() + "?service=" +sys.getServerUrl()+"/";
%>
<script type="text/javascript">
	var base = "<%=root %>/";
	var logoutUrl = "<%=logoutUrl %>"
</script>
<!-- angular and jquery -->
<script src="<%=root%>/static/jquery/jquery-1.9.1.min.js"></script>
<script src="<%=root%>/static/jquery/jquery.fileDownload.js"></script>
<script src="<%=root%>/static/angular/angular.min.js"></script>
<script src="<%=root%>/static/angular/angular-route.min.js"></script>
<!-- bootstrap -->
<script src="<%=root%>/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/font-awesome-4.5.0/css/font-awesome.min.css" /> 
<link rel="stylesheet" type="text/css" href="<%=root%>/static/angular_expand/pc/css/popup-form-style.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/angular_expand/pc/css/tooltip.css" />
<!-- 自定义的angular模块 -->
<script src="<%=root%>/static/angular_expand/pc/ng-system.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/angular_expand/pc/css/angular-expand.css" /> 
<!-- 引用插件 -->
<script src="<%=root%>/static/echarts/echarts.js"></script>
<script src="<%=root%>/static/toastr/toastr.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/toastr/toastr.min.css" />
<link rel="stylesheet" href="<%=root%>/ky/css/keyan-style.css">