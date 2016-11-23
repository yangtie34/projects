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
            	show:false,
                min: 0,
                max : 53000,
                text:['高','低'],           // 文本，默认为数值文本
                calculable : true,
                x: 'left',
                color: ['#fe8e61','#fdd753']
            },
            series : [
                {
                    'name':'人数',
                    'type':'map',
                    'data': config.data
//                     itemStyle:{normal:{areaStyle:{color:'lightskyblue'}}}
                }
            ]
		}
		 return  result;
	},
	renderAreaChart:function(config){
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
