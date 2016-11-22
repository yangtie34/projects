function stuRanks(stuScores, classresultList, majorresultList) {
		var courseNames = [];
		var classRank = [];
		var majorRank = [];
		$.each(stuScores, function(i,o){
			courseNames.push(o.COURSENAME);
		});
		$.each(classresultList, function(i,o){
			classRank.push(o.classRanks);
		});
		$.each(majorresultList, function(i,o){
			majorRank.push(o.majorRanks);
		});
		var myChart = echarts.init(document.getElementById('stu_class_major_ranks'));
		option = {
		    /*title : {
		        text: '某地区蒸发量和降水量',
		        subtext: '纯属虚构'
		    },*/
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['班级中排名','专业中排名']
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
		            data : courseNames
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'班级中排名',
		            type:'bar',
		            data:classRank,
		            itemStyle: { //柱状图样式设置
						normal: {						
							barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
						}
					},
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最高排名'},
		                    {type : 'min', name: '最低排名'}
		                ]
		            },
		           /* markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }*/
		        },
		        {
		            name:'专业中排名',
		            type:'bar',
		            data:majorRank,
		            itemStyle: { //柱状图样式设置
						normal: {						
							barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
						}
					},
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最高排名'},
		                    {type : 'min', name: '最低排名'}
		                ]
		            },
		           /* markLine : {
		                data : [
		                    {type : 'average', name : '平均值'}
		                ]
		            }*/
		        }
		    ]
		};                    
		myChart.setOption(option);
}