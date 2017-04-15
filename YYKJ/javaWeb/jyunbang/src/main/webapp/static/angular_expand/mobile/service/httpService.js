/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('httpService',['$rootScope','toastrService',function(root,toastr){
    return {
        /**
         * @param configs 配置对象
         *
         *  模板 params = {
             	url : "",
             	data : {},
             	success : function(data){}
        	}
         返回jQuery的Ajax对象
         */
        post : function(params){
        	
            var result = $.ajax({
            	type: "POST",   //请求方式
            	url: base + params.url,//请求的url地址
        	    data: params.data,//参数值
        	    dataType: "json",   //返回格式为json
        	    beforeSend: function() {
        	        //请求前的处理
                    if(params.beforeSend)params.beforeSend();
        	    },
        	    success: function(data) {
        	        //请求成功时处理
                    if(params.success)params.success(data);
        	    },
        	    error: function() {
        	        //请求出错处理
        	    	toastr.error("数据请求错误，请重试！");
        	    },
        	    complete : function(){
        	    	root.$apply();
        	    }
            	
            });
            return result;
        },
        get : function(params){
        	 var result = $.ajax({
             	type: "GET",   //请求方式
             	url: params.url,//请求的url地址
         	    data: params.data,//参数值
         	    dataType: "json",   //返回格式为json
                beforeSend: function() {
                     //请求前的处理
                     if(params.beforeSend)params.beforeSend();
                 },
                success: function(data) {
                     //请求成功时处理
                     if(params.success)params.success(data);
                 },
         	    error: function() {
         	        //请求出错处理
         	    	toastr.error("数据请求错误，请重试！");
         	    },
        	    complete : function(){
        	    	root.$apply();
        	    }
             	
             });
             return result;
        }
    };
}]);
/*system.config(['$httpProvider',function($httpProvider) {
	  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
	  var param = function(obj) {
	    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
	      
	    for(name in obj) {
	      value = obj[name];
	        
	      if(value instanceof Array) {
	        for(i=0; i<value.length; ++i) {
	          subValue = value[i];
	          fullSubName = name + '[' + i + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value instanceof Object) {
	        for(subName in value) {
	          subValue = value[subName];
	          fullSubName = name + '[' + subName + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value !== undefined && value !== null)
	        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
	    }
	      
	    return query.length ? query.substr(0, query.length - 1) : query;
	  };
	  $httpProvider.defaults.transformRequest = [function(data) {
	    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
	  }];
}]);
var config = {
	method : "POST",
	url: base + params.url,//请求的url地址
	data : params.data, //参数值
	responseType : "json" //返回格式为json
};
var deferred = q.defer();
var promise = deferred.promise;
http(config).success(function(data){
		deferred.resolve(data);
	}).error(function(data){
		deferred.reject(data);
});
return promise;*/
