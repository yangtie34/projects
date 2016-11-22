NS.define('Pages.permiss.usergroup.UserGroupXzDataPermissLd',{
    initUserGroupXzDataPermissLdSet : function(swc,container,params){
        var me = this,
            group = this.group = params;
        this.callSingle({key : 'queryUsergroupDataPermissByXzzzjg',params : {usergroupId : group.id}},function(data){
            var allDataPermiss = data;
            this.getAdvanceData(['xzzzjg'],function(data){
                var zzjg = data.xzzzjg;
                var page = new Pages.permiss.base.DataPermissLd({
                    allDataPermiss : allDataPermiss,
                    layoutData : {mainTitle : '用户组管理',title : '设置组数据权限',others : group.groupname},//布局显示数据
                    zzjg : zzjg,
                    cancel : function(){
                        swc.switchTo('usergroupPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(jxzzjgIds){
                        me.doUpdateGroupXzDataPermissLd(group.id,jxzzjgIds,function(){
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
    doUpdateGroupXzDataPermissLd : function(usergroupId,jxzzjgIds,callback){
        this.doTipRequest({
            key : 'updateUsergroupDataPermissByXzzzjg',
            params : {usergroupId : usergroupId,allDataPermiss : jxzzjgIds},
            successMsg : '用户组数据权限设置成功!',
            failureMsg : '用户组数据权限设置失败!',
            callback : callback
        });
    }
});