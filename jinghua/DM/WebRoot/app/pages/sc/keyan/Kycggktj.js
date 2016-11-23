/**
 * 科研成果概况统计
 */
NS.define('Pages.sc.keyan.Kycggktj', {
	extend:'Pages.sc.keyan.KeyanXmSituation',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'科研成果展示',
            pageHelpInfo:'按时间、部门统计著作、艺术、论文、专利、研究成果等科研成果信息。'
        }
    }),
    params:{},
	modelConfig: {
		serviceConfig: {
	    	queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，宿舍组织结构
            queryZlcount : 'kycgService?getZlxx',
            queryZzcount : 'kycgService?getZzxx',
            queryLwcount : 'kycgService?getLwxx',
            queryXmcount : 'kycgService?getXmxx',
            queryHjcgcount : 'kycgService?getHjcgxx',
            queryChartData : 'kycgService?getChartData',
            queryGridContent: {
                service:"kycgService?queryGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            queryTimeShaftData:'kycgService?getTimeShaftData'
	   }
	},
	tplRequires : [
	],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	init: function () {
		this.callService('queryYxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryYxzzjgTree);
            var i = 0;
            for(var key in data.queryYxzzjgTree){
                if(i==0){
                    var nodeId = data.queryYxzzjgTree[key].id;
                    this.navigation.setValue(nodeId);
                    this.params.zzjgId = nodeId;
                }
                i++;
            }
            this.initParams();
            this.fillCompData();
            this.createGrid();
        },this);
		
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
            this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	    
	},
	
	yearPicker : new Exp.component.SimpleYear({
        start:1900,
        cls : 'div-border',
        width : "100%",
        margin : '0px 0px 10px 0px'
    }),
    navigation : new Exp.component.Navigation({
        margin : '0px 0px 10px 0px'
    }),
    t1 : new Exp.component.SimpleNumberWidget2({
    	columnWidth : 1/6,
    	padding : 10,
    	margin : "0px 5px 0px 0px",
    	style : {"background-color" : "#08bbdc","color" : "#FFF","font-weight":"bold"}
    }),
    
    t2 : new Exp.component.SimpleNumberWidget2({
    	columnWidth : 1/6,
    	padding : 10,
    	margin : "0px 5px",
    	style : {"background-color" : "#5fb810","color" : "#FFF","font-weight":"bold"}
    }),
    t3 : new Exp.component.SimpleNumberWidget2({
    	columnWidth : 1/6,
    	padding : 10,
    	margin : "0px 5px",
    	style : {"background-color" : "#dd8e15","color" : "#FFF","font-weight":"bold"}
    }),
    t4 : new Exp.component.SimpleNumberWidget2({
    	columnWidth : 1/6,
    	padding : 10,
    	margin : "0px 5px",
    	style : {"background-color" : "#e76122","color" : "#FFF","font-weight":"bold"}
    }),
    t5 : new Exp.component.SimpleNumberWidget2({
    	columnWidth : 1/6,
    	padding : 10,
    	margin : "0px 5px",
    	style : {"background-color" : "#bc1f3e","color" : "#FFF","font-weight":"bold"}
    }),
    t6 : new Exp.component.SimpleNumberWidget2({
    	columnWidth : 1/6,
    	padding : 10,
    	margin : "0px 5px",
    	style : {"background-color" : "#5fb810","color" : "#FFF","font-weight":"bold"}
    }),
	
    title1 : new Exp.chart.PicAndInfo({
        title : "科研成果展示",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),

    title2 : new Exp.chart.PicAndInfo({
        title : "科研成果时间轴",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    
    table1 : new  Ext.container.Container({
		margin : "0px 10px 0px 0px",
		columnWidth : 1/2
	}),
	chart1 : new Exp.chart.HighChart({
		padding : 0,
		cls : 'div-border',
		columnWidth : 1/2
	}),
	
	timeShaft : new Exp.chart.TimeShaft(),
	
	
	createComps : function(){
		var me = this;
		var top = new NS.container.Container({
			items : [me.t1,me.t2,me.t3,me.t4,me.t5,me.t6],
			layout : 'column',
		});
		var tabchar = new NS.container.Container({
			layout : 'column',
			items : [ me.table1 ,me.chart1]
		});
		
		var result = new NS.container.Container({
			items : [me.yearPicker, me.navigation,top,me.title1,tabchar,me.title2,me.timeShaft]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.callService([
            {key:'queryZlcount',params:me.params},
            {key:'queryZzcount',params:me.params},
            {key:'queryLwcount',params:me.params},
            {key:'queryXmcount',params:me.params},
            {key:'queryHjcgcount',params:me.params}],function(respData){
            //metro 表格更新数据
            var lws = Number(respData.queryLwcount),zzs = Number(respData.queryZzcount),
                xms = Number(respData.queryXmcount),zls = Number(respData.queryZlcount),
                hjcgs = Number(respData.queryHjcgcount);
            me.t1.update({title : "获得科研成果数",value : lws+zzs+xms+zls});
            me.t2.update({title : "论文",value : lws});
            me.t3.update({title : "著作",value : zzs});
            me.t4.update({title : "科研项目",value : xms});
            me.t5.update({title : "专利",value : zls});
            me.t6.update({title : "获奖成果",value : hjcgs});
        });

		me.callService([{key:'queryChartData',params:me.params}],function(respData){
            // chart 更新数据
            me.chart1.addChart(me.renderPieChart({
                divId :me.chart1.id,
                title: "",
                data : respData.queryChartData
            }));
        });
        if(this.tplGrid){
            this.tplGrid.load(this.params);
        }
		// table 更新数据
		me.table1.update([]);
		
		//更新时间轴内容
		me.timeShaft.clear();
		me.exFunction.addTimeShaftItem.call(me);
	},
	bindEvents : function(){
		var me = this;
		me.yearPicker.on('validatepass',function(){
            var data = this.getValue();
            me.params.from = data.from;
            me.params.to = data.to;
			me.fillCompData();
        });
		
		me.navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
			me.fillCompData();
        });
		
		me.timeShaft.on("readMore",function(){
			me.exFunction.addTimeShaftItem.call(me);
		});
	},
	exFunction : {
		addTimeShaftItem:function(){
			var me = this;
			// 获取时间轴中当前包含的item数量
			var num = me.timeShaft.total;
            var params ={
                start:num,
                limit:3
            };
            Ext.apply(me.params,params);
			//获取数据，填充  -- callService
            me.callService({key:'queryTimeShaftData',params:me.params},function(respData){
                var datas = respData.queryTimeShaftData.data,items=[];
                for(var i = 0,len= datas.length;i<len;i++){
                    var obj = datas[i],temp={};
                    temp.date =obj.CGRQ;
                    temp.html ='<table class="table1"><thead><tr>'+
                    '<th>成果类型</th>'+
                        '<th>成果名称</th>'+
                        '<th>所属单位</th>'+
                        '</tr></thead>' +
                        '<tbody><tr>'+
                        '<td>'+obj.CGLX+'</td>'+
                        '<td>'+obj.CGMC+'</td>'+
                        '<td>'+obj.SSDW+'</td>'+
                    '</tr></tbody>' +
                        '<tr><td colspan="3" >项目简介</td></tr>' +
                        '<tr><td colspan="3" style="color: red;">未维护</td></tr>' +
                        '</table>';
                    items.push(temp);
                }
                me.timeShaft.addItems(items);
            });

		}
	},
    initParams:function(){
        var nfdata = this.yearPicker.getValue();
        var navdata = this.navigation.getValue();
        this.params.from = nfdata.from;
        this.params.to = nfdata.to;
        this.params.zzjgId = navdata.nodes[0].id;
    },
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>数据未维护</span>",
    createGrid:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
                this.tableData = respData.queryGridContent;
                this.gridFields =["CGMC","CGLX","SSDW","CGRQ","ID"];

                this.tplGrid = this.initXqGrid(this.tableData,this.gridFields,this.convertColumnConfig(),this.params);

                this.table1.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });
            });
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){
        var arrays = this.gridFields;
        var textarrays = "成果名称,成果类型,所属单位,成果获得日期,ID".split(",");
        var widtharrays = [150,80,100,80,90];
        var hiddenarrays = [false,false,false,false,true];
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
    }
});
