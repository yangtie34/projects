/**
 * @class Business.component.PageHeader
 * @extend Template.Page
 */
NS.define('Business.component.PageHeader',{
    singleton : true,
    extend : 'Template.Page',
    cssRequires : ['app/business/component/css/page-header.css'],
    tpl : new NS.Template('<div class="pageheader_top">'+
	    		   '<h3 class="pageheader-floatRight"> ' +
	    				'<span class="pageheader-term">{xnmc}<span class="pageheader_marginLeft">{xqmc}</span></span> ' +
	    		   '</h3>' +
			       '<h1> ' +
			           '<span class="pageheader-model">{cdmc}</span>' +
			           '<span class="pageheader-user">{xm}</span> ' +
			           '<span class="pageheader-dept">{bm}</span> ' +
			       '</h1> '+
			     '</div>'
    ),
    constructor : function(){
        this.basicData = {
            xnmc : MainPage.getXnMc(),
            xqmc : MainPage.getXqMc(),
            xnxqList:MainPage.getXqMc(),
            cdmc : '',
            xm : MainPage.getUserName()?MainPage.getUserName():MainPage.getLoginName(),
            bm : MainPage.getBmxx()?MainPage.getBmxx().mc : ''
        };
        this.callParent(arguments);
    },
    /**
     * 获取页面头组件实例
     * @param {String} cdmc
     * @return {NS.Component}
     */
    getInstance : function(cdm){
        var data = NS.clone(this.basicData);
        NS.apply(data,{cdmc : cdm});
        var component = new NS.Component({
            tpl : this.tpl,
            data : data
        });
        return component;
    }
});