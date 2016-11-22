app.service("schoolService",['httpService',function(http){
    return { 
    	getSchool : function(){
           return http.post({
                url : "student/school/school",
                data : []
            })
        },
        getCounts : function(){
           return http.post({
                url : "student/school/counts",
                data : []
            })
        },
        getPeopleCounts : function(){
        	return http.post({
                url : "student/school/peopleCounts",
                data : []
            })
        }

    }
}]);
