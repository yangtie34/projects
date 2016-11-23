/**
 * 
 */


var myApp=angular.module("myApp",[]);


myApp.controller("myCtrl",function($scope){
	
	$scope.msg="this heat nan's blog";

	
});

myApp.filter("myUpperFilter",function(){

    return function (input){
        return input.toUpperCase();

    }
});

myApp.directive("eline",function(){
    return {
        restrict:'E',
        link : function(scope,element,attrs){
			var defaults = {
				trigger:'axis',
				type:'line',
				pid:'1',
				title_text:'线图',
				title_subtext:'这个一个线图',
				series_name:'当前',
				mark_show:false,
				dataView_show:false,
				dataView_readOnly:true
			};
			
			var options = $.extend(defaults, attrs);
			var obj=eval('('+options.data+')');
            options.dataObject=obj;
			
			var dataString=[];
			var datalable=[];
			var seriesObject=[];
			for(var i=0;i<options.dataObject.length;i++){
				dataString[i]=options.dataObject[i].name;
				datalable=options.dataObject[i].label;
				var series={
							name:options.dataObject[i].name,
							type:options.type,
							data:options.dataObject[i].value,
							markPoint:{data : [ {type : 'max', name: '最大值'},{type : 'min', name: '最小值'} ]},
							markLine:{data : [{type : 'average', name: '平均值'} ] }
					};
				seriesObject[i]=series;
				
			}
			var myChart = echarts.init(document.getElementById(options.pid)); 
			option = {
				title : {
					text: options.title_text,
					subtext: options.title_subtext
				},
				tooltip : {
					trigger: options.trigger
				},
				legend: {
					data:dataString
				},
				toolbox: {
					show : true,
					feature : {
						mark : {show: options.mark_show},
						dataView : {show: options.dataView_show, readOnly: options.dataView_readOnly},
						magicType : {show: true, type: ['line', 'bar']},
						restore : {show: true},
						saveAsImage : {show: true}
					}
				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						boundaryGap : false,
						data : datalable
					}
				],
				yAxis : [
					{
						type : 'value',
						axisLabel : {
							formatter: '{value} °C'
						}
					}
				],
				series : seriesObject
			};
                    
 // 为echarts对象加载数据 
        myChart.setOption(option); 
        }
    }

})
myApp.directive("epie",function(){
    return {
        restrict:'E',
        link : function(scope,element,attrs){
			var defaults = {
				pid:'1',
				title_text:'饼状图',
				title_subtext:'这个一个饼状图',
				title_x:'center',
				series_name:'当前',
				mark_show:false,
				dataView_show:false,
				dataView_readOnly:true
			};
			var options = $.extend(defaults, attrs);
			var obj=eval('('+options.data+')');
            options.dataObject=obj;
			var dataString=[];
			var dataArray=[]
			for(var i=0;i<options.dataObject.length;i++){
				dataString[i]=options.dataObject[i].name;
				dataArray[i]=options.dataObject[i];
			}
			var myChart = echarts.init(document.getElementById(options.pid)); 
			option = {
				title : {
					text: options.title_text,
					subtext: options.title_subtext,
					x:options.title_x
				},
				tooltip : {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					orient : 'vertical',
					x : 'left',
					data:dataString
				},
				toolbox: {
					show : true,
					feature : {
						mark : {show: options.mark_show},
						dataView : {show: options.dataView_show, readOnly: options.dataView_readOnly},
						magicType : {
							show: true, 
							type: ['pie', 'funnel'],
							option: {
								funnel: {
									x: '25%',
									width: '50%',
									funnelAlign: 'left',
									max: 1548
								}
							}
						},
						restore : {show: true},
						saveAsImage : {show: true}
					}
				},
				calculable : true,
				series : [
					{
						name:options.series_name,
						type:'pie',
						radius : '55%',
						center: ['50%', '60%'],
						data:dataArray
					}
				]
			};
                    
 // 为echarts对象加载数据 
        myChart.setOption(option); 
        }
    }

})