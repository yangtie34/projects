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
<base href="<%=root%>/permiss.jsp"/>
<jsp:include page="static/base.jsp"></jsp:include>
<title>权限测试页</title>
</head>
<body ng-controller="controller">
<%-- 这是页面的整体架子 --%>
<div check-permiss shirotag="personal:teacher:salary:*">
	abc
</div>

<script type="text/javascript">
var app = angular.module('app', ['system'])
.controller("controller",['$scope','toastrService','httpService',
                          function(scope,toast,http){
/* //调用后台请求
http.post({
	url : "permiss/getPermission",
	data : {}
}).then(function(data){
	toast.info(data)
}); */
}]);
</script>
</body>
</html>