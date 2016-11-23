
app.controller("userController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	
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
	  scope.searchUser={};
	  scope.resetPassword = function(item){
		  var data={};
		  data.params=[];
		  data.params.push(item.id);
		  data.service='userService?resetPassword';
  		  http.callService(data).success(function(data){
  			timeAlert.success("重置成功");
	      }); 
	  };  
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  var data={};
		  data.params=[];
		  data.params.push(scope.page.currentPage || 1);
		  data.params.push(scope.page.numPerPage || 10);
		  data.params.push(scope.searchUser);
		  data.service='userService?getPageUsers';
  		  http.callService(data).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
	      }); 
		  
	  };
	  // 复合查询组件模拟数据
	  scope.mutiSource = [];
	  http.call({
		  service:'deptTreeService?getDeptTeach'
			  }).call(function(data1){
				  return{
					  service:'roleService?getRoles'
				  };
			  }).end(function(DeptTeach,Roles){
				  var selectObj=[];
				  var roles={};
		            for(var i=0;i<Roles.length;i++){
		                if(roles[Roles[i].role_type]==null){
		                    roles[Roles[i].role_type]=[];
		                }
		            }
		            for(var i=0;i<Roles.length;i++){
		                for(var key in roles){
		                    if(key==Roles[i].role_type){
		                        roles[Roles[i].role_type].push(Roles[i]);
		                       
		                    }
		                }
		            }
		            for(var key in roles){
		            	 selectObj.push({
	                        	mc:key,
	                        	children:function(){
	                        		var children=[];
	                        		for(var i=0;i<roles[key].length;i++){
	                        			children.push({id:roles[key][i].id,mc:roles[key][i].description});
	                        		}
	                        		return children;
	                        	}()
	                        });
		            }
				  var data=DeptTeach;
					var getChildren=function(data){
						var item={};
						item.id=data.id;
						item.mc=data.name_;
						if(data.children!=null){
							item.children=[];
							for(var i=0;i<data.children.length;i++){
								item.children.push(getChildren(data.children[i]));
							}
						}
						return item;
					};
					scope.mutiSource.push({
						queryName : '组织机构',
						queryCode : "comboTree",
						queryType : "comboTree",
						items : getChildren(data)
					});  
					scope.mutiSource.push({
						queryName : '角色',
						queryCode : "comboSelect",
						queryType : "comboSelect",
						items : selectObj
					}); 
					scope.roles=selectObj;
			  });
scope.roleUpdate1=function(item){
	scope.user=item;
	scope.roleUpdateDiv=true;
	var roleid_=scope.user.role_ids.split(",")[0];
	for(var i=0;i<scope.roles.length;i++){
		for(var j=0;j<scope.roles[i].children.length;j++){
			if(scope.roles[i].children[j].id==roleid_){
				scope.userRoles={};
				scope.userRoles.id=scope.roles[i].children[j].id;
				scope.userRoles.mc=scope.roles[i].children[j].mc;
				scope.userRoles.children=[];
				for(var k=0;k<scope.roles[i].children.length;k++){
					if(scope.roles[i].children[k].id!=roleid_){
						scope.userRoles.children.push(scope.roles[i].children[k]);
					}
				}
				break;
			}
		}
	}
};
scope.roleUpdate2=function(){
	var roleids =scope.userRolesResult;
	var ids="";
	for(var i=0;i<roleids.length;i++){
		if(i==0){
			ids+=roleids[i].id;
		}else{
			ids+=","+roleids[i].id;
		}
	}
	if(roleids.length==0||ids==scope.user.role_ids){
		timeAlert.error("未修改");
	}else{
		if(ids.split(",")[0]!=scope.user.role_ids.split(",")[0]){
			ids=scope.user.role_ids.split(",")[0]+","+ids;
		}
		  var data={};
		  data.params=[];
		  data.params.push(scope.user.id);
		  data.params.push(ids);
		  debugger;
		  data.service='userService?addUserRoleAjax';
  		  http.callService(data).success(function(d){
  			 if(d.isTrue){
                 timeAlert.success("角色关联成功");
             }
  			 scope.queryGridContent();
	      }); 
		
	}
	scope.roleUpdateDiv=false;
};

	  scope.queryGridContent();
	  scope.isTrue=function(item){
		  mask.showLoading();
		  debugger;
		  item.istrue=item.istrue==1?0:1;
		  var data={};
		  data.params=[];
		  var l=[];l.push(item.id+'');
		  data.params.push(l);
		  var serv='freezeUsers';
		  if(item.istrue==1)
			  serv='unfreezeUsers';
		  data.service="userService?"+serv;
  		  http.callService(data).success(function(data){
  			  if(data!=null)timeAlert.success("修改成功");
			  mask.hideLoading();
	      }); 
		  
	  };
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
		  if(angular.toJson(val1.conditions)!= angular.toJson(val2.conditions)){
			  scope.searchUser={};
			  if(val1.conditions.length>0){
				 for(var i=0;i<val1.conditions.length;i++){
					 	switch(val1.conditions[i].queryType){
					 		case "comboTree":
							scope.searchUser.dept_id=val1.conditions[i].id;
					 		break;
					 		case "comboSelect":
					 		scope.searchUser.role_ids=val1.conditions[i].id;
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
		  		scope.queryGridContent();
		  	}
	  };
}]);
