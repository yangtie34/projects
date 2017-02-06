/*************************************************
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
 
 事件： config中添加config.on
 config : {
	 config : {
	 	on : ['CLICK',fn] // 格式1
	 	on : [['CLICK',fn],['HOVER',fn]] // 格式2
	 }
 }
 fn : function(param, obj, data){ } [echart原生参数, 组件解析出的数据对象, 传入指令的所有源数据]
 
 空数据提示：config中添加 config.noDataText
 config : {
 	config : {
 		noDataText : '暂无性别分布数据'
 	}
 }
 ************************************************/
system.directive('echart', ['echartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "=",
            height: "@",
            ecClick:"&"
        },
        link : function(scope, element, attrs) {
        	var myChart = null, height = 350;
        	var initChart = function(type, option){
        		if ($(element).height() == 0) {
        			if(type == null && option && option.series){
        				var type2;
        				for(var i=0,len=option.series.length; i<len; i++){
        					var type_one = option.series[i].type;
        					if(i == 0) type2 = type_one;
        					else if(type2 != type_one){
        						type2 = null;
        						break;
        					}
        				}
        				if(type2 != null) type = type2;
        			}
        			switch (type) {
                        case 'pie' :
	    					height = 300;
	    					break;
        				default :
        					break;
        			}
            		scope.height = height;
            		element.height(scope.height);
        		}
            	// 基于准备好的dom，初始化echarts实例
            	myChart = myChart || echarts.init(element[0], 'macarons');
            	myChart.showLoading({
            	    text: '数据加载中...'    //loading话术
            	});
        	}
        	var hideLoading = function(){
        		if(myChart) myChart.hideLoading();
        	}
            scope.renderChart = function(){
                if(scope.config){
                	var type = scope.config ? scope.config.type : null;
                	initChart(type, scope.config);
                	hideLoading();
                	var options = {};
                    if(type){
                        switch (type){
                            case 'line':     
                            case 'bar' : ;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                            	options = service.renderCommonChart(scope.config);
                                break;
                            case 'pie' :
                            	options = service.renderPieChart(scope.config);
                            	break;
                            case 'map' :
			                    options = service.renderMapChart(scope.config);
                            	break;
                            case 'venn':
                            	options = service.renderVennChart(scope.config);
                            	break;
                            default :
                            	options = scope.config;
                                break;
                        }
                    } else {
                    	options = scope.config;
                    }
                    options.color = options.color ? options.color : [ 
          						'#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80',
          						'#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
          						'#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
          						'#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
          					];
                    /**
                     * 处理自定义配置项-无数据说明（2016-09-23） （2016-09-26修改完善）
                     */
                    var noDataText = null;
                    if(scope.config.noDataText){
                    	noDataText = scope.config.noDataText;
                    }else if(scope.config.config){
                    	if(scope.config.config.noDataText){
                    		noDataText = scope.config.config.noDataText;
                    	}
                    }
                 	/**
                 	 * 格式化option数据（统一配置），必须接收
                 	 * scope.config.config  -> 源config中的config
                 	 * options.config  -> 处理之后option中保留的config（Ice.fn...）
                 	 */
                    var _config_ = (scope.config.config || options.config) || {};
                    _config_.noDataText = noDataText;
                    options = service.formatOptionSetting(options, _config_);
                 	type = options.series[0].type;
                 	scope._option_ = options; // 缓存option
                 	/** 判断是否需要绑定事件 */
                 	var oldClickFn = attrs.ecClick;
                 	var eventAry = options.on || [];
                 	scope.eventHaveBinded = (oldClickFn!=null || (eventAry && eventAry.length > 0)) && scope.eventHaveBinded!=false ? true : false;
                 	if(scope.eventHaveBinded){
                 		if(!(eventAry[0] instanceof Array)){
                 			eventAry = [eventAry];
                 		}
                 		require.config({ 
            				paths: { 'echarts': './static/echarts/2' }
                        });
                        require(['echarts' ]);
                        scope.ecConfig = require('echarts/config');
                        scope.zrEvent = require('zrender/tool/event');
        				if(oldClickFn){
        					var oldClickFnAry = null;
	                 		switch (type){
	                 			 case 'line':     
				                 case 'bar' :
				                 case 'area':
				                 case 'spline':
				                 case 'areaspline':
				                	 oldClickFnAry = ['CLICK', scope.ecClick, true];
			                         break;
			                     case 'map' :
			                    	 oldClickFnAry = ['MAP_SELECTED', scope.ecClick, true];
			                         break;
		 	                }
                 			eventAry.push(oldClickFnAry);
        				}
	                	for(var i=0,len=eventAry.length; i<len; i++){
	                		var ary = eventAry[i];
		                	scope.on(ary[0], ary[1], ary.length==3?ary[2]:null);
	                	}
                 	}
                    myChart.setOption(options, true); // 必须要用true，因为默认是合并参数，如果是刷新操作，则不能用合并
                }
                $(window).resize(function(){
                	if(myChart) myChart.resize();
                });
            };
            scope.$watch("config",function(){
                scope.renderChart();
            },true);
            scope.on = function(eventKey, fn, isOld){
            	var eventKey = eventKey, key_list = 'list';
            	myChart.on(scope.ecConfig.EVENT[eventKey], function (param){
            		if(eventKey == 'MAP_SELECTED')
            			param = service.mapclick(param.target);
            		var obj = param.data, seriesIndex = param.seriesIndex, dataIndex = param.dataIndex, data;
            		if(eventKey == 'CLICK'){
	            		// 返回三个参数 (param, obj, data)(原生param, 处理之后的obj, 源数据data)
	            		var option = scope._option_, type = option.series[seriesIndex].type;
	            		data = option.config ? option.config._data_ : {};
	            		if(type == 'bar' || type == 'line'){
	            			if(data instanceof Array && data.length > dataIndex){
	        					obj = data[dataIndex];
	        					if(obj[key_list] && obj[key_list] instanceof Array && obj[key_list].length > seriesIndex){
	        						obj.data = obj[key_list][seriesIndex];
	        					}
	            			}
	            		}
            		}
            		if(fn)
            			if(isOld) fn({param:param,obj:obj,data:data});
            			else fn(param, obj, data);
				});
        		scope.eventHaveBinded = false;
            }
        }
    };
}]);