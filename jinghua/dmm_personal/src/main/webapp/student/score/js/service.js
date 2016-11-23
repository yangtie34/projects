app.service("scoreService",['httpService',function(http){
    return {       
        getLastScore : function(){
           return http.post({
                url : "student/score/lastScore",
                data : []
            })
        },
        getProportion : function(){
           return http.post({
                url : "student/score/proportion",
                data : []
            })
        },
        getScoreList : function(){
           return http.post({
                url : "student/score/scoreList",
                data : []
            })
        },
        getCredit : function(){
           return http.post({
                url : "student/score/credit",
                data : []
            })
        },
        getCreditType : function(){
           return http.post({
                url : "student/score/creditType",
                data : []
            })
        }

    }
}]);
