function teachingTaskPieConfiguration(divId, echartsTitle, results) {
	var pieData = [];
	var nameData = [];
	$.each(results, function(i, o) {
		nameData.push(o.NAME_);
		pieData.push({"name" : o.NAME_,"value" : o.THEORY_PERIOD});
	});
	pieData.push({"name" : "其他","value" : "100"});
	option = {
		title : {
			text : echartsTitle,
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
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
					data : pieData
				}]
	};
                    
	echarts.init(document.getElementById(divId)).setOption(option);
}