var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','bookService','dialog',
    function(scope,bookService,dialog){
    	dialog.showLoading();
    	bookService.getBorrowCounts().then(function(data){
    		scope.borrowCounts = data;
    		dialog.hideLoading();
    	});
    	bookService.getBorrowProportion().then(function(data){
    		scope.proportion = data.proportion;
    		dialog.hideLoading();
    	});
    	bookService.getRecommendBook().then(function(data){
    		scope.books = data;
    		dialog.hideLoading();
    	});
    	bookService.getBorrowType().then(function(data){
    		var n = [];
    		var nv = data;
    		for(var i=0;i<data.length;i++){
    			n.push(data[i].name);
    		}
    		option = {
    				tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        show :false,
				        data:n
				    }, 
				    calculable : false,
				    series : [
				        {
				            name:'图书分类阅读',
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
				                            fontSize : '30',
				                            fontWeight : 'bold'
				                        }
				                    }
				                }
				            },
				            data:nv
				        }
				    ]
				};
			    var mychart = echarts.init(document.getElementById("mycharts"));
				mychart.setOption(option);
    	});
		
    	
}]).controller("booklistcontroller",['$scope','bookService','dialog',
    function(scope,bookService,dialog){
    	dialog.showLoading();
    	var paginationConf = {
            currentPage: 1,
			totalPage : 1
        };
        bookService.getBorrowList('1').then(function(data){
	     	paginationConf.totalPage = data.pagecount;
	     	paginationConf.currentPage = data.curpage;
	     	scope.currpage = data.curpage;
	     	scope.totalpage = data.pagecount;
            scope.borrowbooks = data.result;
            dialog.hideLoading();
     });
     scope.getPage = function () {
         	if(paginationConf.currentPage <= paginationConf.totalPage){
	 			bookService.getBorrowList(paginationConf.currentPage).then(function(data){
				   paginationConf.totalPage = data.pagecount;
		     	   paginationConf.currentPage = data.curpage;
		     	   scope.currpage = data.curpage;
		     	   scope.totalpage = data.pagecount;
				   for(var i=0;i<data.result.length;i++){
				   		scope.borrowbooks.push(data.result[i]);
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
	.when("/bookList", {
		templateUrl: "tpl/bookList.html",
		reloadOnSearch: false,
		controller : "booklistcontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

