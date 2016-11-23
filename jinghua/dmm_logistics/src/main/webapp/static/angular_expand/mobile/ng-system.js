
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
var system = angular.module('system',[]);/**
 * highchart service
 * 根据传入的config 装配成highcharts需要的 config
 * renderCommonChart //图表类型(column,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

system.factory('highChartService',function(){
	Highcharts.setOptions({
        chart: {
            style: {
                fontFamily:"微软雅黑" 
            }
        }
    });
    return {
        /**
         * @param configs 配置对象
         *
         *  模板 configs = {
             title : "  ",
             yAxis : "件",
             isSort : false,
             data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
                     {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
             type :"column"   //图表类型(column,line,area,spline,areaspline)
        }
         */
        renderCommonChart : function(configs){
            configs.isSort = configs.isSort || false;
            var isName ={} , isField = {};
            var fields = [],series = [];
            for(var i in configs.data){
                var tar = configs.data[i];
                if(!isName[tar.name]){
                    series.push({name : tar.name,data : []});
                    isName[tar.name] = true;
                }
                if(!isField[tar.field]){
                    fields.push(tar.field);
                    isField[tar.field] = true;
                }
            }
            if(configs.isSort) fields.sort(function(a,b){return a>b?1:-1;});
            var ser,fie,dat;
            for ( var j in series) {
                ser = series[j];
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < configs.data.length; m++){
                        dat = configs.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                        }
                    }
                    if (ser.data.length < k) {
                        ser.data.push(0);
                    }
                }
            }

            type = configs.type || 'column';
            var config = {
                title: {
                    text: configs.title,
                    x: -20 //center
                },
                chart: {
                    type: type
                },
                xAxis: [{
                    categories: fields,
                    crosshair: true
                }],
                yAxis: {
                    title: {
                        text: configs.yAxis
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix:' '+(configs.dw||''),
                    shared: true
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series:series
            };
            return config;
        },
        /**
         * @param config 配置对象
         *
         * 	config = {
            title: " ",
            data : [{name: '实践课', y: 62.5},{name: '理论课', y: 37.5},{name: '生物', y: 300}],
            showLable: false}
         */
        renderPieChart : function(config){
            config.showLable = (config.showLable == null) ? true : config.showLable;
            var data = config.data;
            var result = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: config.title,
                },
                tooltip: {
                    pointFormat: ' 数量:<b>{point.y}</b><br/> {series.name}:<b>{point.percentage:.1f}%</b>'
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: config.showLable
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: "占比",
                    data: data
                }]
            };
            return  result;
        }
    };
});/**
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
            window.location.href = url+"?"+parseParam(params||{});
        },
        /**
         * 打开一个新的tab页面或者页面
         */
        redirect_new : function(url,params){
        	var action = url+"?"+parseParam(params||{});
            var tempwindow=window.open('_blank');
            tempwindow.location = action;
        }
    }

}]);/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('toastrService',[function(){
	toastr.options = {
	  "closeButton": false,
	  "debug": false,
	  "positionClass": "toast-bottom-full-width",
	  "onclick": null,
	  "showDuration": "100",
	  "hideDuration": "300",
	  "timeOut": "3000",
	  "extendedTimeOut": "500",
	  "showEasing": "linear",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}

    return {
        info : function(str){
        	toastr.info(str)
        },
        success : function(str){
        	toastr.success(str)
        },
        warning : function(str){
        	toastr.warning(str)
        },
        error : function(str){
        	toastr.error(str)
        },
        clear : function(){
        	toastr.clear()
        }
    }
}]);/**
 * dialog 
 * 
 alert : dialog.alert("哈哈？");
		
		
 confirm:dialog.confirm("哈哈？",function(){
			alert("yes");
		},function(){
			alert("no")
		});
 */
