/**
 * 组员管理
 */
NS.define('Pages.permiss.managergroup.GrouperManagement',{
    initGrouperManagement : function(swc,container,params){
        var group = this.group = params;
        this.updateServiceParams('queryUsersByUsergroupManager',{usergroupId : group.id,queryType : 2})
        this.callService([{key : 'queryUsersByUsergroupManager'},
                {key : 'roleList',params : {usergroupId : group.id,start : 0,limit : 200}},
                {key : 'usergroupDataZzjg',params : {usergroupId : group.id}},
                {key : 'queryUsergroupDataPermissBindJxzzjg',params : {usergroupId : group.id}},
                {key : 'queryUsergroupDataPermissBindXzzzjg',params : {usergroupId : group.id}},
                {key : 'queryUserGroupMenu',params : {usergroupId : group.id}}
            ],
            function(data){
             this.preparedChildGroupData(data);
                var layoutCom  = this.grouperManagmentLayout = new Pages.permiss.base.LayoutComponent({
                        layoutTpl : this.groupersManagerLayout,
                        renderNumber : 1,
                        data : {others : group.groupname}
                    }),
                    grid = this.getUsersGrid(data.queryUsersByUsergroupManager,layoutCom);
                layoutCom.register(grid,0);
                layoutCom.bindItemsEvent({
                    'backToMineGrouper.click':{scope : this,fn : function(){
                        swc.switchTo('mineGroupPage');
                        this.remove(container,layoutCom);
                    }},
                    'query.click' : {scope : this,fn : function(){
                        var dxgx = layoutCom.get$ByName('dxgx').val(),
                            bmmc = layoutCom.get$ByName('bmmc').val();
                        layoutCom.getComponent(0).load({
                            'dxgx' : layoutCom.get$ByName('dxgx').val(),
                            'bmmc.like' : layoutCom.get$ByName('bmmc').val()
                        });
                    }},
                    'yet.click' : {scope : this,fn : function(){
                           layoutCom.get$ByName('not')[0].checked = false;
                           layoutCom.get$ByName('all')[0].checked = false;
//                        this.usersGrid.load({queryType : 2});
                        this.updateServiceParams('queryUsersByUsergroupManager',{queryType : 2});
                        this.usersGrid.refresh();
                    }},
                    'not.click' : {scope : this,fn : function(){
                        layoutCom.get$ByName('yet')[0].checked = false;
                        layoutCom.get$ByName('all')[0].checked = false;
//                        this.usersGrid.load({queryType : 3});
                        this.updateServiceParams('queryUsersByUsergroupManager',{queryType : 3});
                        this.usersGrid.refresh();
                    }},
                    'all.click' : {scope : this,fn : function(){
                        layoutCom.get$ByName('yet')[0].checked = false;
                        layoutCom.get$ByName('not')[0].checked = false;
//                        this.usersGrid.load({queryType : 1});
                        this.updateServiceParams('queryUsersByUsergroupManager',{queryType : 1});
                        this.usersGrid.refresh();
                    }}
                });
                this.add(container,layoutCom);
        })

    },
    /**
     * 获取用户组表格
     * @returns {SimpleGrid}
     */
    getUsersGrid : function(data,layoutCom){
        this.getAdvanceData(['userStsx'],function(adata){
            var trandata = this.userGridTranData = NS.E2S(adata.userStsx),
                grid = this.usersGrid = new NS.grid.SimpleGrid({
                    proxy : this.model,
                    columnData : trandata,
                    data : data,
                    serviceKey : 'queryUsersByUsergroupManager',
                    fields : ['jsmcs','jsIds'],
                    columnConfig : [
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
                        },
                        {
                            xtype : 'linkcolumn',
                            name : 'caozuo',
                            text : '操作列',
                            align : 'center',
                            width : 420,
                            links : [
                                {linkText : '设定组角色' },
                                {linkText : '设定教学数据权限' },
//                                {linkText : '设定班级组数据权限' },
                                {linkText : '设定行政数据权限' },
                                {linkText : '设定组菜单权限' }
                            ]
                        },{

                        }
                    ]
                });
            grid.bindItemsEvent({
                caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                    switch(txt){
                        case '设定组角色':this.selectGrid(grid,function(row){
                                this.showRoleSetWindow(row);
                        });break;
                        case '设定教学数据权限' : this.selectGrid(grid,function(row){
                            this.swc.switchTo('grouperDataPermissLd',row,true);
                        });break;
                        case '设定行政数据权限' : this.selectGrid(grid,function(row){
                            this.swc.switchTo('grouperXzDataPermissLd',row,true);
                        });break;
//                        case '设定班级组数据权限' : this.selectGrid(grid,function(row){
//                            this.swc.switchTo('grouperDataPermiss',row,true);
//                        });break;
                        case '设定组菜单权限' : this.selectGrid(grid,function(row){
                            this.swc.switchTo('grouperMenuPermiss',row,true);
                        });break;
                    }
                }}
            });
            layoutCom.register(grid,0);
            return grid;
        });
    },
    /***********************************数据处理********************************************/
    preparedChildGroupData : function(data){
        this.roles = data.roleList.data;//当前角色列表
        this.groupZzjg = data.usergroupDataZzjg;//当前组对应数据权限的教学组织结构
        this.groupMenu = data.queryUserGroupMenu;
        this.usergroupZzjg = data.queryUsergroupDataPermissBindJxzzjg;//用户组数据权限（按教学组织结构）
        this.usergroupXzzzjg = data.queryUsergroupDataPermissBindXzzzjg;//用户组数据权限（按行政组织结构）
    },
    checkAllRoles : function(checked){
        for(var i in this.roleHash){
            this.roleHash[i].checked = checked;
        }
    },
    checkRole : function(roleId,checked){
        this.roleHash[roleId].checked = checked;
    },
    getRoleHashMap : function(userRoleIds){
        var hash = this.roleHash = {},
            roles = this.rolesTplData = NS.clone(this.roles);
        for(var i=0;i<roles.length;i++){
            hash[roles[i].id] = roles[i];
        }
        if(NS.isArray(userRoleIds)){
            for(var i=0;i<userRoleIds.length;i++){
                hash[userRoleIds[i]].checked = true;
            }
        }
        return hash;
    },
    isCheckAllRow : function(){
        var roles = this.rolesTplData,len = roles.length,role,cnum = 0;
        for(var i=0;i<len;i++){
            role = roles[i];
            if(role.checked)cnum++;
        }
        return len == cnum;
    },
    getUserRoleIds : function(){
        var roles = this.rolesTplData, roleIds = [];
        for(var i in roles){
            var role = roles[i];
            if(role.checked)roleIds.push(role.id);
        }
        return roleIds;
    },
