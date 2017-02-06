app.service("service",['httpService', 'constant', function(http, constant){
    return {
    	
    	ctol : "teacherGroup",
    	pmsCtol : "pmsTeacherGroup",
    	
    	
    	getAbstract : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getAbstract",
	    		data : param,
	    		success : function(data){
	    			var ary = data.seniorGroup, ary2 = [], limit = 4;
	    			for(var i=0,len=ary.length; i<len; ){
	    				var thisIndex = ary2.length*limit, aryOne = [];
	    				while(len > thisIndex && i<len && aryOne.length < limit){
	    					aryOne.push(ary[i++])
	    				}
	    				if(aryOne.length > 0) ary2.push(aryOne);
	    			}
	    			var senior = {
	    				list  : ary2,
	    				index : 0,
	    				pageCount : ary2.length
	    			};
	    			delete data.seniorGroup;
	    			callback(data, senior);
	    		}
        	});
        },
        
        getDistribution : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDistribution",
        		data : param,
        		success : function(data){
        			var technicalOption = {
    					type : "pie",
    					data : data.technical,
    					name : '人',
    					config : {
    						series : [{
//    							radius : 115,
    	    					center : ['50%','56%']
    						}],
    	    				noDataText : '暂无职称分布数据', 
	        				title : { 
	        					text : '师资（职称）分布',
	        					show : false
	        				}
    					}
	    			},
	    			degreeOption = {
        					type : "pie",
        					data : data.degree,
        					name : '人',
        					config : {
        						series : [{ center : ['50%','56%'] }],
        	    				noDataText : '暂无学位分布数据', 
    	        				title : { 
    	        					text : '师资（学位）分布',
    	        					show : false
    	        				}
        					}
        			},
        			eduOption = {
        					type : "pie",
        					data : data.edu,
        					name : '人',
        					config : {
        						series : [{ center : ['50%','56%'] }],
        	    				noDataText : '暂无学历分布数据', 
    	        				title : { 
    	        					text : '师资（学历）分布',
    	        					show : false
    	        				}
        					}
        			};
        			callback(technicalOption, data.technicalScale,
        					degreeOption, data.degreeScale, eduOption, data.eduScale);
        		}
        	});
        },
        
        getHistoryTechnical : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getHistoryTechnical",
        		data : param,
        		success : function(data){
        			var option = Ice.echartOption(data.data, {
    					yname_ary  : ['人'], // 单位
    					xname_ary  : ['年'], // 单位
    					type_ary   : ['line'], // 图标类型
    					legend_ary : data.legend_ary, // 图例
    					value_ary  : data.value_ary, // value所对应字段
	    				config     : { noDataText : '暂无历年职称数据', 
	        				title : { 
	        					text : '历年师资（职称）变化',
	        					show : false
	        				}
	    				}
    				})
        			callback(option);
        		}
        	});
        },
        
        getHistoryDegree : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getHistoryDegree",
        		data : param,
        		success : function(data){
        			var option = Ice.echartOption(data.data, {
        				yname_ary  : ['人'], // 单位
    					xname_ary  : ['年'], // 单位
        				type_ary   : ['line'], // 图标类型
        				legend_ary : data.legend_ary, // 图例
        				value_ary  : data.value_ary, // value所对应字段
	    				config     : { noDataText : '暂无历年学位数据', 
	        				title : { 
	        					text : '历年师资（学位）变化',
	        					show : false
	        				}
	    				}
        			})
        			callback(option);
        		}
        	});
        },
        
        getHistoryEdu : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getHistoryEdu",
        		data : param,
        		success : function(data){
        			var option = Ice.echartOption(data.data, {
        				yname_ary  : ['人'], // 单位
    					xname_ary  : ['年'], // 单位
        				type_ary   : ['line'], // 图标类型
        				legend_ary : data.legend_ary, // 图例
        				value_ary  : data.value_ary, // value所对应字段
	    				config     : { noDataText : '暂无历年学历数据', 
	        				title : { 
	        					text : '历年师资（学历）变化',
	        					show : false
	        				}
	    				}
        			})
        			callback(option);
        		}
        	});
        },
        
        getSubjectGroup : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getSubjectGroup",
        		data : param,
        		success : function(data){
        			var option = {
    					type : "bar",
    					data : Ice.changeName2Field(data.list),
    					name : '人',
	    				config : { 
	    					noDataText : '暂无师资（学科）分布数据', 
	        				title : { 
	        					text : '师资（学科）分布',
	        					show : false
	        				}
	        			}
        			};
        			callback(option);
        		}
        	});
        },

        getAgeGroup : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getAgeGroup",
        		data : param,
        		success : function(data){
        			var option = {
    					type : "bar",
    					data : Ice.changeName2Field(data.list),
    					name : '人',
	    				config : { 
	    					noDataText : '暂无师资（年龄）分布数据', 
	        				title : { 
	        					text : '师资（年龄）分布',
	        					show : false
	        				}
	    				}
    				};
        			callback(option, data.scale);
        		}
        	});
        },
        
        getSchoolAgeGroup : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getSchoolAgeGroup",
        		data : param,
        		success : function(data){
        			var option = {
    					type : "bar",
    					data : Ice.changeName2Field(data.list),
    					name : '人',
	    				config : { noDataText : '暂无师资（教龄）分布数据', 
	        				title : { 
	        					text : '师资（教龄）分布',
	        					show : false
	        				}
	    				}
        			};
        			callback(option);
        		}
        	});
        },
        
        getDeptList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDeptList",
        		data : param,
        		success : function(data){
        			var cfg = {
    					legend_ary : ['人数','高职称','高学位','高学历','平均年龄','平均教龄'],
    					yname_ary  : ['人'],
    					xname_append : true,
    					type_ary   : ['bar'],
    					config : {
//    						xAxis  : [{ axisLabel : { rotate : -15 } }],
    						legend : {
    							selectedMode : 'single',
    							selected : { '高职称':false, '高学位':false, '高学历':false, '平均年龄':false, '平均教龄':false },
    						},
    	    				noDataText : '暂无各单位师资数据', 
	        				title : { 
	        					text : '各单位师资分布',
	        					show : false
	        				}
    					}
    				};
        			var option = Ice.echartOption(data.list, cfg);
        			callback(option);
        		}
        	});
        },
        
        getHistoryList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getHistoryList",
        		data : param,
        		success : function(data){
        			var cfg = {
        					legend_ary : ['人数','高职称','高学位','高学历','平均年龄','平均教龄'],
        					yname_ary  : ['人'],
        					xname_ary  : ['年'],
        					type_ary   : ['line'],
        					config : {
        						legend : {
        							selectedMode : 'single',
        							selected : { '高职称':false, '高学位':false, '高学历':false, '平均年龄':false, '平均教龄':false },
        						},
        						yAxis : [{
        							scale : true,
        							min   : 500
        						}],
        	    				noDataText : '暂无历年师资数据', 
    	        				title : { 
    	        					text : '历年师资变化',
    	        					show : false
    	        				}
        					}
        			};
        			var option = Ice.echartOption(data.list, cfg);
        			callback(option);
        		}
        	});
        },
        
        getDeptScaleList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDeptScaleList",
        		data : param,
        		success : function(data){
        			var cfg = {
    					legend_ary : ['生师比'],
    					yname_ary  : [''],
    					xname_append : true,
    					type_ary   : ['bar'],
    					config : { 
    						noDataText : '暂无各'+data.deptMc+'生师比数据', 
	        				title : { 
	        					text : '各'+data.deptMc+'生师比',
	        					show : false
	        				} 
    					}
        			};
        			var option = Ice.echartOption(data.list, cfg);
        			callback(option, data.deptMc);
        		}
        	});
        },
        
        getTeaDetail : function(param, callback, error){
        	http.post({
        		url  : this.pmsCtol+"/getTeaDetail",
        		data : param,
        		success : function(data){
        			callback(data);
        		},
        		error : error
        	});
        },
        
        getTeaDetailDown : function(param, callback){
        	http.fileDownload({
        		url  : this.pmsCtol+"/down",
        		data : param,
//        		success : callback
        	})
        },
        
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Teaching_teacherGroup" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }
        
    }
}]);