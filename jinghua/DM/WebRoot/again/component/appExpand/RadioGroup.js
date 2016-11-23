/**
 * 单选框容器组件。这不同于原生的Ext的radioGroup组件！
 * User: zhangzg
 * Date: 13-11-20
 * Time: 下午3:06
 * To change this template use File | Settings | File Templates.
 */
NS.define('NS.appExpand.RadioGroup',{
    extend:'NS.Component',
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({
            items:true
        });
    },
    initEvents:function(){
        this.callParent();
    },
    packEvents: function(){
        this.callParent();
    },
    initComponent:function(config){
        this.radioCompoMap = {};
        this.radioNSCompoMap = {};
        this.callParent();
        this.createFieldSet(config);
    },
    createFieldSet : function(config){
        var items = config.items,
            length = items.length,
            i = 0,
            firstCheckRadio = false,
            isChecked = false;
        this.component = Ext.create('Ext.container.Container',{
            layout: {
                type:'hbox',
                align:'middle'
            },
            padding: 0,
            margin: '0 0 0 0',
            height: 26,
            border: false
        });
        var width = 0;
        for(;i<length;i++){
            var item = items[i];
            (function(item,me){
                isChecked = !firstCheckRadio&&item.checked ? true : false;
                var radio = new Ext.form.field.Radio({
                    name:item.radioGroupName,
                    inputValue:item.radioKey,
                    checked:isChecked,
                    labelWidth:item.labelWidth,
                    fieldLabel:item.fieldLabel
                });
                var fieldSet = Ext.create('Ext.container.Container', {
                    layout: 'column',
                    padding: 0,
                    margin: '0 0 0 0',
                    width: item.width,
                    height: 26,
                    border: false,
                    items:[radio,item.compo.getLibComponent()]
                });
                if(!isChecked){
                    item.compo.getLibComponent().setDisabled(true);
                }else{
                    me.checkedCompo = item.compo;
                }
                me.radioCompoMap[item.radioKey] = fieldSet;
                me.radioNSCompoMap[item.radioKey] = item.compo;
                radio.addListener('change',function(compo,newValue,oldValue){
                    var compoValue = compo.inputValue;
                    if(!newValue&&oldValue){// 由选中变为 不选中
                        me.radioCompoMap[compoValue].getComponent(1).setDisabled(true);
                    }else if(newValue&&!oldValue){// 由不选中 变为 选中
                        me.checkedCompo = me.radioNSCompoMap[compoValue];
                        me.radioCompoMap[compoValue].getComponent(1).setDisabled(false);
                    }
                },me);
                width = width + item.width;
                me.component.add(fieldSet);
            })(item,this);
        }
        this.component.setWidth(width);
    },
    /**
     * 渲染组件到制定的dom节点id上。
     * @param id
     */
    render : function(id) {
        this.component.render(id);
    },
    getRawValue :function(){
        return this.checkedCompo.getRawValue();
    },
    getValue :function(){
        return this.checkedCompo.getValue();
    }
});