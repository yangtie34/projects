<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下拉选择</title>
<%
    String projectPath =request.getContextPath();
%>
<script>
    var base = "<%=projectPath %>";
</script>
<!-- 导入组件javascript -->
<link rel="stylesheet" type="text/css" href="../../bootstrap-3.3.6/css/bootstrap.css">

<script type="text/javascript" src="../../jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../../angular/angular.min.js"></script>
<script type="text/javascript" src="../mobile/ng-system.js"></script> 
</head>
<body ng-controller="controller">
<div btn-dropdown source="source" on-change="change($data)" btn-class="btn-success" btn-style="background-color:#FFF; color:#222;" display-field="mc"></div>
<br /> 
<div btn-dropdown source="source" on-change="change($data)" btn-class="btn-success noradius" btn-style="background-color:#FFF; color:#222;" display-field="mc"></div>
无圆角<br /> 
<br /> 
<div btn-dropdown source="source" on-change="change($data)" btn-class="btn-danger"   display-field="mc"></div> 
<br /> 
<div btn-dropdown source="source" on-change="change($data)" btn-class="btn-info"  display-field="mc"></div> 
<br /> 
<div btn-dropdown source="source" on-change="change($data)" btn-class="btn-warning"  display-field="mc"></div> 
<br /> 
<div btn-dropdown source="source" on-change="change($data)" btn-class="btn-primary" display-field="mc"></div> 
<br /> 
<div btn-dropdown source="source" on-change="change($data)"  btn-style="background-color:#FFF;border:1px solid #DDD" display-field="mc"></div> 


<style>
	.noradius{
		border-radius : 0px;
	}
</style>
<script type="text/javascript">
	var app = angular.module('app',['system']);
	app.controller("controller",['$scope',function(scope){
		 scope.source = [{
			 id : "1",
			 mc : "第一个"
		 },{
			 id : "2",
			 mc : "第二个",
			 checked : true
		 },{
			 id : "3",
			 mc : "第三个"
		 }];
		 scope.change =function(data){
			 scope.result = data;
			 console.log(data);
		 }
	}]);
</script>
</body>
</html>
