/**
 * 数字组件。
 * User: sunweiguang
 * Date: 14-8-6
 * Time: 下午5:46
 */
Ext.define('Exp.component.SimpleNumberWidget2',{
    extend:'Ext.Component',
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = "{title}<br><br><b style='font-size:20px'>{value}&nbsp;</b>{unit}";
    },
	padding:"5px 20px",
	data : {},
	style : {
		"border-left" :"1px solid #b8bab9",
		"font-size" : "13px",
		"font-family" : "微软雅黑",
		
	}
});