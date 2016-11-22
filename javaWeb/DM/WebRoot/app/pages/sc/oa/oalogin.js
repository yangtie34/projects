/**
 * 图书借阅TOP10
 */
NS.define('Pages.sc.oa.oalogin',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'登录次数统计',
            pageHelpInfo:'登录次数统计'
        }
    }),
    modelConfig: {
        serviceConfig: {
            getAllUserLoginCounts: {
                service:"oaService?getAllUserLoginCounts",
                params:{
                    limit:20,
                    start:0
                }
            },
            getAllLoginLog:"oaService?getAllLoginLog",
            getLoginLog:"oaService?getLoginLog"
        }
    },
    tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css','app/pages/zksf/css/tsjyxx.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	params:[],
	init: function () {
		var me = this;
	    var comps =  this.createComps();
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
        this.createGrid();
	    this.setPageComponent(container);
	},
    t1 : new Exp.component.SimpleNumberWidget2({
        data : {},
        columnWidth : 1/2,
        border : 0
    }),
    t2 : new Exp.component.SimpleNumberWidget2({
        data : {},
        columnWidth : 1/2
    }),

    t3 : new Exp.component.SimpleNumberWidget2({
        data : {},
        columnWidth : 1/2,
        border : 0
    }),
    t4 : new Exp.component.SimpleNumberWidget2({
        data : {},
        columnWidth : 1/2
    }),

    titleTop1 : new NS.container.Container({
        html:"",
        autoScroll:true
    }),

    titleTop2 : new NS.container.Container({
        html:"",
        autoScroll:true
    }),

	dateSection :new NS.appExpand.DateSection(),

    titleDom: "选择的时间",

	title1 : new Exp.chart.PicAndInfo({
        title : "人员访问系统排名",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    dataTable : new Ext.container.Container({
    }),
    createComps : function(){
		var me = this;
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

		var comp1 = new NS.container.Container({
            items : [me.title1],
    		columnWidth: 1
        });

        var comp2 = new NS.container.Container({
            items : [me.titleTop1],
            columnWidth: 1
        });

        var comp3= new NS.container.Container({
            items : [me.titleTop2],
            columnWidth: 1
        });



		var top1 = new NS.container.Container({
            padding:5,
            margin:"0px 0px 10px 0px",
            autoScroll:true,
            items:[comp2,me.t1,me.t2],
            style : {
                "background-color" : "#fefaef",
                "border" : "1px solid #eaeaea"
            },
            layout : "column",
            columnWidth: 1/2
        });

        var top2 = new NS.container.Container({
            padding:5,
            margin:"0px 0px 10px 50px",
            autoScroll:true,
            items:[comp3,me.t3,me.t4],
            style : {
                "background-color" : "#fefaef",
                "border" : "1px solid #eaeaea"
            },
            layout : "column",
            columnWidth: 1/2
        });


        var top=new NS.container.Container({
            items : [top1,top2],
            layout : "column"
        });

		var result = new NS.container.Container({
            items : [dt,top,comp1,this.dataTable]
        });
		
		return result;
	},
	fillCompData : function(){
		var me = this;
        $("#" + me.t1.id).html(me.loadingHtml);
        $("#" + me.t2.id).html(me.loadingHtml);
        $("#" + me.t3.id).html(me.loadingHtml);
        $("#" + me.t4.id).html(me.loadingHtml);
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;

        me.titleTop1.updateHtml(me.getTitleHtml("至今"));

        me.titleTop2.updateHtml(me.getTitleHtml(me.params.fromDate+" 至 "+me.params.toDate));
        
        me.callService([{key:'getAllLoginLog'},{key:'getLoginLog',params:me.params}],function(data){
        	var allCount="--",allAvg="--",thisCount="--",thisAvg="--";
        	if(data.getAllLoginLog.length>0){
        		var datalogin=data.getAllLoginLog[0];
        		allCount=datalogin.ALL_LOGIN_COUNTS;
        		allAvg=datalogin.AVG_DAY_LOGIN;
        	}
        	if(data.getLoginLog.length>0){
        		var datalogin=data.getLoginLog[0];
        		thisCount=datalogin.LOGIN_COUNTS;
        		thisAvg=datalogin.AVG_DAY_LOGIN;
        	}
        	
            me.t1.update({
                title : "总浏览量",
                value :allCount ,
                unit : "次"
            });
            me.t2.update({
                title : "日均浏览量",
                value :allAvg ,
                unit : "次/天"
            });
            
            me.t3.update({
                title : "总浏览量",
                value : thisCount,
                unit : "次"
            });

            me.t4.update({
                title : "日均浏览量",
                value : thisAvg,
                unit : "次/天"
            });
            
        });

	},

    getTitleHtml:function(text){
        var html="<div style='font-size: 18px; margin-bottom: 10px; border-bottom: 1px solid; color: #606060;'>"+text+"</div>";
        return html;
    },
    bindEvents : function(){
		var me = this;
		me.dateSection.addListener('validatepass',function(){
			me.fillCompData();
            me.tplGrid.load(me.params);
		});
	},
    createGrid:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'getAllUserLoginCounts',params:this.params}],
            function(respData){
                this.tableData = respData.getAllUserLoginCounts;
                this.gridFields =["JZG_NAME","LOGIN_COUNTS","RSNUM"];

                this.tplGrid = this.initXqGrid(this.tableData,this.gridFields,this.convertColumnConfig(),this.params);

                this.dataTable.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 10,
            proxy : this.model,
            serviceKey:{
                key:'getAllUserLoginCounts',
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
    convertColumnConfig : function(){
        var arrays = this.gridFields;
        var textarrays = "姓名,访问次数,排名".split(",");
        var widtharrays = [150,150,100];
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

	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});