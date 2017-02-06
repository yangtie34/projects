app.service("service",['httpService',function(http){
    return {
    	//查询个人奖励总金额
    	getPersonalTotal : function(teaId,year){ 
    		return http.post({
    			url : "kyjl/personal/total",
    			data : {
    				teaId : teaId,
    				year: year
    			}
    		});
    	},
    	//查询个人奖励各奖项分布
    	getPersonalAward : function(teaId,year){ 
    		return http.post({
    			url : "kyjl/personal/award",
    			data : {
    				teaId : teaId,
    				year: year
    			}
    		});
    	},
    	//查询个人奖励立项奖
    	querySetup : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/setup",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	},
    	//查询个人奖励结项奖
    	queryEnd : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/end",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	},
    	//查询个人奖励获奖成果奖
    	queryAchievement : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/achievement",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	},
    	//查询个人奖励论文奖
    	queryThesis : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/thesis",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	},
    	//查询个人奖励发明专利奖
    	queryPatent : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/patent",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	},
    	//查询个人奖励科研经费奖
    	queryAwardFund : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/awardfund",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	},
    	//查询个人奖励成果转化奖
    	queryTransform : function(page,year,teaId){
    		return http.post({
    			url : "kyjl/personal/transform",
    			data : {
    				curpage : page.index,
    				pagesize : page.size,
    				year:year,
    				teaId: teaId
    			}
    		})
    	}
    }
}]);
