var app = angular.module('app', ['system'])
.controller("controller",['$scope','wechatMsgService','dialog','toastrService',function(scope,msg,dialog,toastr){
	//	查询菜单 
	scope.sendMassText = function(){
		dialog.showLoading();
		msg.sendMassText(scope.massText).then(function(data){
			if(data.success){
				toastr.success("发送成功");
			}else{
				toastr.error("发送失败");
			}
			dialog.hideLoading();
		})
	}
	
	
	msg.getUserInfo().then(function(data){
		scope.userList = data;
	})
	
	/*	msg.getUserInfoByUsername().then(function(data){
		console.log(data)
	})*/
	
	scope.userSelect={
		openid : ""
	};
}]);