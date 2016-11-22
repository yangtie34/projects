var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','paisanService','dialog',
    function(scope,paisanService,dialog){
    	dialog.showLoading();
    	var paramname = '';
    	var paramflag = 'tx';
    	var paginationConf = {
            currentPage: 1,
			totalPage : 1
        };
    	paisanService.getPaisan().then(function(data){
			  scope.stus = data.result;
			  dialog.hideLoading();
			  paginationConf.currentPage = data.curpage;
			  paginationConf.totalPage = data.pagecount;
			  scope.currpage = data.curpage;
			  scope.totalpage = data.pagecount;
        });
    	scope.searchPaisan = function(sparam){
    		paramname = sparam;
         	paisanService.getPaisanParam('1',paramname,paramflag).then(function(data){
			  scope.stus = data.result;
			  paginationConf.currentPage = data.curpage;
			  paginationConf.totalPage = data.pagecount;
			  scope.currpage = data.curpage;
			  scope.totalpage = data.pagecount;
         	});
         }
         scope.goX = function(){
         	dialog.showLoading();
         	$("#tx").addClass("rxs-td-active");
         	$("#tmx").removeClass("rxs-td-active");
         	$("#tyx").removeClass("rxs-td-active");
         	$("#tzy").removeClass("rxs-td-active");
         	paramflag = 'tx'; 
         	paisanService.getPaisanParam('1',paramname,paramflag).then(function(data){
			  scope.stus = data.result;
			  paginationConf.currentPage = data.curpage;
			  paginationConf.totalPage = data.pagecount;
			  scope.currpage = data.curpage;
			  scope.totalpage = data.pagecount;
			  dialog.hideLoading();
         	});
         }
         scope.goMx = function(){
         	dialog.showLoading();
         	$("#tmx").addClass("rxs-td-active");
         	$("#tx").removeClass("rxs-td-active");
         	$("#tyx").removeClass("rxs-td-active");
         	$("#tzy").removeClass("rxs-td-active");
         	
         	paramflag = 'tmx'
         	paisanService.getPaisanParam('1',paramname,paramflag).then(function(data){
			  scope.stus = data.result;
			  paginationConf.currentPage = data.curpage;
			  paginationConf.totalPage = data.pagecount;
			  scope.currpage = data.curpage;
			  scope.totalpage = data.pagecount;
			  dialog.hideLoading();
         	});
         }
         scope.goZy = function(){
         	dialog.showLoading();
         	$("#tzy").addClass("rxs-td-active");
         	$("#tx").removeClass("rxs-td-active");
         	$("#tyx").removeClass("rxs-td-active");
         	$("#tmx").removeClass("rxs-td-active");
         	paramflag = 'tzy'
         	paisanService.getPaisanParam('1',paramname,paramflag).then(function(data){
			  scope.stus = data.result;
			  paginationConf.currentPage = data.curpage;
			  paginationConf.totalPage = data.pagecount;
			  scope.currpage = data.curpage;
			  scope.totalpage = data.pagecount;
			  dialog.hideLoading();
         	});
         }
         scope.goYx = function(){
         	dialog.showLoading();
         	$("#tyx").addClass("rxs-td-active");
         	$("#tx").removeClass("rxs-td-active");
         	$("#tmx").removeClass("rxs-td-active");
         	$("#tzy").removeClass("rxs-td-active");
         	paramflag = 'tyx'
         	paisanService.getPaisanParam('1',paramname,paramflag).then(function(data){
			  scope.stus = data.result;
			  paginationConf.currentPage = data.curpage;
			  paginationConf.totalPage = data.pagecount;
			  scope.currpage = data.curpage;
			  scope.totalpage = data.pagecount;
			  dialog.hideLoading();
         	});
         }
         scope.getPage = function () {
	     	if(paginationConf.currentPage <= paginationConf.totalPage){
	 			paisanService.getPaisanParam(paginationConf.currentPage,paramname,paramflag).then(function(data){
				   paginationConf.currentPage = data.curpage;
				   paginationConf.totalPage = data.pagecount;
				   scope.currpage = data.curpage;
				   scope.totalpage = data.pagecount;
				   for(var i=0;i<data.result.length;i++){
				   		scope.stus.push(data.result[i]);
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
         
}]).controller("stucontroller",['$scope','$routeParams','paisanService','dialog',
    function(scope,params,paisanService,dialog){
    	dialog.showLoading();
    	console.log(params);
    	paisanService.getPaisanStu(params.stuId).then(function(data){
			 scope.stu = data;
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
	.when("/stu/:stuId", {
		templateUrl: "tpl/stu.html",
		reloadOnSearch: false,
		controller : "stucontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

