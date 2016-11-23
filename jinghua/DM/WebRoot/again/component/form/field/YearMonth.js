/**
 * @class NS.form.field.YearMonth
 * @extends NS.form.field.BaseField
 * 年月组件
 */
NS.define('NS.form.field.YearMonth', {
    extend : 'NS.form.field.BaseField',
    initComponent:function(cfg){
        var basic = {
            editable:false//默认该组件不可编辑
        };
        NS.apply(basic,cfg);
        this.component = Ext.create('Ext.ux.form.MonthField',basic);
    },
    /**
     * 设置值
     * @param value
     */
    setValue:function(value){
        //设置值的时候,将value转换为format格式的,放置其他格式数据产生
        value = Ext.Date.dateFormat(new Date(value), this.component.format);
        this.component.setValue(value);
    }
});