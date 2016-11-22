/*******************************************************************************
 * 联动组件。
 *
 * @param {}
 *            cData={ type:'linkage', items:[ {'全部 ',' 本日',' 本周',' 本月',' 本年','
 *            自定義' text:'全部', params:{ beginDate:'', endDate:"" } },{ text:'本日',
 *            params:{ } },{ text:'本周', params:{ } } ] }
 *
 */
NS.define('Output.DateLinkage', {
    /*******************************************************************
     * 构造函数
     */
    constructor: function (config) {
        this.config = config;// 将参数设置为内部变量
        US.apply(this, config);
        this.buildDom();
    },
    buildDom: function () {
        // 创建一个组件内的父节点div，以便挂接到外界的DOM节点上，完成组件的相互组装。
        var interfaceDIV = document.createElement('DIV');
        interfaceDIV.className = 'gn-space';
        var itemsContent = this.config.displayData.data['idos'];
        for (var index in itemsContent) {
            (function (_index) {
                var aLink = document.createElement('A');
                aLink.href = '#';
                aLink.onclick = function () {
                    var params = itemsContent[_index].params;
                    /*将当前被点击连接的，文本和参数缓存起来*/
                    this.params = params;
                    this.text = itemsContent[_index].text;
                    /*触发事件*/
                    me.fireEvent('datelinkage', this.config.componentId);
                }
                aLink.appendChild(document.createTextNode(itemsContent[_index].text));
                interfaceDIV.appendChild(aLink);
            })(index);
        }
        this.myDom = interfaceDIV;
        this.createComponent();
    },
    /***
     * 创建用以反回getLibComponent 方法反回的对象
     **/
    createComponent: function () {
        var me = this;
        var basic = {
            baseCls: '',
            xtype: 'container',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.myDom);
                }
            }
        };
        US.apply(basic, {});
        this.component = new Ext.container.Container(basic);
    },
    /*******************************************************************
     * 获取并返回当前组件id的状态属性。
     */
    getStatus: function () {
        var status = {},
            value = new Array();
        value.push(/*当前被选中的值*/);
        status['statusText'] = this.text;
        ;
        status['statusParams'] = this.params;
        return status;
    },
    /*******************************************************************
     * 刷新组件。
     */
    loadData: function (cData) {

    },
    /*******************************************************************
     * 获取用于和底层类库交互的组件对象。
     */
    getLibComponent: function () {
        return this.component;
    },
    /*******************************************************************
     * 将创建的原生dom节点渲染到制定的dom节点上。
     *
     * @param {}
     *            id 该参数可以是dom节点的id，也可以是dom对象。
     */
    renderTo: function (id) {
        if (id.nodeType = 1) {
            id.appendChild(this.myDom);
        } else {
            document.getElementById(id).appendChild(this.myDom);
        }
    },
    /**
     *获取组件类型
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
//				C.setWidth(width);
//				C.setHeight(height);
    }
});
