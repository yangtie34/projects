app.controller("AdminsController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 http.callService({
	 	  service:'payPowerService?getxldm',
	 	  params:[]
	 }).success(function(data){
	 	scope.gzbz=data.gzbz;
	 });
	var vm = scope.vm = {};
	  vm.items = [];
	  scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 5,
			  conditions : []
	 	};
	  var htt=[];
	  var httservice="equipManagerService?"; 
	  var methods=[	
	        	'getManagers',	//0获取管理者数量
	        	'getManagersByDept',		//1管理者数量分部门对比
	        	'getContrastByType',	//2仪器设备管理者分类型对比
	        	'getContrastTrendByType',			//3仪器设备管理者分类型对比趋势
	        	'getManagersByDept',		//4过期在用的仪器设备分使用者部门对比
	        	'getCountByUseDept',		//5过期在用的仪器设备分使用者部门对比
	        	'getEmTop',		//6获取排名情况
	        	'',				//7
	        	//xiazuan
	        	'getEmDetil',				//8 获取仪器设备列表
	        	'getEmDetilByType',			//9
	        	'getEmDetilByDeptGroup',	//10
	        	'getEmDetilByDeptId',		//11
	        	'getEmDetilByManagerId',	//12
	        	'getManager',				//13获取管理者列表
	        	'getManagerByType',			//14
	        	'getManagerByDeptGroup',	//15
	        	'getManagerByDeptId',		//16
	               ];
	//下钻
	  scope.isOT=false;
	  scope.getxqlb=function(i){
	  	var params=[];
	  	var title='';var titles='';var titlesCode='';
	  	if(i<13){
	  		titlesCode=em_detil.code;
	  		titles=em_detil.name;
	  	}else{
	  		titlesCode=em_manager_detil.code;
	  		titles=em_manager_detil.name;
	  	}
  		if(i==8){
  			title="仪器设备";
  		}else if(i==9){
	  		params=[scope.type,scope.typeValue];
	  		title="仪器设备";
	  	}else if(i==10){
	  		params=[scope.deptGroup];
	  		title="仪器设备";
	  	}else if(i==11){
	  		params=[scope.DeptGroup,scope.deptId];
	  		title="仪器设备";
	  	}else if(i==12){
	  		params=[scope.managerId];
	  		title="仪器设备";
	  	}else if(i==13){
	  		scope.emType='all';
	  		params=[scope.deptGroup];
	  		title="管理者";
	  	}else if(i==14){
	  		params=[scope.deptGroup,scope.type,scope.typeValue];
	  		title="管理者";
	  	}else if(i==15){
	  		params=[scope.deptGroup];
	  		title="管理者";
	  	}else if(i==16){
	  		params=[scope.deptGroup,scope.deptId];
	  		title="管理者";
	  	}
	  	params=[scope.isOT,scope.emType].concat(params);
	  	titles=titles.split(',');
	  	titlesCode=titlesCode.split(',');
	  	var query=function(pg){
	  		htt[i].params=[pg.currentPage || 1,
	  				          pg.numPerPage || 10,  pg.totalRows||0,pg.sort,pg.isAsc
	  				          ];
	  		htt[i].params = htt[i].params.concat(params);  
	  	 mask.showLoading();
		 if(pg.exportExcel){
			 var invoke=angular.copy(htt[i]);
			 invoke.params[0]=1;
			 invoke.params[1]=pg.totalRows;
			 return{
				 invoke:invoke,
				 title:title,
				 titles:titles,
				 titleCodes:titlesCode
			 }
		 }
	  	  http.callService(htt[i]).success(function(d){
	  		  d.title=title+'详情列表';
	  		  d.titles=titles;
	  		  d.titlesCode=titlesCode;
	  		  d.func=query;
	  		  scope.pagexq=angular.copy(d);
	  	  });
	  	};
	  	scope.pagexq={func:query};
	  }	 
		scope.getxq=function(code){
			scope.deptGroup=code||'';
			scope.getxqlb(13);
		}
		scope.deptGroup='all';
		scope.getxqById=function(item){
			scope.emType=angular.copy(scope.rankType);
			if(scope.queryType=='dept'){
				scope.deptId=item.DEPT_ID;	
				scope.getxqlb(11);
			}else{
				scope.managerId=item.TEA_ID;	
				scope.getxqlb(12);
			}
		}
		scope.getGzxqById=function(item){
			scope.emType=rankType[1];
			if(scope.queryType=='dept'){
				scope.deptId=item.DEPT_ID;	
				scope.getxqlb(11);
			}else{
				scope.managerId=item.TEA_ID;	
				scope.getxqlb(12);
			}	
		}
	scope.alltitle='';  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  if(i>7)httservice='equipmentPageService?';
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
getServiceData();
scope.emType='all';
var type=['edu','zc'];
var rankType=['all','val','money'];

