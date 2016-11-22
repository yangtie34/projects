NS.define('Pages.permiss.user.UserDataPermiss',{
    initUserDataPermissSet : function(swc,container,params){
        var me = this,
            user = params;
        this.callSingle({key : 'userDataPermiss',params : {userId : user.id}},function(data){
            var allDataPermiss = data.allDataPermiss;
            this.getAdvanceData(['jxzzjg'],function(data){
                var zzjg = data.jxzzjg;
                var page = new Pages.permiss.base.DataPermiss({
                    allDataPermiss : allDataPermiss,
                    layoutData : {mainTitle : '用户管理',title : '设置数据权限',others : (user.username||user.loginname)+"-"+user.bmmc+"-"+user.ghxh},//布局显示数据
                    zzjg : zzjg,
                    cancel : function(){
                        swc.switchTo('userPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(bjIds){
                        me.doUpdateUserDataPermiss(user.id,bjIds,function(){
                            swc.switchTo('userPage');
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
    doUpdateUserDataPermiss : function(userId,bjIds,callback){
        this.doTipRequest({
            key : 'updateUserUserGroupDataPermiss',
            params : {userId : userId,allDataPermiss : bjIds,exceptDataPermiss : []},
            successMsg : '用户数据权限设置成功!',
            failureMsg : '用户数据权限设置失败!',
            callback : callback
        });
    }
});