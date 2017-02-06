
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
var system = angular.module('system',[]);/**
 * echartService
 * 根据传入的config 装配成echartService需要的 config
 * renderCommonChart //图表类型(bar,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

system.factory('echartService',function(){
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
            type = configs.type || 'column';
            var isName ={} , isField = {};
            var fields = [],series = [],legendData=[];
            for(var i in configs.data){
                var tar = configs.data[i];
                if(!isName[tar.name]){
                	legendData.push(tar.name);
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
                ser.type = type;
                if(ser.type == 'area'){
                	ser.type = 'line';
                	ser.areaStyle = {normal: {}};
                }
                if(ser.type == 'spline'){
                	ser.type = 'line';
                	ser.smooth = true;
                }
                if(ser.type == 'areaspline'){
                	ser.type = 'line';
                	ser.smooth = true;
                	ser.areaStyle = {normal: {}};
                }
                
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < configs.data.length; m++){
                        dat = configs.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                        }
                    }
                    if (ser.data.length <= k){
                        ser.data.push(0);
                    }
                }
            };
            var axisLabelRotate = 0;
            if(fields.length > 5){
            	axisLabelRotate = 45;
            }
            
            // 指定图表的配置项和数据
            var config = {
                title: {
                    text: configs.title,
                    left : 'center'
                },
                grid: {
                	 top : 40,
                	 left: '1%',
                     right: '1%',
                     bottom: 30,
                     containLabel: true
                },
                toolbox: {
			        show : true,
			        feature : {
		        	    dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    }, 
                tooltip : {
                    trigger: 'axis',
                    axisPointer : { // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    left: 'center',
                    bottom : 'bottom',
                    data:legendData
                },
                xAxis: {
                    data: fields,
                    axisLabel:{
                    	rotate:axisLabelRotate,
                    	interval: 0
                    }
                },
                yAxis : [{
                	name : configs.yAxis,
                    type : 'value'
                }],
                series:  series
            };
            return config;
        },
        /**
         * @param config 配置对象
         *
         * 	config = {
         * 		type : 'pie',
	            title: "饼状图标题",
	            data : [{name: '苹果', value: 30},{name: '橘子', value: 20}]
	        }
         */
        renderPieChart : function(config){
    	   var data = config.data;
    	   config.showLable = (config.showLable == null) ? true : config.showLable;
        	option = {
        		    title : {
        		        text: config.title,
        		        x:'center'
        		    },
        		    grid: {
	                   	top : "middle",
	                   	left: '1%',
                        right: '1%',
                        containLabel: false
                   },
        		    tooltip : {
        		        trigger: 'item',
        		        formatter: "{b} <br/> 数量：{c} &nbsp;&nbsp;(占比{d}%)"
        		    },
        		    label: {
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold'
                            }
                        }
                    },
        		    series : [
        		        {
        		            type: 'pie',
        		            radius : '70%',
        		            center: ['50%', '55%'],
        		            avoidLabelOverlap: false,
        		            selectedMode: 'single',
        		            data: data,
        		            itemStyle: { 
        		                emphasis: {
        		                    shadowBlur: 10,
        		                    shadowOffsetX: 0,
        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        		                }
        		            },
        		            label : {
        		                normal : {
        		                    formatter: '{b}({d}%)'
        		                }
        		            }
        		        }
        		    ]
        		};
        	return option;
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
        	    error: function(dt) {
        	        //请求出错处理
        	    	var msg = dt.status + ":" + dt.statusText;
        	    	if(msg == "200:OK"){
        	    		toastr.error("您没有该项权限！");
        	    	}else{
        	    		toastr.error("数据请求错误，请刷新页面重试！("+msg+")");
        	    	}
        	    	if(dt.status == '404'){
        	    		try{
        	    			//window.parent.location.href = logoutUrl;
        	    			toastr.error("数据请求错误，请刷新页面重试！("+msg+")");
        	    		}catch (e) {
        	    			window.location.href = logoutUrl;
        	    		}
        	    	}
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
        },
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
	    	requestCopy.beanName = sm[0];
	    	requestCopy.methodName = sm[1];
	    	if(!requestCopy.params)requestCopy.params = [];
	    	requestCopy.data = angular.toJson(requestCopy.params);
	    	requestCopy.params = angular.toJson(requestCopy.params);
	    	$.ajax({
	        	type: "POST",   //请求方式
	        	url: base + "common/getData",//请求的url地址
	    	    data: {params : angular.toJson(requestCopy)},//参数值
	    	    dataType: "json",   //返回格式为json
	    	    success: function(data) {
	    	    	if (callback) {
	                    callback(data);
	                } else {
	                    ret[backKey] = data.result;
	                    delete ret.success;
	                }
	    	    }
	        });
	    	return ret;
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
	}]);/**
 * 标准代码service
 * 获取系统中的标准代码以及一些常用的代码信息
 */
