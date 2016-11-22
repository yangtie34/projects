/**
 * 日期工具类
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午5:57
 *
 */
Ext.define('Exp.util.DateUtil',{
    singleton:true,
    /**
     * 获取日期字符串。今天、后天、昨天、几天前，几天后。
     * @param AddDayCount
     * @return {String}
     */
    getDateStr : function (dd,AddDayCount) {
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        return this.formatDate(dd);
    },
    /**
     * 获取本周起止时间
     * @return {String}
     */
    getWeekStartEnd : function(now){               //当前日期
        var nowDayOfWeek = now.getDay();         //今天本周的第几天
        var nowDay = now.getDate();              //当前日
        var nowMonth = now.getMonth();           //当前月
        var nowYear = now.getYear();             //当前年
        nowYear += (nowYear < 2000) ? 1900 : 0;  //
        var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
        var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
        return {
            from:this.formatDate(weekStartDate),
            to:this.formatDate(weekEndDate)
        };
    },
    /**
     * 获取上周起止时间
     * @return {String}
     */
    getPreWeekStartEnd : function(){
        var today = new Date();
        var bzstart = this.getWeekStartEnd(today).from;
        var szend = this.getDateStr(new Date(bzstart),-1);
        return this.getWeekStartEnd(new Date(szend));
    },
    /**
     * 格式化日期
     * @param dd
     * @return {String}
     */
    formatDate:function(dd){
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        if(m < 10){
            m = "0" + m;
        }
        if(d < 10){
            d = "0" + d;
        }
        return y+"-"+m+"-"+d;
    }
});Ext.define('Exp.util.NodeResolve',{
    singleton : true,
    /**
     * 提供一个遍历树的方法
     * @param {Object}root 根节点
     * @param {Function}process  处理函数，提供参数为Node
     * @returns {*}
     */
    traversalTree : function(root,process){
        var iterator = root.children,
            bakIterator = [],
            i = 0,
            len,
            item;
        process.call(this,root);
        while(iterator.length!=0){
            for(i=0, len = iterator.length;i<len;i++){
                item = iterator[i];
                process.call(this,item);
                bakIterator = bakIterator.concat(item.children);
            }
            iterator = bakIterator;
            bakIterator = [];
        }
        return root;
    }
});Ext.define('Exp.util.ResourceLoader',{
    singleton : true,
    /**
     * 加载文件
     * @param {String} path 文件路径
     * @param {Function} callback 回调函数
     * @param {Object} scope
     */
    loadFile : function(path,callback,scope){
        Ext.Ajax.request({
            url: path,
            success: function(response){
                var text = response.responseText;
                callback.call(scope||window,text);
            }
        });
    },
    /**
     * 加载CSS
     * @param {String} path css文件路径
     */
    loadCss : function(path){
        var head = document.getElementsByTagName("head")[0];
        var style = document.createElement("link");
        style.rel = "stylesheet";
        style.type = "text/css";
        style.href = path;
        head.appendChild(style);
    }
},function(loader){
    Exp.loadFile = Ext.Function.alias(loader,'loadFile');
    Exp.loadCss = Ext.Function.alias(loader,'loadCss');
});
/**
 * 导航组件。
 * User: zhangzg
 * Date: 14-7-11
 * Time: 下午3:39
 *
 */
Ext.define('Exp.component.Navigation',{
    extend:'Ext.Component',
    cls:'student-sta-titlediv',
    navgData:{},
    initComponent:function(){
        this.addEvents('click');
        this.tpl = new Ext.XTemplate('<div class="student-sta-titname"><tpl for="nodes">' +
            ' <a href="#" treeId="{id}">{text}</a> ' +
            '{[xindex === xcount ? "" : ">>"]} </tpl>' +
            ' <div style="display: inline; float: right; margin-right: 10px;"><a id="show_hide_top" href="#">隐藏</a></div>'+
            '</div>' +
            '<div class="student-sta-iconname" id="student_sta_child_div"><tpl for="children">{% if (xcount === 0) break; %} ' +
            '<a href="#" treeId="{id}">{text}</a></tpl> </div>');
        this.callParent();
        this.bindEvents();
    },

    /**
     * 绑定事件
     */
    bindEvents : function(){
        var me = this;
        this.on('afterrender',function(){
            if(!me.navgData){
                this.update({nodes:[],children:[]});
            }else{
                this.transData({id:0});
                this.update(this.currentData);
            }

        });
        this.on({
            click : {
                element : 'el',
                fn : function(event,el){
                    if(el.tagName=='A'){
                        if(el.id=='show_hide_top'){
                            me.isShowTop(el);
                        }else{
                            var data = {
                                id : event.getTarget().getAttribute('treeId'),
                                text : el.text
                            };
                            me.transData(data);
                            me.update(me.currentData);

                            me.fireEvent('click');

                        }
                    }
                }
            }
        });
    },
    /**
     * 刷新导航栏组件。
     * @param data
     */
    transData:function(data){
        var nodeId = data.id,
            nodes = [],
            flag = true,
            newNodes = [],
            obj = {};
        while(flag){

            for(var key in this.navgData){
                var item = this.navgData[key];
                if(item.id == nodeId){
                    nodes.push(item);
                    nodeId = item.pid;
                    break;
                }
            }

            // 找到根节点之后，跳出while循环
            if(nodes.length!=0 && (nodes[nodes.length-1].pid == -1 || nodes[nodes.length-1].pid==null)){
                flag = false;
            }
            // 如果hashmap中没有该节点，那么跳出循环。
            if(typeof this.navgData[nodeId] == 'undefined'){
                flag = false;
            }
        }

        for(var i = nodes.length-1;i>=0;i--){
            newNodes.push(nodes[i]);
            if(i==0){
                obj.children = nodes[i].children;
            }
        }
        this.currentData = Ext.apply({nodes:newNodes},obj);
    },
    /**
     * 获取组件的值
     */
    getValue:function(){
        return this.currentData||{};
    },
    /**
     * 刷新组件数据。
     * @param data
     */
    refreshTpl:function(data){
        this.navgData = data;
        this.transData({id:0});
        this.update(this.currentData);

    },
    /**
     * 设置组件的值。
     */
    setValue:function(nodeid){
        this.transData({id:nodeid});
        this.update(this.currentData);
    },
    isShowTop:function(el){
        var showDom=$(el);
        var $childDiv=showDom.parent().parent().next();
        if(showDom.html()=="隐藏"){
            $childDiv.hide(300);
            showDom.html("展开");
        }else{
            $childDiv.show(300);
            showDom.html("隐藏");
        }
    }
});/**
 * 二维图形组件，封装fusioncharts(在此框架下不可用)
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午2:40
 *
 */
