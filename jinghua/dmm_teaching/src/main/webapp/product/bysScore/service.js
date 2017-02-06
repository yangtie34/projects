app.service("service", ['httpService', function(http) {
	return{
//		getScoreTest : function(param,callback) {
//			return http.post({
//						url : "bysScore/getScoreTest",
//						data : param,
//						success : function(data) {
//							var Ary = {};
//							Ary.chartCfg = Ice.echartOption(data.list, {
//								yname_ary : ['人'], // 单位
//								xname_ary : ['学期'], // 单位
//								legend_ary : ['优秀', '良好',"中等","及格","不及格"], // 图例
//								type_ary : ['line', 'line','line', 'line'], // 图标类型
//								value_ary : ['y', 'l','z', 'j','b']
//									// value所对应字段
//								}); 
//							callback(Ary);
//						}
//					});
//		},
//		getCourseList:function(callback) {
//			return http.post({
//				url : "bysScore/getCourseList",
//				success : function(data) {
//					callback(data.list);
//				}
//			});
//        },
		getEduList:function(callback) {
			return http.post({
				url : "bysScore/getEduList",
				success : function(data) {
					callback(data.list);
				}
			});
        },
    	getTargetList:function(callback){
    		return http.post({
				url : "bysScore/getTargetList",
				success : function(data) {
					callback(data.list);
				}
			});
		},
		getScoreGroup:function(callback){
    		return http.post({
				url : "bysScore/getScoreGroup",
				success : function(data) {
					callback(data);
				}
			});
		},
		getScoreTypeList:function(callback){
			return http.post({
				url : "bysScore/getScoreTypeList",
				success : function(data) {
					callback(data.list);
				}
			});
		},
        getPeriodList:function(callback) {
			return http.post({
				url : "bysScore/getPeriodList",
				success : function(data) {
					callback(data.list);
				}
			});
        },
        getDateList:function(callback) {
			return http.post({
				url : "bysScore/getDateList",
				success : function(data) {
					callback(data.list);
				}
			});
        },
        getXzList:function(param,edu,callback) {
			return http.post({
				url : "bysScore/getXzList",
				data : {"param":param,
					"edu":edu},
				success : function(data) {
					callback(data.list);
				}
			});
        },
        getScoreLine:function(param,edu,date,xz,scoreType,target,callback) {
			return http.post({
				url : "bysScore/getScoreLine",
				data : {"param":param,
					    "edu":edu,
					    "date":date,
					    "xz":xz,
					    "scoreType":scoreType,
					    "target":target},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					var yname = ['分'];
					if(scoreType == 'gpa'){yname = ['绩点'];};
					distribute.lineCfg = Ice.echartOption(
							list.data, {
								yname_ary : yname, // 单位
								xname_ary : ['学期'], // 单位
								type_ary : ['line'], // 图标类型
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								config:{
									title:{
										text:'各学期成绩变化',
										show:false
									},
									noDataText:'暂无各学期成绩变化数据'
								}
						});
					var ser = distribute.lineCfg.series;
					for (var i=0;i<ser.length;i++){
						if(ser[i].name == '均值'){
					    ser[i].smooth = false;
						ser[i].itemStyle={normal: {lineStyle: {type: 'dashed'}}};
						}
					}
					callback(distribute);
				}
			});
        },
        getScoreFb:function(param,period,xz,edu,callback) {
			return http.post({
				url : "bysScore/getScoreFb",
				data : {"param":param,
				    "period":period,
				    "xz":xz,
				    "edu":edu},
				success : function(data) {
					var distribute = {};
					var list = data.list;
					distribute.fbCfg = Ice.echartOption(
							list.data, {
								yname_ary : ['人'], // 单位
								xname_ary : ['学期'], // 单位
								type_ary : ['bar'], // 图标类型
								legend_ary : list.legend_ary, // 图例
								value_ary : list.value_ary,
								config:{
									title:{
										text:'各学期成绩分布',
										show:false
									},
									noDataText:'暂无各学期成绩分布数据'
								}
						});
					var ser = distribute.fbCfg.series;
					for (var i=0;i<ser.length;i++){
					    ser[i].stack = "总数";
					}
					callback(distribute);
				}
			});
        },
    	getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : {
							tag : "Teaching_bysScore"
						},
						success : function(data) {
							callback(data);
						}
					})
		}
	}
}]);