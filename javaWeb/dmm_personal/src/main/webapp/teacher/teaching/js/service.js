app.service("teachingService",['httpService',function(http){
    return {       
        getTodayClass : function(){
           return http.post({
                url : "teacher/teaching/todayClass",
                data : []
            })
        },
        getTermClass : function(){
           return http.post({
                url : "teacher/teaching/termClass",
                data : []
            })
        },
        getTodayCourse : function(caid,cid){
           return http.post({
                url : "teacher/teaching/todayCourse",
                data : {courseArrangementId:caid,courseId:cid}
            })
        },
        getClassSchedule : function(w){
        	return http.post({
                url : "teacher/teaching/classSchedule",
                data : {week:w}
            })
        },
        getWeek : function(w,rq,f){
           return http.post({
                url : "teacher/teaching/week",
                data : {week:w,zyrq:rq,flag:f}
            })
        }

    }
}]);