Ext.define('Exp.chart.Chart',{
    extend : 'Ext.Component',
    chartData:{},
    chartType:'',
    chartTheme:'',
    margin:0,
    listeners:{
        resize:function(){
            var width = this.getWidth(),
                height = this.getHeight();
            var wh = this._countChartWH(width,height);
            this.chart.resizeTo(width,height);
        }
    },
    afterRender : function(){
        this.padding = 0;// 屏蔽padding属性。
        this.callParent(arguments);
        var domId = this.getEl().id;
        var chartCfg = this._getChartCfg();
        Ext.apply(chartCfg,this.chartData);
        this.chart = new FusionCharts({type: this.chartType,
            width : '100%',
            height : '100%',
            dataFormat: "json",
            dataSource: chartCfg
        });
        this.chart.render(domId);
    },
    /**
     * 根据组件类型获取图形组件配置数据。
     * @return {Object}
     * @private
     */
    _getChartCfg:function(){
        var chartType = this.chartType;
        switch(chartType){
            case 'column2d':
                return {
                    "chart": {
                        "caption": "",
                        "subCaption": "",
                        "xAxisName": "",
                        "yAxisName": "",
                        "theme": this.chartTheme||'zune'
                    },
                    data:{}
                };
            case 'pie2d':
                return {
                    "chart": {
                        "caption": "",
                        "subCaption": "",
                        "enableSmartLabels": "0",
                        "showPercentValues": "1",
                        "showTooltip": "0",
                        "decimals": "1",
                        "theme": this.chartTheme||'zune'
                    }
                };
            case 'doughnut2d':
                return {
                    "chart": {
                        "centerLabel": "$label: $value",
                        "theme": this.chartTheme||'zune'
                }};
            case 'mscolumn2d':
                return {
                    "chart": {
                        "subCaption": " ",
                        "palettecolors": "#0099ff,#cc6666,#009900",
                        "bgcolor": "FFFFFF",
                        "legendposition": "RIGHT",
                        "showBorder": "0"
                    },
                    "categories": [
                        {
                            "category": [
                                {
                                    "label": ""
                                }
                            ]
                        }
                    ],
                    "dataset": [
                        {
                            "seriesname": "",
                            "data": []
                        }
                    ]
                };
            case 'maps/china':
                return {
                    "map": {
                        "animation": "0",
                        "showbevel": "0",
                        "usehovercolor": "1",
                        "canvasbordercolor": "FFFFFF",
                        "bordercolor": "FFFFFF",
                        "showlegend": "1",
                        "showshadow": "0",
                        "legendposition": "RIGHT",
                        "legendborderalpha": "0",
                        "legendbordercolor": "ffffff",
                        "legendallowdrag": "1",
                        "legendshadow": "0",
                        "connectorcolor": "000000",
                        "fillalpha": "80",
                        "hovercolor": "CCCCCC"
                },
                    "colorrange": {
                        "color": [
                            {
                                "minvalue": "500",
                                "maxvalue": "600",
                                "displayvalue": "> 500",
                                "code": "892C03"
                            },
                            {
                                "minvalue": "200",
                                "maxvalue": "500",
                                "displayvalue": "200 - 500",
                                "code": "e44a00"
                            },
                            {
                                "minvalue": "100",
                                "maxvalue": "200",
                                "displayvalue": "100 - 200",
                                "code": "f8bd19"
                            },
                            {
                                "minvalue": "90",
                                "maxvalue": "100",
                                "displayvalue": "< 100",
                                "code": "6baa01"
                            }
                        ]
                    },
                    data:{}
                };
            default:
                return {}
        }
    },
    /**
     * 根据给定的长度计算图形组件的合理宽高比。
     * @param width
     * @private
     */
    _countChartWH:function(width,height){
        var chartType = this.chartType;
        switch(chartType){
            case 'column2d':
                return {width:width,height:width*1/4};
            case 'pie2d':
                return {width:width,height:width/2};
            default:
                return {width:width,height:height}
        }
    },
    /**
     * 更改图形组件的数据。
     */
    update:function(data){
        if(this.chart){
            this.chart.setJSONData(Ext.apply(this.chart.getJSONData(),data));
        }
    }
});/**
 * HighChart图形组件，封装highcharts,依赖jquery
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午2:40
 */
Ext.define('Exp.chart.HighChart',{
    extend : 'Ext.Component',
    margin:0,
    listeners:{
        resize:function(){
            var width = this.getWidth() - 10,
                height = this.getHeight() - 10;
            if(this.chart){
                this.chart.setSize(width,height);
            }

        }
    },
    afterRender : function(){
        this.padding = 0;// 屏蔽padding属性。
        this.callParent(arguments);
        this.domId =this.getEl().id;
        Ext.EventManager.onWindowResize(function(){
            this.fireEvent('resize');
        },this);
    },
    /**
     * 更改图形组件的数据。
     */
    redraw:function(config,redraw){
        if(redraw){
            $('#'+this.domId).highcharts(config);
            this.chart = $('#'+this.domId).highcharts();
            this.fireEvent('resize');
        }
    },
    /**
     * 添加chart组件。
     * @param chart
     */
    addChart:function(chart){
        this.chart = chart;
    },
	height : 400,
    /**
     * 移除chart组件。
     */
    removeChart:function(){
        $('#'+this.domId).removeChild();
    }
});/**
 * 日期区间组件
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午5:52
 *
 */
