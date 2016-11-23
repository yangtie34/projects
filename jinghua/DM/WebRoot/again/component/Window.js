/**
 * @class NS.window.Window
 * @extends NS.container.Panel
 * 
 */
NS.define('NS.window.Window',{
	extend:'NS.container.Panel',
	/**
	 * @cfg {String} title 窗口标题
	 */
	/**
	 * @cfg {String/Number} bodyPadding 窗体的边框距
	 */
	/**
	 * @cfg {Boolean} closable 是否可用 默认true
	 */
	/**
	 * @cfg {String} closeAction 窗口上关闭图标的关闭的模型，‘destroy’、‘hide’,分别指销毁、隐藏这个窗口,默认destroy
	 */
    /**
     * @cfg {Boolean} modal 是否模态窗口,默认false
     */
	/**
	 * @cfg {String/Boolean} shadow 是否显示阴影 默认"sides"  
	 */
    /**
     * @cfg {Number} x 窗体元素在窗口上显示的横轴的位置
     */
    /**
     *  @cfg {Number} x 窗体元素在窗口上显示的横轴的位置(以屏幕左上角坐标轴为 原点，向左为横轴，向下为纵轴)
     */
    /**
     *  @cfg {Number} y 窗体元素在窗口上显示的纵轴的位置(以屏幕左上角坐标轴为 原点，向左为横轴，向下为纵轴)
     */
	/**
	 * @private
	 */
	initComponent:function(config){
        config.constrainTo = config.constrainTo||Ext.getBody();
        config.constrain = config.constrain !== undefined ? config.constrain : true;
		this.component = Ext.create('Ext.window.Window',config);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			autoShow:true,
			bodyPadding:true,
			shadow:true,
			buttonAlign:true,
            constrain : true,
			buttons:true,
            tools : true,
            resizable : true,//是否允许改变window的宽度和高度
            modal : true,
			closable:true,
			closeAction:true,
			collapsed:true,
            x : true,
            y : true
		});
	}
});