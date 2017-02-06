app.service("service",['httpService',function(http){
    return {
    	
    	ctol : 'punish',
    	pmsCtrl:'pmsPunish',

    	getBzdm : function(){
    		return http.post({
    			url  : this.ctol+"/getBzdm"
    		});
    	},

    	/**
    	 * 摘要
    	 */
    	getAbstract : function(start_year, end_year, edu, param){
    		return http.post({
    			url  : this.ctol+"/getAbstract",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			}
    		});
    	},
    	getStuDetail : function(param,callback,error){//下钻学生信息
			http.post({
				url : this.pmsCtrl+"/getStuDetail",
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

    	/**
    	 * 类型-分布
    	 */
    	getDistribution : function(start_year, end_year, edu, param, callback){
	    	http.post({
	    		url  : this.ctol+"/getDistribution",
	    		data : {
	    			start_year : start_year,
	    			end_year   : end_year,
    				edu   : edu,
    				param : param
	    		},
	    		success : function(data){
	    			var volate     = data.violate,
	    				volateType = data.violateType,
	    				punish     = data.punish;
	    			var volate_data = [], volateType_data = [], punish_data = [], obj;
	    			for(var i=0,len=volate.length; i<len; i++){
	    				obj = volate[i];
	    				volate_data.push({value:obj.value,name:obj.name,code:obj.id});
	    			}
	    			for(var i=0,len=volateType.length; i<len; i++){
	    				obj = volateType[i];
	    				volateType_data.push({value:obj.value,name:obj.name,code:obj.code});
	    			}
	    			for(var i=0,len=punish.length; i<len; i++){
	    				obj = punish[i];
	    				punish_data.push({value:obj.value,name:obj.name,code:obj.code});
	    			}
	    			
	    			var option_volate = {
    				    tooltip : {
    				        trigger: 'item',
    				        formatter: "{a} <br/>{b} : {c}人次 (占比{d}%)"
    				    },
						toolbox: {
							show : true,
							feature : {
								dataView : {show: true, readOnly: false},
								restore : {show: true},
								saveAsImage : {show: true}
							}
						},
	    				series : [{
	    					name : '违纪分类',
	    					type : 'pie',
	    					radius : [0,60],
	    					center : ['50%','53%'],
	    		            itemStyle : {
	    		                normal : {
	    		                    label : { position : 'inner',formatter : "{b}\n{d}%" },
	    		                    labelLine : { show : false }
	    		                }
	    		            },
	    		            data : volateType_data
	    				},{
	    					name : '违纪类型',
	    					type : 'pie',
	    					radius : [75, 110],
	    					center : ['50%','53%'],
	    					data  : volate_data,
	    		            itemStyle : {
	    		                normal : {
	    		                    label : { formatter : "{b}({d}%)" },
	    		                }
	    		            }
	    				}],
	    				config : {
	    					title : {text: '违纪分布',show : false},
	    					noDataText : '暂无违纪分类数据'
	    				}
	    			};
	    			var option_punish = {
    					type : "pie",
    					data : punish,
    					name : '人次',
    					config : {
    						series : [{
    							radius : 115,
    	    					center : ['50%','53%']
    						}],
	    					title : {text: '处分分布',show : false},
	    					noDataText : '暂无处分分类数据'
    					}
	    			}
	    			callback(option_volate, option_punish);
	    		}
	    	});
	    },

		/**
		 * 组织机构-人次
		 */
    	getDeptDataList : function(start_year, end_year, edu, param, callback){
    		http.post({
    			url  : this.ctol+"/getDeptDataList",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
			    	callback(data.list, data.bzdm_type, data.bzdm_target, data.deptMc);
    			}
    		});
    	},
    	
    	getDeptOptionByKeyList : function(list, key_list, deptMc){
    		var cfg = {
				yname_ary : ['人次'], // 单位
				type_ary  : ['bar'], // 图标类型
				key_list  : key_list,
				stack  : 'a',
				config : {
					title : {text: '违纪处分'+deptMc+'分布',show : false},
					xAxis  : [{ axisLabel : { rotate : -15 } }],
					legend : {
//						x : 'left',
//						padding : [5,65]
					},
					noDataText : '暂无各'+deptMc+'分布数据'
				}
			}
    		return Ice.echartOption(list, cfg);
    	},

    	/**
    	 * 组织机构-人数/比例
    	 */
    	getDeptCountScaleList : function(start_year, end_year, edu, param, callback){
    		http.post({
    			url  : this.ctol+"/getDeptCountScaleList",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				callback(data.list);
    			}
    		});
    	},
    	
    	getDeptCountScaleOption : function(list, key_prefix,deptMc){
    		var cfg = {
				legend_ary: ['人数','比例'], // 单位
				yname_ary : ['人','%'], // 单位
				type_ary  : ['bar','line'], // 图标类型
				value_ary : [key_prefix+'_count', key_prefix+'_scale'],
				config : {
					title : {text:'违纪处分'+deptMc+'分布',show : false},
					xAxis  : [{ axisLabel : { rotate : -15 } }],
					legend : {
//						x : 'left',
//						padding : [5,60]
					}
				}
			}
    		return Ice.echartOption(list, cfg);
    	},

    	/**
    	 * 月份
    	 */
    	getMonthList : function(start_year, end_year, edu, param, callback){
    		http.post({
    			url  : this.ctol+"/getMonthList",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				var cfg = {
    					legend_ary : ['违纪','处分'],
    					yname_ary  : ['人次'],
    					xname_ary  : ['月'],
    					xname_append : true,
    					type_ary   : ['bar'],
    					config : {
    						title :{text:'违纪处分月份分布',show:false},
    						legend : {
    							selected : { '处分':false },
//								x : 'left',
//								padding : [5,60]
    						},
    						noDataText : '暂无月份分布数据'
    					}
    				};
    				callback(Ice.echartOption(data.list, cfg));
    			}
    		});
    	},

    	/**
    	 * 年级
    	 */
    	getGrade : function(start_year, end_year, edu, param, callback){
    		http.post({
    			url  : this.ctol+"/getGrade",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				var cfg = {
    					legend_ary : ['违纪','处分'],
    					yname_ary  : ['%'],
    					xname_append : true,
    					type_ary   : ['bar'],
    					config : {
    						title :{text:'违纪处分年级分布',show:false},
    						legend : {
    							selected : { '处分':false },
								x : 'left',
								padding : [5,40]
    						},
    						noDataText : '暂无年级分布数据'
    					}
    				};
    				callback(Ice.echartOption(data.list, cfg));
    			}
    		})
    	},

    	/**
    	 * 年龄
    	 */
    	getAge : function(start_year, end_year, edu, param, callback){
    		http.post({
    			url  : this.ctol+"/getAge",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				var cfg = {
    					legend_ary : ['违纪','处分'],
    					yname_ary  : ['人次'],
    					xname_append : true,
    					type_ary   : ['bar'],
    					config : {    
    						title :{text:'违纪处分年龄分布',show:false},
    						legend : {
    							selected : { '处分':false },
								x : 'left',
								padding : [5,50]
    						},
    						noDataText : '暂无年龄分布数据'
    					}
    				};
    				callback(Ice.echartOption(data.list, cfg));
    			}
    		})
    	},

    	/**
    	 * 二次异动
    	 */
    	getPunishAgain : function(edu, param, callback){
    		http.post({
    			url  : this.ctol+"/getPunishAgain",
    			data : {
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				/*var cfg = {
    					type : 'line',
    					name : '%',
    					data : Ice.changeName2Field(data, '', '')
    				}
    				callback(cfg);*/
    				var cfg = {
    					yname_ary  : ['%'],
    					xname_ary  : ['学年'],
    					type_ary   : ['line'],
    					config:{
        					title :{ text:'处分学生二次处分比例',show:false},
    					},
    					noDataText : '暂无二次处分数据'
    				};
    				callback(Ice.echartOption(data, cfg));
    			}
    		});
    	},
    	 getStuDetailDown : function(param, callback,error){
         	http.fileDownload({
         		url  : this.pmsCtrl+"/down",
         		data : param,
         		success : function(){
         			alert("success");
         		},
         	error:error
         	})
         },
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_punish" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    },
	    /**
    	 * 增加内容
    	 */
	    getLiForAdd : function(start_year, end_year, edu, param){
    		return http.post({
    			url  : this.ctol+"/getLiForAdd",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				param : param
    			}
    		});
    	},
	    getSexAndGradeAndOther : function(start_year, end_year, edu,tag ,param, callback){
    		http.post({
    			url  : this.ctol+"/getSexAndGradeAndOther",
    			data : {
    				start_year : start_year,
    				end_year   : end_year,
    				edu   : edu,
    				tag   : tag,
    				param : param
    			},
    			success : function(data){
    				var vioList=data.pie.violate;
    				var punList=data.pie.punish;
    				var itemStyle ={
                            normal : {
                                labelLine : {
                                    length : 0,
                                 },
                             }
                         };
    				for(var i in vioList){
    					vioList[i].itemStyle=itemStyle;
    				}
    				for(var i in punList){
    					punList[i].itemStyle=itemStyle;
    				}
    				var cfg_violate = {
        					yname_ary  : ['%'],
        					xname_append : true,
        					type_ary   : ['bar'],
        					config : {
        						title : {text:'违纪比例',show:false},
        						grid:{zlevel :100,z:100,y:16,y2:23,width:'75%',height:'70%'},
        					    //grid:{y:28,y2:15},
        					    toolbox: {
        					    	padding:[0,15],
        					        show : true,
        					        feature : {
        					            mark : {show: false},
        					            dataView : {show: true, readOnly: false},
        					            magicType : {show: false, type: ['stack','tiled']},
        					            restore : {show: true},
        					            saveAsImage : {show: true}
        					        }
        					    },
        						
        						noDataText : '暂无数据'
        					}
        				};
    				var cfg_punish = {
        					yname_ary  : ['%'],
        					xname_append : true,
        					type_ary   : ['bar'],
        					config : {
        						title : {text:'处分比例',show:false},
        						toolbox: {
        					    	padding:[0,15],
        					        show : true,
        					        feature : {
        					            mark : {show: false},
        					            dataView : {show: true, readOnly: false},
        					            magicType : {show: false, type: ['stack','tiled']},
        					            restore : {show: true},
        					            saveAsImage : {show: true}
        					        }
        					    },
            					    grid:{zlevel :100,z:100,y:16,y2:23,width:'75%',height:'70%'},
        						noDataText : '暂无数据'
        					}
        				};
    				var option_violate = {
        					type : "pie",
        					data : vioList,
        					name : '人次',
        					config : {
        						title : {text:'违纪分布',show:false},
        						series : [{
        							//radius : 60,
        	    					center : ['50%','53%']
        						}],
    	    					noDataText : '暂无处分分类数据'
        					}
    	    			}
    				var option_punish = {
        					type : "pie",
        					data : punList,
        					name : '人次',
        					config : {
        						title : {text:'处分分布',show:false},
        						series : [{
        							//radius : 60,
        	    					center : ['50%','53%']
        						}],
    	    					noDataText : '暂无处分分类数据'
        					}
    	    			}
    				callback(Ice.echartOption(data.tree.violate, cfg_violate),option_violate,Ice.echartOption(data.tree.punish, cfg_punish),option_punish);
    			}
    		})
    	},

	    
    }
}]);