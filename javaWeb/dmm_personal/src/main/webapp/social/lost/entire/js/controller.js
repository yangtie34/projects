var app = angular.module("app", ['system']);
app.controller("controller",['$scope','dialog','allTopicService','toastrService',function(scope,dialog,service,toastr){

	scope.changeTypeCode = function(type){
		if(type == null){
			$("#sw").removeClass("rxs-ll-active");
			$("#xw").removeClass("rxs-ll-active");
			$("#all").addClass("rxs-ll-active");
		}
		if(type == '1'){
			$("#sw").removeClass("rxs-ll-active");
			$("#all").removeClass("rxs-ll-active");
			$("#xw").addClass("rxs-ll-active");
		}
		if(type == '0'){
			$("#all").removeClass("rxs-ll-active");
			$("#xw").removeClass("rxs-ll-active");
			$("#sw").addClass("rxs-ll-active");
		}
		scope.vm.typeCode = type;
		scope.initQuery();
	};
	
	scope.vm = {
		curpage : 1,
		pagesize : 10,
		sumcount : 0,
		
		keyword : "",
		typeCode : null,
		
		list : []
	};
	//根据文字搜索
	scope.initQuery = function(){
		scope.vm.list = [];
		if(scope.vm.curpage == 1){
			scope.searchTopic();
		}else{
			scope.vm.curpage = 1;
		}
	}
	scope.$watch("vm.curpage",function(newval , oldval){
		scope.searchTopic();
	});
	scope.searchTopic = function(){
		scope.onloading = true;
		service.queryAllTopic(scope.vm).then(function(data){
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
	
}])