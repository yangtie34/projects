var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','scoreService','dialog',
    function(scope,scoreService,dialog){
    	dialog.showLoading();
    	scoreService.getLastScore().then(function(data){
    		scope.score = data;
    		dialog.hideLoading();
    	})
    	scoreService.getCredit().then(function(data){
    		scope.credit = data;
    	})
    	scoreService.getCreditType().then(function(data){
    		scope.creditType = data;
    	})
    	scoreService.getProportion().then(function(data){
    		scope.proportion = data.proportion
    		dialog.hideLoading();
    		var beat = data.beat;
    		var rank = data.rank;
    		var data = [];
    		data.push({name:'超越',value:beat});
    		data.push({name:'未超越',value:rank});
    		option = {
    			tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c}人 ({d}%)"
			    },
			    legend: {
			        show:false,
			        data:['超越','未超越']
			    },
			    calculable : false,
			    color:['#FF6666','#33CC99'],
			    series : [
			        {
			            name:'排名',
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
				                            fontSize : '30',
				                            fontWeight : 'bold'
				                        }
				                    }
				                }
			            },
			            data:data
			        }
			    ]
			};
			var charts = echarts.init(document.getElementById("myProportion"));
			charts.setOption(option);
    	})
    	 
}]).controller("historyscorecontroller",['$scope','scoreService','dialog',
    function(scope,scoreService,dialog){
    	dialog.showLoading();
    	scoreService.getScoreList().then(function(data){
    		scope.scores = data;
    		dialog.hideLoading();
    	})
    	scope.showScore = function(year,term){
    		var yearterm = year+"-"+term;
    		if($("#"+yearterm).is(":hidden")){
    			$("#"+year+term).removeClass("rxs-up-arw");
    			$("#"+year+term).addClass("rxs-up-arw");
    			$("#"+yearterm).show();
    		}else{
    			$("#"+year+term).removeClass("rxs-down-arw");
    			$("#"+year+term).addClass("rxs-down-arw");
    			$("#"+yearterm).hide();
    		}
    	}
    	
    	 
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "controller"
	})
	.when("/historyScore", {
		templateUrl: "tpl/historyScore.html",
		reloadOnSearch: false,
		controller : "historyscorecontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

