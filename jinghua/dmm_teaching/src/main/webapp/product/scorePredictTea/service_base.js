app.service("service",['httpService','setting',function(http, setting){
	return{
	    getEduList:function(){
			return http.post({
						url : setting.ctol+"/getEduList"
					});
        },
        getDate:function(){
			return http.post({
						url : setting.ctol+"/getDate"
					});
        },
        getTimeList:function(){
			return http.post({
						url : setting.ctol+"/getTimeList"
					});
        },
        getThList:function(){
			return http.post({
						url : setting.ctol+"/getThList"
					});
        },
        getFthList:function(){
			return http.post({
						url : setting.ctol+"/getFthList"
					});
        },
        getCourseList:function(param,schoolYear,termCode,edu,callback){
			return http.post({
						url : setting.ctol+"/getCourseList",
						data : {
							"param":param,
							"schoolYear":schoolYear,
							"termCode":termCode,
							"edu":edu
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getTeachClassAndStuCount:function(schoolYear,termCode,courseId,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getTeachClassAndStuCount",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getCourseNatureList:function(schoolYear,termCode,courseId,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getCourseNatureList",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getClassScoreGk:function(schoolYear,termCode,courseId,nature,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getClassScoreGk",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getChartData:function(schoolYear,termCode,courseId,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getChartData",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								var distribute = {};
								var list = data.list;
								var has = data.has;
								if (has == 1 || has == '1'){
								distribute.barCfg = Ice.echartOption(
										list.data, {
											yname_ary : ['人次'], // 单位
											type_ary : ['bar'], // 图标类型
											legend_ary : list.legend_ary, // 图例
											value_ary : list.value_ary,
											config:{
												title:{
													text:'成绩分布',
													show:false
												},
												noDataText:'暂无成绩分布数据'
											}
									});
								}else{
									distribute.barCfg ={
											type : "bar",
											data : data.list,
											config:{
												title:{
													text:'成绩分布',
													show:false
												},
												noDataText:'暂无成绩分布数据',
												yAxis:[{
													name: '人次'
												}],
												tooltip:{
													formatter:"{b} <br/> {c}人次"
													},
											}	
									}	
								}
								callback(distribute);

							}
					});
        },
        getSameData:function(schoolYear,courseId,nature,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getSameData",
						data : {
							"schoolYear":schoolYear,
							"courseId":courseId,
							"nature":nature,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getPrecisionList:function(schoolYear,courseId,nature,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getPrecisionList",
						data : {
							"schoolYear":schoolYear,
							"courseId":courseId,
							"nature":nature,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getNowPrecision:function(schoolYear,termCode,courseId,nature,edu,param,callback){
			return http.post({
						url : setting.ctol+"/getNowPrecision",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : { tag : setting.advancedTag},
						success : function(data) {
							callback(data);
						}
					})
		},
		getStuListDown : function(param, callback){
        	http.fileDownload({
        		url  : setting.pmsCtol+"/down",
        		data : param,
        		success : function(){
        			alert("success");
        		}
        	})
        },
		getStuList : function(param,callback,error) {
			return http.post({
						url : setting.pmsCtol+"/getStuList",
						data :param,
						success : function(data) {
							var page = {};
							page.total = data.total;
							page.items = data.rows;
							page.pagecount = data.pagecount;
							callback(page);
						},
						error:error
					});
		}
	}
}]);