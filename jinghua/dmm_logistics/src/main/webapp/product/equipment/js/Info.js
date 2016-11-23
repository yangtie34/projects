app.controller("InfoController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 http.callService({
	 	  service:'payPowerService?getxldm',
	 	  params:[]
	 }).success(function(data){
	 	scope.gzbz=data.gzbz;
	 });
	 var vm = scope.vm = {};
	  vm.items = [[],[],[],[]];
	  var htt=[];
	  scope.titleFB='在用';
	  var httservice="equipmentInfoService?"; 
	  var methods=[	
	        	'getCount',					//0获取仪器设备信息
	        	'getContrastByType',		//1仪器设备分类型对比
	        	'getContrastTrendByType',	//2仪器设备分类型对比趋势
	        	'getEquipmentTrend',		//3获取仪器设备数量与价值以及增长数的趋势
	        	
	        	'',//4
	        	'',//5
	        	'',//6
	        	'',//7
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
	        	'getEmDetilByYear',			//17
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
	  		params=[Number(scope.radio1id)==0?false:true,scope.year];
	  		title=scope.year+"年仪器设备"+(Number(scope.radio1id)==0?"":"增长");
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
var emType=['all','val','now','nowVal'];
var type=['emType','moneyFrom','moneyCount'];
scope.emTypeId=0;
scope.emTypeClick=function(i){
	scope.emTypeId=i;
	scope.emType=emType[i];
	getData2();
}
scope.qushiClick=function(index){
	scope.qushiData=angular.copy(vm.items[2][index]);
	scope.qsDiv=true;
} 
var getData1=function(){
	mask.showLoading();
	var getd=function(i,k){
		 http.callService(htt[i]).success(function(data){
			 if(i==0){
					 vm.items[i][k]=data;  
			 }else if(i==3){
				  var d=[[],[]];
					 for(var j=0;j<data.length;j++){
						 d[0].push({field:data[j].YEAR_,value:data[j].NUMS,name:'设备数量(件)'}); 
						 d[0].push({field:data[j].YEAR_,value:data[j].MONEYS,name:'设备价值(万元)'});
						 d[1].push({field:data[j].YEAR_,value:data[j].UPNUMS,name:'增长设备数量(件)'}); 
						 d[1].push({field:data[j].YEAR_,value:data[j].UPMONEYS,name:'增长设备价值(万元)'});
					 } 
					 vm.items[i]=[];
					 var event=function(option){
						 option.event=function(param){
							 scope.year=option.xAxis[0].data[param.dataIndex];
								 scope.getxqlb(17);
								 timeout();
							 }; 
							 return option;
					 }
					
					for(var j=0;j<d.length;j++) {
						vm.items[i].push(event(getOption(d[j],'','zxt')));
					}
					scope.radio1id=0;
			 }
			 mask.hideLoading(); 
		 });	
	}
	 for(var i=0;i<emType.length;i++){
		 htt[0].params=[emType[i]];
		 getd(0,i); 
	 }
		 getd(3,null);
}
var qstitle=['类型','经费组成','单价'];
var getData2=function(){
	mask.showLoading();
	var getd=function(i){
		  htt[1].params=[type[i],scope.emType];
		  http.callService(htt[1]).success(function(data){
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:i==1?'经费(万元)':'数量(件)'}); 
				 }
				 var option=getOption(d,'','bzt');
				option.event=function(param){
					scope.type=type[i];
					scope.emType=emType[scope.emTypeId];
					scope.typeValue=param.data.nameCode
					 scope.getxqlb(9);
					 timeout();
				 };
				 vm.items[1][i]=option;
				// vm.items[1][i]=getOption(d,'','bzt'); 
				 mask.hideLoading(); 
		  });
		  htt[2].params=[type[i],scope.emType];
		  http.callService(htt[2]).success(function(data){
				 var d=[];
				 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].YEAR_,value:data[j].VALUE,name:data[j].NAME});  
				 }
					 vm.items[2][i]=fomatSwtDw(getOption(d,'仪器设备'+qstitle[i]+'对比趋势','xzt'),i==1?'经费':'数量',i==1?'万元':'件');  
					 mask.hideLoading(); 
		  });
	}
	 for(var i=0;i<type.length;i++){
		 getd(i); 
	 }
};
getServiceData();
getData1();
scope.emTypeClick(0);
scope.$watch('radio1id',function(val1,val2){
	if(val1!=null){
		scope.radio1data=angular.copy(vm.items[3][Number(scope.radio1id)]);
	}
},true);

}]);