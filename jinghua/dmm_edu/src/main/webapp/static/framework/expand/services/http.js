
/**
 * 基于angularjs的$http服务之上的封装
 * */
angular.module('services').factory('http',['httpConfig','$http',function(httpConfig,$http){
    var baseUrl = httpConfig.baseUrl;
        return {
            /**
             * 请求数据,通过该形式，避免了使用回调函数的形式获取数据，极大的增强了代码的可读性
             * var req = {
             * 	    service : 'beanId?method',
             * 		params : {
             * 			name : '张三',
             * 			sex : '男'
             * 		}
             * };
             * var data = http.callService(req);
             * $scope.iterator = data;//这里需要在模版中通过iterator.data来进行数据的遍历，
             * 						  //不要在这里直接试图通过iterator.data获取数据（ajax请求是异步的，数据尚未绑定到该键上)
             *
             *********************************************************
             * 当然如果你想直接获取数据：你可以这样做：
             * http.callService(req).success(function(data){
             * 		//在这里获取你实际需要的数据
             * });
             */
            callService : function(request){
            	var requestCopy = angular.copy(request),
	        		params = requestCopy.params,
	        		backKey = "data",
                    requests = [],
                    sm = requestCopy.service.split("?"),
	        		callback,
	        		ret = {
            			success : function(thenCallback){
            				callback = thenCallback;
            			}
            		};
            	delete requestCopy.service;
            	requestCopy.server = sm[0];
            	requestCopy.method = sm[1];
            	
                $http({
                    method: 'POST',
                    url: baseUrl,
                    data: requestCopy
                }).success(function(data){
                    if (callback) {
                        callback(data);
                    } else {
                        ret[backKey] = data;
                        delete ret.success;
                    }
                });
	        	return ret;
            },
            /**
             * 为了解决ajax 嵌套调用的问题，屏蔽到复杂的嵌套结构
             *       http.call({
             *         service : 'base?get'
             *       }).call(function(data1){
             *           return {
             *               service : 'base?get1'
             *           }
             *       }).call(function(data1,data2){
             *           return {
             *               service : 'base?get2'
             *           }
             *       }).end(function(data1,data2,data3){
             *
             *       })
             *
             * @param req
             * @returns {{call: call, end: end}}
             */
            call : function(req){
                var  me = this,
                     requests = [req],
                     results = [],
                     endCallback,
                     ret = {
                        call : function(req){
                            requests.push(req);
                            return this;
                        },
                        end : function(callback){
                            endCallback = callback;
                            var reqIndex = 0;
                            var callFn = function(req){
                                if(angular.isFunction(req)){
                                    req = req.apply(window,results);
                                }
                                me.callService(req).success(function(data){
                                    results.push(data);
                                    if(reqIndex < requests.length-1){
                                        var nextReq = requests[reqIndex+1];
                                        callFn(nextReq);
                                    }else if(reqIndex == requests.length-1){
                                        endCallback.apply(window,results);
                                    }
                                    reqIndex++;
                                });
                            }
                            callFn(requests[reqIndex]);
                        }
                     };
                return ret;
            }
        }
    }
]);