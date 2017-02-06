app.service("service",['httpService',function(http){
    return {
    	
    	getChangeBadList : function(callback){
    		return http.post({
    			url  : "changeBad/getChangeBadList"
    		});
    	},
    	
    	getStuChangeAbstract : function(changeCode, param, callback){
    		http.post({
    			url  : "changeBad/getStuChangeAbstract",
    			data : { changeCode : changeCode, param : param },
    			success : function(data){
    				data.name_bad = data.name_bad.join('、');
    				callback(data);
    			}
    		});
    	},
    	//下钻学生信息
    	getStuChangeDetail : function(param,callback,error){
			http.post({
				url : "pmsChangeBad/getStuDetail",
				data : param ,
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
    	
    	getStuChange : function(changeCode, param,tabShow, callback){
        	http.post({
        		url  : "changeBad/getStuChange",
        		data : {
    				"changeCode" : changeCode, 
    				"param" : param
        		},
        		success : function(data){
        			var distribute = {};
    				distribute.typeCfg = {
    					type : "pie",
    					data : data.type,
    					name : '人次',
    					config : {
    						title : {text:tabShow+'分布',show:false},
    						toolbox : {
    							padding:[5,15]
    						},
    					},
						noDataText : '暂无异动分类数据'
    				};
    				distribute.gradeCfg = {
    					type : "pie",
    					data : data.grade,
    					name : '人次',
    					config : {
    						title : {text:tabShow+'（年级）分布',show:false},
    						calculable : true,
    						series : [{
    							radius: ['42%', '68%']
    						}],
    						noDataText : '暂无异动年级数据'
    					}
    				};
    				distribute.subjectCfg = {
    					type : "pie",
    					data : data.subject,
    					name : '人次',
    					config : {
    						title : {text:tabShow+'（学科）分布',show:false},
    						toolbox : {
    							padding:[5,15]
    						},
    						calculable : true,
    						series : [{
    							radius: ['42%', '68%']
    						}],
    						noDataText : '暂无异动学科数据'
    					}
    				};
    				distribute.sexCfg = {
    					type : "pie",
    					data : data.sex,
    					name : '人次',
    					config : { noDataText : '暂无异动性别数据',title : {text:tabShow+'（性别）分布',show:false}, }
    				};
    				callback(distribute);
        		}
        	});
	    },
	    
	    getDeptStuChange : function(changeCode, param,tabShow, callback){
	    	http.post({
	    		url  : "changeBad/getDeptStuChange",
	    		data : {
    				"changeCode" : changeCode, 
    				"param" : param,
        		},
	    		success : function(data){
	    			var deptMc = data.deptMc || '机构';
	    			// 处理数据
	    			if(data.status){
	    				var option = Ice.echartOption(data.list, {
	    					yname_ary  : ['人', '%'], // 单位
	    					type_ary   : ['bar', 'line'], // 图标类型
	    					legend_ary : ['人数', '占比'], // 图例
	    					value_ary  : ['value_change', 'value_stu'], // value所对应字段
	    					config : {
	    						title : {text:'各'+deptMc+tabShow+'人数与比例',show:false},
	    						xAxis : [{ axisLabel : { rotate : -15 } }],
	    						noDataText : '暂无异动'+deptMc+'分布'
	    					}
	    				});
	    				callback(option, deptMc);
	    			}
	    		}
	    	});
	    },
	    
	    getStuChangeMonth : function(changeCode, param,tabShow,callback){
	    	http.post({
	    		url  : "changeBad/getStuChangeMonth",
	    		data : {
    				"changeCode" : changeCode, 
    				"param" : param,
        		},
	    		success : function(data){
		    		var x_name = '月',
		    		info = { // 数据描述
	    				high : 	data.high.join('、'),
	    				infoList   : data.infoList,
	    				dataSource : data.dataSource
		    		};
		    		/*markPoint : {
		            	data : [
		                	{name: '9月',value: 231, xAxis: 0, yAxis: 231, valueIndex: 0, symbol: 'emptyCircle', itemStyle:{normal:{color:'#1e90ff',label:{position:'right'}}}},
		                    {name: '6月',value: 188, xAxis: 9, yAxis: 188, valueIndex: 0, symbol: 'emptyCircle', itemStyle:{normal:{color:'#1e90ff',label:{position:'right'}}}}
		                ]
		            }*/
		    		// 数据醒目标示
		    		var markPoint_dataAry = [];
		    		for(var i=0,len=data.high.length; i<len; i++){
		    			var month = data.high[i];
		    			for(var j=0,lenJ=data.list.length; j<lenJ; j++){
		    				var obj = data.list[j];
		    				if(month == obj.name){
		    					var markPoint_dataOne = {
		    						name : month+x_name, xAxis:j, yAxis:obj.value, value:obj.value, valueIndex:0, symbol:'emptyCircle', itemStyle:{normal:{color:'#1e90ff',label:{position:'right'}}}
		    					};
		    					markPoint_dataAry.push(markPoint_dataOne);
		    				}
		    			}
		    		}
	    			var option = {
	    				type : "line",
	    				name : '人次',
	    				data : Ice.changeName2Field(data.list, '', '月'),
    					config : {
    						title : {text:tabShow+'月份分布',show:false},
	    					series : [{
	    						smooth : true,
		    					markPoint : { data : markPoint_dataAry }
		    				}],
		    				noDataText : '暂无异动月份分布'
						}
	    			},
	    			monthData = {
    					dataSource : data.dataSource
    				};
	    			callback(option, info);
	    		}
	    	});
	    },
	    
	    getStuChangeYear : function(changeCode, param,tabShow,callback){
	    	http.post({
	    		url  : "changeBad/getStuChangeYear",
	    		data : {
    				"changeCode" : changeCode,
    				"param" : param,
        		},
	    		success : function(data){
	    			if(data.status){
	    				var option = Ice.echartOption(data.list, {
	    					legend_ary : ['人数', '占比'], // 图例
	    					yname_ary  : ['人', '%'], // 单位
	    					xname_ary  : ['学年'],
	    					type_ary   : ['bar', 'line'], // 图标类型
	    					value_ary  : ['value_change', 'value_stu'], // value所对应字段
	    					config : { 
	    						title : {text:tabShow+'学生二次异动比例',show:false},
	    						noDataText : '暂无历年异动数据' }
	    				});
	    				callback(option);
	    			}
	    		}
	    	});
	    },
	    
	    getStuChangeHistory : function(changeCode, param, callback){
	    	http.post({
	    		url  : "changeBad/getStuChangeHistory",
	    		data : { changeCode : changeCode, param : param },
	    		success : function(data){
	    			var type    = data.type,
	    				grade   = data.grade,
	    				subject = data.subject,
	    				sex     = data.sex,
	    				config  = {
							legend : {
								padding : [12,50]
    						},
    						grid : {
    							y2 : 30
    						}
						},
	    				type_option = Ice.echartOption(type.data, {
	    					yname_ary  : ['人次'], // 单位
	    					xname_ary  : ['学年'],
	    					type_ary   : ['line'], // 图标类型
	    					legend_ary : type.legend_ary, // 图例
	    					value_ary  : type.value_ary, // value所对应字段
							config     : Ice.apply({noDataText : '暂无历年异动分类数据'},{title : {text:'历年异动（类别）发展趋势',show:false}},config)
	    				}),
	    				grade_option = Ice.echartOption(grade.data, {
	    					yname_ary  : ['人次'], // 单位
	    					xname_ary  : ['学年'],
	    					type_ary   : ['line'], // 图标类型
	    					legend_ary : grade.legend_ary, // 图例
	    					value_ary  : grade.value_ary, // value所对应字段
							config     : Ice.apply({noDataText : '暂无历年异动年级数据'},{title : {text:'历年异动（年级）发展趋势',show:false}},config)
	    				}),
	    				subject_option = Ice.echartOption(subject.data, {
	    					yname_ary  : ['人次'], // 单位
	    					xname_ary  : ['学年'],
	    					type_ary   : ['line'], // 图标类型
	    					legend_ary : subject.legend_ary, // 图例
	    					value_ary  : subject.value_ary, // value所对应字段
							config     : Ice.apply({noDataText : '暂无历年异动学科数据'},{title : {text:'历年异动（学科）发展趋势',show:false}},config)
	    				}),
	    				sex_option = Ice.echartOption(sex.data, {
	    					yname_ary  : ['人次'], // 单位
	    					xname_ary  : ['学年'],
	    					type_ary   : ['line'], // 图标类型
	    					legend_ary : sex.legend_ary, // 图例
	    					value_ary  : sex.value_ary, // value所对应字段
							config     : Ice.apply({noDataText : '暂无历年异动性别数据'},{title : {text:'历年异动（性别）发展趋势',show:false}},config)
	    				});
	    			callback(type_option, grade_option, subject_option, sex_option);
	    		}
	    	});
	    },
	    
	    getStuDetailDown : function(param, callback,error){
        	http.fileDownload({
        		url  : "pmsChangeBad/down",
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
	    		data : { tag : "Xg_changeBad" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    },
	    getChangeAgain: function(changeCode, param,tabShow,callback){
	    	http.post({
	    		url  : "changeBad/getChangeAgain",
	    		data : {
    				"changeCode" : changeCode,
    				"param" : param,
        		},
	    		success : function(data){
	    			if(data.status){
	    				var cfg = {
	        					yname_ary  : ['%'],
	        					xname_ary  : ['学年'],
	        					type_ary   : ['line'],
	        					config:{
	            					title :{ text:tabShow+'学生二次异动比例',show:false},
	        					},
	        					noDataText : '暂无二次异动数据'
	        				};
	        				callback(Ice.echartOption(data.list, cfg));
	    			}
	    		}
	    	});
	    }
    	
    }
}]);