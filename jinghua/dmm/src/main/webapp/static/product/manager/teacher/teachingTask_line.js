function teachingTaskLineConfiguration(divId, echartsTitle, results) {
	console.info(results);
	option = {
		title : {
			text : echartsTitle,
			x : 'center'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			x : 'right',
			orient : 'vertical', // 图例竖着放
			padding : [30, 30, 0, 0],
			borderWidth : 0,
			data : ['助教', '讲师', '副教授', '教授', '职称未维护']
		},
		calculable : true,
		xAxis : [{
					type : 'category',
					boundaryGap : true,
					data : ['外国语学院', '化学化工学院', '计算机科学与技术学院', '教育科学学院', '思想政治理论教研部']
				}],
		yAxis : [{
					show : true,
					type : 'value'
				}],
		series : [{
					name : '助教',
					type : 'line',
					stack : '总量',
					smooth : true,
					data : [120, 132, 101, 134, 90, 230, 210]
				}, {
					name : '讲师',
					type : 'line',
					stack : '总量',
					smooth : true,
					data : [220, 182, 191, 234, 290, 330, 310]
				}, {
					name : '副教授',
					type : 'line',
					stack : '总量',
					smooth : true,
					data : [150, 232, 201, 154, 190, 330, 410]
				}, {
					name : '教授',
					type : 'line',
					stack : '总量',
					smooth : true,
					data : [320, 332, 301, 334, 390, 330, 320]
				}, {
					name : '职称未维护',
					type : 'line',
					stack : '总量',
					smooth : true,
					data : [820, 932, 901, 934, 1290, 1330, 1320]
				}]
	};

	echarts.init(document.getElementById(divId)).setOption(option);
}