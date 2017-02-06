app.service("homeService", [ 'httpService', function(http) {
	return {
		/** 查询在研项目数量  */
		queryGoingOnProjectNums : function(condition) {
			return http.post({
				url : "project/progress/goingon/nums",
				data : {
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 查询各单位不同状态的项目数 */
		queryProjectNumsByDeptAndState : function(condition) {
			return http.post({
				url : "project/progress/depts/state",
				data : {
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 科研项目超期时长分布 */
		queryTimeoutProjectNums : function(condition) {
			return http.post({
				url : "project/progress/timeout",
				data : {
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 科研项目状态组成 */
		queryProjectNumsByState : function(condition) {
			return http.post({
				url : "project/progress/state",
				data : {
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 各单位科研项目到期完成率排名 */
		queryOrderByDept : function(page,condition) {
			return http.post({
				url : "project/progress/order/dept",
				data : {
					pagesize : page.size,
					curpage : page.index,
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		/** 单位 主持人完成率排名 */
		queryOrderByCompere : function(page,condition) {
			return http.post({
				url : "project/progress/order/compere",
				data : {
					pagesize : page.size,
					curpage : page.index,
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},
		
		queryTimeOutProjectList : function(page,condition) {
			return http.post({
				url : "project/progress/timeout/projectlist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					level : condition.level.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
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
    	generatePieConfig : function(title,data){
			var legendData = [];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name);
			}
			return option = {
				title : {
			        text: title, 
			        x:'left'
			    }, 
			    tooltip : {
			        trigger: 'item',
			        formatter: "{b} : {c} ({d}%)"
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
