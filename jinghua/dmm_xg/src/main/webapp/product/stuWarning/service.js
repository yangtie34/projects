app.service("service",['httpService', 'setting', function(http, setting){
    return {
    	
    	ctol : 'stuWarning',
    	pmsCtol:'pmsStuWarning',
    	getAbstract : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getAbstract",
        		data : param,
        		success : function(data){
        			var mold = data.mold;
        			if(mold) mold.splice(0, 0, {mc:'全部数据',id:'null'});
        			callback(data);
        		}
        	});
        },
        
        getDeptDataGrid : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDeptDataGrid",
        		data : param,
        		success : function(data){
        			var count_all = 0;
        			for(var i=0,len=data.list.length; i<len; i++){
        				count_all += data.list[i].count;
        			}
        			callback(data.list, data.deptMc, count_all);
        		}
        	});
        },
        
        isTermOver : function(date){
        	return http.post({
        		url  : this.ctol+"/isTermOver",
        		data : {date : date}
        	});
        },
        
        getBzdmXnXq : function(callback){
        	http.post({
        		url  : this.ctol+"/getBzdmXnXq",
        		success : function(data){
        			callback(data.list);
        		}
        	});
        },
        
        getIsSetStartEndDate : function(schoolYear, termCode, callback){
        	http.post({
        		url  : this.ctol+"/getIsSetStartEndDate",
        		data : {
        			schoolYear : schoolYear,
        			termCode   : termCode
        		},
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        
        getDistribution : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDistribution",
        		data : param,
        		success : function(data){
        			if(data.status){
	        			var typeOption = {
	    					type : 'pie',
	    					name : '人次',
	    					data : data.type,
	    					config : { 
	    						noDataText : '暂无预警类型分布数据',
	    						title : {
	    							text : '预警类型分布',
	    							show : false
	    						}
							}
	        			}
	        			var gradeOption = {
					 		type : 'pie',
	    					name : '人次',
					        data : data.grade,
	    					config : { 
	    						noDataText : '暂无预警年级分布数据',
	    						title : {
	    							text : '预警年级分布',
	    							show : false
	    						}
	    					}
	        			}
	        			var sexOption = {
	    					type : 'pie',
	    					name : '人次',
	    					data : data.sex,
	    					config : { 
	    						noDataText : '暂无预警性别分布数据',
	    						title : {
	    							text : '预警性别分布',
	    							show : false
	    						}
	    					}
	        			}
	        			callback(typeOption, gradeOption, sexOption);
        			}else{
        				alert(data.info);
        			}
        		}
        	});
        },
        
        getDeptDataList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDeptDataList",
        		data : param,
        		success : function(data){
        			var cfg = {
    					yname_ary  : ['人'], // 单位
    					type_ary   : ['bar'], // 图标类型
//    					value_ary  : [value_column], // value所对应字段
    					stack  : 'a',
    					config : { title:{ text:'各'+data.deptMc+'预警分布与人数占比',show:false},noDataText : '暂无'+data.deptMc+'分布数据' }
    				}
        			callback(Ice.echartOption(data.list, cfg), data.deptMc);
        		}
        	});
        },
        
        send : function(deptId, date, callback){
        	http.post({
        		url  : this.ctol+"/send",
        		data : {deptId:deptId, date:date},
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        
        getDetail : function(param, callback,error){
        	http.post({
        		url  : this.pmsCtol+"/getDetail",
        		data : param,
        		success : function(data){
        			callback(data);
        		},
        		error:error
        	});
        },

        gridDetailDown : function(param,error){
        	http.fileDownload({
        		url  : this.ctol+"/gridDetailDown",
        		data : param,
        		success : function(data){
        		},
        		error:error
        	})
        },
        
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_stuEnroll" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    },
	    getSkipClassByWeekDayOrClas : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getSkipClassByWeekDayOrClas",
        		data : param,
        		success : function(data){
        			var week_cfg = {
        					yname_ary  : ['人次'],
        					xname_append : true,
        					type_ary   : ['bar'],
        					config : {
        						title:{text : '逃课时间分布（星期）',show :false},
        						noDataText : '暂无数据'
        					}
        				};
        			var clas_cfg = {
        					yname_ary  : ['人次'],
        					xname_append : true,
        					type_ary   : ['bar'],
        					config : {
        						title:{text : '逃课时间分布（节次）',show :false},
        						noDataText : '暂无数据'
        					}
        				};
        			var whereOption = {
					 		type : 'pie',
	    					name : '人次',
					        //data : data.where,
	    					config : { 
	    						noDataText : '暂无逃课学生位置数据',
	    						title : {
	    							text : '逃课学生位置分布',
	    							show : false
	    						}
	    					}
	        			}
        			var scls_cfg = {
        					yname_ary  : ['%'],
        					xname_append : true,
        					type_ary   : ['bar'],
        					config : {
        						title:{text : '上午逃课学生的下午逃课概率',show :false},
        						noDataText : '暂无数据'
        					}
        				};
        			callback(Ice.echartOption(data.week, week_cfg),Ice.echartOption(data.clas, clas_cfg),whereOption,data.msg,Ice.echartOption(data.scls, scls_cfg),data.sclsInfo);
        		}
        	});
        },

		/**
		 * 根据类型获取标题名称
		 */
		getTitleName : function(type){
			var title = '';
			switch (type) {
			case 'skipClasses':
				title = '疑似逃课';
				break;
			case 'notStay':
				title = '疑似未住宿';
				break;
			case 'stayLate':
				title = '疑似晚勤晚归';
				break;
			case 'stayNotin':
				title = '疑似不在校';
				break;
			default:
				title = '考勤预警';
				break;
			}
			return title;
		}
        
    }
}]);