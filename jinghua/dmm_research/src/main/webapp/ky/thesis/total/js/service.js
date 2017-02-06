app.service("service",['httpService',function(http){
    return {
    	//各类论文发表总数 （需要包含上一年度（段））
    	queryThesisTotalNums : function(condition){ 
    		return http.post({
    			url : "thesis/publish/total",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.deptTeah.id 
    			}
    		});
    	},
    	//以我校为第一单位发表 ,非第一单位发表
    	queryFirstAuthThesisNums : function(condition){
    		return http.post({
    			url : "thesis/publish/fistdept",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.deptTeah.id 
    			}
    		});
    	},
    	//查询各院系发表论文总数及占学校比例
    	queryThesisNumsAndPersByDept : function(condition){
    		return http.post({
    			url : "thesis/publish/depts",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end
    			}
    		});
    	},
    	//单位毎年论文发表量和占比
    	queryThesisNumsAndPersByYear : function(condition){
    		return http.post({
    			url : "thesis/publish/years",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.deptTeah.id 
    			}
    		});
    	},
    	//各引文索引库论文发表量
    	queryYwsykLwfbl : function(condition){
    		return http.post({
    			url : "thesis/publish/qklbfbl",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.deptTeah.id 
    			}
    		});
    	},
    	//各部门论文发表量下钻点击事件
    	queryNumsList : function(condition,page,params){
    		return http.post({
    			url : "thesis/publish/list",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.deptTeah.id,
    				name : params.name,
    				flag : params.flag,
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount
    			}
    		})
    	},
    	generateYearChangeOfAllConfig : function(data,scope){
    		var xAxisData = [], publishNum = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.name);
				publishNum.push(Number(it.nums));
			}
    		scope.yearChangeOfAllConfig = {
    				title :{
    					text : "论文发表总量年度变化趋势"
    				},
    			    tooltip: {
    			        trigger: 'axis'
    			    },
    			    xAxis: [
    			        {
    			            type: 'category',
    			            data: xAxisData,
    			            axisLabel : {
    			            	interval : 0,
    			            	rotate : (xAxisData.length > 8 ? 15 : 0)
    			            }
    			        }
    			    ],
    			    yAxis: [
    			        {
    			            type: 'value',
    			            name: '篇数',
    			            min: 0,
    			            axisLabel: {
    			                formatter: '{value}篇'
    			            }
    			        } 
    			    ],
    			    series: [
    			        {
    			            name:'发表量',
    			            type:'line',
    			            smooth : true,
    			            data:publishNum
    			        }
    			    ]
    			};
    		
    	},
    	generateMiddleChartOption : function(data,scope){
    		var xAxisData = [], publishNum = [],persentOfSchool = [],total = 0;
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.name);
				publishNum.push(it.nums);
				total += Number(it.nums);
			}
    		
    		for (var i = 0; i < publishNum.length; i++) {
    			var it = Number(publishNum[i]);
    			persentOfSchool.push((it*100/(total==0?1:total)).toFixed(2));
			}
    		scope.mainchart = {
    				title : {
    					text : scope.mainchartTitle
    				},
    			    tooltip: {
    			        trigger: 'axis'
    			    },
    			    legend: {
    			    	x: 'center',
    	                y : 'top',
    	                padding : [30,0],
    			        data:['发表量','占全校发表总数的比例(%)']
    			    },
    			    dataZoom : {
        		        show : xAxisData.length > 8 ? true : false,
        		        realtime : true,
        		        start : 0,
        		        end : xAxisData.length > 8 ? 30 : 100
        		    },
    			    xAxis: [
    			        {
    			            type: 'category',
    			            data: xAxisData,
    			            axisLabel : {
    			            	interval : 0,
    			            	rotate : (xAxisData.length > 5 ? 15 : 0)
    			            }
    			        }
    			    ],
    			    yAxis: [
    			        {
    			            type: 'value',
    			            name: '篇数',
    			            min: 0,
    			            axisLabel: {
    			                formatter: '{value}篇'
    			            }
    			        },
    			        {
    			            type: 'value',
    			            name: '占比',
    			            min: 0,
    			            axisLabel: {
    			                formatter: '{value}%'
    			            }
    			        }
    			    ],
    			    series: [
    			        {
    			            name:'发表量',
    			            type:'bar',
    			            data:publishNum
    			        },
    			        {
    			            name:'占全校发表总数的比例(%)',
    			            type:'line',
    			            smooth : true,
    			            yAxisIndex: 1,
    			            data:persentOfSchool
    			        }
    			    ]
    			};
    	},
    	generateBottomPieChart : function(data,scope){
    		var legendData = [];
    		for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
    		
    		scope.piechart = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'horizontal',
			        left : 'left',
			        width : "90%",
			        data:legendData
			    },
			    series : [
			        {
			            name:'访问来源',
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
