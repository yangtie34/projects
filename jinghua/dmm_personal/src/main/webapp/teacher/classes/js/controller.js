var app = angular.module('app', ['ngRoute','system']);
app.controller("controller",['$scope','classService','dialog',function(scope,service,dialog){
	//班级信息汇总
	dialog.showLoading();
	service.queryClassTotalInfo().then(function(data){
		scope.totalInfo = data;
		service.queryClassList().then(function(bjlist){
			//班级列表
			scope.bjlist = bjlist;
			dialog.hideLoading();
		});
	})
}])
.controller("classController",['$scope','$routeParams','classService','dialog','locationService',
                               function(scope,params,service,dialog,location){
	if(params.classId == null){
		dialog.alert("系统参数错误！",function(){
			location.redirect(base + "teacher/classes/classes.jsp");
		});
	}else{
		dialog.showLoading();
		service.queryClassInfo(params.classId).then(function(data){
			scope.currentClassInfo = data;
		});
		service.queryStudentsListOfClass(params.classId).then(function(xslist){
			scope.xslist = xslist;
			dialog.hideLoading();
		});
	}
}])
.controller("studentsController",['$scope','classService','dialog',function(scope,service,dialog){
	dialog.showLoading();
	service.queryStudentsList().then(function(data){
		scope.xslist = data;
		dialog.hideLoading();
	});
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/class/:classId", {
		templateUrl: "tpl/class.html",
		controller :  "classController"
	})
	.when("/students", {
		templateUrl: "tpl/students.html",
		controller : "studentsController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);