NS.define('Pages.permiss.RoleManager',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.MenuPermiss'],
    modelConfig : {
        serviceConfig : {
            'queryRoles' : "roleManager?queryRolesBygroupId",
            'deleteRole' : "roleManager?deleteRole",
            'addRole' : "roleManager?addRole",
            'updateRole' : "roleManager?updateRole",
            'updateRoleMenuPermiss' : "roleManager?updateRoleMenuPermiss",
            'roleStsx' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpRole'}},
            'allMenu' : {service : 'menuManager?queryAllMenus',params : {all : true}},
            'roleMenu' : 'roleManager?queryRoleMenuPermiss'
        }
    },
    addCssRequires : [ 'app/pages/permiss/user/css/user.css'],
    mixins : ['Pages.permiss.role.RolePage','Pages.permiss.role.RoleMenuSet','Pages.permiss.role.RoleContentSet'],
    dataRequires : [
        {fieldname : 'roleStsx' , key : 'roleStsx' },
        {fieldname : 'roleData' , key : 'queryRoles' }
    ],
    advanceDataRequires : [
        {fieldname : 'allMenu' , key : 'allMenu' }
    ],
    tplRequires : [
        {fieldname : 'roleLayoutTpl',path : 'app/pages/permiss/role/tpl/rolePageTpl.html'},
        {fieldname : 'addLayoutTpl',path : 'app/pages/permiss/menu/tpl/addMenuTpl.html'},

        {fieldname : 'menuSetPageTpl',path : 'app/pages/permiss/role/tpl/menuSetPage.html'},

        {fieldname : 'detailMenuTpl',path : 'app/pages/permiss/role/tpl/detailMenu.html'},
        {fieldname : 'childSystemMenuSetTpl',path : 'app/pages/permiss/role/tpl/childSystemMenuSet.html'},
        {fieldname : 'allChildSystemMenuTpl',path : 'app/pages/permiss/role/tpl/allChildSystemMenu.html'},

        {fieldname : 'contentSetTpl',path : 'app/pages/permiss/role/tpl/contentSetTpl.html'}

    ],
    init : function(){
        var container = this.swc = new NS.container.SwitchContainer({
            items :[
                {
                    name : 'rolePage',scope : this,init : this.initRoleCRUD
                },
                {
                    name : 'roleMenuSet',scope : this,init : this.initMenuSet
                }
            ]
        });
        this.setPageComponent(container);
    }
});