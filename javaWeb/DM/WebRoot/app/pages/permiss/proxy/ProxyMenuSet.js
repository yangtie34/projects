NS.define('Pages.permiss.proxy.ProxyMenuSet',{
    initProxyMenuSet : function(swc,container,row){
        var me = this,
            proxyId  = row.id;
        this.callSingle({key : 'queryProxyMenus',params : {proxyId:proxyId}},function(menuData){
            this.getAdvanceData(['myMenus'],function(data){
                var page = new Pages.permiss.base.MenuPermiss({//构建权限赋予功能
                    menuTree : NS.clone(data.myMenus),
                    layoutData : {mainTitle : '用户管理',title : '设置委派用户菜单权限',others :row.xfusername},//布局显示数据
                    contentLayoutData : {description : ''},
                    menuIds : this.getMenuIds(menuData),
                    cancel : function(){
                        swc.switchTo('proxyPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(menuIds){
                        me.doUpdateProxyMenu(proxyId,menuIds.join(','),function(){
                            swc.switchTo('proxyPage');
                            container.component.remove(page.getLibComponent());
                        });
                    }
                });
                page.on('pageready',function(){
                    var com = page.getLibComponent();
                    container.component.add(com);
                });
            })
        });
    },
    /**
     * 获取当前角色拥有的菜单ID
     * @param roleMenuList
     * @returns {Array}
     */
    getMenuIds : function(roleMenuList){
        var menuIds = [],
            i = 0,len = roleMenuList.length,item;
        for(;i<len;i++){
            item = roleMenuList[i];
            menuIds.push(item.menuId);
        }
        return menuIds;
    },
    /******************数据请求******************/
    doUpdateProxyMenu : function(proxyId,menuIds,callback){
        this.doTipRequest({
            key : 'updateProxyMenuPermiss',
            params : {proxyId : proxyId,menuIds : menuIds},
            successMsg : '委托修改成功!',
            failureMsg : '委托修改失败!',
            callback : callback
        });
    }
});