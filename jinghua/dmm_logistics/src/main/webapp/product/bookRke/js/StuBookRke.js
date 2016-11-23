var ydsrqs=null;
app.controller("StuBookRkeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
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
	  var httservice="stuBookRkeService?"; //学生出入图书馆分析
	  var methods=[	
	        	'getRankLively',		//0学生出入图书馆活跃排名top100
	        	'getBlByLB',		//1 按类别统计学生出入图书馆人数比例
	        	'getCountsByDeptLively',	//2分学院学生活跃出入对比分析
	        	'getCountsByDeptNoLively',	//3分学院学生非活跃出入对比分析
	        	'getStuRkeTrend',			//4用户按月统计趋势
	        	'changeRank'				//5修改排名值
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
			  params:['hq:book:StuBookRke:*']//:'+method+':*']
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
				if(i==0){
					 vm.items[i]=data.resultList;
					  scope.page.totalRows=data.totalRows;
		  			  scope.page.totalPages=data.totalPages;
		  			scope.tableData={
		  					titles:{
								code:"RANK_,NO_,NAME_,DEPT_NAME,MAJOR_NAME,CLASS_NAME,SEX_NAME,EDU_NAME,NATION_NAME,COUNT_,CKQS",
								name:"排名,学号,姓名,院系,专业,班级,性别,学历,民族,出入次数,查看趋势",
							},
							func:function(){
								 mask.showLoading();
								 var invoke=angular.copy(htt[i]);
								 invoke.params[0]=1;
								 invoke.params[1]=scope.page.totalRows;
								 var ex={
								 	 invoke:invoke,
								 	 title:'学生出入图书馆活跃排名情况',
								 	 titles:'排名,学号,姓名,院系,专业,班级,性别,学历,民族,出入次数'.split(','),
								 	 titleCodes:'RANK_,NO_,NAME_,DEPT_NAME,MAJOR_NAME,CLASS_NAME,SEX_NAME,EDU_NAME,NATION_NAME,COUNT_'.split(',')
								 }
								 mask.hideLoading(); 
								 exportPage.callService(ex).success(function(ret){
								 })
							},
							data:function(){
								var d=angular.copy(data.resultList);
								for(var j =0;j<d.length;j++){
									d[j].CKQS="<a href='javascript:void(0)' onclick='ydsrqs(\""+d[j].NO_+"\",\""+d[j].NAME_+"\");'><img src='"+base+"resource/images/money-icon.png' title='月度出入趋势'></a>";
								}
								return d;
							}()
							};
				}else if(i==1){
					vm.items[i]={};
					var lb=["xb","xl","mz"];
					for(var k=0;k<lb.length;k++){
						var d=[];
						var dat=data[lb[k]]
						 for(var j=0;j<dat.length;j++){
							 d.push({field:dat[j].NAME,fieldCode:dat[j].CODE,value:dat[j].COUNT_,name:'总次数(次)'}); 
							 d.push({field:dat[j].NAME,fieldCode:dat[j].CODE,value:dat[j].INRATE,name:'人数占比(‱)'}); 
						 }
						 vm.items[i][lb[k]]=function(){
							 var option=getOption(d,'','zxt');
							 option.dataZoom.show=false;
							 return option;
						 }();
					}
				}else if(i==2||i==3){
					var d=[];
					 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].DEPT_NAME,fieldCode:data[j].DEPT_CODE,value:data[j].COUNT_,name:'总次数(次)'}); 
						 d.push({field:data[j].DEPT_NAME,fieldCode:data[j].DEPT_CODE,value:data[j].INRATE,name:'人数占比(%)'}); 
					 }
					 vm.items[i]=getOption(d,'','zxt');
				}else if(i==4){
					var d=[];
					 for(var j=0;j<data.length;j++){
						 d.push({field:data[j].YEAR_MONTH,value:data[j].ALL_COUNT,name:'出入次数(次)'}); 
					 }
					 scope.qushiData=getOption(d,'按年用统计'+stuName+'出入情况','xzt');
					 scope.qsDiv=true; 
				}else if(i==5){
					//修改排名值返回当前排名值
				}
				 mask.hideLoading(); 
			  })
			};
		var getparams=function(i){
			 var params=[];
			 if(i==0){
				 params=[scope.page.currentPage || 1,
				         scope.page.numPerPage || 10,0,
				         startDate,endDate,deptTeach];
			 }else if(i==1||i==2||i==3){
				 params=[startDate,endDate,deptTeach];
			 }else if(i==4){
				 params=[stuId];
			 }else if(i==5){
				 params=[scope.pmInt];
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
	
	var m=[0,1,2,3],m1=[4],m2=[5];
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
				 scope.deptlname=deptTeach.level=='0'?'学院':deptTeach.level=='1'?'专业':deptTeach.level=='2'?'专业':'班级';
				if(initId>0){
					getAllData(m);
				}
				
		},true);
		//初始化数据
		var initData=function(){
			if(initId==0&&startDate!=null&&deptTeach!=null){
				initparams();getAllData(m2);getAllData(m);initId++;
			}else{
				setTimeout(initData,100);
			}
		};
		initData(); 
}]);
