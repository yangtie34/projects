app.service("service", ['httpService', function(http) {
	return {

		getMinGrade : function(param) {
			return http.post({
						url : "stuFrom/getMinGrade",
						data : {"param":param}
					});
		},
		getStuEdu : function(param) {
			return http.post({
						url : "stuFrom/getStuEdu",
						data : {"param":param}
					});
		},
		getStuFrom : function(param, from, to, edu, divid, updown, pagesize,
				index, bili, len, callback) {
			return http.post({
				url : "stuFrom/getStuFrom",
				data : {
					"param" : param,
					"from" : from,
					"to" : to,
					"edu" : edu,
					"divid" : divid,
					"updown" : updown,
					"pagesize" : pagesize,
					"index" : index
				},
				success : function(data) {
					var distribute = {};
					var oData = [];
					var markpoint = "";
					var list = data.pie;
					var count = 0;
					if (list.length > 0) {
						for (var i = 0; i < list.length; i++) {
							count += parseInt(list[i].value);
						}
						if (eval(list[0].value / count * 100) >= bili
								&& list.length >= len) {
							for (var j = 1; j < list.length; j++) {
								oData.push(list[j]);
							}
							markpoint = {
								symbol : 'circle',
								data : [{
									x : '10%',
									y : '20%',
									clickable : false,
									symbolSize : 15,
									tooltip : {
										show : false
									},
									itemStyle : {
										normal : {
											label : {
												position : 'top',
												formatter : function(p) {
													var a = list[0].name;
													var b = parseInt(list[0].value);
													var c = eval(list[0].value
															/ count * 100)
															.toFixed(2);
													return a + ':' + b + '人'
															+ '\n(占比' + c
															+ '%)';
												}
											}
										}
									}
								}]
							}
						} else {
							markpoint = "";
							oData = list;
						}
					}
					distribute.fromCfg = {
						mapType : data.maptype,
						type : "map",
						data : data.list,
						config : {
							title:{
						    	text:'生源地分布',
						    	show:false
						     },
							noDataText:'暂无生源地分布数据',
							dataRange : {
								show : true,
								x : '10%',
								y : '60%',
								max : data.max,
								itemWidth : 15,
								itemHeight : 10
							}
						}
					};
					distribute.fromPieCfg = {
						name : '人',
						type : "pie",
						data : oData,
						isMapData : true,
						config : {
							title:{
						    	text:'生源地分布',
						    	show:false
						     },
							legend : {
									show : true,
									x : 'right',
									orient : 'vertical',
									y : 'center'
								},
								noDataText:'暂无生源地分布数据',
								calculable : true,
								series : [{
											markPoint : markpoint,
											radius : '50%',
											center : ['40%', '55%']
										}]
						}
					};
					distribute.list = data.list;
					distribute.pie = data.pie;
					distribute.sum = data.sum;
					distribute.sxq = data.sxq;
					distribute.sxqid = data.sxqid;
					distribute.avg = data.avg;
					distribute.sub = data.sub;
					distribute.mapt = data.maptype;
					distribute.cc = data.cc;
					callback(distribute);
				}
			});
		},
		getCountLine : function(param, from, to, edu, divid, callback) {
			return http.post({
						url : "stuFrom/getCountLine",
						data : {
							"param" : param,
							"from" : from,
							"to" : to,
							"edu" : edu,
							"divid" : divid
						},
						success : function(data) {
							var distribute = {};
							distribute.countLineCfg = {
								name : "人",
								type : "line",
								data : data.line,
								noDataText:'暂无历年来校人数变化趋势数据',
								config:{
									title:{
								    	text:'历年来校人数变化趋势',
								    	show:false
								     }
								}
							};
							callback(distribute);
						}
					});
		},
		getSchoolTag : function(param, from, to, edu, divid, updown, pagesize,
				index, Order, callback) {
			return http.post({
						url : "stuFrom/getSchoolTag",
						data : {
							"param" : param,
							"from" : from,
							"to" : to,
							"edu" : edu,
							"divid" : divid,
							"updown" : updown,
							"pagesize" : pagesize,
							"index" : index,
							"Order" : Order
						},
						success : function(data) {
							var page = {};
							page.total = data.total;
							page.items = data.list;
							callback(page);
						}
					});
		},
		getGrowth : function(param, from, to, edu, divid, updown,callback) {
			return http.post({
						url : "stuFrom/getGrowth",
						data : {
							"param" : param,
							"from" : from,
							"to" : to,
							"edu" : edu,
							"divid" : divid,
							"updown" : updown
						},
						success : function(data) {
							var page = {};
							page.fromLineCfg = Ice.echartOption(data.line, {
								yname_ary : ['人', '%'], // 单位
								xname_ary : ['学年'], // 单位
								legend_ary : ['同比增长', '同比增幅'], // 图例
								type_ary : ['line', 'line'], // 图标类型
								value_ary : ['val1', 'val2'],
								config:{
									title:{
								    	text:'历年全国同比增长和同比增幅变化趋势',
								    	show:false
								     },
									noDataText:'暂无历年同比增长和同比增幅变化趋势数据'
								}
									// value所对应字段
								});
							page.fromLineCfg.xAxis[0].axisLine = {};
							page.fromLineCfg.xAxis[0].axisLine.onZero = false;
							page.items = data.list;
							page.xl = data.xl;
							callback(page);
						}
					});
		},
		getCountAndLv : function(param,from,to, edu, divid,name,callback) {
			return http.post({
						url : "stuFrom/getCountAndLv",
						data : {
							"param" : param,
							"from" : from,
							"to" : to,
							"edu" : edu,
							"divid" : divid,
						},
						success : function(data) {
							var page = {};
							page.countLvCfg = Ice.echartOption(data.line, {
								yname_ary : ['人', '%'], // 单位
								xname_ary : ['年'], // 单位
								legend_ary : ['人数', '占比'], // 图例
								type_ary : ['bar', 'line'], // 图标类型
								value_ary : ['count', 'lv'],
								config:{
									title:{
								    	text:'历年'+name+'来校人数，占比变化',
								    	show:false
								     },
									noDataText:'暂无历年'+name+'来校人数，占比变化数据'
								}
									// value所对应字段
								});
							callback(page);
						}
					});
		},
		getStuFromTab : function(param, from, to, edu, divid, updown, pagesize,
				index, callback) {
			return http.post({
						url : "stuFrom/getStuFromTab",
						data : {
							"param" : param,
							"from" : from,
							"to" : to,
							"edu" : edu,
							"divid" : divid,
							"updown" : updown,
							"pagesize" : pagesize,
							"index" : index
						},
						success : function(data) {
							var page = {};

							page.total = data.total;
							page.items = data.list;
							callback(page);
						}
					});
		},
		getStuList : function(param,callback,error) {
			return http.post({
						url : "pmsStuFrom/getStuList",
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
							tag : "Xg_stuFrom"
						},
						success : function(data) {
							callback(data);
						}
					})
		},
		 getStuListDown : function(param, callback){
	        	http.fileDownload({
	        		url  : "pmsStuFrom/down",
	        		data : param,
	        		success : function(){
	        			alert("success");
	        		}
	        	})
	        },
	   	 getExportData : function(param,from,to,edu,divid,updown,bs,fields,headers,fileName, callback){
	        	http.fileDownload({
	        		url  : "pmsStuFrom/getExportData",
	        		data : {
	        			"param":param,
	        			"from":from,
	        			"to":to,
	        			"edu":edu,
	        			"divid":divid,
	        			"updown":updown,
	        			"bs":bs,
	        			"fields":fields,
	        			"headers":headers,
	        			"fileName":fileName,
	        		},
	        		success : function(){
	        			alert("success");
	        		}
	        	})
	        },    
	}
}]);