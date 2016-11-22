app.service("daliyLifeService",['httpService',function(http){
    return {       
        getMonthConsume :function(){
        	return http.post({
        		url : "teacher/dailylife/monthConsume",
        		data : []
        	})
        },
        getTotalConsume :function(){
        	return http.post({
        		url : "teacher/dailylife/totalConsume",
        		data : []
        	})
        },
        getBorrow : function(){
        	return http.post({
        		url : "teacher/dailylife/borrow",
        		data : []
        	})
        },
        getMonthConsumeListPage : function(month,p,f){
        	return http.post({
        		url : "teacher/dailylife/monthConsumeListPage",
        		data : {monthStart:month,currpage:p,flag:f}
        	})
        },
        getMonthPayType : function(month){
        	return http.post({
        		url : "teacher/dailylife/monthPayType",
        		data : {monthStart:month}
        	})
        },
        getPayHistory : function(cy){
        	return http.post({
        		url : "teacher/dailylife/payHistory",
        		data : {currYear:cy}
        	})
        },
        getInBorrow : function(){
        	return http.post({
        		url : "teacher/dailylife/inBorrow",
        		data : []
        	})
        },
        getRecommentBorrow : function(){
        	return http.post({
        		url : "teacher/dailylife/recommentBorrow",
        		data : []
        	})
        },
        getOutOfDateBorrow : function(){
        	return http.post({
        		url : "teacher/dailylife/outOfDateBorrow",
        		data : []
        	})
        },
        getReturnBorrow : function(){
        	return http.post({
        		url : "teacher/dailylife/returnBorrow",
        		data : []
        	})
        },
        getStopCounts : function(){
        	return http.post({
        		url : "teacher/dailylife/stopCounts",
        		data : []
        	})
        },
        getStopTimeAvg : function(){
        	return http.post({
        		url : "teacher/dailylife/stopTimeAvg",
        		data : []
        	})
        },
        getCarStop : function(){
        	return http.post({
        		url : "teacher/dailylife/carStop",
        		data : []
        	})
        }
        
    }
}]);
