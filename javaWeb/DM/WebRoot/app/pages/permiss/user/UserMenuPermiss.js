/**
 * 设定角色的功能权限，尚需要完善
 */
NS.define('Pages.permiss.user.UserMenuPermiss',{
    /**
     * 初始化菜单功能设置
     */
    initUserMenuPermissSet : function(swc,container,params){
        var user = params;
        var me = this;
        this.callSingle({key : 'queryUserUsergroupMenuPermiss',params : {userId:user.id}},function(menuData){
            this.getAdvanceData(['allMenu'],function(data){
                var page = new Pages.permiss.base.MenuPermiss({//构建权限赋予功能
                    menuTree : data.allMenu,
                    layoutData : {mainTitle : '用户管理',title : '设置用户菜单权限',others : (user.username||user.loginname)+"-"+user.bmmc+"-"+user.ghxh},//布局显示数据
                    contentLayoutData : {description : user.username},
                    menuIds : this.getMenuIds(menuData.data),
                    cancel : function(){
                        swc.switchTo('userPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(menuIds){
                        me.doUpdateUserMenuPermiss(user.id,menuIds.join(','),function(){
                            swc.switchTo('userPage');
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
    /*************************数据请求*************************************************/
    doUpdateUserMenuPermiss : function(userId,menuIds,callback){
        this.doTipRequest({
            key : 'updateUserMenuPermiss',
            params : {userId : userId,menuIds : menuIds},
            successMsg : '用户菜单权限设置成功!',
            failureMsg : '用户菜单权限设置失败!',
            callback : callback
        });
    }
});