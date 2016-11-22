/**
 * @class NS.Img
 * @extends NS.Component
 * @author yongtaiwang
 *      图片类
 *
        var img = new NS.Img({
            src : '../../show.jpg'
        });
 */
NS.define('NS.Img',{
    extend : 'NS.Component',
    /**
     *@cfg {String} src 图片的路径
     */
    initComponent : function(config){
        this.component = Ext.create('Ext.Img',config);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('src');
    },
    /**
     * 设置组件显示的图片的路径
     * @param {String} url
     */
    setSrc : function(url){
        this.component.setSrc(url);
    }
});