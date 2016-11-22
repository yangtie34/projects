app.service("salaryService",['httpService',function(http){
    return {       
        getLastSalary : function(){
           return http.post({
                url : "teacher/salary/lastSalary",
                data : []
            })
        },
        getTotalSalary : function(){
        	return http.post({
        		url : "teacher/salary/totalSalary",
        		data : []
        	})
        },
        getSalaryCompose : function(){
        	return http.post({
        		url : "teacher/salary/salaryCompose",
        		data : []
        	})
        },
        getRetireTotalSalary : function(){
        	return http.post({
        		url : "teacher/salary/retireTotalSalary",
        		data : []
        	})
        },
        getHistorySalary : function(){
        	return http.post({
        		url : "teacher/salary/historySalary",
        		data : []
        	})
        },
        getFiveYearSalary : function(){
        	return http.post({
        		url : "teacher/salary/fiveYearSalary",
        		data : []
        	})
        },
        getLastSalaryCom : function(y,m){
        	return http.post({
        		url : "teacher/salary/lastSalaryCom",
        		data : {year_:y,month_:m}
        	})
        },
        getLastSalaryPayable : function(y,m){
        	return http.post({
        		url : "teacher/salary/lastSalaryPayable",
        		data : {year_:y,month_:m}
        	})
        },
        getLastSalaryTotal : function(y,m){
        	return http.post({
        		url : "teacher/salary/lastSalaryTotal",
        		data : {year_:y,month_:m}
        	})
        },
        getLastSalarySubtract : function(y,m){
        	return http.post({
        		url : "teacher/salary/lastSalarySubtract",
        		data : {year_:y,month_:m}
        	})
        },
        getLastSalarySubtractTotal : function(y,m){
        	return http.post({
        		url : "teacher/salary/lastSalarySubtractTotal",
        		data : {year_:y,month_:m}
        	})
        }

    }
}]);
