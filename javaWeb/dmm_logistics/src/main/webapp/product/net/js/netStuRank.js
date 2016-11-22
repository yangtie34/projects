app.controller("netStuRankController", [ "$scope","dialog",'mask','$timeout','http','timeAlert','exportPage',function(scope,dialog,mask,timeout,http,timeAlert,exportPage) {
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
	  var httservice="netStuWarnService?"; //学生上网预警
	  var methods=[	
	        	'getNetWarnStus',					//0获取预警人员
	        	'getNetStuType',		//1预警人员分类型展示
	        	'getNetWarnTypeStus',//2按类型获取预警人员信息
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
	scope.titlesCode="STU_ID,STU_NAME,SEX_NAME,DEPT_NAME,MAJOR_NAME,CLASS_NAME,EDU_NAME,NATION_NAME,ALL_TIME,ALL_FLOW,ALL_MONEY".split(',');
	scope.titles="学号,学生,性别,学院,专业,班级,学历,民族,时长(分),流量(MB),金额(元)".split(',');
//下钻
scope.getxqlb=function(i){
	var params=[];
	var title='';
	var titles='';var titlesCode='';
	if(i==2){
		params=[scope.page.currentPage || 1,
		         scope.page.numPerPage || 10,0,
		         startTime,endTime,deptTeach,scope.netType,scope.value,scope.codeType,scope.codeValue];
		title='按类型获取预警人员信息';
		titles=scope.titles;
		titlesCode=scope.titlesCode;
	}
	var query=function(pg){
		htt[i].params=params;
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

scope.exportExcel=function(){
	 mask.showLoading();
var invoke=angular.copy(htt[0]);
invoke.params[0]=1;
invoke.params[2]=scope.page.totalRows;
var ex={
	 invoke:invoke,
	 title:'预警人员信息',
	 titles:scope.titles,
	 titleCodes:scope.titlesCode
}
mask.hideLoading(); 
exportPage.callService(ex).success(function(ret){
	
})

}
var getDeptData=function(method){
http.callService({
	  service:'deptTreeService?get'+method,
	  params:['hq:net:netStuWarn:'+method+':*']
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
scope.timeFlow=function(type){
	if(scope.netType==type){
		return;
	}else{
		scope.netType=type;
	}
	initvm();getAllData(m);
}
var initvm=function(){
	vm = scope.vm = {};
	  vm.items = [];
}
var getvmData=function(i,type){
	http.callService(htt[i]).success(function(data){
		 if(i==0){
			  vm.items[i]=data.resultList;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
		 }else if(i==1){//饼状图
			 vm.items[i]=vm.items[i]||{};
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:'人数(人)'}); 
			 }
			 var option=getOption(d,'',type=='allMz'?'zzt':'bzt'); 
			 option.event=function(param){
					scope.codeValue=type=='allMz'?option.series[param.seriesIndex].dataCode[param.dataIndex]:param.data.nameCode;
					scope.codeType=type;
					scope.getxqlb(2);
					 timeout();
				 };
			 vm.items[i][type]=option;
		 }
		 mask.hideLoading(); 
	  });
	
};
var getparams=function(i,type){
		 var params=[];
		 if(i==0){
			 params=[scope.page.currentPage || 1,
			         scope.page.numPerPage || 10,0,
			         startTime,endTime,deptTeach,scope.netType,scope.value];
			 htt[i].params=params;
			 getvmData(i,null);
		 }else if(i==1){
			 for(var k=0;k<type.length;k++){
				 params=[startTime,endTime,deptTeach,scope.netType,scope.value,type[k]];
				 htt[i].params=params;
				 getvmData(i,type[k]); 
			 }
		 }
};
var getAllData=function(m){
	for(var i=0;i<m.length;i++){
		 mask.showLoading();
		getparams(m[i],['xb','xl','allMz']);
	}
};
var m=[0,1];
scope.netType='time';
var startTime=null;
var endTime=null;
var deptTeach=null;
var initId=0;
scope.value=400;

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
	  var type1Titles=[['排名','学生号','学生名称','所属学院','借书次数','月度上榜次数'],
	                   ['Id','教师号','学生名称','所属学院','借书次数','月度上榜次数'],
	                   ['Id','生号','学生名称','所属学院','借书次数','月度上榜次数']];
	  
	  /*监控dept*/
		scope.$watch('deptResult',function(val1,val2){
			if(val1==null||val1.length==0)return;
				 deptTeach=val1[0];
				if(initId>0){initvm();getAllData(m);}
		},true);
		
	  scope.myKeyup=function(e){
		  var keynum;
			if(window.event) 
		  	{
		  		keynum = e.keyCode;
		  	} else if(e.which) 
		  	{
		  		keynum = e.which;
		  	}
		  	if(keynum==13){
		  		scope.sxreload();
		  	}
	  };
	  scope.sxreload=function(){
		  getAllData(m);
	  };
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