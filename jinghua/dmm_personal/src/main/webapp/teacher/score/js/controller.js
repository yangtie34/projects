var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','scoreService','dialog',
    function(scope,scoreService,dialog){
    	dialog.showLoading();
    	var dt = new Date();
		var curMonth = dt.getMonth();
		var curYear = 0;
		if(curMonth < 8){
			curYear = dt.getFullYear();
		}else{
			curYear = dt.getFullYear() + 1;
		}
		var xnxq = [];
		for (var i = 0; i < 5; i++) {
			for (var j = 2; j >= 1; j--) {
				var it  = {
					xn : (curYear-1-i) +"-"+ (curYear-i),
					xq : '0'+j
				};
				xnxq.push(it);
			}
		}
		var p = [];
		if(curMonth < 8){
			p[0] = xnxq[1].xn;
			p[1] = xnxq[1].xq;
		}else{
			p[0] = xnxq[2].xn;
			p[1] = xnxq[2].xq;
		}
		scope.xn = p[0];
		scope.xq = p[1];
		scope.schoolYearTerm = p[0]+'学年第'+p[1]+'学期';
		
		scoreService.getScoreClasses(p[0],p[1]).then(function(data2){
            scope.classes = data2;
            scoreService.getCourseScore(p[0],p[1]).then(function(data3){
    			dialog.hideLoading();
                scope.courses = data3;
            });
        });
		scope.xnxq = xnxq;
		scope.selectXnxq = function(){
			$("#myModal").modal();
		}
		scope.submitXnxq = function(x){
			dialog.showLoading();
			scope.classes = '';
			scope.courses = '';
			scope.xn = x.xn;
			scope.xq = x.xq;
			scope.schoolYearTerm = x.xn+'学年第'+x.xq+'学期';
    		scoreService.getScoreClasses(x.xn,x.xq).then(function(data2){
	            scope.classes = data2;
	            scoreService.getCourseScore(x.xn,x.xq).then(function(data3){
	    			dialog.hideLoading();
		            scope.courses = data3;
		        });
	        });
		}
}]).controller("scorelistcontroller",['$scope','$routeParams','scoreService','dialog',
    function(scope,params,scoreService,dialog){
    	dialog.showLoading();
		var classId = params.classid;
		var courseId = params.courseid;
		var flag = params.flag;
		var xn = params.xn;
		var xq = params.xq;
		var stuParam = "";
		scope.searchStu = function(sparam){
			scope.stuCourses = '';
			scope.stus = '';
			stuParam = sparam;
			scoreService.getStuScore(classId,courseId,stuParam,xn,xq,flag).then(function(data){
			   scope.xn = xn;
    		   scope.xq = xq;
    		   if(flag == 'xzb'){
    		   		scope.stus = data;
			   }else{
			   		scope.stuCourses = data;
			   }
       		});
		}
    	scoreService.getStuScore(classId,courseId,stuParam,xn,xq,flag).then(function(data){
    		   dialog.hideLoading();
    		   scope.xn = xn;
    		   scope.xq = xq;
    		   if(flag == 'xzb'){
				 	scope.stus = data;
			   }else{
			   	 	scope.stuCourses = data;
			   }
        });
        
}]).controller("scoredetailcontroller",['$scope','$routeParams','scoreService','dialog',
    function(scope,params,scoreService,dialog){
    	dialog.showLoading();
    	 var stuId = params.stuid;
    	 var xn = params.xn;
		 var xq = params.xq;
    	 scoreService.getStuTotalScore(stuId,xn,xq).then(function(data){
    	 	dialog.hideLoading();
               scope.stu = data[0];
         });
         
         scoreService.getStuScoreDetail(stuId,xn,xq).then(function(data){
         	dialog.hideLoading();
               scope.scoredetail = data;
         });
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/scorelist/:classid/:courseid/:xn/:xq/:flag", {
		templateUrl: "tpl/scorelist.html",
		reloadOnSearch: false,
		controller : "scorelistcontroller"
	})
	.when("/scoredetail/:stuid/:xn/:xq", {
		templateUrl: "tpl/scoredetail.html",
		reloadOnSearch: false,
		controller : "scoredetailcontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);
