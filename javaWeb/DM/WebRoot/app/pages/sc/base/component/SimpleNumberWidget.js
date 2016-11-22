/**
 * 数字组件。
 * User: sunweiguang
 * Date: 14-8-6
 * Time: 下午5:46
 */
Ext.define('Exp.component.SimpleNumberWidget',{
    extend:'Ext.Component',
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = '<div style="border: 1px solid #AAA;width:150px;padding:10px 5px 10px 5px;text-align: center; font-weight:bold;border-radius: 3px;display: inline-block;">'+
    	'<div style="color: #FFF;font-size:15px; background-color: '+ this.color+';padding: 3px;border-radius: 3px;">{title}</div>'+
    	'<div style="font-size: 22px;height:55px;line-height:55px;padding:5px; color: '+ this.color+';">{value}</div>'+
    	'<div style="margin: 0 auto;line-height:25px; border-radius:25px;width: 25px;height: 25px;background-color: '+ this.color+';color:#FFF; ">{unit}</div>'+
    	'</div>';
    }
});