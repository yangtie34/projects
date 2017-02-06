app.service("service",['httpService',function(http){
    return {
    	//查询获奖成果数
    	queryAwardsAchievementNums : function(condition){ 
    		return http.post({
    			url : "achievement/awards/nums",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//获取查询获奖成果条件的code
    	queryAwardCode : function(){
	    	return http.post({
    			url : "achievement/awards/code",
    			data : {}
	    	});
    	},
    	//查询获奖成果变化趋势
    	queryAwardsNumsChange : function(condition){ 
    		return http.post({
    			url : "achievement/awards/change",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询获奖级别
    	queryAwardsLevel : function(condition){ 
    		return http.post({
    			url : "achievement/awards/level",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//获奖人单位分布
    	queryAwardsPeopleDept : function(condition){ 
    		return http.post({
    			url : "achievement/awards/peopleDept",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//获奖类别分布
    	queryAwardsType : function(condition){ 
    		return http.post({
    			url : "achievement/awards/type",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//获奖人担任角色
    	queryAwardsPeopleRole : function(condition,authorRole){ 
    		return http.post({
    			url : "achievement/awards/peopleRole",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id ,
    				role : authorRole
    			}
    		});
    	},
    	//查询获奖成果列表
    	queryAwardsList : function(condition,page,params){ 
    		return http.post({
    			url : "achievement/awards/list",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id,
    				param: params.param,
    				level: params.level,
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount
    			}
    		});
    	},
    	
    	AwardsChangeChartOption : function(data,scope){
    		var xAxisData = [];
    		var yAxisData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.year_);
				yAxisData.push(it.nums);
			}
    		scope.changeawardschart = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['获奖成果数']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : xAxisData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            axisLabel : {
			                formatter: '{value} 个'
			            }
			        }
			    ],
			    series : [
			        {
			            name:'获奖成果数',
			            type:'line',
			            data:yAxisData
			        }
			    ]	
    		}
    	},
    	AwardsLevelChartOption : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		scope.levelchart = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'horizontal',
			        x : 'center',
			        data:legendData
			    },
			    calculable : true,
			    series : [
			        {
			            name:'获奖等级',
			            type:'pie',
			            startAngle: 180,
			            radius : '55%',
			            center: ['50%', '60%'],
			            data: data,
    		            label : {
    		                normal : {
    		                    formatter: '{b}({d}%)'
    		                }
    		            }
			        }
			    ]
			};
    	},
    	AwardsPeopleDeptChartOption : function(data,scope){
    		var xData = [];
    		var yData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xData.push(it.name);
				yData.push(it.value);
			}
    		scope.deptchart = {
    			tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['获奖数量']
			    },
			    calculable : true,
			    dataZoom : {
			        show : true,
			        realtime : true,
			        start : 20,
			        end : 80
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : xData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'获奖数量',
			            type:'bar',
			            data:yData
			        }
			    ]
			};
    	},
    	AwardsTypeChartOption  : function(data,scope){
    		var xData = [];
    		var yData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xData.push(it.name);
				yData.push(it.value);
			}
    		scope.typechart = {
    			tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['获奖数量']
			    },
			    calculable : true,
			    dataZoom : {
			        show : true,
			        realtime : true,
			        start : 20,
			        end : 80
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : xData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'获奖数量',
			            type:'bar',
			            data:yData
			        }
			    ]
			};
    	},
    	AwardsPeopleRoleOption : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		scope.peoplechart = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'horizontal',
			        x : 'center',
			        data:legendData
			    },
			    calculable : true,
			    series : [
			        {
			            name:'获奖人担任角色',
			            type:'pie',
			            startAngle: 180,
			            radius : '55%',
			            center: ['50%', '60%'],
			            data: data,
    		            label : {
    		                normal : {
    		                    formatter: '{b}({d}%)'
    		                }
    		            }
			        }
			    ]
			};
    	}
    }
}]);
