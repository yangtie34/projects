<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拖动指令示例</title>
<script type="text/javascript" src="../../angular/angular.min.js"></script>
<script type="text/javascript" src="../pc/cg-custom.js"></script>
<script type="text/javascript" src="drag/controller.js"></script>
<style>
.drag{
	width: 100px;
	height: 20px;
	background-color: orange;
}

.drop{
	width: 200px;
	height: 200px;
	background-color: green;
}
</style>
</head>
<body ng-controller="controller">
 	<div cg-drop class="drop" on-drop-success="successHandler($data,dragList)"> 
		<div ng-repeat="pp in dragList">
			<div cg-drag  on-drop-success="dropSuccessHandler($data,dragList)" drag="pp" class="drag">{{pp.id}} ： {{pp.name}} </div>
		</div>
	</div>
	<br />
	<div cg-drop class="drop" on-drop-success="successHandler($data,dragTarList)"> 
		<div ng-repeat="pp in dragTarList">
			<div cg-drag  on-drop-success="dropSuccessHandler($data,dragTarList)" drag="pp" class="drag">{{pp.id}} ： {{pp.name}}</div>
		</div>
	</div>
</body>
</html>
0