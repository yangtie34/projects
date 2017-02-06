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
		return codeService.getDeptTree("research:writing:work");
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
		//查询著作数
		service.queryWorkNums(scope.condition).then(function(data){
			if(data.success){
				scope.workNums = data.result;
			}else{
				toast.error("请求著作数错误");
			}
		});
		//查询著作作者承担角色
		service.queryAuthorRole(scope.condition).then(function(data){
			if(data.success){
				scope.zb = {code_:'01',name_ : '主编',nums:0};
				scope.fzb = {code_:'02',name:'副主编',nums:0};
				scope.cyry = {code_:'03',name_ : '参与人员',nums:0};
				for(var i=0;i<data.result.length;i++){
					if(data.result[i].name_ == '主编'){
						scope.zb = data.result[i];
					}
					if(data.result[i].name_ == '副主编'){
						scope.fzb = data.result[i];
					}
					if(data.result[i].name_ == '参与人员'){
						scope.cyry = data.result[i];
					}
				}
			}else{
				toast.error("请求作者承担角色错误");
			}
		});
		
		scope.formConfig = {
			title : "著作列表",
			show : false,
			url : "writing/work/clickDetail",
			exportUrl : 'writing/work/clickDetail/export', // 为空则不显示导出按钮
			heads : ['序号','作者单位','作者姓名','著作名称','出版单位','出版字数','出版时间'],
			fields : ['rn','dept_name','authors','title_','press_name','number_','press_time'],
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
			scope.formConfig.title = "著作列表";
			scope.formConfig.params.name = '';
			scope.formConfig.params.flag = '';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryRoleDetail = function(code,name){
			scope.formConfig.title = "作为"+name+"著作列表";
			scope.formConfig.params.name = code;
			scope.formConfig.params.flag = 'role';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickChange = function(param){
			scope.formConfig.title = param.name+"年著作列表";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'year';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickDept = function(param){
			scope.formConfig.title = param.name+"著作列表";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'dept';
			scope.formConfig.show = !scope.formConfig.show;
		}
		//查询著作变化趋势
		service.queryWorkNumsChange(scope.condition).then(function(data){
			if(data.success){
				service.WorkChangeChartOption(data.result,scope);
			}else{
				toast.error("请求著作变化趋势错误");
			}
		});
		//查询著作单位分布
		service.queryWorkDept(scope.condition).then(function(data){
			if(data.success){
				service.WorkDeptChartOption(data.result,scope);
			}else{
				toast.error("请求著作单位分布错误");
			}
			scope.queryProjectList();
		});
	    //查询著作列表
		scope.page = {
			pagesize: 10,
			curpage: 1,
			sumcount: 0
		};
		scope.params = '';
		scope.$watch("page.curpage",function(newval,oldval){
			if(newval != oldval){
				scope.queryProjectList();
			}
		})
		scope.showModal2 = false;
		scope.queryProjectList = function(){
			scope.showModal2 = true;
			service.queryWorkList(scope.condition,scope.page,scope.params).then(function(data){
				if(data.success){
					scope.items = data.result.result;   
					scope.page.sumcount = data.result.sumcount;
					scope.page.pagecount = data.result.pagecount;
				}else{
					toast.error("请求著作列表错误");
				}
				scope.showModal2 = false;
			});
		};
		scope.searchWrok = function(sparam){
			scope.params = sparam;
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

