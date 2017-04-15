/*************************************************
图表指令
************************************************/
app.directive('stuChart', [function () {
	var effect=['spin' , 'bar' , 'ring' , 'whirling' , 'dynamicLine' ];
	var effectIndex=0;
   return {
       restrict: 'AE',
       scope: {
           config: "=",
        //	chart:  "="
       },
       link : function(scope, element, attrs) {
           scope.renderChart = function(){
        	  var myChart = echarts.init(element.context,echarts.config.theme||'macarons');
        	/* effectIndex = ++effectIndex % effect.length;
        	   scope.myChart.showLoading({
        	       text : '正在加载...',//effect[effectIndex],
        	       effect : effect[effectIndex],
        	       textStyle : {
        	           fontSize : 10
        	       }
        	   });*/
            		scope.config.calculable = true;
              	 	if(scope.config.event){
              	 		scope.config.calculable = false;
              	 		myChart.on(echarts.config.EVENT.CLICK,scope.config.event);
							/*	function(param) {
									console.log(param);
									 var mes = { seriesIndex :  param.seriesIndex,
        										dataIndex : param.dataIndex,
												}
								};*/
              	 	};
              	 	//scope.myChart.setOption(scope.config);
              	 	//setTimeout(function (){
              	 		myChart.setOption(scope.config);
              	 		//scope.myChart.hideLoading();
              	 	//},0);
                     window.addEventListener("resize", function () {
                    	 myChart.resize();
                     });
                     
                     if(scope.config.dsjtext){
                    	 element.append(scope.config.dsjtext);
                     }
               }
           scope.$watch("config",function(){
        	   if(scope.config!=null){
        		   scope.renderChart();
        		//   scope.chart=scope.myChart;
        	   }
           });    
           }
   };
}]);