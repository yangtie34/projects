/*******************************************************************************
 * @class US.CommonUtil
 * @description 通用js工具类
 */
US.CommonUtil = {
	/**
	 * 重写函数,建议只重写生成的对象的函数，而不是类的函数
	 * 
	 * @param obj
	 *            需要重写函数的类
	 * @param initalFun
	 *            需要重写的函数
	 * @param reWriteFun
	 *            替代的函数
	 */
	applyFunction : function(obj, initalFun, reWriteFun) {
		obj[initalFun] = reWriteFun;
	},
	/**
	 * 合并2个js对象
	 * 
	 * @param obj1
	 *            合并到的对象
	 * @param obj2
	 *            被合并的对象
	 */
	mergeObject : function(obj1, obj2) {
		var ev = eval(obj2);
		for (var field in ev) {
			obj1[field] = ev[field];
		}
	},
	/**
	 * 判断函数是否存在，如果存在，则执行(目前暂只能执行一个参数)
	 * 
	 * @param fun
	 *            需要执行的函数
	 * @param args
	 *            函数执行所需要的参数
	 */
	judgeExist : function(fun, args) {
		if (fun) {
			fun(args);
		}
	},
	/**
	 * @description 获取查询参数
	 * @param para
	 *            传递的数组参数
	 */
	generateParams : function(para) {
		var paramy = "{";
		for (var i = 0; i < para.length; i++) {
			var obj = para[i].toString().split(",");
			// 修改组装json数据的先后顺序，把组件名放在第一个位置，请求名放在第二个参数里 modify2011年03月21日修改
			paramy += ("\"" + obj[0] + "\":{\"request\":\"" + obj[1] + "\",\"params\":{");
			for (var j = 2; j < obj.length; j++) {
				paramy += obj[j];
				if ((j + 1) != obj.length) {
					paramy += ",";
				}
			}
			paramy += "}}";
			if ((i + 1) != para.length) {
				paramy += ",";
			}
		}
		paramy += "}";
		return paramy;
	},
	baseUrl : 'baseAction!queryByComponents.action',
	/**
	 * @description 获取请求参数
	 * @param para 参数数组
	 * */
	getParams:function(para){
		var params = {components:US.CommonUtil.generateParams(para)};
		return params;
	},
	/**
	 * @description 获取向后台发送请求的参数---注释 --一般用于同步请求数据使用,如果使用异步，请参考参数对生成的参数做另外处理
	 * @param para 参数数组
	 * */
	getQueryParams:function(para){
		var me = this;
	    var obj = {
	       method:'post',
	       params:me.getParams(para)
	    };
	    return obj;
	},
	/**
	 * @description 获取为grid组装的proxy数据
	 * @param queryTableContentParam 请求的参数
	 * @param param JSON对象
	 * @return proxy  store的proxy属性
	 * */
	getProxyForGrid:function(queryTableContentParam,params){
	   var url = this.baseUrl + "?components="
				+ this.generateParams([queryTableContentParam])
	   var proxy = {
				type : 'ajax',
				url : url,// url
				extraParams : params,
				reader : {
					type : 'json',
					root : 'data',// 读取的主要数据 键
					totalProperty : 'count'
					// 数据总量
				}
			};
	   return proxy;
	},
	getProxyForTree : function(treeparams,params){
		var url = this.baseUrl + "?components="
		+ this.generateParams([treeparams])
		var proxy = {
				type : 'ajax',
				url : url,// url
				extraParams : params,
				reader : {
					type : 'json',
					root : 'root'// 读取的主要数据 键
					// 数据总量
				}
			};
	},
	/**
	 * @description 根据参数数组获取同步数据---注---主要用于同步请求数据
	 * @param para 参数数组
	 * @return jsonData 同步请求的获取的数据
	 * */
	getSyncData : function(para){
	   var conn = new US.Connection();
	   var jsonData = conn.syncGetData(this.getQueryParams(para));
	   return jsonData;
	},
	/**
	 * @description 根据传入参数，生成转换后的所有数据
	 * @param para 
	 * @param proxy grid的代理
	 * @return 返回转换后的数据
	 * */
	getTranData : function(jsonData,proxy){
	    jsonData.proxy = proxy;
	    var tranData = new ModelOne.GridDataTran(jsonData).getGridData();
	    return tranData;
	},
	/***
	 * 将dm转换为id
	 * @param {} sx
	 * @param {} v
	 */
	changeDmToId : function(data,sx,v){
		 var tranData = data; 
	     var CS = tranData.componentsData;
	     for(var i=0;i<CS.length;i++){
	         var C = CS[i];
	         if(C.dataIndex == sx){
	         	var data = C.data;
	         	for(var j=0;j<data.length;j++){
	         	    var D = data[j];
	         	    if(D.dm == v) return D.id;
	         	}
	         }
	     }
	     return 0;
	},
	//
	getTranDataFromParam : function(para,proxy){
       	var tranData = this.getTranData(Conn.syncGetData(this.getQueryParams(para)),proxy);
       	return tranData;
	}
};