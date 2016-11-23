/**
 * 学生消费概况统计。
 * User: Administrator
 * Date: 14-6-3
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
NS.define('Pages.sc.YktGeneralSituation',{
    extend:'Template.Page',
    mkm:'PagesscYktGeneralSituation',
    entityName:'VStudentFrom',// 实体属性表
    mixins : [
        "Pages.sc.Scgj"		//输出工具类
    ],
    modelConfig: {
        serviceConfig: {
            'CurrentXnxq':'CurrentXnxq',
            'queryHeaderData': 'baseService?queryForAddByEntityName',
            'getCountByZbId':'studentFromService?getCountByZbId',
            'getRsjXfbs':'yktSituationService?getRsjXfbs',
            'getRsjXfje':'yktSituationService?getRsjXfje',
            'getGDxfrs':'yktSituationService?getGDxfrs',// 获取高低消费人数
            'getListSjxfBsAndJe':'yktSituationService?getListSjxfBsAndJe',// 获取生均消费笔数、生均消费金额
            'getXfqj':'yktSituationService?getXfqj',// 获取消费区间分布
            'getXsjcxg':'yktSituationService?getXsjcxg'// 获取学生消费习惯数据

        }
    },
    tplRequires : [
        {fieldname : 'tpl',path : 'app/pages/sc/template/YktGeneralSituation.html'},
        {fieldname : 'tpl_top',path : 'app/pages/sc/template/YktGeneralSituation_top.html'},
        {fieldname : 'tpl_middle',path : 'app/pages/sc/template/YktGeneralSituation_middle.html'},
        {fieldname : 'tpl_bottom',path : 'app/pages/sc/template/YktGeneralSituation_bottom.html'}
    ],
    cssRequires : [
        'app/pages/sc/template/css/op.css',
        'app/pages/sc/template/css/another-add.css'
    ],
    init:function(){
        var me = this,
            page = new NS.Component({
                layout:'fit',
                border:false,
                autoScroll:true,
                autoShow : true,
                tpl : this.tpl,
                data : {
                     topData:{pageCN:this.mkm}
                }
            });

        page.on('click',function(event){
            if ($(event.getTarget()).attr("attr") == 'help') {
                $("#"+me.mkm+"_helpinfo").slideToggle();
            }
        });
        this.setPageComponent(page);
        this.createCombobox();
        this.createRadioGroup();
        this.createDateSection();
        var params = this.dateSection.getRawValue();
        params.init = true;
        this.renderChart(params);
        this.rendercompos(params);
    },

    trsdata:function(data){
        var zbid = this.zbCombobox.getValue();
        for(var i= 0,len = data.length;i<len;i++){
            if('rxzrs'==zbid){
                data[i].value = data[i].SJXFBS;
            }else if('nchkrs'==zbid){
                data[i].value = data[i].SJXFJE;
            }
        }
        return data;
    },
    trsdata1:function(data){
        for(var i= 0,len = data.length;i<len;i++){
            data[i].field = data[i].XFQJ;
            data[i].name = '日生均消费区间分布';
            data[i].value = data[i].ZS;
        }
        return data;
    },
    trsdata2:function(data){
        var map = {
            three:'日均三餐',
            two:'日均两餐',
            one:'不足两餐'
        };
        for(var i= 0,len = data.length;i<len;i++){
            data[i].name =map[data[i].LX];
            data[i].y = data[i].RS;
        }
        return data;
    },
    rendercompos :function(params){
        this.callService({key:'getRsjXfbs',params:params},function(respData){
                this.render(this.mkm+"_top", this.tpl_top, respData.getRsjXfbs);
        },this);
        // 高低消费的评判标准不准确，这里注释掉不再显示
        /*this.callService({key:'getGDxfrs',params:params},function(respData){
            this.render(this.mkm+"_middle", this.tpl_middle, respData.getGDxfrs);
        },this);*/

    },
    renderChart:function(params){
        var date = this.dateSection.getRawValue();
        this.callService([{key:'getListSjxfBsAndJe',params:params}],function(respData){
            this.getListSjxfBsAndJe = respData.getListSjxfBsAndJe;
            var data = this.trsdata(respData.getListSjxfBsAndJe);
            this.renderCommonChart(this.mkm+'_xintiaotu','从 '+date.fromDate+' 至 '+date.toDate+' 间日生均消费笔数','日生均消费笔数',data,'spline','笔',true);
        },this);
        this.callService([{key:'getXfqj',params:params}],function(respData){
            var data1 = this.trsdata1(respData.getXfqj);
            this.renderCommonChart(this.mkm+'_xiaofeiqujian','从 '+date.fromDate+' 至 '+date.toDate+' 学生消费能力区间分布图','人数',data1,'column','人');
        },this);
        this.callService([{key:'getXsjcxg',params:params}],function(respData){
            this.render(this.mkm+"_bottom", this.tpl_bottom, respData.getXsjcxg);
            var data2 = this.trsdata2(respData.getXsjcxg.list);
            this.createPie(data2,'从 '+date.fromDate+' 至 '+date.toDate+' 学生平均三餐就餐次数组成');
        },this);
    },
    /**
     * 创建日期区间组件。
     */
    createDateSection:function(){
        var dateSection = this.dateSection = new NS.appExpand.DateSection(),
            today = new Date();
        dateSection.setValue({from : today - 14 * 3600* 24000,to : new Date()});
        dateSection.addListener('validatepass',function(){
            var params = this.timeParams= this.dateSection.getRawValue();
            this.rendercompos(params);
            this.renderChart(params);
        },this);

        dateSection.render(this.mkm+"_dateSection");
    },
    /**
     * 创建指标下拉框组件。
     */
    createCombobox:function(){
        this.zbCombobox = new NS.form.field.ComboBox({
            fields:['id','name'],
            data:[{id:'rxzrs',name:'日生均消费笔数'},
                {id:'nchkrs',name:'日生均消费金额'}
            ],
            displayField: 'name',
            valueField: 'id',
            fieldLabel:'选择指标',
            labelWidth:60,
            width:180,
            editable:false
        });
        this.zbCombobox.setValue('rxzrs');
        this.zbCombobox.render(this.mkm+"_combobox");
        this.zbCombobox.on('change',function(compo,newValue,oldValue){
            var params = {
                zbId : newValue
            };
            var comboName = this.zbCombobox.getItemById(newValue).name;
            if(typeof this.timeParams =='undefined'){
                NS.apply(params,this.timeParams);
            }
            var data = this.trsdata(this.getListSjxfBsAndJe);
            var date = this.dateSection.getRawValue();
            this.renderCommonChart(this.mkm+'_xintiaotu','从 '+date.fromDate+' 至 '+date.toDate+' 间'+comboName,comboName,data,'spline','元',true);
        },this);
    },
    createRadioGroup:function(){
        this.radioGroup = new NS.form.field.RadioGroup({

            width:220,
            columns:2,
            vertical: false,
            items: [
                { boxLabel: '按性别统计', name: 'tjlx', inputValue: 'xb', checked: true},
                { boxLabel: '按户口性质统计', name: 'tjlx', inputValue: 'hklx'}
            ]
        });

        this.radioGroup.on('change',function(comp,newValue,oldValue){
            NS.apply(newValue,this.dateSection.getRawValue());
            var comboName = this.zbCombobox.getRawValue();
            this.callService([{key:'getListSjxfBsAndJe',params:newValue}],function(respData){
                this.getListSjxfBsAndJe = respData.getListSjxfBsAndJe;
                var data = this.trsdata(respData.getListSjxfBsAndJe);
                this.renderCommonChart(this.mkm+'_xintiaotu','从 '+newValue.fromDate+' 至 '+newValue.toDate+' 间'+comboName,comboName,data,'spline','元',true);
            },this);
        },this);
        this.radioGroup.render(this.mkm+'_radioGroup');
    },
    /**
     * 创建饼状图图形
     */
    createPie : function(dataStatus,text){

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
            })(dataStatus[i].name,dataStatus[i].y);
        }
        $('#'+this.mkm+'_pie').empty();
        $('#'+this.mkm+'_pie').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: text||''
            },
            /*tooltip: {
             pointFormat: '{series.QXM}:<b>{point.percentage:.1f}%</b>'
             },*/
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
                name: '达标人数',
                data: entransData
            }]
        });
    },
    clearDiv : function(domId){
        var dom=document.getElementById(domId);
        dom.innerHTML="";
    },
    render:function(nodeId,tpl,data){
        this.clearDiv(nodeId);
        var comp = new NS.Component({
            data : data,
            tpl : tpl
        });
        comp.render(nodeId);
    }
});