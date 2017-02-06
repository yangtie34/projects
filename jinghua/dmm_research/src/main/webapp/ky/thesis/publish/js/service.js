app.service("service",['httpService',function(http){
    return {
    	//SCI论文发表总量
    	queryTotal : function(condition){
    		return http.post({
    			url : "thesis/gyxlqk/pub/total",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//发表量变化趋势
    	queryYearChange : function(condition){
    		return http.post({
    			url : "thesis/gyxlqk/pub/yearchange",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//各单位论文发表量和占比
    	queryDepts : function(condition){
    		return http.post({
    			url : "thesis/gyxlqk/pub/depts",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	//单位毎年论文发表量和占比
    	queryIncludeNumsByPeriodical : function(condition){
    		return http.post({
    			url : "thesis/gyxlqk/pub/periodical/includenums",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id 
    			}
    		});
    	},
    	queryGyxlqkPubList : function(condition,page){
    		return http.post({
    			url : "thesis/gyxlqk/pub/list",
    			data : {
    				xkmlid: condition.subject.id,
    				startYear: condition.definedYear.start,
    				endYear: condition.definedYear.end,
    				zzjgid: condition.dept.id,
    				name : params.name,
    				flag : params.flag,
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount
    			}
    		})
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
    	}
    }
}]);
