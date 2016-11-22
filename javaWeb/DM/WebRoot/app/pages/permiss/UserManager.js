NS.define('Pages.permiss.UserManager',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.DataPermiss','Pages.permiss.base.MenuPermiss','Pages.permiss.base.DataPermissLd'],
    modelConfig : {
        serviceConfig : {
            'queryUsers' : {service : "usergroupManager?queryUsersByUsergroup",params : {teacher : true,student : true,others : true}},
            'deleteUser' : "userManager?deleteUser",
            'addUser' : "userManager?addUser",
            'updateUser' : "userManager?updateUser",
            'updateUsergroupRoles' : "userManager?updateUsergroupRoles",//修改用户组角色
            'queryUserByLoginName' : 'userManager?queryUserByLoginName',//根据登录名查询用户
            'userStsx' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpUser'}},
            'roleList' : {service : 'roleManager?queryRolesBygroupId',params : {start : 0,limit : 200}},
            'updateUsergroupRoles' : {service : 'userManager?updateUsergroupRoles'},
            'disableUser' : {service : 'userManager?updateDisableUser'},
            'enableUser' : {service : 'userManager?updateEnableUser'},
            //数据权限
            queryJxzzjg : 'helpManager?queryJxzzjg',
            queryXzzzjg : 'helpManager?queryXzzzjg',//查询行政组织结构权限
            queryJxzzjgWithOutBj : 'helpManager?queryJxzzjgWithOutBj',//查询教学组织结构节点-排除班级
            updateUserUserGroupDataPermiss : 'userManager?updateUserUsergroupDataPermiss',//修改用户-顶级用户组数据权限
            userDataPermiss : 'userManager?queryUserUsergroupDataPermiss',//查询用户-顶级组数据权限
            queryUsergroupDataPermiss : 'usergroupManager?queryUsergroupDataPermiss',//查询用户组数据权限

            //按教学组织结构修正数据权限
            queryUserUsergroupDataPermissByJxzzjg : 'userManager?queryUserUsergroupDataPermissByJxzzjg',//按教学组织结构查询数据权限
            updateUserUsergroupDataPermissByJxzzjg : 'userManager?updateUserUsergroupDataPermissByJxzzjg',//按教学组织结构修改数据权限
            //按行政组织结构修正数据权限
            queryUserUsergroupDataPermissByXzzzjg : 'userManager?queryUserUsergroupDataPermissByXzzzjg',//按行政组织结构查询数据权限
            updateUserUsergroupDataPermissByXzzzjg : 'userManager?updateUserUsergroupDataPermissByXzzzjg',//按行政组织结构修改数据权限

            //查询用户菜单权限
            'allMenu' : {service : 'menuManager?queryAllMenus',params : {all : true}},
            updateUserMenuPermiss : 'userManager?updateUserUsergroupMenuPermiss',
            queryUserUsergroupMenuPermiss : 'userManager?queryUserUsergroupMenuPermiss'

        }
    },
    addCssRequires : [ 'app/pages/permiss/user/css/user.css'],
    mixins : ['Pages.permiss.user.UserPage',
        'Pages.permiss.user.UserRoleSetPage',
        'Pages.permiss.user.UserDataPermiss',
        'Pages.permiss.user.UserDataPermissLd',
        'Pages.permiss.user.UserMenuPermiss',
        'Pages.permiss.user.UserXzDataPermissLd'],
    dataRequires : [
        {fieldname : 'userStsx' , key : 'userStsx' },
        {fieldname : 'userData' , key : 'queryUsers' }
    ],
    advanceDataRequires : [
        {fieldname : 'jxzzjg',key : 'queryJxzzjg'},
        {fieldname : 'allMenu' , key : 'allMenu' },
        {fieldname : 'jzzjgWithOutBj', key :'queryJxzzjgWithOutBj'},
        {fieldname : 'xzzzjg', key :'queryXzzzjg'}
    ],
    tplRequires : [
        {fieldname : 'addUserTpl',path : 'app/pages/permiss/base/tpl/baseFormTpl.html'},
        {fieldname : 'userLayoutTpl',path : 'app/pages/permiss/user/tpl/userPage.html'},
        //角色设置
        {fieldname : 'roleListTpl',path : 'app/pages/permiss/user/tpl/roleList.html'},
        {fieldname : 'userRoleTpl',path : 'app/pages/permiss/user/tpl/userRoleSet.html'}

    ],
    init : function(){
        var container = this.swc = new NS.container.SwitchContainer({
            items :[
                {
                    name : 'userPage',scope : this,init : this.initUserPage
                },
                {
                    name : 'userRolePage',scope : this,init : this.initUserRoleSet
                },
                {
                    name : 'userDataPermissPage',scope : this,init : this.initUserDataPermissSet
                },
                {
                    name : 'userXzDataPermissPage',scope : this,init : this.initUserXzDataPermissSet
                },
                {
                    name : 'userDataPermissLdPage',scope : this,init : this.initUserDataPermissLdSet
                },
                {
                    name : 'userMenuPermissPage',scope : this,init : this.initUserMenuPermissSet
                }
            ]
        });
        this.setPageComponent(container);
    }
});