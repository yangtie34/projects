var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','service','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
	//定义页面查询条件
	scope.queryCondition = {
		definedYear : [],
		dept : {}
	};
	//定义查询呢条件选择结果
	scope.condition = {
		definedYear : {},
		dept : {}
	}
	
	//请求后台填充查询条件数据
    codeService.getDeptTree("research:project:compere").then(function(data){
        scope.queryCondition.dept = data;
        data.checked = true;
        return codeService.getSelfDefinedYearCode();
    }).then(function(data){
        scope.queryCondition.definedYear = data;
		//条件数据填充完毕，开始绑定刷新事件，查询页面内容
		scope.$watch("condition",function(){
			//条件执行完毕，开始查询页面数据
			scope.queryPageInfo();
		},true);
	});
	
	scope.queryPageInfo = function(){
		dialog.showLoading();
		var params = scope.condition;
		service.queryTotalNums(params).then(function(data){
			if(data.success)
				scope.totalNums = data.result;
			else toast.error("请求主持人总数错误！");
		}).then(function(){
			service.queryAuthorNumsByGender(params).then(function(data){
				if(data.success)
					scope.genderNums = data.result;
				else toast.error("请求主持人性别比例错误！");
			});

			service.queryAuthorNumsByType(params).then(function(data){
				if(data.success){
                    scope.typeNums = data.result;
                    scope.typeNumsTotal = 0;
                    for(var i in data.result){
                        scope.typeNumsTotal += parseInt(data.result[i].value);
                    }
                }
				else toast.error("请求按项目级别查看主持人比例错误！");
			});
		});

		service.queryAuthorNumsByEducation(params).then(function(data){
			if(data.success)
				scope.config1 = service.generatePieConfig("学历组成",data.result);
			else toast.error("请求主持人学历分布错误！");
		});
		
		service.queryAuthorNumsByAge(params).then(function(data){
			if(data.success)
				scope.config2 = service.generateDonutConfig("年龄分布",data.result);
			else toast.error("请求主持人年龄分布错误！");
		});

		service.queryAuthorNumsByZyjszwJb(params).then(function(data){
			if(data.success)
				scope.config3 = service.generateDonutConfig("职称级别组成",data.result);
			else toast.error("请求主持人职称级别组成错误！");
		});
		
		service.queryAuthorNumsByZyjszw(params).then(function(data){
			if(data.success)
				scope.config4 = service.generateDonutConfig("职称组成",data.result);
			else toast.error("请求主持人职称组成错误！");
		});
		
		service.queryAuthorNumsByRylb(params).then(function(data){
			if(data.success)
				scope.config5 = service.generateDonutConfig("人员类别组成",data.result);
			else toast.error("请求主持人人员类别组成错误！");
		});
		
		service.queryAuthorNumsByTeaSource(params).then(function(data){
			dialog.hideLoading();
			if(data.success)
				scope.config6 = service.generateDonutConfig("教职工来源组成",data.result);
			else toast.error("请求主持人教职工来源组成错误！");
		});
	}
	scope.allNumClick = function(){
		scope.formConfig.title = "科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id
		};
		scope.formConfig.show = true;
	}
	
	scope.genderClick = function(gender){
		scope.formConfig.title = gender + "科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				gender : gender
		};
		scope.formConfig.show = true;
	}
	
	scope.chart1click = function(params){
		scope.formConfig.title = "学历【" +params.name + "】科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				education : params.name
		};
		scope.formConfig.show = true;
	} 
	scope.chart2click = function(params){
		scope.formConfig.title = "年龄为【" +params.name + "】科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				age : params.name
		};
		scope.formConfig.show = true;
	} 
	scope.chart3click = function(params){
		scope.formConfig.title = "职称级别【" +params.name + "】科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				zcjb : params.name
		};
		scope.formConfig.show = true;
	} 
	scope.chart4click = function(params){
		scope.formConfig.title = "职称【" +params.name + "】科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				zc : params.name
		};
		scope.formConfig.show = true;
	} 
	scope.chart5click = function(params){
		scope.formConfig.title = "人员类别【" +params.name + "】科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				rylb : params.name
		};
		scope.formConfig.show = true;
	} 
	scope.chart6click = function(params){
		scope.formConfig.title = "职工来源【" +params.name + "】科研项目主持人列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				source : params.name
		};
		scope.formConfig.show = true;
	} 
	
	scope.formConfig = {
			title : "",
			show : false,
			url : "project/compere/list",
			exportUrl : 'project/compere/list/export', // 为空则不显示导出按钮
			heads : ['职工号','姓名','性别', '年龄','来源','学历','职称级别','职称','人员类别'],
			fields : ['tea_no','name_','gender', 'age','tea_source','education','zcjb','zc','lb'],
			params : {}
	}
}]);


app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "homeController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);