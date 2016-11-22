/**
 * @class NS.dom.Element
 * @extends NS.Base
 *    DOM操作对象的封装
 */
NS.define('NS.dom.Element', {
    constructor:function (HtmlElement) {
        this.element = new Ext.dom.Element(HtmlElement);
        this.dom = this.element.dom;
    },
    /**
     * 设置Ext.dom.Element的包装对象
     * @private
     */
    set:function (element) {
        this.element = element;
        this.dom = element.dom;
        return this;
    },
    /**
     * 为dom元素添加class类
     * @param className
     */
    addCls:function (className) {
        this.element.addCls(className)
        return this;
    },
    /**
     * 移除dom元素所带的Class类
     */
    removeCls:function (cls) {
        this.element.removeCls(cls);
        return this;
    },
    /**
     * 获取innerHtml 串
     * @return {String}
     */
    getHTML:function () {
        return this.element.getHTML();
    },
    /**
     * 设置dom元素的innerHtml 串
     * @return {String}
     */
    setHTML:function (html) {
        this.element.setHTML(html);
        return this;
    },
    /**
     * 获取高度
     * @return {Number}
     */
    getHeight:function () {
        return this.element.getHeight();
    },
    /**
     * 获取宽度
     * @return {Number}
     */
    getWidth:function () {
        return this.element.getWidth();
    },
    /**
     * 获取Left位置
     * @return {Number}
     */
    getLeft:function () {
        return this.element.getLeft();
    },
    /**
     * 获取Top位置
     * @return {Number}
     */
    getTop:function () {
        return this.element.getTop();
    },
    /**
     * 设置样式
     * @param {String} property 属性名
     * @param {String} value 值
     */
    setStyle:function (property, value) {
        this.element.setStyle(property, value);
        return this;
    },
    /**
     * 根据传入的键，获取dom元素的属性值
     * @return {String} value attribute value
     */
    getAttribute:function (key) {
        return this.element.getAttribute(key);
    },
    /**
     * 返回dom元素的value属性的值
     */
    getValue : function(){
        return this.element.getValue();
    },
    /**
     * 获取dom元素的XY值
     * @return {Array} [x,y]
     */
    getXY : function(){
        return this.element.getXY();
    },
    /**
     * 获取dom元素的X轴上的值
     * @return {Number}
     */
    getX : function(){
        return this.element.getX();
    },
    /**
     * 获取dom元素的Y轴上的值
     * @return {Number}
     */
    getY : function(){
        return this.element.getY();
    },
    /**
     * 设置dom元素的XY值
     * @param {Array} pos 位置数组[x,y]
     * @param {Boolean} 是否以动画形式显示
     */
    setXY : function(pos,flag){
        this.element.setXY(pos,flag);
        return this;
    },
    /**
     * 根据查询规则，查询对应的DOM元素集合
     * @param {String} selector
     * @return {NS.dom.Element}
     */
    query : function(selector){
        return new NS.dom.Element(this.element.query(selector,true));
    },
    /**
     * 根据CSS选择器,查询子元素
     * @param {String} selector
     * @return {NS.dom.Element}
     */
    child : function(selector){
        return new NS.dom.Element(this.element.child(selector));
    }
});
(function () {
    var instance = new NS.dom.Element();
    /**
     *   把dom元素进行包装,获取dom对象的包装类,该包装类是单例的，也就是说，
     *   该包装类中包装的dom对象是可变的，因此，该方法获取的NS.dom.Element对象
     *   比较适合用于一些dom节点的一次性操作，获取的dom对象在下次调用NS.fly之前是指向同一个dom引用
     * @param {String/HtmlElement} el
     * @return {NS.dom.Element}
     * @member NS
     * @method fly
     */
    NS.fly = function (dom) {
        instance.set(Ext.fly(dom));
        return instance;
    }
    /**
     * 通过提供的id或者htmlelement元素，获取封装的NS.dom.Element对象
     * @param {String/HtmlElement} el
     * @return {NS.dom.Element}
     * @member NS
     * @method get
     */
    NS.get = function (el) {
        var element = new NS.dom.Element();
        var extEl = Ext.get(el);
        element.set(extEl);
        return element;
    }
    /**
     * @param {String/HtmlElement} el
     * @return {NS.dom.Element}
     * @member NS
     * @method getBody
     */
    NS.getBody = function(){
        var element = new NS.dom.Element();
        var extel = Ext.getBody();
        element.set(extel);
        return element;
    };
    /**
     * @return {HTMLElement[]}
     * @member NS
     * @method query
     */
    NS.query = function(path,root){
       return Ext.query(path,root);
    };
})();
