
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
var system = angular.module('system',[]);/**
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
});/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('httpService',['$rootScope','toastrService',function(root,toastr){
    return {
        /**
         * @param configs 配置对象
         *
         *  模板 params = {
             	url : "",
             	data : {},
             	success : function(data){}
        	}
         返回jQuery的Ajax对象
         */
        post : function(params){
        	// 移除空参数 null
        	var data = params.data;
        	if(data instanceof Object){
        		for(var key in data){
        			if(data[key]==null){
        				delete data[key]
        			}
        		}
        	}
            var result = $.ajax({
            	type: "POST",   //请求方式
            	url: base + params.url,//请求的url地址
        	    data: data,//参数值
        	    dataType: "json",   //返回格式为json
        	    beforeSend: function() {
        	        //请求前的处理
                    if(params.beforeSend)params.beforeSend();
        	    },
        	    success: function(data) {
        	        //请求成功时处理
                    if(params.success)params.success(data);
        	    },
        	    error: function(dt) {
        	        //请求出错处理
        	    	var msg = dt.status + ":" + dt.statusText;
        	    	if(msg == "200:OK"){
        	    		toastr.warning("您没有访问权限，请联系管理员！");
        	    	}else{
        	    		toastr.error("数据请求错误，请刷新页面重试！("+msg+")");
        	    	}
        	    	if(params.error) params.error(data);
        	    	/*if(dt.status == '404'){
        	    		try{
        	    			window.parent.location.href = logoutUrl;
        	    		}catch (e) {
        	    			window.location.href = logoutUrl;
        	    		}
        	    	}*/
        	    },
        	    complete : function(){
        	    	root.$apply();
        	    }
            	
            });
            return result;
        },
        get : function(params){
        	 var result = $.ajax({
             	type: "GET",   //请求方式
             	url: params.url,//请求的url地址
         	    data: params.data,//参数值
         	    dataType: "json",   //返回格式为json
                beforeSend: function() {
                     //请求前的处理
                     if(params.beforeSend)params.beforeSend();
                 },
                success: function(data) {
                     //请求成功时处理
                     if(params.success)params.success(data);
                 },
         	    error: function() {
         	        //请求出错处理
         	    	toastr.error("数据请求错误，请重试！");
         	    },
        	    complete : function(){
        	    	root.$apply();
        	    }
             	
             });
             return result;
        },
        /**
         * 请求数据,通过该形式，避免了使用回调函数的形式获取数据，极大的增强了代码的可读性
         * var req = {
         * 	    service : 'beanId?method',
         * 		params : {
         * 			name : '张三',
         * 			sex : '男'
         * 		}
         * };
         * var data = http.callService(req);
         * $scope.iterator = data;//这里需要在模版中通过iterator.data来进行数据的遍历，
         * 						  //不要在这里直接试图通过iterator.data获取数据（ajax请求是异步的，数据尚未绑定到该键上)
         *
         *********************************************************
         * 当然如果你想直接获取数据：你可以这样做：
         * http.callService(req).success(function(data){
         * 		//在这里获取你实际需要的数据
         * });
         */
        callService : function(request){
        	var requestCopy = angular.copy(request),
        		params = requestCopy.params,
        		backKey = "data",
                requests = [],
                sm = requestCopy.service.split("?"),
        		callback,
        		ret = {
        			success : function(thenCallback){
        				callback = thenCallback;
        			}
        		};
        	delete requestCopy.service;
        	requestCopy.beanName = sm[0];
        	requestCopy.methodName = sm[1];
        	if(!requestCopy.params)requestCopy.params = [];
        	requestCopy.data = angular.toJson(requestCopy.params);
        	requestCopy.params = angular.toJson(requestCopy.params);
        	$.ajax({
            	type: "POST",   //请求方式
            	url: base + "common/getData",//请求的url地址
        	    data: {params : angular.toJson(requestCopy)},//参数值
        	    dataType: "json",   //返回格式为json
        	    success: function(data) {
        	    	if (callback) {
                        callback(data);
                    } else {
                        ret[backKey] = data.result;
                        delete ret.success;
                    }
        	    }
            });
        	return ret;
        },
        /**
    	 * 文件下载
    	 */
    	fileDownload : function(params){
    		var data = params.data;
        	if(data instanceof Object){
        		for(var key in data){
        			if(data[key]==null){
        				delete data[key]
        			}
        		}
        	}
        	var url = base + params.url; //请求的url地址
    		var result = $.fileDownload(url,{
    			data : data,
    			successCallback: function (url) {
    				params.success();
    			},
				failCallback: function (html, url) {
        	    	toastr.error("数据请求错误，请刷新页面重试！");
				} 
			});  
    		return result
    	}
    };
}]);
/*system.config(['$httpProvider',function($httpProvider) {
	  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
	  var param = function(obj) {
	    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
	      
	    for(name in obj) {
	      value = obj[name];
	        
	      if(value instanceof Array) {
	        for(i=0; i<value.length; ++i) {
	          subValue = value[i];
	          fullSubName = name + '[' + i + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value instanceof Object) {
	        for(subName in value) {
	          subValue = value[subName];
	          fullSubName = name + '[' + subName + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value !== undefined && value !== null)
	        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
	    }
	      
	    return query.length ? query.substr(0, query.length - 1) : query;
	  };
	  $httpProvider.defaults.transformRequest = [function(data) {
	    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
	  }];
}]);
var config = {
	method : "POST",
	url: base + params.url,//请求的url地址
	data : params.data, //参数值
	responseType : "json" //返回格式为json
};
var deferred = q.defer();
var promise = deferred.promise;
http(config).success(function(data){
		deferred.resolve(data);
	}).error(function(data){
		deferred.reject(data);
});
return promise;*/
/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('locationService',[function(){
	var body = $('body'),
        parseParam=function(param, key){
            var paramStr="";
            if(param instanceof String||param instanceof Number||param instanceof Boolean){
                paramStr+="&"+key+"="+encodeURIComponent(param);
            }else{
                $.each(param,function(i){
                    var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
                    paramStr+='&'+parseParam(this, k);
                });
            }
            return paramStr.substr(1);
        };

    return {
        /**
         * 在当前页面跳转到一个新的页面
         * @param url
         * @param params
         */
        redirect : function(url,params){
            window.location.href = url+"?"+parseParam(params||{});
        },
        /**
         * 打开一个新的tab页面或者页面
         */
        redirect_new : function(url,params){
        	var action = url+"?"+parseParam(params||{});
            var tempwindow=window.open('_blank');
            tempwindow.location = action;
        }
    }

}]);/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('toastrService',[function(){
	toastr.options = {
	  "closeButton": false,
	  "debug": false,
	  "positionClass": "toast-bottom-full-width",
	  "onclick": null,
	  "showDuration": "100",
	  "hideDuration": "300",
	  "timeOut": "3000",
	  "extendedTimeOut": "500",
	  "showEasing": "linear",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}

    return {
        info : function(str){
        	toastr.info(str)
        },
        success : function(str){
        	toastr.success(str)
        },
        warning : function(str){
        	toastr.warning(str)
        },
        error : function(str){
        	toastr.error(str)
        },
        clear : function(){
        	toastr.clear()
        }
    }
}]);/**
 * dialog 
 * 
 alert : dialog.alert("哈哈？");
		
		
 confirm:dialog.confirm("哈哈？",function(){
			alert("yes");
		},function(){
			alert("no")
		});
 */