system.factory('sysCodeService',['httpService',function(http){
	return {
		getCodeByCodeType : function(codeType){
			return http.post({
				url : "business/standardcode/codetype",
				data : {
					codeType : codeType
				}
			});
		},
		getSelfDefinedYearCode : function(){
			return http.post({
				url : "business/selfdefined/year",
				data : {}
			});
		},
		getYears : function(){
			return http.post({
				url : "business/selfdefined/years",
				data : {}
			});
		},
		getCodeSubject : function(){
			return http.post({
				url : "business/code/subject",
				data : {}
			});
		},
		getDeptTree : function(shiroTag){
			return http.post({
				url : "business/code/depttree",
				data : {
					shiroTag : shiroTag
				}
			})
		},
		getTeachDeptTree : function(shiroTag){
			return http.post({
				url : "business/code/teachdepttree",
				data : {
					shiroTag : shiroTag
				}
			})
		}
	};
}]);/*************************************************
 echarts图表指令
 功能介绍 ： 
	 	bar，line，area，spline，areaspline 生成柱状图和折线图
	 	数据格式 
	 	config = {
	         title : "图形标题",
	         yAxis : "Y轴单位",
	         data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
	                 {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
	         type :"bar"   //图表类型(bar,line,area,spline,areaspline)
	    }
	 	
	 	pie 生成饼状图
	 	数据格式 
	 	config = {
	 		type : 'pie',
	 		title: "饼状图标题",
	        data : [{name: '苹果', value: 30},{name: '橘子', value: 20}] }
 	
 height chart的高度，默认为300
 例子见 ： static/angular_expand/example/echart.jsp
 ************************************************/
