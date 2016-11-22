app.service("bookService",['httpService',function(http){
    return {       
         getBorrowCounts : function(){
        	return http.post({
                url : "student/book/borrowCounts",
                data : []
            })
        },
        getBorrowProportion : function(){
        	return http.post({
                url : "student/book/borrowProportion",
                data : []
            })
        },
        getRecommendBook : function(){
        	return http.post({
                url : "student/book/recommendBook",
                data : []
            })
        },
        getBorrowList : function(p){
        	return http.post({
                url : "student/book/borrowList",
                data : {currpage:p}
            })
        },
        getBorrowType : function(){
        	return http.post({
                url : "student/book/borrowType",
                data : []
            })
        }
    }
}]);