system.factory('dialog',['$compile','$rootScope',function($compile,$rootScope){
	    //创建遮罩
		var body = $("body");//获取body元素
	    var mask = $('<div class="modal-backdrop fade in"/>');
	    mask.hide();
	    body.append(mask);
	    var confirmHtml = 
	    	'<div class="modal" style="display:none;">'+
	        	'<div class="modal-dialog" style="background-color: #FFF;">'+
	                '<div class="modal-header">'+
	                    '<button type="button" class="close" ng-click="$dialog_confirm_close()" style="opacity: 1;"><i class="fa fa-times text-danger"></i></button>'+
	                    '<h4 class="modal-title text-primary"><i class="fa fa-question"></i>&nbsp;提示</h4>'+
	                 '</div>'+
	                 '<div class="modal-body">'+
	                 	'{replacement}'+
	                 '</div>'+
	                 '<div class="modal-footer" style="text-align: center;">'+
	                 	'<button type="button" class="btn btn-primary" ng-click="$dialog_confirm_sure()">确定</button>'+
	                 	'<button type="button" class="btn btn-default" ng-click="$dialog_confirm_cancel()">关闭</button>'+
	                 '</div>'+
	             '</div>'+
	         '</div>';
	    var successHtml = '<div class="modal" style="display:none;">'+
		    '<div class="modal-dialog" style="background-color: #FFF;">'+
		         '<div class="modal-body" style="padding:30px 10px;">'+
		         	'{replacement}'+
		         '</div>'+
		         '<div class="modal-footer" style="text-align: center;">'+
		         	'<button type="button" class="btn btn-danger btn-sm" ng-click="$dialog_success_close()">关闭</button>'+
		         '</div>'+
		  '</div>';
	    var loadingHtml = '<div class="modal" style="display:none;">'+
	    	'<div style="position: absolute;left: 50%;top: 35%;margin-left: -30px;width: 60px;text-align: center;color: #FFF;"> <i class="fa fa-spinner fa-spin fa-4x" style=""></i></div>'
	    '</div>';
	    /**
	     * 把元素添加到body上，显示遮罩，并把dialog居中显示
	     * replace message,show mask,show dialog
	     * @param html
	     * @param message
	     * @returns {*|jQuery|HTMLElement}
	     */
	    var rsd = function(html,message){
	        var el = $($compile(html.replace("{replacement}",message))($rootScope));
	        mask.show();//显示遮罩
	        body.append(el);
	        el.fadeIn("fast");
	        return el;
	    }
	    var dialog = {
	    		
	    };
	    return {
	        alert : function(message,callback){
	            $rootScope.$dialog_success_close = function(){
	            	dialog.success.remove();
	                mask.hide();
	                delete dialog.success;
	                if(callback)callback()
	            };
	            dialog.success = dialog.success||rsd(successHtml,message);
	        },
	        
	        /**
	         * 显示一个确认窗口
	         * @param message
	         * @param doSure
	         * @param doFailure
	         */
	        confirm : function(message,doSure,doCancel){
	            $rootScope.$dialog_confirm_close = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	            };
	            $rootScope.$dialog_confirm_sure = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	                if(doSure)doSure();
	            };
	            $rootScope.$dialog_confirm_cancel = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	                if(doCancel)doCancel();
	            };
	            dialog.confirm = dialog.confirm||rsd(confirmHtml,message);
	       },
	       showLoading : function(){
	    	   if(dialog.loading){
	    		   mask.show();
	    		   dialog.loading.fadeIn('fast');
	    	   }else{
	    		   dialog.loading = rsd(loadingHtml,"");
	    	   }
	    	   
	       },
	       hideLoading : function(){
	    	   dialog.loading.fadeOut('fast');
               mask.fadeOut('fast');
	       }
	    }
	}]);/**
 * 定义一个mask(遮罩)服务
 */
