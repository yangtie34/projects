/**
 * 科研成果_yangtz User: yangtz Date: 15-7-31 Time: 上午9:54
 * 
 */
NS
		.define(
				'Pages.sc.keyan.Kycg_yangtz',
				{
					extend : 'Template.echartsUtil',
					/*
					 * tplRequires : [], cssRequires : [], requires:[],
					 * params:{},
					 */
					entityName : '',
					modelConfig : {
						serviceConfig : {
							queryJxzzjgTree : 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构

							cggk1 : 'Kycg_yangtzService?cggk1',// 科研成果（获奖成果 著作鉴定成果 专利）分布统计-按成果类型
							cggk2 : 'Kycg_yangtzService?cggk2',// 科研成果（获奖成果 著作鉴定成果 专利）分布统计-按学院
							cgqs : 'Kycg_yangtzService?cgqs',// 成果趋势

							lbxz : 'Kycg_yangtzService?lbxz',// 类别下钻
							xqxz : 'Kycg_yangtzService?xqxz',// 详情列表
							qsxz : 'Kycg_yangtzService?qsxz',// 趋势下钻
						}
					},
					tplRequires : [],
					cssRequires : [ 'app/pages/sc/ky/lw/lwcss/lw.css' ],
					mixins : [ 'Pages.sc.Scgj' ],
					requires : [],
					params : {},
					echa:null,		myChardiv11:null,
					myChardiv12:null,
					init : function() {
						var me = this;
						var pageTitle = new Exp.component.PageTitle({
							data : {
								pageName : '科研成果（获奖成果 著作 鉴定成果 专利）',
								pageHelpInfo : '根据时间段以及部门分析科研成果的组成情况。'
							}
						});
						// 时间选项组件
						var simpleDate = this.simpleDate = new Exp.component.SimpleYear(
								{
									start : 1990,
									defaultDate:'bz',
									margin : '0 0 0 10'
								});

						newWidowqushi = function() {
							$("#id1").hide(); 
							$("#fanhui").hide(); 
							$("#div13div").toggle();
							me.window1();
							
						};
						newWidow11 = function() {
							me.newWindow('window11', me.xq11());
						};
						var containerDate = new Ext.container.Container(
								{
									cls : 'student-sta-titlediv',
									layout : {
										type : 'hbox',
										align : 'left'
									},
									height : 30,
									margin : '5 0 5 0',
									items : [
											simpleDate,
											new Ext.Component(
													{
														html : '<span style="margin-left: 20px;height: 26px;line-height: 26px;color: #777;">☞选择开始年份和结束年份，统计该年份内的人数。</span>'
													}) ]
								});
						var navigation = this.navigation = new Exp.component.Navigation();

						var html =
//							"<div class='vs-infor'>" 
							"<div style='height:35px;' class='list-line'>" +
						"<div class='list-tab'>" +
						"<span id='kx2' val='1' class='vavavavavav list-selected'>自然科学类</span>" +
						"<span id='kx1' val='0' class='vavavavavav list-default'>社会科学类</span>" +
						"</div>" +
						"</div>"
//+"<div class='vs-tab' id='vs-tab'><span id='kx1' class='vs-default vs-selected' val='0' title='哲学社会科学'>人文与社会科学</span>"
//  +"<span id='kx2' class='vs-default  vs-selected' val='0' title='自然科学'>自然科学</span></div>"
//+"</div>"
//+ "<hr style='margin-top:35px;' color='#5299eb'>"
+ "<div id='html' class='x-column-inner' style='background:rgb(247, 247, 247);padding:5px;width:auto;height:100%;'>"
+ "<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>科研概况</span><hr style='margin-top: 5px;' color='#5299eb'></div>"
							+ "<div id='div11' style='width:100%;height:500px;margin:5px;'><div class='loading-indicator'>正在加载....</div></div>"				
							
							+ "<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>科研成果数量</span><hr style='margin-top: 5px;' color='#5299eb'></div>"
							+"<div id='div12' style='width:100%;height:500px;margin:5px;'><div class='loading-indicator'>正在加载....</div></div>"
								+ "<hr style='margin-top: 5px;' color='#5299eb'>"
								+ "<a href='#' onclick='newWidowqushi()'><span style='float:right;height: 26px;line-height: 26px;color: blue;'>科研成果（获奖成果 著作 鉴定成果 专利）趋势统计</span></a>"
								+"<br/>"
								+"<div id='div13div'>"
								+ "<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>科研成果趋势统计</span><hr style='margin-top: 5px;' color='#5299eb'></div>"
								+ "<br/><div id='id1'></div>" +							
										"<div id='div13' style='width:100%;height:500px;'></div>" +
										"<div id='fanhui' style='float:right; position:relative; right:10px;bottom:50px;'><a href='#' ><span style='height: 26px;line-height: 26px;color: blue;'>返回</span></a></div>"
								+ "</div>"
								+"</div>";

						var page = this.component = new NS.Component({
							border : true,
							baseCls : '',
							autoScroll : true,
							html : html,
							autoShow : true
						});
						// window.onresize =
						// document.getElementById('html').resize;
						this.initParams();
						var container = new NS.container.Container({
							padding : 20,
							autoScroll : true,
							items : [ pageTitle, navigation, containerDate,
									page ]

						});
						this.setPageComponent(container);
						// 刷新导航栏
						this
								.callService(
										'queryJxzzjgTree',
										function(data) {
											this.navigation
													.refreshTpl(data.queryJxzzjgTree);
											var i = 0;
											for ( var key in data.queryJxzzjgTree) {
												if (i == 0) {
													var nodeId = data.queryJxzzjgTree[key].id;
													this.navigation
															.setValue(nodeId);
													this.params.zzjgId = nodeId;
													this.params.qxm = data.queryJxzzjgTree[key].qxm;
												}
												i++;
											}
											this.div();
											this.initParams();
										}, this);
						// 刷新机构组件
						this.navigation
								.on(
										'click',
										function() {
											var data = this.getValue(), len = data.nodes.length;
											me.params.zzjgId = data.nodes[len - 1].id;
											me.params.zzjgmc = data.nodes[len - 1].mc;
											me.params.qxm = data.nodes[len - 1].qxm;
											me.div();
										});
						// 刷新时间组件
						simpleDate.on('validatepass', function() {
							var data = this.getValue();
							me.params.from = data.from;
							me.params.to = data.to+1;
							me.div();
						});						
						window.onresize = function() {
							this.myChardiv11.resize();this.myChardiv12.resize();
							this.echa.resize();
						};

					},
			
					/**
					 * 初始化页面参数
					 */
					initParams : function() {
						this.params.kx = '01';
//						debugger;
						var nfdata = this.simpleDate.getValue();
						var navdata = this.navigation.getValue();
						this.params.from = nfdata.from;
						this.params.to = nfdata.to+1;
						this.params.ny = null;
						var me=this;
						$(function(){
//							$(".vavavavavav")
//							.click(
//									function() {
//										
//										if (((me.params.kx == '01' || me.params.kx == '10') && $(
//												this).attr("val") == '0')
//												|| me.params.kx == '11') {
//											if ($(this).attr("val") == '0') {
//												$(this).addClass(
//														"list-selected");
//												$(this).attr({
//													val : '1'
//												});
//											} else {
//												$(this).removeClass(
//														"list-selected");
//												$(this).attr({
//													val : '0'
//												});
//											}
//											;
//											me.params.kx = $("#kx1")
//													.attr("val")
//													+ $("#kx2")
//															.attr("val");
//											me.div();
//										}
//									});
							$("#kx1").click(function() {
								if($(this).attr("val") == '0'){
									$("#kx2").removeClass("list-selected");
									$("#kx2").addClass("list-default");
									$("#kx1").removeClass("list-default");
									$("#kx1").addClass("list-selected");
									$("#kx1").attr({val : '1'});
									$("#kx2").attr({val : '0'});
									me.params.kx = $("#kx1").attr("val")+ $("#kx2").attr("val");
									me.div();
								}
							})
								$("#kx2").click(function() {
								if($(this).attr("val") == '0'){
									$("#kx1").removeClass("list-selected");
									$("#kx1").addClass("list-default");
									$("#kx2").removeClass("list-default");
									$("#kx2").addClass("list-selected");
									$("#kx2").attr({val : '1'});
									$("#kx1").attr({val : '0'});
									me.params.kx = $("#kx1").attr("val")+ $("#kx2").attr("val");
									me.div();
								}
							})
						});
					},

					// 主页面显示
					div : function() {
						$("#div13div").hide();$("#fanhui").hide();
						this.myChardiv11 = echarts.init(document// init(Ext.Component());
								.getElementById('div11'),'blue');
						this.callService({
							key : 'cggk1',
							params : this.params
						}, function(dat) {
							var me = this;
							var datas = dat.cggk1;
							var title = {
								// 主标题文本，'\n'指定换行
								text : '',
								x : 'left'
							}
							
							var data = this.data2d(datas);
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
//									orient : 'vertical',
//									x : 'left',
									data : legends
								}, 
								//calculable : true,
								noDataLoadingOption : {
									text : '科研概况暂无数据',
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
								//
								series : [ {
									name : '数量',
									type : 'pie',
									radius : '55%',
									center : [ '50%', '60%' ],
									data : datas
								} ]
							};
							
							//var option = this.bzt(datas, title);
							// var myChardiv11 = echarts.init();
							
							me.myChardiv11.setOption(option);
							//window.onresize = me.myChardiv11.resize;
							// --
							me.myChardiv11.on(echarts.config.EVENT.CLICK,
									function(param) {
										console.log(param);
										me.params.lbname = param.name;
										me.newWindow('window11', me.xq11());

									});
							// --
						}, this);
						this.myChardiv12 = echarts.init(document
								.getElementById('div12'),'blue');
						this.echa = echarts.init(document
								.getElementById('div13'),'blue');
						this.callService({
							key : 'cggk2',
							params : this.params
						}, function(data) {
							var me = this;
							
							var datas = me.data3d(data.cggk2, 'bar');
							var legents = datas[0];
							var xAxiss = datas[1];
							var seriess = datas[2];
							
							var option = {
								backgroundColor:'#FFFFFF',
								title : {
									text : ''
								},
								tooltip : {
									trigger : 'axis'
								},
								grid:{
								    y:100
								  },
								legend : {
//									orient : 'vertical',
//						             x:'right',
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
								//calculable : true,
								xAxis : [ {
									type : 'category',
									data : xAxiss
								} ],
								yAxis : [ {
									type : 'value'
								} ],
								series : seriess,
								noDataLoadingOption : {
								  text : '科研成果数量暂无数据',
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
							
							
							
						
							
							me.myChardiv12.setOption(option);
							//window.onresize = me.myChardiv12.resize;
							// --
							me.myChardiv12.on(echarts.config.EVENT.CLICK,
									function(param) {
										console.log(param);
										me.params.bm = param.name; // 部门
										me.params.lbname = param.seriesName;
										me.xq12();
									});
							// --
						}, this);
						var me=this;
						window.onresize = function() {
								me.myChardiv11.resize();
								me.myChardiv12.resize();
								me.echa.resize();	
						};
					},
					// 详情11
					xq11 : function() {						
						var me = this;
						var title = {
							text : '',
							x : 'center',
							y : 'top'
						};
						me.callService({
							key : 'lbxz',
							params : me.params
						}, function(data) {
							var option =null;
							var html="<span style='padding:0px 0px 0px 5px;font-weight: bold;font-size: 16px;'>";
							switch (me.params.lbname) {
							case '专利':
								title.text = '';
								html+='专利详情';
								option = me.bzt(data.lbxz, title);
								break;
							case '科研获奖成果':
								title.text = '';	html+='科研获奖详情';
								option = me.djbzt(data.lbxz, title);
								break;
							case '著作/教材':
								title.text = '';html+='著作无具体分类';
								option = me.bzt(data.lbxz, title);
								break;
							case '成果鉴定':
								title.text = '';html+='成果鉴定详情';
								option = me.bzt(data.lbxz, title);
								break;
							}
							var myChar = echarts.init(document
									.getElementById('window11'),'blue');
							//var option = me.bzt(data.lbxz, title);
							myChar.setOption(option);
							var page = this.component = new NS.Component({
								baseCls : '',
								html : html+'</span>',
								margin:[20,0,0,0],
								autoShow : true
							});
							page.render('id11');
						}, this);
					},
					// 详情12
					xq12 : function() {
						var me = this;
						var title = null;
						var field = null;
						var chaxun = {
							name : '',
							lx : ''
						};// ,xk:''};
						var params = {
							params : me.params,
							start : 0,
							limit : 15,
							chaxun : chaxun
						};
						/*
						 * 创建下拉值
						 */
						var xqcreateComboBox = function(
								data, colum) {
							var itemsMap = {};
							var items = [];
							for (var i = 0; i < data.data.length; i++) {
								var item = data.data[i][colum];
								if (itemsMap[item] == null) {
									itemsMap[item] = {};
									items.push(item);
								}
							}
							var dat = [];
							for (var i = 0; i < items.length; i++) {
								dat.push({
									id : i,
									mc : items[i]
								});
							}
							return dat;
						};
						/*
						 * 详情页面对象
						 */
						xqym = function(items, data,
								params, fields, serviceKey) {
							var gridFields = [];
							for ( var key in data.data[0]) {
								gridFields.push(key);
							}
							gridFields.sort();
							gridFields.pop();
							var columnCfgs = me.getColumn(
									gridFields, fields);
							// 创建详情grid
							var dxgrid = this.dxgrid = me
									.initXqGrid(data,
											gridFields,
											columnCfgs,
											params,
											serviceKey,
											false, true, 15);
							var title = {
								xtype : "box",
								layout : 'fit',
								id:'title',
								html : "<div style='margin:20px;text-align:center;font-size:24px;text-align:ceter'>"
										+ me.params.from
										+ "-"
										+ me.params.to
										+ "年"
										+ me.params.bm
										+ me.params.lbname
										+ "详细列表</div>"
							};

							var butt = {
								xtype : "button",
								style : {
									marginLeft : '10px',
									marginRight : '20px'
								},
								text : "查询",
								handler : function() {
									params.chaxun.name=Ext.getCmp('name').getValue();
									if(me.params.lbname=='著作/教材'){
										params.chaxun.lx=Ext.getCmp('lx').getValue();
									}else{
										params.chaxun.lx=Ext.getCmp('lx').rawValue;
									}
									
									 dxgrid.load();
								
									//this.up("window").close();
								}
							};
							 var exportMd=function(servicAndMethod,queryGridParams){
					                // 导出
					                var serviceAndParams ={
					                    servicAndMethod:servicAndMethod,
					                    params:queryGridParams
					                } ;
					                var strParams = JSON.stringify(serviceAndParams);
					                window.document.open("customExportAction.action?serviceAndParams="+strParams,
					                    '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
					            };
				            var exportBtn1 = new NS.button.Button({
				                text : "导出数据",
				                handler : function(){
				                    exportMd("Kycg_yangtzService?xqxzexcel",{
										params : me.params,
										chaxun : params.chaxun,
										fields:fields,
										title:Ext.getCmp('title').container.dom.childNodes[0].textContent//.innerText;
				             
									});
				                },
				                iconCls : "page-excel",
				                border:true
				            });
				           
							items.push(butt);
							items.push(exportBtn1);
							var xqcx = {
								xtype : "container",
								layout : "hbox",
								items : items
							};
							me.win = new NS.window.Window(
									{
										title : ' 列表详情',
										layout : 'fit',
										modal : true,
										width : 810,
										height : 580,
										id:'win',
										items : [ {
											xtype : "form",
											defaultType : 'textfield',
											defaults : {
												anchor : '100%',
											},
											fieldDefaults : {
												labelWidth : 80,
												labelAlign : "left",
												flex : 1,
												// margin:
												// 5,
												width : 280
											},
											height:105,
											items : [
														title,
														xqcx
														 ]
											},{
															id	:'grid',layout : 'fit',
															height:445,
															items : [dxgrid]
															} ]
									});
							me.win.show();
						};
						
						
						var items = [];
						me.callService(
										{
											key : 'xqxz',
											params : params
										},
										function(data) {
											var data = data.xqxz;

											switch (me.params.lbname) {
											
											case '专利':
												field = "单位代码,发明人单位,发明人,专利名称,专利类型,专利编号,专利实施,专利号,申请日,授权日,证书编号,备注";
												title = '专利详情';
												items = [
														{
															xtype : "textfield",
															id:'name',
															style : {
																marginLeft : "50px"
															},
															name : "name",
															fieldLabel : "专利名称"
														},
														me.createComboBox(
																		xqcreateComboBox(
																				data,
																				'L06'),
																		'   专利类型') ];
												break;
											case '科研获奖成果':
												field = "单位代码,获奖单位,成果名称,成果编号,获奖名称,获奖等级,授奖单位,证书编号,评价等级,备注";
												title = '科研获奖详情';
												items = [
														{
															xtype : "textfield",
															id:'name',
															style : {
																marginLeft : "50px"
															},
															name : "name",
															fieldLabel : "成果名称"
														},
														me
																.createComboBox(
																		xqcreateComboBox(
																				data,
																				'L07'),
																		'   获奖等级') ];
												break;
											case '著作/教材':
												field = "单位代码,作者单位,作者姓名,著作名称,著作编号,出版单位,出版时间,著作字数,著作书号,备注";
												title = '著作详情';
												items = [ {
													xtype : "textfield",
													id:'name',
													style : {
														marginLeft : "50px"
													},
													name : "name",
													fieldLabel : "著作名称"
												}, {
													xtype : "textfield",
													id:'lx',
													style : {
														marginLeft : "50px"
													},
												
													fieldLabel : "作者名称"
												}, ];
												break;
											case '成果鉴定':
												field = "单位代码,完成人单位,完成人,成果名称,成果编号,鉴定单位,鉴定级别,鉴定形式,鉴定水平,鉴定证号,鉴定时间,我校名次,备注";
												title = '成果鉴定详情';
												items = [ {
													xtype : "textfield",
													id:'name',
													style : {
														marginLeft : "50px"
													},
													name : "name",
													fieldLabel : "成果名称"
												} ];
												break;
											}

											xqym(items, data, params, field,
													'xqxz');

										}, this);
					},
					/*
					 * 一级弹窗
					 */
					option13:null,
					tcc : function(option1){
						this.option13=option1;
						this.callService(
								'queryJxzzjgTree',
								function(data) {
						var me=this;	
						$("#id1").show(); 
						$("#fanhui").show();   
						$(function(){
							$("#fanhui").click(function(){
								document.getElementById('div13').innerHTML = '';
								me.echa = echarts.init(document
										.getElementById('div13'),'blue');
								me.echa.setOption(me.option13);	
								me.echa.on(
										echarts.config.EVENT.CLICK,
										function(param) {
											me.params.ny = me.option13.xAxis[0].data[param.dataIndex];
											me.params.lbname = me.option13.legend.data[param.seriesIndex];
											me.tcc(me.option13);
									});
								$("#fanhui").hide();   
								$("#id1").hide(); 
							});   
						});
						me.echa.clear();
						//myChar.clear();
						document.getElementById("id1").innerHTML = "";
						var myChar = echarts.init(document
								.getElementById('div13'),'blue');
						var title = {
							// 主标题文本，'\n'指定换行
							text : '',
							// 主标题文本超链接
							//link : 'javascript:outer();',
							target : 'self',
							// 副标题文本，'\n'指定换行
						//	subtext : '点击标题返回上一级',
							// sublink:
							
							// 'http://www.stepday.com/myblog/?Echarts',
							// 水平安放位置，默认为左侧，可选为：'center'
							// | 'left'
							// | 'right'
							// |
							// {number}（x坐标，单位px）
							x : 'left',
							// 垂直安放位置，默认为全图顶端，可选为：'top'
							// |
							// 'bottom'
							// |
							// 'center'
							// |
							// {number}（y坐标，单位px）
							y : 'top'
						};
						switch (me.params.lbname) {
						case '专利':
							title.text = '专利详情';
							me.params.fl = '分类';
							me.xzechars(
									myChar,
											'qsxz',
											title);
							me.comboBox(
											[
													"分类",
													"状态" ],
													myChar,
											'qsxz',
											title);
							break;
						case '科研获奖成果':
							title.text = '科研获奖详情';
							me.params.fl = '类型';
							me
									.xzechars(
											myChar,
											'qsxz',
											title);
							me
									.comboBox(
											[ "类型" ],
											myChar,
											'qsxz',
											title);
							break;
						case '著作/教材':
							title.text = '著作详情';
							me.params.fl = '出版单位';
							me
									.xzechars(
											myChar,
											'qsxz',
											title);
							me
									.comboBox(
											[ "出版单位" ],
											myChar,
											'qsxz',
											title);
							break;
						break;
					case '成果鉴定':
						title.text = '成果鉴定详情';
						me.params.fl = '级别';
						me.xzechars(
								myChar,
								'qsxz',
								title);
						me.comboBox(
										[
												"级别",
												"鉴定类型",
												"领先水平" ],
												myChar,
										'qsxz',
										title);
						break;
					};
					},this)},
					window1 : function() {
						var me = this;

						this
								.callService(
										{
											key : 'cgqs',
											params : this.params
										},
										function(data) {

											
											var datas = me.data3d(data.cgqs, 'line');
											var legents = datas[0];
											var xAxiss = datas[1];
											var seriess = datas[2];
											
											var option = {
												backgroundColor:'#FFFFFF',
												title : {
													text : ''
												},
												tooltip : {
													trigger : 'axis'
												},
												grid:{
												    y:100
												  },
												legend : {
//													orient : 'vertical',
//										             x:'right',
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
												//calculable : true,
												xAxis : [ {
													type : 'category',
													data : xAxiss
												} ],
												yAxis : [ {
													type : 'value'
												} ],
												series : seriess,
												noDataLoadingOption : {
												  text : '学院历年成果分布统计暂无数据',
												   effectOption : null,
												    effect : 'bubble',
												    x:'center',
												    y:'center',
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
											
//											me.echa = echarts
//													.init(document
//															.getElementById('div13'));
											document.getElementById("div13").innerHTML = "";
											me.echa = echarts.init(document
													.getElementById('div13'),'blue');
											me.echa.clear();
											me.echa.setOption(option);	
											me.echa.resize();
											//window.onresize = me.echa.resize;
											// --
											
											me.echa.on(
															echarts.config.EVENT.CLICK,
															function(param) {
																me.params.ny = option.xAxis[0].data[param.dataIndex];
																me.params.lbname = option.legend.data[param.seriesIndex];
																me.tcc(option);
														});
											outer = function() {
												document.getElementById("id1").innerHTML = "";
												myChar.clear();
												myChar.setOption(option);
											}
										
											//--
										}, this);

					}
					

				});
