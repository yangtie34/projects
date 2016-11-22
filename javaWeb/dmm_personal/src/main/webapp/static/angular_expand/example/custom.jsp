<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试 条件选择</title>
<%
    String projectPath =request.getContextPath();
%>
<script>
    var base = "<%=projectPath %>";
</script>
<!-- 导入组件javascript -->
<link rel="stylesheet" type="text/css" href="../pc/css/combotree.css">
<link rel="stylesheet" type="text/css" href="../pc/css/list-style.css">
<link rel="stylesheet" type="text/css" href="../../bootstrap-3.3.6/css/bootstrap.min.css">

<script type="text/javascript" src="../../jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../../angular/angular.min.js"></script>
<script type="text/javascript" src="../pc/cg-custom.js"></script>
<script type="text/javascript" src="custom/controller.js"></script>
</head>
<body ng-controller="controller">
	<button ng-click="changeMutiSource()">改变选择组件数据源</button>
	<button ng-click="changeMutiTreeSource()">改变选择组件中树的数据源</button>
	<div style="width: 800px;">
		<div cg-mul-query-comm source="mutiSource" result="multResult" expand="true" on-change="change($data)">
		</div>	
	</div>
	{{multResult}}
	<br />
	<br />
	<hr />
	<div cg-combo-tree source="treeData" result="treeresult"></div>	
	{{treeresult}}
	<button ng-click="changeTreeSource()">改变树的数据源</button>
	
	
</body>
</html>