system.directive('echart', ['echartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "=",
            height: "@",
            onClick : "&"
        },
        link : function(scope, element, attrs) {
        	if (typeof(scope.height) == "undefined") { 
        		scope.height = 400;
    		}  
    		element.height(scope.height);
            scope.renderChart = function(){
            	// 基于准备好的dom，初始化echarts实例
            	var myChart = echarts.init(element[0]);
                if(scope.config){
                	var options = {};
                    if(scope.config.type){
                        switch (scope.config.type){
                            case 'bar' : ;
                            case 'line':;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                            	options = service.renderCommonChart(scope.config);
                                break;
                            case 'pie' :
                            	options = service.renderPieChart(scope.config);
                            	break;
                            default :
                            	options = scope.config;
                                break;
                        }
                    } else{
                    	options = scope.config;
                    	if(!options.grid){
                    		options.grid = {
    		                	 top : "60px",
    		                	 left: '1%',
    		                     right: '1%',
    		                     bottom: 40,
    		                     containLabel: true
                    		}
                    	}
                    	if(!options.toolbox){
	                    	if(options.series.length>0 && options.series[0].type == 'pie'){
	                    		options.toolbox = {
	            			        show : true,
	            			        feature : {
	            		        	    dataView : {show: true, readOnly: false},
	            			            restore : {show: true},
	            			            saveAsImage : {show: true}
	            			        }
	            			    };
	                    	}else{
	                    		options.toolbox = {
	            			        show : true,
	            			        feature : {
	            		        	    dataView : {show: true, readOnly: false},
	            			            magicType : {show: true, type: ['line', 'bar']},
	            			            restore : {show: true},
	            			            saveAsImage : {show: true}
	            			        }
	            			    };
	                    	}
                    	}
                    }
                    options.color = [ 
						'#b6a2de','#5ab1ef','#ffb980','#d87a80',
						'#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
						'#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
						'#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
                 ]
                    myChart.setOption(options);
                }
                if(scope.onClick){
               	 	myChart.on('click', function (params) {
                        scope.onClick({
                        	$params : params
                        });
                        scope.$apply();
                    });
                }
                $(window).resize(function(){
                	myChart.resize();
                });
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);system.controller('PaginationController', ['$scope', '$attrs', '$parse', function ($scope, $attrs, $parse) {
  var self = this,
      ngModelCtrl = { $setViewValue: angular.noop }, // nullModelCtrl
      setNumPages = $attrs.numPages ? $parse($attrs.numPages).assign : angular.noop;

  this.init = function(ngModelCtrl_, config) {
    ngModelCtrl = ngModelCtrl_;
    this.config = config;

    ngModelCtrl.$render = function() {
      self.render();
    };

    if ($attrs.itemsPerPage) {
      $scope.$parent.$watch($parse($attrs.itemsPerPage), function(value) {
        self.itemsPerPage = parseInt(value, 10);
        $scope.totalPages = self.calculateTotalPages();
      });
    } else {
      this.itemsPerPage = config.itemsPerPage;
    }
  };

  this.calculateTotalPages = function() {
    var totalPages = this.itemsPerPage < 1 ? 1 : Math.ceil($scope.totalItems / this.itemsPerPage);
    return Math.max(totalPages || 0, 1);
  };

  this.render = function() {
    $scope.page = parseInt(ngModelCtrl.$viewValue, 10) || 1;
  };

  $scope.selectPage = function(page) {
    if ( $scope.page !== page && page > 0 && page <= $scope.totalPages) {
      ngModelCtrl.$setViewValue(page);
      ngModelCtrl.$render();
    }
  };

  $scope.getText = function( key ) {
    return $scope[key + 'Text'] || self.config[key + 'Text'];
  };
  $scope.noPrevious = function() {
    return $scope.page === 1;
  };
  $scope.noNext = function() {
    return $scope.page === $scope.totalPages;
  };

  $scope.$watch('totalItems', function() {
    $scope.totalPages = self.calculateTotalPages();
  });

  $scope.$watch('totalPages', function(value) {
    setNumPages($scope.$parent, value); // Readonly variable

    if ( $scope.page > value ) {
      $scope.selectPage(value);
    } else {
      ngModelCtrl.$render();
    }
  });
}])

.constant('paginationConfig', {
  itemsPerPage: 10,
  boundaryLinks: false,
  directionLinks: true,
  firstText: '《',
  previousText: '〈',
  nextText: '〉',
  lastText: '》',
  rotate: true
})

.directive('pagination', ['$parse', 'paginationConfig', function($parse, paginationConfig) {
  return {
    restrict: 'EA',
    scope: {
      totalItems: '=',
      firstText: '@',
      previousText: '@',
      nextText: '@',
      lastText: '@'
    },
    require: ['pagination', '?ngModel'],
    controller: 'PaginationController',
    template:  "<ul class=\"pagination\">\n" +
	    "  <li ng-if=\"boundaryLinks\" ng-class=\"{disabled: noPrevious()}\"><a href ng-click=\"selectPage(1)\">{{getText('first')}}</a></li>\n" +
	    "  <li ng-if=\"directionLinks\" ng-class=\"{disabled: noPrevious()}\"><a href ng-click=\"selectPage(page - 1)\">{{getText('previous')}}</a></li>\n" +
	    "  <li ng-repeat=\"page in pages track by $index\" ng-class=\"{active: page.active}\"><a href ng-click=\"selectPage(page.number)\">{{page.text}}</a></li>\n" +
	    "  <li ng-if=\"directionLinks\" ng-class=\"{disabled: noNext()}\"><a href ng-click=\"selectPage(page + 1)\">{{getText('next')}}</a></li>\n" +
	    "  <li ng-if=\"boundaryLinks\" ng-class=\"{disabled: noNext()}\"><a href ng-click=\"selectPage(totalPages)\">{{getText('last')}}</a></li>\n" +
	    "</ul>",
    replace: true,
    link: function(scope, element, attrs, ctrls) {
      var paginationCtrl = ctrls[0], ngModelCtrl = ctrls[1];

      if (!ngModelCtrl) {
         return; // do nothing if no ng-model
      }

      // Setup configuration parameters
      var maxSize = angular.isDefined(attrs.maxSize) ? scope.$parent.$eval(attrs.maxSize) : paginationConfig.maxSize,
          rotate = angular.isDefined(attrs.rotate) ? scope.$parent.$eval(attrs.rotate) : paginationConfig.rotate;
      scope.boundaryLinks = angular.isDefined(attrs.boundaryLinks) ? scope.$parent.$eval(attrs.boundaryLinks) : paginationConfig.boundaryLinks;
      scope.directionLinks = angular.isDefined(attrs.directionLinks) ? scope.$parent.$eval(attrs.directionLinks) : paginationConfig.directionLinks;

      paginationCtrl.init(ngModelCtrl, paginationConfig);

      if (attrs.maxSize) {
        scope.$parent.$watch($parse(attrs.maxSize), function(value) {
          maxSize = parseInt(value, 10);
          paginationCtrl.render();
        });
      }

      // Create page object used in template
      function makePage(number, text, isActive) {
        return {
          number: number,
          text: text,
          active: isActive
        };
      }

      function getPages(currentPage, totalPages) {
        var pages = [];
        // Default page limits
        var startPage = 1, endPage = totalPages;
        var isMaxSized = ( angular.isDefined(maxSize) && maxSize < totalPages );

        // recompute if maxSize
        if ( isMaxSized ) {
          if ( rotate ) {
            // Current page is displayed in the middle of the visible ones
            startPage = Math.max(currentPage - Math.floor(maxSize/2), 1);
            endPage   = startPage + maxSize - 1;

            // Adjust if limit is exceeded
            if (endPage > totalPages) {
              endPage   = totalPages;
              startPage = endPage - maxSize + 1;
            }
          } else {
            // Visible pages are paginated with maxSize
            startPage = ((Math.ceil(currentPage / maxSize) - 1) * maxSize) + 1;

            // Adjust last page if limit is exceeded
            endPage = Math.min(startPage + maxSize - 1, totalPages);
          }
        }

        // Add page number links
        for (var number = startPage; number <= endPage; number++) {
          var page = makePage(number, number, number === currentPage);
          pages.push(page);
        }

        // Add links to move between page sets
        if ( isMaxSized && ! rotate ) {
          if ( startPage > 1 ) {
            var previousPageSet = makePage(startPage - 1, '...', false);
            pages.unshift(previousPageSet);
          }

          if ( endPage < totalPages ) {
            var nextPageSet = makePage(endPage + 1, '...', false);
            pages.push(nextPageSet);
          }
        }

        return pages;
      }

      var originalRender = paginationCtrl.render;
      paginationCtrl.render = function() {
        originalRender();
        if (scope.page > 0 && scope.page <= scope.totalPages) {
          scope.pages = getPages(scope.page, scope.totalPages);
        }
      };
    }
  };
}])

.constant('pagerConfig', {
  itemsPerPage: 10,
  previousText: '« Previous',
  nextText: 'Next »',
  align: true
})

.directive('pager', ['pagerConfig', function(pagerConfig) {
  return {
    restrict: 'EA',
    scope: {
      totalItems: '=',
      previousText: '@',
      nextText: '@'
    },
    require: ['pager', '?ngModel'],
    controller: 'PaginationController',
    template: 
    	"<ul class=\"pager\">\n" +
	    "  <li ng-class=\"{disabled: noPrevious(), previous: align}\"><a href ng-click=\"selectPage(page - 1)\">{{getText('previous')}}</a></li>\n" +
	    "  <li ng-class=\"{disabled: noNext(), next: align}\"><a href ng-click=\"selectPage(page + 1)\">{{getText('next')}}</a></li>\n" +
	    "</ul>",
    replace: true,
    link: function(scope, element, attrs, ctrls) {
      var paginationCtrl = ctrls[0], ngModelCtrl = ctrls[1];
      if (!ngModelCtrl) {
         return; // do nothing if no ng-model
      }
      scope.align = angular.isDefined(attrs.align) ? scope.$parent.$eval(attrs.align) : pagerConfig.align;
      paginationCtrl.init(ngModelCtrl, pagerConfig);
    }
  };
}]);/**
 * 树指令
 * 
//*******************使用方法**********************  
//
//   <ul class="list1">
//	    <li cg-tree="node in items of treeData">
//	      <a ng-click="check(node)">{{node.name}}</a>
//	      <ul> <li cg-treecurse ></li> </ul>
//	    </li>
//	  </ul>
//
 *******************数据格式**********************
 * 
 * $scope.treeData = {
 *		  name: "Root",
 *		  items : [
 *		           {name:2,items:[{name:3},{name:4}]},
 *		           {name:5,items:[{name:6},{name:7}]}]
 * }
 ***********************************************
 */
system.directive('cgTree', ['$log', function($log) {
	return {
	   restrict: 'A',
	   scope: true,
	   controller: ['$scope', '$attrs',
	     function TreeController($scope, $attrs){
		   //***** 检测指令内的参数格式是否正确   ”node in items of treeData”
		   var expression = $attrs.cgTree;
		    var match = expression.match(/^\s*([\$\w]+)\s+in\s+([\S\s]*)\s+of\s+([\S\s]*)$/);
		    if (! match) {
		      throw new Error("Expected cgTree in form of"+ " '_item_ in _collection_ of _root_' but got '" + expression + "'.");
		    }
		    //******检测结束*******
		    
		    
	       var ident = this.ident = {value: match[1],collection: match[2],root: match[3]};
	       //$log.info("Parsed '%s' as %s", $attrs.cgTree, JSON.stringify(this.ident));
	       $scope.$watch(this.ident.root, function(v){
	         $scope[ident.value] = v;
	       });
	     }
	   ],
	   // Get the original element content HTML to use as the recursive template
	   compile: function cgTreecurseCompile(element){
	     var template = element.html();
	     return {
	       // set it in the pre-link so we can use it lower down
	       pre: function cgTreePreLink(scope, iterStartElement, attrs, controller){
	         controller.template = template;
	       }
	     };
	   }
	 };
	}]).directive('cgTreecurse', ['$compile', function($compile){
	return {
	   // 必须被cgTree指令包含
	   require: "^cgTree",
	   link: function cgTreecursePostLink(scope, iterStartElement, attrs, controller) {
	     // 使用父标签传入的参数进行解析，相当于使用递归
	     var build = [
	       '<', iterStartElement.context.tagName, ' ng-repeat="',
	       controller.ident.value, ' in ',
	       controller.ident.value, '.', controller.ident.collection,
	       '">',
	       controller.template,
	       '</', iterStartElement.context.tagName, '>'];
	     var el = angular.element(build.join(''));
	     // We swap out the element for our new one and tell angular to do its
	     // thing with that.
	     iterStartElement.replaceWith(el);
	     $compile(el)(scope);
	   }
	};
}]);/**
 * 验证权限指令
 */

system.directive('checkPermiss',["httpService", function(http) {
	return {
		restrict : 'AE',
		scope: {
			callback : "&"
		},
		link : function(scope, element, attrs) {
			var data={};
			data.params=[];
			data.params.push(attrs.shirotag);
			data.service='checkPermiss?checkPermiss';
	  		http.callService(data).success(function(data){
	  			if(scope.callback)scope.callback();
	  			if(data==true) return;
	  			$(element[0]).remove();
		    }); 
		}
	}
}]);
/**
 * 查询树组件
 * 
 * <div cg-combo-tree source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData = {
//		  id :1,
//		  name: "全校",
//		  children : [
//		           {id:2,name:"计算机与信息工程学院",children:[{id:3,name:"计算机"},{id:4,name:"软件工程"}]},
//		           {id:3,name:"体育学院",children:[{id:3,name:'健美'},{id:4,name:'体操'}]},
//		           {id:4,name:"建筑工程工程学院",children:[{id:3,name:'造价'},{id:4,name:'城市工程'}]},
//		           {id:5,name:"会计学院",children:[{id:6,name:'国际贸易'},{id:7,name:'银行电算'}]}]
//	  }
//	  $scope.treeResult = {};
 * 
 */
system.directive('cgComboTree',['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/comboTree.html',
		scope : {
			source:"=",
			result:"=",
			code : "="
		},
		link : function(scope, element, attrs) {
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				dt.nodeId = nodeId++;
				if (dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
				}
			};
			scope.selectTree = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				//scope.result = {};
				angular.copy({},scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				scope.packageSourceData(scope.treeData);
				
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
			},true);
			
			//寻找父节点,找到后将父节点塞进选定节点容器
			scope.findPanrentOfNode = function(node,tree){
				if(node.nodeId != tree.nodeId){
					if (node.parentNodeId == tree.nodeId) {
						scope.selectTree.push(tree);
						scope.findPanrentOfNode(tree,scope.treeData);
					}else{
						if (tree.children) {
							for ( var i = 0; i < tree.children.length; i++) {
								scope.findPanrentOfNode(node,tree.children[i]);
							}
						}
					}
				}
			};
			
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//置空容器
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(node);
				scope.findPanrentOfNode(node,scope.treeData);
				//由于生成的节点容器将树倒置，所以翻转容器
				scope.selectTree.reverse();
				
				//备份数据，并清空树的数据，实现点击后将树隐藏
				var treeDataBak = angular.copy(scope.selectTree);
				scope.selectTree = [];
				$interval(function() {
					//重新给树赋值
					scope.selectTree = treeDataBak;
			     }, 1,1);
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				
				scope.result.queryCode = angular.copy(scope.code);
				scope.result.queryType = "comboTree";
			};
			
			var defaultCheckedHasFind = false;
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
				if (dt.checked) { 
					defaultCheckedHasFind = true;
					scope.cgComboTreeNodeClick(dt);
				}else if(dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						if (defaultCheckedHasFind) {
							defaultCheckedHasFind = false;
							break;
						}else{
							scope.findeDefaultChecked(dt.children[i]);
						}
					}
				}
			};
			
			scope.show_all_items = false;
			scope.changeShowAllItems = function(){
				scope.show_all_items = !scope.show_all_items;
			}
		}
	};
}]);
system.directive('cgMulQueryComm', function() {
	return {
		restrict : 'AE', 
		templateUrl : base
				+ '/static/angular_expand/pc/directives/tpl/multiQueryComm.html',
		scope : {
			source : "=",
			result : "=",
			expand : "@",
			noborder : "@",
			onChange : "&"
		},
		link : function(scope, element, attrs) {
			// 当前查询条件
			scope.result = [];
			
			//树的选择结果
			scope.innerTreeResult = {};
			
			//监听树的选择结果
			scope.$watch('innerTreeResult',function(val) {
				if (val.queryCode) {
					var innerTreeHasPushed = false;
					for ( var i = 0; i < scope.result.length; i++) {
						if (scope.result[i].queryCode == scope.innerTreeResult.queryCode) {
							scope.result.splice(i,1,angular.copy(scope.innerTreeResult));
							innerTreeHasPushed = true;
						}
					}
					if (!innerTreeHasPushed) {
						scope.result.push(angular.copy(scope.innerTreeResult));
					}
				}
			}, true);
			
			// 清空查询条件
			scope.cancleQueryAll = function() {
				scope.result = [];
				for ( var i = 0; i < scope.queryArray.length; i++) {
					var item = scope.queryArray[i];
					if(scope.queryArray[i].queryType == "comboTree"){
						scope.setCheckedOfTreedata(scope.queryArray[i].items);
						scope.queryArray[i].items.dataChangeDate = new Date();
					}else{
						for ( var j = 0; j < item.items.length; j++) {
							item.items[j].checked = false;
						}
					}
				}
			};
			
			//改变树形数据的checked属性
			scope.setCheckedOfTreedata = function(tree){
				tree.checked = false;
				if(tree.children){
					for(var i = 0; i < tree.children.length; i++) {
						var node = tree.children[i];
						scope.setCheckedOfTreedata(node);
					}
				}
			}
			
			
			scope.cancleQuery = function(obj) {
				for ( var i = 0; i < scope.result.length; i++) {
					if(scope.result[i] == obj){
						scope.result.splice(i,1);
					}
				}
				if(obj.queryType == "comboTree"){
					for ( var i = 0; i < scope.queryArray.length; i++) {
						if(scope.queryArray[i].queryCode == obj.queryCode){
							scope.setCheckedOfTreedata(scope.queryArray[i].items);
							scope.queryArray[i].items.dataChangeDate = new Date();
						}
					}
				}else{
					for ( var i in scope.queryArray) {
						var item = scope.queryArray[i];
						if(obj.queryName == item.queryName){
							for ( var j = 0; j < item.items.length; j++) {
								item.items[j].checked = false;
							}
						}
					}
				}
			};

			// 查询条件组合
			scope.queryArray = angular.copy(scope.source);

			// 点击更多后，显示某组条件的每一个条件项
			scope.showAll = function(obj) {
				obj.isAll = true;
				for ( var i in obj.items) {
					obj.items[i].show = true;
				}
			};
			// 高级搜索
			scope.isExpandALL = scope.expand ? true : false;
			scope.expandALL = function() {
				scope.isExpandALL = !scope.isExpandALL;
				for ( var i = 1; i < scope.queryArray.length; i++) {
					scope.queryArray[i].isShow = !scope.queryArray[i].isShow;
				}
			};
			// change
			scope.change = function(item, condition) {
				// 改变选中
				for ( var j = 0; j < condition.items.length; j++) {
					condition.items[j].checked = false;
				}
				item.checked = true;
				// 判断item所属的条件组是否有已选条件在result数组中,如果有将新的替换旧的
				var it = {}, itHasPushed = false;
				angular.copy(item, it);
				it.queryName = condition.queryName;
				it.queryCode = condition.queryCode;
				var array = [];
				for ( var i in scope.result) {
					var tt = scope.result[i];
					// 将新的替换旧的
					if (tt.queryCode != it.queryCode) {
						array.push(tt);
					} else {
						array.push(it);
						itHasPushed = true;
					}
				}
				if (!itHasPushed) {
					array.push(it);
				}
				scope.result = array;
			};
			
			scope.$watch('result',function() {
				// 是否页面显示当前查询条件
				scope.isQuery = scope.result.length > 0 ? true : false;
				scope.onChange({
					$data : scope.result
				});
			}, true);

			scope.$watch('source',function() {
				scope.cancleQueryAll();
				scope.queryArray = angular.copy(scope.source);
				scope.setDefaultChecked();
			}, true);
			
			scope.setDefaultChecked = function(){
				// 遍历条件组合数组，设置默认选中项和默认显示项
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					// 当条件的组数大于1的时候，默认显示两组，其余的组合点击高级查询显示
					if (i < 1) {
						item.isShow = true;
					} else {
						item.isShow = scope.isExpandALL; 
					}
					// 当某组条件的数量大于5的时候，默认显示5个，其余的点击更多后展示
					item.isAll = item.items.length <= 5 ? true : false;
					if (item.queryType != "comboTree") {
						for ( var j in item.items) {
							var inner = item.items[j];
							inner.show = true;
							/*if (i < 1 && j == 0) {
								inner.checked = true;
								scope.change(inner, item);
							}*///默认第一个选中
							if (inner.checked) {
								scope.change(inner, item);
							}
							if (j >= 5) {
								inner.show = false;
							}
						}
					}
				}
				
				/*if (scope.queryArray && scope.result.length == 0 && scope.queryArray.length > 0) {
					var ot = scope.queryArray[0];
					if(ot.items && ot.items.length > 0){
						ot.items[0].checked = true;
						scope.change(ot.items[0],ot);
					}
				}*/
			};
			scope.setDefaultChecked();
			if(!scope.noborder){
				scope.noborder = true;
			}
		}
	};
});/**
 * 拖动指令
 *  首先熟悉drag 事件 (源)dragstart - (目)enter  -  (目)dragover  -  (目)drop/dragleave - (源)dragend
 */
