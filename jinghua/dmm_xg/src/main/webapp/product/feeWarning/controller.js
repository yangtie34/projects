/**
 * 学费预警
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout','dialog', function($scope, service,advancedService,$timeout,dialog){
	$scope.showButton=true;//展开更多按钮
	$scope.before_pxData={index:null,order:null};
	$scope.showData=5;//欠费详细信息初始数据-只展示5条
	$scope.QfTitle=['组织机构','学生数','欠费人数','占比','欠费金额'];
	$scope.tYear=true;
	$scope.onlyYear='1';
	$scope.data = {
			mask   : [false,false],
			statuss:2,
			status:[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],
			value_Qflx : '', // 选中的年id
			value_edu  : null,//选中学生类型
			value_zjzt : null,
			advance : {
				param : null
			},
			detailUrl : "pmsFeeWarning/getStuDetail",
			exportUrl : 'pmsFeeWarning/down',
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
		};
	
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; };
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; };
	var showLoading = function(){ dialog.showLoading();};
	var hideLoading = function(){ dialog.hideLoading(); };
	var partshowLoading = function(sort){for(var i=0;i<$scope.data.mask.length;i++){$scope.data.mask[i]=false;} $scope.data.mask[sort] = true;$timeout(function(){});};
	var parthideLoading = function(){ for(var i=0;i<$scope.data.mask.length;i++){$scope.data.mask[i]=false;};$timeout(function(){}); };
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
	var querySelectType=function(){//得到学年
		service.querySelectType(function(data){
			showLoading();
				var $data = $scope.data;
				$data.bzdm_edu = data.EDU_ID;//学生类型(本专科生)
				$data.bzdm_lx = data.lx;//欠费类型
				$data.bzdm_zjzt  = data.zt;//在籍状态
				$data.value_edu  = $data.bzdm_edu[0].id;//初始化学历的值
				$data.value_Qflx  = $data.bzdm_lx[0].id;//初始化欠费类型
				$data.value_zjzt  = $data.bzdm_zjzt[0].id;//初始化在籍状态
				getxfInfoData();//初始化总体信息(总人数,总金额,人均金额)
				getQfInfoData();//初始化欠费详细信息
			});
	};
	$scope.changEdu = function(id, data){//改变学历
		$scope.data.value_edu = id;
		getQfInfoData();
	};
	$scope.changQflx = function(id, data){//改变欠费类型
		$scope.data.value_Qflx = id;
		getQfInfoData();
	};
	$scope.changZjzt = function(id, data){//改变在籍状态
		$scope.data.value_zjzt = id;
		getQfInfoData();
	};
	var getxfInfoData=function(){//得到未缴费的总体信息(总人数,总金额)
		service.queryXfInfo(getAdvancedParam(),function(data){
			$scope.XfyjCfg=data;
		});			
		service.queryXflxfb(getAdvancedParam(),function(data){//得到未缴费学费类型及金额
			data.config.on=['CLICK',function (param){
				abstractDetailAll('qfnamemc','欠费（类型：'+param.name+'）',param.name,'欠费');
				}];
			$scope.XfflCfg=data;
		});			
		service.queryXslxfb(getAdvancedParam(),function(data){//得到未缴费学生类型及人数
			data.config.on=['CLICK',function (param){
				abstractDetailAll('grademc','欠费（年级：'+param.name+'）',param.name,'年级');
				}];
			$scope.XsflCfg=data;
		});			
		service.queryQfjeAndRadio(getAdvancedParam(),function(data){//历年欠费金额及比例
			data.config.on=['CLICK',function (param){
				abstractDetailAll('school_year',param.name+'学年（欠费）',param.name,'欠费');
				}];
			hideLoading();
			$scope.QfradioCfg=data;
		});			
	};
	//得到欠费的详细信息(院系  学生人数 欠费人数 占比 金额)
	var getQfInfoData=function(){
		partshowLoading(0);
		service.queryQfInfo($scope.onlyYear,$scope.data.value_edu,$scope.data.value_Qflx,$scope.data.value_zjzt,function(data1,data2){//得到欠费的详细信息
		for(var i=0;i<data1.length;i++){
			$scope.data.status[i]=2;
		}
		   parthideLoading();
			hideLoading();
			$scope.QfinfoCfg=data1;
			$scope.Qfinfo=data2;
		});	
	};
	//发送邮件
	var getSendData=function(sendType,pid,list,sid){
		service.sendQfInfo(sendType,pid,list,$scope.onlyYear,$scope.data.value_edu,$scope.data.value_Qflx,$scope.data.value_zjzt,function(data){//得到欠费的详细信息
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
	querySelectType();//得到筛选条件
//	getQfInfoData('21','1','1');
	getxfInfoData();
	/**
	 * 单击发送邮件按钮
	 */
	$scope.send=function(sendType,sid){
		var pid=$scope.Qfinfo.list[sid].id;//得到当前院系的id
		var map =$scope.QfinfoCfg[sid];
		var list=map.CL01+': 在籍生欠费人数 '+map.CL03+',占比 '+map.CL04+',金额 '+map.CL05;
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
		var list=map.CL01+': 在籍生欠费人数 '+map.CL03+',占比 '+map.CL04+'25,金额 '+map.CL05;
			window.open("feeWarning/excelQfInfo?pid="+pid+"&list="+list+"&year="+year+"&edu="+$scope.data.value_edu+"&lx="+$scope.data.value_Qflx+"&slx="+$scope.data.value_zjzt);
	};
	/**
	 * 单击全部导出按钮
	 */
	$scope.exportAll=function(){
		for(var i=0;i<$scope.Qfinfo.list.length;i++){
			$scope.send('',i);//生成所有的excel文件
		}
		window.open("feeWarning/excelQfAll");
//		service.excelQfAll(function(data){//生成压缩包并发送
//		});
	};
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
			 $scope.onlyYear='0';
			 getQfInfoData();
//			 $scope.onlyYear=null;
			 $scope.tYear=false;
		 }else{
			 $scope.onlyYear='1';
			 getQfInfoData();
			 $scope.tYear=true;
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
			detail.headers= ['学号','姓名','学年','性别','院系','专业','班级','欠费类型','欠费金额'];
			detail.fields = ['no','name','school_year','sexmc','deptmc','majormc','classmc','qfnamemc','money'];
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
			Ice.apply(param, {values:values, fields:fields, headers:headers});
//			Ice.apply(param, {page:page, values:values, fields:fields, headers:headers});
			return param;
		};
		/** 请求 */
		var abstractDetail = function(){
			var detail = $scope.data.abstract_detail;
			showLoading();
			service.getStuDetail(abstractDetailParam(), function(data){
				detail.list = data.rows;
				detail.page.sumcount = data.total;
				detail.page.pagecount = data.pagecount;
				detail.mask = false;
				detail.detail_show = true;
				hideLoading();
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
			if(type=="qfnamemc"||type=="school_year"){
				detail.headers= ['学号','姓名','学年','性别','院系','专业','班级','欠费类型','欠费金额'];
				detail.fields = ['no','name','school_year','sexmc','deptmc','majormc','classmc','qfnamemc','money'];
			}else{
				detail.headers= ['学号','姓名','学年','性别','院系','专业','班级','欠费类型','欠费金额',typemc];
				detail.fields = ['no','name','school_year','sexmc','deptmc','majormc','classmc','qfnamemc','money',type];
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