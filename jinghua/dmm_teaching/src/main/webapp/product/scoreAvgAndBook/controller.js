
/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', function($scope, service, advancedService){

	service.getBzdm(function(data){
	$scope.chartCfg = data;
	});
}]);