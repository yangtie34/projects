/**
 * 页面Title
 */
NS.define('Pages.permiss.base.ColumnTitle',{
    singleton : true,
    getInstance : function(config){
        var baseHtml = '<h2><span class="wbh-common-title">{0}</span>{1}</h2>',
            desc = config.desc,
            other = config.other||'',
            html = NS.String.format(baseHtml,desc,other);
        config.html = html;
        component = new NS.Component(config);
        return component;
    }
});