app.service("teacherService",['httpService',function(http){
    return {
        getTeacherInfo : function(){
        	return http.post({
        		 url : "teacher/getSelfInfo",
                 data : {}
        	});
        },
        queryPersonalInfoDetail : function(){
        	return http.post({
                url : "teacher/getSelfInfoDetial",
                data : {}
            })
        	
        },
        submitAdvice : function(advice){
        	return http.post({
                url : "teacher/submitAdvice",
                data : {advice:advice}
            })
        	
        },
        queryTeacherHistoryList : function(){
        	 return http.post({
                 url : "teacher/getSelfHistoryList",
                 data : {}
             })
        },
        queryTeacherHistoryInfo : function(){
        	return http.post({
        		url : "teacher/getSelfHistoryInfo",
        		data : {}
        	});
        }
    }
}]);
