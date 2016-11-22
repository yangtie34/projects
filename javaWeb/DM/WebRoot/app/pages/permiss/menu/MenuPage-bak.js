NS.define('Pages.permiss.menu.MenuPage',{
    /**
     * 初始化菜单页面
     * @param swt
     * @param pc
     */
    initMenuPage : function(swt,pc){
        var pageTitle = this.getMenuPageTitle(),
            grid = this.getMenuPageGrid(),
            tree = this.getMenuPageTree(),
            container = new NS.container.Container({
                layout : 'border',
                cls : 'permiss-common-container',
                items : [
                        pageTitle,
                    {
                        xtype : 'container',
                        region : 'center',
                        height : '80%',
                        layout : 'border',
                        items : [
                           tree,grid
                        ]
                    }
                ]
            });
        pc.add(container);
    },
    getMenuPageTitle : function(){
        var pageTitle = this.PageTitle.getInstance({
            data : {
                title : '菜单管理',
                username : MainPage.getUserName(),
                department : "计算机系"//MainPage.getBmxx().mc
            },
            region : 'north',
            height : '10%',
            maxHeight:50
        });
        return pageTitle;
    },
    getMenuPageGrid : function(){
        var trandata = this.menuGridTranData = NS.E2S(this.cdStsx),
            tbar  = this.getMenuPageTbar(),
            grid = this.menuGrid = new NS.grid.SimpleGrid({
                width : '70%',
                region : 'east',
                proxy : this.model,
                tbar : tbar,
                data : this.menuGridData,
                columnData : trandata,
                serviceKey : 'queryChildrenByMenuId',
                columnConfig : [
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo',
                        text : '操作列',
                        align : 'center',
                        width : 140,
                        renderer : function(txt,data,rIndex,cIndex){
                              var ah = '<a href="javascript:void(0);" style="color:{0};">{1}</a>';
                              if(data.cdssfl != 2){
                                  return NS.String.format(ah,'blue','修改')+ " " +NS.String.format(ah,'red','删除');
                              }else{
                                  return NS.String.format(ah,'blue','修改')+ " " +NS.String.format(ah,'red','删除')+ " " +NS.String.format(ah,'blue','功能明细');
                              }
                        }
                    }
                ]
            });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                switch(txt){
                    case '修改':break;
                    case '删除':break;
                    case '功能明细' : break;
                }
            }}
        })
        return grid;
    },
    getMenuPageTree : function(){
        var tree = new NS.container.Tree({
            treeData:NS.clone(this.menuData),
            width : '30%',
            region : 'west',
            rootVisible : true,
            frameHeader : false,
            header : false,
            border : true,
            margin : '0 0 0 0'
        });
        tree.on('itemclick',function(tree,data){
            var menuId = data.id;
            this.menuGrid.load({parentId : menuId});
        },this);
        tree.on('itemcontextmenu',function(tree, record, record, item, index, e){
            this.showOptions(e.getX(), e.getY());
            e.preventDefault();
        },this);
        return tree;
    },
    showOptions : function(x,y){
        var menu = new NS.menu.Menu({
            items : [
                {iconCls : 'page-add',text : '在选中节点下新增菜单节点',name : 'addmenu'}
            ]
        });
        menu.on('click',function(menu,item){
            if(item.name == "addmenu"){
                this.swc.switchTo('funPage');
            }
        },this);
        menu.showAt(x,y);
    },
    getMenuPageTbar : function(){
        var tbar = new NS.toolbar.Toolbar({
            items : [
                {xtype : 'textfield',fieldLabel : '菜单名称'},
                {xtype : 'button',text : '查询'},
                {xtype : 'button',text : '添加菜单',name : 'add'},
                {xtype : 'button',text : '删除菜单',name : 'deleteMenu'},
                {xtype : 'button',text : '修改菜单',name : 'updateMenu'}
            ]
        });
        tbar.bindItemsEvent({
            add : {event : 'click',scope : this,fn : this.addMenu},
            deleteMenu : {event : 'click',scope : this,fn : this.doDeleteMenuRequest},
            updateMenu : {event : 'click',scope : this,fn : this.updateMenu}
        })
        return tbar;
    },
    /**
     * 添加菜单
     */
    addMenu : function(){
        var form = this.showMenuWindow(function(){
            this.doAddMenuRequest(form.getValues());
        });
    },
    updateMenu : function(){
        var select = this.menuGrid.getRow(this.menuGrid.getFirstSelectIndex()),
            form = this.showMenuWindow(function(){
                this.doUpdateMenuRequest(form.getValues());
            });
        form.setValues(select);
    },
    showMenuWindow : function(submit){
        var form = this.createAddForm({
            data : this.menuGridTranData,
            bindItems : {
                submit : {event : 'click',fn : function(){
                    submit.call(this,form.getValues());
                },scope : this},
                cancel : {event : 'click',fn : function(){
                    form.close();
                },scope : this}
            }});
        return form;
    }
});