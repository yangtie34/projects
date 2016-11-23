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
<jsp:include page="../../base.jsp"></jsp:include>
<script src="<%=root%>/static/material/js/material.min.js"></script>
<script src="<%=root%>/static/material/js/ripples.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/bootstrap-material-design.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/ripples.min.css" />
<title>meterial for bootstrap 使用方法</title>
</head>
<body ng-controller="controller">
	<h2>Styling</h2>
  <div class="form-group has-success">
    <input type="text" class="form-control" placeholder="placeholder">
  </div>
  <div class="form-group has-success label-floating">
    <label for="777" class="control-label">floating label</label>
    <input type="text" class="form-control" id="777">
  </div>
<button class="btn  btn-raised btn-danger btn-xs" >你好</button>
<a href="javascript:void(0)" class="btn btn-raised btn-primary">Primary</a>


<script type="text/javascript">
var app = angular.module('app', ['system'])
.controller("controller",['$scope','toastrService','httpService','$interval',
                          function(scope,toast,http,$interval){
	$interval(function() {$.material.init(); }, 1000);
}]);
</script>
</body>
</html>