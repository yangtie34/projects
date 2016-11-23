NS.define('NS.Editor',{
    extend : 'NS.Component',
    /**
     * @cfg {NS.form.field.BaseField} field 编辑组件
     */
    /**
     * @cfg {Array} offsets 位置信息
     */
    /**
     * @param config
     */
    initComponent : function(config){
        var basic = {
            updateEl : true
        }
        this.component = new Ext.Editor(basic);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        var getField = function(value, property, config){
            config[property] = value.getLibComponent();
        };
        this.addConfigMapping({
            updateEl : true,
            field : true,
            offsets : true//位置信息
        });
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /***
             * @event complete 组件渲染完毕后
             * @param {NS.Component} this
             */
            'complete'
        );
    },
    onComplete : function(){
        this.component.on('complete',function(editor,value,startValue){
            this.fireEvent('complete', this,value,startValue,this.component.boundEl);
        },this);
    },
    /**
     * 要编辑的元素
     * @param element
     */
    startEdit : function(element){
        this.component.startEdit(element);
    },
    /**
     * 取消编辑
     */
    cancelEdit : function(){
        this.component.cancelEdit();
    }
});