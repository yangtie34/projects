NS.ns('NS.util');
/**
 *
 * 文件加载工具类
 *
 * @author wangyt
 * @class  NS.util.FileLoader
 */
NS.define('NS.util.FileLoader',{
    singleton : true,
    constructor : function(){
        this.connection = new Ext.data.Connection();
    },
    /**
     * 加载文件
     * @private
     * @param {String} url 文件路径
     * @param {Function} fun 回调函数
     * @param {Object} scope 作用域
     *
     *          NS.util.FileLoader.loadFile('app/pages/myfile',function(text,response){
     *                  alert(text);//是你加载的文件
     *
     *          });
     */
    loadFile : function(url,fun,scope){
        this.connection.request({
            url : url,
            success : function(response){
                fun.call(scope,response.responseText,response);
            }
        });
    },
    /**
     * 加载样式表
     * @private
     * @param {String} url 样式表路径
     *
     *      NS.util.FileLoader.loadCss('app/pages/mycss.css');
     */
    loadCss : function(url){
        var head = document.getElementsByTagName("head")[0];
        var style = document.createElement("link");
        style.rel = "stylesheet";
        style.type = "text/css";
        style.href = url;
        head.appendChild(style);
    }
});
(function(){
    var alias = NS.Function.alias;
    /**
     * @member NS
     * @method loadCss
     * @inheritdoc NS.util.FileLoader#loadCss
     */
    NS.loadCss = alias(NS.util.FileLoader,'loadCss');
    /**
     * @member NS
     * @method loadFile
     * @inheritdoc NS.util.FileLoader#loadCss
     */
    NS.loadFile = alias(NS.util.FileLoader,'loadFile');
})();