app.service("service", ['httpService', function(http) {
	return{
    	getAdvance : function(callback) {
			http.post({
						url : "advanced",
						data : {
							tag : "Teaching_scoreByStuLb"
						},
						success : function(data) {
							callback(data);
						}
					})
		},
		getXnXqList:function() {
			return http.post({
				url : "scoreByStuLb/getXnXqList",
			});
        },
        getTargetList:function() {
			return http.post({
				url : "scoreByStuLb/getTargetList",
			});
        },
//        getCourseTypeList:function(schoolYear,termCode,param) {
//			return http.post({
//				url : "scoreByStuLb/getCourseTypeList",
//				data:{
//					"schoolYear":schoolYear,
//					"termCode":termCode,
//					"param":param
//				},
//			});
//        },
//        getCourseCategoryList:function(schoolYear,termCode,param) {
//			return http.post({
//				url : "scoreByStuLb/getCourseCategoryList",
//				data:{
//					"schoolYear":schoolYear,
//					"termCode":termCode,
//					"param":param
//				},
//			});
//        },
        getCourseAttrList:function(schoolYear,termCode,param) {
			return http.post({
				url : "scoreByStuLb/getCourseAttrList",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"param":param
				},
			});
        },
        getCourseNatureList:function(schoolYear,termCode,param) {
			return http.post({
				url : "scoreByStuLb/getCourseNatureList",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"param":param
				},
			});
        },
        getCourseList:function(schoolYear,termCode,type,category,attr,nature,param){
			return http.post({
				url : "scoreByStuLb/getCourseList",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"param":param
				},
			});
        },
        getScoreByOriginList:function(schoolYear,termCode,type,category,attr,nature,course,tag,param,callback){
			return http.post({
				url : "scoreByStuLb/getScoreByOriginList",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"course":course,
					"tag":tag,
					"param":param
				},
				success : function(data) {
					var distribute = {};
					distribute.originCfg = {
							type : "bar",
							data : data.list,
							name : '分',
							noDataText:'暂无各生源地成绩对比数据'
						};
					distribute.id = data.id;
					distribute.mc = data.mc;
					callback(distribute);
				}
			});
        },
        getScoreByNationList:function(schoolYear,termCode,type,category,attr,nature,course,tag,param,callback){
			return http.post({
				url : "scoreByStuLb/getScoreByNationList",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"course":course,
					"tag":tag,
					"param":param
				},
				success : function(data) {
					var distribute = {};
					distribute.nationCfg = {
							type : "bar",
							data : data.list,
							name : '分',
							noDataText:'暂无各民族成绩对比数据'
						};
					distribute.id = data.id;
					distribute.mc = data.mc;
					callback(distribute);
				}
			});
        },
        getScoreByZzmmList:function(schoolYear,termCode,type,category,attr,nature,course,tag,param,callback){
			return http.post({
				url : "scoreByStuLb/getScoreByZzmmList",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"course":course,
					"tag":tag,
					"param":param
				},
				success : function(data) {
					var distribute = {};
					distribute.zzmmCfg = {
							type : "bar",
							data : data.list,
							name : '分',
							noDataText:'暂无各政治面貌成绩对比数据'
						};
					distribute.id = data.id;
					distribute.mc = data.mc;
					callback(distribute);
				}
			});
        },
        getScoreFbByOrigin:function(schoolYear,termCode,type,category,attr,nature,course,originId,param,callback){
			return http.post({
				url : "scoreByStuLb/getScoreFbByOrigin",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"course":course,
					"originId":originId,
					"param":param
				},
				success : function(data) {
					var distribute = {};
					distribute.originPieCfg = {
							type : "pie",
							data : data,
							name : '人',
							noDataText:'暂无成绩分布数据'
						};
					callback(distribute);
				}
			});
        },
        getScoreFbByNation:function(schoolYear,termCode,type,category,attr,nature,course,nationId,param,callback){
			return http.post({
				url : "scoreByStuLb/getScoreFbByNation",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"course":course,
					"nationId":nationId,
					"param":param
				},
				success : function(data) {
					var distribute = {};
					distribute.nationPieCfg = {
							type : "pie",
							data : data,
							name : '人',
							noDataText:'暂无成绩分布数据'
						};
					callback(distribute);
				}
				
			});
        },
        getScoreFbByZzmm:function(schoolYear,termCode,type,category,attr,nature,course,zzmmId,param,callback){
			return http.post({
				url : "scoreByStuLb/getScoreFbByZzmm",
				data:{
					"schoolYear":schoolYear,
					"termCode":termCode,
					"type":type,
					"category":category,
					"attr":attr,
					"nature":nature,
					"course":course,
					"zzmmId":zzmmId,
					"param":param
				},
				success : function(data) {
					var distribute = {};
					distribute.zzmmPieCfg = {
							type : "pie",
							data : data,
							name : '人',
							noDataText:'暂无成绩分布数据'
						};
					callback(distribute);
				}
			});
        },
	}
}]);