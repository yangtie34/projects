NS.define('NS.form.field.CheckboxGroup',{
    extend : 'NS.form.field.BaseField',
    /**
     * @cfg {Number} columns 你可以设置让checkbox几列显示
     */
    /**
     * @cfg {Boolean} vertical 控制checkboxgroup的显示方式(true为纵向，false为横向)，默认为false
     */
    /**
     * @cfg {Array} items  checkboxgroup子项
     *  var checkbox = new NS.form.field.CheckboxGroup({
             fieldLabel: '两列',
             columns: 2,
             vertical: true,
             items: [
             { boxLabel: 'Item 1', name: 'rb', inputValue: '1' },
             { boxLabel: 'Item 2', name: 'rb', inputValue: '2', checked: true },
             { boxLabel: 'Item 3', name: 'rb', inputValue: '3' },
             { boxLabel: 'Item 4', name: 'rb', inputValue: '4' },
             { boxLabel: 'Item 5', name: 'rb', inputValue: '5' },
             { boxLabel: 'Item 6', name: 'rb', inputValue: '6' }
             ]
     *  });
     */
    initComponent : function(config){
        this.component = Ext.create('Ext.form.CheckboxGroup',config);
    },
    /**
     * @private
     * 初始化参数配置mapping映射关系
     */
    initConfigMapping:function(){
        this.callParent();
        var processEditable = function(value,property,config){
            config.readOnly = Boolean(!value)
            if(!value){
                config.fieldStyle = "background:#E6E6E6;";
            }
        }
        this.addConfigMapping({
            items : true,
            columns : true,//几列分布显示
            vertical : true
        });
    },
    /**
     * 设置checkboxgroup的值
     * var myCheckboxGroup = new Ext.form.CheckboxGroup({
         columns: 3,
         items: [{
             name: 'cb1',
             boxLabel: 'Single 1'
             }, {
             name: 'cb2',
             boxLabel: 'Single 2'
             }, {
             name: 'cb3',
             boxLabel: 'Single 3'
             }, {
             name: 'cbGroup',
             boxLabel: 'Grouped 1'
             inputValue: 'value1'
             }, {
             name: 'cbGroup',
             boxLabel: 'Grouped 2'
             inputValue: 'value2'
             }, {
             name: 'cbGroup',
             boxLabel: 'Grouped 3'
             inputValue: 'value3'
             }]
         });

         myCheckboxGroup.setValue({
             cb1: true,//cb1--->被选中
             cb3: false,//cb3--->未被选中
             cbGroup: ['value1', 'value3']//名字为cbGroup的前2个值为value1 和value3
         });
     */
    setValue : function(){
        this.component.setValue.apply(this.component,arguments);
    },
    /**
     * 重置 
     */
    reset : function(){
        this.component.reset();
    }
});