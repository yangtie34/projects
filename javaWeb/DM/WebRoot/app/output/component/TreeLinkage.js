/*******************************************************************************
 * 定义树形联动组件
 */
NS.define('Output.TreeLinkage', {
    /*******************************************************************
     * 初始化构造函数
     */
    constructor: function () {
        this.initData.apply(this, arguments);
        this.initComponent();
        this.addEvents('linkage');
    },
    /*******************************************************************
     * 初始化数据
     */
    initData: function (config) {
        this.config = config;
        US.apply(this, config);
        this.root = this.displayData.menuTree;
        this.root.text = '全校';
        this.createNodeMap(this.root);// 创建节点hash表
    },
    /*******************************************************************
     * 初始化组件
     *
     * @return {}
     */
    initComponent: function () {
        this.initDom();
        this.createComponent();
    },
    /*******************************************************************
     * 获取用于和底层类库进行交互的组件
     */
    getLibComponent: function () {
        return this.component;
    },
    /***
     * 获取组件类型
     * @return {}
     */
    getComponentType: function () {
        return this.componentType;
    },
    /***
     * 获取组件状态
     * @return
     */
    getStatus: function () {
        return this.status;
    },
    /*******************************************************************
     * 创建用于和底层类库进行交互的组件
     */
    createComponent: function () {
        var me = this;
        var basic = {
            baseCls: '',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.pdiv);
                }
            }
        };
        US.apply(basic, {});
        this.component = new Ext.container.Container(basic);
    },
    /***
     * 初始化dom
     * @param {} root
     */
    initDom: function () {
        this.spanArray = [];
        this.pdiv = document.createElement('DIV');
        this.titleDiv = document.createElement('DIV');
        this.titleDiv.appendChild(this.createIndexSpan(this.root));
        this.pdiv.appendChild(this.titleDiv);
    },
    // 移除标题容器中的span
    removeSpan: function (nodeid) {
        if (this.root.id == nodeid && this.spanArray.length == 1)
            return;
        var flag = false;
        var bak = []
        for (var i = 0; i < this.spanArray.length; i++) {
            var obj = this.spanArray[i];
            if (!flag) {
                bak.push(obj);
                if (obj.id == nodeid) {
                    flag = true;
                    continue;
                }
            }
            if (flag) {
                this.titleDiv.removeChild(obj.span);
            }
        }
        this.spanArray = bak;
    },

    // 创建索引span
    createIndexSpan: function (node) {

        var me = this;
        return (function (node) {
            var span = document.createElement('span');
            var obj = {};
            obj.id = node.id;
            obj.span = span;
            me.spanArray.push(obj);

            var text1 = document.createTextNode('>>');
            var text2 = document.createTextNode(node.text);
            var a = document.createElement('a');
            a.href = '#';
            a.appendChild(text2);
            if (node !== me.root)
                span.appendChild(text1);
            span.appendChild(a);
            a.onclick = function () {
                me.status = {nodeId: node.id};
                me.fireEvent('linkage');
                me.removeSpan(node.id);
                if (node.children.length != 0) {
                    me.removeChildDiv();
                    var cdiv = me.cdiv = me.createDiv(node.children);
                    me.pdiv.appendChild(cdiv);

                }
                return false;
            };
            return span;
        })(node);
    },
    // 创建A节点
    createA: function (node) {
        var me = this;
        return (function (node, me) {
            var a = document.createElement('A');
            a.href = '#';
            a.appendChild(document.createTextNode(node['text']));
            a.onclick = function () {
                if (node.children.length != 0) {
                    me.status = {nodeId: node.id};
                    me.fireEvent('linkage');
                    me.removeChildDiv();
                    var cdiv = me.cdiv = me.createDiv(node.children);
                    me.titleDiv.appendChild(me.createIndexSpan(node));
                    me.pdiv.appendChild(me.cdiv);
                }
                return false;
            };
            return a;
        })(node, me);
    },
    // 创建子div
    createDiv: function (nodes) {
        var div = document.createElement('DIV');
        for (var i = 0, len = nodes.length; i < len; i++) {
            var node = nodes[i];
            div.appendChild(this.createA(node));
            div.appendChild(this.createText());
        }
        return div;
    },
    // 删除DIV子节点
    removeChildDiv: function () {
        if (this.cdiv)
            this.pdiv.removeChild(this.cdiv);
    },

    // 创建空白text节点
    createText: function () {
        return document.createTextNode("   ");
    },
    // 创建节点map,以id为键，节点为值
    createNodeMap: function (root) {
        this.nodeMap = {};
        this.nodeMap[root.id] = root;
        var iterator = root.children;
        var biter = [];
        while (iterator.length != 0) {
            for (var i = 0, len = iterator.length; i < len; i++) {
                var node = iterator[i];
                this.nodeMap[node.id] = node;
                biter = biter.concat(node.children);
            }
            iterator = biter;
            biter = [];
        }
    }
});