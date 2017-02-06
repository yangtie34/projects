app.service("service",['httpService',function(http){
    return {
    	getAllTypes : function(condition){
    		return http.post({
    			url : "thesis/gyxlqk/in/types",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	getIncludeNumsOfDepts : function(periodicalType,condition){
    		return http.post({
    			url : "thesis/gyxlqk/in/depts",
    			data : {
    				periodicalType : periodicalType,
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	getIncludeNumsOfYears : function(periodicalType,condition){
    		return http.post({
    			url : "thesis/gyxlqk/in/years",
    			data : {
    				periodicalType : periodicalType,
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	getSciImpact : function(periodicalType,condition){
    		return http.post({
    			url : "thesis/gyxlqk/in/sci/impact",
    			data : {
    				periodicalType : periodicalType,
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	getSciZone : function(periodicalType,condition){
    		return http.post({
    			url : "thesis/gyxlqk/in/sci/zone",
    			data : {
    				periodicalType : periodicalType,
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	generateChartData : function(data){
			var xAxisData = [],seriesData = [];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.name);
				seriesData.push(it.value);
			}
			return {
				xAxisData : xAxisData,
				seriesData : seriesData
			};
    	},
    	generateTwoChartData : function(data){
			var xAxisData = [],seriesData = [[],[]];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				xAxisData.push(it.name);
				seriesData[0].push(it.value1);
				seriesData[1].push(it.value2);
			}
			return {
				xAxisData : xAxisData,
				seriesData : seriesData
			};
    	}
    }
}]);