system.directive('cgDrag', [ '$document', '$rootScope','$parse','$timeout',function($document, $root,$parse,$timeout) {
	return {
		restrict : 'A',
		scope : {
			onDropSuccess : "&",
			drag : "="
		},
		link : function(scope, element, attrs) {

			element.attr("draggable", true);
			var dragElm = element[0];
			dragElm.ondragstart = function(ev) {
				try{
					ev.dataTransfer.setData("Text",ev.target.id);
				}catch(e){
					console.log("drag is not avalible!");
				}
				ev.dataTransfer.effectAllowed = "all";
				eleDrag = ev.target;
				$root.dragdata = scope.drag;
				return true;
			};
			dragElm.ondragend = function(ev) {
				//ev.dataTransfer.clearData("text");
				eleDrag = null;
				
				if(attrs.onDropSuccess && $root.dropFlag){
					scope.$apply(function(){
						scope.onDropSuccess({
							$data : scope.drag
						});
					});
				}
				$root.dropFlag = false;
				$root.dragdata = null;
				return false;
			};
		}
	};
}])
/**
 * 接收
 */
.directive('cgDrop', [ '$rootScope','$timeout', function($root,$timeout) {
	return {
		restrict : 'A',
		scope : {
			onDropSuccess : "&"
		},
		link : function(scope, element, attrs) {
			var dropElm = element[0];
			//进入
			dropElm.ondragenter = function(ev) {
				//element.addClass("");
				return true;
			};
			//出去
			dropElm.ondragover = function(ev) {
				ev.preventDefault();
				return true;
			};
			//放下
			dropElm.ondrop = function(ev) {
				if(attrs.onDropSuccess){
					scope.$apply(function(){
						scope.onDropSuccess({
							$data : angular.copy($root.dragdata)
						});
					});
					$root.dropFlag = true;
				}
				//element.removeClass("");
				return false;
			};
			dropElm.ondragleave = function(ev) {
				return false;
			};
		}
	};
} ]);/*************************************************
 下拉框指令 (此指令依赖于bootstrap)
 功能介绍 ： 
	  配置数据显示数据下拉框	
 例子见 ：static/angular_expand/example/dropdown.jsp
 ************************************************/
