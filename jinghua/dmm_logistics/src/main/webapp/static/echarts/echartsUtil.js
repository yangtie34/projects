var echarColor= [
                 '#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80',
                 '#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
                 '#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
                 '#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
             ];
var optionid=0;
	/*
	 * 二维饼状图数据解析
	 */
	var echardata2d =function(data) {
		var legends = [];
		var datas = [];
		for (var i = 0; i < data.length; i++) {
			legends.push(data[i].field||'未维护');
			datas.push({
				value : data[i].value||0,
				name : data[i].field||'未维护',
				nameCode : data[i].fieldCode||''
//				 itemStyle:{
//	                  normal:{color:echarColor[i%5]}
//	              }
			});
		}

		var ret = [];
		ret[0] = legends;
		ret[1] = datas;
		data[0]=data[0]||{};
		ret[2] = data[0].name||'未维护';
		return ret;
	};
	/*
	 * 三维柱状图解析数据
	 */
	var echardata3d = function(datas, typ) {
		var yxDatas = [];
		var yxMap = {}; //存放院系的数据
		var legentMap = {}; //中间数据
		var legent = []; //图例
		var series = [];
		for (var i = 0; i < datas.length; i++) {
			datas[i].name=datas[i].name||'未维护';
			var data = datas[i];
			var yx = data.field||'未维护';
			if (yxMap[yx] == null) {
				yxMap[yx] = {};
				yxDatas.push(yx);
			}
			yxMap[yx][data.name] = data;
			if (legentMap[data.name] == null) {
				legentMap[data.name] = {
					name : data.name,
					nameCode : data.nameCode||'',
					type : typ,
					data : [],
					dataCode:[]
				};
				series.push(legentMap[data.name]);
				legent.push(data.name);
			}

		}
		for (var i = 0; i < legent.length; i++) {
			var leg = legent[i];
			for ( var k=0;k< yxDatas.length;k++) {
				var yx=yxDatas[k];
				var yxData = yxMap[yx];
				if (yxData[leg] == null) {
					legentMap[leg].data.push(0);
				} else {
					legentMap[leg].data.push(yxData[leg].value||0);
					legentMap[leg].dataCode.push(yxData[leg].fieldCode||'');
				}
			}
		}
		if(series.length==0){
			series.push({
				name : '0',
				type : typ,
				data : []
			});
		}
		var ret = [];
		ret[0] = legent;
		ret[1] = yxDatas;
		ret[2] = series;
		return ret;
	};
	/*
	 * 三维雷达图解析数据
	 */
	var echardataldt = function(datas) {
		
		var maxvalue=0;
		for(var i=0;i<datas.length;i++){
			if(Number(datas[i].value)>maxvalue){
				maxvalue=Number(datas[i].value||0);
			}
		}
		var yxDatas = [];
		var yxMap = {}; //存放院系的数据
		var legentMap = {}; //中间数据
		var legent = []; //图例
		var series = [];
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			data.name=data.name||'未维护';
			var yx = data.field||'未维护';
			if (yxMap[yx] == null) {
				yxMap[yx] = {};
				yxDatas.push({
					text: yx, max: maxvalue
				});
				}
			
			yxMap[yx][data.name] = data;
			if (legentMap[data.name] == null) {
				legentMap[data.name] = {
					name : data.name,
					value : []
				};
				series.push(legentMap[data.name]);
				legent.push(data.name);
			}
		}
		
		for (var i = 0; i < legent.length; i++) {
			var leg = legent[i];
			for ( var yx in yxMap) {
				var yxData = yxMap[yx];
				if (yxData[leg] == null) {
					legentMap[leg].value.push(0);
				} else {
					legentMap[leg].value.push(Number(yxData[leg].value));
				}
			}
		}
		var seriess=[];
		seriess.push({
			name:'',
			type:'radar',
			data:series
		});
		if(seriess.length==0){
			seriess.push({
				name : '',
				type : 'radar',
				data : []
			});
		}
		var ret = [];
		ret[0] = legent;
		ret[1] = yxDatas;
		ret[2] = seriess;
		return ret;
	};
	/*
	 * 内外多级饼状图
	 */
	var datadj = function(data) {
		var legends = [];
		var datas1 = [];
		var datas2 = [];
		for (var i = 0; i < data.length; i++) {
			legends.push(data[i].field);
			if (data[i].cc == '内') {
				datas1.push({
					value : data[i].counts,
					name : data[i].field
				});
			} else {
				datas2.push({
					value : data[i].counts,
					name : data[i].field
				});
			}

		}

		var ret = [];
		ret[0] = legends;
		ret[1] = datas1;
		ret[2] = datas2;
		return ret;
	};
	
	var fomatOption=function(option,title){
	option.title = {
			        text: title,
			        x:'left'
			    };
	if(option.legend.data.length>5){
		var aaa=option.title.text;
		var a="";
		for(var i=0;i<aaa.length;i++){
			a+=aaa.substr(i,1)+"\n";
		}
		option.title.text=a;
		option.title.y=50;
	}
	if(option.xAxis){
		option.tooltip={
				trigger: 'axis'
		};
	}else{
		option.tooltip={
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    };
	}
	
	option.noDataLoadingOption ={
		text :title+'暂无数据',
	    effectOption : null,
	    effect : 'bubble',
	    effectOption : {
	    	effect : {n:'0'}
	    }};
	option.toolbox={
	        show : true,
	      orient : 'vertical',
	        feature : {
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    };
	optionid++;
	option.optionid=optionid;
	return option;
};
var fomatSwtDw=function(option,name,dw){
	option.yAxis=[ {
		 name : name,
			type : 'value',
			axisLabel : {
                formatter: '{value}'+dw
            }
		} ];
	return option;
};
var fomatxqs=function(option){//趋势图只显示线
	option={
	noDataLoadingOption: option.noDataLoadingOption,
	series: option.series,
	title: option.title,
	tooltip:  option.tooltip,
	grid:{borderWidth:0,
		 x:0,x2:0,//y:0,y2:0
	 },
	xAxis:option.xAxis,
	yAxis: option.yAxis,
	axis:{splitLine:{show:false}},
	yxis:{splitLine:{show:false}}
	};
	/*option.dataZoom.show=false;
	option.toolbox={};
	option.legend={};
	option.xAxis[0].show=false;
	option.yAxis[0].show=false;
	option.grid={borderWidth:0};*/
	//option.xAxis[0].splitLine={show:false};
	option.xAxis[0].splitLine={show:false};
	option.yAxis[0].splitLine={show:false};
	option.yAxis[0].show=false;
	option.series[0].markPoint = {
        data : [
                {type : 'max', name: '最大值'},
                {type : 'min', name: '最小值'}
            ]
        };
	option.theme="blue";
	return option;	
};
var fomatbztzx=function(option){//饼状图不显示折线
	option.series[0].itemStyle={ 
			normal: {
        label: {
            show: false
        },
        labelLine: {
            show: false
        }
    }
	};
	option.legend.orient = 'horizontal';
	option.legend.x="center";
	option.toolbox={};
	return option;
}
	var getOption=function(data,title,type){
		var option={};
		switch(type){
		case 'bztwz'://饼状图无折线
		case 'bzt'://饼状图
			option=fomatOption(echarbzt(data),title);
			option.calculable = true;
			break;
//		case 'bztwz'://饼状图无折线
//			option=fomatbztzx(fomatOption(echarbzt(data),title));
//			break;
		case 'mgt'://玫瑰图
			option=fomatOption(echarmgt(data),title);
			break;
		case 'hxt'://环形图
			option=fomatOption(echarbzhx(data),title);
			break;
		case 'sht'://内外环形图
			option=fomatOption(echardjbzt(data),title);
			break;
		case 'jzt'://金字塔图
			option=fomatOption(echarjzt(data),title);
			break;
		case 'ldt'://雷达图
			option=fomatOption(echarldt(data),title);
			break;
		case 'zzt'://柱状图
			option=fomatOption(echarswt(data,"bar"),title);
			break;
		case 'hzzt'://横柱状图
		case 'hzztleft'://横柱状图左	
			option=fomatOption(echarswth(data,"bar","left"),title);
			break;
		case 'hzztright'://横柱状图右
			option=fomatOption(echarswth(data,"bar","right"),title);
			if(option.tooltip){
				option.tooltip.formatter= function(a,b,c,d){
			      	return a[0].seriesName+"<br/>"+a[0].name+":"+(-a[0].data);
			      };
			}
			break;
		case 'xzt'://线状图
			option=fomatOption(echarswt(data,"line"),title);
			break;
		case 'xqs'://线趋势图
			option=fomatxqs(fomatOption(echarswt(data,"line"),title));
			break;
		case 'timeSwt'://时间轴三维图
			option=getTimeSwtData(data.data,title,data.type);
			break;
		case 'xmt'://线面图
			option=fomatOption(echarswt(data,"line"),title);
			for(var i=0;i<option.series.length;i++){
				option.series[i].itemStyle={normal: {areaStyle: {type: 'default'}}};
			}
			break;
		case 'zxt'://柱线四维图
			option=fomatOption(echarswzxt(data),title);
			break;
		}
/*		//大于%50 文本显示
		
		switch(type){
		case 'bzt'://饼状图
		case 'mgt'://玫瑰图	
		case 'hxt'://环形图
		case 'jzt'://金字塔图
		case 'ldt'://雷达图
			var all=0;
			var sd=option.series[0].data;
			if(sd.length<3)break;
			for(var i=0;i<sd.length;i++){
				all+=sd[i].value;
			}
			for(var i=0;i<sd.length;i++){
				var bl=(sd[i].value*100/all).toFixed(2);
				if(bl>50){
					option.dsjtext="<p class='echatsdsjtext'><i class='icon-info-sign'></i>其中<b>"+sd[i].name+"</b>计<b>"+sd[i].value+getdw(option.series[0].name)+"</b>，占比<b>"+bl+"%</b>，以上为<b>其余</b>对比。</p>";
					var selected={};
						selected[sd[i].name] = false;
					option.legend.selected=selected;
					//option.series[0].data.splice(i,1);
					break;
				}
			}
			break;
		}*/
		return option;
	};
	var getTimeSwtData=function(data,title,type){
		var map={};
		for(var i=0;i<data.length;i++){
			var time=data[i].time;
			if(!map[time]){
				map[time]=[];
			}
			map[time].push(data[i]);
		}
		var options=[];
		var keyData=[];
		for(var key in map){
			var opt=getOption(map[key],title,type);
			opt.grid = {'y':80,'y2':80};
			opt.dataZoom={show:false};
			options.push(opt);
			keyData.push(key);
		}
		var option={
				 timeline:{
				        data:keyData,
				        label : {
				            formatter : function(s) {
				                return s.slice(0, 4);
				            }
				        },
				        autoPlay : true,
				        playInterval : 1000
				    },
				    options:options
		}
		return option;
	}
	var getdw=function(sname){
		if(sname==null)return '';
		//var sname=option.series[0].name;
		var wz=sname.indexOf('(')+1;
		if(wz==0)wz=sname.length-1;
		return sname.substring(wz,sname.length-1);
	}
	
	
	/*
	 * 柱线四维图
	 */
	var echarswzxt = function(dat) {
		var datas = this.echardata3d(dat, 'bar');
		var legents = datas[0];
		var xAxiss = datas[1];
		var seriess = datas[2];
		if(seriess[1]){
			seriess[1].type='line';
			seriess[1].yAxisIndex= 1;
		}
		
		var option = {
			   
			    calculable : true,
			    legend: {
			        data:legents
			    },
			    xAxis : [
			        {
			          name:'',
			            type : 'category',
			            data : xAxiss
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            name : legents[0],
			            axisLabel : {
			                formatter: '{value}'+getdw(legents[0])
			            }
			        },
			        {
			            type : 'value',
			            name : legents[1],
			            axisLabel : {
			                formatter: '{value}'+getdw(legents[1])
			            }
			        }
			    ],
			    series : seriess,
			    dataZoom : {
					show : true,
					realtime : true,
					start : 0,
					end : 100
				}
			};
		return option;

	};		
	/*
	 * 横柱状图
	 */
	var echarswth = function(dat, typ,position) {
		var datas = this.echardata3d(dat, typ);
		var legents = datas[0];
		var xAxiss = datas[1];
		var seriess = datas[2];
		if(position=="right"){
			for(var i=0;i<seriess[0].data.length;i++){
				seriess[0].data[i]=-seriess[0].data[i];
			}
		}
		var option = {
				legend : {
					data : legents
				},
			xAxis : [ {
				 type : 'value',
		            axisLabel :{formatter:function (value){
		            	return position=="right"?-value:value} },
		            boundaryGap : [0, 0.01]
			} ],
			yAxis : [ {
				 position:position,
		            type : 'category',
		            data : xAxiss
			} ],
			series : seriess,			
		};
		return option;

	};	
	/*
	 * 三维图
	 */
	var echarswt = function(dat, typ) {
		var datas = this.echardata3d(dat, typ);
		var legents = datas[0];
		var xAxiss = datas[1];
		var seriess = datas[2];
		
		var option = {
	
		
			legend : {
				data : legents
			},
			xAxis : [ {
				splitLine:{show:false},
				splitArea:{show:false},
				type : 'category',
				data : xAxiss
			} ],
			yAxis : [ {
				splitLine:{show:false},
				splitArea:{show:false},
				 name : legents[0],
				type : 'value',
				axisLabel : {
	                formatter: '{value}'+getdw(legents[0])
	            }
			} ],
			series : seriess,			
			dataZoom : {
				show : true,
				realtime : true,
				start : 0,
				end : 100
			}
		};
		return option;

	};
	/*
	 * 标准雷达图
	 */
	var echarldt=function(dat){
		var datas = this.echardataldt(dat);
		var legents = datas[0];
		var xAxiss = datas[1];
		var seriess = datas[2];
		option = {
		    legend: {
		        orient : 'vertical',
		        x : 'right',
		        y : 'bottom',
		        data:legents
		    },
		    polar : [
		       {
		           indicator : xAxiss
		        }
		    ],
		    calculable : true,
		    series : seriess
		};
		return option;                   
	};

	/*
	 * 金字塔
	 */
	var echarjzt = function(dat) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
			    legend: {
			    	x : 'center',
			        y : 'bottom',
			        data:legends
			    },
			series : [ {
				name : data[2],
		        type:'funnel',
	            sort : 'ascending',
	            itemStyle: {
	                normal: {
	                    // color: 各异,
	                    label: {
	                        position: 'left'
	                    }
	                }
	            },
				data : datas
			} ]
		};
		return option;
	};
	/*
	 * 内外多级饼状图
	 */
	var echardjbzt = function(dat) {
		var datas = this.datadj(dat);
		var legends = datas[0];
		var data1 = datas[1];
		var data2 = datas[2];
		option = {
			legend : {
	             y:'center',
				orient : 'vertical',
				x : 'left',
				data : legends
			},
			calculable : true,
			series : [ {
				type : 'pie',
				selectedMode : 'single',
				radius : [ 0, 70 ],
				x : '20%',
				width : '40%',
				funnelAlign : 'right',
				max : 1548,
				itemStyle : {
					normal : {
						label : {
							position : 'inner'
						},
						labelLine : {
							show : false
						}
					}
				},
				data : data1
			}, {
				name : '访问来源',
				type : 'pie',
				radius : [ 100, 140 ],
				x : '60%',
				width : '35%',
				funnelAlign : 'left',
				max : 1048,
				data : data2
			} ]
		};
		return option;
	};	
	/*
	 * 饼状环形图
	 */
	var echarbzhx = function(dat) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option={   
			    legend: {
			        x : 'center',
			        y : 'bottom',
			        data:legends
			    },
					    series: [
					        {
					            name:data[2],
					            type:'pie',
					            radius: ['45%', '70%'],
					            avoidLabelOverlap: false,
					            labelLine: {
					                normal: {
					                    show: false
					                }
					            },
					            data:datas
					        }
					    ]
					};
		return option;
	};
	/*
	 * 玫瑰图
	 */
	var echarmgt = function(dat) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
			    legend: {
			    	 x : 'center',
			         y : 'top',
			        data:legends
			    },
			series : [ {
				name : data[2],
				type : 'pie',
				 radius : [20, 110],
		            center : ['50%', 200],
		            roseType : 'radius',
		            max: 40,            // for funnel
		            itemStyle : {
		                normal : {
		                    label : {
		                        show : false
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : true
		                    },
		                    labelLine : {
		                        show : true
		                    }
		                }
		            },
				data : datas
			} ]
		};
		return option;
	};	
	/*
	 * 饼状图
	 */
	var echarbzt = function(dat) {
		var data = this.echardata2d(dat);
		var legends = data[0];
		var datas = data[1];
		var option = {
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:legends
			    },
			series : [ {
		        itemStyle:{ 
					normal: {
		        label: {
		            formatter: "{b} ({d}%)"
		        },     
		    }
		          },
		          startAngle:90,
		          clockWise:false,
				name : data[2],
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : datas
			} ]
		};
		return option;
	};

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


