/**
 * 一卡通吃饭统计
 */
NS.define('Pages.sc.ykt.YktCf', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生用餐消费统计',
            pageHelpInfo:'按照时间段统计学生一卡通用餐的刷卡消费的频率和金额'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryJxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构
	    	yktCf : "yktCfTjService?queryYktCf", // 图书借阅按类别排名
	    }
	},
	tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	params:[],
	init: function () {
		var me = this;
	    var comps = this.createComps();
	    var navigation = this.navigation = new Exp.component.Navigation({margin:'0 0 10 0'});
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[this.pageTitle,navigation,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    container.on("afterrender",function(){
	    	this.callService('queryJxzzjgTree',function(data){
                this.navigation.refreshTpl(data.queryJxzzjgTree);
                var i = 0;
                for(var key in data.queryJxzzjgTree){
                    if(i==0){
                        var nodeId = data.queryJxzzjgTree[key].id;
                        this.navigation.setValue(nodeId);
                        this.params.zzjgId = nodeId;
                    }
                    i++;
                }
            },this);
	    	this.fillCompData();
	    	this.bindEvents();
            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgPid = data.nodes[len-1].pid;
				me.fillCompData();
            });
	    },this);
	    this.setPageComponent(container);
	},
	dateSection :new NS.appExpand.DateSection(),
	xsColumn1 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		style : {
			padding : "0px"
		},
		margin : "0px 0px 10px 0px"
	}),
	xsColumn2 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		style : {
			padding : "0px"
		},
		margin : "0px 0px 10px 0px"
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "一卡通刷卡排名",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	title2 : new Exp.chart.PicAndInfo({
		title : "一卡通消费排名",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	createComps : function(){
		var me = this,
		today = new Date();
		me.dateSection.setValue({from : today - 15 * 3600* 24000,to : today});
		var xx = new Ext.Component({
			tpl : "☞选择开始时间和结束时间，如果时间正确，页面会自动刷新。 ",
			data : {}
		});
		
		var dt = new NS.container.Container({
			layout : "column",
			items : [me.dateSection,xx],
			cls : "div-border",
			style : {
				"margin-bottom" : "10px"
			}
		});
		
		var result = new NS.container.Container({
			items : [dt,me.title1,me.xsColumn1]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;
		
		$("#"+me.xsColumn1.id).html(me.loadingHtml);
		me.params.cflx='all';
		me.params.order='sk';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.xsColumn1.addChart(
				me.renderCommonChart({
					divId : me.xsColumn1.id,
		    	    title : "一卡通刷卡排名",
		    	    yAxis : "",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
		
		$("#"+me.xsColumn2.id).html(me.loadingHtml);
		me.params.cflx='all';
		me.params.order='je';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.xsColumn2.addChart(
				me.renderCommonChart({
					divId : me.xsColumn2.id,
		    	    title : "一卡通消费排名",
		    	    yAxis : "",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
	},
	bindEvents : function(){
		var me = this;
		me.dateSection.addListener('validatepass',function(){
			me.params.isfast='0';
			me.fillCompData();
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});
