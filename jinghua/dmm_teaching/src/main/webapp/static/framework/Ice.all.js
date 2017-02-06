/**
 * 组件库 Ice
 */
var Ice = Ice || {};
(function(Ice){
	var objectPrototype = Object.prototype,
    	toString = objectPrototype.toString,
    	enumerables = [//'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable',
                   'valueOf', 'toLocaleString', 'toString', 'constructor'];
	for (i in { toString: 1 }) {
        enumerables = null;
    }
	
	/**
	 * 深度赋值
	 * @param object 原数据
	 * @param config copy数据
	 * @returns object
	 */
	Ice.apply = function(object, config){
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
	Ice.applyDept = Ice.apply;
	
	
})(Ice);
/**
 * Ice扩展
 */
var Ice = Ice || {};
(function(Ice){

	/**
	 * 将数组中对象的 "name"改成"field"
	 * @param ary 数组
	 * @param prefix 前缀
	 * @param suffix 后缀
	 */
	Ice.changeName2Field = function(ary, prefix, suffix){
		prefix = prefix || '';
		suffix = suffix || '';
		for(var i=0,len=ary.length; i<len; i++){
			var d = ary[i];
			d.field = prefix+d.name+suffix;
			delete d.name;
		}
		return ary;
	};
	
	/**
	 * 获取数组
	 */
	var getAry = function(data){
		var ary = null;
		if(data){
			if(data instanceof Array)
				ary = data;
			else if(data instanceof String)
				ary = [data];
		}
		return ary;
	};
	
	/**
	 * 处理echartOption
	 * 
	 * @param list 数据集合
	 * 数据格式需注意如下标识
	 * @1:不可变； @2：可配置； @3：自定义字段
	 *  <br> 格式1：
	 	[{
	 		name  : '文学院', // @1
	 		value : '1385', // @2
	 		code  : '' // @3
	 	}]
	 	<br> 格式2：支持堆叠
	 	[{
	 		name  : '文学院', // @1
	 		list  : [{  // @2
	 			name  : '国家奖学金', // @1
	 			value : '78', // @2
	 			code  : '' // @3
	 		},{
	 			name  : '国家励志奖学金', // @1
	 			value : '451', // @2
	 			code  : ''
	 		}],
	 		code  : '' // @3
	 	}]
	 * @param config 配置
	 * 	<br>
	 	{
	 		lengend_ary  : ['国家奖学金','国家励志奖学金'], // 图例（格式1：【需配置】；格式2：【解析name属性】）
	 		yname_ary    : ['人数', '比例'], // Y轴单位（与 type_ary、value_ary 对应）【需配置】
	 		xname_ary    : ['人', '%'], // X轴单位【需配置】
	 		type_ary     : ['bar', 'line'], // 图标类型（与 yname_ary、value_ary 对应）【需配置】
	 		value_ary    : ['count', 'scale'], // 数据对应的字段（与 yname_ary、type_ary 对应）【默认['value']】
	 		stack        : 'a'||boolean, // 是否堆叠（echart标准设置，不等于'',null即可）【默认空】
	 		sort_legend  : ''||boolean, // 图例翻转（堆叠默认翻转图例，可配置）【默认不翻转】
	 		key_list     : '', // 堆叠数据的集合 key（如：格式2中的 list）【默认'list'】
	 		title        : '', // 标题【默认空】
	 		xname_append : true/false, // X轴单位是否拼接在xAxis上（拼接 且 X轴有单位时(xname_ary.length>0)拼接；其他情况则不拼接，直接显示单位）
	 		yIndex_ary   : [0, 0, 1], // 数据对应Y轴
	 		config : {
	 			noDataText : '暂无性别分布数据'
	 		}
	 	}
	 */
	Ice.echartOption = function(list2, config){
		var list = [];
		Ice.apply(list, list2); // 修复list2在处理方法之后被改变结构的问题 20161121
		// 固定设置，不可变
		var name = 'name';
		// 配置
		var legend_ary  = getAry(config.legend_ary) || [], // 图例
			yname_ary   = getAry(config.yname_ary) || [], // Y轴单位
			xname_ary   = getAry(config.xname_ary) || [], // X轴单位
			type_ary    = getAry(config.type_ary) || [], // 图表类型
			value_ary   = getAry(config.value_ary) || ['value'], // value所对应字段
			stack       = (config.stack!=null && config.stack!='') ? true : false, // 是否堆叠
			sort_legend = config.sort_legend || stack, // 图例翻转（堆叠及翻转）
			key_list    = config.key_list || 'list', // 键：堆叠数据集合的key
			title       = config.title || '',
			yIndex_ary  = getAry(config.yIndex_ary) || [],
			xname_append= config.xname_append && xname_ary.length > 0 || false; // X轴拼接 且 有单位
		// 常量
		var	xAxis_data  = [], // x 轴
			series_data = []; // 系列
		// data 处理
		for(var i=0,len=list.length; i<len; i++){
			var obj = list[i], name_ = obj[name];
			name_ += xname_append ? xname_ary[0] : '';
			// x轴
			xAxis_data.push(name_);
			// 判断是否是两组数据
			if(obj[key_list] && obj[key_list] instanceof Array){
				var li = obj[key_list];
				for(var j=0,lenJ=li.length; j<lenJ; j++){
					if(!series_data[j]) series_data[j] = [];
					series_data[j].push(li[j][value_ary[0]] || 0);
					// 图例（格式2数据自动解析图例）
					if(legend_ary.length < j+1 && li[j][name])
						legend_ary.push(li[j][name]);
				}
			}else{
				// 转换非标准双纵轴 TO 标准双纵轴 20160829
				var _list_ = [],
					_o_ = {
					name : name_,
					list : _list_,
					code : list[i].code || ''
				};
				// 处理 series_data
				for(var k=0,lenK=value_ary.length; k<lenK; k++){
					if(!series_data[k]) series_data[k] = [];
					series_data[k].push(obj[value_ary[k]] || 0);
					_list_.push({name:legend_ary[k]||'', value:obj[value_ary[k]], code:value_ary[k]});
				}
				list[i] = _o_; // 替换纵轴源数据 20160829
			}
		}
		// Y轴（Y轴必须要有一个值，无论是否有Y轴单位）
		var yAxis = [], i=0, len=yname_ary.length;
		do{
			var axis = {
				type : 'value',
				name : yname_ary[i] || ''	
			}
			if(i>0){
	        	axis.splitLine = {show:false}; 
	        	axis.splitArea = {show:false}; 
			}
			yAxis.push(axis);
		}while(++i<len);
		// X轴
		var xAxis = [];
		if(xname_ary.length > 0){
			for(var i=0,len=xname_ary.length; i<len; i++){
				xAxis.push({
					type : 'category',
	                data : xAxis_data,
	                name : (xname_ary[i]&&!xname_append) ? xname_ary[i] : '' // X轴有单位 且 不拼接时，显示单位
				});
			}
		}else{
			xAxis.push({
				type : 'category',
                data : xAxis_data
			});
		}
		// series
		var series = [];
		for(var i=0,len=series_data.length; i<len; i++){
			var series_obj = {
				name : legend_ary[i] || '',
				type : type_ary[i] || type_ary[0],
				data : series_data[i],
				yAxisIndex : yname_ary.length<=1 ? 0 : (yIndex_ary.length>i ? yIndex_ary[i] : i)
			}
			if(stack) series_obj.stack = stack; // 堆叠
			series.push(series_obj);
		}
		// 鼠标浮动提示
		var farmatter = null, isFarmatterCount = true; // 是否允许叠加显示
		var magicType =[];
		if(isFarmatterCount){
			var xAxis_name = xAxis[0].name || '', // X轴单位 
				yAxisIndex = 0, // 使用哪个Y轴，Y轴下标
				yAxis_name = yAxis[0].name; // Y轴单位，eg：人，%
			// 判断是否启用 all提示
			for(var i=0,len=series.length; i<len; i++){
				if(i+1<len && (series[i].type!=series[i+1].type || yAxis.length>1)){
					isFarmatterCount = false; break;
				}
			}
			// 操作图标
			if(isFarmatterCount) magicType = ['stack','tiled'];
			farmatter = function(params){
	            var dataName = params[0].name, // X轴名称，eg：信息工程学院
	            	seriesName, count = 0, val = 0, htm = '';
		        for (var i=0, l=params.length; i<l; i++) {
		          	val = params[i].value;
		          	count += val;
		          	yAxisIndex = params[i].series.yAxisIndex || 0;
		          	yAxis_name = yAxis[yAxisIndex].name || '';
		          	seriesName = params[i].seriesName || '';
		            htm += '<br/>' + (seriesName=='' ? '' : (seriesName+' ： ')) +val+yAxis_name;
		        }
		        var other = (stack&&isFarmatterCount) ? (' ： 共' + count + yAxis_name) : '';
		      	return dataName+xAxis_name + other + htm;
		    };
		}
		// 图例翻转
		legend_ary = sort_legend ? legend_ary.reverse() : legend_ary;
		var option = {
			title : {
				text : title
			},
            tooltip : {
                trigger : 'axis',
                formatter : farmatter,
                axisPointer : { // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            toolbox: {
			        show : true,
			        feature : {
			        	dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: magicType},
			            restore : {show: true},
			            saveAsImage : {show: true}
				     }
				 },
            legend: {
                x   : 'left',
                data   : legend_ary
            },
            xAxis : xAxis,
            yAxis : yAxis,
            series : series
		};
		// 如果有其他原生配置
		if(config.config){
			option.config = config.config;
		}
		Ice.apply(option, {config:{_data_:list}}); // 缓存 data
		return option;
	};
	
	/**
	 * 获取echart中某一列的值并设置堆叠
	 * @deprecatede 暂不用
	 */
	Ice.echartStackColumnOption = function(list, value_column, yname, legend, config){
		var cfg = {
			legend_ary : legend ? [legend] : [],
			yname_ary  : [yname], // 单位
			type_ary   : ['bar'], // 图标类型
			value_ary  : [value_column], // value所对应字段
			stack  : 'a'
		}
		for(var key in config){
			cfg[key] = config[key];
		}
    	var option = Ice.echartOption(list, cfg);
    	return option;
	};
	
})(Ice);