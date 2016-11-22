/**
 * “角色授权页面”controller
 */

app.controller("planController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  //ajax service
	  var htt={};
	  htt.planData={};
	  htt.planData.service="taskPlanService?getPlans";
	  htt.planStart={};
	  htt.planStart.service="taskPlanService?startNow";
	  htt.planAdd={};
	  htt.planAdd.service="taskPlanService?updateOrInsert";
	  htt.planDel={};
	  htt.planDel.service="taskPlanService?del";
	  htt.planUpdate={};
	  htt.planUpdate.service=htt.planAdd.service;
	  htt.planByName={};
	  htt.planByName.service="taskPlanService?getPlanByName";
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
	  //加载表
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  htt.planData.params=[];
		  htt.planData.params.push(scope.page.currentPage || 1);
		  htt.planData.params.push(scope.page.numPerPage || 10);
		  http.callService(htt.planData).success(function(data){
			  vm.items=data.resultListObject;
			  for(var i=0;i<vm.items.length;i++){
				  vm.items[i].desc_=cronStr(vm.items[i].cron_expression);
			  };
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
	  //新增计划
	  scope.createPlan=function(){
		  htt.planAdd.params=[];
		  scope.plan.isTrue=scope.plan.isTrue?1:0;
		  htt.planAdd.params.push(scope.plan);
		  http.callService(htt.planAdd).success(function(data){
  			  if(data){
  				 if(scope.up_){
  					timeAlert.success("更新成功");
   				  }else{
   					timeAlert.success("添加成功");
   				}
  				scope.createPlanDiv=false;
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
  			 scope.plan.name_="";
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
	  scope.isTruePlan=function(item){
		  item.isTrue=item.isTrue==1?0:1;
		  scope.start=true;
		  scope.updatePlan(item);
	  };
	  scope.update=function(item){
		  scope.createPlanDiv=true;
		  scope.up_=true;
		  scope.plan=angular.copy(item);
		  scope.planisTrue=scope.plan.isTrue==1?true:false;
	  };
	  scope.updatePlan=function(item){
		  htt.planUpdate.params=[];
		  htt.planUpdate.params.push(item);
		  http.callService(htt.planUpdate).success(function(data){
  			  if(data){
  					timeAlert.success(item.isTrue==1?"已启动":"已停止");
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  scope.startPlan=function(id){
		  htt.planStart.params=[];
		  htt.planStart.params.push(id);
		  http.callService(htt.planStart).success(function(data){
  			  if(data){
  				timeAlert.success("执行成功");
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  scope.delPlan=function(item){
		  htt.planDel.params=[];
		  htt.planDel.params.push(item);
		  http.callService(htt.planDel).success(function(data){
  			  if(data){
  				timeAlert.success("删除成功");
  			  }else{
  				timeAlert.error("删除失败");
  			  }
  			  scope.queryGridContent();
	      }); 
	  };
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
	  scope.expression=function(){
		  scope.cron_expression=true;
		  scope.cron_expressionsource=angular.copy(scope.plan.cron_expression);
	  };
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
		  		scope.getPlanByName();
		  	}
	  };
}]);