system.directive('btnDropdown', [function () {
    return {
        restrict: 'AE',
        templateUrl : base + '/static/angular_expand/pc/directives/tpl/btnDropdown.html',
        scope: {
            source: "=",
            onChange:"&",
            displayField:"@",
            btnClass : "@",
            btnStyle : "@"
        },
        link : function(scope, element, attrs) {
        	scope.showDetail = false;
        	
        	scope.toggleDetail = function(){
        		scope.showDetail = !scope.showDetail;
        		if(scope.showDetail == true){
        			var foo = function(event){
                        if ($(element).has(event.target).length < 1) {
                            scope.$apply(function() {
                            	scope.showDetail = false;
                            });
                           $("html").unbind("click",foo);
                        }
                    };
                    $("html").unbind("click",foo).bind('click',foo);
        		}
        	}
        	
        	if(scope.btnClass == '' || scope.btnSuccess == null){
        		scope.btnClass = "btn-default"
        	}
        	
        	scope.selectChange = function(data){
        		scope.showDetail = false;
        		scope.result = data;
        		for ( var i in scope.source) {
					var it = scope.source[i]
					it.checked = false;
				}
        		data.checked = true;
        		
        	}
        	
        	scope.$watch("result",function(newval,oldval){
        		if(newval){
        			scope.onChange({
            			$data : newval
            		});
        		}
        	},true);
        	
        	scope.defaultChecked = function(){
        		for (var i = 0; i < scope.source.length; i++) {
        			var item = scope.source[i];
					if(item.checked){
						scope.selectChange(item);
						break;
					}
				}
        	}
        	scope.$watch("source",function(newval,oldval){
        		if(newval && newval.length > 0){
        			scope.defaultChecked();
        		}
        	},true);
        }
    };
}]);/**
 * 局部遮罩指令
 */
