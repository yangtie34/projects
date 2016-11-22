/**
 * 文本组件
 * User: sunweiguang
 * Date: 14-8-6
 * Time: 下午5:46
 * 
 * 	data : {
 * 		title : "",
 * 		texts : [{text : ""},{text : ""},{text : ""}]
 * }
 */
Ext.define('Exp.component.MetroTextWidget',{
    extend:'Ext.Component',
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = "<div style='height:240px;width:320px;font-family:微软雅黑; color:#fff;white-space:nowrap; background-color:"+ this.color +";padding:10px;'>" +
        		"<div style='font-size:26px;margin-bottom: 5px; '>{title}</div>	" +
        		"<tpl for='texts'><tpl if='xindex &lt;= 4'>" +
        		"  <div style='width:140px;height: 100px;font-size: 18px;float: left;'>{text}</div>	" +
        		"</tpl>	</tpl><div style='clear: both;'></div></div>";
    },
    color : "#6599ff",
    data : {}
});