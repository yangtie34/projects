/**
 * @class NS.grid.query.SingleFieldQuery
 * @extends NS.Component
 *  单字段查询组件,包含查询字段下拉表和字段值两部分。 字段值组件可以根据查询字段下拉表中的字段类型显示不同的组件。
 *              目前字段值组件只有3种：输入框、下拉框、日期组件。
 *             var single = new NS.grid.query.SingleFieldQuery(config);
 */
NS.define('NS.grid.query.SingleFieldQuery', {
    extend: 'NS.Component',
    /**
     *  初始化构造函数
     */
    constructor: function (config) {
        this.initData.apply(this, arguments);// 初始化数据
        this.initComponent();//初始化组件
    },
    /***
     * 注册下将要关联的grid
     * @param grid
     */
    registerGrid: function (grid) {
        this.grid = grid;
    },
    /**
     * 清空查询组件在grid的参数
     * @private
     */
    clear: function () {
        this.field.setValue();
    },
    /**
     *  配置组件数据源
     * @param config
     *            需要配置的数据源
     */
    initData: function (config) {
        var tranData = [], i = 0, item, data = config.data,
            obj = {// 配置参数
                compWidth: 300,
                firstColumnWidth: .4,
                secondColumnWidth: .6
            };
        this.fieldsConfig = config.fieldsConfig;
        this.config = config;

        NS.apply(config, obj);// 合并参数对象

        for (i, len = data.length; i < len; i++) {
            item = data[i];
            if (item.isQuery) {
                tranData.push(item);
            }
        }
        this.tranData = tranData;
        this.registerGrid(config.grid);
    },
    /**
     *  初始化组件
     * @param config 配置参数
     */
    initComponent: function () {

        var config = this.config;
        this.component = this.createFieldSet(config);// 创建fieldset
        var fieldCombox = this.createFieldComboBox();// 创建属性列表combox
        this.component.add(fieldCombox);// 将属性列表combox添加进fieldset中
        this.setDefaultComponent();
        this.setQueryButton();
    },
    setQueryButton : function(){
        var me = this;
        var button = new Ext.button.Button({
            text : '查询',
            iconCls : 'ns-btn-search',//背景图片
            margin : '1 0 0 4',
            frame : true,
            handler :function(){
                me.grid.load(me.getParams());
            }
        });
        button.ui = button.ui+'-toolbar';//针对toolbar的特殊情况修正
        this.component.add(button);
    },
    /**
     *  获取查询参数
     * @return Object 查询参数
     */
    getParams: function () {
        var field = this.fieldCombox.getValue();// 属性名
        var type = this.field.xtype;// 组件类型
        var com = this.field;
        var obj = {};
        switch (type) {
            case 'textfield' :
                obj[field + ".like"] = com.getValue();
                break;
            case 'combobox' :
                if(com.getValue()==com.getRawValue()){//不做处理
                }else{
                	obj[field] = com.getValue();
                }
                break;
            case 'datefield' :
                obj[field] = com.getRawValue();
                break;
            case 'timefield' :
                obj[field] = com.getRawValue();
                break;
            case 'monthfield' :
                obj[field] = com.getRawValue();
                break;
            case 'numberfield' :
            	obj[field] = com.getRawValue();
            	break;
            case 'textarea' :
            	obj[field+'.like'] = com.getValue();
            	break;
            case 'checkbox' ://对checkbox做特殊处理
            	obj[field] = com.getValue();//单字段中已更改,不需做特殊处理==true?'1':'0';
            	break;
            case 'treecombobox' :
            	obj[field] = com.getValue();break;
            default :
                obj[field] = com.getValue();
                break;
        }
        if(obj[field] == null){
            delete obj[field];
        }
        return obj;
    },
    /**
     *  获取默认的组件组件，默认为第一个下拉属性对应的组件
     */
    setDefaultComponent: function () {
        //如果不配置查询数据的话，该处会报错
        var me = this,
            fieldStore = this.fieldStore,
            item = this.tranData[0];
        this.fieldCombox.setValue(item.name);
        this.addComponentForFieldSet(item);
    },
    /**
     *  获得fieldset
     * @return Ext.form.FieldSet
     */
    getFieldSet: function () {
        return this.fieldSet;
    },
    /**
     *  创建fieldset
     * @param config
     *            配置参数
     */
    createFieldSet: function (config) {
        var fieldSet = Ext.create('Ext.container.Container', {
            layout: 'column',
            padding: 0,
            frame : true,
//            margin: '0 0 0 0',
            width: config.compWidth,
            bubbleEvents: ['add'],//移除默认冒泡事件remove
            height: 22,
            border: 0
        });
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {
            Ext.apply(fieldSet, {
                baseCls: '{border:0px solid #B5B8C8;}'
            });
        }
        return fieldSet;
    },
    /**
     *  创建属性列表combox
     *  @return Ext.form.field.ComboBox
     */
    createFieldComboBox: function () {
        var me = this;
        var config = this.config;// 配置参数
        var store = this.createFieldStore();// 创建store
        var combox = this.fieldCombox = Ext.create('Ext.form.field.ComboBox', {// 创建combobox
            store: store,
            valueField: 'value',// 实际值
            displayField: 'display',// 显示值
            editable: false,//不可编辑
            columnWidth: config.firstColumnWidth,
            border: 0,
            listeners: {
                'select': {
                    scope: this,
                    fn: function (combo, records) {
                        var name = records[0].data.value;
                        var componentData;
                        Ext.each(this.tranData, function (component) {
                            if (component.name == name) {
                                componentData = component;// 数据相同
                            }
                        });
                        this.componentData = componentData;// 组件数据
                        this.addComponentForFieldSet(componentData);// 为fieldset添加组件
                    }
                }
            }
        });
        return combox;
    },
    /**
     *  创建属性列表store
     * @return Ext.data.Store
     */
    createFieldStore: function () {
        var tranData = this.tranData,// 转换后的数据
            fieldsData = this.fieldsData = [],
            item;
        for (var i = 0, len = tranData.length; i < len; i++) {
            item = tranData[i];
            if (item.isQuery) {
                fieldsData.push({value: item.name, display: item.nameCh});
            }
        }
        var store = this.fieldStore = Ext.create('Ext.data.Store', {
            fields: [ 'value', 'display' ],
            data: fieldsData
        });
        return store;
    },
    /**
     *  为fieldset添加组件
     * @param componentData
     *            创建组件的数据
     */
    addComponentForFieldSet: function (componentData) {
        var me = this;
        var field = me.createComponent(componentData);
        var fieldSet = this.component;
        if (fieldSet.items.length == 3) {
            fieldSet.remove(me.field);
            fieldSet.insert(1,field);
        } else {
        	var xtype = componentData.xtype; 
            if (xtype == 'checkbox') {
                fieldSet.add(field);
            }else{
            	fieldSet.add(field);
            }//..有待陆续维护
        }
    },
    /**
     *  根据属性列表的选择创建对应的组件
     * @param componentData
     *            组件数据
     * @return Array 组件数组
     */
    createComponent: function (item) {
        //如果是下拉或者时间选择之类的，我考虑选择时触发查询事件。交互起来会更好点。
        var me = this, C = item;
        //var config = this.config;// 配置参数
        var component, basic = {
            name: 'fieldValue',
            itemId: 'remove',
            editable : true,
            columnWidth: me.config.secondColumnWidth,
            listeners: {
                specialkey: {
                    scope: this,
                    fn: function (textField, e) {
                        if (e.getKey() == e.ENTER) {
                            //this.fireEvent('doQuery');//直接调用这个事件也可以,目前因这个事件有问题，暂代替
                        	var params = this.getParams();
                        	if(NS.Object.getSize(params)==0){return;}
                        	this.grid.load(params);
                        }
                    }
                }
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
        var fieldConfig = this.getComponentConfig(item);
        NS.apply(basic,fieldConfig);
        this.field = component = NS.util.FieldCreator.createField(C,basic);
        
        if(C.xtype=="treecombobox"){
			component.on('treeselect',function(){
				this.grid.load(this.getParams());
			},this);
		}else{
			//这个仅仅针对下拉起作用//下拉之类的应有change或者select事件
		   component.on('select',function(){
	             this.grid.load(this.getParams());
           },this);
		}
        delete component.maxLength;//删除最大长度提示信息,因为查询不需要做严格校验
        delete component.regex;//删除正则表达式校验,因为查询不需要做严格校验
        delete component.regexText;//删除正则表达式校验提示信息,因为查询不需要做严格校验
        return component || {};
    },
    getComponentConfig: function(cdata){
        var sx = cdata.name, i =0,item,
            fieldsConfig = this.fieldsConfig ||{},
            len = fieldsConfig.length;
        for(;i<len;i++){
            item = fieldsConfig[i];
            if(item.name == sx){
               return item;
            }
        }
    }
});