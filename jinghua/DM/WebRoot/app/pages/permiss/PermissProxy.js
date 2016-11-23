/**
 * 权限代理、委托
 */
NS.define('Pages.permiss.PermissProxy',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.MenuPermiss'],
    modelConfig : {
        serviceConfig : {
            queryProxy : 'proxyPermiss?queryMyProxy',
            addProxy : 'proxyPermiss?addProxy',
            updateProxy : 'proxyPermiss?updateProxy',
            deleteProxy : 'proxyPermiss?deleteProxy',
            updateProxyMenuPermiss : 'proxyPermiss?updateProxyMenuPermiss',
            queryMyMenuPermiss : 'proxyPermiss?queryUserMenus',
            queryProxyMenus : 'proxyPermiss?queryProxyMenus',
            proxyStsx : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpUserMenuProxy'}},
            queryUsers : 'proxyPermiss?queryUsers'//查询用户
        }
    },
    mixins : ['Pages.permiss.proxy.ProxyPage','Pages.permiss.proxy.ProxyMenuSet'],
    dataRequires : [
        {fieldname : 'proxyStsx' , key : 'proxyStsx' },
        {fieldname : 'proxyData' , key : 'queryProxy' }
    ],
    advanceDataRequires : [
        {fieldname : 'myMenus' , key : 'queryMyMenuPermiss' }
    ],
    tplRequires : [
        {fieldname : 'proxyPageTpl',path : 'app/pages/permiss/proxy/tpl/proxyPageTpl.html'},
        //proxy
        {fieldname : 'addLayoutTpl',path : 'app/pages/permiss/menu/tpl/addMenuTpl.html'}
    ],
    init : function(){
        var container = this.swc = new NS.container.SwitchContainer({
            items :[
                {
                    name : 'proxyPage',scope : this,init : this.initProxyPage
                },
                {
                    name : 'proxyMenuSet',scope : this,init : this.initProxyMenuSet
                }
            ]
        });
        this.setPageComponent(container);
    }
});