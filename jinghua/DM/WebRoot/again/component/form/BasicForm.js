/**
 * @class NS.form.BasicForm
 * @extends NS.container.Container
 * 例如
 *
 *          var layoutData={
                        height:300,// 高度
                        width:300,// 宽度
                        padding : '10 10 10 10',
                        margin : '10 10 10 10',
                        modal:true,// 魔态，值为true是弹出窗口的。
                                                title : '新增Form',
                        items : [{
                                xtype : 'fieldset',
                                columns : 3,
                                colspan : 2,
                                rowspan : 2,
                                padding : '10 10 10 10',
                                margin: '10 10 10 10',
                                groupName : '',
                                border : true,
                                items : [{
                                    xtype : 'entityField',
                                    propertyName : 'xh',
                                    colspan : 2,
                                    rowspan : 2,
                                    width: 2
                                }]
                        }]
                    };
            var form = new NS.form.BasicForm(layoutData);
 */
NS.define('NS.form.BasicForm',{
    extend : 'NS.container.Panel',
    /**
     * @cfg {Boolean} modal 定义Form是否是窗体，能够单独显示
     */
    /**
     * @private
     * @param config
     */
    initComponent : function(config){
    	if(config && config.modal){
            NS.apply(config,{
                closable : true,
                floating : true,
                draggable : true
            });
        }
        this.component = Ext.create('Ext.form.Panel',config);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
        	modal:true
        });
    },
    initEvents:function(){
        this.callParent(arguments);
        /**
         * 针对form本身添加beforeshow事件
         */
        this.addEvents('beforeshow');
    },
    /**
     * 执行beoreshow事件监听
     */
    onBeforeshow:function(){
        this.component.on('beforeshow',function(t, e){
            this.fireEvent('beforeshow', this);
        },this);
    },
    /***
     * 设置Form中所有字段的属性的值
     * @param {Object} data 值集合
     */
    setValues : function(values){
        var vs = this.removeNUSL(values);
        this.component.form.setValues(vs);
    },
    /***
     * 获取form中所所有field的值集合对象
     * @return {Object}
     */
    getValues : function(){
//        return this.removeNUSL(this.component.form.getValues());
        return this.component.form.getValues();
    },
    /***
     * 根据field的name获取一个field对象
     * @param {String} name 组件的name
     * @return {NS.form.field.BaseField}
     */
    getField : function(name){
        var field = this.component.form.findField(name);
        if(NS.isExtObj(field)){
            return NS.util.ComponentInstance.getInstance(field);
        }
    },
    /***
     * 通过field的name属性来查找对应的field对象，并设置其值
     * @param {String} name  field组件的name
     * @param {Object} value 要设置的值
     */
    setFieldValueByName : function(name,value){
        var field = this.component.form.findField(name);
        if(field && (value === false || value)){
            field.setValue(value);
        }
    },
    /**
     * 根据field组件的name，获取该field组件的值
     * @param {String} name  field组件的name
     * @return {Object}
     */
    getFieldValueByName:function(name){
    	var field = this.component.form.findField(name);
        if(field){
            return field.getValue();
        }
        throw 'BasicForm。js:name为'+name+'的组件不存在！';
    },
    /**
     * 移除属性中为“” null undefined 的属性
     * @private
     * @param object
     */
    removeNUSL: function(obj){
        var object = NS.clone(obj);
        for(var i in object){
//            if(object !== false && object != 0 && object[i]!="0" && object[i]!="false" && !object[i]){
//                delete object[i];
//            }
        	if(NS.isString(object[i]) || NS.isNumber(object[i]) || NS.isBoolean(object[i]) || NS.isObject(object[i]) || NS.isArray(object[i])){}
            else{delete object[i];}
//            if(NS.isDefined(object[i]) && object[i]!==null){}else delete object[i];
        }
        return object;
    },
    /**
     * 对{NS.form.BasicForm} 中的field的值进行校验，如果校验不通过，返回false，通过返回true
     * @return {Boolean}
     */
    isValid : function(){
        return this.component.getForm().isValid();
    },
    /**
     * 对{NS.form.BasicForm} 中的字段的值进行重置
     * @return {NS.form.BasicForm}
     */
    reset : function(){
        this.component.getForm().reset();
        return this;
    },
    /**
     * 清空Form中所有Field的值
     */
    clearValues : function(){
        var c = this.component;
        if(c.trackResetOnLoad == true){
            c.trackResetOnLoad = false;//将标志位置为false
            this.reset();
            this.component.trackResetOnLoad = true;
        }else{
            this.reset();
        }

    },
    /**
     * 提交Form
     *
     *      var form =new NS.form.BasicForm();
     *
     *      form.submit({
     *          url : 'sfsfs.action',
     *          params : {},
     *          callback : function(result){
     *
     *          },
     *          timeout : 300000,请求中断时间
     *          scope ： this
     *      });
     * @param {Object} obj obj 参数对象必须包含的属性为url callback,
     *         params 和scope可以根据自己的需要添加
     */
    submit : function(obj){
        var me = this;
        obj = obj || {};
        this.component.submit({
            clientValidation : true,
            url : obj.url,
            params : obj.params,
            timeout : obj.timeout,
            success : function(form,action){
                if(obj.callback){
                    obj.callback.call(obj.scope||me,action.result);
                }
            },
            failure : function(form,action){
                if(obj.callback){
                    obj.callback.call(obj.scope||me,action.result);
                }
            },
            scope : obj.scope || this
        });
    }
});