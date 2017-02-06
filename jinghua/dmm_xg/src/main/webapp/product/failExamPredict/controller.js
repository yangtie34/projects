/**
 * 不及格预测
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout','dialog', function($scope, service,advancedService,$timeout,dialog){
	$scope.showButton=true;//展开更多按钮
	$scope.before_pxData={index:null,order:null};
	$scope.showData=5;//不及格详细信息初始数据-只展示5条
	$scope.GkTitle=['组织机构','学生数','预测不及格学生','预测不及格门次'];
	$scope.tYear=true;
	$scope.looklistcssdata=['fa fa-angle-down','fa fa-angle-up'];
	$scope.looklistcss=$scope.looklistcssdata[0];
	$scope.onlyYear=null;
	$scope.showdetailtable=false;
	$scope.data = {
			statuss:2,
			status:[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],
			mask : true,
			value_Qflx : '', // 选中的年id
			value_edu  : null,//选中学生类型
			value_zjzt : null,
			advance : {
				param : null
			},
			detailUrl : "pmsFailExamPredict/getStuDetail",
			exportUrl : 'pmsFailExamPredict/down',
			model   : {},
			distribution_detail : { // 分布参数
				page : {
					curpage  : 1,
					pagesize : 10,
					sumcount : null
				},
				title  : null,
				values : {},
				fields : [],
				headers: [],
				list   : null,
				mask   : false
			},
			abstract_detail : { // 详情参数
				page : {
					curpage  : 1,
					pagesize : 10,
					sumcount : null
				},
				title  : null,
				values : {},
				fields : [],
				headers: [],
				list   : null,
				mask   : false
			},
			DATE_NOW:'',
			DATE_PRO:''
		};
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; };
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; };
	var showLoading = function(){ dialog.showLoading();};
	var hideLoading = function(){ dialog.hideLoading(); };
	/**
	 * { schoolYear:'', termCode:'', edu:'', param:'' }
	 */
	var getParam = function(){
		var obj = Ice.apply({},$scope.data.param);
		obj.param = getAdvancedParam();
		obj.lx = $scope.data.model.lx_id;
		return obj;
	};
	/**
	 * 获取分页参数
	 */
	var getJson = function(obj){
		return JSON.stringify(obj);
	};
	var getxfInfoData=function(){
		showLoading();
		service.getYearAndTerm(function(data){//得到不及格预测的学年学期
			if(data.date.length==0){
				$scope.data.DATE_NOW='';
				$scope.data.DATE_PRO='';
			}else{
				$scope.data.DATE_NOW=data.date[0].DATE_NOW;
				$scope.data.DATE_PRO=data.date[0].DATE_PRO;	
			}
			$scope.XnxqCfg=data;
		});			
		service.queryXfInfo(getAdvancedParam(),function(data){//得到不及格预测的的总体信息(期末考试人数,不及格概率)
			$scope.XfyjCfg=data;
		});			
		service.queryXflxfb(getAdvancedParam(),function(data){//得到不及格学生性别分布
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('sexmc','预测不及格（性别：'+param.name+'）',param.name);
				}]}});
			$scope.XfflCfg=data;
		});			
		service.queryXslxfb(getAdvancedParam(),function(data){//得到不及格预测学生年级分布
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('grademc','预测不及格（年级：'+param.name+'）',param.name,'年级');
				}]}});		
			$scope.XsflCfg=data;
		});			
		service.queryQfjeAndRadio(getAdvancedParam(),function(data){//各院系学生不及格预测人数及比例
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('deptmc',param.name+'（预测不及格）',param.name);
				}]}});
			hideLoading();
			$scope.QfradioCfg=data;
		});			
	};
	//得到不及格预测的详细信息(院系  预测不及格学生人数 预测不及格门数)
	var getQfInfoData=function(){
		service.queryQfInfo(getAdvancedParam(),function(data1,data2){//得到欠费的详细信息
			$scope.QfinfoCfg=data1;
			$scope.Qfinfo=data2;
		});	
	};
	//发送邮件
	var getSendData=function(sendType,pid,list,sid){
		service.sendQfInfo(sendType,pid,list,function(data){//得到欠费的详细信息
			$scope.QfsendCfg=data;
			if(sendType=='only'){
//			alert(data.fh);
				if(data.fh=='发送成功!'){
					$scope.data.status[sid]=0;	
				}else{
					$scope.data.status[sid]=1;	
				}
			}
		});	
	};

	getxfInfoData();
	getQfInfoData();
	/**
	 * 单击发送邮件按钮
	 */
	$scope.send=function(sendType,sid){
		var pid=$scope.Qfinfo.list[sid].id;//得到当前院系的id
		var map =$scope.QfinfoCfg[sid];
		var list=map.CL01+': 在籍生预测不及格人数 '+map.CL03+",";
		getSendData(sendType,pid,list,sid);
	};
	/**
	 * 单击全部发送按钮
	 */
	$scope.sendAll=function(){
		for(var i=0;i<$scope.Qfinfo.list.length;i++){
			$scope.send('',i);//生成所有的excel文件
		}
		service.sendAll(function(data){//生成压缩包并发送
//			alert(data.fh);
			if(data.fh=='发送成功!'){
				$scope.data.statuss=0;	
			}else{
				$scope.data.statuss=1;	
			}
		});
	};
	/**
	 * 单击导出按钮
	 */
	$scope.exportExcel=function(sid){
		var pid=$scope.Qfinfo.list[sid].id;//得到当前院系的id
		var map =$scope.QfinfoCfg[sid];
		var year=$scope.onlyYear;//得到是否为当前年
		var list=map.CL01+': 在籍生预测不及格人数 '+map.CL03+",";
		window.open("failExamPredict/excelGkInfo?list="+list+"&pid="+pid);
	};
	/**
	 * 单击全部导出按钮
	 */
	$scope.exportAll=function(){
		for(var i=0;i<$scope.Qfinfo.list.length;i++){
			$scope.send('',i);//生成所有的excel文件
		}
		window.open("failExamPredict/excelGkAll");
//		service.excelQfAll(function(data){//生成压缩包并发送
//		});
	}
	/**
	 * 展开更多
	 */
	$scope.showMore=function(data,order){
		if(data=='00'){
			$scope.showData=5;	
			$scope.showButton=true;
		}else{
			$scope.showData=$scope.QfinfoCfg.length;
		}
//		$scope.showData=data+5;
		if($scope.showData>=$scope.QfinfoCfg.length){
			$scope.showButton=false;
		}
	};
	/**
	 * 排序升序
	 */
	$scope.changeOrder=function(index,order){
//		if($scope.before_pxData.index==index&&$scope.before_pxData.order==order){
		var d;
		var p1=[''],p2=[''], p3=[''],p4=[''],p5=[''];
		var p=[p1,p2,p3,p4,p5];
		for(var i=0;i<$scope.QfinfoCfg.length;i++){
			p1[i]=$scope.QfinfoCfg[i].CL01;
			p2[i]=$scope.QfinfoCfg[i].CL02;
			p3[i]=$scope.QfinfoCfg[i].CL03;
			p4[i]=$scope.QfinfoCfg[i].CL04;
			p5[i]=$scope.QfinfoCfg[i].CL05;
			
		}	
		var pd=p[index][i] < p[index][j];
		for(var i=0;i<$scope.QfinfoCfg.length;i++){
			for(var j=0;j<$scope.QfinfoCfg.length-1-i;j++){
				if(order=='up'){
					 pd=p[index][j] > p[index][j+1];
				}else if(order=='down'){
					 pd=p[index][j] < p[index][j+1];
				}
				if(pd){
					d=p[index][j+1] ;
					p[index][j+1] =p[index][j] ;
					p[index][j]=d;
					d=$scope.QfinfoCfg[j+1];
					$scope.QfinfoCfg[j+1]=$scope.QfinfoCfg[j];
					$scope.QfinfoCfg[j]=d;
				}
			}
		}
//		}
//		$scope.before_pxData.index=index;
//		$scope.before_pxData.order=order;
	};
	//只看今年
	 $scope.onlyThisYear =function(tYear){
		 if(tYear){
			 $scope.onlyYear='2015';
			 getQfInfoData();
//			 $scope.onlyYear=null;
			 $scope.tYear=false;
		 }else{
			 $scope.onlyYear=null;
			 getQfInfoData();
			 $scope.tYear=true;
		 }
	 };
	 /**
	  * 查看名单
	  */
	 $scope.lookList =function(){
		 if($scope.looklistcss==$scope.looklistcssdata[0]){
			 $scope.looklistcss=$scope.looklistcssdata[1];
//			 $scope.showMore(0);
			 $scope.showData=5;
			 $scope.showButton=true;
			 $scope.showdetailtable=true;
		 }else{
			 $scope.looklistcss=$scope.looklistcssdata[0];
			 $scope.showdetailtable=false;
		 }
	 };
		/**
		 * 高级查询-change事件
		 */
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			getxfInfoData();//初始化总体信息(总人数,总金额,人均金额)
			getxfInfoData();
