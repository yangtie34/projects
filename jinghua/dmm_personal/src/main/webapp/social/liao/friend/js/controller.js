var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/index", {
		templateUrl: "tpl/index.html",
		controller : "indexController"
	})
	.when("/add", {
		templateUrl: "tpl/add.html",
		controller : "addController"
	})
	.otherwise({
		redirectTo : "/index"
	});
}]);

app.controller("indexController",['$scope','dialog','toastrService','friendService',function(scope,dialog,toastrs,service){
	scope.searchText = "";
	scope.queryFriendList = function(){
		scope.onLoading = true;
		dialog.showLoading();
		service.queryMyFriendList().then(function(data){
			dialog.hideLoading();
			scope.onLoading = false;
			if(data.success){
				scope.friendList = data.result;
			}else{
				toastr.error("数据请求失败！");
			}
		});
	}
	scope.deleteFriendShip = function(it){
		dialog.confirm("确定删除？删除后你将无法看到该好友的个人信息。",function(){
			service.delFriendShip(it.id).then(function(data){
				if(data.success){
					scope.queryFriendList();
					toastr.success("删除成功！");
				}else{
					toastr.error("删除好友失败！");
				}
			});
		});
	}
	scope.queryFriendList();
	
}]).controller("addController",['$scope','toastrService','friendService',function(scope,toastr,service){
	//绑定搜索文字
	scope.searchText = "";
	//根据文字搜索
	scope.queryUserList = function(){
		scope.vm = {
			curpage : 1,
			pagesize : 20,
			sumcount : 0,
			list : [],
			searchText : scope.searchText
		}
		scope.searchUserList();
	}
	scope.$watch("vm.curpage",function(newval , oldval){
		if(newval != undefined && newval != 1){
			scope.searchUserList();
		}
	},true);
	scope.searchUserList = function(){
		scope.onloading = true;
		service.searchUserList(scope.vm).then(function(data){
			scope.onloading = false;
			if(data.success){
				scope.vm.sumcount = data.result.sumcount;
				scope.vm.list = scope.vm.list.concat(data.result.result);
			}else{
				toastr.error("查询出错，请重试");
			}
		});
	}
	
	//页面滚动到底部加载下一页
	$(window).scroll(function(e) {
		var toBottom = ($(window).scrollTop() + $(window).outerHeight() + 200 > $("body").height());
		if(scope.vm.curpage * scope.vm.pagesize < scope.vm.sumcount && toBottom && !scope.onloading){
			scope.vm.curpage =  scope.vm.curpage + 1;
			scope.$apply();
		}
	});
	
	//通过用户好友申请
	scope.passApply = function(it){
		service.passApply(it.apply_id).then(function(data){
			if(data.success){
				it.is_apply = 2;
				it.pass = true;
			}else{
				toastr.error("操作失败！");
			}
		});
	}
	
	//忽略用户好友申请
	scope.ignoreApply = function(it){
		service.ignoreApply(it.apply_id).then(function(data){
			if(data.success){
				it.is_apply = 2;
				it.ignore = true;
			}else{
				toastr.error("操作失败！");
			}
		});
	}
	
	//发送好友申请
	scope.sendFriendApply = function(it){
		service.sendFriendApply(it.username).then(function(data){
			if(data.success){
				it.is_apply = 1;
				it.apply_target = it.username;
			}else{
				toastr.error("请求失败！");
			}
		});
	}
}]);