system.factory('mask',function(){
    var body = $("body");//获取body元素
    //创建一个全局遮罩
    var mask = $('<div class="cs-window-background"></div>');
    var loading = $('<div class="cs-window-loading"><div class="cs-window-img"></div></div>');
    mask.hide();
    loading.hide();
    body.append(mask);
    body.append(loading);
    return {
        /**
         * 显示遮罩
         */
        show : function(){
            mask.show();
        },
        /**
         * 隐藏遮罩
         */
        hide : function(){
            mask.hide();
        },
        /**
         * 显示遮罩和加载中
         */
        showLoading : function(){
        	mask.show();
        	loading.show();
        },
        /**
         * 隐藏遮罩和加载中
         */
        hideLoading : function(){
        	mask.hide();
        	loading.hide();
        }
    }
});
system.factory('advancedService',[function(){
	return {
		
		/**
		 * 获取高级查询组件参数 {Json类型}
		 */
		getParam : function(data){
			var paramList = [], obj;
			for(var i=0,len=data.length; i<len; i++){
				obj = data[i];
				paramList.push({
					code   : obj.queryCode,
					values : obj.id,
					group  : obj.group
				});
			}
			// 高级查询-参数
			return JSON.stringify(paramList);
		},
		/**
		 * 获取高级查询组件某一个条件的数据
		 */
		get : function(data, code){
			var obj;
			if(code != null){
				for(var i=0,len=data.length; i<len; i++){
					obj = data[i];
					if(code == obj.queryCode){
						return obj;
					};
				}
			}
			return obj;
		},
		
		/**
		 * 设置每一个查询条件的第一个值选中
		 */
		checkedFirst : function(data){
			if(data != null && data.length > 0){
				for(var i=0,len=data.length; i<len; i++){
					var obj = data[i];
					// 非树形组织机构
					if(obj.queryType != 'comboTree'){
						var items = obj.items;
						if(items.length > 0){
							items[0].checked = true;
						}
					}
				}
			}
		}
	
	}
	
}]);/*************************************************
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
}]);/**
 * 验证权限指令
 */

system.directive('checkPermiss',["httpService", function(http) {
	return {
		restrict : 'AE',
		scope: {
			callback : '&'
		},
		link : function(scope, element, attrs) {
			var data={};
			data.params=[];
			data.params.push(attrs.shirotag);
			data.service='checkPermiss?checkPermiss';
	  		http.callService(data).success(function(data){
	  			if(data==true){
	  				if(scope.callback) scope.callback();
	  				return;
	  			}else{
	  				$(element[0]).remove();
	  				if(scope.callback) scope.callback();
	  			}
		    }); 
		}
	}
}]);
/**
 * 树指令
 * 
//*******************使用方法**********************  
//
//   <ul class="list1">
//	    <li cg-tree="node in items of treeData">
//	      <a ng-click="check(node)">{{node.name}}</a>
//	      <ul> <li cg-treecurse ></li> </ul>
//	    </li>
//	  </ul>
//
 *******************数据格式**********************
 * 
 * $scope.treeData = {
 *		  name: "Root",
 *		  items : [
 *		           {name:2,items:[{name:3},{name:4}]},
 *		           {name:5,items:[{name:6},{name:7}]}]
 * }
 ***********************************************
 */
