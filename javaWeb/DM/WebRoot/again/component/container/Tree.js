/**
 * @class NS.container.Tree
 * @extends NS.container.Container
 *         树形容器 支持普通树、多选树、多列树、过滤树
 *
         var treeData =[{"checked":true,
                                "cc":"2",
                                "cclx":"ZY",
                                "dm":"0103",
                                "fjdId":"1001000000372700",
                                "id":"1001000000372711",
                                "mc":"工程测量技术",
                                "sfky":"1",
                                "sfyzjd":"0"},........];// 具有树结果关系的数组(Array)数据。

         var treeConfig = {
                            data:treeData,
                            title : null,
                            rootVisible : true,
                            border : true,
                            margin : '0 0 0 0',
                            checkable : true,// 是否是多选树
                            multiple:	true,// 多列树
                            multyFields:[{"columnName":"节点","dataIndex":"text"},
                                         {"columnName":"描述","dataIndex":"cclx"}],
                            filter:true,// 是否具有过滤器
                            iconClsCfg : {
                                'YX':"page-add", // 院系层次上的节点图标样式
                                'ZY':"page-update",// 专业层次上的节点图标样式
                                'BJ':"page-search",
                                'XJD':"page-xtsz",
                                'KM':"page-book"
                            }// 配置各个层次类型上节点的图标样式。
                }
         var treePanel = new NS.container.Tree(obj);

 */
