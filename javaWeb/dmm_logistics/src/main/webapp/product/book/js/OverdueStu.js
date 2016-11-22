app.controller("OverdueStuController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 var vm = scope.vm = {};
	  vm.items = [];
	  var startTime=null;
	  var endTime=null;
	  var htt=[];
	  var httservice="bookOutTimeStuService?"; 
	  var methods=[	
	               //无参数
	        	'getNowOutTimeBook',			//0获取当前已还书籍
	        	'getOutTimePeopleTrend',		//1已还书籍人数趋势
	        	'getOutTimeRateTrend',			//2逾期率趋势
	        	'getoutTimeTrend',				//3获取逾期历史情况分析
	               //时间开始结束
	        	'getOutTimeBookCount',			//4获取逾期书籍概要统计
	        	'getOutTimePeopleByPeopleType',	//5已还书籍人数对比
	        	'getOutTimeRateByPeopleType',	//6逾期率对比
	        	'getOutTimeByType',				//7获取各类型数据对比情况
	        	'getOutTimeByDept',				//8获取各类型数据对比情况
	        	
	        	//xiazuan
	        	'getNowOutTime',				//9获取当前已还书籍
	        	'getOutTime',					//10获取逾期已还书籍
	        	'getOutTimeByPeople',			//11通过人员获取逾期已还书籍
	        	'getOutTimeByStore',			//12通过藏书类别获取逾期已还书籍
	        	'getOutTimeByDeptTeach',		//13通过学院获取逾期已还书籍
	        	'getBorrowByTimePeo',			//14通过还书时间获取逾期已还书籍
	        	'getOutTimeBySchoolYear',		//15通过学年获取逾期已还书籍
	               ];
		//下钻
	  scope.getxqlb=function(i){
	  	var params=[];
	  	var title='';var titles='';var titlesCode='';
	  	titlesCode=borrow_detil.code;
  		titles=borrow_detil.name;
  		params=[scope.date.startTime,scope.date.endTime];
  		if(i==9){
  			params=[];
	  		title='当前已还书籍';
	  	}else if(i==10){
		  	title='逾期已还书籍';
		}else if(i==11){
	  		params.push(scope.people);
	  		title=scope.peopleName+'逾期已还书籍';
	  	}else if(i==12){
	  		params.push(scope.store);
	  		title=scope.storeName+'逾期已还书籍';
	  	}else if(i==13){
	  		params.push(scope.deptTeach);
	  		title=scope.deptTeachName+'逾期已还书籍';
	  	}else if(i==14){
	  		params.push(scope.time,scope.people);
	  		title=scope.time+'时'+scope.peopleName+'逾期已还书籍';
	  	}else if(i==15){
	  		params=[scope.schoolYear];
	  		title=scope.schoolYear+'学年逾期已还书籍';
	  	}
	  	titles=titles.split(',');
	  	titlesCode=titlesCode.split(',');
	  	var query=function(pg){
	  		htt[i].params=[pg.currentPage || 1,
	  				          pg.numPerPage || 10,
	  				        pg.totalRows||0
	  				          ];
	  		htt[i].params = htt[i].params.concat(params);  
	  		htt[i].params = htt[i].params.concat([deptTeach]);  
	  	 mask.showLoading();
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
	  var getDeptData=function(method){
		  http.callService({
		  	  service:'deptTreeService?get'+method,
		  	  params:['hq:book:overdueStu:*']
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
		  getDeptData('DeptTeach');
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
getServiceData();
scope.qushiClick=function(index){
	scope.qushiData=angular.copy(vm.items[index]);
	scope.qsDiv=true;
}
var getvmData=function(i){
	http.callService(htt[i]).success(function(data){
		 if(i==5){//玫瑰图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,value:data[j].VALUE,name:'人次(人)',fieldCode:data[j].CODE}); 
			 }
			 var option=getOption(d,'','bzt'); //mgt
			 option.event=function(param){
					scope.peopleName=param.name;
					scope.people=param.data.nameCode;
					scope.getxqlb(11);
					 timeout();
				 };
			 vm.items[i]=option;
		 }else if(i==6){//金字塔图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].NAME,value:data[j].VALUE,name:'比例(‱)'}); 
			 }
			
				 var option=getOption(d,'','bzt'); //jzt
			 option.tooltip.formatter= "{a} <br/>{b} : {c} ";
			 vm.items[i]=option;
		 }else if(i==7||i==8){//柱状图
			 var d=[[],[],[]];
			 for(var j=0;j<data.length;j++){
				 d[0].push({field:data[j].NAME,value:data[j].NUMS,name:'逾期数量(册)',fieldCode:data[j].CODE}); 
				 d[1].push({field:data[j].NAME,value:data[j].NUMRATE,name:'逾期率(%)',fieldCode:data[j].CODE}); 
				 d[2].push({field:data[j].NAME,value:data[j].AVGTIME,name:'平均逾期时长(天)',fieldCode:data[j].CODE}); 
			 } 
			// scope.vmitems5titles=['分类型对比藏书数量','分类型对比藏书价值','分类型对比藏书文献保障率'];
			 vm.items[i]=[];
			 for(var k=0;k<d.length;k++){
				 var option=getOption(d[k],'','zzt');
				 option.event=function(param){
						scope.storeName=param.name;
						scope.store=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
						scope.deptTeachName=param.name;
						scope.deptTeach=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
						scope.getxqlb(i==7?12:13);
						 timeout();
					 };
					 vm.items[i][k]=option;
			 }
			/* vm.items[i]=[getOption(d[0],'','zzt'),
			              getOption(d[1],'','zzt'),
			              getOption(d[2],'','zzt')];*/
			 if(i==7)scope.radio1id=0;if(i==8)scope.radio2id=0;
		 }else if(i==3){
			 var d=[[],[]];
			 for(var j=0;j<data.length;j++){
				 d[0].push({field:data[j].SCHOOLYEAR,value:data[j].VALUE,name:'逾期本数(册)'}); 
				 d[1].push({field:data[j].SCHOOLYEAR,value:data[j].RATE,name:'逾期率(‱)'}); 
			 }
			var event =function(option){
				option.event=function(param){
						scope.schoolYear=param.name;
						scope.getxqlb(15);
						 timeout();
					 };
					 return option;
			}
			 vm.items[i]=[event(getOption(d[0],'','zzt')),
					 event(getOption(d[1],'','xzt'))];
			 scope.radio3id=0;
		 }else if(i==2||i==1){//趋势图
			 var d=[];
			 for(var j=0;j<data.length;j++){
				 d.push({field:data[j].SCHOOLYEAR,value:data[j].VALUE,name:data[j].NAME}); 
			 }
			 var title="已还书籍人数趋势";
			 if(i==2) title="逾期率趋势";
			 vm.items[i]=fomatSwtDw(getOption(d,title,'xzt'),'人数','人'); 
		 }else if(i==0||i==4){
			  vm.items[i]=data;
		 } 
		 mask.hideLoading(); 
	  });
}
	
var getparams=function(i){
	 var params=[];
	if(i>3){
		 params=[startTime,endTime];
	 }
	 htt[i].params=params;
	 htt[i].params = htt[i].params.concat([deptTeach]);  
	 getvmData(i);
};
var getAllData=function(m){
for(var i=0;i<m.length;i++){
	 mask.showLoading();
	getparams(m[i]);
}
};
var m1=[0,1,2,3];
var m2=[4,5,6,7,8];
//getAllData(m1);
var initId=0;
var deptTeach=null;
/*监控时间*/
scope.$watch('date',function(val1,val2){
	if(val1!=null){
		startTime=angular.copy(val1.startTime);
		endTime=angular.copy(val1.endTime);
		scope.radio1data=null;scope.radio2data=null;
		scope.radio1id=null;scope.radio2id=null;
		if(initId>0){
			vm.items = [];scope.radio1data=null;scope.radio2data=null;scope.radio3data=null;
			;getAllData(m2);
		}
	}
},true);
/*监控dept*/
scope.$watch('deptResult',function(val1,val2){
	if(val1==null||val1.length==0)return;
		 deptTeach=val1[0];
		 scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
		 if(initId>0){vm.items = [];scope.radio1data=null;scope.radio2data=null;scope.radio3data=null;		
			 getAllData(m1);
			 getAllData(m2);
		 }
},true);
//初始化数据
var initData=function(){
	if(initId==0&&scope.date!=null&&deptTeach!=null){
		getAllData(m1);getAllData(m2);initId++;
	}else{
		setTimeout(initData,100);
	}
};
initData(); 
scope.$watch('radio1id',function(val1,val2){
	if(val1!=null){
		scope.radio1data=angular.copy(vm.items[7][Number(scope.radio1id)]);
	}
},true);
scope.$watch('radio2id',function(val1,val2){
	if(val1!=null){
		scope.radio2data=angular.copy(vm.items[8][Number(scope.radio2id)]);
	}
},true);
scope.$watch('radio3id',function(val1,val2){
	if(val1!=null){
		scope.radio3data=angular.copy(vm.items[3][Number(scope.radio3id)]);
	}
},true);

}]);