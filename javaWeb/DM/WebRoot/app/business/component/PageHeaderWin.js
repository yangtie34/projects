/**
 * @class Business.component.PageHeader
 * @extend Template.Page
 */
NS.define('Business.component.PageHeaderWin',{
    singleton : true,
    extend : 'Template.Page',
    cssRequires : ['app/business/component/css/page-header.css'],
    tpl : new NS.Template('<div><h1><span class="pageheader_win">{title}</span></h1></div>'),
    constructor : function(){
        this.basicData = {title : ''};
        this.callParent(arguments);
    },
    /**
     * 获取页面头组件实例
     * @param {String} cdmc
     * @return {NS.Component}
     */
    getInstance : function(title){
        var data = NS.clone(this.basicData);
        NS.apply(data,{title : title});
        var component = new NS.Component({
            tpl : this.tpl,
            data : data
        });
        return component;
    }
});