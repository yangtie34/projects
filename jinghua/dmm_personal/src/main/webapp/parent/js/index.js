var app = angular.module('app', ['ngRoute']);
app.config(function($routeProvider) {
	for(var i in appMenus){
		var menu = appMenus[i];
		$routeProvider.when(menu.url, {
			templateUrl: menu.tpl,
			reloadOnSearch: false,
			controller : menu.controller || "homeController"
		});
	}
});

app.controller("controller",['$scope','$rootScope',function(scope,root){
	scope.menulist = appMenus;
}]).controller("homeController",['$scope','$rootScope',function(scope,root){
	scope.name = "haha"; 
}]).controller("testController",['SharedState','$scope','$rootScope',function(SharedState,scope,root){
	scope.name = "test";
	SharedState.turnOn("");
}]).controller("wechatController",['SharedState','$scope','userService','messageService',function(SharedState,scope,userService,messageService){
	scope.getSubscriberList = function(){
		userService.getSubscriberList().then(function(data){
			scope.subscriberList = data;
		});
	}
	scope.message="你好";
	scope.changeSelectUser = function(user){
		scope.selectUser = user;
	}
	
	scope.sendMessage = function(){
		messageService.sendMessage(scope.message,scope.selectUser.openId).then(function(data){
			SharedState.turnOn('modal1');
		});
	}
	scope.turnOnModal = function(){
		SharedState.turnOn('modal1');
	};
}])