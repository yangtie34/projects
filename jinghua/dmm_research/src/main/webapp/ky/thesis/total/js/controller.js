var app = angular.module('app', ['system']);
app.controller("controller",['$scope','service','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
	//定义页面查询条件
	scope.queryCondition = {
		subject : [],
		definedYear : [],
		deptTeah : {}
	};
	//定义查询呢条件选择结果
	scope.condition = {
		subject : {},
		definedYear : {},
		deptTeah : {}
	}
	
	//请求后台填充查询条件数据
	dialog.showLoading();
	codeService.getSelfDefinedYearCode().then(function(data){
		scope.queryCondition.definedYear = data;
		return codeService.getDeptTree("research:thesis:total");
	}).then(function(data){
		scope.queryCondition.deptTeah = data;
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
		
		//查询论文总数
		service.queryThesisTotalNums(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.thesisNums = data.result;
			}else{
				toast.error("请求论文总数错误");
			}
		});
		//
		scope.formConfig = {
				title : "下钻明细",
				show : false,
				url : "thesis/publish/list",
				exportUrl : 'thesis/publish/list/export', // 为空则不显示导出按钮
				heads : ['序号','论文名称','论文作者','学科门类','发文年份','论文出处','期刊类别','出版单位'],
				fields : ['rownum','lwmc','lwzz','xkml','fwnx','zzjg','qklb','cbdw'],
				params : {
					startYear : scope.condition.definedYear.start,
					endYear : scope.condition.definedYear.end,
					zzjgid : scope.condition.deptTeah.id,
					xkmlid: scope.condition.subject.id,
					name : '',
					flag : ''
				}
		}
		scope.queryDetials = function(){
			scope.formConfig.title = "发表各类论文总数";
			scope.formConfig.params.name = '';
			scope.formConfig.params.flag = 'sorts';
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		scope.clickyear=function(param){
			scope.formConfig.title = param.name + "年论文发表量";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'year';
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		scope.clickdept = function(param){
			scope.formConfig.title = "各部门论文发表量";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'dept';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.clickgywlw = function(param){
			scope.formConfig.title = "各引文索引库论文发表量  " + param.name;
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = 'gywlw';
			scope.formConfig.show = !scope.formConfig.show;
		}
		//以我校为第一单位发表
		service.queryFirstAuthThesisNums(scope.condition).then(function(data){
			if(data.success){
				scope.firstDept = data.result;
			}else{
				toast.error("请求以我校为第一单位发表数量错误");
			}
		});
		//判断当前选择组织机构类型
		if(scope.condition.deptTeah.pid == '-1'){
			scope.showYearChangeOfAll = true;
			//选择是学校
			scope.mainchartTitle = scope.condition.deptTeah.mc+"各部门论文发表量和占比";
			service.queryThesisNumsAndPersByDept(scope.condition).then(function(data){
				if(data.success){
					service.generateMiddleChartOption(data.result,scope);
				}else{
					toast.error("请求各单位论文发表量和占比错误");
				}
			});
			service.queryThesisNumsAndPersByYear(scope.condition).then(function(data){
				if(data.success){
					service.generateYearChangeOfAllConfig(data.result,scope);
				}else{
					toast.error("请求论文发表量年度变化错误");
				}
			});
		}else{
			scope.showYearChangeOfAll = false;
			//选择是非学校
			scope.mainchartTitle = scope.condition.deptTeah.mc+"论文发表量和占比年度变化";
			service.queryThesisNumsAndPersByYear(scope.condition).then(function(data){
				if(data.success){
					service.generateMiddleChartOption(data.result,scope);
				}else{
					toast.error("请求各单位论文发表量和占比错误");
				}
			});
		}
		//各期刊类别发表量和占比
		service.queryYwsykLwfbl(scope.condition).then(function(data){
			if(data.success){
				scope.vm = {
					size: 10,
				    index: 1,
				    items : data.result
				}
				service.generateBottomPieChart(data.result,scope);
			}else{
				toast.error("各期刊类别发表量和占比");
			}
		});
	};
}]);
app.filter('paging', function() {
	  return function (items, index, pageSize) {
	    if (!items)
	      return [];
	    var offset = (index - 1) * pageSize;
	    return items.slice(offset, parseInt(offset) + parseInt(pageSize));
	 };
});