/**
 *@class NS.grid.SimpleGrid
 *@extends NS.grid.Grid
 */
NS.define('NS.grid.SimpleGrid',{
	extend : 'NS.grid.Grid',
    /***************************************************************************
     * 初始化Column
     * @private
     */
    initColumns : function() {
        NS.apply(this.config,{
            defaultColumnConfig : {
                style : {
                    color : 'white',
                    background : '#3598FE',
                    overCls : ''
                },
                onTitleMouseOver : NS.emptyFn
            }
        });
        this.callParent();
    },
    initComponent : function(){
        this.callParent(arguments);
        if(this.component.headerCt.style){
            NS.apply(this.component.headerCt.style,{background:'#3598FE'});
        }else{
            this.component.headerCt.style = {background:'#3598FE'};
        }
        this.onRowclick();
    },
    /***
     * 初始化分页栏
     * @private
     */
    initPagingBar : function(){
        var me = this,ome = this;
        this.pbar = Ext.create('Ext.toolbar.NumberPaging',{
            store : this.store,
            dock: 'bottom',
            displayInfo: true
        });
        this.pbar.on('linkclick',function(index){
            var page = index,
                baseParams = {
                    start : (page-1)*this.store.pageSize,
                    limit : this.store.pageSize
                },
                params = me.getParams();
            NS.applyIf(baseParams,params);
            var returnParams = me.fireEvent('beforeload',me,baseParams);
            me.getData(returnParams||(baseParams||{}),page);
        });
        return this.pbar;
    },
    /**
     * 当行被双击的时候触发
     * @private
     */
    onRowclick : function(){
        this.component.on('itemclick',function(view, record,item,rowindex,e){
            //scrollTo(e.getX(), e.getY());
            NS.Event.setEventObj(e);
            this.fireEvent('rowclick', this,record.getData(),item,rowindex,NS.Event);
        },this);
    }
});