/*******************************************************************************
 * 瀹氫箟瀵艰埅鑿滃崟鏍戝舰缁勪欢
 */
NS.define('Output.NavigationTreeLinkage', {
    /*******************************************************************
     * 鍒濆鍖栨瀯閫犲嚱鏁�
     */
    constructor: function (root) {
        this.initData();
        this.initComponent();
    },
    /***
     * 鍒濆鍖栨暟鎹�
     * @param {} root
     */
    initData: function (root) {
        this.root = root;
        this.createNodeMap(root);
        this.spanArray = [];
    },
    /***
     * 鍒濆鍖栫粍浠�
     */
    initComponent: function () {
        this.pdiv = document.createElement('DIV');
        this.titleDiv = document.createElement('DIV');
        this.titleDiv.appendChild(this.createIndexSpan(root));
        this.pdiv.appendChild(this.titleDiv);
        this.createComponent();
    },
    /***
     * 鑾峰彇鐢ㄤ簬鍜屽簳灞傜被搴撹繘琛屼氦浜掔殑缁勪欢
     * @return {}
     */
    getLibComponent: function () {
        return this.component;
    },
    /***
     * 鍒涘缓缁勪欢
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: '',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.pdiv);
                }
            }
        });

    },
    getDom: function () {
        return this.pdiv;
    },
    /***
     * 绉婚櫎鏍囬瀹瑰櫒涓殑瀵艰埅span
     * @param {} nodeid
     */
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
    /*****鍒涘缓绱㈠紩span***
     * 闇�鍒涘缓鐨勭储寮昻ode
     * @param {} node
     * @return {}
     */
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
    /****鍒涘缓A鑺傜偣**
     * 闇�鍒涘缓灞曞紑div鐨刵ode鑺傜偣
     * @param {} node
     * @return {}
     */
    createA: function (node) {
        var me = this;
        return (function (node) {
            var a = document.createElement('A');
            a.href = '#';
            a.appendChild(document.createTextNode(node['text']));
            a.onclick = function () {
                if (node.children.length != 0) {
                    me.removeChildDiv();
                    var cdiv = me.cdiv = me.createDiv(node.children);
                    me.titleDiv.appendChild(me.createIndexSpan(node));
                    me.pdiv.appendChild(me.cdiv);
                }
                return false;
            };
            return a;
        })(node);
    },
    /****鍒涘缓瀛恉iv***
     * 鍒涘缓node鑺傜偣鐨勫瓙鑺傜偣灞曞紑鍖哄煙
     * @param {} nodes
     * @return {}
     */
    createDiv: function (nodes) {
        var div = document.createElement('DIV');
        for (var i = 0, len = nodes.length; i < len; i++) {
            var node = nodes[i];
            div.appendChild(this.createA(node));
            div.appendChild(this.createText());
        }
        return div;
    },
    /****鍒犻櫎DIV瀛愯妭鐐�**
     * 鍒犻櫎灞曞紑瀛恉iv
     */
    removeChildDiv: function () {
        if (this.cdiv)
            this.pdiv.removeChild(this.cdiv);
    },

    /****鍒涘缓绌虹櫧text鑺傜偣**
     *
     * @return {}
     */
    createText: function () {
        return document.createTextNode("   ");
    },
    //鍒涘缓鑺傜偣map,浠d涓洪敭锛岃妭鐐逛负鍊�
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