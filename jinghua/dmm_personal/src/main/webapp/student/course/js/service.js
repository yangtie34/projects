app.service("courseService",['httpService',function(http){
    return {       
        getToday : function(){
           return http.post({
                url : "student/course/today",
                data : []
            })
        },
        getTodayCourse : function(){
           return http.post({
                url : "student/course/todayCourse",
                data : []
            })
        },
        getSchedule : function(w){
           return http.post({
                url : "student/course/schedule",
                data : {week:w}
            })
        },
        getWeek : function(w,rq,f){
           return http.post({
                url : "student/course/week",
                data : {week:w,zyrq:rq,flag:f}
            })
        }

    }
}]);
