NS.define('Pages.permiss.MenuManager',{
    extend : 'Pages.permiss.base.BaseModel',
    modelConfig : {
        serviceConfig : {
            'queryAllMenu' : "menuManager?queryAllMenus",
            'deleteMenu' : "menuManager?deleteMenu",
            'addMenu' : "menuManager?addMenu",
            'updateMenu' : "menuManager?updateMenu",
            'getMenuById' : "menuManager?getMenuById",
            'queryChildrenByMenuId' : {service :  "menuManager?queryChildrenByMenuId",params : {parentId : 0}},
            'cdStsx' : {service : 'base_queryForAddByEntityName',params : {entityName : 'TpMenu'}}
        }
    },
    mixins : ['Pages.permiss.menu.MenuPage'],
    dataRequires  : [
        {fieldname : 'cdStsx' , key : 'cdStsx' },
        {fieldname : 'menuData',key : 'queryAllMenu'},
        {fieldname : 'menuGridData',key : 'queryChildrenByMenuId'}
    ],
    init : function(){
        this.initComponent();
    },
    initComponent : function(){
        var container = this.swc = new NS.container.SwitchContainer({
            items :[
                {
                    name : 'menuPage',scope : this,init : this.initMenuPage
                },
                {
                    name : 'funPage',scope : this,init : this.initFunPage
                },
                {
                    name : 'conentPage',scope : this,init : this.initConentPage
                }
            ]
        });
        this.setPageComponent(container);
    },
    /**
     * 执行新增菜单请求
     * @param values
     */
    doAddMenuRequest : function(values,callback){
        this.doTipRequest({
            key : 'addMenu',
            params : values,
            successMsg : '菜单添加成功!',
            failureMsg : '菜单添加失败!',
            callback : callback
        });
    },
    /**
     * 执行删除菜单请求
     * @param values
     */
    doDeleteMenuRequest : function(){
        var select = this.menuGrid.getRow(this.menuGrid.getFirstSelectIndex()),
            menuId = select.id;
        this.doTipRequest({
            key : 'deleteMenu',
            confirm : true,
            confirmMsg : '您确定要删除这行菜单么？',
            params : {menuId : menuId},
            successMsg : '菜单删除成功!',
            failureMsg : '菜单删除失败!'
        });
    },
    /**
     * 执行修改菜单请求
     * @param values
     */
    doUpdateMenuRequest : function(values){
        this.doTipRequest({
            key : 'updateMenu',
            params : values,
            successMsg : '菜单修改成功!',
            failureMsg : '菜单修改失败!'
        });
    }
});