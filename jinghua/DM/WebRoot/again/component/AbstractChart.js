/**
 * @class NS.Chart
 * @extend NS.SimpleComponent
 *  该类不能直接被示例化并使用,如果需要创建Chart类，请参照其子类
 *
 *      var chart = new NS.chart.Column({
                 chartType : 'Column2D',//图表的类型
                 chartConfig : {//针对报表的基本配置
                     "caption":"图表标题",
                     "subcaption":"图表副标题",
                     "xaxisname":"X轴名称",
                     "yaxisname":"Y轴名称",
                     "numberprefix":"$"//y轴单位
                 },
                 renderTo : '123',
                 data ://多图表的数据
                 {
                     labels : ['一月','二月'],
                     dataSet : [
                     {
                     name : '2006',
                     data : [13,14]
                     },{
                     name : '2007',
                     data : [15,17]
                     }
                     ]
                 },

                 data : [//单图表的数据格式
                     {label : '一月',value : 12},
                     {label : '二月',value : 13},
                     {label : '三月',value : 12},
                     {label : '四月',value : 13},
                     {label : '五月',value : 12},
                     {label : '六月',value : 13}
                 ]
 *      });
 */
NS.define('NS.AbstractChart',{
    extend : 'NS.Component',

    id_Sign : '_chart',

    /**
     * @cfg {String} chartType 图表类型
     */
    chartType : '',
    /**
     * swf 文件的路径
     * @private
     */
    baseSwfUrl : 'FusionCharts/charts/',
    chartConfig : {
        "caption":"图表标题",
        "subcaption":"图表副标题",
        "xaxisname":"X轴名称",
        "yaxisname":"Y轴名称",
        "numberprefix":"Y轴值单位"
    },
    /**
     * 构造组件
     * @private
     *
     */
    initComponent : function(){
        var width = this.width,
            height = this.height,
            chartType = this.support(this.chartType),//判断是否支持该组件类型
            swfUrl = this.baseSwfUrl,
            data = this.translate(this.data);//将数据做下转换

        if(!width)width = '100%';
        if(!height)height = '100%';

        this.chartId = this.id+"_fusionchart";
//        this.chart = new FusionCharts({
//            swfUrl: swfUrl+chartType+".swf",
//            id : this.chartId,//id
//            width: width,
//            height: height,
//            debugMode : false
//        });
        this.chart = new FusionCharts(swfUrl+chartType+".swf",this.chartId,width,height,'0','1');
        this.chart.setJSONData(data);
        //配置数据项
        this.chart.configure({
            ParsingDataText: '数据读取中,请稍候...',
            PBarLoadingText: '数据加载中,请稍候...',
            RenderingChartText: '数据正在渲染,请稍候...',
            LoadDataErrorText: '数据加载错误!',
            ChartNoDataText: "无数据可供显示!"
            // add more...-->key:value
        });

        this.callParent(arguments);
    },
    /**
     * @private
     */
    afterRender : function(){
        this.chart.render(this.component.id);
    },
    /**
     * 根据传入的数据，重绘chart图表
     */
    redraw : function(data){
        this.chart.setJSONData(this.translate(data));
    },
    /**
     * 数据转置为Single Series Chart JSON（单图表图系列）
     * 数据格式必须以固定的格式，如下
     *
     *      var chartData = [
     *         {mc : '一月',value : '12'},
     *         {mc : '二月',value : '12'},
     *         {mc : '三月',value : '12'},
     *         {mc : '四月',value : '12'},
     *         {mc : '五月',value : '12'}
     *      ];
     *
     * @private
     * @param {Object} data 需要转置的数据
     */
    translate : function(data){
        var chartData = this.getChartData(data),
            chartParams = this.getChartParams(),
            styles = this.getChartStyles();
        var ret = {};
        NS.apply(ret,chartParams,chartData);
        NS.apply(ret,styles);
        return ret;
    },
    /**
     * 获取Chart的数据
     *  简单格式数据为
     *
          var simpleData = [
                {
                    label : '一月',
                    value : 12
                },
                 {
                     label : '二月',
                     value : 14
                 }
          ];
     *
     *    var complexData = {
     *       labels : ['一月','二月'],
     *       dataSet : [
     *           {
     *              name : '2006',
     *              data : [13,14]
     *           },
     *           {
     *              name : '2007',
     *              data : [13,14]
     *           }
     *       ]
     *    };
     *
     * @private
     * @param {Array} data
     */
    getChartData : function(data){
        var chartType = this.chartType,
            i = 0,
            j = 0,
            item,
            len,
            clen,
            list = [],
            category = [],
            dataset = [],
            categories = [],
            child,
            fields = data.fields,//域属性

            ret = {};
        if(this.isSingleSeriesChart(chartType)){
            ret.data = data;
        }else if(this.isMultiSeriesChart(chartType)){
            for(len = data.labels.length;i<len;i++){
                category.push({label : data.labels[i]});
            }
            for(i = 0;i<data.dataSet.length;i++){
                item = data.dataSet[i];
                child = {};
                child.seriesname = item.name;
                child.data = [];
                for(j = 0,clen = item.data.length;j<clen;j++){
                    child.data.push({value : item.data[j]});
                }
                dataset.push(child);
            }

            categories.push({category : category});
            ret.dataset = dataset;
            ret.categories = categories;

        }
        return ret;
    },
    /**
     * 获取chart的样式
     * @private
     */
    getChartStyles : NS.emptyFn,
    /**
     * 获取单图表系列转换数据
     * @private
     * @param {Array}data
     * @return {Array}
     */
    getSscChartData : function(data){
        var i = 0,len = data.length,item,list = [];
        for(;i<len;i++){
            item = data[i];
            list.push({label : item.mc,value : item.value});
        }
        return list;
    },
    /**
     * 获取图表的参数
     * @private
     * @return {Object}
     */
    getChartParams : function(){
        return {chart : this.getBasicParams()};
    },
    /**
     * @private
     * @return {Object}
     */
    getBasicParams : function(){
        var basic =  {
            // yaxisname : yaxisname || "",
            // xaxisname : xaxisname || "",
            baseFontSize : '12',// 基本字体
            // outCnvBaseFontSize : '12',//画布之外字体
            baseFontColor : '6B6B6B',// 基本字体颜色
            showvalues : '0',// 是否显示值 1 是 0 否
            formatNumberScale : '0',// 是否格式化数字 1 是 会添加k m等英文单位
            showlabels : '1',// 是否显示标签
            legendBgColor : 'FFFFFF',// legend的背景色
            showlegend : '1',// 是否显示legend
            legendposition : 'right',// legend显示位置为右侧(四周均可)
            legendShadow : '1',// legend 的阴影显示
            legendBorderColor : 'FFFFFF',
            legendScrollBgColor : 'DEECFD',
            legendScrollBarColor : 'BAD1EF',
            legendScrollBtnColor : 'BAD1EF',
            // legendborderalpha : "0",// legend的边框宽度
            bgcolor : 'FFFFFF,FFFFFF',// 图形背景色
            showborder : '0',// 图形边框是否显示
            manageLabelOverflow : '1',// 字溢出时会自动隐藏
            useEllipsesWhenOverflow : '1',// 当溢出时,当Label溢出时候使用...将鼠标移至label处会显示
            // showAboutMenuItem : 0,// 是否显示关于fusioncharts
            aboutMenuItemLabel : '河南省精华科技有限公司',// 覆盖目录label
            aboutMenuItemLink : 'n-http://www.gilight.cn',
            zeroPlaneAlpha : '10',// 0线的像素
            use3DLighting : 1,// 是否使用3d光效果
            // defaultAnimation : 1,// 第一次是关闭动态展现
            // 添加下载
            // exportEnabled : 1,
            // exportShowMenuItem : 1,
            //exportFormats : 'PNG=导出png格式图片|JPG=导出jpg格式图片|PDF=导出pdf文件',
            // exportAction : 'download',
            // showExportDialog : 1,
            // exportAtClient : 0,
            // exportTargetWindow : '_slef',
            animation : '1'// 动态展现
            },
            config = this.chartConfig;
        NS.apply(basic,config);
        return basic;
    },
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : '',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : '',
    /**
     * 是否单图系列
     * @param {String} chartType
     */
    isSingleSeriesChart : function(chartType){
        return this.SingleSeriesChartSupport.indexOf(chartType)!=-1;
    },
    /**
     * 是否复合图形系列
     * @param {String} chartType
     */
    isMultiSeriesChart : function(chartType){
        return this.MultiSeriesChartSupport.indexOf(chartType)!=-1;
    },
    /**
     * 合并参数
     * @private
     */
    mergeParams : function(params1,params2){
        NS.apply(params1,params2);
        return params1;
    },
    /**
     * 判断是否支持该图表类型
     *
     * @param  {String} type 图形组件类型
     */
    support: function (type) {
        // 维护的图形组件类型列表
        var chartMap = {
            //1、饼形
            Pie2D: "Pie2D",
            Pie3D: "Pie3D",
            //2、线形
            Line: "Line",
            MSLine: "MSLine",
            ScrollLine2D: "ScrollLine2D",
            //3、环形
            Doughnut2D: "Doughnut2D",
            Doughnut3D: "Doughnut3D",
            //4、条形
            Bar2D: "Bar2D",
            MSBar2D: "MSBar2D",
            MSBar3D: "MSBar3D",
            StackedBar2D: "StackedBar2D",
            StackedBar3D: "StackedBar3D",
            //5、柱形
            Column2D: "Column2D",
            Column3D: "Column3D",
            MSColumn2D: "MSColumn2D",
            MSColumn3D: "MSColumn3D",
            ScrollColumn2D: "ScrollColumn2D",// 滚动条的柱形图
            StackedColumn2D: "StackedColumn2D",
            StackedColumn3D: "StackedColumn3D",
            ScrollStackedColumn2D: "ScrollStackedColumn2D",
            //6、区域形
            Area2D: "Area2D",
            ScrollArea2D: "ScrollArea2D",
            StackedArea2D: "StackedArea2D",
            MSArea: "MSArea"
            // 陆续待加...24
        };
        var chartType = chartMap[type] || "MSColumn2D";// 如果不支持该类型，则将其置为MSColumn2D
        return chartType;
    }
});