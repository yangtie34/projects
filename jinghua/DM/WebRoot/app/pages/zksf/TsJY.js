/**
 * 学生图书借阅量统计
 */
NS.define('Pages.zksf.TsJY', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'图书借阅量统计',
            pageHelpInfo:'按照时间段和院系以及借书类别统计图书借阅量。'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryJxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构
	    	bookYx : "tsJYService?queryBookNumberByYX", // 图书借阅量按院系排名
	    	bookType : "tsJYService?queryBookNumberByType", // 图书借阅按借书类别排名
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
				me.student.hide();
				me.top.show();
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
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	zxColumn : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth:0.5,
		style : {
			padding : "0px"
		}
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "图书借阅量排名",
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
		
		var topZS = new Ext.container.Container({
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
            items:[me.zxColumn,me.yxColumn]
        });
		
		var result = new NS.container.Container({
			items : [dt,topZS, me.title1,me.xsColumn]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		$("#"+me.xsColumn.id).html(me.loadingHtml);
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;
		me.callService({key:'bookYx',params: me.params},function(data){
			me.xsColumn.addChart(
				me.renderCommonChart({
					divId : me.xsColumn.id,
		    	    title : "图书借阅量排名",
		    	    yAxis : "借书数",
		    	    isSort : false,
		    	    data : data.bookYx,
		    	    type :"column" 
				})
			);
		});
		$("#"+me.yxColumn.id).html(me.loadingHtml);
		me.callService({key:'bookType',params: me.params},function(data){
			me.yxColumn.addChart(
				me.renderCommonChart({
					divId : me.yxColumn.id,
		    	    title : "图书借阅类别排名",
		    	    yAxis : "借书数",
		    	    isSort : false,
		    	    data : data.bookType,
		    	    type :"column" 
				})
			);
		});
		$("#"+me.zxColumn.id).html(me.loadingHtml);
		me.callService({key:'bookType',params: me.params},function(data){
			var cdata = [];
			for(var i=0;i<data.bookType.length;i++){
                var obj = data.bookType[i];
                cdata.push({name:obj.field,y:Number(obj.value)});
            }
			me.zxColumn.addChart(
				me.renderPieChart({
					divId : me.zxColumn.id,
		    	    title : "图书借阅类别比例",
		    	    yAxis : "借书数",
		    	    isSort : false,
		    	    showLable : true,
		    	    data : cdata,
		    	    type :"pie" 
				})
			);
		});
	},
	bindEvents : function(){
		var me = this;
		me.dateSection.addListener('validatepass',function(){
			me.fillCompData();
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});
