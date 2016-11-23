function stuCompareMajorClass(stuScores, majorList, classList) {
	var scoresName = [];
	var myselfScores = [];
	var majorScores = [];
	var classScores = [];
	$.each(stuScores, function(i,o){
		scoresName.push(o.COURSENAME);
		myselfScores.push(o.CENSCORE);
	});
	$.each(majorList, function(i,o){
		majorScores.push(o.avgScore);
	});
	$.each(classList, function(i,o){
		classScores.push(o.avgScore);
	});
	var myChart = echarts.init(document.getElementById('compare_class_major_score'));
	option = {
	    /*title : {
	        text: '某地区蒸发量和降水量',
	        subtext: '纯属虚构'
	    },*/
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['自己','班级平均分','专业平均分']
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['bar','line']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            data : scoresName
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'自己',
	            type:'bar',
	            data:myselfScores,
	            itemStyle: { //柱状图样式设置
					normal: {						
						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
					}
				},
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均分'}
	                ]
	            }
	        },
	        {
	            name:'班级平均分',
	            type:'bar',
	            data:classScores,
	            itemStyle: { //柱状图样式设置
					normal: {						
						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
					}
				},
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            }
	        },
			{
				name:'专业平均分',
	            type:'bar',
	            data:majorScores,
	            itemStyle: { //柱状图样式设置
					normal: {						
						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
					}
				},
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            }
			}
	    ]
	};                    
	// 为echarts对象加载数据 
	myChart.setOption(option);
	
};