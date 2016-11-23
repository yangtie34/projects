app.controller("BookReadStuController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="bookBorrowStuService?"; 
	  var methods=[	
	               //无参数
	        	'getPeopleAvgBorrowTrend',	//0各类型人群人均借书量趋势
	        	'getReaderRateTrend',		//1各类型借书人数比例趋势
	        	'getBorrowTrendByYear',		//2图书借阅历史趋势按学年
	        	'getBorrowTrendByMonth',	//3图书借阅历史趋势按月份
	        	
	        	 //时间参数开始、结束
	        	'getBorrowCount',			//4图书借阅概要统计
	        	'getPeopleAvgBorrowByPeopleType',		//5各类型人群人均借书量对比
	        	'getReaderRateByPeopleType',//6各类型借书人数比例对比
	        	'getBorrowByType',			//7获取当前图书藏书类别对比
	        	'getStuBorrowByDept',		//8分学院分析学生借阅情况
	        	'getBorrowByTime',			//9各类人群借阅书籍时段情况
	        	
	        	//xiazuan
	        	'getBorrow',				//10获取借阅列表
	        	//'getBorrowCountByPeople',	//11通过借阅者获取人均借书比
	        	'getBorrowByPeople',		//11通过借阅者获取借阅列表
	        	//'getBorrowPeosCountByPeople',//通过借阅者获取人均比
	        	'getBorrowByStore',			//12通过藏书列表获取借书列表
	        	'getBorrowByDeptTeach',		//13通过学院获取借书列表
	        	'getBorrowByTimePeo',		//14通过时间和人员类型获取借书列表
	        	'getBorrowBySchoolYear',	//15通过学年获取借书列表
	        	'getBorrowCountByMonth',	//16通过月份获取借书列表
	        	
	        	'getBorrowTrend', //17图书借阅历史趋势
	               ];
	//下钻
	  scope.getxqlb=function(i){
		  if(i==17){
			  htt[i].params=[deptTeach];
			  http.callService(htt[i]).success(function(data){
			  var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].CODE,value:data[j].VALUE,name:'月份'}); 
				 }
				 var title="图书借阅历史趋势";
				 scope.qushiData=fomatSwtDw(getOption(d,title,'xzt'),'借书量','册'); 
				 scope.qsDiv=true;
			  });
			  return;
		  }
	  	var params=[];
	  	var title='';var titles='';var titlesCode='';
	  	titlesCode=borrow_detil.code;
  		titles=borrow_detil.name;
  		params=[scope.date.startTime,scope.date.endTime];
	  	if(i==10){
	  		title='借阅';
	  	}else if(i==11){
	  		params.push(scope.people);
	  		title=scope.peopleName+'借阅';
	  	}else if(i==12){
	  		params.push(scope.store);
	  		title=scope.storeName+'借阅';
	  	}else if(i==13){
	  		params.push(scope.deptTeach);
	  		title=scope.deptTeachName+'借阅';
	  	}else if(i==14){
	  		params.push(scope.time,scope.people);
	  		title=scope.time+'时'+scope.peopleName+'借阅';
	  	}else if(i==15){
	  		params=[scope.schoolYear];
	  		title=scope.schoolYear+'学年借阅';
	  	}else if(i==16){
	  		params=[scope.month];
	  		title=scope.month+'月借阅';
	  	}
	  	titles=titles.split(',');
	  	titlesCode=titlesCode.split(',');
	  	var query=function(pg){
	  		htt[i].params=[pg.currentPage || 1,
	  				          pg.numPerPage || 10,
	  				        pg.totalRows||0,pg.sort,pg.isAsc
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
		  	  params:['hq:book:readStu:*']
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
var getData1=function(){
	var getd=function(i){
		 http.callService(htt[i]).success(function(data){
			 if(i==0||i==1){
				 var title=i==0?'各类型人群人均借书量趋势':'各类型借书人数比例趋势';
				 var d=[];
				 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].YEAR_MONTH,value:data[j].VALUE,fieldCode:data[j].CODE,name:data[j].NAME});  
				 }
						 var option=fomatSwtDw(getOption(d,title,'xzt'),'借书量','册');  
					
						 vm.items[i]=option;
			 }else if(i==2||i==3){
				 var d=[];
				 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].NAME,value:data[j].VALUE,name:'借阅量(册)'});  
				 }
					 var option=getOption(d,'','xzt'); 
					option.event=function(param){
							scope.schoolYear=param.name;
							scope.month=param.name;
							scope.getxqlb(i==2?15:16);
							 timeout();
						 };
					 vm.items[i]=option;
					 
					if(i==3) scope.radio3id='MONTH'; 
			 }
			
		 });	
	}
	 for(var i=0;i<4;i++){
		 htt[i].params = [deptTeach];  
		 getd(i);
	 }
};
var getData2=function(){
	var loadid=4;
	var getd=function(i){
		 mask.showLoading();
		  htt[i].params=[scope.date.startTime,scope.date.endTime];
		  htt[i].params = htt[i].params.concat([deptTeach]);  
		  http.callService(htt[i]).success(function(data){
			  loadid++;
		  if(i==5||i==6){//饼状图
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,value:data[j].VALUE,name:i==6?'人数占比(%)':'借书量(册)',fieldCode:data[j].CODE}); 
				 }
				 var option=getOption(d,'','bzt'); 
				 option.event=function(param){
						scope.peopleName=param.name;
						scope.people=param.data.nameCode;
						scope.getxqlb(11);
						 timeout();
					 };
					 if(i==6){
						 option.tooltip.formatter= "{a} <br/>{b} : {c} ";
					 }
				 vm.items[i]=option;
		  }else if(i==7){//柱状图
				 var d=[[],[],[],[]];
				 var vtil=['NUMS','NUMRATE','USERATE','RENEWRATE'];
				 var dname=['流通量(册)','流通率(‱)','利用率(‱)','续借率(‱)'];
				 for(var j=0;j<data.length;j++){
					 for(var k=0;k<d.length;k++){
						 d[k].push({field:data[j].NAME,value:data[j][vtil[k]],name:dname[k],fieldCode:data[j].CODE});  
					 }
				 } 
				 var tit="分类型展示图书";
				 vm.items[i]=[];
				 for(var k=0;k<d.length;k++){
					 var option=getOption(d[k],tit+dname[k],'zzt');
					 option.event=function(param){
							scope.storeName=param.name;
							scope.store=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
							scope.getxqlb(12);
							 timeout();
						 };
						 vm.items[i][k]=option;
				 }
				 scope.radio6id=0; 
			}else if(i==8){//柱状图
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
						 d[0].push({field:data[j].NAME,value:data[j].NUMS,name:'数量(册)',fieldCode:data[j].CODE});  
						 d[1].push({field:data[j].NAME,value:data[j].STUAVG,name:'数量(册)',fieldCode:data[j].CODE});  
				 }
				 vm.items[i]=[];
				 for(var k=0;k<d.length;k++){
					 var option=getOption(d[k],'','zzt');
					 if(k==0)option.event=function(param){
							scope.deptTeachName=param.name;
							scope.deptTeach=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
							scope.getxqlb(13);
							 timeout();
						 };
						 option.series[0].itemStyle = { normal: {label : {show: true, position: 'inside'}}};
					 vm.items[i][k]=option;
				 }
					 scope.radio7id=0;
			}else if(i==9){//线面图
				 var d=[];
				 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].TIME,value:data[j].VALUE,name:data[j].NAME,nameCode:data[j].CODE,fieldCode:data[j].CODE});  
				 }
					 var option=fomatSwtDw(getOption(d,'','xmt'),'数量','册');
					 option.event=function(param){
							scope.time=param.name;
							scope.peopleName=param.seriesName;
							scope.people=option.series[param.seriesIndex].nameCode; 
							scope.getxqlb(14);
							 timeout();
						 };
					 vm.items[i]=option;
			}else{
			  vm.items[i]=data;
			 }
		  if(loadid==8)mask.hideLoading(); 
		  });
	}
	 for(var i=4;i<10;i++){
		 getd(i); 
	 }
	 
};
getServiceData();
//getData1();
var initId=0;
var deptTeach=null;
/*监控时间*/
scope.$watch('date',function(val1,val2){
	if(val1!=null){
		scope.radio6data=null;
		scope.radio6id=null;
		scope.radio7data=null;
		scope.radio7id=null;
		if(initId>0)getData2();
	}
},true);
/*监控dept*/
scope.$watch('deptResult',function(val1,val2){
	if(val1==null||val1.length==0)return;
		 deptTeach=val1[0];
		 scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
		 scope.radio6data=null;
			scope.radio6id=null;
			scope.radio7data=null;
			scope.radio7id=null;
			scope.radio3data=null;
			scope.radio3id=null;
		 if(initId>0){
			 getData2();
			 getData1();
		 }
},true);
//初始化数据
var initData=function(){
	if(initId==0&&scope.date!=null&&deptTeach!=null){
		getData1();getData2();initId++;
	}else{
		setTimeout(initData,100);
	}
};
initData(); 
scope.$watch('radio6id',function(val1,val2){
	if(val1!=null){
		scope.radio6data=angular.copy(vm.items[7][Number(scope.radio6id)]);
	}
},true);
scope.$watch('radio7id',function(val1,val2){
	if(val1!=null){
		scope.radio7data=angular.copy(vm.items[8][Number(scope.radio7id)]);
	}
},true);
scope.$watch('radio3id',function(val1,val2){
	if(val1!=null){
		scope.radio3data=angular.copy(vm.items[val1=='year'?2:3]);
	}
},true);
}]);