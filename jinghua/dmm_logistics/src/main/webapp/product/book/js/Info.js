app.controller("BookInfoController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="bookInfoService?"; 
	  var methods=[	
	        	'getNowLibraryCount',	//0获取当前图书馆信息
	        	'getNowBookState',//'getNowBookLangu',		//1获取当前中外文书籍对比——》分状态统计
	        	'getBookStateTrend',//'getBookLanguTrend',	//2获取中外文书籍对比趋势——》分状态统计
	        	'getNowReader',			//3获取当前读者人员对比
	        	'getReadersTrend',		//4获取读者人员对比趋势
	        	'getNowBookByType',		//5获取当前图书藏书类别对比
	        	'getBooksTrend',		//6获取藏书数量与价值以及增长数的趋势
	        	//下钻
	        	'getAllBook',			//7获取所有图书
	        	'getNowReader',			//8获取当前所有读者
	        	'getNowBook',			//9获取当前所有图书
	        	'getBookBySchoolYear',	//10通过学年获取所有图书
	        	'getUpBookBySchoolYear',//11通过学年获取所有新增图书
	        	'getBookByLanguage',	//12通过学年和语言类型获取所有图书
	        	'getReaderByType',		//13通过学年和读者类型获取所有读者
	        	//'getNowBookByLanguage',	//14通过当前学年和语言类型获取所有图书
	        	'getNowBookByState',	//14通过当前学年和状态获取所有图书
	        	'getNowReaderByType',	//15通过当前学年和读者类型获取所有读者
	        	'getNowBookByStore',	//16通过当前学年和藏书类别获取所有图书
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
scope.qushiClick=function(index){
	scope.qushiData=angular.copy(vm.items[index]);
	scope.qsDiv=true;
}
scope.vmitems5idex=function(index){
	 scope.vmitems5title= scope.vmitems5titles[index];
	scope.vm5idex=index;
	scope.vmitems5=angular.copy(vm.items[5][index]);
}
scope.vmitems6titles=['总数','增长量'];
scope.vmitems6idex=function(index){
	 scope.vmitems6title= scope.vmitems6titles[index];
	scope.vm6idex=index;
	scope.vmitems6=angular.copy(vm.items[6][index]);
}
var getdatas=function(){
	var getd=function(i){
		 http.callService(htt[i]).success(function(data){
			 if(i==1||i==3){//饼状图
				 var d=[];
				 var name=i==1?'数量(册)':'人次(人)';
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:name}); 
				 }
				 var option=getOption(d,'','bzt'); 
				 option.event=function(param){
						scope.schoolYear=param.name;
						//alert(param.name.indexOf('中'));
						scope.isCN=param.data.nameCode;//param.name.indexOf('中')==0?true:false;
						if(i==3){
							scope.type=param.data.nameCode;scope.typeName=param.name; 
						}
						scope.getxqlb(i==1?14:15);
						 timeout();
					 };
				 vm.items[i]=option;
			 }else if(i==2||i==4){//趋势曲线
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].SCHOOLYEAR,value:data[j].VALUE,name:data[j].NAME,nameCode:data[j].CODE,fieldCode:data[j].CODE}); 
				 }
					 var option=fomatSwtDw(getOption(d,(i==2?'书籍分状态':'读者人员')+'对比趋势','xzt'),i==2?'数量':'人数',i==2?'册':'人');
					 option.event=function(param){
							scope.schoolYear=param.name;
							scope.isCN=param.seriesName.indexOf('中')==0?true:false;
							i==4?scope.type=option.series[param.seriesIndex].dataCode[param.dataIndex]:null; 
							scope.getxqlb(i==2?12:13);
							 timeout();
						 };
					 vm.items[i]=option;
			 }else if(i==5){//柱状图
				 var d=[[],[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].NAME,value:data[j].BOOKS,name:'数量(册)',fieldCode:data[j].CODE}); 
					 d[1].push({field:data[j].NAME,value:data[j].MONEYS,name:'价值(元)',fieldCode:data[j].CODE}); 
					 d[2].push({field:data[j].NAME,value:data[j].PEOPLEHASRATE,name:'保障率(%)',fieldCode:data[j].CODE}); 
				 } 
				 scope.vmitems5titles=['分类型对比藏书数量','分类型对比藏书价值','分类型对比藏书文献保障率'];
				 var event=function(option){
					 option.event=function(param){
							scope.store=option.series[param.seriesIndex].dataCode[param.dataIndex];
							scope.storeName=param.name;
							scope.getxqlb(16);
							 timeout();
						 };
						 return option;
				 }
				 vm.items[i]=[event(getOption(d[0],'','zzt')),
						 		event(getOption(d[1],'','zzt')),
								 event(getOption(d[2],'','zzt'))];
				 scope.vmitems5idex(0);
			 }else if(i==6){//柱状图加曲线
				 var d=[[],[]];
				 for(var j=0;j<data.length;j++){
					 d[0].push({field:data[j].SCHOOLYEAR,value:data[j].BOOKS,name:'数量(万册)'}); 
					 d[0].push({field:data[j].SCHOOLYEAR,value:data[j].MONEYS,name:'价值(万元)'});
					 d[1].push({field:data[j].SCHOOLYEAR,value:data[j].UPBOOKS,name:'增长数量(万册)'}); 
					 d[1].push({field:data[j].SCHOOLYEAR,value:data[j].UPMONEYS,name:'增长价值(万元)'});
				 } 
				 vm.items[i]=[];
				for(var j=0;j<d.length;j++) {
					var option=getOption(d[j],'','zxt');
					option.event=function(param){
						scope.schoolYear=param.name;
						scope.getxqlb(vm.items[i].length==0?10:11);
						 timeout();
					 };
					 vm.items[i].push(option);
				}
				 scope.vmitems6idex(0);
			 }else{
			  vm.items[i]=data;
			 }
			 mask.hideLoading(); 
	   }); 
	}
	 for(var i=0;i<7;i++){
		 mask.showLoading();
		 getd(i);
	}
}
getServiceData();
getdatas();

