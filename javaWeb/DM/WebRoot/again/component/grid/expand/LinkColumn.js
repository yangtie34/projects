/***
 * @class Ext.grid.column.LinkColumn
 * @extends Ext.grid.column.Column
 *    针对ext列的扩展类
 *    var column = new Ext.grid.column.LinkColumn({
                        header: 'Phone',
                        dataIndex: 'phone',
                        width:200,
                        links : [
                        {
                            linkText : '第一个链接',
                            style : {
                                color : 'red',
                                font : '18px'
                            }
                        },
                        {
                            linkText : '第二个链接'
                        }
                    ]}
 *    });
 *    当然你也可以在column中添加render方法，方法最后返回html字符串，但是字符串必须包含您所需要的button标签，
 *    这样，你可以监听该组件的{@link Ext.grid.column.LinkColumn#linkclick}linkclick事件，事件会返回一些属性供您使用。
 */
Ext.define('Ext.grid.column.LinkColumn',{
    extend : "Ext.grid.column.Column",
    alias: ['widget.linkcolumn'],
    /**
     * 配置的固定列的链接
     *      配置项参照元素参照
     *              var item = [{
                             linkText : '第一个链接',
                             style : {
                                 color : 'red',
                                 font : '18px'
                             }
                         }];
     */
    link : [],
    /**
     * 创建linkcolumn对象
     * @param {Object} config
     */
    constructor : function(){
        var me = this;
        this.callParent(arguments);
        /***
         * @event linkclick
         *     当点击此列中任意一个链接的时候触发该事件
         * @param {String} linkValue 链接的名称
         * @param {Number} recordIndex 行索引
         * @param {Number} cellIndex 列索引
         * @param {Object} data 行数据
         * @param {HTMLElement} element 链接对象
         */
        this.addEvents('linkclick');
        if(!this.renderer){
            this.renderer = function(){
                var basicHL = '<a href="javascript:void(0);" style="{0}">{1}</a>';
                var items = me.links;
                var html = "";
                var format = Ext.String.format;
                for(var i=0;i<items.length;i++){
                    var item = items[i];
                    var style = me.generateStyleString(item.style||{});
                    html += format(basicHL,style,item.linkText)+"&nbsp;";
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
                if(cell.contains(target) && target.nodeName == "A"){
                    e.stopEvent();
                    this.fireEvent('linkclick',this.deleteHtmlTag(target.innerHTML),recordIndex,cellIndex,record.data,target,1);
                }else if (type == 'mousedown' && item.stopSelection !== false) {
                    return false;
                }
            }
        }
        return me.callParent(arguments);
    },
    /***
     * 移除html字符串
     * @private
     */
    deleteHtmlTag : function(str){
        return str.replace(/<\/?.+?>/g, "");// 去掉所有的html标记
    }
});