app.controller("netTypeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	mask.showLoading();
	 scope.echarColor=['green','blue','purple','pink'];
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="netTypeService?"; // 上网类型分析
	  var methods=[	
	        	'getCounts',		//0获取上网信息
	        	'getNetStuRatio',	//1获取上网学生占比
	        	'getNetHourStu'			//2分时段获取上网学生人数
	               ];
	  scope.qushiDatas={};
	  scope.qushiClick=function(key,type_code){
		  if(scope.qushiDatas[key]==null){
			  scope.qushiDatas[key]={};
		  }
		  if(scope.qushiDatas[key][type_code]){
			  scope.qushiData=scope.qushiDatas[key][type_code];
			  scope.qsDiv=true;
			  return;
		  }
		  if(type_code!='STU_RATIO')
		  http.callService({
			  service:'netTypeService?getCountsTrend',
			  params:[deptTeach,key=='all'?'all':'onType']
		  }).success(function(data){
			  var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].CODE])mapData[data[j].CODE]=[];
					 mapData[data[j].CODE].push(data[j]);
				 }
			  for(var ke in mapData){
					 var keyData=mapData[ke];
					 var d={
							  ALL_TIME:[],
							  ALL_FLOW:[]
							  };
				for(var j=0;j<keyData.length;j++){
					d.ALL_TIME.push({field:keyData[j].YEAR_MONTH,value:keyData[j].ALL_TIME,name:"时长(分)"}); 
					d.ALL_FLOW.push({field:keyData[j].YEAR_MONTH,value:keyData[j].ALL_FLOW,name:'流量(MB)'}); 
				 }
				 scope.qushiDatas=scope.qushiDatas||{};
				 for(var dk in d){
					 scope.qushiDatas[ke][dk]=getOption(d[dk],'上网'+dk=='ALL_TIME'?'时长':'流量'+'信息趋势统计','xzt');
				 }
			}
				 scope.qushiData=scope.qushiDatas[key][type_code];
				 scope.qsDiv=true;
		  });
		  if(type_code=='STU_RATIO')
			  http.callService({
				  service:'netTypeService?getNetStuRatioTrend',
				  params:[deptTeach,key=='all'?'all':'onType']
			  }).success(function(data){
				  var mapData={};
					 for(var j=0;j<data.length;j++){
						 if(!mapData[data[j].CODE])mapData[data[j].CODE]=[];
						 mapData[data[j].CODE].push(data[j]);
					 }
				  for(var ke in mapData){
						 var keyData=mapData[ke];
				  var d=[];
					 for(var j=0;j<keyData.length;j++){
						d.push({field:keyData[j].YEAR_MONTH,value:keyData[j].STU_RATIO,name:"人数比(%)"}); 
					 }
					 scope.qushiDatas[ke]['STU_RATIO']=getOption(d,'上网人数比信息趋势统计','xzt');
				  }
					 scope.qushiData=scope.qushiDatas[key][type_code];
					 scope.qsDiv=true;
			  });
		  
}
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
getServiceData();
var getDeptData=function(method){
	http.callService({
		  service:'deptTreeService?get'+method,
		  params:['hq:net:netType:*']//:'+method+':*']
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
	var xldm={};
	var initvm=function(){
		vm = scope.vm = {all:{
			info:{
				 ALL_FLOW: 0,
				 ALL_TIME: 0,
				 DAY_FLOW: 0,
				 DAY_TIME: 0,
				 ONE_FLOW: 0,
				 ONE_TIME: 0
				 },
				 STU_RATIO:0,
				 fsd:[]
		}};
		  vm.items = [];
			scope.order=['all'];
			scope.orderName=['整体情况'];
	};


	
	var getvmData=function(i,type){
		http.callService(htt[i]).success(function(data){
			 var mapData={};
			 for(var j=0;j<data.length;j++){
				 if(!mapData[data[j].CODE])mapData[data[j].CODE]=[];
				 mapData[data[j].CODE].push(data[j]);
			 }
			 if(i==0){
				 if(type!='all'){
					 for(var j=0;j<data.length;j++){
					 scope.order.push(data[j].CODE);
					 scope.orderName.push(data[j].NAME);
					 };
				 }
				 for(var key in mapData){
					 var keyData=mapData[key][0];
					 vm[key]=vm[key]||{};
					 vm[key].info={
							 ALL_FLOW: keyData.ALL_FLOW,
							 ALL_TIME: keyData.ALL_TIME,
							 DAY_FLOW: keyData.DAY_FLOW,
							 DAY_TIME: keyData.DAY_TIME,
							 ONE_FLOW: keyData.ONE_FLOW,
							 ONE_TIME: keyData.ONE_TIME
							 };
				 }
			 }else if(i==1){
				 for(var key in mapData){
					 var keyData=mapData[key][0];
					 vm[key]=vm[key]||{};
					 vm[key].STU_RATIO=keyData.STU_RATIO;
				 }
			 }else if(i==2){
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[];
					 for(var j=0;j<keyData.length;j++){
						 d.push({field:keyData[j].HOUR_,fieldCode:keyData[j].HOUR_,value:keyData[j].IN_COUNTS,name:'在线(人)'}); 
						 d.push({field:keyData[j].HOUR_,fieldCode:keyData[j].HOUR_,value:keyData[j].ON_COUNTS,name:'上线(人)'}); 
						 d.push({field:keyData[j].HOUR_,fieldCode:keyData[j].HOUR_,value:keyData[j].OUT_COUNTS,name:'离线(人)'}); 
					 }
					 vm[key]=vm[key]||{};
					 var option=getOption(d,'','xqs').saveAsImage(mapData[key].NAME+"分时段情况");
					 option.legend={
				        data:['在线(人)','上线(人)','离线(人)']
				    }
					 vm[key].fsd=option;
				 }
			}
			 mask.hideLoading(); 
		  },null,null,null,true);
		
	};
	var getparams=function(i){
		 var type=angular.copy(scope.type);
			 var params=[];
				 params=[startDate,endDate,deptTeach,type];
			 htt[i].params=params;
			 getvmData(i,type);
	};
	var getAllData=function(m){
		for(var k=0;k<types.length;k++){
			scope.type=types[k];
			for(var i=0;i<m.length;i++){
				 mask.showLoading();
				getparams(m[i]);
			}
		}
		scope.ulcla=vm.length>3?'col-md-3':'col-md-4';
	};
	var m=[0,1,2];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
	var types=['all','onType'];
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return;
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0){initvm();getAllData(m);}
	},true);
	/*监控dept*/
	scope.$watch('deptResult',function(val1,val2){
		if(val1==null||val1.length==0)return;
			 deptTeach=val1[0];
			if(initId>0){initvm();getAllData(m);}
	},true);
	//初始化数据
	var initData=function(){
		if(initId==0&&startDate!=null&&deptTeach!=null&&initvm!=null){
			initvm();getAllData(m);initId++;mask.hideLoading(); 
		}else{
			setTimeout(initData,100);
		}
	};
	initData(); 
}])