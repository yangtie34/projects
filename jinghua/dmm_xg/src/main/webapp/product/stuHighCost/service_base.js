app.service("service",['httpService','setting',function(http, setting){
    return {
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : setting.advancedTag},
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    },
	    getStandardMap:function(schoolYear,termCode,callback){
			return http.post({
						url : setting.ctol+"/getStandardMap",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							},
							success : function(data) {
								var distribute = {};
								distribute.value = data.value;
								distribute.name = data.name;
								distribute.type = data.type;
								callback(distribute);
							}
					});
        },
	    
	    getCountByDept:function(schoolYear,termCode,month,column,asc,param,callback){
				return http.post({
							url : setting.ctol+"/getCountByDept",
							data : {
								"schoolYear":schoolYear,
								"termCode":termCode,
								"month":month,
								"column":column,
								"asc":asc,
								"param":param
								},
								success : function(data) {
									var distribute = {};
									distribute.result = data.result;
									callback(distribute);
								}
						});
	    },
	    
	    getLastMonthData:function(param,schoolYear,termCode,month){
			return http.post({
						url : setting.ctol+"/getLastMonthData",
						data : {
							"param":param,
							"schoolYear":schoolYear,
							"termCode":termCode,
							"month":month
							}
					});
        },
        
        getTermByGrade:function(schoolYear,termCode,param,callback){
			return http.post({
						url : setting.ctol+"/getTermByGrade",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.termGradeCfg = {
									name : "人",
									type : "pie",
									data : data.list,
									config:{
										title:{
											text:setting.xslb+'学生年级分布',
											show:false
										},
										noDataText:'暂无'+ setting.xslb+'学生年级分布',
										series:[{
											radius:['25%','64%'],
											roseType:'radius'
										}]
									}
								};
								callback(distribute);
							}
					});
        },
        
        getYearByGrade:function(start,end,param,callback){
			return http.post({
						url : setting.ctol+"/getYearByGrade",
						data : {
							"start":start,
							"end":end,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.yearGradeCfg = {
									name : "人次",
									type : "pie",
									data : data.list,
									config:{
										title:{
											text:setting.xslb+'学生年级分布',
											show:false
										},
										noDataText:'暂无'+ setting.xslb+'学生年级分布',
										series:[{
											radius:['25%','64%'],
											roseType:'radius'
										}]
									}
								};
								callback(distribute);
							}
					});
        }, 
        
        getTermBySex:function(schoolYear,termCode,param,callback){
			return http.post({
						url : setting.ctol+"/getTermBySex",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.termSexCfg = {
									name : "人",
									type : "pie",
									data : data.list,
									config:{
										title:{
											text:setting.xslb+'学生性别分布',
											show:false
										},	
										noDataText:'暂无'+ setting.xslb+'学生性别分布',
										series:[{
											radius:'64%'
										}]
									}
								};
								callback(distribute);
							}
					});
        },
        
        getYearBySex:function(start,end,param,callback){
			return http.post({
				url : setting.ctol+"/getYearBySex",
				data : {
					"start":start,
					"end":end,
					"param":param
					},
					success : function(data) {
						var distribute = {};
						distribute.yearSexCfg = {
							name : "人次",
							type : "pie",
							data : data.list,
							config:{
								title:{
									text:setting.xslb+'学生性别分布',
									show:false
								},	
								noDataText:'暂无'+ setting.xslb+'学生性别分布',
								series:[{
									radius:'64%'
								}]
							}
						};
						callback(distribute);
					}
			});
        },
        
        getTermBySubsidy:function(schoolYear,termCode,param,callback){
			return http.post({
						url : setting.ctol+"/getTermBySubsidy",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.termSubsidyCfg = {
										title:{
											text:'助学金与'+setting.xslb,
											show:false
										},	
//									name : "人",
//									type : "venn",
//									data : data.list
										 noDataLoadingOption:{
				            					effect : function(params) {
				            						params.start = function(h) {
				            							h._bgDom.innerText = '暂无获助学金'+ setting.xslb+'学生数据';
				            							h._bgDom.style.textAlign = 'center';
				            							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
				            							h._domRoot.style.backgroundRepeat = 'no-repeat';
				            							h._domRoot.style.backgroundPositionX = 'center';
				            							h._domRoot.style.backgroundPositionY = 'center';
				            							h._domRoot.style.backgroundSize = '53px 53px';
				            							h._bgDom.style.top = '60%';
				            							h._bgDom.style.width = '100%';
				            							params.stop = function() {
				                							h._bgDom.innerText = '';
				                							h._bgDom.style.textAlign = '';
				                							h._domRoot.style.backgroundImage = '';
				                							h._domRoot.style.backgroundRepeat = '';
				                							h._domRoot.style.backgroundPositionX = '';
				                							h._domRoot.style.backgroundPositionY = '';
				                						}
				            						}
				            						return params;
				            					}
				            				},
										 tooltip : {
					               		        trigger: 'item',
					               		        formatter: "{b} <br/> {c}人"
					               		    },
					       				    calculable : false,
					               		    series : [
					               		        {
					               		        	name:'',
					               		        	type:'venn',
					               		        	  itemStyle: {
					               		                 normal: {
					               		                     label: {
					               		                         show: true,
//					               		                         textStyle: {
//					               		                             fontFamily: 'Arial, Verdana, sans-serif',
//					               		                             fontSize: 16,
//					               		                             fontStyle: 'italic',
//					               		                             fontWeight: 'bolder'
//					               		                         }
					               		                     },
					               		                     labelLine: {
					               		                         show: false,
					               		                         length: 10,
					               		                         lineStyle: {
					               		                             // color: 各异,
					               		                             width: 1,
					               		                             type: 'solid'
					               		                         }
					               		                     }
					               		                 },
					               		                 emphasis: {
					               		                     color: '#cc99cc',
					               		                     borderWidth: 3,
					               		                     borderColor: '#996699'
					               		                 }
					               		             },
					               		            data: data.list
					               		        }
					               		    ]
								};
								distribute.list = data.list;
								distribute.scale = data.scale;
								distribute.count = data.count;
								callback(distribute);
							}
					});
        },
        
        getYearBySubsidy:function(start,end,param,callback){
			return http.post({
				url : setting.ctol+"/getYearBySubsidy",
				data : {
					"start":start,
					"end":end,
					"param":param
					},
					success : function(data) {
						var distribute = {};
						distribute.yearSubsidyCfg = {
								title:{
									text:'助学金与'+setting.xslb,
									show:false
								},	
//							name : "人",
//							type : "venn",
//							data : data.list
								 noDataLoadingOption:{
		            					effect : function(params) {
		            						params.start = function(h) {
		            							h._bgDom.innerText = '暂无获助学金'+ setting.xslb+'学生数据';
		            							h._bgDom.style.textAlign = 'center';
		            							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
		            							h._domRoot.style.backgroundRepeat = 'no-repeat';
		            							h._domRoot.style.backgroundPositionX = 'center';
		            							h._domRoot.style.backgroundPositionY = 'center';
		            							h._domRoot.style.backgroundSize = '53px 53px';
		            							h._bgDom.style.top = '60%';
		            							h._bgDom.style.width = '100%';
		            							params.stop = function() {
		                							h._bgDom.innerText = '';
		                							h._bgDom.style.textAlign = '';
		                							h._domRoot.style.backgroundImage = '';
		                							h._domRoot.style.backgroundRepeat = '';
		                							h._domRoot.style.backgroundPositionX = '';
		                							h._domRoot.style.backgroundPositionY = '';
		                						}
		            						}
		            						return params;
		            					}
		            				},
								 tooltip : {
			               		        trigger: 'item',
			               		        formatter: "{b} <br/> {c}人次"
			               		    },
			       				    calculable : false,
			               		    series : [
			               		        {
			               		        	name:'',
			               		        	type:'venn',
			               		        	  itemStyle: {
			               		                 normal: {
			               		                     label: {
			               		                         show: true,
//			               		                         textStyle: {
//			               		                             fontFamily: 'Arial, Verdana, sans-serif',
//			               		                             fontSize: 16,
//			               		                             fontStyle: 'italic',
//			               		                             fontWeight: 'bolder'
//			               		                         }
			               		                     },
			               		                     labelLine: {
			               		                         show: false,
			               		                         length: 10,
			               		                         lineStyle: {
			               		                             // color: 各异,
			               		                             width: 1,
			               		                             type: 'solid'
			               		                         }
			               		                     }
			               		                 },
			               		                 emphasis: {
			               		                     color: '#cc99cc',
			               		                     borderWidth: 3,
			               		                     borderColor: '#996699'
			               		                 }
			               		             },
			               		            data: data.list
			               		        }
			               		    ]
						};
						distribute.count = data.count;
						distribute.list = data.list;
						distribute.scale = data.scale;
						callback(distribute);
					}
			});
        },
        
        getTermByLoan:function(schoolYear,termCode,param,callback){
			return http.post({
						url : setting.ctol+"/getTermByLoan",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.termLoanCfg = {
										title:{
											text:'助学贷款与'+setting.xslb,
											show:false
										},	
//									name : "人",
//									type : "venn",
//									data : data.list
										 tooltip : {
					               		        trigger: 'item',
					               		        formatter: "{b} <br/> {c}人"
					               		    },
					               		 noDataLoadingOption:{
					            					effect : function(params) {
					            						params.start = function(h) {
					            							h._bgDom.innerText = '暂无获助学贷款'+setting.xslb+'学生数据';
					            							h._bgDom.style.textAlign = 'center';
					            							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
					            							h._domRoot.style.backgroundRepeat = 'no-repeat';
					            							h._domRoot.style.backgroundPositionX = 'center';
					            							h._domRoot.style.backgroundPositionY = 'center';
					            							h._domRoot.style.backgroundSize = '53px 53px';
					            							h._bgDom.style.top = '60%';
					            							h._bgDom.style.width = '100%';
					            							params.stop = function() {
					                							h._bgDom.innerText = '';
					                							h._bgDom.style.textAlign = '';
					                							h._domRoot.style.backgroundImage = '';
					                							h._domRoot.style.backgroundRepeat = '';
					                							h._domRoot.style.backgroundPositionX = '';
					                							h._domRoot.style.backgroundPositionY = '';
					                						}
					            						}
					            						return params;
					            					}
					            				},
					       				    calculable : false,
					               		    series : [
					               		        {
					               		        	name:'',
					               		        	type:'venn',
					               		        	  itemStyle: {
					               		                 normal: {
					               		                     label: {
					               		                         show: true,
//					               		                         textStyle: {
//					               		                             fontFamily: 'Arial, Verdana, sans-serif',
//					               		                             fontSize: 16,
//					               		                             fontStyle: 'italic',
//					               		                             fontWeight: 'bolder'
//					               		                         }
					               		                     },
					               		                     labelLine: {
					               		                         show: false,
					               		                         length: 10,
					               		                         lineStyle: {
					               		                             // color: 各异,
					               		                             width: 1,
					               		                             type: 'solid'
					               		                         }
					               		                     }
					               		                 },
					               		                 emphasis: {
					               		                     color: '#cc99cc',
					               		                     borderWidth: 3,
					               		                     borderColor: '#996699'
					               		                 }
					               		             },
					               		            data: data.list
					               		        }
					               		    ]
								};
								distribute.count = data.count;
								distribute.list = data.list;
								distribute.scale = data.scale;
								callback(distribute);
							}
					});
        },
        
        getYearByLoan:function(start,end,param,callback){
			return http.post({
				url : setting.ctol+"/getYearByLoan",
				data : {
					"start":start,
					"end":end,
					"param":param
					},
					success : function(data) {
						var distribute = {};
						distribute.yearLoanCfg = {
//							name : "人",
//							type : "venn",
//							data : data.list
								title:{
									text:'助学贷款与'+setting.xslb,
									show:false
								},	
			               		 noDataLoadingOption:{
		            					effect : function(params) {
		            						params.start = function(h) {
		            							h._bgDom.innerText = '暂无获助学贷款'+setting.xslb+'学生数据';
		            							h._bgDom.style.textAlign = 'center';
		            							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
		            							h._domRoot.style.backgroundRepeat = 'no-repeat';
		            							h._domRoot.style.backgroundPositionX = 'center';
		            							h._domRoot.style.backgroundPositionY = 'center';
		            							h._domRoot.style.backgroundSize = '53px 53px';
		            							h._bgDom.style.top = '60%';
		            							h._bgDom.style.width = '100%';
		            							params.stop = function() {
		                							h._bgDom.innerText = '';
		                							h._bgDom.style.textAlign = '';
		                							h._domRoot.style.backgroundImage = '';
		                							h._domRoot.style.backgroundRepeat = '';
		                							h._domRoot.style.backgroundPositionX = '';
		                							h._domRoot.style.backgroundPositionY = '';
		                						}
		            						}
		            						return params;
		            					}
		            				},
								 tooltip : {
			               		        trigger: 'item',
			               		        formatter: "{b} <br/> {c}人次"
			               		    },
			       				    calculable : false,
			               		    series : [
			               		        {
			               		        	name:'',
			               		        	type:'venn',
			               		        	  itemStyle: {
			               		                 normal: {
			               		                     label: {
			               		                         show: true,
//			               		                         textStyle: {
//			               		                             fontFamily: 'Arial, Verdana, sans-serif',
//			               		                             fontSize: 16,
//			               		                             fontStyle: 'italic',
//			               		                             fontWeight: 'bolder'
//			               		                         }
			               		                     },
			               		                     labelLine: {
			               		                         show: false,
			               		                         length: 10,
			               		                         lineStyle: {
			               		                             // color: 各异,
			               		                             width: 1,
			               		                             type: 'solid'
			               		                         }
			               		                     }
			               		                 },
			               		                 emphasis: {
			               		                     color: '#cc99cc',
			               		                     borderWidth: 3,
			               		                     borderColor: '#996699'
			               		                 }
			               		             },
			               		            data: data.list
			               		        }
			               		    ]
						};
						distribute.count = data.count;
						distribute.list = data.list;
						distribute.scale = data.scale;
						callback(distribute);
					}
			});
        },
        
        getTermByJm:function(schoolYear,termCode,param,callback){
			return http.post({
						url : setting.ctol+"/getTermByJm",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.termJmCfg = {
//									name : "人",
//									type : "venn",
//									data : data.list
										title:{
											text:'学费减免与'+setting.xslb,
											show:false
										},	
										 noDataLoadingOption:{
				            					effect : function(params) {
				            						params.start = function(h) {
				            							h._bgDom.innerText = '暂无获学费减免'+setting.xslb+'学生数据';
				            							h._bgDom.style.textAlign = 'center';
				            							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
				            							h._domRoot.style.backgroundRepeat = 'no-repeat';
				            							h._domRoot.style.backgroundPositionX = 'center';
				            							h._domRoot.style.backgroundPositionY = 'center';
				            							h._domRoot.style.backgroundSize = '53px 53px';
				            							h._bgDom.style.top = '60%';
				            							h._bgDom.style.width = '100%';
				            							params.stop = function() {
				                							h._bgDom.innerText = '';
				                							h._bgDom.style.textAlign = '';
				                							h._domRoot.style.backgroundImage = '';
				                							h._domRoot.style.backgroundRepeat = '';
				                							h._domRoot.style.backgroundPositionX = '';
				                							h._domRoot.style.backgroundPositionY = '';
				                						}
				            						}
				            						return params;
				            					}
				            				},
										 tooltip : {
					               		        trigger: 'item',
					               		        formatter: "{b} <br/> {c}人"
					               		    },
					       				    calculable : false,
					               		    series : [
					               		        {
					               		        	name:'',
					               		        	type:'venn',
					               		        	  itemStyle: {
					               		                 normal: {
					               		                     label: {
					               		                         show: true,
//					               		                         textStyle: {
//					               		                             fontFamily: 'Arial, Verdana, sans-serif',
//					               		                             fontSize: 16,
//					               		                             fontStyle: 'italic',
//					               		                             fontWeight: 'bolder'
//					               		                         }
					               		                     },
					               		                     labelLine: {
					               		                         show: false,
					               		                         length: 10,
					               		                         lineStyle: {
					               		                             // color: 各异,
					               		                             width: 1,
					               		                             type: 'solid'
					               		                         }
					               		                     }
					               		                 },
					               		                 emphasis: {
					               		                     color: '#cc99cc',
					               		                     borderWidth: 3,
					               		                     borderColor: '#996699'
					               		                 }
					               		             },
					               		            data: data.list
					               		        }
					               		    ]
								};
								distribute.count = data.count;
								distribute.scale = data.scale;
								distribute.list = data.list;
								callback(distribute);
							}
					});
        },
        
        getYearByJm:function(start,end,param,callback){
			return http.post({
				url : setting.ctol+"/getYearByJm",
				data : {
					"start":start,
					"end":end,
					"param":param
					},
					success : function(data) {
						var distribute = {};
						distribute.yearJmCfg = {
//							name : "人",
//							type : "venn",
//							data : data.list
								title:{
									text:'学费减免与'+setting.xslb,
									show:false
								},	
								 noDataLoadingOption:{
		            					effect : function(params) {
		            						params.start = function(h) {
		            							h._bgDom.innerText = '暂无获学费减免'+setting.xslb+'学生数据';
		            							h._bgDom.style.textAlign = 'center';
		            							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
		            							h._domRoot.style.backgroundRepeat = 'no-repeat';
		            							h._domRoot.style.backgroundPositionX = 'center';
		            							h._domRoot.style.backgroundPositionY = 'center';
		            							h._domRoot.style.backgroundSize = '53px 53px';
		            							h._bgDom.style.top = '60%';
		            							h._bgDom.style.width = '100%';
		            							params.stop = function() {
		                							h._bgDom.innerText = '';
		                							h._bgDom.style.textAlign = '';
		                							h._domRoot.style.backgroundImage = '';
		                							h._domRoot.style.backgroundRepeat = '';
		                							h._domRoot.style.backgroundPositionX = '';
		                							h._domRoot.style.backgroundPositionY = '';
		                						}
		            						}
		            						return params;
		            					}
		            				},
								 tooltip : {
			               		        trigger: 'item',
			               		        formatter: "{b} <br/> {c}人次"
			               		    },
			       				    calculable : false,
			               		    series : [
			               		        {
			               		        	name:'',
			               		        	type:'venn',
			               		        	  itemStyle: {
			               		                 normal: {
			               		                     label: {
			               		                         show: true,
//			               		                         textStyle: {
//			               		                             fontFamily: 'Arial, Verdana, sans-serif',
//			               		                             fontSize: 16,
//			               		                             fontStyle: 'italic',
//			               		                             fontWeight: 'bolder'
//			               		                         }
			               		                     },
			               		                     labelLine: {
			               		                         show: false,
			               		                         length: 10,
			               		                         lineStyle: {
			               		                             // color: 各异,
			               		                             width: 1,
			               		                             type: 'solid'
			               		                         }
			               		                     }
			               		                 },
			               		                 emphasis: {
			               		                     color: '#cc99cc',
			               		                     borderWidth: 3,
			               		                     borderColor: '#996699'
			               		                 }
			               		             },
			               		            data: data.list
			               		        }
			               		    ]
						};
						distribute.count = data.count;
						distribute.scale = data.scale;
						distribute.list = data.list;
						callback(distribute);
					}
			});
        },
        
        getTermByMeal:function(schoolYear,termCode,param,callback){
			return http.post({
						url : setting.ctol+"/getTermByMeal",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								distribute.termMealCfg = {
									name : "次",
									type : "pie",
									data : data.list,
									config:{
										title:{
											text:'各餐别'+setting.xslb+'分布',
											show:false
										},	
										noDataText:'暂无各餐别'+setting.xslb+'学生占比',
										series:[{
											radius:'64%'
										}]
									}
								};
								callback(distribute);
							}
					});
        },
        
        getYearByMeal:function(start,end,param,callback){
			return http.post({
				url : setting.ctol+"/getYearByMeal",
				data : {
					"start":start,
					"end":end,
					"param":param
					},
					success : function(data) {
						var distribute = {};
						distribute.yearMealCfg = {
							name : "次",
							type : "pie",
							data : data.list,
							config:{
								title:{
									text:'各餐别'+setting.xslb+'分布',
									show:false
								},
								noDataText:'暂无各餐别'+setting.xslb+'学生占比',
								series:[{
									radius:'64%'
								}]
							}
						};
						callback(distribute);
					}
			});
        },
        
        getTermCountDept:function(schoolYear,termCode,param,callback){
			return http.post({
				url : setting.ctol+"/getTermCountDept",
				data : {
					"schoolYear":schoolYear,
					"termCode":termCode,
					"param":param
					},
					success : function(data) {
						var list = data.list;
						var distribute = {};
						distribute.termDeptCfg = Ice.echartOption(list, {
							yname_ary : ['人', '%'], // 单位
							legend_ary : ['人数', '占比'], // 图例
							type_ary : ['bar', 'line'], // 图标类型
							value_ary : ['count', 'scale'],
							config:{
								title:{
									text:'各院系'+setting.xslb+'学生分布',
									show:false
								},
								noDataText:'暂无各院系'+setting.xslb+'学生分布与占比数据',
								xAxis : [{ axisLabel : { rotate : -15 } }]
							}
							});
						callback(distribute);
					}
			});
        },
        
        getYearCountDept:function(start,end,param,callback){
			return http.post({
				url : setting.ctol+"/getYearCountDept",
				data : {
					"start":start,
					"end":end,
					"param":param
					},
					success : function(data) {
						var list = data.list;
						var distribute = {};
						distribute.yearDeptCfg = Ice.echartOption(list, {
							yname_ary : ['人次', '%'], // 单位
							legend_ary : ['人次', '占比'], // 图例
							type_ary : ['bar', 'line'], // 图标类型
							value_ary : ['count', 'scale'],
						    config:{
						    	title:{
									text:'各院系'+setting.xslb+'学生分布',
									show:false
								},
							    noDataText:'暂无各院系'+setting.xslb+'学生分布与占比数据',
								xAxis : [{ axisLabel : { rotate : -15 } }]
							}
							});
						callback(distribute);
					}
			});
        },
        
        getGradeHistory:function(param,callback){
			return http.post({
				url : setting.ctol+"/getGradeHistory",
				data : {
					"param":param
					},
				success : function(data) {
						var list = data.list;
						var history = {};
						history.gradeHistoryCfg = Ice.echartOption(list.data, {
							yname_ary : ['人'], // 单位
							legend_ary : list.legend_ary, // 图例
							type_ary : ['line'], // 图标类型
							value_ary : list.value_ary,
						    config:{
						    	title:{
									text:'各年级'+setting.xslb+'学生人数变化',
									show:false
								},
								 noDataText:'暂无各年级'+setting.xslb+'学生人数变化数据',
								xAxis : [{ axisLabel : { rotate : -15 } }]
							}
							});
						callback(history);
					}
			});
        }, 
        
        getSexHistory:function(param,callback){
			return http.post({
				url : setting.ctol+"/getSexHistory",
				data : {
					"param":param
					},
			    success : function(data) {
						var list = data.list;
						var history = {};
						history.sexHistoryCfg = Ice.echartOption(list.data, {
							yname_ary : ['人'], // 单位
							legend_ary : list.legend_ary, // 图例
							type_ary : ['line'], // 图标类型
							value_ary : list.value_ary,
						    config:{
						    	title:{
									text:'各性别'+setting.xslb+'学生人数变化',
									show:false
								},
								noDataText:'暂无各性别'+setting.xslb+'学生人数变化数据',
								xAxis : [{ axisLabel : { rotate : -15 } }]
							}
							});
						callback(history);
					}
			});
        },
        
        getSubsidyHistory:function(param,callback){
			return http.post({
				url : setting.ctol+"/getSubsidyHistory",
				data : {
					"param":param
					},
				success : function(data) {
						var list = data.list;
						var history = {};
						history.subsidyHistoryCfg = Ice.echartOption(list, {
							yname_ary : ['人', '%'], // 单位
							legend_ary : ['人数', '占比'], // 图例
							type_ary : ['line', 'line'], // 图标类型
							value_ary : ['count', 'scale'],
						    config:{
								xAxis : [{ axisLabel : { rotate : -15 } }],
								title : {
							        text: '获助学金的'+setting.xslb+'学生人数与占比',
							        x:"center"
							    },
							    legend:{
							    	padding:20
							    }
							}
							});
						callback(history);
					}
			});
        }, 
        
        getLoanHistory:function(param,callback){
			return http.post({
				url : setting.ctol+"/getLoanHistory",
				data : {
					"param":param
					},
				success : function(data) {
						var list = data.list;
						var history = {};
						history.loanHistoryCfg = Ice.echartOption(list, {
							yname_ary : ['人', '%'], // 单位
							legend_ary : ['人数', '占比'], // 图例
							type_ary : ['line', 'line'], // 图标类型
							value_ary : ['count', 'scale'],
						    config:{
								xAxis : [{ axisLabel : { rotate : -15 } }],
								title : {
							        text: '获助学贷款的'+setting.xslb+'学生人数与占比',
							        x:"center"
							    },
							    legend:{
							    	padding:20
							    }
							}
							});
						callback(history);
					}
			});
        },
        
        getJmHistory:function(param,callback){
			return http.post({
				url : setting.ctol+"/getJmHistory",
				data : {
					"param":param
					},
				success : function(data) {
						var list = data.list;
						var history = {};
						history.jmHistoryCfg = Ice.echartOption(list, {
							yname_ary : ['人', '%'], // 单位
							legend_ary : ['人数', '占比'], // 图例
							type_ary : ['line', 'line'], // 图标类型
							value_ary : ['count', 'scale'],
						    config:{
								xAxis : [{ axisLabel : { rotate : -15 } }],
								title : {
							        text: '获学费减免的'+setting.xslb+'学生人数与占比',
							        x:"center"
							    },
							    legend:{
							    	padding:20
							    }
							}
							});
						callback(history);
					}
			});
        },
        
        getMealHistory:function(param,callback){
			return http.post({
				url : setting.ctol+"/getMealHistory",
				data : {
					"param":param
					},
				success : function(data) {
						var list = data.list;
						var history = {};
						history.mealHistoryCfg = Ice.echartOption(list.data, {
							yname_ary : ['次'], // 单位
							legend_ary : list.legend_ary, // 图例
							type_ary : ['line'], // 图标类型
							value_ary : list.value_ary,
						    config:{
						    	title:{
									text:'历年各餐别'+setting.xslb+'人次变化',
									show:false
								},	
								xAxis : [{ axisLabel : { rotate : -15 } }]
							}
							});
						callback(history);
					}
			});
        },
        getExportData:function(sendType,mc,schoolYear,termCode,month,pid,param){
			return http.post({
				url : setting.ctol+"/getExportData",
				data : {
					"sendType":sendType,
					"mc":mc,
					"schoolYear":schoolYear,
					"termCode":termCode,
					"month":month,
					"pid":pid,
					"param":param
					}
			});
        },
    	//全部发送
    	sendAll : function(callback){
    		http.post({
    			url  : setting.ctol+"/sendAll",
    			success : function(data){
    				callback(data);
    			}
    		});	    		
    	},
    	//全部导出
    	excelAll : function(callback){
    		http.post({
    			url  : setting.ctol+"/excelAll",
    			success : function(data){
    				callback(data);
    			}
    		});	    		
    	},
