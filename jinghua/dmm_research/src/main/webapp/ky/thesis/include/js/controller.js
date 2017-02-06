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
		return codeService.getDeptTree("research:thesis:include");
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
	
	scope.queryPageInfo = function(){
		dialog.showLoading();
		service.getAllTypes(scope.condition).then(function(data){
			dialog.hideLoading();
			if(data.success){
				scope.periodicalTypes = data.result;
				for (var i = 0; i < data.result.length; i++) {
					var it = data.result[i];
					if(it.name == 'SCI'){
						scope.queryDetailOfPeriodical(it.code);
					}
				}
			}else{
				toast.error("请求期刊类别出错！");
			}
		});
	}
	
	//根据期刊类别查询期刊信息
	scope.queryDetailOfPeriodical = function(qklb){
		dialog.showLoading();
		for (var i = 0; i < scope.periodicalTypes.length; i++) {
			var it = scope.periodicalTypes[i];
			if(it.code == qklb){
				it.active= true;
				scope.showBottom = (it.name == "SCI");
				service.getSciImpact(qklb,scope.condition).then(function(data){
					if(data.success){
						var chartData = service.generateTwoChartData(data.result);
						scope.chart3Config = {
						    title : {
						        text: 'SCI论文收录期刊影响因子分布'
						    },
						    tooltip : {
						        trigger: 'axis',
						        formatter: function(params){
						            var seriesName = "SCI影响因子值：" +params[0].name, htm = '';
							        for (var i=0, l=params.length; i<l; i++) {
							            htm += '<br/>' + params[i].seriesName + ' ： ' +  params[i].value ;
							        }
							      	return seriesName + htm;
							    }
						    },
						    xAxis : [
						        {
						            type : 'category',
						            data : chartData.xAxisData
						        }
						    ],
						    dataZoom : {
		        		        show : chartData.xAxisData.length > 5 ? true : false,
		        		        realtime : true,
		        		        start : 0,
		        		        end : chartData.xAxisData.length > 5 ? 50 : 100
		        		    },
						    yAxis : [
						        {
						            name : '论文收录数',
						            min: 0,
						            axisLabel : {
						                formatter: '{value} 篇'
						            }
						        },
						        {
						            name : '期刊数',
						            min: 0,
						            axisLabel : {
						                formatter: '{value} 种'
						            }
						        }
						    ],
						    legend: {
						        orient : 'horizontal',
						        left : 'center',
						        top : 'top',
						        data:['论文收录数','期刊数']
						    },
						    series : [{
					            name:'论文收录数',
					            type:'bar',
					            data: chartData.seriesData[1]
					        },{
					            name:'期刊数',
					            type:'bar',
					            yAxisIndex: 1,
					            data: chartData.seriesData[0]
					        }]
						};
					}else{
						toast.error("请求SCI影响因子出错！");
					}
				});
				service.getSciZone(qklb,scope.condition).then(function(data){
					if(data.success){
						var legendData = [];
			    		for (var i = 0; i < data.result.length; i++) {
							var it = data.result[i];
							legendData.push(it.name);
						}
						
						scope.chart4Config = {
						    title : {
						        text: 'SCI收录论文分区'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient : 'vertical',
						        left : 'right',
						        top : 'center',
						        show : false,
						        data:legendData
						    },
						    calculable : true,
						    series : [
						        {
						            name:'SCI分区',
						            type:'pie',
						            startAngle: 180,
						            radius : '55%',
						            center: ['50%', '60%'],
						            data: data.result,
			    		            label : {
			    		                normal : {
			    		                    formatter: '{b}({d}%)'
			    		                }
			    		            }
						        }
						    ]
						}
					}else{
						toast.error("请求SCI分区出错！");
					}
				});
			}else{
				it.active = false;
			}
		}
		service.getIncludeNumsOfDepts(qklb,scope.condition).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.chart1Config = {
				    title : { text: '收录论文所属单位分布'},
				    tooltip : {trigger: 'axis',
				        formatter: "{b} <br/>{a} : {c}篇 " },
				    xAxis : [{
				            type : 'category',
				            data : chartData.xAxisData
			        }],
				    yAxis : [{
			            name : '论文收录篇数',
			            min: 0,
			            axisLabel : {
			                formatter: '{value} 篇'
			        }}],
			        dataZoom : {
        		        show : true,
        		        realtime : true,
        		        start : 0,
        		        end : 6*100/chartData.xAxisData.length
        		    },
				    series : [{
			            name:'论文收录篇数',
			            type:'bar',
			            data: chartData.seriesData
			        }]
				};
			}else{
				toast.error("请求收录论文各单位归属出错！");
			}
		});
		service.getIncludeNumsOfYears(qklb,scope.condition).then(function(data){
			if(data.success){
				var chartData = service.generateChartData(data.result);
				scope.chart2Config = {
					title : { text: '收录论文发表时间分布'},
				    tooltip : {trigger: 'axis',
				        formatter: "发表年份：{b} <br/>{a} : {c} 篇" },
				    toolbox: {show : true,
				        feature : {
			        	    dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
			        }}, 
				    xAxis : [{
				            type : 'category',
				            data : chartData.xAxisData
			        }],
				    yAxis : [{
			            name : '发表论文篇数',
			            min: 0,
			            axisLabel : {
			                formatter: '{value} 篇'
			        }}],
				    series : [{
			            name:'发表论文篇数',
			            type:'line',
			            data: chartData.seriesData
			        }]
				};
			}else{
				toast.error("请求收录论文年份变化趋势出错！");
			}
		});
		dialog.hideLoading();
		
		scope.chart1click = function(param){
			scope.formConfig.title = param.name + "收录论文列表";
			scope.formConfig.params = {
				periodicalType : qklb,
				xkmlid: scope.condition.subject.id,
				startYear: scope.condition.definedYear.start,
				endYear: scope.condition.definedYear.end,
				zzjgid: scope.condition.dept.id,
				zzjgmc : param.mc
			}
			scope.formConfig.show = true;
		}
		scope.chart2click = function(param){
			scope.formConfig.title = param.name + "年发表论文收录列表";
			scope.formConfig.params = {
				periodicalType : qklb,
				xkmlid: scope.condition.subject.id,
				startYear: scope.condition.definedYear.start,
				endYear: scope.condition.definedYear.end,
				zzjgid: scope.condition.dept.id,
				pubYear : param.name
			}
			scope.formConfig.show = true;
		}
		scope.chart4click = function(param){
			scope.formConfig.title = param.name + "分区论文收录列表";
			scope.formConfig.params = {
					periodicalType : qklb,
					xkmlid: scope.condition.subject.id,
					startYear: scope.condition.definedYear.start,
					endYear: scope.condition.definedYear.end,
					zzjgid: scope.condition.dept.id,
					thesisInSci : param.name
			}
			scope.formConfig.show = true;
		}
	}
	
	scope.formConfig = {
		title : "",
		show : false,
		url : "thesis/gyxlqk/in/list",
		exportUrl : 'thesis/gyxlqk/in/list/export', // 为空则不显示导出按钮
		heads : ['收录期刊','收录年份','收录年卷期页', '论文题目','发表期刊','发表年卷期页','发表年份'],
		fields : ['in_periodical','in_year','in_njqy', 'thesis_name','pub_periodical','pub_njqy','pub_year'],
		params : {}
	}
}]);