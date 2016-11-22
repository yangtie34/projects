NS.define('Pages.permiss.user.UserXzDataPermissLd',{
    initUserXzDataPermissSet : function(swc,container,params){
        var me = this,
            user = params;
        this.callSingle({key : 'queryUserUsergroupDataPermissByXzzzjg',params : {userId : user.id}},function(data){
            var allDataPermiss = data;
            this.getAdvanceData(['xzzzjg'],function(data){
                var zzjg = data.xzzzjg;
                var page = new Pages.permiss.base.DataPermissLd({
                    allDataPermiss : allDataPermiss,
                    layoutData : {mainTitle : '用户管理',title : '设置行政数据权限',others : (user.username||user.loginname)+"-"+user.bmmc+"-"+user.ghxh},//布局显示数据
                    zzjg : zzjg,
                    cancel : function(){
                        swc.switchTo('userPage');
                        container.component.remove(page.getLibComponent());
                    },
                    submit : function(jxzzjgIds){
                        me.doUpdateUserXzDataPermissLd(user.id,jxzzjgIds,function(){
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
    /**
     * 执行粒度到组织结构节点的数据权限设置
     */
    doUpdateUserXzDataPermissLd : function(userId,zzjgIds,callback){
        this.doTipRequest({
            key : 'updateUserUsergroupDataPermissByXzzzjg',
            params : {userId : userId,allDataPermiss : zzjgIds},
            successMsg : '用户数据权限设置成功!',
            failureMsg : '用户数据权限设置失败!',
            callback : callback
        });
    }
});