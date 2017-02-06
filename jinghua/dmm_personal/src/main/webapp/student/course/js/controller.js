var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','courseService','dialog',
    function(scope,courseService,dialog){
    	dialog.showLoading();
    	 courseService.getToday().then(function(data){
    	 	scope.today = data;
    	 	 dialog.hideLoading();
         });
         courseService.getTodayCourse().then(function(data){
    	 	scope.courses = data;
    	 	 dialog.hideLoading();
         });
}]).controller("schedulecontroller",['$scope','courseService','dialog',
    function(scope,courseService,dialog){
    	dialog.showLoading();
	    courseService.getSchedule('0').then(function(data){
	    	scope.cous1 = data[0].list;
	    	scope.cous2 = data[1].list;
	    	scope.cous3 = data[2].list;
	    	scope.cous4 = data[3].list;
	    	scope.cous5 = data[4].list;
	    	dialog.hideLoading();
	    })
	    courseService.getWeek('0','','').then(function(data){
	    	dialog.hideLoading();
	    	scope.school_year = data.school_year;
	    	scope.school_term = data.school_term;
	    	scope.month = data.month;
	    	scope.zc = data.zc;
	    	scope.sz = data.zc-1;
	    	scope.xz = data.zc+1;
	    	scope.zy = data.zy;
	    	scope.ze = data.ze;
	    	scope.zs = data.zs;
	    	scope.zsi = data.zsi;
	    	scope.zw = data.zw;
	    	scope.zl = data.zl;
	    	scope.zr = data.zr;
	    	scope.zyrq = data.zyrq;
	    })
	    scope.lastWeek = function(){
	    	dialog.showLoading();
	    	var week = '';
	    	if(scope.zc != 1){
	    		week = scope.zc - 1;
	    		var zyrq = scope.zyrq;
		    	courseService.getSchedule(week).then(function(data){
			    	scope.cous1 = data[0].list;
			    	scope.cous2 = data[1].list;
			    	scope.cous3 = data[2].list;
			    	scope.cous4 = data[3].list;
			    	scope.cous5 = data[4].list;
			    	dialog.hideLoading();
			    })
			    courseService.getWeek(week,zyrq,'-').then(function(data){
			    	scope.school_year = data.school_year;
			    	scope.school_term = data.school_term;
			    	scope.month = data.month;
			    	scope.zc = data.zc;
			    	scope.sz = data.zc-1;
			    	scope.xz = data.zc+1;
			    	scope.zy = data.zy;
			    	scope.ze = data.ze;
			    	scope.zs = data.zs;
			    	scope.zsi = data.zsi;
			    	scope.zw = data.zw;
			    	scope.zl = data.zl;
			    	scope.zr = data.zr;
			    	scope.zyrq = data.zyrq;
			    })
	    	}
	    	dialog.hideLoading();
	    }
	    scope.nextWeek = function(){
	    	dialog.showLoading();
	    	var week = '';
	    	if(scope.zc < 20){
	    		week = scope.zc + 1;
	    		var zyrq = scope.zyrq;
		    	courseService.getSchedule(week).then(function(data){
			    	scope.cous1 = data[0].list;
			    	scope.cous2 = data[1].list;
			    	scope.cous3 = data[2].list;
			    	scope.cous4 = data[3].list;
			    	scope.cous5 = data[4].list;
			    	dialog.hideLoading();
			    })
			    courseService.getWeek(week,zyrq,'+').then(function(data){
			    	dialog.hideLoading();
			    	scope.school_year = data.school_year;
			    	scope.school_term = data.school_term;
			    	scope.month = data.month;
			    	scope.zc = data.zc;
			    	scope.sz = data.zc-1;
			    	scope.xz = data.zc+1;
			    	scope.zy = data.zy;
			    	scope.ze = data.ze;
			    	scope.zs = data.zs;
			    	scope.zsi = data.zsi;
			    	scope.zw = data.zw;
			    	scope.zl = data.zl;
			    	scope.zr = data.zr;
			    	scope.zyrq = data.zyrq;
			    })
	    	}
	    	dialog.hideLoading();
	    }
	    
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/schedule", {
		templateUrl: "tpl/schedule.html",
		reloadOnSearch: false,
		controller : "schedulecontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