Ext.define('Exp.component.SimpleDate',{
    extend:'Ext.container.Container',
    defaultDate:'',
    width:500,
    initComponent:function(){
        /*增加事件*/
        this.addEvents(
            /**
             * @event 日期组件校验通过事件。
             *         即开始日期不大于结束日期，并且开始日期和结束日期都不为空时触发的事件。
             * @param {Ext.Component} this
             */
            'validatepass'
        );
        this.callParent(arguments);

        this.createComponent();
    },
    createComponent:function(){
        var tpl ,me = this;
        if(me.defaultDate==''){
            tpl = new Ext.XTemplate(this.getTplStr());
        }else{
            tpl = new Ext.XTemplate(this.getTplStr(me.defaultDate));
        }
        this.compo = new Ext.Component({
            style: {"float": 'left'},
            tpl:tpl,
            data:{}
        });
        this.compo.on({
            click : {
                element : 'el',
                fn : function(event,el){
                    if(el.tagName=='A'){
                        me.clickFn(el);
                    }
                }
            }
        });
        this.createDateSection();
        this.add([this.compo,this.fromDateField,this.toDateField]);
    },
    createDateSection : function(){
        var me = this;
        this.fromDateField = Ext.create('Ext.form.field.Date', {
            width:100,
            style: {"float": 'left'},
            name: 'from_date',
            margin:'3 0 0 0',
            format:'Y-m-d',
            value: new Date(),
            editable:false
        });

        this.toDateField  = Ext.create('Ext.form.field.Date', {
            width:100,
            style: {"float": 'left'},
            name: 'to_date',
            margin:'3 0 0 0',
            format:'Y-m-d',
            value: new Date(),
            editable:false
        });
        this.toDateField.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getRawValue(),
                fromDate = this.fromDateField.getRawValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.toDateField.focus();
                    this.toDateField.setValue(null);
                }
            }
        },this);
        this.fromDateField.addListener('change',function(comp,newValue,oldValue){
            this.toDateField.setValue(null);
            var fromDate = comp.getRawValue(),
                toDate = this.toDateField.getRawValue();
            this.toDateField.setMinValue(fromDate);
            if(toDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.fromDateField.setValue(null);
                    this.fromDateField.focus();
                }
            }
        },this);
        switch(me.defaultDate){
            case 'jr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),0);
                me.setValue({from : today,to :today});
                break;
            case 'zr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),-1);
                me.setValue({from : today,to :today});
                break;
            case 'bz' :
                me.setValue(Exp.util.DateUtil.getWeekStartEnd(new Date()));
                break;
            case 'sz' :
                me.setValue(Exp.util.DateUtil.getPreWeekStartEnd(new Date()));
                break;
            case 'zjygy' :
                var start = Exp.util.DateUtil.getDateStr(new Date(),-30);
                var end = Exp.util.DateUtil.getDateStr(new Date(),0);
                me.setValue({from : start,to :end});
                break;
            default :
                var today = Exp.util.DateUtil.getDateStr(new Date(),0);
                me.setValue({from : today,to :today});
                break;
        }
    },
    /**
     * 获取模板
     */
    getTplStr : function(){
        return '<div>'+
            '<a class="jiaoxuerizhi-alink jiaoxuerizhi-alink-visited" href="#" dType="jr">今日</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zr">昨日</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="bz">本周</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="sz">上周</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zjygy">最近一个月</a></div>';
    },
    getTplStr : function(def){
        var jrClass='',zrClass='',bzClass='',szClass='',zjClass='';
        switch(def){
            case 'jr' :
                jrClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'zr' :
                zrClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'bz' :
                bzClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'sz' :
                szClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'zjygy' :
                zjClass=' jiaoxuerizhi-alink-visited';
                break;
            default :
                jrClass=' jiaoxuerizhi-alink-visited';
                break;
        }

        return "<div>"+
            "<a class='jiaoxuerizhi-alink"+jrClass+"' href='#' dType='jr'>今日</a>"+
            "<a class='jiaoxuerizhi-alink"+zrClass+"' href='#' dType='zr'>昨日</a>"+
            "<a class='jiaoxuerizhi-alink"+bzClass+"' href='#' dType='bz'>本周</a>"+
            "<a class='jiaoxuerizhi-alink"+szClass+"' href='#' dType='sz'>上周</a>"+
            "<a class='jiaoxuerizhi-alink"+zjClass+"' href='#' dType='zjygy'>最近一个月</a></div>";
    },
    // 添加class
    addClass :function (ele,className){
        ele.className += " " + className; //以空格分开
    },
    // 移除class
    removeClass:function (ele,className){
        var tmpClassName = ele.className;
        ele.className = null;    //清除类名
        ele.className = tmpClassName.split(new RegExp(" " + className + "|" + className + " " + "|" + "^" + className + "$","ig")).join("");
    },
    clickFn:function(target){
        var dType = target.getAttribute('dType');
        var tagName = target.tagName;
        var childrens = target.parentNode.childNodes;
        for(var i=0;i<childrens.length;i++){
            this.removeClass(childrens[i],'jiaoxuerizhi-alink-visited')
        }
        if(tagName =='A'){
            this.addClass(target,'jiaoxuerizhi-alink-visited');
        }
        switch(dType){
            case 'jr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),0);
                this.setValue({from : today,to :today});
                break;
            case 'zr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),-1);
                this.setValue({from : today,to :today});
                break;
            case 'bz' :
                this.setValue(Exp.util.DateUtil.getWeekStartEnd(new Date()));
                break;
            case 'sz' :
                this.setValue(Exp.util.DateUtil.getPreWeekStartEnd(new Date()));
                break;
            case 'zjygy' :
                var start = Exp.util.DateUtil.getDateStr(new Date(),-30);
                var end = Exp.util.DateUtil.getDateStr(new Date(),0);
                this.setValue({from : start,to :end});
                break;
            default :
                break;
        }
    },

    /**
     * 获取组件的值
     * @return {
     *     from:'2014-06-16',
     *     to:'2014-06-16'
     * }
     */
    getValue : function(){
        return {
            from :this.fromDateField.getRawValue(),
            to : this.toDateField.getRawValue()
        }
    },
    /**
     * 设置组件的日期区间
     * @param config
     */
    setValue : function(config){
        this.fromDateField.setValue(new Date(config.from));
        this.toDateField.setValue(new Date(config.to));
    }
});/**
 * 区域图形组件。
 * User: zhangzg
 * Date: 14-7-24
 * Time: 下午3:03
 *
 */
Ext.define('Exp.component.ZonePart',{
    extend:'Ext.Component',
    style:{
        background:'#fff',
        padding:'10px'
    },
    width:'100%',
    listeners:{
        resize:function(){
            if(this._chartCompo){
                this._chartCompo.fireEvent('resize');
            }
        }
    },
    initComponent:function(){

        var tplStr = '<div class="student-sta-overFlow">' +
            '<div class="student-sta-floatLeft student-sta-bigfont">{title}</div>' +
            '<div class="student-sta-floatRight">{text} ' +
            '<span class="student-sta-weightbold student-sta-bigfont">{boldText}</span></div></div>' +
            '<div class="student-sta-clear"></div>' +
            '<div id="{0}_dsfChartRenderId" style="height: {chartHeight}px"></div>';

        this.tpl = Ext.String.format(tplStr,this.id);
        this.callParent(arguments);
    },
    /**
     * 添加图形组件。
     * @param Exp.chart.Chart
     */
    insertChart:function(compo){
        compo.render(this.id+"_dsfChartRenderId");
        this._chartCompo = compo;
    },
    /**
     * 获取图形组件。
     * @return {*}
     */
    getChart:function(){
        return this._chartCompo;
    }
});/**
 * 区域组件。
 * User: zhangzg
 * Date: 14-7-24
 * Time: 下午3:03
 *
 */
