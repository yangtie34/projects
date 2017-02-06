var app = angular.module('app', [ 'ngRoute', 'system' ]);
app.controller("homeController", [
		'$scope',
		'homeService',
		'dialog',
		'sysCodeService',
		'toastrService',
		function(scope, service, dialog, codeService, toast) {
			// 定义页面查询条件
			scope.queryCondition = {
				level : [],
				definedYear : [],
				dept : {}
			};
			// 定义查询呢条件选择结果
			scope.condition = {
				level : {},
				definedYear : {},
				dept : {}
			}

			// 请求后台填充查询条件数据
			dialog.showLoading();
			codeService.getDeptTree("research:project:progress").then(function(data) {
				scope.queryCondition.dept = data;
				data.checked = true;
				return codeService.getCodeByCodeType("RES_PROJECT_LEVEL_CODE");
			}).then(function(data) {
				scope.queryCondition.level = data;
				scope.condition.level = data[0];
				return codeService.getSelfDefinedYearCode();
			}).then(function(data) {
				scope.queryCondition.definedYear = data;
				// 条件数据填充完毕，开始绑定刷新事件，查询页面内容
				scope.$watch("condition", function() {
					// 条件执行完毕，开始查询页面数据
					scope.queryPageInfo();
				}, true);
			});
			// 查询页面内容方法
			scope.queryPageInfo = function() {
				dialog.showLoading();
				var params = scope.condition;
				
				scope.formConfig = {
					title : "项目进度",
					show : false,
					url : "project/progress/queryProgressDetail",
					exportUrl : 'project/progress/queryProgressDetail/export', // 为空则不显示导出按钮
					heads : ['序号','项目名称','主持人姓名','承担单位','项目类别','项目状态','下达部门','起止时间','经费（万元）','立项年度'],
					fields : ['rn','name','tea','dept','lb','state','issude_dept','time_','fund','setup_year'],
					params : {
						startYear : scope.condition.definedYear.start,
						endYear : scope.condition.definedYear.end,
						zzjgid : scope.condition.dept.id,
						level: scope.condition.level.id,
						name : "",
						flag : ""
					}
				}
				scope.queryDetail = function(){
					scope.formConfig.title = "在研项目列表";
					scope.formConfig.params.name = '';
					scope.formConfig.params.flag = 'in';
					scope.formConfig.show = !scope.formConfig.show;
				}
				scope.queryDept = function(params){
					scope.formConfig.title = params.name+"项目列表";
					scope.formConfig.params.name = params.name;
					scope.formConfig.params.flag = 'dept';
					scope.formConfig.show = !scope.formConfig.show;
				}
				scope.queryOverTime = function(params){
					scope.formConfig.title = "超期"+params.name+"年项目列表";
					scope.formConfig.params.name = params.name;
					scope.formConfig.params.flag = 'years';
					scope.formConfig.show = !scope.formConfig.show;
				}
				scope.queryState = function(params){
					scope.formConfig.title = params.name+"项目列表";
					scope.formConfig.params.name = params.name;
					scope.formConfig.params.flag = 'state';
					scope.formConfig.show = !scope.formConfig.show;
				}
				scope.queryDeptProgress = function(param){
					scope.formConfig.title = param+"项目列表";
					scope.formConfig.params.name = param;
					scope.formConfig.params.flag = 'dept';
					scope.formConfig.show = !scope.formConfig.show;
				}
				scope.queryCompere = function(tea_id,tea){
					scope.formConfig.title = tea+"主持项目列表";
					scope.formConfig.params.name = tea_id;
					scope.formConfig.params.flag = 'tea';
					scope.formConfig.show = !scope.formConfig.show;
				}
				
				
				/** 查询在研项目数量 */
				service.queryGoingOnProjectNums(params).then(function(data) {
					dialog.hideLoading();
					if (data.success)
						scope.totalNums = data.result;
					else
						toast.error("查询在研项目数量！");
				});

				/** 查询各单位不同状态的项目数 */
				service.queryProjectNumsByDeptAndState(params).then(
						function(data) {
							if (data.success){
								var temp = [];
								for (var i = 0; i < data.result.length; i++) {
									var tt = data.result[i];
									temp.push({name : "在研",field : tt.dept,value:tt.goingon});
									temp.push({name : "结项",field : tt.dept,value:tt.complete});
									temp.push({name : "超期",field : tt.dept,value:tt.timeout});
									var persent = (Number(tt.complete)*100 /(Number(tt.timeout)+ Number(tt.complete) + Number(tt.goingon))).toFixed(2);
									temp.push({name : "完成率(%)",field : tt.dept,value: persent});
								}
								
								var chartData = service.generateColumnChartData(temp);
								for(var i in chartData.series){
									var it = chartData.series[i];
									if(it.name == '完成率(%)'){
										it.type = 'line';
										it.yAxisIndex = 1;
									}else{
										it.stack = '总量';
									}
								}
								scope.config1 = {
								    title : {
								        text: '科研项目校内分布直方图'
								    },
								    tooltip : {
								        trigger: 'axis'
								    },
								    legend: {
								        data: chartData.legendData,
										padding : [30,0]
								    },
								    dataZoom : {
				        		        show : true,
				        		        realtime : true,
				        		        start : 0,
				        		        end : 6*100/chartData.xAxisData.length
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
								            name: '项目数',
				    			            min: 0
								        },{
								            type : 'value',
								            name: '完成率(%)',
				    			            min: 0
								        }
								    ],
								    series : chartData.series
								};
							}else
								toast.error("查询各单位不同状态的项目数！");
						});

				/** 科研项目超期时长分布 */
				service.queryTimeoutProjectNums(params).then(function(data) {
					if (data.success){
						var chartData = service.generateChartData(data.result);
						scope.config2 = {
						    title : {
						        text: '科研项目超期时长分布'
						    },
						    tooltip : {
						        trigger: 'axis',
						        formatter: '超期{b0}年<br />{a0}: {c0}'
						    },
						    grid: {
						        left: '25',
						        right: '40'
						    },
						    xAxis : [{
					    		name : "超期\n年数",
					            type : 'category',
					            data : chartData.xAxisData
					        }],
						    yAxis : [
						        {
						            type : 'value',
						            name: '项目数',
		    			            min: 0
						        }
						    ],
						    series : [{
					            name:'项目数',
					            type:'bar',
					            data: chartData.seriesData
					        } ]
						};
					}else
						toast.error("科研项目超期时长分布！");
				});

				/** 科研项目状态组成 */
				service.queryProjectNumsByState(params).then(function(data) {
					if (data.success){
						scope.config3 = service.generatePieConfig("科研项目状态组成",data.result);
					}else
						toast.error(" 科研项目状态组成！");
				});
				
				scope.vm1 = {
						size: 10,
						index: 1,
						items : [],
						total : 0,
						showModal : false
				};
				scope.$watch("vm1.index",function(newval,oldval){
					scope.vm1.showModal = true;
					/** 各单位科研项目到期完成率排名 */
					service.queryOrderByDept(scope.vm1,scope.condition).then(function(data) {
						if (data.success){
							scope.vm1.total = data.result.total;
							scope.vm1.items = data.result.rows;
						}else
							toast.error("各单位科研项目到期完成率排名！");
						scope.vm1.showModal = false;
					});
				});
				
				scope.vm2 = {
					size: 10,
					index: 1,
					items : [],
					total : 0,
					showModal : false
				};
				scope.$watch("vm2.index",function(newval,oldval){
					scope.vm2.showModal = true;
					/** 单位 主持人完成率排名 */
					service.queryOrderByCompere(scope.vm2,scope.condition).then(function(data) {
						if (data.success){
							scope.vm2.total = data.result.total;
							scope.vm2.items = data.result.rows;
						}else
							toast.error(" 单位 主持人完成率排名！");
						scope.vm2.showModal = false;
					});
				});
				
				scope.vm3 = {
						size: 10,
						index: 1,
						items : [],
						total : 0,
						showModal : false
				};
				scope.$watch("vm3.index",function(newval,oldval){
					scope.vm3.showModal = true;
					/** 超期项目列表 */
					service.queryTimeOutProjectList(scope.vm3,scope.condition).then(function(data) {
						if (data.success){
							scope.vm3.total = data.result.total;
							scope.vm3.items = data.result.rows;
						}else
							toast.error("超期项目列表 ！");
						scope.vm3.showModal = false;
					});
				});
			};
	}]);
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when("/home", {
		templateUrl : "tpl/home.html",
		reloadOnSearch : false,
		controller : "homeController"
	}).otherwise({
		redirectTo : "/home"
	});
} ]);