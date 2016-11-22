/**
 * 宿舍楼入住统计
 * User: zhangzg
 * Date: 14-8-12
 * Time: 下午4:57
 *
 */
NS.define('Pages.sc.SsRzTj',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            querySszzjgTree: 'scService?getSszzjgTree',// 获取导航栏数据，宿舍组织结构
            querySsRzData:'sstjService?getSsRzData'

        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins:[],
    requires:[],
    params:{},
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'宿舍楼住宿概况统计',
                pageHelpInfo:'分性别、学院统计宿舍概况、学生人数资源利用率。'}
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,containerx]
        });
        this.setPageComponent(container);

        container.on('afterrender',function(){
            // 刷新导航栏
            this.callService('querySszzjgTree',function(data){
                this.navigation.refreshTpl(data.querySszzjgTree);
                var i = 0;
                for(var key in data.querySszzjgTree){
                    if(i==0){
                        var nodeId = data.querySszzjgTree[key].id;
                        this.navigation.setValue(nodeId);
                        this.params.zzjgId = nodeId;
                    }
                    i++;
                }
            },this);
            this.fillCompoByData();
            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgPid = data.nodes[len-1].pid;
                me.fillCompoByData();
            });
        },this);
    },
    /**
     * 创建主题容器
     */
    createMain:function(){

        var container = new Ext.container.Container({
            layout : 'column',
            items:[new Ext.Component({
                html:'<div style="width: 878px"><div  class="loading-indicator">正在加载....</div></div>'
            })]

        });
        return container;
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(){
        this.callService({key:'querySsRzData',params:this.params},function(data){
            var tjData = data.querySsRzData;
            var compoArr = [];
            for(var i = 0,len=tjData.length;i<len;i++){
                var tjObj = tjData[i];
                var compo = new Exp.component.SsRzCompo({data:tjObj,columnWidth:0.5,margin:'10 5 0 0'});
                compoArr.push(compo);
            }


            this.mainContainer.removeAll();


            this.mainContainer.add(compoArr);
        },this);

    }
});