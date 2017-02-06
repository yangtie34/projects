var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','homeService','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
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
	dialog.showLoading();
    codeService.getDeptTree("research:project:fund").then(function(data){
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
	//查询页面内容方法
	scope.queryPageInfo = function(){
		dialog.showLoading();
		var params = scope.condition;
		
		//下钻
		scope.formConfig = {
			title : "项目列表",
			show : false,
			url : "project/fund/queryProjectDetail",
			exportUrl : 'project/fund/queryProjectDetail/export', // 为空则不显示导出按钮
			heads : ['序号','项目名称','主持人姓名','承担单位','项目类别','起止时间','经费（万元）'],
			fields : ['rn','name_','compere','dept_name','level_','time_','fund'],
			params : {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				name : "",
				flag : ""
			}
		}
		scope.queryDept = function(params){
			scope.formConfig.title = params.name+"项目列表";
			scope.formConfig.params.name = params.name;
			scope.formConfig.params.flag = 'dept';
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		//科研项目经费总额
		service.queryProjectFundTotal(params).then(function(data){
			dialog.hideLoading();
			if(data.success)
				scope.totalNums = data.result;
			else toast.error("科研项目经费总额！");
		});

		//项目金额投入变化趋势
		service.queryFundTotalByYears(params).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.config1 = {
				    title : {
				        text: '历年科研经费变化趋势',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : chartData.xAxisData
				        }
				    ],
				    yAxis : [
				        {
				        	name : '科研经费总额',
				            axisLabel : {
				                formatter: '{value} 万元'
				            }
				        }
				    ],
				    series : [
				        {
				            name:'科研经费总额',
				            type:'line',
				            smooth : true,
				            data: chartData.seriesData,
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            } 
				        } 
				    ]
				};
			}else toast.error("项目金额投入变化趋势！");
		});

		//各单位各级别项目投入总额
		service.queryFundTotalByDeptAndProjectLevel(params).then(function(data){
			if(data.success){
				var chartData = service.generateColumnChartData(data.result);
				scope.config2 = {
				    title : {
				        text: '各单位科研经费总额对比',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data: chartData.legendData,
						padding : [30,0],
						left : "center"
				    },
				    dataZoom : {
        		        show : true,
        		        realtime : true,
        		        start : 0,
        		        end : 20
        		    },
				    grid : {
				    	top : 80,
				    	bottom :80,
	                	 left: '1%',
	                     right: '1%',
	                     containLabel: true
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : chartData.xAxisData,
				            axisLabel : {
    			            	interval : 0,
    			            	rotate : (chartData.xAxisData.length > 5 ? 15 : 0)
    			            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name: '项目经费总额',
				            axisLabel : {
				                formatter: '{value} 万元'
				            },
    			            min: 0
				        }
				    ],
				    series : chartData.series
				};
			}else toast.error("各单位各级别项目投入总额！");
		});

		//各级别项目投入总额
		service.queryFundTotalByProjectLevel(params).then(function(data){
			if(data.success){
				scope.config3 = service.generatePieConfig("横、纵向项目经费组成",data.result);
			}else toast.error("各级别项目投入总额！");
		});

		//各单位项目投入平均额
		service.queryFundAvgByDept(params).then(function(data){
			if(data.success){
				var xAxisData = [],
					countData = [],
					totalData = [],
					avgData = [];
				for(var i in data.result){
					var it = data.result[i];
					xAxisData.push(it.name);
					countData.push(it.nums);
					totalData.push(it.total);
					avgData.push(it.avgval);
				}
				
				scope.config4 = {
				    title : {
				        text: '各单位科研经费总额对比',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data: ['项目总数','项目经费总额(万元)','项目经费均额(万元)'],
						padding : [30,0],
						left : "center"
				    },
				    dataZoom : {
        		        show : true,
        		        realtime : true,
        		        start : 0,
        		        end : 50
        		    },
				    grid : {
				    	top : 80,
				    	bottom :80,
	                	 left: '1%',
	                     right: '1%',
	                     containLabel: true
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : xAxisData,
				            axisLabel : {
    			            	interval : 0,
    			            	rotate :15
    			            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name: '项目总数',
				            axisLabel : {
				                formatter: '{value} 个'
				            },
    			            min: 0
				        },
				        {
				            type : 'value',
				            name: '项目经费',
				            axisLabel : {
				                formatter: '{value} 万元'
				            },
    			            min: 0
				        } 
				    ],
				    series : [{
				    	 type:'bar',
				    	 name : '项目总数',
				    	 data : countData
				    },{
				    	type:'line',
				    	 name : '项目经费总额(万元)',
				    	 yAxisIndex: 1,
				    	 smooth : true,
				    	 data : totalData
				    },{
				    	type:'line',
				    	smooth : true,
				    	name : '项目经费均额(万元)',
				    	yAxisIndex: 1,
				    	data : avgData
				    }]
				};
			}else toast.error("各单位项目投入平均额！");
		});
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