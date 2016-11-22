app.service("topicService",['httpService',function(http){
    return {
    	//保存新帖子
    	saveTopic : function(topic){
    		return http.post({
    			url : "social/topic/save",
    			data : topic
    		});
    	},
    	//查询所有帖子列表
    	queryAllTopicList : function(page){
    		return http.post({
    			url : "social/topic/queryAll",
    			data : page
    		});
    	},
    	queryAllOfFriend : function(page){
  	    	return http.post({
  	    		url : "social/topic/queryAllOfFriend",
  	    		data : page
  	    	});
  	    },
    	queryTopicDetail : function(id){
    		return http.post({
    			url : "social/topic/queryTopicInfo",
    			data : {
    				id : id
    			}
    		});
    	},
    	deleteTopic : function(id){
    		return http.post({
    			url : "social/topic/deleteTopic",
    			data : {
    				id : id
    			}
    		});
    	},
    	queryMyTopicList : function(page){
    		return http.post({
    			url : "social/topic/queryMyTopicList",
    			data : page
    		});
    	},
    	 queryTopicNumOfToday : function(){
 	    	return http.post({
 	    		url : "social/personal/queryTopicNumOfToday",
 	    		data : {}
 	    	});
 	    },
 	   saveReply : function(params){
 	    	return http.post({
 	    		url : "social/topic/saveReply",
 	    		data : params
 	    	});
 	    },
 	    saveReplyAnswer : function(params){
 	    	return http.post({
 	    		url : "social/topic/saveReplyAnswer",
 	    		data : params
 	    	});
 	    },
    }
}]);