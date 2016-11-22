
/**
 *模型操作Service,操作$scope对象中的模型
 */
angular.module('services').factory('model',function(){
    return {
        /**
         * 将$scope中的VM对象取出来
         */
        getObject : function(scope,path){
            if(!path)return undefined;
            var split = path.split(".");
            var p = scope;
            for(var i=0;i<split.length;i++){
                var pName = split[i];
                if(i == split.length -1){
                    return p[pName];
                }else{
                    if(!p[pName])
                        return null;
                }
                p = p[pName];
            }
            return p;
        },
        /**
         * 设置$scope中的VM对象
         */
        putObject : function(scope,path,value){
            var split = path.split(".");
            var p = scope;
            for(var i=0;i<split.length;i++){
                var pName = split[i];
                if(i == split.length -1){
                    p[pName] = value;
                }else{
                    if(!p[pName])
                        p[pName] = {};
                }
                p = p[pName];
            }
            np = value;
        }
    }
});