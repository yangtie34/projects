/**
 * 设定角色的功能权限，尚需要完善
 */
NS.define('Pages.permiss.usergroup.UserGroupMenuSet',{
    initMenuSet : function(swc,container,params){
        var group = params;
        var me = this;
        this.callSingle({key : 'usergroupMenu',params : {usergroupId:group.id}},function(menuList){
                this.getAdvanceData(['allMenu'],function(data){
                    var page = new Pages.permiss.base.MenuPermiss({//构建权限赋予功能
                        menuTree : NS.clone(data.allMenu),
                        layoutData : {mainTitle : '用户组管理',title : '设置用户组菜单权限',others : group.groupname},//布局显示数据
                        contentLayoutData : {description:group.groupname},
                        menuIds : this.getMenuIds(menuList),
                        cancel : function(){
                            swc.switchTo('usergroupPage');
                            container.component.remove(page.getLibComponent());
                        },
                        submit : function(menuIds){
                            me.doUpdateRoleMenuPermiss(group.id,menuIds.join(','),function(){
                                swc.switchTo('usergroupPage');
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
     * 获取当前用户组拥有的菜单ID
     * @param groupMenuList
     * @returns {Array}
     */
    getMenuIds : function(groupMenuList){
        var menuIds = [],
            i = 0,len = groupMenuList.length,item;
        for(;i<len;i++){
            item = groupMenuList[i];
            menuIds.push(item.menuId);
        }
        return menuIds;
    },
    /*************************数据请求*************************************************/
    doUpdateRoleMenuPermiss : function(usergroupId,menuIds,callback){
       var successMsg = '用户组功能权限设置成功!',
           failureMsg = '用户组功能权限设置失败!',
           updateParams = {usergroupId : usergroupId,menuIds : menuIds},
           deleteOldMenuParams = {usergroupId : usergroupId};
        this.callSingle({key : 'updateGroupMenu',params : updateParams},function(data){
            if(data.success){
                NS.Msg.info(successMsg);
                this.callSingle(
                    {key : 'deleteUserOldMenuPermissInUserGroup',params : deleteOldMenuParams},function(){

                    });
                if(callback)callback.call(this);
            }else{
                NS.Msg.info(failureMsg);
            }
        });
    }
});