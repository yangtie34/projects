/**
 * 定义启动模块
 * @type {module|*}
 */
var app = angular.module('app',['system']);
//定义一个controller
app.controller("controller",['$scope',function(scope){
	var data = [
	            {field : '一月',name : '男' ,value : 200 },
	            {field : '二月',name : '男' ,value : 1400},
	            {field : '三月',name : '男' ,value : 120 },
	            {field : '四月',name : '男' ,value : 1230 },
	            {field : '五月',name : '男' ,value : 120 },
	            {field : '一月',name : '女' ,value : 320 },
	            {field : '二月',name : '女' ,value : 10},
	            {field : '三月',name : '女' ,value : 5220},
	            {field : '四月',name : '女' ,value : 5320},
	            {field : '五月',name : '女' ,value : 5230},
	            {field : '一月',name : '变态' ,value : 30 },
	            {field : '二月',name : '变态' ,value : 100},
	            {field : '三月',name : '变态' ,value : 230},
	            {field : '四月',name : '变态' ,value : 690},
	            {field : '五月',name : '变态' ,value : 510},
	        ] ;
    scope.columnChart =  {
        title : "柱状图",
        isSort : false,
        yAxis : "人",
        data : data,
        type :"bar"  //图表类型(bar,line,area,spline)
    };
    scope.columnChart1 =  {
    		title : "折线图",
    		isSort : false,
    		data : data,
    		yAxis : "人",
    		type :"line"  //图表类型(bar,line,area,spline)
    };
    scope.columnChart2 =  {
    		title : "面积图",
    		isSort : false,
    		yAxis : "人",
    		data : data,
    		type :"area"  //图表类型(bar,line,area,spline)
    };
    scope.columnChart3 =  {
    		title : "曲线图",
    		isSort : false,
    		yAxis : "人",
    		data : data,
    		type :"spline"  //图表类型(bar,line,area,spline)
    };
    scope.columnChart4 =  {
    		title : "曲线面积图",
    		isSort : false,
    		yAxis : "人",
    		data : data,
    		type :"areaspline"  //图表类型(bar,line,area,spline,areaspline)
    };

    scope.pieChart = {
        title: "饼状图示例",
        data : [{name: '实践课', value :200 },{name: '理论课', value :120 },{name: '生物', value: 300}],
        showLable: false,
        type:"pie"
    };
    
    
    scope.otherChart =  {
    	    title : {
    	        text: 'iphone销量',
    	        subtext: '纯属虚构',
    	        left: 'center'
    	    },
    	    tooltip : {
    	        trigger: 'item'
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data:['iphone3','iphone4','iphone5']
    	    },
    	    visualMap: {
    	        min: 0,
    	        max: 2500,
    	        left: 'left',
    	        top: 'bottom',
    	        text:['高','低'],           // 文本，默认为数值文本
    	        calculable : true
    	    },
    	    toolbox: {
    	        show: true,
    	        orient : 'vertical',
    	        left: 'right',
    	        top: 'center',
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    series : [
    	        {
    	            name: 'iphone3',
    	            type: 'map',
    	            mapType: 'china',
    	            roam: false,
    	            label: {
    	                normal: {
    	                    show: false
    	                },
    	                emphasis: {
    	                    show: true
    	                }
    	            },
    	            data:[
    	                {name: '北京',value: Math.round(Math.random()*1000)},
    	                {name: '天津',value: Math.round(Math.random()*1000)},
    	                {name: '上海',value: Math.round(Math.random()*1000)},
    	                {name: '重庆',value: Math.round(Math.random()*1000)},
    	                {name: '河北',value: Math.round(Math.random()*1000)},
    	                {name: '河南',value: Math.round(Math.random()*1000)},
    	                {name: '云南',value: Math.round(Math.random()*1000)},
    	                {name: '辽宁',value: Math.round(Math.random()*1000)},
    	                {name: '黑龙江',value: Math.round(Math.random()*1000)},
    	                {name: '湖南',value: Math.round(Math.random()*1000)},
    	                {name: '安徽',value: Math.round(Math.random()*1000)},
    	                {name: '山东',value: Math.round(Math.random()*1000)},
    	                {name: '新疆',value: Math.round(Math.random()*1000)},
    	                {name: '江苏',value: Math.round(Math.random()*1000)},
    	                {name: '浙江',value: Math.round(Math.random()*1000)},
    	                {name: '江西',value: Math.round(Math.random()*1000)},
    	                {name: '湖北',value: Math.round(Math.random()*1000)},
    	                {name: '广西',value: Math.round(Math.random()*1000)},
    	                {name: '甘肃',value: Math.round(Math.random()*1000)},
    	                {name: '山西',value: Math.round(Math.random()*1000)},
    	                {name: '内蒙古',value: Math.round(Math.random()*1000)},
    	                {name: '陕西',value: Math.round(Math.random()*1000)},
    	                {name: '吉林',value: Math.round(Math.random()*1000)},
    	                {name: '福建',value: Math.round(Math.random()*1000)},
    	                {name: '贵州',value: Math.round(Math.random()*1000)},
    	                {name: '广东',value: Math.round(Math.random()*1000)},
    	                {name: '青海',value: Math.round(Math.random()*1000)},
    	                {name: '西藏',value: Math.round(Math.random()*1000)},
    	                {name: '四川',value: Math.round(Math.random()*1000)},
    	                {name: '宁夏',value: Math.round(Math.random()*1000)},
    	                {name: '海南',value: Math.round(Math.random()*1000)},
    	                {name: '台湾',value: Math.round(Math.random()*1000)},
    	                {name: '香港',value: Math.round(Math.random()*1000)},
    	                {name: '澳门',value: Math.round(Math.random()*1000)}
    	            ]
    	        },
    	        {
    	            name: 'iphone4',
    	            type: 'map',
    	            mapType: 'china',
    	            label: {
    	                normal: {
    	                    show: false
    	                },
    	                emphasis: {
    	                    show: true
    	                }
    	            },
    	            data:[
    	                {name: '北京',value: Math.round(Math.random()*1000)},
    	                {name: '天津',value: Math.round(Math.random()*1000)},
    	                {name: '上海',value: Math.round(Math.random()*1000)},
    	                {name: '重庆',value: Math.round(Math.random()*1000)},
    	                {name: '河北',value: Math.round(Math.random()*1000)},
    	                {name: '安徽',value: Math.round(Math.random()*1000)},
    	                {name: '新疆',value: Math.round(Math.random()*1000)},
    	                {name: '浙江',value: Math.round(Math.random()*1000)},
    	                {name: '江西',value: Math.round(Math.random()*1000)},
    	                {name: '山西',value: Math.round(Math.random()*1000)},
    	                {name: '内蒙古',value: Math.round(Math.random()*1000)},
    	                {name: '吉林',value: Math.round(Math.random()*1000)},
    	                {name: '福建',value: Math.round(Math.random()*1000)},
    	                {name: '广东',value: Math.round(Math.random()*1000)},
    	                {name: '西藏',value: Math.round(Math.random()*1000)},
    	                {name: '四川',value: Math.round(Math.random()*1000)},
    	                {name: '宁夏',value: Math.round(Math.random()*1000)},
    	                {name: '香港',value: Math.round(Math.random()*1000)},
    	                {name: '澳门',value: Math.round(Math.random()*1000)}
    	            ]
    	        },
    	        {
    	            name: 'iphone5',
    	            type: 'map',
    	            mapType: 'china',
    	            label: {
    	                normal: {
    	                    show: false
    	                },
    	                emphasis: {
    	                    show: true
    	                }
    	            },
    	            data:[
    	                {name: '北京',value: Math.round(Math.random()*1000)},
    	                {name: '天津',value: Math.round(Math.random()*1000)},
    	                {name: '上海',value: Math.round(Math.random()*1000)},
    	                {name: '广东',value: Math.round(Math.random()*1000)},
    	                {name: '台湾',value: Math.round(Math.random()*1000)},
    	                {name: '香港',value: Math.round(Math.random()*1000)},
    	                {name: '澳门',value: Math.round(Math.random()*1000)}
    	            ]
    	        }
    	    ]
    	};
}]);