NS.define('Pages.permiss.base.MenuPermiss',{
    extend : 'Pages.permiss.base.BaseModel',
    requires : ['Pages.permiss.base.LayoutComponent'],
    tplRequires : [
        {fieldname : 'menuPermissLayout',path : 'app/pages/permiss/base/tpl/menu/menuPermissLayout.html'},
        {fieldname : 'childSystemsTpl',path : 'app/pages/permiss/base/tpl/menu/childSystemsTpl.html'},
        {fieldname : 'childSystemMenuSetTpl',path : 'app/pages/permiss/base/tpl/menu/menuCheck.html'},
        {fieldname : 'contentSetTpl',path : 'app/pages/permiss/base/tpl/menu/contentSet.html'}
    ],
    /**************配置项*************************/
    /**
     * 布局数据项目
     */
    layoutData : {mainTitle : '',title : '',others : ''},//布局显示数据
    contentLayoutData : {},
    /**
     * 取消操作
     */
    cancel : function(){},//设置取消
    /**
     *
     * @param {Array(Number)[]}menuIds
     */
    submit : function(menuIds){},//设置提交
    /***************************************/
    init : function(config){
        var menuTree = config.menuTree,
            menuIds = config.menuIds;
        this.generateNodeHashAndCheckNode(menuTree,menuIds);//生成节点Hash表，另设置拥有的菜单的checked属性为true
        this.systemMenus = this.menuTree.children;

        this.initLayout();//初始化布局
    },
    /*******************************组件生成***************************************/
    /**
     * 初始化布局
     */
    initLayout : function(){
        var layoutCom = this.layout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.menuPermissLayout,
                renderNumber: 3,
                data : this.layoutData
            }),
//            currentModalCom = this.currentModalCom = this.getCurrentModalComponent(),//当前拥有菜单模型---目前暂时省略
            childSystemCom = this.childSystemCom = this.getChildSystemComponent(),//子系统----
            currentCheckModalCom = this.currentCheckModalCom = this.getCurrentCheckModalComponent();//当前菜单选中模型

        childSystemCom.refreshTplData(this.systemMenus);
        currentCheckModalCom.refreshTplData('');

        layoutCom.register(childSystemCom,1);
        layoutCom.register(currentCheckModalCom,2);

        layoutCom.bindItemsEvent({
            'cancel.click' : {scope : this,fn : function(){
                this.cancel();
            }},
            'submit.click' : {scope : this,fn : function(){
                layoutCom.get$ByName('submit').attr('disable',true);
                this.submit(this.getCheckedMenuIds());
            }},
            'cancel1.click' : {scope : this,fn : function(){
                this.cancel();
            }},
            'submit1.click' : {scope : this,fn : function(){
                layoutCom.get$ByName('submit1').attr('disable',true);
                this.submit(this.getCheckedMenuIds());
            }}
        });
        this.setPageComponent(layoutCom.component);
    },
    /**
     * 获取子系统显示组件
     */
    getChildSystemComponent : function(){
        var component = new NS.Component({
            tpl : this.childSystemsTpl
        });
        component.on('click',function(event,el){
            this.filterNode('TD',el,function(el){
                var itemId = $(el).parent().attr('itemId');
                if(itemId){
                    var node = this.csnode = this.nodeHash[itemId];
                    var system = this.childSystemData = this.translateMenuTree(node);
                    this.currentCheckModalCom.refreshTplData(system);

                    $(el.parentNode.parentNode).find('TD').each(function(){
                        $(this).removeClass('permiss_table_menu_select')
                    });
                    $(el).addClass('permiss_table_menu_select');
                }
            });
            this.filterNode('INPUT',el,function(el){
                var itemId = $(el).parent().parent().attr('itemId');
                var node = this.csnode = this.nodeHash[itemId];
                //选中所有子节点、父节点
                this.checkAllChildren(node,$(el)[0].checked);
                this.checkParent(node.id,$(el)[0].checked);
                var system = this.childSystemData = this.translateMenuTree(node);
                this.currentCheckModalCom.refreshTplData(system);
            });

            component.on('mouseover',function(event,td){
                var tr = td.parentNode;
                this.filterNode('TR',tr,function(tr){
                    var tbody = tr.parentNode;
                    $(tbody).find('TR').each(function(){
                        $(this).removeClass('permiss_table_menu_mouseover')
                    });
                    $(tr).addClass('permiss_table_menu_mouseover');
                });
            },this);
        },this);

        return component;
    },
    /**
     * 获取当前子系统显示组件
     */
    getCurrentModalComponent : function(){
        var component = new NS.Component({
            tpl : this.detailMenuTpl
        });
        return component;
    },
    /**
     * 显示当前子系统的设置权限界面
     */
    getCurrentCheckModalComponent : function(){
        var me = this,
            component = new NS.Component({
                tpl : this.childSystemMenuSetTpl,
                cls : '',
                baseCls : '',
                data  : {}
            });
        component.on('click',function(event,el){
            this.filterNode('INPUT',el,function(el){
                var c = $(el),
                    ctype = c.attr('ctype'),
                    itemId = c.attr('itemid'),
                    checked = c[0].checked,
                    hash = me.nodeHash,
                    modals = me.childSystemData.modals;

                this.check(itemId,checked);//选中子节点

                this.checkParent(itemId,checked);
                this.childSystemData = this.translateMenuTree(this.csnode);
                this.currentCheckModalCom.refreshTplData(this.childSystemData);
                this.childSystemCom.refreshTplData(this.systemMenus);
            });
            this.filterNode('A',el,function(el){
                var c = $(el),
                    ctype = c.attr('ctype'),
                    itemId = c.attr('itemid'),
                    checked = c[0].checked,
                    hash = me.nodeHash,
                    modals = me.childSystemData.modals;
                if(ctype == 'content'){
                    this.showRoleContentSetPage(itemId);
                }
            });
        },this);
        return component;
    },
    /******************************数据处理*******************************/
    /**
     * 获取选中的菜单IDS
     * @returns {Array}
     */
    getCheckedMenuIds : function(){
        var menuIds =  [],hash = this.nodeHash;
        for(var i in hash){
            var item = hash[i];
            if(item.checked == true)menuIds.push(item.id);
        }
        return menuIds;
    },
    /**
     * 转置模型数据
     * @param modal {模块}
     */
    translateMenuTree : function(node){
        var system = {},
            modals = [];//模块集合
        system.mc = node.text;
        system.id = node.id;
        system.modals = modals;
        //生成模块
        var children = node.children;
        var specialModal = {//针对子系统下，直接挂接在子系统的菜单
            id : '',
            pid : node.id,
            text : '其他模块',
            checkNum : 0,
            menus : []
        };
        for(var i=0;i<children.length;i++){
            var item = children[i];
            if(item.leaf == "true"){
                specialModal.menus.push(item);
                if(item.checked)specialModal.checkNum+=1;
            }else{
                modals.push({
                    id : item.id,
                    text : item.text,
                    checked : item.checked,
                    menus : [],
                    children : item.children
                });
            }
        }
        //判断特殊菜单是否应该被选中
        specialModal.checked = specialModal.checkNum >0;
        //把模块下的菜单挂接到模块下
        for(var i=0;i<modals.length;i++){
            var modal = modals[i],
                menus = [],
                iterator = [],
                bakToIterator = [];
            iterator = modal.children;
            delete modal.children;

            while(iterator.length != 0){
                for(var j=0;j<iterator.length;j++){
                    var item = iterator[j];
                        if(item.leaf == "true"){
                            menus.push(item);
                        }else{
                            bakToIterator = bakToIterator.concat(item.children);
                        }
                }
                iterator = bakToIterator;
                bakToIterator = [];
            }
            modal.menus = menus;
        }
        modals.push(specialModal);
        system.rowspan = this.computeRowSpan(system);
        return system;
    },
    /**
     * 生成节点Hash表，并选中已经拥有的节点添加checked属性
     * @param node
     * @param roleMenuList
     */
    generateNodeHashAndCheckNode : function(node,roleMenuList){
        var nodeHash = this.nodeHash = {},
            iterator = node.children,
            len = roleMenuList.length,
            bakToIterator = [];
        while(iterator.length != 0){
            for(var i=0;i<iterator.length;i++){
                var item = iterator[i];
                nodeHash[item.id] = item;
                bakToIterator = bakToIterator.concat(item.children);
            }
            iterator = bakToIterator;
            bakToIterator = [];
        }
        for(var i=0;i<len;i++){
            var id = roleMenuList[i];
            if(nodeHash[id])nodeHash[id].checked = true;

        }
    },
    /**
     * 选中当前节点以及所有子节点
     * @param node
     */
    checkAllChildren : function(node,checked){
        var iterator = node.children,
            bakToIterator = [];
        node.checked = checked;//选中
        while(iterator.length != 0){
            for(var i=0;i<iterator.length;i++){
                var item = iterator[i];
                item.checked = checked;//选中
                bakToIterator = bakToIterator.concat(item.children);
            }
            iterator = bakToIterator;
            bakToIterator = [];
        }
    },
    check : function(itemId,checked){
        if(itemId){
            this.checkAllChildren(this.nodeHash[itemId],checked);
        }else{
            var modals = this.childSystemData.modals,
                sm = modals[modals.length-1],
                menus = sm.menus;
            for(var i=0;i<menus.length;i++){
                var menu = menus[i];
                this.checkAllChildren(this.nodeHash[menu.id],checked);
            }
        }
    },
    checkParent : function(itemId,checked){
        //选中/不选中当前节点以及其父节点
        var hash = this.nodeHash,
            node = hash[itemId],
            pnode = node;
        if(node){
            node.checked = checked;//当前节点直接复制checked
            pnode = hash[pnode.pid];
            while(pnode){
                var pchecked = checked;
                if(!checked){
                    for(var i=0;i<pnode.children.length;i++){
                        var cn = pnode.children[i];
                        if(cn.checked){
                            pchecked = true;
                            break;
                        }
                    }
                }
                pnode.checked = pchecked;
                if(hash[pnode.pid]){
                    pnode = hash[pnode.pid];
                }else{
                    pnode = undefined;
                }
            }
        }
    },
    /**
     * 根据模型数据生成rowspan
     * @param modal
     */
    computeRowSpan : function(system){
          var modals = system.modals,
              len = modals.length,
              modal,
              rowspan = len,
              i = 0;
        for(;i<len;i++){
            modal = modals[i];
            rowspan = rowspan+modal.menus.length;
        }
        return rowspan;
    },
    /**
     * 刷新当前角色权限设置页面
     */
    refreshRoleMenuPage : function(){
        this.currentModalCom.refreshTplData(this.currentChildSystemMenu);
        this.currentCheckModalCom.refreshTplData(this.currentChildSystemMenu);
    },
    /***********************************内容设置**********************************************/
    showRoleContentSetPage : function(funId){
        var me = this,
            hash = this.nodeHash,
            fun = hash[funId],//功能
            menu = hash[fun.pid],//菜单
            contentList = fun.children;
//        //用户拥有角色组件
        var ownerContentCom = this.getOwnerContentComponent(contentList);
//        //布局组件
        var layout  = this.roleSetLayout = this.getContentSetLayout(menu,fun,contentList,ownerContentCom,hash);
    },
    /**
     * 获取角色设置布局页面
     * @returns {LayoutComponent}
     */
    getContentSetLayout : function(menu,fun,contentList,ownerContentCom,hash){
        //布局组件
        var me = this;
        var layoutData = this.contentLayoutData;
        NS.apply(layoutData,{
            contentList :contentList
        });
        layoutData.description +="-"+menu.text+"-"+fun.text;//设定显示的数据
        var layout  = this.contentSetLayout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.contentSetTpl,
                renderNumber : 1,
                data : this.contentLayoutData
            });
        //组件注册进入布局容器中
        layout.register(ownerContentCom,0);
        //绑定布局容器内部html元素事件
        layout.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){window.close();}},
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
            items : layout.component
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