system.directive('cgTree', ['$log', function($log) {
	return {
	   restrict: 'AE',
	   scope: true,
	   controller: ['$scope', '$attrs',
	     function TreeController($scope, $attrs){
		   //***** 检测指令内的参数格式是否正确   ”node in items of treeData”
		   var expression = $attrs.cgTree;
		    var match = expression.match(/^\s*([\$\w]+)\s+in\s+([\S\s]*)\s+of\s+([\S\s]*)$/);
		    if (! match) {
		      throw new Error("Expected cgTree in form of"+ " '_item_ in _collection_ of _root_' but got '" + expression + "'.");
		    }
		    //******检测结束*******
		    
		    
	       var ident = this.ident = {value: match[1],collection: match[2],root: match[3]};
	       //$log.info("Parsed '%s' as %s", $attrs.cgTree, JSON.stringify(this.ident));
	       $scope.$watch(this.ident.root, function(v){
	         $scope[ident.value] = v;
	       });
	     }
	   ],
	   // Get the original element content HTML to use as the recursive template
	   compile: function cgTreecurseCompile(element){
	     var template = element.html();
	     return {
	       // set it in the pre-link so we can use it lower down
	       pre: function cgTreePreLink(scope, iterStartElement, attrs, controller){
	         controller.template = template;
	       }
	     };
	   }
	 };
	}]).directive('cgTreecurse', ['$compile', function($compile){
	return {
	   // 必须被cgTree指令包含
	   require: "^cgTree",
	   link: function cgTreecursePostLink(scope, iterStartElement, attrs, controller) {
	     // 使用父标签传入的参数进行解析，相当于使用递归
	     var build = [
	       '<', iterStartElement.context.tagName,' ng-repeat="',
	       controller.ident.value, ' in ',
	       controller.ident.value, '.', controller.ident.collection,
	       '">',
	       controller.template,
	       '</', iterStartElement.context.tagName, '>'];
	     var el = angular.element(build.join(''));
	     // We swap out the element for our new one and tell angular to do its
	     // thing with that.
	     iterStartElement.replaceWith(el);
	     $compile(el)(scope);
	   }
	};
}]);system.directive('cgCustomComm', function() {
	return {
		restrict : 'AE', 
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/customComm.html',
		scope : {
			source : "=",
			expand : "@",
			noborder : "@",
			onChange : "&"
		},
		link : function(scope, element, attrs) {
			// 当前查询条件
			scope.result = [];
			
			//树的选择结果
			scope.innerTreeResult = {};
			
			//监听树的选择结果
			scope.$watch('innerTreeResult',function(val) {
				if (val.queryCode) {
					var innerTreeHasPushed = false;
					for ( var i = 0; i < scope.result.length; i++) {
						if (scope.result[i].queryCode == scope.innerTreeResult.queryCode) {
							scope.result.splice(i,1,angular.copy(scope.innerTreeResult));
							innerTreeHasPushed = true;
						}
					}
					if (!innerTreeHasPushed) {
						scope.result.push(angular.copy(scope.innerTreeResult));
					}
					scope.onChange({
						$data : scope.result
					});
				}
			}, true);
			
			// 清空查询条件
			scope.cancleQueryAll = function() {
				scope.result = [];
				for ( var i = 0; i < scope.queryArray.length; i++) {
					var item = scope.queryArray[i];
					if(scope.queryArray[i].queryType == "comboTree"){
						scope.queryArray[i].items.dataChangeDate = new Date();
					}else{
						for ( var j = 0; j < item.items.length; j++) {
							item.items[j].checked = false;
						}
					}
				}
				
			};
			scope.cancleQuery = function(obj) {
				for ( var i = 0; i < scope.result.length; i++) {
					if(scope.result[i] == obj){
						scope.result.splice(i,1);
					}
				}
				if(obj.queryType == "comboTree"){
					for ( var i = 0; i < scope.queryArray.length; i++) {
						if(scope.queryArray[i].queryCode == obj.queryCode){
							scope.queryArray[i].items.dataChangeDate = new Date();
						}
					}
				}else{
					for ( var i in scope.queryArray) {
						var item = scope.queryArray[i];
						if(obj.queryName == item.queryName){
							for ( var j = 0; j < item.items.length; j++) {
								item.items[j].checked = false;
							}
						}
					}
				}
			};

			// 查询条件组合
			scope.queryArray = angular.copy(scope.source);

			// 点击更多后，显示某组条件的每一个条件项
			scope.showAll = function(obj) {
				obj.isAll = true;
				for ( var i in obj.items) {
					obj.items[i].show = true;
				}
			};
			// 高级搜索
			scope.isExpandALL = scope.expand ? true : false;
			scope.expandALL = function() {
				scope.isExpandALL = !scope.isExpandALL;
				for ( var i = 1; i < scope.queryArray.length; i++) {
					scope.queryArray[i].isShow = !scope.queryArray[i].isShow;
				}
			};
			// change
			scope.change = function(item, condition) {
				scope.changeIt(item, condition);
				scope.onChange({
					$data : scope.result
				});
			};
			
			scope.$watch('source',function() {
				scope.queryArray = angular.copy(scope.source);
				scope.setDefaultChecked();
			}, true);
			
			// change
			scope.changeIt = function(item, condition) {
				// 改变选中
				for ( var j = 0; j < condition.items.length; j++) {
					condition.items[j].checked = false;
				}
				item.checked = true;
				// 判断item所属的条件组是否有已选条件在result数组中,如果有将新的替换旧的
				var it = {}, itHasPushed = false;
				angular.copy(item, it);
				it.queryName = condition.queryName;
				it.queryCode = condition.queryCode;
				it.group     = condition.group; // 20160517 条件分组[xuebl]
				var array = [];
				for ( var i in scope.result) {
					var tt = scope.result[i];
					// 将新的替换旧的
					if (tt.queryCode != it.queryCode) {
						array.push(tt);
					} else {
						array.push(it);
						itHasPushed = true;
					}
				}
				if (!itHasPushed) {
					array.push(it);
				}
				scope.result = array;
			};
			
			scope.setDefaultChecked = function(){
				// 遍历条件组合数组，设置默认选中项和默认显示项
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					// 当条件的组数大于1的时候，默认显示两组，其余的组合点击高级查询显示
					if (i < 1) {
						item.isShow = true;
					} else {
						item.isShow = scope.isExpandALL; 
					}
					// 当某组条件的数量大于5的时候，默认显示5个，其余的点击更多后展示
					item.isAll = item.items.length <= 5 ? true : false;
					if (item.queryType != "comboTree") {
						for ( var j in item.items) {
							var inner = item.items[j];
							inner.show = true;
							if (inner.checked) {
								scope.changeIt(inner, item);
							}
							if (j >= 5) {
								inner.show = false;
							}
						}
					}
				}
			};
			if(!scope.noborder){
				scope.noborder = true;
			}
		}
	};
});/**
 * 查询树组件
 * 
 * <div cg-combo-tree source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData = {
//		  id :1,
//		  name: "全校",
//		  children : [
//		           {id:2,name:"计算机与信息工程学院",children:[{id:3,name:"计算机"},{id:4,name:"软件工程"}]},
//		           {id:3,name:"体育学院",children:[{id:3,name:'健美'},{id:4,name:'体操'}]},
//		           {id:4,name:"建筑工程工程学院",children:[{id:3,name:'造价'},{id:4,name:'城市工程'}]},
//		           {id:5,name:"会计学院",children:[{id:6,name:'国际贸易'},{id:7,name:'银行电算'}]}]
//	  }
//	  $scope.treeResult = {};
 * 
 */
