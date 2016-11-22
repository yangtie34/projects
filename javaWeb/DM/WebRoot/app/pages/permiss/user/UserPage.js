NS.define('Pages.permiss.user.UserPage',{
    initUserPage : function(swc,container){
        var grid = this.getUserGrid(),
            layoutCom  = this.userLayout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.userLayoutTpl,
                renderNumber : 1,
                data : this.getUsernameAndBmmc()
            });
        var c = new NS.container.Container({
            layout : 'fit',
            style : {
                paddingRight : '18px'
            },
            items : grid
        });
        layoutCom.register(c,0);

        layoutCom.bindItemsEvent({
            'addUser.click' : {scope : this,fn : function(){
                this.addUser();
            }},
            'student.click' : {scope : this,fn : function(){this.doQuery(layoutCom);}},
            'teacher.click' : {scope : this,fn : function(){this.doQuery(layoutCom);}},
            'others.click' : {scope : this,fn : function(){this.doQuery(layoutCom);}},
            'query.click' : {fn : function(){
                grid.load({
                    'dxgx' : layoutCom.get$ByName('dxgx').val(),
                    'bmmc.like' : layoutCom.get$ByName('bmmc').val()
                });
            }},
            'disableUser.click' : {
                scope : this,
                fn : function(){
                var rows = grid.getSelectRows();
                if(rows.length == 0){
                    this.alert("提示","请您选中一行记录!");
                }else{
                    this.doDisableUserRequest(NS.Array.pluck(rows,'id'),function(){
                        grid.refresh();
                    });
                }
            }
        },
        'enableUser.click' : {
                scope : this,
                fn : function(){
                    var rows = grid.getSelectRows();
                    if(rows.length == 0){
                        this.alert("提示","请您选中一行记录!");
                    }else{
                        this.doEnableUserRequest(NS.Array.pluck(rows,'id'),function(){
                            grid.refresh();
                        });
                    }
                }
            }
        });
        this.add(container,layoutCom);
        grid.loadData(this.userData);
    },
    doQuery : function(layoutCom){
        var student = layoutCom.get$ByName('student')[0],
            teacher = layoutCom.get$ByName('teacher')[0],
            others = layoutCom.get$ByName('others')[0];
        this.updateServiceParams('queryUsers',{
            teacher : teacher.checked,
            student : student.checked,
            others : others.checked
        });
        this.userGrid.load();

    },
    getUserGrid : function(){
        var trandata = this.userGridTranData = NS.E2S(this.userStsx),
            grid = this.userGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                columnData : trandata,
                style : {
                    paddingRight : '2px'
                },
                multiSelect : true,
//                data : this.userData,
                serviceKey : 'queryUsers',
                fields : ['jsIds','jsmcs'],
                columnConfig : [
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo',
                        text : '操作列',
                        align : 'center',
                        width : 420,
                        links : [
                            {linkText : '修改' },
                            { linkText : '删除' },
                            { linkText : '设定角色' },
                            { linkText : '设定教学数据权限' },
                            { linkText : '设定行政数据权限' },
//                            { linkText : '设定班级数据权限' },
                            { linkText : '设定菜单权限' }
                        ]
                    },
                    {
                        name : 'jsmcs',
                        text : '角色名称',
                        width : 160,
                        index : 3,
                        align : 'center',
                        renderer : function(value,row,rowIndex,colIndex){
                            if(value!=''){
                                return value.join(',');
                            }else{
                                return value;
                            }
                        }
                    },{
                        name : 'yhly',
                        renderer : function(value,row,rowIndex,colIndex){
                            if(value == 1){
                                return "用户添加";
                            }else{
                                return "系统自动同步";
                            }
                        }
                    }
                ]
            });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                switch(txt){
                    case '修改':this.updateUser();break;
                    case '删除':this.doDeleteUserRequest(this.userGrid,function(){this.userGrid.refresh();});break;
                    case '设定角色' :  this.getRoleSetWindow();break;
//                    case '设定班级数据权限' :  this.selectGrid(this.userGrid,function(row){
//                        this.swc.switchTo('userDataPermissPage',row,true)
//                    });break;
                    case '设定行政数据权限' :  this.selectGrid(this.userGrid,function(row){
                        this.swc.switchTo('userXzDataPermissPage',row,true)
                    });break;
                    case '设定教学数据权限' :  this.selectGrid(this.userGrid,function(row){
                        this.swc.switchTo('userDataPermissLdPage',row,true)
                    });break;
                    case '设定菜单权限' : this.selectGrid(this.userGrid,function(row){
                        this.swc.switchTo('userMenuPermissPage',row,true)
                    });break;
                }
            }}
        });
        return grid;
    },
    /***************第二层*****************************************************************/
    addUser : function(){
        var form = this.getUserForm(),
            values = {},
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addUserTpl,
                renderNumber : 1,
                data : {title :'用户管理',desc : '新增用户',columnTitle : '用户内容'}
            });

        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doAddUserRequest(form.getValues(),function(){
                window.close();
                this.userGrid.refresh();
            })}},
            'cancel.click' : {fn : function(){window.close();}}
        });

        form.setValues(values);

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    updateUser : function(){
        this.selectGrid(this.userGrid,function(values){
            var form = this.getUserForm(),
                layoutCom = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.addUserTpl,
                    renderNumber : 1,
                    data : {title :'用户管理',desc : '修改用户',columnTitle : '用户内容'}
                });

            layoutCom.register(form,0);
            layoutCom.bindItemsEvent({
                'submit.click' : {scope : this,fn : function(){
                    if(form.isValid()){
                        this.doUpdateUserRequest(form.getValues(),function(){
                            window.close();
                            this.userGrid.refresh();
                        });
                    }
                    }
                   },
                'cancel.click' : {fn : function(){window.close();}}
            });

            values.spassword = values.password;
            form.setValues(values);

            var window = this.getBaseWindow({
                items : layoutCom.component
            });
            window.show();
        });
    },
    getUserForm : function(){
        var form = NS.form.EntityForm.create({
            data : this.userGridTranData,
            frame : false,
            formType : 'add',
            columns : 1,
            border : false,
            items : [
                'id',
                'ryId',
                'username',
                'loginname',
                new NS.form.field.Checkbox({fieldLabel : '是否默认密码',name : 'isDefaultPassword',boxLabel : '<span style="color: red;">默认密码123456</span>'}),
//                {name : 'password',inputType:'password'},
                {name :  'password',inputType : 'password'},
                new NS.form.field.Text({fieldLabel : '密码确认',name : 'spassword',inputType : 'password',width : 270,labelWidth : 70}),
                'bmId',
                'rylbId',
                'xxdm',
                'ghxh',
                'bmmc',
                'sfky'
            ]
        });
        form.bindItemsEvent({
            isDefaultPassword : {event : 'click',scope : this,fn : function(){
                var password = form.getField('password');
                var spassword = form.getField('spassword');
                var idDefault = form.getField('isDefaultPassword');
                if(idDefault.getValue()){
                    password.setReadOnly(true);
                    spassword.setReadOnly(true);
                    password.setValue('123456');
                    spassword.setValue('123456');
                }else{
                    password.setValue();
                    password.setReadOnly(false);
                    spassword.setValue();
                    spassword.setReadOnly(false);
                }
            }}
        });
        return form;
    },
    /**************************角色设定******************************/
    getRoleSetWindow : function(){
        this.selectGrid(this.userGrid,function(row){
            var jsIds = row.jsIds;
            this.callSingle('roleList',function(data){
                this.initUserRoleSet(jsIds,data.data,row)
            });
        });
    },
    generateRoleList : function(roleMap){
        var list = [];
        for(var i in roleMap){
            list.push({rolename : roleMap[i],roleId :i});
        }
        return list;
    },
    /********************数据请求**********************/
    doAddUserRequest : function(values,callback){
        if(values.password == values.spassword){
            if(values.password === ""){this.alert('提示','密码不能为空');return;}
            this.callSingle({key : 'queryUserByLoginName',params : {loginname : values.loginname}},function(ret){
                if(ret.success){
                    this.doTipRequest({
                        key : 'addUser',
                        params : values,
                        successMsg : '用户添加成功!',
                        failureMsg : '用户添加失败!',
                        callback : callback
                    });
                }else{
                    this.alert('提示','当前添加的用户名已经存在，请修改后重新保存！');
                }
            });
        } else{
            this.alert('提示','两次输入的密码必须一致！');
        }
    },
    doUpdateUserRequest : function(values,callback){
        this.doTipRequest({
            key : 'updateUser',
            params : values,
            successMsg : '用户修改成功!',
            failureMsg : '用户修改失败!',
            callback : callback
        });
    },
    doDeleteUserRequest : function(grid,callback){
        var select = grid.getRow(grid.getFirstSelectIndex()),
            yhly = select.yhly,
            userId = select.id;
        if(yhly == 1){
                this.doTipRequest({
                    key : 'deleteUser',
                    confirm : true,
                    confirmMsg : '您确定要删除选中的用户么？',
                    params : {userId : userId},
                    successMsg : '用户删除成功!',
                    failureMsg : '用户删除失败!',
                    callback : callback
                });
        }else{
            this.alert('提示','手动添加的用户才能够被删除，系统同步的用户不能被删除！');
        }
    },
    doDisableUserRequest : function(values,callback){
        this.doTipRequest({
            key : 'disableUser',
            params : {userIds : values},
            confirm : true,
            confirmMsg : '您确定要禁用选中的用户么？',
            successMsg : '用户禁用成功!',
            failureMsg : '用户禁用失败!',
            callback : callback
        });
    },
    doEnableUserRequest : function(values,callback){
        this.doTipRequest({
            key : 'enableUser',
            params : {userIds : values},
            confirm : true,
            confirmMsg : '您确定要启用选中的用户么？',
            successMsg : '用户启用成功!',
            failureMsg : '用户启用失败!',
            callback : callback
        });
    }
});