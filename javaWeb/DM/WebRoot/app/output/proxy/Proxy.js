Output = {};
/**
 * 数据请求代理--工具类
 * @calss US.DataRequestProxy
 */
Output.DataRequestProxy = {
	 /****
	  * 
	  **/
	 setRequestParam:function(paramArray,callBack){
	     this.paramArray = paramArray;
		 var me = this;
		 me.requestParamAssemble();
         me.sendRequest(me.paramAssembleArray,callBack);
	 },
      /**
       * 请求参数组装--传来的格式
	    items:[{
	            componentId:id,
				wd:obj,//string
				fw:obj,
				zb:obj
	          },{
			    comoponetId:id,
				wd:obj,
				fw:obb, 
				zb:obj
			  }]
       */
      requestParamAssemble:function(){
		  var me = this,pa = me.paramsArray,_array=[];
		  for(var i=0,len=pa.length;i<len;i++){
		      var newArray = [],obj = {},id,serviceName;
			  id = pa.componentId;//得到组件id
			  serviceName = pa.serviceName||"baseChartService";//默认走统一的chartSevice路径
			  for(var j in pa){
                   obj[j] = pa[j]; 
			  }
			  newArray.push(id,serviceName,"params="+obj);//obj：拼接查询参数->顺带有id属性，另外数组首位id是组件id 这是为了返回数据时辨别是哪个组件的数据
			  _array.push(newArray);
		  }
		  this.paramAssembleArray = _array;//返回的是转换后的数据
      }, 
      /**
       * 发送请求--异步请求
	   * responseParamList:需要的格式-chart_Service是为了分开统一的action  serviceName是单独走的service-默认走的是统一的是为了单独走而预留。 params是统计参数
	   * responseNmae,chart_Service,params=[[componentId1,serviceName,params={zb:zbString,wd:wdString,fw:fwString}],[componentId1,serviceName,params={zb:zbString,fw:fwString,wd:wdString}]]
       */
      sendRequest:function(params,callback,scope){
          var me = this;
          var conn = new US.Connection;//这是原先框架内连接方法
          params.success = function(response){
			     var data = Ext.JSON.decode(response.responseText, true);
//				 callback(JSONDATA);//数据返回的数据格式：
			     callback.call(scope||this,data);
			 };
          params.failure = function(response){
			     var data = Ext.JSON.decode(response.responseText, true);
//				 callback(JSONDATA);//数据返回的数据格式：
			     callback(data);
			 };
          conn.request(params);
      }
 };
(function(){
    var alias = NS.Function.alias;
    US = {};
    /**
     * @member NS
     * @method namespace
     * @inheritdoc NS.ClassManager#namespace
     */
    US.apply = alias(NS,'apply');
})();