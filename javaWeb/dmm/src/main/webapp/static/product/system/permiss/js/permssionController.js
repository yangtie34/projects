/**
 * “角色授权页面”controller
 */

app.controller("permssionController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	if(scope.perm_flag=='role'){
		if(scope.urole.id == ""){
		toastr['error']('该角色不存在', "");
		}
	}else if(scope.urole.id == ""){
		toastr['error']('该用户不存在', "");
	}
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
	  scope.res_treeData=angular.copy(res_treeData);
		  scope.zzjg_treeData=zzjg_treeData1;
			 scope.jxjg_treeData=jxjg_treeData1;
	//资源数据
/*	  scope.perm_dataTree=perm_dataTree;*/
	var htt={};
	var perm_id='';
	 var isAdd=true;
	//添加授权 
		htt.addData={};
	  htt.addData.service='permssionService?addPermssionsAjax';
	  htt.permData={};//加载权限页面
	  htt.delData={};  //删除权限
	  htt.zzjgDate={};
	  htt.JxjgDate={};
	  htt.upDeptDate={};
	  htt.upDeptDate.service="permssionService?resetZzOrJxjg";
	  htt.upJxjgDate={};
	  htt.upJxjgDate.service="permssionService?resetZzOrJxjg";
	  if(scope.perm_flag == 'role'){
		  htt.permData.service='permssionService?getRolePermssionPage';
		  htt.delData.service='permssionService?deleteRolePermssion';
		  htt.zzjgDate.service='deptPermissionService?getDeptByRolePermsId';
		  htt.JxjgDate.service='deptPermissionService?getDeptTeachByRolePermsId';
      }else{
		  htt.permData.service='permssionService?getUserPermssionPage';
		  htt.delData.service='permssionService?deleteUserPermssion';
		  htt.zzjgDate.service='deptPermissionService?getDeptByUserPermsId';
		  htt.JxjgDate.service='deptPermissionService?getDeptTeachByUserPermsId';
      }
	  var alertF=function(d,info){
		  if(d.isTrue){
				timeAlert.success(info);
          }else{
              	timeAlert.error(d.name);
          }
	  };
	  scope.save_zzjg=function(){
		  var nodes=scope.zzjg_treeResult;
		  var ids="";
	        for(var i=0;i<nodes.length;i++){
	            ids+=nodes[i].id+",";
	        }
	        if(isAdd){
	            scope.zzjgText=ids;
	        }else{
	        	var resetDept={};
	            resetDept.peopleType=scope.perm_flag;
	            resetDept.jgType='zzjg';
	            resetDept.permId=perm_id;
	            resetDept.jgIds=ids;
	            htt.upDeptDate.params=[resetDept];
	            http.callService(htt.upDeptDate).success(function(d){
	            	 if(d.isTrue){
	            		 timeAlert.success('更新成功');
	                 }else{
	                     	timeAlert.error(d.name);
	                 }
	  		  });
	  };
	  };
	  scope.save_jxjg=function(){
		  var nodes=scope.jxjg_treeResult;
		  var ids="";
	        for(var i=0;i<nodes.length;i++){
	            ids+=nodes[i].id+",";
	        }
	        if(isAdd){
	            scope.jxjgText=ids;
	        }else{
	        	var resetDept={};
	            resetDept.peopleType=scope.perm_flag;
	            resetDept.jgType='jxjg';
	            resetDept.permId=perm_id;
	            resetDept.jgIds=ids;
	            htt.upJxjgDate.params=[resetDept];
	            http.callService(htt.upJxjgDate).success(function(d){
	            	 if(d.isTrue){
	            		 timeAlert.success('更新成功');
	                 }else{
	                     	timeAlert.error(d.name);
	                 }
	  		  });
	        }
	  };
	  var getDataCheck=function(data1,data){
		  var data2=angular.copy(data);
		  var a=function(data){
			  var map={};
			  for(var i=0;i<data.length;i++){
				  map[data[i].id]=data[i];
			  }
			  return map;
		  };
		  var map=a(data1);
		  for(var i=0;i<data2.length;i++){
			  if(map[data2[i].id]!=null){data2[i].check=true;}
		  };
		 return toAGLobj(data2,'0');
	  };
	  scope.add_perm=function(){
		  scope.perm_data=scope.perm_data||{};
		  scope.perm_data.peopleType=scope.perm_flag;
		  scope.perm_data.peopleId=scope.urole.id;
		  var perm_data=scope.perm_data;
		  if(scope.res_treeResult.length<1){
			  timeAlert.error('请选择资源');
		        return;
		    }
		    if(!perm_data.operId||perm_data.operId==""){
		    	timeAlert.error('请选择操作权限');
		        return;
		    }
		    if(perm_data.dataId==null||perm_data.dataId==""){
		    	timeAlert.error('请选择数据范围');
		        return;
		    }else if(perm_data.dataId == 1){
		    	perm_data.jgIds=scope.jxjgText;
		    	if(perm_data.jgIds == ""){
		    		timeAlert.error('请选择组织机构');
		            return;
		    	}
		    }else if(perm_data.dataId == 2){
		    	perm_data.jgIds=scope.zzjgText;
		    	if(perm_data.jgIds == ""){
		    		timeAlert.error('请选择组织机构');
		            return;
		    	}
		    }
		    scope.res_treeData=1;
			  scope.zzjg_treeData=1;
				 scope.jxjg_treeData=1;
		    htt.addData.params=[perm_data,scope.res_treeResult];
		    mask.showLoading();
		    http.callService(htt.addData).success(function(d){
		    	if(d.isTrue){
		            scope.zzjgText="";
		            scope.jxjgText="";
		            scope.res_treeData=angular.copy(res_treeData);
		  		  scope.zzjg_treeData=zzjg_treeData1;
		  			 scope.jxjg_treeData=jxjg_treeData1;
		    		timeAlert.success("添加成功");
		    		scope.queryGridContent();
				}else{
		            	timeAlert.error(d.name);
				}	
		    	mask.hideLoading();
		    })
	  };
	  //加载表
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  htt.permData.params=[];
		  htt.permData.params.push(scope.page.currentPage || 1);
		  htt.permData.params.push(scope.page.numPerPage || 10);
		  var rPermssion =scope.perm_flag == 'role'? {role:scope.urole}:{user:scope.urole};
			if(scope.resourcesName!=""){
				rPermssion.Resource = {name_:scope.resourcesName};
			}
			htt.permData.params.push(rPermssion); 
		  http.callService(htt.permData).success(function(data){
			  vm.items=data.resultList;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
			  scope.resourcesName="";
		  });
	  };
	  scope.queryGridContent();
	  scope.open_zzjg=function(){
		  scope.zzjg_treeData=zzjg_treeData1;
		  isAdd=true;
	  };
 scope.open_jxjg=function(){
	 scope.jxjg_treeData=jxjg_treeData1;
	 isAdd=true;
	  };
	//自定义zzjg数据
	  scope.showZzjg=function(id){
		  mask.showLoading();
		  htt.zzjgDate.params=[];
		  htt.zzjgDate.params.push(id);
		 http.callService(htt.zzjgDate).success(function(data){
			  mask.hideLoading();
			 scope.zzjg_treeData=getDataCheck(data,zzjg_treeData);
			 scope.zzjgInfoWindow=true;
			 isAdd=false;
	            perm_id=id;
	     });
	  };
	 //自定义jxjg数据
	 scope.showJxjg=function(id){
		  mask.showLoading();
		 htt.JxjgDate.params=[];
		  htt.JxjgDate.params.push(id);
		 http.callService(htt.JxjgDate).success(function(data){
			  mask.hideLoading();
			 scope.jxjg_treeData=getDataCheck(data,jxjg_treeData);
			 scope.jxjgInfoWindow=true;
			 isAdd=false;
	            perm_id=id;
	     });
	  };	
	  
	  scope.deletePerm=function(id){
		  mask.showLoading();
		  htt.delData.params=[id];
		  http.callService(htt.delData).success(function(d){
			  mask.hideLoading();
			  if(d.isTrue){
				  timeAlert.success('删除成功');
	                scope.queryGridContent();
				}
		     });
	  };
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
	  scope.$watch('res_treeResult',function(val){
		  val=val?val:{};
		  var mc=val.mc?val.mc:"";
		  var id=val.id?val.id:"";
		  scope.perm_data=scope.perm_data?scope.perm_data:{};
		  scope.perm_data.resName=mc;
		  scope.perm_data.resId=id;
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
var toAGLobj=function(treeList,pd){
	var deptMap={};
	for(var i=treeList.length-1;i>=0;i--){
		treeList[i].pId?null:treeList[i].pId=treeList[i].pid;
		treeList[i].mc?null:treeList[i].name_?treeList[i].mc=treeList[i].name_:treeList[i].name?treeList[i].mc=treeList[i].name:null;
		var tree=treeList[i];
		deptMap[tree.id]=tree;
	}
	if(!deptMap[pd]){
		deptMap[pd]={};
		deptMap[pd].mc='全校';
		deptMap[pd].id=pd;
		deptMap[pd].pId="-1";
		treeList.push(deptMap[pd]);
	}
	deptMap[pd].children=[];
	for(var i=treeList.length-1;i>=0;i--){
		if(treeList[i].pId==pd){
			deptMap[pd].children.push(treeList[i]);
			toAGLobj(treeList,treeList[i].id);
		}
	}
	var aaa=deptMap[pd];
	return deptMap[pd];
};