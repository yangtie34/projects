app.service("service",['httpService',function(http){
	return{
	    getEduList:function(){
			return http.post({
						url : "scoreTea/getEduList"
					});
        },
        getTimeList:function(){
			return http.post({
						url : "scoreTea/getTimeList"
					});
        },
        getThList:function(){
			return http.post({
						url : "scoreTea/getThList"
					});
        },
        getFthList:function(){
			return http.post({
						url : "scoreTea/getFthList"
					});
        },
        getCourseList:function(param,schoolYear,termCode,edu,callback){
			return http.post({
						url : "scoreTea/getCourseList",
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
						url : "scoreTea/getTeachClassAndStuCount",
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
						url : "scoreTea/getCourseNatureList",
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
						url : "scoreTea/getClassScoreGk",
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
        getDyData:function(schoolYear,termCode,courseId,classid,nature,edu,param,callback){
			return http.post({
						url : "scoreTea/getDyData",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"classid":classid,
							"nature":nature,
							"edu":edu,
							"param":param
							},
							success : function(data) {
								callback(data);
							}
					});
        },
        getReport:function(schoolYear,termCode,courseId,nature,edu,param,callback){
			return http.post({
						url : "scoreTea/getReport",
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
						url : "scoreTea/getChartData",
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
						url : "scoreTea/getSameData",
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
        saveYzZt:function(schoolYear,termCode,courseId,nature,classid,callback){
			return http.post({
						url : "scoreTea/saveYzZt",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"classid":classid
							},
			            success : function(data) {
								callback(data);
							}
					});
        },
        getYzZt:function(schoolYear,termCode,courseId,nature,classid,callback){
			return http.post({
						url : "scoreTea/getYzZt",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"classid":classid
							},
			            success : function(data) {
								callback(data);
							}
					});
        },
        getDtXt:function(schoolYear,termCode,courseId,nature,classid,callback){
			return http.post({
						url : "scoreTea/getDtXt",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"classid":classid
							},
			            success : function(data) {
								callback(data);
							}
					});
        },
        saveDt:function(schoolYear,termCode,courseId,nature,classid, kslx, kcxx, one, two, three, four, five,fxr,zr,sj,qt,callback){
			return http.post({
						url : "scoreTea/saveDt",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"classid":classid,
							"kslx":kslx,
							"kcxx":kcxx,
							"one":one,
							"two":two,
							"three":three,
							"four":four,
							"five":five,
							"fxr":fxr,
							"zr":zr,
							"sj":sj,
							"qt":qt
							},
			            success : function(data) {
								callback(data);
							}
					});
        },
        saveXt:function(schoolYear,termCode,courseId,nature,classid,tx,tf,df,th,thn,callback){
			return http.post({
						url : "scoreTea/saveXt",
						data : {
							"schoolYear":schoolYear,
							"termCode":termCode,
							"courseId":courseId,
							"nature":nature,
							"classid":classid,
							"tx":tx,
							"tf":tf,
							"df":df,
							"th":th,
							"thn":thn
							},
			            success : function(data) {
								callback(data);
							}
					});
        },
        getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : { tag : 'Teaching_scoreTea'},
						success : function(data) {
							callback(data);
						}
					})
		}
	}
}]);