app.service("bindService",['httpService',function(http){
    return {
    	bindUserInfo : function(user){
    		return http.post({
    			url : "student/bind/wechat",
    			data : user
    		});
    	}
    }
}]);
