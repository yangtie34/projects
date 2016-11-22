<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收费管理系统-查询</title>
<%
	String basePath = request.getContextPath();
    int port = request.getServerPort();
    String ip = request.getServerName();
    String projectPath = "http://"+ip+":"+port+basePath;
%>
<meta charset="UTF-8">
<link rel="shortcut icon" href="<%=projectPath%>/resource/dorm/images/ico3.ico" />
<!-- 导入angualr基础配置JSP -->
<jsp:include page="/framework/angular.jsp"></jsp:include>
<!-- import css -->
<script>
    // 其他非法路径-返回首页或指定url
    function ToHome(url){
        window.location.href = httpConfig.basePath+'/'+ (url ? url : 'index.html');
    }
    // 访问的数据不合法
    function Wrongful(){
    	alert('您访问的数据不合法！');
    }
    var dorm = angular.module('dorm',['services']);
    var base = "<%=projectPath %>";
</script>
<!-- 导入组件javascript -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="<%=projectPath %>/resource/dorm/js/bootstrap.min.js"></script>
<link href="<%=projectPath %>/resource/dorm/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/resource/dorm/css/combotree.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/resource/dorm/css/list-style.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/resource/dorm/css/sushe-index.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/resource/dorm/css/sushe-xinxi.css">
<script type="text/javascript" src="../../app_angular_expand/dorm/directives/cgComboTree.js"></script>
<script type="text/javascript" src="../../app_angular_expand/dorm/directives/cgCustomComm.js"></script>
<script type="text/javascript" src="../../app_angular_expand/dorm/directives/cgTree.js"></script>
<script type="text/javascript" src="custom/controller.js"></script>

<link type="text/css" rel="stylesheet" href="css/example.css">
<style>

</style>
</head>
<body ng-controller="controller">
	<div style="width: 800px;">
		<div cg-custom-comm source="mutiSource" result="multResult" expand="true" on-change="onChange($data)">
		</div>	
	</div>
</body>
</html>
