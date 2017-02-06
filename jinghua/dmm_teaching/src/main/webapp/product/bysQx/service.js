app.service("service", ['httpService', function(http) {
	return{
		getTimeList:function() {
			return http.post({
				url : "bysQx/getTimeList",
			});
        },
        getBysQxFb:function(param,schoolYear,callback){
    		return http.post({
				url : "bysQx/getBysQxFb",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					distribute.pieQxCfg = {
							type : "pie",
							data : data,
							name : '人',
							config:{
								title:{
									text:'毕业生去向分布',
									show:false
								},
								 calculable : true,
								 legend:{
								 	show:true,
								 	orient : 'vertical',
								    x : 'left',
								 },
								 toolbox:{
								 	show:true
								 },
								 noDataText:'暂无毕业生去向分布数据',
								 series:[{
									 	radius:'60%',
									    center:['50%','54%']
									 }]
							}
						};
					callback(distribute);
				}
			});
		},
		getBysQxSzFb:function(param,schoolYear,callback){
    		return http.post({
				url : "bysQx/getBysQxSzFb",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					distribute.pieSzCfg = {
							type : "pie",
							data : data,
							name : '人',
							config:{
								title:{
									text:'毕业生升造去向分布',
									show:false
								},
								 calculable : true,
								 legend:{
								 	show:true,
								 	orient : 'vertical',
								    x : 'left',
								 },
								 toolbox:{
								 	show:true
								 },
								 noDataText:'暂无毕业生升造去向分布数据',
								 series:[{
									 	radius:'60%',
									    center:['50%','54%']
									 }]
							}
						};
					callback(distribute);
				}
			});
		},
		getLnBysQxfb:function(param,schoolYear,callback){
			return http.post({
				url : "bysQx/getLnBysQxfb",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.barQxCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['占比'], // 单位
								type_ary : ['bar'], // 图标类型
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								config:{
									title:{
										text:'近五年毕业生去向占比',
										show:false
									},
									noDataText:'暂无近五年毕业生去向占比数据',
									legend:{
										x:'center'
									},
									yAxis:[{
										max:100
									}]
								}
						});
					var stack = distribute.barQxCfg.series;
					var xAxis = distribute.barQxCfg.xAxis;
					var yAxis = distribute.barQxCfg.yAxis;
					distribute.barQxCfg.tooltip.formatter =  function(params){
	    				        	var html = '';
	    				            for(var i=0,len=params.length; i<len; i++){
	    				            	var obj = params[i];
	    				                html += '<br>'+obj.seriesName+'：'+obj.value+'%';
	    				            }
	    				            return  params[0].name + html;
	    				        };
					var xAxis_data = [];
					for (var i = 0; i < stack.length; i++) {
						stack[i].stack = '总量';
					}
					for (var j = 0; j < list.data.length; j++) {
						var obj = list.data[j];
						xAxis_data.push(obj.name);
					}
					yAxis[0].data = xAxis_data;
					delete yAxis[0].name;
					yAxis[0].name = '学年',
					yAxis[0].type = 'category';
					xAxis[0].type = 'value';
					xAxis[0].name = '占比';
					yAxis[0].axisLabel = {rotate : 25};
					delete xAxis[0].data;
					callback(distribute);
				}
			});
		},
		getLnBysSzQxfb:function(param,schoolYear,callback){
			return http.post({
				url : "bysQx/getLnBysSzQxfb",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.barSzCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['占比'], // 单位
								type_ary : ['bar'], // 图标类型
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								config:{
									title:{
										text:'近五年毕业生升造去向占比',
										show:false
									},
									noDataText:'暂无近五年升造学生去向占比数据',
									legend:{
										x:'center'
									}
								}
						});
					var stack = distribute.barSzCfg.series;
					var xAxis = distribute.barSzCfg.xAxis;
					var yAxis = distribute.barSzCfg.yAxis;
					distribute.barSzCfg.tooltip.formatter =  function(params){
	    				        	var html = '';
	    				            for(var i=0,len=params.length; i<len; i++){
	    				            	var obj = params[i];
	    				                html += '<br>'+obj.seriesName+'：'+obj.value+'%';
	    				            }
	    				            return  params[0].name + html;
	    				        };
					var xAxis_data = [];
					for (var i = 0; i < stack.length; i++) {
						stack[i].stack = '总量';
					}
					for (var j = 0; j < list.data.length; j++) {
						var obj = list.data[j];
						xAxis_data.push(obj.name);
					}
					yAxis[0].data = xAxis_data;
					delete yAxis[0].name;
					yAxis[0].name = '学年',
					yAxis[0].type = 'category';
					xAxis[0].type = 'value';
					xAxis[0].name = '占比';
					xAxis[0].scale=true;
					xAxis[0].max=100;
					yAxis[0].axisLabel = {rotate : 25};
					delete xAxis[0].data;
					callback(distribute);
				}
			});
		},
		getLnReasonfb:function(param,schoolYear,callback){
			return http.post({
				url : "bysQx/getLnReasonfb",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.barReasonCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['%'], // 单位
								type_ary : ['bar'], // 图标类型
								xname_ary : ['学年'], // 单位
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								stack : 'a',
								config:{
									title:{
										text:'历年未就业主要原因占比',
										show:false
									},
									noDataText:'暂无历年未就业主要原因占比数据',
									legend:{
										x:'center'
									},
									yAxis:[{
										type : 'value',
										scale:true,
										max:100
									}]
//									xAxis: [{
//									    axisLabel: {
//									    	rotate : -15
//									    }
//									}]
								}
						});
					callback(distribute);
				}
			});
		},
		getScaleByDept:function(param,schoolYear,callback){
			return http.post({
				url : "bysQx/getScaleByDept",
				data:{
					"param":param,
					"schoolYear":schoolYear
				},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					var name = data.name;
					distribute.name = name;
					distribute.barDeptCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['%'], // 单位
								xname_ary : ['学年'], // 单位
								type_ary : ['bar'], // 图标类型
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								stack : 'a',
								config:{
									title:{
										text:'各'+name+'毕业生去向对比',
										show:false
									},
									noDataText:'暂无各'+name+'毕业生去向对比数据',
									legend:{
										x:'center'
									},
									yAxis:[{
										type : 'value',
										scale:true,
										max:100
									}]
//									xAxis: [{
//									    axisLabel: {
//									    	rotate : -15
//									    }
//									}]
								}
						});
					callback(distribute);
				}
			});
		},
		getStuListDown : function(param, callback){
        	http.fileDownload({
        		url  : "pmsBysQx/down",
        		data : param,
        		success : function(){
        			alert("success");
        		}
        	})
        },
		getStuList : function(param,callback,error) {
			return http.post({
						url : "pmsBysQx/getStuList",
						data :param,
						success : function(data) {
							var page = {};
							page.total = data.total;
							page.items = data.rows;
							page.pagecount = data.pagecount;
							callback(page);
						},
						error:error
					});
		 },
    	getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : {
							tag : "Teaching_bysQx"
						},
						success : function(data) {
							callback(data);
						}
					})
		}
	}
}]);