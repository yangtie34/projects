<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String root = request.getContextPath();
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>弹出层Form</title>
<jsp:include page="../../base.jsp"></jsp:include>
</head>
<body ng-controller="controller">
	<div modal-form config="formConfig"></div>	
	<button class="btn btn-primary" ng-click="toggleModal()">测试弹出层</button>
	
	<div > 
		<table class="table">
			<tr ng-repeat="row in grid.data">
				<td>{{row.nm}}</td>
				<td>{{row.name}}</td>
				<td>
					<span ng-repeat="e in grid.events">
						<a ng-click="rowClick($index,row)">{{e.name}}</a>
					</span>
				</td>
			</tr>
		</table>
		
	
	</div>
	
	
	<script type="text/javascript">
		var app = angular.module('app', ['system']);
		app.controller("controller",['$scope',function(scope){
			scope.formConfig = {
				title : "测试弹出层",
				show : false,
				url : "project/nums/list",
				exportUrl : 'project/nums/list/export', // 为空则不显示导出按钮
				heads : ['序号','名称','部门','结束时间','经费' ],
				fields : ['nm','name_','dept','end_time','fund'],
				params : {
					queryString : "",
					startYear : "2011",
					endYear : "2016",
					zzjgid : "000000"
				}
			} 
			scope.toggleModal = function(){
				scope.formConfig.show = !scope.formConfig.show;
			}
			
			scope.rowClick = function(idx,row){
				scope.grid.events[idx].func(row,idx);
			}
			
			var sbv = 123;
			
			scope.grid = {
				data : [{
					name : "lili" ,
					nm : 1
				},{
					name : "haha" ,
					nm : 2
				},{
					name : "mike" ,
					nm : 3
				}],
				events : [{
					name : "更新",
					func : function($rows,$index){
						console.log("您点击了更新：",$rows);
						
					}
				},{
					name : "删除",
					func : function($rows,$index){
						console.log("您点击了删除：",$rows);
					}
				}]
			}
		}]);
	</script>
</body>
</html>
