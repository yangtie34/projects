app.controller("netTeaController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 mask.showLoading();
	 var vm = scope.vm = {};
	  
	  var htt=[];
	  var httservice="netTeaService?"; //
	  var methods=[	
	        	'getCounts',		//0获取上网信息
	        	'getNetType',		//1分类型获取上网信息
	        	'getNetHourTea'		//2分时段获取上网人数
	               ];
	  scope.qushiDatas=scope.qushiDatas||{};
	  scope.qushiClick=function(type,type_code,lx){
		  if(scope.qushiDatas[type]==null){
			  scope.qushiDatas[type]={};
		  }else{
			  scope.qushiData=scope.qushiDatas[type][type_code][lx];
			  scope.qsDiv=true;
			  return;
		  }
		  http.callService({
			  service:'netTeaService?getCountsTrend',
			  params:[deptTeach,type]
		  }).success(function(data){
			  var mapData={};
				 for(var j=0;j<data.length;j++){
					 if(!mapData[data[j].CODE])mapData[data[j].CODE]=[];
					 mapData[data[j].CODE].push(data[j]);
				 }
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var lxMap={
							  all:[],
							  one:[],
							  day:[]
					  }
					 for(var j=0;j<keyData.length;j++){
						 lxMap.all.push({field:keyData[j].YEAR_MONTH,value:keyData[j].ALL_TIME,name:"时长(分)"}); 
						 lxMap.all.push({field:keyData[j].YEAR_MONTH,value:keyData[j].ALL_FLOW,name:'流量(MB)'}); 
						 lxMap.one.push({field:keyData[j].YEAR_MONTH,value:keyData[j].ONE_TIME,name:"时长(分)"}); 
						 lxMap.one.push({field:keyData[j].YEAR_MONTH,value:keyData[j].ONE_FLOW,name:'流量(MB)'}); 
						 lxMap.day.push({field:keyData[j].YEAR_MONTH,value:keyData[j].DAY_TIME,name:"时长(分)"}); 
						 lxMap.day.push({field:keyData[j].YEAR_MONTH,value:keyData[j].DAY_FLOW,name:'流量(MB)'}); 
						 }
					 scope.qushiDatas[type][key]=scope.qushiDatas[type][key]||{};
					 for(var dk in lxMap){
						 scope.qushiDatas[type][key][dk]=getOption(lxMap[dk],(dk=='all'?'共计':dk=='one'?'人均单次':'日均')+'上网信息趋势统计','xzt');
					 }
				 }
				 scope.qushiData=scope.qushiDatas[type][type_code][lx];
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
scope.fllx=[{code:'xb',name:'性别'},{code:'status',name:'状态'},{code:'bzlb',name:'编制'},{code:'zwjb',name:'职称'}];
http.callService({
	  service:'payPowerService?getxldm',
	  params:[]
}).success(function(data){
	scope.ico_title={all:{all:{ico:'all',name:'整体'}},
			xb:{1:{ico:'man',name:'男生'},2:{ico:'female',name:'女生'}}
	};
	for(var i=1;i<scope.fllx.length;i++){
		var codes=data['tea.'+scope.fllx[i].code+'.code'].split(',');
		var names=data['tea.'+scope.fllx[i].code+'.name'].split(',');
		scope.ico_title[scope.fllx[i].code]={};
		for(var j=0;j<codes.length;j++){
			scope.ico_title[scope.fllx[i].code][codes[j]]={ico:'null',name:names[j]}
		}
	}
});
var getDeptData=function(method){
	http.callService({
		  service:'deptTreeService?get'+method,
		  params:['hq:net:netTea:*']//:'+method+':*']
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
	var initvm=function(){
		for(var key in scope.ico_title){
			vm[key]={};
			var initd={
					info:{
						 ALL_TIME:0,
						 ALL_FLOW:0,
						 ONE_TIME:0,
						 ONE_FLOW:0,
						 DAY_TIME:0,
						 DAY_FLOW:0
						 },
				flx:{time:[],flow:[]},
				fsd:[]
		};
			for(var vk in scope.ico_title[key]){
				vm[key][vk]=angular.copy(initd);
			}
		}
		vm.items=[];
		};
	//scope.xbxlcode='xb';
	scope.fllxClick=function(code){
		scope.fllxcode=code;
		var xl=angular.copy(vm[code]);
		vm[code]={};
		vm[code]=xl;
		var all=angular.copy(vm['all']);
		vm['all']={};
		vm['all']=all;
		scope.ulcla=vm[code]!=null&&Object.keys(xl).length>2?['col-md-3','col-md-9']:['col-md-4','col-md-8'];
		scope.ulindex=[];
		scope.ulindexnow=xl!=null?Object.keys(xl).length:2;
		scope.ulindexnow>3?scope.ulindexnow=3:null;
		var lengths=xl!=null?Object.keys(xl).length:2;
		for(var i=0;i<lengths;i++){
			scope.ulindex[i]=i<3?true:false;
		}
		
	};
	scope.fllxClick('xb');
	scope.ulindexclick=function(index){
		if(index>0){
			
			scope.ulindex[scope.ulindexnow]=true;
			scope.ulindex[scope.ulindexnow-3]=false;
			scope.ulindexnow++;
		}else{
			scope.ulindexnow--;
			scope.ulindex[scope.ulindexnow]=false;
			scope.ulindex[scope.ulindexnow-3]=true;
		}
	};
	var getvmData=function(i,type){
		
		http.callService(htt[i]).success(function(data){
			 var mapData={};
			 for(var j=0;j<data.length;j++){
				 if(data[j].CODE=='null')break;
				 if(!mapData[data[j].CODE])mapData[data[j].CODE]=[];
				 mapData[data[j].CODE].push(data[j]);
			 }
			 if(i==0){
				 for(var key in mapData){
					 var keyData=mapData[key][0];
					 vm[type][key]=vm[type][key]||{};
					 vm[type][key].info={
							 ALL_TIME:keyData.ALL_TIME,
							 ALL_FLOW:keyData.ALL_FLOW,
							 ONE_TIME:keyData.ONE_TIME,
							 ONE_FLOW:keyData.ONE_FLOW,
							 DAY_TIME:keyData.DAY_TIME,
							 DAY_FLOW:keyData.DAY_FLOW
							 };
				 }
					 for(var key in mapData){
					 var upDown=function(){
						if(scope.initAllInfo==true){
							var allinfo=vm.all.all.info;
							var keyinfo=vm[type][key].info;
							for(var udk in keyinfo){
								keyinfo.updown=keyinfo.updown||{};
								keyinfo.updown[udk+'cla']=(keyinfo[udk]-allinfo[udk])>0?['xscz-red','grow-up']:['xscz-greener','grow-down'];
								keyinfo.updown[udk+'val']=Math.abs(keyinfo[udk]-allinfo[udk]);
							}
							
						}else{
							setTimeout(upDown,100);
						}
					};
					if(type=='all'){
						scope.initAllInfo=true;
					}else{
						upDown();
					}
				 }
			 }else if(i==1){
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d={time:[],flow:[]};
					 for(var j=0;j<keyData.length;j++){
						 d.time.push({field:keyData[j].TYPENAME,fieldCode:keyData[j].TYPECODE,value:keyData[j].ALL_TIME,name:'时长(分)'}); 
						 d.flow.push({field:keyData[j].TYPENAME,fieldCode:keyData[j].TYPECODE,value:keyData[j].ALL_FLOW,name:'流量(MB)'});  
					 }
					 vm[type][key]=vm[type][key]||{};
					 vm[type][key].flx={time:getOption(d.time,'','bztwz'),flow:getOption(d.flow,'','bztwz')};
				 }
			 }else if(i==2){
				 for(var key in mapData){
					 var keyData=mapData[key];
					 var d=[];
					 for(var j=0;j<keyData.length;j++){
						 d.push({field:keyData[j].HOUR_,fieldCode:keyData[j].HOUR_,value:keyData[j].IN_COUNTS,name:'月均在线(人)'}); 
						 d.push({field:keyData[j].HOUR_,fieldCode:keyData[j].HOUR_,value:keyData[j].ON_COUNTS,name:'上线(次)'}); 
						 d.push({field:keyData[j].HOUR_,fieldCode:keyData[j].HOUR_,value:keyData[j].OUT_COUNTS,name:'离线(次)'}); 
					 }
					 vm[type][key]=vm[type][key]||{};
					 var option=getOption(d,'','xqs');
					 option.legend={
				        data:['在线(人)','上线(次)','离线(次)']
				    }
					 vm[type][key].fsd=option;
				 }
			 }
			 mask.hideLoading(); 
		  });
		
	};
	var getparams=function(i,type){
			 htt[i].params=[startDate,endDate,deptTeach,type];
			 getvmData(i,type);
	};
	var getAllData=function(m){
		for(var key in scope.ico_title){
			for(var i=0;i<m.length;i++){
				 mask.showLoading();
				getparams(m[i],key);
			}
		}
	};
	var m=[0,1,2];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return;initvm();
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0)getAllData(m);
	},true);
	/*监控dept*/
	scope.$watch('deptResult',function(val1,val2){
		if(val1==null||val1.length==0)return;initvm();
			 deptTeach=val1[0];
			if(initId>0)getAllData(m);
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