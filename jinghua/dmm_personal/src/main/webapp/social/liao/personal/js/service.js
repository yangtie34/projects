app.service("personalService",['httpService',function(http){
    return {
    	queryPersonalInfo : function(username){
    		return http.post({
    			url : "social/personal/queryPersonalInfo",
    			data : {username:username}
    		});
    	},
	    queryMyFriendAndTopicNums : function(){
	    	return http.post({
	    		url : "social/personal/queryMyFriendAndTopicNums",
	    		data : {}
	    	});
	    },
	    queryUnreadMessageNum : function(){
    		return http.post({
    			url : "social/personal/queryMyUnreadMessageNum",
    			data : {}
    		});
    	},
	    queryUnreadMessage : function(){
	    	return http.post({
	    		url : "social/personal/queryMyUnreadMessage",
	    		data : {}
	    	});
	    },
	    queryCard : function(username){
	    	return http.post({
	    		url : "social/personal/queryCard",
	    		data : {username:username}
	    	});
	    },
	    queryBook : function(username){
	    	return http.post({
	    		url : "social/personal/queryBook",
	    		data : {username:username}
	    	});
	    },
	    queryCourse : function(username){
	    	return http.post({
	    		url : "social/personal/queryCourse",
	    		data : {username:username}
	    	});
	    },
	    sendRequest : function(username){
    		return http.post({
    			url : "social/friend/sendFriendApply",
    			data : {targetUsername:username}
    		});
    	}
    }
}]);
