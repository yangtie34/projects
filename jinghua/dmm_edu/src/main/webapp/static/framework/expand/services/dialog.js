/**
 * 对话框服务
 * 因为对话框是以window的形式存在，故没有必要以HTML指令的形式存在于html模版中，因此
 *
 * */
angular.module('services').factory('dialog',function(){

    var body = $("body");//获取body元素

    return {

        alert : function(content){

        }

    }
});