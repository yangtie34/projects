app.service("homeService",['httpService',function(http){
    return {
    	queryLoginInfo : function(){
    		return http.post({
    			url : "business/home/logininfo",
    			data : {}
    		});
    	}
    }
}]);
