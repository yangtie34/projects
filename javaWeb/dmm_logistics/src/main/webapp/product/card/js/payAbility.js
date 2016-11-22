app.controller("payAbilityController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 mask.showLoading();
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="payPowerService?"; //学生消费能力分析
	  var methods=[	
	        	'getPower',			//0整体消费能力
	        	'getPowerBySex',	//1分性别消费能力
	        	'getPowerByEdu',	//2分学历消费能力
	        	'getPowerByDept',	//3分学院消费能力
	        	'getPayType',		//4整体消费组成
	        	'getPayTypeBySex',	//5分性别消费组成
	        	'getPayTypeByEdu',	//6分学历消费组成
	        	'getPayRegion',		//7整体消费区间
	        	'getPayRegionBySex',//8分性别消费区间
	        	'getPayRegionByEdu',//9分学历消费区间
	        	'cardTrendService?getPayYearTrend',	//10分学历消费区间
	               ];
	  scope.qushiClick=function(type_code,flag,lx){
		  flag=flag=='all'?'pay_all':flag=='xb'?'pay_sex':'pay_edu';
		  http.callService({
			  service:'cardTrendService?getStuPayTrend',
			  params:[deptTeach,type_code,flag]
		  }).success(function(data){
			  var d=[];
				 for(var j=0;j<data.length;j++){
					// d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:"总次数(次)"}); 
					if(lx=='all') d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_MONEY,name:'总金额(元)'}); 
					if(lx=='bj') d.push({field:data[j].YEAR_MONTH,value:data[j].ONE_MONEY,name:'笔均消费金额(元)'}); 
					if(lx=='rj') d.push({field:data[j].YEAR_MONTH,value:data[j].DAY_MONEY,name:'日均消费金额(元)'}); 
				 }
				 scope.qushiData=getOption(d,'分类型消费趋势统计','xzt');
				 scope.qsDiv=true;
		  });
	  }
