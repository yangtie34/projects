/***
 * @class Ext.grid.column.ButtonColumn
 * @extends Ext.grid.column.Column
 *    针对ext列的扩展类
 *    var column = new Ext.grid.column.ButtonColumn({
                        header: 'Phone',
                        dataIndex: 'phone',
                        width:200,
                        buttons : [
                        {
                            buttonText : '第一个按钮',
                            style : {
                                color : 'red',
                                font : '18px'
                            }
                        },
                        {
                            buttonText : '第二个按钮'
                        }
                    ]}
 *    });
 *    当然你也可以在column中添加render方法，方法最后返回html字符串，但是字符串必须包含您所需要的button标签，
 *    这样，你可以监听该组件的{@link Ext.grid.column.ButtonColumn#buttonclick}buttonclick事件，事件会返回一些属性供您使用。
 */
Ext.define('Ext.grid.column.ButtonColumn',{
    extend : "Ext.grid.column.Column",
    alias: ['widget.buttoncolumn'],
    /**
     * 创建buttoncolumn对象
     * @param {Object} config
     */
    constructor : function(){
        var me = this;
        this.callParent(arguments);
        /***
         * @event buttonclick
         *         当点击此列中任意一个按钮的时候触发该事件
         * @param {String} buttonValue 按钮的名称
         * @param {Number} recordIndex 行索引
         * @param {Number} cellIndex 列索引
         * @param {Object} data 行数据
          */
        this.addEvents('buttonclick');
        if(!this.renderer){
            this.renderer = function(){
                var basicHL = '<input type="button" value={0} style="{1}" name="{2}">';
                var items = me.buttons;
                var html = "";
                var format = Ext.String.format;
                for(var i=0;i<items.length;i++){
                    var item = items[i];
                    var style = me.generateStyleString(item.style||{});
                    html += format(basicHL,item.buttonText,style,item.name);
                }
                return html;
            };
        }
    },
    /**
     * 生成样式字符串
     * @param {Object} obj 样式对象
     * @return {string}
     */
    generateStyleString : function(obj){
        var style = "";
        for(var i in obj){
            style += i + ":" + obj[i]+";";
        }
        return style;
    },
    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * Also fires any configured click handlers. By default, cancels the mousedown event to prevent selection.
     * Returns the event handler's status to allow canceling of GridView's bubbling process.
     */
    processEvent : function(type, view, cell, recordIndex, cellIndex, e, record, row){
        var me = this,
            item, fn;
        var dataIndex = view.getGridColumns()[cellIndex].dataIndex;//当前点击列的属性
        if(dataIndex == this.dataIndex){
            if(type == "click"){
                var target = e.getTarget();
                if(cell.contains(target) && target.nodeName == "INPUT" && target.type == "button"){
//                            this.onClick.call(this.scope||this,item.buttonText,recordIndex,cellIndex);
                	this.fireEvent('buttonclick',target.value,recordIndex,cellIndex,record.data,target.name||'',cell);
                }else if (type == 'mousedown' && item.stopSelection !== false) {
                    return false;
                }
            }
        }
        return me.callParent(arguments);
    }
});