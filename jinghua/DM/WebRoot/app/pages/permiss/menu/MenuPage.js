NS.define('Pages.permiss.menu.MenuPage',{
    /**
     * 初始化菜单页面-----------第一层页面
     * @param swt
     * @param pc
     */
    initMenuPage : function(swt,pc,row){
        var pageTitle = this.getMenuPageTitle(),
            grid = this.getMenuPageGrid(),
            columnTitle = this.ColumnTitle.getInstance({desc:"<span style='color: red'>菜单功能</span>-<span style='color: blue;'>模块-</span>"+'子菜单列表',cls : 'wbh-common-box'}),
            tree = this.getMenuPageTree();
        var layoutCom  = this.menuLayout = new Pages.permiss.base.LayoutComponent({
            layoutTpl : this.menuLayoutTpl,
            renderNumber : 3,
            data : this.getUsernameAndBmmc()
        });

        layoutCom.register(tree,0);
        layoutCom.register(columnTitle,1);
        layoutCom.register(grid,2);

        layoutCom.bindItemsEvent({
            'addmenu.click':{scope : this, fn : this.addMenu},
            'query.click' : {scope : this,fn : function(){this.menuGrid.load({"parentId" : this.menuId||0,'mc.like' : layoutCom.get$ByName('menutext').val()});}}
        });
        this.add(pc,layoutCom);
    },
    getMenuPageTitle : function(){
        var pageTitle = this.PageTitle.getInstance({
            data : {
                title : '菜单管理',
                username : MainPage.getUserName(),
                department : "计算机系"//MainPage.getBmxx().mc
            }
        });
        return pageTitle;
    },
    getMenuPageGrid : function(){
        var trandata = this.menuGridTranData = NS.E2S(this.cdStsx),
            tbar  = this.getMenuPageTbar(),
            grid = this.menuGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                columnData : trandata,
                style : {
                    paddingRight : '1px'
                },
                data : this.menuGridData,
                serviceKey : 'queryChildrenByMenuId',
                columnConfig : [
                    {
                        name : 'cdssfl',
                        editorConfig : {//后台实体属性表组件的扩展数据配置
                            fields : ['id','mc'],data : [{id:'1',mc:'模块'},{id:'2',mc:'菜单'}]
                        }
                    },
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
                    case '修改':this.updateMenuForm();break;
                    case '删除':this.doDeleteMenuRequest(this.menuGrid,function(){this.menuGrid.refresh();this.refreshMenuTree();});break;
                    case '功能明细' : this.selectGrid(grid,function(row){this.swc.switchTo('funPage',row,true)}); break;
                }
            }}
        });
        return grid;
    },
    getMenuPageTree : function(){
        this.menuData.expanded = true;
        var tree  = this.menuTree = new NS.container.Tree({
            treeData:NS.clone(this.menuData),
            rootVisible : true,
            frameHeader : false,
            header : false,
            border : true,
            height : 500,
            margin : '0 0 0 0'
        });
        tree.on('itemclick',function(tree,data){
            var menuId  = this.menuId = data.id,text = data.text;
            this.leaf = data.leaf;
            if(this.leaf == true){
                text = "<span style='color: red;'>菜单-</span>"+text+"<span style='color: red'>(无子菜单)</span>";
                this.menuGrid.load({parentId : '-1'});
                this.menuLayout.get$ByName('somewherehide').hide();
                this.menuGrid.hide();
            }else{
                text = "<span style='color: red'>"+text+"</span>-<span style='color: blue;'>模块-</span>"+'子菜单列表';
                this.menuGrid.load({parentId : menuId});
                this.menuLayout.get$ByName('somewherehide').show();
                this.menuGrid.show();
            }
            var columnTitle = this.ColumnTitle.getInstance({desc:text,cls : 'wbh-common-box'});
            this.menuLayout.register(columnTitle,1);
        },this);
//        tree.on('itemcontextmenu',function(tree, record, record, item, index, e){
//            this.showOptions(e.getX(), e.getY());
//            e.preventDefault();
//        },this);
//        tree.expandAll();
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
    /*************************第二层的时候---弹窗*********************************/
    /**
     * 添加菜单
     */
    addMenu : function(){
        var form = this.getMenuForm(),
            values = {},
            layoutCom = new Pages.permiss.base.LayoutComponent({
            layoutTpl : this.addLayoutTpl,
            renderNumber : 1,
            data : {title :'菜单管理',desc : '新增菜单',columnTitle : '菜单内容'}
        });
        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doAddMenuRequest(form.getValues(),function(){
                    window.close();this.menuGrid.refresh();
                    this.refreshMenuTree();
                })}
            },
            'cancel.click' : {fn : function(){window.close();}}
        });

        //给Form设置默认值
        if(this.menuId && this.menuId !== "0"){
            values.parentId = this.menuId;
            if(this.leaf){
                this.alert('提示','不能在叶子节点下添加子节点!');
                return;
            }
        }else{
            values.parentId = 0;
            values.cdssfl = '1';
            form.getField('cdssfl').hide();
        }
        form.setValues(values);

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    updateMenuForm : function(){
        this.selectGrid(this.menuGrid,function(values){
            var form = this.getMenuForm(),
                layoutCom = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.addLayoutTpl,
                    renderNumber : 1,
                    data : {title :'菜单管理',desc : '新增菜单',columnTitle : '菜单内容'}
                });
            layoutCom.register(form,0);
            layoutCom.bindItemsEvent({
                'submit.click' : {scope : this,fn : function(){
                    if(!form.isValid())return;
                    this.doUpdateMenuRequest(form.getValues(),function(){
                        window.close();this.menuGrid.refresh();
                        this.refreshMenuTree();
                    });
                }},
                'cancel.click' : {fn : function(){window.close();}}
            });
            form.setValues(values);
            if(values.cdssfl == '1'){
                form.getField('url').setDisabled(true)
            }
            var window = this.getBaseWindow({
                items : layoutCom.component
            });
            window.show();
        });
    },
    /**
     * 获取菜单页Form
     * @returns {BasicForm|*}
     */
    getMenuForm : function(){
        var form = NS.form.EntityForm.create({
            data : this.menuGridTranData,
            frame : false,
            formType : 'add',
            columns : 1,
            border : false,
            items : [
                'id','mc',
                {name : 'cdssfl',allowBlank : false,fields : ['id','mc'],data : [{id:'1',mc:'模块'},{id:'2',mc:'菜单'}]},
                {name : 'sortnum',minValue : 1},
                'parentId','url',
                {name : 'leaf'},
                'funtype',
                'fundesc','qxm'
            ]
        });

        form.bindItemsEvent({
             'cdssfl' : {event : 'select',scope : this,fn : function(){
                 var cdssfl = form.getField('cdssfl').getValue();
                 if(cdssfl == 1){
                     form.getField('leaf').setValue(0);
                     form.getField('url').setDisabled(true);
                 }else{
                    form.getField('leaf').setValue(1);
                    form.getField('url').setDisabled(false);
                 }
             }}
        });
        return form;
    },
    /**
     * 刷新菜单树
     */
    refreshMenuTree : function(){
        this.callSingle('queryAllMenu',function(data){
            this.menuTree.refresh(data);
//            this.menuTree.expandAll();
        });
    }
});