<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>局部遮罩层</title>
<jsp:include page="../../base.jsp"></jsp:include>
</head>
<body ng-controller="controller">
	<div class="container" part-modal show-modal="showModal">
		<div class="row">
			<ul>
				<li>1</li>
				<li>2</li>
				<li>3</li>
				<li>4</li>
				<li>5</li>
				<button class="btn btn-success btn-block">哈哈</button>
			</ul>
		</div>
	</div>
	<button class="btn btn-primary" ng-click="toggleModal()">toggle modal</button>
	<script type="text/javascript">
		var app = angular.module('app', ['system']);
		app.controller("controller",['$scope',function(scope){
			scope.showModal = false;
			scope.toggleModal = function(){
				scope.showModal = !scope.showModal;
			}
		}]);
	</script>
</body>
</html>