system.directive('cgComboTree', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/comboTree.html',
		scope : {
			source:"=",
			result:"=",
			code  : "=",
			group : "="
		},
		link : function(scope, element, attrs) {
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				dt.nodeId = nodeId++;
				if (dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
				}
			};
			scope.selectTree = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				//scope.result = {};
				angular.copy({},scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				scope.packageSourceData(scope.treeData);
				
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
			},true);
			
			//寻找父节点,找到后将父节点塞进选定节点容器
			scope.findPanrentOfNode = function(node,tree){
				if(node.nodeId != tree.nodeId){
					if (node.parentNodeId == tree.nodeId) {
						scope.selectTree.push(tree);
						scope.findPanrentOfNode(tree,scope.treeData);
					}else{
						if (tree.children) {
							for ( var i = 0; i < tree.children.length; i++) {
								scope.findPanrentOfNode(node,tree.children[i]);
							}
						}
					}
				}
			};
			
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//置空容器
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(node);
				scope.findPanrentOfNode(node,scope.treeData);
				//由于生成的节点容器将树倒置，所以翻转容器
				scope.selectTree.reverse();
				
				//备份数据，并清空树的数据，实现点击后将树隐藏
				var treeDataBak = angular.copy(scope.selectTree);
				scope.selectTree = [];
				$interval(function() {
					//重新给树赋值
					scope.selectTree = treeDataBak;
			     }, 1,1);
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				
				scope.result.queryCode = angular.copy(scope.code);
				scope.result.queryType = "comboTree";
				scope.result.group = angular.copy(scope.group);
			};
			
			var defaultCheckedHasFind = false;
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
				if (dt.checked) { 
					defaultCheckedHasFind = true;
					scope.cgComboTreeNodeClick(dt);
				}else if(dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						if (defaultCheckedHasFind) {
							defaultCheckedHasFind = false;
							break;
						}else{
							scope.findeDefaultChecked(dt.children[i]);
						}
					}
				}
			};
		}
	};
}]);
system.directive('cgSelect', ['$parse', '$timeout', function ($parse,$timeout) {
	var temp = '<div class="btn-group cg-select-dropdown">' +
				'<button type="button" class="btn btn-default ss-drop-input dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
	            	'<span class="input-area">{{display}}</span>' +
	            	'<span class="pull-right">' +
	                    '<span class="line"></span>' +
	                    '<span class="caret"></span>' +
	                    '<span class="sr-only">Toggle Dropdown</span>' +
	                '</span>' +
	            '</button>' +
	            '<ul class="dropdown-menu dropdown-menu-scrollbar">' +
	                '<li ng-repeat="item in list" ng-click="selectOption(item,$index)" ng-class="{active:value==item.id}"><a href="">{{item.mc}}</a></li>' +
	            '</ul>' +
           '</div>';
	return {
		restrict: 'A',
		scope: {
			list  : '=data',
			value : '=ngModel',
        	valueField   : '@valueField',
        	displayField : '@displayField',
        	onChange : '&'
		},
		template: temp,
		link: function (scope, element, attrs) {
			var displayField = scope.displayField || 'mc',
				valueField   = scope.valueField || 'id';
			scope.$parent.$watch(attrs.data, function(data){ // watch attr.data与list的区别
//			scope.$parent.$watch("list", function(data){
            	scope.list = data;
            	$timeout(function(){
            		scope.$apply();
            	});
            })
			scope.$watch("value", function(newVal, oldVal){
				if(newVal != oldVal){
					setValue(newVal, scope.list, scope);
				}
			})
			scope.selectOption = function(item, $index){
				var val = item[valueField];
				if(val == scope.value) return;
				scope.value   = val;
				scope.display = item[displayField];
				if(scope.onChange){
					scope.onChange({
						$value : item[valueField],
                		$data  : item
                	});
				}
			}
			var setValue = function(value, list, scope){
            	if(!value || value == null){
            		scope.display = null;
            		return;
            	}
            	for(var i=0,len = list.length;i<len;i++){
            		var item = list[i];
            		if(item[valueField] == value){
                        scope.value = item[valueField];
                        scope.display = item[displayField];
            			return;
            		}
            	}
            }
			setValue(scope.value, scope.list, scope); // 加载默认值
		}
	}
	
}]);
/*system.directive('select', ['$parse', '$timeout', function ($parse,$timeout) {
	var temp = '<div class="dropdown">' +
		  '<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aropup="true" aria-haspia-expanded="true">' +
		    '{{value}}' +
		    '<span class="caret"></span>' +
		  '</button>' +
		  '<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">' +
		    '<li ng-repeat="item in list" ng-click="selectOption(item,$index)"><a href="">{{item.mc}}</a></li>' +
		  '</ul>' +
		'</div>';
	return {
        restrict: 'A',
        scope: {
        	onSelected : '&',
        	list : '=data',
        	valueField : '&valueField',
        	displayField : '=displayField',
        	value : '=ngModel'
        },
        transclude: true,
        template: temp,
        link: function (scope, element, attrs) {
        	var valueField   = attrs.valueField,//实际值，赋予ng-model的值
            	displayField = attrs.displayField;//显示值字段
        	var listSize = null, selectIndex;
        	scope.$parent.$watch(attrs.data,function(data){
            	scope.list = data;
            	if(scope.list){
        			listSize = scope.list.length;
            		setValue(scope.value, scope.list, scope);
        		}
            	$timeout(function(){
            		scope.$apply();
            	});
            })
            scope.selectOption = function (item,$index) {
//            	selected = true;//标识是下拉选择值变换
//                scope.listShow = false;
//                scope.$parent.$item = item;
                //if(setter)setter(scope.$parent, item[valueField]);
//                input.val(item[displayField]);//
                scope.value   = item[valueField];
                scope.display = item[displayField];
                
//                delete scope.$parent.$item;
                
                scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                selectIndex = $index;//赋予当前选中行的值
                scope.selectLi(selectIndex);//为当前选中行添加样式
                
                if(scope.onSelected)
                	scope.onSelected({
                		$data : 123
                	});
            }
        	scope.selectLi = function(index){
            }
            scope.unselectLi = function(index){
            }
            var setValue = function(value, list, scope){
            	if(!value || value == null){
            		scope.display = null;
            		return;
            	}
            	for(var i=0,len = list.length;i<len;i++){
            		var item = list[i];
            		if(item[valueField] == value){
            			selected = true;//标识是下拉选择值变换
                        scope.value   = item[valueField];
                        scope.display = item[displayField];
                        scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                        selectIndex = i;//赋予当前选中行的值
                        scope.selectLi(selectIndex);//为当前选中行添加样式
            			return;
            		}
            	}
            };
        }
        
	}
}]);*//**
 * 局部遮罩指令
 */
