/**
 * @class NS.toolbar.EntityToolbar
 * @extend NS.Base
 *  实体Toolbar ，用于根据实体属性表中的组件来创建tbar工具栏
 */
NS.define('NS.toolbar.EntityToolbar',{
    singleton : true,
    /**
     * var util = NS.toolbar.EntityToolbar;
     * var toolbar = util.create({
     *     data : header,
     *     items : ['xn','xq',new NS.form.field.Text({name : 张三})]
     * });
     * var xn = toolbar.queryComponentByName(xn);
     * xn.on('select',function(){
     *
     * });
     * @param config
     */
    create : function(config){
        var data = config.data;
        this.initFieldsConfig(config);
        var items = this.processItems(config);
        var basic = {
            items : items
        }
        var toolbar = new NS.toolbar.Toolbar(basic);
        return toolbar;
    },
    processItems : function(config){
        var items = config.items||[],array = [],item,
            createField = NS.Function.alias(NS.util.FieldCreator,'createField'),basic,component;
        for(var i = 0,len=items.length;i<len;i++){
            item = items[i];
            if(NS.isString(item)){
               basic = this.getFieldConfig(item);
               component = createField(this.fcMap[item],basic);
            }else if(NS.isObject(item)){
               component = item;
            }else if(NS.isNSComponent(item)){
               component = item.getLibComponent();
            }
            array.push(component);
        }
        return array;
    },
    /**
     * 初始化fields配置
     * @param config
     * @private
     */
    initFieldsConfig : function(config){
        var data = config.data
        var map = this.fcMap = {};
        for(var i= 0,len = data.length;i<len;i++){
            var item = data[i];
            map[item.name] = item;
        }
    },
    /**
     * 通过name来获取field的配置参数
     * @param {String} name 组件对应的name
     * @private
     */
    getFieldConfig : function(name){
        var C = this.fcMap[name],xtype = C.xtype;
        if(!C){
            NS.error({
                sourceClass : 'NS.toolbar.EntityToolbar',
                sourceMethod : 'getFieldConfig',
                msg : 'name对应的组件没有查找到：name为'+name
            });
        }
        var basic = {
            width : 200,
            labelWidth : 60,
            fieldLabel : C.nameCh,// 属性字段标签
            maxLength : C.dbLength,
            readOnly : !C.editable//是否可编辑1 可编辑 0 不可编辑
        };
        return basic;
    }
});