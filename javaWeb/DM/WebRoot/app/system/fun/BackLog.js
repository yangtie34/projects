NS.define("System.fun.BackLog",{
    extend : 'Template.Page',
    tplRequires : [
        {fieldname : 'backlogTpl',path : 'app/system/fun/tpl/backlog.html'}
    ],
    cssRequires : ['app/system/fun/css/list-style.css'],
    modelConfig : {
        serviceConfig : {
            'queryMessage' : {
                service : "backlogServiceImpl?queryMyMessage"
            }
        }
    },
    dataRequires : [
        {fieldname : 'mymessage',key : 'queryMessage'}
    ],
    constructor : function(ready){
        this.ready = ready;
        this.callParent();
    },
    init : function(ready){
       var component = new NS.Component({
           tpl : this.backlogTpl,
           data : this.mymessage
       });
        component.on('click',function(event,el){
            if(el.nodeName == "A"){
               var index =  $(el).attr('index');
               var obj = this.mymessage[index-1];
                if(obj){
                    var classname = obj.pageName;
                    MainPage.openPage(classname);
                }
            }
        },this);
       this.ready(component);
    }
});