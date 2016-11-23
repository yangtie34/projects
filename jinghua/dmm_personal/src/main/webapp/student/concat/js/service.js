app.service("concatService",['httpService',function(http){
    return {       
		getConcat : function(){
           return http.post({
                url : "student/concat/concat",
                data : []
            })
        },
        getConcatParam : function(sparam){
           return http.post({
                url : "student/concat/concatParam",
                data : {param:sparam}
            })
        }
    }
}]);
