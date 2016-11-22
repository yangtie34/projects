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
	  htt.groupCode.service="resourcesService?getResType";
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
	  scope.addnewresource=function(){
		  if(scope.thisNode.id=='0'){
			  scope.resource.resource_type_code='06';
			  scope.shirotagPre='';
		  }else{
		  var data={};
		  data.params=[];
		  data.params.push(scope.thisNode.id);
		  data.service="resourcesService?findById";
		  mask.showLoading();
			http.callService(data).success(function(d){
				 scope.shirotagPre=d.shiro_tag+":";
				 scope.selResDlTag=d.shiro_tag.split(":")[0];
				 mask.hideLoading();
		    });
		  }
		 scope.resource={};
		 scope.addResource.flag = true;
		  scope.resourceUpdataflag=false;
		  scope.resourceshiro_tag='';
		  scope.resource.isShow=true;
		  scope.resourceistrue=false;
		  
	  }
   var dtree=function(id){
	   var data={};
		  data.params=[{}];
		  data.service='resourcesService?getResourceAngularTree';
		  mask.showLoading();
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
			  mask.hideLoading();
	    }); 
   };
   dtree("0");
   scope.thisNode=scope.treeData;
   var resourceshiro_tag=function(){
	   var reg = "^[A-Za-z]+$";
	   if(!(new RegExp(reg)).test(scope.resourceshiro_tag)){
		   alert("资源标识符只允许输入英文字母");
		  throw new Error("资源标识符只允许输入英文字母");
	   }
		scope.resource.shiro_tag= scope.shirotagPre+scope.resourceshiro_tag;
		scope.resource.istrue?scope.resource.istrue=1:scope.resource.istrue=0;
		scope.resource.isShow?scope.resource.isShow=1:scope.resource.isShow=0;
   }
   var getresourceshiro_tag=function(){
	   var a=scope.resource.shiro_tag.split(":");
	   if(a.length==1){
		   scope.resourceshiro_tag=a[0];
	   }else{
		   scope.resourceshiro_tag=a[a.length-1]; 
	   }
	   scope.shirotagPre=scope.resource.shiro_tag.substring(0, scope.resource.shiro_tag.lastIndexOf(':'));
	   
	   if(scope.shirotagPre.length>0)scope.shirotagPre+=":";
   } 
	//添加资源
	scope.insert_res=function(){
		resourceshiro_tag();
		if(scope.thisNode.pid=='0'){
			scope.resource.sysGroup_=scope.thisNode.id;
		}else{
			scope.resource.sysGroup_=getRootPrev(scope.thisNode).id;	
		}
		scope.resource.istrue=scope.resource.istrue?1:0;
		scope.resource.isShow=scope.resource.isShow?1:0;
		 var data={};
		  data.params=[];
		  scope.resource.pid=scope.modelRes.pid;
		  data.params.push(scope.resource);
		  data.service="resourcesService?createResourceAjax";
		  mask.showLoading();
		http.callService(data).success(function(d){
			if(d.isTrue){
				dtree(scope.thisNode.id);initId=0;
				timeAlert.success("添加成功");
				 scope.addResource.flag = false;
			}else{
				timeAlert.error(d.name);
			}
			mask.hideLoading();
	    });
	};
	//编辑
	scope.update1=function(item){
		item.istrue==1?scope.resourceistrue=true:scope.resourceistrue=false;
		item.isShow?scope.resourceisShow=true:scope.resourceisShow=false;
		item.istrue=scope.resourceistrue;
		scope.resource=item;
		getresourceshiro_tag();
		scope.addResource.flag = true;
		scope.resourceUpdataflag=true;
	};
	var tfst=function(item){
		if(item.istrue==true)item.istrue=1;
		if(item.istrue==false)item.istrue=0;
		if(item.isShow==true)item.isShow=1;
		if(item.isShow==false)item.isShow=0;
		return item;
	}
	scope.istrue=function(item){
		item=tfst(item);
		scope.resourceUpdataflag=true;
		var data={};
		  data.params=[];
		  item.istrue=item.istrue==1?0:1;
		  data.params.push(item);
		  data.service="resourcesService?updateResourcesAjax";
		  mask.showLoading();
		http.callService(data).success(function(d){
			if(d.isTrue){
				timeAlert.success("修改成功");
			}else{
				timeAlert.error(d.name);
			}
			mask.hideLoading();
	    });
	};
	scope.isShow=function(item){
		item=tfst(item);
		scope.resourceUpdataflag=true;
		var data={};
		  data.params=[];
		  item.isShow=item.isShow==1?0:1;
		  data.params.push(item);
		  data.service="resourcesService?updateResourcesAjax";
		  mask.showLoading();
		http.callService(data).success(function(d){
			if(d.isTrue){
				timeAlert.success("修改成功");
			}else{
				timeAlert.error(d.name);
			}
			mask.hideLoading();
	    });
	};
	scope.update_res=function(){
		resourceshiro_tag();
		if(scope.thisNode.pid=='0'){
			scope.resource.sysGroup_=scope.thisNode.id;
		}else{
			scope.resource.sysGroup_=getRootPrev(scope.thisNode).id;	
		}
		scope.resource.isShow=scope.resource.isShow?1:0;
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
		if(confirm('您确定要删除该条数据吗？')) {
		var data={};
		  data.params=[];
		  data.params.push(item.id);
		  data.service="resourcesService?deleteResourcesAjax";
		  mask.showLoading();
		http.callService(data).success(function(d){
			mask.hideLoading();
			if(d.isTrue){
				dtree(scope.thisNode.id);
				scope.queryGridContent();
				timeAlert.success('删除成功');
			}else{
				timeAlert.error(d.name);
			}
	    });
		}
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
		  mask.showLoading();
  		  http.callService(data).success(function(data){
			  vm.items=data.resultListObject;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
	      }); 
	  };
	  //查询
	  scope.getResourceByInput= function(){
		  scope.queryGridContent();
	  };
	  getRootPrev=function(dt){
		  var a=getqxbzf(scope.treeData,dt.pid)
		  if(a==null){
			  return dt;
		  }
		  if(a.pid!=0)a=getRootPrev(a);
		  return a;
	  }
	  getqxbzf=function(data,id){
		  var a=null;
		  if(data.id==id){
			  a= data;
		  }else if(data.children){
			  for(var i=0;i<data.children.length;i++){
				  a=  getqxbzf(data.children[i],id);
				  if(a!=null)break;
			  }
		  }
		  return a;
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
			 
			  mask.showLoading();
			http.callService(data).success(function(d){
				scope.parentres=d;
				if(d.resource_type_code=='07'){
					scope.selResDl=d;
				}
				mask.hideLoading();
		    });
			initId++;
	  },true);
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
	  },true);
	  var initId=0;
	  scope.$watch('selResResult',function(val1,val2){
		  if(initId>0)
		  if(val1.pid>0){
			  var data={};
			  data.params=[];
			  data.params.push(val1.id);
			  data.service="resourcesService?findById";
			  mask.showLoading();
				http.callService(data).success(function(d){
					 scope.resource=d;
					 scope.resourceshiro_tag=function(){
						 var arr=d.shiro_tag.split(":");
						 return arr[arr.length-1];
					 }();
					 scope.resourceisShow=d.isShow==1?true:false;
						 scope.resourceistrue=d.istrue==1?true:false;
						if(scope.resource.url_.length>0){
							var getPreXt=function(treeData,id,xtid){
								  if(treeData.id==id){
									  scope.prextid=xtid;
									  return;
								  }else if(treeData.children){
									  for(var i=0;i<treeData.children.length;i++){
										  xtid=treeData.pid=='0'?treeData.id:xtid
											getPreXt(treeData.children[i],id,xtid);
									  }
								  }
							}
						getPreXt(scope.treeData,scope.resource.id,null);
						data={};
						  data.params=[];
						  data.params.push(scope.prextid);
						  data.service="resourcesService?findById";
						  mask.showLoading();
						http.callService(data).success(function(d){
							scope.resource.url_=d.url_+scope.resource.url_;
							mask.hideLoading();
						 });
						}
					 mask.hideLoading();
			    });
		  }else{
			  val1=null;
			  timeAlert.error("请选择系统子资源！"); 
		  }
	  },true);
}]);