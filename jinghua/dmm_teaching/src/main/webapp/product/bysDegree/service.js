app.service("service", ['httpService', function(http) {
	return{
		getTimeList:function() {
			return http.post({
				url : "bysDegree/getTimeList",
			});
        },
    	getThList:function() {
			return http.post({
				url : "bysDegree/getThList",
			});
        },
        getTopData:function(param,schoolYear,callback){
    		return http.post({
				url : "bysDegree/getTopData",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					callback(data);
				}
			});
		},
		getTableData:function(param,schoolYear,Lx,order,asc,pagesize,index,callback){
    		return http.post({
				url : "bysDegree/getTableData",
				data:{
					"param":param,
					"schoolYear":schoolYear,
					"Lx":Lx,
					"order":order,
					"asc":asc,
					"pagesize":pagesize,
					"index":index
				},
				success : function(data) {
					callback(data);
				}
			});
		},
		getLvByDept:function(param,schoolYear,callback){
			return http.post({
				url : "bysDegree/getLvByDept",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.name = data.name;
					distribute.barDeptCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['%'], // 单位
								type_ary : ['bar'], // 图标类型
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								config:{
									title:{
										text:'各'+data.name+'毕业，学位授予率',
										show:false
									},
									noDataText:'暂无各'+data.name+'毕业，学位授予率数据',
									legend:{
										x:'left'
									},
									yAxis:[{
										max:100
									}]
								}
						});
					callback(distribute);
				}
			});
		},
		getBysScore:function(param,schoolYear,Lx,type,callback){
			return http.post({
				url : "bysDegree/getBysScore",
				data:{
					"param":param,
					"schoolYear":schoolYear,
					"Lx":Lx,
					"type":type
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.barScoreCfg = {
							type : "bar",
							data : data.list,
							config:{
								title:{
									text:'历年毕业生成绩',
									show:false
								},
								noDataText:'暂无历年毕业生成绩数据',
								xAxis:[{
									name:'学年'
								}],
								yAxis:[{
									name: type == 'score' ? '分':'绩点'	
								}]
							}		
					}
					callback(distribute);
				}
			});
		},
		getBysGpa:function(param,schoolYear,type,callback){
			return http.post({
				url : "bysDegree/getBysGpa",
				data:{
					"param":param,
					"schoolYear":schoolYear,
					"type":type
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.barGpaCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['%'], // 单位
								type_ary : ['bar'], // 图标类型
								xname_ary : ['学年'], // 单位
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								stack : 'a',
								config:{
									title:{
										text:'历年毕业生绩点分布',
										show:false
									},
									noDataText:'暂无历年毕业生绩点分布数据',
									legend:{
										x:'left'
									},
									yAxis:[{
										type : 'value',
										scale:true,
										max:100
									}]
									
								}
						});
					callback(distribute);
				}
			});
		},
		getBySyLvList:function(param,Lx,callback){
			return http.post({
				url : "bysDegree/getBySyLvList",
				data:param,
				success : function(data) {
					var distribute = {};
					var list = data;
					distribute.lineCfg = {
							type : "line",
							data : list,
							config:{
								title:{
									text:'历年'+Lx+'变化',
									show:false
								},
								noDataText:'暂无历年'+Lx+'数据',
								xAxis:[{
									name:'学年',
									axisLabel:{
										rotate:-15
									}
								}],
					            yAxis:[{
					            	name:'%'
					            }]
							}		
					}
					callback(distribute);
				}
			});
		},
    	getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : {
							tag : "Teaching_bysDegree"
						},
						success : function(data) {
							callback(data);
						}
					})
		}
	}
}]);