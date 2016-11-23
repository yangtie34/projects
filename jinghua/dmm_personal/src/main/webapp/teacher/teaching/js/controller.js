var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','teachingService','dialog',
    function(scope,teachingService,dialog){
    	dialog.showLoading();
    	 teachingService.getTodayClass().then(function(data){
               scope.todayClasses = data;
               dialog.hideLoading();
         });
         teachingService.getTermClass().then(function(data){
               scope.classes = data;
               dialog.hideLoading();
         });
         scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
	          getPic();
		 });
         var getPic = function(){
         	mydata = scope.classes;
	         for(var i=0;i<mydata.length;i++){
	       		var datas = [];
	       		var ysks = mydata[i].ALREADY_CLASS;
	       		var syks = mydata[i].CLASSCOUNTS - mydata[i].ALREADY_CLASS;
	       		datas.push({name:'已上课时',value:ysks});
	       		datas.push({name:'剩余课时',value:syks})
		        option = {
				    legend: {
				        show :false,
				        data:['已上课时','剩余课时']
				    }, 
				    calculable : false,
		    		color:['#FF6666','#33CC99'],
				    series : [
				        {
				            name:'课时进度',
				            type:'pie',
				            radius : ['50%', '70%'],
				            itemStyle : {
				            	normal : {
                                    label : {
                                        show : false
                                    },
                                    labelLine : {
                                        show : false
                                    }
                                },
				                emphasis : {
				                    label : {
				                        show : true,
				                        position : 'center',
				                        textStyle : {
				                            fontSize : '15',
				                            fontWeight : 'bold'
				                        }
				                    }
				                }
				            },
				            data:datas
				        }
				    ]
				};
			    var charts = echarts.init(document.getElementById(i));
				charts.setOption(option);
	         }
         }
}]).controller("teachingclasscontroller",['$scope','$routeParams','teachingService','dialog',
    function(scope,params,teachingService,dialog){
    	dialog.showLoading();
    	 var id = params.todayclassid;
    	 var courseId = params.courseid;
    	 teachingService.getTodayCourse(id,courseId).then(function(data){
    	 	   console.log(data);
    	 	   if(data != '' && data.length>0){
 	   				scope.course_name = data[0].COURSE_NAME;
 	   				scope.time_ = data[0].TIME_;
 	   				scope.classroom = data[0].CLASSROOM;
					scope.courses = data;
    	 	   }
				dialog.hideLoading();
         });
}]).controller("classschedulecontroller",['$scope','teachingService','dialog',
    function(scope,teachingService,dialog){
    	dialog.showLoading();
    	teachingService.getClassSchedule('0').then(function(data){
	    	scope.cous1 = data[0].list;
	    	scope.cous2 = data[1].list;
	    	scope.cous3 = data[2].list;
	    	scope.cous4 = data[3].list;
	    	scope.cous5 = data[4].list;
	    	dialog.hideLoading();
	    })
	    teachingService.getWeek('0','','').then(function(data){
	    	scope.year = data.year;
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
	    	if(scope.zc > 1){
	    		week = scope.zc - 1;
	    		var zyrq = scope.zyrq;
		    	teachingService.getClassSchedule(week).then(function(data){
			    	scope.cous1 = data[0].list;
			    	scope.cous2 = data[1].list;
			    	scope.cous3 = data[2].list;
			    	scope.cous4 = data[3].list;
			    	scope.cous5 = data[4].list;
			    	dialog.hideLoading();
			    })
			    teachingService.getWeek(week,zyrq,'-').then(function(data){
			    	scope.year = data.year;
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
		    	teachingService.getClassSchedule(week).then(function(data){
			    	scope.cous1 = data[0].list;
			    	scope.cous2 = data[1].list;
			    	scope.cous3 = data[2].list;
			    	scope.cous4 = data[3].list;
			    	scope.cous5 = data[4].list;
			    	dialog.hideLoading();
			    })
			    teachingService.getWeek(week,zyrq,'+').then(function(data){
			    	dialog.hideLoading();
			    	scope.year = data.year;
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
	.when("/teachingclass/:todayclassid/:courseid", {
		templateUrl: "tpl/teachingclass.html",
		reloadOnSearch: false,
		controller : "teachingclasscontroller"
	})
	.when("/classschedule", {
		templateUrl: "tpl/classschedule.html",
		reloadOnSearch: false,
		controller : "classschedulecontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

app.directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    };
});
