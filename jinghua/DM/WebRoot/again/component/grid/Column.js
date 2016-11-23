/**
 * @class NS.grid.Column
 * @extends NS.Component
 *     目前支持的类型为buttoncolumn,linkcolumn,progresscolumn
 *
 * 定义grid的column列的抽象类
 */
NS.define('NS.grid.Column',{
    extend : 'NS.Component',
    /**
     * @cfg  {NS.form.field}editor 可编辑表格组件
     */
    /**
     * @cfg  {Function} renderer  渲染函数，其返回值决定列的最终渲染
     */
    /**
     * @cfg  {Function} name  列名，其返回值决定列的最终渲染
     */
    /**
     * @cfg  {Function} text  列文本内容，显示grid列头上显示的内容
     */
    /**
     * @cfg  {Boolean} flex  自伸展
     */
    /**
     * 初始化组件
     * @private
     */
   initComponent : function(config){
       this.component = Ext.create('Ext.grid.column.Column',config||{});
   },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('text','name','renderer','xtype','locked','flex');
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents : function(){
        this.callParent();
        this.addEvents(
            /**
             * 当列上的链接被点击的时候触发该事件
             * @event linkclick
             * @param {String} linktext 链接的文本内容
             * @param {Number} rowIndex 行索引
             * @param {Number} columnIndex 列索引
             * @param {Object} rowData 行数据
             */
            'linkclick',
            /**
             * 当列上的按钮被点击的时候触发该事件
             * @event buttonclick
             * @param {String} buttontext 按钮的文本内容
             * @param {Number} rowIndex  行索引
             * @param {Number} columnIndex 列索引
             * @param {Object} rowData 行数据
             */
            'buttonclick');
    },
    onLinkclick : function(){
        this.component.on('linkclick',function(linktext,rowIndex,columnIndex,rowData,cell){
            this.fireEvent('linkclick',linktext,rowIndex,columnIndex,rowData,cell);
        },this)
    },
    onButtonclick : function(){
        this.component.on('buttonclick',function(buttontext,rowIndex,columnIndex,rowData,name,cell){
            this.fireEvent('buttonclick',buttontext,rowIndex,columnIndex,rowData,name,cell);
        },this)
    },
    /**
     * 获取Column对象的编辑组件
     * @param {NS.form.field.BaseField}
     */
    getEditor : function(){
        var editor = this.component.getEditor()||this.editor;
        return NS.util.ComponentInstance.getInstance(editor);
    },
    /**
     * 设置列的文本信息
     * @param {String} text 列的显示文本信息
     */
    setText : function(text){
        this.component.setText(text);
    },
    /**
     * 设置当前列的可编辑组件
     * @param {NS.form.field.BaseField} field
     * @private (something wrong)
     */
    setEditor : function(field){
       this.component.setEditor(field.getLibComponent());
    },
    /**
     * 获取列属性
     */
    getDataIndex : function(){
        return this.component.dataIndex;
    }
});