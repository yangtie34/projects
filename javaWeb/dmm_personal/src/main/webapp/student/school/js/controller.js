var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','schoolService','dialog',
    function(scope,schoolService,dialog){
    	dialog.showLoading();
    	schoolService.getSchool().then(function(data){
    		scope.school = data;
    		dialog.hideLoading();
    	});
    	schoolService.getPeopleCounts().then(function(data){
    		scope.stuCounts = data.stuCounts;
    		dialog.hideLoading();
    	});
        schoolService.getCounts().then(function(data){
        	scope.map = data;
        	dialog.hideLoading();
        }); 
        
}]);
