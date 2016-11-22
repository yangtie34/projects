<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../base.jsp"></jsp:include>
<title>分页测试</title>
<script type="text/javascript" src="page/controller.js"></script>
<script type="text/javascript" src="page/service.js"></script>
</head>
<body ng-controller="controller">
	<table class="table">
		<thead>
			<tr> <th>ID</th> <th>NAME</th></tr>
		</thead>
		<tbody>
			<tr ng-repeat="item in vm.items">
				<td> {{item.id}} </td>
				<td> {{item.name}} </td>
			</tr>
		</tbody>
	</table>
	<div >
		<div class="pull-left">
			 <select ng-model="vm.page.size" style="border: 1px solid #DDD;"><option value="5">5</option><option value="10">10</option><option value="20">20</option><option value="50">50</option> </select> / 每页
		</div>
		<div pagination total-items="vm.page.total" ng-model="vm.page.index" items-per-page="vm.page.size"
			max-size="10" class="pagination-sm pull-right" boundary-links="true"></div>
		<div class="clearfix"></div>
	</div>
	
	<!-- 点击加载更多 -->
	<table class="table">
		<thead>
			<tr> <th>ID</th> <th>NAME</th>  </tr>
		</thead>
		<tbody>
			<tr ng-repeat="item in vm2.items ">
				<td> {{item.id}} </td>
				<td> {{item.name}} </td>
			</tr>
		</tbody>
	</table>
	
	<div class="hidden">
		<div pagination total-items="vm2.page.total" ng-model="vm2.page.index" items-per-page="vm2.page.size"
			max-size="10" class="pagination-sm pull-right" boundary-links="true"></div>
		<div class="clear-all"></div>
	</div>
	<button class="btn btn-primary btn-block" ng-click="loadMore()" ng-hide="vm2.loading || (vm2.page.total <= vm2.page.index*vm2.page.size)"> 点击加载更多 </button>
	<button class="btn btn-danger btn-block" ng-click="loadMore()" ng-show="vm2.loading">加载中</button>
</body>
</html>
