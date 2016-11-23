app.service("studentService",['httpService',function(http){
    return {
        getStudentInfo : function(){
        	return http.post({
        		 url : "student/main/getSelfInfo",
                 data : {}
        	});
        },
        queryStudentInfoDetail : function(){
        	return http.post({
                url : "student/main/getSelfInfoDetial",
                data : {}
            });
        },
        updateStudentTelephone : function(tel){
        	return http.post({
                url : "student/main/updateTelOfStudent",
                data : {tel : tel}
            });
        }
    }
}]);
