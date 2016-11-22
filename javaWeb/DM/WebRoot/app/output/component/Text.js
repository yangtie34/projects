/*******************************************************************************
 * 基本组件：文本组件。 根据传递的文本组件配置数据创建组件。
 *
 * @argument cData = { componentType : 'popText', showMaxItems : 2, htmlstring :
 *           "上述变更数据，已同步数据节点 {0} 处，被动同步数据节点数 {1} 处，未完成同步数据节点数 {2} 处。", items : [{
 *           text : '286', params : { name : 'zhangsan', age : 12 } }, { text :
 *           '224', params : { name : 'lisi', age : 12 } }] }
 * @return
 *           @example
 */
NS.define('Output.Text', {
    constructor: function (cData) {
        this.config = cData;// 配置参数
        this.buildDom(cData);
        this.addEvent('pop');// 添加弹窗事件
    },
    /*******************************************************************
     * 创建组件的dom节点
     */
    buildDom: function (cData) {
        var me = this;
        var data = cData.displayData;
        // 创建一个组件内的父节点div，以便挂接到外界的DOM节点上，完成组件的相互组装。
        var interfaceDIV = document.createElement('DIV');
        interfaceDIV.style.width = '90%';
        interfaceDIV.className = 'opTextRoot';
        var itemsContent = data['data'];
        var htmlString = data['htmlStr'];
        // 获取要显示的最大连接数。
        var showMaxItems = this.showMaxItems = cData['showMaxItems'];
        // 正则表达式，分割htmlString字符串。
        var strs = htmlString.split(/\{[0-9]*\}/);
        // 循环数据和分割后得到的字符串数据，来创建div子节点元素。
        for (var index in strs) {
            if (index > showMaxItems) {
                break;
            }
            interfaceDIV.appendChild(document
                .createTextNode(strs[index]));
            // 如果有连接数据，则添加连接元素。
            if (index < itemsContent.length) {
                (function (index, interfaceDIV, item) {
                    var a = document.createElement('A');
                    a.href = "#";
                    a.onclick = function () {
                        var params = item.params;
                        me.fireEvent('pop', params.stsxm, params.id);// 触发弹窗事件
                        // var popWindow = new Output.PopWindow({
                        // id : params.wbid,
                        // entityName : params.stsxm
                        // });
                        return false;
                    };
                    var entityName = item.params.stsxm;
                    if (entityName != null && entityName != "") {
                        a
                            .appendChild(document
                                .createTextNode(itemsContent[index]['text']));
                        interfaceDIV.appendChild(a);
                    } else {
                        var span = document.createElement('span');
                        span.appendChild(document
                            .createTextNode(itemsContent[index]['text']));
                        interfaceDIV.appendChild(span);
                    }
                })(index, interfaceDIV, itemsContent[index]);
            }
        }

        this.myDom = interfaceDIV;
        this.createComponent(cData);
    },
    /*******************************************************************
     * 创建用以反回getLibComponent 方法反回的对象
     */
    createComponent: function (cData) {
        var me = this;
        var basic = {
            baseCls: '',
            xtype: 'container',
            columnWidth: 0.65,
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.myDom);
                    // var div = document.createElement('DIV');
                    // div.style.width = '20%';
                    // this.el.appendChild(div);
                }
            }
        };
        US.apply(basic, {});
        this.textcontainer = new Ext.container.Container(basic);
        this.createXxxxContainer();
        this.component = new Ext.container.Container({
            baseCls: '',
            layout: 'column',
            items: [
                this.textcontainer,
                this.xxxxcontainer
            ]
        });
    },
    /***
     * 创建详细信息容器
     */
    createXxxxContainer: function () {
        this.xxxxcontainer = new Ext.container.Container({
            baseCls: 'opTextRoot_xxxx',
            columnWidth: 0.35,
            listeners: {
                'afterrender': function () {
                    // 创建一个“点击查看详细信息”链接！
//									var xxxx = document.createElement('A');
//									xxxx.href = "#";
//									Ext.fly(xxxx).on('click',function(event){
//									     event.stopEvent();
//									     alert('查看详细信息');
//									});
                    // 创建详细信息节点
                    var span = document.createElement('SPAN');
                    var textNode = document
                        .createTextNode('点击查看详细信息');
//									span.className = 'opTextRoot_xxxx';
                    span.appendChild(textNode);
//									xxxx.appendChild(span);
                    this.el.appendChild(span);
                }
            }
        });
    },
    /*******************************************************************
     * 获取并返回当前组件id的状态属性。
     */
    getStatus: function () {
    },
    /*******************************************************************
     * 刷新组件。
     */
    loadData: function (cData) {

    },
    /**
     * 获取组件类型
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    /*******************************************************************
     * 获取用于和底层类库交互的组件对象。
     */
    getLibComponent: function () {
        return this.component;
    },
    /*******************************************************************
     * 展示最大items项目。
     */
    showMaxItems: function () {
        alert('展示最大的items项目');
    },
    /*******************************************************************
     * 将创建的原生dom节点渲染到制定的dom节点上。
     *
     * @param {}
     *            id 该参数可以是dom节点的id，也可以是dom对象。
     */
    render: function (id) {
        this.component.render(id);
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
        // C.setWidth(width);
        // C.setHeight(height);
    }
});