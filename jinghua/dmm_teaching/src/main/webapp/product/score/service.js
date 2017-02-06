app.service("service",['httpService', 'constant', function(http, constant){
    return {
    	
    	ctol : "score",
    	
    	getBzdm : function(callback){
    		http.post({
    			url  : this.ctol+"/getBzdm",
    			success : function(data){
    				callback(data);
    			}
    		});
    	},
    	
    	getAbstract : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getAbstract",
	    		data : param,
	    		success : function(data){
	    			callback(data);
	    		}
        	});
        },
        
        getGroup : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getGroup",
        		data : param,
        		success : function(data){
        			var groupCfg = Ice.echartOption(data.list, {
        				yname_ary : ['人'],
        				xname_ary : ['绩点'],
        				type_ary  : ['bar'],
        				config    : { 
        					noDataText : '暂无成绩分组数据',
        					title : {
        						text : '成绩分组',
        						show : false
        					}
        				}
        			});
        			data.option = groupCfg;
        			callback(data);
        		}
        	});
        },
        
        getScale : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getScale",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        
        
        getGpaCourse : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getGpaCourse",
        		data : param,
        		success : function(data){
        			var attrAry      = data.attr,
        				natureAry    = data.nature,
        				scoreTypeCls = constant.scoreTypeCls,
        				scoreTypeLen = scoreTypeCls.length;
        			for(var i=0,len=natureAry.length; i<len; i++){
        				attrAry.push(natureAry[i]);
        			}
        			for(var i=0,len=attrAry.length; i<len; i++){
        				attrAry[i].cls = (i<scoreTypeLen-1) ? scoreTypeCls[i] : scoreTypeCls[scoreTypeLen-1];
        			}
        			callback(attrAry, data.bzdm);
        		}
        	})
        },
        
        getDisplayedLevelType : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getDisplayedLevelType",
        		data : param,
        		success : function(data){
        			var ary = [], level_type = data.level_type;
        			if(level_type != null){
        				if(level_type == 'YX'){
        					ary.push({id:'YX',mc:'按院系'});
        					ary.push({id:'ZY',mc:'按专业'});
        				}else if(level_type == 'ZY'){
        					ary.push({id:'ZY',mc:'按专业'});
        				}
        				ary.push({id:'BJ',mc:'按班级'});
        			}
        			ary.push({id:'SUBJECT',mc:'按学科'});
        			ary.push({id:'COURSE',mc:'按课程'});
    				ary.push({id:'TEACHER',mc:'按教师'});
        			callback(ary);
        		}
        	})
        },
        
        // 表头字段名称
//        headerName : ['平均数', '中位数', '众数', '方差', '标准差', '优秀率', '挂科率', '重修率', '低于平均数'],
        headerTitle : [ '一组数据中所有数据之和再除以这组数据的个数',
                        '把所有数据高低排序后正中间的一个为中位数，一种衡量集中趋势的指标',
                        '一组数据中出现次数最多的数值叫众数，就是一组数据中占比例最多的那个数',
                        '各个数据分别与其平均数之差的平方的和的平均数，方差不仅仅表达了样本偏离均值的程度，更是揭示了样本内部彼此波动的程度',
                        '标准差是方差的算术平方根，标准差能反映一个数据集的离散程度',
                        '成绩大于等于90分的学生所占比例',
                        '成绩小于60分的学生所占比例',
                        '成绩小于40分的学生所占比例',
                        '成绩低于平均数的学生所占比例',],
        getGridList : function(param, callback){
//        	var headerName = this.headerName;
        	var headerTitle = this.headerTitle;
        	http.post({
        		url  : this.ctol+"/getGridList",
        		data : param,
        		success : function(data){
        			if(data.header){
        				var headerAry = data.header;
        				// 前台处理 title属性
        				for(var i=0,len=headerAry.length; i<len; i++){
        					headerAry[i].title = headerTitle[i]; 
        				}
        				headerAry[0].asc = 'desc';
        			}
        			callback(data);
        		}
        	})
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