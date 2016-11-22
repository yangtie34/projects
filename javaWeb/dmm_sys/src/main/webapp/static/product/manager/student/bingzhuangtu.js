function viewBzt(results){
	//人员学科组成
	var menuNames = [];
	var datas = [];
	$.each(results,function(i,o){
		if((o.SUBJECTNAME=="" || o.SUBJECTNAME == null) || (o.COUNTS=="" || o.COUNTS == null)){
			o.SUBJECTNAME = "0";
			o.COUNTS = 0;
		}
		menuNames.push(o.SUBJECTNAME);
		datas.push({name:o.SUBJECTNAME,value:o.COUNTS});
	});
	option = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:menuNames
	    },
	    calculable : true,
	    series : [
	        {
	            /*name:edus,*/
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
	var myChart = echarts.init(document.getElementById('bzt_Stu'));
	myChart.setOption(option);
}