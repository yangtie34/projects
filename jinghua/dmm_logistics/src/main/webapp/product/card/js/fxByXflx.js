app.controller("fxByXflxController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	mask.showLoading();
	 scope.echarColor=['green','blue','purple','pink'];
	var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="habitPayTypeService?"; //学生分消费类型消费习惯分析
	  var methods=[	
	        	'getHabitCount',		//0整体情况
	        	'getHabitCountByType',	//1分类型情况
	        	'getHabitZao',			//2整体早中晚
	        	'getHabitZaoByType',	//3分类型早中晚
	        	'getHabitHour',			//4整体时段情况
	        	'getHabitHourByType',	//5分类型时段情况
	               ];
	  scope.qushiClick=function(type_code){
		  http.callService({
			  service:'cardTrendService?getPayTypeTrend',
			  params:[deptTeach,type_code]
		  }).success(function(data){
			  var d=[];
				 for(var j=0;j<data.length;j++){
					if(scope.csjecode=='cs') d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:"总次数(次)"}); 
					if(scope.csjecode=='je')  d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_MONEY,name:'总金额(元)'}); 
					/* d.push({field:data[j].YEAR_MONTH,value:data[j].COUNT_RATE,name:'刷卡率(%)'}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].MONEY_RATE,name:'消费率(%)'}); */
				 }
				 scope.qushiData=getOption(d,'分消费类型消费趋势统计','xzt');
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
		  params:['hq:card:fxByXflx:*']//:'+method+':*']
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
	var initvm=null;
	http.callService({
		  service:'payPowerService?getxldm',
		  params:[]
	}).success(function(data){
		xldm=data;
		var ct=data.ct;
		var cs=data.cs;
		var xy=data.xy;
		initvm=function(){
			vm.items[0]={order:['all',ct,cs,xy]};vm.items[1]=vm.items[0];};
		scope.photo={all:'all'};scope.photo[ct]='dinner';scope.photo[cs]='supermarket';scope.photo[xy]='bath';
	});
	
	scope.csjecode='cs';
	scope.csje=function(code){
		scope.csjecode=code;
	};
	
	var getvmData=function(i){
		http.callService(htt[i]).success(function(data){
			vm.items[0].all=vm.items[0].all||{};
			 if(i==0){
				 vm.items[0].all={name:'整体情况',cs:data.ALL_COUNT,je:data.ALL_MONEY};
			 }else if(i==1){
				 for(var j=0;j<data.length;j++){
					var map={name:data[j].TYPE_NAME,cs:data[j].ALL_COUNT,je:data[j].ALL_MONEY};
					 for(var key in map){
						 vm.items[0][data[j].TYPE_CODE]=vm.items[0][data[j].TYPE_CODE]||{};
						 vm.items[0][data[j].TYPE_CODE][key]=map[key];
						 vm.items[0][data[j].TYPE_CODE].show=map[key]>0?true:false;
					 }
				 } 
				 var showclaid=1;
				 vm.items[0].all.show=true;
				 for(var key in vm.items[0]){
					 if(key=='order'||key=='all')break;
					 vm.items[0][key].show=vm.items[0][key].cs>0?true:false;
					 vm.items[0][key].show==true?showclaid++:null;
				 }
				 scope.ulcla=showclaid>3?['col-md-3','col-md-9']:['col-md-4','col-md-8'];
				 scope.ulcla=vm.items[0].all.cs==null?['col-md-3','col-md-9']:scope.ulcla;
			 }else if(i==2){
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d[1].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_MONEY,name:'金额(元)'}); 
				 }
				 vm.items[0].all.zzw={cs:getOption(d[0],'','bztwz').saveAsImage("整体早中晚刷卡次数情况"),
						 				je:getOption(d[1],'','bztwz').saveAsImage("整体早中晚刷卡金额情况")};
			}else if(i==3){
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
					 vm.items[0][key]=vm.items[0][key]||{};
					 vm.items[0][key].zzw={cs:getOption(d[0],'','bztwz').saveAsImage(vm.items[0][key].name+"早中晚刷卡次数情况"),
							 				je:getOption(d[1],'','bztwz').saveAsImage(vm.items[0][key].name+"早中晚刷卡金额情况")};
				 }
			}else if(i==4){
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].HOUR_,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d[1].push({field:data[j].HOUR_,value:data[j].ALL_MONEY,name:'金额(元)'}); 
				 }
				 vm.items[0].all.fsd={cs:getOption(d[0],'','xqs').saveAsImage("整体分时段统计刷卡次数"),
						 			je:getOption(d[1],'','xqs').saveAsImage("整体分时段统计刷卡金额")};
			}else if(i==5){
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
					 vm.items[0][key]=vm.items[0][key]||{};
					 vm.items[0][key].fsd={cs:getOption(d[0],'','xqs').saveAsImage(vm.items[0][key].name+"分时段统计刷卡次数"),
							 				je:getOption(d[1],'','xqs').saveAsImage(vm.items[0][key].name+"分时段统计刷卡金额")};
				 }
			}
			 mask.hideLoading(); 
		  },null,null,null,true);
		
	};
	var getparams=function(i){
			 var params=[];
			 if(i<6){
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
		vm.items[1]=vm.items[0];
	};
	var m=[0,1,2,3,4,5];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
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