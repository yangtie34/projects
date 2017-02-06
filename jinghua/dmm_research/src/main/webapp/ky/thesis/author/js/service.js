app.service("service", [ 'httpService', function(http) {
	return {
		//查询论文作者总人数
		queryTotalNums : function(condition) {
			return http.post({
				url : "thesis/author/nums",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//按照性别查询教职工人数
		queryAuthorNumsByGender : function(condition) {
			return http.post({
				url : "thesis/author/nums/gender",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//按照类别
		queryAuthorNumsByType : function(condition) {
			return http.post({
				url : "thesis/author/nums/type",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//高产作者
		queryAuthorNumsByOutput : function(condition) {
			return http.post({
				url : "thesis/author/nums/output",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//学历组成
		queryAuthorNumsByEducation : function(condition) {
			return http.post({
				url : "thesis/author/edu",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//年龄组成
		queryAuthorNumsByAge : function(condition) {
			return http.post({
				url : "thesis/author/age",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//专业技术职务级别
		queryAuthorNumsByZyjszwJb : function(condition) {
			return http.post({
				url : "thesis/author/zyjszwjb",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//专业技术职务（职称）
		queryAuthorNumsByZyjszw : function(condition) {
			return http.post({
				url : "thesis/author/zyjszw",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},

		//人员类别
		queryAuthorNumsByRylb : function(condition) {
			return http.post({
				url : "thesis/author/rylb",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},
		//教职工来源组成
		queryAuthorNumsByTeaSource : function(condition) {
			return http.post({
				url : "thesis/author/source",
				data : {
					xkmlid : condition.subject.id,
					startYear : condition.definedYear.start,
					endYear : condition.definedYear.end,
					zzjgid : condition.dept.id
				}
			});
		},
		generatePieConfig : function(title,data){
			var legendData = [];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name)
			}
			return option = {
				title : {
			        text: title, 
			        x:'left'
			    }, 
			    tooltip : {
			        trigger: 'item',
			        formatter: "{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'right',
			        y: 'center',
			        data:legendData
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    series : [
			        {
			            name:title,
			            type:'pie',
			            startAngle: 180,
			            radius : '55%',
			            center: ['50%', '50%'],
			            data: data,
    		            label : {
    		                normal : {
    		                    formatter: '{b}({d}%)'
    		                }
    		            }
			        }
			    ]
			};
		},
		generateDonutConfig : function(title,data){
			var legendData = [];
			for (var i = 0; i < data.length; i++) {
				var it = data[i];
				legendData.push(it.name)
			}
			return option = {
				title : {
					text : title,
					left : 'left'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					left : 'right',
					top : 'center',
					data : legendData
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : true,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				series : [ {
					name : title,
					type:'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false, 
		            label: {
		                normal: {
		                	formatter: '{b}({d}%)'
		                },
		                emphasis: {
		                    show: true,
		                    textStyle: {
		                        fontSize: '30',
		                        fontWeight: 'bold'
		                    }
		                }
		            },
					data : data
				} ]
			};
		}
	}
} ]);
