/**
 * 宿舍楼住宿统计
 */
NS.define('Pages.zksf.Sslzstj', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生住宿明细',
            pageHelpInfo:'学生住宿明细情况'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	sslist : "sslzsqkService?queryAllDorms", //所有宿舍楼信息
	    	sslzsxb : "sslzsqkService?queryDormZyxb", //宿舍楼住宿性别情况
	    	sslxsszyx : "sslzsqkService?queryDormXsszyx", //宿舍楼住宿学生所在院系情况
	    	sslinfo : "sslzsqkService?queryDormInfo", //查询宿舍楼详细信息
	    	ssllcxx : "sslzsqkService?queryDormLc" , //宿舍楼楼层信息
	    	sslfjxx : {
                service:"sslzsqkService?queryDormFjxx",
                params:{
                    limit:10,
                    start:0
                }
            }, //宿舍楼房间信息
            ssljcrs : "sslzsqkService?queryDormjcrs", // 宿舍楼进出人数
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
	    	this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	},
	sslist : new Exp.component.Sslist({
		height : 750,
		data : []
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "宿舍楼住宿情况",
		onlyTitle : true,
		margin : "0px 0px 10px 0px"
	}),
	
	piechart1 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth : 1/2,
		style : {
			padding : "0px",
			"border-right-width" : "0px"
		}
	}),
	columnchart1 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		columnWidth : 1/2,
		style : {
			padding : "0px"
		}
	}),
	ssinfo : new Ext.Component({
		tpl : "宿舍楼[{name}]共 {cs} 层，房间数共计 {fjs} 间，共可以住宿人数 {cws} 人，已经住宿  {yzrs} 人，入住率{rzl}。 ",
		data : {}
	}),
	sscombo : new Ext.form.field.ComboBox({
		fieldLabel: '楼层',
	    queryMode: 'local',
	    store:{
	    	fields: ['id', 'name'],
    	    data :  []
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 200,
	    value : 1,
	    margin : 0
	}),
	sscombo1 : new Ext.form.field.ComboBox({
		fieldLabel: '状态',
	    queryMode: 'local',
	    store:{
	    	fields: ['id', 'name'],
    	    data :  [{id : 1,name : "全部" },{id : 2,name : '已住满'},{id : 3,name : '有空床' }]
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 200,
	    value : 1,
	    margin : "0px 0px 0px 30px",
	}),
	middleDataPicker : new Ext.form.field.Date({
		fieldLabel: '日期',
		anchor: '100%',
	    format : "Y-m-d",
	    value: new Date(),
	    maxValue: new Date(),
	    border : 10
	}),
	middleChart : new Exp.chart.HighChart({
		columnWidth : 1/2,
		height : 325,
		style : {
			padding : "0px",
			"border-right-width" : 0
		}
	}), 
	createtopgrid : function(){
    	var me = this;
    	var grid = new NS.grid.SimpleGrid({
            autoScroll: true,
            pageSize : 10,
            proxy : me.model,
            multiSelect: false,
            lineNumber: true,
            serviceKey:{
                key:'sslfjxx',
                params:{ssid :1,lc:1,lx :1}
            },
            fields : ['FJH','CWS','YZSRS','KCWS','FJDM','ZSXB','SFBZ'],
            columnConfig : [{ xtype : 'column',name : "FJH",text : '房间号',width : 60,hidden : false,align : 'center'},
                            { xtype : 'column',name : "CWS",text : '床位数',width : 60,hidden : false,align : 'center'},
                            { xtype : 'column',name : "YZSRS",text : '已住宿人数',width : 80,hidden : false,align : 'center'},
                            { xtype : 'column',name : "KCWS",text : '空床位数',width : 80,hidden : false,align : 'center'},
                            { xtype : 'column',name : "FJDM",text : '房间代码',width : 100,hidden : false,align : 'center'},
                            { xtype : 'column',name : "ZSXB",text : '住宿性别',width : 80,hidden : false,align : 'center'},
                            { xtype : 'column',name : "SFBZ",text : '收费标准',width : 80,hidden : false,align : 'center'}
                            ],
            border: true,
            checked: false,
            height : 285,
            style : {
            	'margin-top' : "10px"
            }
        });
    	return grid;
    },
	createComps : function(){
		var me = this;
		var charts = new NS.container.Container({
			layout : 'column',
			items : [me.piechart1,me.columnchart1]
		});
		
		var trbl = new NS.container.Container({
			columnWidth : 1/2,
			items : [me.middleDataPicker,me.middleChart],
			style : {
				"background-color" : "#FFF",
				"padding" : "10px"
			}
		});
		
		var sscombobox = new NS.container.Container({
			cls : 'div-border',
			layout : 'column',
			items : [me.sscombo,me.sscombo1],
			style : {
				"padding" : "5px",
				"background-color" : "#fdfef0",
				"margin-top" : "10px"
			}
		});
		me.topgrid = me.createtopgrid();
		var trbr = new NS.container.Container({
			columnWidth : 1/2,
			cls : 'div-border',
			items : [me.ssinfo,sscombobox,me.topgrid],
			style : {
				"background-color" : "#FFF",
				"border-width" : "0px 0px 0px 1px"
			}
		});
		var trb =  new NS.container.Container({
			layout : "column",
			cls : 'div-border',
			items : [trbl,trbr],
			style : {
				padding : "0px",
				"margin-top" : "10px"
			}
		});
		
		var tr =  new NS.container.Container({
			cls : 'div-border',
			columnWidth : 1,
			items : [me.title1,charts,trb],
			style : {
				"margin-left" : "10px",
				'background-color' : '#faffed',
				'border-radius' : "10px"
			}
		});
		var top = new NS.container.Container({
			layout : 'column',
			items : [me.sslist,tr]
		});
		
		var result = new NS.container.Container({
			items : [top]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.callService("sslist",function(data){
			me.sslist.update(data.sslist);
			$("#" + this.sslist.id +"_container").find("input[type=button]")[0].click();
		});
	},
	bindEvents : function(){
		var me = this;
		me.sslist.on("select",function(ssid,ssmc){
			me.sslistclick(ssid,ssmc);
		});
		me.sscombo.on("select",function(cmb){
			me.sscomboclick(me.sslist.getValue(), cmb.value,me.sscombo1.getValue());
		});
		me.sscombo1.on("select",function(cmb){
			me.sscomboclick(me.sslist.getValue(),me.sscombo.getValue(), cmb.value);
		});
		me.middleDataPicker.on("select",function(obj,val,opts){
			me.ssdatepickerclick(me.sslist.getValue(),obj.getRawValue());
		});
	},
	//左侧列表点击后刷新右侧
	sslistclick : function(ssid,ssmc){
		var me = this;
		$("#"+me.title1.id).find("div").find("span").html(ssmc);
		me.currentDorm = ssmc;
		me.callService({key:'sslxsszyx',params:{ssid : ssid}},function(data){
			me.columnchart1.addChart(
				me.renderCommonChart({
					divId : me.columnchart1.id,
		    	    title : "各院系住宿人数",
		    	    yAxis : "件",
		    	    isSort : false,
		    	    data : data.sslxsszyx,
		    	    type :"column" 
				})
			);
		});
		
		me.callService({key:'sslzsxb',params:{ssid : ssid}},function(data){
			me.piechart1.addChart(
				me.renderPieChart({
					divId :me.piechart1.id,
					title: "住宿学生男女占比",
					data :data.sslzsxb,
					showLable: true
				})
			);
		});
		me.callService({key : "sslinfo",params : {ssid : ssid}},function(data){
			me.ssinfo.update(data.sslinfo);
		});
		
		me.callService({key : "ssllcxx",params : {ssid : ssid}},function(data){
			me.sscombo.getStore().loadData(data.ssllcxx,false);
			me.sscombo.setValue(data.ssllcxx[0].id);
			me.sscomboclick(ssid, data.ssllcxx[0].id,me.sscombo1.getValue());
		});
		me.ssdatepickerclick(ssid,me.middleDataPicker.getRawValue());
		
	},
	sscomboclick : function(ssid,lc,lx){
		var me = this;
		me.topgrid.load({ssid : ssid ,lc : lc,lx : lx});
	},
	ssdatepickerclick : function(ssid,date){
		var me = this;
		$("#"+me.middleChart.id).html(me.loadingHtml);
		me.callService({key:'ssljcrs',params:{ssid : ssid, ssmc : me.currentDorm,date : date}},function(data){
			if (data.ssljcrs.length == 0) {
				$("#"+me.middleChart.id).html("<span style='color : red;'>学生进出宿舍楼人数情况没有数据</span>");
			}else{
				me.middleChart.addChart(
					me.renderCommonChart({
						divId :me.middleChart.id,
						title: "宿舍楼进出人数情况",
						yAxis : "人",
						data :data.ssljcrs,
						type : "spline"
					})
				);
			}
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});
