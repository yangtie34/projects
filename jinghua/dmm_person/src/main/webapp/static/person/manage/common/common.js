var echartsgetsex=function(d){
	var data=d.data;
	var all=data[0]+data[1];
	var dataStyle = {
		    normal: {
		        label: {show:false},
		        labelLine: {show:false}
		    }
		};
		var placeHolderStyle = {
		    normal : {
		        color: 'rgba(0,0,0,0)',
		        label: {show:false},
		        labelLine: {show:false}
		    },
		    emphasis : {
		        color: 'rgba(0,0,0,0)'
		    }
		};
		var option = {
		    title: {
		        text: '男女比例',
		        subtext: '共'+all+'人',
		        //sublink: 'http://e.weibo.com/1341556070/AhQXtjbqh',
		        x: 'center',
		        y: 'center',
		        itemGap: 20,
		        textStyle : {
		            color : 'rgba(30,144,255,0.8)',
		            fontFamily : '微软雅黑',
		            fontSize : 20,
		           // fontWeight : 'bolder'
		        }
		    },
		    tooltip : {
		        show: true,
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		       x : '50%',
		        y : '10%',
		        itemGap:5,
		        data:['男','女']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		           /* mark : {show: true},
		            dataView : {show: true, readOnly: false},*/
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            //name:'1',
		            type:'pie',
		            clockWise:false,
		            radius : [ '65%','80%'],
		            itemStyle : dataStyle,
		            data:[
		                {
		                    value:data[0],
		                    name:'男'
		                },
		                {
		                    value:data[1],
		                    name:'invisible',
		                    itemStyle : placeHolderStyle
		                }
		            ]
		        },
		        {
		            //name:'2',
		            type:'pie',
		            clockWise:false,
		            radius : [ '50%','65%'],
		            itemStyle : dataStyle,
		            data:[
		                {
		                    value:data[1], 
		                    name:'女'
		                },
		                {
		                    value:data[0],
		                    name:'invisible',
		                    itemStyle : placeHolderStyle
		                }
		            ]
		        }
		    ]
		};
		if(d.toolbox==false){
			option.toolbox.show=false;
		}
		 return option;                   
}
app.factory('echartService',function(){
return {
	renderMapChart:function(config){
		var result = {
            tooltip : {'trigger':'item', textStyle:{  align:'left'}},
            toolbox : {
                'show':true, 
                'feature':{
                    'mark':{'show':false},
                    'dataView':{'show':false,'readOnly':false},
                    'restore':{'show':false},
                    'saveAsImage':{'show':true}
                }
            },
            dataRange: {
            	//show:false,
                min: 0,
                max : 1000,
                text:['高','低'],           // 文本，默认为数值文本
                calculable : true,
                x: 'left',  y: 'bottom',
                calculable : true,color: ['#fe8e61','#fdd753']
            },
            series : [
                {     itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                },
                    'name':'人数',
                    'type':'map',
                    'data': config.data
//                     itemStyle:{normal:{areaStyle:{color:'lightskyblue'}}}
                }
            ]
		}
		 return  result;
	},
	renderAreaChart:function(config) {
		      var isName ={} , isField = {};
            var fields = [],series = [];legends=[]; var color = ['#65d8d4','#918ecf','#fecb69'];
            for(var i=0;i<config.data.length;i++){
                var tar = config.data[i];
                if(!isName[tar.NAME]){
                    series.push({name : tar.NAME,type:config.series.type,stack:config.series.stack,smooth:true, itemStyle: {normal: {areaStyle: {type: 'default'},color:''}},data : []});
                    legends.push(tar.NAME);
                    isName[tar.NAME] = true;
                }
                if(!isField[tar.FIELD]){
                    fields.push(tar.FIELD);
                    isField[tar.FIELD] = true;
                }
            }
            if(config.isSort) fields.sort(function(a,b){return a>b?1:-1;});
            if(config.legendSort) legends.sort(function(a,b){return a<b?1:-1;});
            var ser,fie,dat; 
            for ( var j=0;j<series.length;j++) {
                ser = series[j];
                if (config.type=='area'){
                
                }else if (config.type=='column') {
               
                	ser.itemStyle.normal.color = color[j];
                }
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < config.data.length; m++){
                        dat = config.data[m];
                        if(dat.NAME == ser.name && dat.FIELD == fie){
                            ser.data.push(parseFloat(dat.VALUE));
                        }
                    }
                    if (ser.data.length < k+1) {
                        ser.data.push(0);
                    }
                }
            }
          var end = 0;
         if(config.big=='10'){
         	if(fields.length >=10){
         	end = parseFloat(eval((10/fields.length)*100))
         	}else if(fields.length<10){
         	end = 100	
         	}
         }else if(config.big=='4'){
         	if(fields.length>=4){
         	end = parseFloat(eval((4/fields.length)*100))
         	}else if (fields.length<4){
         		end = 100
         	}
         }else{
         	end = config.dataZoom.end
         }
		var result = {
				title : config.title||null,
			tooltip : {
           trigger: 'axis',
           textStyle:{  align:'left'},
           axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type :config.axisPointer.type       // 默认为直线，可选为：'line' | 'shadow'
        }   
    },
    legend: {
    	show: config.legend.show,
        data:legends
    },
    toolbox:config.toolbox,
    calculable : true,
    xAxis : [
        {
        	name :config.xAxis[0].name,
            type : 'category',
            boundaryGap : config.xAxis[0].boundaryGap,
            data : fields
        }
    ], 
      yAxis : [
        {
        	name :config.yAxis[0].name,
            type : 'value'
        }
    ],
    dataZoom : {
        show : config.dataZoom.show,
        realtime : config.dataZoom.realtime,
        start :  config.dataZoom.start,
        end : end
     },
    series : series
		}
		 return  result;
	},
	//学历组成
