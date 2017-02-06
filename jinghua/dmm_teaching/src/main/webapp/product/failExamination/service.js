app.service("service",['httpService', function(http){
    return {
    	
    	ctol : "failExamination",
    	
    	querySelectType : function(callback){
        	http.post({
        		url  : this.ctol+"/querySelectType",
	    		success : function(data){
	    			callback(data);
	    		}
        	});
        },
        //(不及格人数  不及格率 环比变化 平均不及格数)
        getGkInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getGkInfo",
        		data : {
        				"param":param,
        				"schoolYear":schoolYear,
        				"termCode":termCode,
        				"edu":edu
        				},
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        //学生类别 人数 不及格率 变化
        getGkflInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getGkflInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu
        		},
        		success : function(data){
        			for(var i=0;i<data.length;i++){
        				if(data[i].CL01=='20'){data[i].CL01='本科生';}
        				if(data[i].CL01=='30'){data[i].CL01='专科生';}
        			}
        			callback(data);
        		}
        	});
        },
        //各年级不及格分布(人数 人均不及格数)
        getNjGkInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getNjGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu
        		},
        		success : function(data){
        			var data1 = {field:'大一',name:'人数',value:0} ;
        			var data2 = {field:'大二',name:'人均不及格数',value:0} ;
        			var data3=[data1,data2];
        			for(var i=0;i<data.length;i++){
        				if(data[i].CL01!=null){
        				data1.field=data[i].CL01;
        				data1.name='人数';
        				data1.value=data[i].CL03;
        				data2.field=data[i].CL01;
        				data2.name='人均不及格数';
        				data2.value=data[i].CL04;
        				data3[i]=angular.copy(data1);
        				data3[i+data.length]=angular.copy(data2);
        				}else{
            				data1.field='未知';
            				data1.name='人数';
            				data1.value=data[i].CL03;
            				data2.field='未知';
            				data2.name='人均不及格数';
            				data2.value=data[i].CL04;
            				data3[i]=angular.copy(data1);
            				data3[i+data.length]=angular.copy(data2);	
        				}
        			}
        			var option = {
        				        isSort : false,
        				        yAxis : "人",
        				        data : data3,
        				        type :"bar",  //图表类型(bar,line,area,spline)
    				        	config : {
    				        		legend : {selected: {'人数' : false}},
    				        		 title : {
	 		     	    			        text: '各年级不及格分布',
	 		     	    			        show:false
	 		     	    			    },
    				        		tooltip : {
//    				        			formatter:'{b}<br>{a}:{c}人',
    				        	        formatter: function (params,ticket,callback) {
//    				        	            console.log(params);
    				        	        	var unit=['人','门'];
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
    				        	}
        				};
        			callback(option);
        		}
        	});
        },
        //男女生不及格分布(人数 人均不及格数)
        getXbGkInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getXbGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu
        		},
        		success : function(data){
        			var data1 = {field:'男',name:'人数',value:0} ;
        			var data2 = {field:'女',name:'人均不及格数',value:0} ;
        			var data3=[data1,data2];
        			for(var i=0;i<data.length;i++){
        				if(data[i].CL01!=null){
        				data1.field=data[i].CL01;
        				data1.name='人数';
        				data1.value=data[i].CL02;
        				data2.field=data[i].CL01;
        				data2.name='人均不及格数';
        				data2.value=data[i].CL03;
        				data3[i]=angular.copy(data1);
        				data3[i+data.length]=angular.copy(data2);
        				}else{
            				data1.field='未知';
            				data1.name='人数';
            				data1.value=data[i].CL03;
            				data2.field='未知';
            				data2.name='人均不及格数';
            				data2.value=data[i].CL04;
            				data3[i]=angular.copy(data1);
            				data3[i+data.length]=angular.copy(data2);	
        				}
        			}
        			var option = {
        					isSort : false,
        					yAxis : "人",
        					data : data3,
        					type :"bar",  //图表类型(bar,line,area,spline)
				        	config : {
				        		legend : {selected: {'人数' : false}},
				        		 title : {
		     	    			        text: '性别不及格分布',
		     	    			        show:false
		     	    			    },
			        			tooltip : {
			//			        			formatter:'{b}<br>{a}:{c}人',
			        				formatter: function (params,ticket,callback) {
//			        					console.log(params);
				        	        	var unit=['人','门'];
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
        						}
        			};
        			callback(option);
        		}
        	});
        },
        //不及格课程分布--公共课/专业课(人数 人均不及格数)
        getNatKcGkInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getNatKcGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu
        		},
        		success : function(data){
        			var data1 = {field:'',name:'',value:''} ;
        			var data2 = {field:'',name:'',value:''} ;
        			var data3=[{}];
        			for(var i=0;i<data.length;i++){
        				if(data[i].CL01!=null){
        				data1.field=data[i].CL01;
        				data1.name='人数';
        				data1.value=data[i].CL02;
        				data2.field=data[i].CL01;
        				data2.name='人均不及格数';
        				data2.value=data[i].CL03;
        				data3[i]=angular.copy(data1);
        				data3[i+data.length]=angular.copy(data2);
        				}else{
            				data1.field='专业课';
            				data1.name='人数';
            				data1.value=data[i].CL02;
            				data2.field='专业课';
            				data2.name='人均不及格数';
            				data2.value=data[i].CL03;
            				data3[i]=angular.copy(data1);
            				data3[i+data.length]=angular.copy(data2);	
        				}
        			}
        			var option = {
        					isSort : false,
        					yAxis : "人",
        					data : data3,
        					type :"bar",  //图表类型(bar,line,area,spline)
				        	config : {
				        		legend : {selected: {'人数' : false}},
				        		 title : {
		     	    			        text: '不及格课程分布',
		     	    			        show:false
		     	    			    },
			        			tooltip : {
			//			        			formatter:'{b}<br>{a}:{c}人',
			        				formatter: function (params,ticket,callback) {
//			        					console.log(params);
				        	        	var unit=['人','门'];
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
        						}
        			};
        			callback(option);
        		}
        	});
        },
        //不及格课程分布--必修课/选修课(人数 人均不及格数)
        getAttrKcGkInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getAttrKcGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu
        		},
        		success : function(data){
        			var data1 = {field:'',name:'',value:''} ;
        			var data2 = {field:'',name:'',value:''} ;
        			var data3=[{}];
        			for(var i=0;i<data.length;i++){
        				if(data[i].CL01!=null){
        					data1.field=data[i].CL01;
        					data1.name='人数';
        					data1.value=data[i].CL02;
        					data2.field=data[i].CL01;
        					data2.name='人均不及格数';
        					data2.value=data[i].CL03;
        					data3[i]=angular.copy(data1);
        					data3[i+data.length]=angular.copy(data2);
        				}else{
        					data1.field='选修';
        					data1.name='人数';
        					data1.value=data[i].CL02;
        					data2.field='选修';
        					data2.name='人均不及格数';
        					data2.value=data[i].CL03;
        					data3[i]=angular.copy(data1);
        					data3[i+data.length]=angular.copy(data2);	
        				}
        			}
        			var option = {
        					isSort : false,
        					yAxis : "人",
        					data : data3,
        					type :"bar",  //图表类型(bar,line,area,spline)
				        	config : {
				        		legend : {selected: {'人数' : false}},
				        		 title : {
		     	    			        text: '课程不及格分布',
		     	    			        show:false
		     	    			    },
			        			tooltip : {
			//			        			formatter:'{b}<br>{a}:{c}人',
			        				formatter: function (params,ticket,callback) {
//			        					console.log(params);
				        	        	var unit=['人','门'];
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
        						}
        			};
        			callback(option);
        		}
        	});
        },
        //各机构不及格分布(人数 人均不及格数)
        getJgGkInfo : function(param,schoolYear,termCode,edu, callback){
        	http.post({
        		url  : this.ctol+"/getJgGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu
        		},
        		success : function(data){
        			var data1 = {field:' ',name:'人数',value:0} ;
        			var data2 = {field:' ',name:'人均不及格数',value:0} ;
        			var data3=[data1,data2];
        			var val="人均不及格数";
        			for(var i=0;i<data.list.length;i++){
        				if(data.list[i].list!=null){
        					data1.field=data.list[i].name;
        					data1.name='人数';
        					data1.value=data.list[i].list[0].CL01;
        					data2.field=data.list[i].name;
        					data2.name='人均不及格数';
        					data2.value=data.list[i].list[0].CL02;
        					data3[i]=angular.copy(data1);
        					data3[i+data.list.length]=angular.copy(data2);
        				}else{
        					data1.field=data.list[i].name;
        					data1.name='人数';
        					data1.value=0;
        					data2.field=data.list[i].name;
        					data2.name='人均不及格数';
        					data2.value=0;
        					data3[i]=angular.copy(data1);
        					data3[i+data.list.length]=angular.copy(data2);
        				}
        			}
        			var option = {
        					isSort : false,
        					yAxis : "人",
        					data : data3,
        					type :"bar",  //图表类型(bar,line,area,spline)
				        	config : {
				        		legend : {selected: {'人数' : false}},
				        		 title : {
		     	    			        text: '各机构不及格分布',
		     	    			        show:false
		     	    			    },
			        			tooltip : {
			//			        			formatter:'{b}<br>{a}:{c}人',
			        				formatter: function (params,ticket,callback) {
//			        					console.log(params);
				        	        	var unit=['人','门'];
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
        						}
        			};
        			callback(option);
        		}
        	});
        },
        //不及格top
        getTopGkInfo : function(param,schoolYear,termCode,edu,lx,gkSort,turnPage, callback){
        	http.post({
        		url  : this.ctol+"/getTopGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu,
        			"lx" :lx,
        			"gkSort":gkSort,
        			"turnPage":turnPage
        		},
        		success : function(data){
        			callback(data);
        		}
        	});
        },  
        //学生不及格top
        getStuTopGkInfo : function(param,schoolYear,termCode,edu,gkStuSort,turnStuPage, callback){
        	http.post({
        		url  : this.ctol+"/getStuTopGkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu,
        			"gkStuSort":gkStuSort,
        			"turnStuPage":turnStuPage
        		},
        		success : function(data){
        			callback(data);
        		}
        	});
        },  
        //人均补考top
        getTopbkInfo : function(param,schoolYear,termCode,edu,lx,bkTopSort,bkturnPage, callback){
        	http.post({
        		url  : this.ctol+"/getTopbkInfo",
        		data : {
        			"param":param,
        			"schoolYear":schoolYear,
        			"termCode":termCode,
        			"edu":edu,
        			"lx" :lx,
        			"bkTopSort":bkTopSort,
        			"bkturnPage":bkturnPage
        		},
        		success : function(data){
        			for(var i=0;i<data.length;i++){
        				if(data[i].CL02==null){
        					data[i].CL02=0;
        				}
        			}
        			callback(data);
        		}
        	});
        },  	    
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Teaching_failExamination" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }
    }
}]);