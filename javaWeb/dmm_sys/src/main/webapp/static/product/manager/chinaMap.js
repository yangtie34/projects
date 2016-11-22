$(function(){
	/**
	 * 初始化时，var map=new jQuery.ChinaMap(dom,data,callback,scope);
	 * 更改数据时 map.draw(data,mapType);
	 * mapType:全国时，传'china'，各省份时，传省份的中文名称
	 * data格式：[{name:省份名称,value:val},{},...]
	 */
	 jQuery.ChinaMap=function(dom, data, callbackfn,actionScope) {
	 	var me=this;
		if(typeof dom=='string'){
			dom=document.getElementById(dom)
		}
		this.chart=echarts.init(dom);
		var dataMap={};
		$.each(data,function(i,o){
			dataMap[o.name]=o;
		});
		this.chart.on('mapSelected', function(params){
			var cur=me.isLevel0(params.target);
			var mapType='china';
			var sourceId=null;
			if(cur>=0){
				mapType=params.target;
				me.level++;
				sourceId=dataMap[mapType].id;
			}else{
				me.level=0;
			}
			me.draw(data,mapType);
			if(callbackfn){
				callbackfn.call(actionScope,{level:me.level,mapType:mapType,sourceId:sourceId || 0});
			}
		});
		this.draw(data,'china');
    };
    
    jQuery.ChinaMap.prototype={
    	config:function(option){
    		this.option=option; 
    	},
		draw:function(dataArray,mapType){
			 var maxValue=0;
			 $.each(dataArray,function(i,o){
			 	if(o.value){
			 		maxValue=maxValue<Number(o.value)?Number(o.value):maxValue;
			 	}
			 	
			 });
			 
			 var maxValStr=maxValue.toString();
			 var num=(parseInt(maxValStr.substring(0,1))+1)+"";
			 var ten=1;
			 for(var i=0;i<maxValStr.length-1;i++){
				 num=num+'0';
			 }
			 var option = {
			     tooltip : {
			         trigger: 'item',
			         formatter: '{b},{c}人'
			     },
			     dataRange: {
			         min: 0,
			         max: num || 3000,
			         color:['red','yellow'],
			         text:['高','低'],           // 文本，默认为数值文本
			         precision:0,
			         calculable : true
			     },
			     series : [
			         {
			             name: '学生人数',
			             type: 'map',
			             mapType: mapType || 'china',
			             selectedMode : 'single',
			             mapLocation: {
			                 x: '130'
			             },
			             itemStyle:{
			                 normal:{label:{show:true}},
			                 emphasis:{label:{show:true}}
			             },
			             data:dataArray
			         }
			     ]
			 };
			 this.chart.setOption(option, true); 
				 
		},
		isLevel0:function(str){	
			var mapType=['china','广东', '青海', '四川', '海南', '陕西', '甘肃', '云南', '湖南', '湖北', '黑龙江',
				  '贵州', '山东', '江西', '河南', '河北','山西', '安徽', '福建', '浙江', '江苏', '吉林', '辽宁', '台湾',
				  '新疆', '广西', '宁夏', '内蒙古', '西藏','北京', '天津', '上海', '重庆','香港', '澳门'];
			for(var i=0;i<mapType.length;i++){
				if(str==mapType[i]){
					return i;
				}
			}
			return -1;
		},
		level:0
    }
});