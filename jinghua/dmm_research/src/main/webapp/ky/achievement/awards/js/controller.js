var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','service','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
	//定义页面查询条件
	scope.queryCondition = {
		subject : [],
		definedYear : [],
		dept : {}
	};
	//定义查询呢条件选择结果
	scope.condition = {
		subject : {},
		definedYear : {},
		dept : {}
	}
	
	//请求后台填充查询条件数据
	dialog.showLoading();
	codeService.getSelfDefinedYearCode().then(function(data){
		scope.queryCondition.definedYear = data;
		return codeService.getDeptTree("research:achievement:awards");
	}).then(function(data){
		scope.queryCondition.dept = data;
		data.checked = true;
		return codeService.getCodeSubject();
	}).then(function(data){
		scope.queryCondition.subject = data;
		scope.condition.subject = data[0];
		//条件数据填充完毕，开始绑定刷新事件，查询页面内容
		scope.$watch("condition",function(){
			//条件执行完毕，开始查询页面数据
			scope.queryPageInfo();
		},true);
	});
	
	//查询页面内容方法
	scope.queryPageInfo = function(){
		dialog.showLoading();
		
		//查询获奖成果数
		service.queryAwardsAchievementNums(scope.condition).then(function(data){
			if(data.success){
				scope.awardsAchievementNums = data.result;
			}else{
				toast.error("请求获奖成果数错误");
			}
		});
		//获取查询获奖成果条件的code
		service.queryAwardCode().then(function(data){
			if(data.success){
				scope.querycode = data.result;
			}else{
				toast.error("成果查询条件code获取失败");
			}
		});
		//查询获奖成果变化趋势
		service.queryAwardsNumsChange(scope.condition).then(function(data){
			if(data.success){
				service.AwardsChangeChartOption(data.result,scope);
			}else{
				toast.error("请求获奖成果变化趋势错误");
			}
		});
		scope.formConfig = {
			title : "获奖成果明细",
			show : false,
			url : "achievement/awards/queryDetailList",
			exportUrl : 'achievement/awards/list/export', // 为空则不显示导出按钮
			heads : ['序号','获奖人单位','获奖人','成果名称','获奖名称','获奖级别','获奖类别','获奖等级','授奖单位','授奖时间'],
			fields : ['rn','dept_name','prizewinner','name_','award_name','awards_level','awards_category','awards_rank','award_dept','award_time'],
			params : {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				xkmlid: scope.condition.subject.id,
				name : "",
				flag : ""
			}
		}
		scope.queryDetail = function(){
			scope.formConfig.title = "获奖成果";
			scope.formConfig.params.name = '';
			scope.formConfig.params.flag = '';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryChange = function(param){
			scope.formConfig.title = param.name+"年获奖成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'year';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryLevel = function(param){
			scope.formConfig.title = param.name+"获奖成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'level';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryDept = function(param){
			scope.formConfig.title = param.name+"获奖成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'dept';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryType = function(param){
			scope.formConfig.title = param.name+"获奖成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'type';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryPeople = function(param){
			scope.formConfig.title = param.name+"获奖成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'people';
			scope.formConfig.show = !scope.formConfig.show;
		}
		//查询获奖级别
		service.queryAwardsLevel(scope.condition).then(function(data){
			if(data.success){
				service.AwardsLevelChartOption(data.result,scope);
			}else{
				toast.error("请求获奖级别错误");
			}
		});
		//查询获奖人单位分布
		service.queryAwardsPeopleDept(scope.condition).then(function(data){
			if(data.success){
				service.AwardsPeopleDeptChartOption(data.result,scope);
			}else{
				toast.error("请求获奖人单位分布错误");
			}
		});
		//查询获奖类别分布
		service.queryAwardsType(scope.condition).then(function(data){
			if(data.success){
				service.AwardsTypeChartOption(data.result,scope);
			}else{
				toast.error("请求获奖类别分布错误");
			}
		});
		//查询完成人承担角色
		scope.showModal1 = false;
		var authorRole = $("input[name='inlineRadioOptions']:checked").val();
		service.queryAwardsPeopleRole(scope.condition,authorRole).then(function(data){
			if(data.success){
				service.AwardsPeopleRoleOption(data.result,scope);
			}else{
				toast.error("请求完成人承担角色错误");
			}
			scope.queryProjectList();
		});
		$(":radio").click(function(){
			scope.showModal1 = true;
			authorRole = $("input[name='inlineRadioOptions']:checked").val();
			service.queryAwardsPeopleRole(scope.condition,authorRole).then(function(data){
				if(data.success){
					service.AwardsPeopleRoleOption(data.result,scope);
				}else{
					toast.error("请求完成人承担角色错误");
				}
				scope.showModal1 = false;
		    });
	    });
	    //查询获奖成果列表
		scope.page = {
			pagesize: 10,
			curpage: 1,
			sumcount: 0
		};
		scope.params = {
			param : '',
			level : ''
		}
		scope.selectLevel = function(val){
			scope.params.level = val;
			scope.queryProjectList();
		}
		scope.$watch("page.curpage",function(newval,oldval){
			if(newval != oldval){
				scope.queryProjectList();
			}
		})
		scope.showModal2 = false;
		scope.queryProjectList = function(){
			scope.showModal2 = true;
			service.queryAwardsList(scope.condition,scope.page,scope.params).then(function(data){
				if(data.success){
					scope.items = data.result.result;   
					scope.page.sumcount = data.result.sumcount;
					scope.page.pagecount = data.result.pagecount;
				}else{
					toast.error("请求获奖成果列表错误");
				}
				scope.showModal2 = false;
			});
		};
		scope.searchAchievement = function(sparam){
			scope.params.param = sparam;
			scope.page.curpage = 1;
			scope.page.sumcount = 0;
			scope.queryProjectList();
		};
		dialog.hideLoading();
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

