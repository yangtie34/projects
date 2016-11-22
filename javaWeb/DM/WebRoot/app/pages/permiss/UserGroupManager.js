NS.define('Pages.permiss.UserGroupManager',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.MenuPermiss','Pages.permiss.base.DataPermiss','Pages.permiss.base.DataPermissLd'],
    modelConfig : {
        serviceConfig : {
            usergroupStsx : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpUsergroup'}},
            queryChildGroup : 'usergroupManager?queryChildrenUsergroup',
            addUsergroup : 'usergroupManager?addUsergroup',
            updateUsergroup : 'usergroupManager?updateUsergroup',
            deleteUsergroup : 'usergroupManager?deleteUsergroup',
            queryUserExceptUserIds : 'userManager?queryUserExceptUserIds',
            updateUsergroupManager : 'usergroupManager?updateUsergroupManager',
            //功能权限设置
            allMenu : {service : 'menuManager?queryAllMenus',params : {all : true}},
            usergroupMenu : 'menuManager?queryUsergroupMenuPermiss',
            updateGroupMenu : 'menuManager?updateUsergroupMenuPermiss',
            //数据权限设置
            queryJxzzjg : 'helpManager?queryJxzzjg',
            queryXzzzjg : 'helpManager?queryXzzzjg',//查询行政组织结构权限
            queryJxzzjgWithOutBj : 'helpManager?queryJxzzjgWithOutBj',//查询教学组织结构节点-排除班级
            updateUsergroupDataPermiss : 'usergroupManager?updateUsergroupDataPermiss',
            queryUsergroupDataPermiss : {service : 'usergroupManager?queryUsergroupDataPermiss'},
            deleteUserOldMenuPermissInUserGroup : 'menuManager?deleteUserOldMenuPermissInUserGroup',
            //按教学组织结构设置数据权限
            queryUsergroupDataPermissByJxzzjg : 'usergroupManager?queryUsergroupDataPermissByJxzzjg',
            updateUsergroupDataPermissByJxzzjg : 'usergroupManager?updateUsergroupDataPermissByJxzzjg',
            //按行政组织结构修正数据权限
            queryUsergroupDataPermissByXzzzjg : 'usergroupManager?queryUsergroupDataPermissByXzzzjg',//按行政组织结构查询数据权限
            updateUsergroupDataPermissByXzzzjg : 'usergroupManager?updateUsergroupDataPermissByXzzzjg'//按行政组织结构修改数据权限


        }
    },
    addCssRequires : [ 'app/pages/permiss/user/css/user.css'],
    mixins : ['Pages.permiss.usergroup.UserGroupPage','Pages.permiss.usergroup.UserGroupContentSet',
                'Pages.permiss.usergroup.UserGroupManagerSetPage','Pages.permiss.usergroup.UserGroupMenuSet',
                'Pages.permiss.usergroup.UserGroupDataPermiss','Pages.permiss.usergroup.UserGroupDataPermissLd',
                'Pages.permiss.usergroup.UserGroupXzDataPermissLd'],
    dataRequires : [
        {fieldname : 'usergroupStsx' , key : 'usergroupStsx' },
        {fieldname : 'usergroupData' , key : 'queryChildGroup' }
    ],
    advanceDataRequires : [
        {fieldname : 'allMenu' , key : 'allMenu' },
        {fieldname : 'jxzzjg',key : 'queryJxzzjg'},
        {fieldname : 'jzzjgWithOutBj', key :'queryJxzzjgWithOutBj'},
        {fieldname : 'xzzzjg', key :'queryXzzzjg'}
    ],
    tplRequires : [
        {fieldname : 'usergroupLayoutTpl',path : 'app/pages/permiss/usergroup/tpl/usergroupPage.html'},
        {fieldname : 'addUsergroupTpl',path : 'app/pages/permiss/base/tpl/baseFormTpl.html'},
        {fieldname : 'groupManagerTpl',path : 'app/pages/permiss/usergroup/tpl/groupManagerSet.html'},
        {fieldname : 'usergroupMenuSetTpl',path : 'app/pages/permiss/usergroup/tpl/usergroupMenuSetPage.html'},

        {fieldname : 'detailMenuTpl',path : 'app/pages/permiss/role/tpl/detailMenu.html'},
        {fieldname : 'childSystemMenuSetTpl',path : 'app/pages/permiss/role/tpl/childSystemMenuSet.html'},
        {fieldname : 'allChildSystemMenuTpl',path : 'app/pages/permiss/role/tpl/allChildSystemMenu.html'},

        {fieldname : 'contentSetTpl',path : 'app/pages/permiss/role/tpl/contentSetTpl.html'},

        {fieldname : 'datapermissLayoutTpl',path : 'app/pages/permiss/usergroup/tpl/datapermissLayoutTpl.html'},
        {fieldname : 'yxbjCheckTpl',path : 'app/pages/permiss/usergroup/tpl/datapermissYxBjCheck.html'},
        {fieldname : 'yxqxOptionTpl',path : 'app/pages/permiss/usergroup/tpl/datapermissYxQxOption.html'}


    ],
    init : function(){
        var container = this.swc = new NS.container.SwitchContainer({
            items :[
                {
                    name : 'usergroupPage',scope : this,init : this.initUserGroupPage
                },
                {
                    name : 'usergroupManagerSet',scope : this,init : this.initUserGroupManagerSetPage
                },
                {
                    name : 'usergroupMenuSet',scope : this,init : this.initMenuSet
                },
                {
                    name : 'usergroupDataPermissSet',scope : this,init : this.initUserGroupDataPermissSet
                },
                {
                    name : 'usergroupDataPermissLdSet',scope : this,init : this.initUserGroupDataPermissLdSet
                },
                {
                    name : 'usergroupXzDataPermissLdSet',scope : this,init : this.initUserGroupXzDataPermissLdSet
                }
            ]
        });
        this.setPageComponent(container);
    }
});