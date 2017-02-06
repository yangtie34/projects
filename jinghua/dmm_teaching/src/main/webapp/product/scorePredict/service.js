/**
 * 成绩预测
 */
app.service("service",['httpService',function(http){
    return {
    	ctol : "scorePredict",
    	pmsCtol:"pmsScorePredict",
    	getYearAndTerm : function(callback){
        	http.post({
        		url  : this.ctol+"/getYearAndTerm",
	    		success : function(data){
	    			callback(data);
	    		}
        	});
        },
        queryCourseByType : function(id,schoolYear,termCode,callback){
        	http.post({
        		url  : this.ctol+"/queryCourseByType",
        		data : {
        			"id":id,
        			"schoolYear":schoolYear,
    				"termCode":termCode
    				},
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        queryGkInfo:function(param,schoolYear,termCode,edu,majorType,callback){//得到辅导员总体信息
    		http.post({
    			url  : this.ctol+"/queryGkInfo",
        		data : {
    				"param":param,
    				"schoolYear":schoolYear,
    				"termCode":termCode,
    				"edu":edu,
    				"majorType":majorType
    				},
    			success : function(data){
    				callback(data[0]);
    			}
    		});	
    	},
    	queryScoreInfo:function(param,schoolYear,termCode,edu,courseType,courseid,majorType,callback){//成绩预测概况
    	http.post({
    		url  : this.ctol+"/queryScoreInfo",
    		data : {
    			"param":param,
				"schoolYear":schoolYear,
				"termCode":termCode,
				"edu":edu,
				"courseType":courseType,
				"courseid"  :courseid,
				"majorType" :majorType
    			},
    		success : function(data){
    			callback(data[0]);
    		}
    	});	
    },
    //成绩分布(按课程)
    queryScorefb:function(param,schoolYear,termCode,edu,majorType,callback){//成绩预测概况
    	http.post({
    		url  : this.ctol+"/queryScorefb",
    		data : {
    			"param":param,
    			"schoolYear":schoolYear,
    			"termCode":termCode,
    			"edu":edu,
    			"majorType" :majorType
    		},
    		success : function(data){
    			callback(data);
    		}
    	});	
    },
    //成绩分布(按课程性质)
    queryScorefbByNature:function(param,schoolYear,termCode,edu,majorType,callback){//成绩预测概况
    	http.post({
    		url  : this.ctol+"/queryScorefbByNature",
    		data : {
    			"param":param,
    			"schoolYear":schoolYear,
    			"termCode":termCode,
    			"edu":edu,
    			"majorType" :majorType
    		},
    		success : function(data){
    			var option = {
				        isSort : false,
				        yAxis : "人",
				        data : data,
				        type :"bar",  //图表类型(bar,line,area,spline)
			        	config : {
			        		config :{
		    					on :['CLICK','COURSETYPEMC']
		    				},
		    				 title : {
	     	    			        text: '预测成绩分布',
	     	    			        show:false
	     	    			    },
			        		tooltip : {
//			        			formatter:'{b}<br>{a}:{c}人',
			        	        formatter: function (params,ticket,callback) {
//			        	            console.log(params);
			        	        	var unit=['人次','人次'];
			        	            var res = params[0].name;
			        	            for (var i = 0, l = params.length; i < l; i++) {
			        	            	res += '<br/>' + params[i].seriesName + ' : ' + params[i].value
			        	            	if(params[i].seriesName=='人数'){
			        	            		res +=unit[0];
			        	            	}else{
			        	            		res +=unit[1];
			        	            	}
			        	            }
			        	            return res;
			        	        }
			        		},
			        		noDataText:'暂无成绩分布数据',
			        		  xAxis : [
		    	    			        {
		    	    			        	name : '分',
		    	    			            type : 'category',
		    	    			        }
		    	    			    ],
		    	    			    grid :{x:80},
		    	    			     yAxis : [
		    	    			              {
		    	    			                  type : 'value',
		    	    			                  name : '人次',
		    	    			              },
		    	    			    ],
			        	}
				};
			callback(option);
    		}
    	});	
    },
    	//高级搜索
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "teaching_scorePredict" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	});
	    },
	    //得到下钻详细信息
	    getStuDetail : function(param, callback,error){
        	http.post({
        		url  : "pmsScorePredict/getStuDetail",
        		data : param,
        		success : function(data){
        			callback(data);
        		},
        		error:error
        	});
        },	
        //导出
        getStuDetailDown : function(param, callback){
        	http.fileDownload({
        		url  : "pmsScorePredict/down",
        		data : param,
        		success : function(){
        			alert("success");
        		}
        	})
        },
    };
}]);