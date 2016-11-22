NS.define('Pages.permiss.base.Tbar',{
    singleton : true,
    getInstance : function(config){
        var me = this,
            items = config.items,
            listeners = config.listeners,
            itemsHtml = this.generateItemsHtml(items),
            baseHtml = '<div class="permiss-common-stats" style="border:none">{0}</div>',
            html = NS.String.format(baseHtml,itemsHtml);
        var component = new NS.Component({
            html : html
        });
        component.on('afterrender',function(){
            me.processListeners(component,listeners);
        });
        return component;
    },
    /**
     *
     * @param component
     * @param listeners  ｛
     *                           name.click : {scope : this,fn : function(){}}
     *                     ｝
     */
    processListeners : function(component,listeners){
        var item,ay,pe = component.getLibComponent().el.dom;
        for(var i in listeners){
            item = listeners[i];
            ay = i.split('.');
            this.bindItemEvent(pe,ay[0],ay[1],item.fn,item.scope)
        }
    },
    bindItemEvent : function(parent,name,event,fn,scope){
        var selector = NS.String.format("[name='{0}']",name),
            rets = $(parent).find(selector);
        if(rets.length != 1){
            NS.log("程序出错!找到name为"+name+"的元素个数为"+rets.length+"个！");
        }else{
            $(rets[0])[event](function(){
                fn.call(scope||this,arguments);
            });
        }
    },
    generateItemsHtml : function(items){
        var i= 0,len = items.length,item,html = '';
        for(;i<len;i++){
            item = items[i];
            if(item){
                switch(item.xtype){
                    case 'textfield' : html += this.generateInput(item);break;
                    case 'button' : html += this.generateButton(item);break;
                }
            }
        }
        return html;
    },
    generateInput : function(item){
        var baseHtml = '<label class="permiss-common-labeldefault">{0}：</label><input class="permiss-common-inputdefault " name="{1}" type="text" placeholder="{2}"/>';
            label = item.label,
            blankText = item.blankText,
            name = item.name;
        return NS.String.format(baseHtml,label,name,blankText);
    },
    generateButton : function(item){
        var baseHtml = '<button class="permiss-common-buttondefault permiss-common-orange" name = {0} type="button">{1}</button>',
            text = item.text,
            name = item.name;
        return NS.String.format(baseHtml,name,text);
    }
});