app.service("paisanService",['httpService',function(http){
    return {       
         getPaisan : function(){
        	return http.post({
                url : "student/paisan/paisan",
                data : []
            })
        },
        getPaisanParam : function(p,paramname,paramflag){
        	return http.post({
                url : "student/paisan/paisanParam",
                data : {currpage:p,stuname:paramname,flag:paramflag}
            })
        },
        getPaisanStu : function(stuId){
        	return http.post({
                url : "student/paisan/paisanStu",
                data : {stu_id:stuId}
            })
        }
    }
}]);
