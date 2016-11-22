 function libraryScore(boyScoreAndLibrary, girlScoreAndLibrary) {
	 var myChart = echarts.init(document.getElementById('main8'));
	 var dataBoyNums = [];
	 var dataGirlNums = [];
	 $.each(boyScoreAndLibrary, function(i,o){
		 var boyNums = Number(o.NUMBERS);
		 var boyScore = Number(o.SCORE_AVG);
		 dataBoyNums.push([boyNums, boyScore]);
	 });
	 
	 $.each(girlScoreAndLibrary, function(i,o){
		 var girlNums = Number(o.NUMBERS);
		 var girlScore = Number(o.SCORE_AVG);
		 dataGirlNums.push([girlNums, girlScore]);
	 });
	 option = {
    title : {
        text: '男生和女生成绩和图书借阅统计情况',
        //subtext: '抽样调查来自: Heinz  2003'
    },
    tooltip : {
        trigger: 'axis',
        showDelay : 0,
        formatter : function (params) {
            if (params.value.length > 1) {
                return params.seriesName + ' :<br/>'
                   + params.value[0] + '本 ' 
                   + params.value[1] + '分 ';
            }
            else {
                return params.seriesName + ' :<br/>'
                   + params.name + ' : '
                   + params.value + 'kg ';
            }
        },  
        axisPointer:{
            show: true,
            type : 'cross',
            lineStyle: {
                type : 'dashed',
                width : 1
            }
        }
    },
    legend: {
        data:['女生','男生']
    },
    /*toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataZoom : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },*/
    xAxis : [
        {
            type : 'value',
            scale:true,
            axisLabel : {
                formatter: '{value} 本'
            }
        }
    ],
    yAxis : [
        {
            type : 'value',
            scale:true,
            axisLabel : {
                formatter: '{value} 分'
            }
        }
    ],
    series : [
        {
            name:'女生',
            type:'scatter',
            data: dataGirlNums,
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'男生',
            type:'scatter',
            data: dataBoyNums,
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        }
    ]
};
myChart.setOption(option);
 }