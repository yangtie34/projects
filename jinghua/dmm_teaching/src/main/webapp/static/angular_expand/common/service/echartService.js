/**
 * echartService
 * 根据传入的config 装配成echartService需要的 config
 * renderCommonChart //图表类型(bar,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

system.factory('echartService',function(){
	/**
	 * 深度赋值
	 * @param object 原数据
	 * @param config copy数据
	 * @returns object
	 */
	var apply = function(object, config){
		if(config instanceof Array && object.length == config.length){
			// 数组：循环递归处理
			for(var i=0,len=config.length; i<len; i++){
				arguments.callee(object[i], config[i]);
			}
		}else if(config instanceof Object){
			for(key in config){
				var value = config[key];
				// 不存在 或 简单数据类型直接赋值
				if(!object[key] || value==null || typeof value == 'string' || typeof value == 'number' || typeof value == 'boolean' || typeof value == 'function'
					|| (value instanceof Array && (typeof value[0] == 'string' || typeof value[0] == 'number' || typeof value == 'boolean')) ){
					object[key] = value;
				}else{
					// 对象：递归处理
					arguments.callee(object[key], config[key]);
				}
			}
		}
		return object;
	};
	var cityList = {}; var dataformat=[];
    return {
    		mapclick:function(mc){
    			    var dzb;
					if(dataformat.length==0){
					var dzb1 = $.ajax({
			          url: "static/angular_expand/map/placedz.json",
			            async: false
			            }).responseText.replace(/[\r\n]/g,"");
			             dzb = JSON.parse(dzb1);
			            dataformat = dzb;
					}else{
					 dzb = dataformat;
					}
					var mc1 = "";
					for(var i = 0;i<dzb.length;i++){
						var name = dzb[i];
						if(mc == name[1]){
							mc1 = name[0];
						}
					}
			      return mc1==""?mc:mc1;		
	},
        /**
         * @param config 配置对象
         *
         *  模板 config = {
             title : "  ",
             yAxis : "件",
             isSort : false,
             data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
                     {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
             type :"column"   //图表类型(column,line,area,spline,areaspline)
        }
         */
        renderCommonChart : function(config){
            config.isSort = config.isSort || false;
            type = config.type || 'column';
            var isName ={} , isField = {};
            var fields = [],series = [],legendData=[];
            for(var i in config.data){
                var tar = config.data[i];
                if(!isName[tar.name]){
                	if(tar.name) legendData.push(tar.name);
                    series.push({name : tar.name,data : []});
                    isName[tar.name] = true;
                }
                if(!isField[tar.field]){
                    fields.push(tar.field);
                    isField[tar.field] = true;
                }
            }
            if(config.isSort) fields.sort(function(a,b){return a>b?1:-1;});
            var ser,fie,dat, _data_=[];
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
                    for(var m = 0; m < config.data.length; m++){
                        dat = config.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                            _data_.push(dat);
                        }
                    }
                    if (ser.data.length < k){
                        ser.data.push(0);
                        _data_.push({});
                    }
                }
            };
        	var name = config.name ? config.name : ''; // 单位
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: config.title,
                    left : 'center'
                },
                tooltip : {
                    trigger: 'axis',
    		        formatter: "{b} <br/> {c}" +name,
                    axisPointer : { // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                toolbox: {
			        show : true,
			        feature : {
			        	dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
				     }
				 },
                legend: {
                    x: 'left',
                    data:legendData
                },
                xAxis: [{
                    data: fields
                }],
                yAxis : [{
                	name : name,
                    type : 'value'
                }],
                series:  series
            };
        	// 初始化自定义配置 config已经在外面处理了
