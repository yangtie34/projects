var app = angular.module('app', ['ngRoute','system']);
app.controller("tabController",['$scope','service','dialog',function(scope,service,dialog){
	//定义tab切换事件
	scope.$watch("condition",function(){
		scope.$broadcast("pageConditionChange", scope.condition);
	});
	
	scope.refreshPageData = function(){
		dialog.showLoading();
		service.refreshPageData().then(function(data){
			if(data.success) scope.$broadcast("pageConditionChange", scope.condition);
			else toastr.error("数据刷新出错！");
		});
	}
	
	scope.condition = {
		year : (new Date()).getFullYear(),
		zgh : '',
		xkid :''
	};
	
	scope.$on("pageTabChange",function(e,msg){
		scope.currentTab = msg;
	});
	
}]);
app.controller("ryzcController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	scope.$emit("pageTabChange",1);
	scope.$on("pageConditionChange",function(){
		scope.pageRefresh();
	});
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryRyzc(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.ryzcsj = data.result;
			}else{
				toastr.error("数据请求错误！");
			}
		});
	}
	scope.pageRefresh();
}]);
app.controller("zbjzController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	scope.$emit("pageTabChange",2);
	scope.$on("pageConditionChange",function(){
		scope.pageRefresh();
	});
	
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryZbjz(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.zbjzsj = data.result;
			}else{
				toastr.error("数据请求错误！");
			}
		});
	}
	scope.pageRefresh();
}]);
app.controller("fxzbController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	scope.$emit("pageTabChange",3);
	scope.$on("pageConditionChange",function(){
		scope.pageRefresh();
	});
	service.queryZblist().then(function(data){
		data.result[0].checked = true;
		scope.zblist = data.result;
	});
	
	scope.zbChange = function(zb){
		scope.condition.zbid = zb.id
		scope.pageRefresh();
	}
	
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryGxkzbjz(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.fxzbsj = data.result;
			}else{
				toastr.error("数据请求错误！");
			}
		});
	}
}]);
app.controller("zbController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	scope.$emit("pageTabChange",4);
	scope.$on("pageConditionChange",function(){
		scope.pageRefresh();
	});
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryYwczb(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.zbsj = data.result;
			}else{
				toastr.error("数据请求错误！");
			}
		});
	}
	scope.pageRefresh();
}]);
app.controller("zbwclController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	scope.$emit("pageTabChange",5);
	scope.$on("pageConditionChange",function(){
		scope.pageRefresh();
	});
	
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryZbwcl(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.zbwclsj = data.result;
			}else{
				toastr.error("数据请求错误！");
			}
		});
	}
	scope.pageRefresh();
}]);
app.controller("grzbwclController",['$scope','service','dialog','toastrService',function(scope,service,dialog,toast){
	//带头人个人指标完成率
	scope.$emit("pageTabChange",6);
	scope.$on("pageConditionChange",function(){
		scope.pageRefresh();
	});
	
	//获取指标列表填入第一个下拉按钮
	service.queryXkmc().then(function(data){
		data.result[0].checked = true;
		scope.condition.xkid = data.result[0].id;
		scope.xklist = data.result;
		service.queryGrzgh(scope.condition.xkid).then(function(data){
			data.result[0].checked = true;
			scope.condition.zgh = data.result[0].tea_no;
			scope.dtrlist = data.result;
		});
	});
	
	//选择学科
	scope.xkChange = function(xk){
		scope.condition.xkid = xk.id;
		//获取带头人列表填入第二个下拉按钮
		service.queryGrzgh(scope.condition.xkid).then(function(data){
			data.result[0].checked = true;
			scope.dtrlist = data.result;
		})
	}
	
	//选择带头人
	scope.dtrChange = function(tea){
		scope.condition.zgh = tea.tea_no;
		scope.pageRefresh();
	}
	
	//页面刷新加载
	scope.pageRefresh = function(){
		dialog.showLoading();
		service.queryGrzbwcl(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.list = data.result;
			}else{
				toastr.error("数据请求错误！");
			}
		})
	}
}]);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/ryzc", {
		templateUrl: "tpl/ryzc.html",
		reloadOnSearch: false,
		controller : "ryzcController"
	})
	.when("/zbjz", {
		templateUrl: "tpl/zbjz.html",
		reloadOnSearch: false,
		controller : "zbjzController"
	})
	.when("/fxzb", {
		templateUrl: "tpl/fxzb.html",
		reloadOnSearch: false,
		controller : "fxzbController"
	})
	.when("/zb", {
		templateUrl: "tpl/zb.html",
		reloadOnSearch: false,
		controller : "zbController"
	})
	.when("/zbwcl", {
		templateUrl: "tpl/zbwcl.html",
		reloadOnSearch: false,
		controller : "zbwclController"
	})
	.when("/grzbwcl", {
		templateUrl: "tpl/grzbwcl.html",
		reloadOnSearch: false,
		controller : "grzbwclController"
	})
	.otherwise({
		redirectTo : "/ryzc"
	});
}]);