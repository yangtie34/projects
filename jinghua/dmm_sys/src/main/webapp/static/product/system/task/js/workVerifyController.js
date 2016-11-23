/**
 * “角色授权页面”controller
 */

app.controller("workVerifyController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.work=work;
	scope.workVerifys=[];
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
	  htt.verifyData={};
	  htt.verifyData.service="taskVerifyService?getVerifys";
	  htt.workVerifyData={};
	  htt.workVerifyData.service="taskWorkVerifyService?getWorkVerifys";
	  htt.workVerifyDataByPlanId={};
	  htt.workVerifyDataByPlanId.service="taskWorkVerifyService?getVerifysByPlanId";
	  htt.workVerifyAdd={};
	  htt.workVerifyAdd.service="taskWorkVerifyService?updateOrInsert";
	  htt.workVerifyDel={};
	  htt.workVerifyDel.service="taskWorkVerifyService?del";
	  htt.workVerifyUpdate={};
	  htt.workVerifyUpdate.service=htt.workVerifyAdd.service;
	  htt.workVerifyByName={};
	  htt.workVerifyByName.service="taskWorkVerifyService?getWorkVerifyByName";
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
		  htt.workVerifyDataByPlanId.params=[];
		  htt.workVerifyDataByPlanId.params.push(scope.page.currentPage || 1);
		  htt.workVerifyDataByPlanId.params.push(scope.page.numPerPage || 10);
		  htt.workVerifyDataByPlanId.params.push(scope.work.id);
		  http.callService(htt.workVerifyDataByPlanId).success(function(data){
			  vm.items=data.resultListObject;
			  vm.itemsOrder=data.resultList[0];
			  scope.workVerifys=data.resultList[0].list;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
	  scope.qVerify={};
	  scope.queryVerify = function(){
			  if(scope.qVerify.group_==''){
				  scope.qVerify.group_=null;
			  }
		  mask.showLoading();
		  htt.verifyData.params=[];
		  htt.verifyData.params.push(scope.pageW.currentPage || 1);
		  htt.verifyData.params.push(scope.pageW.numPerPage || 10);
		  htt.verifyData.params.push( scope.qVerify);
		  http.callService(htt.verifyData).success(function(data){
			  vmW.items=data.resultListObject;
			  scope.pageW.totalRows=data.totalRows;
			  scope.pageW.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryVerify();
	  //新增计划
	  scope.createWorkVerify=function(){
		  htt.workVerifyAdd.params=[];
		  if(scope.workVerify.verifyId==""||!scope.workVerify.verifyId){
			  timeAlert.error("请选择验证");
			  return;
		  }
		  if(scope.workVerify.rule==""||!scope.workVerify.rule){
			  timeAlert.error("请选择验证规则");
			  return;
		  }
		  if(scope.workVerify.order_==""||!scope.workVerify.order_){
			  timeAlert.error("请选择顺序");
			  return;
		  }
		 scope.workVerify.workId=scope.work.id;
		  htt.workVerifyAdd.params.push(scope.workVerify);
		  mask.showLoading();
		  http.callService(htt.workVerifyAdd).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("添加成功");
  				 scope.workVerify={};
  				scope.createWorkVerifyDiv=false;
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
		  scope.queryGridContent();
	  };
	  scope.getWorkVerifyByName=function(){
		  mask.showLoading();
		  htt.workVerifyByName.params=[];
		  htt.workVerifyByName.params.push(scope.page.currentPage || 1);
		  htt.workVerifyByName.params.push(scope.page.numPerPage || 10);
		  htt.workVerifyByName.params.push(scope.workVerify.name_);
		  http.callService(htt.workVerifyByName).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  
	  scope.isTrueWorkVerify=function(item){
		  item.isTrue=item.isTrue==1?0:1;
		  scope.updateWorkVerify(item);
	  };
	  scope.updateWorkVerify=function(item){
		  htt.workVerifyUpdate.params=[];
		  htt.workVerifyUpdate.params.push(item);
		  mask.showLoading();
		  http.callService(htt.workVerifyUpdate).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("更新成功");
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  scope.delWorkVerify=function(index){
		  if(confirm('您确定要删除该条数据吗？')) {
		  htt.workVerifyDel.params=[];
		  htt.workVerifyDel.params.push(scope.workVerifys[index]);
		  mask.showLoading();
		  http.callService(htt.workVerifyDel).success(function(data){
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
	  scope.checkVerify=function(item){
		  scope.verify=item;
		  scope.workVerify.verifyId=item.id;
		  };
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
	  scope.$watch('pageW',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
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
		  		scope.queryVerify();
		  	}
	  };
}]);