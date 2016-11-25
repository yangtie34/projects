/**
 * 组员管理
 */
NS.define('Pages.permiss.managergroup.GrouperDataPermiss',{
    initGrouperDataPermiss : function(swc,container,params){
        var me = this,
            user = this.user = params;
        this.callSingle({key : 'userDataPermiss',params : {userId : user.id,usergroupId : this.group.id}},function(data){
            var allDataPermiss = this.allDataPermiss = data.allDataPermiss;
            var zzjg = this.groupZzjg;//组的数据权限-绑定组织结构
            var page = new Pages.permiss.base.DataPermiss({
                allDataPermiss : allDataPermiss,
                layoutData : {mainTitle : '用户组组员管理',title : '设置组员数据权限',others : user.loginname+"-"+user.username},//布局显示数据
                zzjg : zzjg,
                cancel : function(){
                    swc.switchTo('grouperManagement');
                    container.component.remove(page.getLibComponent());
                },
                submit : function(bjIds){
                    me.doUpdateGrouperDataPermiss(user.id,me.group.id,bjIds,function(){
                        swc.switchTo('grouperManagement');
                        container.component.remove(page.getLibComponent());
                    });
                }
            });
            page.on('pageready',function(){
                var com = page.getLibComponent();
                container.component.add(com);
            });
        })
    },
    /*************************************数据请求*************************************************/
    doUpdateGrouperDataPermiss : function(userId,usergroupId,bjIds,callback){
        this.doTipRequest({
            key : 'updateGrouperDataPermiss',
            params : {userId : userId,usergroupId : usergroupId,allDataPermiss : bjIds,exceptDataPermiss : []},
            successMsg : '用户组数据权限设置成功!',
            failureMsg : '用户组数据权限设置失败!',
            callback : callback
        });
    }
});