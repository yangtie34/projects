/**
 * @class NS.grid.plugin.HeaderQuery
 * @extends NS.Plugin
 *    {NS.grid.Grid}的插件
 * @author wangyongtai
 *      var grid = NS.grid.Grid({
 *          plugins : [new NS.grid.plugin.HeaderQuery()]
 *      });
 */
NS.define('NS.grid.plugin.HeaderQuery',{
    extend : 'NS.Plugin',
    /**
     * 构造函数
     * @constructor
     * @param config 组件配置对象
     */
	constructor : function(config){
		this.addEvents('headerclick','query');//添加表头点击事件
	},
	/***
	 * 插件初始化函数
	 * @param grid
	 */
	init : function(grid){
		this.registerGrid(grid);
		this.addHeaderClick();
        this.addListener('headerclick',this.onHeaderClick,this);
        this.addListener('query',this.onQuery,this);
	},

	/***
	 * 将grid进行注册
	 * @param grid 
	 */
	registerGrid : function(grid){
		this.grid = grid;
	},
	/***
	 * 清空查询组件状态
	 */
	clear : function(){
		this.bbar.remove(this.panel);
		//this.bbar.insert(12, { xtype: 'tbfill' });
		delete this.params;//删除查询参数
        delete this.panel;
		delete this.showInfos;//删除显示条件
        this.grid.load({});
	},
	/***
	 * 获取查询组件参数(用于和grid进行交互)
	 * @return {Object}
	 */
	getParams : function(){
		var item = this.columnData,// 创建表头所需数据
			component = this.component,// 创建的组件
			params = this.params = this.params||{},// 查询参数
			xtype = item.xtype,
			name = item.name;
		if (xtype == 'textfield'||xtype == 'textarea') {
			name = item.name + ".like";
			var value = component.text.getValue();
			params[name] = value;
		} else if(xtype=='numberfield'){
			var value = component.text.getValue();
			params[name] = value;
		}else if (xtype == "combobox"
				|| xtype == "tree"
				|| xtype == "checkbox"
				|| xtype == 'treecombobox') {
			var value = component.combox.getValue();
			params[name] = value;
		} else if (xtype == 'datefield') {
			var name1 = name + ".date1";
			var name2 = name + ".date2";
			var value1 = component.date1.getRawValue();
			var value2 = component.date2.getRawValue();
			params[name1] = value1;
			params[name2] = value2;
		}else if(xtype == 'timefield'||xtype == "yearmonth"){
			var value = component.combox.getRawValue();
			params[name] = value;
		}else{
			var value = component.combox.getValue();
			params[name] = value;
		}
		return params;
	},
	/**
	 * 为添加表头点击事件
	 */
	addHeaderClick : function(){
		var me = this,
		    grid = this.grid.getLibComponent(),
			dataSet = this.grid.config.columnData;
        if(grid.headerCt){
            grid.headerCt.on('headerclick', function(ct, column, event, t,
                                                     eOpts) {
                Ext.each(dataSet, function(item) {
                    if (item.name == column.dataIndex
                        && item.isQuery == 1) {
                        me.fireEvent('headerclick',column,item,event);
                    }
                });
            });
        }else if(grid.lockedGrid.headerCt && grid.normalGrid.headerCt ){
            grid.lockedGrid.headerCt.on('headerclick', function(ct, column, event, t,
                                                     eOpts) {
                Ext.each(dataSet, function(item) {
                    if (item.name == column.dataIndex
                        && item.isQuery == 1) {
                        me.fireEvent('headerclick',column,item,event);
                    }
                });
            });
            grid.normalGrid.headerCt.on('headerclick', function(ct, column, event, t,
                                                                eOpts) {
                Ext.each(dataSet, function(item) {
                    if (item.name == column.dataIndex
                        && item.isQuery == 1) {
                        me.fireEvent('headerclick',column,item,event);
                    }
                });
            });
        }

	},
	/***
	 * 当表头被点击的时候
	 * @param itemData 该列的表头对应的数据
	 */
	onHeaderClick : function(column,itemData,event){
		this.columnData = itemData;
		this.createHeaderWindow.apply(this,arguments);
        if(this.columnData.xtype!='treecombobox'){
        	this.doListeners();//执行监听
        }else{
//        	this.component.combox.on({
//        		
//        	});
        }
	},
    /**
     * 执行特殊的条件监听，用以检测是否让表头查询组件消失
     */
    doListeners : function() {
        var win = this.window;
        var winEl = win.getEl();// 获取window所在的元素
        var body = Ext.getBody();
        var bdom = body.dom;
        var close = false;
//        if(this.columnData.xtype=='treecombobox'){
//        	winEl = this.component.combox.getEl();//如果是下拉树,其作用域范围扩大
//        }
        body.on('click', function(event) {// 针对body的监听事件
            var parent = event.getTarget();
            if (event.within(winEl)) {
                return;
            }else if(!event.within(winEl)) {
                while (parent != bdom) {
                    if (parent !== null
                        && (parent.id.match("datepicker") != null)) {
                        return;
                    } else {
                        parent = (parent != null
                            ? parent.parentNode
                            : bdom);
                    }
                }
                if (close) {
                    win.close();
                } else {
                    close = true;
                }
            }
        });
    },
	/***
	 * 当query事件被触发后
	 */
	onQuery : function(){
		var params = this.getParams();
		var grid = this.grid;
		//对null值的判断
        grid.load(params);
//		if(grid.query == this){
//			grid.load(params);
//		}else{
//            if(grid.query) grid.query.clear();
//            //grid.load(params);
//            grid.query = this;
//		}
        this.window.close();
        delete this.window;
        this.setPagingBar();
	},
	/**
	 * 设置分页栏上的显示情况
	 */
	setPagingBar : function(){
		this.setShowMessage();//设置pagingbar上显示的信息
        this.insertPanelIntoBottom();//将显示的字符串 插入进分页面栏中
	},
	/**
	 * 将显示的查询条件的面板插入PagingBar中
	 */
	insertPanelIntoBottom : function(){
		var me = this;
        var barsize = this.barsize = this.getSizeForBarPanel();
		var panel = this.panel = this.panel||Ext.create('Ext.panel.Panel', {
					height : barsize.height - 5,
					frame : true,
					border : false,
					baseCls : '{solid #B5B8C8;}',
					margin : 0,
					html : ''
				});// 获取数据显示面板
        if(!this.bbar.items.contains(panel)){
            this.bbar.insert(11, panel);
        }
		var id = Ext.id();
		var query = this.getQueryString() + "&nbsp;&nbsp;<span id='" + id + "'></span>";
		panel.body.update(query);
		var button = Ext.create('Ext.button.Button', {
			iconCls : 'page-delete',
			handler : function() {
				me.clear();
			},
			renderTo : id
		});
		
	},
    /**
     * 获取refresh button的位置以及pagingbar的高度
     */
    getSizeForBarPanel : function() {
        var me = this;
        var bbar = this.bbar = me.grid.getLibComponent().getDockedComponent(1);// 底部bbar
        var el = bbar.getEl();
        var height = el.getHeight();
        var button = bbar.getComponent('refresh');
        var elr = button.getEl();// 刷新按钮的el
        var displayItem = bbar.child('#displayItem');
        var eld = displayItem.getEl();// 显示信息的el
        var params = {
            x : elr.getX() + elr.getWidth(),// 信息的初始化x位置
            y : el.getY(),// 信息窗体的初始化y位置
            width : eld.getX() - elr.getX() - elr.getWidth() - 40,// 最大宽度
            height : height
        };
        return params;
    },
	/**
	 * 获取显示的查询字符串
	 */
	getQueryString : function(){
		var basequery = "";
		var params = this.showInfos;//显示的信息
		for (var i = 0; i < params.length; i++) {
			if (i == params.length - 1) {
				basequery += "\"" + params[i].name + "\"" + " 等于 " + "\""
						+ params[i].value + "\"";
			} else {
				basequery += "\""
						+ params[i].name
						+ "\""
						+ " 等于 "
						+ "\""
						+ params[i].value
						+ "\"";
						
			}
			if (this.getStrLength(this.delHtmlTag(basequery)) >= this.barsize.width-40
				) {
                basequery += "&nbsp;&nbsp;<span style='color:red;'>并且......</span>&nbsp;&nbsp;";
				break;
			}
		}
		return basequery;
	},
    /***
     * 删除字符串中的HTML标签
     *
     * @param {String} str 待删除字符串
     * @return {String}
     */
    delHtmlTag : function(str) {
        return str.replace(/<\/?.+?>/g, "");// 去掉所有的html标记
    },
    /**
     * 获取字符串的像素
     *
     * @param {String} str
     * @return {Number}
     */
    getStrLength : function(str) {
        var len = 0;
        for (var i = 0;; i++) {
            if (!str.charCodeAt(i))
                break;
            if (str.charCodeAt(i) > 255)
                len += 2*6;
            else
                len += 1*6;
        }
        return len;
    },
	/***
	 * 当鼠标悬停查询条件面板一秒后
	 */
	onPanelHover : function(){
		var el = this.panel.getEl();// 获得Window的Element元素
		var timeflag = true;
		el.on('mouseover', function(event, html, obj) {// 增加监听事件
					timeflag = true;
					Ext.defer(function() {
								if (timeflag && !PopWindow.popDiv)
									me.showPop(event);// 显示弹泡信息
							}, 1000);
				});
		el.on('mouseout', function(event, html, obj) {// 增加监听事件
					timeflag = false;
				});
	},
	showPop : function(event){
		var query = this.getQueryString();
		if (this.popDiv&&this.popDiv.parentNode == document.body) {
			document.body.removeChild(this.popDiv);
		}
		var config = {};
		if(Ext.isIE8){
			   config.x = obj.getX();
			   config.y = obj.getY();
		}else{
			   config.x = event.getX();
			   config.y = event.getY();
		}
		var me = this;
		//var client = this.getBodySize();// 获取body的宽度和高度
		var div = document.createElement('div');
		this.popDiv = div; 
		var style = div.style;
		style.webkitBorderRadius = 8;
		style.mozBorderRadius = 8;
		style.borderRadius = 8;
		style.boxShadow = "#666 0px 0px 6px";
		style.width = config.width;
//		style.left = config.x + 10;
//		style.top = config.y - 70;
		style.padding = "16 10 16 16";
		style.position = 'absolute';// 绝对位置
		style.zIndex = 1000000;// 设置层的级别
		div.innerHTML = "<div style=\'float:left;width:32;height:100%;\'><img src=\'images/020.png\' style = \'width:32;height:32;\'/></div>" +
							"<div style='margin-left:16;float:right;line-height:2;height:100%;padding-top:0;width:260px;'>"+config.message+"</div>";// div的内容
		div.className = "x-window-body x-window-body-default x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable x-box-layout-ct";
		Ext.getBody().appendChild(div);
		Ext.fly(div).setXY([config.x + 10,config.y - 70]);
		var size = this.getSize(div);//获取div的高度和宽度
		Ext.fly(div).setY(config.y- Ext.fly(div).getWidth().height);
		Ext.defer(function() {
					if (div.parentNode == document.body)
						Ext.fly(div).remove();
						delete me.popDiv;
				}, 3000);
	},
	/**
	 * 获取refresh button的位置以及pagingbar的高度
	 */
	getBarSize : function() {
		var me = this;
		var bbar = this.bbar = this.bbar||this.grid.getLibComponent().getDockedComponent('bottom');// 底部bbar
		var el = bbar.getEl();
		var height = el.getHeight();
		var button = bbar.getComponent('refresh');
		var elr = button.getEl();// 刷新按钮的el
		var displayItem = bbar.child('#displayItem');
		var eld = displayItem.getEl();// 显示信息的el
		var params = {
			x : elr.getX() + elr.getWidth(),// 信息的初始化x位置
			y : el.getY(),// 信息窗体的初始化y位置
			width : eld.getX() - elr.getX() - elr.getWidth() - 40,// 最大宽度
			height : height
		};
		return params;
	},
	/**
	 * 将查询的条件信息加入到查询信息数组中
	 * 
	 * @private
	 * @param config
	 *            配置参数对象
	 * @return
	 */
	setShowMessage : function() {
		var item = this.columnData,
			xtype = item.xtype,
			name = item.nameCh,
			component = this.component;
		var infos = this.showInfos = this.showInfos||[];
		
		if (xtype == "textfield"||xtype == "textarea"||xtype=='numberfield') {// 组件类型为text时
			for (var i = 0; i < infos.length; i++) {
				if (infos[i].name == name) {
					infos[i].value = component.text.getValue();
					return;
				}
			}
			infos.push({
						name : name,
						value : component.text.getValue()
					});
		} else if (xtype == "combobox"
				|| xtype == "tree"
				|| xtype == "timefield"
				|| xtype == "yearmonth"
				|| xtype == 'checkbox'
				|| xtype == 'treecombobox') {// 组件类型为combox时
			for (var i = 0; i < infos.length; i++) {
				if (infos[i].name == name) {
					infos[i].value = component.combox.getRawValue();
					return;
				}
			}
			infos.push({
						name : name,
						value : component.combox.getRawValue()
					});
		} else if (xtype == "datefield") {// 组件类型为datefield时
			for (var i = 0; i < infos.length; i++) {
				if (infos[i].name == name) {
					infos[i].value = component.date1.getRawValue() + " 到 "
							+ component.date2.getRawValue();
					return;
				}
			}
			infos.push({
						name : name,
						value : component.date1.getRawValue() + " 到 "
								+ component.date2.getRawValue()
					});
		}
	},
	/**
	 * 创建表头窗体
	 * @param itemData 该列的表头对应的数据
	 */
	createHeaderWindow : function(column,itemData,event){
		var defaultConfig  = {
		    		closable : false,
		    		draggable : false,
		    		header : false,
		    		height : 100,
		    		border : false,
		    		plain : true
		    	};
        var component = this.createComponent(itemData);
		if (itemData.xtype == "textfield"
			||itemData.xtype == "textarea"
				||itemData.xtype == "numberfield") {
			NS.apply(defaultConfig,{
						width : 178,
						height : 63,
						minWidth : 162,
						minHeight : 63,
						buttonAlign : 'center',
						layout : {
							type : 'vbox',
							align : 'center'
						},
						items : [component.text],
						buttons : [component.button1, component.button2]
					});
		} else if (itemData.xtype == "combobox"
				|| itemData.xtype == "tree"
				|| itemData.xtype == "timefield"
				|| itemData.xtype == "checkbox"
				|| itemData.xtype == "treecombobox") {
			NS.apply(defaultConfig,{
						width : 160,
						height : 30,
						items : component.combox
					});
		} else if (itemData.xtype == 'datefield') {
			NS.apply(defaultConfig,{
						width : 220,
						height : 100,
						buttonAlign : 'center',
						layout : {
							//type : 'vbox',
							align : 'center'
						},
						items : [component.date1, component.date2],
						buttons : [component.button1, component.button2]
					});
		}
		if(this.window){
			this.window.close();
		}
		this.window = Ext.create('Ext.window.Window',defaultConfig);
		if(component.text){
		   component.text.focus(true, true);// 获得焦点
		}
		//设置显示窗体的位置以及大小
		this.setWindowPositionAndSize(this.window,column);
	},
	/**
	 * 设定window的位置
	 */
	setWindowPositionAndSize : function(window,column){
		var el = column.getEl();
		var pagey = el.getY() + el.getHeight();// y轴位置
		var pagex = el.getX();//x轴位置
		var body = Ext.getBody();//获取body的Ext.Element对象
		var x = body.getWidth();
		if (pagex + window.width > x) {
			pagex = x - window.width-20;
		}
		window.setPosition(pagex, pagey, false);
		window.setSize({
			width : window.width,
			height : window.height
		});
		window.show();// 显示window窗体
	},
	createWindowContainer : function(itemData){
		
	},
	/**
	 * 创建conditionWindow所需要的组件
	 * 
	 * @param config
	 *            配置参数对象
	 */
	createComponent : function(itemData) {
		var me = this;
        var codeData = itemData.codeData.data,//编码数据
            name = itemData.name,//组件name
            xtype = itemData.xtype;//组件类型
        var date1_date2 = true;//针对日期查询
		var component = this.component = {};// 声明组件
		if (xtype == 'textfield'||xtype == 'textarea') {
			component.text = Ext.create('Ext.form.field.Text', {
						hiddenName : name,
						border : false,
						width : 150,
						listeners : {
							'specialkey' : function(textfield, e) {
								if (e.getKey() == e.ENTER) {
									component.button1.fireEvent('click');
								}
							}
						}
					});
		}else if(xtype=='numberfield'){
			component.text = Ext.create('Ext.form.field.Number', {
				hiddenName : name,
				border : false,
				width : 150,
				listeners : {
					'specialkey' : function(textfield, e) {
						if (e.getKey() == e.ENTER) {
							component.button1.fireEvent('click');
						}
					}
				}
			});
		}else if (xtype == 'timefield') {
			component.combox = Ext.create('Ext.form.field.Time', {
				hiddenName : name,
				border : false,
				format :"G时i分",
	            submitFormat:"G时i分",
				width : 150,
				listeners : {
					'select' : function() {
						me.fireEvent('query');
					}
				}
			});
		} else if (xtype == "combobox") {
			component.combox = Ext.create('Ext.form.field.ComboBox', {
						width : 150,
						valueField : 'id',
						displayField : 'mc',
						hiddenName : name,
                        lastQuery : '',
                        queryMode : 'local',
						store : {data : codeData,fields : ['id','mc']},
						allowBlank : false,
						editabled : false,
						listeners : {
							'select' : function() {
								me.fireEvent('query');

							}
						}
					});
		} else if (xtype == 'treecombobox') {
			var config = {
						//emptyText : '选择叶子节点...',
						expanded : true,
						editable : true,
						isLeafSelect:true,
						//isParentSelect:false,
						rootVisible:false,
					    width : 150,
					    treeData: NS.clone(codeData.children[0]),
						listeners : {
							'treeselect' : function(self,records) {
								me.fireEvent('query');
							}
						}
					};
			component.combox = Ext.create('Ext.ux.comboBoxTree', config);
		} else if (xtype == "tree") {
			component.combox = Ext.create('Ext.form.field.ComboBox', {
						width : 150,
						valueField : 'id',
						store : {data : codeData,fields : ['id','text','pid']},
						displayField : 'text',
						queryMode : 'local',
						editable : false,
						allowBlank : false,
						editabled : false,
						listeners : {
							'select' : function() {
								me.fireEvent('query');
							}
						}
					});
		} else if (xtype == "checkbox") {
			component.combox = Ext.create('Ext.form.field.ComboBox', {
				width : 150,
				editable : false,
				store : {
				     fields : ['id','mc'],
				     data : [
				        {'id':'1','mc' : '是'},
				        {'id':'0','mc' : '否'}
				     ]
				   },
				valueField : 'id',// 实际值
				displayField : 'mc',// 显示值
				allowBlank : false,
				editabled : false,
				listeners : {
					'select' : function() {
						me.fireEvent('query');
					}
				}
			});
		} else if (xtype == 'datefield') {
			var dId1 = Ext.id();
			var dId2 = Ext.id();
			component.date1 = Ext.create('Ext.form.field.Date', {
						id : dId1,
						fieldLabel : '起始',
						emptyText:'应早于结束时间...',
						format : 'Y-m-d',
						labelWidth : 40,
						editable : false,
						name : 'startValue',
						//vtype : 'daterange',// daterange类型为上代码定义的类型
						endDateField : dId2,// 必须跟endDate的id名相同
						listeners : {
//							'specialkey' : function(date1, e) {
//								if (e.getKey() == e.ENTER) {
//									component.button1.fireEvent('click');
//								}
//							},
							change:function(text,nV,oV){
								if(component.date2.getValue()!=null&&(text.getValue()>component.date2.getValue())){
									date1_date2 = false;
									text.setValue(null);
								}else{
									date1_date2 = true;
								}
							}
						}
					});
			component.date2 = Ext.create('Ext.form.field.Date', {
						id : dId2,
						fieldLabel : '结束',
						emptyText:'应晚于起始时间...',
						format : 'Y-m-d',
						labelWidth : 40,
						name : 'endValue',
						editable : false,
						//vtype : 'daterange',// daterange类型为上代码定义的类型
						startDateField : dId1,// 必须跟startDate的id名相同
						listeners : {
//							'specialkey' : function(date2, e) {
//								if (e.getKey() == e.ENTER) {
//									component.button1.fireEvent('click');
//								}
//							},
							change:function(text,nV,oV){
								if(component.date2.getValue()!=null&&(text.getValue()<component.date1.getValue())){
									date1_date2 = false;
									text.setValue(null);
								}else{
									date1_date2 = true;
								}
							}
						}
					});
		}
		component.button1 = Ext.create("Ext.button.Button", {
					text : "确认",
					textAlign : "center",
					//tooltip:'起始时间需晚于结束时间,且均不可为空时方可执行.',
					listeners : {
                        click : {
                            fn : function() {
                                if(xtype == 'datefield'){
                                	if(!(component.date1.getValue()==component.date2.getValue()&&component.date1.getValue()==null)){
                                    	if(date1_date2 ==true){
                                    		this.fireEvent('query');	
                                    	}
                                    }
                                }else{
                                	this.fireEvent('query');
                                }
                            	
                            },
                            scope : this
                        }
					}
				});
		component.button2 = Ext.create("Ext.button.Button", {
					text : "取消",
					textAlign : "center",
					listeners : {
                           click : {
                               fn : function() {
                                    this.window.close();
                               },
                               scope : this
                           }
					}
				});

		return component;
	}
});