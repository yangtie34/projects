app.controller("rechargeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	mask.showLoading();
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="rechargeService?"; //学生充值分析
	  var methods=[	
	        	'getRecharge',				//0充值统计
	        	'getRechargeByDept',		//1分学院充值统计
	        	'getRechargeRegion',		//2充值金额区间
	        	'getLastMoneyRegion',		//3剩余金额充值区间
	        	'getRechargeByType',		//4 充值类型统计
	        	'getRechargeByHour',		//5分时段充值类型统计
	        	'getRechargeTrend',			//6年度充值趋势
	        	'getRechargeTrendByType',	//7年度充值分类型趋势
	               ];
	  
 scope.qushiClick=function(index){
			scope.qushiData=angular.copy(scope.qsdata[index]);
			scope.qsDiv=true;
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
		  params:['hq:card:recharge:*']//:'+method+':*']
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
	var getvmData=function(i){
		http.callService(htt[i]).success(function(data){
			 if(i==1){
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_MONEY,name:'金额(元)'});
				 } 
				 var option=getOption(d,'','zxt');
				 option.toolbox.orient="horizontal";
				 vm.items[i]=option.saveAsImage("各学院充值情况");
				
			 }else if(i==2||i==3){//柱状图
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:i==2?'次数(次)':'次数(次)'}); 
				 }
				 var option=getOption(d,'',i==2?'hzztright':'hzztleft').saveAsImage(i==2?"单次充值金额次数分布":"充值前所剩余额分布");
				 vm.items[i]=option;
			 }else if(i==4){//饼状图
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d[1].push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ALL_MONEY,name:'金额(元)'}); 
				 }
				 vm.items[i]=[getOption(d[0],'','bzt').saveAsImage("充值次数"),
				              getOption(d[1],'','bzt').saveAsImage("充值总金额")];
			 }else if(i==5){////趋势图
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].HOUR_,value:data[j].ALL_COUNT,name:data[j].NAME}); 
					 d[1].push({field:data[j].HOUR_,value:data[j].ALL_MONEY,name:data[j].NAME}); 
				 }
				 vm.items[i]=[fomatSwtDw(getOption(d[0],'','xzt'),"次数","次").saveAsImage("充值次数对比趋势"),
				              fomatSwtDw(getOption(d[1],'','xzt'),"金额","元").saveAsImage("充值金额对比趋势")];
			 }else if(i==6){//
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].YEAR,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d.push({field:data[j].YEAR,value:data[j].ALL_MONEY,name:'金额(元)'});
				 } 
				 var option=getOption(d,'','zxt');
				 option.toolbox.orient="horizontal";
				 vm.items[i]=option.saveAsImage("总体充值历史情况");
				 scope.type67='all';
			 }else if(i==7){ //时间轴三维图
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({time:data[j].YEAR,field:data[j].TYPE_NAME,fieldCode:data[j].TYPE_CODE,value:data[j].ALL_COUNT,name:'次数(次)'}); 
					 d.push({time:data[j].YEAR,field:data[j].TYPE_NAME,fieldCode:data[j].TYPE_CODE,value:data[j].ALL_MONEY,name:'金额(元)'});
				 } 
				 vm.items[i]=getOption({data:d,type:'zxt'},'','timeSwt').saveAsImage("按类型充值历史情况");
			 }else{
				 vm.items[i]=data;
			 }
			 mask.hideLoading(); 
		  });
		
	};
	 getQsData=function(){
		  
		  http.callService({
			  service:'cardTrendService?getRechargeTrend',
			  params:[deptTeach]
		  }).success(function(data){
			  var d=[[],[],[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].YEAR_MONTH,value:data[j].ALL_MONEY,name:'总金额(元)'}); 
					 d[1].push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:"总次数(次)"}); 
					 d[2].push({field:data[j].YEAR_MONTH,value:data[j].USE_PEOPLE,name:"充值人数(人)"}); 
					 d[3].push({field:data[j].YEAR_MONTH,value:data[j].PEOPLE_MONEY,name:'人均充值(元)'}); 
				 }
				 scope.qsdata=[getOption(d[0],'','xzt').saveAsImage("充值总金额对比趋势"),
				               getOption(d[1],'','xzt').saveAsImage("充值总次数对比趋势"),
				               getOption(d[2],'','xzt').saveAsImage("充值人数对比趋势"),
				               getOption(d[3],'','xzt').saveAsImage("人均充值对比趋势"),
				               ];
		  });
		  
	  }
	var getparams=function(i){
			 var params=[];
			 if(i<6){
				 params=[startDate,endDate,deptTeach];
			 }else if(i<8){
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
	var m=[0,1,2,3,4,5];
	var m1=[6,7];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return; vm.items = [];
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0)getAllData(m);
	},true);
	/*监控dept*/
	scope.$watch('deptResult',function(val1,val2){
		if(val1==null||val1.length==0)return; vm.items = [];
			 deptTeach=val1[0];
			 scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
			if(initId>0){
				getAllData(m);getAllData(m1);getQsData();
				scope.type67=null;
			}
			
	},true);
	scope.$watch('type67',function(val1,val2){
		if(val1==null)return;
		scope.type67data=vm.items[scope.type67=='all'?6:7];
	},true);
	//初始化数据
	var initData=function(){
		if(initId==0&&startDate!=null&&deptTeach!=null){ 
			getAllData(m1);getQsData();
			getAllData(m);initId++;mask.hideLoading(); 
		}else{
			setTimeout(initData,100);
		}
	};
	initData(); 
}])