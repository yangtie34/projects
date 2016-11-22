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
				url:httpConfig.basePath+"/four/score/detail/"+httpConfig.id,
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
				url:httpConfig.basePath+"/four/score/detail/group/sy/"+httpConfig.id,
				success: function(data){
					$scope.mutiSource[0].items=data.object;
				}
			});
		  mask.hideLoading();
	  };
	  
	  // 复合查询组件模拟数据
	  $scope.mutiSource = [{
			queryName : '学年',
			queryCode : "school_year",
			items : [ ],
		}, {
			queryName : '学期',// 名称
			queryCode : "term_name",
			items : [{	id : '第一学期',mc : '第一学期'}, 
			          {	id : '第二学期',mc : '第二学期'}
			         ],// 条件数据
		} , {
			queryName : '考试类型',// 名称
			queryCode : "type",
			items : [{	id : 'bf',mc : '百分制'}, 
			          {	id : 'dj',mc : '等级制'}
			         ],// 条件数据
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