//            if(config.config){
//	        	option = apply(option, config.config);
//            }
    		Ice.apply(option, {config:{_data_:_data_}}); // 缓存data
            return option;
        },
        /**
         * @param config 配置对象
         *
         * 	config = {
         * 		type : 'pie',
	            title: "饼状图标题",
	            data : [{name: '苹果', value: 30},{name: '橘子', value: 20}]
	            name : '', // 单位
	            config : {} // 等同于option，根据配置自定义替换option中的属性，有几个替换几个
	        }
         */
        renderPieChart : function(config){
        var isName = {}, legendData = [];
			var serdata = [], _data_=[];
			if(config.isMapData){
				if (dataformat.length == 0) {
					var dzb1 = $.ajax({
								url : "static/angular_expand/map/placedz.json",
								async : false
							}).responseText.replace(/[\r\n]/g, "");
					var dzb = JSON.parse(dzb1);
					dataformat = dzb;
				} else {
					var dzb = dataformat;
				}
				for (var i in config.data) {
					var tar = config.data[i];
					if (!isName[tar.name]) {
						var mc="";
						if (tar.name) {
							for (var k = 0; k < dzb.length; k++) {
								var name = dzb[k];
								if (tar.name == name[0]) {
									mc = name[1];
								}
							}
							if(mc==""){
								mc=tar.name;
							}
							legendData.push(mc);
							isName[tar.name] = true;
						}
					}
					var name1 = config.data[i].name;
					var value = config.data[i].value;
					var code = "";
					if(config.data[i].code){
						code = config.data[i].code;
					}
					var name3 = "";
					for (var j = 0; j < dzb.length; j++) {
						var name2 = dzb[j];
						if (name1 == name2[0]) {
							name3 = name2[1];
							serdata.push({
										'name' : name3,
										'value' : value,
										'code' : code
									});
						}
					}
					if (name3 == "") {
						serdata.push({
									'name' : name1,
									'value' : value,
									'code' : code
								})
					}
				}
			}else{
	            for(var i in config.data){
	                var tar = config.data[i];
	                if(!isName[tar.name]){
	                	if(tar.name) legendData.push(tar.name);
	                    isName[tar.name] = true;
	                }
	            }
	            serdata = config.data;
			}
			_data_  = config.data;
        	var name = config.name ? config.name : ''; // 单位
        	var option = {
        		    title : {
        		        text: '',
        		        x:'center'
        		    },
        		    tooltip : {
        		        trigger: 'item',
        		        formatter: "{b} <br/> {c}" +name+ "&nbsp;&nbsp;(占比{d}%)"
        		    },
        		    legend: {
        		        show:false,
        		        x: 'left',
        		        data: legendData
        		    },
        		    toolbox: {
				        show : true,
				        feature : {
				        	dataView : {show: true, readOnly: false},
				            restore : {show: true},
				            saveAsImage : {show: true}
					    }
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
        		        	name:'',
        		            type: 'pie',
        		            radius : '75%',
        		            center: ['50%', '50%'],
        		            selectedMode: 'single',
        		            data: [],
        		            itemStyle: { 
        		            	normal : {
        		            		label : {
        		            			position : 'outer',
        		            			formatter : "{b}({d}%)"
        		            		}
        		            	},
        		                emphasis: {
        		                    shadowBlur: 10,
        		                    shadowOffsetX: 0,
        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        		                }
        		            }
        		        }
        		    ]
        	};
        	// 初始化自定义配置
