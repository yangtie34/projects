Ext.define('Exp.util.ResourceLoader',{
    singleton : true,
    /**
     * 加载文件
     * @param {String} path 文件路径
     * @param {Function} callback 回调函数
     * @param {Object} scope
     */
    loadFile : function(path,callback,scope){
        Ext.Ajax.request({
            url: path,
            success: function(response){
                var text = response.responseText;
                callback.call(scope||window,text);
            }
        });
    },
    /**
     * 加载CSS
     * @param {String} path css文件路径
     */
    loadCss : function(path){
        var head = document.getElementsByTagName("head")[0];
        var style = document.createElement("link");
        style.rel = "stylesheet";
        style.type = "text/css";
        style.href = path;
        head.appendChild(style);
    }
},function(loader){
    Exp.loadFile = Ext.Function.alias(loader,'loadFile');
    Exp.loadCss = Ext.Function.alias(loader,'loadCss');
});
