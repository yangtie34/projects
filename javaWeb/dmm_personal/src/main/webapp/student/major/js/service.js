app.service("majorService",['httpService',function(http){
    return {       
        getMajor : function(){
           return http.post({
                url : "student/major/major",
                data : []
            })
        },
        getCourse : function(){
           return http.post({
                url : "student/major/course",
                data : []
            })
        },
        getCourseScore : function(){
        	return http.post({
                url : "student/major/courseScore",
                data : []
            })
        },
        getChooseCourse : function(p){
        	return http.post({
                url : "student/major/chooseCourse",
                data : {currpage:p}
            })
        },
        getPostgraduate : function(p){
        	return http.post({
                url : "student/major/postgraduate",
                data : {currpage:p}
            })
        }

    }
}]);
