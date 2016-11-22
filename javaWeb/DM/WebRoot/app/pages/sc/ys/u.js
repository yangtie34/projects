/**
 * 教职工教学任务统计
 * User: zhangzg
 * Date: 15-1-17
 * Time: 下午15:44
 *
 */
NS.define('Pages.sc.ys.u',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryJxzzjgTree: 'scService?getYxCcTree',// 获取导航栏数据，教学组织结构
            getlv:'studentMarkService?getLv',
            getls:'studentMarkService?getLsCj',
            queryGridContent: {
                service:"studentMarkService?queryGridContent",
                params:{
                    limit:20,
                    start:0
                }
            },
            deleteStudent:"leaveSchoolService?deleteStudent",
            getCj:'studentMarkService?getCjFb',
		
        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:['Pages.zksf.comp.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'各院系学生成绩统计',
                pageHelpInfo:'按照院系、专业分析统计学生成绩，合格率、优秀率、平均分、方差、均方差、标准差反映学生成绩整体水平'}
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,containerx]
        });
        
        
        this.createGrid();
        
        this.setPageComponent(container);

        container.on('afterrender',function(){
            // 刷新导航栏
            this.callService('queryJxzzjgTree',function(data){
                this.navigation.refreshTpl(data.queryJxzzjgTree);
            },this);
            this.fillCompoByData();
            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgmc = data.nodes[len-1].mc;
                me.fillCompoByData();
                me.tplGrid.load(me.params);
                me.showHide.hide();
                
            });
        },this);
      

    },
    chart7 : new Exp.chart.HighChart({
		cls : 'div-border',
		height : 300,
		style : {
			padding : "0px"
		}
	}),
    chart8 : new Exp.chart.HighChart({
        html:"<div  class='loading-indicator'>正在加载....</div>",
        height:580
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "各院系成绩情况对比",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "成绩趋势变化图",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title3 : new Exp.chart.PicAndInfo({
        title : "各院系成绩情况对",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    /**
     * 创建中部容器组件。
     */
    createMain:function(){
        this.createCombobox();
        var xnxqContainer = new Ext.container.Container({
            layout:"column",
            margin:'5 0 0 10',
            items:[this.combobox,this.xqcombobox]
        });
        this.gridContainer = new Ext.container.Container();
        
        this.showHide = new Ext.container.Container({
            items:[ this.title2,this.chart8]
        });
        
        var containerx = new Ext.container.Container({
            items:[
                xnxqContainer,
                this.title1,this.chart7,this.showHide,
                this.title3,this.gridContainer]
        });
        this.showHide.hide();
        return containerx;
    },
    createCombobox:function(){
        var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : [{id:2010,mc:'2010-2011学年'},
                {id:2011,mc:'2011-2012学年'},
                {id:2012,mc:'2012-2013学年'},
                {id:2013,mc:'2013-2014学年'},
                {id:2014,mc:'2014-2015学年'},
                {id:2015,mc:'2015-2016学年'},
                {id:2016,mc:'2016-2017学年'},
                {id:2017,mc:'2017-2018学年'}]
        });

        var combobox = this.combobox = new Ext.form.ComboBox({
            width:200,
            labelWidth:60,
            store: states,
            fieldLabel:'选择学年',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var xq = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : [{id:0,mc:'第一学期'},
                {id:1,mc:'第二学期'}]
        });
        var xqCom = this.xqcombobox = new Ext.form.ComboBox({
            width:200,
            labelWidth:60,
            store: xq,
            fieldLabel:'选择学期',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var myDate = new Date();
        myDate.getYear();        //获取当前年份(2位)
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
        var month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
        var value = month<7?Number(year-1):Number(year);
        var currentxq = month<7&&month>=2?1:0;
        combobox.setValue(value);
        this.params.xn = value;
        xqCom.setValue(currentxq);
        this.params.xq = currentxq;
        combobox.on('change',function(compo,newValue,oldValue){
            this.params.xn = newValue;
            this.fillCompoByData();
            this.tplGrid.load(this.params);
          
        },this);
        xqCom.on('change',function(compo,newValue,oldValue){
            this.params.xq = newValue;
            this.fillCompoByData();
            this.tplGrid.load(this.params);

        },this);
        /*this.callService('queryTjlx',function(data){
            //states.loadData(data.queryTjlx);
            todo:添加获取学年学期列表的方法,暂时写死了，日后再改
        },this);*/
    },
    fillCompoByData:function(){
    	var me=this;
    	$("#"+me.chart7.id).html(me.loadingHtml);
    	me.callService({key:'getlv',params: me.params},function(data){
			me.chart7.addChart(
				me.renderCommonChart({
					divId : me.chart7.id,
		    	    title : "院系成绩情况",
		    	    yAxis : "优秀率/合格率",
		    	    isSort : false,
		    	    data : data.getlv,
		    	    type :"column" 
				})
			);
		});
    	
    	
    	
    },
    loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
    
    
    createGrid:function(){
        var params = {start:0,limit:20};
        Ext.apply(this.params,params);
        this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
                this.tableData = respData.queryGridContent;
                this.gridFields =["ID","XY","HGL","PJF","ZGF","JC","ZWS","FC","BZC","YXL",'LS','CJFB'];

                this.tplGrid = this.initXqGrid(this.tableData,this.gridFields,this.convertColumnConfig(),this.params);

                this.gridContainer.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });

                this.tplGrid.bindItemsEvent({
                    'LS' :{event:'linkclick',fn:this.clickLs,scope:this}
                });
                
                this.tplGrid.bindItemsEvent({
                    'CJFB' :{event:'linkclick',fn:this.clickCj,scope:this}
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
            pageSize : 20,
            proxy : this.model,
            serviceKey:{
                key:'queryGridContent',
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
        var textarrays = "ID,部门,合格率,平均分,最高分,极差,中位数,方差,标准差,优秀率,历史趋势,成绩分布".split(",");
        var widtharrays = [90,100,100,100,100,100,100,100,100,100,150,150];
        var hiddenarrays = [true,false,false,false,false,false,false,false,false,false,false,false];
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
            case 'LS':
                Ext.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer:function(data){
                        return '<a href="javascript:void(0);">历史趋势</a>';
                    }
                });
                break;
            case 'CJFB':
                Ext.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer:function(data){
                        return '<a href="javascript:void(0);">成绩分布</a>';
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
    clickLs:function(text){
    	var me=this;
    	me.params.thisId=arguments[3].ID;
    	var name=arguments[3].XY+"——历年成绩趋势";
    	me.showHide.show();
    	$("#"+me.chart8.id).html(me.loadingHtml);
    	me.callService({key:'getls',params: me.params},function(data){
			me.chart8.addChart(
				me.renderCommonChart({
					divId : me.chart8.id,
		    	    title : name,
		    	    yAxis : "%",
		    	    isSort : false,
		    	    data : data.getls,
		    	    type :"spline" 
				})
			);
		});
    },
    clickCj:function(text){
    	var me=this;
    	me.params.thisId=arguments[3].ID;
    	var name=arguments[3].XY+"——本学期成绩分布";
    	me.showHide.show();
    	$("#"+me.chart8.id).html(me.loadingHtml);
    	me.callService({key:'getCj',params: me.params},function(data){
			me.chart8.addChart(
				me.renderCommonChart({
					divId : me.chart8.id,
		    	    title : name,
		    	    yAxis : "人数",
		    	    isSort : false,
		    	    data : data.getCj,
		    	    type :"column" 
				})
			);
		});
    },
    
});