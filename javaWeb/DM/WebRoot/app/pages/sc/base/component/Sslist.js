/**
 * 宿舍列表
 * User: sunwg
 * Date: 14-9-17
 * Time: 15:33
 *
 */
Ext.define('Exp.component.Sslist',{
	extend : 'Ext.Component',
    height : 700,
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = '<div id="'+this.id +'_container" style="width: 210px;height: '+this.height+'px;border: 1px solid #AAA;border-radius:10px;overflow-y: auto;padding: 10px;">'+
        '<tpl for=".">' + 
        '<div style="width: 180px;height:100px;border:1px solid #AAA;border-radius:5px;margin-bottom: 10px;padding: 5px;line-height: 30px;background-color: #f5f5f5;">'+
        '<div style="font-weight: bold;">{name}</div><div>共{cs}层，房间数 {fjs} 间</div><div>床位数 {cws} 个&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" ssid="{ssid}" value="详情" '+
        ' style="width: 50px;height: 26px;border-radius :13px;"/></div></div></tpl></div>';
    },
    listeners:{
        select : function(){}
    },
    afterRender : function(){
    	this.bindEvent();
    },
    bindEvent : function(){
    	var me = this;
    	$("#" + me.id).click(function(event){
    		if(event.target.tagName == "INPUT"){
	    		var tar = $(event.target);
	    		tar.parent().parent().siblings().css({
	    			'background-color' : '#f5f5f5',
	    			'color' : '#000'
	    		});
	    		tar.parent().parent().css({
	    			'background-color' : '#009865',
	    			'color' : '#FFF'
	    		});
	    		me.value = tar.attr('ssid');
	    		me.fireEvent('select',tar.attr('ssid'),$(tar.parent().siblings()[0]).html());
    		}
    	});
    },
    data : [],
    getValue : function(){
    	return this.value;
    }
});