var ydsrqs=null;
app.controller("byPortController", [ "$scope","dialog",'mask','$timeout','http','timeAlert','exportPage',function(scope,dialog,mask,timeout,http,timeAlert,exportPage) {
	mask.showLoading();
	 	
	scope.page0 = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 5000,
			  conditions : []
	 	};
	  scope.page1=angular.copy(scope.page0);
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="diningRoomService?"; //餐厅窗口分析
	  var methods=[	
	        	'getDiningRoom',		//0餐厅情况
	        	'getDiningPort',		//1 窗口情况
	        	'getDiningRoomTrend',	//2餐厅月度趋势
	        	'getDiningPortTrend',	//3窗口月度趋势
	               ];
	  ydsrqs=function(id,name){
		  scope.ydqsName=name;
		  scope.qushiClick(id,scope.flag,scope.queryType);  
	  }
	  scope.qushiClick=function(type_code,flag,queryType){
		  mask.showLoading();
		  http.callService({
			  service:'cardTrendService?getEatTrend',
			  params:[type_code,flag,queryType]
		  }).success(function(data){
			  var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:"总次数(次)"}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_MONEY,name:'总金额(元)'}); 
					/* d.push({field:data[j].YEAR_MONTH,value:data[j].ONE_MONEY,name:'笔均消费金额(元)'}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].DAY_COUNT,name:'日均消费笔数(笔)'}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].DAY_MONEY,name:'日均消费金额(元)'}); */
				 }
				 scope.ydqs=getOption(d,'','zxt');
				 mask.hideLoading(); 
				 scope.ydqsdiv=true;
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
		  params:['hq:card:byPort:*']//:'+method+':*']
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
	//getDeptData("DeptTeach");
	var portQsDivShow=false;
	scope.getPortQs=function(id){
		portId=id;
		if(scope.ckDivShow==false){
			getparams(2);
		}else{
			getparams(3);
		}
	};
	scope.ymdata={pglist:{ct:vm.items[0],ck:vm.items[1]},
					  pg:{ct:scope.page0,ck:scope.page1},
					ydqu:{ct:vm.items[2],ck:vm.items[3]}};
	scope.tjData={ct:0,ck:0,bs:0,je:0};
	scope.tableData={};
	var getvmData=function(i){
		http.callService(htt[i]).success(function(data){
			 if(i==0){
				 vm.items[i]=data.resultList;
				 scope.tjData.ct=data.resultList.length;
				 for(var j=0;j<scope.tjData.ct;j++){
					 scope.tjData.bs+=data.resultList[j].ALL_COUNT;
					 scope.tjData.je+=data.resultList[j].ALL_MONEY;
				 }
				  scope.page0.totalRows=data.totalRows;
	  			  scope.page0.totalPages=data.totalPages;
	  			scope.tableData.ct={
	  					titles:{
							code:"INDEX,NAME,ALL_MONEY,ALL_COUNT,DAY_MONEY,DAY_COUNT,ONE_MONEY,CKQS",
							name:"序号,餐厅名称,总消费,总消费次数,日均消费,日均消费次数,笔均消费金额,查看趋势",
						},
						func:function(){
							 mask.showLoading();
							 var invoke=angular.copy(htt[i]);
							 invoke.params[0]=1;
							 invoke.params[1]=scope.page0.totalRows;
							 var ex={
							 	 invoke:invoke,
							 	 title:'餐厅消费情况',
							 	 titles:'id,餐厅名称,总消费,总消费次数,日均消费,日均消费次数,笔均消费金额'.split(','),
							 	 titleCodes:'ID,NAME,ALL_MONEY,ALL_COUNT,DAY_MONEY,DAY_COUNT,ONE_MONEY'.split(',')
							 }
							 mask.hideLoading(); 
							 exportPage.callService(ex).success(function(ret){
							 })
						},
						data:function(){
							var d=angular.copy(data.resultList);
							for(var i =0;i<d.length;i++){
								d[i].INDEX=i+1;
								d[i].CKQS="<a href='javascript:void(0)' onclick='ydsrqs(\""+d[i].ID+"\",\""+d[i].NAME+"\");'><img src='"+base+"resource/images/money-icon.png' title='月度收入趋势'></a>";
							}
							return d;
						}()
						};
			 }else if(i==1){
				 vm.items[i]=data.resultList;
				 scope.ckrq=getListMapTop(data.resultList,"ALL_COUNT",10);//人气窗口
				 scope.ckth=getListMapTop(data.resultList,"ALL_MONEY",10);//土豪窗口
				 scope.pagerq = {
						  totalPages : scope.ckrq.length/10+(scope.ckrq.length%10>0?1:0),
						  totalRows : scope.ckrq.length,
						  currentPage : 1,
						  numPerPage : 10,
				 	};
				 scope.pageth = {
						  totalPages : scope.ckth.length/10+(scope.ckth.length%10>0?1:0),
						  totalRows : scope.ckth.length,
						  currentPage : 1,
						  numPerPage : 10,
				 	};
				 scope.tjData.ck=data.resultList.length;
				  scope.page1.totalRows=data.totalRows;
	  			  scope.page1.totalPages=data.totalPages;
	  		
	  			scope.tableData.ck={
	  					titles:{
							code:"INDEX,NAME,ALL_MONEY,ALL_COUNT,DAY_MONEY,DAY_COUNT,ONE_MONEY,CKQS",
							name:"序号,窗口名称,总消费,总消费次数,日均消费,日均消费次数,笔均消费金额,查看趋势",
						},
						func:function(){
							 mask.showLoading();
							 var invoke=angular.copy(htt[i]);
							 invoke.params[0]=1;
							 invoke.params[1]=scope.page1.totalRows;
							 var ex={
							 	 invoke:invoke,
							 	 title:'窗口消费情况',
							 	 titles:'id,窗口名称,总消费,总消费次数,日均消费,日均消费次数,笔均消费金额'.split(','),
							 	 titleCodes:'ID,NAME,ALL_MONEY,ALL_COUNT,DAY_MONEY,DAY_COUNT,ONE_MONEY'.split(',')
							 }
							 mask.hideLoading(); 
							 exportPage.callService(ex).success(function(ret){
							 })
						},
						data:function(){
							var d=angular.copy(data.resultList);
							for(var i =0;i<d.length;i++){
								d[i].INDEX=i+1;
								d[i].CKQS="<a href='javascripe:void(0)' onclick='ydsrqs(\""+d[i].ID+"\",\""+d[i].NAME+"\");'><img src='"+base+"resource/images/money-icon.png' title='月度收入趋势'></a>";
							}
							return d;
						}()
						};
			 }else if(i==2||i==3){
				 var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_MONEY,name:"总消费(元)"}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:"总消费次数(次)"}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].DAY_MONEY,name:"日均消费(元)"}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].DAY_COUNT,name:"日均消费次数(次)"}); 
					 d.push({field:data[j].YEAR_MONTH,value:data[j].ONE_MONEY,name:"笔均消费金额(元)"}); 
				 }
				 scope.ztqs=scope.ztqs||{};
				 scope.ztqs[i==2?'ct':'ck']=getOption(d,'','xzt'); 
			
			 }
			 mask.hideLoading(); 
		  });
		
	};
	var getparams=function(i){
			 var params=[];
			 if(i==0||i==1){
				 var page=angular.copy(i==0?scope.page0:scope.page1);
				 params=[page.currentPage || 1,
				         page.numPerPage || 10,0,
				         startDate,endDate,scope.queryType];
			 }else if(i==2||i==3){
				 params=[scope.queryType,portId];
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
	var m=[0,1];
	var initId=0;
	var startDate=null;
	var endDate=null;
	var portId=null;
	scope.flagClick=function(index){
		if(scope.flagIndex!=index){
			scope.ctckCode=index==0?'ct':'ck';
			scope.flagIndex=index;
			scope.flag=scope.flagList[index].code;
			scope.flagName=scope.flagList[index].name;
			scope.ydqsdiv=false;
		}
	};
	scope.queryTypeClick=function(index){
		scope.queryTypeIndex=index;
		scope.queryType=scope.queryTypeList[index].code;
	};
	scope.flagList=[{code:'eat_room',name:'餐厅'},{code:'eat_port',name:'窗口'}];
	scope.queryTypeList=[{code:'all',name:'全天'},{code:'zao',name:'早餐'},{code:'zhong',name:'午餐'},{code:'wan',name:'晚餐'}];
	scope.flagClick(0);	scope.queryTypeClick(0);
	scope.$watch('pagerq.numPerPage',function(val1,val2){
		  if(val1!=null){
			 scope.rqys=parseInt(scope.pagerq.totalRows/Number(val1))+(scope.pagerq.totalRows%Number(val1)>0?1:0);
		  }
	})
	scope.$watch('pageth.numPerPage',function(val1,val2){
		 if(val1!=null){
			 scope.thys= parseInt(scope.pageth.totalRows/Number(val1))+(scope.pageth.totalRows%Number(val1)>0?1:0);
		  }
	})
	scope.trisshow=function(index,currentPage,numPerPage){
		return (index>=((Number(currentPage)-1)*Number(numPerPage)))&&(index<(Number(currentPage)*Number(numPerPage)));
	}
	/*监控时间*/
	scope.$watch('date',function(val1,val2){
		if(val1==null)return;
		startDate=angular.copy(val1.startTime);
		endDate=angular.copy(val1.endTime);
		if(initId>0)getAllData(m);
		 scope.ydqsdiv=false;
	},true);
	/*监控queryType*/
	scope.$watch('queryType',function(val1,val2){
		if(val1==null)return;
			if(initId>0)getAllData(m);portQsDivShow=false;scope.ydqsdiv=false;
	},true);
	scope.queryTypeClick(0);
	//初始化数据
	var initData=function(){
		if(initId==0&&startDate!=null&&scope.queryType!=null){
			getAllData(m);initId++;mask.hideLoading(); 
		}else{
			setTimeout(initData,100);
		}
	};
	initData(); 
}])
var getListMapTop=function(list,key,top){
	var removearr=function(arr,index){
		var a=[];
		for(var i=0;i<arr.length;i++){
			if(i!=index)a.push(arr[i]);
		}
		return a;
	}
	var getTop=function(list,key){
		var map={flagflag:0};
		map[key]=0;
		for(var i=0;i<list.length;i++){
			if(list[i][key]>map[key]){
				map=list[i];
			}
		}
		if(map.flagflag!=null)return list[0];
		return map;
	}
	var ll=angular.copy(list);
	var l=[];
	for(var i=0;i<top;i++){
		if(ll.length==0)return l;
		var map=getTop(ll,key);
		if(l.length>0)if(map[key]==l[l.length-1][key])i--;
		map.RANK=i+1;
		l.push(map);
		for(var k=0;k<ll.length;k++){
			if( angular.toJson(ll[k])== angular.toJson(map)){
				ll=removearr(ll,k);
				break;
			}
		}
	}
	return l;
};