/*	  scope.qushiClick=function(index){
			scope.qushiData=angular.copy(vm.items[index]);
			scope.qsDiv=true;
		}  */
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
		  if(i==10)htt[i].service=htt[i].service.replace(httservice,"");
	 }
};
getServiceData();
var xldm={};
http.callService({
	  service:'payPowerService?getxldm',
	  params:[]
}).success(function(data){
	xldm=data;
	scope.xlxsids=[xldm.bk,xldm.dz];//////学历只显示大专本科
	scope.k_xlxsids=function(k){
		if(scope.xbxlcode=='xb'){
			return true;
		}
		if(k=='all')return true;
		for(var i=0;i<scope.xlxsids.length;i++){
			if(k==scope.xlxsids[i])return true
		}
		return false;
	};
	var xl={name:'学历'};
	xl[xldm.yjs]={ico:'11',name:'研究生'};
	xl[xldm.bk]={ico:'21',name:'本科'};
	xl[xldm.dz]={ico:'31',name:'大专'};
	scope.ico_title={all:{all:{ico:'all',name:'整体'},name:'整体'},
			xb:{1:{ico:'man',name:'男生'},2:{ico:'female',name:'女生'},name:'性别'},
			xl:xl
};
});
var getDeptData=function(method){
	http.callService({
		  service:'deptTreeService?get'+method,
		  params:['hq:card:payAbility:*']//:'+method+':*']
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
	var initvm=function(){vm.items[0]={all:{all:{}},xb:{},xl:{}};vm.items[3]=null};
	scope.xbxlcode='xb';
	scope.xbxl=function(code){
		scope.xbxlcode=code;
		var xl=angular.copy(vm.items[0][code]);
		vm.items[0][code]={};
		vm.items[0][code]=xl;
	};
	var getvmData=function(i){
		http.callService(htt[i]).success(function(data){
			 if(i==0){
				 vm.items[0].all.all.xfnl=data[0];	 
			 }else if(i==1||i==2){
				 for(var j=0;j<data.length;j++){
					 vm.items[0][i==1?'xb':'xl'][data[j].TYPE_CODE]=
						 vm.items[0][i==1?'xb':'xl'][data[j].TYPE_CODE]||{};
					 vm.items[0][i==1?'xb':'xl'][data[j].TYPE_CODE].xfnl=data[j];	 	 
				 }
			 }else if(i==3){
				 var d=[[],[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].TYPE_NAME,value:data[j].ALL_MONEY,name:'总金额(元)',fieldCode:data[j].TYPE_CODE}); 
					 d[1].push({field:data[j].TYPE_NAME,value:data[j].DAY_MONEY,name:'日均金额(元)',fieldCode:data[j].TYPE_CODE}); 
					 d[2].push({field:data[j].TYPE_NAME,value:data[j].ONE_MONEY,name:'单笔金额(元)',fieldCode:data[j].TYPE_CODE}); 
				 } 
				// scope.vmitems5titles=['分类型对比藏书数量','分类型对比藏书价值','分类型对比藏书文献保障率'];
				 vm.items[i]=[];
				 for(var k=0;k<d.length;k++){
					 var option=getOption(d[k],'','zzt');
					 vm.items[i][k]=option;	 
				 }
				 scope.radio1id=0;
			 }else if(i==4){
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_MONEY,name:'金额(元)'}); 
				 }
				 vm.items[0].all.all.xfzc=getOption(d,'','bztwz');
			 }else if(i==5||i==6){
				 var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].TYPE_CODE])mapData[data[j].TYPE_CODE]=[];
					 mapData[data[j].TYPE_CODE].push(data[j]);
				 }
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[];
					 for(var j=0;j<keyData.length;j++){
						 d.push({field:keyData[j].NAME,fieldCode:keyData[j].CODE,value:keyData[j].ALL_MONEY,name:'金额(元)'}); 
					 }
					 vm.items[0][i==5?'xb':'xl'][key]=vm.items[0][i==5?'xb':'xl'][key]||{};
					 vm.items[0][i==5?'xb':'xl'][key].xfzc=getOption(d,'','bztwz');
				 }
			 }else if(i==7){
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,value:data[j].ALL_STU,name:'总人数(人)'}); 
				 }
				 vm.items[0].all.all.xfqj=getOption(d,'','zzt');
			 }else if(i==8||i==9){
				 var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].TYPE_CODE])mapData[data[j].TYPE_CODE]=[];
					 mapData[data[j].TYPE_CODE].push(data[j]);
				 }
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[];
					 for(var j=0;j<keyData.length;j++){
						 d.push({field:keyData[j].NAME,value:keyData[j].ALL_STU,name:'总人数(人)'}); 
					 }
					 vm.items[0][i==8?'xb':'xl'][key]=vm.items[0][i==8?'xb':'xl'][key]||{};
					 vm.items[0][i==8?'xb':'xl'][key].xfqj=getOption(d,'','zzt');
				 }
			 }else if(i==10){
			 
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].YEAR,value:data[j].USE_PEOPLE,name:"持卡人数(人)"}); 
					 d.push({field:data[j].YEAR,value:data[j].YEAR_MONEY,name:'人均年消费(元)'}); 
					//d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_MONEY,name:'总金额(元)'}); 
					 
				 }
				 vm.items[i]=getOption(d,'','zxt');
			 }
			 mask.hideLoading(); 
		  });
		
	};
	var getparams=function(i){
			 var params=[];
			 if(i<10){
				 params=[startDate,endDate,deptTeach];
			 }else if(i=10){
				 params=[deptTeach];
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
	var m=[0,1,2,3,4,5,6,7,8,9,10];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return;initvm();
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0)getAllData([0,1,2,3,4,5,6,7,8,9]);
	},true);
	/*监控dept*/
	scope.$watch('deptResult',function(val1,val2){
		if(val1==null||val1.length==0)return;initvm();
			 deptTeach=val1[0];
			 vm.items[3]=null;
			if(initId>0)getAllData(m);
	},true);
	scope.$watch('radio1id',function(val1,val2){
		if(val1!=null){
			scope.radio1data=angular.copy(vm.items[3][Number(scope.radio1id)]);
		}
	},true);
	//初始化数据
	var initData=function(){initvm();
		if(initId==0&&startDate!=null&&deptTeach!=null){
			getAllData(m);initId++; mask.hideLoading(); 
		}else{
			setTimeout(initData,100);
		}
	};
	initData();
}])