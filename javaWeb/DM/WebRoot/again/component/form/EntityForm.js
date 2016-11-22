/**
 * @class NS.form.EntityForm
 * @extends NS.Base
 * @singleton
 *
 *      创建具体实体form组件
 *
        var form =  NS.form.EntityForm;
        var form = form.create({
                    data : data,
                    autoScroll : true,
                    columns : 2,
                    margin : '5px',
                    autoShow : true,
                    modal:true,// 模态，值为true是弹出窗口的。
                  //items : ['id','xh','xm','yyxId','yzy','ybjId','ynj',
                  //  'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'],
                    items : [
                        {
                            xtype : 'fieldset',
                            columns : 2,
                            title : '分组1',
                            height : 250,
                            items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                        },
                        {xtype : 'fieldset',
                            title : '分组2',
                            columns : 1,
                            items : [
                                'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                            ]},
                        {xtype : 'fieldset',
                            title : '分组2',
                            columns : 3,
                            colspan : 2,
                            items : [
                                'yzy','ybjId','ynj'
                            ]}
                    ]
                });
 */
NS.define('NS.form.EntityForm',{
    singleton : true,
    /**
     * 构造函数
     * @param {Object} config 配置对象
     * @private
     */
    constructor : function(){
        this.callParent(arguments);
    },
    /**
     * 创建具体实体form组件,如以下代码所示。
     * 在较为复杂的情况下对Form进行布局的时候，如果某个field直接按照后台实体属性表的形式来的话，那么只需要写上属性名即可，
     * 如果需要做特殊配置的话，就以配置对象的形式走。
     *
         var form =  NS.form.EntityForm;
         var form = form.create({
                        data : data,
                        autoScroll : true,
                        columns : 2,
                        margin : '5px',
                        autoShow : true,
                        fieldConfig : {width : 200,height:200},//默认全局Field配置属性
                        modal:true,// 模态，值为true是弹出窗口的。
                      //items : ['id','xh','xm','yyxId','yzy','ybjId','ynj',
                      //'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'],
                        items : [
                            {
                                xtype : 'fieldset',
                                columns : 2,
                                title : '分组1',
                                height : 250,
                                items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                            },
                            {xtype : 'fieldset',
                                title : '分组2',
                                columns : 1,
                                items : [
                                    'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                                ]},
                            {xtype : 'fieldset',
                                title : '分组2',
                                columns : 3,
                                colspan : 2,
                                items : [
                                    'yzy','ybjId','ynj'
                                ]}
                        ]
                    });

     * @param {Object} config 创建BasicForm的配置对象
     * @return {NS.form.BasicForm}
     */
    create : function(config){
        this.fieldConfig = config.fieldConfig || {};
        this.formType = config.formType;//待创建Form类型（新增，修改，查看）
        this.fields = [];
        this.cascade = {};//级联集合
        this.initFieldsConfig(config);
        this.processItems(config);
        this.processCascade(this.fields);
        var layout = config.layout;
        NS.applyIf(config,{
                            layout : {type : 'table',columns : config.columns||2},
                            frame : true
                          });
        if(layout)config.layout = layout;

        if(config.modal){
            NS.apply(config,{
                closable : true,
                closeAction : config.closeAction||'destroy',
                floating : true,
                shadow : false,
                //trackResetOnLoad : true,//当为true时,reset方法是还原到之前一次的默认值,默认false
                border : false,
                frame : true,
                bodyBorder : false
            });
        }
        delete this.formType;
        delete this.fieldConfig;
        var form = new Ext.form.Panel(config);
        var bform = NS.util.ComponentInstance.getInstance(form);
        if(config.values){
//          bform.setValues(config.values);
            bform.setValues(config.values);
        }
        return bform;
    },
    /**
     * 初始化fields 配置
     * @param {Object} config 配置参数
     * @private
     */
    initFieldsConfig : function(config){
        var data = config.data;
        var map = this.fcMap = {};
        for(var i= 0,len = data.length;i<len;i++){
            var item = data[i];
            map[item.name] = item;
        }
    },
    /**
     * 创建Fields对象
     * @param config
     * @private
     */
    processItems : function(config){
        var me = this,
            items = config.items||[],
            createField = function(){
                return me.createField.apply(me,arguments);
            },
            item,
            name = null;
        if(items.length !== 0){
            for(var i= 0,len = items.length;i<len;i++){
                item = items[i];
                if(NS.isNSComponent(item)){
                    items[i] = item.component;//属性不变
                }else if(item.xtype === 'fieldset'){
                    items[i] = this.createFieldSet(item);
                }else{
                    if(NS.isString(item)){
                        name = item;
                        item = this.getFieldConfig(name);
                    }else if(item && NS.isObject(item)){
                        name = item.name;
                        var c = this.getFieldConfig(name);
                        NS.apply(c,item);
                        item = c;
                        if(item.readOnly == true){ //额外补丁处理--如果配置属性readOnly为true，将对应字段置为灰色
                                item['fieldStyle'] = "background:#E6E6E6;";
                        }
                    }
                    items[i] = createField(this.fcMap[name],item);
                }
            }
        }else{
            config.items = this.getDefaultItems(config);
        }
    },
    /**
     * 获取默认的field集合
     * @private
     * @return {Array}
     */
    getDefaultItems : function(config){
        var me = this,
            data = config.data,array = [],item,
            createField = function(){
                return me.createField.apply(me,arguments);
            };
        for(var i= 0,len=data.length;i<len;i++){
            item = data[i];
            array.push(createField(item,this.getFieldConfig(item.name)));
        }
        return array;
    },
    /**
     * 创建Fieldset容器
     * @param {Object} config 创建fieldset 的配置对象
     * @private
     */
    createFieldSet : function(config){
        var me = this,
            items = config.items||[],
//            createField = NS.Function.alias(NS.util.FieldCreator,'createField'),
            createField = function(){
                return me.createField.apply(me,arguments);
            },
            item,
            name = null;
        for(var i= 0,len = items.length;i<len;i++){
            item = items[i];
            if(item.xtype === 'fieldset'){
                items[i] = this.createFieldSet(item);
            }else if(NS.isNSComponent(item)){
                items[i] = item.component;
            }else{
                if(NS.isString(item)){
                    name = item;
                    item = this.getFieldConfig(name);
                }else if(item && NS.isObject(item)){
                    name = item.name;
                    var c = this.getFieldConfig(name);
                    NS.apply(c,item);
                    item = c;
                    if(item.readOnly == true){ //额外补丁处理--如果配置属性readOnly为true，将对应字段置为灰色
                        item['fieldStyle'] = "background:#E6E6E6;";
                    }
                }
                items[i] = createField(this.fcMap[name],item);
            }
        }
        NS.applyIf(config,{layout : {type : 'table',columns : config.columns||2}});
        delete config.columns;
        return new Ext.form.FieldSet(config);
    },
    /**
     * 创建组件的代理，处理其他额外的事情
     * @private
     * @return {Object}
     */
    createField : function(C,config){
        var fields = this.fields,//field存储集合
            cascade = this.cascade;//级联集合

        if(C.xtype == "combobox" && config.editable == true){
            config.listeners  = {
                blur : function(com){
                    var value = com.getValue();
                    var rawValue = com.getRawValue();
                    if(value == rawValue)com.setValue();
                }
            }
        }
        var f = NS.util.FieldCreator.createField.apply(NS.util.FieldCreator,arguments);
        fields.push(f);
        var fl = f.fl;
        if(cascade[fl] && f.bmcc){
            cascade[fl].push(f);
        }else if(!cascade[fl] && f.bmcc){
            cascade[fl] = [];
            cascade[fl].push(f);
        }
        return f;
    },
    /**
     * 通过name来获取field的配置参数
     * @private
     * @param {String} name 组件对应的name
     * @private
     */
    getFieldConfig : function(name){
        var C = this.fcMap[name]||{};
        if(!C){
            NS.error({
                sourceClass : 'NS.form.EntityForm',
                sourceMethod : 'getFieldConfig',
                msg : 'name对应的组件没有查找到：name为'+name
            });
        };
//        var xtype = C.xtype;
        var basic = {
            width : 270,
            labelWidth : 70,
            fieldLabel : C.nameCh,// 属性字段标签
            maxLength : C.dbLength
            },
            fieldConfig = this.fieldConfig||{};
        NS.apply(basic,fieldConfig);

        if(!C.isBlank){
            basic['labelSeparator'] = "<span >[</span><span style='color:red;'>*</span><span>]:</span>";
        }
        if(C.editable == false){
            basic['readOnly'] = true;
            basic['fieldStyle'] = "background:#E6E6E6;";
        }
        NS.apply(basic,this.getFieldConfigByFormType(C));//获取不同类型的Form的字段的配置参数
        return basic;
    },
    /***
     * 获取不同类型Form的Field配置参数
     * @private
     */
    getFieldConfigByFormType : function(config){
        var type = this.formType,basic = {};
        switch (type){
            case 'add' : {
                NS.apply(basic,{
                    hidden : config.isAddField == 1 ? false : true// 如果修改字段属性为0
                });
                break;
            }
            case 'update' : {
                NS.apply(basic,{
                    hidden : config.isUpdateField == 1 ? false : true// 如果修改字段属性为0
                });
                break;
            }
            case 'see'  : {
                NS.apply(basic,{
                    readOnly : true,
                    fieldStyle : 'background:#E6E6E6;'
                });
            }
        }
        return basic;
    },
    /**
     * 处理实体Form的级联问题
     * @private
     */
    processCascade : function(){
        var  cascade = this.cascade,map = cascade;
        for (var i in map) {//将级联分组内的组件按照层次进行排序
            if (i && map[i] instanceof Array) {
                map[i].sort(function(a, b) {
                    if (a.bmcc > b.bmcc) {
                        return 1;
                    }
                    if (a.bmcc < b.bmcc) {
                        return -1;
                    }
                    return 0;
                });
            }
        }
        for (var i in map) {
            if (i && map[i] instanceof Array) {
                this.associate(map[i]);
            }
        }
    },
    /**
     * 使得组件之间产生事件关联
     * @private
     */
    associate : function(array){
        var item;
        for(var i= 0;i<array.length;i++){
            if (i == array.length - 1) {
                return;
            }
            (function(array,i,me){
                  var com1 = array[i],com2 = array[i+1];
                      com1["nextSelectCore"] = com2;
                  com1.on('select',function(combox, records){
                      var id = records[0].get('id'),next = com2;
                      com2.getStore().clearFilter();
                      com2.getStore().filter('pid',id);
                      com2.setValue();
                      while(next){
                          next.setValue();
                          next = next.nextSelectCore;
                      }
                  });
                  com2.on('expand',function(combox, records){
                      if(com1.getValue()){
                            com2.getStore().clearFilter();
                            com2.getStore().filter(com2.associateField||'pid',com1.getValue());
                      }
                      return true;
                  });
            })(array,i,this);
        }
    }
});