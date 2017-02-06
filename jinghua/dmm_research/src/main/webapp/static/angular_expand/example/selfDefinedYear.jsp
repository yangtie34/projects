<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义年份选择</title>
<jsp:include page="../../base.jsp"></jsp:include>
</head>
<body ng-controller="controller">
	<div self-defined-year source="source" result=result></div>
	
	result : {{result | json}}
	<script type="text/javascript">
		var app = angular.module('app', ['system']);
		app.controller("controller",['$scope',function(scope){
			scope.source = [{"mc":"今年","value":"2016","start":"2016","end":"2016"},
			                {"mc":"去年","value":"2015","start":"2015","end":"2015"},
			                {"mc":"近五年","value":"2012-2016","start":"2012","end":"2016"},
			                {"mc":"近十年","value":"2007-2016","start":"2007","end":"2016"}];
			
			scope.result ={};
		}]);
	</script>
</body>
</html>
