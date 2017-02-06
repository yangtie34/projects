/**
 * 成绩预测（辅导员）
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout','dialog', function($scope, service,advancedService,$timeout,dialog){
	$scope.before_pxData={index:null,order:null};
	$scope.showTitleRow2=true;
	$scope.showTitleRow1=true;
	$scope.gkTableTitle=['班级','人数','课程（门）','90-100分','80-90分','70-80分','60-70分','<60分'];
	$scope.gkTableTitlemc=['班级','人数','课程（门）','90-100分','90-100分','80-90分','80-90分','70-80分','70-80分','60-70分','60-70分','<60分','<60分'];
	$scope.choose_score_rank=['bz','class','rs','kc','90','all','80','all','70','all','60','all','<60','all'];
	$scope.tYear=true;
	$scope.looklistcssdata=['fa fa-angle-down','fa fa-angle-up'];
	$scope.looklistcss=$scope.looklistcssdata[0];
	$scope.onlyYear=null;
	$scope.showdetailtable=false;
	$scope.data = {
			majormc:null,
			order:'',
			cjgk:false,
			cjfb:false,
			index:3,
			majorcss:[],
			majorType:null,
			mask : true,
			value_Qflx : '', // 选中的年id
			value_edu  : null,//选中学生类型
			coursetpye :null,
			coursedata:[{id:'all',mc:'全部课程'}],
			advance : {
				param : null
			},
			detailUrl : "pmsScorePredict/getStuDetail",
			exportUrl : 'pmsScorePredict/down',
			model   : {},
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
	var querySelectType=function(){//得到学年,本专科生
		service.getYearAndTerm(function(data){
			showLoading();
				var $data = $scope.data;
				$data.bzdm_edu = data.edu;//学生类型(本专科生)
				$data.bzdm_xn = data.xnxq;//学年
				$data.coursetpye = data.coursesx;//课程属性（公共基础课...）
				$data.coursedata = [{id:'all',mc:'选择课程'}];//课程名
				$data.value_edu  =$data.bzdm_edu[0]==null?'': $data.bzdm_edu[0].id;//初始化学历的值
				$data.value_year  =$data.bzdm_xn[0]==null?'': $data.bzdm_xn[0].id.split(',')[0];//初始化学年
				$data.value_courseType  = $data.coursetpye[0].id.split(',')[0];//初始化学年
				$data.value_term=$data.bzdm_xn[1]==null?'01':$data.bzdm_xn[1].id.split(',')[1];//初始化学期
				$scope.data.majorType="all";
				$scope.data.value_courseData="all";
				$scope.data.value_coursemc="选择课程";
				$scope.data.yczql='准确率';
				queryMajorAndCourse();
				$data.value_year  = $data.bzdm_xn[0]==null?'':$data.bzdm_xn[0].id;
				$data.value_edu  =$data.bzdm_edu[0]==null?'': $data.bzdm_edu[0].id;//初始化学历的值
				$data.value_courseType  =$data.coursetpye[0].id;
				$data.course=$data.coursedata[0].id;
				$scope.data.value_courseData=$data.course;
			});
	};
	/**
	 * 初始化数据
	 */
	var queryMajorAndCourse=function(){
		service.queryGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.data.majorType,function(data){//得到挂科预测的的总体信息(期末考试人数,挂科概率)
			$scope.data.majormc=data.majorsort;//得到辅导员所带专业
			$scope.data.majorType=$scope.data.majormc.length!=0?$scope.data.majormc[0].MAJORMC:'all';
			getInfoData();
			for(var i=0;i<data.majorsort.length;i++){
				if(i==0){
					$scope.data.majorcss[0]='active';	
				}else{
					$scope.data.majorcss[i]='';
				}
			}
		});	
		service.queryCourseByType($scope.data.value_courseType,$scope.data.value_year,$scope.data.value_term,function(data){//得到挂科预测的的总体信息(期末考试人数,挂科概率)
			if(data.course.length==0){
				$scope.data.coursedata = [{id:'all',mc:'选择课程'}];
				$scope.data.value_courseData=$scope.data.coursedata[0].id;
			}else{
				$scope.data.coursedata=data.course;
				$scope.data.value_courseData=$scope.data.coursedata[0].id;
			}
		});	
	};
	querySelectType();
	/**
	 * 清除选择年默认数据
	 */
	var clearYearSelectedFn = function(){
		var $data = $scope.data;
		$data.value_year = null;
		$data.fiveYear = false;
		$data.tenYear  = false;
	};
	$scope.changXn = function(id, data){
		var $data = $scope.data, bzdm_xn = $data.bzdm_xn;
		if($data.start_year==id && $data.end_year==id) return;
		clearYearSelectedFn();
		for(var i=0,len=bzdm_xn.length; i<len; i++){
			if(bzdm_xn[i].id == id){
				$data.value_year = id.split(',')[0];
				$data.value_term = id.split(',')[1];
				$data.dateName = bzdm_xn[i].mc;
				break;
			}
		}
		$scope.data.majorType="all";
		$scope.data.value_courseData="all";
		queryMajorAndCourse();
//		getInfoData();
		$data.value_year=id;
	};
	/**
	 * 选择课程属性
	 */
	$scope.changCourse = function(id, data){//改变课程性质
		$scope.data.value_courseType = id;
		service.queryCourseByType($scope.data.value_courseType,$scope.data.value_year,$scope.data.value_term,function(data){//得到挂科预测的的总体信息(期末考试人数,挂科概率)
			if(data.course.length==0){
				$scope.data.coursedata = [{id:'all',mc:'选择课程'}];
				$scope.data.value_courseData=$scope.data.coursedata[0].id;
			}else{
				$scope.data.coursedata=data.course;
				$scope.data.value_courseData=$scope.data.coursedata[0].id;
			}
		});	
		getInfoData();
	};
	/**
	 * 改变课程
	 */
	$scope.changCourseData = function(id, data){//改变课程
		$scope.data.value_courseData = id;
		$scope.data.value_coursemc = data.mc;
		getInfoData();
	};
	var getInfoData=function(){
		showLoading();
		service.getYearAndTerm(function(data){//得到挂科预测的学年学期
			if(data.date.length==0){
				$scope.data.DATE_NOW='';
				$scope.data.DATE_PRO='';
			}else{
				$scope.data.DATE_NOW=data.date[0].DATE_NOW;
				$scope.data.DATE_PRO=data.date[0].DATE_PRO;	
			}
			$scope.XnxqCfg=data;
		});			
		service.queryGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.data.majorType,function(data){//得到挂科预测的的总体信息(期末考试人数,挂科概率)
			$scope.XfyjCfg=data;
//			$scope.data.majormc=data.majorsort;//得到辅导员所带专业
		});	
		//成绩概况
		service.queryScoreInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,
								$scope.data.value_edu,$scope.data.value_courseType,$scope.data.value_courseData,$scope.data.majorType,function(data){//成绩预测概况
			if(data.scoreinfo.length==0){
				$scope.data.cjgk=true;
			}else{
				$scope.data.cjgk=false;
			}
			var sumCount=0,sumCourse=0;
			if(data.scoreinfo.length!=0){
			for(var i=0;i<data.scoreinfo.length;i++){
				sumCount+=data.scoreinfo[i].CL02;
				sumCourse+=data.scoreinfo[i].CL03;
			}
			}
			data.count[0].CL02=sumCount;
			data.count[0].CL03=sumCourse;
			$scope.XfflCfg=data;
		});			
		//成绩分布(按课程)
		service.queryScorefb(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,
				$scope.data.value_edu,$scope.data.majorType,function(data){//成绩预测概况
			if($scope.data.value_year==$scope.data.bzdm_xn[0].id) {
				$scope.data.yczql='去年预测准确率';
			}else{
				$scope.data.yczql='准确率';
			}
			if(data.length==0){
				$scope.data.cjfb=true;
			}else{
				$scope.data.cjfb=false;
			}
			$scope.XffbCfg=data;
		});			
		//成绩分布(按课程性质)
		service.queryScorefbByNature(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,
				$scope.data.value_edu,$scope.data.majorType,function(data){//成绩预测概况
			data.config.on=['CLICK',function (param){
				$scope.abstractDetailClick('tbfb',param.name,{CL01:param.name+'分',CL02:param.seriesName});
				}];
			$scope.XffbByCfg=data;
			hideLoading();
		});			
	};

	
	/**
	 * 选择专业
	 */
	$scope.majorTypeCk=function(type){
		$scope.data.majorType=$scope.data.majormc[type].MAJORMC;
//		$scope.data.value_year=$scope.data.value_year.split(',')[0];
		getInfoData();
		for(var i=0;i<$scope.data.majormc.length;i++){
			if(i==type){
				$scope.data.majorcss[i]='active';
			}else{
				$scope.data.majorcss[i]='';
			}
			
		}
	};
	/**
	 * 查看
	 */
	$scope.checkType=function(){
		 $scope.showTitleRow2=true;
		 $scope.showTitleRow1=true;
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.values= {};
		$scope.data.abstract_detail.headers=['班级','预测人数','课程（门）','90-100分','80-90分','70-80分','60-70分','<60分'];
		$scope.data.index=3;
		$scope.data.abstract_detail.title='同年级同专业学生成绩';
		detail.values["type"] = 'look';
		detail.values["majorType"] = $scope.data.majorType||null;
		detail.values["schoolYear"] = $scope.data.value_year||null;
		detail.values["termCode"] = $scope.data.value_term||null;
		detail.values["edu"] = $scope.data.value_edu||null;
		abstractDetail();
	};
	/**
	 * 去对比
	 */
	$scope.contrastCk=function(index){
		 $scope.showTitleRow2=true;
		 $scope.showTitleRow1=true;
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.values= {};
		$scope.data.abstract_detail.headers=['班级','课程属性','平均成绩','去年预测准确率','90-100分','80-90分','70-80分','60-70分','<60分'];
		$scope.data.index=4;
		$scope.data.abstract_detail.title=$scope.XffbCfg[index].CL01;
		detail.values["type"] = 'contrast';
		detail.values["majorType"] = $scope.data.majorType||null;
		detail.values["schoolYear"] = $scope.data.value_year||null;
		detail.values["termCode"] = $scope.data.value_term||null;
		detail.values["edu"] = $scope.data.value_edu||null;
		detail.values["coursemc"] = $scope.XffbCfg[index].CL01||null;
		detail.values["coursetypemc"] = $scope.XffbCfg[index].CL02||'';
		detail.fields = ['no','CL02','CL03','CL04','CL05','CL06','CL07','CL08','CL09','CL10','CL11','CL12','CL13'];
		abstractDetail();
	};
	/**
	 * 下钻详情
	 */
	$scope.abstractDetailClick=function(type,key,item){
		var detail = $scope.data.abstract_detail;
		$scope.data.abstract_detail.title=item.CL01;
		detail.page.curpage = 1;
		detail.values= {};
		var choose='',choosemc='',title1='';
		if(type!='tbfb'){
			key=parseInt(key.split("L")[1]);
			choosemc=$scope.gkTableTitlemc[key-1];
			title1=detail.title+'（'+choosemc+'）学生名单';
		}
		if(type=='fb'){choose=$scope.choose_score_rank[key-1];}
		else if(type=='tbfb'){choose=(key.split('-'))[0];item.CL01=item.CL02;title1=item.CL01+'（'+detail.title+'）学生名单';}else{choose=$scope.choose_score_rank[key];}
		$scope.data.abstract_detail.headers=['学号','姓名','班级','学年','学期','课程名称','课程属性','预测分数'];
		$scope.data.index=4;
		detail.values["type"] = type;
		detail.values["majorType"] = $scope.data.majorType||null;
		detail.values["schoolYear"] = $scope.data.value_year||null;
		detail.values["termCode"] = $scope.data.value_term||null;
		detail.values["edu"] = $scope.data.value_edu||null;
		detail.values["coursemc"] = $scope.data.value_courseData||null;
		if(type=='gk'){
			detail.values["coursetypemc"] = $scope.data.value_courseType;
		}else{
			detail.values["coursetypemc"] = item.CL02||null;
		}
		detail.values["class"] = item.CL01||null;
		detail.values["choose"] = choose||null;
		detail.fields = ['cl01','cl02','cl03','cl04','cl05','cl06','cl07','cl08'];
//		abstractDetail();
		detail.formConfig = {
				title : title1,//detail.title+'（'+choosemc+'）学生名单',
				show  : true,
				url       : $scope.data.detailUrl,
				exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
				heads  : detail.headers,
				fields : detail.fields,
				params : abstractDetailParamXz()
			};
		$timeout(function(){});
		 $scope.showTitleRow2=false;
		 $scope.showTitleRow1=false;
	};
	/**
	 * 预测准确率
	 */
	$scope.accuracyCk=function(index){
//		abstractDetailAll('majorType','同专业同年级',$scope.data.majorType,'all');
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.values= {};
		$scope.data.abstract_detail.headers=['班级','课程属性'];
		$scope.data.index=4;
		$scope.data.abstract_detail.title=index.CL01;
		detail.values["type"] = 'accuracy';
		detail.values["majorType"] = $scope.data.majorType||null;
		detail.values["schoolYear"] = $scope.data.value_year||null;
		detail.values["termCode"] = $scope.data.value_term||null;
		detail.values["edu"] = $scope.data.value_edu||null;
		detail.values["coursemc"] = index.CL01||null;
		detail.values["coursetypemc"] = index.CL02||null;
		showLoading();
		service.getStuDetail(abstractDetailParam(), function(data){
			var acc_major_data=[],acc_other_major_data=[],rData=[acc_major_data,acc_other_major_data];
		   if(data.rows.length!=0){
			   acc_major_data=['本专业',data.rows[0].cl02];
			   acc_other_major_data=['其他专业',data.rows[0].cl02];
			   var j=1,m=2,n=2;
			for(var i=0;i<data.rows.length;i++){
				if(i==0){
					$scope.data.abstract_detail.headers[2]=data.rows[0].cl00;
				}else{
					if(data.rows[i].cl00!=data.rows[0].cl00){
						$scope.data.abstract_detail.headers[j+2]=data.rows[i].cl00;
					}
				}
				if(data.rows[i].cl01=='本专业'){
					acc_major_data[m++]=data.rows[i].cl03;
				}
				if(data.rows[i].cl01=='其他专业'){
					acc_other_major_data[n++]=data.rows[i].cl03;
				}
			}
		   }
		   $scope.showTitleRow2=false;
		   $scope.showTitleRow1=true;
		   rData[0]=acc_major_data;
		   rData[1]=acc_other_major_data;
			detail.list = rData;
			detail.page.sumcount = data.total;
			detail.page.pagecount = data.pagecount;
			detail.mask = false;
			detail.detail_show = true;
			hideLoading();
		});
	};
	service.getAdvance(function(data){
		advancedService.checkedFirst(data);
		$scope.data.advance.source = data;
	});
	/**
	 * 高级查询-切换show
	 */
	$scope.advanceShow = function(){
		$scope.data.advance.show = !$scope.data.advance.show;
	};
		/**
		 * 高级查询-change事件
		 */
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			getInfoData();//初始化总体信息(总人数,总金额,人均金额)
//			getxfInfoData();
		};
		//加载高级查询组件
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});
		/** 参数 */
		var abstractDetailParam = function(){
			var detail = $scope.data.abstract_detail,
				param  = getParam(),
				page   = getJson(detail.page),
				values = getJson(detail.values),
				fields = getJson(detail.fields),
				headers= getJson(detail.headers);
			Ice.apply(param, {page:page, values:values, fields:fields, headers:headers});
			return param;
		};
		var abstractDetailParamXz = function(){
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
		
}]);