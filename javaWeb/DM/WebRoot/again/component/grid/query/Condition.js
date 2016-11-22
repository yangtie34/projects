/**
 * 
 * 创建一个查询条件组件
 * 
 * @class NS.grid.query.Condition
 * @author:wangyongtai
 * @private
 */
NS.define('NS.grid.query.Condition',{
   /**
    * 初始化构造函数
    * 
    */
   constructor:function(){
      this.initData.apply(this,arguments);
   },
   /**
	 *  配置组件数据源
	 * @param {Object} config 需要配置的数据源
	 * */
	initData:function(config){
		this.config = config;
		this.ids = [];
		this.num = 1;
	},
	/**
	 *  初始化组件
	 * @param config
	 *            配置参数
	 */
	initComponent : function() {
		var config = this.config;
		this.SeniorQuery = config.SeniorQuery;// 设置父类容器
		this.setFieldsStore({
					data : config.fieldsStoreData
				});// 设置字段属性store
		this.componentsData = config.componentsData;// 组件数据
		this.createLogicalOperatorStore();// 创建逻辑操作符store
		this.createOperatorStore();// 创建操作符store

	},
	/**
	 *  获取前面显示的 类似 条件1、条件2、条件3。。。。
	 *  
	 * @return string 返回的拼写字符串
	 */
	getShowText : function() {
		var text = "条件" + this.num++;
		return text;
	},
	/**
	 * 获取组件panel
	 * @return {Ext.panel.Panel}
	 */
	getPanel : function() {
		var me = this;
		var id = Ext.id();
		this.ids.push(id);
		var fieldCombobox = this.createFieldsCombox({// 创建字段属性combox
			id : id + "_fields",
			configId : id,
			width : 220,
			fieldLabel : me.getShowText() + "-  属性列表"
		});
		var operatorCombox = this.createOperatorCombox({// 操作符combox
			id : id + "_operator",
			width : 150,
			columnWidth : .20
		});
		var text = Ext.create('Ext.form.field.Text', {// 默认text框
			fieldLabel : '值',
			labelWidth : 25,
			width : 150,
			id : id + "_remove"
		});
		var logicalOperatorCombox = this.createLogicalOperatorCombox({// 逻辑操作符combox
			id : id + "_logical",
			width : 150
		});
		var button = Ext.create('Ext.button.Button', {
					text : '添加条件',
					id : id + "_button",
					listeners : {
						'click' : function(button) {
							if (this.text == "添加条件") {
								me.SeniorQuery.window.add(me.getPanel());// 将新生成的面版加入window中
								this.setText("删除条件");
							} else if (this.text == "删除条件") {
								me.SeniorQuery.window.remove(Ext.getCmp(id));
								me.deleteIdFromIds(id);
								me.resizeLabel();
							}

						}
					}
				});
		var panel = Ext.create('Ext.form.Panel', {// 创建 panel
						width : '100%',
						height : 45,
						id : id,
						border : false,
						frame : true,
						margin : '5,5,0,5',
						padding : '10,0,0,0',
						layout : 'column',
						items : [fieldCombobox, operatorCombox, text,
								logicalOperatorCombox, button]
		});
		return panel;
	},
	/**
	 * 设置条件标签属性
	 */
	resizeLabel : function() {
		for (var i = 0; i < this.ids.length; i++) {
			var com = Ext.getCmp(this.ids[i] + "_fields");
			com.setFieldLabel(" 条件" + (i + 1) + "-  属性列表");
		}
		this.num = this.ids.length + 1;// 重置长度
	},
	/**
	 * 删除已经无效的id
	 * 
	 * @param deleteId
	 *            需要在自维护列表中删除的id
	 */
	deleteIdFromIds : function(deleteId) {
		var ids = [];
		Ext.each(this.ids, function(id) {
					if (id != deleteId) {
						ids.push(id);
					}
				});
		this.ids = ids;
	},
	/**
	 * 创建fields store
	 * 
	 * @param config
	 */
	setFieldsStore : function(config) {
		var store = this.createStore({
					fields : ['value', 'display'],
					data : config.data
				});
		this.fieldStore = store;
	},
	/**
	 * 创建fields combox
	 * 
	 * @param config
	 *            配置参数
	 */
	createFieldsCombox : function(config) {
		var me = this;
		var configId = config.configId;// 默认id
		var combox = this.createCombox({
					valueField : 'value',
					displayField : 'display',
					fieldLabel : config.fieldLabel,
					store : this.fieldStore,
					labelWidth : 100,
					id : config.id,
					editable : false,
					width : config.width,
					listeners : {
						'select' : function(combo, records, obj) {
							var dataIndex = records[0].data.value,component,form = this.up("form"),id = configId+ "_remove";
                            form.remove(Ext.getCmp(id));
							Ext.each(me.componentsData, function(c) {
										if (c.name == dataIndex) {
											component = me.createComponent(c,id);
										}
									});
							form.insert(2, component);
							component.setValue("");
						}
					}
				});
		return combox;
	},
	/**
	 * 创建组件 combox,textfield,datefield
	 * 
	 * @param config
	 *            配置参数
	 * @return 一个包含各种组件的对象
	 */
	createComponent : function(item,id) {
		var component;// 声明组件
        //如果是下拉或者时间选择之类的，我考虑选择时触发查询事件。交互起来会更好点。
        var me = this,C = item;
        //var config = this.config;// 配置参数
        var component,basic = {
            name : 'fieldValue',
            xtype : item.xtype,
            codeData : item.codeData,
            id : id,
            itemId : 'remove',
            columnWidth : me.config.secondColumnWidth,
            emptyText : '请输入查询内容...',
            listeners : {
//                specialkey : {
//                    scope : this,
//                    fn : function(textField, e) {
//                        if (e.getKey() == e.ENTER) {
//                            this.fireEvent('doQuery');
//                        }
//                    }
//                }
            }
        };
        if(C.xtype=='textarea'){
        	C = NS.clone(C);//克隆备份
        	C.xtype = 'textfield';
        }
        if(C.xtype=='checkbox'){
        	C = NS.clone(C);
        	C.xtype = 'combobox';
        	C.codeData.data=[{id:'1',mc:'是',dm:'1'},{id:'0',mc:'否',dm:'0'}];
        }
        component = NS.util.FieldCreator.createField(C,basic);
        return component || {};
	},
	/**
	 * 创建operator combox 操作符combox
	 * 
	 * @param config
	 *            创建combox的配置参数
	 * @return Ext.form.field.ComboBox
	 */
	createOperatorCombox : function(config) {
		var combox = this.createDefaultCombox({
					store : this.operatoreStore,
					id : config.id,
					editable : false,
					fieldLabel : '关系',
					width : config.width
				});
		return combox;
	},
	/**
	 * 创建logicalOperator 逻辑操作符combox
	 * 
	 * @param config
	 *            创建combox的配置参数
	 * @return Ext.form.field.ComboBox
	 */
	createLogicalOperatorCombox : function(config) {
		var combox = this.createDefaultCombox({
					store : this.logicalOperatorStore,
					id : config.id,
					editable : false,
					fieldLabel : '连接符',
					width : config.width
				});
		return combox;
	},
	/**
	 * 创建默认combox
	 * 
	 * @param config
	 *            创建combox的配置参数
	 * @return Ext.form.field.ComboBox
	 */
	createDefaultCombox : function(config) {
		var combox = this.createCombox({
					valueField : 'value',
					displayField : 'display',
					fieldLabel : config.fieldLabel,
					queryMode : 'local',
					labelWidth : 45,
					editable : false,
					store : config.store,
					width : config.width,
					id : config.id
				});
		return combox;
	},
	/**
	 * 创建combox
	 * 
	 * @param {Object} config 配置参数
	 */
	createCombox : function(config) {
		var combox = Ext.create('Ext.form.field.ComboBox', config);
		return combox;
	},
	/**
	 * 创建store
	 * 
	 * @param config
	 *            配置参数
	 * @return Ext.data.Store
	 */
	createStore : function(config) {
		var store = Ext.create('Ext.data.Store', config);
		return store;
	},
	/**
	 * 创建操作符store
	 */
	createOperatorStore : function() {
		var store = Ext.create('Ext.data.Store', {
					fields : ['value', 'display'],
					data : [{
								"value" : "=",
								"display" : "等于"
							}, {
								"value" : "like",
								"display" : "相似于"
							}, {
								"value" : "<>",
								"display" : "不等于"
							}, {
								"value" : ">",
								"display" : "大于"
							}, {
								"value" : "<",
								"display" : "小于"
							}, {
								"value" : ">=",
								"display" : "大于等于"
							}, {
								"value" : "<=",
								"display" : "小于等于"
							}]
				});
		this.operatoreStore = store;
	},
	/**
	 * 创建逻辑操作符store
	 */
	createLogicalOperatorStore : function() {
		var store = Ext.create('Ext.data.Store', {
					fields : ['value', 'display'],
					data : [{
								"value" : "and",
								"display" : "并且"
							}, {
								"value" : "or",
								"display" : "或"
							}]
				});
		this.logicalOperatorStore = store;
	}
});
