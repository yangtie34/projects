/**
 * 图片和内容组件
 * User: Sunwg
 * Date: 14-8-18
 * Time: 下午9:00
 */
Ext.define('Exp.chart.PicAndInfo',{
    extend : 'Ext.Component',
    margin:0,
    initComponent : function(){
        this.callParent(arguments);
        this.tpl = "<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>"+this.title+"</span>" +
		"<hr style='margin-top: 5px;' color='#5299eb'></div>" ;
        if(!this.onlyTitle){
        	this.tpl += "<div >	<table style='width: 100%;'><tr> <td width='130px'><img alt='"+ this.title +"' src='"+this.picurl+"' width='120px' height='100px'/></td>" +
        		"<td><div style='background-color: #e3f4fb;height: 80px;width: 100%;padding : 5px;'>"+ this.tplHtml +"</div></td></tr></table></div>";
        }
    },
    data : {
    	
    },
    onlyTitle : false
});