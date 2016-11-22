app.service("friendService",['httpService',function(http){
    return {
    	queryMyFriendList : function(){
    		return http.post({
    			url : "social/friend/queryMyFriendList",
    			data:{}
    		});
    	},
    	delFriendShip : function(id){
    		return http.post({
    			url : "social/friend/delFriendShip",
    			data : {
    				id : id 
    			}
    		});
    	},
    	
    	searchUserList : function(page){
    		return http.post({
    			url : "social/friend/searchUserList",
    			data : page
    		});
    	},
    	
    	passApply : function(id){
    		return http.post({
    			url : "social/friend/passFriendApply",
    			data : {
    				id : id
    			}
    		});
    	},
    	ignoreApply : function(id){
    		return http.post({
    			url : "social/friend/ignoreFriendApply",
    			data : {
    				id : id
    			}
    		});
    	},
    	
    	sendFriendApply : function(username){
    		return http.post({
    			url : "social/friend/sendFriendApply",
    			data : {
    				targetUsername : username
    			}
    		});
    	}
    }
}]);
