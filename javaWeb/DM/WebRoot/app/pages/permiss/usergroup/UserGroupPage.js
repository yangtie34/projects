NS.define('Pages.permiss.usergroup.UserGroupPage',{
    initUserGroupPage : function(swc,container){
        var grid = this.getUserGroupGrid(),
            layoutCom  = this.usergroupLayout = new Pages.permiss.base.LayoutComponent({
            layoutTpl : this.usergroupLayoutTpl,
            renderNumber : 1,
            data : this.getUsergroupLayoutData()
        });
        layoutCom.register(this.getPackContainer({items : grid,style : {paddingRight : '18px'}}),0);

        layoutCom.bindItemsEvent({
            'addUsergroup.click' : {scope : this,fn : this.addUsergroup},
            'query.click' : {scope : this, fn : function(){
                var value = layoutCom.get$ByName("usergrouptext").val();
                grid.load({'groupname.like' : value});
            }}
        });

        this.add(container,layoutCom);
    },
    getUsergroupLayoutData : function(){
        return this.getUsernameAndBmmc();
    },
    /**
     * 获取用户组表格
     * @returns {SimpleGrid}
     */
    getUserGroupGrid : function(){
        var trandata = this.usergroupGridTranData = NS.E2S(this.usergroupStsx),
            grid = this.usergroupGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                columnData : trandata,
                data : this.usergroupData,
                serviceKey : 'queryChildGroup',
                fields : ['managers'],
                columnConfig : [
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo',
                        text : '操作列',
                        align : 'center',
                        width : 460,
                        links : [
                            {linkText : '修改' },
                            {linkText : '删除' },
                            { linkText : '设置组管理员' },
                            { linkText : '设置功能权限' },
                            { linkText : '设定教学数据权限' },
//                            { linkText : '设定班级数据权限' },
                            { linkText : '设定行政数据权限' }

                        ]
                    },{

                    }
                ]
            });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                switch(txt){
                    case '修改':this.updateUsergroup(grid);break;
                    case '删除' : this.doDeleteUsergroupRequest(grid,function(){grid.refresh();});break;
                    case '设置组管理员':this.selectGrid(grid,function(row){
                                                this.swc.switchTo('usergroupManagerSet',row,true);
                                            });break;
                    case '设置功能权限' :  this.selectGrid(grid,function(row){
                                                this.swc.switchTo('usergroupMenuSet',row,true);
                                            });break;
//                    case '设定班级数据权限' : this.selectGrid(grid,function(row){
//                                                this.swc.switchTo('usergroupDataPermissSet',row,true);
//                                            });break;
                    case '设定教学数据权限' : this.selectGrid(grid,function(row){
                        this.swc.switchTo('usergroupDataPermissLdSet',row,true);
                    });break;
                    case '设定行政数据权限' : this.selectGrid(grid,function(row){
                        this.swc.switchTo('usergroupXzDataPermissLdSet',row,true);
                    });break;
                }
            }}
        });
        return grid;
    },
    addUsergroup : function(){
        var form = this.getUsergroupForm(),
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addUsergroupTpl,
                renderNumber : 1,
                data : {title :'用户组管理',desc : '新增用户组',columnTitle : '用户组内容'}
            });

        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doAddUsergroupRequest(form.getValues(),function(){
                    window.close();
                    this.usergroupGrid.refresh();
                })}},
            'cancel.click' : {fn : function(){window.close();}}
        });

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    updateUsergroup : function(grid){
        this.selectGrid(grid,function(values){
            var form = this.getUsergroupForm(),
                layoutCom = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.addUsergroupTpl,
                    renderNumber : 1,
                    data : {title :'用户组管理',desc : '修改用户组',columnTitle : '用户组内容'}
                });

            layoutCom.register(form,0);
            layoutCom.bindItemsEvent({
                'submit.click' : {scope : this,fn : function(){
                    if(!form.isValid())return;
                    this.doUpdateUsergroupRequest(form.getValues(),function(){
                        window.close();
                        grid.refresh();
                    })}},
                'cancel.click' : {fn : function(){window.close();}}
            });

            form.setValues(values);

            var window = this.getBaseWindow({
                items : layoutCom.component
            });
            window.show();
        });
    },
    /****************************第二层********************************/
    getUsergroupForm : function(){
        var form = NS.form.EntityForm.create({
            data : this.usergroupGridTranData,
            frame : false,
            formType : 'add',
            columns : 1,
            border : false
        });
        return form;
    },
    /****************************处理请求**************************/
    doAddUsergroupRequest : function(values,callback){
        this.doTipRequest({
            key : 'addUsergroup',
            params : values,
            successMsg : '用户组添加成功!',
            failureMsg : '用户组添加失败!',
            callback : callback
        });
    },
    doUpdateUsergroupRequest : function(values,callback){
        this.doTipRequest({
            key : 'updateUsergroup',
            params : values,
            successMsg : '用户组修改成功!',
            failureMsg : '用户组修改失败!',
            callback : callback
        });
    },
    doDeleteUsergroupRequest : function(grid,callback){
        var select = grid.getRow(grid.getFirstSelectIndex()),
            usergroupId = select.id;
            this.doTipRequest({
                key : 'deleteUsergroup',
                confirm : true,
                confirmMsg : '您确定要删除选中的用户组么？',
                params : {usergroupId : usergroupId},
                successMsg : '用户组删除成功!',
                failureMsg : '用户组删除失败!',
                callback : callback
            });
    }
});