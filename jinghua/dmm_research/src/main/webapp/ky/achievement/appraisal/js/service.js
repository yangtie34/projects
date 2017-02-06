app.service("service",['httpService',function(http){
    return {
    	//查询鉴定成果数
    	queryAppraisalAchievementNums : function(condition){ 
    		return http.post({
    			url : "achievement/appraisal/nums",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//获取查询成果条件的code
    	queryAchievementCode : function(){
	    	return http.post({
    			url : "achievement/appraisal/code",
    			data : {}
	    	});
    	},
    	//查询鉴定成果变化趋势
    	queryAppraisalNumsChange : function(condition){ 
    		return http.post({
    			url : "achievement/appraisal/change",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询鉴定级别
    	queryAppraisalGrade : function(condition){ 
    		return http.post({
    			url : "achievement/appraisal/grade",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询鉴定水平
    	queryAppraisalLevel : function(condition){ 
    		return http.post({
    			url : "achievement/appraisal/level",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询鉴定形式
    	queryAppraisalMode : function(condition){ 
    		return http.post({
    			url : "achievement/appraisal/mode",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//完成人承担角色
    	queryAppraisalPeopleRole : function(condition,authorRole){ 
    		return http.post({
    			url : "achievement/appraisal/peopleRole",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id,
    				role: authorRole
    			}
    		});
    	},
    	//查询鉴定成果列表
    	queryAppraisalList : function(condition,page,params){ 
    		return http.post({
    			url : "achievement/appraisal/list",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id,
    				param: params.param,
    				grade: params.grade,
    				level:params.level,
    				mode : params.mode,
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount
    			}
    		});
    	},
    	
    	AppraisalChangeChartOption : function(data,scope){
    		var xAxisData = [];
    		var yAxisData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.year_);
				yAxisData.push(it.nums);
			}
    		scope.changechart = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['鉴定成果数']
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
			            name:'鉴定成果数',
			            type:'line',
			            data:yAxisData
			        }
			    ]	
    		};
    	},
    	AppraisalGradeOption : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		scope.gradechart = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'horizontal',
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
			    calculable : true,
			    series : [
			        {
			            name:'鉴定级别',
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
    	AppraisalLevelOption : function(data,scope){
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
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'鉴定成果水平',
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
    	AppraisalModeOption : function(data,scope){
    		var xData = [];
    		var yData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xData.push(it.name);
				yData.push(it.value);
			}
    		scope.modechart = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['鉴定形式']
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
			            name:'鉴定形式',
			            type:'bar',
			            data: yData
			        }
			    ]
			};
    	},
    	AppraisalPeopleRoleOption : function(data,scope){
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
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'完成人承担角色',
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