//    	getStuDetail : function(param,callback,error){
//			return http.post({
//				url : setting.pmsCtol+"/getStuDetail",
//				data : param,
//				success : function(data) {
//					var page = {};
//					page.total = data.total;
//					page.items = data.rows;
//					page.pagecount = data.pagecount;
//					callback(page);
//				},
//				error:error
//			});
//		},
		exportData:function(mc,schoolYear,termCode,month,pid,param,callback){
			http.fileDownload({
        		url  : setting.ctol+"/exportData",
        		data : {
					"mc":mc,
					"schoolYear":schoolYear,
					"termCode":termCode,
					"month":month,
					"pid":pid,
					"param":param
					},
        		success : function(){
        			alert("success");
        		}
        	})	
		},
		getNearLv : function(xnxqList,monthList,deptid,param,callback){
			return http.post({
				url : setting.ctol+"/getNearLv",
				data : {
					"xnxqList":xnxqList,
					"monthList":monthList,
					"deptid":deptid,
					"param":param
					},
				success : function(data) {
						var distribute = {};
						distribute.scaleLineCfg = {
								noDataText:'暂无近几月'+setting.xslb+'学生人数变化数据',
								name : "%",
								type : "line",
								data : data.scale,
								config:{
									title:{
										text:'近几月'+setting.xslb+'学生人数变化',
										show:false
									},
									  xAxis : [
									           {
									              name:'月',
									           }
									       ]
								}
							};
						distribute.changeLineCfg = {
								noDataText:'暂无近几月'+setting.xslb+'变化数据',	
								name : "人",
								type : "line",
								data : data.change,
								config:{
									title:{
										text:'近几月'+setting.xslb+'变化',
										show:false
									},
									  xAxis : [
									           {
									              name:'月',
									           }
									       ]
								}
							};
						callback(distribute);
					}
			});
		},
