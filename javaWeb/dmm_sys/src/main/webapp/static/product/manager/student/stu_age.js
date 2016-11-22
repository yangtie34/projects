function ageConfigration(results) {
	var ageName = [];
	var ageData = [];
	$.each(results, function(i, o){
		ageName.push(o.AGENAME);
		ageData.push({"name":o.AGENAME,"value":o.COUNTS});
	});
	option = {
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : ageName
		},
		calculable : true,
		series : [{
					name : '访问来源',
					type : 'pie',
					radius : ['60%', '80%'],
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
								show : true,
								position : 'center',
								textStyle : {
									fontSize : '30',
									fontWeight : 'bold'
								}
							}
						}
					},
					data : ageData
				}]
	};
	var ageCharts = echarts.init(document.getElementById("age_main"));
	ageCharts.setOption(option);
}