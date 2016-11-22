/**
 * @class NS.form.field.ItemSelector
 * @extend NS.form.field.BaseField
 */
NS.define('NS.form.field.ItemSelector', {
    extend: 'NS.form.field.BaseField',
    /**
     * @cfg {Object}  data 数据
     */
    /**
     * @cfg {String}  displayField 数据
     */
    /**
     * @cfg {String}  valueField 数据
     */
    initComponent: function (config) {
        this.processConfig(config);
        this.component = Ext.create('Ext.ux.form.ItemSelector', config);
    },
    /**
     * 处理组件配置项
     * @param {Object} config
     */
    processConfig: function (config) {
        var data = this.backData = config.data;
        var store = new Ext.data.Store({
            fields: config.fields||['id', 'mc'],
            data: data
        });
        NS.applyIf(config, {
            store: store,
            displayField: 'mc',
            valueField: 'id',
            imagePath: '../ux/images/'
        });
        delete config.data;
    },
    /**
     * @private
     * 初始化参数配置mapping映射关系
     */
    initConfigMapping : function () {
        this.callParent();
        this.addConfigMapping({
            fromTitle: true,
            title : true,
            toTitle: true
        });
    },
    /**
     * 把传入的id从可选项中移除
     * @param id
     */
    removeOptionsByIds : function(id){
        var com = this.component,
            fromStore = com.fromField.getStore(),
            toStore = com.toField.getStore(),
            process = function(id){
            for(var i= 0,len=id.length;i<len;i++){
                var record = fromStore.findRecord(com.valueField,id[[i]])
                fromStore.remove(record);
            }
        }
        if(NS.isArray(id)){
            process(id);
        }else if(NS.isString(id)){
            process(id.split(","));
        }else if(NS.isNumber(id)){
            process([id.toString()]);
        }
        com.setValue();
    },
    /**
     * 把id对应的数据添加到可选项中
     */
    addOptionsWithIds : function(id){
        var data = this.backData,nowdata,com = this.component,
            fromStore = com.fromField.getStore(),
            toStore = com.toField.getStore(),
            removeRecords = fromStore.getRemovedRecords(),
            process = function(ids){
                for(var i= 0,len=ids.length;i<len;i++){
                    var id = ids[i];
                    for(var k=0;k<removeRecords.length;k++){
                        if(id == removeRecords[k].get('id')){
                           fromStore.add(removeRecords[k]);
                        }
                    }
                }
            };
        if(NS.isArray(id)){
            process(id);
        }else if(NS.isString(id)){
            process(id.split(","));
        }else if(NS.isNumber(id)){
            process([id.toString()]);
        }
        com.setValue();
    },
    /**
     * 获取组件的显示值
     */
    getRawValue : function(){
        var array = this.component.getValue(),
                      toStore = this.component.toField.getStore(),item,id;
        var rawvalue = [];
        for(var i= 0;i<array.length;i++){
            id = array[i];
            item = toStore.findRecord(this.component.valueField,id);
            rawvalue.push(item.get(this.component.displayField));
        }
        return rawvalue;
    },
    /**
     * 重新加载ItemSelect的可选项
     */
    loadData : function(data){
        var com = this.component,
            fromStore = com.fromField.getStore(),
            toStore = com.toField.getStore();
        com.getStore().removeAll();
        com.getStore().loadData(data);
        fromStore.removeAll();
        toStore.removeAll();
        fromStore.add(com.getStore().getRange());
        com.syncValue();
    }
});