Ext.define('Exp.component.ZonePart2',{
    extend:'Ext.Component',
    style:{
        background:'#fff'
    },
    width:'100%',
    listeners:{
        resize:function(){
            if(this._chartCompo){
                this._chartCompo.fireEvent('resize');
            }
        }
    },
    initComponent:function(){
        var tplStr = '<div class="student-sta-zonepart"><table style="width:100%;height: 100%"><tr><td style="width:150px;">' +
            '<div class="student-sta-left" style="background:#e4e4e4;height:100%;width:100%;border-radius:5px 0px 0px 5px;position:relative;">'+
            '<tpl for=".">'+
                '<div class="student-sta-ti-title student-sta-white student-sta-bg-{theme}" style="margin:10px 10px;">{title}</div>' +
                '<div class="student-sta-renshu student-sta-{theme}">{count}</div>' +
                '<div class="student-sta-liang student-sta-white student-sta-bg-{theme}">{axisname}</div>' +
            '</tpl>'+
            '<div class="student-sta-sanjiao"></div>' +
            '</div></td><td><div id="{0}_dsfChartRenderId" class="student-sta-left" style="height:100%;width:100%;padding: 10px;">' +
            '</div></td></tr></table></div>';
        this.tpl = Ext.String.format(tplStr,this.id);
        this.callParent(arguments);
    },
    /**
     * 添加图形组件。
     * @param Exp.chart.Chart
     */
    insertChart:function(compo){
        compo.render(this.id+"_dsfChartRenderId");
        this._chartCompo = compo;
    },
    /**
     * 获取图形组件。
     * @return {*}
     */
    getChart:function(){
        return this._chartCompo;
    }
});/**
 * 页面头部统一组件。
 * User: zhangzg
 * Date: 14-7-23
 * Time: 上午9:40
 *
 */
Ext.define('Exp.component.PageTitle',{
    extend:'Ext.Component',
    tpl : '<div class="wbh-common-top">'+
        '<h1> <span class="wbh-common-model" style="background: none">{pageName}</span>' +
        '<div attr="help" class="another-add-help"></div> ' +
        '<span class="another-add-more">' +
        //'<a class="another-add-abg" href="#">下载报表</a>'+
        '</span></h1></div>'+
        '<div class="another-add-helpinfo">{pageHelpInfo}</div>',
    initComponent:function(){
        this.addEvents('click');
        this.callParent(arguments);
        this.bindEvents();
    },
    bindEvents:function(){
        var me = this;
        this.on({
            click : {
                element: 'el', //bind to the underlying el property on the panel
                fn: function(e){
                    //点击问号显示或者隐藏帮助信息
                    var tar = e.target;
                    if(tar.tagName == 'DIV' && (Ext.get(tar).getAttribute("attr")=="help")){
                        if($){
                            $(Ext.get(this.id).query(".another-add-helpinfo")).slideToggle();
                        }else{
                            var info = Ext.get(Ext.get(this.id).query(".another-add-helpinfo"));
                            info.setVisibilityMode(2);
                            info.toggle();
                        }
                    }
                    if(tar.tagName=='A'){
                        me.fireEvent('click');
                    }
                }
            }
        });
    }
});/**
 * 数字组件。
 * User: zhangzg
 * Date: 14-7-24
 * Time: 下午4:46
 *
 */
Ext.define('Exp.component.NumberWidget',{
    extend:'Ext.Component',
    tpl:'<div class="student-sta-tableft {[values.sfxz == true ? "student-sta-tabbg-grey" : ""]}">' +
        '<div class="student-sta-title student-sta-white student-sta-bg-{theme}" >{title}</div>' +
        '<div class="student-sta-renshu student-sta-{theme}" >{count}</div>' +
        '<div class="student-sta-liang student-sta-white student-sta-bg-{theme}">{axisname}</div>' +
        '<div class="student-sta-zhuanye">学历：{bz1}</div>' +
        '<div class="student-sta-zhuanye">学位：{bz2}</div></div>',
    initComponent:function(){
        /*增加事件*/
        this.addEvents('click');
        this.callParent(arguments);

        var me = this;
        this.on({
            click : {
                element : 'el',
                fn : function(event,el){

                    me.fireEvent('click');
                }
            }
        });
    },
    getData:function(){
        return this.data;
    }
});/**
 * 男女比例组件。
 * User: zhangzg
 * Date: 14-7-29
 * Time: 下午4:02
 *
 */
