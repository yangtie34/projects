/**
 * “资源管理页面”controller
 */

app.controller("resourceController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	var vm = scope.vm = {};
	  vm.items = [];
	scope.searchRes={};
	scope.modelRes={};
	scope.searchRes.name_="";
	var addResource = scope.addResource = {};
	addResource.resource={};
	/*addResourceflag.res_name =true;//"请输入资源名称";
	addResourceflag.res_type="请选择资源类型";
    addResourceflag.res_url="请输入资源url";
    addResourceflag.res_desc="请输入资源描述";
    addResourceflag.res_shiro ="请输入资源权限标识";
    addResourceflag.res_order ="请输入资源排序号";
    addResourceflag.res_keyword ="请输入资源关键字";*/
	var htt={};
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
   var dtree=function(id){
	   var data={};
		  data.params=[{}];
		  data.service='resourcesService?getResourceAngularTree';
		  http.callService(data).success(function(data){
			  scope.treeData=data;
			  var putCheck=function(id,data){
				  if(data.id==id){
					  data.check=true;
					  return;
				  }else if(data.children){
					  for(var i=0;i<data.children.length;i++){
						  putCheck(id,data.children[i]);
					  }
				  }
			  };
			  putCheck(id,scope.treeData);
	    }); 
   };
   dtree("0");
   scope.thisNode=scope.treeData;
	//添加资源
	scope.insert_res=function(){
		 var data={};
		  data.params=[];
		  scope.resource.pid=scope.modelRes.pid;
		  data.params.push(scope.resource);
		  data.service="resourcesService?createResourceAjax";
		http.callService(data).success(function(d){
			if(d.isTrue){
				dtree(scope.thisNode.id);
				timeAlert.success("添加成功");
				 scope.addResource.flag = false;
			}else{
				timeAlert.error(d.name);
			}
	    });
	};
	//编辑
	scope.update1=function(item){
		scope.resourceUpdataflag=true;
		var data={};
		  data.params=[];
		  data.params.push(item.id);
		  data.service="resourcesService?findById";
		scope.resource.id=item.id;
		http.callService(data).success(function(d){
			d.istrue==1?scope.resourceistrue=true:scope.resourceistrue=false;
			scope.resource=d;
	    });
		scope.addResource.flag = true;
	};
	scope.istrue=function(item){
		scope.resourceUpdataflag=true;
		var data={};
		  data.params=[];
		  item.istrue=item.istrue==1?0:1;
		  data.params.push(item);
		  data.service="resourcesService?updateResourcesAjax";
		http.callService(data).success(function(d){
			if(d.isTrue){
				timeAlert.success("修改成功");
			}else{
				timeAlert.error(d.name);
			}
	    });
	};
	scope.update_res=function(){
		 var data={};
		  data.params=[];
		  data.params.push(scope.resource);
		  data.service="resourcesService?updateResourcesAjax";
		http.callService(data).success(function(d){
			if(d.isTrue){
				scope.queryGridContent();
				timeAlert.success('更新成功');
				 scope.addResource.flag = false;
			}else{
				timeAlert.error(d.name);
			}
	    });
	};
	//删除
	scope.del_res=function(item){
		var data={};
		  data.params=[];
		  data.params.push(item.id);
		  data.service="resourcesService?deleteResourcesAjax";
		http.callService(data).success(function(d){
			if(d.isTrue){
				dtree(scope.thisNode.id);
				scope.queryGridContent();
				timeAlert.success('删除成功');
			}else{
				timeAlert.error(d.name);
			}
	    });
	};
	//上级节点
	 getPrevs=function(dt){
		if (dt.children) { 
			for(var i=0;i<dt.children.length;i++){
				dt.children[i].check=false;
				if(dt.children[i].id==scope.thisNode.id){
					//scope.$$childTail.cgComboCheckTreeClick(dt);
						/*  scope.searchRes.pid=dt.id;
						  scope.modelRes.pid=dt.id;
						  scope.modelRes.pname=dt.mc;
						  scope.searchRes.name_="";
						  scope.queryGridContent();
						  scope.thisNode=dt;*/
				}else{
					getPrevs(dt.children[i]);
				}
			}
		}
	};
	scope.getPrev=function(){
		if(angular.toJson(scope.thisNode)!=angular.toJson(scope.treeData)){
			getPrevs(scope.treeData);
		}
	};
	  scope.queryGridContent = function(){
		  var data={};
		  data.params=[];
		  data.params.push(scope.page.currentPage || 1);
		  data.params.push(scope.page.numPerPage || 10);
		  data.params.push(scope.searchRes);
		  data.service='resourcesService?getResourcesPageByThis';
  		  http.callService(data).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
	      }); 
	  };
	  //查询
	  scope.getResourceByInput= function(){
		  scope.queryGridContent();
	  };
	  getqxbzf=function(data,id){
		  for(var i=0;i<data.children.length;i++){
			  if(data.children[i].id==id){
				  return data.children[i];
			  }else{
				  getqxbzf(data.children[i],id); 
			  }
		  }
		  
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
		  		scope.getResourceByInput();
		  	}
	  };
	 /* 监控list点击*/
	  scope.$watch('treeResult',function(nData){
		  scope.searchRes.pid=nData[0].id;
		  scope.modelRes.pid=nData[0].id;
		  scope.resource={};
		  scope.resource.pid=scope.modelRes.pid;
		  scope.modelRes.pname=nData[0].mc;
		  scope.searchRes.name_="";
		  scope.queryGridContent();
		  scope.thisNode=nData[0];
		  scope.parentres=getqxbzf(scope.treeData,nData[0].pid); 
			var data={};
			  data.params=[];
			  data.params.push(scope.parentres.id);
			  data.service="resourcesService?findById";
			http.callService(data).success(function(d){
				scope.parentres=d;
		    });
	  },true);
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
}]);