function nationConfigration(results) {
	var nationName = [];
	var navtonData = [];
	$.each(results, function(i, o){
		nationName.push(o.NATIONNAME);
		navtonData.push({"name":o.NATIONNAME, "value":o.COUNTS});
	});
	option = {
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : nationName
		},
		calculable : false,
		series : [{
					name : '访问来源',
					type : 'pie',
					radius : [50, 90],
					x : '60%',
					width : '35%',
					funnelAlign : 'left',
					max : 1048,
					// 外环数据
					data : navtonData
				}]
	};
	var nationCharts = echarts.init(document.getElementById("nation_main"));
	nationCharts.setOption(option);
}