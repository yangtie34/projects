app.controller("consumeController",['$scope','$routeParams','warningService','dialog','locationService','toastrService',
                               function(scope,params,service,dialog,location,toastr){
	scope.params = params;
	dialog.showLoading();
	service.queryAvgDayConsume()
	.then(function(data){
		//学生消费均额
		scope.avgDayConsume = data;
		return service.queryXfycStudentsList(params.months,params.type);
	}).then(function(data1){
		scope.xfycXslist = data1;
		dialog.hideLoading();
		if(data1.length == 0){
			toastr.info("未找到对应的学生信息");
		}
	});
}])
.controller("stayController",['$scope','$routeParams','warningService','dialog','locationService',
                                 function(scope,params,service,dialog,location){
	scope.params  = params;
	//宿舍开门时间
	scope.dormOpenTime = {
		open : "06:00",
		lock : "23:30"
	};
	
	if(params.type == 'late'){
		//查询晚归名单
		dialog.showLoading();
		service.queryLateStudentsList().then(function(data){
			scope.lateStudentList = data;
			dialog.hideLoading();
		});
	}else{
		//查询不在校名单
		dialog.showLoading();
		service.queryOutStudentList().then(function(data){
			scope.outStudentList = data
			dialog.hideLoading();
		});
	}
}])
.controller("studyController",['$scope','$routeParams','warningService','dialog','locationService',
                              function(scope,params,service,dialog,location){
	scope.params = params;
	
	dialog.showLoading();
	service.queryCourseFailStudents(params.xn,params.xq,params.classId).then(function(data){
		scope.courseFailStudents = data;
		dialog.hideLoading();
	});
	
}]);