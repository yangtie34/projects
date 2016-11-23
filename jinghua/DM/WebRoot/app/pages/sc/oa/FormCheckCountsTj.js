/**
 * 公告查看次数统计
 */
NS.define('Pages.sc.oa.FormCheckCountsTj',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'公告查看次数统计',
            pageHelpInfo:'公告查看次数统计'
        }
    }),
    modelConfig: {
        serviceConfig: {
        	getCheckByDept : "oaService?getCheckByDept",//获取部门查看公告排名
        	getAllNotice : "oaService?getAllNotice",//查询各公告查看次数
        	getNoticeCheckByDept : "oaService?getNoticeCheckByDept",//某公告被各部门查看排名
        	getNoticeCountsByUser : "oaService?getNoticeCountsByUser"//查看各员工公告查看情况
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
	    var container = new NS.container.Container({
	        padding:10,
	        autoScroll:true,
	        items:[this.pageTitle,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    container.on("afterrender",function(){
	    	this.fillCompData();
	    },this);
	    this.bindEvents();
	    this.createGrid1();
	    this.createGrid2();
	    this.createGrid3();
	    this.createGrid4();
	    this.setPageComponent(container);
	},
	dateSection :new NS.appExpand.DateSection(),
	title1 : new Exp.chart.PicAndInfo({
        title : "各部门查看公告排名",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "公告查看排名",
        onlyTitle : true
    }),
    title3 : new Exp.chart.PicAndInfo({
        title : "各部门查看这条公告排名",
        onlyTitle : true
    }),
    title4 : new Exp.chart.PicAndInfo({
        title : "各职工查看公告情况",
        onlyTitle : true
    }),
    
    table1 : new Ext.container.Container({
    	
    }),
    
    table2 : new Ext.container.Container({
    	
    }),
    
    table3 : new Ext.container.Container({
    	
    }),
    
    table4 : new Ext.container.Container({
    	
    }),
    
    
    createComps : function(){
		var me = this;
		today = new Date();
		me.dateSection.setValue({from : today - 15 * 3600* 24000,to : today});
		var xx = new Ext.Component({
			tpl : "☞选择开始时间和结束时间，如果时间正确，页面会自动刷新，统计该时间段内的公告查看情况信息。 ",
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
		
		var comp1 = new NS.container.Container({
            items : [me.title1,me.table1],
    		columnWidth: 1
        });
		
		var comp2 = new NS.container.Container({
            items : [me.title2,me.table2],
            style:{
                "padding" : "5px",
                "margin-bottom" : "10px"
            },
    		columnWidth: 0.75
        });
		
		var comp3 = new NS.container.Container({
            items : [me.title3,me.table3],
            style:{
                "padding" : "5px",
                "margin-bottom" : "10px"
            },
    		columnWidth: 0.25
        });
		
		var comp23 = new NS.container.Container({
			layout:"column",
            items : [comp2,comp3],
            style:{
                "margin-bottom" : "10px"
            },
    		columnWidth: 1
        });
		me.title3.hide();
		me.table3.hide();
		var comp4 = new NS.container.Container({
            items : [me.title4,me.table4],
    		columnWidth: 1
        });
		
		me.top = new NS.container.Container({
            items : [comp1,comp23,comp4]
        });
		var result = new NS.container.Container({
            style:{
               
            },
            items : [dt,me.top]
        });
		
		return result;
	},
	
	createGrid1:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'getCheckByDept',params:this.params}],
            function(respData){
                this.tableData1 = respData.getCheckByDept;
                this.gridFields1 =["DEPT_NAME","DEPT_CHECK_COUNTS","RSNUM"];

                this.tplGrid1 = this.initXqGrid1(this.tableData1,this.gridFields1,this.convertColumnConfig1(),this.params);

                this.table1.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid1.getLibComponent()]
                });
                this.tplGrid1.bindItemsEvent({
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid1 : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 10,
            proxy : this.model,
            serviceKey:{
                key:'getCheckByDept',
                params:queryParams
            },
            multiSelect: false,
            lineNumber: true,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig1 : function(){
        var arrays = this.gridFields1;
        var textarrays = "部门名称,查看次数,排名".split(",");
        var widtharrays = [310,310,310];
        var hiddenarrays = [false,false,false];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            columns.push(basic);
        }
        return columns;
    },
	
    createGrid2:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'getAllNotice',params:this.params}],
            function(respData){
                this.tableData2 = respData.getAllNotice;
                this.gridFields2 =["NOTICE_ID","THEME","CHECK_COUNTS","CREATE_TIME","CZ"];

                this.tplGrid2 = this.initXqGrid2(this.tableData2,this.gridFields2,this.convertColumnConfig2(),this.params);

                this.table2.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid2.getLibComponent()]
                });
                this.tplGrid2.bindItemsEvent({
                    'CZ' :{event:'linkclick',fn:this.showTable3,scope:this}
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid2 : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 10,
            proxy : this.model,
            serviceKey:{
                key:'getAllNotice',
                params:queryParams
            },
            multiSelect: false,
            lineNumber: true,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig2 : function(){
        var arrays = this.gridFields2;
        var textarrays = "公告ID,公告主题,查看次数,发布时间,操作".split(",");
        var widtharrays = [90,400,60,150,80];
        var hiddenarrays = [true,false,false,false,false];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            switch(arrays[i]){
            case 'CZ':
                Ext.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer:function(data){
                        return '<a href="javascript:void(0);">查看公告</a>';
                    }
                });
                break;
            default:
                break;
            }
            columns.push(basic);
        }
        
        return columns;
    },
    
    createGrid3:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'getNoticeCheckByDept',params:this.params}],
            function(respData){
                this.tableData3 = respData.getNoticeCheckByDept;
                this.gridFields3 =["DEPT_NAME","CHECK_COUNTS","RSNUM"];

                this.tplGrid3 = this.initXqGrid3(this.tableData3,this.gridFields3,this.convertColumnConfig3(),this.params);

                this.table3.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid3.getLibComponent()]
                });
                this.tplGrid3.bindItemsEvent({
                    //'CZ' :{event:'linkclick',fn:this.deleteCode,scope:this}
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid3 : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 10,
            proxy : this.model,
            serviceKey:{
                key:'getNoticeCheckByDept',
                params:queryParams
            },
            multiSelect: false,
            lineNumber: true,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig3 : function(){
        var arrays = this.gridFields3;
        var textarrays = "部门名称,查看次数,排名".split(",");
        var widtharrays = [90,60,40];
        var hiddenarrays = [false,false,false];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            columns.push(basic);
        }
        return columns;
    },
    
    createGrid4:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'getNoticeCountsByUser',params:this.params}],
            function(respData){
                this.tableData4 = respData.getNoticeCountsByUser;
                this.gridFields4 =["JZG_NAME","CHECK_NOTICES","NO_CHECK_NOTICES"];

                this.tplGrid4 = this.initXqGrid4(this.tableData4,this.gridFields4,this.convertColumnConfig4(),this.params);

                this.table4.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid4.getLibComponent()]
                });
                this.tplGrid4.bindItemsEvent({
                   // 'CZ' :{event:'linkclick',fn:this.deleteCode,scope:this}
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid4 : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 10,
            proxy : this.model,
            serviceKey:{
                key:'getNoticeCountsByUser',
                params:queryParams
            },
            multiSelect: false,
            lineNumber: true,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig4 : function(){
        var arrays = this.gridFields4;
        var textarrays = "教职工姓名,查看公告条数,未查看公告条数".split(",");
        var widtharrays = [310,310,310];
        var hiddenarrays = [false,false,false];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            columns.push(basic);
        }
        return columns;
    },
    
	fillCompData : function(){
		var me = this;
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;
	},
	
	bindEvents : function(){
		var me = this;
		me.dateSection.addListener('validatepass',function(){
			me.fillCompData();
            me.tplGrid1.load(me.params);
            me.tplGrid2.load(me.params);
            me.tplGrid4.load(me.params);
            me.title3.hide();
    		me.table3.hide();
		});
	},
	showTable3:function(text){
    	var me=this;
    	me.params.notice_id=arguments[3].NOTICE_ID
    	me.tplGrid3.load(me.params);
    	me.title3.show();
		me.table3.show();
    },
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});