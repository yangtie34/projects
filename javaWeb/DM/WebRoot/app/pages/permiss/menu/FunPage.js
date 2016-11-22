NS.define('Pages.permiss.menu.FunPage',{
    /**
     * 初始化菜单页面
     * @param swt
     * @param pc
     */
    initFunPage : function(swc,pc,row){
        this.menu = row;
        var columnTitleText = "<span style='color: red'>"+row.mc+"</span>-功能列表";
        if(!this.funLayout){
            var grid = this.getFunGrid(row),
                columnTitle = this.getFunColumnTitle(columnTitleText),
                layoutCom = this.funLayout = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.funLayoutTpl,
                    renderNumber : 2,
                    data : this.getUsernameAndBmmc()
                });
            layoutCom.register(columnTitle,0);
            layoutCom.register(grid,1);

            layoutCom.bindItemsEvent({
                'backMenu.click':{scope : this, fn : function(){swc.switchTo('menuPage');}},
                'addFun.click' : {scope : this ,fn : function(){this.addFun(this.menu);}},
                'query.click' : {scope : this ,fn : function(){this.funGrid.load({"parentId" : this.menu.id,'mc.like' : layoutCom.get$ByName('menutext').val()});}}
            });
            this.add(pc,layoutCom);
        }else{
            var columnTitle = this.getFunColumnTitle(columnTitleText);
            this.funLayout.register(columnTitle,0);
            this.funGrid.load({parentId : row.id});
        }
    },
    getFunGrid : function(row){
        var trandata = this.menuGridTranData,
            grid = this.funGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                data : this.menuGridData,
                    style : {
                        paddingRight : '1px'
                    },
                columnData : trandata,
                serviceKey : {key : 'queryChildrenByMenuId',params : {parentId : row.id}},
                columnConfig : [
                    {name : 'cdssfl', hidden : true },{name : 'url', hidden : true },{name : 'funtype', hidden : false },{name : 'fundesc', hidden : false },
                    {name : 'mc',header : '功能名称'},
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo1',
                        text : '操作列',
                        align : 'center',
                        width : 140,
                        links : [
                            {linkText : '修改' }, { linkText : '删除' },{ linkText : '内容明细'}
                        ]
                    }
                ]
                }
            );
        grid.bindItemsEvent({
            caozuo1 : {event : 'linkclick',scope : this,fn : function(txt){
                switch(txt){
                    case '修改':this.updateFunForm(grid);break;
                    case '删除':this.doDeleteMenuRequest(grid,function(){grid.refresh();});break;
                    case '内容明细' : this.selectGrid(grid,function(row){
                        this.swc.switchTo('contentPage',row,true);
                    });break;
                }
            }}
        })
        grid.refresh();
        return grid;
    },
    getFunColumnTitle : function(desc){
       var  columnTitle = this.ColumnTitle.getInstance({
               desc:desc||'功能列表',
               cls : 'wbh-common-box'
           });
       return columnTitle;
    },
    /***********************************第二层***************************************/
    /**
     * 添加功能
     */
    addFun : function(row){
        var form = this.getFunForm(),
            values = {parentId : row.id,cdssfl : '3'},
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addLayoutTpl,
                renderNumber : 1,
                data : {title :'功能管理',desc : '新增功能',columnTitle : '功能内容'}
            });
        form.setValues(values);
        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doAddMenuRequest(form.getValues(),function(){
                    window.close();this.funGrid.refresh();
                })}},
            'cancel.click' : {fn : function(){window.close();}}
        });

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    updateFunForm : function(grid){
        this.selectGrid(grid,function(row){
            var form = this.getFunForm(),
                layoutCom = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.addLayoutTpl,
                    renderNumber : 1,
                    data : {title :'功能管理',desc : '新增功能',columnTitle : '功能内容'}
                });
            form.setValues(row);

            layoutCom.register(form,0);
            layoutCom.bindItemsEvent({
                'submit.click' : {scope : this,fn : function(){
                    if(!form.isValid())return;
                    this.doUpdateMenuRequest(form.getValues(),function(){
                    window.close();this.funGrid.refresh();
                })}},
                'cancel.click' : {fn : function(){window.close();}}
            });

            var window = this.getBaseWindow({
                items : layoutCom.component
            });
            window.show();
        });
    },
    getFunForm : function(){
        var form = NS.form.EntityForm.create({
            data : this.menuGridTranData,
            frame : false,
            formType : 'add',
            columns : 1,
            border : false,
            items : [
                'id',{name : 'mc' ,fieldLabel : '功能名称'},
                {name : 'cdssfl',allowBlank : false,hidden : true,fields : ['id','mc'],data : [{id:'1',mc:'模块'},{id:'2',mc:'菜单'},{id:'3',mc:'功能'},{id:'4',mc:'内容'}]},
                {name : 'sortnum',minValue : 1},
                'parentId',{name : 'url',hidden : true},
                'leaf',
                {name :'funtype',hidden : false},{name :'fundesc',hidden : false},'qxm'
            ]
        });
        return form;
    }
});