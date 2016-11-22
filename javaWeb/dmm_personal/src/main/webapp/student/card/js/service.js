app.service("cardService",['httpService',function(http){
    return {
    	queryYktYe : function(){
    		return http.post({
    			url : "student/card/queryYktYe",
    			data : {}
    		});
    	},
    	queryMonthConsumeList : function(page){
    		return http.post({
    			url : "student/card/queryYktMonthConsume",
    			data : page
    		});
    	},
    	queryMonthConsumeDetail : function(page,month){
    		page.month = month;
    		return http.post({
    			url : "student/card/queryYktMonthConsumeDetail",
    			data : page
    		});
    	},
    	queryConsumeTotalInfo : function(){
    		return http.post({
    			url : "student/card/queryStudentConsumeTotal",
    			data : {}
    		});
    	}
    }
}]);