/************************************************************第二层**********************************************************************/
    roleList : [],
    roleHash : {},
    showRoleSetWindow : function(row){
        var roleHash  = this.getRoleHashMap(row.jsIds);//所有角色rolemap  {id1 : role1}
        var isCheckAllRow = this.isCheckAllRow();//是否全选
        //用户拥有角色组件
        var ownerRoleCom = this.getOwnerRoleComponent(this.rolesTplData);
        var roleListCom = this.getRoleSetListComponent(this.rolesTplData,isCheckAllRow);
        //布局组件
        var layout  =  this.roleSetLayout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.grouperRoleSetTpl,
                renderNumber : 2,
                data : {username : row.username,bmmc : row.bmmc,ghxh : row.ghxh}
            });
        //绑定布局容器内部html元素事件
        layout.bindItemsEvent({
            'cancel.click' : {scope : this,fn : function(){window.close();}},
            'submit.click' : {scope : this,fn : function(){
                this.doUpdateUserRoles({
                    userId : row.id,
                    usergroupId : this.group.id,
                    roleIds : this.getUserRoleIds()
                },function(){
                    window.close();
                    this.usersGrid.refresh();
                });
            }}
        });

        layout.register(ownerRoleCom,0);
        layout.register(roleListCom,1);

        var window = this.getBaseWindow({
            items : layout.component
        });
        window.show();
    },
    /**
     * 获取用户拥有角色组件
     * @returns {Component}
     */
    getOwnerRoleComponent : function(ownerRoleMap){
        //用户拥有角色组件
        var ownerRoleCom = new NS.Component({
            tpl : new NS.Template('<h2><span class="wbh-common-title ">用户拥有的角色</span> </h2>'+
                '  <tpl for=".">'+
                '  <tpl if="checked == true"><span>{rolename}</span><a href="javascript:void(0);" class="permiss-outname-delete-bg" roleId="{roleId}"></a>'+
                ' </tpl></tpl>'),
            data : this.roles
        });
        ownerRoleCom.on('click',function(event,element){
            this.filterA(element,function(el){
                var roleId = $(el).attr('roleId');
                this.roleHash[roleId].checked = false;
                ownerRoleCom.refreshTplData(this.rolesTplData);
                //反选角色列表中的选择框
                this.roleSetLayout.get$ByAttribute('value',roleId)[0].checked = false;
                var ck = this.roleSetLayout.get$ByName('checkAll')[0].checked = false;

            });
        },this);
        return ownerRoleCom;
    },
    getRoleSetListComponent : function(data,checkAll){
        var com = new NS.Component({
            tpl : this.roleListSetTpl,
            data : {roles : data,checkAll : checkAll}
        });
        com.on('click',function(event,el){
            this.filterNode('INPUT',el,function(el){
                var ev = $(el),
                    checked = el.checked,
                    roleId = ev.attr('value'),
                    name = ev.attr('name');
                if(name == "checkAll"){
                    this.checkAllRoles(checked);
                }else{
                    this.checkRole(roleId,checked);
                }
                com.refreshTplData({roles : data,checkAll: this.isCheckAllRow()});
            });
        },this);
        return com;
    },
    /*************************请求处理**************************/
    doUpdateUserRoles : function(params,callback){
        this.doTipRequest({
            key : 'updateUsergroupRoles',
            params : params,
            successMsg : '设定用户组角色成功!',
            failureMsg : '设定用户组角色失败!',
            callback : callback
        });
    }
});