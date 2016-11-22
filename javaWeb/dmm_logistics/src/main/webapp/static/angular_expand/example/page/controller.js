//定义一个controller
//参数分别是1 模块名 2 控制器名 3 注入对象以及控制器函数
var app = angular.module('app',['custom','charge']);
custom.controller('app',"controller",['$scope',function($scope){
	$scope.pageconfig = {
		  currentPage: 1,
            totalItems: 8000,
            itemsPerPage: 15,
            pagesLength: 11,
            perPageOptions: [10, 20, 30, 40, 50],
            rememberPerPage: 'perPageItems',
            onChange: function(data){
            	console.log(data);
            }
	};
	
}]);
