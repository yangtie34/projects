app.service("smartStudentService",['httpService',function(http){
    return {
    	//请求成绩信息
    	compareScore : function(){
    		return http.post({
    			url : "student/smart/compareScore",
    			data : {}
    		});
    	},
    	//请求消费数据
    	compareConsume : function(){
    		return http.post({
    			url : "student/smart/compareConsume",
    			data : {}
    		});
    	},
    	//请求用餐数据
    	compareDinner : function(){
    		return http.post({
    			url : "student/smart/compareDinner",
    			data : {}
    		});
    	},
    	//请求借阅信息
    	compareBook : function(){
    		return http.post({
    			url : "student/smart/compareBook",
    			data : {}
    		});
    	}/*,
    	//请求阅读偏好
    	compareBookType : function(){
    		return http.post({
    			url : "student/smart/compareBookType",
    			data : {}
    		});
    	}*/
    }
}]);
