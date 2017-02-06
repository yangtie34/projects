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
	dialog.showLoading();
	codeService.getDeptTree("research:project:total").then(function(data){
		scope.queryCondition.dept = data;
		data.checked = true;
		return codeService.getSelfDefinedYearCode();
	}).then(function(data){
		scope.queryCondition.definedYear = data;
		//条件数据填充完毕，开始绑定刷新事件，查询页面内容
		scope.$watch("condition",function(newval,oldval){
			//条件执行完毕，开始查询页面数据
			scope.queryPageInfo();
		},true);
	});
	scope.queryPageInfo = function(){
		dialog.showLoading();
		var params = scope.condition;
		service.queryTotalNums(params).then(function(data){
			dialog.hideLoading();
			if(data.success)
				scope.totalNums = data.result;
			else toast.error("请求科研项目总数错误！");
		});
		service.queryProjectNumsOfYears(params).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.config1 = {
					    title : {
					        text: '科研项目增量变化趋势',
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
					        	name : '项目数',
					            axisLabel : {
					                formatter: '{value} 项'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'项目数',
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
			}else toast.error("查询每年科研项目数量错误！");
		});
		
		service.queryProjectNumsOfDeptAndLevel(params).then(function(data){
			if(data.success){
				var chartData = service.generateColumnChartData(data.result);
				scope.config2 = {
				    title : {
				        text: '科研项目校内分布直方图',
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
    			            	rotate :  15
    			            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name: '项目数',
    			            min: 0
				        }
				    ],
				    series : chartData.series
				};
			}else toast.error("请求各单位各级别项目总数错误！");
		});
		
		service.queryProjectNumsOfLevel(params).then(function(data){
			if(data.success){
				scope.config3 = service.generatePieConfig("科研项目类别组成",data.result);
			}else toast.error("请求项目级别分布错误！");
		});
		
		service.queryIssuedDeptNumsOfProjectLevel(params).then(function(data){
			if(data.success){
				for ( var i in data.result) {
					data.result[i].field = data.result[i].name;
					data.result[i].name = "下达部门数";
				}
				var chartData = service.generateColumnChartData(data.result);
				scope.config4 = {
				    title : {
				        text: '各级项目下达部门数统计',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : chartData.xAxisData,
				            axisLabel : {
    			            	interval : 0,
    			            	rotate :  15
    			            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name: '下达部门数',
    			            min: 0
				        }
				    ],
				    series : chartData.series
				};
			}else toast.error("请求各级项目下达部门分布错误！");
		});
		
		service.queryProjectRankNumsOfProjectLevel(params).then(function(data){
			if(data.success){
				var chartData = service.generateColumnChartData(data.result);
				for(var i in chartData.series){
					 chartData.series[i].stack = '总量';
				}
				scope.config5 = {
				    title : {
				        text: '各类别科研项目级别组成',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data: chartData.legendData,
						padding : [30,0]
				    },
				    grid: {
	                	 top : 80,
	                	 left: '1%',
	                     right: '1%',
	                     bottom: 40,
	                     containLabel: true
	                },
				    xAxis : [
				        {
				            type : 'category',
				            data : chartData.xAxisData,
				            axisLabel : {
    			            	interval : 0,
    			            	rotate : 15 
    			            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name: '项目数',
    			            min: 0
				        }
				    ],
				    series : chartData.series
				};
			} else toast.error("请求项目等级分布错误！");
		});
		//scope.queryProjectList();
	}
	
	/*scope.vm = {
			showModal : false,
			size: 10,
			index: 1,
			items : [],
			total : 0,
			queryString:"" 
	};
	scope.$watch("vm.index",function(newval,oldval){
		if(newval != oldval)
		scope.queryProjectList();
	})
	
	scope.queryProjectList = function(){
		scope.vm.showModal = true;
		service.queryProjectList(scope.vm, scope.condition).then(function(data){
			if(data.success){
				scope.vm.total = data.result.total;
				scope.vm.items = data.result.rows;
			}else toast.error("请求科研项目列表错误！");
			scope.vm.showModal = false;
		});
	};*/
	
	scope.formConfig = {
		title : "",
		show : false,
		url : "project/nums/list",
		exportUrl : 'project/nums/list/export', // 为空则不显示导出按钮
		heads : ['承担单位','主持人','项目名称','项目类别','下达部门','立项年份','开始时间','结束时间','经费数额(万元)' ],
		fields : ['dept','compere','name_','level_name','issued_dept','setup_year','start_time','end_time','fund'],
		params : {}
	}
	
	scope.allnumClick = function(){
		scope.formConfig.title = "各类科研项目列表";
		scope.formConfig.params = {
			startYear : scope.condition.definedYear.start,
			endYear : scope.condition.definedYear.end,
			zzjgid : scope.condition.dept.id,
			queryString : ""
		}
		scope.formConfig.show = true;
	}
	
	scope.chart1click = function(params){
		if(params.name == '最大值' || params.name == '最小值') return;
		scope.formConfig.title =  params.name + "年立项科研项目列表";
		scope.formConfig.params = {
			startYear : scope.condition.definedYear.start,
			endYear : scope.condition.definedYear.end,
			zzjgid : scope.condition.dept.id,
			queryString : "",
			setupYear : params.name
		}
		scope.formConfig.show = true;
	}
	
	scope.chart2click = function(params){
		scope.formConfig.title =  params.name + "科研项目列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				queryString : "",
				dept : params.name
		}
		scope.formConfig.show = true;
	}
	
	scope.chart3click = function(params){
		scope.formConfig.title =  params.name + "列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				queryString : "",
				level : params.name
		}
		scope.formConfig.show = true;
	}
	
	
	scope.levelClick = function(level){
		scope.formConfig.title =  level + "列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				queryString : "",
				level : level
		}
		scope.formConfig.show = true;
	}
	scope.deptClick = function(dept){
		scope.formConfig.title =  dept + "下达项目列表";
		scope.formConfig.params = {
				startYear : scope.condition.definedYear.start,
				endYear : scope.condition.definedYear.end,
				zzjgid : scope.condition.dept.id,
				queryString : "",
				issuedDept : dept
		}
		scope.formConfig.show = true;
	}
	
	scope.queryBtnClick = function(){
		if(scope.vm.index == 1)scope.queryProjectList();
		else scope.vm.index = 1;
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