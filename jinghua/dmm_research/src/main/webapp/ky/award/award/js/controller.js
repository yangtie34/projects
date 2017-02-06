var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','service','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
	//定义页面查询条件
	scope.queryCondition = {
		subject : [],
		year : [],
		dept : {}
	};
	//定义查询条件选择结果
	scope.condition = {
		subject : {},
		year : {},
		dept : {}
	}
	//请求后台填充查询条件数据
	dialog.showLoading();
	codeService.getYears().then(function(data){
		data[0].checked = true;
		scope.queryCondition.year = data;
		scope.condition.year = data[0].value;
		return codeService.getTeachDeptTree("research:kyjl:award");
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
	scope.change =function(data){
		 scope.condition.year = data.value;
	 }
	//查询页面内容方法
	scope.queryPageInfo = function(){
		dialog.showLoading();
		
		//查询奖励金额
		service.queryFund(scope.condition).then(function(data){
			if(data.success){
				scope.fund = data.result;
			}else{
				toast.error("请求奖励金额错误");
			}
		});
		//查询各奖项
		service.queryAwardPie(scope.condition).then(function(data){
			if(data.success){
				var legendData = [];
				for(var i=0;i<data.result.length;i++){
					legendData.push(data.result[i].name);
				}
				scope.awardpie = {
					tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c}万元 ({d}%)"
				    },
				    legend: {
				        x : 'center',
				        data:legendData
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    series : [
				        {
				            name:'奖励项',
				            type:'pie',
				            startAngle: 180,
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: data.result
				        }
				    ]
				}
			}else{
				toast.error("请求各奖项金额分布错误");
			}
		});
	    //查询获得奖励人员单位分布
		service.queryDept(scope.condition).then(function(data){
			if(data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].name_);
					numdata.push(data.result[i].nums);
					funddata.push(data.result[i].fund);
				}
				scope.deptchart = {
					tooltip : {
				        trigger: 'axis',
				        formatter: "{b} <br/>{a0} : {c0} <br/> {a1} : {c1}万元"
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    dataZoom : {
				        show : true,
				        realtime : true,
				        start : 20,
				        end : 80
				    },
				    legend: {
				        data:['获奖人数','获奖总金额']
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : xdata
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name : '人数',
				            axisLabel : {
				                formatter: '{value} '
				            }
				        },
				        {
				            type : 'value',
				            name : '金额(万元)',
				            axisLabel : {
				                formatter: '{value} '
				            }
				        }
				    ],
				    series : [
				        {
				            name:'获奖人数',
				            type:'bar',
				            data:numdata
				        },
				        {
				            name:'获奖总金额',
				            type:'line',
				            yAxisIndex: 1,
				            data:funddata
				        }
				    ]
				}
			}else{
				toast.error("请求获奖人员单位分布错误");
			}
			scope.queryPeopleList();
		});
		scope.page = {
			pagesize: 10,
			curpage: 1,
			sumcount: 0
		};
		scope.params = {
			param : ''
		};
		scope.$watch("page.curpage",function(newval,oldval){
			if(newval != oldval){
				scope.queryPeopleList();
			}
		})
		scope.showModal2 = false;
		scope.queryPeopleList = function(){
			scope.showModal2 = true;
			service.queryAwardPeople(scope.page,scope.condition,scope.params).then(function(data){
				if(data.success){
					scope.items = data.result.result;   
					scope.page.sumcount = data.result.sumcount;
					scope.page.pagecount = data.result.pagecount;
				}else{
					toast.error("请求获奖人员排名错误");
				}
				scope.showModal2 = false;
			});
		};
		scope.searchPeople = function(sparam){
			scope.params.param = sparam;
			scope.page.curpage = 1;
			scope.page.sumcount = 0;
			scope.queryPeopleList();
		};
		scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/award/detail",
			exportUrl : 'kyjl/award/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid : scope.condition.subject.id,
				zzjgid : scope.condition.dept.id,
				paramName : '',
				paramValue : ''
			}
		}
		scope.queryDetail = function(){
			scope.formConfig.title = "科研奖励明细";
			scope.formConfig.params.paramName = '';
			scope.formConfig.params.paramValue = '';
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryPie = function(param){
			scope.formConfig.title = param.name+"奖励明细";
			scope.formConfig.params.paramName = 'pie';
			scope.formConfig.params.paramValue = param.name;
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.querychart = function(param){
			scope.formConfig.title = param.name+"奖励明细";
			scope.formConfig.params.paramName = 'dept';
			scope.formConfig.params.paramValue = param.name;
			scope.formConfig.show = !scope.formConfig.show;
		}
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
app.filter('paging', function() {
	  return function (items, index, pageSize) {
	    if (!items)
	      return [];
	    var offset = (index - 1) * pageSize;
	    return items.slice(offset, parseInt(offset) + parseInt(pageSize));
	 };
});

