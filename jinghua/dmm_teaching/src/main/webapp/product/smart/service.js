app.service("service", ['httpService', function(http) {
	return{
		getGradeSelect : function(param,schoolYear) {
			return http.post({
						url : "smart/getGradeSelect",
						data : {"param":param,
							"schoolYear":schoolYear}
					});
		},
		getYearAndTerm : function() {
			return http.post({
						url : "smart/getYearAndTerm",
						data : {}
					});
		},
		getTimeLine : function() {
			return http.post({
						url : "smart/getTimeLine",
						data : {}
					});
		},
	    getEduSelect : function(schoolYear,termCode) {
			return http.post({
						url : "smart/getEduSelect",
						data : {"schoolYear":schoolYear,
							 "termCode":termCode}
					});
		},
		getTopGpa : function(schoolYear,term,grade,edu,param,pagesize,index) {
			return http.post({
						url : "smart/getTopGpa",
						data : 
							{"schoolYear":schoolYear,
							 "term":term,
							 "grade":grade,
							 "edu":edu,
							 "param":param,
							 "pagesize":pagesize,
							 "index":index}
					});
		},
		getXbFrom : function(schoolYear,term,grade,edu,xzqh,updown,param,callback) {
			return http.post({
						url : "smart/getXbFrom",
						data : 
							{"schoolYear":schoolYear,
							 "term":term,
							 "grade":grade,
							 "edu":edu,
							 "xzqh":xzqh,
							 "updown":updown,
							 "param":param},
						success : function(data) {
					          var distribute = {};
					          distribute.from= data.list;
					          distribute.fromCfg = {
								mapType : data.maptype,
								type : "map",
								data : data.list,
								config : {
									title:{
										text:'学霸生源地分布',
										show:false
									},
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
					          distribute.cc =data.cc;
					          distribute.maptype =data.maptype;
							callback(distribute);
						}
					});
		},
		getTable : function(schoolYear,term,grade,edu,pagesize,index,column,asc,type,param) {
			return http.post({
						url : "smart/getTable",
						data : 
							{"schoolYear":schoolYear,
							 "term":term,
							 "grade":grade,
							 "edu":edu,
							 "pagesize":pagesize,
							 "index":index,
							 "column":column,
							 "asc":asc,
							 "type":type,
							 "param":param}
					});
		},
		getXbCountLine : function(edu,param,callback) {
			return http.post({
						url : "smart/getXbCountLine",
						data : 
							{
							 "edu":edu,
							 "param":param
							 },
						success : function(data) {
					          var distribute = {};
						distribute.countLineCfg = Ice.echartOption(data.list, {
								yname_ary : ['人', '%'], // 单位
								xname_ary : ['学年'], // 单位
								legend_ary : ['学霸人数', '学霸占比'], // 图例
								type_ary : ['bar', 'line'], // 图标类型
								value_ary : ['count', 'lv'],
								config:{
									title:{
										text:'历年学霸人数与占比',
										show:false
									},
									noDataText:'暂无历年学霸人数与占比数据'
								}
									// value所对应字段
								}); 
							  callback(distribute);
						}
					});
		},
		getRadar: function(schoolYear,term,grade,edu,param,callback) {
			return http.post({
						url : "smart/getRadar",
						data : 
							{"schoolYear":schoolYear,
							 "term":term,
							 "grade":grade,
							 "edu":edu,
							 "param":param
							 },
						success:function(data){
							var legend_ary = [],
			    				series_ary = [],
			    				color_ary  = [];
			    			for(var i=0,len=data.list.length; i<len; i++){
			    				var obj = data.list[i];
			    				legend_ary.push(obj.name);
			    				series_ary.push({
			    					name  : obj.name,
			    					value : [obj.score, obj.bookcount, obj.bookrke,obj.zccount,obj.risecount]
			    				});
			    			}
			    			var name_ary = ['','本/月','次/月','次/月','次/月']; // 定制单位
			    			// 雷达图
			    			var option = {
		    					title:{
									text:'学霸行为',
									show:false
								},
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
		    				    legend : {
		    				        x : 'right',
//		    				      	y : 'center',
//		    				      	orient : 'vertical',
		    				        data   : legend_ary
		    				    },
		    				    toolbox : {
		    				        show : false,
		    				        feature : {
		    				            dataView : {show: true, readOnly: false},
		    				            restore  : {show: true},
		    				            saveAsImage : {show: true}
		    				        }
		    				    },
		    				    calculable : true,
		    				    polar : [{
		    				            indicator : [
		    				                {text : '成绩', max  : data.score_max},
		    				                {text : '借书量', max  : data.bookcount_max},
		    				                {text : '图书馆进出次数', max  : data.bookrke_max},
		    				                {text : '早餐次数', max  : data.zccount_max},
		    				                {text : '早起次数', max  : data.risecount_max}
		    				            ],
		    				          	name : {
		    				            	textStyle: {
		    				                    color : 'rgba(30,144,255,0.8)',
		    				                    fontFamily : '微软雅黑',
		    				                    fontSize   : 16,
		    				                    fontWeight : 'bolder'         
		    				                }
		    				            },
		    				          	center : [ '50%', '55%'],
		    				            radius : '70%'
		    				    }],
		    				    series : [{
		    				    	name : '学霸vs在校生',
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
			    			callback(option);
						}
					});
		},
		getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : {
							tag : "Teaching_smart"
						},
						success : function(data) {
							callback(data);
						}
					})
		},
	   getDisplayedLevelType : function(callback){
	        	http.post({
	        		url  : "smart/getDisplayedLevelType",
	        		success : function(data){
	        			var ary = [], level_type = data.level_type;
	        			if(level_type != null){
	        				if(level_type == 'YX'){
	        					ary.push({id:'YX',mc:'按院系'});
	        					ary.push({id:'ZY',mc:'按专业'});
	        				}else if(level_type == 'ZY'){
	        					ary.push({id:'ZY',mc:'按专业'});
	        				}
	        				ary.push({id:'BJ',mc:'按班级'});
	        			}
	        			ary.push({id:'SUBJECT',mc:'按学科'});
	        			ary.push({id:'COURSE',mc:'按课程'});
	    				ary.push({id:'TEACHER',mc:'按教师'});
	        			callback(ary);
	        		}
	        	})
	        },
	 getRadarStu: function(schoolYear,term,grade,edu,stuNo,param,callback) {
				return http.post({
							url : "smart/getRadarStu",
							data : 
								{"schoolYear":schoolYear,
								 "term":term,
								 "grade":grade,
								 "edu":edu,
								 "stuNo":stuNo,
								 "param":param
								 },
							success:function(data){
								var legend_ary = [],
				    				series_ary = [],
				    				color_ary  = [];
				    			for(var i=0,len=data.list.length; i<len; i++){
				    				var obj = data.list[i];
				    				legend_ary.push(obj.name);
				    				series_ary.push({
				    					name  : obj.name,
				    					value : [obj.score, obj.bookcount, obj.bookrke,obj.zccount,obj.risecount]
				    				});
				    			}
				    			var name_ary = ['','本/月','次/月','次/月','次/月']; // 定制单位
				    			// 雷达图
				    			var option = {
			    					title:{
										text:'学霸行为',
										show:false
									},
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
			    				    legend : {
			    				        x : 'right',
//			    				      	y : 'center',
//			    				      	orient : 'vertical',
			    				        data   : legend_ary
			    				    },
			    				    toolbox : {
			    				        show : false,
			    				        feature : {
			    				            dataView : {show: true, readOnly: false},
			    				            restore  : {show: true},
			    				            saveAsImage : {show: true}
			    				        }
			    				    },
			    				    calculable : true,
			    				    polar : [{
			    				            indicator : [
			    				                {text : '成绩', max  : data.score_max},
			    				                {text : '借书量', max  : data.bookcount_max},
			    				                {text : '图书馆进出次数', max  : data.bookrke_max},
			    				                {text : '早餐次数', max  : data.zccount_max},
			    				                {text : '早起次数', max  : data.risecount_max}
			    				            ],
			    				          	name : {
			    				            	textStyle: {
			    				                    color : 'rgba(30,144,255,0.8)',
			    				                    fontFamily : '微软雅黑',
			    				                    fontSize   : 16,
			    				                    fontWeight : 'bolder'         
			    				                }
			    				            },
			    				          	center : [ '50%', '55%'],
			    				            radius : '70%'
			    				    }],
			    				    series : [{
//			    				    	name : '学霸vs在校生',
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
				    			callback(option);
							}
						});
			},
	  getCostStu: function(schoolYear,term,grade,edu,stuNo,param,callback) {
				return http.post({
							url : "smart/getCostStu",
							data : 
								{"schoolYear":schoolYear,
								 "term":term,
								 "grade":grade,
								 "edu":edu,
								 "stuNo":stuNo,
								 "param":param
								 },
							success:function(data){
				    			callback(data);
							}
						});
			},
		getScoreStu: function(schoolYear,term,grade,edu,stuNo,param,callback) {
					return http.post({
								url : "smart/getScoreStu",
								data : 
									{"schoolYear":schoolYear,
									 "term":term,
									 "grade":grade,
									 "edu":edu,
									 "stuNo":stuNo,
									 "param":param
									 },
								success:function(data){
					    			callback(data);
								}
							});
				},
		getBorrowStu: function(schoolYear,term,grade,edu,stuNo,param,callback) {
						return http.post({
									url : "smart/getBorrowStu",
									data : 
										{"schoolYear":schoolYear,
										 "term":term,
										 "grade":grade,
										 "edu":edu,
										 "stuNo":stuNo,
										 "param":param
										 },
									success:function(data){
						    			callback(data);
									}
								});
				},	
		getScoreStuMx: function(schoolYear,term,stuNo,callback) {
					return http.post({
								url : "smart/getScoreStuMx",
								data : 
									{"schoolYear":schoolYear,
									 "term":term,
									 "stuNo":stuNo,
									 },
								success:function(data){
					    			callback(data);
								}
							});
			},
		getDormStu: function(stuNo,callback) {
				return http.post({
							url : "smart/getDormStu",
							data : 
								{
								 "stuNo":stuNo
								 },
							success:function(data){
				    			callback(data);
							}
						});
		}			
	}
}]);