renderPieChart:function(config){
	var result={
	    tooltip : {
	        trigger: config.tooltip.trigger,
	        formatter: config.tooltip.formatter
	    },
	     toolbox : {
             'show':true, 
             'feature':{
                 'mark':{'show':false},
                 'dataView':{'show':false,'readOnly':false},
                 'restore':{'show':false},
                 'saveAsImage':{'show':true}
             }
         },
	    series :
	    [
	        {
	            name:config.series[0].name,
	            type:'pie',
	            radius : config.series[0].radius,
	           // center: config.series[0].center,
	            data:config.series[0].data
	        }
	    ]}
	return result;
},

renderColumnChart:function(config){
		      var isName ={} , isField = {};
            var fields = [],series = [];legends=[];var max = 0;
               var color = ['#65d8d4','#918ecf','#fecb69'];
            for(var i=0;i<config.data.length;i++){
                var tar = config.data[i];
                if(!isName[tar.NAME]){
                    series.push({name : tar.NAME,type:config.series.type,stack:config.series.stack,smooth:true, itemStyle: {normal: {areaStyle: {type: 'default'},label:{show: config.series.itemStyle.normal.label.show, position: 'insideRight'},color:''}},data : []});
                    legends.push(tar.NAME);
                    isName[tar.NAME] = true;
                }
                if(!isField[tar.FIELD]){
                    fields.push(tar.FIELD);
                    isField[tar.FIELD] = true;
                }
            }
            var ser,fie,dat; 
            for ( var j=0;j<series.length;j++) {
                ser = series[j];ser.itemStyle.normal.color = color[j];
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < config.data.length; m++){
                        dat = config.data[m];
                        if(dat.NAME == ser.name && dat.FIELD == fie){
                            ser.data.push(parseFloat(dat.VALUE));
                        }
                    }
                    if (ser.data.length < k+1) {
                        ser.data.push(0);
                    }
                }
            }
		var result = {
			tooltip : {
           trigger: 'axis',
           textStyle:{align:'left'},
           axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type :config.axisPointer.type       // 默认为直线，可选为：'line' | 'shadow'
        }   
        
    },
    legend: {
        data:legends
    },
    grid :config.grid,
    toolbox:config.toolbox,
    calculable : true,
    xAxis : [
         {
        	name :config.xAxis[0].name,
            type : 'value',
            max :config.xAxis[0].max,
            min : config.xAxis[0].min
        }
    ], 
      yAxis : [
          {
        	name :config.yAxis[0].name,
            type : 'category',
            boundaryGap : config.yAxis[0].boundaryGap,
            data : fields
        }
    ],
    series : series
		}
		 return  result;
	}
	


};
	
	
});
/*************************************************
图表指令
************************************************/
app.directive('stuChart', ['echartService',function (service) {
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
                           case 'column' :;
                           case 'line':;
                           case 'area':
                               var myChart = echarts.init(element.context,'macarons');
                            	 myChart.setOption(service.renderAreaChart(scope.config));
                            	  break;
                           case 'columnf':
                           var myChart = echarts.init(element.context,'macarons');
                            	 myChart.setOption(service.renderColumnChart(scope.config));
                            	  break;
                           case 'areaspline':;
                           case 'map' :
                            	var myChart = echarts.init(element.context,'macarons');
                            	myChart.setOption(service.renderMapChart(scope.config));
                               break;
                           case 'sex' :
                            if(scope.config.click){
                            	var fileLocation = './../static/js/charts/echarts/dist/';
                            	require.config({
                                    paths:{ 
                                        echarts: fileLocation,
                                        'echarts-all': fileLocation+'echarts-all',
                                    }
                                });
                            	require(
                            	        ['echarts',
                            	          'echarts-all'
                            	        // 'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
                            	        ],
                            	        function (echarts) {
                            	        	var ecConfig = require('echarts/config');
                            	        	var myChart = echarts.init(element.context,'macarons');
                                          	myChart.on(ecConfig.EVENT.CLICK, scope.config.click);
                                      		myChart.setOption(echartsgetsex(scope.config)); 	
                            	        })
                              
                              	}else{
                              	 	var myChart = echarts.init(element.context,'macarons');
                              		myChart.setOption(echartsgetsex(scope.config));
                              	}
                              break;
                           case 'pie':
                               var myChart = echarts.init(element.context,'macarons');
                          	 myChart.setOption(service.renderPieChart(scope.config));
                          	 break;
                           default :
                               break;
                       }
                   } else{
//                       scope.config = angular.extend(scope.config,{
//                           credits : {         // 不显示highchart标记
//                               enabled : false
//                           }
//                       });
//                       element.echarts(scope.config);
                	  NewSexView($('.male_female'),[123, 22]);// scope.config);
                 }
                     window.addEventListener("resize", function () {
                     myChart.resize();

                 });
               }
                 
           };
           scope.$watch("config",function(){
               scope.renderChart();
           });
       }
   };
}]);