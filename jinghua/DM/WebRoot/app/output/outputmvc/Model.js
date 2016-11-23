NS.define('Output.Model',{
   /***
    * 启动函数
    */
   constructor : function(){
       
   },
   /***
    * 请求页面以及组件数据
    */
   pageRequest : function(params,callback,scope){
   	   var array = new Array();
       array.push('pageInitData,commonEntrance,"output":'+JSON.stringify(params));
       var obj = US.CommonUtil.getQueryParams(array);
       this.getProxy().sendRequest(obj,callback,scope);
   },
   /***
    * 弹窗请求表头数据
    */
   gridHeaderRequest : function(entityName,callback){
   	   var entityKV = '"entityName":\"' + entityName + "\"";
       var array = new Array();
       array.push('queryAddFormField,base_queryForAddByEntityName,'+entityKV); 
       var obj = US.CommonUtil.getQueryParams(array);
       this.getProxy().sendRequest(obj,callback);
   },
   /***
    * 反回Connection 连接类单例
    * @return {}
    */
   getProxy : function(){
      return  Output.DataRequestProxy;
   }
});