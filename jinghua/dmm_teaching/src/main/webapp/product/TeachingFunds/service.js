app.service("service", ['httpService', function(http) {
	return {
		getTeaFundsYear:function() {
			return http.post({
				url : "teachingFunds/getTeaFundsYear",
			});
		},
		getTeaFundsByAll:function(year,callback) {
			http.post({
				url : "teachingFunds/getTeaFundsByAll",
				data : {"year":year,
				},
				success : function(data) {
					callback(data.funds);
					}
			});
		},
		getDataByLastYear:function(year,callback){
			//获取去年
			year1=year-1;
			http.post({
				url : "teachingFunds/getTeaFundsByAll",
				data : {"year":year1,
				},
				success : function(data) {
					callback(data.funds);
					}
			});
			
		},
		//学校各项教学经费分布
		getTeaFundsBySty : function(year,callback) {
			http.post({
						url : "teachingFunds/getTeaFundsBySty",
						data : {"year":year
						},
						success : function(data) {
							var distribute = {};
							distribute.fundsCfg = {
								type : "pie",
								data : data.funds,
								name : '万元',
								config:{
									 title:{text : '教学经费分布',show:false},
									 calculable : true,
									 legend:{
									 	show:true
									 },
									 toolbox:{
									 	show:true
									 },
									 noDataText:'暂无教学经费分布数据',
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
		//学校各项教学经费发展趋势
		getTeaFundsLine : function(year,callback) {
			http.post({
						url : "teachingFunds/getTeaFundsLine",
						data : {"year":year,
							},
						success : function(data) {
							var history = {};
							var line = data.line;
							history.fundsLineCfg = Ice.echartOption(line.data, {
								yname_ary : ['万元'], // 单位
								type_ary : ['line'], // 图标类型
								xname_ary : ['年'],
								legend_ary : line.legend_ary, // 图例
								value_ary : line.value_ary,
								xname_append:true,
								config:{
									title : {text : '教学经费支出趋势',show : false},
									noDataText:'暂无各教学经费变化趋势数据'
								}// value所对应字段
								});
							callback(history);
					}
			});
		},
		getTeaFundsByCount : function(year,callback) {
			http.post({
						url : "teachingFunds/getTeaFundsByCount",
						data : {"year":year,
							},
						success : function(data) {
							var distribute = {};
							var fundsCount = data.bar;
							distribute.fundsCountCfg = Ice.echartOption(
									fundsCount.data, {
										yname_ary : ['万元'], // 单位
										xname_ary : ['年'],
										type_ary : ['bar'], // 图标类型
										legend_ary : fundsCount.legend_ary, // 图例
										value_ary : fundsCount.value_ary,
										xname_append:true,
										config:{
											title : {text : '历年教学经费分析（按金额）',show : false},
											noDataText:'暂无经费支出分布数据',
											legend:{
												x:'center',
												padding:[30,40,0,0]
											},
										    toolbox:{
										        orient:'vertical'
									        }										
										}
								});
							var stack = distribute.fundsCountCfg.series;
							var xAxis = distribute.fundsCountCfg.xAxis;
							var yAxis = distribute.fundsCountCfg.yAxis;
							distribute.fundsCountCfg.tooltip.formatter =  function(params){
			    				        var html = '';
			    				        for(var i=0,len=params.length; i<len; i++){
			    				           var obj = params[i];
			    				            html += '<br>'+obj.seriesName+'：'+obj.value+'万元';
			    				            }
			    				            return  params[0].name + html;
			    				        };
							var xAxis_data = [];
							for (var i = 0; i < stack.length; i++) {
								stack[i].stack = '总量';
							}
							for (var j = 0; j < fundsCount.data.length; j++) {
								var obj = fundsCount.data[j];
								xAxis_data.push(obj.name+"年");//形式表示为 2016年.
							}
							callback(distribute);
					}
			});
		},
		getTeaFundsByZB : function(year,callback) {
			http.post({
						url : "teachingFunds/getTeaFundsByZB",
						data : {"year":year,
							},
						success : function(data) {
							var distribute = {};
							var fundsZB = data.bar;
							distribute.fundsZBCfg = Ice.echartOption(
									fundsZB.data, {
										yname_ary : ['%'], // 单位
										xname_ary : ['年'], // 单位
										type_ary : ['bar'], // 图标类型
										xname_append:true,
										yname_append:true,
										legend_ary : fundsZB.legend_ary, // 图例
										value_ary : fundsZB.value_ary,
										config:{
											title : {text : '历年教学经费分析（按占比）',show : false},
											noDataText:'暂无经费占比数据',
											legend:{
												x:'center',
												padding:[30,40,0,0]
											},
										    toolbox:{
										        orient:'vertical'
									        }										
										}
								});
							var stack = distribute.fundsZBCfg.series;
							var xAxis = distribute.fundsZBCfg.xAxis;
							var yAxis = distribute.fundsZBCfg.yAxis;
							yAxis[0].max=100;
							distribute.fundsZBCfg.tooltip.formatter =  function(params){
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
							for (var j = 0; j < fundsZB.data.length; j++) {
								var obj = fundsZB.data[j];
								xAxis_data.push(obj.name+"年");//形式表示为 2016年.
							}
							callback(distribute);
					}
			});
		},
		getTeaFundsAvg : function(year,callback) {
			http.post({
						url : "teachingFunds/getTeaFundsAvg",
						data : {"year":year,
							},
						success : function(data) {
							var collegeAvg=data.collegeAvg,collegeAvg_ary=[],collegeName_ary=[];
							var schoolAvg=data.schoolAvg,schoolAvg_ary=[];
							for(var i=0;i<collegeAvg.length;i++){
								collegeName_ary[i]=collegeAvg[i].deptName;
								collegeAvg_ary[i]=collegeAvg[i].avg;
								schoolAvg_ary[i]=schoolAvg.avg;
							}
							var school=data.school,college=data.college;
					option = {
						title : {text : '生均教学经费分析',show : false},
					    tooltip : {trigger: 'axis',formatter: function(params){
				        	var res = params[0].name;
				        	for(var i=0;i<params.length;i++){
				        		res+='<br/>'+params[i].seriesName+' : '+params[i].value+'元';
				        	}
				        	return res;
				        } },
						toolbox: { show : true, feature : { mark : {show: true}, dataView : {show: true, readOnly: false}, magicType: {show: true, type: ['line', 'bar']}, restore : {show: true}, saveAsImage : {show: true}}  },
						calculable : true,
						legend : { data:[college,school] },
						xAxis  : [{type : 'category', data : collegeName_ary }],
						yAxis  : [{ type : 'value', name : '元'},],
						series : [{ name:school, type:'line', data:schoolAvg_ary},{ name:college, type:'bar', data:collegeAvg_ary }]
							};
					callback(option);
					}
			});
		},
		getTeaFundsByDept : function(year,callback) {
			http.post({
						url : "teachingFunds/getTeaFundsByDept",
						data : {"year":year,
							},
						success : function(data) {
							var distribute = {};
							var bar = data.bar;
							distribute.fundsBarCfg = Ice.echartOption(bar.data, {
								yname_ary : ['万元'], // 单位
								type_ary : ['bar'], // 图标类型
								xname_ary : [''],
								legend_ary : bar.legend_ary, // 图例
								value_ary : bar.value_ary,
								config:{
									title : {text : '各'+data.title+'教学经费',show : false},
									legend:{
										x:'center',
										padding:[30,40,0,0]
									},
									noDataText:'暂无各'+data.title+'教学经费信息'
								}// value所对应字段
								});
							distribute.title=data.title;
							callback(distribute);
					}
			});
		},
	}
}]);