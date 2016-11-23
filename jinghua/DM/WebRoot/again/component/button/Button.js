/**
 * @class NS.button.Button
 * @extends NS.Component
 * 例如
 *
              var button = new NS.button.Button({
                   text : '新增',
                   name :'add',
                   iconCls : 'page_add'
              });

 */
NS.define('NS.button.Button',{
    extend : 'NS.Component',
    /**
     *@cfg {String} text 按钮的显示名称
     */
    /**
     *@cfg {String} name 按钮的name属性
     */
    /**
     *@cfg {String} iconCls 按钮背景图片的Class类
     */

    initComponent : function(config){
        this.component = new Ext.button.Button(config);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
                text : true,
                name : true,
                handler : true,
                iconCls : true
            }
        );
    },
    onClick : function(){
        this.component.on('click',function(button,event){
            NS.Event.setEventObj(event);
            this.fireEvent('click',this,NS.Event);
        },this);
    },
    /**
     * 设置按钮的文字显示
     * @param {String} text
     */
    setText : function(text){
        this.component.setText(text);
    },
    /**
     * 获取按钮的文字显示
     * @return {String}
     */
    getText : function(){
        return this.component.getText();
    },
    /**
     * 设置按钮的图标样式
     * @param {String} cls css类样式
     */
    setIconCls : function(cls){
        this.component.setIconCls(cls);
    }
});