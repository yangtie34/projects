app.service("service",['httpService',function(http){
    return {
    	//查询计算机著作权数
    	querySoftNums : function(condition){ 
    		return http.post({
    			url : "achievement/soft/nums",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//获取查询获奖成果条件的code
    	querySoftCode : function(){
	    	return http.post({
    			url : "achievement/soft/code",
    			data : {}
	    	});
    	},
    	//查询计算机著作权数变化趋势
    	querySoftNumsChange : function(condition){ 
    		return http.post({
    			url : "achievement/soft/change",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询计算机著作权取得方式
    	querySoftGet : function(condition){ 
    		return http.post({
    			url : "achievement/soft/get",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询计算机著作权人员单位分布
    	querySoftPeopleDept : function(condition){ 
    		return http.post({
    			url : "achievement/soft/peopleDept",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询活跃软件开发者
    	querySoftAuthor : function(condition){ 
    		return http.post({
    			url : "achievement/soft/author",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询计算机著作权列表
    	querySoftList : function(condition,page,params){ 
    		return http.post({
    			url : "achievement/soft/list",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id,
    				params: params.param,
    				copyright : params.copyright,
    				getcode: params.getcode,
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount
    			}
    		});
    	},
    	
    	SoftNumsChangeChartOption : function(data,scope){
    		var xAxisData = [];
    		var yAxisData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.year_);
				yAxisData.push(it.nums);
			}
    		scope.changesoftchart = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['计算机著作权数']
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
			            name:'计算机著作权数',
			            type:'line',
			            data:yAxisData
			        }
			    ]	
    		}
    	},
    	SoftGetChartOption : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		scope.getchart = {
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
			            name:'取得方式',
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
    	SoftPeopleDeptChartOption : function(data,scope){
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
			        data:['人员数量']
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
			            name:'人员数量',
			            type:'bar',
			            data:yData
			        }
			    ]
			};
    	}
    }
}]);
