/**
 * 设定角色的功能权限，尚需要完善
 */
NS.define('Pages.permiss.role.RoleMenuSet',{
    /**
     * 初始化菜单功能设置
     */
    initMenuSet : function(swc,container,params){
        var role = params;
        var me = this;
        this.callSingle({key : 'roleMenu',params : {roleId:role.id}},function(roleMenuList){
            this.getAdvanceData(['allMenu'],function(data){
                var page = new Pages.permiss.base.MenuPermiss({//构建权限赋予功能
                    menuTree : NS.clone(data.allMenu),
                    layoutData : {mainTitle : '角色管理',title : '设置角色权限',others : "角色:<span style='color: red'>"+role.jsmc+"</span>"},//布局显示数据
                    contentLayoutData : {description : role.jsmc},
                    menuIds : this.getMenuIds(roleMenuList),
                    cancel : function(){
                        swc.switchTo('rolePage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(menuIds){
                        me.doUpdateRoleMenuPermiss(role.id,menuIds.join(','),function(){
                            swc.switchTo('rolePage');
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
    doUpdateRoleMenuPermiss : function(roleId,menuIds,callback){
        this.doTipRequest({
            key : 'updateRoleMenuPermiss',
            params : {roleId : roleId,menuIds : menuIds},
            successMsg : '角色菜单权限设置成功!',
            failureMsg : '角色菜单权限设置失败!',
            callback : callback
        });
    }
});