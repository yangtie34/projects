/**
 * Page页面,所有页面类都需要从此类继承
 */
NS.define('Template.echartsUtil', {
	extend : 'Template.Page',
	/*
	 * 二维饼状图数据解析
	 */
	data2d : function(data) {
		var legends = [];
		var datas = [];
		for (var i = 0; i < data.length; i++) {
			legends.push(data[i].field);
			datas.push({
				value : data[i].counts,
				name : data[i].field
			});
		}

		var ret = [];
		ret[0] = legends;
		ret[1] = datas;
		return ret;
	},
	/*
	 * 内外多级饼状图
	 */
	datadj : function(data) {
		var legends = [];
		var datas1 = [];
		var datas2 = [];
		for (var i = 0; i < data.length; i++) {
			legends.push(data[i].field);
			if (data[i].cc == '内') {
				datas1.push({
					value : data[i].counts,
					name : data[i].field
				});
			} else {
				datas2.push({
					value : data[i].counts,
					name : data[i].field
				});
			}

		}

		var ret = [];
		ret[0] = legends;
		ret[1] = datas1;
		ret[2] = datas2;
		return ret;
	},
	/*
	 * 标准环形图
	 */
	bzhxt : function(dat, title) {
		var data = this.data2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
				backgroundColor:'#FFFFFF',
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
	},
	/*
	 * 饼状图
	 */
	bzt : function(dat, title) {
		var data = this.data2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
				backgroundColor:'#FFFFFF',
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
	},

	/*
	 * 三维柱状图解析数据
	 */
	data3d : function(datas, typ) {
		var yxDatas = [];
		var yxMap = {}; //存放院系的数据
		var legentMap = {}; //中间数据
		var legent = []; //图例
		var series = [];
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			var yx = data.field;
			if (yxMap[yx] == null) {
				yxMap[yx] = {};
				yxDatas.push(yx);
			}
			yxMap[yx][data.name] = data;
			if (legentMap[data.name] == null) {
				legentMap[data.name] = {
					name : data.name,
					type : typ,
					data : []
				};
				series.push(legentMap[data.name]);
				legent.push(data.name);
			}

		}
		for (var i = 0; i < legent.length; i++) {
			var leg = legent[i];
			for ( var yx in yxMap) {
				var yxData = yxMap[yx];
				if (yxData[leg] == null) {
					legentMap[leg].data.push(0);
				} else {
					legentMap[leg].data.push(yxData[leg].counts);
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
	},
	/*
	 * 三维雷达图解析数据
	 */
	dataldt : function(datas, typ,title) {
		
		var maxvalue=0;
		for(var i=0;i<datas.length;i++){
			if(Number(datas[i].counts)>maxvalue){
				maxvalue=Number(datas[i].counts);
			}
		}
		var yxDatas = [];
		var yxMap = {}; //存放院系的数据
		var legentMap = {}; //中间数据
		var legent = []; //图例
		var series = [];
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			var yx = data.field;
			if (yxMap[yx] == null) {
				yxMap[yx] = {};
				yxDatas.push({
					text: yx, max: maxvalue
				});
				}
			
			yxMap[yx][data.name] = data;
			if (legentMap[data.name] == null) {
				legentMap[data.name] = {
					name : data.name,
					value : []
				};
				series.push(legentMap[data.name]);
				legent.push(data.name);
			}
		}
		
		for (var i = 0; i < legent.length; i++) {
			var leg = legent[i];
			for ( var yx in yxMap) {
				var yxData = yxMap[yx];
				if (yxData[leg] == null) {
					legentMap[leg].value.push(0);
				} else {
					legentMap[leg].value.push(Number(yxData[leg].counts));
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
	},
	/*
	 * 内外多级饼状图
	 */
	djbzt : function(dat, title) {
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
	},
	/*
	 * 标准雷达图
	 */
	ldt:function(dat, typ, titl){
		var datas = this.dataldt(dat, typ,titl);
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
	},
	/*
	 * 三维图
	 */
	swt : function(dat, typ, titl) {
		var datas = this.data3d(dat, typ);
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
			    y:100
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
			dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 100
			}
		};
		return option;

	},
	/*
	 * 弹出窗口
	 */
	newWindow : function(id, func) {
		var window = this.window = new NS.window.Window({
			height : screen.height * 3 / 5,
			width : screen.width * 2 / 4,
			border : false,
			modal : true,
			autoScroll : true,
			layout : 'fit',
			bodyStyle : {
				backgroundColor : 'white'
			},
			html : '<div id="id11"></div><div id="' + id
					+ '" style="width:100%;height:400px;margin:3px;"></div>',
			buttons : [ {
				xtype : 'button',
				name : 'cancel',
				text : '关闭',
				handler : function() {
					this.up("window").close();
				}
			} ]
		});
		window.on('afterrender', function() {
			func;
		}, this);
		window.show();
	},

	//fields"学号,姓名,性别,年龄,院系,专业,入学年级"

	getColumn : function(gridFields, fields) {
		var textarrays = fields.split(",");
		var columns = [];
		for (var i = 0; i < gridFields.length; i++) {
			var basic = {
				xtype : 'column',
				name : gridFields[i],
				text : textarrays[i],
				align : 'center'
			};

			columns.push(basic);
		}
		return columns;
	},

	/*
	 * 列表对象
	 */
	initXqGrid : function(data, fields, columnConfig, queryParams, serviceKey,
			multiSelec, paging, pagesize) {
		var lineNumber = Boolean(multiSelec) == true ? false : true;
		var grid = new NS.grid.SimpleGrid({
			columnData : data,
			data : data,
			autoScroll : true,
			pageSize : pagesize || 20,
			proxy : this.model,
			serviceKey : {
				key : serviceKey,
				params : queryParams
			},
			multiSelect : multiSelec || false,
			lineNumber : lineNumber,
			fields : fields,
			columnConfig : columnConfig,
			border : false,
			checked : multiSelec || false,
			paging : paging || false
		});
		return grid;
	},
	/*
	 * 创建下拉框事件
	 */
	comboBox : function(dat, mychar, servicekey, title) {
		var me = this;
		var data = [];
		for (var i = 0; i < dat.length; i++) {
			data.push({
				id : i,
				mc : '按' + dat[i] + '统计'
			});
		}
		var combobox = me.createComboBox(data, '学历统计指标');
		combobox.on('change', function(compo, newValue, oldValue) {
			this.params.fl = dat[newValue];
			me.xzechars(mychar, servicekey, title);
		}, this);
		combobox.render('id1');//把这个下拉框组件渲染到div当中
	},
	/*
	 * 返回一个下拉框
	 */
	createComboBox : function(dat, name) {
		var states = Ext.create('Ext.data.Store', {
			fields : [ 'id', 'mc' ],
			data : dat
		});
		var combobox = this.combobox = new Ext.form.ComboBox({
			width : 250,
			id:'lx',
			store : states,
			fieldLabel : name,
			queryMode : 'local',
			displayField : 'mc',
			margin : '0 0 0 0',
			columnWidth : 0.5,
			valueField : 'id'
		});
		combobox.setValue(0);
		return combobox;
	},

	xzechars : function(mychar, servicekey, title) {
		var me = this;
		me.callService({
			key : servicekey,
			params : me.params
		}, function(data) {
			var optionzlxz = me.swt(data[servicekey], 'line', title);
			mychar.setOption(optionzlxz);
		}, this);
	}

});