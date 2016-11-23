NS.define('Pages.permiss.menu.ContentPage',{
    /**
     * 初始化菜单页面
     * @param swt
     * @param pc
     */
    initContentPage : function(swt,pc,row){
        this.fun = row;
        var columnTitleText = "<span style='color: red'>"+this.menu.mc+"-"+row.mc+"</span>-内容列表";
        if(!this.contentLayout){
            var grid = this.getContentGrid(row),
                columnTitle = this.getContentColumnTitle(columnTitleText),
                layoutCom = this.contentLayout = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.contentLayoutTpl,
                    renderNumber : 2,
                    data : this.getUsernameAndBmmc()
                });
            layoutCom.register(columnTitle,0);
            layoutCom.register(grid,1);

            layoutCom.bindItemsEvent({
                'backMenu.click':{scope : this, fn : function(){swt.switchTo('menuPage');}},
                'backFun.click':{scope : this, fn : function(){swt.switchTo('funPage');}},
                'addContent.click' : {scope : this ,fn : function(){this.addContent(this.fun);}},
                'query.click' : {scope : this ,fn : function(){this.contentGrid.load({"parentId" : this.fun.id,'mc.like' : layoutCom.get$ByName('menutext').val()});}}
            });
            this.add(pc,layoutCom);
        }else{
            var columnTitle = this.getFunColumnTitle(columnTitleText);
            this.contentLayout.register(columnTitle,0);
            this.contentGrid.load({parentId : row.id});
        }
    },
    getContentGrid : function(row){
        var trandata = this.menuGridTranData,
            grid = this.contentGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                data : this.menuGridData,
                style : {
                      paddingRight : '2px'
                },
                columnData : trandata,
                serviceKey : {key : 'queryChildrenByMenuId',params : {parentId : row.id}},
                columnConfig : [
                    {name : 'cdssfl', hidden : true },{name : 'url', hidden : true },{name : 'funtype', hidden : false },{name : 'fundesc', hidden : false },
                    {name : 'mc',header : '内容名称'},
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo',
                        text : '操作列',
                        align : 'center',
                        width : 140,
                        links : [
                            {linkText : '修改' }, { linkText : '删除' }
                        ]
                    }
                ]
                }
            );
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                switch(txt){
                    case '修改':this.updateContentForm(grid);break;
                    case '删除':this.doDeleteMenuRequest(grid,function(){grid.refresh();});break;
                }
            }}
        })
        grid.refresh();
        return grid;
    },
    getContentColumnTitle : function(desc){
       var  columnTitle = this.ColumnTitle.getInstance({
               desc:desc||'内容列表',
               cls : 'wbh-common-box'
           });
       return columnTitle;
    },
    /*********************第二层********************************************************/
    /**
     * 添加功能
     */
    addContent : function(row){
        var form = this.getContentForm(),
            values = {parentId : row.id,cdssfl : '4'},
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addLayoutTpl,
                renderNumber : 1,
                data : {title :'内容管理',desc : '新增内容',columnTitle : '内容信息'}
            });
        form.setValues(values);
        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doAddMenuRequest(form.getValues(),function(){
                window.close();this.contentGrid.refresh();
            })}},
            'cancel.click' : {fn : function(){window.close();}}
        });

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    updateContentForm : function(grid){
        this.selectGrid(grid,function(row){
            var form = this.getContentForm(),
                layoutCom = new Pages.permiss.base.LayoutComponent({
                    layoutTpl : this.addLayoutTpl,
                    renderNumber : 1,
                    data : {title :'内容管理',desc : '新增内容',columnTitle : '内容信息'}
                });
            form.setValues(row);

            layoutCom.register(form,0);
            layoutCom.bindItemsEvent({
                'submit.click' : {scope : this,fn : function(){
                    if(!form.isValid())return;
                    this.doUpdateMenuRequest(form.getValues(),function(){
                    window.close();this.contentGrid.refresh();
                })}},
                'cancel.click' : {fn : function(){window.close();}}
            });

            var window = this.getBaseWindow({
                items : layoutCom.component
            });
            window.show();
        });
    },
    getContentForm : function(){
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