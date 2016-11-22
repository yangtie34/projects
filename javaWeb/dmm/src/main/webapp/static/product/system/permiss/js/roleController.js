
app.controller("roleController", [ "$scope","dialog",'mask','http','timeAlert',function(scope,dialog,mask,http,timeAlert) {
	scope.boxSource={};
	scope.role={};
    scope.roleTypeSelect = [];
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  
	  scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	  scope.searchRole={};
	  
	  scope.updateRole=function(role){
		  var data={};
		  data.params=[];
		  data.params.push(role.id);
		  if(role.istrue==0){
			  data.params.push(1);
		  }else{
			  data.params.push(0);
		  }
		  data.service='roleService?updateRoleIstureAjax';
  		  http.callService(data).success(function(data){
  			  if(data.isTrue){
  				timeAlert.success("设置成功");
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  };
	  
	  scope.myKeyup = function(e){
          var keycode = window.event?e.keyCode:e.which;
          if(keycode==13){
        	  scope.queryGridContent();
          }
      };
	  var _initRoleType=function(){
		  var data={};
		  data.params=[];
  		  data.service='roleService?findRoleType';
		  http.callService(data).success(function(resultData){
			  var roleTypes=[];
			  angular.forEach(resultData,function(o){
				  var roleTypeO={};
				  roleTypeO.id=o.TYPE_CODE;
				  roleTypeO.name=o.TYPE_NAME;
				  roleTypes.push(roleTypeO);
  			  });
			  scope.roleTypeSelect=roleTypes;
			  
	      }); 

	  };
	  
	  scope.initData=function(){
		  var data={};
		  data.params=[];
		  data.params.push({});
		  data.service='resourcesService?getResourceAngularTree';
  		  http.callService(data).success(function(resultData){
  			  scope.boxSource=resultData;
	      }); 
  		  
		  _initRoleType();
		  scope.queryGridContent();
	  };
	  
	  scope.showSetMain=function(role){
		  mask.showLoading();
		  scope.role=role;
		  _checkTree(scope.boxSource,"");
		  var data={};
		  data.params=[];
		  data.params.push({id:role.id});
		  data.service='resourcesService?getMainPageByRole';
  		  http.callService(data).success(function(data){
  			if(data != null && typeof data !='undefined' && data != ''){
  				_checkTree(scope.boxSource,data.id);
  			}
  			scope.showWinFlag = true;
	  			mask.hideLoading();
	      }); 
	  };
	  scope.setMain=function(){
		  var data={};
		  data.params=[];
		  data.params.push(scope.role.id);
		  data.params.push(scope.boxResult.id);
		  data.service='roleService?setRoleResourceAjax';
  		  http.callService(data).success(function(data){
  			  if(data.isTrue){
  				timeAlert.success("设置成功");
  			  }else{
  				timeAlert.error(data.name);
  			  }
  				
	      });
		  scope.showWinFlag = false;
	  };
	  
	  scope.showCreateRoleDiv=function(){
		  scope.role={};
		  scope.createRoleDiv=true;
	  };
	  
	  scope.createRole=function(){
		  if(scope.roleType == null || typeof scope.roleType =='undefined' || scope.roleType == ''){
			  timeAlert.error("角色类型不能为空");
			  return ;
		  }
		  if(scope.role.istrue == null || typeof scope.role.istrue =='undefined' || scope.role.istrue == ''){
			  timeAlert.error("角色状态不能为空");
			  return ;
		  }
		  var data={};
		  data.params=[];
		  scope.role.role_type=scope.roleType.id;
		  data.params.push(scope.role);
		  data.service='roleService?createRoleAjax';
  		  http.callService(data).success(function(data){
  			  if(data.isTrue){
  				timeAlert.success("保存成功");
  				scope.createRoleDiv=false;
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
  			  
	      }); 
		  
		  
	  }
	  
	  
	  scope.deleteRole=function(role){
		  var data={};
		  data.params=[];
		  data.params.push(role.id);
		  data.service='roleService?deleteRoleAjax';
  		  http.callService(data).success(function(data){
  			  if(data.isTrue){
  				timeAlert.success("删除成功");
    			scope.queryGridContent();
  			  }else{
  				timeAlert.error(data.name);
  			  }
	      }); 
	  }
	  
	  var _checkTree=function(tree,id){
		  if(tree.id==id){
			  tree.checked=true;
		  }else{
			  tree.checked=false;
		  }
		  if(tree.children){
			  angular.forEach(tree.children,function(o){
				  _checkTree(o,id);
  			  });
		  }
		  
	  }
	  
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  var data={};
		  data.params=[];
		  data.params.push(scope.page.currentPage || 1);
		  data.params.push(scope.page.numPerPage || 10);
		  data.params.push(scope.searchRole);
		  data.service='roleService?getPageRole';
		  
  		  http.callService(data).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
	      }); 
		  
	  };
	  scope.initData();
	  
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)){
			  scope.queryGridContent();
		  }
	  },true);
	  
}]);
