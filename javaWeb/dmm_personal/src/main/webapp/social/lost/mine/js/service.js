app.service("mineService",['httpService',function(http){
    return {
    	deleteTopic : function(id){
    		return http.post({
    			url : "social/lost/mine/deleteTopic",
    			data: {
    				id : id
    			}   
    		});
    	},
    	queryMyTopic : function(username){
    		return http.post({
    			url:"social/lost/mine/queryMyTopic",
    			data : username
    		});
    	},
    	saveTopic : function(topic){
    		return http.post({
    			url : "social/lost/mine/saveTopic",
    			data : topic
    		});
    	},
    	modifyStatus : function(id){
    		return http.post({
    			url : "social/lost/mine/modifyStatus",
    			data : {
    				id : id
    			}
    		});
    	}
    }
}]);
