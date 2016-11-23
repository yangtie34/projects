// 把日期类型转换成String类型
var dateToString = function(date) {
	return date.getFullYear() + "-" + (((date.getMonth() + 1) >= 10) ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1))) + "-" + (((date.getDate()) >= 10) ? (date.getDate()) : ("0" + (date.getDate())));
};
// 把String类型转换成日期类型
var stringToDate = function(dateString) {
	var dateStringArr = dateString.split("-");
	return new Date(dateStringArr[0], dateStringArr[1] - 1, dateStringArr[2]);
};
// 获取日期的前一天或后一天，前一天bA为true， 后一天bA为false
var beforOrAfterDate = function(dateString, bA) {
	var date;
	if (bA) {
		date = new Date(stringToDate(dateString).getTime() - (1000 * 60 * 60 * 24)); // 获取前一天
	} else {
		date = new Date(stringToDate(dateString).getTime() + (1000 * 60 * 60 * 24)); // 获取前一天
	}
	return dateToString(date);
};

/**
 * 折线统计图
 */
function conHabitLineConfiration(params, results) {

	var startDateStr;
	var endDateStr;
	if (params) {
		var dateNum = 15;
		var nowDate = new Date();
		startDateStr = params.startDateStr;
		endDateStr = params.endDateStr;
		if (endDateStr == undefined || endDateStr == "") {
			endDateStr = dateToString(nowDate);
		}
		if (startDateStr == undefined || startDateStr == "") {
			startDateStr = beforOrAfterDate(dateToString(new Date(nowDate.getTime() - (1000 * 60 * 60 * 24 * (dateNum)))), false);
		}
	}
	// 生成x轴的数据
	var autoxAxis = function() {
		var xAxisData = [];
		var dateStr = beforOrAfterDate(startDateStr, true); // 获取前一天
		do {
			dateStr = beforOrAfterDate(dateStr, false); // 获取后一天
			xAxisData.push(dateStr);
		} while (stringToDate(dateStr).getTime() < stringToDate(endDateStr).getTime());
		return xAxisData;
	};

	var manData = [];
	var wemanData = [];
	var xAxisData = autoxAxis();
	$.each(xAxisData, function(i, xDate) {
				var manPayNumbers = 0;
				var wemanPayNumbers = 0;
				$.each(results, function(i, o) {
							if (xDate == o.PAY_DATE) {
								if (o.STU_SEX == "男") {
									manPayNumbers = o.PAY_NUMBERS;
								}
								if (o.STU_SEX == "女") {
									wemanPayNumbers = o.PAY_NUMBERS;
								}
							}
						});
				manData.push(manPayNumbers);
				wemanData.push(wemanPayNumbers);
			});
	var option = {
		title : {
			text : "从" + startDateStr + "至" + endDateStr + "间" + params.echartsTitle,
			x : 'center'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			show : true,
			x : 'center',
			y : 'bottom',
			orient : 'horizontal', // vertical图例竖着放
			padding : [15, 15, 15, 15],
			borderWidth : 0,
			data : ['男', '女']
		},
		calculable : false,
		grid : {
			y2 : '25%' // x轴的高度
		},
		xAxis : [{
					name : "时间",
					type : 'category',
					axisLabel : {
						interval : '0',
						margin : 20,
						rotate : 60
					},
					data : xAxisData
				}],
		yAxis : [{
					name : "日生均消费笔数",
					show : true,
					type : 'value'
				}],
		series : [{
					name : '男',
					type : 'line',
					smooth : true,
					data : manData
				}, {
					name : '女',
					type : 'line',
					smooth : true,
					data : wemanData
				}]
	};
	selectOption("conHabitLine_main", option);
}

/**
 * 圆饼图
 */
function conHabitPieConfiration(params, results) {
	var startDateStr;
	var endDateStr;
	if (params) {
		var dateNum = 15;
		var nowDate = new Date();
		startDateStr = params.startDateStr;
		endDateStr = params.endDateStr;
		if (endDateStr == undefined || endDateStr == "") {
			endDateStr = dateToString(nowDate);
		}
		if (startDateStr == undefined || startDateStr == "") {
			startDateStr = beforOrAfterDate(dateToString(new Date(nowDate.getTime() - (1000 * 60 * 60 * 24 * (dateNum)))), false);
		}
	}
	var nameData = [];
	var chartsData = [];
	$.each(results, function(i, o) {
		nameData.push(o.LX);
		chartsData.push({"value" : o.STU_NUMBERS, "name" : o.LX});
	});
	var option = {
		title : {
			text : "从" + startDateStr + "至" + endDateStr + "间" + params.echartsTitle,
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} <br/> 达标人数: {c} ({d}%)"
		},
		legend : {
			show : false,
			orient : 'vertical',
			x : 'left',
			data : nameData
		},
		calculable : false,
		series : [{
					name : '访问来源',
					type : 'pie',
					radius : '55%',
					center : ['50%', '60%'],
					data : chartsData
				}]
	};
	selectOption("conHabitPie_main", option);
}

/**
 * 柱状图
 */
function conHabitBarConfiration(params, results) {
	var startDateStr;
	var endDateStr;
	if (params) {
		var dateNum = 15;
		var nowDate = new Date();
		startDateStr = params.startDateStr;
		endDateStr = params.endDateStr;
		if (endDateStr == undefined || endDateStr == "") {
			endDateStr = dateToString(nowDate);
		}
		if (startDateStr == undefined || startDateStr == "") {
			startDateStr = beforOrAfterDate(dateToString(new Date(nowDate.getTime() - (1000 * 60 * 60 * 24 * (dateNum)))), false);
		}
	}
	var nameData = [];
	var echartsData = [];
	$.each(results, function(i, o){
		nameData.push(o.XFQJ);
		echartsData.push(o.NUMBERS)
	});
	var option = {
		title : {
			text : "从" + startDateStr + "至" + endDateStr + "间" + params.echartsTitle,
			x : "center"
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : ['日生均消费区间分布'],
			x : "center",
			y : "bottom"
		},
		calculable : false,
		xAxis : [{
					type : 'category',
					data : nameData
				}],
		yAxis : [{
					name : "人数",
					show : true,
					type : 'value'
				}],
		series : [{
					name : '日生均消费区间分布',
					type : 'bar',
					data : echartsData
				}]
	};

	selectOption("conHabitBar_main", option);
}

function selectOption(divId, option) {
	echarts.init(document.getElementById(divId)).setOption(option);
}