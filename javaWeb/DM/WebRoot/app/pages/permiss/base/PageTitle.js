/**
 * 页面Title
 */
NS.define('Pages.permiss.base.PageTitle',{
    singleton : true,
    getInstance : function(config){
        var baseHtml = '<div class="permiss-common-top"><h1> <span class="permiss-common-model">{0}</span></span>' +
                '<span class="permiss-common-user">{1}</span><span class="permiss-common-dept">{2}</span></h1></div>',
            data = config.data;
            title = data.title,
            username = data.username,
            department = data.department,
            html = NS.String.format(baseHtml,title,username,department);
        delete config.data;
        NS.apply(config,{
            style : {
                backgroundColor : 'white'
            },
            html : html
        });
        var component = new NS.Component(config);
        return component;
    }
});