function educationConfigration(results, mainId) {
	var dataType = [];
	var dataObj = [];
	if(results){
		$.each(results, function(i, obj){
			dataType.push((obj.EDUNAME == null) ? "其他" : obj.EDUNAME);
			var dataElement = {};
			dataElement.value = obj.COUNTS;
			dataElement.name = (obj.EDUNAME == null) ? "其他" : obj.EDUNAME;
			dataObj.push(dataElement);
		});
	}
	option = {
		// 图标标题
		title : {
			show : false,
			text : '学历占比变化',
			textStyle : {
				fontSize : 14
			},
			subtext : '',
			x : 'right',
			y : 'bottom'
		},
		// 悬浮提示信息
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		// 图例
		legend : {
			orient : 'vertical',
			x : 'left',
			data : dataType
		},
		// 是否启动拖拽重计算特性，默认为关闭
		calculable : false,
		// 通用
		series : (function() {
			var series = [];
			for (var i = 0; i < 30; i++) {
				series.push({
							name : '',
							type : 'pie',
							itemStyle : {
								normal : {
									label : {
										show : i > 28
									},
									labelLine : {
										show : i > 28,
										length : 20
									}
								}
							},
							// 饼图的内外半径
							radius : [i * 3 + 20, i * 3 + 23],
							data : dataObj
						});
			}
			series[0].markPoint = {
				symbol : 'emptyCircle',
				symbolSize : series[0].radius[0],
				effect : {
					show : true,
					scaleSize : 12,
					color : 'rgba(250,225,50,0.8)',
					shadowBlur : 10,
					period : 30
				},
				data : [{
							x : '50%',
							y : '50%'
						}]
			};
			return series;
		})()
	};

	var educationCharts = echarts.init(document.getElementById(mainId));
	educationCharts.setOption(option);
}