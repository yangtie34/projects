app.service("homeService",['httpService',function(http){
    return {
    	getHomeUrl : function(){
    		return http.post({
    			url : "/thesis/home/test",
    			data : {}
    		});
    	}
    }
}]);
