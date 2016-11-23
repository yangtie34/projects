var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "homeController"
	})
	.when("/month", {
		templateUrl: "tpl/month.html",
		controller :  "monthController"
	})
	.when("/detail/:datetime", {
		templateUrl: "tpl/detail.html",
		controller : "detailsController"
	})
	.when("/total", {
		templateUrl: "tpl/total.html",
		controller : "totalController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

app.controller("homeController",['$scope','cardService','dialog',function(scope,service,dialog){
	dialog.showLoading();
	service.queryYktYe().then(function(data){
		scope.yktye = data.ye;
		scope.xflist = data.xflist;
		dialog.hideLoading();
	});
}])
.controller("monthController",['$scope','cardService','dialog',function(scope,service,dialog){
	scope.page = {
		pagesize: 6,
		curpage: 1,
		sumcount: 0,
		loading : false
	};
	scope.monthlist = [];
	dialog.showLoading();
	scope.$watch("page.curpage",function(newval,oldval){
		scope.page.loading = true;
		service.queryMonthConsumeList(scope.page).then(function(data){
			scope.monthlist = $.merge(scope.monthlist, data.result);   
			scope.page.sumcount = data.sumcount;
			scope.page.loading = false;
			if (scope.page.curpage == 1) {
				dialog.hideLoading();
			}
		});
	},true);
	scope.loadMore = function(){
		scope.page.curpage ++;
	}
}])
.controller("detailsController",['$scope','$routeParams','cardService','dialog',function(scope,params,service,dialog){
	scope.params = params;
	scope.page = {
		pagesize: 10,
		curpage: 1,
		sumcount: 0,
		loading : false
	};
	scope.detaillist = [];
	dialog.showLoading();
	scope.$watch("page.curpage",function(newval,oldval){
		scope.page.loading = true;
		service.queryMonthConsumeDetail(scope.page,params.datetime).then(function(data){
			scope.detaillist = $.merge(scope.detaillist, data.result);   
			scope.page.sumcount = data.sumcount;
			scope.page.loading = false;
			if (scope.page.curpage == 1) {
				dialog.hideLoading();
			}
		});
	},true);
	scope.loadMore = function(){
		scope.page.curpage ++;
	}
}])
.controller("totalController",['$scope','cardService','dialog',function(scope,service,dialog){
	dialog.showLoading();
	service.queryConsumeTotalInfo().then(function(data){
		scope.totalConsume = data.total;
		scope.higherPersent = data.higherPersent;
		scope.dealList = data.dealList;
		scope.chartConfig = {
	        data : data.dealList,
	        type:"pie"
	    };
		dialog.hideLoading();
	});
}]);