app.service("service", [ 'httpService', function(http) {
	return {
		//查询考核主题list
		queryKhztList : function(){
			return http.post({
				url : "gd/mbrws/khztlist",
				data : {}
			});
		},
		//查询考核计划list
		queryKhjhList : function(){
			return http.post({
				url : "gd/mbrws/khjhlist",
				data : {}
			});
		},
		//查询学科排名
		queryXkpm : function(condition){
			return http.post({
				url : "gd/mbrws/xkpm",
				data : {
					zzjgid : condition.khdw.id,
					khjhid : condition.khjh.id
				}
			});
		},
		//查询考核主题结果
		queryKhzt : function(condition){
			return http.post({
				url : "gd/mbrws/khzt",
				data : {
					khztid : condition.khztid,
					zzjgid : condition.khdw.id,
					khjhid : condition.khjh.id
				}
			});
		},
		packChartConfigOfKhxm : function(khxm){
			var xAxisData = [],finishNum = [],finishPersent = [],targetNum = [], tempNum = 0,khmb = Number(khxm.khmb);
			for (var i = 0; i < khxm.detail.length; i++) {
				var it = khxm.detail[i];
				xAxisData.push(it.khnf);
				finishNum.push(Number(it.khjg));
				finishPersent.push(((tempNum+=Number(it.khjg))*100/khmb).toFixed(2));
				targetNum.push(khxm.khmb);
			}
			if(khxm.val_type == 'number'){
				khxm.chartConfig = {
						title: {
		                    text: khxm.khdw+khxm.khxm+'完成情况',
		                    left : 'left'
		                },
	    			    tooltip: {
	    			        trigger: 'axis'
	    			    },
	    			    legend: {
	    			    	x: 'center',
	    	                y : 'top',
	    	                padding : [30,0],
	    			        data:['完成量','完成率(%)']
	    			    },
	    			    xAxis: [
	    			        {
	    			            type: 'category',
	    			            data: xAxisData,
	    			            axisLabel : {
	    			            	interval : 0,
	    			            }
	    			        }
	    			    ],
	    			    yAxis: [
	    			        {
	    			            type: 'value',
	    			            name: '完成量',
	    			            min: 0,
	    			            axisLabel: {
	    			                formatter: '{value}'
	    			            }
	    			        },
	    			        {
	    			            type: 'value',
	    			            name: '完成率(%)',
	    			            min: 0,
	    			            axisLabel: {
	    			                formatter: '{value}%'
	    			            }
	    			        }
	    			    ],
	    			    series: [
	    			        {
	    			            name:'完成量',
	    			            type:'bar',
	    			            data: finishNum
	    			        },
	    			        {
	    			            name:'完成率(%)',
	    			            type:'line',
	    			            smooth : true,
	    			            yAxisIndex: 1,
	    			            data: finishPersent
	    			        }
	    			    ]
	    			};
			}else if(khxm.val_type == 'persent'){
				khxm.chartConfig = {
						title: {
		                    text: khxm.khdw+khxm.khxm+'完成情况',
		                    left : 'left'
		                },
	    			    tooltip: {
	    			        trigger: 'axis'
	    			    },
	    			    legend: {
	    			    	x: 'center',
	    	                y : 'top',
	    	                padding : [30,0],
	    			        data:['实际完成比例(%)','目标值(%)']
	    			    },
	    			    xAxis: [
	    			        {
	    			            type: 'category',
	    			            data: xAxisData,
	    			            axisLabel : {
	    			            	interval : 0,
	    			            }
	    			        }
	    			    ],
	    			    yAxis: [
	    			        {
	    			            type: 'value',
	    			            name: '完成比例(%)',
	    			            min: 0,
	    			            axisLabel: {
	    			                formatter: '{value}%'
	    			            }
	    			        } 
	    			    ],
	    			    series: [
	    			        {
	    			            name:'实际完成比例(%)',
	    			            type:'line',
	    			            smooth : true,
	    			            data: finishNum
	    			        },
	    			        {
	    			            name:'目标值(%)',
	    			            type:'line',
	    			            data: targetNum
	    			        }
	    			    ]
	    			};
			}
		}
	}
} ]);
