var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','daliyLifeService','dialog',
    function(scope,daliyLifeService,dialog){
    	dialog.showLoading();
    	daliyLifeService.getMonthConsume().then(function(data){
               scope.card = data;
               dialog.hideLoading();
         });
         daliyLifeService.getTotalConsume().then(function(data){
               scope.totalConsume = data.totalConsume;
               if(scope.totalConsume == 0){
               		$("#cardA").removeAttr("href");
               }
               dialog.hideLoading();
         });
         daliyLifeService.getBorrow().then(function(data){
               scope.book = data;
               if(scope.book.totalBorrow == 0){
               	$("#bookA").removeAttr("href");
               }
               dialog.hideLoading();
         });
         daliyLifeService.getStopCounts().then(function(data){
         		scope.stopCounts = data.counts;
         		if(scope.stopCounts == 0){
         			$("#carA").removeAttr("href");
         		}
         });
         daliyLifeService.getStopTimeAvg().then(function(data){
         		scope.stopTimeAvg = data.day_avg;
         });
}]).controller("bookborrowController",['$scope','$routeParams','daliyLifeService','dialog',
    function(scope,params,daliyLifeService,dialog){
    	dialog.showLoading();
    	scope.inBorrow = function(){
    		$("#recomment").removeClass("jzhi-menu-hover");
    		$("#outOfDate").removeClass("jzhi-menu-hover");
    		$("#return").removeClass("jzhi-menu-hover");
    		$("#noneRecomment").hide();
    		$("#noneOut").hide();
    		$("#noneReturn").hide();
    		$("#re").hide();
	    	$("#out").hide();
	    	$("#retu").hide();
	    	$('#in').show();
	    	$('#inBorrow').addClass("jzhi-menu-hover");
	    	if(scope.in_borrow == 0){
    			$("#noneIn").show();
    		}
    	}
    	scope.returnBorrow = function(){
    		$("#recomment").removeClass("jzhi-menu-hover");
    		$("#outOfDate").removeClass("jzhi-menu-hover");
    		$("#inBorrow").removeClass("jzhi-menu-hover");
    		$("#noneRecomment").hide();
    		$("#noneOut").hide();
    		$("#noneIn").hide();
    		$('#in').hide();
    		$("#re").hide();
	    	$("#out").hide();
	    	$("#retu").show();
	    	$('#return').addClass("jzhi-menu-hover");
	    	if(scope.return_book == 0){
    			$("#noneReturn").show();
    		}
    	}
    	scope.outOfDate = function(){
    		$("#recomment").removeClass("jzhi-menu-hover");
    		$("#return").removeClass("jzhi-menu-hover");
    		$("#inBorrow").removeClass("jzhi-menu-hover");
    		$("#noneRecomment").hide();
    		$("#noneReturn").hide();
    		$("#noneIn").hide();
    		$('#in').hide();
    		$("#re").hide();
	    	$("#retu").hide();
	    	$('#outOfDate').addClass("jzhi-menu-hover");
	    	$("#out").show();
	    	if(scope.out_of_date == 0){
    			$("#noneOut").show();
    		}
    	}
    	scope.recommentBorrow= function(){
    		$("#return").removeClass("jzhi-menu-hover");
    		$("#outOfDate").removeClass("jzhi-menu-hover");
    		$("#inBorrow").removeClass("jzhi-menu-hover");
    		$("#noneOut").hide();
    		$("#noneReturn").hide();
    		$("#noneIn").hide();
    		$('#in').hide();
	    	$("#retu").hide();
	    	$("#out").hide();
	    	$("#re").show();
	    	$('#recomment').addClass("jzhi-menu-hover");
	    	if(scope.recomment_book == 0){
    			$("#noneRecomment").show();
    		}
    	}
    	daliyLifeService.getInBorrow().then(function(data){
    		scope.in_borrow = data.length;
    		if(scope.in_borrow == 0){
    			$("#noneIn").show();
    		}
            scope.inborrowbooks = data;
            dialog.hideLoading();
        });
        daliyLifeService.getRecommentBorrow().then(function(data){
        	scope.recomment_book = data.length;
            scope.recommendbooks = data;
            dialog.hideLoading();
        });
        daliyLifeService.getOutOfDateBorrow().then(function(data){
        	scope.out_of_date = data.length;
            scope.outofdatebooks = data;
            dialog.hideLoading();
        });
        daliyLifeService.getReturnBorrow().then(function(data){
        	scope.return_book = data.length;
            scope.returnbooks = data;
            dialog.hideLoading();
        });
    	
}]).controller("totalConsumeController",['$scope','daliyLifeService','dialog',
     function(scope,daliyLifeService,dialog){
     	dialog.showLoading();
     	var myDate = new Date();
     	scope.currYear = myDate.getFullYear(); 
     	scope.lastYear = scope.currYear-1;
     	scope.nextYear = scope.currYear+1;
         daliyLifeService.getPayHistory(scope.currYear).then(function(data){
            console.log(data);
            scope.consumes = data;
            dialog.hideLoading();
         });
         getData = function(){
         	scope.consumes = '';
         	daliyLifeService.getPayHistory(scope.currYear).then(function(data){
	            console.log(data);
	            scope.consumes = data;
	            dialog.hideLoading();
	         });
         }
         scope.clickLeft = function(){
         	dialog.showLoading();
         	scope.currYear = scope.currYear -1;
         	scope.lastYear = scope.lastYear -1;
         	scope.nextYear = scope.nextYear -1;
         	getData();
         }
         scope.clickRight = function(){
         	dialog.showLoading();
         	scope.currYear = scope.currYear +1;
         	scope.lastYear = scope.lastYear +1;
         	scope.nextYear = scope.nextYear +1;
         	getData();
         }
}]).controller("monthConsumeController",['$scope','$routeParams','daliyLifeService','dialog',
    function(scope,params,daliyLifeService,dialog){
    	dialog.showLoading();
    	var month = params.year+'-'+params.month+'-01';
    	daliyLifeService.getMonthPayType(month).then(function(data){
	           scope.pay_type = data;
	     });
	     //配置分页基本参数
        var paginationConf = {
            currentPage: 1,
			totalPage : 1
        };
		 var f = "all";
	     daliyLifeService.getMonthConsumeListPage(month,'1',f).then(function(data){
		   paginationConf.totalPage = data.pagecount;
		   paginationConf.currentPage = data.curpage;
		   scope.monthConsumes = data.result;
		   scope.currpage = data.curpage;
		   scope.totalpage = data.pagecount;
		   dialog.hideLoading();
         });
         scope.getEat = function(){
         	f = "eat";
         	daliyLifeService.getMonthConsumeListPage(month,'1',f).then(function(data){
			   paginationConf.totalPage = data.pagecount;
			   paginationConf.currentPage = data.curpage;
			   scope.monthConsumes = data.result;
			   scope.currpage = data.curpage;
		   	   scope.totalpage = data.pagecount;
			   dialog.hideLoading();
	         });
         }
         scope.getOther = function(){
         	f = "other";
         	daliyLifeService.getMonthConsumeListPage(month,'1',f).then(function(data){
			   paginationConf.totalPage = data.pagecount;
			   paginationConf.currentPage = data.curpage;
			   scope.monthConsumes = data.result;
			   scope.currpage = data.curpage;
		       scope.totalpage = data.pagecount;
			   dialog.hideLoading();
	         });
         }
         
         scope.getPage = function () {
         	var month = params.year+'-'+params.month+'-01';
         	if(paginationConf.currentPage <= paginationConf.totalPage){
	 			daliyLifeService.getMonthConsumeListPage(month,paginationConf.currentPage,f).then(function(data){
				   paginationConf.totalPage = data.pagecount;
				   paginationConf.currentPage = data.curpage;
				   for(var i=0;i<data.result.length;i++){
				   		scope.monthConsumes.push(data.result[i]);
				   }
				   scope.currpage = data.curpage;
		   		   scope.totalpage = data.pagecount;
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
}]).controller("carStopController",['$scope','daliyLifeService','dialog',
     function(scope,daliyLifeService,dialog){
     	dialog.showLoading();
     	var resultList = '';
         daliyLifeService.getCarStop().then(function(data){
            console.log(data);
            resultList = data;
            if(data.length > 0){
            	scope.curry = data[0].y;
            	scope.currm = data[0].m;
            	scope.cars = data[0].list;
            }else{
            	var myDate = new Date();
            	scope.curry = myDate.getFullYear();
            	scope.currm = myDate.getMonth()+1;
            	scope.cars = '';
            }
            scope.lasty = (scope.currm-1==0)?scope.curry-1:scope.curry; 
            scope.lastm = (scope.currm-1==0)?12:scope.currm-1;
            scope.nexty = (scope.currm+1==13)?scope.curry+1:scope.curry; 
            scope.nextm = (scope.currm+1==13)?1:scope.currm+1;
            dialog.hideLoading();
         });
         getData = function(){
         	scope.cars = '';
         	for(var i=0;i<resultList.length;i++){
         		if(resultList[i].y == scope.curry && resultList[i].m == scope.currm){
         			scope.cars = resultList[i].list;
         		}
         	}
         }
         scope.clickLeft = function(){
         	scope.curry = (scope.currm-1==0)?scope.curry-1:scope.curry; 
            scope.currm = (scope.currm-1==0)?12:scope.currm-1;
         	scope.lasty = (scope.currm-1==0)?scope.curry-1:scope.curry; 
            scope.lastm = (scope.currm-1==0)?12:scope.currm-1;
         	scope.nexty = (scope.currm+1==13)?scope.curry+1:scope.curry; 
            scope.nextm = (scope.currm+1==13)?1:scope.currm+1;
         	getData();
         }
         scope.clickRight = function(){
         	scope.curry = (scope.currm+1==13)?scope.curry+1:scope.curry; 
            scope.currm = (scope.currm+1==13)?1:scope.currm+1;
         	scope.lasty = (scope.currm-1==0)?scope.curry-1:scope.curry; 
            scope.lastm = (scope.currm-1==0)?12:scope.currm-1;
         	scope.nexty = (scope.currm+1==13)?scope.curry+1:scope.curry; 
            scope.nextm = (scope.currm+1==13)?1:scope.currm+1;
         	getData();
         }
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/bookborrow/:flag", {
		templateUrl: "tpl/bookborrow.html",
		reloadOnSearch: false,
		controller : "bookborrowController"
	})
	.when("/totalConsume", {
		templateUrl: "tpl/totalConsume.html",
		reloadOnSearch: false,
		controller : "totalConsumeController"
	})
	.when("/carStop", {
		templateUrl: "tpl/carStop.html",
		reloadOnSearch: false,
		controller : "carStopController"
	})
	.when("/monthConsume/:year/:month", {
		templateUrl: "tpl/monthConsume.html",
		reloadOnSearch: false,
		controller : "monthConsumeController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);
