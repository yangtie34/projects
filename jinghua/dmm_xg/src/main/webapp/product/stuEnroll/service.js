app.service("service", ['httpService', function(http) {
	return {

		getStuCountByDept : function(param) {
			return http.post({
						url : "stuEnroll/getStuCountByDept",
						data : {"param":param},
					});
		},
		getGraduateStuCount : function(param) {
			return http.post({
						url : "stuEnroll/getGraduateStuCount",
						data : {"param":param}
					});
		},
		getBsCount : function(param) {
			return http.post({
						url : "stuEnroll/getBsCount",
						data : {"param":param}
					});
		},
		getStuDetail : function(param,callback,error){
			return http.post({
				url : "pmsStuEnroll/getStuDetail",
				data : param,
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
		getStuDetailDown : function(param, callback){
	        	http.fileDownload({
	        		url  : "pmsStuEnroll/down",
	        		data : param,
	        		success : function(){
	        			alert("success");
	        		}
	        	})
	        },
		getStuCountBySex : function(param, callback) {
			return http.post({
						url : "stuEnroll/getStuCountBySex",
						data : {"param":param},
						success : function(data) {
							var distribute = {};
							distribute.sexCfg = {
								type : "pie",
								data : data.sex,
								name : '人',
								config:{
								   title:{
								    	text:'在籍生性别分布',
								    	show:false
								    },
									 calculable : true,
									 legend:{
									 	show:true
									 },
									 toolbox:{
									 	show:true
									 },
									 noDataText:'暂无在籍生性别分布数据',
									 series:[{
									 	radius:'60%',
									    center:['50%','54%']
									 }]
								}
							};
							distribute.stateCfg = {
									type : "pie",
									data : data.state,
									name : '人',
									config:{
										title:{
									    	text:'在籍生状态',
									    	show:false
									     },
										noDataText:'暂无学生状态分布数据',
										 calculable : true,
										 legend:{
										 	show:true
										 },
										 toolbox:{
										 	show:true
										 },
										 series:[{
										 	radius:'60%',
										    center:['50%','50%']
										 }]
									}
								};
							 distribute.styleCfg = {
										type : "pie",
										data : data.style,
										name : '人',
										config:{
											title:{
										    	text:'在籍生学习方式分布',
										    	show:false
										     },
											noDataText:'暂无学习方式分布数据',
											 calculable : true,
											 legend:{
											 	show:true
											 },
											 toolbox:{
											 	show:true
											 },
											 series:[{
											    name:'学习方式',
											 	radius:'60%',
											    center:['50%','50%']
											 }]
										}
									};
							   distribute.formCfg = {
										type : "pie",
										data : data.form,
										name : '人',
										config:{
											title:{
										    	text:'在籍生学习形式分布',
										    	show:false
										     },
											noDataText:'暂无学习形式分布数据',
											 legend:{
											 	show:true
											 },
											 toolbox:{
											 	show:true
											 },
											 series:[{
												name:'学习形式',
												roseType:'radius',
											 	radius : ['30%','60%'],
											    center:['50%','50%']
											 }]
										}
									};
							callback(distribute);
						}
					});
		},
		getStuCountByAge : function(param, callback) {
			return http.post({
						url : "stuEnroll/getStuCountByAge",
						data : {"param":param},
						success : function(data) {
							var distribute = {};
							var age = data.age;
							distribute.ageCfg = Ice.echartOption(age.data, {
								yname_ary : ['人'], // 单位
								type_ary : ['line'], // 图标类型
								legend_ary : age.legend_ary, // 图例
								value_ary : age.value_ary,
								config:{
									title:{
									    	text:'在籍生年龄分布',
									    	show:false
									   },
									legend:{
										formatter:function(p){
											var name = p;
											return name+"级";
										}
									},
									noDataText:'暂无在籍生年龄分布数据',
									xAxis: [{
									    axisLabel: {
									    	rotate : -45
									    }
									}]
								}
									// value所对应字段
									
								});
								var ser = distribute.ageCfg.series;
								for (var i=0;i<ser.length;i++){
									ser[i].itemStyle={normal: {areaStyle: {type: 'default'}}};
								}
							callback(distribute);
						}
					});
		},
		getStuCountLine : function(param, callback) {
			return http.post({
						url : "stuEnroll/getStuCountLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							history.stateLineCfg = {
								type : "line",
								data : data.line,
								name : '人',
								config:{
									title:{
								    	text:'学生总数变化趋势',
								    	show:false
								     },
									noDataText:'暂无历年学生人数变化数据',
									xAxis:[{
									name:'学年'	
									}],
									yAxis:[{
										 min:parseInt(data.min),
									//	 splitNumber:parseInt(data.split),
										 scale:true
									}],
									legend:{
									show:false
								}
								}
							};
							callback(history);
						}
					});
		},
		getContrastByDept : function(param, callback) {
			http.post({
						url : "stuEnroll/getContrastByDept",
						data : {"param":param},
						success : function(data) {
							var distribute = {};
							var contrast = data.contrast;
							var i = contrast.data.length;
							var x = (12/i)*100;
							distribute.name = data.name;
							distribute.contrastCfg = Ice.echartOption(
									contrast.data, {
										yname_ary : ['人'], // 单位
										type_ary : ['bar'], // 图标类型
										legend_ary : contrast.legend_ary, // 图例
										value_ary : contrast.value_ary,
										// value所对应字段
										config:{
											title:{
										    	text:'各'+data.name+'在籍生分布',
										    	show:false
										     },
											noDataText:'暂无各'+data.name+'学生分布数据',
											xAxis : [{ axisLabel : { rotate : -15 } }],
											dataZoom:{
												show: true,
										        start : 0,
										        end : x
											}
										}
								});
							var stack = distribute.contrastCfg.series;
							for (var i = 0; i < stack.length; i++) {
								stack[i].stack = '总量';
							}
							callback(distribute);

						}
					});
		},
		getContrastByPolitics : function(param, callback) {
			http.post({
						url : "stuEnroll/getContrastByPolitics",
						data : {"param":param},
						success : function(data) {
							var distribute = {};
							var politics = data.politics;
							distribute.politicsCfg = Ice.echartOption(
									politics.data, {
										yname_ary : ['人'], // 单位
										type_ary : ['bar'], // 图标类型
										legend_ary : politics.legend_ary, // 图例
										value_ary : politics.value_ary,
										config:{
											title:{
										    	text:'在籍生政治面貌分布',
										    	show:false
										     },
											noDataText:'暂无政治面貌分布数据',
											legend:{
												x:'left',
												padding:[30,40,0,0]
											},
										    toolbox:{
										        orient:'vertical'
									        }
//									        tooltip:{
//									        	   formatter : function(params){
//						    				        	var html = '';
//						    				            for(var i=0,len=params.length; i<len; i++){
//						    				            	var obj = params[i];
//						    				                html += '<br>'+obj.name+'：'+obj.value+'人';
//						    				            }
//						    				            return  params[0].indicator + html;
//						    				        }
//									        }
										
										}
								});
							var stack = distribute.politicsCfg.series;
							var xAxis = distribute.politicsCfg.xAxis;
							var yAxis = distribute.politicsCfg.yAxis;
							distribute.politicsCfg.tooltip.formatter =  function(params){
			    				        	var html = '';
			    				            for(var i=0,len=params.length; i<len; i++){
			    				            	var obj = params[i];
			    				                html += '<br>'+obj.seriesName+'：'+obj.value+'人';
			    				            }
			    				            return  params[0].name + html;
			    				        };
							var xAxis_data = [];
							for (var i = 0; i < stack.length; i++) {
								stack[i].stack = '总量';
							}
							for (var j = 0; j < politics.data.length; j++) {
								var obj = politics.data[j];
								xAxis_data.push(obj.name);
							}
							yAxis[0].data = xAxis_data;
							delete yAxis[0].name;
							yAxis[0].type = 'category';
							xAxis[0].type = 'value';
							xAxis[0].name = '人';
							delete xAxis[0].data;
							distribute.tuan = data.tuan;
							distribute.fei = data.fei;
							distribute.wwh = data.wwh;
							callback(distribute);

						}
					});
		},
		getStuFrom : function(param, callback) {
			http.post({
				url : "stuEnroll/getStuFrom",
				data : {"param":param},
				success : function(data) {
					var distribute = {};
					distribute.fromCfg = {
					     mapType:data.mapType,
						 data:data.from,
						 type:'map',
						 config:{
							 title:{
							    	text:'在籍生生源地分布',
							    	show:false
							     },	
							 dataRange:{
								show : true,
								min:0,
							 	max:data.max,
							 	itemHeight:8,
							    x: '20%',
						        y: 'bottom',
						        text:['高','低'],           
						        calculable : true
							 },
						     noDataText:'暂无在籍生生源地数据',
							 series:[{
							 mapLocation:{
	//							x:'30%',
	//						 	y:'11%',
							 	height:'85%'
							 }
						 }]
					}
					};
					distribute.gat = data.gat;
					distribute.q = data.q;
					distribute.gatcode = data.gatcode;
					distribute.qcode = data.qcode;
					var i = data.nation.length;
					var x = (5/i)*100;
					var dataZoom= x==100?{}:{show: true,start : 0,end : x};
					distribute.nationCfg = {
						type : "bar",
						data : data.nation,
						config:{
						    title:{
						    	text:'少数民族组成',
						    	show:false
						    },
							noDataText:'暂无民族组成数据',
							tooltip:{
							formatter:"{b} <br/> {c}人"
							},
							toolbox:{
								 orient:'vertical'
							},
							
							dataZoom:dataZoom
						}
					};
					distribute.han = data.han;
					distribute.shaoshu = data.shaoshu;
					distribute.wwh = data.wwh;
					callback(distribute);

				}
			});
		},
		getStuFromLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getStuFromLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.fromLineCfg = Ice.echartOption(line.data, {
								yname_ary : ['人'], // 单位
								type_ary : ['line'], // 图标类型
								xname_ary : ['学年'],
								legend_ary : line.legend_ary, // 图例
								value_ary : line.value_ary,
								config:{
									title:{
								    	text:'各地区学生总数变化趋势',
								    	show:false
								     },
									noDataText:'暂无各地区学生总数变化趋势数据'
								}
								});
							callback(history);

						}
					});
		},
		getStuNationLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getStuNationLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.nationLineCfg = Ice.echartOption(line.data,
									{
										yname_ary : ['人'], // 单位
										type_ary : ['line'], // 图标类型
										xname_ary : ['学年'],
										legend_ary : line.legend_ary, // 图例
										value_ary : line.value_ary,
										config:{
											title:{
										    	text:'汉族少数民族学生总数变化趋势',
										    	show:false
										     },
											noDataText:'暂无各民族学生总数变化趋势数据'
										}
										// value所对应字段
								});
							callback(history);

						}
					});
		},
		getStuSexLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getStuSexLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.sexLineCfg = Ice.echartOption(line.data, {
								yname_ary : ['人'], // 单位
								type_ary : ['line'], // 图标类型
								xname_ary : ['学年'],
								legend_ary : line.legend_ary, // 图例
								value_ary : line.value_ary,
								config:{
									title:{
								    	text:'各性别学生总数变化趋势',
								    	show:false
								     },
									noDataText:'暂无各性别学生总数变化趋势数据'
								}// value所对应字段
								});
							callback(history);

						}
					});
		},
		getStuAgeLine : function(param, callback) {
			return http.post({
						url : "stuEnroll/getStuAgeLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							history.ageLineCfg = {
								type : "line",
								data : data.line,
								name : '岁',
								config : {
									title:{
								    	text:'新生入学时平均年龄变化趋势',
								    	show:false
								     },
									noDataText:'暂无历年新生入学时平均年龄变化趋势数据',
									tooltip : {
										formatter : "{b} <br/> 平均年龄：{c}岁"
									},
									xAxis:[{
									 name:'学年'
									}],
									yAxis:[{
									 min:15,
									 scale:true
									}],
									series:[{
									    name:'平均年龄'
									}]
								}
							};
							callback(history);
						}
					});
		},
		getPoliticsLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getPoliticsLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.politicsLineCfg = Ice.echartOption(
									line.data, {
										yname_ary : ['人'], // 单位
										type_ary : ['line'], // 图标类型
										xname_ary : ['学年'],
										legend_ary : line.legend_ary, // 图例
										value_ary : line.value_ary,
										// value所对应字段
										config:{
											title:{
										    	text:'各政治面貌学生总数变化趋势',
										    	show:false
										     },
											noDataText:'暂无各政治面貌学生总数变化趋势数据',
											toolbox:{
											   orient:'vertical'
										       }
										}
										
								});
							callback(history);
						}
					});
		},
		getDeptLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getDeptLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.name = data.name;
							history.deptLineCfg = Ice.echartOption(line.data, {
								yname_ary : ['人'], // 单位
								xname_ary : ['学年'],
								type_ary : ['line'], // 图标类型
								legend_ary : line.legend_ary, // 图例
								value_ary : line.value_ary,
									// value所对应字段
								config:{
									title:{
								    	text:'各'+data.name+'学生总数变化趋势',
								    	show:false
								     },
									noDataText:'暂无各'+data.name+'学生总数变化趋势数据',
									legend:{
										padding:[0,100,40,40],
										selected:data.legend
									},
									grid:{
										y:'20%'
									},
									toolbox:{
										orient:'vertical'
									}
								}
								});
							callback(history);
						}
					});
		},
		getStyleLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getStyleLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.styleLineCfg = Ice.echartOption(line.data,
									{
										yname_ary : ['人'], // 单位
										type_ary : ['line'], // 图标类型
									    xname_ary : ['学年'],
										legend_ary : line.legend_ary, // 图例
										value_ary : line.value_ary,
										config:{
											title:{
										    	text:'各学习方式学生人数变化趋势',
										    	show:false
										     },
											noDataText:'暂无各学习方式学生人数变化趋势数据',
										}
										// value所对应字段
								});
							callback(history);
						}
					});
		},
		getFormLine : function(param, callback) {
			http.post({
						url : "stuEnroll/getFormLine",
						data : {"param":param},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.formLineCfg = Ice.echartOption(line.data, {
								yname_ary : ['人'], // 单位
								xname_ary : ['学年'], // 单位
								type_ary : ['line'], // 图标类型
								legend_ary : line.legend_ary, // 图例
								value_ary : line.value_ary,
								config:{
									title:{
								    	text:'各学习形式学生人数变化趋势',
								    	show:false
								     },
									noDataText:'暂无各学习形式学生人数变化趋势数据',
								}
									// value所对应字段
								});
							callback(history);
						}
					});
		},
		getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_stuEnroll" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }
	}
}]);