system.directive('partModal',[function() {
	return {
		restrict : 'AE',
		scope: {
			showModal : "="
		},
		link : function(scope, element, attrs) {
			//生成遮罩层
			element.css({
				"position" : "relative"
			});
			var modal = $("<div>"+
					    	"<div style='position: absolute;left: 50%;top: 35%;margin-left: -30px;width: 60px;text-align: center;color: #FFF;'>" +
					    		" <i class='fa fa-spinner fa-pulse fa-2x'></i>" +
					    	"</div>"+
					    "</div>").css({
					    	"background-color" : "rgb(180, 180, 180)",
					    	opacity : "0.4"
					    });
			var html = $("<div />").css({
				position : "absolute",
				left : 0,
				top : 0,
			    "z-index" : 999
			});
			modal.appendTo(html);
			element.append(html);
			
			//监听遮罩层的显示控制
			scope.$watch('showModal',function(val){
				if(val){
					modal.css({
				    	height : "inherit",
				    	width : "inherit",
				    });
					html.css({
						height :  "100%",
						width :  "100%",
					})
					html.fadeIn('fast');
				}else{
					html.hide();
				}
			});
		}
	}
}]);
system.controller('PaginationController', ['$scope', '$attrs', '$parse', function ($scope, $attrs, $parse) {
  var self = this,
      ngModelCtrl = { $setViewValue: angular.noop }, // nullModelCtrl
      setNumPages = $attrs.numPages ? $parse($attrs.numPages).assign : angular.noop;

  this.init = function(ngModelCtrl_, config) {
    ngModelCtrl = ngModelCtrl_;
    this.config = config;

    ngModelCtrl.$render = function() {
      self.render();
    };

    if ($attrs.itemsPerPage) {
      $scope.$parent.$watch($parse($attrs.itemsPerPage), function(value) {
        self.itemsPerPage = parseInt(value, 10);
        $scope.totalPages = self.calculateTotalPages();
      });
    } else {
      this.itemsPerPage = config.itemsPerPage;
    }
  };

  this.calculateTotalPages = function() {
    var totalPages = this.itemsPerPage < 1 ? 1 : Math.ceil($scope.totalItems / this.itemsPerPage);
    return Math.max(totalPages || 0, 1);
  };

  this.render = function() {
    $scope.page = parseInt(ngModelCtrl.$viewValue, 10) || 1;
  };

  $scope.selectPage = function(page) {
    if ( $scope.page !== page && page > 0 && page <= $scope.totalPages) {
      ngModelCtrl.$setViewValue(page);
      ngModelCtrl.$render();
    }
  };

  $scope.getText = function( key ) {
    return $scope[key + 'Text'] || self.config[key + 'Text'];
  };
  $scope.noPrevious = function() {
    return $scope.page === 1;
  };
  $scope.noNext = function() {
    return $scope.page === $scope.totalPages;
  };

  $scope.$watch('totalItems', function() {
    $scope.totalPages = self.calculateTotalPages();
  });

  $scope.$watch('totalPages', function(value) {
    setNumPages($scope.$parent, value); // Readonly variable

    if ( $scope.page > value ) {
      $scope.selectPage(value);
    } else {
      ngModelCtrl.$render();
    }
  });
}])

.constant('paginationConfig', {
  itemsPerPage: 10,
  boundaryLinks: false,
  directionLinks: true,
  firstText: '《',
  previousText: '〈',
  nextText: '〉',
  lastText: '》',
  rotate: true
})

.directive('pagination', ['$parse', 'paginationConfig', function($parse, paginationConfig) {
  return {
    restrict: 'EA',
    scope: {
      totalItems: '=',
      firstText: '@',
      previousText: '@',
      nextText: '@',
      lastText: '@'
    },
    require: ['pagination', '?ngModel'],
    controller: 'PaginationController',
    template:  "<ul class=\"pagination\">\n" +
	    "  <li ng-if=\"boundaryLinks\" ng-class=\"{disabled: noPrevious()}\"><a href ng-click=\"selectPage(1)\">{{getText('first')}}</a></li>\n" +
	    "  <li ng-if=\"directionLinks\" ng-class=\"{disabled: noPrevious()}\"><a href ng-click=\"selectPage(page - 1)\">{{getText('previous')}}</a></li>\n" +
	    "  <li ng-repeat=\"page in pages track by $index\" ng-class=\"{active: page.active}\"><a href ng-click=\"selectPage(page.number)\">{{page.text}}</a></li>\n" +
	    "  <li ng-if=\"directionLinks\" ng-class=\"{disabled: noNext()}\"><a href ng-click=\"selectPage(page + 1)\">{{getText('next')}}</a></li>\n" +
	    "  <li ng-if=\"boundaryLinks\" ng-class=\"{disabled: noNext()}\"><a href ng-click=\"selectPage(totalPages)\">{{getText('last')}}</a></li>\n" +
	    "</ul>",
    replace: true,
    link: function(scope, element, attrs, ctrls) {
      var paginationCtrl = ctrls[0], ngModelCtrl = ctrls[1];

      if (!ngModelCtrl) {
         return; // do nothing if no ng-model
      }

      // Setup configuration parameters
      var maxSize = angular.isDefined(attrs.maxSize) ? scope.$parent.$eval(attrs.maxSize) : paginationConfig.maxSize,
          rotate = angular.isDefined(attrs.rotate) ? scope.$parent.$eval(attrs.rotate) : paginationConfig.rotate;
      scope.boundaryLinks = angular.isDefined(attrs.boundaryLinks) ? scope.$parent.$eval(attrs.boundaryLinks) : paginationConfig.boundaryLinks;
      scope.directionLinks = angular.isDefined(attrs.directionLinks) ? scope.$parent.$eval(attrs.directionLinks) : paginationConfig.directionLinks;

      paginationCtrl.init(ngModelCtrl, paginationConfig);

      if (attrs.maxSize) {
        scope.$parent.$watch($parse(attrs.maxSize), function(value) {
          maxSize = parseInt(value, 10);
          paginationCtrl.render();
        });
      }

      // Create page object used in template
      function makePage(number, text, isActive) {
        return {
          number: number,
          text: text,
          active: isActive
        };
      }

      function getPages(currentPage, totalPages) {
        var pages = [];
        // Default page limits
        var startPage = 1, endPage = totalPages;
        var isMaxSized = ( angular.isDefined(maxSize) && maxSize < totalPages );

        // recompute if maxSize
        if ( isMaxSized ) {
          if ( rotate ) {
            // Current page is displayed in the middle of the visible ones
            startPage = Math.max(currentPage - Math.floor(maxSize/2), 1);
            endPage   = startPage + maxSize - 1;

            // Adjust if limit is exceeded
            if (endPage > totalPages) {
              endPage   = totalPages;
              startPage = endPage - maxSize + 1;
            }
          } else {
            // Visible pages are paginated with maxSize
            startPage = ((Math.ceil(currentPage / maxSize) - 1) * maxSize) + 1;

            // Adjust last page if limit is exceeded
            endPage = Math.min(startPage + maxSize - 1, totalPages);
          }
        }

        // Add page number links
        for (var number = startPage; number <= endPage; number++) {
          var page = makePage(number, number, number === currentPage);
          pages.push(page);
        }

        // Add links to move between page sets
        if ( isMaxSized && ! rotate ) {
          if ( startPage > 1 ) {
            var previousPageSet = makePage(startPage - 1, '...', false);
            pages.unshift(previousPageSet);
          }

          if ( endPage < totalPages ) {
            var nextPageSet = makePage(endPage + 1, '...', false);
            pages.push(nextPageSet);
          }
        }

        return pages;
      }

      var originalRender = paginationCtrl.render;
      paginationCtrl.render = function() {
        originalRender();
        if (scope.page > 0 && scope.page <= scope.totalPages) {
          scope.pages = getPages(scope.page, scope.totalPages);
        }
      };
    }
  };
}])

