app.controller("cardEmployController", [ "$scope","dialog",'mask','$timeout','http','timeAlert','exportPage',function(scope,dialog,mask,timeout,http,timeAlert,exportPage) {
	mask.showLoading();
	 
	  scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="cardUsedService?"; //学生一卡通使用状况分析
	  var methods=[	
	        	'getCardUsed',			//0一卡通所有学生情况
	        	'getCardUsedBySex',		//1分性别使用情况
	        	'getCardUsedByEdu',		//2分学历使用情况
	        	'getCardUsedByDept',	//3分学院使用情况
	        	'getNoCardUsed',		//4不活跃人群
	               ];

	  scope.qushiClick=function(type_code,flag){
				  http.callService({
					  service:'cardTrendService?getCardUsedTrend',
					  params:[deptTeach,type_code,flag]
				  }).success(function(data){
					  var d=[];
						 for(var j=0;j<data.length;j++){
							 d.push({field:data[j].YEAR_MONTH,value:data[j].USE_RATE*100,name:'使用率(%)'}); 
							 //d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_PEOPLE,name:"总人数(人)"}); 
							 //d.push({field:data[j].YEAR_MONTH,value:data[j].USE_PEOPLE,name:"使用人数(人)"}); 
						 }
						 scope.qushiData=getOption(d,'一卡通使用率趋势统计','xzt');
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
		  params:['hq:card:cardEmploy:*']//:'+method+':*']
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
		 if(i<3){
			 vm.items[i]=data;
		 }else if(i==3){
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].TYPE_NAME,fieldCode:data[j].TYPE_CODE,value:data[j].USE_NUM,name:'使用人数(人)'}); 
				 d.push({field:data[j].TYPE_NAME,fieldCode:data[j].TYPE_CODE,value:data[j].USE_RATE,name:'使用率(%)'});
			 } 
			 vm.items[i]=getOption(d,'','zxt');
		 }else if(i==4){
			 vm.items[i]=data.resultList;
			  scope.page.totalRows=data.totalRows;
 			  scope.page.totalPages=data.totalPages; 
		 }
		 mask.hideLoading(); 
	  });
	
};
scope.exportExcel=function(){
	 mask.showLoading();
var invoke=angular.copy(htt[4]);
invoke.params[0]=1;
invoke.params[1]=scope.page.totalRows;
var ex={
	 invoke:invoke,
	 title:'一卡通非活跃学生名单',
	 titles:'学号,姓名,性别,所属学院,专业,班级,刷卡次数'.split(','),
	 titleCodes:'STU_ID,STU_NAME,SEX_NAME,DEPT_NAME,MAJOR_NAME,CLASS_NAME,PAY_COUNT'.split(',')
}
mask.hideLoading(); 
exportPage.callService(ex).success(function(ret){
	
})

}
var getparams=function(i){
		 var params=[];
		 if(i<4){
			 params=[startDate,endDate,deptTeach];
		 }else if(i==4){
			 params=[scope.page.currentPage || 1,
			         scope.page.numPerPage || 10,0,
			         startDate,endDate,deptTeach];
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
var m=[0,1,2,3,4];
var initId=0;
var startDate=null;
var endDate=null;
var deptTeach=null;
scope.$watch('page.currentPage',function(val1,val2){
	if(initId>0)getparams(4);
})
scope.$watch('page.numPerPage',function(val1,val2){
	if(initId>0)getparams(4);
})
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
		 scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
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