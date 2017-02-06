var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','homeService','dialog',function(scope,homeService,dialog){
	dialog.showLoading();
	homeService.getHomeUrl()
    .then(function(data){
    	if(data.success){
    		scope.personalInfo = data.result;
    	}
    	dialog.hideLoading();
    });
}]);


app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "homeController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);