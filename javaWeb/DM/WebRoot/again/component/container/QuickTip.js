/**
 * 快速提示工具 
 */
NS.define('NS.tip.QuickTip', {
    extend: 'NS.container.Panel',
    /**
     * @cfg {Boolean} hideCollapseTool true隐藏工具条栏
     */
    /**
     * @cfg {Boolean} overlapHeader 
     */
    /**
     * @cfg {Boolean} autoShow　true自动显示
     */
    /**
     * @cfg {Number} dismissDelay 自动展现时间,这个时间段之外组件隐藏
     */
    /**
     * @cfg {Number} hideDelay 隐藏延时
     */
    initComponent:function(cfg){
    	var basic = {
            width: 200,
            autoShow:true,
            shadow: false,
            bodyBorder: false,
            frameHeader: false,
            hideCollapseTool: true,
            overlapHeader: true,
            bodyBorder: false
        };
    	NS.apply(basic,cfg);
    	this.component = Ext.create('Ext.tip.QuickTip',basic);
    },
    initConfigMapping:function(){
    	this.callParent();
    	this.addConfigMapping({
    		hideCollapseTool:true,
    		overlapHeader:true,
    		autoShow:true,
    		dismissDelay:true,
    		hideDelay:true
    	});
    }
    //其他方法均基础于panel
});