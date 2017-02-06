var app = angular.module('app', ['ngRoute','system']);
app.controller("tabController",['$scope','service','sysCodeService','dialog',function(scope,service,codeService,dialog){
	scope.queryCondition = {
		subject : [],
		year : [],
		dept:{}
		
	};
	scope.condition = {
		subject : {},
		year : {},
		dept:{}
		
	};
	
	codeService.getYears().then(function(data){
		data[0].checked = true;
		scope.queryCondition.year = data;
		scope.condition.year = data[0].value;
		return codeService.getTeachDeptTree("research:kyjl:detail");
	}).then(function(data){
		scope.queryCondition.dept = data;
		data.checked = true;
		return codeService.getCodeSubject();
	}).then(function(data){
		scope.queryCondition.subject = data;
		scope.condition.subject = data[0];
		//定义tab切换事件
		scope.$watch("condition",function(){
			scope.$broadcast("pageConditionChange", scope.condition);
		},true);
	});
	scope.change =function(data){
		 scope.condition.year = data.value;
	 }
	scope.refreshPageData = function(){
		dialog.showLoading();
		service.refreshPageData(scope.condition.year).then(function(data){
			if(data.success) scope.$broadcast("pageConditionChange", scope.condition);
			else toastr.error("数据刷新出错！");
		});
		dialog.hideLoading();
	}
	scope.$on("pageTabChange",function(e,msg){
		scope.currentTab = msg;
	});
	scope.$watch("currentTab",function(){
		scope.$broadcast("pageConditionChange", scope.condition);
	},true);
}]);
app.controller("xmlxController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",1);
	scope.condition = {
		subject : {},
		year : {},
		dept : {}
	};
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid:scope.condition.subject.id,
				zzjgid : scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryLevel = function(param){
		scope.formConfig.title = param.name+"获得 高层次项目立项奖 奖励结果";
		scope.formConfig.params.name  = "高层次项目立项奖";
		scope.formConfig.params.paramName  = "level_";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	scope.queryRank = function(param){
		scope.formConfig.title = param.name+"项目获得 高层次项目立项奖 奖励结果";
		scope.formConfig.params.name  = "高层次项目立项奖";
		scope.formConfig.params.paramName  = "rank_";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 高层次项目立项奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "高层次项目立项奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun();
	});
	scope.fun = function(){
		service.queryAwardList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else
				toast.error("高层次项目立项奖奖励名单错误！");
			scope.vm1.showModal = false;
		});
	}
	scope.pageRefresh = function(){
		scope.fun();
		service.queryAwardLevel(scope.condition).then(function(data){
			if (data.success){
				var legendData = [];
				for(var i=0;i<data.result.length;i++){
					legendData.push(data.result[i].name);
				}
				scope.levelconfig = {
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
				            name:'项目级别',
				            type:'pie',
				            startAngle: 180,
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: data.result
				        }
				    ]
				}
			}else{
				toast.error("立项奖奖励金额项目级别分布！");
			}
		});
		service.queryAwardRank(scope.condition).then(function(data){
			if (data.success){
				var legendData = [];
				for(var i=0;i<data.result.length;i++){
					legendData.push(data.result[i].name);
				}
				scope.rankconfig = {
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
				            name:'项目程度',
				            type:'pie',
				            startAngle: 180,
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: data.result
				        }
				    ]
				}
			}else{
				toast.error("立项奖奖励金额项目程度分布！");
			}
		});
		service.querySetupPeopleDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
			}
		});
	}
}]);
app.controller("xmjxController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",2);
	scope.condition = {
		subject : {},
		year : {},
		dept : {}
	};
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid : scope.condition.subject.id,
				zzjgid:scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 高层次项目结项奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "高层次项目结项奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount : 0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun();
	});
	scope.fun = function(){
		service.queryEndList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else
				toast.error("高层次项目结项奖奖励名单错误！");
			scope.vm1.showModal = false;
		});
	}
	scope.pageRefresh = function(){
		scope.fun();
		service.queryEndDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
			}
		});
	}
}]);
app.controller("yjcgController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",3);
	scope.condition = {
		subject:{},
		year:{},
		dept:{}
	}
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid: scope.condition.subject.id,
				zzjgid: scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 高层次研究成果奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "高层次研究成果奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.vm2 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun1();
	});
	scope.$watch("vm2.index",function(newval,oldval){
		scope.vm2.showModal = true;
		scope.fun2();
	});
	scope.fun1 = function(){
		service.queryAchievementList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else
				toast.error("高层次研究成果奖励名单错误！");
			scope.vm1.showModal = false;
		});
	} 
	scope.fun2 = function(){
		service.queryAchievementList2(scope.vm2,scope.condition).then(function(data) {
			if (data.success){
				scope.vm2.total = data.result.total;
				scope.vm2.pagecount = data.result.pagecount;
				scope.vm2.items = data.result.rows;
			}else
				toast.error("高层次研究成果参与获奖奖励名单错误！");
			scope.vm2.showModal = false;
		});
	} 
	scope.pageRefresh = function(){
		scope.fun1();
		scope.fun2();
		service.queryAchievementDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
			}
		});
	}
}]);
app.controller("xslwController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",4);
	scope.condition = {
		subject:{},
		year : {},
		dept:{}
	}
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid: scope.condition.subject.id,
				zzjgid: scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryIn = function(param){
		scope.formConfig.title = param.name+"获得 高层次学术论文奖 奖励结果";
		scope.formConfig.params.paramName  = "in";
		scope.formConfig.params.name  = "高层次学术论文奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	scope.queryReship = function(param){
		scope.formConfig.title = param.name+"获得 高层次学术论文奖 奖励结果";
		scope.formConfig.params.paramName  = "reship";
		scope.formConfig.params.name  = "高层次学术论文奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 高层次学术论文奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "高层次学术论文奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.vm2 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun1();
	});
	scope.$watch("vm2.index",function(newval,oldval){
		scope.vm2.showModal = true;
		scope.fun2();
	});
	scope.fun1 = function(){
		service.queryThesisInList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else
				toast.error("高层次学术论文收录奖励名单错误！");
			scope.vm1.showModal = false;
		});
	};
	scope.fun2 = function(){
		service.queryThesisReshipList(scope.vm2,scope.condition).then(function(data) {
			if (data.success){
				scope.vm2.total = data.result.total;
				scope.vm2.pagecount = data.result.pagecount;
				scope.vm2.items = data.result.rows;
			}else
				toast.error("高层次学术论文转载奖励名单错误！");
			scope.vm2.showModal = false;
		});
	};
	scope.pageRefresh = function(){
		scope.fun1();
		scope.fun2();
		service.queryThesisIn(scope.condition).then(function(data){
			if (data.success){
				var legendData = [];
				for(var i=0;i<data.result.length;i++){
					legendData.push(data.result[i].name);
				}
				scope.inconfig = {
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
				            name:'收录期刊',
				            type:'pie',
				            startAngle: 180,
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: data.result
				        }
				    ]
				}
			}else{
				toast.error("学术论文收录期刊分布！");
			}
		});
		service.queryThesisReship(scope.condition).then(function(data){
			if (data.success){
				var legendData = [];
				for(var i=0;i<data.result.length;i++){
					legendData.push(data.result[i].name);
				}
				scope.reshipconfig = {
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
				            name:'转载期刊',
				            type:'pie',
				            startAngle: 180,
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: data.result
				        }
				    ]
				}
			}else{
				toast.error("学术论文转载分布！");
			}
		});
		service.queryThesisDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
			}
		});
	}
}]);
app.controller("fmzlController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",5);
	scope.condition = {
		subject : {},
		year : {},
		dept:{}
	}
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid: scope.condition.subject.id,
				zzjgid: scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryScope = function(param){
		scope.formConfig.title = param.name+"发明专利奖 奖励结果";
		scope.formConfig.params.paramName  = "scope";
		scope.formConfig.params.name  = "发明专利奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 发明专利奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "发明专利奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun();
	});
	scope.fun = function(){
		service.queryPatentList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else{
				toast.error("发明专利奖励名单错误！");
			}
			scope.vm1.showModal = false;
		});
	}
	scope.pageRefresh = function(){
		scope.fun();
		service.queryPatentType(scope.condition).then(function(data){
			if (data.success){
				var legendData = [];
				for(var i=0;i<data.result.length;i++){
					legendData.push(data.result[i].name);
				}
				scope.typeconfig = {
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
				            name:'专利类别',
				            type:'pie',
				            startAngle: 180,
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: data.result
				        }
				    ]
				}
			}else{
				toast.error("发明专利专利类别分布错误！");
			}
		});
		service.queryPatentDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
				toast.error("发明人单位分布错误！");
			}
		});
	}
}]);
app.controller("kyjfController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",6);
	scope.condition = {
		subject:{},
		year:{},
		dept:{}
	}
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid: scope.condition.subject.id,
				zzjgid: scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 科研经费奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "科研经费奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount:0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun();
	});
	scope.fun = function(){
		service.queryFundList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else{
				toast.error("科研经费奖励名单错误！");
			}
			scope.vm1.showModal = false;
		});
	}
	scope.pageRefresh = function(){
		scope.fun();
		service.queryFundDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
			}
		});
	}
}]);
app.controller("cgzhController",['$scope','service','sysCodeService','dialog','toastrService',function(scope,service,codeService,dialog,toast){
	scope.$emit("pageTabChange",7);
	scope.condition = {
		subject : {},
		year : {},
		dept:{}
	}
	scope.$on("pageConditionChange",function(e,d){
		scope.condition = d;
		scope.pageRefresh();
	});
	scope.formConfig = {
			title : "科研奖励结果明细",
			show : false,
			url : "kyjl/detail/detail",
			exportUrl : 'kyjl/detail/detail/export', // 为空则不显示导出按钮
			heads : ['序号','负责人','负责人单位','奖项名称','奖励金额（万元）','统计年份'],
			fields : ['rn','tea_name','dept_name','award_name','fund','year_'],
			params : {
				year: scope.condition.year,
				xkmlid: scope.condition.subject.id,
				zzjgid: scope.condition.dept.id,
				name : '',
				paramName : '',
				paramValue : ''
			}
		}
	scope.queryDept = function(param){
		scope.formConfig.title = param.name+"获得 科研成果转化奖 奖励结果";
		scope.formConfig.params.paramName  = "dept";
		scope.formConfig.params.name  = "科研成果转化奖";
		scope.formConfig.params.paramValue = param.name;
		scope.formConfig.params.year = scope.condition.year;
		scope.formConfig.params.xkmlid = scope.condition.subject.id;
		scope.formConfig.params.zzjgid = scope.condition.dept.id;
		scope.formConfig.show = !scope.formConfig.show;
	};
	
	scope.vm1 = {
		size: 5,
		index: 1,
		items : [],
		total : 0,
		pagecount : 0,
		showModal : false
	};
	scope.$watch("vm1.index",function(newval,oldval){
		scope.vm1.showModal = true;
		scope.fun();
	});
	scope.fun = function(){
		service.queryTransformList(scope.vm1,scope.condition).then(function(data) {
			if (data.success){
				scope.vm1.total = data.result.total;
				scope.vm1.pagecount = data.result.pagecount;
				scope.vm1.items = data.result.rows;
			}else{
				toast.error("科研成果转化奖励名单错误！");
			}
			scope.vm1.showModal = false;
		});
	}
	scope.pageRefresh = function(){
		scope.fun();
		service.queryTransformDept(scope.condition).then(function(data){
			if (data.success){
				var xdata = [];
				var numdata = [];
				var funddata = [];
				for(var i=0;i<data.result.length;i++){
					xdata.push(data.result[i].dept_name);
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
			}
		});
	}
}]);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/xmlx", {
		templateUrl: "tpl/xmlx.html",
		reloadOnSearch: false,
		controller : "xmlxController"
	})
	.when("/xmjx", {
		templateUrl: "tpl/xmjx.html",
		reloadOnSearch: false,
		controller : "xmjxController"
	})
	.when("/yjcg", {
		templateUrl: "tpl/yjcg.html",
		reloadOnSearch: false,
		controller : "yjcgController"
	})
	.when("/xslw", {
		templateUrl: "tpl/xslw.html",
		reloadOnSearch: false,
		controller : "xslwController"
	})
	.when("/fmzl", {
		templateUrl: "tpl/fmzl.html",
		reloadOnSearch: false,
		controller : "fmzlController"
	})
	.when("/kyjf", {
		templateUrl: "tpl/kyjf.html",
		reloadOnSearch: false,
		controller : "kyjfController"
	})
	.when("/cgzh", {
		templateUrl: "tpl/cgzh.html",
		reloadOnSearch: false,
		controller : "cgzhController"
	})
	.otherwise({
		redirectTo : "/xmlx"
	});
}]);