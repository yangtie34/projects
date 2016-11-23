$(function(){
	
	 jQuery.addMapPie=function(pid, data, callbackfn,actionScope) {
		 require.config({
		        paths: {
		        	//这块路径地址不会配置 src/main/webapp/static
		        	'echarts/chart/map': "../../static/js/charts/echarts/dist/chart/map",
		        	'echarts/chart/pie': "../../static/js/charts/echarts/dist/chart/pie"
		        }
		    });
		    require(
		    [
		        'echarts',
		        'echarts/chart/map' ,//按需加载图表关于地图相关的js文件,
		        'echarts/chart/pie' 
		    ],
		        DrawEChart //异步加载的回调函数绘制图表
		    );
		    var myChart;
		  	//创建ECharts图表方法
		    function DrawEChart(ec) {
				 var ecConfig = require('echarts/config');
				 var zrEvent = require('zrender/tool/event');
				 var curIndx = 0;
				 var mapType = [
				     'china',
				     // 23个省
				     '广东', '青海', '四川', '海南', '陕西', 
				     '甘肃', '云南', '湖南', '湖北', '黑龙江',
				     '贵州', '山东', '江西', '河南', '河北',
				     '山西', '安徽', '福建', '浙江', '江苏', 
				     '吉林', '辽宁', '台湾',
				     // 5个自治区
				     '新疆', '广西', '宁夏', '内蒙古', '西藏', 
				     // 4个直辖市
				     '北京', '天津', '上海', '重庆',
				     // 2个特别行政区
				     '香港', '澳门'
				 ];
				 myChart = ec.init(document.getElementById(pid));
				 var dataString=[];
				 var dataArray=[];
				 var maxValue=0;
				 for(var i=0;i<data.length;i++){
					 dataString[i]=data[i].name;
					 dataArray[i]=data[i];
					 var thisVaule=Number(data[i].value);
					 if(maxValue<thisVaule){
						 maxValue=thisVaule;
					 }
				 }
				 var maxValStr=maxValue.toString();
				 var num=Number(maxValStr.substring(0,1))+1;
				 var ten=1;
				 for(var i=0;i<maxValStr.length-1;i++){
					 ten*=10;
				 }
				 maxValue=num*ten;
				 myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
				   var len = mapType.length;
				   var mt = mapType[curIndx % len];
				   var showStr="";
				     if (mt == 'china') {
				         // 全国选择时指定到选中的省份
				         var selected = param.selected;
				         for (var i in selected) {
				             if (selected[i]) {
				                 mt = i;
				                 while (len--) {
				                     if (mapType[len] == mt) {
				                         curIndx = len;
				                     }
				                 }
				                 break;
				             }
				         }
				         option.tooltip.formatter = '点击返回全国<br/>{b},{c}人';
				         showStr=mt;
				     }
				     else {
				         curIndx = 0;
				         mt = 'china';
				         showStr="全国";
				         option.tooltip.formatter = '点击进入该省<br/>{b},{c}人';
				     }
				     var outdata={};
				     outdata.myChart=myChart;
				     outdata.mt=mt;
				     callbackfn.call(actionScope, outdata);
				     option.series[0].mapType = mt;
				     option.title.subtext = showStr + '人数分布';
				     myChart.setOption(option, true);
				 });
				 var option = {
				     title: {
				         text : '学生人数分布图',
				         subtext : '全国人数分布'
				     },
				     tooltip : {
				         trigger: 'item',
				         formatter: '点击进入该省<br/>{b},{c}人'
				     },
				     legend: {
				       show:false,
				         x:'center',
				        y :'bottom',
				         data:dataArray
				     },
				     dataRange: {
				         min: 0,
				         max: maxValue,
				         color:['orange','yellow'],
				         text:['高','低'],           // 文本，默认为数值文本
				         calculable : true
				     },
				     series : [
				         {
				             name: '学生人数',
				             type: 'map',
				             mapType: 'china',
				             selectedMode : 'single',
				             mapLocation: {
				                 x: '130'
				             },
				             itemStyle:{
				                 normal:{label:{show:true}},
				                 emphasis:{label:{show:true}}
				             },
				             data:dataArray
				         },
				         {
				             name:'学生人数',
				             type:'pie',
				             roseType : 'area',
				             tooltip: {
				                 trigger: 'item',
				                 formatter: "{a} <br/>{b} : {c} ({d}%)"
				             },
				             center: [document.getElementById(pid).offsetWidth - 250, 225],
				             radius: [30, 120],
				             data:dataArray
				         }
				     ]
				 };
				 myChart.setOption(option); 
			//	 successfn.call(actionScope, d);
			}
		    alert("这里需要变成同步的加载，而不是异步");
         return myChart;
    };
    jQuery.reloadMapPie=function(myChart, data) {
    	var dataString=[];
    	var dataArray=[];
    	var maxValue=0;
		 for(var i=0;i<data.length;i++){
			 dataString[i]=data[i].name;
			 dataArray[i]=data[i];
			 var thisVaule=Number(data[i].value);
			 if(maxValue<thisVaule){
				 maxValue=thisVaule;
			 }
		 }
		 var maxValStr=maxValue.toString();
		 var num=Number(maxValStr.substring(0,1))+1;
		 var ten=1;
		 for(var i=0;i<maxValStr.length-1;i++){
			 ten*=10;
		 }
		 maxValue=num*ten;
		 var option=myChart.getOption();
		 option.legend.data = dataString;
		 option.series[0].data = dataArray;
		 option.series[1].data = dataArray;
		 option.dataRange.max=maxValue;
		 myChart.setOption(option, true);
    };
    
});