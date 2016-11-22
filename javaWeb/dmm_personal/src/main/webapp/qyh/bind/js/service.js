app.service("bindService",['httpService',function(http){
    return {
    	bindUserInfo : function(user){
    		return http.post({
    			url : "qyh/bindInfo",
    			data : user
    		});
    	}
    }
}]);
