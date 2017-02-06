app.service("service",['httpService', 'setting', function(http, setting){
    return {
    	
    	ctol : 'worker',
    	pmsCtol : 'pmsWorker',
    	
    	getWorker : function(param, callback){
        	http.post({
        		url  : this.ctol+"/getWorker",
	    		data : { param : param },
	    		success : function(data){
	    			// 工作者分类
	    			var wkDateAry= data.worker, // 基础数据数组
	    				wkCfgAry = [], // 多个pie配置数组
	    				count    = wkDateAry.length>0 ? Number(wkDateAry[0].value) : 0, // 总数
	    				radius   = [45,65],
	    				legendAry = [],
	    				seriesAry = [],
	    				length    = wkDateAry.length,
	    				y = 60, x_one = 100/(length+1), x0 = x_one-x_one/(length-1), x_ = x_one+x_one/(length);
	    			for(var i=0,len=wkDateAry.length; i<len; i++){
	    				var oneWkDa    = wkDateAry[i],
	    					oneWkDaAry = [];
	    				oneWkDa.value  = Number(oneWkDa.value); // 每个类型数量
	    				oneWkDa.itemStyle = setting.labelTop;
	    				oneWkDaAry.push({name:'other', value:count-oneWkDa.value, itemStyle:setting.labelBottom});
	    				oneWkDaAry.push(oneWkDa);
	    				legendAry.push(oneWkDa.name);
	    				seriesAry.push({
	    					type   : 'pie',
	    					center : [x0+x_*i+'%', y+'%'],
	    		            radius : radius,
	    		            data   : oneWkDaAry,
	    		            itemStyle : setting.labelFromatterFn(count)
	    				})
	    			}
	    			var option = {
	    				legend : {
	    					x : x0-6+'%',
	    					y : '5%',
	    					data : legendAry
	    				},
	        		    toolbox: {
					        show : true,
					        feature : {
					        	dataView : {show: true, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
						    }
					    },
	    				series : seriesAry,
	    				config : { 
	    					noDataText : '暂无学生工作者数据',
	    					title : {
	    						text : '学生工作者分布',
	    						show : false
	    					}
	    				}
	    			};
	    			callback(data.scale, option);
	    		}
        	});
        },
        
        getWorkerDistribute : function(param, callback){
	    	http.post({
	    		url  : this.ctol+"/getWorkerDistribute",
	    		data : { param : param },
	    		success : function(data){
	    			if(data.status){
	    				// 职称
	    				var wkZcCfg = {
	    					type : "pie",
	    					data : data.zc,
	    					name : '人',
	    					config : { 
	    						noDataText : '暂无学生工作者（职称）分布数据',
		    					title : {
		    						text : '学生工作者（职称）分布',
		    						show : false
		    					}
	    					}
	    				};
	    				// 学位
	    				var wkDegreeCfg = {
	    					type : "pie",
	    					data : data.degree,
	    					name : '人',
	    					config : {
	    						series : [{
	    							roseType : 'area',
	    							radius: [50, 112]
	    						}],
	    						noDataText : '暂无学生工作者（学位）分布数据',
		    					title : {
		    						text : '学生工作者（学位）分布',
		    						show : false
		    					}
	    					}
	    				};
	    				// 年龄分布
	    				for(var i=0,len=data.age.length; i<len; i++){
	    					var d = data.age[i];
	    					d.field = d.name; // 将name改成field
	    					delete d.name;
	    				}
	    				var wkAgeCfg = {
	    					type : "bar",
	    					data : data.age,
	    					name : '人',
	    					config : { 
	    						noDataText : '暂无学生工作者（年龄）分布数据',
		    					title : {
		    						text : '学生工作者（年龄）分布',
		    						show : false
		    					}
							}
	    				};
	    				// 性别
	    				var wkSexCfg = {
	    					type : 'pie',
	    					data : data.sex,
	    					name : '人',
	    					config : {
	    						calculable : true,
	    						series : [{
	    							center : ['50%','55%'],
	    						}],
	    						noDataText : '暂无学生工作者（性别）分布数据',
		    					title : {
		    						text : '学生工作者（性别）分布',
		    						show : false
		    					}
	    					}
	    				}
	    				callback(wkZcCfg, wkDegreeCfg, wkAgeCfg, wkSexCfg);
	    			}
	    		}
	    	});
	    },
        
	    getInstructorsDistribute : function(param, callback){
	    	http.post({
	    		url  : this.ctol+"/getInstructorsDistribute",
	    		data : { param : param },
	    		success : function(data){
	    			if(data.status){
	    				// 职称
	    				var wkZcCfg = {
	    					type : "pie",
	    					data : data.zc,
	    					name : '人',
	    					config : { 
	    						noDataText : '暂无专职辅导员（职称）分布数据',
		    					title : {
		    						text : '专职辅导员（职称）分布',
		    						show : false
		    					}
							}
	    				};
	    				// 学位
	    				var wkDegreeCfg = {
	    					type : "pie",
	    					data : data.degree,
	    					name : '人',
	    					config : {
	    						series : [{
	    							roseType : 'area',
	    							radius: [50, 112]
	    						}],
	    						noDataText : '暂无专职辅导员（学位）分布数据',
		    					title : {
		    						text : '专职辅导员（学位）分布',
		    						show : false
		    					}
	    					}
	    				};
	    				// 年龄分布
	    				for(var i=0,len=data.age.length; i<len; i++){
	    					var d = data.age[i];
	    					d.field = d.name;
	    					delete d.name;
	    				}
	    				var wkAgeCfg = {
	    					type : "bar",
	    					data : data.age,
	    					name : '人',
	    					config : { 
	    						noDataText : '暂无专职辅导员（年龄）分布数据',
		    					title : {
		    						text : '专职辅导员（年龄）分布',
		    						show : false
		    					}
							}
	    				};
	    				// 性别
	    				var wkSexCfg = {
	    					type : 'pie',
	    					data : data.sex,
	    					name : '人',
	    					config : {
	    						calculable : true,
	    						series : [{
	    							center : ['50%','55%'],
	    						}],
	    						noDataText : '暂无专职辅导员（性别）分布数据',
		    					title : {
		    						text : '专职辅导员（性别）分布',
		    						show : false
		    					}
	    					}
	    				}
	    				var code = data.code;
	    				callback(wkZcCfg, wkDegreeCfg, wkAgeCfg, wkSexCfg,code);
	    			}
	    		}
	    	});
	    },
	    
	    getOrganizationInstructorsStuRatio : function(param, schoolYear, callback){
	    	http.post({
	    		url  : this.ctol+"/getOrganizationInstructorsStuRatio",
        		data : { param : param, schoolYear : schoolYear},
        		success : function(data){
        			var val_0 = data[0].value,
	        			val_1 = data[1].value,
	        			deptMc= data[2].value,
	        			data_ary = Ice.changeName2Field(data.slice(3)),
	        			length   = data_ary.length;
	        		var info = {
	        			jyb_data  : val_0, // 教育部标准
	        			this_data : val_1, // 当前数据
	        			db_count  : 0, // 达标
	        			bdb_count : 0 // 不达标
	        		}
	        		// 计算是否达标
	        		for(var i=0; i<length; i++){
	        			var val = data_ary[i].value;
	        			if(val > val_0 || val==0) info.bdb_count++;
	        			else info.db_count++;
	        		}
	        		var option = {
	        			type : "bar",
	        			data : data_ary,
	        			name : '人',
	        			config : {
	        				xAxis  : [{ axisLabel : { rotate : -15 } }],
	        				series : [{
	        					markLine : {
	        						data : [
	        						    [{name: '教育部标准', value: val_0, xAxis: -1, yAxis: val_0},
	        		        			 {xAxis: length, yAxis: val_0}],
	        		        			[{name: '当前数据', value: val_1, xAxis: -1, yAxis: val_1},
	        			        		 {xAxis: length, yAxis: val_1}]
	        						]
	        					}
	        				}],
	    					title : {
	    						text : '各'+deptMc+'每个专职辅导员管理学生数',
	    						show : false
	    					}
	        			}
	        		}
	        		callback(info, option, deptMc);
        		}
	    	});
	    },
	    
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_worker" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    },
	    getTeaDetail : function(param, callback, error){
	    	return http.post({
				url : this.pmsCtol+"/getTeaDetail",
				data : param,
				success : function(data) {
					var page = {};
					page.total = data.total;
					page.items = data.rows;
					page.pagecount = data.pagecount;
					callback(page);
				},
				error : error
			});
	    },
	    down : function(param, callback){
        	http.fileDownload({
        		url  : this.pmsCtol+"/down",
        		data : param,
        		success : function(){
        		}
        	})
        },
    }
}]);