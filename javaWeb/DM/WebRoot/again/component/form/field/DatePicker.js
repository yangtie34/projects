NS.define('NS.form.field.DatePicker',{
    extend : 'NS.form.field.BaseField',
    /**
     * @cfg {String} format 可以覆盖默认的日期格式字符串的本地化支持(Y-m-d H:i:s)。格式必须是有效的根据Ext.Date.parse。 默认是：Y-m-d
     */
    /**
     * @cfg {String/Date} maxValue 最大允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。
     */
    /**
     * @cfg {String/Date} minValue 最小允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。
     */
    initComponent : function(cfg){
    	var value = cfg.value;
    	delete cfg.value;
        this.component = Ext.create('Ext.picker.Date', cfg);
        if(value)this.setValue(value);
    },
    /**
     * @private
     */
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({
            format:true,
            maxValue:function(value,property,config){
                config['maxDate'] = value;
                delete config.maxValue;
            },
            minValue:function(value,property,config){
                config['minDate'] = value;
                delete config.minValue;
            }
        });
    },
    /**
     * 获取Picker的值
     * @returns {String|*}
     */
    getValue : function(){
        return Ext.Date.format(this.component.getValue(),this.component.format || 'Y-m-d');
    },
    /**
     * 设置picker的值
     * @param v {String}  设置的picker的值
     */
    setValue : function(v){
        if(NS.isDate(v)){
            this.component.setValue(v);
        }else if(NS.isString(v)){
            this.component.setValue(Ext.Date.parse(v,this.component.format||'Y-m-d'));
        }
    }
});