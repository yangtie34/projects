/**
 * 我所管理的用户组
 */
NS.define('Pages.permiss.GroupsManagerByMe',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.RoleManager',
        'Pages.permiss.UserGroupManager',
        'Pages.permiss.base.MenuPermiss',
        'Pages.permiss.base.DataPermissLd'],
    modelConfig : {
        serviceConfig : {
            //我所管理的用户组
            'queryMineGroups' : "usergroupManager?queryUsergroupManagerByMine",
            'usergroupStsx' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpUsergroup'}},
            //组员管理
            'queryUsersByUsergroupManager' : {service : 'usergroupManager?queryUsersByUsergroupManager',params : {queryType : 1}},
            'userStsx' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpUser'}},
            'roleList' : {service : 'roleManager?queryRolesBygroupId',params : {start : 0,limit : 200}},
            'updateUsergroupRoles' : {service : 'userManager?updateUsergroupRoles'},
            //查询用户菜单权限
            'allMenu' : {service : 'menuManager?queryAllMenus',params : {all : true}},
            updateUserMenuPermiss : 'userManager?updateUserUsergroupMenuPermiss',
            queryUserGroupMenu : {service : 'menuManager?queryUsergroupMenus',params : {start : 0,limit : 20000,all : true}},
            queryUserUsergroupMenuPermiss : 'userManager?queryUserUsergroupMenuPermiss',
            //数据权限
            userDataPermiss : 'userManager?queryUserUsergroupDataPermiss',//查询用户-组数据权限
            usergroupDataZzjg : 'helpManager?queryJxzzjgWithGrouperDataPermiss',//用户组数据权限树，跟教学组织结构树做绑定
            updateGrouperDataPermiss : 'usergroupManager?updateGrouperDataPermiss',//修改用户组员数据权限

            //用户-按教学组织结构修正数据权限
            queryUserUsergroupDataPermissByJxzzjg : 'userManager?queryUserUsergroupDataPermissByJxzzjg',//按教学组织结构查询用户数据权限
            updateUserUsergroupDataPermissAndSaveWidthExactValue : 'userManager?updateUserUsergroupDataPermissAndSaveWidthExactValue',//按教学组织结构修改用户数据权限

            //用户组-按教学组织结构设置数据权限
            queryUsergroupDataPermissBindJxzzjg : 'usergroupManager?queryUsergroupDataPermissBindJxzzjg',//查询用户组-教学组织结构-粒度-数据权限-并绑定教学组织结构
            updateUsergroupDataPermissByJxzzjg : 'usergroupManager?updateUsergroupDataPermissByJxzzjg',

            //---------------------行政组织结构权限
            //用户-按行政组织结构修正数据权限
            queryUserUsergroupXzDataPermissByXzzzjg : 'userManager?queryUserUsergroupDataPermissByXzzzjg',//查询用户组-教学组织结构-粒度-数据权限-并绑定教学组织结构
            updateUserUsergroupXzDataPermissAndSaveWidthExactValue : 'userManager?updateUserUsergroupXzDataPermissAndSaveWidthExactValue',
            //用户组-行政数据权限
            queryUsergroupDataPermissBindXzzzjg : 'usergroupManager?queryUsergroupDataPermissBindXzzzjg',//查询用户组-教学组织结构-粒度-数据权限-并绑定教学组织结构
            updateUsergroupDataPermissByXzzzjg : 'usergroupManager?updateUsergroupDataPermissByXzzzjg'



        }
    },
    addCssRequires : [ 'app/pages/permiss/user/css/user.css'],
    tplRequires : [
        {fieldname : 'groupsLayout',path : 'app/pages/permiss/managergroup/tpl/mineGroupsLayout.html'},
        //组员管理
        {fieldname : 'groupersManagerLayout',path : 'app/pages/permiss/managergroup/tpl/groupersManagerLayout.html'},
        {fieldname : 'roleSetTpl',path : 'app/pages/permiss/managergroup/tpl/grouper/grouperRoleSetTpl.html'},
        {fieldname : 'roleListSetTpl',path : 'app/pages/permiss/managergroup/tpl/grouper/roleSetListTpl.html'},
        {fieldname : 'grouperRoleSetTpl',path : 'app/pages/permiss/managergroup/tpl/grouper/grouperRoleSetTpl.html'},
        //组员数据权限设置tpl
        {fieldname : 'datapermissLayoutTpl',path : 'app/pages/permiss/managergroup/tpl/grouper/datapermissLayoutTpl.html'},
        {fieldname : 'yxbjCheckTpl',path : 'app/pages/permiss/usergroup/tpl/datapermissYxBjCheck.html'},
        {fieldname : 'yxqxOptionTpl',path : 'app/pages/permiss/usergroup/tpl/datapermissYxQxOption.html'}
    ],
    mixins : [
        //我的用户组
        'Pages.permiss.managergroup.MineGroupPage',
        //组员管理
        'Pages.permiss.managergroup.GrouperManagement',
        'Pages.permiss.managergroup.GrouperDataPermiss',
        'Pages.permiss.managergroup.GrouperDataPermissLd',
        'Pages.permiss.managergroup.GrouperXzDataPermissLd',
        'Pages.permiss.managergroup.GrouperMenuPermiss',
        //组角色管理
        'Pages.permiss.managergroup.GroupRoleManagement',
        //子用户组管理
        'Pages.permiss.managergroup.ChildrenGroupManagement'
    ],
    dataRequires  : [
        {fieldname : 'usergroupStsx' , key : 'usergroupStsx' },
        {fieldname : 'usergroupData' , key : 'queryMineGroups' }
    ],
    advanceDataRequires : [
        {fieldname : 'userStsx' , key : 'userStsx' }
    ],
    init : function(){
        var container = this.swc = new NS.container.SwitchContainer({
            items :[
                {
                    name : 'mineGroupPage',scope : this,init : this.initMineGroupPage//我管理的用户组
                },
                {
                    name : 'grouperManagement',scope : this,init : this.initGrouperManagement//组员管理
                },
                {
                    name : 'grouperDataPermiss',scope : this,init : this.initGrouperDataPermiss//组员数据权限设置(按班级)
                },
                {
                    name : 'grouperDataPermissLd',scope : this,init : this.initGrouperDataPermissLd//组员数据权限设置(按教学组织结构)
                },
                {
                    name : 'grouperXzDataPermissLd',scope : this,init : this.initGrouperXzDataPermissLd//组员数据权限设置(按行政组织结构)
                },
                {
                    name : 'grouperMenuPermiss',scope : this,init : this.initGrouperMenuPermiss//组员数据权限设置
                },
                {
                    name : 'groupRoleManagement',scope : this,init : this.initGroupRoleManagement//组角色管理
                },
                {
                    name : 'usergroupDataPermissSet',scope : this,init : this.initUserGroupDataPermissSet//用户组数据权限(按班级)
                },
                {
                    name : 'childrenGroupManager',scope : this,init : this.initChildrenGroupManager//子用户组管理
                }
            ]
        });
        this.setPageComponent(container);
    }
});