//        	if(config.config){
//	        	option = apply(option, config.config);
//        	}
        	// 设置基本属性
        	option.title = config.title;
        	var ser = option.series[0];
        	ser.type = config.type;
        	ser.data = serdata;
    		Ice.apply(option, {config:{_data_:_data_}}); // 缓存data
        	return option ;
        },
  renderMapChart:function(config){
  	          if($.isEmptyObject(cityList)){
						var map = $.ajax({
				          url: "static/angular_expand/map/cityMap.json",
				            async: false
				            }).responseText.replace(/[\r\n]/g,"");
				            var map1 = JSON.parse(map);
				            cityList = map1;
				      }else{
							var map1 = cityList;
							}
			                                var cityMap = map1;
							                var mapType = [
												'china',
												'广东', '青海', '四川', '海南', '陕西', 
												'甘肃', '云南', '湖南', '湖北', '黑龙江',
												'贵州', '山东', '江西', '河南', '河北',
												'山西', '安徽', '福建', '浙江', '江苏', 
												'吉林', '辽宁', '台湾',
												'新疆', '广西', '宁夏', '内蒙古', '西藏', 
												'北京', '天津', '上海', '重庆',
												'香港', '澳门'
						                   	];
						var _mapParams = echarts.util.mapData.params;
						for (var city in cityMap) {
						mapType.push(city);
						_mapParams.params[city] = {
						    getGeoJson: (function (c) {
							var geoJsonName = cityMap[c];
							return function(callback) {
							$.getJSON('static/echarts/2/geoJson/china-main-city/'+geoJsonName+'.json', function(data){
							callback(_mapParams.decode(data));
				        });
				      }
				    })(city)
				  }
				}
        		if(dataformat.length==0){
				var dzb1 = $.ajax({
		          url: "static/angular_expand/map/placedz.json",
		            async: false
		            }).responseText.replace(/[\r\n]/g,"");
		            var dzb = JSON.parse(dzb1);
		            dataformat = dzb;
				}else{
				var dzb = dataformat;
				}
				var mtype="";
				if (config.mapType=='中国'||config.mapType==null){mtype="china";}else{
				for(var i = 0;i<dzb.length;i++){
					var name = dzb[i];
					if(config.mapType == name[0]){
						mtype = name[1];
					}
				} 
				}
				if (mtype==""){
					mtype = config.mapType;
				}
				    var serdata =[];
					for (var i = 0;i<config.data.length;i++){
					var name1 = config.data[i].name;var value = config.data[i].value;
					var name3="";
					for (var j = 0;j<dzb.length;j++){
						var name2 =dzb[j];
						if(name1==name2[0]){
							name3=name2[1]; 
							serdata.push({'name':name3,'value':value});
						}
					}
					if (name3==""){
						serdata.push({'name':name1,'value':value})
					}
					}
					if (mtype =='china'){
						subtext = '中国';
					}else{
						subtext = mtype;
					}
		        	option = {
						    tooltip : {
						        trigger: 'item'
						    },
		        		    toolbox: {
						        show : true,
						        feature : {
						        	dataView : {show: true, readOnly: false},
						            restore : {show: true},
						            saveAsImage : {show: true}
							    }
						    },
						    dataRange: {
						        min: 0,
						        max: 5000,
						        show:false,
						        x: 'left',
						        y: 'bottom',
						        text:['高','低'], 
						        color:['#ff9900','#ffcd00','#ffff00','#dddddd'],
						        calculable : true
						    },
						    series : [
						        {
						            name: '学生人数',
						            type: 'map',
						            mapType: mtype,
						            selectedMode : 'single',
						            itemStyle:{
						                normal:{label:{show:true}},
						                emphasis:{label:{show:true}}
						            },
						            data:serdata
						        }
						    ]
						};
						return option;
        },
        	/**
        	 * 格式化option数据（统一配置）
        	 */
        	formatOptionSetting : function(option, config){
        		// 优先取出 noDataText，之后删除以防止其对echart原生属性产生污染
        		var noDataText = '暂无数据';
        		if(config.noDataText){
        			noDataText = config.noDataText || noDataText;
//        			delete config.noDataText;  // 不能删除，删除会触发watch('config')，再次render，这是已经将noDataText删除，所以无法显示提示信息
        		}
            	// 初始化自定义配置
                if(config){
    	        	option = apply(option, config);
                }
                isDataZoom  = option.dataZoom ? true : false; // 是否有时间轴
                if(!isDataZoom){
                	if(option.series.length > 0){
                		for(var i = 0; i< option.series.length; i++){
                			if( option.series[i].type == 'bar' && option.xAxis[0].data){
                				if(option.xAxis[0].data.length > 14){
                				option.dataZoom = {show: true,start : 0,end : (12/option.xAxis[0].data.length)*100};
                				}
                			}
                		}
                	}
                }
                /**无数据显示 2016-09-23**/ /**9-28修改**/
                option.noDataLoadingOption= {
    					effect : function(params) {
    						params.start = function(h) {
    							h._bgDom.innerText = noDataText;
    							h._bgDom.style.textAlign = 'center';
    							h._domRoot.style.backgroundImage = 'url(static/resource/images/noData.png)';
    							h._domRoot.style.backgroundRepeat = 'no-repeat';
    							h._domRoot.style.backgroundPositionX = 'center';
    							h._domRoot.style.backgroundPositionY = 'center';
    							h._domRoot.style.backgroundSize = '53px 53px';
    							h._bgDom.style.top = '60%';
    							h._bgDom.style.width = '100%';
    							params.stop = function() {
        							h._bgDom.innerText = '';
        							h._bgDom.style.textAlign = '';
        							h._domRoot.style.backgroundImage = '';
        							h._domRoot.style.backgroundRepeat = '';
        							h._domRoot.style.backgroundPositionX = '';
        							h._domRoot.style.backgroundPositionY = '';
        						}
    						}
    						return params;
    					}
    				};
                // 格式化series为暂无数据状态，echart会根据series[0].data == [] 初始化 冒气泡的动画
                if(option.series.length == 0) option.series[0] = {data:[],type:'bar'}; 
        		// 表格配置
        		var type = option.series[0] ? option.series[0].type : null;
        		switch (type){
	        		case 'line':     
	                case 'bar' :
	                case 'area':
	                case 'spline':
	                case 'areaspline':
	                	var grid = {
	                    	 y  : 60,
	                    	 y2 : 36,
	                    	 x  : 65,
	                    	 x2 : 65
//	                    	 left: '1%',
//	                         right: '1%',
//	                         bottom: '30px',
//	                         containLabel: true
	                    },
	                	legend = { padding : [5,55] },
	                	isAxisLabel = (option.xAxis[0] && option.xAxis[0].axisLabel) ? true : false, // X轴是否旋转
	                	isDataZoom  = option.dataZoom ? true : false; // 是否有时间轴
	                	// 没有图例的情况下，上边距 设置为40
	                	if(option.legend.data.length <= 0 || option.legend.show==false){
	                		grid.y = 40;
	                	}
	                	// x轴数据选转时，下边距设置为 65
	                	if(isAxisLabel || isDataZoom){
	                		grid.y2 = 60;
	                	}
	                	// 有时间轴的情况 || 旋转
	                	if(isDataZoom && isAxisLabel){
	                		grid.y2 = 80;
	                	}
	                	var baseCfg = {
                			legend : legend,
	                		grid   : grid
	                	};
	                	option = apply(baseCfg,option);
	                	break;
        		}
        		/**
        		 * 重大修复： 20160616
        		 * 以前的写法是：option = apply(option, {grid:grid});
        		 * 现在的写法是：option = apply({grid:grid}, option);
        		 * 以前将属性复制到option上，现在将option复制到{grid:grid}上，所以必须返回option
        		 */
        		return option;
        	},
        	  renderVennChart:function(config){
                  serdata =config.data;
               	var name = config.name ? config.name : ''; // 单位
               	var option = {
               		    tooltip : {
               		        trigger: 'item',
               		        formatter: "{b} <br/> {c}" +name+ ""
               		    },
       				    calculable : false,
               		    series : [
               		        {
               		        	name:'',
               		        	type:config.type,
               		        	  itemStyle: {
               		                 normal: {
               		                     label: {
               		                         show: true,
               		                         textStyle: {
               		                             fontFamily: 'Arial, Verdana, sans-serif',
               		                             fontSize: 16,
               		                             fontStyle: 'italic',
               		                             fontWeight: 'bolder'
               		                         }
               		                     },
               		                     labelLine: {
               		                         show: false,
               		                         length: 10,
               		                         lineStyle: {
               		                             // color: 各异,
               		                             width: 1,
               		                             type: 'solid'
               		                         }
               		                     }
               		                 },
               		                 emphasis: {
               		                     color: '#cc99cc',
               		                     borderWidth: 3,
               		                     borderColor: '#996699'
               		                 }
               		             },
               		            data: serdata
               		        }
               		    ]
               	};
               	// 设置基本属性
               	option.title = config.title;
               	return option ;
              }
    };
});