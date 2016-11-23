/**
 * 学生出入图书馆统计
 */
NS.define('Pages.zksf.Xsjctsg', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生出入图书馆统计',
            pageHelpInfo:'按照时间段和院系统计学生出入图书馆人次情况。'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryJxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构
	    	xslist : "tsxxtjService?queryXsjctsgqk", //学生进出图书馆情况
	    	yxlist : "tsxxtjService?queryGyxxsjctsgqk" //学生进出图书馆人次
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
//				me.student.hide();
//				me.top.show();
            });
	    },this);
	    this.setPageComponent(container);
	},
	dateSection :new NS.appExpand.DateSection(),
	xsColumn : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		style : {
			padding : "0px"
		},
		margin : "0px 0px 10px 0px"
	}),
	yxColumn : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		style : {
			padding : "0px"
		}
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "学生各时间段进出图书馆人次",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	title2 : new Exp.chart.PicAndInfo({
		title : "学生进出图书馆人次",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	createComps : function(){
		var me = this,
		today = new Date();
		me.dateSection.setValue({from : today - 15 * 3600* 24000,to : today});
		var xx = new Ext.Component({
			tpl : "☞选择开始时间和结束时间，如果时间正确，页面会自动刷新，统计该时间段内的学生消费情况信息。 ",
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
			items : [dt, me.title1,me.xsColumn,me.title2,me.yxColumn]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		$("#"+me.xsColumn.id).html(me.loadingHtml);
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;
		me.callService({key:'xslist',params: me.params},function(data){
			me.xsColumn.addChart(
				me.renderCommonChart({
					divId : me.xsColumn.id,
		    	    title : "学生各时间段进出图书馆人次",
		    	    yAxis : "%",
		    	    isSort : false,
		    	    data : data.xslist,
		    	    type :"spline" 
				})
			);
		});
		$("#"+me.yxColumn.id).html(me.loadingHtml);
		me.callService({key:'yxlist',params: me.params},function(data){
			var titleName="学生进出图书馆人次";
			if(data.yxlist.length>0){
				titleName=data.yxlist[0].name;
			}
			me.yxColumn.addChart(
				me.renderCommonChart({
					divId : me.yxColumn.id,
		    	    title : titleName,
		    	    yAxis : "%",
		    	    isSort : false,
		    	    data : data.yxlist,
		    	    type :"column" 
				})
			);
		});
	},
	bindEvents : function(){
		var me = this;
		me.dateSection.addListener('validatepass',function(){
			me.params.isfast="0";
			me.fillCompData();
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});
