function viewDashboard(results){
	//政治面貌分布
	var legents=[];
	var datas=[];
	$.each(results,function(i,o){
		legents.push(o.POLICALNAME);
		datas.push({name:o.POLICALNAME,value:o.COUNTS});
	});
	option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:legents
		    },
		    calculable : true,
		    series : [
		        {
		            type:'pie',
		            radius : ['50%', '70%'],
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
		                            fontSize : '20',
		                            fontWeight : 'bold'
		                        }
		                    }
		                }
		            },
		            data:datas
		        }
		    ]
		};
	var myChart = echarts.init(document.getElementById('politics_Stu'));
	myChart.setOption(option);
}