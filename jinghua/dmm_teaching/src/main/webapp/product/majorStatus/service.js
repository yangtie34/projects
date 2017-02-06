app.service("service",['httpService', function(http, constant){
    return {
    	
    	ctol : "majorStatus",
    	
    	queryXn : function(callback){
    		http.post({
    			url  : this.ctol+"/queryXn",
    			success : function(data){
    				callback(data);
    			}
    		});
    	},
    	
    	queryMajorScoreList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorScoreList",
	    		data : param,
	    		success : function(data){
	    			callback(data.list, data.bzdm, data.pagecount, data.sumcount);
	    		}
        	});
        },
        queryMajorScoreHis : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorScoreHis",
        		data : param,
        		success : function(data){
        			var list = data.list,
        				bzdm = data.bzdm;
        			var type_score  = 'bar',
	    				type_rank   = 'line',
	    				index_score = 0,
        				index_rank  = 1,
        				legend_ary = ['平均成绩'],
        				yname_ary  = ['分', '名'],
        				value_ary  = ['value'],
        				type_ary   = [type_score], // 平均成绩柱状图
        				yIndex_ary = [index_score];
        			for(var i=0,len=bzdm.length; i<len; i++){
        				legend_ary.push(bzdm[i].mc);
        				value_ary.push(bzdm[i].id);
        				type_ary.push(type_score);
        				yIndex_ary.push(index_score);
        			}
        			legend_ary.push('平均成绩排名');
        			value_ary.push('ranking_value');
        			type_ary.push(type_rank);
        			yIndex_ary.push(index_rank);
        			
        			var option = Ice.echartOption(list, {
        				legend_ary : legend_ary,
        				yname_ary  : yname_ary,
        				value_ary  : value_ary,
        				type_ary   : type_ary,
        				yIndex_ary : yIndex_ary,
        				xname_append : '学年'
        			});
        			callback(option);
        		}
        	});
        },
        
        queryMajorFailScaleList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorFailScaleList",
        		data : param,
        		success : function(data){
        			callback(data.list, data.bzdm, data.pagecount, data.sumcount);
        		}
        	});
        },
        queryMajorFailScaleHis : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorFailScaleHis",
        		data : param,
        		success : function(data){
        			var list = data.list,
        				bzdm = data.bzdm;
        			var type_score  = 'bar',
	    				type_rank   = 'line',
	    				index_score = 0,
        				index_rank  = 1,
        				legend_ary = ['平均不及格率'],
        				yname_ary  = ['%', '名'],
        				value_ary  = ['value'],
        				type_ary   = [type_score], // 平均成绩柱状图
        				yIndex_ary = [index_score];
        			for(var i=0,len=bzdm.length; i<len; i++){
        				legend_ary.push(bzdm[i].mc);
        				value_ary.push(bzdm[i].id);
        				type_ary.push(type_score);
        				yIndex_ary.push(index_score);
        			}
        			legend_ary.push('平均不及格率排名');
        			value_ary.push('ranking_value');
        			type_ary.push(type_rank);
        			yIndex_ary.push(index_rank);
        			
        			var option = Ice.echartOption(list, {
        				legend_ary : legend_ary,
        				yname_ary  : yname_ary,
        				value_ary  : value_ary,
        				type_ary   : type_ary,
        				yIndex_ary : yIndex_ary,
        				xname_append : '学年'
        			});
        			callback(option);
        		}
        	});
        },
        
        queryMajorEvaluateTeachList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorEvaluateTeachList",
        		data : param,
        		success : function(data){
        			callback(data.list, data.bzdm, data.pagecount, data.sumcount);
        		}
        	});
        },
    	queryMajorEvaluateTeachHis : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorEvaluateTeachHis",
        		data : param,
        		success : function(data){
        			var list = data.list,
        				bzdm = data.bzdm;
        			var type_score  = 'bar',
	    				type_rank   = 'line',
	    				index_score = 0,
        				index_rank  = 1,
        				legend_ary = ['评教平均分'],
        				yname_ary  = ['分', '名'],
        				value_ary  = ['value'],
        				type_ary   = [type_score], // 平均成绩柱状图
        				yIndex_ary = [index_score];
        			for(var i=0,len=bzdm.length; i<len; i++){
        				legend_ary.push(bzdm[i].mc);
        				value_ary.push(bzdm[i].id);
        				type_ary.push(type_score);
        				yIndex_ary.push(index_score);
        			}
        			legend_ary.push('评教平均分排名');
        			value_ary.push('ranking_value');
        			type_ary.push(type_rank);
        			yIndex_ary.push(index_rank);
        			
        			var option = Ice.echartOption(list, {
        				legend_ary : legend_ary,
        				yname_ary  : yname_ary,
        				value_ary  : value_ary,
        				type_ary   : type_ary,
        				yIndex_ary : yIndex_ary,
        				xname_append : '学年'
        			});
        			callback(option);
        		}
        	});
        },
        
        queryMajorByJyList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorByJyList",
        		data : param,
        		success : function(data){
        			callback(data.list, data.bzdm, data.pagecount, data.sumcount);
        		}
        	});
        },

        queryMajorByHis : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorByHis",
        		data : param,
        		success : function(data){
        			var list = data.list;
	    			var type_score  = 'bar',
	    				type_rank   = 'line',
	    				index_score = 0,
	    				index_rank  = 1,
	    				legend_ary = ['毕业率'],
	    				yname_ary  = ['%', '名'],
	    				value_ary  = ['value'],
	    				type_ary   = [type_score], // 平均成绩柱状图
	    				yIndex_ary = [index_score];
	    			legend_ary.push('毕业率排名');
	    			value_ary.push('ranking_value');
	    			type_ary.push(type_rank);
	    			yIndex_ary.push(index_rank);
	    			
	    			var option = Ice.echartOption(list, {
	    				legend_ary : legend_ary,
	    				yname_ary  : yname_ary,
	    				xname_ary  : ['学年'],
	    				value_ary  : value_ary,
	    				type_ary   : type_ary,
	    				yIndex_ary : yIndex_ary,
	    				xname_append : true
	    			});
	    			callback(option);
        		}
        	});
        },
        queryMajorJyHis : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorJyHis",
        		data : param,
        		success : function(data){
        			var list = data.list;
	    			var type_score  = 'bar',
	    				type_rank   = 'line',
	    				index_score = 0,
	    				index_rank  = 1,
	    				legend_ary = ['就业率'],
	    				yname_ary  = ['%', '名'],
	    				value_ary  = ['value'],
	    				type_ary   = [type_score], // 平均成绩柱状图
	    				yIndex_ary = [index_score];
	    			legend_ary.push('就业率排名');
	    			value_ary.push('ranking_value');
	    			type_ary.push(type_rank);
	    			yIndex_ary.push(index_rank);
	    			
	    			var option = Ice.echartOption(list, {
	    				legend_ary : legend_ary,
	    				yname_ary  : yname_ary,
	    				xname_ary  : ['学年'],
	    				value_ary  : value_ary,
	    				type_ary   : type_ary,
	    				yIndex_ary : yIndex_ary,
	    				xname_append : true
	    			});
	    			callback(option);
        		}
        	});
        },
        
        queryMajorSearchList : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryMajorSearchList",
        		data : param,
        		success : function(data){
        			callback(data.list);
        		}
        	});
        },
        
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Teaching_score" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }
    }
}]);