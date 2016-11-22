/***
 * @class Ext.grid.column.ProgressColumn
 * @extends Ext.grid.column.Column
 *    针对ext列的扩展类
 *    var column = new Ext.grid.column.ProgressColumn({
                        header: 'Phone',
                        dataIndex: 'phone',
                        width:200,
                        color:'red'
                      });
 *    当然你也可以在column中添加render方法，方法最后返回html字符串，但是字符串必须包含您所需要的button标签，
 *    这样，你可以监听该组件的{@link Ext.grid.column.LinkColumn#linkclick}linkclick事件，事件会返回一些属性供您使用。
 */
Ext.define('Ext.grid.column.ProgressColumn',{
    extend : "Ext.grid.column.Column",
    alias: ['widget.progresscolumn'],
    /**
     * 创建progresscolumn对象
     * @param {Object} config
     */
    constructor : function(){
        var me = this;
        this.callParent(arguments);
        this.renderer = function(value){
            var basicHL = '<div style="height:16px;border:1px {0} solid;">' +
                              '<div style="background-color:{1};height:14px;width:{2}%;text-align:center;">{3}%</div>' +
                          '</div>';
            var items = me.buttons;
            var html = "";
            var format = Ext.String.format;
            html += format(basicHL,me.color,me.color,value,value);
            return html;
        };
    },
    /**
     * @cfg {String} 进度条的颜色
     */
    color : 'blue'
});