Ext.define('Exp.component.NnvRatio',{
    extend:'Ext.Component',
    /*nnvbl:{ns:{
     count:208,
     zb:'35.88'
     },nvs:{
     count:209,
     zb:'64.12'
     },bl:'1:1',
     text:'地空导弹'},*/
    listeners:{
        resize:function(){
            var data = this.translateData();
            this.update(data);
        }
    },
    initComponent:function(){
        this.tpl ='<tpl if="success == true"> <div style="position: relative;"> ' +
            '<div id="{0}_fdk" class="student-sta-zongjie" style="display:none;">{xb} {count}人 <br /> 占 {bl}%</div>' +
            '<div class="student-sta-marginauto" style="padding-top: 80px;" >' +
            '<tpl for="imgs">' +
            '<img src="app/pages/sc/base/component/images/{img}.png" xb="{img}" width="{width}" height="70" style="margin-right:{marginRight}px;margin-left: {marginLeft}px" />' +
            '</tpl></div>' +
            '<div class="student-sta-marginauto student-sta-overFlow">' +
            '<div class="student-sta-floatLeft student-sta-list-red" ms="true" style="width:{nvsZb}%"></div>' +
            '<div class="student-sta-floatLeft student-sta-list-blue" ms="true" style="width:{nsZb}%"></div></div>' +
            '<div class="student-sta-clear"></div>' +
            '<div class="student-sta-overFlow">' +
            '<div class="student-sta-floatRight student-sta-weightbold "> {text}</div>' +
            '</div></div><tpl else><div align="center" style="font-size: 10px; color:#e4e4;">No data to display</div></tpl>';

        this.tpl = Ext.String.format(this.tpl,this.id);

        this.callParent(arguments);
        var me = this;
        this.on({
            mouseover:{
                element : 'el',
                fn : function(event,el){
                    if(el.tagName=='IMG'||event.getTarget().getAttribute('ms')){
                        var x = event.getX()-this.getX();
                        var xb = event.getTarget().getAttribute('xb');
                        if(xb=='girl'){
                            Ext.get(me.id+"_fdk").setStyle({left:x +"px",color:'red',display:'block'});
                            document.getElementById(me.id+"_fdk").innerHTML="女 "+me.nnvbl.nvs.count+"人<br/> 占 "+me.nnvbl.nvs.zb+"%";
                        }else{
                            Ext.get(me.id+"_fdk").setStyle({left:x +"px",color:'blue',display:'block'});
                            document.getElementById(me.id+"_fdk").innerHTML="男 "+me.nnvbl.ns.count+"人<br/> 占 "+me.nnvbl.ns.zb+"%";
                        }

                    }
                }
            }
        });
    },
    afterRender:function(){
        var data = this.translateData();
        this.update(data);
    },
    /*转换数据*/
    translateData:function(){
        var num = 10,width = this.getWidth(),ns = 0,nvs = 0,vagWidth = (width-10)/num;
        if(typeof this.nnvbl =='undefined'){
            return {success:false};
        }
        ns = Math.round(this.nnvbl.ns.zb/10);
        nvs = Math.round(this.nnvbl.nvs.zb/10);
        var imgs = [];
        for(var i = 0;i<nvs;i++){
            imgs.push({img:'girl',width:30,marginRight:(vagWidth-30)/2,marginLeft:(vagWidth-30)/2});
        }
        for(var j = 0;j<10-nvs;j++){
            imgs.push({img:'boy',width:30,marginRight:(vagWidth-30)/2,marginLeft:(vagWidth-30)/2});
        }

        return {
            success:true,
            imgs:imgs,
            text:this.nnvbl.text||'',
            nvsZb:this.nnvbl.nvs.zb,
            nsZb:this.nnvbl.ns.zb
        };
    },
    /**
     * 刷新tpl组件数据。
     * @param data
     */
    refreshTpl:function(data){
        this.nnvbl = data;
        data = this.translateData();
//        this.update(data);
        this.tpl.overwrite(this.id,data);
    }
});/**
 *
 * User: zhangzg
 * Date: 14-8-1
 * Time: 下午2:12
 *
 */
Ext.define('Exp.component.TopBottom',{
    extend:'Ext.Component',

    listeners:{
        resize:function(){
            var data = this.translateData();
            this.update(data);
        }
    },
    initComponent:function(){
        this.tpl =
            '<div class="student-sta-marginauto" >' +
            '<div class="student-sta-leibietop-triangle"></div>' +
            '<div class="student-sta-leibietop">' +
            '<p class="student-sta-imporant" style="text-align: center;">{top.count}人</p><p style="text-align: right;">' +
                '{top.zb}%</p><p style="text-align: left;"> {top.lb}</p></div>' +
            '<div class="student-sta-leibiebottom">' +
            '<p style="text-align: left;">{bottom.lb}</p><p style="text-align: right;">{bottom.zb}%</p><p class="student-sta-imporant" style="text-align: center;">{bottom.count}人</p></div>' +
            '<div class="student-sta-leibiebottom-triangle"></div>' +
            '<div style="text-align:center;"><b>{xy}</b> ，人员学科组成多以 <b>{xk}</b> 学科人员组成。</div>' +
            '</div>';

        this.callParent(arguments);
    },
    afterRender:function(){
        var data = this.translateData();
        this.update(data);
    },
    /*转换数据*/
    translateData:function(){
        return {
            top:{
                count:0,
                zb:0.00,
                lb:'--'
            },
            bottom:{
                count:0,
                zb:0.00,
                lb:'--'
            },
            xy:'--',
            xk:'--',
            qxxk:'--'
        };
    }
});/**
 * 数字组件。
 * User: sunweiguang
 * Date: 14-8-6
 * Time: 下午5:46
 */
Ext.define('Exp.component.SimpleNumberWidget',{
    extend:'Ext.Component',
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = '<div style="border: 1px solid #AAA;width:150px;padding:10px 5px 10px 5px;text-align: center; font-weight:bold;border-radius: 3px;display: inline-block;">'+
    	'<div style="color: #FFF;font-size:15px; background-color: '+ this.color+';padding: 3px;border-radius: 3px;">{title}</div>'+
    	'<div style="font-size: 22px;height:55px;line-height:55px;padding:5px; color: '+ this.color+';">{value}</div>'+
    	'<div style="margin: 0 auto;line-height:25px; border-radius:25px;width: 25px;height: 25px;background-color: '+ this.color+';color:#FFF; ">{unit}</div>'+
    	'</div>';
    }
});/**
 * 数字组件。
 * User: sunweiguang
 * Date: 14-8-6
 * Time: 下午5:46
 */
Ext.define('Exp.component.SimpleNumberWidget2',{
    extend:'Ext.Component',
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = "{title}<br><br><b style='font-size:20px'>{value}&nbsp;</b>{unit}";
    },
	padding:"5px 20px",
	data : {},
	style : {
		"border-left" :"1px solid #b8bab9",
		"font-size" : "13px",
		"font-family" : "微软雅黑",
		
	}
});/**
 * 宿舍入住统计组件
 * User: zhangzg
 * Date: 14-8-13
 * Time: 下午2:14
 *
 */
