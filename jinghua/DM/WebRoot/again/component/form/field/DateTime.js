/**\
 * 日期组件，可以精确到时分秒
 * var datetime = new Ext.form.field.DateTime({
         format : 'Y-m-d H:i:s',
         width : 300,
         labelWidth:60,
         fieldLabel : '发送时间'
     });
 */
NS.define('NS.form.field.DateTime',{
    extend : 'NS.form.field.Date',
    initComponent : function(config){
        this.component = new  Ext.form.field.DateTime(config);
    }
});