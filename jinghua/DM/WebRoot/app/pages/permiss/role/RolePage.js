NS.define('Pages.permiss.role.RolePage',{
    initRoleCRUD : function(swc,container){
        var grid = this.getRoleGrid(),
            layoutCom  = this.roleLayout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.roleLayoutTpl,
                renderNumber : 1,
                data :this.getRoleLayoutTplData()
            });

        layoutCom.register(this.getPackContainer({items : grid,style : {paddingRight : '12px'}}),0);

        layoutCom.bindItemsEvent({
            'addRole.click' : {scope : this,fn : function(){this.addRole();}},
            'query.click' : {fn : function(){grid.load({'jsmc.like' : layoutCom.get$ByName('roletext').val()});}}
        });
        this.add(container,layoutCom);
    },
    getRoleLayoutTplData : function(){
        return this.getUsernameAndBmmc();
    },
    getRoleGrid : function(){
        var trandata = this.roleGridTranData = NS.E2S(this.roleStsx),
            grid = this.roleGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                columnData : trandata,
                data : this.roleData,
                style : {
                    marginRight : '8px'
                },
                serviceKey : 'queryRoles',
                columnConfig : [
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo',
                        text : '操作列',
                        align : 'center',
                        width : 160,
                        links : [
                            {linkText : '修改' }, { linkText : '删除' },{ linkText : '设定功能权限' }
                        ]
                    }
                ]
            });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                switch(txt){
                    case '修改':this.updateRole();break;
                    case '删除':this.doDeleteRoleRequest(this.roleGrid,function(){this.roleGrid.refresh();});break;
                    case '设定功能权限' : this.selectGrid(grid,function(row){
                                                this.swc.switchTo('roleMenuSet',row,true);
                                            }); break;
                }
            }}
        });
        return grid;
    },
    /***************第二层*************/
    addRole : function(){
        var form = this.getRoleForm(),
            values = {},
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addLayoutTpl,
                renderNumber : 1,
                data :this.getAddRoleTplData()
            });

        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                    if(!form.isValid())return;
                    this.doAddRoleRequest(form.getValues(),function(){
                        window.close();
                        this.roleGrid.refresh();
                    });
            }},
            'cancel.click' : {fn : function(){window.close();}}
        });

        form.setValues(values);

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    getAddRoleTplData : function(){
        return {title :'角色管理',desc : '新增角色',columnTitle : '角色内容'};
    },
    getUpdateRoleTplData : function(){
        return {title :'角色管理',desc : '修改角色',columnTitle : '角色内容'};
    },
    updateRole : function(){
        this.selectGrid(this.roleGrid,function(values){
            var form = this.getRoleForm(),
                layoutCom = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.addLayoutTpl,
                    renderNumber : 1,
                    data : this.getUpdateRoleTplData()
                });

            layoutCom.register(form,0);
            layoutCom.bindItemsEvent({
                'submit.click' : {scope : this,fn : function(){
                    if(!form.isValid())return;
                    this.doUpdateRoleRequest(form.getValues(),function(){
                        window.close();
                        this.roleGrid.refresh();
                    });
                }},
                'cancel.click' : {fn : function(){window.close();}}
            });

            form.setValues(values);

            var window = this.getBaseWindow({
                items : layoutCom.component
            });
            window.show();
        });
    },
    deleteRole : function(){

    },
    getRoleForm : function(){
        var form = NS.form.EntityForm.create({
            data : this.roleGridTranData,
            frame : false,
            formType : 'add',
            columns : 1,
            border : false,
            items : [
                'id', 'jsmc', 'jslxId', 'usergroupId', 'jsms'
            ]
        });

        return form;
    },
    /********************数据请求**********************/
    doAddRoleRequest : function(values,callback){
        this.doTipRequest({
            key : 'addRole',
            params : values,
            successMsg : '角色添加成功!',
            failureMsg : '角色添加失败!',
            callback : callback
        });
    },
    doUpdateRoleRequest : function(values,callback){
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