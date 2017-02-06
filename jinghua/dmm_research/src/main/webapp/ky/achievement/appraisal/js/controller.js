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
		return codeService.getDeptTree("research:achievement:appraisal");
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
		//查询鉴定成果数
		service.queryAppraisalAchievementNums(scope.condition).then(function(data){
			if(data.success){
				scope.appraisalAchievementNums = data.result;
			}else{
				toast.error("请求鉴定成果数错误");
			}
		});
		//获取查询成果条件的code
		service.queryAchievementCode().then(function(data){
			if(data.success){
				scope.querycode = data.result;
			}else{
				toast.error("成果查询条件code获取失败");
			}
		});
		scope.formConfig = {
			title : "鉴定成果明细",
			show : false,
			url : "achievement/appraisal/changeClick",
			exportUrl : 'achievement/appraisal/list/export', // 为空则不显示导出按钮
			heads : ['序号','部门','完成人','成果名称','鉴定单位','鉴定水平','鉴定形式','鉴定级别','鉴定时间'],
			fields : ['rn','dept_name','authors','name_','appraisal_dept','level_','mode_','grade_','time_'],
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
			scope.formConfig.title = "鉴定成果明细";
			scope.formConfig.params.name = '';
			scope.formConfig.params.flag = '';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickChange = function(param){
			scope.formConfig.title = param.name+"年鉴定成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'year';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickGrade = function(param){
			scope.formConfig.title = param.name+"鉴定成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'grade';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickLevel = function(param){
			scope.formConfig.title = param.name+"鉴定成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'level';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickMode = function(param){
			scope.formConfig.title = param.name+"鉴定成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'mode';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickPeople = function(param){
			scope.formConfig.title = param.name+"鉴定成果";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'people';
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		//查询鉴定成果变化趋势
		service.queryAppraisalNumsChange(scope.condition).then(function(data){
			if(data.success){
				service.AppraisalChangeChartOption(data.result,scope);
			}else{
				toast.error("请求鉴定成果变化趋势错误");
			}
		});
		//查询鉴定成果级别
		service.queryAppraisalGrade(scope.condition).then(function(data){
			if(data.success){
				service.AppraisalGradeOption(data.result,scope);
			}else{
				toast.error("请求鉴定级别错误");
			}
		});
		//查询鉴定成果水平
		service.queryAppraisalLevel(scope.condition).then(function(data){
			if(data.success){
				service.AppraisalLevelOption(data.result,scope);
			}else{
				toast.error("请求鉴定成果水平错误");
			}
		});
		//查询鉴定形式
		service.queryAppraisalMode(scope.condition).then(function(data){
			if(data.success){
				service.AppraisalModeOption(data.result,scope);
			}else{
				toast.error("请求鉴定形式错误");
			}
		});
		//查询完成人承担角色
		scope.showModal1 = false;
		var authorRole = $("input[name='inlineRadioOptions']:checked").val();
		service.queryAppraisalPeopleRole(scope.condition,authorRole).then(function(data){
			if(data.success){
				service.AppraisalPeopleRoleOption(data.result,scope);
			}else{
				toast.error("请求完成人承担角色错误");
			}
			scope.queryProjectList();
		});
		$(":radio").click(function(){
			scope.showModal1 = true;
			authorRole = $("input[name='inlineRadioOptions']:checked").val();
			service.queryAppraisalPeopleRole(scope.condition,authorRole).then(function(data){
				if(data.success){
					service.AppraisalPeopleRoleOption(data.result,scope);
				}else{
					toast.error("请求完成人承担角色错误");
				}
				scope.showModal1 = false;
		    });
	    });
	    
	    //查询鉴定成果列表
		scope.page = {
			pagesize: 10,
			curpage: 1,
			sumcount: 0
		};
		scope.params = {
			grade : '',
			level : '',
			mode : '',
			param : ''
		}
		scope.selectGrade = function(val){
			scope.params.grade = val;
			scope.queryProjectList();
		}
		scope.selectLevel = function(val){
			scope.params.level = val;
			scope.queryProjectList();
		}
		scope.selectMode = function(val){
			scope.params.mode = val;
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
			service.queryAppraisalList(scope.condition,scope.page,scope.params).then(function(data){
				if(data.success){
					scope.items = data.result.result;   
					scope.page.sumcount = data.result.sumcount;
					scope.page.pagecount = data.result.pagecount;
				}else{
					toast.error("请求鉴定成果列表错误");
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
	};
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
app.filter('paging', function() {
	  return function (items, index, pageSize) {
	    if (!items)
	      return [];
	    var offset = (index - 1) * pageSize;
	    return items.slice(offset, parseInt(offset) + parseInt(pageSize));
	 };
});