system.directive('partModal',[function() {
	return {
		restrict : 'AE',
		scope: {
			showModal : "="
		},
		link : function(scope, element, attrs) {
			//生成遮罩层
			element.css({
				"position" : "relative"
			});
			var modal = $("<div>"+
					    	"<div style='position: absolute;left: 50%;top: 35%;margin-left: -30px;width: 60px;text-align: center;color: #FFF;'>" +
					    		" <i class='fa fa-spinner fa-pulse fa-2x'></i>" +
					    	"</div>"+
					    "</div>").css({
					    	"background-color" : "#888",
					    	opacity : "0.4"
					    });
			var html = $("<div />").css({
				position : "absolute",
				left : 0,
				top : 0,
			    "z-index" : 999,
			    margin : 0
			});
			modal.appendTo(html);
			element.append(html);
			
			//监听遮罩层的显示控制
			scope.$watch('showModal',function(val){
				if(val){
					modal.css({
				    	height : "inherit",
				    	width : "inherit",
				    });
					html.css({
						height :  "100%",
						width :  "100%",
					})
					html.show();
				}else{
					html.fadeOut('fast');
				}
			});
		}
	}
}]);
/*************************************************
 弹出层form 
 功能介绍 ： 
	  弹出form，分页显示数据
	  
	  config : {
	  	title : "",
		show : false,
		url : "",
		exportUrl : "",// 为空则不显示导出按钮
		heads : [],
		fields : [],
		params : {
			
		}
	  }
 例子见 ：static/angular_expand/example/modalForm.jsp
 ************************************************/
