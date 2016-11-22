/**
 * 科研项目基本概况统计。
 * User: zhangzg
 * Date: 14-9-2
 * Time: 上午10:12
 *
 */
NS.define('Pages.sc.keyan.KeyanXmSituation',{
    extend:'Template.Page',
    entityName:'VTeacherRsbh',// 教职工Grid
    modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据
            queryGridContent: {
                service:"kyXmService?queryGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            queryHeaderData: 'baseService?queryForAddByEntityName',
            queryChartData:'kyXmService?getChartData',
            queryTjlx :'kyXmService?getTjlx'
        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'历年科研项目数对比统计',
                pageHelpInfo:'以项目状态、项目分类、项目来源为维度，按照日期、科研部门对科研项目数横向及纵向对比统计。'}
        });
        var simpleDate = this.simpleDate = new Exp.component.SimpleYear({
            start:1900,
            margin:'0 0 0 10'
        });

        var container = new Ext.container.Container({
            cls:'student-sta-titlediv',
            layout:{
                type:'hbox',
                align:'middle'
            },
            height:40,
            margin:'0 0 5 0',
            items:[simpleDate,new Ext.Component({
                html:'<span style="margin-left: 20px;height: 26px;line-height: 26px;color: #777;">' +
                    '☞选择开始年份和结束年份，统计该年份内的人数变化趋势及人员名单。</span>'
            })]
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,container,navigation,containerx]
        });
        this.setPageComponent(container);

        // 刷新导航栏
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
            this.fillCompoByData();
            this.createGrid();
        },this);

        navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
            me.fillCompoByData();
        });
        simpleDate.on('validatepass',function(){
            var data = this.getValue();
            me.params.from = data.from;
            me.params.to = data.to;
            me.fillCompoByData();
        });
    },
    /**
     * 创建主题容器
     */
    createMain:function(){
        var chart = this.chart = new Exp.chart.HighChart({
            height:380,
            margin:'5 20 5 20'
        });
        var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : []
        });
        var combobox = this.combobox = new Ext.form.ComboBox({
            width:120,
            store: states,
            queryMode: 'local',
            displayField: 'mc',
            valueField: 'id'
        });
        this.callService('queryTjlx',function(data){
            states.loadData(data.queryTjlx);
            combobox.setValue('1');
            combobox.on('change',function(compo,newValue,oldValue){
                this.params.tjzb = newValue;
                this.fillCompoByData();
            },this);
        },this);

        var radioGroup = this.radioGroup = new Ext.form.RadioGroup({
            width:120,
            items:[
                {boxLabel: '线形图', name: 'tjxs', inputValue: '2', checked: true },
                {boxLabel: '柱状图', name: 'tjxs', inputValue: '1' }

            ]});
        radioGroup.on('change',function(compo,newValue,oldValue){
            switch(newValue.tjxs){
                case "1":
                    this.chartCfg.chart.type='column';
                    this.currentType ='column';
                    break;
                case "2":
                    this.chartCfg.chart.type='line';
                    this.currentType ='line';
                    break;
                default:
                    break;
            }
            this.chart.redraw(this.chartCfg,true);
        },this);
        var tools = this.tools = new Ext.container.Container({
            layout : {type : 'vbox',align : 'center'},
            height:40,
            items : {
                xtype : 'container',
                padding:2,
                style:{
                    border:'1px solid #797979'
                },
                layout : {type : 'hbox',align : 'middle'},
                items:[new Ext.Component({html:'<b>统计形式</b>'}),
                    radioGroup,
                    new Ext.Component({html:'<b>统计维度</b>'}),combobox

                ]
            }
        });
        var msgCompo = this.msgCompo =  new Ext.Component({
            style:'text-align:center;color:red;font-size:14px;',
            margin:'0 0 5 0'
        });
        var container = new Ext.container.Container({
            items:[chart,msgCompo,tools]
        });
        return container;
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(){
        this.callService({key:'queryChartData',params:this.params},function(data){
            var respData = data.queryChartData;
            if(respData.success==false){
                this.msgCompo.update("<li>"+respData.msg+"</li>");
            }else{
                this.msgCompo.update('');
            }
            var cfg = {
                title:'<b>科研项目数变化趋势</b>',
                type:this.currentType||'line',
                yAxis:'项目数',
                sfqx:false
            };
            for(var i=0;i<data.queryChartData.length;i++){
                var temp = data.queryChartData[i];
                temp.field = Number(temp.field);
            }
            var config = this.translateData(data.queryChartData,cfg);

            config.legend={
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0};
            this.chartCfg = config;
            this.chart.redraw(config,true);
        },this);
        if(this.tplGrid){
            this.tplGrid.load(this.params);
        }

    },
    initParams:function(){
        var nfdata = this.simpleDate.getValue();
        var navdata = this.navigation.getValue();
        this.params.from = nfdata.from;
        this.params.to = nfdata.to;
        this.params.zzjgId = navdata.nodes[0].id;
        this.params.tjzb = this.combobox.getValue();
    },
    createGrid:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
                this.tableData = respData.queryGridContent;
                this.gridFields =["XMMC","XMLY","SSDW","XMZT","LXRQ","JXRQ","XMLX","XMJJ","ID"];

                this.tplGrid = this.initXqGrid(this.tableData,this.gridFields,this.convertColumnConfig(),this.params);

                this.mainContainer.add({
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
        var textarrays = "项目名称,项目来源,所属单位,项目状态,立项时间,结项日期,项目类型,项目简介,ID".split(",");
        var widtharrays = [250,100,100,90,90,90,100,250,20];
        var hiddenarrays = [false,false,false,false,false,false,false,false,true];
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