//		getStuDetailDown : function(param, callback){
//	        	http.fileDownload({
//	        		url  : setting.pmsCtol+"/down",
//	        		data : param,
//	        		success : function(){
//	        			alert("success");
//	        		}
//	        	})
//	        },
	       	/**
        	 * 格式化option数据（统一配置）
        	 */
        	formatOptionSetting : function(option, config){
            	// 初始化自定义配置
                if(config){
    	        	option = apply(option, config);
                }
                // 格式化series为暂无数据状态，echart会根据series[0].data == [] 初始化 冒气泡的动画
                if(option.series.length == 0) option.series[0] = {data:[]}; 
        		// 表格配置
        		var type = option.series[0] ? option.series[0].type : null;
        		switch (type){
	        		case 'line':     
	                case 'bar' :
	                case 'area':
	                case 'spline':
	                case 'areaspline':
	                	var grid = {
	                    	 y  : 60,
	                    	 y2 : 36,
	                    	 x  : 65,
	                    	 x2 : 65
//	                    	 left: '1%',
//	                         right: '1%',
//	                         bottom: '30px',
//	                         containLabel: true
	                    },
	                	legend = { padding : [5,55] },
	                	isAxisLabel = (option.xAxis[0] && option.xAxis[0].axisLabel) ? true : false, // X轴是否旋转
	                	isDataZoom  = option.dataZoom ? true : false; // 是否有时间轴
	                	// 没有图例的情况下，上边距 设置为40
	                	if(option.legend.data.length <= 0 || option.legend.show==false){
	                		grid.y = 40;
	                	}
	                	// x轴数据选转时，下边距设置为 65
	                	if(isAxisLabel || isDataZoom){
	                		grid.y2 = 60;
	                	}
	                	// 有时间轴的情况 || 旋转
	                	if(isDataZoom && isAxisLabel){
	                		grid.y2 = 80;
	                	}
	                	var baseCfg = {
                			legend : legend,
	                		grid   : grid
	                	};
	                	option = apply(baseCfg,option);
	                	break;
        		}
        		/**
        		 * 重大修复： 20160616
        		 * 以前的写法是：option = apply(option, {grid:grid});
        		 * 现在的写法是：option = apply({grid:grid}, option);
        		 * 以前将属性复制到option上，现在将option复制到{grid:grid}上，所以必须返回option
        		 */
        		return option;
        	},    
    }
}]);