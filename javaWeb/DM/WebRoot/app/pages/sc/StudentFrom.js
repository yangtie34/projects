/**
 * 学生生源地统计
 * User: Administrator
 * Date: 14-5-23
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
NS.define('Pages.sc.StudentFrom',{
    extend:'Template.Page',
    mkm:'PagesjwcxtjStudentFrom',
    entityName:'VStudentFrom',
    oEntityName:'VStudentFromByxx',
    tabIndex:{
        templates:[{
            templateId:'lyd',
            templateTitle:'地区',
            grid:""
        },{
            templateId:'byxx',
            templateTitle:'毕业学校',
            grid:""
        }]
    },
    modelConfig: {
        serviceConfig: {
            'queryGridContent': {
                service:"studentFromService?queryGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            'queryGridContent1': {
                service:"studentFromService?queryByxxGridContent",
                params:{
                    limit:10,
                    start:0
                }
            },
            'CurrentXnxq':'CurrentXnxq',
            'queryHeaderData': 'baseService?queryForAddByEntityName',
            'getZbCounts':'studentFromService?getCountZbs',
            'getCountByZbId':'studentFromService?getCountByZbId',
            'queryJxzzjgTree': 'scService?getJxzzjgTree'// 获取导航栏数据，教学组织结构
        }
    },
    tplRequires : [
        {fieldname : 'tpl',path : 'app/pages/sc/template/StudentFrom.html'},
        {fieldname : 'tableTpl',path : 'app/pages/sc/template/StudentFrom_table.html'}
    ],
    cssRequires : [
        'app/pages/sc/template/css/op.css',
        'app/pages/sc/template/css/another-add.css',
        'app/pages/sc/base/map/jqvmap.css'
    ],
    pageParams:{},
    respParams:{},
    init:function(){
        var bmmc = MainPage.getBmxx().mc;
        this.BmMc = bmmc == '' ? '暂无部门信息' : bmmc;
        this.UserName = MainPage.getUserName();
        this.callService([/*'CurrentXnxq',*/'getZbCounts'],function(respData){
//            this.xnxq = respData.CurrentXnxq;
            this.zbCounts = respData.getZbCounts;
            this.initTpl(this.zbCounts);
        },this);

    },
    refreshComps:function(){
        this.callService([{key:'getZbCounts',params:this.respParams},
            {key:'getCountByZbId',params:this.respParams}],function(resp){
            // 刷新上部组件
            this.titleTplCompo.refreshTplData({topData:{
                zbCounts:resp.getZbCounts.list
            }});
            // 刷新地图组件
            this.refreshMap(resp.getCountByZbId);
            // 刷新grid组件
            this.tplGrid.load(this.respParams);
            if(typeof this.tplGrid1 !='undefined'){
                this.tplGrid1.load(this.respParams);
            }

            // 刷新饼状图
            this.createPie(resp.getCountByZbId);


        },this);
    },
    initTpl:function(zbCounts){
        var me = this;
        this.initPage(zbCounts.list);
        this.initTitleTpl(zbCounts.list);
        this.createTabIndex();
        this.createCombobox();
        this.createDateSection();
        this.dateSection.setValue({from:zbCounts.from,to:zbCounts.to});
        this.timeParams = {from:zbCounts.from,to:zbCounts.to};
        this.dateSection.addListener('validatepass',function(){
            this.timeParams =this.dateSection.getRawValue();
            var zbId = this.zbCombobox.getValue();
            this.respParams = {
                zbId : zbId
            };
            NS.apply(this.respParams,this.timeParams);
            NS.apply(this.respParams,this.pageParams);

            this.refreshComps();
        },this);

        var navigation = this.navigation = new Exp.component.Navigation();
        navigation.render(this.mkm+"_navigation");

        this.callService('queryJxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryJxzzjgTree);
            var i = 0;
            for(var key in data.queryJxzzjgTree){
                if(i==0){
                    var nodeId = data.queryJxzzjgTree[key].id;
                    this.navigation.setValue(nodeId);
                    this.pageParams.zzjgId = nodeId;
                }
                i++;
            }

            this.navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.pageParams.zzjgId = data.nodes[len-1].id;
                me.pageParams.zzjgmc = data.nodes[len-1].mc;
                NS.apply(me.respParams,me.timeParams);
                NS.apply(me.respParams,me.pageParams);
                me.refreshComps();
            });

            this.createMap();
            this.reqestGridDate();
        },this);
    },
    /**
     * 初始化gird组件。
     */
    reqestGridDate:function(){
        var params = {start:0,limit:10};
        NS.apply(params,this.pageParams);
        NS.apply(params,this.timeParams);
        this.callService([{key:'queryGridContent',params:params},
            {key:'queryHeaderData',params:{entityName:this.entityName}}],
            function(respData){
                this.tranData = NS.E2S(respData.queryHeaderData);
                this.tableData = respData.queryGridContent;
                this.tplGrid = this.initGrid(this.tranData,this.tableData,'queryGridContent',{});
                var gridContainer = this.gridContainer = new NS.container.Container({
                    layout:'fit'
                });
                gridContainer.add(this.tplGrid);
                gridContainer.render(this.mkm+'_table');
                this.tabIndex.templates[0].grid = this.tplGrid;
//                this.tplGrid.render(this.mkm+'_table');
            });
    },
    /**
     * 初始化gird组件。
     */
    reqestGridDate1:function(){
        var params = {start:0,limit:10};
        NS.apply(params,this.pageParams);
        NS.apply(params,this.timeParams);
        this.callService([{key:'queryGridContent1',params:params},
            {key:'queryHeaderData',params:{entityName:this.oEntityName}}],
            function(respData){
                var tranData = NS.E2S(respData.queryHeaderData);
                var tableData = respData.queryGridContent1;
                this.tplGrid1 = this.initGrid(tranData,tableData,'queryGridContent1',{});
                this.tabIndex.templates[1].grid = this.tplGrid1;
                this.gridContainer.add(this.tplGrid1);
            });
    },
    /**
     * 初始化grid！
     * @param tranData 通过NS.E2S方法转换的供grid使用的数据。
     * @param contentData 表格数据。
     * @param serviceKey 请求modelConfig.serviceConfig的key值。
     * @param columnCfg 自定义的grid配置项。
     */
    initGrid:function(tranData,contentData,serviceKey,columnCfg){
        var me = this;
        this.basicCfg = this.gridBasiConfig= {
            columnData: tranData,
            modelConfig: {
                data : contentData
            },
            serviceKey:serviceKey,
            proxy:this.model,
            border:false,
            checked:false,
            autoScroll: true,
            multiSelect: false,
            lineNumber: true,
            pageSize:10
        };
        NS.applyIf(columnCfg,this.basicCfg);
        return new NS.grid.SimpleGrid(columnCfg);
    },
    /**
     * 初始化页面。
     */
    initPage:function(zbCounts){
        var me = this;
        this.homePage = new NS.Component({
            layout:'fit',
            border:false,
            autoScroll:true,
            tpl : this.tpl,
            data : {
                topData:{
                    UserName:this.UserName,
                    Bmmc:this.BmMc,
                    xn:"",
                    xq:"",
                    pageCN:this.mkm,
                    pageTitle:this.pageTitle,
                    sm:'点击“教学单位”的名字，将按照 学校 >> 系部 >> 专业 的顺序一级一级下钻。当前默认结果集为本学年本学期下的统计结果。',
                    zbCounts:zbCounts
                }
            }

        });
        this.homePage.on('click',function(event){
            if ($(event.getTarget()).attr("attr") == 'help') {
                $("#"+me.mkm+"_helpinfo").slideToggle();
            }
        });
        this.setPageComponent(this.homePage);
    },
    /**
     * 初始化上部表格统计数据。
     * @param zbCounts
     */
    initTitleTpl : function(zbCounts){
        this.titleTplCompo = new NS.Component({
            tpl : this.tableTpl,
            data : {
                topData:{
                   zbCounts:zbCounts
                }
            }
        });
        this.titleTplCompo.render(this.mkm+"_titletable");
    },
    /**
     * 创建指标下拉框组件。
     */
    createCombobox:function(){
        this.zbCombobox = new NS.form.field.ComboBox({
            fields:['id','name'],
            data:[{id:'rxzrs',name:'来校人数'},
                {id:'nchkrs',name:'农村户口人数'},
                {id:'xzhkrs',name:'县镇户口人数'},
                {id:'cshkrs',name:'城市户口人数'},
                {id:'nsrs',name:'男生人数'},
                {id:'nvsrs',name:'女生人数'}/*,
                {id:'czqdrs',name:'初中起点'},
                {id:'gzqdrs',name:'高中起点'}*/
            ],
            displayField: 'name',
            valueField: 'id',
            fieldLabel:'选择指标',
            labelWidth:60,
            editable:false
        });
        this.zbCombobox.setValue('rxzrs');
        this.zbCombobox.render(this.mkm+"_combobox");
        this.zbCombobox.on('change',function(compo,newValue,oldValue){
            var params = {
                zbId : newValue
            };
            if(typeof this.timeParams!='undefined'){
                NS.apply(params,this.timeParams);
            }

            this.callService([{key:'getCountByZbId',params:params}],function(resp){
                // 刷新地图组件
                this.refreshMap(resp.getCountByZbId);
                // 刷新饼状图
                this.createPie(resp.getCountByZbId);
            },this);


        },this);
    },
    /**
     * 创建日期区间组件。
     */
    createDateSection:function(){
        var dateSection = this.dateSection = new NS.appExpand.DateSection();


        dateSection.render(this.mkm+"_dateSection");
    },
    /**
     * 模板切换
     */
    createTabIndex : function(){
        var me = this;
        var tabTpl = new NS.Template('<h2 class="wbh-common-table-relative">'+
            '<div class="wbh-common-table-absolute" >'+
            '<tpl for="templates">'+
            '<tpl if="xindex == 1">'+
            '<a href="javascript:void(0);" class="wbh-common-table-link"  name="{templateId}">{templateTitle}</a>&nbsp;'+
            '<tpl else>'+
            '<a href="javascript:void(0);" name="{templateId}"  >{templateTitle}</a>&nbsp;'+
            '</tpl>'+
            '</tpl>'+
            '&nbsp;'+
            '</div>'+
            '</h2>');
        this.tabComponent = new NS.Component({
            data : this.tabIndex,
            tpl : tabTpl
        });

        this.tabComponent.on('click',function(event,el){
            // 获取被点击tab的id并将它们对应的模板激活。
            var tar = $(el),parent = tar.parent();
            if(el.tagName == 'A'){
                parent.find("a.wbh-common-table-link").removeClass('wbh-common-table-link');
                tar.addClass('wbh-common-table-link');
                var tabId = tar.attr("name");
                // 显示相应的grid并隐藏其他grid
                me.hideByTabid(tabId);

            }
        });
        this.tabComponent.render(this.mkm+'_tabIndex');
    },
    /**
     * 根据tabid显示相应的div，并隐藏其他的div
     */
    hideByTabid :function(tabId){
        this.gridContainer.removeAll(false);
        for(var i = 0,len = this.tabIndex.templates.length;i<len;i++){
            var obj = this.tabIndex.templates[i];
            if(obj.templateId==tabId){
                if(obj.grid=='' && tabId=='byxx'){
                    this.reqestGridDate1();
                }else if(tabId=='byxx'){
                    this.gridContainer.add(obj.grid);
                }else if(tabId=='lyd'){
                    this.gridContainer.add(obj.grid);
                }

            }
        }

    },
    createMap :function(){
        var me = this;
        this.callService([{key:'getCountByZbId',params:this.pageParams}],function(resp){
            var dataStatus = resp.getCountByZbId;
            jQuery('#'+this.mkm+'_map').vectorMap({
                map: 'china_zh',
                enableZoom: false,// 是否能够放大缩小
                showTooltip: true,
//            values: sample_data,
//            selectedRegion: 'kaifeng',
                backgroundColor:'#FFFFFF',
                color:'#f4f3f0',
//            hoverColor:'#c9dfaf',
                onRegionClick: function(element, code, region)
                {
                    var message = 'You clicked "'
                        + region
                        + '" which has the code: '
                        + code.toUpperCase();

                    console.log(message);
                }


            });
            this.refreshMap(dataStatus);

            this.createPie(dataStatus);
        },this);

    },
    /**
     * 渲染map
     */
    refreshMap : function(dataStatus){
        var me = this;
        //jQuery('#'+this.mkm+'_map').empty();
        $.each([], function (i, items) {

        });
        $.each(dataStatus, function (i, items) {
            if (items.ZS >= 1000) {//动态设定颜色，
                var josnStr = "{'" + (items.YWM||items.QXM) + "':'#095ba5'}";
                $('#'+me.mkm+'_map').vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS >= 500 && items.ZS<1000) {//动态设定颜色，
                var josnStr = "{'" + (items.YWM||items.QXM) + "':'#136dce'}";
                $('#'+me.mkm+'_map').vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS >= 250 && items.ZS<500) {//动态设定颜色，
                var josnStr = "{'" + (items.YWM||items.QXM) + "':'#6b9ad2'}";
                $('#'+me.mkm+'_map').vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS < 250 && items.ZS>=100) {//动态设定颜色，
                var josnStr = "{'" + (items.YWM||items.QXM) + "':'#a1caff'}";
                $('#'+me.mkm+'_map').vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS > 0 && items.ZS<100) {//动态设定颜色，
                var josnStr = "{'" + (items.YWM||items.QXM) + "':'#daedfe'}";
                $('#'+me.mkm+'_map').vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS == 0) {//动态设定颜色，
                var josnStr = "{'" + (items.YWM||items.QXM) + "':'#f4f3f0'}";
                $('#'+me.mkm+'_map').vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }

        });
        jQuery('#'+this.mkm+'_map').bind('labelShow.jqvmap',function(event, label, code){
            var comboboxValue = me.zbCombobox.getRawValue();
            $.each(dataStatus, function (i, items) {
                if (code == (items.YWM||items.QXM)) {
                    label.html('<table><tr><th>'+items.MC +'</th></tr><tr><td>'
                        +comboboxValue+"</td><td>"+items.ZS
                        +'</td></tr><tr><td>占比</td><td>'+items.ZB+'%</td></tr></table>');

                }
            });
            }
        );
    },
    /**
     * 获取颜色数据。
     * @return {Object}
     */
    getColors:function(gdpData){
        var max = 0,
            min = Number.MAX_VALUE,
            cc,
            startColor = [200, 238, 255],
            endColor = [0, 100, 145],
            colors = {},
            hex;

        //find maximum and minimum values
        for (cc in gdpData)
        {
            if (parseFloat(gdpData[cc]) > max)
            {
                max = parseFloat(gdpData[cc]);
            }
            if (parseFloat(gdpData[cc]) < min)
            {
                min = parseFloat(gdpData[cc]);
            }
        }

        //set colors according to values of GDP
        for (cc in gdpData)
        {
            if (gdpData[cc] > 0)
            {
                colors[cc] = '#';
                for (var i = 0; i<3; i++)
                {
                    hex = Math.round(startColor[i]
                        + (endColor[i]
                        - startColor[i])
                        * (gdpData[cc] / (max - min))).toString(16);

                    if (hex.length == 1)
                    {
                        hex = '0'+hex;
                    }

                    colors[cc] += (hex.length == 1 ? '0' : '') + hex;
                }
            }
        }
        return colors;
    },
    /**
     * 创建饼状图图形
     */
    createPie : function(dataStatus){
        var comboboxValue = this.zbCombobox.getRawValue();
        var entransData = [];

        for(var i= 0,len=dataStatus.length;i<len;i++){
            (function(name,y){
                var obj = {
                    name:name,
                    y:Number(y)
                };
                if(obj.name!=''){
                    entransData.push(obj);
                }
            })(dataStatus[i].MC,dataStatus[i].ZS);
        }
        $('#'+this.mkm+'_pie').empty();
        $('#'+this.mkm+'_pie').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: ''
            },
            tooltip: {
                pointFormat: '</b>{series.name}<b>{point.y}，占比<b>{point.percentage:.1f}%</b>'
            },
            credits : {// 不显示highchart标记
                enabled : false
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            series: [{
                type: 'pie',
                name: comboboxValue,
                data: entransData
            }]
        });
    }
});