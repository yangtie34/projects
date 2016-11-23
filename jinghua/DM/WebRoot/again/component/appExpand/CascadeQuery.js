/**
 * 组织结构级联查询组件。
 * @class NS.appExpand.CascadeQuery
 */
NS.define('NS.appExpand.CascadeQuery',{
	extend:'NS.container.Container',
	initComponent:function(obj){
        this.holeConfig = obj;
		this.callParent();
        this.translateData(obj);
        this.createContainer(obj);
        this.bindComboboxEvent();
	},
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
                crosswise : {name :'crosswise'},
            	holistic : {name : 'holistic'},
                cascadeType : {name :'cascadeType'},
            	data : {name :'data'}
        });
	},
	initEvents:function(){
		this.callParent();
	},
	packEvents: function(){
		this.callParent();
	},
	createContainer : function(obj){
        /*是否横向参数 如果未定义或者定义为true 布局的值设为column 否则设置为table;*/
        var isCrosswise = typeof obj.crosswise == 'undefined' || obj.crosswise==true;
            layout = isCrosswise ? 'column':'anchor',
            fsHeight = isCrosswise ? (obj.height||22):(obj.height||obj.cascadeType.length*26),
            defaults = isCrosswise ? {}:{anchor: '100%'};
		this.component = Ext.create('Ext.form.FieldSet', {
            layout: layout,
            defaults:defaults,
            padding: 0,
            margin: '0 0 0 0',
            width: obj.width||200,
            height: fsHeight,
            border: obj.border||false
        });
        this.createComboboxs(obj);
	},
    /**
     * @private
     * @param {Object} obj待转换数据
     *          将转换后的数据作为obj的一个属性。
     */
    translateData : function(obj){
        var datacfg = obj.data,nodeHash = {},rootId = 0;
        obj.nodeHash = {};//传递进来的数据，经过转换后变成nodehash座位obj的一个属性。
        obj.firstCCData = [];//第一层的数据。
        if(datacfg instanceof Array){
            if(datacfg.length==0){
                return ;
            }
            for(var i =0;i<datacfg.length;i++){
                var dataObj = datacfg[i];
                var node = {
                    pid : dataObj.fjdId||dataObj.fjdid,
                    text : dataObj.mc,
                    expanded : true,
                    leaf : true,
                    children : [],
                    id : dataObj.id,
                    cc : dataObj.cc,
                    cclx : dataObj.cclx,
                    qxm : dataObj.qxm
                }
                nodeHash[dataObj.id] = node;
                if(node.cclx==obj.cascadeType[0].cclx){
                    obj.firstCCData.push(node);
                }
            }
            for(var key in nodeHash){
                var hashNode = nodeHash[key];
                if(nodeHash.hasOwnProperty(hashNode.pid)){
                    nodeHash[hashNode.pid].children.push(hashNode);
                    nodeHash[hashNode.pid].leaf = false;
                }else{
                    rootId=hashNode.id;
                }
            }
            obj.nodeHash = nodeHash;
        }
    },
    /**
     * @private
     * @param {Object} obj
     *          根据配置项创建下拉表。
     */
    createComboboxs : function(obj){
        var me = this;
        var cascadeType = obj.cascadeType;
        for(var i=0;i<cascadeType.length;i++){
            var typeObj = cascadeType[i];
            (function (i,labelName,valueName,displayName,num,cclx,obj){
                if(i==0){
                    var comboboxStore = Ext.create('Ext.data.Store', {
                        fields: ['id', 'text','cclx','qxm'],
                        data : obj.firstCCData
                    });
                }else{
                    var comboboxStore = Ext.create('Ext.data.Store', {
                        fields: ['id', 'text','cclx','qxm']
                    });
                }
                var testCombobox = Ext.create('Ext.form.field.ComboBox', {
                    fieldLabel: labelName,
                    labelWidth:30,
                    store: comboboxStore,
                    queryMode: 'local',
                    columnWidth:1/num,
                    displayField: displayName,
                    valueField: valueName,
                    editable : false,
                    name:cclx,
                    emptyText :'请选择...'
                });
                me.component.add(testCombobox);
            })(i,typeObj.labelName,typeObj.valueName||"id",typeObj.displayName||"text",cascadeType.length,typeObj.cclx,obj);
        }
    },
    /**
     * @private
     *      为级联组件中各下拉组件绑定事件。
     */
    bindComboboxEvent : function(){
        var me = this;
        var i = 0;
        for(;i<this.holeConfig.cascadeType.length;i++){
            (function(i,length){
                var aComponent = me.component.getComponent(i);
                if(i!=(length-1)){
                    aComponent.addListener("change",function(com,newValue,oldValue,opts){
                        // 将下一个cc的组件值设置为空
                        var nextComponent = me.component.getComponent(i+1);
                        nextComponent.setValue(null);
                        // 从nodehash中根据id获取响应的childrens
                        var storeArray = typeof me.holeConfig.nodeHash[newValue]=='undefined'?null:
                            me.holeConfig.nodeHash[newValue].children;
                        // 根据获取的children刷新下一个组件的store
                        if(storeArray==null){
                            nextComponent.getStore().loadData([]);
                        }else{
                            nextComponent.getStore().loadData(storeArray);
                        }

                    });
                }
            })(i,this.holeConfig.cascadeType.length);
        }
    },
    /**
     * 将创建的原生dom节点渲染到制定的dom节点上。
     * @param id
     *          被渲染的dom节点id 或者 dom节点。
     */
    render : function(id) {
        this.component.render(id);
    },
    /**
     *  获取级联组件的值。
     *  @return {Array}
     *      各组件相应的值
     *
     *      var result ={
     *          cclx :"yx", //院系
     *          id :101010101l,// 主键
     *          text : "建筑工程系"   // 显示值
     *      }
     *
     */
    getValue : function(){
        var values = [],
            items = this.component.items.items,
            itemData;
        for(var i=0;i<items.length;i++){
            itemData = items[i];
            if(itemData.displayTplData.length == 0){
               break;
            }
            values.push(itemData.displayTplData[0]);
        }
        return values;
    }
});