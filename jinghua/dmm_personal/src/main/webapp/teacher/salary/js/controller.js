var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','salaryService','dialog',
    function(scope,salaryService,dialog){
    	 dialog.showLoading();
    	 salaryService.getLastSalary().then(function(data){
			  scope.lastSalary = data;
			  if(scope.lastSalary.LASTSALARY == ''){
			  	$("#mya1").removeAttr("href");
			  }
			  dialog.hideLoading();
         });
         salaryService.getTotalSalary().then(function(data){
                //console.log(data);
				scope.totalSalary = data.TOTALSALARY;
				if(scope.totalSalary == ''){
				  	$("#mya2").removeAttr("href");
				 }
				dialog.hideLoading();
         });
         salaryService.getSalaryCompose().then(function(data){
                //console.log(data);
                scope.xzzc = data;
                dialog.hideLoading();
         });
         salaryService.getRetireTotalSalary().then(function(data){
                //console.log(data);
				scope.futureSalary = data.futureSalary;
				dialog.hideLoading();
		 });
		 salaryService.getFiveYearSalary().then(function(data){
		 		//console.log(data);
		 		scope.mycharts =  {
			    		title : "近五年月均薪资",
			    		isSort : false,
			    		data : data,
			    		yAxis : "元",
			    		type :"line"  
			    };
				dialog.hideLoading();
		 })
}]).controller("historysalarycontroller",['$scope','salaryService','dialog',
    function(scope,salaryService,dialog){
    	dialog.showLoading();
    	var resultList = '';
    	salaryService.getHistorySalary().then(function(data){
    		dialog.hideLoading();
            //console.log(data);
            resultList = data;
            scope.currYear = data[0].year;
            scope.lastYear = data[0].year-1;
            scope.nextYear = data[0].year+1;
            scope.historysalary = data[0].list;
         });
         getData = function(){
         	scope.historysalary = '';
         	for(var i=0;i<resultList.length;i++){
         		if(resultList[i].year == scope.currYear){
         			scope.historysalary = resultList[i].list;
         		}
         	}
         }
         scope.clickLeft = function(){
         	scope.currYear = scope.currYear -1;
         	scope.lastYear = scope.lastYear -1;
         	scope.nextYear = scope.nextYear -1;
         	getData();
         }
         scope.clickRight = function(){
         	scope.currYear = scope.currYear +1;
         	scope.lastYear = scope.lastYear +1;
         	scope.nextYear = scope.nextYear +1;
         	getData();
         }
}]).controller("lastsalarycontroller",['$scope','$routeParams','salaryService','dialog',
    function(scope,params,salaryService,dialog){
    	dialog.showLoading();
    	var year_ = params.year_;
    	var month_ = params.month_;
    	salaryService.getLastSalaryCom(year_,month_).then(function(data){
    		//console.log(data);
    		scope.salaryCom = data;
    		dialog.hideLoading();
    	})
    	salaryService.getLastSalaryPayable(year_,month_).then(function(data){
    		//console.log(data);
    		scope.totalSalaryCom = data;
    		dialog.hideLoading();
    	})
    	salaryService.getLastSalaryTotal(year_,month_).then(function(data){
    		//console.log(data);
    		scope.totalSalary = data;
    		dialog.hideLoading();
    	})
    	salaryService.getLastSalarySubtract(year_,month_).then(function(data){
    		//console.log(data);
    		scope.subtracts = data;
    		dialog.hideLoading();
    	})
    	salaryService.getLastSalarySubtractTotal(year_,month_).then(function(data){
    		//console.log(data);
    		scope.subtractSalary = data;
    		dialog.hideLoading();
    	})
    	
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/lastsalary/:year_/:month_", {
		templateUrl: "tpl/lastsalary.html",
		reloadOnSearch: false,
		controller : "lastsalarycontroller"
	})
	.when("/historysalary", {
		templateUrl: "tpl/historysalary.html",
		reloadOnSearch: false,
		controller : "historysalarycontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

