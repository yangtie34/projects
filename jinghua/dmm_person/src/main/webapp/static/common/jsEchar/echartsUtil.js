
var echarColor= [
                 '#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80',
                 '#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
                 '#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
                 '#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
             ];
	/*
	 * 二维饼状图数据解析
	 */
	var echardata2d =function(data) {
		var legends = [];
		var datas = [];
		for (var i = 0; i < data.length; i++) {
			legends.push(data[i].FIELD);
			datas.push({
				value : data[i].VALUE,
				name : data[i].FIELD,
				 itemStyle:{
	                  normal:{color:echarColor[i%5]}
	              }
			});
		}

		var ret = [];
		ret[0] = legends;
		ret[1] = datas;
		return ret;
	};
	/*
	 * 内外多级饼状图
	 */
	var echardatadj = function(data) {
		var legends = [];
		var datas1 = [];
		var datas2 = [];
		for (var i = 0; i < data.length; i++) {
			legends.push(data[i].FIELD);
			if (data[i].cc == '内') {
				datas1.push({
					value : data[i].VALUE,
					name : data[i].FIELD
				});
			} else {
				datas2.push({
					value : data[i].VALUE,
					name : data[i].FIELD
				});
			}

		}

		var ret = [];
		ret[0] = legends;
		ret[1] = datas1;
		ret[2] = datas2;
		return ret;
	};
	/*
	 * 饼状环形图
	 */
	var echarbzhx = function(dat, title) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var all=0;
		for(var i=0;i<datas.length;i++){
			all+=Number(datas[i].value);
		}
		var re = /([0-9]+\.[0-9]{2})[0-9]*/;  
		all = (all+'').replace(re,"$1");  
		var option={   
				tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
					    series: [
					        {
					            name:title,
					            type:'pie',
					            radius: ['45%', '70%'],
					            avoidLabelOverlap: false,
					            itemStyle : { normal : {
				                    label : {
					                      show : true,
					                      formatter:''+all,
					                        position : 'center',
					                        textStyle : {
					                            fontSize : '20',
					                            fontWeight : 'bold'
					                        }
					                    },
					                    labelLine:{show:false}
						            },
					        emphasis : {
				                    label : {
				                    	/* formatter:function(params, ticket, callback){
				                    		 var cla=params.name=='女'?'jiaozhi-girl':'jiaozhi-boy';
				                    		 var res="Function formatter :<span class='"+cla+"'></span>"+params.value+"人";
				                    		 callback(ticket, res);return res;
				                    	 },*/
				                      show : true,
				                        position : 'outer',
				                        textStyle : {
				                            fontSize : '20',
				                        }
				                    },
				                    labelLine:{show:true}
					            },
					            
				           },
					            labelLine: {
					                normal: {
					                    show: false
					                }
					            },
					            data:datas
					        }
					    ]
					};
		return option;
	};
	/*
	 * 标准环形图
	 */
	var echarbzhxt = function(dat, title) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
				backgroundColor:'#FFFFFF',
			title : title.text,
			//			title : {
			//				text : title,
			//				//subtext : '纯属虚构',
			//				x : 'center'
			//			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
			
	             y:'20',
