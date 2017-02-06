app.service("service",['httpService',function(http){
    return {
    	
    	getStuChangeAbstract : function(param, callback){
    		http.post({
    			url  : "change/getStuChangeAbstract",
    	    	data : { param : param },
    			success : function(data){
    				data.name_bad = data.name_bad.join('、');
    				callback(data);
    			}
    		});
    	},
    	
    	getStuChangeDetail : function(param,callback,error){
			http.post({
				url : "pmsChange/getStuDetail",
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
    	
    	getStuChange : function(param, callback){
        	http.post({
        		url  : "change/getStuChange",
    	    	data : { param : param },
    	    	success : function(data){
    	    		var distribute = {};
    				distribute.typeCfg = {
    					type : "pie",
    					data : data.type,
    					name : '人次',
    					config : {
    						title : {text:'在籍生学籍异动（类型）分布',show:false},
    						toolbox : {
    							padding:[5,15]
    						},
    						series : [{
    							radius : 108,
								center : ['50%','48%']
    						}],
    						noDataText : '暂无异动分类数据'
    					}
    				};
    				distribute.gradeCfg = {
    					type : "pie",
    					data : data.grade,
    					name : '人次',
    					config : {
    						title : {text:'在籍生学籍异动（年级）分布',show:false},
    						calculable : true,
    						series : [{
//    							radius : 115,
								center : ['50%','48%'],
    							radius : ['36%','53%']
    						}],
    						noDataText : '暂无异动年级数据'
    					}
    					/*config : {
    						series : [{
    							roseType : 'area',
    							radius: [40, 120]
    						}]
    					}*/
    				};
    				distribute.subjectCfg = {
    					type : "pie",
    					data : data.subject,
    					name : '人次',
    					config : {
    						title : {text:'在籍生学籍异动（学科）分布',show:false},
    						calculable : true,
    						toolbox : {
    							padding:[5,15]
    						},
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
    					config : { noDataText : '暂无异动性别数据',title : {text:'在籍生学籍异动（性别）分布',show:false} }
    				};
    				callback(distribute);
    	    	}
        	});
	    },
	    
	    getDeptStuChange : function(param, callback){
	    	http.post({
	    		url  : "change/getDeptStuChange",
    	    	data : { param : param },
	    		success : function(data){
	    			var deptMc = data.deptMc || '机构';
	    			// 处理数据
	    			if(data.status){
	    				var option = Ice.echartOption(data.list, {
	    					yname_ary  : ['人', '%'], // 单位
	    					legend_ary : ['异动人数', '占比'], // 图例
	    					type_ary   : ['bar', 'line'], // 图标类型
	    					value_ary  : ['value_change', 'value_stu'], // value所对应字段
	    					config : {
	    						title : {
	    							text:'各'+deptMc+'异动人数与比例',
	    							show:false,
	    							},
	    						xAxis : [{ axisLabel : { rotate : -15 } }],
	    						noDataText : '暂无异动'+deptMc+'分布'
	    					}
	    				});
	    				callback(option, deptMc);
	    			}
	    		}
	    	});
	    },
	    
	    getStuChangeMonth : function(param, callback){
	    	http.post({
	    		url  : "change/getStuChangeMonth",
    	    	data : { param : param },
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
	    				data : Ice.changeName2Field(data.list, '', x_name),
	    				config : {
	    					title : {
	    						text:'学籍异动月份分布',
	    						show:false,
	    					},
	    					series : [{
	    						smooth : true,
		    					markPoint : { data : markPoint_dataAry }
		    				}],
		    				noDataText : '暂无异动月份分布'
	    				}
	    			};
	    			callback(option, info);
		    	}
	    	});
	    },
	    
	    getStuChangeYear : function(param, callback){
	    	http.post({
	    		url  : "change/getStuChangeYear",
    	    	data : { param : param },
	    		success : function(data){
	    			if(data.status){
	    				var option = Ice.echartOption(data.list, {
	    					legend_ary : ['异动人数', '占比'], // 图例
	    					yname_ary  : ['人', '%'], // 单位
	    					xname_ary  : ['学年'],
	    					type_ary   : ['bar', 'line'], // 图标类型
	    					value_ary  : ['value_change', 'value_stu'], // value所对应字段
	    					config : { 
	    						title : {
	    							text:'历年异动人数与比例',
	    							show:false,
	    						},
	    							   noDataText : '暂无历年异动数据' }
	    				});
	    				callback(option);
	    			}
	    		}
	    	});
	    },
	    
	    getStuChangeHistory : function(param, callback){
	    	http.post({
	    		url  : "change/getStuChangeHistory",
    	    	data : { param : param },
	    		success : function(data){
	    			var type    = data.type,
	    				grade   = data.grade,
	    				subject = data.subject,
	    				sex     = data.sex,
	    				config  = {
							legend : {
								x : 'left',
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
							config     : Ice.apply({noDataText : '暂无历年异动分类数据'},{title : {text:'历年异动（类别）发展趋势',show:false}},config),
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
	    getIsAll : function(param,callback){
	    	return http.post({
				url : "change/isAll",
				data : {param: param },
				success : function(data){
					callback(data);
				} 
			});
    	},
	    
	    getStuChangeByDeptOrMajor : function(tag,param,callback){
    		http.post({
    			url  : "change/getStuChangeByDeptOrMajor",
    			data : {tag : tag ,
    					param: param },
    			success : function(data){
    				var cfg = {
    					legend_ary : ['转入','转出'],
    					yname_ary  : ['人次'],
    					xname_append : true,
    					type_ary   : ['bar'],
    					config : {
    						title : {text: '各'+data.deptMc+'转专业(转入，转出)分布',show:false},
    						legend : {
								x : 'left',
								padding : [5,40]
    						},
    						noDataText : '暂无年级分布数据'
    					}
    				};
    				callback((Ice.echartOption(data.list, cfg)),data.deptMc);
    			}
    		})
    	},
	    getStuDetailDown : function(param, callback,error){
        	http.fileDownload({
        		url  : "pmsChange/down",
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
	    		data : { tag : "Xg_change" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }
	    
    }
}]);