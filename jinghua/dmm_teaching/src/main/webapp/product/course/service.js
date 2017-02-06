app.service("service", ['httpService', function(http) {
	return {
		getBzdm : function(callback){
        	http.post({
        		url  : "course/getBzdm",
        		success : function(data){
        			callback(data);
        		}
        	});
        },
		
		getCourseDistribution : function(param,edu,schoolYear,termCode, callback){
	        	http.post({
	        		url  : "course/getCourseDistribution",
	        		data : {
	        			param :param,
	        			edu : edu,
	        			schoolYear : schoolYear,
	        			termCode : termCode
	        		},
	        		success : function(data){
	        			if(data.status){
		        			var attrOption = {
		    					type : 'pie',
		    					name : '门',
		    					data : data.attr,
		    					config : { 
		    						noDataText : '暂无课程属性分布数据',
		    						title : {
		    							text : '课程属性分布',
		    							show : false
		    						}
								}
		        			}
		        			var natOption = {
						 		type : 'pie',
		    					name : '门',
						        data : data.nat,
		    					config : { 
		    						noDataText : '暂无课程性质分布数据',
		    						title : {
		    							text : '课程性质分布',
		    							show : false
		    						}
		    					}
		        			}
		        			var cateOption = {
		    					type : 'pie',
		    					name : '门',
		    					data : data.cate,
		    					config : { 
		    						noDataText : '暂无课程类别分布数据',
		    						title : {
		    							text : '课程类别分布',
		    							show : false
		    						}
		    					}
		        			}
		        			callback(data.abs,attrOption, natOption, cateOption);
	        			}
	        		}
	        	});
	        },
	        getDeptDistribution : function(param,edu,schoolYear,termCode,codeType,code,callback){
	        	http.post({
	        		url  : "course/getDeptDistribution",
	        		data : {
	        			param :param,
	        			edu : edu,
	        			schoolYear : schoolYear,
	        			termCode : termCode,
	        			codeType : codeType,
	        			code  : code
	        		},
	        		success : function(data){
	        			var cfg = {
		    					//legend_ary : ['数量'],
		    					yname_ary  : ['门'],
		    					xname_append : true,
		    					type_ary   : ['bar'],
		    					config : {    
		    						title :{text:'各'+data.deptMc+'课程分布',show:false},
		    						legend : {
		    							x : 'left',
		    							padding : [5,50]
		    						},
		    						noDataText : '暂无各'+data.deptMc+'课程分布数据'
		    					}
		    				};
		    		callback(Ice.echartOption(data.list, cfg),data.deptMc);
		    		}
	        	})
	        },
	        getSubjectDistribution : function(param,edu,schoolYear,termCode,codeType,code,callback){
	        	http.post({
	        		url  : "course/getSubjectDistribution",
	        		data : {
	        			param :param,
	        			edu : edu,
	        			schoolYear : schoolYear,
	        			termCode : termCode,
	        			codeType : codeType,
	        			code  : code
	        		},
	        		success : function(data){
	        			var cfg = {
		    					//legend_ary : ['数量'],
		    					yname_ary  : ['门'],
		    					xname_append : true,
		    					type_ary   : ['bar'],
		    					config : {    
		    						title :{text:'各学科课程分布',show:false},
		    						legend : {
		    							x : 'left',
		    							padding : [5,50]
		    						},
		    						noDataText : '暂无各学科课程分布数据'
		    					}
		    				};
		    		callback(Ice.echartOption(data.list, cfg));
		    		}
	        	})
	        },
	        getDeptHistory : function(param,edu,codeType,code, callback){
		    	http.post({
		    		url  : "course/getDeptHistory",
		    		data : {
	        			param :param,
	        			edu : edu,
	        			codeType : codeType,
	        			code  : code
	        		},
		    		success : function(data){
		    			var config  = {
								legend : {
									x : 'left',
									padding : [12,50]
	    						},
	    						grid : {
	    							y2 : 30
	    						}
							},
		    				option = Ice.echartOption(data.data, {
		    					yname_ary  : ['门'], // 单位
		    					xname_ary  : ['学年'],
		    					xname_append : true,
		    					type_ary   : ['line'], // 图标类型
		    					legend_ary : data.legend_ary, // 图例
		    					value_ary  : data.value_ary, // value所对应字段
								config     : Ice.apply({noDataText : '暂无历年各'+data.deptMc+'课程趋势数据'},{title : {text:'历年各'+data.deptMc+'课程发展趋势',show:false}},config),
		    				})
		    			callback(option);
		    		}
		    	});
		    },
		    getSubjectHistory : function(param,edu,codeType,code, callback){
		    	http.post({
		    		url  : "course/getSubjectHistory",
		    		data : {
	        			param :param,
	        			edu : edu,
	        			codeType : codeType,
	        			code  : code
	        		},
		    		success : function(data){
		    			var config  = {
								legend : {
									x : 'left',
									padding : [12,50]
	    						},
	    						grid : {
	    							y2 : 30
	    						}
							},
		    				option = Ice.echartOption(data.data, {
		    					yname_ary  : ['门'], // 单位
		    					xname_ary  : ['学年'],
		    					xname_append : true,
		    					type_ary   : ['line'], // 图标类型
		    					legend_ary : data.legend_ary, // 图例
		    					value_ary  : data.value_ary, // value所对应字段
								config     : Ice.apply({noDataText : '暂无历年各学科课程趋势数据'},{title : {text:'历年各学科课程发展趋势',show:false}},config),
		    				})
		    			callback(option);
		    		}
		    	});
		    },
	        
	        getAdvance : function(callback){
		    	http.post({
		    		url  : "advanced",
		    		data : { tag : "Teaching_course" },
		    		success : function(data){
		    			callback(data);
		    		}
		    	})
		    }
		
	}
}]);