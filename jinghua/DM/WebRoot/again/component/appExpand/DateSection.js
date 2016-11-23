/**
 * 时间段组件
 * User: zhangzg
 * Date: 13-11-20
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
NS.define('NS.appExpand.DateSection',{
    extend:'NS.Component',
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({});// 不接受任何配置参数
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
        this.createFieldSet();
    },
    createFieldSet : function(){
        var fromDateField = this.fromDateField = Ext.create('Ext.form.field.Date', {
            width:120,
            fieldLabel: '起',
            labelWidth:20,
            name: 'from_date',
            format:'Y-m-d',
            editable:false
        });

        var toDateField = this.toDateField  = Ext.create('Ext.form.field.Date', {
            width:120,
            margin:'0 0 0 5',
            fieldLabel: '止',
            labelWidth:20,
            name: 'to_date',
            format:'Y-m-d',
            editable:false
        });

        this.component = Ext.create('Ext.container.Container', {
            layout: 'hbox',
            padding: 0,
            margin: '0 0 0 0',
            width: 250,
            height: 26,
            border: false,
            items:[fromDateField,toDateField]
        });
        this.validate();
    },
    validate:function(){
        this.toDateField.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getRawValue(),
                fromDate = this.fromDateField.getRawValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    this.component.fireEvent('validatepass');
                }else{
                    this.toDateField.focus();
                    this.toDateField.setValue(null);
                }
            }
        },this);
        this.fromDateField.addListener('change',function(comp,newValue,oldValue){
            this.toDateField.setValue(null);
            var fromDate = comp.getRawValue(),
                toDate = this.toDateField.getRawValue();
            this.toDateField.setMinValue(fromDate);
            if(toDate!=''){
                if(toDate>=fromDate){
                    this.component.fireEvent('validatepass');
                }else{
                    this.fromDateField.setValue(null);
                    this.fromDateField.focus();
                }
            }
        },this);
    },
    /**
     * 渲染组件到制定的dom节点id上。
     * @param id
     */
    render : function(id) {
        this.component.render(id);
    },
    /**
     * 获取日期段组件的值。
     * @return {Object}
     */
    getRawValue : function(){
        var fromDate = this.component.getComponent(0).getRawValue(),
            toDate = this.component.getComponent(1).getRawValue();
        return {fromDate:fromDate,toDate:toDate};
    },
    setDisabled:function(){
        this.component.getComponent(0).setDisabled(true);
        this.component.getComponent(1).setDisabled(true);
    },
    setValue2Today:function(){
        this.component.getComponent(0).setValue(new Date());
        this.component.getComponent(1).setValue(new Date());
    },
    setValue : function(config){
        this.component.getComponent(0).setValue(new Date(config.from));
        this.component.getComponent(1).setValue(new Date(config.to));
    }
});