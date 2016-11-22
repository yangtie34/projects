/**
 * 组件区间
 * User: zhangzg
 * Date: 13-11-20
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
NS.define('NS.appExpand.CompoSection',{
    extend:'NS.Component',
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({
            fromCompo:true,
            toCompo:true
        });
    },
    initEvents:function(){
        this.callParent();
    },
    packEvents: function(){
        this.callParent();
        /*增加事件*/
        this.addEvents(
            /**
             * @event 日期组件校验通过事件。
             *         即开始日期不大于结束日期，并且开始日期和结束日期都不为空时触发的事件。
             * @param {NS.Component} this
             */
            'validatepass'
        );
    },
    /*增加事件监听器*/
    onValidatepass:function(){
        this.component.on('validatepass',function(){
            this.fireEvent('validatepass',this);
        },this);
    },
    initComponent:function(config){
        this.callParent();
        this.createFieldSet(config);
    },
    createFieldSet : function(config){
        this.component = Ext.create('Ext.container.Container', {
            layout: 'hbox',
            padding: 0,
            margin: '0 0 0 0',
            width:250,
            height: 26,
            border: false,
            items:[config.fromCompo.getLibComponent(),config.toCompo.getLibComponent()]
        });
        this.validate(config);
    },
    validate:function(config){
        var fromCompo = config.fromCompo.getLibComponent(),
            toCompo = config.toCompo.getLibComponent();
        toCompo.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getValue(),
                fromDate = fromCompo.getValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    this.component.fireEvent('validatepass');
                }else{
                    toCompo.focus();
                    toCompo.setValue(null);
                }
            }
        },this);
        fromCompo.addListener('change',function(comp,newValue,oldValue){
            toCompo.setValue(null);
        },this);
    },
    /**
     * 渲染组件到制定的dom节点id上。
     * @param id
     */
    render : function(id) {
        this.component.render(id);
    },
    getValue : function(){
        var from = this.component.getComponent(0).getValue(),
            to = this.component.getComponent(1).getValue();
        return {from:from,to:to};
    },
    getRawValue : function(){
        var from = this.component.getComponent(0).getRawValue(),
            to = this.component.getComponent(1).getRawValue();
        return {from:from,to:to};
    },
    setDisabled:function(){
        this.component.getComponent(0).setDisabled(true);
        this.component.getComponent(1).setDisabled(true);
    },
    setValue : function(config){
        this.component.getComponent(0).setValue(config.from),
        this.component.getComponent(1).setValue(config.to);
    }
});