//				orient : 'vertical',
//				x : 'left',
				data : legends
			}, 
			noDataLoadingOption : {
				text : title.text+'暂无数据',
			    effectOption : null,
			    effect : 'bubble',
			    effectOption : {
			    	effect : {n:'0'}
			    }},
			toolbox : {
				y:'40',
				show : true,
				feature : {					
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			series : [ {
				name : '数量',
				type : 'pie',
				radius : '55%',
	            radius : ['50%', '70%'],
	            itemStyle : {
	                normal : {
	                    label : {
	                        show : false
	                    },
	                    labelLine : {
	                        show : false
	                    }
	                },
	                emphasis : {
	                    label : {
	                        show : true,
	                        position : 'center',
	                        textStyle : {
	                            fontSize : '30',
	                            fontWeight : 'bold'
	                        }
	                    }
	                }
	            },
				data : datas
			} ]
		};
		return option;
	};
	/*
	 * 饼状图
	 */
	var echarbzt = function(dat, title) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
				type:'pie',
			title : title,
			//			title : {
			//				text : title,
			//				//subtext : '纯属虚构',
			//				x : 'center'
			//			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			/*grid:{  
			    x:30,
				y:30,
			    x2:30,
			    y2:30
			  },*/
			legend : {
			
	             y:'40',
//				orient : 'vertical',
//				x : 'left',
				data : legends
			}, 
			noDataLoadingOption : {
				text : title.text+'暂无数据',
			    effectOption : null,
			    effect : 'bubble',
			    effectOption : {
			    	effect : {n:'0'}
			    }},
			toolbox : {
				y:'40',
				show : true,
				feature : {					
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			series : [ {
				name : '数量',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : datas
			} ]
		};
		return option;
	};

	/*
	 * 三维柱状图解析数据
	 */
	var echardata3d = function(datas, typ) {
		var yxDatas = [];
		var yxMap = {}; //存放院系的数据
		var legentMap = {}; //中间数据
		var legent = []; //图例
		var series = [];
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			var yx = data.FIELD;
			if (yxMap[yx] == null) {
				yxMap[yx] = {};
				yxDatas.push(yx);
			}
			yxMap[yx][data.NAME] = data;
			if (legentMap[data.NAME] == null) {
				legentMap[data.NAME] = {
					name : data.NAME,
					type : typ,
					data : []
				};
				series.push(legentMap[data.NAME]);
				legent.push(data.NAME);
			}

		}
		for (var i = 0; i < legent.length; i++) {
			var leg = legent[i];
			for ( var yx in yxMap) {
				var yxData = yxMap[yx];
				if (yxData[leg] == null) {
					legentMap[leg].data.push(0);
				} else {
					legentMap[leg].data.push(yxData[leg].VALUE);
				}
			}
		}
		if(series.length==0){
			series.push({
				name : '0',
				type : typ,
				data : []
			});
		}
		var ret = [];
		ret[0] = legent;
		ret[1] = yxDatas;
		ret[2] = series;
		return ret;
	};
	/*
	 * 三维雷达图解析数据
	 */
	var echardataldt = function(datas, typ,title) {
		
		var maxvalue=0;
		for(var i=0;i<datas.length;i++){
			if(Number(datas[i].VALUE)>maxvalue){
				maxvalue=Number(datas[i].VALUE);
			}
		}
		var yxDatas = [];
		var yxMap = {}; //存放院系的数据
		var legentMap = {}; //中间数据
		var legent = []; //图例
		var series = [];
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			var yx = data.FIELD;
			if (yxMap[yx] == null) {
				yxMap[yx] = {};
				yxDatas.push({
					text: yx, max: maxvalue
				});
				}
			
			yxMap[yx][data.NAME] = data;
			if (legentMap[data.NAME] == null) {
				legentMap[data.NAME] = {
					name : data.NAME,
					value : []
				};
				series.push(legentMap[data.NAME]);
				legent.push(data.NAME);
			}
		}
		
		for (var i = 0; i < legent.length; i++) {
			var leg = legent[i];
			for ( var yx in yxMap) {
				var yxData = yxMap[yx];
				if (yxData[leg] == null) {
					legentMap[leg].value.push(0);
				} else {
					legentMap[leg].value.push(Number(yxData[leg].VALUE));
				}
			}
		}
		var seriess=[];
		seriess.push({
			name:title.text,
			type:typ,
			data:series
		});
		if(seriess.length==0){
			seriess.push({
				name : title.text,
				type : typ,
				data : []
			});
		}
		var ret = [];
		ret[0] = legent;
		ret[1] = yxDatas;
		ret[2] = seriess;
		return ret;
	};
	/*
	 * 内外多级饼状图
	 */
	var echardjbzt = function(dat, title) {
		var datas = this.datadj(dat);
		var legends = datas[0];
		var data1 = datas[1];
		var data2 = datas[2];
		option = {
			title : title,
			backgroundColor:'#FFFFFF',
			tooltip : {
				trigger : 'item',
				 formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
//			grid:{
//				y:'120px'
//			},
			noDataLoadingOption : {
				text : title.text+'暂无数据',
			    effect : 'bubble',
			    effectOption : {
			    	effect : {n:'0'}
			    }},
			legend : {
	             y:'center',
				orient : 'vertical',
				x : 'left',
				data : legends
			},
			calculable : true,
			series : [ {
				type : 'pie',
				selectedMode : 'single',
				radius : [ 0, 70 ],
				x : '20%',
				width : '40%',
				funnelAlign : 'right',
				max : 1548,
				itemStyle : {
					normal : {
						label : {
							position : 'inner'
						},
						labelLine : {
							show : false
						}
					}
				},
				data : data1
			}, {
				name : '访问来源',
				type : 'pie',
				radius : [ 100, 140 ],
				x : '60%',
				width : '35%',
				funnelAlign : 'left',
				max : 1048,
				data : data2
			} ]
		};
		return option;
	};
	/*
	 * 标准雷达图
	 */
	var echarldt=function(dat, typ, titl){
		var datas = this.echardataldt(dat, typ,titl);
		var legents = datas[0];
		var xAxiss = datas[1];
		var seriess = datas[2];
		option = {
		    title : titl,
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'right',
		        y : 'bottom',
		        data:legents
		    },
		    toolbox: {
		        show : true,
		        feature : {
//		            mark : {show: true},
//		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		
		    polar : [
		       {
		           indicator : xAxiss
		        }
		    ],
		    calculable : true,
		    series : seriess
		};
		return option;                   
	};
	/*
	 * 三维图
	 */
	var echarswt = function(dat, typ, titl) {
		var datas = this.echardata3d(dat, typ);
		var legents = datas[0];
		var xAxiss = datas[1];
		var seriess = datas[2];
		
		var option = {
			backgroundColor:'#FFFFFF',
			title : titl,
			tooltip : {
				trigger : 'axis'
			},
			grid:{  
			    x:30,
				y:30,
			    x2:30,
			    y2:30
			  },
			legend : {
//				orient : 'vertical',
//	             x:'right',
             y:40,
				data : legents
			},
			toolbox : {
				y:'40',
				show : true,
				feature : {
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : xAxiss
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : seriess,
			noDataLoadingOption : {
			  text : titl.text+'暂无数据',
			   // effectOption : null,
			    effect : 'bubble',
			    effectOption : {
			    	effect : {n:'0'}
			    }},
			/*dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 100
			}*/
		};
		return option;

	};

