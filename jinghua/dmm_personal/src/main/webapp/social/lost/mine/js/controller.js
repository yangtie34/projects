var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/index", {
		templateUrl: "tpl/index.html",
		controller : "indexController"
	}).when("/newPost",{
		templateUrl: "tpl/newPost.html",
		controller : "newPostController"
	}).otherwise({
		redirectTo : "/index"
	});
}]);

app.controller("indexController",['$scope','dialog','mineService','toastrService','locationService',function(scope,dialog,service,toastr,location){
	dialog.showLoading();
	service.queryMyTopic().then(function(data){
		scope.myTopicList = data.result;
	});
	scope.changeSolveState = function(it){
		service.modifyStatus(it.id).then(function(data){
			it.is_solve = 1;
		})		
	};
	
	scope.deleteTopic = function(it){
		dialog.confirm("确定删除此篇文章?",function(){
			service.deleteTopic(it.id).then(function(data){
				dialog.hideLoading();
				if(data.success){
					toastr.success("删除成功");
					location.reload();
				}else{
					toastr.error("删除失败");
				}
			})
		});
	};
	dialog.hideLoading();
}])
.controller("newPostController",['$scope','dialog','mineService','toastrService','locationService','$routeParams',
                                 function(scope,dialog,service,toastr,location,params){
	scope.newTopic = {
		imageUrl : "",
		tel : "",
		message : "",
		lostfoundTypeCode : 0
	};
	
	
	//图片上传
	scope.selectImage=function(){
		$("#hideImageSelectButton").click();
	}
	
	$('#uploadImageForm').ajaxForm({
		dataType:'json',
		beforSend:function(){
			scope.$apply();
		},success:function(data){
			var err_code = data.error;
			if(err_code=='1'){
				toastr.error("图片上传失败"+data.message);
			}else{
				scope.newTopic.imageUrl = base+data.url;
			}
			$('#uploadImageForm').clearForm();
			scope.$apply();
		}
	});
	//提交  绑定
	scope.submit = function(){
		dialog.showLoading();
		service.saveTopic(scope.newTopic).then(function(data){
			dialog.hideLoading();
			if(data.success){
				location.redirect(base + "social/lost/mine/index.jsp#/index" );
			}else{
				toastr.error("保存失败！");
			}
		});
	}
	
}]);

