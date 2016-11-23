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
});