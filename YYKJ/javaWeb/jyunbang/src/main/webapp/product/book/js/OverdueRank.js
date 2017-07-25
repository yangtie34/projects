app.controller("OverdueRankController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 mask.showLoading();
	 var vm = scope.vm = {};
	  vm.items = [];
	  scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 5,
			  conditions : []
	 	};
	  scope.schoolYears=[];
	  var getxn=function(){
		  var date=new Date;
		  var month=date.getMonth()+1;
		  var year=date.getFullYear(); 
		  if(month>9)year++;
		  for(var i=0;i<6;i++){
			  year--;
			  var aaa=year+"-"+(year+1);
			  scope.schoolYears.push(aaa);  
		  }
	  }
	  getxn();
	  var startTime=null;
	  var endTime=null;
	  var initId=0;
	  scope.schoolYear=scope.schoolYears[1];
	  var htt=[];
	  var httservice="bookOutTimeTopService?"; 
	  var methods=[	
	        	'getOutTimeTop',				//0图书逾期Top10排名
	        	'getOutTimeTopByPeopleType',	//1类型对比
	        	'getOutTimeTopByPeopleTypeTrend',//2类型对比趋势
	        	'getOutTimeTopBySex',			//3性别对比
	        	'getOutTimeTopBySTrend',		//4性别对比趋势
	        	'getOutTimeTopByGrade',			//5年级对比
	        	'getOutTimeTopByGradeTrend',	//6年级对比趋势
	        	'getOutTimeTopByOf',			//7所属情况对比
	        	'getOutTimeTopByOfTrend',		//8所属情况对比趋势
	        	//xiazuan
	        	'getOutTime',	//9获取借阅列表
	        	'getAllTop',		//10获取Top10名次列表
	        	'getEdu',		//11
	        	'getSex',		//12
	        	'getGrade',		//13
	        	'getOf',		//14
	               ];
	//下钻
	  scope.getxqlb=function(i){
	  	var params=[];
	  	var title='';var titles='';var titlesCode='';
	  	titlesCode=borrow_detil.code;
  		titles=borrow_detil.name;
  		if(i==9||i==10){
  			params=[ scope.type1,scope.numOrDay,startTime,endTime,scope.id];
  			if(i==10){
  				params=[ scope.type1,scope.numOrDay,scope.id];
  				titlesCode=Top10_detil.code;
  		  		titles=Top10_detil.name;
  			}
  			title='借阅';
  		}else if(i==11||i==12||i==13){
	  		params=[ scope.type1,scope.numOrDay, scope.oftem,startTime,endTime,Number(scope.rank),scope.value];
	  		if(scope.type1=='stu'){
	  			titlesCode=typeStu_detil.code;
  		  		titles=typeStu_detil.name;
	  		}else{
	  			titlesCode=typeTea_detil.code;
  		  		titles=typeTea_detil.name;
	  		}
	  		title='借阅';
	  	}else if(i==14){
	  		params=[ scope.type1,scope.numOrDay, scope.oftemfa, scope.oftemchi,startTime,endTime,Number(scope.rank)];
	  		if(scope.type1=='stu'){
	  			titlesCode=typeStu_detil.code;
  		  		titles=typeStu_detil.name;
	  		}else if(scope.type1=='tea'){
	  			titlesCode=typeTea_detil.code;
  		  		titles=typeTea_detil.name;
	  		}else{
	  			titlesCode=typeBook_detil.code;
  		  		titles=typeBook_detil.name;
	  		}
	  		title='借阅';
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
	  		  d.func=query;	  //d.Sort=false;
	  		  scope.pagexq=angular.copy(d);
	  	  });
	  	};
	  	scope.pagexq={func:query};
	  }	   	
	  scope.getxqById=function(id,i){
		  scope.id=id;
		  scope.getxqlb(i);
	  }
	  
	  var getServiceData=function(){
	  	  for(var i=0;i<methods.length;i++){
	  		  htt.push({
	  			  service:httservice+methods[i],
	  			  params:[]
	  		  });
	  	 }
	  };
	  scope.numOrDayClick=function(nd){
		  scope.numOrDay=nd;
			getAllData(m1); 
	  };
	  scope.numOrDay='num';
	  scope.qushiClick=function(index){
	  	scope.qushiData=angular.copy(vm.items[index]);
	  	scope.qsDiv=true;
	  }
	  scope.zkygjtitle=="收缩月度"
	  scope.zkygjdiv=function(id){
	  	scope[id]=!scope[id];
	  	/*scope.zkygjtitle=scope.zkygjtitle=="展开查看"?'收缩月度':'展开查看';*/
	  	$("#"+id).toggle("slow");
	  };
	  scope.zkygjdiv();
	  var getDeptData=function(method){
		  http.callService({
		  	  service:'deptTreeService?get'+method,
		  	  params:['hq:book:overdueRank:'+method+':*']
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
	  	scope.mutiSource=[];
		var setCheck=function(item){
			if(item.level!=null)
			item.checked=true;
			return item;
		}
	  	scope.mutiSource.push({
	  		queryName : '所属部门',
	  		queryCode : "comboTree",
	  		queryType : "comboTree",
	  		items :setCheck(getChildren(data))
	  	});
	  })
	  }
	  scope.xnulshow=false;
	  getServiceData();
	  scope.oftem={id:0,level:0,istrue:0};
	  scope.rank=10;
	  scope.type1='stu';
	  scope.type2='stu';
	  scope.xnulClick=function(item){
	  	scope.schoolYear=item;
	  	scope.xnulshow=false;
	  };
	  var getvmData=function(i){
	  	http.callService(htt[i]).success(function(data){
	  		 if(i==0){
	  			  vm.items[i]=data.resultList;
	  			  scope.page.totalRows=data.totalRows;
	  			  scope.page.totalPages=data.totalPages;

	  		 }else if(i==1||i==3||i==5){//饼状图
	  			 var d=[];
	  			 for(var j=0;j<data.length;j++){
	  				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:'人次(人)'}); 
	  			 }
	  			 var option=getOption(d,'','bzt').saveAsImage(i==1?"学历对比":"性别对比"); 
				 option.event=function(param){
						scope.value=param.data.nameCode;
						scope.getxqlb(i==1?11:i==3?12:13);
						 timeout();
					 };
				 vm.items[i]=option;
	  		 }else if(i==7){//柱状图
	  			 var d=[];
	  			 for(var j=0;j<data.length;j++){
	  				 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:'数量(册)'}); 
	  			 }
	  			 var option=getOption(d,'','zzt'); 
				 option.event=function(param){
					 scope.oftemfa=angular.copy(scope.oftem);
					 scope.oftemchi=angular.copy(scope.oftem);
					 scope.oftemchi.level++;
					 scope.oftemchi.name=param.name;
					 scope.oftemchi.id=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
					 if(scope.type1=='book'){
						 scope.oftemfa={store:'all'};
						 scope.oftemchi={store:scope.oftemchi.id};
					 }
					scope.getxqlb(14);
						 timeout();
					 };
					 vm.items[i]=option.saveAsImage(scope.type1=='book'?'所属类型对比':'所属学院对比');
	  		 }else if(i==2||i==4||i==6||i==8){//趋势图
	  			 var d=[];
	  			 for(var j=0;j<data.length;j++){
	  				 d.push({field:data[j].SCHOOLYEAR,value:data[j].VALUE,name:data[j].NAME}); 
	  			 }
	  			 vm.items[i]=fomatSwtDw(getOption(d,(i==2?'类型':i==4?'性别':i==6?'年级':'所属情况')+'对比趋势','xzt'),i==8?'数量':'人次',i==8?'册':'人'); 
	  		 }else if(i==9||i==10||i==11){
	  			  vm.items[i]=data;
	  		 } 
	  		 mask.hideLoading(); 
	  	  });
	  	
	  };
	  var getparams=function(i){
	  		 var params=[];
	  		 if(i==0){
	  			 params=[scope.page.currentPage || 1,
	  			         scope.page.numPerPage || 10,0,
	  			         scope.type1,scope.numOrDay,
	  			         scope.oftem,
	  			         startTime,endTime,Number(scope.rank)];
	  		 }else if(i==1||i==3||i==5||i==7){
	  			 params=[scope.type1,scope.numOrDay,
	  			         scope.oftem,
	  			         startTime,endTime,scope.rank];
	  		 }else if(i==2||i==4||i==6||i==8){
	  			 params=[scope.type1,scope.numOrDay,
	  			         scope.oftem,
	  			         scope.rank];
	  		 }else if(i==9||i==10||i==11){
	  			 params=[scope.type2,scope.numOrDay,
	  			         scope.schoolYear];
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
	  var m1=[0,1,2,3,4,5,6,7,8];
	  var m2=[];
	  //var m2=[9,10,11];
	  /*监控时间*/
	  scope.$watch('date',function(val1,val2){
	  	if(val1!=null){
	  		startTime=angular.copy(val1.startTime);
	  		endTime=angular.copy(val1.endTime);
	  		if(initId!=0){
	  			getAllData(m1);initId++;}
	  	}
	  },true);
	  	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  	  scope.$watch('page',function(val1,val2){
	  		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
	  			  getparams(0);
	  		  }
	  		  if(angular.toJson(val1.conditions)!= angular.toJson(val2.conditions)){
	  			  if(val1.conditions.length>0){
	  				 for(var i=0;i<val1.conditions.length;i++){
	  					 	switch(val1.conditions[i].queryType){
	  					 		case "comboTree":
	  							scope.oftem=val1.conditions[i];
	  							 scope.deptlname=scope.oftem.level=='0'?'学院':scope.oftem.level=='1'?'专业':scope.oftem.level=='2'?'专业':'班级';
	  					 		break;
	  					 		/*case "comboSelect":
	  					 		scope.searchUser.role_ids=val1.conditions[i].id;
	  					 		break;*/
	  					 	}
	  					 }
	  			 if(scope.oftem.level==null)scope.oftem={id:0,level:0,istrue:0};
				  getAllData(m1);initId++;
	  			  }
	  		  }
	  	  },true);
	  	  var type1Titles=[['排名','学生号','学生名称','所属学院','借书次数','月度上榜次数'],
	  	                   ['Id','教师号','学生名称','所属学院','借书次数','月度上榜次数'],
	  	                   ['Id','生号','学生名称','所属学院','借书次数','月度上榜次数']];
	  	  scope.$watch('type1',function(val1,val2){
	  	  	if(val1!=null){
	  	  		scope.type1Title=type1Titles[val1=='stu'?0:val1=='tea'?1:2];
		  		if(val1=='book'){
		  			 getAllData(m1);return;
		  		}
		  		if(val1=='stu')getDeptData('DeptTeach');
		  		if(val1=='tea')getDeptData('Dept');
		  		scope.page.conditions=[];
		  		scope.mutiSource=null;
	  	  	}
	  	  },true);
	  	  scope.$watch('rank',function(val1,val2){
	  		  	if(val1!=null){
	  		  		if(initId!=0) getAllData(m1);
	  		  	}
	  		  },true);
	  	  var initId2=0;
	  	  scope.$watch('schoolYear',function(val1,val2){
	  		  if(initId2!=0)
	  		  		getAllData(m2);
	  		  },true);
	  	  scope.$watch('type2',function(val1,val2){
	  	  	if(val1!=null){
	  	  		getAllData(m2);initId2++;
	  	  	}
	  	  },true);
}]);