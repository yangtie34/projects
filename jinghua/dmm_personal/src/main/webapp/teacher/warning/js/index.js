var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		controller : "homeController"
	})
	.when("/consume/:months/:type", {
		templateUrl: "tpl/consume.html",
		controller : "consumeController"
	})
	.when("/stay/:type", {
		templateUrl: "tpl/stay.html",
		controller : "stayController"
	})
	.when("/study/:xn/:xq/:classId/:class/:nums", {
		templateUrl: "tpl/study.html",
		controller : "studyController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);