NS.define('NS.container.Tree', {
    extend: 'NS.container.Panel',
    /**
     * 创建panel
     * @param {Object} obj 配置对象
     */
    initComponent: function (obj) {
        var me = this;
        this.configObj = obj;
        this.configFilter(obj);
        var treeStore = Ext.create('Ext.data.TreeStore', {
            root : obj.root
        });
        delete obj.root;
        obj.store = treeStore;
        this.component = Ext.create('Ext.tree.Panel', obj);
        this.editor = new Ext.tree.TreeEditor({
            updateEl: true,
            field: {
                xtype: 'textfield'
            }
        });
        this.modifyValue = {};
        this.editor.addListener('complete',function(editor,newValue,oldValue){
            var node = editor.editorNode,
                id = node.data.id;
            me.modifyValue[id] = newValue;
            node.set('text',newValue);
        });
        this.initMyEvent(obj);
//        this.requestTransfer(obj);
    },
    configFilter: function(obj){
        if(obj.filter){
            this.filterSet(obj);
        }
        if(obj.multiple==true && typeof obj.multyFields !='undefined'){
            this.multipleSet(obj);
        }
        this.translate(obj);
        obj.useArrows = (typeof obj.useArrows !='undefined') ? obj.useArrows : true;
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping: function () {
        this.callParent();
        this.addConfigMapping(
            { checkable: {name: 'checkable'},
                treeData: {name: 'treeData'},
                rootVisible: {name: 'rootVisible'},
                multyFields: {name: 'multyFields'},
                multiple: {name: 'multiple'},
                filter: {name: 'filter'},
                iconClsCfg :{name :'iconClsCfg'},
                useArrows : {name :'useArrows'},
                treeEditor : {name:'treeEditor'}
            }
        );
    },
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /**
             * @event
             */
            'itemclick',
            /**
             * @event
             */
            'itemcontextmenu'
        );
    },
    onItemclick: function () {
        this.component.on('itemclick',function (com, record, item) {
                var data = record.data;
                NS.applyIf(data,record.raw);
                this.fireEvent('itemclick', this, data, item);
            },this);
    },
    onItemcontextmenu: function () {
        this.component.on('itemcontextmenu',function (com, record, item, index, e) {
            this.fireEvent('itemcontextmenu', this, record, record, item, index, e);
        },this);
    },

    /**
     * 转换数据
     * @private
     * @param {Object}
     *                 datacfg 树形面板的实际数据。
     */
    translate: function (obj) {
        var checkable = Boolean(obj.checkable),
            datacfg = obj.treeData,
            nodeHash = {},
            rootId = 0,
            dataObj = null,
            node={},
            hashNode = null;
        if (datacfg instanceof Array) {
        	datacfg.push({
        		children:[],
        		text:'',
        		expanded: true,
        		id:0
        	});
            if (datacfg.length == 0) {
                this.configObj.root = {
                    text: "根节点",
                    expanded: true,
                    children: []
                };
            }
            for (var i = 0,len=datacfg.length; i < len; i++) {
                dataObj = datacfg[i];
                node = {
                    pid: dataObj.fjdId||dataObj.fjdid,
                    text: dataObj.mc,
                    expanded: dataObj.expanded!=undefined ? dataObj.expanded : false,
                    leaf: dataObj.leaf!=undefined ? dataObj.leaf : false,
                    children: [],
                    id: dataObj.id,
                    cc: dataObj.cc,
                    cclx: dataObj.cclx
                }
                if(obj.iconClsCfg){
                    node.iconCls = obj.iconClsCfg[dataObj.cclx];
                }
                NS.applyIf(node,dataObj);
                if (checkable) {
                    node.checked = Boolean(Number(dataObj.checked));
                }
                nodeHash[dataObj.id] = node;
            }
            for (var key in nodeHash) {
                hashNode = nodeHash[key];
                var nodesPid = hashNode.pid;
                if (nodeHash[nodesPid]) {
                    nodeHash[nodesPid].children.push(hashNode);
                    nodeHash[nodesPid].leaf = false;
                } else {
                    rootId = hashNode.id;
                }
            }
            this.configObj.root = nodeHash[rootId];
        } else {
            this.configObj.root = datacfg;
        }
    },
    /**
     * @private
     * @param {Object} 多行树。
     */
    multipleSet: function (obj) {
        var fields = new Array();
        var columns = new Array();
        var fieldObj = obj.multyFields;
        if (obj.multiple && fieldObj) {
            for (var i = 0,len=fieldObj.length; i < len; i++) {
                var afieldObj = fieldObj[i];
                var columnObj = {
                    text: afieldObj.columnName,
                    width: afieldObj.width||200,
                    dataIndex: afieldObj.dataIndex,
                    hidden : afieldObj.hidden||false
                }
                if (afieldObj.dataIndex == 'text') {
                    columnObj.xtype = 'treecolumn';
                }
                fields.push(afieldObj.dataIndex);
                columns.push(columnObj);
            }
            obj.fields = fields;
            obj.columns = columns;
        }
    },
    /**
     * @private
     * @param {Object} obj 树节点过滤器的配置转换方法。
     */
    filterSet: function (obj) {
        var me = this;
        if (obj.filter) {
            var filterField = obj.filter.field || 'text';
            var triggerCfg = {
                xtype: 'trigger',
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                },
                enableKeyEvents: true,
                listeners: {
                    keyup: {buffer: 150, fn: function (field, e) {
                        if (Ext.EventObject.ESC == e.getKey() && !this.getRawValue()) {
                            field.onTriggerClick();
                        }
                        else {
                            var val = this.getRawValue();
                            var re = new RegExp('.*' + val + '.*', 'i');
                            var rootNode = me.component.getRootNode();

                            me.component.getStore().setRootNode(rootNode);
                        }
                    }}
                }
            }
            obj.tbar = [obj.filter.labelName || '过滤', triggerCfg];
        }
    },
    /**
     * 初始化树的默认事件，例如：节点选择行为。
     * @private
     * @param {Object}
     *                 obj 配置参数
     */
    initMyEvent: function (obj) {
        var me = this;
        var changeFun = function (node, checked) {
            node.expand();
            node.set('checked', checked);
            node.eachChild(function (child) {
                child.set('checked', checked);
                changeFun(child, checked);
            });
        }
        var checkedParent = function (node, checked) {
            var parentNode = node.parentNode;
            if (checked && parentNode) {
                parentNode.set('checked', checked);
                arguments.callee(parentNode, checked);
            } else if (parentNode) {
                var temp = 0;
                parentNode.eachChild(function (child) {
                    if (child.get('checked')) {
                        temp++;
                    }
                });
                if (temp == 0) {
                    parentNode.set('checked', false);
                }
            }
        }
        this.component.addListener('checkchange', function (node, checked, opts) {
            changeFun(node, checked);
            checkedParent(node, checked)
        }, this.component);

        if(obj.treeEditor){
            this.component.addListener('itemdblclick', function (com, record, item, index, e) {
                var div = Ext.fly(item).down('div');
                me.editor.startEdit(div,record.getData().text);
                me.editor.editorNode = record;
            }, this.component);
        }
    },
    requestTransfer : function(obj){
        this.component.addListener('beforeitemexpand',function(node,index,item,opts){
            if(this.configObj.serviceKey){
                return;
            }
            var nodedata = node.data;
            if(node.childNodes.length==0 && nodedata.leaf==false){
                var model = new NS.mvc.Model();
                var params ={
                    id:nodedata.id,
                    parendId : nodedata.parendId,
                    text : nodedata.text,
                    leaf : nodedata.leaf
                }
                model.callService([{key:this.serviceKey,params:params}],function(respData){
                    if(respData[this.serviceKey] instanceof Array){
                        node.appendChild(this.translateForExpendEvent(respData[this.serviceKey]));
                    }
                },this);
            }
        },this);
    },
    translateForExpendEvent :function(respArray){
        var i= 0,nodei = new Array(),len=respArray.length,newCfg={},respi=null;
        for(;i<len;i++){
            respi= respArray[i];
            newCfg = {
                pid: respi.fjdId,
                text: respi.mc,
                expanded: true,
                leaf: respi.sfyzjd==0?false:true,
                children:[]
            }
            nodei.push(newCfg);
        }
        return nodei;
    },
    itemRightClickFun: function (com, record, item, index, e) {
        e.preventDefault();
        e.stopEvent();
        Ext.create('Ext.menu.Menu', {
            width: 60,
            margin: '0 0 10 0',
            floating: true,
            items: [
                /*{
                    iconCls: 'page-add',
                    text: '新增',
                    handler: function () {
                        record.appendChild({text: '新建节点', leaf: true, checked: false, cclx: "ZY"});
                    }
                },
                {
                    iconCls: 'page-delete',
                    text: '删除',
                    handler: function () {
                        record.remove();
                    }
                },*/
                {
                    iconCls: 'page-expand',
                    text: '展开',
                    disabled: record.data.leaf,// 叶子节点不能展开
                    handler: function () {
                        var store = com.getStore();
                        var node = store.treeStore.getNodeById(record.data.id);
                        node.expand();
                    }
                },
                {
                    iconCls: 'page-collapse',
                    text: '收缩',
                    disabled: record.data.leaf,// 叶子节点不能收缩
                    handler: function () {
                        var store = com.getStore();
                        var node = store.treeStore.getNodeById(record.data.id);
                        node.collapse();
                    }
                }
            ]
        }).showAt(e.getXY());
    },
    /**
     * 获取树中所有被选择的节点的数据。
     * @return {Array} 返回数中所有被选择的节点的数据Model数组。
     */
    getChecked: function () {
        var extModels = this.component.getChecked(),
            result = new Array(),
            len = extModels.length,
            i=0;
        for (; i < len; i++) {
            result.push(extModels[i].raw);
        }
        return result;
    },
    /**
     * 获取树中最后一次被选择的节点。
     * @return {Object}
     */
    getSelectionModel : function(){
        var model = this.component.getSelectionModel().getLastSelected();
        return model==null?null:model.raw;
    },
    /**
     * 获取树中所有被选择的叶子节点的数据。
     * @return {Array} 返回数中所有被选择的节点的数据Model数组。
     */
    getCheckedLeaf: function () {
        var extModels = this.component.getChecked();
            result = new Array(),
            len = extModels.length,
            i=0;
        for (; i < len; i++) {
            if (extModels[i].getData().leaf) {
                result.push(extModels[i].raw);
            }
        }
        return result;
    },
    /**
     * 刷新树。
     * @param {Object} 格式为{data:[你的数据]}
     */
    refresh: function (data) {
        this.configObj.treeData = data;
        this.translate(this.configObj);
        this.component.getStore().setRootNode(this.configObj.root);
        /*this.component.expandAll();*/
    },
    /**
     * 返回树面板的工具栏组件。
     * @returns {Object} 工具栏。
     */
    getTbar: function () {
        var treeTbar = new NS.toolbar.Toolbar();
        treeTbar.component = this.component.getComponent('tbar');
        return treeTbar;
    },
    /**
     * 根据节点id获取节点数据。
     * @param {Object} id  节点id
     * @return {Object} 节点数据对象
     */
    getNodeDataById: function (id) {
        var store = this.component.getStore(),
            node = store.getNodeById(id);
        return node.getData();
    },
    /**
     * 全选树中所有节点。
     */
    checkAllNode : function(){
//        this.component.fireEvent("checkchange",this.component.getRootNode(),true,this.component);
        this.checkedChildren(this.component.getRootNode(),true);
    },
    checkedChildren : function(node,check){
        var childs = node.childNodes;
        var itertor = childs;// 需要迭代的节点
        //向下子节点
        if(itertor&&itertor.length>0){
            var needToIter = [];// 待被迭代的节点
            while (itertor.length != 0) {
                for (var i = 0, len = itertor.length; i < len; i++) {
                    var c = itertor[i];// 获取子节点
                    c.set('checked', check);// 设置该节点被选择
                    if (c.childNodes)
                        needToIter = needToIter.concat(c.childNodes);
                }
                itertor = needToIter;
                needToIter = [];
            }
        }
    },
    /**
     * 反选树中所有节点。
     */
    unCheckAllNode : function(){
        //this.component.fireEvent("checkchange",this.component.getRootNode(),false,this.component);
        this.checkedChildren(this.component.getRootNode(),false);
    },
    /**
     * 展开树所有节点。
     */
    expandAll : function(){
        this.component.expandAll();
    },
    /**
     * 收缩树所有节点。
     */
    collapseAll:function(){
        this.component.collapseAll();
    },
    getModifyValue : function(){
        var modifyValue = this.modifyValue,
            values = new Array();
        for(var i in modifyValue){
            values.push({
                id:i,
                text:modifyValue[i]
            })
        }
        return values;
    }
});