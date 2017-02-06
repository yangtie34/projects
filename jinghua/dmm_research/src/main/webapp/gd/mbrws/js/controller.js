var app = angular.module('app', ['ngRoute','system']);
var firstOpen = true;
app.controller("tabController",['$scope','service','dialog','sysCodeService',function(scope,service,dialog,codeService){
	//定义页面查询条件
	scope.queryCondition = {
		khjhlist : [],
		dept : {}
	};
	//条件结果
	scope.condition = {
		khjh : {},
		khdw : {}
	};
	scope.changeKhjh = function(data){
		scope.condition.khjh = data;
	}
	//请求后台填充查询条件数据
    codeService.getTeachDeptTree("research:mbrws:index").then(function(data){
        scope.queryCondition.dept = data;
        data.checked = true;
        //查询考核计划
        return service.queryKhjhList();
    }).then(function(data){
    	if(data.result.length > 0){
    		data.result[0].checked = true;
    		scope.condition.khjh = data.result[0];
    	}
        scope.queryCondition.khjhlist = data.result;
		//条件数据填充完毕，开始绑定刷新事件，查询页面内容
		scope.$watch("condition",function(newval){
			//条件执行完毕，开始查询页面数据
			scope.$broadcast("pageConditionChange", scope.condition);
		},true);
	});
	
    //查询考核主题
    service.queryKhztList().then(function(data){
    	scope.khztlist = data.result;
    });
    
	scope.$on("pageTabChange",function(e,msg){
		scope.currentTab = msg;
	});
}]);
app.controller("zypmController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	scope.$emit("pageTabChange",0);
	scope.$on("pageConditionChange",function(){
		if(firstOpen)
			firstOpen = false;
		scope.pageRefresh();
	});
	scope.pageRefresh = function(){
		dialog.showLoading();
		var kssj = Number(scope.condition.khjh.ksnf);
		var jssj = Number(scope.condition.khjh.jsnf);
		scope.khnflist = [];
		for(var year = kssj;year <= jssj; year ++){
			scope.khnflist.push(year);
		}
		service.queryXkpm(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.xkpmlist = data.result;
			}else{
				toast.error("后台请求错误");
			}
		});
	}
	scope.pageRefresh();
}]);

app.controller("otherController",['$scope','service','dialog','toastrService','$routeParams',function(scope,service,dialog,toast,routeParams){
	scope.$emit("pageTabChange",routeParams.khztid);
	scope.khztid = routeParams.khztid;
	scope.$on("pageConditionChange",function(){
		if(firstOpen){
			firstOpen = false;
		}else{
			scope.pageRefresh();
		}
	});
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryKhzt(angular.extend({khztid : scope.khztid},scope.condition)).then(function(data){
			dialog.hideLoading();
			if(data.success){
				if(data.result.length == 0){
					scope.khxmlist = [];
					scope.$apply();
					toastr.warning("该单位没有目标任务，请选择其他院系查看目标任务结果！");
				}else {
					scope.khxmlist = data.result;
					for ( var i in scope.khxmlist) {
						service.packChartConfigOfKhxm(scope.khxmlist[i]);
					}
				}
			}else{
				toast.error("后台请求错误");
			}
		});
	}
	scope.pageRefresh();
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider.when("/0", {
		templateUrl: "tpl/zypm.html",
		controller : "zypmController"
	}).when("/other/:khztid" , {
		templateUrl: "tpl/other.html",
		controller : "otherController"
	}).otherwise({
		redirectTo : "/other/1"
	}); 
}]);