system.factory('dialog',['$compile','$rootScope',function($compile,$rootScope){
	    //创建遮罩
		var body = $("body");//获取body元素
	    var mask = $('<div class="modal-backdrop fade in"/>');
	    mask.hide();
	    body.append(mask);
	    var confirmHtml = 
	    	'<div class="modal" style="display:none;">'+
	        	'<div class="modal-dialog" style="background-color: #FFF;">'+
	                '<div class="modal-header">'+
	                    '<button type="button" class="close" ng-click="$dialog_confirm_close()" style="opacity: 1;"><i class="fa fa-times text-danger"></i></button>'+
	                    '<h4 class="modal-title text-primary"><i class="fa fa-question"></i>&nbsp;提示</h4>'+
	                 '</div>'+
	                 '<div class="modal-body">'+
	                 	'{replacement}'+
	                 '</div>'+
	                 '<div class="modal-footer" style="text-align: center;">'+
	                 	'<button type="button" class="btn btn-primary" ng-click="$dialog_confirm_sure()">确定</button>'+
	                 	'<button type="button" class="btn btn-default" ng-click="$dialog_confirm_cancel()">关闭</button>'+
	                 '</div>'+
	             '</div>'+
	         '</div>';
	    var successHtml = '<div class="modal" style="display:none;">'+
		    '<div class="modal-dialog" style="background-color: #FFF;">'+
		         '<div class="modal-body" style="padding:30px 10px;">'+
		         	'{replacement}'+
		         '</div>'+
		         '<div class="modal-footer" style="text-align: center;">'+
		         	'<button type="button" class="btn btn-danger btn-sm" ng-click="$dialog_success_close()">关闭</button>'+
		         '</div>'+
		  '</div>';
	    var loadingHtml = '<div class="modal" style="display:none;">'+
	    	'<div style="position: absolute;left: 50%;top: 35%;margin-left: -30px;width: 60px;text-align: center;color: #FFF;"> <i class="fa fa-spinner fa-spin fa-4x" style=""></i></div>'
	    '</div>';
	    /**
	     * 把元素添加到body上，显示遮罩，并把dialog居中显示
	     * replace message,show mask,show dialog
	     * @param html
	     * @param message
	     * @returns {*|jQuery|HTMLElement}
	     */
	    var rsd = function(html,message){
	        var el = $($compile(html.replace("{replacement}",message))($rootScope));
	        mask.show();//显示遮罩
	        body.append(el);
	        el.fadeIn("fast");
	        return el;
	    }
	    var dialog = {
	    		
	    };
	    return {
	        alert : function(message,callback){
	            $rootScope.$dialog_success_close = function(){
	            	dialog.success.remove();
	                mask.hide();
	                delete dialog.success;
	                if(callback)callback()
	            };
	            dialog.success = dialog.success||rsd(successHtml,message);
	        },
	        
	        /**
	         * 显示一个确认窗口
	         * @param message
	         * @param doSure
	         * @param doFailure
	         */
	        confirm : function(message,doSure,doCancel){
	            $rootScope.$dialog_confirm_close = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	            };
	            $rootScope.$dialog_confirm_sure = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	                if(doSure)doSure();
	            };
	            $rootScope.$dialog_confirm_cancel = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	                if(doCancel)doCancel();
	            };
	            dialog.confirm = dialog.confirm||rsd(confirmHtml,message);
	       },
	       showLoading : function(){
	    	   if(dialog.loading){
	    		   mask.show();
	    		   dialog.loading.fadeIn('fast');
	    	   }else{
	    		   dialog.loading = rsd(loadingHtml,"");
	    	   }
	    	   
	       },
	       hideLoading : function(){
	    	   dialog.loading.fadeOut('fast');
               mask.fadeOut('fast');
	       }
	    }
	}]);/*************************************************
 图表指令
 ************************************************/