Ext.define('Exp.component.SsRzCompo',{
    extend:'Ext.Component',
    tpl : '<div class="rztj-titname">'+
    '<div class="student-sta-ti-title ">{MC}</div>'+
    '<div style=" padding:0 10px; ">'+
    '<div  class="student-sta-left-width">'+
    '<div class="student-sta-ti" >'+
    '<fieldset>'+
    '<legend>容量</legend>'+
    '<p style="text-align: center;">层 <span class="student-sta-count-green">{lcs} </span>房间 ' +
    '<span  class="student-sta-count-green">{fjs} </span>床位 ' +
    '<span  class="student-sta-count-green">{cws} </span></p>'+
    '</fieldset>'+
    '</div>'+
    '<div class="student-sta-ti"  >'+
    '<fieldset>'+
    '<legend>入住率</legend>'+
    '<p style="text-align: center;"><span  class="student-sta-count-green">{rzl}%</span>，空床位<span  class="student-sta-count-green"> {kcw} </span></p>'+
    '</fieldset>'+
    '</div>'+
    '<div class="student-sta-ti" >'+
    '<fieldset>'+
    '<legend>住宿标准</legend>'+
    '<p style="text-align: center;"><span  class="student-sta-count-green"><tpl if="zsbz.length == 0">--元/年<tpl else>' +
        '<tpl for="zsbz">{MC}元/年{[xindex==xcount?"":","]}</tpl></tpl></p>'+
    '</fieldset>'+
    '</div>'+
    '</div>'+
    '<div class="student-sta-right-width">'+
    '<div class="student-sta-ti-border"  >'+
    '<h3>{xy}</h3>'+
    '<p><span>居多,占总人数的 {rszb}%</span></p>'+
    '</div>'+
    '<div class="student-sta-ti"  style="margin-left:15px;" >'+
    '<fieldset>'+
    '<legend>住宿性别</legend>'+
    '<div style="width:130px; margin:0 auto;text-align: center">'+
    '<tpl if="nan.count &gt; 1"> <div class="student-sta-count-blue"><img style="float:left" src="app/pages/sc/base/component/images/boy.png"/> <span style="float: right;">{nan.count} 人</span><br>'+
    '<span style="float: right;">{nan.zb}%</span> </div> </tpl>'+
    '<div style="clear:both;"></div>'+
    '<tpl if="nv.count &gt; 1"> <div class="student-sta-count-red"> <img  style="float:left" src="app/pages/sc/base/component/images/girl.png"/> <span style="float: right;">{nv.count} 人</span><br>'+
    '<span style="float: right;">{nv.zb}%</span> </div> </tpl>'+
    '</div>'+
    '</fieldset>'+
    '</div>'+
    '</div>'+
    '</div>'+
    '<div style="clear:both;"></div>'+
    '<div style="text-align:right;font-size:12px;"  class="student-sta-green"><b> {ssllx}<b> </div>'+
    '</div>'
});/**
 * 年份选择组件(学年)
 * User: Sunwg
 * Date: 14-8-15
 * Time: 下午5:00
 */
Ext.define('Exp.chart.YearPicker',{
	/**
     * @cfg {number} num 下拉框年份数量
     */
	
	/**
     * @cfg {number} value 默认值
     */
	
    extend : 'Ext.Component',
    margin:0,
    listeners:{
        change : function(){}
    },
    initComponent : function(){
        var today = new Date(),
            thisYear = parseInt(today.getFullYear()),
            month = parseInt(today.getMonth());
        thisYear = month>7?thisYear:(thisYear-1);
        var options = "";
        var curXn = thisYear + '-' + (thisYear + 1);
        this.value = curXn;
        for(var i = 0;i< this.num;i++){
        	var xn = (thisYear-i) + "-" +(thisYear+1-i);
        	options += "<option value= '"+ xn +"' " + ((this.value== xn ?  "select='true'" : ""))+ ">" + xn+ "</option>";
        }
        this.callParent(arguments);
        this.html = "<div style='padding:10px;border: 1px solid #aaa;'>" +
        		"选择统计学年 ： <select id = " + this.id + "_years>" + options +"</select>"+
        		"<input type='button' name='"+ this.id +"_btn' value='本学年' year='"+thisYear+ "-" + (thisYear + 1)+"' style=' width: 50px; height: 25px;margin: 0px 10px;'/> "+
        		"<input type='button' name='"+ this.id +"_btn' value='上学年' year='"+(thisYear - 1)+ "-" + thisYear+"' style='width: 50px; height: 25px;margin: 0px 10px;'/> " +
        		"&nbsp;&nbsp;&nbsp;&nbsp;☞选择统计学年更新页面内容。"+
        		" </div>";
    },
    afterRender : function(){
    	 this.bindEvent();
    },
    num : 5,
    getValue : function(){
    	return this.value;
    },
    value : null,
    bindEvent : function(){
    	var me = this;
    	var sId = me.id + "_years";
    	var combo = Ext.get(sId);
    	combo.on('change',function(){
    		me.value = combo.getValue();
    		me.fireEvent("change",combo.getValue());
    	});
    	var compo = Ext.get(me.id);
    	var btns = compo.query("input[name="+me.id +"_btn]");
    	Ext.get(btns).on('click',function(event,tar){
    		var year = Ext.get(tar).getAttribute('year');
    		me.value = year;
    		me.fireEvent("change",year);
    		if($){
    			$("#" + sId).val(year);
    		}
    	});
    }
});/**
 * 图片和内容组件
 * User: Sunwg
 * Date: 14-8-18
 * Time: 下午9:00
 */
Ext.define('Exp.chart.PicAndInfo',{
    extend : 'Ext.Component',
    margin:0,
    initComponent : function(){
        this.callParent(arguments);
        this.tpl = "<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>"+this.title+"</span>" +
		"<hr style='margin-top: 5px;' color='#5299eb'></div>" ;
        if(!this.onlyTitle){
        	this.tpl += "<div >	<table style='width: 100%;'><tr> <td width='130px'><img alt='"+ this.title +"' src='"+this.picurl+"' width='120px' height='100px'/></td>" +
        		"<td><div style='background-color: #e3f4fb;height: 80px;width: 100%;padding : 5px;'>"+ this.tplHtml +"</div></td></tr></table></div>";
        }
    },
    data : {
    	
    },
    onlyTitle : false
});/**
 * 宿舍入住统计组件
 * User: zhangzg
 * Date: 14-8-13
 * Time: 下午2:14
 *
 */
