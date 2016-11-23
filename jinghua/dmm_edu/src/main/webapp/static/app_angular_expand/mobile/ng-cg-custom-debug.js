
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
var jxpgmobile = angular.module('jxpgmobile',[]);
/**
 * highchart service
 * 根据传入的config 装配成highcharts需要的 config
 * renderCommonChart //图表类型(column,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

jxpgmobile.factory('highChartService',function(){
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
});/*************************************************
 图表指令
 ************************************************/
jxpgmobile.directive('cgChart', ['highChartService',function (service) {
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
jxpgmobile.directive("cgStarRating", [ function() {
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
jxpgmobile.directive("cgStarRatingFull", [ function() {
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