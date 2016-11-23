<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta name="apple-mobile-web-app-status-bar-style" content="yes" />
<meta charset="UTF-8">
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<title>系统中配置的jobs</title>
<!-- angular and jquery -->
<script src="<%=root%>/static/jquery/jquery-1.9.1.min.js"></script>
<script src="<%=root%>/static/angular/angular.min.js"></script>
<script src="<%=root%>/static/angular/angular-route.min.js"></script>
<!-- bootstrap -->
<script src="<%=root%>/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap.min.css" />
<!-- 自定义的angular模块 -->
<script src="<%=root%>/static/angular_expand/mobile/ng-system.min.js"></script>
<!-- 引用插件 -->
<script src="<%=root%>/static/toastr/toastr.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/toastr/toastr.min.css" />
<script src="<%=root%>/static/material/js/material.min.js"></script>
<script src="<%=root%>/static/material/js/ripples.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/bootstrap-material-design.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/ripples.min.css" />
<script type="text/javascript" src="js/controller.js"></script>
<base href="<%=root%>/common/jobs.jsp"/>
</head>
<body ng-controller="controller">
<div class="navbar navbar-inverse navbar-success">
  <div class="container-fluid">
    <div class="navbar-header">
      <h3 style="margin: 20px">系统中实现的定时任务job手动执行页面</h3>
    </div>
 </div>
 </div>

<div style="max-width: 800px;margin: 0 auto;">
    <div class="thumbnail" style="padding: 10px 10px 10px 10px;" ng-repeat="it in jobs">
    	<div class="pull-left">
	        <h3>{{$index + 1}} . {{it.name}}</h3>
	        <p>{{it.code}}</p>
    	</div>
    	<div class="pull-right">
    		<button class="btn btn-success btn-raised" ng-click="execute(it)">执行</button>
    	</div>
    	<div class="clearfix"></div>
    </div>
</div>
</body>
</html>