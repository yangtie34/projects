var app=angular.module("app",["custom"]);
/**
 * “资源管理页面”controller
 */
app.controller("controller", [ "$scope","dialog",'mask','$timeout','http',function($scope,dialog,mask,timeout,http) {
	
	$scope.data = {
		name : 'zhangsan',
		age  : 16,
		data : {}
	}

	var data={};
	data.params=[8010308];
	data.service='stuScoreService?getTermScoreByDept';
	http.callService(data).success(function(reData){
		console.log(reData);
		$scope.data.obj = reData[0];
    });
	  
	/*var dtree=function(){
	   var data={};
		  data.params=[{}];
		  data.service='resourcesService?getResourceAngularTree';
		  http.callService(data).success(function(data){
			  scope.treeData=data;
			  scope.treeData.check=true;
	    }); 
   };*/
   
}]);