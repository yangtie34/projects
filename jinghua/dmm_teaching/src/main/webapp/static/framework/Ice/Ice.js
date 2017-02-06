/**
 * 组件库 Ice
 */
var Ice = Ice || {};
(function(Ice){
	var objectPrototype = Object.prototype,
    	toString = objectPrototype.toString,
    	enumerables = [//'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable',
                   'valueOf', 'toLocaleString', 'toString', 'constructor'];
	for (i in { toString: 1 }) {
        enumerables = null;
    }
	
	/**
	 * 深度赋值
	 * @param object 原数据
	 * @param config copy数据
	 * @returns object
	 */
	Ice.apply = function(object, config){
		if(config instanceof Array && object.length == config.length){
			// 数组：循环递归处理
			for(var i=0,len=config.length; i<len; i++){
				arguments.callee(object[i], config[i]);
			}
		}else if(config instanceof Object){
			for(key in config){
				var value = config[key];
				// 不存在 或 简单数据类型直接赋值
				if(!object[key] || value==null || typeof value == 'string' || typeof value == 'number' || typeof value == 'boolean' || typeof value == 'function'
					|| (value instanceof Array && (typeof value[0] == 'string' || typeof value[0] == 'number' || typeof value == 'boolean')) ){
					object[key] = value;
				}else{
					// 对象：递归处理
					arguments.callee(object[key], config[key]);
				}
			}
		}
		return object;
	};
	Ice.applyDept = Ice.apply;
	
	
})(Ice);