//			getXfData();//历年学费减免分布
//			getXffbData();//学费减免分布
//			refreshData();
		};
		//加载高级查询组件
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});
		/** 概况信息 点击事件 */
		$scope.abstractDetailClick = function(type, title, seniorCode){
			var detail = $scope.data.abstract_detail;
			detail.page.curpage = 1;
			detail.title = title;
			detail.values= {};
			detail.values[type] = seniorCode||null;
			detail.headers= ['学号','学年','学期','姓名','性别','院系','专业','班级','预测不及格课程','预测成绩','逃课次数'];
			detail.fields = ['no','school_year','term_code','name','sexmc','deptmc','majormc','classmc','coursemc','score','tkcount'];
			detail.formConfig = {
					title : title,
					show  : true,
					url       : $scope.data.detailUrl,
					exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
					heads  : detail.headers,
					fields : detail.fields,
					params : abstractDetailParam()
				};
			$timeout(function(){});
			//			abstractDetail();
		};
		/** 参数 */
		var abstractDetailParam = function(){
			var detail = $scope.data.abstract_detail,
				param  = getParam(),
//				page   = getJson(detail.page),
				values = getJson(detail.values),
				fields = getJson(detail.fields),
				headers= getJson(detail.headers);
//			Ice.apply(param, {page:page, values:values, fields:fields, headers:headers});
			Ice.apply(param, {values:values, fields:fields, headers:headers});
			return param;
		};
		/** 请求 */
		var abstractDetail = function(){
			var detail = $scope.data.abstract_detail;
			showLoading();
			service.getStuDetail(abstractDetailParam(), function(data){
				hideLoading();
				detail.list = data.rows;
				detail.page.sumcount = data.total;
				detail.page.pagecount = data.pagecount;
				detail.mask = false;
				detail.detail_show = true;
			},hideLoading);
		};
		/** 翻页 */
		$scope.$watch("data.abstract_detail.page.curpage",function(newVal,oldVal){
			if(newVal != oldVal){
				$scope.data.abstract_detail.mask = true;
				$scope.data.abstract_detail.page.curpage = newVal;
				abstractDetail();
			}
		},true);
		// down
		$scope.abstractDetailDown = function(){
			var param = abstractDetailParam();
			param.fileName = $scope.data.abstract_detail.title;
			service.getStuDetailDown(param, function(data){
			});
		};
		/** 概况信息 点击事件 */
		var abstractDetailAll = function(type, title, seniorCode,typemc){
			var detail = $scope.data.abstract_detail;
			detail.page.curpage = 1;
			detail.title = title;
			detail.values= {};
			detail.values[type] = seniorCode||null;
			if(type=="sexmc"||type=="deptmc"){
				detail.headers= ['学号','学年','学期','姓名','性别','院系','专业','班级','预测不及格课程','预测成绩','逃课次数'];
				detail.fields = ['no','school_year','term_code','name','sexmc','deptmc','majormc','classmc','coursemc','score','tkcount'];
			}else{
				detail.headers= ['学号','学年','学期','姓名','性别','院系','专业','班级','预测不及格课程','预测成绩','逃课次数',typemc];
				detail.fields = ['no','school_year','term_code','name','sexmc','deptmc','majormc','classmc','coursemc','score','tkcount',type];
			}
			detail.formConfig = {
					title : title+'学生名单',
					show  : true,
					url       : $scope.data.detailUrl,
					exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
					heads  : detail.headers,
					fields : detail.fields,
					params : abstractDetailParam()
				};
			$timeout(function(){});
//			abstractDetail();
		};		
	 
}]);