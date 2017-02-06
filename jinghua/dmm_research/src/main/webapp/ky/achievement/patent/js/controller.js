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
		return codeService.getDeptTree("research:achievement:patent");
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
		
		scope.formConfig = {
			title : "专利明细",
			show : false,
			url : "achievement/patent/queryPatentDetail",
			exportUrl : 'achievement/patent/queryPatentDetail/export', // 为空则不显示导出按钮
			heads : ['序号','专利名称','发明人单位','专利权单位','发明人','专利类型','专利实施状态','受理日','授权日','专利号'],
			fields : ['rn','name_','dept_name','patent_dept','inventors','type_','state_','accept_time','accredit_time','patent_no'],
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
			scope.formConfig.title = "专利";
			scope.formConfig.params.name = '';
			scope.formConfig.params.flag = '';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryType = function(params){
			scope.formConfig.title = params.name+"专利";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'type';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryState = function(params){
			scope.formConfig.title = params.name+"专利";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'state';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryChange = function(params){
			scope.formConfig.title = params.name+"年专利";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'year';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryDept = function(params){
			scope.formConfig.title = params.name+"专利";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'dept';
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		//查询专利数
		service.queryPatentNums(scope.condition).then(function(data){
			if(data.success){
				scope.patentNums = data.result;
			}else{
				toast.error("请求专利数错误");
			}
		});
		//查询专利类型
		service.queryPatentType(scope.condition).then(function(data){
			if(data.success){
				service.PatentTypeChartOption(data.result,scope);
			}else{
				toast.error("请求专利类型错误");
			}
		});
		//查询专利实施状态
		service.queryPatentState(scope.condition).then(function(data){
			if(data.success){
				service.PatentStateChartOption(data.result,scope);
			}else{
				toast.error("请求专利实施状态错误");
			}
		});
		//查询专利贡献度高的单位
		service.queryPatentCon(scope.condition).then(function(data){
			if(data.success){
				scope.map = data.result;
			}else{
				toast.error("请求专利贡献度高的单位错误");
			}
		});
		//查询专利变化趋势
		scope.showModal1 = false;
		var param = $("input[name='selectOptions']:checked").val();
		service.queryPatentChange(scope.condition,param).then(function(data){
			if(data.success){
				service.PatentChangeSelectOption(data.result,scope);
			}else{
				toast.error("请求专利变化趋势错误");
			}
		});
		$("input[name='selectOptions']").click(function(){
			scope.showModal1 = true;
			param = $("input[name='selectOptions']:checked").val();
			service.queryPatentChange(scope.condition,param).then(function(data){
				if(data.success){
					service.PatentChangeSelectOption(data.result,scope);
				}else{
					toast.error("请求专利变化趋势错误");
				}
				scope.showModal1 = false;
			});
	    });
	    
	    //查询专利单位分布
	    scope.showModal2 = false;
		var param = $("input[name='selectDeptOptions']:checked").val();
		service.queryPatentDept(scope.condition,param).then(function(data){
			if(data.success){
				service.PatentDeptChartOption(data.result,scope);
			}else{
				toast.error("请求专利单位分布错误");
			}
		});
		$("input[name='selectDeptOptions']").click(function(){
			scope.showModal2 = true;
			param = $("input[name='selectDeptOptions']:checked").val();
			service.queryPatentDept(scope.condition,param).then(function(data){
				if(data.success){
					service.PatentDeptChartOption(data.result,scope);
				}else{
					toast.error("请求专利单位分布错误");
				}
				scope.showModal2 = false;
			});
	    });
	    
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

