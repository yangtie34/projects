
/**
 * 定义针对service的封装,为了和controller保持定义行为的一致性
 */
custom.service = (function(){
    /**
     * 获取实际的控制器
     * @param array
     * @returns {*}
     */
    function getService(array){
        return array[array.length-1];
    }
    /**
     * 获取注入的对象
     * @param array
     * @returns {Array|*}
     */
    function getInjects(array){
        array.splice(array.length-1,1);
        return array;
    }
    /**
     * 生成service的函数
     */
    return function(app,service,array){
        var appModule = angular.module(app);//获取当前模块,当前模块一定是被angular.module(app,[]);完成定义的
        if(angular.isArray(array)){
        	var serviceFn = getService(array);//获取service方法
            var injects = getInjects(array);//获取待注入服务
            var fn = function(){//代理函数
                return serviceFn.apply(this,arguments);
            }
            injects.push(fn);
            appModule.factory(service,injects);
        }else if(angular.isFunction(array)){
            var fn = function(){//代理函数
                return array.apply(this,arguments);
            }
            appModule.factory(service,fn);
        }
    }
})();