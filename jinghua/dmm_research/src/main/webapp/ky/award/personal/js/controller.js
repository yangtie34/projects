var app = angular.module('app', ['ngRoute','system']);
app.controller("homeController",['$scope','service','dialog','sysCodeService','toastrService',function(scope,service,dialog,codeService,toast){
	scope.username = usernameparam;
	//定义页面查询条件
	scope.queryCondition = {
		year : []
	};
	//定义查询条件选择结果
	scope.condition = {
		year : {}
	}
	//请求后台填充查询条件数据
	codeService.getYears().then(function(data){
		data[0].checked = true;
		scope.queryCondition.year = data;
		scope.condition.year = data[0].value;
		//条件数据填充完毕，开始绑定刷新事件，查询页面内容
		scope.$watch("condition",function(){
			//条件执行完毕，开始查询页面数据
			scope.queryPageInfo();
		},true);
	});
	scope.change =function(data){
		 scope.condition.year = data.value;
	 }
	//查询页面内容方法
	scope.queryPageInfo = function(){
		scope.show = {
			xmlx : false,
			xmjx : false,
			hjcg : false,
			xslw : false,
			fmzl : false,
			kyjf : false,
			cgzh : false
		};
		scope.showTable = function(awardName){
			if(awardName == '高层次项目立项奖'){
				scope.show.xmlx = true;
				scope.show.xmjx = false;
				scope.show.hjcg = false;
				scope.show.xslw = false;
				scope.show.fmzl = false;
				scope.show.kyjf = false;
				scope.show.cgzh = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '高层次项目立项奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
			if(awardName == '高层次项目结项奖'){
				scope.show.xmjx = true;
				scope.show.xmlx = false;
				scope.show.hjcg = false;
				scope.show.xslw = false;
				scope.show.fmzl = false;
				scope.show.kyjf = false;
				scope.show.cgzh = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '高层次项目结项奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
			if(awardName == '高层次研究成果奖'){
				scope.show.hjcg = true;
				scope.show.xmlx = false;
				scope.show.xmjx = false;
				scope.show.xslw = false;
				scope.show.fmzl = false;
				scope.show.kyjf = false;
				scope.show.cgzh = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '高层次研究成果奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
			if(awardName == '高层次学术论文奖'){
				scope.show.xslw = true;
				scope.show.xmlx = false;
				scope.show.xmjx = false;
				scope.show.hjcg = false;
				scope.show.fmzl = false;
				scope.show.kyjf = false;
				scope.show.cgzh = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '高层次学术论文奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
			if(awardName == '发明专利奖'){
				scope.show.fmzl = true;
				scope.show.xmlx = false;
				scope.show.xmjx = false;
				scope.show.hjcg = false;
				scope.show.xslw = false;
				scope.show.kyjf = false;
				scope.show.cgzh = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '发明专利奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
			if(awardName == '科研经费奖'){
				scope.show.kyjf = true;
				scope.show.xmlx = false;
				scope.show.xmjx = false;
				scope.show.hjcg = false;
				scope.show.xslw = false;
				scope.show.fmzl = false;
				scope.show.cgzh = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '科研经费奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
			if(awardName == '科研成果转化奖'){
				scope.show.cgzh = true;
				scope.show.xmlx = false;
				scope.show.xmjx = false;
				scope.show.hjcg = false;
				scope.show.xslw = false;
				scope.show.fmzl = false;
				scope.show.kyjf = false;
				for (var i = 0; i < scope.awards.length; i++) {
					var it = scope.awards[i];
					if(it.name == '科研成果转化奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
			}
		}
		service.getPersonalTotal(scope.username,scope.condition.year).then(function(data){
			if(data.success){
				scope.total = data.result;
			}else{
				toast.error("请求个人奖励总金额错误");
			}
		});
		service.getPersonalAward(scope.username,scope.condition.year).then(function(data){
			if(data.success){
				for (var i = 0; i < data.result.length; i++) {
					var it = data.result[i];
					if(it.name == '高层次项目立项奖'){
						it.active = true;
					}else{
						it.active = false;
					}
				}
				scope.show.xmlx = true;
				scope.awards = data.result;
			}else{
				toast.error("请求个人各奖励分布错误");
			}
		});
		scope.vm1 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm1.index",function(newval,oldval){
			scope.vm1.showModal = true;
			scope.fun1();
		});
		scope.fun1 = function(){
			service.querySetup(scope.vm1,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm1.total = data.result.total;
					scope.vm1.pagecount = data.result.pagecount;
					scope.vm1.items = data.result.rows;
				}else
					toast.error("个人高层次项目立项奖奖错误！");
				scope.vm1.showModal = false;
			});
		};
		
		scope.vm2 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm2.index",function(newval,oldval){
			scope.vm2.showModal = true;
			scope.fun2();
		});
		scope.fun2 = function(){
			service.queryEnd(scope.vm2,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm2.total = data.result.total;
					scope.vm2.pagecount = data.result.pagecount;
					scope.vm2.items = data.result.rows;
				}else
					toast.error("个人高层次项目结项奖奖错误！");
				scope.vm2.showModal = false;
			});
		};
		
		scope.vm3 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm3.index",function(newval,oldval){
			scope.vm3.showModal = true;
			scope.fun3();
		});
		scope.fun3 = function(){
			service.queryAchievement(scope.vm3,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm3.total = data.result.total;
					scope.vm3.pagecount = data.result.pagecount;
					scope.vm3.items = data.result.rows;
				}else
					toast.error("个人高层次获奖成果奖励错误！");
				scope.vm3.showModal = false;
			});
		};
		
		scope.vm4 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm4.index",function(newval,oldval){
			scope.vm4.showModal = true;
			scope.fun4();
		});
		scope.fun4 = function(){
			service.queryThesis(scope.vm4,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm4.total = data.result.total;
					scope.vm4.pagecount = data.result.pagecount;
					scope.vm4.items = data.result.rows;
				}else
					toast.error("个人高层次学术论文奖励错误！");
				scope.vm4.showModal = false;
			});
		};
		
		scope.vm5 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm5.index",function(newval,oldval){
			scope.vm5.showModal = true;
			scope.fun5();
		});
		scope.fun5 = function(){
			service.queryPatent(scope.vm5,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm5.total = data.result.total;
					scope.vm5.pagecount = data.result.pagecount;
					scope.vm5.items = data.result.rows;
				}else
					toast.error("个人发明专利奖励错误！");
				scope.vm5.showModal = false;
			});
		};
		
		scope.vm6 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm6.index",function(newval,oldval){
			scope.vm6.showModal = true;
			scope.fun6();
		});
		scope.fun6 = function(){
			service.queryAwardFund(scope.vm6,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm6.total = data.result.total;
					scope.vm6.pagecount = data.result.pagecount;
					scope.vm6.items = data.result.rows;
				}else
					toast.error("个人科研经费奖励错误！");
				scope.vm6.showModal = false;
			});
		};
		
		scope.vm7 = {
			size: 10,
			index: 1,
			items : [],
			total : 0,
			pagecount:0,
			showModal : false
		};
		scope.$watch("vm7.index",function(newval,oldval){
			scope.vm7.showModal = true;
			scope.fun7();
		});
		scope.fun7 = function(){
			service.queryTransform(scope.vm7,scope.condition.year,scope.username).then(function(data) {
				if (data.success){
					scope.vm7.total = data.result.total;
					scope.vm7.pagecount = data.result.pagecount;
					scope.vm7.items = data.result.rows;
				}else
					toast.error("个人成果转化奖励错误！");
				scope.vm7.showModal = false;
			});
		};
	}
}]);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		reloadOnSearch: false,
		controller : "homeController"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);
app.filter('paging', function() {
	  return function (items, index, pageSize) {
	    if (!items)
	      return [];
	    var offset = (index - 1) * pageSize;
	    return items.slice(offset, parseInt(offset) + parseInt(pageSize));
	 };
});

