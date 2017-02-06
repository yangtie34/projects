app.service("service",['httpService',function(http){
    return {
    	//查询专利数
    	queryPatentNums : function(condition){ 
    		return http.post({
    			url : "achievement/patent/nums",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询专利类型
    	queryPatentType : function(condition){ 
    		return http.post({
    			url : "achievement/patent/type",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询专利实施状态
    	queryPatentState : function(condition){ 
    		return http.post({
    			url : "achievement/patent/state",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询专利贡献度高的单位
    	queryPatentCon : function(condition){ 
    		return http.post({
    			url : "achievement/patent/deptMax",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//查询专利变化趋势
    	queryPatentChange : function(condition,param){ 
    		return http.post({
    			url : "achievement/patent/change",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id ,
    				param : param
    			}
    		});
    	},
    	//查询专利单位分布
    	queryPatentDept : function(condition,param){ 
    		return http.post({
    			url : "achievement/patent/dept",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id ,
    				param : param
    			}
    		});
    	},
    	
    	PatentTypeChartOption : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		scope.typechart = {
    			title : {
			        text: '专利类型',
			        x:'left'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'right',
			        y : 'center',
			        data:legendData
			    },
			    calculable : true,
			    series : [
			        {
			            name:'专利类型',
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
    	PatentStateChartOption : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		scope.statechart = {
    			title : {
			        text: '专利实施状态',
			        x:'left'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'right',
			        y : 'center',
			        data:legendData
			    },
			    calculable : true,
			    series : [
			        {
			            name:'专利实施状态',
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
    	PatentChangeSelectOption : function(data,scope){
    		var xdata = [];
    		var seriesObject = [];
    		var xAxisData = [];
    		if(data.length>0){
	    		for(var i=0;i<data[0].list.length;i++){
	    			xAxisData.push(data[0].list[i].year);
	    		}
    		}
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xdata.push(it.name);
				var dd = [];
				var series = {};
				for(var j=0;j<it.list.length;j++){
					var ity = it.list[j];
					dd.push(ity.value);
				}
				series = {
					name : it.name,
					type : 'line',
					data : dd
				}
				seriesObject.push(series);
			}
    		scope.changechart = {
    			tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:xdata
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
			    series : seriesObject
    		}
    	},
    	PatentDeptChartOption : function(data,scope){
    		var xdata = [];
    		var seriesObject = [];
    		var xAxisData = [];
    		if(data.length >0){
	    		for(var i=0;i<data[0].list.length;i++){
	    			xAxisData.push(data[0].list[i].dept);
	    		}
    		}
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xdata.push(it.name);
				var dd = [];
				var series = {};
				for(var j=0;j<it.list.length;j++){
					var ity = it.list[j];
					dd.push(ity.value);
				}
				series = {
					name : it.name,
					type : 'bar',
					data : dd
				}
				seriesObject.push(series);
			}
    		scope.deptchart = {
					tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:xdata
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
				    series : seriesObject
		    	}
    		}
    }
}]);
