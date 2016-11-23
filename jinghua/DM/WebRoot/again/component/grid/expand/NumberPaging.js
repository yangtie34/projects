/**
 * 自定义grid分页栏
 */
Ext.define('Ext.toolbar.NumberPaging', {
    extend: 'Ext.toolbar.Toolbar',
    tpl1 : new Ext.XTemplate([
        '<span style="margin-left:12px;display: inline-block;float: left">',
         '<tpl for="list">',
            '<tpl if="link == true"><a href="javascript:void(0);" style="font-size: 12;color: blue;margin-left: 2px;" index="{index}">{mc}</a></tpl>',
            '<tpl if="link === false"><a index="{index}" style="font-size: 16;font-weight:bolder;margin-left: 2px;">{mc}</a></tpl>',
         '</tpl>' +
            '</span>',
        '<span style="margin-right:14px;display:inline-block;float:right;color: blue;">共<span style="color: #000000;"> {count} </span>条记录，当前显示第 {start} 到 {end} 条记录</span>'
    ]),
    layout : 'fit',
    height : 20,
    initComponent : function(){
        this.addEvents('linkclick');
        var component = this.content = new Ext.Component({
            tpl : this.tpl1
        });
        component.on({
            click : {
                element : 'el',scope : this,
                fn : function(event,he){
                    if(he.nodeName == "A"){
                        var e = Ext.fly(he);
                        var index = e.getAttribute('index');
                        this.onLinkclick(parseInt(index));
                        this.fireEvent('linkclick',this.index);
                    }
                }
            }
        });
        this.callParent();
    },
    onRender: function () {
        var me = this;
        this.add(this.content);
        this.callParent(arguments);
        this.onLinkclick(1);
    },
    onLinkclick : function(index){
        this.index = parseInt(index);
        this.content.update(this.createLinkData(this.index));
    },
    /**
     * @cfg {Number} 最大链接数 1,2,3,4,5,6,7,8,9,10
     */
    maxLink: 7,
    onLoad : function(){
        this.content.update(this.createLinkData(this.store.currentPage));
    },
    createLinkData : function(index){
        var store = this.store, count = parseInt(store.totalCount)||0,maxLink = this.maxLink,
            pageSize = store.pageSize, currentPage = store.currentPage,
            maxPage = Math.ceil(count / pageSize), i = index, iteLength,array = [],allData = {};
        if (maxPage > maxLink) {
            iteLength = maxLink;
        } else {
            iteLength = maxPage;
        }
        iteLength  += index;
        if(index>7){
            iteLength = iteLength-3;
            i = i-3;
        }
        if(iteLength>maxPage){
            iteLength = maxPage+1;
            if(iteLength>maxLink){
                i = iteLength-maxLink;
            }else{
                i = 1;
            }
        }
        array.push({mc : '首页',index : 1,link : true});//将第一页的链接放入首页
        if(index > 1){
            array.push({mc : '上一页',index : index-1,link : true});//将上一页的链接放入
        }
        for (i; i < iteLength;i++) {
            if( i == index){
                array.push({mc : i,index : i,link : false});
            }else{
                array.push({mc : i,index : i,link : true});
            }
        }
        if(index<maxPage){
            array.push({mc : '下一页',index : index+1,link : true});//将下一页的链接放入
        }
        array.push({mc : '末页',index : maxPage,link : true});//将最后一页的链接放入

        allData.list = array;
        allData.maxPage = maxPage;
        allData.count = count;
        var start = pageSize*(index-1)+1;
        var end = pageSize*index;
        allData.end = end<count?end:count;
        allData.start = start<allData.end?start:allData.end;

        return allData;
    }
});