system.directive('modalForm',["httpService",'toastrService',function (http,toastr) {
    return {
        restrict: 'AE',
        templateUrl : base + '/static/angular_expand/pc/directives/tpl/modalForm.html',
        scope: {
        	config: "="
        },
        link : function(scope, element, attrs) {
        	//隐藏form弹出
        	scope.hideModalForm = function(){
        		scope.config.show = false;
        	}
        	//空白地方点击隐藏form
        	element.click(function(e){
        		scope.hideModalForm();
        		scope.$apply();
        	}).find(".modal-dialog").click(function(e){
        		e.stopPropagation();
        	});
        	
        	//监听显示控制，重置分页参数
        	scope.$watch("config.show",function(newval){
        		if(newval == true){
        			element.find(".fade").show().scrollTop(0).addClass("in");
        			scope.vm.curpage = 1;
        			scope.vm.rows = [];
        			scope.vm.total = 0;
        			
        		}else{
        			element.find(".fade").removeClass("in").fadeOut('fast');
        			scope.vm.curpage = 0;
        		}
        	});
        	//分页参数
        	scope.vm = {
    			showModal : false,
    			pagesize: 10,
    			curpage: 0,
    			rows : [],
    			total : 0,
    			pageTotal : 0
        	};
        	//监听分页参数，请求后台数据
        	scope.$watch("vm.curpage",function(newval,oldval){
        		if(newval != 0){
        			scope.vm.showModal = true;
        			scope.queryPageItems(scope.config.params).then(function(data){
        				scope.vm.showModal = false;
        				scope.vm.rows = data.result.rows;
        				scope.vm.total = data.result.total;
        				scope.vm.pageTotal = parseInt(scope.vm.total%scope.vm.pagesize == 0 ? scope.vm.total/scope.vm.pagesize : scope.vm.total/scope.vm.pagesize+1);
        			})
        		}
        	});
        	//请求数据方法
        	scope.queryPageItems = function(params){
        		return http.post({
        			url : scope.config.url,
        			data : angular.extend({
    					curpage : scope.vm.curpage,
        				pagesize : scope.vm.pagesize,
        				sortColumn : scope.sortColumn,
        				sortOrder : scope.sortOrder
    				},params)
        		});
        	}
        	//下载方法
        	scope.download = function(){
        		 $.fileDownload(base + scope.config.exportUrl,{
         			data : angular.extend({
     					curpage : 1,
         				pagesize : scope.vm.total,
         				heads : angular.toJson(scope.config.heads),
         				fields : angular.toJson(scope.config.fields),
         				filename : scope.config.title
     				},scope.config.params),
 				    failCallback: function (html, url) {
 				    	if(html.indexOf("您没有该页面的访问权限") != -1){
 				    		toastr.error("您没有该项权限！");
 				    	}
				    } 
         		});
        	}
        	//列排序方法
        	scope.orderByColumn = function(field){
        		if(scope.sortColumn == field ){
        			if(scope.sortOrder == 'asc')
        				scope.sortOrder = 'desc';
        			else if(scope.sortOrder == 'desc'){
        				scope.sortColumn = '';
        			}
        		}else{
        			scope.sortOrder = 'asc';
        			scope.sortColumn = field;
        		}
        		//请求数据
        		scope.vm.showModal = true;
    			scope.queryPageItems(scope.config.params).then(function(data){
    				scope.vm.showModal = false;
    				scope.vm.rows = data.result.rows;
    			});
        	}
        }
    };
}]);/**
 * 局部遮罩指令
 */
