/**
 * “角色授权页面”controller
 */

app.controller("planWorkController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.plan=plan;
	scope.planWorks=[];
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	scope.pageW= {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var vmW = scope.vmW = {};
	  vmW.items = [];

	  //ajax service
	  var htt={};
	  htt.WorkData={};
	  htt.WorkData.service="taskWorkService?getWorks";
	  
	  htt.planWorkData={};
	  htt.planWorkData.service="taskPlanWorkService?getPlanWorks";
	  htt.planWorkDataByPlanId={};
	  htt.planWorkDataByPlanId.service="taskPlanWorkService?getWorksByPlanId";
	  htt.planWorkAdd={};
	  htt.planWorkAdd.service="taskPlanWorkService?updateOrInsert";
	  htt.planWorkDel={};
	  htt.planWorkDel.service="taskPlanWorkService?del";
	  htt.planWorkUpdate={};
	  htt.planWorkUpdate.service=htt.planWorkAdd.service;
	  htt.planWorkByName={};
	  htt.planWorkByName.service="taskPlanWorkService?getPlanWorkByName";
	  htt.groupCode={};
	  htt.groupCode.service="taskGroupService?getGroupCodes";
	  scope.queryCode = function(){
		  htt.groupCode.params=[];
		  mask.showLoading();
		  http.callService(htt.groupCode).success(function(data){
			  scope.codes=data;
			  scope.codeMap={};
			  for(var i=0;i<data.length;i++){
				  scope.codeMap[data[i].code_]=data[i].name_;
			  };
			  mask.hideLoading();
		  });
	  };
	  scope.queryCode();
	  //加载表
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  htt.planWorkDataByPlanId.params=[];
		  htt.planWorkDataByPlanId.params.push(scope.page.currentPage || 1);
		  htt.planWorkDataByPlanId.params.push(scope.page.numPerPage || 10);
		  htt.planWorkDataByPlanId.params.push(scope.plan.id);
		  http.callService(htt.planWorkDataByPlanId).success(function(data){
			  vm.items=data.resultListObject;
			  vm.itemsOrder=data.resultList[0];
			  scope.planWorks=data.resultList[0].list;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
	  scope.qWork={};
	  scope.queryWork = function(){
		  if(scope.qWork.group_==''){
			  scope.qWork.group_=null;
		  }
		  mask.showLoading();
		  htt.WorkData.params=[];
		  htt.WorkData.params.push(scope.pageW.currentPage || 1);
		  htt.WorkData.params.push(scope.pageW.numPerPage || 10);
		  htt.WorkData.params.push( scope.qWork);
		  http.callService(htt.WorkData).success(function(data){
			  vmW.items=data.resultListObject;
			  scope.pageW.totalRows=data.totalRows;
			  scope.pageW.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryWork();
	  //新增计划
	  scope.createPlanWork=function(){
		  htt.planWorkAdd.params=[];
		  scope.planWork.planId=scope.plan.id;
		  if(scope.planWork.workId==""||!scope.planWork.workId){
			  timeAlert.error("请选择业务");
			  return;
		  }
		  if(scope.planWork.order_==""||!scope.planWork.order_){
			  timeAlert.error("请选择顺序");
			  return;
		  }
		  htt.planWorkAdd.params.push(scope.planWork);
		  mask.showLoading();
		  http.callService(htt.planWorkAdd).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("添加成功");
  				scope.createPlanWorkDiv=false;
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  scope.getPlanByName=function(){
		  mask.showLoading();
		  htt.planByName.params=[];
		  htt.planByName.params.push(scope.page.currentPage || 1);
		  htt.planByName.params.push(scope.page.numPerPage || 10);
		  htt.planByName.params.push(scope.plan.name_);
		  http.callService(htt.planByName).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  
	  scope.isTruePlanWork=function(item){
		  item.isTrue=item.isTrue==1?0:1;
		  scope.updatePlanWork(item);
	  };
	  scope.updatePlanWork=function(item){
		  htt.planWorkUpdate.params=[];
		  htt.planWorkUpdate.params.push(item);
		  mask.showLoading();
		  http.callService(htt.planWorkUpdate).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("更新成功");
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  scope.delPlanWork=function(index){
		  if(confirm('您确定要删除该条数据吗？')) {
		  htt.planWorkDel.params=[];
		  htt.planWorkDel.params.push(scope.planWorks[index]);
		  mask.showLoading();
		  http.callService(htt.planWorkDel).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("删除成功");
  			  }else{
  				timeAlert.error("删除失败");
  			  }
  			  scope.queryGridContent();
	      }); 
		  }
	  };
	  scope.checkWork=function(item){
		  scope.work=item;
		  scope.planWork.workId=item.id;
		  };
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
	  scope.$watch('pageW',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryWork();
		  }
	  },true);
	  scope.myKeyup=function(e){
		  var keynum;
			if(window.event) 
		  	{
		  		keynum = e.keyCode;
		  	} else if(e.which) 
		  	{
		  		keynum = e.which;
		  	}
		  	if(keynum==13){
		  		scope.queryWork();
		  	}
	  };
}]);