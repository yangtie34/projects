/**
 * 宿舍楼统计
 * User: zhangzg
 * Date: 14-9-17
 * Time: 下午4:31
 *
 */
NS.define('Pages.zksf.SsfxyTj',{
    extend:'Pages.sc.TeacherSituation',
    modelConfig: {
        serviceConfig: {
            queryTitleData:'sslService?getTitleData',
            querySslRzColumn:'sslService?getSslRzColumn',
            queryTable1Data:'sslService?getTable1Data',
            queryTable2Data:'sslService?getTable2Data'

        }
    },
    tplRequires : [],
    cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
    mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{},
    init: function () {
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'院系入住总体明细情况',
                pageHelpInfo:'院系入住总体明细情况'}
        });
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,containerx]
        });
        this.setPageComponent(container);
        this.fillCompoByData();
    },
    title2 : new Exp.chart.PicAndInfo({
        title : "院系入住总体情况",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    table2 : new Ext.Component({
        tpl :'<table class="table1"><thead><tr><th >院系</th> <th>总人数</th> <th>住宿学生</th><th>已住宿占比</th><th>分布楼宇</th></tr></thead>'+
            '<tbody><tpl if="values.length == 0"> <tr></tr><div class="loading-indicator">正在加载....</div></tr></tpl>' +
            '<tpl for=".">' +
            '<tr><td>{MC}</td><td>{zxss}</td><td>{yrzs}</td><td>{rzzb}%</td><td><table class="table1" style="border: 0px;">' +
            '<tpl for="list">' +
            '<tr><td style="width: 100px;">{RXNJ_ID}年级</td><td style="width: 80px;">{XB}</td><td>{lys}</td></tr>' +
            '</tpl>' +
            '</table></td></tr>'+
            '</tpl>' +
            '</tbody></table>' ,
        data : []
    }),

    /**
     * 创建中部容器组件。
     */
    createMain:function(){

        var containerx = new Ext.container.Container({
            items:[this.title2,this.table2]
        });

        return containerx;
    },
    fillCompoByData:function(){
        this.callService({key:'queryTable2Data',params:this.params},function(respData){
            this.table2.update(respData.queryTable2Data);
        },this);
    }
});