var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','concatService','dialog',
    function(scope,concatService,dialog){
    	dialog.showLoading();
    	 concatService.getConcat().then(function(data){
    	 	scope.concats = data;
    	 	dialog.hideLoading();
         });
         scope.searchConcat = function(sparam){
         	dialog.showLoading();
         	concatService.getConcatParam(sparam).then(function(data){
	    	 	scope.concats = data;
	    	 	dialog.hideLoading();
	         });
         }
    }
]);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);