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
		return codeService.getDeptTree("research:achievement:soft");
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
		
		//查询计算机著作权数
		service.querySoftNums(scope.condition).then(function(data){
			if(data.success){
				scope.softNums = data.result;
			}else{
				toast.error("请求计算机著作权数错误");
			}
		});
		//获取查询软件著作权条件的code
		service.querySoftCode().then(function(data){
			if(data.success){
				scope.querycode = data.result;
			}else{
				toast.error("查询条件code获取失败");
			}
		});
		//查询计算机著作权数变化趋势
		service.querySoftNumsChange(scope.condition).then(function(data){
			if(data.success){
				service.SoftNumsChangeChartOption(data.result,scope);
			}else{
				toast.error("请求计算机著作权数变化趋势错误");
			}
		});
		scope.formConfig = {
			title : "计算机著作权明细",
			show : false,
			url : "achievement/soft/querySoftDetail",
			exportUrl : 'achievement/soft/list/export', // 为空则不显示导出按钮
			heads : ['序号','软件名称','完成人单位','著作权人','完成人','版权类型','权利取得方式','开发完成日期','首次发表日期','软件著作等级日期'],
			fields : ['rn','soft_name','dept_name','owner','complete_man','copyright_','get_','complete_time','dispatch_time','regist_time'],
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
			scope.formConfig.title = "计算机著作权";
			scope.formConfig.params.name = '';
			scope.formConfig.params.flag = '';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryChange = function(params){
			scope.formConfig.title = params.name+"年计算机著作权";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'year';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryGet = function(params){
			scope.formConfig.title = params.name+"计算机著作权";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'get';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryDept = function(params){
			scope.formConfig.title = params.name+"计算机著作权";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'dept';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryAuthorDetail = function(param,author){
			scope.formConfig.title = author+"完成的计算机著作权";
			scope.formConfig.params.name = param;
			scope.formConfig.params.flag = 'author';
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		//查询计算机著作权取得方式
		service.querySoftGet(scope.condition).then(function(data){
			if(data.success){
				service.SoftGetChartOption(data.result,scope);
			}else{
				toast.error("请求计算机著作权取得方式错误");
			}
		});
		//查询计算机著作权获得人员单位分布
		service.querySoftPeopleDept(scope.condition).then(function(data){
			if(data.success){
				service.SoftPeopleDeptChartOption(data.result,scope);
			}else{
				toast.error("请求计算机著作权获得人员单位分布错误");
			}
		});
		//查询活跃软件开发者
		service.querySoftAuthor(scope.condition).then(function(data){
			if(data.success){
				scope.authors = data.result;
			}else{
				toast.error("请求活跃软件开发者错误");
			}
			scope.queryProjectList();
		});
	    //查询计算机著作权列表
		scope.page = {
			pagesize: 10,
			curpage: 1,
			sumcount: 0
		};
		scope.params = {
			param : '',
			copyright:'',
			getcode:''
		}
		scope.selectCopyright = function(val){
			scope.params.copyright = val;
			scope.queryProjectList();
		}
		scope.selectGet = function(val){
			scope.params.getcode = val;
			scope.queryProjectList();
		}
		scope.$watch("page.curpage",function(newval,oldval){
			if(newval != oldval){
				scope.queryProjectList();
			}
		})
		scope.showModal = false;
		scope.queryProjectList = function(){
			scope.showModal = true;
			service.querySoftList(scope.condition,scope.page,scope.params).then(function(data){
				if(data.success){
					scope.items = data.result.result;   
					scope.page.sumcount = data.result.sumcount;
					scope.page.curpage = data.result.curpage;
					scope.page.pagecount = data.result.pagecount;
				}else{
					toast.error("请求计算机著作权列表错误");
				}
				scope.showModal = false;
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

