<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
	<title>在pc显示指令实例</title>
	<jsp:include page="../../base.jsp"></jsp:include>
	<script type="text/javascript" src="../mobile/service/broswerCheckService.js"></script>
	<script type="text/javascript" src="../mobile/directives/showOnPc.js"></script>
</head>
<body ng-controller="controller">
	<div show-on-pc>
		this part only show on pc
	</div>	
	<div hide-on-weixin>
		this part hide on weixin
	</div>	
	<script type="text/javascript">
		var app = angular.module('app', ['system']);
		app.controller("controller",['$scope','browserCheckService',function($scope,service){
			 alert(service.checkBrowser())
			 alert(service.checkDevice());
		}]);
	</script>
</body>
</html>
