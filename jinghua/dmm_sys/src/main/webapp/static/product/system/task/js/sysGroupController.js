/**
 * “角色授权页面”controller
 */

app.controller("sysGroupController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	  var vm = scope.vm = {};
	  vm.items = [];
	  //ajax service
	  var htt={};
	  htt.groupData={};
	  htt.groupData.service="taskGroupService?getGroups";
	  htt.groupAdd={};
	  htt.groupAdd.service="taskGroupService?updateOrInsert";
	  htt.groupDel={};
	  htt.groupDel.service="taskGroupService?del";

	  //加载表
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  htt.groupData.params=[];
		  http.callService(htt.groupData).success(function(data){
			  vm.items=data;
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
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
	  //新增计划
	  scope.createGroup=function(){
		  htt.groupAdd.params=[];
		  htt.groupAdd.params.push(scope.group);
		  htt.groupAdd.params.push(scope.groupName);
		  mask.showLoading();
		  http.callService(htt.groupAdd).success(function(data){
			  mask.hideLoading();
  			  if(data){
  				timeAlert.success("成功");
  				scope.createGroupDiv=false;
    			scope.queryGridContent();
    			scope.queryCode();
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };

	  scope.delGroup=function(item){
		  if(confirm('您确定要删除该条数据吗？')) {
		  htt.groupDel.params=[];
		  htt.groupDel.params.push(item);
		  mask.showLoading();
		  http.callService(htt.groupDel).success(function(data){
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
	  scope.update=function(item){
		  scope.groupName=scope.codeMap[item.id];
		  scope.group=item;
		  scope.createGroupDiv=true;
	  };

}]);