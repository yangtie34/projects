app.service("service",['httpService',function(http){
    return {
    	//查询著作数
    	queryWorkNums : function(condition){ 
    		return http.post({
    			url : "writing/work/nums",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询作者承担角色
    	queryAuthorRole : function(condition){ 
    		return http.post({
    			url : "writing/work/authorRole",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	}, 
    	//查询著作数量历年变化
    	queryWorkNumsChange : function(condition){ 
    		return http.post({
    			url : "writing/work/numsChange",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询著作单位分布
    	queryWorkDept : function(condition){ 
    		return http.post({
    			url : "writing/work/dept",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	
    	//查询著作列表
    	queryWorkList : function(condition,page,params){ 
    		return http.post({
    			url : "writing/work/list",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id,
    				param: params,
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount
    			}
    		});
    	},
    	
    	WorkChangeChartOption : function(data,scope){
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
			        data:['著作数']
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
			                formatter: '{value} 部'
			            }
			        }
			    ],
			    series : [
			        {
			            name:'著作数',
			            type:'line',
			            data:yAxisData
			        }
			    ]	
    		};
    	},
    	WorkDeptChartOption  : function(data,scope){
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
			        data:['著作数量']
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
			            name:'著作数量',
			            type:'bar',
			            data:yData
			        }
			    ]
			};
    	}
    }
}]);
