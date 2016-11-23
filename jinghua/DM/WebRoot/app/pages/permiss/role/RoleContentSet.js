NS.define('Pages.permiss.role.RoleContentSet',{
    showRoleContentSetPage : function(funId,role){
        var me = this,
            hash = this.nodeHash,
            fun = hash[funId],//功能
            menu = hash[fun.pid],//菜单
            contentList = fun.children;
//        //用户拥有角色组件
        var ownerContentCom = this.getOwnerContentComponent(contentList);
//        //布局组件
        var layout  = this.roleSetLayout = this.getContentSetLayout(menu,fun,role,contentList,ownerContentCom,hash);
    },
    /**
     * 获取角色设置布局页面
     * @returns {LayoutComponent}
     */
    getContentSetLayout : function(menu,fun,role,contentList,ownerContentCom,hash){
        //布局组件
        var me = this,
            layout  = this.contentSetLayout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.contentSetTpl,
                renderNumber : 1,
                data : {jsmc : role.jsmc,cdmc : menu.mc,gnmc : fun.mc,contentList :contentList}
            });
        //组件注册进入布局容器中
        layout.register(ownerContentCom,0);
        //绑定布局容器内部html元素事件
        layout.bindItemsEvent({
            'cancel.click' : {scope : this,fn : function(){window.close();}}
        });
        layout.component.on('click',function(event,el){
            me.filterNode('INPUT',el,function(el){
                var ev = $(el),
                    ctype = ev.attr('ctype'),
                    checked = el.checked;
                if(ctype == 'checkAll'){
                    layout.get$ByAttribute('ctype','item').each(function(){
                        $(this)[0].checked = checked;
                        hash[$(this).attr('value')].checked = checked;
                        me.checkParent($(this).attr('value'),checked);
                    });
                }else if(ctype == "item"){
                    hash[ev.attr('value')].checked = checked;
                    me.checkParent($(this).attr('value'),checked);
                }
                this.ownerContentCom.refreshTplData(contentList);
                this.refreshMenuCheckCom();
            });
        });
        var window = this.getBaseWindow({
            items : layout.component,
        });
        window.show();
        return layout;
    },
    /**
     * 获取用户拥有角色组件
     * @returns {Component}
     */
    getOwnerContentComponent : function(ownerContentList){
        //用户拥有角色组件
        var ownerContentCom = this.ownerContentCom = new NS.Component({
            tpl : new NS.Template('<h2><span class="wbh-common-title ">用户组拥有的内容</span> </h2>'+
                '<tpl for=".">'+
                    '<tpl if="checked == true">' +
                        '<span>{text}</span><a href="javascript:void(0);" class="permiss-outname-delete-bg" contentId="{id}"></a>' +
                    '</tpl>'+
                ' </tpl>'),
            data : ownerContentList
        });
        ownerContentCom.on('click',function(event,element){
            this.filterA(element,function(el){
                var ev = $(el),
                    contentId = ev.attr('contentId');
                this.checkParent(contentId,false);
                this.roleSetLayout.get$ByAttribute('value',contentId).each(function(){
                    $(this)[0].checked = false;
                });
                me.checkParent($(this).attr(contentId),checked);
                ownerContentCom.refreshTplData(ownerContentList);//刷新我拥有的内容
                me.refreshMenuCheckCom();
            });
        },this);
        return ownerContentCom;
    },
    refreshMenuCheckCom : function(){
        this.currentCheckModalCom.refreshTplData(this.childSystemData);
        this.childSystemCom.refreshTplData(this.systemMenus);
    }
});