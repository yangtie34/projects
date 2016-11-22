function sexConfigration(datas) {
	var myChart = echarts.init(document.getElementById('main6'));
	var data = [];
	var dataName = [];
	
	$.each(datas, function(i, o){
		dataName.push(o.SEXNAME);
	});
	
	
	// i：循环当前下标，o是object对象
	$.each(datas, function(i, o) {
		data.push({
			name : o.SEXNAME,
			value : o.COUNTS
		});
	});

	option = {
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		
		 legend: { orient : 'vertical', x : 'left',
		 data:dataName},
		 
		calculable : true,
		series : [ {
			name : '性别分布',
			type : 'pie',
			radius : [ '50%', '70%' ],
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
			data : data
		} ]
	};
	myChart.setOption(option);
}