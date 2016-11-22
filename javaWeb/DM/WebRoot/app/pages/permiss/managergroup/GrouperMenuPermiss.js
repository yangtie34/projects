/**
 * 组员管理
 */
NS.define('Pages.permiss.managergroup.GrouperMenuPermiss',{
    initGrouperMenuPermiss : function(swc,container,params){
        var me = this,
            group = this.group,
            user = this.user = params;
        this.callSingle({key : 'queryUserUsergroupMenuPermiss',params : {usergroupId : group.id,userId:user.id}},function(data){
                var page = new Pages.permiss.base.MenuPermiss({//构建权限赋予功能
                    menuTree : NS.clone(this.groupMenu),
                    layoutData : {mainTitle : '用户组组员管理',title : '设置组员菜单权限',others : user.loginname+"-"+user.username},//布局显示数据
                    contentLayoutData : {description : ''},
                    menuIds : this.getMenuIds(data.data),
                    cancel : function(){
                        swc.switchTo('grouperManagement');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(menuIds){
                        me.doUpdateUserMenuPermiss(group.id,user.id,menuIds.join(','),function(){
                            swc.switchTo('grouperManagement');
                            container.component.remove(page.getLibComponent());
                        });
                    }
                });
                page.on('pageready',function(){
                    var com = page.getLibComponent();
                    container.component.add(com);
                });
            })
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
    doUpdateUserMenuPermiss : function(usergroupId,userId,menuIds,callback){
        this.doTipRequest({
            key : 'updateUserMenuPermiss',
            params : {usergroupId : usergroupId,userId : userId,menuIds : menuIds},
            successMsg : '用户组菜单权限设置成功!',
            failureMsg : '用户组菜单权限设置失败!',
            callback : callback
        });
    }
});