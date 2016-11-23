NS.define('Pages.sc.schoolbasicsituation.ChartBuilder',{
//    mixins : [
//        "Pages.sc.Scgj"		//输出工具类
//    ],
    constructor : function(config){
        this.addEvents('close','toleft','toright','relayout');
        var data = config.data,
            title = config.title,
            yAxis = config.yAxis,
            isCommon = config.isCommon,
            x = config.x,
            y = config.y,
            height = config.height || 350,
            width = config.width || 400,
            type = config.type;
        var window = this.window = this.getWindow(config);
        window.setWidth(width);
        window.showAt(x,y,true);

        if(type == "text"){
//            window.setHeight(400);
            Ext.get(this.id).setHTML(config.data);
            window.doLayout();
            return;
        }
        if(type != "pie"){
            window.setHeight(height);
            this.renderCommonChart(this.id,title,yAxis,data,type);
        }else if(type == 'pie'){
            window.setHeight(height);
            this.renderPieChart(this.id,title,yAxis,data,type);

        }
    },
    getWindow : function(config){
        this.id = NS.id();
        var me = this,
            basic = {
//            closable : false,
            bodyCls : '',
//            baseCls : '',
            bodyStyle : {
                backgroundColor:'white'
            },
                    draggable : false,
            collapsible : true,
            plain : false,
            resizable : false,
            autoScroll : true,
            title : config.title,
            shadow : false,
            frame : false,
            frameHeader : false,
            layout : 'fit',
            tools:[{
                type:'left',
                name : 'toleft',
                tooltipType : 'title',
                tooltip: '移到左边',
                handler: function(event, toolEl, panel){
                    me.fireEvent('toleft',me,window);
                }
            },
                {
                    type:'right',
                    name : 'toright',
                    tooltipType : 'title',
                    tooltip: '移到右边',
                    handler: function(event, toolEl, panel){
                        me.fireEvent('toright',me,window)
                    }
                },{
                    type:'refresh',
                    name : 'rightleftto',
                    tooltipType : 'title',
                    tooltip: '左右互换',
                    handler: function(event, toolEl, panel){
                        me.fireEvent('rightleftto',me,window)
                    }
                }],
            html : '<div style="background-color: white;" width="100%"  height="100%" id="'+this.id+'"></div>'
        };
        NS.apply(basic,config);
        delete basic.tpl;

        var window  = new NS.window.Window(basic);

        window.on('destroy',function(){
            this.fireEvent('close');
        },this);
        window.on('hide',function(){
            $('#' + this.id).hide();
        },this);
        window.component.on('show',function(){
            $('#' + this.id).show();
        },this);
        window.component.on('collapse',function(){
            this.fireEvent('relayout');
        },this);
        window.component.on('expand',function(){
            this.fireEvent('relayout');
        },this);
        return window;
    },
    getWindow : function(config){
        var pme = this;
        this.id = NS.id();
        this.headerId = NS.id();
        var me = this,
            html = config.tpl.tpl.html,
            fs = NS.String.format(html,this.headerId,config.title,this.id),
            basic = {
                bodyCls : '',
                baseCls : '',
                header : false,
                frame : false,
                bodyStyle : {
                    backgroundColor:'white'
                },
                shadow : false,
                resizable : false,
                frameHeader : false,
                preventHeader : true,
                layout : 'fit',
                html : fs,
                /**
                 * @private
                 * Override Component.initDraggable.
                 * Window uses the header element as the delegate.
                 */
                initDraggable: function() {
                    var me = this,
                        ddConfig;
                        ddConfig = Ext.applyIf({
                            el: me.el,
                            delegate: '#' + pme.headerId,
                            onStart: function(e) {
                                var me = this,
                                    comp = me.comp;
                                this.startPosition = comp.el.getXY();
                                if (comp.ghost && !comp.liveDrag) {
                                    me.proxy = comp.ghost();
                                    me.dragTarget = Ext.get(pme.headerId);
                                }
                                if (me.constrain || me.constrainDelegate) {
                                    me.constrainTo = me.calculateConstrainRegion();
                                }
                            },
                            onDrag: function(e) {
                                var me = this,
                                    comp = (me.proxy && !me.comp.liveDrag) ? me.proxy : me.comp,
                                    offset = me.getOffset(me.constrain || me.constrainDelegate ? 'dragTarget' : null);
                                comp.setPagePosition(me.startPosition[0] + offset[0], me.startPosition[1] + offset[1]);
                            },
                            onEnd: function(e) {
                                if (this.proxy && !this.comp.liveDrag) {
                                    this.comp.unghost();
                                }
                            }
                        }, me.draggable);

//                        if (me.constrain || me.constrainHeader) {
                            ddConfig.constrain = me.constrain;
                            ddConfig.constrainDelegate = me.constrainHeader;
                            ddConfig.constrainTo = me.constrainTo || me.container;
//                        }

                        /**
                         * @property {Ext.util.ComponentDragger} dd
                         * If this Window is configured {@link #cfg-draggable}, this property will contain an instance of
                         * {@link Ext.util.ComponentDragger} (A subclass of {@link Ext.dd.DragTracker DragTracker}) which handles dragging
                         * the Window's DOM Element, and constraining according to the {@link #constrain} and {@link #constrainHeader} .
                         *
                         * This has implementations of `onBeforeStart`, `onDrag` and `onEnd` which perform the dragging action. If
                         * extra logic is needed at these points, use {@link Ext.Function#createInterceptor createInterceptor} or
                         * {@link Ext.Function#createSequence createSequence} to augment the existing implementations.
                         */
                        me.dd = new Ext.util.ComponentDragger(this, ddConfig);
                        me.relayEvents(me.dd, ['dragstart', 'drag', 'dragend']);
                    }
            };
        var window  = new Ext.window.Window(basic);
        window.on({
            'click' : {
                element : 'el',
                    fn : function(e,element){
                    if(element.nodeName == "A"){
                        var name = Ext.fly(element).getAttribute('name');
                        switch(name){
                            case "cols":pme.fireEvent('relayout',pme,window);me.collapse = !me.collapse;if(me.collapse)window.collapse();else window.expand();return;
                            case "left":pme.fireEvent('toleft',pme,window);return;
                            case "right":pme.fireEvent('toright',pme,window);return;
                            case "leftright":pme.fireEvent('rightleftto',pme,window);return;
                            case "close":window.close();pme.fireEvent('close',pme,window);return;
                        }
                    }
                }
            }
        })
        return window;
    },
    /**
     * @param divId 目标divId
     * @param title chart标题
     * @param yAxis Y轴单位
     * @param data 数据
     * @param type 图表类型(column,line,area,spline,areaspline)
     *
     *      模板 data = [{name : '男' ,field : '一月',value : 200 },{name : '男' ,field : '二月',value : 200},
     * 				    {name : '女' ,field : '一月',value : 2020 },{name : '女' ,field : '二月',value : 2020}]
     */
    renderCommonChart : function(divId,title,yAxis,data,type){
        var isName ={} , isField = {};
        var fields = [],series = [];
        for(var i in data){
            var tar = data[i];
            if(!isName[tar.name]){
                series.push({name : tar.name,data : []});
                isName[tar.name] = true;
            }
            if(!isField[tar.field]){
                fields.push(tar.field);
                isField[tar.field] = true;
            }
        }
        fields.sort(function(a,b){return a>b?1:-1});
        var ser,fie,dat;
        for ( var j in series) {
            ser = series[j];
            for ( var k = 0; k < fields.length; k++) {
                fie = fields[k];
                for(var m = 0; m < data.length; m++){
                    dat = data[m];
                    if(dat.name == ser.name && dat.field == fie){
                        ser.data.push(parseFloat(dat.value));
                    }
                }
                if (ser.data.length < k) {
                    ser.data.push(0);
                }
            }
        }

        type = type || 'column';
        var config = {
            title: {
                text: title,
                x: -20 //center
            },
            chart: {
                height: 260,
                width : 400,
                type: type
            },
            xAxis: {
                categories: fields
            },
            yAxis: {
                title: {
                    text: yAxis
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            legend: {
                borderWidth: 1,
                borderRadius : 3
            },
            tooltip: {
                useHTML : true
            },
            credits : {// 不显示highchart标记
                enabled : false
            },
            series:series
        };
        $('#' + divId).highcharts(config);
    },
    /**
     * @param divId 目标div
     * @param title 标题
     * @param yAxis 数据含义
     * @param data 数据
     * @param showLable 是否显示lable提示
     *
     * 		data = [{name: '实践课', y: 62.5, num :200 },{name: '理论课', y: 37.5, num :120 }]
     */
    renderPieChart : function(divId,title,yAxis,data,showLable){
        showLable = (showLable == null) ? true : showLable;

        var translate = function(data){
            var array = [];
            for(var i=0;i<data.length;i++){
                array.push({name : data[i].name,num : data[i].value,y : data[i].value});
            }
            return array;
        }

        $('#' +divId).highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                height : 300
            },
            title: {
                text: title
            },
            tooltip: {
                pointFormat: '<span style="color: blue;">{point.y}</span>{series.name}:<b><span style="color: darkgreen;">{point.percentage:.1f}%</span></b>'
            },
            credits : {// 不显示highchart标记
                enabled : false
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: showLable
                    }
                }
            },
            series: [{
                type: 'pie',
                name: yAxis,
                data: translate(data)
            }]
        });
    }
});