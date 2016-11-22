/**
 * 分类别统计学生用餐消费
 */
NS.define('Pages.sc.ykt.YktCfTj', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'分类别统计学生用餐消费',
            pageHelpInfo:'按照类别统计学生一卡通用餐的刷卡消费的频率和金额'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryJxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构
	    	yktCf : "yktCfTjService?queryYktCf", // 图书借阅按借书类别排名
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
	yxColumn1 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	zxColumn1 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	yxColumn2 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	zxColumn2 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	yxColumn3 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	zxColumn3 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "早餐刷卡消费情况",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	title2 : new Exp.chart.PicAndInfo({
		title : "午餐刷卡消费情况",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	title3 : new Exp.chart.PicAndInfo({
		title : "晚餐刷卡消费情况",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	createComps : function(){
		var me = this,
		today = new Date();
		me.dateSection.setValue({from : today - 15 * 3600* 24000,to : today});
		var xx = new Ext.Component({
			tpl : "☞选择开始时间和结束时间，如果时间正确，页面会自动刷新，统计该时间段内的学生借书情况信息。 ",
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
		
		var topZS1 = new Ext.container.Container({
            style:{
                'background':'#e4e4e4',
                'border-radius':'5px',
                'padding':'5px',
                'margin-top':'10px'
            },
            layout:{
                type:'column',
                columns:2
            },
            items:[me.zxColumn1,me.yxColumn1]
        });
		var topZS2 = new Ext.container.Container({
            style:{
                'background':'#e4e4e4',
                'border-radius':'5px',
                'padding':'5px',
                'margin-top':'10px'
            },
            layout:{
                type:'column',
                columns:2
            },
            items:[me.zxColumn2,me.yxColumn2]
        });
		var topZS3 = new Ext.container.Container({
            style:{
                'background':'#e4e4e4',
                'border-radius':'5px',
                'padding':'5px',
                'margin-top':'10px'
            },
            layout:{
                type:'column',
                columns:2
            },
            items:[me.zxColumn3,me.yxColumn3]
        });
		
		var result = new NS.container.Container({
			items : [dt,me.title1,topZS1,me.title2,topZS2,me.title3,topZS3]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;
		
		//-----早餐-----
		$("#"+me.yxColumn1.id).html(me.loadingHtml);
		me.params.cflx='zao';
		me.params.order='sk';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.yxColumn1.addChart(
				me.renderCommonChart({
					divId : me.yxColumn1.id,
		    	    title : "早餐刷卡统计",
		    	    yAxis : "刷卡次数",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
		$("#"+me.zxColumn1.id).html(me.loadingHtml);
		me.params.order='je';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.zxColumn1.addChart(
				me.renderCommonChart({
					divId : me.zxColumn1.id,
		    	    title : "早餐消费统计",
		    	    yAxis : "刷卡金额",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
		
		//-----午餐-----
		$("#"+me.yxColumn2.id).html(me.loadingHtml);
		me.params.cflx='wu';
		me.params.order='sk';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.yxColumn2.addChart(
				me.renderCommonChart({
					divId : me.yxColumn2.id,
		    	    title : "午餐刷卡统计",
		    	    yAxis : "刷卡次数",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
		$("#"+me.zxColumn2.id).html(me.loadingHtml);
		me.params.order='je';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.zxColumn2.addChart(
				me.renderCommonChart({
					divId : me.zxColumn2.id,
		    	    title : "午餐消费统计",
		    	    yAxis : "刷卡金额",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
		
		//-----晚餐-----
		$("#"+me.yxColumn3.id).html(me.loadingHtml);
		me.params.cflx='wan';
		me.params.order='sk';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.yxColumn3.addChart(
				me.renderCommonChart({
					divId : me.yxColumn3.id,
		    	    title : "晚餐刷卡统计",
		    	    yAxis : "刷卡次数",
		    	    isSort : false,
		    	    data : data.yktCf,
		    	    type :"column" 
				})
			);
		});
		$("#"+me.zxColumn3.id).html(me.loadingHtml);
		me.params.order='je';
		me.callService({key:'yktCf',params: me.params},function(data){
			me.zxColumn3.addChart(
				me.renderCommonChart({
					divId : me.zxColumn3.id,
		    	    title : "晚餐消费统计",
		    	    yAxis : "刷卡金额",
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
