app.controller("dormEmployController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	var initId=0;
	 var vm = scope.vm = {};
	var initparams=function(){
		scope.pmInt=50;
		  vm.items = [];
			scope.page = {
					  totalPages : 0,
					  totalRows : 0,
					  currentPage : 1,
					  numPerPage : 5000,
					  conditions : []
			 	};
	}
	  var htt=[];
	  var httservice="dormEmployeeService?"; //宿舍使用情况
	  var methods=[	
	        	'getDormInfo',		//0通过当前宿舍获取住宿基本信息
	        	'getDormByType',		//1 通过当前宿舍获取子节点学生住宿情况
	        	'getDormByNews',	//2通过当前宿舍获取子节点迎新力量
	        	'getDormByStuType',	//3通过当前宿舍和学生分布类型获取学生在该类型下的分布情况
	        	
	        	'getDormTopByGroup', //4通过当前宿舍获取明细分组
	        	'getDormTopPage', //5通过当前宿舍获取明细
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
	var getDeptData=function(){
		http.callService({
			  service:'dormEmployeeService?getDormTree',
			  params:[]//:'+method+':*']
		  }).success(function(data){
			var getChildren=function(data){
				var item=angular.copy(data);
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
		getDeptData();
		
		scope.getxq=function(code){
			scope.pageType=code;
			var i=5;scope.pageId='';
			if(code=='LY'||code=='QS'){
				i=4;
			}
			scope.getxqlb(i);
		}
		
		  scope.getxqlb=function(i){
			  	var params=[];
			  	var title='住宿详情';
			  	var titles='';
			  	var titlesCode='';
		  		if(i==4){
			  		params=[];
			  		 titles='名称,床位数,空床位数';
				  	 titlesCode='NAME,CW_SUM,KCW_SUM';
			  	}else if(i==5){
			  		params=[scope.pageId];
			  		 titles='楼宇,楼层,房间,床位,学号,姓名,性别,所属学院,专业,班级';
				  	 titlesCode='LY_NAME,LC_NAME,QS_NAME,BERTH_NAME,STU_ID,STU_NAME,SEX_NAME,DEPT_NAME,MAJOR_NAME,CLASS_NAME';
			  	}
			  	titles=titles.split(',');
			  	titlesCode=titlesCode.split(',');
			  	var query=function(pg){
			  		htt[i].params=[pg.currentPage || 1,
			  				          pg.numPerPage || 10,  pg.totalRows||0,pg.sort,pg.isAsc,
			  				        deptTeach,scope.pageType
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
		var getvmData=function(i,stuType){
			http.callService(htt[i]).success(function(data){	
				if(i==0){
					 vm.items[i]=data;
				}else if(i==1||i==2){
						var d=[];
						 for(var j=0;j<data.length;j++){
							 if(i==1){
								 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].CW_SUM-data[j].KCW_SUM,name:'入住数(床)'}); 
							 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].RZ_RATE,name:'入住率(%)'}); 
							 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].KCW_SUM,name:'空床位数(床)'}); 
							 }else{
								 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].CW_SUM,name:'床位数(床)'}); 
							 }
						 }
							 var option=getOption(d,'',i==1?'zxt':'zzt');
							 option.series[0].itemStyle = { normal: {label : {show: true, position: 'inside'}}};
							 if(i==1){
								 option.series[0].stack= 'sum';option.series[2].stack= 'sum';
								 option.series[2].itemStyle= {
						                normal: {
						                    color: 'tomato',
						                    barBorderColor: 'tomato',
						                    barBorderWidth: 6,
						                    barBorderRadius:0,
						                    label : {
						                        show: true, position: 'top'
						                    },
						                    
								 
						                }
						            };
								// option.series[2].itemStyle = { normal: {label : {show: true, position: 'top'}}};
							 }
							 option.event=function(param){
								 scope.emType=angular.copy(scope.DeptGroup);
								 if(i==1){
									 var si=param.seriesIndex;
									 if(si==0){
										 scope.pageType='DORM_RZ';
									 }else if(si==2){
										 scope.pageType='DORM_WRZ';
									 }else{
										 return;
									 }
								 }else{
									 scope.pageType='NEWS';
								 }
									scope.pageId=option.series[param.seriesIndex].dataCode[param.dataIndex]; 
									scope.getxqlb(5);
									 timeout();
								 };
							 option.dataZoom.show=false;
							 vm.items[i]= option;
				}else if(i==3){
					var d=[];
					 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:'人数(人)'}); 
					 }
					 var lx='zzt';
					 var bztypes=['SEX','EDU','NJ'];
					 for(var type in bztypes){
						 bztypes[type]==stuType?lx='bzt':null;
					 }
					 var option=getOption(d,'',lx);
					 if(stuType=='MZ'||stuType=='DEPT'){
						 option.series[0].itemStyle = { normal: {label : {show: true, position: 'top'}}};
					 }
					 option.event=function(param){
							scope.pageType=stuType;
							scope.pageId=lx=='bzt'?param.data.nameCode:option.series[param.seriesIndex].dataCode[param.dataIndex]; 
							 scope.getxqlb(5);
							 timeout();
						 };
					 vm.items[i][stuType]=option;
					 if(stuType=='DEPT'){
						 scope.radio0=stuType;
					 }
				}
				 mask.hideLoading(); 
			  })
			};
		var getparams=function(i){
			 var params=[];
			 if(i==0||i==1||i==2){
				 params=[deptTeach];
			 }else if(i==3){
				 var stuTypes=['SEX','EDU','NJ','DEPT','MAJOR','CLASS','MZ']
				 vm.items[i]={};
				 for(var j=0;j<stuTypes.length;j++){
					 htt[i].params =[deptTeach,stuTypes[j]];
					 getvmData(i,stuTypes[j]);
				 }
				return; 
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
	
	var m=[0,1,2,3];
	ydsrqs=function(id,name){
		stuId=id;
		stuName=name;
		getAllData(m1);
	}
	var startDate=null;
	var endDate=null;	
	var deptTeach=null;
		/*监控时间*/
		scope.$watch('date',function(val1,val2){
			if(val1==null)return; 
			initparams();
			startDate=angular.copy(val1.startTime);
			endDate=angular.copy(val1.endTime);
			if(initId>0)getAllData(m);
		},true);
		/*监控dept*/
		scope.$watch('deptResult',function(val1,val2){
			if(val1==null||val1.length==0)return; 
			initparams();
			deptTeach=val1[0];
				 //scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
				if(initId>0){
					getAllData(m);
				}
		},true);
		//初始化数据
		var initData=function(){
			if(initId==0&&deptTeach!=null){
				initparams();getAllData(m);initId++;
			}else{
				setTimeout(initData,100);
			}
		};
		initData(); 
}]);
