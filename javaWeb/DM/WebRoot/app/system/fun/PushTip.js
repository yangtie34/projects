NS.define('System.fun.PushTip',{
    extend : 'Template.Page',
    tplRequires : [{
        fieldname : 'tpl',path : 'app/system/fun/tpl/message.html'
    }],
    cssRequires : [
        'app/system/fun/css/outer-style.css'
    ],
    modelConfig : {
        serviceConfig : {
            'see' : {
                service : "message_updateReadYetFlag"
            }
        }
    },
    init : function(){
        this.createWindow();
    },
    message : {},
    /**
     * 创建window窗体
     */
    createWindow : function(){
        var body = Ext.getBody(),
            scWidth = body.getWidth(),
            scHeight = body.getHeight(),
            width = 250,
            height = 200,
            x = scWidth- 300,
            y = scHeight - 200;
        this.contentComponent = this.createContent();
        this.popWindow = new Ext.window.Window({
            closable : false,
            bodyCls : '',
            baseCls : '',
            draggable : false,
            plain : true,
            resizable : false,
            header : false,
//            shadow : false,
            x : x,
            y : y,
            layout : 'fit',
            items : [this.contentComponent]
        });
    },
    /**
     * 创建内容显示组件
     * @return {NS.Component}
     */
    createContent : function(){
        var component = new Ext.Component({
            tpl : this.tpl.tpl
        });
        component.on({
            click : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    if(element.tagName == "A"){
                        var id = NS.fly(element).getAttribute("index");
                        this.see(id);
                    }
                }
            }
        });
        return component;
    },
    /**
     * 弹出窗口
     * @param data
     */
    pop : function(data){
          this.window.show();
    },
    /**
     * 查看
     * @param id
     */
    see : function(id){
          this.callSingle({
              key : 'see',
              params : {id : id}
          },function(data){
              if(data.success){
                 this.next(id);
              }
          });
    },
    /**
     * 显示下一条信息
     * @param id
     */
    next : function(id){
         delete this.message[id];
         this.showMessage();
    },
    /**
     * 将请求的数据压入存储数据中
     * @param data
     */
    pushMessage : function(data){
        var msg = this.message;
        data = data || [];
        for(var i=0;i<data.length;i++){
            var item = data[i];
            msg[item.id] = item;
        }
        this.showMessage();//显示消息
    },
    /**
     * 显示消息
     */
    showMessage : function(){
        if(this.popWindow.isHidden()){
            for(var i in this.message){
                this.contentComponent.update(this.message[i]);
                this.popWindow.show();
                return;
            }
        }else{
            this.popWindow.hide();
            this.showMessage();
        }
    }
});