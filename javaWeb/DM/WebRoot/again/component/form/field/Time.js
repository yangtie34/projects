/**
 * @class NS.form.field.Time
 * @extends NS.form.field.BaseField
 */
NS.define('NS.form.field.Time',{
    extend : 'NS.form.field.BaseField',
    initComponent : function(config){
        var basic = {
            format :"G时i分",
            submitFormat:"G:i"
        };
        NS.apply(basic,config);
        this.component = Ext.create('Ext.form.field.Time',basic);
    },
    setMinValue:function(value){
        this.component.setMinValue(value);
    },
    setMaxValue:function(value){
        this.component.setMaxValue(value);
    }
});