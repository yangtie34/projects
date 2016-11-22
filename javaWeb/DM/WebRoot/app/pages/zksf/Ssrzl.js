/**
 * 宿舍楼住宿统计
 */
NS.define('Pages.zksf.Ssrzl', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各宿舍楼住宿情况统计',
            pageHelpInfo:'各宿舍楼住宿情况统计'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryTable1Data:'sslService?getTable1Data',
	    }
	},
	tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	init: function () {
	    var comps = this.createComps();
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[this.pageTitle,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    container.on("afterrender",function(){
	    	this.fillCompData();
	    },this);
	    this.setPageComponent(container);
	},
	table1 : new Ext.Component({
        tpl :'<table class="table1"><thead><tr><th >校区</th> <th>楼号</th> <th>住宿性别</th><th>总房间数</th><th>床位数</th><th>入住学生数</th><th>空房间数</th><th>空床位数</th></tr></thead>'+
            '<tbody><tpl if="values.length == 0"> <tr><div class="loading-indicator">正在加载....</div></tr></tpl>' +
            '<tpl for=".">' +
            '{%this.mc=values.mc;%}'+
            '<tpl for="list"><tr>' +

                '<tpl if="xindex==1"><td rowspan="{[xcount]}">{[this.mc]}</td></tpl><td>{MC}</td><td>{zsxb}</td><td>{zfjs}</td><td>{cws}</td><td>{yrzs}</td><td>{kfjs}</td><td>{kcws}</td></tr>' +

            '</tpl>' +
            '</tpl></tbody></table>' ,
        data : []
    }),
	createComps : function(){
		var me = this;
		var result = new NS.container.Container({
			items : [me.table1]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.callService({key:'queryTable1Data',params:this.params},function(respData){
            this.table1.update(respData.queryTable1Data);
        },this);
		
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});
