
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
	  scope.update=function(item){
		  scope.showCreateRoleDiv();
		  scope.role=angular.copy(item);
		  scope.flag='update';
		  scope.roleType=function(){
			  for(var i=0;i<scope.roleTypeSelect.length;i++){
				  if(item.role_type==scope.roleTypeSelect[i].name){
					  return scope.roleTypeSelect[i];
				  }
			  }
		  }();
	  }
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
		  mask.showLoading();
  		  http.callService(data).success(function(data){
  			mask.hideLoading();
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
  		  mask.showLoading();
		  http.callService(data).success(function(resultData){
			  var roleTypes=[];
			  angular.forEach(resultData,function(o){
				  var roleTypeO={};
				  roleTypeO.id=o.TYPE_CODE;
				  roleTypeO.name=o.TYPE_NAME;
				  roleTypes.push(roleTypeO);
  			  });
			  scope.roleTypeSelect=roleTypes;
			  mask.hideLoading();
	      }); 

	  };
	  
	  scope.initData=function(){
		  var data={};
		  data.params=[];
		  data.params.push({});
		  data.service='resourcesService?getResourceAngularTree';
		  mask.showLoading();
  		  http.callService(data).success(function(resultData){
  			  scope.boxSource=resultData;
  			  mask.hideLoading();
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
		  mask.showLoading();
  		  http.callService(data).success(function(data){
  			mask.hideLoading();
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
		  scope.flag='create';
	  };
	  
	  scope.createRole=function(){
		  if(scope.roleType == null || typeof scope.roleType =='undefined' || scope.roleType == ''){
			  timeAlert.error("角色类型不能为空");
			  return ;
		  }
		  if(scope.role.istrue == null ){//}|| typeof scope.role.istrue =='undefined' || scope.role.istrue == ''){
			  timeAlert.error("角色状态不能为空");
			  return ;
		  }
		  var data={};
		  data.params=[];
		  var role=scope.role;
		  role.role_type=scope.roleType.id;
		  role.ismain=0;
		  role.resourceid=0;
		  data.params.push(role);
		  data.service='roleService?createRoleAjax';
		 if(scope.flag=='update') data.service='roleService?updateRoleAjax';
		  mask.showLoading();
  		  http.callService(data).success(function(data){
  			  mask.hideLoading();
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
		  if(confirm('您确定要删除该条数据吗？')) {
			  var data={};
			  data.params=[];
			  data.params.push(role.id);
			  data.service='roleService?deleteRoleAjax';
			  mask.showLoading();
	  		  http.callService(data).success(function(data){
	  			  mask.hideLoading();
	  			  if(data.isTrue){
	  				timeAlert.success("删除成功");
	    			scope.queryGridContent();
	  			  }else{
	  				timeAlert.error(data.name);
	  			  }
		      }); 
		  }
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
