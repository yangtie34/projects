var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/index", {
		templateUrl: "tpl/index.html",
		controller : "indexController"
	})
	.when("/message", {
		templateUrl: "tpl/message.html",
		controller :  "messageController"
	})
	.when("/consume", {
		templateUrl: "tpl/consume.html",
		controller :  "consumeController"
	})
	.when("/book", {
		templateUrl: "tpl/book.html",
		controller :  "bookController"
	})
	.when("/study", {
		templateUrl: "tpl/study.html",
		controller :  "studyController"
	})
	.otherwise({
		redirectTo : "/index"
	});
}]);

app.controller("indexController",['$scope','dialog','personalService','toastrService','topicService','$timeout',function(scope,dialog,service,toastr,topic,$timeout){
	dialog.showLoading();
	service.queryPersonalInfo().then(function(data){
		dialog.hideLoading();
		if(data.success){
			scope.personalInfo = data.result;
		}else{
			toastr.error("请求失败！");
		}
	});
	dialog.showLoading();
	service.queryUnreadMessageNum().then(function(data){
		dialog.hideLoading();
		if(data.success){
			scope.unreadmessageNums = data.result;
		}else{
			scope.unreadmessageNums= 0;
			toastr.error("请求失败！");
		}
	});
	dialog.showLoading();
	service.queryMyFriendAndTopicNums().then(function(data){
		dialog.hideLoading();
		if(data.success){
			scope.myFriendAndTopicNums = data.result;
		}else{
			toastr.error("请求失败！");
		}
	});
	dialog.showLoading();
	topic.queryTopicNumOfToday().then(function(data){
		dialog.hideLoading();
		if(data.success){
			scope.todayTopicNum = data.result;
		}else{
			toastr.error("请求失败！");
		}
	});
	
	scope.vm = {
		curpage : 1,
		pagesize : 6,
		sumcount : 0,
		list : []
	}
	scope.$watch("vm.curpage",function(){
		scope.onloading = true;
		topic.queryAllOfFriend(scope.vm).then(function(data){
			scope.onloading = false;
			if(data.success){
				scope.vm.sumcount = data.result.sumcount;
				scope.vm.list = scope.vm.list.concat(data.result.result);
			}else{
				toastr.error("数据请求错误，请重试");
			}
		});
	});
	$(window).scroll(function(e) {
		var toBottom = ($(window).scrollTop() + $(window).outerHeight() + 200 > $("body").height());
		if(scope.vm.curpage * scope.vm.pagesize < scope.vm.sumcount && toBottom && !scope.onloading){
			scope.vm.curpage =  scope.vm.curpage + 1;
			scope.$apply();
		}
	});
	
	scope.openCommentForm = function(it,reply,answer){
		it.commentContent = '';
		it.showCommentForm = true;
		it.newComment.topicId = it.id;
		it.newComment.replyId = reply.id;
		if(answer == undefined){
			it.commentPlaceholder='回复'+reply.real_name;
			it.newComment.toUsername = reply.username
		}else{
			it.commentPlaceholder='回复'+answer.real_name;
			it.newComment.toUsername = answer.username
		}
		it.newComment.level = 2;
		$timeout(function(){
			$("#c_input"+ it.nm).focus();
		}, 10);
	}
	
	scope.toggleCommentForm = function(it){
		if(it.commentPlaceholder!='说点儿什么吧'){
			it.commentContent = '';
			it.commentPlaceholder='说点儿什么吧';
		}else{
			it.showCommentForm = !it.showCommentForm;
		}
		if(it.showCommentForm){
			it.newComment.topicId = it.id;
			it.newComment.level = 1;
			$timeout(function(){
				$("#c_input"+ it.nm).focus();
			}, 10);
		}
	}
	
	scope.submitComment = function(it){
		dialog.showLoading();
		if(it.newComment.level == 1){
			topic.saveReply({
				topicId : it.id,
				commentContent : it.newComment.commentContent,
				returnAll : false
			}).then(function(data){
				dialog.hideLoading();
				if(data.success){
					it.showCommentForm = false;
					it.newComment.commentContent = '';
					it.comments = data.result;
				}else{
					toastr.error("评论失败！");
				}
			});
		}else{
			topic.saveReplyAnswer({
				topicId : it.id,
				toUsername : it.newComment.toUsername,
				replyId : it.newComment.replyId,
				commentContent : it.newComment.commentContent,
				returnAll : false
			}).then(function(data){
				dialog.hideLoading();
				if(data.success){
					it.showCommentForm = false;
					it.newComment.commentContent = '';
					it.comments = data.result;
					
				}else{
					toastr.error("评论失败！");
				}
			});
		}
	};
	
}]).controller("messageController",['$scope','dialog','personalService','toastrService','friendService',function(scope,dialog,service,toastr,friend){
	dialog.showLoading();
	service.queryUnreadMessage().then(function(data){
		dialog.hideLoading();
		if(data.success){
			scope.unreadmessages = data.result;
		}else{
			scope.unreadmessageNums= 0;
			toastr.error("请求失败！");
		}
	});
	
	scope.passApply = function(it){
		friend.passApply(it.id).then(function(data){
			if(data.success){
				it.handle = true;
				it.pass = true;
			}else{
				toastr.error("请求失败！");
			}
		});
	};
	
	scope.ignoreApply = function(it){
		friend.ignoreApply(it.id).then(function(data){
			if(data.success){
				it.handle = true;
				it.pass = false;
			}else{
				toastr.error("请求失败！");
			}
		});
	};
}]).controller("consumeController",['$scope','dialog',function(scope,dialog){
	dialog.showLoading();
	
	dialog.hideLoading();
}]).controller("bookController",['$scope','dialog',function(scope,dialog){
	dialog.showLoading();
	
	dialog.hideLoading();
}]).controller("studyController",['$scope','dialog',function(scope,dialog){
	dialog.showLoading();
	
	dialog.hideLoading();
}]);