var queryType=['manager','dept'];
scope.queryTypeClick=function(i){
	scope.queryType=queryType[i];
	scope.queryTypeindex=i;
	 getparams(6);
}
scope.queryType='manager';
scope.queryTypeindex=0;

scope.qushiClick=function(index){
	scope.qushiData=angular.copy(vm.items[3][index]);
	scope.qsDiv=true;
}
scope.cursordiv=function(item){
	scope.DeptGroup=item.CODE;
	scope.alltitle=item.NAME;
	getAllData(m2); getparams(6);
	scope.radio0id=null;
}

var getvmData=function(i,k){
	http.callService(htt[i]).success(function(data){
		 if(i==2){//饼状图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:'人次(人)'}); 
			 }
			 var option=getOption(d,'','bzt');
				option.event=function(param){
					scope.type=type[k];
					scope.emType='all';
					scope.deptGroup=angular.copy(scope.DeptGroup);
					scope.typeValue=param.data.nameCode
					 scope.getxqlb(14);
					 timeout();
				 };
				 vm.items[i][k]=option;
			 //vm.items[i][k]=getOption(d,'','bzt');; 
		 }else if(i==3){//趋势图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].YEAR_,value:data[j].VALUE,name:data[j].NAME}); 
			 }
			 vm.items[i][k]=fomatSwtDw(getOption(d,(vm.items[i]==0?'分类型':'按职称')+'对比趋势','xzt'),'人次','人'); 
		 }else if(i==4){//柱状图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].USERS,name:'人次(人)'}); 
			 }
			 var option=getOption(d,'','zzt');
			 option.event=function(param){
				 scope.emType=angular.copy(scope.DeptGroup);
					scope.deptGroup=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
					scope.getxqlb(15);
					 timeout();
				 };
			 vm.items[i]=option;
			 scope.radio0id='00';
		 }else if(i==5){//柱状图
			 var d=[[],[],[]];
			 for(var j=0;j<data.length;j++){
				 d[0].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].NUMS,name:'设备数量(件)'}); 
				 d[1].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].MONEYS,name:'价值(万元)'}); 
				 d[2].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].AVGSTU,name:'生均价值(元)'}); 
			 } 
			// scope.vmitems5titles=['分类型对比藏书数量','分类型对比藏书价值','分类型对比藏书文献保障率'];
			 var event=function(option){
				 option.event=function(param){
					 	scope.deptGroup=angular.copy(scope.DeptGroup);
						scope.deptId=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
						scope.getxqlb(11);
						 timeout();
					 };
					 return option;
			 }
			 vm.items[i]=[event(getOption(d[0],'','zzt')),
			              	event(getOption(d[1],'','zzt')),
							 event(getOption(d[2],'','zzt'))];
			 //scope.radio0id=0;
		 }else if(i==6){
			  vm.items[i]=data.resultList;
			  scope.page.totalRows=data.totalRows;
  			  scope.page.totalPages=data.totalPages;
		 }else{
			 vm.items[i]=data; 
		 }
		 mask.hideLoading(); 
	  });
	
};
scope.deptId="all";
scope.DeptGroup="all";
scope.rank=10;
var getparams=function(i){
	mask.showLoading();
		 var params=[];
		 if(i==2||i==3){
			 vm.items[i]=[];
			 params=[type[0],
			         scope.DeptGroup];
			 htt[i].params=params;
			 getvmData(i,0); 
			 params=[type[1],
			         scope.DeptGroup];
			 htt[i].params=params;
			 getvmData(i,1); 
			 return;
		 }else if(i==4||i==5){
			 params=[scope.deptId,
			         scope.DeptGroup];
		 }else if(i==6){
			 params=[scope.page.currentPage || 1,
			         scope.page.numPerPage || 10,0,
			         scope.rankType,
			         scope.queryType,
			         scope.DeptGroup,scope.rank];
		 }
		 htt[i].params=params;
		 getvmData(i,null);
};
var getAllData=function(m){
	for(var i=0;i<m.length;i++){
		 mask.showLoading();
		getparams(m[i]);
	}
};
var m1=[0,1];
var m2=[2,3,4,5];
getAllData(m1);
getAllData(m2);
scope.$watch('page.currentPage',function(val1,val2){
	if(val1!=val2)getparams(6);
})
scope.$watch('page.numPerPage',function(val1,val2){
	if(val1!=val2)getparams(6);
})
scope.$watch('radio0id',function(val1,val2){
	if(val1!=null){
		scope.radio0data=angular.copy(val1=='00'?vm.items[4]:vm.items[5][Number(scope.radio0id)]);
	}
},true);
scope.$watch('radio1id',function(val1,val2){
	if(val1!=null){
		scope.rankType=rankType[Number(scope.radio1id)];
		 getparams(6);
	}
},true);
scope.radio1id=0;
}]);