//下钻
scope.getxqlb=function(i){
	var params=[];
	var title='';var titles='';var titlesCode='';
	if(i==7){
		title='图书';
		titlesCode=tushu.code;
		titles=tushu.name;
	}else if(i==8){
		title='读者明细';
		titlesCode=duzhe.code;
		titles=duzhe.name;
	}else if(i==9){
		title='当前所有图书';
		titlesCode=tushu.code;
		titles=tushu.name;
	}else if(i==10){
		params=[scope.schoolYear];
		title=scope.schoolYear+'学年图书';
		titlesCode=tushu.code;
		titles=tushu.name;
	}else if(i==11){
		params=[scope.schoolYear];
		title=scope.schoolYear+'学年新增图书';
		titlesCode=tushu.code;
		titles=tushu.name;
	}else if(i==12){
		params=[scope.isCN,scope.schoolYear];
		title=scope.schoolYear+'学年'+scope.isCN==true?'中'+'文图书':'英'+'文图书';
		titlesCode=tushu.code;
		titles=tushu.name;
	}else if(i==13){
		params=[scope.type,scope.schoolYear];
		title=scope.schoolYear+'学年'+scope.typeName+'读者';
		titlesCode=duzhe.code;
		titles=duzhe.name;
	}else if(i==14){
		params=[scope.isCN];
		title='当前学年'+scope.isCN==true?'中'+'文图书':'英'+'文图书';
		titlesCode=tushu.code;
		titles=tushu.name;
	}else if(i==15){
		params=[scope.type];
		title='当前学年'+scope.typeName+'读者';
		titlesCode=duzhe.code;
		titles=duzhe.name;
	}else if(i==16){
		params=[scope.store];
		title='当前学年'+scope.storeName+'图书';
		titlesCode=tushu.code;
		titles=tushu.name;
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


}]);