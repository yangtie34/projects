/**
 * @class Business.component.PageSubtitle
 * @extend Template.Page
 */
NS.define('Business.component.PageSubtitle',{
    singleton : true,
    extend : 'Template.Page',
    cssRequires : ['app/business/component/css/page-header.css'],
    tpl : new NS.Template('<div class="pagesubtitle pageheader">'+
	    				'<h2><span class="pagesubtitle_title">{title}</span></h2>' +
	    		   		'</div>'),
    /**
     * 获取页面-副标题组件
     * @param {String} title
     * @return {NS.Component}
     */
    getInstance : function(title){
        var data = {title:title};
        var component = new NS.Component({
            tpl : this.tpl,
            data : data
        });
        return component;
    }
});