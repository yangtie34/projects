//定义一个controller
//参数分别是1 模块名 2 控制器名 3 注入对象以及控制器函数
custom.controller('app',"controller",['$scope','mask',function($scope,mask){
	
	 var vm = $scope.vm = {};
	 vm.items = [];
	 
	 
	 $scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 0,
			  numPerPage : 10,
			  conditions : []
	 	};
	  
	  $scope.queryGridContent = function(){
		  mask.showLoading();
		  $.ajax({
				type: "POST",
				async:false,
				url:httpConfig.basePath+"/four/card/detail/"+httpConfig.id,
				data:{page:angular.toJson($scope.page),conditions:angular.toJson($scope.page.conditions)},
				dataType:"json",
				success: function(data){
					vm.items=data.object.resultList;
					$scope.page.totalRows=data.object.totalRows;
					$scope.page.totalPages=data.object.totalPages;
				}
			});
		  mask.hideLoading();
	  };
	  $scope.initSource = function(){
		  mask.showLoading();
		  $.ajax({
				type: "POST",
				async:false,
				url:httpConfig.basePath+"/four/card/detail/group/deal/"+httpConfig.id,
				success: function(data){
					$scope.mutiSource[0].items=data.object;
				}
			});
		  $.ajax({
				type: "POST",
				async:false,
				url:httpConfig.basePath+"/four/card/detail/group/time/"+httpConfig.id,
				success: function(data){
					$scope.mutiSource[1].items=data.object;
				}
			});
		  mask.hideLoading();
	  };
	  
	  // 复合查询组件模拟数据
	  $scope.mutiSource = [{
			queryName : '交易类型',// 名称
			queryCode : "card_deal_id",
			items : [  ],// 条件数据
		}, {
			queryName : '交易时间',// 名称
			queryCode : "time_",
			items : [  ],// 条件数据
		} ];
	  $scope.initSource();
	  
	  /*监控选择条件的变化，若变更则执行后台调用*/
	  $scope.$watch('page.conditions',function(val1,val2){
		  if (val1 != val2) {
			  $scope.page.currentPage = 1;
			  $scope.queryGridContent();
		  }
	  },true);
	  
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  $scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)){
			  $scope.queryGridContent();
		  }
	  },true);
	  
	  
}]);

