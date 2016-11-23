NS.define('Pages.permiss.usergroup.UserGroupDataPermiss',{
    initUserGroupDataPermissSet : function(swc,container,params){
        var me = this,
            group = this.group = params;
        this.callSingle({key : 'queryUsergroupDataPermiss',params : {usergroupId : group.id}},function(data){
            var allDataPermiss = data.allDataPermiss;
            this.getAdvanceData(['jxzzjg'],function(data){
                var zzjg = data.jxzzjg;
                var page = new Pages.permiss.base.DataPermiss({
                    allDataPermiss : allDataPermiss,
                    layoutData : {mainTitle : '用户组管理',title : '设置组数据权限',others : group.groupname},//布局显示数据
                    zzjg : zzjg,
                    cancel : function(){
                        swc.switchTo('usergroupPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(bjIds){
                        me.doUpdateGroupDataPermiss(group.id,bjIds,function(){
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
    doUpdateGroupDataPermiss : function(usergroupId,bjIds,callback){
        this.doTipRequest({
            key : 'updateUsergroupDataPermiss',
            params : {usergroupId : usergroupId,allDataPermiss : bjIds,exceptDataPermiss : []},
            successMsg : '用户组数据权限设置成功!',
            failureMsg : '用户组数据权限设置失败!',
            callback : callback
        });
    }
});