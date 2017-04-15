app.controller("netTeaRankController", [ "$scope","dialog",'mask','$timeout','http','timeAlert','exportPage',function(scope,dialog,mask,timeout,http,timeAlert,exportPage) {
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
	  var httservice="netTeaTopService?"; //
	  var methods=[	
	        	'getTeaTop',					//0获取排名
	               ]; 
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
getServiceData();
scope.flowOrtimeClick=function(flowtime){
	scope.type=flowtime;
	getparams(0);
}  
var getDeptData=function(method){
http.callService({
	  service:'deptTreeService?get'+method,
	  params:['hq:net:netTeaRank:'+method+':*']
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
	vm = scope.vm = {};
	  vm.items = [];
}
scope.exportExcel=function(){
	 mask.showLoading();
var invoke=angular.copy(htt[0]);
invoke.params[0]=1;
invoke.params[1]=scope.page.totalRows;
var ex={
	 invoke:invoke,
	 title:'教师上网信息排名',
	 titles:scope.titles.name,
	 titleCodes:scope.titles.code
}
mask.hideLoading(); 
exportPage.callService(ex).success(function(ret){
	
})

}
var getvmData=function(i){
	http.callService(htt[i]).success(function(data){
		 if(i==0){
			  vm.items[i]=data.resultList;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
		 }
		 mask.hideLoading(); 
	  });
	
};
scope.titles={
			code:['RANK_','TEA_NO','TEA_NAME','USE_FLOW','USE_TIME','SEX_NAME','DEPT_NAME','EDU_NAME'],
			name:['排名','职工号','姓名','流量','时长','性别','部门','学历']
	}
var getparams=function(i){
		 var params=[];
		 if(i==0){
			 params=[scope.page.currentPage || 1,
			         scope.page.numPerPage || 10,0,
			         startTime,endTime,deptTeach,scope.type,scope.rank];
			 htt[i].params=params;
			 getvmData(i);
		 }
};
var getAllData=function(m){
	for(var i=0;i<m.length;i++){
		 mask.showLoading();
		getparams(m[i]);
	}
};
var m=[0];
scope.type='flow';
var startTime=null;
var endTime=null;
var deptTeach=null;
var initId=0;
scope.rank=10;

/*监控时间*/
scope.$watch('date',function(val1,val2){
	if(val1!=null){
		startTime=angular.copy(val1.startTime);
		endTime=angular.copy(val1.endTime);
		if(initId>0){
		getAllData(m);}
	}
},true);
	  /*监控分页工具选择页码的变化,若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  mask.showLoading();getparams(0);
		  }
	  },true);	 
	  
	  /*监控dept*/
		scope.$watch('deptResult',function(val1,val2){
			if(val1==null||val1.length==0)return;
				 deptTeach=val1[0];
				if(initId>0){initvm();getAllData(m);}
		},true);
		  scope.$watch('rank',function(val1,val2){
			  	if(val1!=null){ mask.showLoading();vm.items[0]=null;
			  		if(initId!=0) getAllData(m);
			  	}
			  },true);
	//初始化数据
		var initData=function(){
			if(initId==0&&startTime!=null&&deptTeach!=null&&initvm!=null){
				initvm();getAllData(m);initId++;mask.hideLoading(); 
			}else{
				setTimeout(initData,100);
			}
		};
		initData(); 
}]);