Ext.define('Exp.component.SsRzCompo',{
    extend:'Ext.Component',
    tpl : '<div class="rztj-titname">'+
    '<div class="student-sta-ti-title ">{MC}</div>'+
    '<div style=" padding:0 10px; ">'+
    '<div  class="student-sta-left-width">'+
    '<div class="student-sta-ti" >'+
    '<fieldset>'+
    '<legend>容量</legend>'+
    '<p style="text-align: center;">层 <span class="student-sta-count-green">{lcs} </span>房间 ' +
    '<span  class="student-sta-count-green">{fjs} </span>床位 ' +
    '<span  class="student-sta-count-green">{cws} </span></p>'+
    '</fieldset>'+
    '</div>'+
    '<div class="student-sta-ti"  >'+
    '<fieldset>'+
    '<legend>入住率</legend>'+
    '<p style="text-align: center;"><span  class="student-sta-count-green">{rzl}%</span>，空床位<span  class="student-sta-count-green"> {kcw} </span></p>'+
    '</fieldset>'+
    '</div>'+
    '<div class="student-sta-ti" >'+
    '<fieldset>'+
    '<legend>住宿标准</legend>'+
    '<p style="text-align: center;"><span  class="student-sta-count-green"><tpl if="zsbz.length == 0">--元/年<tpl else>' +
        '<tpl for="zsbz">{MC}元/年{[xindex==xcount?"":","]}</tpl></tpl></p>'+
    '</fieldset>'+
    '</div>'+
    '</div>'+
    '<div class="student-sta-right-width">'+
    '<div class="student-sta-ti-border"  >'+
    '<h3>{xy}</h3>'+
    '<p><span>居多,占总人数的 {rszb}%</span></p>'+
    '</div>'+
    '<div class="student-sta-ti"  style="margin-left:15px;" >'+
    '<fieldset>'+
    '<legend>住宿性别</legend>'+
    '<div style="width:130px; margin:0 auto;text-align: center">'+
    '<tpl if="nan.count &gt; 1"> <div class="student-sta-count-blue"><img style="float:left" src="app/pages/sc/base/component/images/boy.png"/> <span style="float: right;">{nan.count} 人</span><br>'+
    '<span style="float: right;">{nan.zb}%</span> </div> </tpl>'+
    '<div style="clear:both;"></div>'+
    '<tpl if="nv.count &gt; 1"> <div class="student-sta-count-red"> <img  style="float:left" src="app/pages/sc/base/component/images/girl.png"/> <span style="float: right;">{nv.count} 人</span><br>'+
    '<span style="float: right;">{nv.zb}%</span> </div> </tpl>'+
    '</div>'+
    '</fieldset>'+
    '</div>'+
    '</div>'+
    '</div>'+
    '<div style="clear:both;"></div>'+
    '<div style="text-align:right;font-size:12px;"  class="student-sta-green"><b> {ssllx}<b> </div>'+
    '</div>'
});/**
 * 年度区间组件
 * User: zhangzg
 * Date: 14-8-15
 * Time: 上午11:33
 *
 */
Ext.define('Exp.component.SimpleYear',{
    extend:'Exp.component.SimpleDate',
    width:500,
    defaultDate:'',
    start:1990,
    createDateSection : function(){
        var me = this;
        var store = new Ext.data.Store({
            fields: ['id', 'name'],
            data : this.getNfqj(this.start||1900)
        });
        this.fromDateField = Ext.create('Ext.form.ComboBox', {
            width:100,
            style: {"float": 'left'},
            name: 'from_date',
            margin:'3 0 0 0',
            valueField:'id',
            displayField: 'name',
            store:store,
            value: new Date().getFullYear(),
            editable:true
        });

        this.toDateField  = Ext.create('Ext.form.ComboBox', {
            width:100,
            style: {"float": 'left'},
            name: 'to_date',
            margin:'3 0 0 0',
            valueField:'id',
            displayField: 'name',
            store:Ext.clone(store),
            value: new Date().getFullYear(),
            editable:false
        });
        this.toDateField.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getRawValue(),
                fromDate = this.fromDateField.getRawValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.toDateField.focus();
                    this.toDateField.setValue(null);
                }
            }
        },this);
        this.fromDateField.addListener('change',function(comp,newValue,oldValue){
            this.toDateField.setValue(null);
            var fromDate = comp.getRawValue(),
                toDate = this.toDateField.getRawValue();

            if(toDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.fromDateField.setValue(null);
                    this.fromDateField.focus();
                }
            }
        },this);
        var jn = new Date().getFullYear();
        switch(me.defaultDate){
            case 'jr' :
                me.setValue({from : jn,to :jn});
                break;
            case 'zr' :
                me.setValue({from : jn-1,to :jn-1});
                break;
            case 'bz' :
                me.setValue({from : jn-4,to :jn});
                break;
            case 'sz' :
                me.setValue({from : jn-9,to :jn});
                break;
            case 'zjygy' :
                me.setValue({from : jn-19,to :jn});
                break;
            default :
                me.setValue({from : jn,to :jn});
                break;
        }

    },
    /**
     * 获取模板
     */
    getTplStr : function(){
        return '<div>'+
            '<a class="jiaoxuerizhi-alink jiaoxuerizhi-alink-visited" href="#" dType="jr">今年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zr">去年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="bz">近五年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="sz">近十年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zjygy">近二十年</a></div>';
    },
    getTplStr : function(def){
        var jrClass='',zrClass='',bzClass='',szClass='',zjClass='';
        var jn = new Date().getFullYear();
        switch(def){
            case 'jr' :
                jrClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'zr' :
                zrClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'bz' :
                bzClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'sz' :
                szClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'zjygy' :
                zjClass=' jiaoxuerizhi-alink-visited';
                break;
            default :
                jrClass=' jiaoxuerizhi-alink-visited';
                break;
        }

        return "<div>"+
            "<a class='jiaoxuerizhi-alink"+jrClass+"' href='#' dType='jr'>今年</a>"+
            "<a class='jiaoxuerizhi-alink"+zrClass+"' href='#' dType='zr'>去年</a>"+
            "<a class='jiaoxuerizhi-alink"+bzClass+"' href='#' dType='bz'>近五年</a>"+
            "<a class='jiaoxuerizhi-alink"+szClass+"' href='#' dType='sz'>近十年</a>"+
            "<a class='jiaoxuerizhi-alink"+zjClass+"' href='#' dType='zjygy'>近二十年</a></div>";
    },

    clickFn:function(target){
        var dType = target.getAttribute('dType');
        var tagName = target.tagName;
        var childrens = target.parentNode.childNodes;
        for(var i=0;i<childrens.length;i++){
            this.removeClass(childrens[i],'jiaoxuerizhi-alink-visited')
        }
        if(tagName =='A'){
            this.addClass(target,'jiaoxuerizhi-alink-visited');
        }
        var jn = new Date().getFullYear();
        switch(dType){
            case 'jr' :
                this.setValue({from : jn,to :jn});
                break;
            case 'zr' :
                this.setValue({from : jn-1,to :jn-1});
                break;
            case 'bz' :
                this.setValue({from:jn-4,to:jn});
                break;
            case 'sz' :
                this.setValue({from:jn-9,to:jn});
                break;
            case 'zjygy' :
                this.setValue({from:jn-19,to:jn});
                break;
            default :
                break;
        }
    },
    /**
     * 设置组件的日期区间
     * @param config
     */
    setValue : function(config){
        this.fromDateField.setValue(config.from);
        this.toDateField.setValue(config.to);
    },
    /**
     * 获取年份区间。
     */
    getNfqj:function(start){
        var arrs=[],to = new Date().getFullYear();
        for(;start<=to;start++){
            arrs.push({id:start,name:start+"年"});
        }
        return arrs;
    },
    /**
     * 获取组件的日期区间
     */
    getValue:function(){
        return {
            from:this.fromDateField.getValue(),
            to:this.toDateField.getValue()
        }
    }
});/**
 * 文本组件
 * User: sunweiguang
 * Date: 14-8-6
 * Time: 下午5:46
 * 
 * 	data : {
 * 		title : "",
 * 		texts : [{text : ""},{text : ""},{text : ""}]
 * }
 */
