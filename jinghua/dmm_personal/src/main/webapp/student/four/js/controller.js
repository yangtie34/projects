var app = angular.module('app', ['ngRoute','system']);

app.controller("controller",['$scope','fourService','dialog',function(scope,service,dialog){
	var swiper = new Swiper('.swiper-container', {
	    mousewheelControl:true,
        pagination: '.swiper-pagination',
        paginationType: 'progress',
        paginationClickable: true,
        direction: 'vertical',
		onInit: function(swiper){ //Swiper2.x的初始化是onFirstInit
			swiperAnimateCache(swiper); //隐藏动画元素 
			 
		}, 
		onSlideChangeEnd: function(swiper){ 
			 //每个slide切换结束时也运行当前slide动画
		} 
    });
    $('#dyc').click(function(){
    	swiper.slideTo(1, 1000, false);//切换到第一个slide，速度为1秒
    });
    $('#rm').click(function(){
    	swiper.slideTo(14, 1000, false);//切换到第一个slide，速度为1秒
    });
    $('#jc').click(function(){
    	swiper.slideTo(16, 1000, false);//切换到第一个slide，速度为1秒
    });
    $('#tsg').click(function(){
    	swiper.slideTo(9, 1000, false);//切换到第一个slide，速度为1秒
    });
    $('#cj').click(function(){
    	swiper.slideTo(11, 1000, false);//切换到第一个slide，速度为1秒
    });
    $('#xf').click(function(){
    	swiper.slideTo(5, 1000, false);//切换到第一个slide，速度为1秒
    });
    $('#bqq').click(function(){
    	swiper.slideTo(swiper.slides.length-1, 1000, false);//切换到第一个slide，速度为1秒
    })
	dialog.showLoading();
	service.getFdys().then(function(data){
		scope.fdys = data;
	});
	service.getMySchool().then(function(data){
		scope.school = data;
	});
	//生成学年学期列表
	var dt = new Date();
	var curMonth = dt.getMonth();
	var curYear = 0;
	if(curMonth < 8){
		curYear = dt.getFullYear();
	}else{
		curYear = dt.getFullYear() + 1;
	}
	var xnxq = [];
	for (var i = 0; i < 5; i++) {
		for (var j = 2; j >= 1; j--) {
			var it  = {
				xn : (curYear-1-i) +"-"+ (curYear-i),
				xq : '0'+j
			};
			xnxq.push(it);
		}
	}
	//学期下拉框
	scope.source = [];
	for(var i=0;i<xnxq.length;i++){
		var it = {
			id:xnxq[i],
			mc:xnxq[i].xn+"学年"+xnxq[i].xq+"学期",
			checked:false
		}
		if(curMonth < 8){
			if(i==0) it.checked = true;
		}else{
			if(i==1) it.checked = true;
		}
		scope.source.push(it);
	}
	service.queryTea(xnxq[0]).then(function(data){
		scope.teas = data;
	});
	scope.change =function(data){
		scope.result = data;
		service.queryTea(data.id).then(function(data){
			 scope.teas = data;
		});
	 }
	 
	 //奖惩
	 service.getAward().then(function(data){
		scope.award = data;
	 });
	 service.getPunish().then(function(data){
		scope.punish = data;
	 });
	 
	 //成绩
	 service.getScoreMap().then(function(data){
		scope.score = data;
	 });
	 service.getGoodScore().then(function(data){
		scope.goodScore = data;
	 });
	 service.getScoreCourse().then(function(data){
		scope.course = data;
	 });
	 service.getScoreChart().then(function(data){
	 	var year = [];
	 	var my = [];
	 	var avg = [];
	 	for(var i=0;i<data.length;i++){
	 		year.push(data[i].school_year+"学年"+data[i].term_code+"学期");
	 		my.push(data[i].my_avg);
	 		avg.push(data[i].major_avg);
	 	}
	 	scope.scoreChart = {
	        tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['我的平均成绩','本专业平均成绩']
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
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'我的平均',
		            type:'line',
		            data: my
		        },
		        {
		            name:'本届人均',
		            type:'line',
		            data: avg
		        }
		    ]
	    };
	 });
	 
	 //第一次
	 service.getFirst().then(function(data){
		scope.first = data;
	 });
	 
	 //我的借阅
	 service.queryBorrowBooks().then(function(data){
		scope.borrowBooks = data;
	});
	 //我的借阅/本届人均
	service.querymyBorrowBooks().then(function(data){
		var year = [];
		var my = [];
		var avg = [];
		for (var i = 0; i < data.length; i++) {
			year.push( data[i].xn+"学年"+data[i].xq+"学期");
			my.push( data[i].my);
			avg.push( data[i].pj);
		}
		scope.myBorrow = {
		        tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['我的借阅','本届人均']
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
			                formatter: '{value}'
			            }
			        }
			    ],
			    series : [
			        {
			            name:'我的借阅',
			            type:'line',
			            data: my
			        },
			        {
			            name:'本届人均',
			            type:'line',
			            data: avg
			        }
			    ]
		    };
	 });
	 //我的进出图书馆
	 service.queryInOutLibr().then(function(data){
		 scope.inOutLibr = data;
	 });
	 //我的进出图书馆/本届人均
	 service.queryMyLibrs().then(function(data){
		 var year = [];
		 var my = [];
		 var pj = [];
		 for (var i = 0; i < data.length; i++) {
			 year.push( data[i].xn+"学年"+data[i].xq+"学期");
			 my.push( data[i].my);
			 pj.push( data[i].pj);
		}
		 scope.myLibrs = {
			        tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['我的进出','本届人均']
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
				                formatter: '{value}'
				            }
				        }
				    ],
				    series : [
				        {
				            name:'我的进出',
				            type:'line',
				            data: my
				        },
				        {
				            name:'本届人均',
				            type:'line',
				            data: pj
				        }
				    ]
			    };
	 });
	
	//标签墙
	service.getWall().then(function(data){
		scope.wall = data;		
	})
	 //消费
	 service.getCardMap().then(function(data){
		scope.card = data;
	 });
	 service.getCardWin().then(function(data){
	 	scope.cardWins = data;
	 });
	 service.getCardMeal().then(function(data){
	 	scope.meals = data;
	 });
	 service.getCardMealPayAvg().then(function(data){
	 	var myCard = [data.my_breakfast,data.my_lunch,data.my_dinner];
	 	var meal = [data.breakfast_avg,data.lunch_avg,data.dinner_avg];
	 	var yData = [{name:'我的平均消费',value:myCard},{name:'同届同性别平均消费',value:meal}]
	 	scope.cardPieChart = {
		    tooltip : {
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'right',
		        y : 'top',
		        data:['我的平均消费','同届同性别平均消费']
		    },
		    polar : [
		       {
		           indicator : [
		               { text: '早餐', max: 10},
		               { text: '午餐', max: 10},
		               { text: '晚餐', max: 10}
		            ]
		        }
		    ],
		    calculable : true,
		    series : [
		        {
		            name: '我的平均消费 vs 同届同性别平均消费',
		            type: 'radar',
		            data : yData
		        }
		    ]
	 	}
	 });
	 service.getShopCard().then(function(data){
	 	scope.shopCard = data;
	 });
	 service.getWashCard().then(function(data){
	 	scope.washCard = data;
	 });
	 service.getCardChart().then(function(data){
	 	var year = [];
	 	var my = [];
	 	var avg = [];
	 	for(var i=0;i<data.length;i++){
	 		year.push(data[i].school_year+"学年"+data[i].term_code+"学期");
	 		my.push(data[i].sums);
	 		avg.push(data[i].avg_);
	 	}
	 	scope.cardChart = {
	        tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['我的消费','本届人均']
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
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'我的消费',
		            type:'line',
		            data: my
		        },
		        {
		            name:'本届人均',
		            type:'line',
		            data: avg
		        }
		    ]
	    };
	 });
	 service.getCardPie().then(function(data){
	 	var ydata = [];
	 	var eat_pay = {name:'超市',value:data.shopPay};
	 	var shop_pay = {name:'餐厅',value:data.eatPay};
	 	ydata.push(eat_pay);
	 	ydata.push(shop_pay);
	 	scope.cardPie = {
	        tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['超市','餐厅']
		    },  
		    series : [
		        {
		            name:'消费类型',
		            type:'pie',
		            radius : ['50%', '70%'],
		            label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
		            data:ydata
		        }
		    ]
	    };
	});
	
	
	dialog.hideLoading();
}]);



