app.controller("homeController",['$scope','warningService','dialog',function(scope,service,dialog){
	//消费异常时间选择
	scope.consumeDate = 1;
	//查询消费异常人数
	scope.$watch("consumeDate",function(newVal,oldVal){
		dialog.showLoading();
		service.queryConsumeNums(newVal).then(function(data){
			scope.consumeNums = data;
			dialog.hideLoading();
		});
	});
	
	//查询住宿异常学生人数
	dialog.showLoading();
	service.queryStayNums().then(function(data){
		scope.stayNum = data;
		dialog.hideLoading();
	});
	
	//生成学年学期列表
	var dt = new Date();
	var curMonth = dt.getMonth();
	var curYear = 0;
	if(curMonth < 8){
		curYear = dt.getFullYear();
	}else{
		curYear = dt.getFullYear() + 1;
	}
	var xnxq = [];
	for (var i = 0; i < 5; i++) {
		for (var j = 2; j >= 1; j--) {
			var it  = {
				xn : (curYear-1-i) +"-"+ (curYear-i),
				xq : '0'+j
			};
			xnxq.push(it);
		}
	}
	
	
	//学生学业异常学期下拉框
	scope.xnxqList = xnxq;
	if(curMonth < 8){
		scope.xnxqSelect = scope.xnxqList[1];
	}else{
		scope.xnxqSelect = scope.xnxqList[2];
	}
	scope.changeXnxqSelect = function(it){
		scope.xnxqSelect = it;
	}
	
	scope.$watch("xnxqSelect",function(newVal,oldVal){
		dialog.showLoading();
		service.queryStudyNums(scope.xnxqSelect).then(function(data){
			scope.xyycList = data;
			dialog.hideLoading();
		});
	})
}]);