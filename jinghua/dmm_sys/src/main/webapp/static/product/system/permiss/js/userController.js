
app.controller("userController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	var GetQueryString=function(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
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
		  if(confirm('您确定要重置密码吗？')) {
			  mask.showLoading();
		      var data={};
			  data.params=[];
			  data.params.push(item.id);
			  data.service='userService?resetPassword';
	  		  http.callService(data).success(function(data){
	  			mask.hideLoading();
	  			timeAlert.success("重置成功");
		      }); 
		  }
		  
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
	  var htmlroleId=null;
	  // 复合查询组件模拟数据
	  scope.mutiSource = [];
	  var selectObj=[];
	  http.call({
		  service:'deptTreeService?getDeptTeach'
			  }).call(function(data1){
				  return{
					  service:'roleService?getRoles'
				  };
			  }).call(function(data1){
				  return{
					  service:'deptTreeService?getDeptPerms'
				  };
			  }).end(function(DeptTeach,Roles,Dept){
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
					 htmlroleId=GetQueryString("roleId");
						if(htmlroleId!=null){
							for(var i =0;i<selectObj.length;i++){
								var nodes=selectObj[i].children;
								for(var j=0;j<nodes.length;j++){
									if(nodes[j].id==htmlroleId){
										nodes[j].checked=true;
										break;
									}
								}
							}
						}else{
							scope.queryGridContent();
						}
					scope.mutiSource=[{
						queryName : '角色',
						queryCode : "comboSelect",
						queryType : "comboSelect",
						items : selectObj
					},{
						queryName : '组织机构',
						queryCode : "deptTree",
						queryType : "comboTree",
						items : getChildren(Dept)
					},{
						queryName : '教学机构',
						queryCode : "deptTeachTree",
						queryType : "comboTree",
						items : getChildren(DeptTeach)
					}];  
					 
					scope.roles=selectObj;
			  });
scope.roleUpdate1=function(item){
	scope.user=item;
	scope.roleUpdateDiv=true;
	
	var data={};
	  data.params=[];
	  data.params.push(item.username);
	  data.service='userService?getUserRoles';
	  mask.showLoading();
	  http.callService(data).success(function(data){
		  scope.userRoles=[];
		  for(var i=0;i<data.length;i++){
			  if(data[i].ISMAIN==1){
				  scope.userRootRole=data[i];
			  }else{
				  scope.userRoles.push(data[i]);  
			  }
		  }
		  mask.hideLoading();
    }); 
};
scope.roleUpdate2=function(){
	var roleids =scope.userRoles;
	var ids=scope.userRootRole.ID;
	for(var i=0;i<roleids.length;i++){
		if(roleids[i].ischeck==true){
			ids+=","+roleids[i].ID;
		}
	}
	if(ids==scope.user.role_ids){
		timeAlert.error("未修改");
	}else{
		  var data={};
		  data.params=[];
		  data.params.push(scope.user.id);
		  data.params.push(ids);
		  data.service='userService?addUserRoleAjax';
		  mask.showLoading();
  		  http.callService(data).success(function(d){
  			mask.hideLoading();
  			 if(d.isTrue){
                 timeAlert.success("角色关联成功");
             }
  			 scope.queryGridContent();
	      }); 
		
	}
	scope.roleUpdateDiv=false;
};

	  scope.isTrue=function(item){
		  mask.showLoading();
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
  			mask.hideLoading();
  			if(data!=null)timeAlert.success("修改成功");
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
					 	switch(val1.conditions[i].queryCode){
					 		case "deptTree":
							scope.searchUser.dept_id=val1.conditions[i].id;
					 		break;
					 		case "deptTeachTree":
							scope.searchUser.dept_teach_id=val1.conditions[i].id;
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
