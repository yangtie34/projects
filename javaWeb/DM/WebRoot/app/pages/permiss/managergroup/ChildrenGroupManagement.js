/**
 * 用户组-子用户组管理
 */
NS.define('Pages.permiss.managergroup.ChildrenGroupManagement',{

    initChildrenGroupManager : function(swc,container,params){
        this.pgroup = pgroup = params;
        var up = {usergroupId : pgroup.id};
        var page = new Pages.permiss.UserGroupManager({
            tplRequires : [
                {fieldname : 'usergroupLayoutTpl',path : 'app/pages/permiss/managergroup/tpl/childrengroup/usergroupPage.html'},
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
            modelConfig : {
                serviceConfig : {
                    usergroupStsx : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpUsergroup'}},
                    queryChildGroup :{service :  'usergroupManager?queryChildrenUsergroup',params : {pgroupId : pgroup.id}},
                    addUsergroup : {service : 'usergroupManager?addUsergroup',params : {pgroupId : pgroup.id}},
                    updateUsergroup : 'usergroupManager?updateUsergroup',
                    deleteUsergroup : 'usergroupManager?deleteUsergroup',
                    queryUserExceptUserIds : {service : 'userManager?queryUserExceptUserIds'},
                    updateUsergroupManager : {service : 'usergroupManager?updateUsergroupManager',params: up},
                    //功能权限设置
                    allMenu : {service : 'menuManager?queryUsergroupMenus',params : {usergroupId : pgroup.id,all : true}},
                    usergroupMenu : {service : 'menuManager?queryUsergroupMenuPermiss'},
                    updateGroupMenu : {service :  'menuManager?updateUsergroupMenuPermiss'},
                    //数据权限设置
                    queryJxzzjg : {service :'helpManager?queryJxzzjgWithGrouperDataPermiss',params : up},
                    queryXzzzjg : {service : 'helpManager?queryXzzzjg',params : up},//查询行政组织结构权限
                    queryJxzzjgWithOutBj : { service : 'usergroupManager?queryUsergroupDataPermissBindJxzzjg',params : up},//查询教学组织结构节点-排除班级
                    updateUsergroupDataPermiss : 'usergroupManager?updateUsergroupDataPermiss',
                    queryUsergroupDataPermiss : {service : 'usergroupManager?queryUsergroupDataPermiss'},
                    deleteUserOldMenuPermissInUserGroup : 'menuManager?deleteUserOldMenuPermissInUserGroup',
                    //按教学组织结构设置数据权限
                    queryUsergroupDataPermissByJxzzjg : 'usergroupManager?queryUsergroupDataPermissByJxzzjg',
                    updateUsergroupDataPermissByJxzzjg : {service :  'usergroupManager?updateUsergroupDataPermissAndSaveWidthExactValue',params : {pgroupId : pgroup.id}},
                    //按行政组织结构修正数据权限
                    queryUsergroupDataPermissByXzzzjg : 'usergroupManager?queryUsergroupDataPermissByXzzzjg',//按行政组织结构查询数据权限
                    updateUsergroupDataPermissByXzzzjg :{service :   'usergroupManager?updateUsergroupDataPermissByXzzzjg',params:{pgroupId : pgroup.id}}//按行政组织结构修改数据权限


                }
            },
            getUsergroupLayoutData : function(){
                return {groupname : pgroup.groupname};
            },
            //重写form
            getUsergroupForm : function(){
                var form = NS.form.EntityForm.create({
                    data : this.usergroupGridTranData,
                    frame : false,
                    formType : 'add',
                    values : {pgroupId : pgroup.id},
                    columns : 1,
                    border : false
                });
                return form;
            }
        });
        page.on('pageready',function(){
            var com = page.getLibComponent();
            container.component.add(com);
            page.usergroupLayout.bindItemsEvent({
                'backToGroup.click' : {scope : this,fn : function(){
                    swc.switchTo('mineGroupPage');
                    container.component.remove(com);
                }}
            });
        });
    }
});