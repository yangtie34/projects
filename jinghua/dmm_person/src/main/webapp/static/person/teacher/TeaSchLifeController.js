app.controller("TeaSchLifeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.echarColor=echarColor;
	page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 5,
			  conditions : []
	 	};
	scope.pageGz=angular.copy(page);
	scope.pageXf=angular.copy(page);
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="teaSchLifeService?"; 
	  
	  var methods=['gzComb',	//0工资组成
	               'gzChange',	//1工资变化
	               'gzxq',		//2工资详情
	               'yktxf',		//3一卡通消费+余额
	               'yktcz',		//4一卡通充值记录
	               'yktxffx',	//5一卡通消费分析
	               'yktxfmx',//6一卡通消费明细
	               'ieAvgTime',//7平均上网时间
	               'ieAllTime',//8总上网时间
	               'schFirst',//9大学首次
	               ];
	  
	  var getServiceData=function(){
		  for(var i=0;i<methods.length;i++){
			  htt.push({
				  params:[userId],
				  service:httservice+methods[i]
			  });
		 }
	};
	var get3woption=function(data,typ){
		var option=echarswt(data,typ,{text:''});
		delete option.title;
		delete option.toolbox;
		delete option.legend;
		return option;
	}
	var getgztData=function(){

		htt[2].params=[userId,scope.date1.startTime,scope.date1.endTime];
		htt[2].params.push(scope.pageGz.currentPage || 1);
		htt[2].params.push(scope.pageGz.numPerPage || 5);
		http.callService(htt[2]).success(function(data){
			var d=data.resultList;
			/*dt=[{FIELD:'基本工资',VALUE:'256',LX:1,NAME:'2015/06'},
					{FIELD:'课时费',VALUE:'256',LX:1,NAME:'2015/06'},
					{FIELD:'绩效工资',VALUE:'256',LX:1,NAME:'2015/06'},
				{FIELD:'住房补贴',VALUE:'256',LX:1,NAME:'2015/06'},
			{FIELD:'五险一金',VALUE:'256',LX:0,NAME:'2015/06'}]*/
			var dt=[];
			 scope.gztTitles=['年月'];
			 var titess={};
			for(var i=0; i<d.length;i++){
				dt[i]={};
				dt[i]['年月']=d[i].CL01;
				 dt[i]['合计(元)']=d[i].CL07; 
				 dt[i]['带扣(元)']=d[i].CL08;
				 dt[i]['实发(元)']=d[i].CL09;
				if(d[i].TYPES==null)continue;
				var types=d[i].TYPES.split(',');
				var vals=d[i].VALS.split(',');
				
			 for(var j=0;j<types.length;j++){
				 titess[types[j]]=null;
				 dt[i][types[j]]=vals[j]; 
			 }	
			
			}
				for(var key in titess){
					scope.gztTitles.push(key);	
				}
			/*var datas = this.echardata3d(dt, 'line');
			var legents = datas[0];
			var xAxiss = datas[1];
			var seriess = datas[2];
			 scope.gztTitles=['序号','年月'];
			for(var i=0;i<xAxiss.length;i++){
				scope.gztTitles.push(xAxiss[i]);
			}*/
			scope.gztTitles.push('合计(元)');scope.gztTitles.push('带扣(元)');scope.gztTitles.push('实发(元)');
			/*var nyrm={};	
			var list=[];
			for(var i=0;i<legents.length;i++){
				if(!nyrm[legents[i]])nyrm[legents[i]]={};
				var l=[legents[i]];
				l=l.concat(seriess[i].data);  
				list.push(l);
			}
			for(var i=0;i<dt.length;i++){
				nyrm[dt[i].NAME].sum=nyrm[dt[i].NAME].sum||{yf:0,dk:0,sf:0};
				if(dt[i].LX==1){
					nyrm[dt[i].NAME].sum.sf+=Number(dt[i].VALUE);
				}else{
					nyrm[dt[i].NAME].sum.dk+=Number(dt[i].VALUE);
				}
				nyrm[dt[i].NAME].sum.yf+=Number(dt[i].VALUE);
			}
			for(var i=0;i<legents.length;i++){
				list[i].push(''+nyrm[legents[i]].sum.yf);
				list[i].push(''+nyrm[legents[i]].sum.dk);
				list[i].push(''+nyrm[legents[i]].sum.sf);
			}*/
		
			vm.items[2] = dt;
			 scope.pageGz.totalRows=data.totalRows;
			  scope.pageGz.totalPages=data.totalPages;
		  });
	}
	var getData1=function(){
		htt[0].params=[userId,scope.date1.startTime,scope.date1.endTime];
		http.callService(htt[0]).success(function(d){
			var data=[];
			for(var key in d[0]){
				if(!(key=='YF'||key=='DK'||key=='SF')&&d[0][key]!=null&&d[0][key]!=0)
					data.push({FIELD:key,VALUE:d[0][key]||0});
			}
			/*data=[{FIELD:'基本工资',VALUE:'256',bz:1},
					{FIELD:'课时费',VALUE:'256',bz:1},
					{FIELD:'绩效工资',VALUE:'256',bz:1},
				{FIELD:'住房补贴',VALUE:'256',bz:1},
			{FIELD:'五险一金',VALUE:'256',bz:0}]*/
			var sum={yf:d[0].YF,dk:d[0].DK,sf:d[0].SF};
			vm.items[0] ={
					option:echarbzhx(data,"工资组成"),
					list:data,
					sum:sum
				}; 
		  });
		
		htt[1].params=[userId,scope.date1.startTime,scope.date1.endTime];
		http.callService(htt[1]).success(function(data){
		/*	data=[{FIELD:'2015/06',VALUE:'356',NAME:"收入"},
					{FIELD:'2015/07',VALUE:'256',NAME:"收入"},
					{FIELD:'2015/08',VALUE:'406',NAME:"收入"},
				{FIELD:'2015/09',VALUE:'596',NAME:"收入"},
			{FIELD:'2015/10',VALUE:'256',NAME:"收入"}]*/
			vm.items[1] =get3woption(data,'line');
		  });
		getgztData();
	};
	scope.xfmxTitles=['序号','消费内容','消费金额(元)','消费地点','消费时间'];
	var getXfData=function(){
		htt[6].params=[userId,scope.date2.startTime,scope.date2.endTime];
		htt[6].params.push(scope.pageXf.currentPage || 1);
		htt[6].params.push(scope.pageXf.numPerPage || 10);
		http.callService(htt[6]).success(function(data){
			vm.items[6] = data.resultList;
			 scope.pageXf.totalRows=data.totalRows;
			  scope.pageXf.totalPages=data.totalPages;
		  });
	};
	var getData2=function(){
		htt[3].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[3]).success(function(data){			
			var d=[];
			var ye=null;
			ye=data[0].VALUE||0;
			for(var i=0;i<data[1].length;i++){
					d.push(data[1][i]);
			}
			vm.items[3] ={
					option:echarbzhx(d,""),
					list:d,
					ye:ye
				}; 
		  });
		htt[4].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[4]).success(function(data){

			var m={};
			for(var i=0;i<data.length;i++){
				if(!m[data[i].FIELD])m[data[i].FIELD]=0;
				m[data[i].FIELD]+=Number(data[i].VALUE);
			}
			var list=[];
			var xjcz={allval:0};
			for(var key in m){
				if(key.indexOf("充值")==0){
					xjcz.val=Number(m[key]);
				};
				xjcz.allval+=Number(m[key]);
				list.push({
					FIELD:key,
					VALUE:m[key]
				});
			}
			xjcz.val=xjcz.val||0;
			xjcz.bl=Percentage(xjcz.val,xjcz.allval);
			vm.items[4] = {
					option:echarbzhx(list,"充值记录"),
					list1:list,
					list2:data,
					xjcz:xjcz
				}; 
		  });
		
		htt[5].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[5]).success(function(data){
			vm.items[5] ={
					xffx:get3woption(data[0],'line'),
					rjxf:data[1][0].VALUE
			}
		  });
		getXfData();
	};
	var getData3=function(){
		htt[7].params=[userId,scope.date3.startTime,scope.date3.endTime];
		http.callService(htt[7]).success(function(data){
			//上网账号,开始时间,最小值,最大值,日均上网时间 
			/*data=[{CL03:5,CL04:6,CL05:4}]*/
			var d=[];
			if(data.length>0)
			 d=[{FIELD:'最低',VALUE:data[0].CL03,NAME:'上网时间'},
			       {FIELD:'平均',VALUE:data[0].CL04,NAME:'上网时间'},
			       {FIELD:'最高',VALUE:data[0].CL05,NAME:'上网时间'},
			       ];
			vm.items[7] =get3woption(d,'line');
		  });
		htt[8].params=[userId,scope.date3.startTime,scope.date3.endTime];
		http.callService(htt[8]).success(function(data){
			/*data=[{grsw:[
			            {SSUM_:15} 
			             ],
				xxsw:[ {MIN_:5,MAX_:20,AVG_:15,SUM_:100} 
				      ]}]*/
			var d=[];
			data[0].grsw[0]=data[0].grsw[0]||{};
			 d=[{FIELD:'个人累计',VALUE:data[0].grsw[0].SSUM_||0,NAME:'上网时间'},
			       {FIELD:'校平均',VALUE:data[0].xxsw[0].AVG_||0,NAME:'上网时间'},
			       {FIELD:'校累计最低',VALUE:data[0].xxsw[0].MIN_||0,NAME:'上网时间'},
			       ];
			vm.items[8] = {
			  	      type :'columnf',
			  	      data:d,
			  	      isSort:true,
		              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		               type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		              },
		        toolbox: {
//		        orient : 'vertical',
//		        x : 'right',
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            magicType : {show: false, type: ['line', 'bar', 'stack', 'tiled']},
		            restore : {show: false},
		            saveAsImage : {show: true}
		        }
		    },
		    noDataLoadingOption : {
				  text : '暂无数据',
				   // effectOption : null,
				    effect : 'bubble',
				    effectOption : {
				    	effect : {n:'0'}
				    }},
		     series : {
		            type:'bar',
		            stack:'总时长',
		            itemStyle : { normal: {label : {show: false, position: 'insideRight'}}}
		     },
		  	  xAxis : [
		         {
		            name:'时长(分)',
		            type:'value',
		            max:'defualt',
		            min:0
		        }
		    ], 
		      grid :{
		    	x  :120,
		    	x2 :80
		    },
		      yAxis : [
		         {
		            name:'统计类型',
		            boundaryGap : true
		        }
		    ]  
		    };
		  });
	};
	//矿大首次
	var getData4=function(){
		htt[9].params=[userId];
		http.callService(htt[9]).success(function(data){
			data=data[0];
//			dt=[//{NAME_:data[0].jc[0].PAY_MANEY,TIME:data[0].jc[0].TIME_},
//			    {NAME_:data[0].gw[0].PAY_MONEY,TIME:data[0].gw[0].TIME_},
//			    {NAME_:data[0].jy[0].NAME_,TIME:data[0].jy[0].TIME_},
//			    {NAME_:data[0].ry[0].NAME_,TIME:data[0].ry[0].TIME_}]
		
			vm.items[9] = data;
		  });
	};
	getServiceData();
	getData4();	  
		  
		  /*监控变化，若变更则执行后台调用*/
		  scope.$watch('date1',function(val1,val2){
				if(val1!=null){
					getData1();
				}
		  },true);
		  scope.$watch('date2',function(val1,val2){
			  if(val1!=null){
				  getData2();
				} 
		  },true);
		  scope.$watch('date3',function(val1,val2){
				if(val1!=null){
					getData3();
				}
		  },true);
		  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
		  scope.$watch('pageGz',function(val1,val2){
		  	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
		  		getgztData();
		  	  }
		  },true);
		  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
		  scope.$watch('pageXf',function(val1,val2){
		  	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
		  		  getXfData();
		  	  }
		  },true);
		  function Percentage(num, total) { 
			    return ((Math.round(num / total * 10000) / 100.00)||0 + "%");// 小数点后两位百分比
			}	  
}]);