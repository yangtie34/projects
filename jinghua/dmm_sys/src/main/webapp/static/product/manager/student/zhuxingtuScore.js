/**第一个图*/
function viewStuScore(results,divId){
	var mc = [];
	var avg = [];
	var hgl = [];
	var yxl = [];
	var departments = [];
	$.each(results,function(i,o){
		mc.push(o.MC);
		avg.push(o.AVG);
		hgl.push(o.HGL);
		yxl.push(o.YXL);
		departments.push({name:o.MV,value:o.AVG});//专业名称，平均分
	});
	option = {
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['优秀率','合格率']
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            data : mc
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'优秀率',
	            type:'bar',
	            data:yxl,
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }
	        },
	        {
	            name:'合格率',
	            type:'bar',
	            data:hgl,
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }
	        }
	    ]
	};
	var myChart = echarts.init(document.getElementById(divId));
	myChart.setOption(option);
}

/** 历年趋势*/
function stuScoreQu(results,divId){
	var year = [];
	var hgl = [];
	var yxl = [];
	var mc = [];
	$.each(results,function(i,o){
		year.push(o.SCHOOL_YEAR);
		hgl.push(o.HGL);
		yxl.push(o.YXL);
		mc.push(o.MC);
	});
	option = {
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['合格率','优秀率']
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : year
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value} %'
	            }
	        }
	    ],
	    series : [
	        {
	            name:'合格率',
	            type:'line',
	            data:hgl,
	        },
	        {
	            name:'优秀率',
	            type:'line',
	            data:yxl,
	        }
	    ]
	};
	var myChart = echarts.init(document.getElementById(divId));
	myChart.setOption(option);
}

/** 成绩分布*/
function stuScoreFb(results,divId){
	var score = [];
	var numbers = [];
	$.each(results,function(i,o){
		score.push(o.SCORE);
		numbers.push(o.NUMBERS);
	});
	option = {
	    tooltip : {
	        trigger: 'axis'
	    },
	    /*legend: {
	        data:['汉语言文学']//这里可以显示对应的院系专业 名称
	    },*/
	 
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : score
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value} (人数)'
	            }
	        }
	    ],
	    series : [
	        {
	            //name:'汉语言文学',
	            type:'line',
	            data:numbers,
	        }
	    ]
	};
	var myChart = echarts.init(document.getElementById(divId));
	myChart.setOption(option);
}