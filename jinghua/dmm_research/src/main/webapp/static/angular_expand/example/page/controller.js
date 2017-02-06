//定义一个controller
//参数分别是1 模块名 2 控制器名 3 注入对象以及控制器函数
var app = angular.module('app',['system']);
app.controller("controller",['$scope','pageService','dialog',function(scope,service,dialog){
	//分页指令的测试数据
	 scope.vm = {
		page : {
			total : 0, // 总记录数
			size: 10,  //每页显示条数
		    index: 1   //当前页码
		},
		items : [] //数据集
	 };
	 scope.$watch("vm.page.index",function(newVal,oldVal){
		 dialog.showLoading();
		 service.getDataByPage(scope.vm.page,function(data){
			 scope.vm.items = data.items;
			 scope.vm.page.total = data.total;
			 scope.$apply(); 
			 dialog.hideLoading();
		 });
	 },true);
	 
	 scope.$watch("vm.page.size",function(newVal,oldVal){
		 if(scope.vm.page.index == 1){
			 service.getDataByPage(scope.vm.page,function(data){
				 scope.vm.items = data.items;
				 scope.vm.page.total = data.total;
				 scope.$apply(); 
				 dialog.hideLoading();
			 });
		 }else{
			 scope.vm.page.index = 1
		 }
	 },true);
	 
	 
	 
	 scope.vm2= {
		page : {
			total : 0,
			size: 10,
		    index: 1
		},
		items : [],
		loading : false
	 };
	 scope.$watch("vm2.page.index",function(newVal,oldVal){
		 scope.vm2.loading = true;
		 service.getDataByPage(scope.vm2.page,function(data){
			 scope.vm2.items = $.merge(scope.vm2.items, data.items);   
			 scope.vm2.page.total = data.total;
			 scope.vm2.loading = false;
			 scope.$apply(); 
		 });
	 },true);
	 scope.loadMore = function(){
		 scope.vm2.page.index++;
	 }
}]);