Ext.define('Exp.component.MetroTextWidget',{
    extend:'Ext.Component',
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = "<div style='height:240px;width:320px;font-family:微软雅黑; color:#fff;white-space:nowrap; background-color:"+ this.color +";padding:10px;'>" +
        		"<div style='font-size:26px;margin-bottom: 5px; '>{title}</div>	" +
        		"<tpl for='texts'><tpl if='xindex &lt;= 4'>" +
        		"  <div style='width:140px;height: 100px;font-size: 18px;float: left;'>{text}</div>	" +
        		"</tpl>	</tpl><div style='clear: both;'></div></div>";
    },
    color : "#6599ff",
    data : {}
});/**
 * 时间轴
 * User: Sunwg
 * Date: 14-8-15
 * Time: 下午5:00
 * 
 * 		var shaft = new Exp.chart.TimeShaft({
 * 			data : [{{date : '2012-12-12',html :"hellom,this is the test",picUrl:'app/pages/sc/base/component/images/tpic.png'}}]
 * 		});
 * 
 *		shaft.addItems([{{date : '2012-12-12',html :"hellom,this is the test",picUrl:'app/pages/sc/base/component/images/tpic.png'}}]);
 * 		
 * 		var number = shaft.total;
 */
Ext.define('Exp.chart.TimeShaft',{
    extend : 'Ext.Component',
    margin:0,
    listeners:{
    	readMore : function(){}
    },
    initComponent : function(){
    	this.callParent(arguments);
    	this.tpl = "<div id='"+this.id +"_content'></div><div id='"+this.id +"_more' style='border:1px solid #AAA;margin-top:30px;padding:10px;" +
    			"text-align:center;font-weight:bold;cursor: pointer;'> 点击加载更多...</div>";
    	this.on('afterrender',function(){
    		this.addItems(this.data);
    		this.bindEvent();
    	});
    },
    //data : [{date : '2012-12-12',html :"hellom,this is the test",picUrl:'app/pages/sc/base/component/images/tpic.png'}],
    data : [],
    modelHtml : "<div><table width='100%'><tr><td width='100px' style='vertical-align: top;padding-top: 40px;font-weight: bold;font-size:14px;'><div style='text-align:center;'>{0}</div></td>" +
    		"<td height='300px' width='80px'> <div style='width: 80px; height: 100%; margin-top: 20px; background: url(app/pages/sc/base/component/images/tsbz.png) repeat;'> " +
    		"<div style='height: 80px; width: 80px; padding: 20px; background: url(app/pages/sc/base/component/images/tsb.png) no-repeat;padding:20px;'> " +
    		"<div style='height:40px;width:40px;background-image: url({1});'></div>" +
    		"</div></div></td> " +
    		"<td height='300px'> <div style='position: relative; height: 100%;width: 100%; padding: 10px 0px 5px 15px;'>" +
    		" <div style='position: absolute; top: 30px; left: 1px; height: 15px; width: 18px; background: url(app/pages/sc/base/component/images/tsi.png) no-repeat;'></div>" +
    		" <div style='height: 100%; background-color: #afdcf8; border: 1px solid #000; border-radius: 10px;padding:10px;'>{2}" +
    		" </div></div></td></tr></table> </div>",
    addItems : function(data){
    	var me = this; 
    	me.total += data.length;
    	if(data.length == 0){
    		$("#"+me.id + "_more").hide();
    		return;
    	}else{
    		$("#"+me.id + "_more").show();
    	}
    	//遍历数据
    	for ( var i = 0; i < data.length; i++) {
			var item = data[i];
			var date =item.date || "",
				picUrl = item.picUrl || "app/pages/sc/base/component/images/tpic.png",
				html =item.html || "无内容";
			$("#" + me.id +"_content").append(Ext.String.format(me.modelHtml,date,picUrl,html));
		}
    },
    total : 0,
    bindEvent : function(){
    	var me = this;
    	$("#"+me.id + "_more").click(function(){
    		$("#" + me.id +"_more").html("<div class='loading-indicator'>正在加载....</div>");
    		me.fireEvent("readMore");
    		$("#" + me.id +"_more").html("点击加载更多..");
    	});
    },
    clear : function(){
    	this.total = 0;
    	$("#" + this.id +"_content").html("");
    }
});/**
 * 宿舍列表
 * User: sunwg
 * Date: 14-9-17
 * Time: 15:33
 *
 */
Ext.define('Exp.component.Sslist',{
	extend : 'Ext.Component',
    height : 700,
    initComponent:function(){
        this.callParent(arguments);
        this.tpl = '<div id="'+this.id +'_container" style="width: 210px;height: '+this.height+'px;border: 1px solid #AAA;border-radius:10px;overflow-y: auto;padding: 10px;">'+
        '<tpl for=".">' + 
        '<div style="width: 180px;height:100px;border:1px solid #AAA;border-radius:5px;margin-bottom: 10px;padding: 5px;line-height: 30px;background-color: #f5f5f5;">'+
        '<div style="font-weight: bold;">{name}</div><div>共{cs}层，房间数 {fjs} 间</div><div>床位数 {cws} 个&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" ssid="{ssid}" value="详情" '+
        ' style="width: 50px;height: 26px;border-radius :13px;"/></div></div></tpl></div>';
    },
    listeners:{
        select : function(){}
    },
    afterRender : function(){
    	this.bindEvent();
    },
    bindEvent : function(){
    	var me = this;
    	$("#" + me.id).click(function(event){
    		if(event.target.tagName == "INPUT"){
	    		var tar = $(event.target);
	    		tar.parent().parent().siblings().css({
	    			'background-color' : '#f5f5f5',
	    			'color' : '#000'
	    		});
	    		tar.parent().parent().css({
	    			'background-color' : '#009865',
	    			'color' : '#FFF'
	    		});
	    		me.value = tar.attr('ssid');
	    		me.fireEvent('select',tar.attr('ssid'),$(tar.parent().siblings()[0]).html());
    		}
    	});
    },
    data : [],
    getValue : function(){
    	return this.value;
    }
});