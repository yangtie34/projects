app.service("service",['httpService', function(http){
	return {
		ctol : "studentsQuality",
		
		querySelectType : function() {
			return http.post({
						url : this.ctol+"/querySelectType",
						data : {}
					});
		},
		queryStudents : function(year,sub,callback){
			http.post({
				url  : this.ctol+"/queryStudents",
				data : {
					year : year,
					sub : sub
				},
        		success : function(data){
        			callback(data);
        		}
			});
		},
		queryAllSub : function (year,callback){
			http.post({
				url : this.ctol+ "/queryAllSub",
			    data: {
			    	year : year
			    },
			    success : function(data){
			    	callback(data);
			    }
			});
		},
		queryStudentsSub : function(year,sub, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsSub",
        		data : {
        			year : year,
        			sub : sub
        				},
        		success : function(data){
        			callback(data);
        		}
        	});
        },queryStudentsScore : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsScore",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },queryScoreLineByPro : function(param ,callback){
        	http.post({
        		url : this.ctol+"/queryScoreLineByPro",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },queryStudentsAdjust : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsAdjust",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },queryStudentsEnroll : function(param, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsEnroll",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },queryStudentsNotReport : function(year, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsNotReport",
        		data : {
        			year : year
        				},
        		success : function(data){
        			callback(data);
        		}
        	});
        },queryStudentsNotReportByLocal : function(year,xzqh,updown, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsNotReportByLocal",
        		data : {
        			year : year,
        			xzqh : xzqh,
        			updown :updown
        				},
        		success : function(data){
			        var local = {};
        			local.from= data.list;
        			local.fromCfg = {
						mapType : data.maptype,
						type : "map",
						data : data.list,
						config : {
							dataRange : {
								show : true,
								x : '10%',
								y : '60%',
								max : data.max,
								itemWidth : 15,
								itemHeight : 10
							}
						}
					};
        			local.cc =data.cc;
        			local.maptype =data.maptype;
        			callback(local);
        		}
        	});
        },queryStudentsNotReportReason : function(year, callback){
        	http.post({
        		url  : this.ctol+"/queryStudentsNotReportReason",
        		data : {
        			year : year
        				},
        		success : function(data){
        			var reason = {
        					type :"pie",
        					data : data.rea,
        					name : '人',
        					config :{
        						calculable :true,
        						legend:{
        							show:true
        						},toolbox:{
        							show:true
        						},noDataText:'暂无未报到原因数据',
        						series:[{
        							radius:'60%',
        							center:['50%','54%']
        						}]
        					}
        			};
        			callback(reason);
        		}
        	});
        },
        getNotReportReason : function(param,callback){
        	http.post({
        		url  : this.ctol+"/getNotReportReason",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        queryWbdDetail : function(param,callback){
        	http.post({
        		url : this.ctol + "/queryWbdDetail",
        		data : param,
        		success : function(data){
        			callback(data);
        		}
        	});
        },
        stuListDown : function(param, callback){
        	http.fileDownload({
        		url  :this.ctol + "/down",
        		data : param
        	})
     }
	}
}])