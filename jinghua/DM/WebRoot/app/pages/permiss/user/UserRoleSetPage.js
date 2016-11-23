NS.define('Pages.permiss.user.UserRoleSetPage',{
    initUserRoleSet : function(ownerIds,roles,row){
        var me = this,
            roleHash = this.getRoleHash(roles,row.jsIds),
            ownerRoleCom = this.getOwnerRoleComponent(roles,roleHash),
            roleListCom = this.getRoleListComponent(roles,roleHash),
            layout = this.getRoleSetLayout(ownerRoleCom,roleListCom,roles,row);
        this.user = row;//当前用户
        this.refreshUserRolesStatus = function(){ownerRoleCom.refreshTplData(roles);roleListCom.refreshTplData(roles);}//刷新组件数据

    },
    getRoleSetLayout : function(ownerRole,roleList,roles,row){
            //布局组件
            var me = this,
                layout  = this.roleSetLayout = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.userRoleTpl,
                    renderNumber : 2,
                    data : {username : row.username,bmmc : row.bmmc,ghxh : row.ghxh}
                });
            //组件注册进入布局容器中
            layout.register(ownerRole,0);
            layout.register(roleList,1);
            //绑定布局容器内部html元素事件
            layout.bindItemsEvent({
                'cancel.click' : {scope : this,fn : function(){window.close();}},
                'submit.click' : {scope : this,fn : function(){
                    this.doUpdateUserRoles({
                        userId : row.id,
                        roleIds : this.getUserRoleIds(roles)
                    },function(){
                        this.userGrid.refresh();
                        window.close();
                    });
                }}
            });
            var window = this.getBaseWindow({
                maxHeight : 580,
                autoScroll : true,
                items : layout.component
            });
            window.show();
            return layout;
        },
    getRoleListComponent : function(roles,rolehash){
        var me = this,
            component = new NS.Component({
                tpl : this.roleListTpl,
                data : roles
            });
        component.on('click',function(event,el){
            this.filterNode('INPUT',el,function(e){
                var ev = $(e),
                    checked = e.checked,
                    ctype = ev.attr('ctype'),
                    itemId = ev.attr('itemId');
                if(ctype == "checkAll"){
                    me.checkAllRoles(roles,checked);
                }else{
                    rolehash[itemId].checked = checked;
                }
                me.refreshUserRolesStatus();
            });
        },this);
        return component;
    },
    getOwnerRoleComponent : function(roles,rolehash){
        var me = this,
            component = new NS.Component({
            tpl : new NS.Template('<h2><span class="wbh-common-title ">用户拥有的角色</span> </h2>'+
                '  <tpl for=".">' +
                    '<tpl if="checked == true">' +
                        '<span>{jsmc}</span><a href="javascript:void(0);" class="permiss-outname-delete-bg" roleId="{id}"></a>'+
                    '</tpl>'+
                ' </tpl>'),
            data : roles
        });
        component.on('click',function(event,element){
            this.filterA(element,function(el){
                var roleId = $(el).attr('roleId');
                //移除我拥有的角色
                rolehash[roleId].checked = false;
                me.refreshUserRolesStatus();
            });
        },this);
        return component;
    },
    refreshUserRolesStatus : function(){},
    /***********************数据处理********************************/
    /**
     * 获取用户角色ID集合
     * @param roles
     * @returns {Array}
     */
    getUserRoleIds  : function(roles){
        var roleIds = [];
        for(var i =0;i<roles.length;i++){
            if(roles[i].checked)roleIds.push(roles[i].id);
        }
        return roleIds;
    },
    /**
     * 选所有角色
     * @param roles
     * @param checked
     */
    checkAllRoles : function(roles,checked){
         for(var i=0;i<roles.length;i++){
             roles[i].checked = checked;
         }
    },
    getRoleHash : function(roles,jsIds){
        var roleMap = {};
        for(var i=0;i<roles.length;i++){
            var role = roles[i];
            roleMap[role.id] = role;
        }
        for(var i=0;i<jsIds.length;i++){
            if(roleMap[jsIds[i]])
               roleMap[jsIds[i]].checked = true;
        }
        return roleMap;
    },
    /*************************请求处理**************************/
    doUpdateUserRoles : function(params,callback){
        this.doTipRequest({
            key : 'updateUsergroupRoles',
            params : params,
            successMsg : '设定用户角色成功!',
            failureMsg : '设定用户角色失败!',
            callback : callback
        });
    }
});