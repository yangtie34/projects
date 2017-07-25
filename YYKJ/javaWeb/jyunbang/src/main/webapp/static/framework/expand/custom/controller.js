
/**
 * 定义针对controller的封装
 */
custom.controller = (function(){
    /**
     * 获取实际的控制器
     * @param array
     * @returns {*}
     */
    function getController(array){
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
     * 获取scope对象在数组中的index
     * @param array
     * @returns {number}
     */
    function getScopeIndex(array){
        for(var i= 0,len = array.length;i<len;i++){
            if(angular.isString(array[i]) && array[i] == "$scope"){
                return i;
            }
        }
        return -1;
    }

    /**
     * 生成控制器的函数
     */
    return function(app,controller,array){
        if(!angular.isArray(array)){
            throw new Error("controller传入的第三个参数必须是一个数组!");
        }
        var appModule = angular.module(app);//获取当前模块,当前模块一定是被angular.module(app,[]);完成定义的
        var scopeIndex = getScopeIndex(array);//获取$scope对象的索引
        var controllerFn = getController(array);//获取控制器方法
        var injects = getInjects(array);//获取待注入服务

        var fn = function(){
            var $scope = arguments[scopeIndex];
            controllerFn.apply(this,arguments);
            //针对scope中的事件函数做代理处理，权限控制留的入口
            
        }
        injects.push(fn);
        appModule.controller(controller,injects);
    }
})();