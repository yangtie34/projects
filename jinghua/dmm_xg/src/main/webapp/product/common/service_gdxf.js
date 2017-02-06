app.service("service_gdxf",['httpService',function(http){
    return {
    	getDataEndDate : function(){
    		return http.post({
    			url  : "stuHighCost/getDataEndDate"
    		});
    	}
    }
}]);