var app = angular.module('app', ['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/index", {
		templateUrl: "tpl/index.html",
		controller : "indexController"
	})
	.when("/edit/:backpage", {
		templateUrl: "tpl/edit.html",
		controller :  "editController"
	})
	.when("/mytopic", {
		templateUrl: "tpl/mytopic.html",
		controller :  "mytopicController"
	})
	.when("/topic/:id", {
		templateUrl: "tpl/topic.html",
		controller :  "topicController"
	})
	.otherwise({
		redirectTo : "/index"
	});
}]);

app.controller("indexController",['$scope','dialog','toastrService','topicService',function(scope,dialog,toastr,service){
	scope.vm = {
		curpage : 1,
		pagesize : 10,
		sumcount : 0,
		list : []
	}
	scope.$watch("vm.curpage",function(){
		scope.onloading = true;
		service.queryAllTopicList(scope.vm).then(function(data){
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
}])

.controller("editController",['$scope','dialog','$routeParams','toastrService','locationService','topicService',function(scope,dialog,params,toastr,location,service){
	//初始化qq表情插件
	var qqfaceBase = base + "static/jQuery-qqFace/img/"
	$('.emotion').qqFace({
		id : 'topic_content_id', 
		assign:'topic_content', 
		path:qqfaceBase	//表情存放的路径
	});
	
	//选择图片上传
	scope.selectImage = function(){
		$("#hideImageSelectButton").click();
	}
	
	$('#uploadImageForm').ajaxForm({
		dataType : 'json',
		beforeSend : function(){
			dialog.showLoading()
		},
        success:function(data){
        	dialog.hideLoading();
     		var err_code = data.error;
     		if(err_code == '1'){
     			toastr.error("图片上传失败，"+data.message)
     		}else{
	        	scope.uploadImageList.push(base + data.url);
	        	scope.$apply();
     		}
     		$('#uploadImageForm').clearForm();
        }
    });
	//上传图片集合
	scope.uploadImageList = [];
	scope.commit = function(){
		var str = $("#topic_content").val();
		if(str == ""){
			toastr.clear();
			toastr.warning("发表内容不能为空！");
			return;
		}
		scope.content = str.replace(/\[em_([0-9]*)\]/g,'<img src="'+qqfaceBase+'$1.gif" border="0" />');
		dialog.showLoading();
		service.saveTopic({
			content : str.replace(/\[em_([0-9]*)\]/g,'<img src="'+qqfaceBase+'$1.gif" border="0" />'),
			images : angular.toJson(scope.uploadImageList)
		}).then(function(data){
			dialog.hideLoading();
			if(data.success){
				location.redirect(base + "social/liao/bar/index.jsp#/" + params.backpage);
			}else{
				toastr.error(data.message);
			}
		});
	}
}])

.controller("mytopicController",['$scope','dialog','topicService','locationService',function(scope,dialog,service,location){
	scope.vm = {
		curpage : 1,
		pagesize : 10,
		sumcount : 0,
		list : []
	};
	
	scope.$watch("vm.curpage",function(){
		scope.onloading = true;
		service.queryMyTopicList(scope.vm).then(function(data){
			scope.onloading = false;
			scope.vm.sumcount = data.result.sumcount;
			scope.vm.list = scope.vm.list.concat(data.result.result);
		});
	});
	
	$(window).scroll(function(e) {
		var toBottom = ($(window).scrollTop() + $(window).outerHeight() + 200 > $("body").height());
		if(scope.vm.curpage * scope.vm.pagesize < scope.vm.sumcount && toBottom && !scope.onloading){
			scope.vm.curpage =  scope.vm.curpage + 1;
			scope.$apply();
		}
	});

	scope.deleteTopic = function(it){
		dialog.confirm("确定删除?",function(){
			dialog.showLoading();
			service.deleteTopic(it.id).then(function(data){
				dialog.hideLoading()
				if(data.success){
					location.reload();
					toastr.success("删除成功");
				}else{
					toastr.error("删除失败！");
				}
			});
		});
	}
	
}])

.controller("topicController",['$scope','dialog','$routeParams','topicService','locationService','toastrService','$timeout',function(scope,dialog,params,service,location,toastr,$timeout){
	dialog.showLoading();
	scope.onLoading = true;
	service.queryTopicDetail(params.id).then(function(data){
		dialog.hideLoading();
		scope.onLoading = false;
		if(data.success){
			scope.topic = data.result;
			scope.topic.newComment = {commentContent:''};
			scope.topic.commentPlaceholder='说点儿什么吧';
			scope.topic.showCommentForm = false;
		}else{
			toastr.error("数据请求出错");
		}
	});
	
	scope.deleteTopic = function(){
		dialog.confirm("确定删除?",function(){
			dialog.showLoading();
			service.deleteTopic(params.id).then(function(data){
				dialog.hideLoading()
				if(data.success){
					scope.topic = null;
					toastr.success("删除成功");
					location.redirect(base + "social/liao/bar/index.jsp#/index");
				}else{
					toastr.error("删除失败！");
				}
			});
		});
	}
	
	
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
			$("#c_input").focus();
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
				$("#c_input").focus();
			}, 10);
		}
	}
	
	scope.submitComment = function(it){
		dialog.showLoading();
		if(it.newComment.level == 1){
			service.saveReply({
				topicId : it.id,
				commentContent : it.newComment.commentContent,
				returnAll : true
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
			service.saveReplyAnswer({
				topicId : it.id,
				toUsername : it.newComment.toUsername,
				replyId : it.newComment.replyId,
				commentContent : it.newComment.commentContent,
				returnAll : true
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
	
}]);