system.directive('selfDefinedYear',[function() {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/selfDefinedYear.html',
		scope: {
			source : "=",
			result : "="
		},
		link : function(scope, element, attrs) {
			var curYear = (new Date()).getFullYear();
			scope.result = {
				start : curYear,
				end : curYear
			};
			scope.$watch("source",function(val){
				if(val==undefined || val.length==0) return;
				else{
					scope.item= {
						result : scope.source[2]
					};
					
					// 设置开始日期下拉框的数据源
					scope.setStartDropDownSource = function(endYear,defaultVal){
						result = [];
						for (var i = endYear; i >= curYear- 15; i--) {
							var year = {
								id : i,
								mc : i +'年'
							}
							if(defaultVal ==i)year.checked=true;
							result.push(year)
						}
						scope.startDateSource = result;
					}
					
					
					
					// 设置结束日期下拉框的数据源
					scope.setEndDropDownSource = function(startYear,defaultVal){
						result = [];
						for (var i = curYear; i >= startYear; i--) {
							var year = {
								id : i,
								mc : i +'年'
							}
							if(defaultVal ==i)year.checked=true;
							result.push(year)
						}
						scope.endDateSource = result;
					}
					
					//开始时间变化
					scope.startYearChange = function(data){
						scope.result.start = data.id;
						scope.setEndDropDownSource(data.id,scope.result.end);
					}
					//结束时间变化
					scope.endYearChange = function(data){
						scope.result.end = data.id;
						scope.setStartDropDownSource(data.id,scope.result.start);
					}
					
					//文字点击
					scope.$watch("item",function(newval){
						scope.result.start = Number(newval.result.start);
						scope.result.end = Number(newval.result.end);
						scope.setStartDropDownSource(Number(newval.result.end),Number(newval.result.start));
						scope.setEndDropDownSource(Number(newval.result.start),Number(newval.result.end));
					},true);
					
					//监控结果
					scope.$watch("result",function(val){
						//如果结果和文字选中的时间不同，则将文字选中置空
						if(val.start != Number(scope.item.result.start) || val.end != Number(scope.item.result.end)){
							scope.item.result = angular.copy(scope.item.result)
						}
						//如果结果选中和文字列表中的有相同，将文字选中
						for ( var i in scope.source) {
							var sc = scope.source[i]
							if(val.start == Number(sc.start) && val.end == Number(sc.end)){
								scope.item.result = sc;
							}
						}
					},true);
				}
			});
			 
		}
	}
}]);
/**
 * Bootstrap提示工具 : toolTip
 */
system.directive('toolTip', [function() {
	return {
		restrict : 'AE',
		transclude : true,
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/tooltip.html',
		scope : {
			placement : "@",
			hideIcon : "@"
		},
		link : function(scope, element, attrs) {
			element.addClass("tooltip-container");
			element.on("mouseenter",function(){
				var eleHeight = element.height();
				var eleWidth = element.width();
				var elePosition = element.position()
				var eleLeft = elePosition.left;
				var eleTop  = elePosition.top;
				
				var content = element.find(".popover");
				var contWidth = content.width();
				var contHeight = content.height();
				
				var contLeft = -200,contTop = -200;
				switch (scope.placement) {
					case 'top':
						contLeft = eleLeft +  (eleWidth - contWidth)/2 - 2;
						contTop = eleTop - contHeight - 4;
						break;
					case 'right':
						contLeft = eleLeft + eleWidth;
						contTop = eleTop + (eleHeight-contHeight)/2 - 4;
						break;
					case 'bottom':
						contLeft = eleLeft + (eleWidth - contWidth)/2 - 2;
						contTop = eleTop + eleHeight - 2;
						break;
					case 'left':
						contLeft = eleLeft - contWidth - 4;
						contTop =  eleTop + (eleHeight-contHeight)/2 - 4;
						break;
					default:
						contLeft = eleLeft + eleWidth - 2;
						contTop = eleTop + (eleHeight-contHeight)/2 - 2;
						break;
				}
				content.css({
					left : contLeft,
					top : contTop
				})
			});
		}
	};
}]);