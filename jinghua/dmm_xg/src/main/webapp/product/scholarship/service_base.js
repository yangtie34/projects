app.service("service",['httpService','setting',function(http, setting){
	/*$.ajax({
		//其他参数已省...
		timeout:120000, //超时时间，考虑到网络问题，5秒还是比较合理的
	});*/
    return {

    	getBzdm : function(){
    		return http.post({
    			url  : setting.ctol+"/getBzdm"
    		});
    	},
    	getStuDetail : function(param,callback,error){//下钻学生信息
			http.post({
				url : setting.pmsCtol+"/getStuDetail",
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
    	getAbstract : function(schoolYear, edu, param){
    		return http.post({
    			url  : setting.ctol+"/getAbstract",
    			data : {
    				schoolYear : schoolYear,
    				edu   : edu,
    				param : param
    			}
    		});
    	},
    	
    	getTypeList : function(schoolYear, edu, param, callback){
	    	http.post({
	    		url  : setting.ctol+"/getTypeList",
	    		data : {
	    			schoolYear : schoolYear,
    				edu   : edu,
    				param : param
	    		},
	    		success : function(data){
	    			var legend_ary = [],
	    				series_ary = [],
	    				color_ary  = [],
	    				cls   = setting.cls,
	    				color = setting.color,
	    				len2 = cls.length;
	    			for(var i=0,len=data.list.length; i<len; i++){
	    				var obj = data.list[i];
	    				legend_ary.push(obj.name);
	    				series_ary.push({
	    					name  : obj.name,
	    					value : [obj.money_one, obj.scale, obj.money]
	    				});
	    				obj.cls = i<(len2-1) ? cls[i] : cls[len2-1];
	    				color_ary.push(i<(len2-1) ? color[i] : color[len2-1]);
	    			}
	    			var name_ary = ['元','%','元']; // 定制单位
	    			// 雷达图
	    			var option = {
    				    tooltip : {
    				        trigger  : 'axis',
    				      	position : function(ary){
    				         	return [ary[0]+80, ary[1]]
    				        },
    				        formatter : function(params){
    				        	var html = '';
    				            for(var i=0,len=params.length; i<len; i++){
    				            	var obj = params[i];
    				                html += '<br>'+obj.name+'：'+obj.value+name_ary[obj.dataIndex];
    				            }
    				            return  params[0].indicator + html;
    				        }
    				    },
    				    legend : {
    				        x : 'left',
    				      	y : 'center',
    				      	orient : 'vertical',
    				        data   : legend_ary
    				    },
    				    color   : color_ary,
    				    toolbox : {
    				        show : true,
    				        feature : {
    				            dataView : {show: true, readOnly: false},
    				            restore  : {show: true},
    				            saveAsImage : {show: true}
    				        }
    				    },
    				    calculable : true,
    				    polar : [{
    				            indicator : [
    				                {text : '单项金额', max  : data.money_one_max},
    				                {text : '覆盖率', max  : data.scale_max},
    				                {text : '总金额', max  : data.money_max}
    				            ],
    				          	name : {
    				            	textStyle: {
    				                    color : 'rgba(30,144,255,0.8)',
    				                    fontFamily : '微软雅黑',
    				                    fontSize   : 16,
    				                    fontWeight : 'bolder'         
    				                }
    				            },
    				          	center : [ '55%', '68%'],
    				            radius : 160,
    				    }],
    				    series : [{
    				    	name : '奖学金类型',
				            type : 'radar',
				            itemStyle : {
				                normal : {
				                    areaStyle : {
				                        type : 'default'
				                    }
				                }
				            },
				            data : series_ary
				        }]
	    			};
	    			callback(data.list, option);
	    		}
	    	});
	    },
    	
    	queryDeptDataList : function(schoolYear, edu, param, callback){
    		var me = this;
    		http.post({
    			url  : setting.ctol+"/queryDeptDataList",
    			data : {
    				schoolYear : schoolYear,
    				edu   : edu,
    				param : param,
    			},
    			success : function(data){
    				callback(data.bzdm, data.list, data.deptMc);
    			}
    		});
    	},
	    /**
	     * 获取列表中一个列的值并封装option
	     * @param list 数据列表
	     * @param value_column 列
	     * @param yname Y轴单位
	     * @param legend 图例
	     * @param config 自定义配置
	     */
    	getStackOptionByColumn : function(list, value_column, yname, legend, config){
    		var cfg = {
    			legend_ary : legend ? [legend] : [],
				yname_ary  : [yname], // 单位
				type_ary   : ['bar'], // 图标类型
				value_ary  : [value_column], // value所对应字段
				stack  : 'a'
			}
    		for(var key in config){
    			cfg[key] = config[key];
    		}
	    	return Ice.echartOption(list, cfg);
	    },
	    /**
	     * 得到option中的数据并排序
	     */
	    getSortDataFromOption : function(option, asc){
	    	var nameAry = option.xAxis[0].data,
	    		valueAry = option.series[0].data;
	    	var ary = [];
	    	for(var i=0,len=nameAry.length; i<len; i++){
	    		ary.push({ id : valueAry[i], mc : nameAry[i] });
	    	}
	    	var sort = function(key, asc){
	    		asc = asc==false ? false : true;
	    		return function(o1, o2){
	    			var v1 = o1[key], v2 = o2[key], no = 0;
	    			if(v1 > v2){
	    				no = 1;
	    			}else if(v1 < v2){
	    				no = -1;
	    			}
	    			return asc ? no : -no;
	    		}
	    	}
	    	if(asc != null)
	    		ary.sort(sort("id", asc));
	    	return ary;
	    },

    	getBehavior : function(schoolYear, edu, param, callback){
    		http.post({
    			url  : setting.ctol+"/getBehavior",
    			data : {
    				schoolYear : schoolYear,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				var legend_ary = [],
	    				series_ary = [];
	    			for(var i=0,len=data.list.length; i<len; i++){
	    				var obj = data.list[i];
	    				legend_ary.push(obj.name);
	    				series_ary.push({
	    					name  : obj.name,
	    					value : [obj.score, obj.earlyCount, obj.breakfastCount, obj.bookRke, obj.bookCount]
	    				});
	    			}
	    			var name_ary = ['学分','','','','']; // 定制单位
	    			// 雷达图
	    			var option = {
					    tooltip : {
					        trigger  : 'axis',
					      	position : function(ary){
					         	 return [ ary[0]+80, ary[1]]
					        },
    				        formatter : function(params){
    				        	var html = '';
    				            for(var i=0,len=params.length; i<len; i++){
    				            	var obj = params[i];
    				                html += '<br>'+obj.name+'：'+obj.value+name_ary[obj.dataIndex];
    				            }
    				            return  params[0].indicator + html;
    				        }
					    },
					    legend: {
					        x : 'left',
					        y : 'top',
							padding : [5,40],
					        data   : legend_ary
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: true, readOnly: false},
					            restore  : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    polar : [{
					            indicator : [
					                {text : '成绩', max  : data.score},
					                {text : '早起次数', max  : data.earlyCount},
					                {text : '早餐次数', max  : data.breakfastCount},
					                {text : '图书馆进出次数', max  : data.bookRke},
					                {text : '借阅量', max  : data.bookCount}
					            ],
					          	center : [ '50%', '60%'],
					            radius : 110,
					    }],
					    series : [{
				            type: 'radar',
				            itemStyle: {
				                normal: {
				                    areaStyle: {
				                        type: 'default'
				                    }
				                }
				            },
				            data : series_ary
				        }]
	    			};
	    			callback(data.list, option);
    			}
    		});
    	},
    	
    	getCoverageGrade : function(schoolYear, edu, param, callback){
    		http.post({
    			url  : setting.ctol+"/getCoverageGrade",
    			data : {
    				schoolYear : schoolYear,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				callback(data.list);
    			}
    		});
    	},

    	getCoverageDept : function(schoolYear, edu, param, callback){
    		http.post({
    			url  : setting.ctol+"/getCoverageDept",
    			data : {
    				schoolYear : schoolYear,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				callback(data.bzdm, data.list, data.deptMc);
    			}
    		});
    	},

    	getCoverageGradeSex : function(schoolYear, edu, param, callback){
    		http.post({
    			url  : setting.ctol+"/getCoverageGradeSex",
    			data : {
    				schoolYear : schoolYear,
    				edu   : edu,
    				param : param
    			},
    			success : function(data){
    				callback(data.list);
    			}
    		});
    	},

    	getTop : function(schoolYear, edu, param, column, index, callback){
    		http.post({
    			url  : setting.ctol+"/getTop",
    			data : {
    				schoolYear : schoolYear,
    				edu    : edu,
    				param  : param,
    				column : column,
    				index  : index
    			},
    			success : function(data){
    				callback(data);
    			}
    		});
    	},

    	getHistory : function(schoolYear, edu, param, callback){
    		http.post({
    			url  : setting.ctol+"/getHistory",
    			data : {
    				schoolYear : schoolYear,
    				edu    : edu,
    				param  : param
    			},
    			success : function(data){
    				callback(data.bzdm, data.list);
    			}
    		});
    	},
    	  getStuDetailDown : function(param, callback,error){
          	http.fileDownload({
          		url  : setting.pmsCtol+"/down",
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
	    		data : { tag : setting.advancedTag },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }
    }
}]);