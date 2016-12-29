/**
 * “角色授权页面”controller
 */

app.controller("permssionController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
    $(function(){
        obj = document.getElementById("operateList");
        for(i=0;i<obj.length;i++){
            if(obj[i].value=='6')
                obj[i].selected = true;
            scope.perm_data.operId='6';
        }
    })
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
	  var new_=0;  var newzy=false;
	  scope.res_treeData=angular.copy(res_treeData);
	  scope.res_treeData.new_=new_;
	  initjgdata=function(){
		  new_++;
		  scope.zzjg_treeData=angular.copy(zzjg_treeData);
		  scope.zzjg_treeData.new_=new_;
			 scope.jxjg_treeData=angular.copy(jxjg_treeData);
			 scope.jxjg_treeData.new_=new_;
	  }
	  initjgdata();
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
	  htt.getresData={service:"resourcesService?getPermssionsByFlagAndId",
			  params:[scope.perm_flag,scope.urole.id]}
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
	  getlevelList=function(data){
		  var list=[];
		  for(var i=0;i<data.length;i++){
			  var level=Number(data[i].level_);
			  list[level]=list[level]||'';
			  if(list[level]==''){
				  list[level]+=data[i].id;
			  }else{
				  list[level]+=","+data[i].id;  
			  }
		  }
		  return list;
	  }
	  scope.save_zzjg=function(){
		  var nodes=scope.zzjg_treeResult;
		  var ids=getlevelList(nodes);
	       /* for(var i=0;i<nodes.length;i++){
	            ids+=nodes[i].id+",";
	        }*/
	        if(isAdd){
	            scope.zzjgText=ids;
	        }else{
	        	if(nodes.length==0){
		        	scope.deletePerm(perm_id);
		        	return
		        };
	        	var resetDept={};
	            resetDept.peopleType=scope.perm_flag;
	            resetDept.jgType='zzjg';
	            resetDept.permId=perm_id;
	            resetDept.jgIds=ids;
	            htt.upDeptDate.params=[resetDept];
	            mask.showLoading();
	            http.callService(htt.upDeptDate).success(function(d){
	            	mask.hideLoading();
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
		  var ids=getlevelList(nodes);
	        /*for(var i=0;i<nodes.length;i++){
	            ids+=nodes[i].id+",";
	        }
	        if(ids==''){
	        	scope.deletePerm(perm_id);
	        	return
	        }*/
	        if(isAdd){
	            scope.jxjgText=ids;
	        }else{
	        	if(nodes.length==0){
		        	scope.deletePerm(perm_id);
		        	return
		        };
	        	var resetDept={};
	            resetDept.peopleType=scope.perm_flag;
	            resetDept.jgType='jxjg';
	            resetDept.permId=perm_id;
	            resetDept.jgIds=ids;
	            htt.upJxjgDate.params=[resetDept];
	            mask.showLoading();
	            http.callService(htt.upJxjgDate).success(function(d){
	            	mask.hideLoading();
	            	 if(d.isTrue){
	            		 timeAlert.success('更新成功');
	                 }else{
	                     	timeAlert.error(d.name);
	                 }
	  		  });
	        }
	  };
	  var cxfw=false;
	  var getDataCheck=function(data1,data){
			  for(var i=0;i<data1.length;i++){
				  var index=data.idMap[data1[i].id];
				  data.data[index].check=true;
			  }
			  data.new_++;
			  cxfw=true;
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
		    htt.addData.params=[perm_data,scope.res_treeResult];
		    mask.showLoading();
		    http.callService(htt.addData).success(function(d){
		    	mask.hideLoading();
		    	if(d.isTrue){
		            scope.zzjgText="";
		            scope.jxjgText="";
		    		timeAlert.success("添加成功");
		    		scope.queryGridContent();
		    		getresData();
		    		 scope.zzjg_treeData.new_++;
		  			 scope.jxjg_treeData.new_++;
				}else{
		            	timeAlert.error(d.name);
				}	
		    	
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
			mask.showLoading();
		  http.callService(htt.permData).success(function(data){
			  vm.items=data.resultList;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
			  scope.resourcesName="";
			  mask.hideLoading();
		  });
	  };
	  scope.queryGridContent();
	  scope.open_zzjg=function(){
		  isAdd=true;  if(cxfw)initjgdata();cxfw=false;
	  };
 scope.open_jxjg=function(){
	 isAdd=true; if(cxfw)initjgdata();cxfw=false;
	  };
	//自定义zzjg数据
	  scope.showZzjg=function(id){
		  mask.showLoading();
		  htt.zzjgDate.params=[];
		  htt.zzjgDate.params.push(id);
		 http.callService(htt.zzjgDate).success(function(data){
			 initjgdata();
			 if(typeof data=="string"){
				 scope.showZzjg(id);alert("查询错误，重新查询中！");return;
			 }
			 getDataCheck(data,scope.zzjg_treeData);
			 scope.zzjgInfoWindow=true;
			 isAdd=false;
	            perm_id=id;
	            mask.hideLoading();
	     });
	  };
	  
	 //自定义jxjg数据
	 scope.showJxjg=function(id){
		  mask.showLoading();
		 htt.JxjgDate.params=[];
		  htt.JxjgDate.params.push(id);
		 http.callService(htt.JxjgDate).success(function(data){
			 if(typeof data=="string"){
				 scope.showJxjg(id); alert("查询错误，重新查询中！");return;
			 }
			 initjgdata();
			 getDataCheck(data,scope.jxjg_treeData);
			 scope.jxjgInfoWindow=true;
			 isAdd=false;
	         perm_id=id;
	         mask.hideLoading();
	     });
	  };	
	 var getresData=function(){
		 cxfw=true;
		 scope.res_treeResult=[];
		 mask.showLoading();
		 http.callService(htt.getresData).success(function(d){
         	scope.res_treeData=angular.copy(d);
         	new_++;
         	scope.res_treeData.new_=new_;
         	mask.hideLoading();
         	newzy=false;
		     });
	 } 
	  scope.deletePerm=function(id){
		  if(confirm('您确定要删除该条数据吗？')) {
		  mask.showLoading();
		  htt.delData.params=[id];
		  http.callService(htt.delData).success(function(d){
			  mask.hideLoading();
			  if(d.isTrue){
				  timeAlert.success('删除成功');
	                scope.queryGridContent();
	                newzy=true;
				}
		     });
		  }
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
		  scope.res_treeResultMcs=[];
		  for(var i=0;i<val.length;i++){
			  scope.res_treeResultMcs.push(val[i].mc);
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
	  scope.zyClick=function(){
		  if(newzy)  getresData();
		  scope.zyInfoWindow=true;
	  }
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