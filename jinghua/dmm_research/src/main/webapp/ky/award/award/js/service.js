app.service("service",['httpService',function(http){
    return {
    	//查询奖励金额
    	queryFund : function(condition){ 
    		return http.post({
    			url : "kyjl/award/fund",
    			data : {
    				year: condition.year,
    				xkmlid:condition.subject.id,
    				zzjgid : condition.dept.id
    			}
    		});
    	},
    	//查询科研奖励项金额分布
    	queryAwardPie : function(condition){
    		return http.post({
    			url : "kyjl/award/pie",
    			data : {
    				year: condition.year,
    				xkmlid:condition.subject.id,
    				zzjgid : condition.dept.id
    			}
    		})
    	},
    	//查询获奖人员部门分布
    	queryDept : function(condition){
    		return http.post({
    			url : "kyjl/award/dept",
    			data : {
    				year: condition.year,
    				xkmlid:condition.subject.id,
    				zzjgid : condition.dept.id
    			}
    		})
    	},
    	//查询科研人员排名
    	queryAwardPeople : function(page,condition,params){
    		return http.post({
    			url : "kyjl/award/people",
    			data : {
    				curpage : page.curpage,
    				pagesize : page.pagesize,
    				sumcount : page.sumcount,
    				year:condition.year,
    				xkmlid:condition.subject.id,
    				zzjgid : condition.dept.id,
    				param: params.param
    			}
    		})
    	}
    }
}]);