system.directive('cgChart', ['highChartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "="
        },
        link : function(scope, element, attrs) {
            scope.renderChart = function(){
                if(scope.config ){
                    if(scope.config.type){
                        switch (scope.config.type){
                            case 'column' : ;
                            case 'line':;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                                element.highcharts(service.renderCommonChart(scope.config));
                                break;
                            case 'pie' :
                                element.highcharts(service.renderPieChart(scope.config));
                                break;
                            default :
                                break;
                        }
                    } else{
                        scope.config = angular.extend(scope.config,{
                            credits : {         // 不显示highchart标记
                                enabled : false
                            }
                        });
                        element.highcharts(scope.config);
                    }
                }
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);

/*星星评分控件。
 *  eg:
 *  scope.value=Number;
 *  scope.max=Number;
 *  <any cg-star-rating cg-value="value" cg-max="max"></any><!--最小使用-->
 *  eg:
 *  <any cg-star-rating cg-value="value" cg-max="max" size="md" isreadonly="true"></any>
 *  ----------
 *  size可用值为：xl, lg, md, sm, or xs. Defaults to xs
 */
system.directive("cgStarRating", [ function() {
	return {
		restrict : "AE",
		replace : true,
		template : "<input type=\"number\" class=\"rating\" />",
		scope : {
			cgMax : "=",//最大值（星星的个数）
			cgValue : "=" //选中的星星的个数
		},
		link : function(scope, element, iAttrs) {
			scope.cgValue = scope.cgValue || 0;//设置默认值为0
			scope.cgMax = scope.cgMax || 5;
			element.val(scope.cgValue);//设置input的值
			//属性默认值
			var readonly = iAttrs.isreadonly || "false";
			var size = iAttrs.size || "xs";
			//生成星星控件
			var readonly_value = readonly == "true" ? true : false;
			var params = {//控制星星的显示属性
				min : 0,
				max : scope.cgMax,
				step : 1,
				size : size,
				stars : scope.cgMax,
				showClear : false,
				showCaption : false,
				readonly : readonly_value
			}
			$(element).rating(params);
			//响应外部scope变化
			scope.$watch("cgValue", function(newVal, oldVal) {
				$(element).rating("update", newVal);//更新值
			}, true)
			scope.$watch("cgMax", function(newVal, oldVal) {
				$(element).rating("refresh", {
					max : newVal,
					stars : newVal
				});//更新控件
			}, true)
			//触发外部scope变化
			$(element).on('rating.change', function() {
				scope.$apply(scope.cgValue = element.val());
			});
		}
	}
} ]);

/*星星评分控件。没有对传入属性进行限制,功能最大。
 * https://github.com/kartik-v/bootstrap-star-rating
 *  eg:
 *  scope.value=Number;
 *  scope.attr={min : 0,
				max : 5,
				step : 1,
				size : "xs",
				stars : 5,
				showClear : false,
				showCaption : false,
				readonly : false};//具体属性，按照https://github.com/kartik-v/bootstrap-star-rating要求为准
 *  <any cg-star-rating-full cg-value="value" cg-attr="attr"></any>
 */
system.directive("cgStarRatingFull", [ function() {
	return {
		restrict : "AE",
		replace : true,
		template : "<input type=\"number\" class=\"rating\" />",
		scope : {
			cgAttr : "=",//星星控件属性配置
			cgValue : "=" //选中的星星的个数
		},
		link : function(scope, element, iAttrs) {
			scope.cgValue = scope.cgValue || 0;//设置默认值为0
			element.val(scope.cgValue);//设置input的值
			var params = {//控制星星的显示属性
				min : 0,
				max : 5,
				step : 1,
				size : "xs",
				stars : 5,
				showClear : false,
				showCaption : false,
				readonly : false
			}
			scope.cgAttr = scope.cgAttr || params;
			$(element).rating(scope.cgAttr);
			//响应外部scope变化
			scope.$watch("cgValue", function(newVal, oldVal) {
				$(element).rating("update", newVal);//更新值
			}, true)
			scope.$watch("cgAttr", function(newVal, oldVal) {
				$(element).rating("refresh", newVal);//属性改变更新控件
			}, true)
			//触发外部scope变化
			$(element).on('rating.change', function() {
				//该函数没有在angular声明周期内被调用，需用$apply通知scope变化。
				scope.$apply(scope.cgValue = element.val());
			});
		}
	}
} ]);