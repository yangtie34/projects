NS.define("System.fun.Announcement",{
    extend : 'Template.Page',
    tplRequires : [
        {fieldname : 'atTpl',path : 'app/system/fun/tpl/anment.html'}
    ],
    modelConfig : {
        serviceConfig : {
            'queryLater' : {
                service : "ast_queryRecentAnnouncement"
            }
        }
    },
    dataRequires : [
        {fieldname : 'laterData',key : 'queryLater'}
    ],
    constructor : function(ready){
        this.ready = ready;
        this.callParent();
    },
    init : function(ready){
        this.initTplData();
        var com = this.initPage();
        var container = new NS.container.Container({
            title : '主页',
            items : com
        });
        this.ready(com);
    },
    /**
     * 初始化新的报告
     */
    initPage : function(){
        var component = this.component = new NS.Component({
            tpl : this.atTpl,
            autoScroll : true,
            data : this.tplData
        });
        component.on('click',function(event,html){
            if(html.nodeName == "A"){
               var id = html.getAttribute('vId');
               var index = Number(html.getAttribute('index'));
               var list = NS.clone(this.laterData);
                this.tplData.list = list;
                if(NS.isArray(list)){
                    this.tplData.list[index-1].important = true;
                    this.setShowDataByIdAndRefresh(id);
                }
            }
        },this);
        return component;
    },
    /**
     * 初始化模版数据
     */
    initTplData : function(){
        var show;
        if(this.laterData.length == 0){
            show = {};
        }else{
            show = this.laterData[0];
        }
        var tplData = this.tplData  = {
            show : show,
            list : NS.clone(this.laterData)
        };
        if(NS.isArray(tplData.list) && tplData.list && tplData.list.length != 0){
            tplData.list[0].important = true;
        }
    },
    setShowDataByIdAndRefresh : function(id){
        var i= 0,
            data = this.laterData,
            len = data.length,
            item;
        for(;i<len;i++){
            item = data[i]
            if(item.id == id){
                this.tplData.show = item;
                this.component.refreshTplData(this.tplData);
                return;
            }
        }
    }
});