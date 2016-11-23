app.service("bindService",['httpService',function(http){
    return {
    	bindUserInfo : function(user){
    		return http.post({
    			url : "teacher/bind/wechat",
    			data : user
    		});
    	}
    }
}]);
