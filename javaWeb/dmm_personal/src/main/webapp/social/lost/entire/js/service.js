app.service("allTopicService",['httpService',function(http){
    return {
    	queryAllTopic : function(params){
    		return http.post({
    			url:"social/lost/entire/queryallTopic",
    			data:params
    		});
    	}
    }
}]);
