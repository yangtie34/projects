<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收费管理系统-查询</title>
<jsp:include page="/app/dorm/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/controllerdrag.js"></script>
<script type="text/javascript" src="js/service.js"></script>
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
