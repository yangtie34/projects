//柱状图，折线图 2
function showMap3(results) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('main3'));
	var depts = [];
	var edus = [];
	depts = results.dataList;
	edus = results.eduList;
	/*var totalNums = results.totalNum;
	var depts=[{dept_id:123,dept_name:'文学院',12:30,23:23},{dept_id:123,dept_name:'文学院',12:30,23:23}];
	var edus=[{edu_id:12,edu_name:'大专'},{edu_id:22,edu_name:'本科'}];*/
	var legends = [];
	var deptNames=[];
	
	$.each(depts,function(i,o){
		deptNames.push(o.DEPT_NAME);
	});
	
	
	$.each(edus,function(i,o){
		o.data=[];
		o.name=o.EDUNAME;
		legends.push(o.name);
		o.type='bar';
		o.itemStyle= { //柱状图样式设置
					normal: {						
						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
					}
				};
		$.each(depts,function(i,dept){
			if(dept[o.EDU_ID]){
				o.data.push(Number(dept[o.EDU_ID]));
			}else{
				o.data.push(0);
			}
			
		});
		delete o.EDU_ID;
		delete o.COUNTS;
		delete o.EDUNAME;
	});

	option = {
	    title : {
	       // text: '静态本，专，研人数',
	        //subtext: '纯属虚构'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:legends,x:'left'
	    },
	   /* toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['bar','line']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },*/
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            data : deptNames
//	            data : ['文学院','化学工学院','体育学院','教育科学学院','外国语学院','计算机科学与技术学院','美术与设计学院','政法学院','软件学院','生命科学与农学学院','音乐舞蹈学院','新闻与传媒学院']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : edus
//	    series : [
//	        {
//	            name:'研究生',
//	            type:'bar',
//	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
//	            itemStyle: { //柱状图样式设置
//					normal: {						
//						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
//					}
//				},
//	            markPoint : {
//	                data : [
//	                    {type : 'max', name: '最大值'},
//	                    {type : 'min', name: '最小值'}
//	                ]
//	            },
//	            markLine : {
//	                data : [
//	                    {type : 'average', name: '平均值'}
//	                ]
//	            }
//	        },
//	        {
//	            name:'本科生',
//	            type:'bar',
//	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
//	            itemStyle: { //柱状图样式设置
//					normal: {						
//						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
//					}
//				},
//	            markPoint : {
//	                data : [
//	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
//	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
//	                ]
//	            },
//	            markLine : {
//	                data : [
//	                    {type : 'average', name : '平均值'}
//	                ]
//	            }
//	        },
//			{
//				name:'专科生',
//	            type:'bar',
//	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
//	            itemStyle: { //柱状图样式设置
//					normal: {						
//						barBorderRadius:[5, 5, 5, 5] //（顺时针左上，右上，右下，左下）柱形边框圆角
//					}
//				},
//	            markPoint : {
//	                data : [
//	                    {type : 'max', name: '最大值'},
//	                    {type : 'min', name: '最小值'}
//	                ]
//	            },
//	            markLine : {
//	                data : [
//	                    {type : 'average', name: '平均值'}
//	                ]
//	            }
//			}
//	    ]
	};                    
	// 为echarts对象加载数据 
	myChart.setOption(option);
}