.constant('pagerConfig', {
  itemsPerPage: 10,
  previousText: '« Previous',
  nextText: 'Next »',
  align: true
})

.directive('pager', ['pagerConfig', function(pagerConfig) {
  return {
    restrict: 'EA',
    scope: {
      totalItems: '=',
      previousText: '@',
      nextText: '@'
    },
    require: ['pager', '?ngModel'],
    controller: 'PaginationController',
    template: 
    	"<ul class=\"pager\">\n" +
	    "  <li ng-class=\"{disabled: noPrevious(), previous: align}\"><a href ng-click=\"selectPage(page - 1)\">{{getText('previous')}}</a></li>\n" +
	    "  <li ng-class=\"{disabled: noNext(), next: align}\"><a href ng-click=\"selectPage(page + 1)\">{{getText('next')}}</a></li>\n" +
	    "</ul>",
    replace: true,
    link: function(scope, element, attrs, ctrls) {
      var paginationCtrl = ctrls[0], ngModelCtrl = ctrls[1];
      if (!ngModelCtrl) {
         return; // do nothing if no ng-model
      }
      scope.align = angular.isDefined(attrs.align) ? scope.$parent.$eval(attrs.align) : pagerConfig.align;
      paginationCtrl.init(ngModelCtrl, pagerConfig);
    }
  };
}]);/**
 * 日期指令
 *  <div cs-window show="flag" autoCenter="true" offset="offset">窗体</div>
 */
system.directive('csWindow',['$timeout',function($timeout){
	//创建遮罩
	var body = $("body");//获取body元素
    var mask = $('<div class="cs-window-background"></div>');
    mask.hide();
    body.append(mask);
    var offset = function(el,autoCenter){
        var body = $(window),
            w = el.width(),
            h = el.height(),
            bw = body.outerWidth(true),
            bh = body.outerHeight(true),
            top = (bh-h)/2-40,
            left = (bw-w)/2;
        // 修复弹出框超出窗口顶部，左部
        top  = top<0 ? 0 : top;
        left = left<0 ? 0 : left;
        if(autoCenter == "true"){
              el.css("left",left + "px");
              el.css("top",top + "px");
//              el.css("right", "8%"); // 取消自适应：自适应会让小窗口内容显示效果不好 20161221
        }
    }
    var offset1 = function(el,offset){
    	el.offset(offset);
    }
    return {
        restrict : 'A',
        scope : {
            show : '=show',
            offset : '=offset',
            clickDisappear : '=clickDisappear'
        },
        link : function(scope, element, attrs){
            var autoCenter = attrs.autoCenter || "true",
                m = attrs.mask || "true",
                disappear = attrs.clickDisappear || true; // 默认鼠标点击外部区域窗口消失 20161221
            
            var windowClickEvent = false;
            
            scope.$watch('show',function(newV,oldV){
                if(newV == true){
                    element.show();
                    if(m === "true")mask.show();
                    if(disappear){
                    	$timeout(function(){
                        	$(document).one('click',function(event){
                        		scope.show = false;
                        		$timeout(function(){
                        			scope.$apply();
                        		});
                        	});
                    	});
                    }
                    $timeout(function(){
                    	scope.reOffset();
                	});
                }else{//如果值不是通过document click事件进行改变的，需要首先对document的click事件解除绑定
//                	$(document).unbind('click'); // 解除文档click事件影响巨大 20160808
                    element.hide();
                    mask.hide();
                }
            });
            if(disappear){
            	if(!windowClickEvent){//如果值false，则表明未初始化click事件，注册click事件，阻止冒泡
            		element.on('click',function(event){
            			event.stopPropagation();
            		});
            		windowClickEvent = true;
            	}
            }
            scope.$watch('offset',function(newV,oldV){
                offset1(element,newV);//手动移动到指定位置
            });
            //判断是否显示window窗口
            if(scope.show) element.show(); else element.hide();
            //添加window样式
            element.addClass('cs-window-windows');

            //设置元素居中
            scope.reOffset=function(){
	           	 if(!scope.offset)offset(element,autoCenter);
	             else offset1(element,scope.offset);//手动移动到指定位置
            };
            $(window).resize(function () {  
            	scope.reOffset();
            });
            scope.reOffset();
        }
    }
}]);/**
 * 日期指令
 * <input type="text" maxValue="endDate" ng-model="startDate" datepicker/>
 * <input type="text" minValue="startDate" ng-model="endDate" datepicker/>
 */
system.directive('csDatepicker',['$parse','$timeout',function($parse,$timeout){
    return {
        restrict : 'A',
        scope : {
            maxValue : '=maxvalue',
            minValue : '=minvalue',
            onChange : '&onChange'
        },
        link : function(scope, element, attrs){
            var onChange = scope.onChange,
                maxValue = scope.maxValue,
                minValue = scope.minValue,
                clickId = attrs.clickId,
                getter = $parse(attrs.ngModel),
                setter = getter.assign;
            var fn = function(dp){
                setter(scope.$parent,dp.cal.getDateStr());
                if(onChange){
                   onChange();
                }
                $timeout(function(){
                	scope.$apply(function(){});
                });
            };
            var config = {
                onpicked:fn,dateFmt:'yyyy-MM-dd',readOnly:true,
                doubleCalendar : true,autoUpdateOnChanged : true,
                oncleared : fn
            };
            if(maxValue)config.maxDate = maxValue;
            if(minValue)config.minDate = minValue;
            element.on('focus',function(){
            	if(scope.maxValue)config.maxDate = scope.maxValue;
                if(scope.minValue)config.minDate = scope.minValue;
                WdatePicker(config);
            });
            if(clickId)
                $("#"+clickId).on('click',function(){
                    if(scope.maxValue)config.maxDate = scope.maxValue;
                    if(scope.minValue)config.minDate = scope.minValue;
                    element.focus();
                });
        }
    }
}]);/*************************************************
 弹出层form 
 功能介绍 ： 
	  弹出form，分页显示数据
	  
	  config : {
	  	title : "",
		show : false,
		url : "",
		exportUrl : "",// 为空则不显示导出按钮
		heads : [],
		fields : [],
		params : {
			
		}
	  }
 例子见 ：static/angular_expand/example/modalForm.jsp
 ************************************************/
