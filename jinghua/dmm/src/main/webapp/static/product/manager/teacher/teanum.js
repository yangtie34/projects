function teaNumBarOrline(divId, d) {
	var lables = [];
	var names = [];
	var legents = [];
	var nameFlag = true;
	var seriesObject = [];
	var lableTemp = "";
	for (var i = 0; i < d.length; i++) {
		if (lableTemp != d[i].lable) {
			lableTemp = d[i].lable;
			var values = [];
			for (var j = 0; j < d.length; j++) {
				if (lableTemp = d[j].lable) {
					if (nameFlag) {
						names.push(d[j].name);
					}
					values.push(d[j].value);
				} else {
					break;
				}
			}
			var series = {
				name : lableTemp,
				type : 'line',
				data : values
			};
			nameFlag = false;
			seriesObject.push(series);
			lables.push(lableTemp);
		}
	}

	var option = {
		title : {
			text : '人员变化情况',
			x:'left'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : lables
		},
		calculable : true,
		xAxis : [{
					type : "category",
					splitLine : {
						show : false
					},
					data : names
				}],
		yAxis : [{
					type : 'value',
					axisLabel : {
						formatter : '{value} 人'
					}
				}],
		series : seriesObject
	};
	var ageCharts = echarts.init(document.getElementById(divId));
	ageCharts.setOption(option);
}