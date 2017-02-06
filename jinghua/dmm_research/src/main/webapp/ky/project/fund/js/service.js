app.service("homeService", [ 'httpService', function(http) {
	return {
		/** 
		 *  科研项目经费总额
		 */
		queryProjectFundTotal : function(condition) {
			return http.post({
				url : "project/fund/total",
				data : {
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 
		 *  项目金额投入变化趋势
		 */
		queryFundTotalByYears : function(condition) {
			return http.post({
				url : "project/fund/trend",
				data : {
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 
		 *  各单位各级别项目投入总额
		 */
		queryFundTotalByDeptAndProjectLevel : function(condition) {
			return http.post({
				url : "project/fund/dept/level",
				data : {
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 
		 *  各级别项目投入总额
		 */
		queryFundTotalByProjectLevel : function(condition) {
			return http.post({
				url : "project/fund/level",
				data : {
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 
		 *  各单位项目投入平均额
		 */
		queryFundAvgByDept : function(condition) {
			return http.post({
				url : "project/fund/avg",
				data : {
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},
		
		generateChartData : function(data){
			var xAxisData = [],seriesData = [];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.name);
				seriesData.push(it.value);
			}
			return {
				xAxisData : xAxisData,
				seriesData : seriesData
			};
    	},
    	generateColumnChartData : function(data){
            var isName ={} , isField = {},legendData=[];
            var fields = [],series = [];
            for(var i in data){
                var tar = data[i];
                if(!isName[tar.name]){
                    series.push({type:'bar',name : tar.name,data : []});
                    legendData.push(tar.name);
                    isName[tar.name] = true;
                }
                if(!isField[tar.field]){
                    fields.push(tar.field);
                    isField[tar.field] = true;
                }
            }
            for ( var j in series) {
                var ser = series[j];
                for ( var k = 0; k < fields.length; k++) {
                    for(var m = 0; m < data.length; m++){
                        var dat = data[m];
                        if(dat.name == ser.name && dat.field == fields[k]){
                            ser.data.push(parseFloat(dat.value));
                            break;
                        }
                    }
                    if (ser.data.length <= k) {
                        ser.data.push(0);
                    }
                }
            }
            return {
            	series : series,
            	xAxisData : fields,
            	legendData : legendData
            }
    	},
    	generatePieConfig : function(title,data){
			var legendData = [];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name)
			}
			return option = {
				title : {
			        text: title, 
			        x:'left'
			    }, 
			    tooltip : {
			        trigger: 'item',
			        formatter: "{b} : {c} 万元 ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'right',
			        y: 'center',
			        data:legendData
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:title,
			            type:'pie',
			            startAngle: 180,
			            radius : '55%',
			            center: ['50%', '50%'],
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
} ]);
