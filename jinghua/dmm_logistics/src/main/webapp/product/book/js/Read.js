app.controller("BookReadController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="bookBorrowService?"; 
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
	               ];
	//下钻
	  scope.getxqlb=function(i){
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
	  		params.push(scope.schoolYear);
	  		title=scope.schoolYear+'学年借阅';
	  	}else if(i==16){
	  		params.push(scope.month);
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
						 d.push({field:data[j].SCHOOLYEAR,value:data[j].VALUE,fieldCode:data[j].CODE,name:data[j].NAME});  
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
					 
					if(i==2) scope.radio3id='year'; 
			 }
			
		 });	
	}
	 for(var i=0;i<4;i++){
		 getd(i);
	 }
};
var getData2=function(){
	var getd=function(i){
		  htt[i].params=[scope.date.startTime,scope.date.endTime];
		  http.callService(htt[i]).success(function(data){
		  if(i==5||i==6){//饼状图
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,value:data[j].VALUE,name:i==6?'人数占比(‱)':'借书量(册)',fieldCode:data[j].CODE}); 
				 }
				 var option=getOption(d,'','bzt'); 
				 option.event=function(param){
						scope.peopleName=param.name;
						scope.people=param.data.nameCode;
						scope.getxqlb(11);
						 timeout();
					 };
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
				 var d=[];
				 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].NAME,value:data[j].VALUE,name:'数量(册)',fieldCode:data[j].CODE});  
				 }
					 var option=getOption(d,'','zzt');
					 option.event=function(param){
							scope.deptTeachName=param.name;
							scope.deptTeach=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
							scope.getxqlb(13);
							 timeout();
						 };
						 option.series[0].itemStyle = { normal: {label : {show: true, position: 'inside'}}};
					 vm.items[i]=option;
				 
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
		  mask.hideLoading(); 
		  });
	}
	 for(var i=4;i<10;i++){
		 mask.showLoading();
		 getd(i); 
	 }
	 
};
getServiceData();
getData1();
/*监控时间*/
scope.$watch('date',function(val1,val2){
	if(val1!=null){
		scope.radio6data=null;
		scope.radio6id=null;
		getData2();
	}
},true);
scope.$watch('radio6id',function(val1,val2){
	if(val1!=null){
		scope.radio6data=angular.copy(vm.items[7][Number(scope.radio6id)]);
	}
},true);
scope.$watch('radio3id',function(val1,val2){
	if(val1!=null){
		scope.radio3data=angular.copy(vm.items[val1=='year'?2:3]);
	}
},true);
}]);