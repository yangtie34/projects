/**
 * 组角色管理
 */
NS.define('Pages.permiss.managergroup.GroupRoleManagement',{
    /**
     * 初始化组角色管理
     */
    initGroupRoleManagement : function(swc,container,params){
        var rolePage = new Pages.permiss.RoleManager({
            modelConfig : {
                serviceConfig : {
                    'queryRoles' : {service : "roleManager?queryRolesBygroupId",params : {usergroupId : params.id}},
                    'deleteRole' : "roleManager?deleteRole",
                    'addRole' : "roleManager?addRole",
                    'updateRole' : "roleManager?updateRole",
                    'updateRoleMenuPermiss' : "roleManager?updateRoleMenuPermiss",
                    'roleStsx' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpRole'}},
                    'allMenu' : {service : 'menuManager?queryUsergroupMenus',params : {all : true,usergroupId : params.id}},
                    'roleMenu' : 'roleManager?queryRoleMenuPermiss'
                }
            },
            tplRequires : [
                {fieldname : 'roleLayoutTpl',path : 'app/pages/permiss/managergroup/tpl/role/rolePageTpl.html'},
                {fieldname : 'addLayoutTpl',path : 'app/pages/permiss/menu/tpl/addMenuTpl.html'},

                {fieldname : 'menuSetPageTpl',path : 'app/pages/permiss/managergroup/tpl/role/menuSetPage.html'},

                {fieldname : 'detailMenuTpl',path : 'app/pages/permiss/managergroup/tpl/role/detailMenu.html'},
                {fieldname : 'childSystemMenuSetTpl',path : 'app/pages/permiss/managergroup/tpl/role/childSystemMenuSet.html'},
                {fieldname : 'allChildSystemMenuTpl',path : 'app/pages/permiss/managergroup/tpl/role/allChildSystemMenu.html'},

                {fieldname : 'contentSetTpl',path : 'app/pages/permiss/managergroup/tpl/role/contentSetTpl.html'}

            ],
            getRoleLayoutTplData : function(){
                return {groupname : params.groupname};
            },
            getAddRoleTplData : function(){
                return {title :'组角色管理',desc : '新增组角色',columnTitle : '组角色内容'};
            },
            getUpdateRoleTplData : function(){
                return {title :'组角色管理',desc : '修改组角色',columnTitle : '组角色内容'};
            },
//            getRoleForm : function(){
//                var form = NS.form.EntityForm.create({
//                    data : this.roleGridTranData,
//                    frame : false,
//                    formType : 'add',
//                    columns : 1,
//                    border : false,
//                    items : [
//                        'id', 'jsmc', {name : 'jslxId',hidden  :true}, 'usergroupId', 'jsms'
//                    ]
//                });
//
//                return form;
//            },
            /********************数据请求**********************/
            doAddRoleRequest : function(values,callback){
                values.usergroupId = params.id;
                this.doTipRequest({
                    key : 'addRole',
                    params : values,
                    successMsg : '角色添加成功!',
                    failureMsg : '角色添加失败!',
                    callback : callback
                });
            },
            doUpdateRoleRequest : function(values,callback){
                values.usergroupId = params.id;
                this.doTipRequest({
                    key : 'updateRole',
                    params : values,
                    successMsg : '角色修改成功!',
                    failureMsg : '角色修改失败!',
                    callback : callback
                });
            },
            doDeleteRoleRequest : function(grid,callback){
                var select = grid.getRow(grid.getFirstSelectIndex()),
                    roleId = select.id;
                this.doTipRequest({
                    key : 'deleteRole',
                    confirm : true,
                    confirmMsg : '您确定要删除选中的角色么？',
                    params : {roleId : roleId},
                    successMsg : '角色删除成功!',
                    failureMsg : '角色删除失败!',
                    callback : callback
                });
            }
        });
        rolePage.on('pageready',function(){
            var com = rolePage.getLibComponent();
            container.component.add(com);
            rolePage.roleLayout.bindItemsEvent({
                'backToGroup.click' : {scope : this,fn : function(){
                    swc.switchTo('mineGroupPage');
                    container.component.remove(com);
                }}
            });
        });
    }
});