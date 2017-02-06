var app = angular.module('app', ['system']);
app.controller("controller",['$scope','service','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
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
		return codeService.getDeptTree("research:thesis:publish");
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
		//高影响力期刊论文发表总量
		service.queryTotal(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.thesisNums = data.result;
			}else{
				toast.error("请求高影响力期刊论文发表总量错误");
			}
		});
		scope.formConfig = {
				title : "下钻明细",
				show : false,
				url : "/thesis/gyxlqk/pub/list",
				exportUrl : 'thesis/gyxlqk/pub/list/export', // 为空则不显示导出按钮
				heads : ['序号','论文名称','论文作者','学科门类','发文年份','论文出处','期刊类别','出版单位'],
				fields : ['rownum','lwmc','lwzz','xkml','fwnx','zzjg','qklb','cbdw'],
				params : {
					startYear : scope.condition.definedYear.start,
					endYear : scope.condition.definedYear.end,
					zzjgid : scope.condition.dept.id,
					xkmlid: scope.condition.subject.id,
					name : "",
					flag : ""
				}
		}
		scope.queryNums = function(){
			scope.formConfig.title = "在SCI源期刊发文/篇";
			scope.formConfig.params.name = "";
			scope.formConfig.params.flag = "nums";
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryYear = function(param){
			scope.formConfig.title = "在SCI源期刊发文的变化";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = "year";
			scope.formConfig.show = !scope.formConfig.show;
		}
		scope.queryDept = function(param){
			scope.formConfig.title = "发文篇数所属单位分布";
			scope.formConfig.params.name = param.name;
			scope.formConfig.params.flag = "dept";
			scope.formConfig.show = !scope.formConfig.show;
		}
		
		//发表量变化趋势
		service.queryYearChange(scope.condition).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.changeTrendConfig = {
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
					            name : '发文篇数',
					            min: 0,
					            axisLabel : {
					                formatter: '{value} 篇'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'发文篇数',
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
			}else{
				toast.error("请求发表量变化趋势错误");
			}
		});
		//各单位论文发表量与占比
		service.queryDepts(scope.condition).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.deptSourceConfig = {
					    title : {
					        text: '所属单位分布',
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
	    			            	rotate :15
	    			            }
					        }
					    ],
					    dataZoom : {
	        		        show : true,
	        		        realtime : true,
	        		        start : 0,
	        		        end : 50
	        		    },
					    grid : {
					    	top : 60,
					    	bottom :80,
		                	 left: '1%',
		                     right: '1%',
		                     containLabel: true
					    },
					    yAxis : [
					        {
					        	name : '发文篇数',
					            min: 0,
					            axisLabel : {
					                formatter: '{value} 篇'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'发文篇数',
					            type:'bar',
					            data:chartData.seriesData,
					            itemStyle: {
					            	normal: {
					            		color : "#b6a2de",
					            	}
					        
					            }
					        } 
					    ]
					};
			}else{
				toast.error("请求各单位论文发表量和占比错误");
			}
		});
		//发文期刊的载文量分析
		service.queryIncludeNumsByPeriodical(scope.condition).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.publishNumsConfig = {
					    title : {
					        text: '发文期刊的载文量分析',
					        show : false
					    },
					    tooltip : {
					        trigger: 'axis',
					        formatter: "载文量：{b} <br/>{a} : {c} 种"
					    },
					    xAxis : [
					        {
					            type : 'category',
					            data : chartData.xAxisData
					        }
					    ],
					    yAxis : [
					        {
					        	name : '期刊种数',
					            min: 0,
					            axisLabel : {
					                formatter: '{value}种 '
					            }
					        }
					    ],
					    series : [
					        {
					            name:'期刊种数',
					            type:'bar',
					            data:chartData.seriesData
					        } 
					    ]
					};
			}else{
				toast.error("请求发文期刊的载文量错误");
			}
		});
	};
}]);