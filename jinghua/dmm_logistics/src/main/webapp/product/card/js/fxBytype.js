app.controller("fxBytypeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	mask.showLoading();
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="habitStuTypeService?"; //分学生类型消费习惯分析
	  var methods=[	
	        	'getHabitZao',		//0整体早中晚
	        	'getHabitZaoBySex',	//1分性别早中晚
	        	'getHabitZaoByEdu',		//2分学历早中晚
	        	'getHabitHour',			//3整体时段情况
	        	'getHabitHourBySex',	//4分性别时段情况
	        	'getHabitHourByEdu',	//5分学历时段情况
	        	'getHabitEat',			//6整体刷卡用餐次数
	        	'getHabitEatBySex',		//7分性别刷卡用餐次数
	        	'getHabitEatByEdu',		//8分学历刷卡用餐次数
	        	
	        	'getHabitZaoByArea',	//9分地区早中晚
	        	'getHabitHourByArea',	//10分地区时段情况
	        	'getHabitEatByArea',		//11分地区刷卡用餐次数
	        	
	        	'getHabitZaoByMZ',	//12分地区早中晚
	        	'getHabitHourByMZ',	//13分地区时段情况
	        	'getHabitEatByMZ'		//14分地区刷卡用餐次数
	               ];
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
var xldm={};
http.callService({
	  service:'payPowerService?getxldm',
	  params:[]
}).success(function(data){
	xldm=data;
	var xl={name:'学历'};
	xl[xldm.yjs]={ico:'11',name:'研究生'};
	xl[xldm.bk]={ico:'21',name:'本科'};
	xl[xldm.dz]={ico:'31',name:'大专'};
	var dq={name:'籍贯'};
//	dq[xldm.dqcode]={ico:'minority',name:xldm.dqname};
//	dq['other']={ico:'wwhz',name:'其他地区'};
	
	scope.ico_title={all:{all:{ico:'all',name:'总体'},name:'总体'},
			xb:{1:{ico:'man',name:'男'},2:{ico:'female',name:'女'},name:'性别'},
			xl:xl,
			dq:dq,
			mz:{name:'民族'},
};
});
scope.upDownClick=function(key){
	scope.upDown[key]=!scope.upDown[key];
	if(scope.upDown[key]==true){
		var a=angular.copy(vm.items[0][key]);
		vm.items[0][key]=null;
		vm.items[0][key]=a;
	}
};
getServiceData();
var getDeptData=function(method){
	http.callService({
		  service:'deptTreeService?get'+method,
		  params:['hq:card:fxByType:*']//:'+method+':*']
	  }).success(function(data){
		var getChildren=function(data){
			var item={};
			item.id=data.id;
			item.mc=data.name_;
			item.level=data.level_;
			item.istrue=data.istrue;
			if(data.children!=null){
				item.children=[];
				for(var i=0;i<data.children.length;i++){
					item.children.push(getChildren(data.children[i]));
				}
			}
			return item;
		};
		scope.mutiSource=null;
		var mutisource=[];
		var setCheck=function(item){
			if(item.level!=null)
			item.checked=true;
			return item;
		}
		mutisource.push({
			queryName : '所属部门',
			queryCode : "comboTree",
			queryType : "comboTree",
			items : setCheck(getChildren(data))
		});
		scope.mutiSource=mutisource;
	})
	};
	getDeptData("DeptTeach");
	scope.csjecode='cs';
	scope.csje=function(code){
		scope.csjecode=code;
	};
	var initvm=function(){
		scope.dqmz={};
vm.items[0]={
		all:angular.copy(scope.ico_title.all),
		xb:angular.copy(scope.ico_title.xb),
		xl:angular.copy(scope.ico_title.xl),
		dq:angular.copy(scope.ico_title.dq),
		mz:angular.copy(scope.ico_title.mz)
		};
		};
		scope.dqmz={};
	var getvmData=function(i){
		http.callService(htt[i]).success(function(data){
			 if(i==0){
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d[1].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_MONEY,name:'金额(元)'}); 
				 }
				 vm.items[0].all.all.zzw={cs:getOption(d[0],'','bztwz').saveAsImage("整体早中晚刷卡次数情况")
						 	,je:getOption(d[1],'','bztwz').saveAsImage("整体早中晚刷卡金额情况")};
			 }else if(i==1||i==2||i==9||i==12){
				 var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].TYPE_CODE])mapData[data[j].TYPE_CODE]=[];
					 mapData[data[j].TYPE_CODE].push(data[j]);
				 }
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[[],[]];
					 for(var j=0;j<keyData.length;j++){
						 d[0].push({field:keyData[j].NAME,fieldCode:keyData[j].CODE,value:keyData[j].ALL_COUNT,name:'次数(次)'}); 
						 d[1].push({field:keyData[j].NAME,fieldCode:keyData[j].CODE,value:keyData[j].ALL_MONEY,name:'金额(元)'}); 
					 }
					 var keyvmd=vm.items[0][i==1?'xb':i==2?'xl':i==9?'dq':'mz'][key];
					 keyvmd=keyvmd||{name:keyData[0].TYPE_NAME};
					 keyvmd.zzw={cs:getOption(d[0],'','bztwz').saveAsImage(keyvmd.name+"早中晚刷卡金额情况"),
								je:getOption(d[1],'','bztwz').saveAsImage(keyvmd.name+"早中晚刷卡金额情况")
										};
					 vm.items[0][i==1?'xb':i==2?'xl':i==9?'dq':'mz'][key]=keyvmd;
				 }
				 if(i==9){
					 scope.dqmz.dq=[];
					 for(var key in mapData){
						 if(scope.dqmz.dq.length<2){
							 scope.dqmz.dq.push(key);
						 }else{
							 break;
						 }
					 }
				 }else if(i==12){
					 scope.dqmz.mz=[];
					 for(var key in mapData){
						 if(scope.dqmz.mz.length<2){
							 scope.dqmz.mz.push(key);
						 }else{
							 break;
						 }
					 }
				 }
			 }else if(i==3){
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].HOUR_,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d[1].push({field:data[j].HOUR_,value:data[j].ALL_MONEY,name:'金额(元)'}); 
				 }
				 vm.items[0].all.all.fsd={cs:getOption(d[0],'','xqs').saveAsImage("整体分时段统计刷卡次数"),je:getOption(d[1],'','xqs').saveAsImage("整体分时段统计刷卡金额")};
			}else if(i==4||i==5||i==10||i==13){
				var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].TYPE_CODE])mapData[data[j].TYPE_CODE]=[];
					 mapData[data[j].TYPE_CODE].push(data[j]);
				 }
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[[],[]];
					 for(var j=0;j<keyData.length;j++){
						 d[0].push({field:keyData[j].HOUR_,value:keyData[j].ALL_COUNT,name:'次数(次)'}); 
						 d[1].push({field:keyData[j].HOUR_,value:keyData[j].ALL_MONEY,name:'金额(元)'}); 
					 }
					 var keyvmd= vm.items[0][i==4?'xb':i==5?'xl':i==10?'dq':'mz'][key];
					 keyvmd=keyvmd||{name:keyData[0].TYPE_NAME};
					 keyvmd.fsd
					 ={cs:getOption(d[0],'','xqs').saveAsImage(keyvmd.name+"分时段统计刷卡次数"),
						je:getOption(d[1],'','xqs').saveAsImage(keyvmd.name+"分时段统计刷卡金额")};
					 vm.items[0][i==4?'xb':i==5?'xl':i==10?'dq':'mz'][key]=keyvmd;
				 }
			}else if(i==6){
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,value:data[j].VALUE,name:'人数(人)'}); 
				 }
				 vm.items[0].all.all.yczc=falsedataZoom(getOption(d,'','zzt').saveAsImage("整体刷卡就餐人次组成"));
			}else if(i==7||i==8||i==11||i==14){
				var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].TYPE_CODE])mapData[data[j].TYPE_CODE]=[];
					 mapData[data[j].TYPE_CODE].push(data[j]);
				 }
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[];
					 for(var j=0;j<keyData.length;j++){
						 d.push({field:keyData[j].NAME,value:keyData[j].VALUE,name:'人次(次)'}); 
					 }
					 var keyvmd= vm.items[0][i==7?'xb':i==8?'xl':i==11?'dq':'mz'][key];
					 keyvmd=keyvmd||{name:keyData[0].TYPE_NAME};
					 keyvmd.yczc=falsedataZoom(getOption(d,'','zzt').saveAsImage(keyvmd.name+"刷卡就餐人次组成"));
					 vm.items[0][i==7?'xb':i==8?'xl':i==11?'dq':'mz'][key]=keyvmd;
				 }
			}
			 mask.hideLoading(); 
		  });
		
	};
	var falsedataZoom=function(option){
		option.dataZoom.show=false;
		return option;
	}
	var getparams=function(i){
			 var params=[];
			 if(i<15){
				 params=[startDate,endDate,deptTeach];
			 }
			 htt[i].params=params;
			 getvmData(i);
	};
	var getAllData=function(m){
		for(var i=0;i<m.length;i++){
			 mask.showLoading();
			getparams(m[i]);
		}
		
	};
	var m=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return;
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0){
			initvm();getAllData(m);
		}

	},true);
	/*监控dept*/
	scope.$watch('deptResult',function(val1,val2){
		if(val1==null||val1.length==0)return;
			 deptTeach=val1[0];
			if(initId>0){
				initvm();getAllData(m);
			}
	},true);
	//初始化数据
	var initData=function(){
		if(initId==0&&startDate!=null&&deptTeach!=null&&scope.ico_title!=null){
			initvm();getAllData(m);initId++;mask.hideLoading(); 
		}else{
			setTimeout(initData,100);
		}
	};
	initData(); 
}])