<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="App">
<head >
<title>toolTip指令</title>
<jsp:include page="../../base.jsp"></jsp:include>
</head>
<body ng-controller="controller" class="container">
	<br>
	<br>
	<br>
<div>
	右：
	<div tool-tip placement="right" class="text-warning">
		<span>这是需要提示的内容</span>
	</div><br>
	
	左：
	<div tool-tip placement="left" class="text-primary">
		<span>这是需要提示的内容</span>
	</div><br>
	
	上：
	<div tool-tip placement="top" class="text-danger">
		<span>这是需要提示的内容</span>
	</div><br>
	
	下：
	<div tool-tip placement="bottom" class="text-info">
		<span>这是需要提示的内容</span>
		<img alt="" src="http://www.160ys.com/data/attachment/forum/201612/09/214836f5xxvxp9w544f2gx.jpg" width="100%">
	</div> 
	<br>
	未设置：
	<div tool-tip >
		<span>这是需要提示的内容</span>
	</div>
	</div>
	<script type="text/javascript">
	var app = angular.module("App",['system']);
	app.controller("controller",['$scope', function(scope){
		
	}]);
	</script>
</body>
</html>