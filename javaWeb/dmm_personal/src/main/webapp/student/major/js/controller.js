var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','majorService','dialog',
    function(scope,majorService,dialog){
    	dialog.showLoading();
    	 majorService.getMajor().then(function(data){
			  scope.major = data.major;
			  dialog.hideLoading();
         });
}]).controller("teachingplancontroller",['$scope','majorService','dialog',
    function(scope,majorService,dialog){
    	dialog.showLoading();
    	 majorService.getCourse().then(function(data){
			  scope.courses = data;
			  dialog.hideLoading();
         });
}]).controller("scorepasscontroller",['$scope','majorService','dialog',
    function(scope,majorService,dialog){
    	dialog.showLoading();
    	 majorService.getCourseScore().then(function(data){
			  scope.courseScore = data;
			  dialog.hideLoading();
         });
}]).controller("choosecoursecontroller",['$scope','majorService','dialog',
    function(scope,majorService,dialog){
    	dialog.showLoading();
    	var paginationConf = {
            currentPage: 1,
			totalPage : 1
        };
        majorService.getChooseCourse('1').then(function(data){
	     	paginationConf.totalPage = data.pagecount;
	     	paginationConf.currentPage = data.curpage;
	     	scope.currpage = data.curpage;
	     	scope.totalpage = data.pagecount;
            scope.chooseCourse = data.result;
            dialog.hideLoading();
        });
        scope.getPage = function () {
     	   if(paginationConf.currentPage <= paginationConf.totalPage){
 			   majorService.getChooseCourse(paginationConf.currentPage).then(function(data){
			      paginationConf.totalPage = data.pagecount;
			      paginationConf.currentPage = data.curpage;
	     	      scope.currpage = data.curpage;
	     	      scope.totalpage = data.pagecount;
			      for(var i=0;i<data.result.length;i++){
			   		 scope.chooseCourse.push(data.result[i]);
			      }
         	   });
     	   }
        }
		$(window).scroll(function() {
	        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
	        	if(paginationConf.currentPage < paginationConf.totalPage){
	        		paginationConf.currentPage = paginationConf.currentPage+1;
	        		scope.getPage();
	        	}
	        }
		});
}]).controller("postgraduatecontroller",['$scope','majorService','dialog',
    function(scope,majorService,dialog){
    	dialog.showLoading();
    	var paginationConf = {
            currentPage: 1,
			totalPage : 1
        };
        majorService.getPostgraduate('1').then(function(data){
	     	paginationConf.totalPage = data.pagecount;
	     	paginationConf.currentPage = data.curpage;
	     	scope.currpage = data.curpage;
	     	scope.totalpage = data.pagecount;
           scope.postgraduates = data.result;
           dialog.hideLoading();
     });
     scope.getPage = function () {
     	if(paginationConf.currentPage <= paginationConf.totalPage){
 			majorService.getPostgraduate(paginationConf.currentPage).then(function(data){
			   paginationConf.totalPage = data.pagecount;
			   paginationConf.currentPage = data.curpage;
	     	   scope.currpage = data.curpage;
	     	   scope.totalpage = data.pagecount;
			   for(var i=0;i<data.result.length;i++){
			   		scope.postgraduates.push(data.result[i]);
			   }
         	});
     	}
     }
	$(window).scroll(function() {
        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
        	if(paginationConf.currentPage < paginationConf.totalPage){
        		paginationConf.currentPage = paginationConf.currentPage+1;
        		scope.getPage();
        	}
        }
	});
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/teachingplan", {
		templateUrl: "tpl/teachingplan.html",
		reloadOnSearch: false,
		controller : "teachingplancontroller"
	})
	.when("/scorepass", {
		templateUrl: "tpl/scorepass.html",
		reloadOnSearch: false,
		controller : "scorepasscontroller"
	})
	.when("/choosecourse", {
		templateUrl: "tpl/choosecourse.html",
		reloadOnSearch: false,
		controller : "choosecoursecontroller"
	})
	.when("/postgraduate", {
		templateUrl: "tpl/postgraduate.html",
		reloadOnSearch: false,
		controller : "postgraduatecontroller"
	})
	.when("/work", {
		templateUrl: "tpl/work.html",
		reloadOnSearch: false,
		controller : "workcontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

