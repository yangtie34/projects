/**
 * 其他要混入的功能
 */
NS.define('System.minx.Utils',{
    /**
     * 提供一个在框架上打开新页面的功能
     * @param classname 需要打开的页面对应的类名
     * @param classInitConfig 类初始化参数
     * @param closeIfExist 如果存在是否关闭,默认为false
     */
    openPage : function(classname,classInitConfig,closeIfExist){
        var page = this.pages[classname];
        if(closeIfExist){
            if(page){
               this.center.remove(page,true);
               delete this.pages[classname];
            }
            var node = this.getNodeByClassName(classname);
            if(!node)return;
            this.addPage(node,classname,classInitConfig);
        }else{
            if(page){
               this.center.setActiveTab(page);
            }else{
               var node = this.getNodeByClassName(classname);
               if(!node)return;
               this.addPage(node,classname,classInitConfig);
            }
        }
    },
    /**
     *var params = {
     *     action : 'loadFileAction',
     *     params : {
     *         name : '张三',
     *         age : 51
     *     }
     *}
     * 该方法不支持参数的多层嵌套，只支持一层
     */
    downLoad : function(config){
        var i,name,len,
            downLoadFrame = document.getElementById('system_for_download'),
            action = config.action||"",
            params = config.params,
            urlP = "",
            nameArray = [];
        for(i in params){
            nameArray.push(i);
        }
        for(i=0,len = nameArray.length;i<len;i++){
            name = nameArray[i];
            if(NS.isString(params[name])){
                urlP +=name+"="+params[name];
            }else{
                urlP +=name+"="+NS.encode(params[name]);
            }
            if(i != len-1){
               urlP += "&";
            }
        }
        urlP = action+"?"+urlP;
        downLoadFrame.src = urlP;
    }
});