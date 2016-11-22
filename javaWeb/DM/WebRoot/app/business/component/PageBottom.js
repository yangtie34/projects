/**
 * 页面底部
 */
NS.define('Business.component.PageBottom',{
    singleton : true,
    extend : 'Template.Page',
    cssRequires : ['app/business/component/css/page-header.css'],
    tpl : new NS.Template(
    		'  <div class="pageheader_bottom"> '+
    			'<img class="pageheader-imgcss" > '+
    			'<span class="pageheader-shuoinfo">'+
    			'<span class="pageheader-text-cor">说明</span>：{ms}</span>'+
    		 '</div>'
    ),
   
    /**
     * 获取页面头组件实例
     * @param {String} cdmc
     * @return {NS.Component}
     */
    getInstance : function(ms){
        var data = {ms : ms};
        var component = new NS.Component({
            tpl : this.tpl,
            data : data
        });
        return component;
    }
});