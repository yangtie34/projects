/**
 * “角色授权页面”controller
 */

app.controller("planLogController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.plan=plan;
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	scope.p={};
	scope.panelClass=[
	                  'panel-primary','panel-success','panel-info','panel-warning','panel-danger'
	                  ];
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];

	  //ajax service
	  var htt={};
	  htt.planLogData={};
	  htt.planLogData.service="taskPlanLogService?getPlanLogs";
	  htt.planLogDetailsData={};
	  htt.planLogDetailsData.service="taskPlanLogDetailsService?getWorkPlanLogDetailsByPlanLogId";
	  
	  htt.verifyDetailsData={};
	  htt.verifyDetailsData.service="taskPlanLogDetailsService?getVerifyPlanLogDetailsByPlanLogId";
	  
	  htt.groupCode={};
	  htt.groupCode.service="taskGroupService?getGroupCodes";
	  scope.queryCode = function(){
		  htt.groupCode.params=[];
		  http.callService(htt.groupCode).success(function(data){
			  scope.codes=data;
			  scope.codeMap={};
			  for(var i=0;i<data.length;i++){
				  scope.codeMap[data[i].code_]=data[i].name_;
			  };
		  });
	  };
	  scope.queryCode();
	  scope.plusClick=function(item){
		  scope['table'+item.id]=scope['table'+item.id]?false:true;
		  scope.p['page'+item.id]=angular.copy(scope.page);
		  var query=function(id){
			  mask.showLoading();
			  htt.planLogDetailsData.params=[];
			  htt.planLogDetailsData.params.push(scope.p['page'+id].currentPage || 1);
			  htt.planLogDetailsData.params.push(scope.p['page'+id].numPerPage || 10);
			  htt.planLogDetailsData.params.push(id);
			  http.callService(htt.planLogDetailsData).success(function(data){
				  vm['items'+id]=data.resultListObject;
				  scope.p['page'+id].totalRows=data.totalRows;
				  scope.p['page'+id].totalPages=data.totalPages;
				  mask.hideLoading();
			  }); 
		  };
		  if(!vm['items'+item.id])query(item.id);
		  scope.$watch('p.page'+item.id,function(val1,val2){
			  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
				  query(item.id);
			  }
		  },true);
	  };
	  scope.getVerify=function(item){
		  scope.getVerifyVm='itemsV'+item.logTypeId;
		  scope.getVerifyDiv=true;
		  mask.showLoading();
		  htt.verifyDetailsData.params=[];
		  htt.verifyDetailsData.params.push(item.planLogId);
		  htt.verifyDetailsData.params.push(item.logTypeId);
		  if(!vm['itemsV'+item.logTypeId])
		  http.callService(htt.verifyDetailsData).success(function(data){
			  vm['itemsV'+item.logTypeId]=data;
		  });  
		  mask.hideLoading();
	  };
	  //加载表
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  htt.planLogData.params=[];
		  htt.planLogData.params.push(scope.page.currentPage || 1);
		  htt.planLogData.params.push(scope.page.numPerPage || 10);
		  http.callService(htt.planLogData).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
	
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
	  
}]);