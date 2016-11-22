app.controller("payHotController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	mask.showLoading();
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="?"; 
	  var methods=[	
	        	'',		//0
	        	'',		//1
	        	'',		//2
	        	'',		//3
	        	'',		//4
	        	'',		//5
	        	'',		//6
	        	'',		//7
	        	'',		//8
	        	'',		//9
	               ];
	  scope.qushiClick=function(index){
			scope.qushiData=angular.copy(vm.items[index]);
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
		  params:['hq:card:payHot:*']//:'+method+':*']
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
			 if(i==0){
			 }
			 mask.hideLoading(); 
		  });
		
	};
	var getparams=function(i){
			 var params=[];
			 if(i==0){
				 params=[];
			 }else if(i==1){
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
	var m=[0,1,2,3,4,5,6,7,8];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var deptTeach=null;
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return;
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0)getAllData(m);
	},true);
	/*监控dept*/
	scope.$watch('deptResult',function(val1,val2){
		if(val1==null||val1.length==0)return;
			 deptTeach=val1[0];
			if(initId>0)getAllData(m);
	},true);
	//初始化数据
	var initData=function(){
		if(initId==0&&startDate!=null&&deptTeach!=null){
			getAllData(m);initId++;mask.hideLoading(); 
		}else{
			setTimeout(initData,100);
		}
	};
	initData(); 
}])