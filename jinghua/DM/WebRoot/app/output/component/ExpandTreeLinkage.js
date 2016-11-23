/*******************************************************************************
 * 瀹氫箟鍚戜笅灞曞紑鏍戝瀷缁勪欢
 */
NS.define('Output.ExpandTreeLinkage', {
    /*******************************************************************
     * 鍒濆鍖栨瀯閫犲嚱鏁�
     */
    constructor: function () {
        this.initData.apply(this, arguments);
        this.initComponent();
        this.addEvents('linkage');
    },
    /*****************************
     * 鍒濆鍖栨暟鎹�
     */
    initData: function (root) {
        this.root = root;
        this.createNodeMap(root);
    },
    /***
     * 鍒濆鍖栫粍浠�
     * @return {}
     */
    initComponent: function () {
        var div = this.div = this.createDiv([root]);
        this.createComponent();
    },
    /***
     * 鑾峰彇鐢ㄤ簬鍜屽簳灞傜被搴撹繘琛屼氦浜掔殑缁勪欢
     */
    getLibComponent: function () {
        return this.component;
    },
    /***
     * 鍒涘缓鐢ㄤ簬鍜屽簳灞傜被搴撹繘琛屼氦浜掔殑缁勪欢
     */
    createComponent: function () {
        var me = this;
        var basic = {
            baseCls: '',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.div);
                }
            }
        };
        US.apply(basic, {});
        this.component = new Ext.container.Container(basic);
    },
    /***
     * 鏍规嵁鏍戣妭鐐瑰垱寤篈鑺傜偣
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
                var div = a.parentNode;
                if (!node.expand) {// 濡傛灉璇ヨ妭鐐规病鏈夊睍寮�
                    me.removeChildDiv(div);
                    div.appendChild(me.createDiv(node.children || []));
                } else {
                    me.setChildExpand(node);// 璁剧疆璇ヨ妭鐐规墍鏈夊瓙鑺傜偣涓�
                }
                node.expand = true;
                var pnode = me.nodeMap[node.pid];
                me.setNodeExpand(pnode, node);
                var params = {};
                me.fireEvent('linkage', {"zzjg": node.id});
                return false;
            };
            return a;
        })(node);
    },
    /***
     * 鐐瑰嚮鑺傜偣锛屽垱寤哄瓙鑺傜偣鐨刣iv
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
    /*** 鍒犻櫎DIV瀛愯妭鐐�
     * 閫氳繃鐐瑰嚮鐨勮妭鐐癸紝鍒犻櫎璇ヨ妭鐐笵IV瀛愯妭鐐�
     * @param {} node
     */
    removeChildDiv: function (node) {
        var childNodes = node.childNodes;
        for (var i = 0, len = childNodes.length; i < len; i++) {
            var cnode = childNodes[i];
            if (cnode.tagName == 'DIV')
                node.removeChild(cnode);
        }
    },
    /***璁剧疆鐖惰妭鐐圭殑鍏朵粬瀛愯妭鐐圭殑灞曞紑涓篺alse
     *
     * @param {} pnode  鐖惰妭鐐�
     * @param {} node   涓嶉渶瑕佽璁剧疆灞曞紑涓篺alse鐨勮妭鐐�
     */
    setNodeExpand: function (pnode, node) {
        for (var i = 0, len = pnode.children.length; i < len; i++) {
            var cnode = pnode.children[i];
            if (cnode != node) {
                cnode.expand = false;
                this.setChildExpand(cnode);
            }
        }
    },
    /***璁剧疆鎵�湁瀛愯妭鐐瑰睍寮�负false
     *
     * @param {} node  闇�琚缃甧xpand 涓篺alse鐨勮妭鐐�
     */
    setChildExpand: function (node) {
        var iterator = node.children;
        var biter = [];
        while (iterator.length != 0) {
            for (var i = 0, len = iterator.length; i < len; i++) {
                var node = iterator[i];
                biter = biter.concat(node.children);
                node.expand = false;
            }
            iterator = biter;
            biter = [];
        }
    },
    /***鍒涘缓绌虹櫧text鑺傜偣
     * @return {}
     */
    createText: function () {
        return document.createTextNode("   ");
    },
    // 鍒涘缓鑺傜偣map,浠d涓洪敭锛岃妭鐐逛负鍊�
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