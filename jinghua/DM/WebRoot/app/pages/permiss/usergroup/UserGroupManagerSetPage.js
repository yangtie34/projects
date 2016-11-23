NS.define('Pages.permiss.usergroup.UserGroupManagerSetPage',{
    managers : [],//管理员列表
    exceptRyIds : [],//需要排除的人员ID
    initUserGroupManagerSetPage : function(swc,container,params){
        this.group = params;
        this.managers = params.managers ;
        this.managerIds = NS.Array.pluck(this.managers,'id');
//        if(!this.groupManagerSetLayout){
            var  layoutCom  = this.groupManagerSetLayout = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.groupManagerTpl,
                    renderNumber : 3,
                    data : {groupname : params.groupname}
                 }),
                 managerCom = this.managerCom = this.getManagerComponent(this.managers),
                 teacherGrid = this.teacherGrid = this.getExceptTeacherGrid(this.managerIds),
                 managerGrid =  this.managerGrid = this.getManagerGrid(this.managers);

            layoutCom.register(managerCom,0);//将当前管理员组件注册进入布局中
            layoutCom.register(teacherGrid,1);
            layoutCom.register(managerGrid,2);

            layoutCom.bindItemsEvent({
                  "cancel.click" : {scope : this,fn : function(){this.swc.switchTo('usergroupPage');this.remove(container,layoutCom);}},
                  "query1.click" : {scope : this,fn : function(){
                         var loginname = layoutCom.get$ByName('loginname1').val();
                         var bmmc = layoutCom.get$ByName('bmmc1').val();
                         var username = layoutCom.get$ByName('username1').val();
                         this.teacherGrid.load({"bmmc.like" : bmmc,"username.like" : username,'loginname.like':loginname,userIds : this.managerIds});
                  }},
                  "submit.click" : {scope : this,fn : function(){
                         this.doUpdateGroupManagerRequest({usergroupId : this.group.id,usermanagers : this.managerIds },function(){
                             this.swc.switchTo('usergroupPage');
                             this.usergroupGrid.refresh();
                             this.remove(container,layoutCom);
                         });
                  }}
            });
            this.add(container,layoutCom);
            this.managerGrid.loadData(this.managers);
            this.managerCom.refreshTplData(this.managers);
            this.teacherGrid.load({userIds : this.managerIds});
//        }else{
//            this.managerGrid.loadData(this.managers);
//            this.managerCom.refreshTplData(this.managers);
//            this.teacherGrid.load({userIds : this.managerIds});
//            this.managerCom.refreshTplData(this.managers);
//        }
    },
    /*************************对表格用户做处理**************************************/
    /**
     * 获取用户拥有角色组件
     * @returns {Component}
     */
    getManagerComponent : function(managers){
        //用户拥有角色组件
        var managerCom = new NS.Component({
            tpl : new NS.Template('<h2><span class="wbh-common-title ">当前组管理员</span> </h2>'+
                '  <tpl for=".">'+
                '  <span>登录名:{loginname}-姓名:{username}-部门:{bmmc}</span><a href="javascript:void(0);"  class="permiss-outname-delete-bg" userId="{id}"></a>'+
                ' </tpl>'),
            data : managers
        });
        managerCom.on('click',function(event,element){
            this.filterA(element,function(el){
                var id = $(el).attr('userId');
                //移除我拥有的角色
                this.managerIds = NS.Array.remove(this.managerIds,id);
                for(var i=0;i<this.managers.length;i++){
                    var manager = this.managers[i];
                    if(manager.id == id){
                       this.managers = NS.Array.remove(this.managers,manager);
                       break;
                    }
                }
                this.managerGrid.loadData(this.managers);
                this.managerCom.refreshTplData(this.managers);
                this.teacherGrid.load({userIds : this.managerIds});
                managerCom.refreshTplData(this.managers);

            });
        },this);
        return managerCom;
    },
    /**
     * 获取排除管理员的教职工的表格列表
     */
    getExceptTeacherGrid : function(managerIds){
        var grid = new NS.grid.SimpleGrid({
            data : [],
            proxy : this.model,
            pageSize : 15,
            serviceKey : {key : 'queryUserExceptUserIds',params : {userIds :managerIds}},
            fields : ['ry_id','username','bmmc','ghxh','loginname'],
            columnConfig : [
                {name : 'loginname',text : '登录名',width : 100,align : 'center'},
                {name : 'ghxh',text : '工号/学号',width : 100,align : 'center'},
                {name : 'username',text : '姓名',width : 100,align : 'center'},
                {name : 'bmmc',text : '部门名称',width : 100,align : 'center'},
                {name : 'caozuo',text : '操作列',xtype:'linkcolumn',width : 100,align : 'center', links : [
                    {linkText : '添加' }
                ]}
            ]
        });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt,ci,ri,data){
                    switch(txt){
                        case '添加' : {
                            this.managers.push(data);
                            this.managerIds = NS.Array.pluck(this.managers,'id');
                            grid.load({userIds : this.managerIds});
                            this.managerGrid.loadData(this.managers);
                            this.managerCom.refreshTplData(this.managers);
                        }
                    }
            }}
        })
        return grid;
    },
    getManagerGrid : function(managers){
        var grid = new NS.grid.SimpleGrid({
            paging : false,
            data : {success : true,data : managers,count : managers.length},
            fields : ['ry_id','username','bmmc','ghxh','loginname'],
            columnConfig : [
                {name : 'loginname',text : '登录名',width : 100,align : 'center'},
                {name : 'ghxh',text : '工号/学号',width : 100,align : 'center'},
                {name : 'username',text : '姓名',width : 100,align : 'center'},
                {name : 'bmmc',text : '部门名称',width : 100,align : 'center'},
                {name : 'caozuo',text : '操作列',xtype:'linkcolumn',width : 100,align : 'center', links : [
                                                                {linkText : '移除' }
                                                            ]}
            ]
        });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                    switch(txt){
                        case '移除':{
                            var manager = grid.getSelectRows()[0];
                            grid.removeSelectRows();
                            this.managers = grid.getAllRow();//当前管理员
                            grid.loadData(this.managers);
                            this.managerIds = NS.Array.pluck(this.managers,'id');//当前管理员ID
                            this.teacherGrid.load({userIds : this.managerIds});
                            this.managerCom.refreshTplData(this.managers);
                        }
                    }
            }}
        });
        return grid;
    },
    /*************************执行请求******************************/
    doUpdateGroupManagerRequest : function(params,callback){
        this.doTipRequest({
            key : 'updateUsergroupManager',
            params : params,
            successMsg : '用户组管理员设置成功!',
            failureMsg : '用户组管理员设置失败!',
            callback : callback
        });
    }
});