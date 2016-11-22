/**
 * @class NS.window.MessageBox
 * @extends NS.window.Window 消息框
 */
NS.define('NS.window.MessageBox', {
	extend : 'NS.window.Window',
	/**
     * {property} 按钮配置属性,为'确定'
     * @type Number
     */
    OK : Ext.Msg.OK,
    /**
     * {property}按钮配置属性,为'是'
     * @type Number
     */
    YES : Ext.Msg.YES,
    /**
     * {property} 按钮配置属性,为'否'
     * @type Number
     */
    NO : Ext.Msg.NO,
    /**
     * {property}按钮配置属性,为'取消'
     * @type Number
     */
    CANCEL : Ext.Msg.CANCEL,
    /**
     * {property}按钮配置属性,分别为'确定','取消'
     * @type Number
     */
    OKCANCEL : Ext.Msg.OKCANCEL,
    /**
     * {property}按钮配置属性,分别为'是','否'
     * @type Number
     */
    YESNO : Ext.Msg.YESNO,
    /**
     * {property} 按钮配置属性,分别为'是','否','取消'
     * @type Number
     */
    YESNOCANCEL : Ext.Msg.YESNOCANCEL,
    /**
     * {property} 图标ICON配置属性,为'消息图标'
     * @type String
     */
    INFO : Ext.Msg.INFO,
    /**
     * {property} 图标ICON配置属性,为'警告图标'
     * @type String
     */
    WARNING : Ext.Msg.WARNING,
    /**
     * {property}图标ICON配置属性,为'疑问图标'
     * @type String
     */
    QUESTION : Ext.Msg.QUESTION,
    /**
     * {property}图标ICON配置属性,为'错误图标'
     * @type String
     */
    ERROR : Ext.Msg.ERROR,
    /**
     * @cfg {Boolean} autoShow 是否创建即自动显示，默认false
     */
    /**
     * @cfg {Object/Object[]} buttons 放置的按钮对象集合，一般系统默认可满足，不需要额外更改
     */
    /**
     * @cfg {String} buttonAlign 按钮显示位置，有以下几个可选项: 'right', 'left' 和 'center' 
     */
    /**
     * @cfg {Boolean} closable 是否可关闭，默认true
     */
    /**
     * @cfg {String} closeAction 关闭模式：'destroy','hide',默认destroy
     */
    /**
     * @cfg {Boolean} hidden 隐藏属性 默认 false
     */
    /**
     * @cfg {Number} height 组件高度
     */
    /**
     * @cfg {String/Object} html  html元素内的内容，默认""
     */
    /**
     * @cfg {String} icon 图标(这里值配置属性内的值范围)
     */
    /**
     * @cfg {String} iconCls 图标样式 
     */
    /**
     * @cfg {String} title 组件的标题
     */
    /**
     * @cfg {String} msg 组件的信息提示内容
     */
    /**
     * @cfg {Number} width 组件宽度
     */
    /**
     * @private
     */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			autoShow:true,
			buttons:true,
			buttonAlign:true,
			closable:true,
			closeAction:true,
			hidden:true,
			height:true,
			html:true,
			icon:true,
			iconCls:true,
			title:true,
			msg:true,
			listener:true,
			width:true
		});
	},
	/**
	 * @private
	 * @param {Object} cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.window.MessageBox', cfg);
	},
	/**
	 * 显示消息框
	 * @param {Object} cfg 配置参数对象
	 * @return 返回这个ext对象本身
	 */
	show : function(cfg) {
		this.component.show(cfg);
		return this.component;
	},
	/**
	 * 提示框
	 * @param {String/Object} cfg 配置对象或者是标题
	 * @param {String} msg 信息内容
	 * @param {Function} fn 回调函数
	 * @param {Object} scope 作用对象
	 * @return 返回ext这个对象本身
	 */
	alert : function(cfg, msg, fn, scope) {
		return this.component.alert(cfg, msg, fn, scope);
	},
	/**
	 * 选择框
	 * @param {String/Object}cfg 配置对象或者是标题
	 * @param {String} msg 信息内容
	 * @param {Function} fn 回调函数
	 * @param {Object} scope 作用对象
	 * @return 返回ext这个对象本身
	 */
	confirm : function(cfg, msg, fn, scope) {
		return this.component.confirm(cfg, msg, fn, scope);
	},
	/**
	 * 输入提示框
	 * @param {Object/String} cfg 配置对象或者是标题
	 * @param {String} msg 信息内容
	 * @param {Function} fn 回调函数
	 * @param {Object} scope 作用对象
	 * @param {Boolean/Number} multiline 默认false,为true时表示创建一个多文本输入框对象,为数字时则表示创建number行的多文本对象
	 * @param {String} value 获取输入的值
	 * @return 返回ext这个对象本身
	 */
	prompt : function(cfg, msg, fn, scope, multiline, value) {
		return this.component.prompt(cfg, msg, fn, scope, multiline, value);
	},
	/**
	 * 组件隐藏方法
	 */
	hide:function(){
		this.component.hide();
	},
	/**
	 * 关闭组件方法
	 */
	close:function(){
		this.component.close();
	},
	/**
	 * 信息提示框,扩展方法，相对于alert,添加了样式,默认提示信息
     * @param {String/Object} info 字符串信息或者配置对象
	 */
	info:function(){
		var cfg = arguments[0];
		if(NS.isObject(cfg)){
			cfg.title = cfg.title || '信息提示';
			cfg.icon = cfg.icon || this.INFO;
			cfg.buttons = cfg.buttons || this.OK;
		}else if(NS.isString(cfg)){
			var len = arguments.length;
			if(len==1){
				cfg = {};
				cfg.msg = arguments[0];
			}else if(len==2){
				cfg = {};
				cfg.title = arguments[0];
				cfg.msg = arguments[1];
			}else{
				throw 'NS.Msg.info() : length is not valid!';
			}
			cfg.title = cfg.title || '信息提示';
			cfg.icon = cfg.icon || this.INFO;
			cfg.buttons = cfg.buttons || this.OK;
		}else{
			throw 'NS.Msg.info() : type is not valid !';
		}
		return this.show(cfg);
	},
	/**
	 * 警告提示框，扩展方法,默认了警告提示框的样式、按钮
	 * @param {Object} cfg 提示框配置信息
	 * @return Ext组件自身
	 */
	warning:function(){
		var cfg = arguments[0];
		if(NS.isObject(cfg)){
			cfg.title = cfg.title || '警告提示';
			cfg.icon = cfg.icon || this.WARNING;
			cfg.buttons = cfg.buttons || this.OK;
		}else if(NS.isString(cfg)){
			var len = arguments.length;
			if(len==1){
				cfg = {};
				cfg.msg = arguments[0];
			}else if(len==2){
				cfg = {};
				cfg.title = arguments[0];
				cfg.msg = arguments[1];
			}else{
				throw 'NS.Msg.warning() : length is not valid!';
			}
			cfg.title = cfg.title || '警告提示';
			cfg.icon = cfg.icon || this.WARNING;
			cfg.buttons = cfg.buttons || this.OK;
		}else{
			throw 'NS.Msg.warning() : type is not valid !';
		}
		return this.show(cfg);
	},
	/**
	 * 错误提示框，扩展方法,默认了错误提示框的样式、按钮
	 * @param {Object} cfg 提示框配置信息
	 * @return Ext组件自身
	 */
	error:function(){
		var cfg = arguments[0];
		if(NS.isObject(cfg)){
			cfg.title = cfg.title || '错误提示';
			cfg.icon = cfg.icon || this.ERROR;
			cfg.buttons = cfg.buttons || this.OK;
		}else if(NS.isString(cfg)){
			var len = arguments.length;
			if(len==1){
				cfg = {};
				cfg.msg = arguments[0];
			}else if(len==2){
				cfg = {};
				cfg.title = arguments[0];
				cfg.msg = arguments[1];
			}else{
				throw 'NS.Msg.error() : length is not valid!';
			}
			cfg.title = cfg.title || '错误提示';
			cfg.icon = cfg.icon || this.ERROR;
			cfg.buttons = cfg.buttons || this.OK;
		}else{
			throw 'NS.Msg.error() : type is not valid !';
		}
		return this.show(cfg);
	},
	/**
	 * 选择提示框，扩展方法,用于选择时，默认按钮是'是、确定'等为true的返回方法处理
	 * @param {Object/String} cfg 配置参数对象或者title
	 * @param {String} msg 提示内容 
	 * @param {Function} fn 回调函数(默认为真时的回调)
	 * @return Ext组件本身
	 */
	changeTip:function(cfg,msg,fn,scope){
		var title = '',msg_,fn_,scope_;
		if(NS.isObject(cfg)){//如果是对象
			fn_ = cfg.fn;
			msg_ = cfg.msg;
			title = cfg.title;
			scope_ = cfg.scope;
		}else if(NS.isString(cfg)){
			title = cfg;
			msg_ = msg;
			fn_ = fn;
			scope_=scope;
		}
		return this.confirm(title,msg_,function(btn){
			if(btn=='yes'){//以后可以根据需要，更改confirm的icon\buttons,这样可以在内部更改btn的实际值即可
				fn_.call(scope_||this);
			}
		});
	}
});
/**
 * @class NS.Msg
 * @extends NS.window.MessageBox
 */
/**
 * @class NS.MessageBox
 * @extends NS.window.MessageBox
 */
NS.MessageBox = NS.Msg = new NS.window.MessageBox();