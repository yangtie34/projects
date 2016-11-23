app.controller("OverdueController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 http.callService({
	 	  service:'payPowerService?getxldm',
	 	  params:[]
	 }).success(function(data){
	 	scope.gzbz=data.gzbz;
	 });
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="equipOutTimeService?"; 
	  var methods=[	
	        	'getCount',					//0获取过期在用的仪器设备信息
	        	'getContrastByType',		//1过期在用的仪器设备分类型对比
	        	'getContrastTrendByType',	//2过期在用的仪器设备分类型对比趋势
	        	'getCountByUseDept',		//3过期在用的仪器设备分使用者部门对比
	        	'getCountByUseDept',		//4过期在用的仪器设备分使用者部门对比
	        	'getWillOutTime',			//5仪器设备将要过期情况
	        	'',				//6
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
	        	'getEmDetilByLastYear',			//17
	               ];
	//下钻
	  scope.isOT=true;
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
	  		params=[scope.deptGroup,scope.deptId];
	  		title="仪器设备";
	  	}else if(i==12){
	  		params=[scope.managerId];
	  		title="仪器设备";
	  	}else if(i==13){
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
	  	}else if(i==17){
	  		titlesCode=em_detil.code;
	  		titles=em_detil.name;
	  		params=[scope.year];
	  		title="距离"+scope.yearName+"过期仪器设备";
	  	}
	  	params=[scope.isOT,scope.emType].concat(params);
	  	titles=titles.split(',');
	  	titlesCode=titlesCode.split(',');
	  	var query=function(pg){
	  		htt[i].params=[pg.currentPage || 1,
	  				          pg.numPerPage || 10,  pg.totalRows||0
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
		scope.getxq=function(i){
			scope.emType=emType[i];
			scope.getxqlb(8);
		}   
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

var getvmData=function(i,k){
	http.callService(htt[i]).success(function(data){
		if(i==0){
			 vm.items[i][k]=data; 
		 }else if(i==1){//饼状图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:k==1?'经费(万元)':'数量(件)'}); 
			 }
			 var option=getOption(d,'','bzt');
				option.event=function(param){
					scope.type=type[k];
					scope.emType=emType[scope.emTypeIndex];
					scope.typeValue=param.data.nameCode
					 scope.getxqlb(9);
					 timeout();
				 };
				 vm.items[i][k]=option;
			 //vm.items[i][k]=getOption(d,'','bzt');; 
		 }else if(i==2){//趋势图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].YEAR_,value:data[j].VALUE,name:data[j].NAME}); 
			 }
			 vm.items[i][k]=fomatSwtDw(getOption(d,'过期在用设备'+(k==0?'分类型':k==1?'经费':'单价')+'对比趋势','xzt'),k==1?'经费':'数量',k==1?'万元':'件'); 
		 }else if(i==4){//柱状图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].NUMS,name:'设备数量(件)'}); 
				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].MONEYS,name:'设备价值(万元)'});
			 } 
			 var option=getOption(d,'','zxt');
			 option.event=function(param){
				 scope.emType=emType[scope.emTypeIndex];
				 scope.deptGroup=angular.copy(scope.DeptGroup);
					scope.deptId=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
					scope.getxqlb(11);
					 timeout();
				 };
			 vm.items[i]=option;
		 }else if(i==5){//柱状图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].YEAR_,value:data[j].VALUE,name:'数量(件)'}); 
			 }
			 var option=getOption(d,'','zzt');
			 option.event=function(param){
				 scope.yearName=option.xAxis[0].data[param.dataIndex];
				 if(scope.yearName.length>2){
					 scope.year=10;//scope.yearName.substr(0, 2); 
				 }else{
					 scope.year=scope.yearName.substr(0, 1);  
				 }
					 scope.getxqlb(17);
					 timeout();
				 }; 
				 vm.items[i]=option; 
		 }else if(i==3){
			  vm.items[i]=data;
			 scope.DeptGroupClick(data[0]);
		 } 
		 mask.hideLoading(); 
	  });
	
};
var emType=['all','val','now'];
var type=['emType','moneyFrom','moneyCount'];
scope.deptId="all";
scope.qushiClick=function(index){
	scope.qushiData=angular.copy(vm.items[2][index]);
	scope.qsDiv=true;
} 
scope.DeptGroupClick=function(item){
	scope.jgmc=item.NAME;
	scope.DeptGroup=item.CODE||'';
	getparams(4);
}
scope.getEmDetilByDeptGroup=function(deptGroup){
	scope.emType=emType[scope.emTypeIndex];
	scope.deptGroup=deptGroup;
	scope.getxqlb(10);
}
scope.DeptGroup="all";
scope.alltitle='在用';
scope.emTypeClick=function(i){
		scope.alltitle=i==0?'在用':'贵重';
	scope.emType=emType[i];
	scope.emTypeIndex=i;
	getAllData([1,2,3,4,5]);
}
scope.emType=emType[0];
scope.emTypeIndex=0;
var getparams=function(i){
	mask.showLoading();
		 var params=[];
		 if(i==0){
			 vm.items[i]=[];
			 params=[emType[0]];
			 htt[i].params=params;
			 getvmData(i,0); 
			 params=[emType[1]];
			 htt[i].params=params;
			 getvmData(i,1); 
//			 params=[emType[2]];
//			 htt[i].params=params;
//			 getvmData(i,2); 
		 }else if(i==1||i==2){
			 vm.items[i]=[];
			 params=[type[0],scope.emType];
			 htt[i].params=params;
			 getvmData(i,0); 
			 params=[type[1],scope.emType];
			 htt[i].params=params;
			 getvmData(i,1); 
			 params=[type[2],scope.emType];
			 htt[i].params=params;
			 getvmData(i,2); 
		 }else if(i==3||i==5){
			 params=[scope.emType];
			 htt[i].params=params;
			 getvmData(i,null);
		 }else if(i==4){
			 params=[scope.deptId,scope.DeptGroup,scope.emType];
			 htt[i].params=params;
			 getvmData(i,null);
		 }
		
};
var getAllData=function(m){
	for(var i=0;i<m.length;i++){
		 mask.showLoading();
		getparams(m[i]);
	}
};
var m=[0,1,2,3,5];
getAllData(m);
}]);

