app.controller("LibraryRkeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	var initId=0;
	 var vm = scope.vm = {};
	 var xldm=null;
	 http.callService({
	 	  service:'payPowerService?getxldm',
	 	  params:[]
	 }).success(function(data){
	 	xldm=data;
	 });
	var initparams=function(){
		scope.radio1id=null;
		if(xldm==null)return;
		var xl={};
	 	xl[xldm.yjs]={ico:'11',name:'研究生',show:false};
	 	xl[xldm.bk]={ico:'21',name:'本科',show:true};
	 	xl[xldm.dz]={ico:'31',name:'大专',show:true};
	 	xl['null']={ico:'wwhz',name:'未维护',show:false};
	 	var mz={};
	 	mz[xldm.hz]={ico:'hanz',name:'汉族',show:true};
	 	mz[xldm.zzcode]={ico:'minority',name:xldm.zzname,show:true};
	 	mz['null']={ico:'wwhz',name:'其他',show:true};
		  vm.items = [{
			 all:{all:{ico:'all',name:'整体',show:true}},
			 xb:{1:{ico:'man',name:'男生',show:true},
				 2:{ico:'female',name:'女生',show:true},
				 'null':{ico:'wwhz',name:'未维护',show:false}},
			 xl:xl,
			 mz:mz
		  }];
	}
	scope.qushiDs={};
	  scope.qushiClick=function(type_code,flag,lx){
		  mask.showLoading();
		  var xrqs=function(){
			  scope.qushiData=scope.qushiDs[flag][type_code][lx];
				 mask.hideLoading(); 
				 scope.qsDiv=true; 
		  };
		  scope.qushiDs[flag]=scope.qushiDs[flag]||{};
		  scope.qushiDs[flag][type_code]=
			  scope.qushiDs[flag][type_code]||{};
		  if(scope.qushiDs[flag][type_code][lx]!=null){
			  xrqs();
			  return;
		  }
		  http.callService({
			  service:'libraryRkeService?getStuRkeTrend',
			  params:[deptTeach,flag,type_code]
		  }).success(function(data){
			  var lxs=['all','avg','rate'];
			  for(var i=0;i<lxs.length;i++){
				  var d=[];
				  for(var j=0;j<data.length;j++){
						if(lx==lxs[i]) d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:'出入总次数(次)'}); 
						if(lx==lxs[i]) d.push({field:data[j].YEAR_MONTH,value:data[j].AVG_COUNT,name:'人均出入次数(次)'}); 
						if(lx==lxs[i]) d.push({field:data[j].YEAR_MONTH,value:data[j].INRATE,name:'入馆率(%)'}); 
					 } 
				  scope.qushiDs[flag][type_code][lxs[i]]=getOption(d,'分类型图书馆出入趋势统计','xzt');
			  }
			  xrqs();
		  });
	  }
	scope.xbxl=function(code){
		scope.xbxlcode=code;
		var l=1;
		for(var key in vm.items[0][code]){
			if(vm.items[0][code][key].show==true)l++;
		}
		var c=l==3?'col-md-4':'col-md-3';
		if(scope.xbxlclass!=c){
			scope.xbxlclass=c;
			c=angular.copy(vm.items[0].all);
			vm.items[0].all=null;
			vm.items[0].all=angular.copy(c);
		}
	}
	
	  var htt=[];
	  var httservice="libraryRkeService?"; //图书馆门禁分析
	  var methods=[	
	        	'getRkeInfo',		//0出入门禁信息
	        	'getCountsByDept',		//1 分学院学生出入对比分析
	        	'getCountsForYears',	//2分学年学生出入对比
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
	var getDeptData=function(method){
		http.callService({
			  service:'deptTreeService?get'+method,
			  params:['hq:book:LibraryRke:*']//:'+method+':*']
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
		
		
		
		var getvmData=function(i,lb){
			http.callService(htt[i]).success(function(data){	
				if(i==0){
					var vmd=vm.items[0][lb];
					for(var key in data){
						switch(key){
						case'ALL_COUNT':
						case'AVG_COUNT':
						case'INRATE':
							for(var j=0;j<data[key].length;j++){
								vmd[data[key][j].CODE][key]=data[key][j][key];
							}
							break;
						case'week':
						case'hour':
							var d={};
							for(var j=0;j<data[key].length;j++){
								var code=data[key][j].CODE;
								d[code]=d[code]||[];
								 d[code].push({field:data[key][j].FIELDNAME,value:data[key][j].COUNT_,name:'次数(次)'}); 
							}
							for(var k in d){
								vmd[k][key]=getOption(d[k],'',key=='week'?'zzt':'xqs').saveAsImage(vmd[k].name+(key=='week'?"分周次统计出入频次":"分时段统计出入频次"));
							}
							break;
						case'csqj':
							var d={};
							for(var j=0;j<data[key].length;j++){
								var code=data[key][j].CODE;
								d[code]=d[code]||[];
								 d[code].push({field:data[key][j].FIELD,value:data[key][j].COUNT_,name:'次数(次)'}); 
							}
							for(var k in d){
								vmd[k][key]=getOption(d[k],'','xzt').saveAsImage(vmd[k].name+("入馆次数区间分布"));
							}
							break;
						}
					}
					if(lb=='xb')scope.xbxl('xb');
				}else if(i==1){
					vm.items[i]=[];
						 var d=[[],[],[]];
						 for(var j=0;j<data.length;j++){
							 d[0].push({field:data[j].DEPT_NAME,fieldCode:data[j].DEPT_CODE,value:data[j].ALL_COUNT,name:'总次数(次)'}); 
							 d[1].push({field:data[j].DEPT_NAME,fieldCode:data[j].DEPT_CODE,value:data[j].AVG_COUNT,name:'人均(次)'}); 
							 d[2].push({field:data[j].DEPT_NAME,fieldCode:data[j].DEPT_CODE,value:data[j].INRATE,name:'入管率(%)'}); 
						 }
						 var saveAsImageName=['分学院学生总出入次数对比分析','分学院学生人均出入次数对比分析','分学院学生入馆率(%)对比分析'];
						 for(var j=0;j<d.length;j++){
							vm.items[i][j]=getOption(d[j],'','zzt').saveAsImage(saveAsImageName[j]);
						 }
						 scope.radio1id=0;
				}else if(i==2){
				var d=[];
				 for(var j=0;j<data.length;j++){
					 d.push({field:data[j].YEAR,fieldCode:data[j].YEAR,value:data[j].ALL_COUNT,name:'总次数(次)'}); 
					 d.push({field:data[j].YEAR,fieldCode:data[j].YEAR,value:data[j].AVG_COUNT,name:'人均(次)'}); 
				 }
				 vm.items[i]=getOption(d,'','zxt').saveAsImage("分学年学生出入对比分析");
				}
				 mask.hideLoading(); 
			  })
			};
		var getparams=function(i){
			 var params=[];
			 if(i==0){
				 params=[startDate,endDate,deptTeach,'all']; htt[i].params=params;getvmData(i,'all');
				 params=[startDate,endDate,deptTeach,'xb'];	 htt[i].params=params;getvmData(i,'xb');
				 params=[startDate,endDate,deptTeach,'xl'];  htt[i].params=params;getvmData(i,'xl');
				 params=[startDate,endDate,deptTeach,'mz'];
			 }else if(i==1){
				 params=[startDate,endDate,deptTeach];
			 }else if(i==2){
				 params=[deptTeach];
			 }
			 htt[i].params=params;
			 getvmData(i,'mz');
	};
	var getAllData=function(m){
		for(var i=0;i<m.length;i++){
			 mask.showLoading();
			getparams(m[i]);
		}
	};
	
	var m=[0,1],m1=[2];
	var startDate=null;
	var endDate=null;	
	var deptTeach=null;
		/*监控时间*/
		scope.$watch('date',function(val1,val2){
			if(val1==null)return; initparams();
			startDate=angular.copy(val1.startTime);
			endDate=angular.copy(val1.endTime);
			if(initId>0)getAllData(m);
		},true);
		/*监控dept*/
		scope.$watch('deptResult',function(val1,val2){
			if(val1==null||val1.length==0)return; initparams();
				 deptTeach=val1[0];
				 scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
				if(initId>0){
					getAllData(m);getAllData(m1);
				}
				
		},true);
		scope.$watch('radio1id',function(val1,val2){
			if(val1!=null){
				scope.radio1data=angular.copy(vm.items[1][Number(scope.radio1id)]);
			}
		},true);
		//初始化数据
		var initData=function(){
			if(initId==0&&startDate!=null&&deptTeach!=null&&xldm!=null){
				initparams();getAllData(m);getAllData(m1);initId++;
			}else{
				setTimeout(initData,100);
			}
		};
		initData(); 
}]);