system.directive('modalForm',["httpService",function (http) {
    return {
        restrict: 'AE',
        templateUrl : base + '/static/angular_expand/pc/directives/tpl/modalForm.html',
        scope: {
        	config: "="
        },
        link : function(scope, element, attrs) {
        	//隐藏form弹出
        	scope.hideModalForm = function(){
        		scope.config.show = false;
        	}
        	//空白地方点击隐藏form
        	element.click(function(e){
        		scope.hideModalForm();
        		scope.$apply();
        	}).find(".modal-dialog").click(function(e){
        		e.stopPropagation();
        	});
        	
        	//监听显示控制，重置分页参数
        	scope.$watch("config.show",function(newval){
        		if(newval == true){
        			element.find(".fade").show().scrollTop(0).addClass("in");
        			scope.vm.curpage = 1;
        			scope.vm.rows  = [];
        			scope.vm.total = 0;
        			scope.sortColumn = null; // 每次打开重置排序字段
        		}else{
        			element.find(".fade").removeClass("in").fadeOut('fast');
        			scope.vm.curpage = 0;
        		}
        	});
        	//分页参数
        	scope.vm = {
    			showModal : false,
    			pagesize: 10,
    			curpage : 1,
    			rows  : [],
    			total : 0,
    			pageTotal : 0
        	};
        	//监听分页参数，请求后台数据
        	scope.$watch("vm.curpage",function(newval,oldval){
        		if(newval != 0){
        			scope.vm.showModal = true;
        			scope.queryPageItems(scope.config.params).then(function(data){
        				scope.vm.showModal = false;
        				scope.vm.rows  = data.rows;
        				scope.vm.total = data.total;
        				scope.vm.pageTotal = parseInt(scope.vm.total%scope.vm.pagesize == 0 ? scope.vm.total/scope.vm.pagesize : scope.vm.total/scope.vm.pagesize+1);
        			})
        		}
        	});
        	//请求数据方法
        	scope.queryPageItems = function(params){
        		var page = {
					curpage  : scope.vm.curpage,
					pagesize : scope.vm.pagesize,
					sumcount : scope.vm.total,
					sortColumn : scope.sortColumn,
    				order : scope.sortOrder
        		}
        		page = JSON.stringify(page);
        		return http.post({
        			url  : scope.config.url,
        			data : angular.extend({
        				page : page
    				},params)
    				/*data : angular.extend({
    					curpage : scope.vm.curpage,
    					pagesize : scope.vm.pagesize,
    					sortColumn : scope.sortColumn,
    					sortOrder : scope.sortOrder
    				},params)*/
        		});
        	}
        	//下载方法
        	scope.download = function(){
        		 /*$.fileDownload(base + scope.config.exportUrl,{
         			data : angular.extend({
     					curpage : 1,
         				pagesize : scope.vm.total,
         				heads : angular.toJson(scope.config.heads),
         				fields : angular.toJson(scope.config.fields),
         				filename : scope.config.title
     				},scope.config.params)
         		});*/
        		var page = {
					curpage  : 1,
					pagesize : scope.vm.total,
					sortColumn : scope.sortColumn,
    				order : scope.sortOrder
        		}
        		page = JSON.stringify(page);
         		var params = scope.config.params;
         		http.fileDownload({
              		url  : scope.config.exportUrl,
              		data : angular.extend({
              				fileName : scope.config.title,
              				page : page
 	    				},params),
             	})
        	}
        	//列排序方法
        	scope.orderByColumn = function(field){
        		if(scope.sortColumn == field ){
        			if(scope.sortOrder == 'asc')
        				scope.sortOrder = 'desc';
        			else if(scope.sortOrder == 'desc'){
        				scope.sortColumn = '';
        			}
        		}else{
        			scope.sortOrder = 'asc';
        			scope.sortColumn = field;
        		}
        		//请求数据
        		scope.vm.showModal = true;
    			scope.queryPageItems(scope.config.params).then(function(data){
    				scope.vm.showModal = false;
    				scope.vm.rows = data.rows;
    			});
        	}
        }
    };
}]);/**
 * Bootstrap提示工具 : toolTip
 */
system.directive('toolTip', [function() {
	return {
		restrict : 'AE',
		transclude : true,
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/tooltip.html',
		scope : {
			placement : "@",
			hideIcon : "@"
		},
		link : function(scope, element, attrs) {
			element.addClass("tooltip-container");
			element.on("mouseenter",function(){
				var eleHeight = element.height();
				var eleWidth = element.width();
				var elePosition = element.position()
				var eleLeft = elePosition.left;
				var eleTop  = elePosition.top;
				
				var content = element.find(".popover");
				var contWidth = content.width();
				var contHeight = content.height();
				
				var contLeft = -200,contTop = -200;
				switch (scope.placement) {
					case 'top':
						contLeft = eleLeft +  (eleWidth - contWidth)/2 - 2;
						contTop = eleTop - contHeight - 4;
						break;
					case 'right':
						contLeft = eleLeft + eleWidth;
						contTop = eleTop + (eleHeight-contHeight)/2 - 4;
						break;
					case 'bottom':
						contLeft = eleLeft + (eleWidth - contWidth)/2 - 2;
						contTop = eleTop + eleHeight - 2;
						break;
					case 'left':
						contLeft = eleLeft - contWidth - 4;
						contTop =  eleTop + (eleHeight-contHeight)/2 - 4;
						break;
					default:
						contLeft = eleLeft + eleWidth - 2;
						contTop = eleTop + (eleHeight-contHeight)/2 - 2;
						break;
				}
				content.css({
					left : contLeft,
					top : contTop
				})
			});
		}
	};
}]);