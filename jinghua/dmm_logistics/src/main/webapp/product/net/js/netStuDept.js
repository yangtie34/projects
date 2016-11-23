var ydsrqs=null;
app.controller("netStuDeptController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	var initId=0;
	 var vm = scope.vm = {};
	var initparams=function(){
		  vm.items = [];
		  scope.radio1id='all';
		  scope.radio2id='on';
		  scope.radio3id='time';
	}
	  var htt=[];
	  var httservice="netStuDeptService?"; //
	  var methods=[	
	        	'getNetStus',		//0获取上网人数
	        	'getNetCounts',		//1 获取上网信息
	        	'getNetTimes',		//2获取上网信息
	        	'getNetWarnStus'	//3获取预警人数对比
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
			  params:['hq:net:netStuDept:*']//:'+method+':*']
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
				var d=[];
				if(i==0){
						 for(var j=0;j<data.length;j++){
							 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].ON_STU,name:'总人数(人)'}); 
							 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].STU_RATIO,name:'人数占比(%)'}); 
						 }
				}else if(i==1){
					 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].TIME_,name:'时长(分)'}); 
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].FLOW_,name:'流量(MB)'}); 
					 }
				}else if(i==2){
					 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].HOUR_,name:'时段(时)'}); 
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].VALUE,name:'人数(人)'}); 
					 }
				}else if(i==3){
					 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].WARN_STU,name:'人数(人)'}); 
						 d.push({field:data[j].NAME,fieldCode:data[j].CODE,value:data[j].STU_RATIO,name:'人数占比(%)'}); 
					 }
				}
				vm.items[i]=function(){
					 var option=getOption(d,'','zxt');
					 //option.dataZoom.show=false;
					 if(i==2){
						 option.yAxis[0] ={
								 name:'时段(时)',
						              type : 'value',
						            max:24,
						          splitNumber:12
						      //    boundaryGap:false
						          };
					 }
					 return option;
				 }();
				 mask.hideLoading(); 
			  })
			};
		var getparams=function(i){
			 var params=[];
			 if(i==0){
			 }else if(i==1){
				 params=[scope.radio1id];
			 }else if(i==2){
				 params=[scope.radio2id];
			 }else if(i==3){
				 params=[scope.radio3id,scope.radio3idval[scope.radio3id]];
			 }
			 htt[i].params=[startDate,endDate,deptTeach].concat(params);  
			 getvmData(i);
	};
	var getAllData=function(m){
		for(var i=0;i<m.length;i++){
			 mask.showLoading();
			getparams(m[i]);
		}
	};
	
	var m=[0,1,2,3];
	var startDate=null;
	var endDate=null;	
	var deptTeach=null;
	var initId=0;
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
		scope.$watch('radio1id',function(val1,val2){
			if(initId>0){
				getparams(1);
			}
		},true);
		scope.$watch('radio2id',function(val1,val2){
			if(initId>0){
				getparams(2);
			}
		},true);
		scope.$watch('radio3id',function(val1,val2){
			if(initId>0){
				getparams(3);
			}
		},true);
		scope.radio3idval={time:4000,flow:4000};
		scope.radio3idvalClick=function(){
			getparams(3);
		}
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
			  		scope.radio3idvalClick();
			  	}
		  };
		//初始化数据
		var initData=function(){
			if(initId==0&&startDate!=null&&deptTeach!=null){
				initparams();getAllData(m);initId++;
			}else{
				setTimeout(initData,100);
			}
		};
		initData(); 
}]);
