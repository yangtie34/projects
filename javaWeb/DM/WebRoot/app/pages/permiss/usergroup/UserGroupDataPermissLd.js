NS.define('Pages.permiss.usergroup.UserGroupDataPermissLd',{
    initUserGroupDataPermissLdSet : function(swc,container,params){
        var me = this,
            group = this.group = params;
        this.callSingle({key : 'queryUsergroupDataPermissByJxzzjg',params : {usergroupId : group.id}},function(data){
            var allDataPermiss = data;
            this.getAdvanceData(['jzzjgWithOutBj'],function(data){
                var zzjg = data.jzzjgWithOutBj;
                var page = new Pages.permiss.base.DataPermissLd({
                    allDataPermiss : allDataPermiss,
                    layoutData : {mainTitle : '用户组管理',title : '设置组数据权限',others : group.groupname},//布局显示数据
                    zzjg : zzjg,
                    cancel : function(){
                        swc.switchTo('usergroupPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(jxzzjgIds){
                        me.doUpdateGroupDataPermissLd(group.id,jxzzjgIds,function(){
                            swc.switchTo('usergroupPage');
                            container.component.remove(page.getLibComponent());
                        });
                    }
                });
                page.on('pageready',function(){
                    var com = page.getLibComponent();
                    container.component.add(com);
                });
            });
        });

    },
    /*************************************数据请求*************************************************/
    doUpdateGroupDataPermissLd : function(usergroupId,jxzzjgIds,callback){
        this.doTipRequest({
            key : 'updateUsergroupDataPermissByJxzzjg',
            params : {usergroupId : usergroupId,allDataPermiss : jxzzjgIds},
            successMsg : '用户组数据权限设置成功!',
            failureMsg : '用户组数据权限设置失败!',
            callback : callback
        });
    }
});