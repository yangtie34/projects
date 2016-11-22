/**
 * 定义启动模块
 * @type {module|*}
 */
var app = angular.module('app',['custom','dorm']);
//定义一个controller
//参数分别是1 模块名 2 控制器名 3 注入对象以及控制器函数
custom.controller('app',"controller",['$scope',function(scope){
    scope.columnChart =  {
        title : "columnChartTest",
        yAxis : "件",
        isSort : false,
        data : [
            {field : '一月',name : '男' ,value : 200 },
            {field : '二月',name : '男' ,value : 100},
            {field : '三月',name : '男' ,value : 120 },
            {field : '一月',name : '女' ,value : 320 },
            {field : '二月',name : '女' ,value : 10},
            {field : '三月',name : '女' ,value : 520},
        ] ,
        type :"areaspline"  //图表类型(column,line,area,spline,areaspline)
    };

    scope.pieChart = {
        title: "pieChartTest",
        data : [{name: '实践课', y :200 },{name: '理论课', y :120 },{name: '生物', y: 300}],
        showLable: false,
        type:"pie"
    };
    scope.otherChart = {
        title: {
            text: 'Combination chart'
        },
        xAxis: {
            categories: ['Apples', 'Oranges', 'Pears', 'Bananas', 'Plums']
        },
        labels: {
            items: [{
                html: 'Total fruit consumption',
                style: {
                    left: '50px',
                    top: '18px',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                }
            }]
        },
        series: [{
            type: 'column',
            name: 'Jane',
            data: [3, 2, 1, 3, 4]
        }, {
            type: 'column',
            name: 'John',
            data: [2, 3, 5, 7, 6]
        }, {
            type: 'column',
            name: 'Joe',
            data: [4, 3, 3, 9, 0]
        }, {
            type: 'spline',
            name: 'Average',
            data: [3, 2.67, 3, 6.33, 3.33],
            marker: {
                lineWidth: 2,
                lineColor: Highcharts.getOptions().colors[3],
                fillColor: 'white'
            }
        }, {
            type: 'pie',
            name: 'Total consumption',
            data: [{
                name: 'Jane',
                y: 13,
                color: Highcharts.getOptions().colors[0] // Jane's color
            }, {
                name: 'John',
                y: 23,
                color: Highcharts.getOptions().colors[1] // John's color
            }, {
                name: 'Joe',
                y: 19,
                color: Highcharts.getOptions().colors[2] // Joe's color
            }],
            center: [100, 80],
            size: 100,
            showInLegend: false,
            dataLabels: {
                enabled: false
            }
        }]
    };
}]);