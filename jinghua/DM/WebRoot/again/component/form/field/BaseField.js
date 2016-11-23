/**
 * @class NS.form.field.BaseField
 * @extends NS.Component field的基类
 */
NS.define('NS.form.field.BaseField', {
	extend : 'NS.Component',
	/**
	 * @cfg {Function} enterFn 回车事件产生后的回调函数
	 */
    /**
     * @cfg {Boolean} editable 组件是否可编辑
     */
	/**
	 * @cfg {String} fieldLabel 文本标签名称
	 */
	/**
	 * @cfg {Boolean} hideLabel 是否隐藏文本标签名称 默认false
	 */
	/**
	 * @cfg {String} labelAlign 文本标签位置 三个可选项："left","right","top".默认"left"
	 */
    /**
     * @cfg {String} invalidText 校验错误时的提示信息
     */
	/**
	 * @cfg {Number} labelWidth 文本标签宽度
	 */
	/**
	 * @cfg {Number} labelSeparator 文本分隔符
	 */
	/**
	 * @cfg {String} name 组件名称
	 */
	/**
	 * @cfg {Boolean/String} shadow 是否显示阴影,true表示显示,false则不显示 默认"sides"
	 */
	/**
	 * @cfg {String} style 组件样式 语法同css标准语法
	 */
	/**
	 * @cfg {Object} value 组件的值 
	 */
    /**
     * @cfg {Boolean} allowBlank 是否允许空值 默认false
     */
    /**
     * @cfg {String} blankText  不允许为空值是，若组件值为空则提示该信息
     */
    /**
     * @cfg {Boolean} disabled 是否可用 默认true
     */
    /**
     * @cfg {Boolean} editable 是否可编辑  默认true
     */
    /**
     * @cfg {String} emptyText 组件值为空是显示在组件的文字
     */
    /**
     * @cfg {Boolean} readOnly 是否只读 默认false
     */
    /**
     * @cfg {String} regex 正则表达式 对字段值进行测试验证过程中的一个JavaScript RegExp对象。如果测试失败，本场将被标记为无效使用要么regexText或invalidText。
     */
    /**
     * @cfg {RegExp} regexText 正则表达式校验的信息提示
     */
    /**
     * @cfg {Number} size  在文字输入元素的'大小'属性的初始值。这是仅用于域没有配置的宽度并没有给出其容器的布局宽度。默认为20。
     */
    /**
     * @cfg {String} vtype 验证类型名称定义
     */
    /**
     * @cfg {String} vtypeText 自定义错误消息显示Vtype域提供的消息，目前此字段设置的默认到位。注：仅适用于如果Vtype域设置，否则忽略。
     */
	/**
	 * @cfg {Number} tabIndex 此字段的tabIndex。注意，这仅适用于所呈现的领域，而不是那些通过内置applyTo
	 */
    /**
     * @cfg {Number} maxLength 输入的值的最大长度
     */
	/**
	 *  {Object} listeners 监听配置项 该项保留，暂不建议使用
	 */
	initData : NS.emptyFn,
	/**
	 * 创建一个Field组件
	 * @private
	 * @param {Object}
	 *            config 配置对象
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Base', config);
	},
	/**
	 * 执行enterFn方法,为扩展方法,
	 * 需在组件生成时添加enterFn对象,
	 * 该对象key对应的value为function(text,textValue,textRawValue){}
	 * @param enterFn
	 */
	doEnterFn:function(enterFn){
		//Ext.form.field.Text,Ext.EventObject,Object
		this.component.on('specialkey',function(text,e,eOpts){
			if(e.getKey()==13){
		     	enterFn(text,text.getValue(),text.getRawValue());
				//enterFn.call(enterFn,text,text.getValue(),text.getRawValue());
			}
		});
	},
	/**
	 * @private
	 * 初始化参数配置mapping映射关系
	 */
	initConfigMapping:function(){
		this.callParent();
        var processEditable = function(value,property,config){
               config.readOnly = Boolean(!value)
               if(!value){
                  config.fieldStyle = "background:#E6E6E6;";
               }
        }
		this.addConfigMapping({
			isEnter:true,
			enterFn:true,
			fieldLabel:true,
            fieldStyle : true,
			autoScroll:true,
			autoShow:true,
			hideLabel:true,
			labelAlign:true,
			labelWidth:true,
			labelSeparator:true,
			name:true,
			shadow:true,
			style:true,
			value:true,
			xtype:true,
			tabIndex:true,
			data:true,
            editable : processEditable,
			listeners:true,
            maxLength : true,
            validator : true
		});
	},
    /**
     * @private
     */
    initEvents:function(){
        this.callParent();
        this.addEvents(
            /**
             * @event keydown 键盘点下时触发
             * @param {NS.Component} this
             */
            'keydown',
            /**
             * @event keypress 按键输入字段事件。此事件只触发，如果enableKeyEvents设置为true。
             * @param {NS.Component} this
             */
            'keypress',
            /**
             * @event keyup 键盘键松开时触发
             * @param {NS.Component} this
             */
            'keyup',
            /**
             * @event select 数据被选中时触发
             * @param {NS.Component} this
             * @param {Array[]} array 对象数组
             */
            'select',
            /**
             * @event  change Field的值变换的时候触发该事件
             * @param {NS.Component} this
             * @param {String} newvalue 新值
             * @param {String} oldvalue 旧值
             */
            'change',
            /**
             * @event  blur Field失去焦点时触发该事件
             * @param {NS.Component} this
             */
            'blur',
            /**
             * @event  focus Field获得焦点时触发该事件
             * @param {NS.Component} this
             */
            'focus',
            /**
             * 特殊按钮事件
             * @event specialkey 特殊案件事件
             * @param {NS.Component} com
             * @param {NS.Event} event
             */
            'specialkey'

        );
    },
	onKeydown : function(){
        this.component.on('keydown',function(){
            this.fireEvent('keydown',this);
        },this);
    },
    onKeyup : function(){
        this.component.on('keyup',function(){
            this.fireEvent('keyup',this);
        },this);
    },
    onKeypress : function(){
        this.component.on('keypress',function(){
            this.fireEvent('keypress',this);
        },this);
    },
    onSelect : function(){
        this.component.on('select',function(com,records){
            var array = NS.Array.pluck(records,'data');
            this.fireEvent('select', this,array);
        },this);
    },
    onChange : function(){
        this.component.on('change',function(com,newvalue,oldvalue){
            this.fireEvent('change', this,newvalue,oldvalue);
        },this);
    },
    onBlur : function(){
        this.component.on('blur',function(com){
            this.fireEvent('blur', this);
        },this);
    },
    onFocus : function(){
        this.component.on('focus',function(com){
            this.fireEvent('focus', this);
        },this);
    },
    onSpecialkey : function(){
        this.component.on('specialkey',function(com,event){
            NS.Event.setEventObj(event);
            this.fireEvent('specialkey', this,event);
        },this);
    },
	/**
	 * 得到组件名称
	 * 
	 * @return {String}
	 */
	getName : function() {
		return this.component.getName();
	},
	/**
	 * 设置实际值
	 * 
	 * @param {Object} name
	 */
	setValue : function(name) {
		this.component.setValue(name);
	},
	/**
	 * 设置显示值
	 * 
	 * @param {Object} value
	 */
	setRawValue : function(value) {
		this.component.setRawValue(value);
	},
	/**
	 * 设置组件宽度
	 * 
	 * @param {Number}  width
	 */
	setWidth : function(width) {
		this.component.setWidth(width);
	},
	/**
	 * 判断是否能通过过校验
	 * 
	 * @return {Boolean} 通过校验返回true,反之false
	 */
	isValid : function() {
		return this.component.isValid();
	},
	/**
	 * 得到组件的值
	 * 
	 * @return {Object}
	 */
	getValue : function() {
		return this.component.getValue();
	},
	/**
	 * 设置是否可用
	 * 
	 * @param {Boolean} flag
	 * 
	 */
	setDisabled : function(flag) {
		this.component.setDisabled(flag);
	},
	/**
	 * 设置是否可见
	 * 
	 * @param {Boolean}  flag
	 */
	setVisible : function(flag) {
		this.component.setVisible(flag);
	},
    /**
     * 设置组件是否可编辑
     *
     * @param {Boolean}　editable
     */
    setEditable : function(editable) {
        this.component.setEditable(editable);
    },
	/**
     * 设置是否只读
     *
     * @param {Boolean} flag
     */
    setReadOnly : function(flag) {
        this.component.setReadOnly(flag);
        if(this.component.setFieldStyle){
            if(flag === true){
                this.component.setFieldStyle("background:#E6E6E6;");
            }else if(flag === false){
                this.component.setFieldStyle("background:white;");
            }
        }
    },
//	/**
//	 * 增加监听事件
//	 *
//	 * @param {String}  eName 事件名
//	 * @param {Function} fn 相应回调函数
//	 * @param {Object}  scope 作用域
//	 * @param {Object}  options
//	 */
//	addListener : function(eName, fn, scope, options) {
//		this.component.addListener(eName, fn, scope, options);
//	},
//	/**
//	 * 销毁组件
//	 */
//	destroy : function() {
//		this.component.destroy();
//	},
	/**
	 * 找到该组件的上级组件,如果fn返回true,则上级组件被返回
	 * @private
	 * @param {Function}
	 *            fn
	 * @return {Ext.container.Container}
	 */
	findParentBy : function(fn) {
		return this.component.findParentBy(fn);
	},
	/**
	 * 得到显示值
	 * @return {Number/String}
	 */
	getRawValue:function(){
		return this.component.getRawValue();
	},
    /**
     * 设置field的标签名
     * @param {String} label 标签名
     */
    setFieldLabel : function(label){
        this.component.setFieldLabel(label);
    },
    /**
     * 获取field的标签字符串
     * @param {String}
     */
    getFieldLabel : function(){
        return this.component.getFieldLabel();
    }
});