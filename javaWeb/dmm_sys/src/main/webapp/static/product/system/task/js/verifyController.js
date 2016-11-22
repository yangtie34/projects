/**
 * “角色授权页面”controller
 */

app.controller("verifyController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {

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
	  scope.queryverify={};
	  //ajax service
	  var htt={};
	  htt.verifyData={};
	  htt.verifyData.service="taskVerifyService?getVerifys";
	  htt.verifyAdd={};
	  htt.verifyAdd.service="taskVerifyService?updateOrInsert";
	  htt.verifyDel={};
	  htt.verifyDel.service="taskVerifyService?del";
	  htt.verifyUpdate={};
	  htt.verifyUpdate.service=htt.verifyAdd.service;
	  htt.verifyByName={};
	  htt.verifyByName.service="taskVerifyService?getVerifyByName";
	  htt.groupCode={};
	  htt.groupCode.service="taskGroupService?getGroupCodes";
	  scope.queryCode = function(){
		  htt.groupCode.params=[];
		  mask.showLoading(); scope.workCode=[];
		  http.callService(htt.groupCode).success(function(data){
			  scope.codes=data;
			  scope.codeMap={};
			  for(var i=0;i<data.length;i++){
				  scope.codeMap[data[i].code_]=data[i].name_;scope.workCode.push({id:data[i].code_,mc:data[i].name_});
			  };
			  mask.hideLoading();
		  });
	  };
	  scope.queryCode();
	  scope.mutiSource=[{
			queryName : '业务类型',// 名称
			queryCode : "group_",
			items :  scope.workCode,// 条件数据
		},{
			queryName : '是否启用',// 名称
			queryCode : "isTrue",
			items :  [{id:1,mc:'启用'},
			          {id:0,mc:'禁用'}]// 条件数据
		}];
	  //加载表
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  htt.verifyData.params=[];
		  htt.verifyData.params.push(scope.page.currentPage || 1);
		  htt.verifyData.params.push(scope.page.numPerPage || 10);
		  htt.verifyData.params.push(scope.queryverify);
		  http.callService(htt.verifyData).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
	  //新增计划
	  scope.createVerify=function(){
		  htt.verifyAdd.params=[];
		  scope.verify.isTrue=scope.verify.isTrue?"1":"0";
		  htt.verifyAdd.params.push(scope.verify);
		  mask.showLoading();
		  http.callService(htt.verifyAdd).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success(scope.wTitle+"成功");
  				scope.createVerifyDiv=false;
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
  			 scope.verify.name_="";
	      }); 
	  };
	  scope.update1=function(item){
		  scope.wTitle='修改';
		  scope.createVerifyDiv=true;
		  scope.verify=item;
		  scope.verifyisTrue=scope.verify.isTrue==1?true:false;
	  };
	  scope.getVerifyByName=function(){
		  scope.queryGridContent();
	  };
	  
	  scope.isTrueVerify=function(item){
		  item.isTrue=item.isTrue==1?0:1;
		  scope.updateVerify(item);
	  };
	  scope.updateVerify=function(item){
		  htt.verifyUpdate.params=[];
		  htt.verifyUpdate.params.push(item);
		  mask.showLoading();
		  http.callService(htt.verifyUpdate).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("更新成功");
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  scope.delVerify=function(item){
		  if(confirm('您确定要删除该条数据吗？')) {
		  htt.verifyDel.params=[];
		  htt.verifyDel.params.push(item);
		  mask.showLoading();
		  http.callService(htt.verifyDel).success(function(data){
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
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  } 
		  if(angular.toJson(val1.conditions)!= angular.toJson(val2.conditions)){
			  scope.queryverify={name_:scope.queryverify.name_};
			  if(val1.conditions.length>0){
				 for(var i=0;i<val1.conditions.length;i++){
					 	switch(val1.conditions[i].queryCode){
					 		case "group_":
					 		scope.queryverify.group_=val1.conditions[i].id;
					 		break;
					 		case "isTrue":
					 		scope.queryverify.isTrue=val1.conditions[i].id;
						 	break;
					 	}
					 }
			  }
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
		  		scope.getVerifyByName();
		  	}
	  };
}]);