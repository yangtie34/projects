app.service("colorPageService",['httpService',function(http){
    return {
    	//请求学校信息
    	getSchool : function(){
    		return http.post({
    			url : "student/colorPage/school",
    			data : {}
    		});
    	},
    	//请求辅导员信息
    	getFdy : function(){
    		return http.post({
    			url : "student/colorPage/fdy",
    			data : {}
    		});
    	},
    	//请求课程信息
    	getKc : function(){
    		return http.post({
    			url : "student/colorPage/kc",
    			data : {}
    		});
    	},
    	//请求学生数
    	getStu : function(){
    		return http.post({
    			url : "student/colorPage/stu",
    			data : {}
    		});
    	},
    	//请求同母校学生数
    	getTmx : function(){
    		return http.post({
    			url : "student/colorPage/tmx",
    			data : {}
    		});
    	},
    	//请求同乡学生数
    	getTx : function(){
    		return http.post({
    			url : "student/colorPage/tx",
    			data : {}
    		});
    	},
    	//请求用餐信息
    	getYc : function(){
    		return http.post({
    			url : "student/colorPage/yc",
    			data : {}
    		});
    	},
    	//请求专业
    	getMajor : function(){
    		return http.post({
    			url : "student/colorPage/major",
    			data : {}
    		});
    	}
    }
}]);
