/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('locationService',[function(){
	var body = $('body'),
        parseParam=function(param, key){
            var paramStr="";
            if(param instanceof String||param instanceof Number||param instanceof Boolean){
                paramStr+="&"+key+"="+encodeURIComponent(param);
            }else{
                $.each(param,function(i){
                    var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
                    paramStr+='&'+parseParam(this, k);
                });
            }
            return paramStr.substr(1);
        };

    return {
        /**
         * 在当前页面跳转到一个新的页面
         * @param url
         * @param params
         */
        redirect : function(url,params){
        	if(params){
        		url += "?"+parseParam(params||{});
        	}
            window.location.href = url;
        },
        /**
         * 打开一个新的tab页面或者页面
         */
        redirect_new : function(url,params){
        	var action = url+"?"+parseParam(params||{});
            var tempwindow=window.open('_blank');
            tempwindow.location = action;
        },
        reload : function(){
        	 window.location.reload();
        }
    }

}]);