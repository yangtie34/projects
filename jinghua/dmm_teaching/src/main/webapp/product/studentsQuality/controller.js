var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope', 'service','advancedService', function($scope,service,advancedService) {
	//定义页面查询条件
	$scope.cont = {
			model:{},
			advance : { param : null },
			mask : true,//状态标识
			year : (new Date()).getFullYear(),//初始化年份year : (new Date()).getFullYear(),
			yearname: '今年',
			sub : ['101','102'],//科类代码
			point :'20',//分数代码
			isChose : false,
			score_line : { // 各省详情-->?各省分数线参数
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
			score_detail :{//超出分数线表单‘查看全部’
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
			adj_detail :{//调剂率
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
			enroll_detail:{//自主招生
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
			reason_detail : {
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
			wbd_detail : { // 未报到学生详情分布
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
	$scope.updown = true;
	$scope.scorecssClick = ['','on','','','','','','','',''];//初始化点击样式
	$scope.adjustcssClick = ['','on','','','',''];
	$scope.enrcssClick = ['','on','','','',''];
	$scope.changeCssOfYear = ['has-green',''];
	$scope.textcss = 'text-greener jx-rectangle-add';
	$scope.turncssType=['prevv','prevv disable'];
	$scope.turnnexcssType=['nextt','nextt disable'];
	$scope.turncssTop=$scope.turncssType[1];
	$scope.turnnexcssTop=$scope.turnnexcssType[0];
	var getAdvancedParam = function(){ return $scope.cont.advance.show ? $scope.cont.advance.param : null; }
	var showLoading = function(){ $scope.cont.mask = true; };
	var hideLoading = function(){ $scope.cont.mask = false; };
	var querySelectType=function(){//得到学年
		service.querySelectType().then(function(data){
			showLoading();
			$scope.cont.year=(new Date()).getFullYear();//从后台获取到year后，填充进查询条件
			$scope.now_year = (new Date()).getFullYear();//初始化今年
			$scope.last_year = (new Date()).getFullYear()-1;//初始化去年
			$scope.cont.yearList = data.list;//获得学年列表
			$scope.final_year = data.list[data.list.length-1].id.split('-')[0];//取出学年列表最后一项的年份
			if(data.list.length>0){//循环，取出第一条学年
				$scope.cont.yearName = data.list[0].mc;//对yearName初始化
			}
			getInfoData();//填充year查询条件后，刷新页面
			$scope.cont.yearName = data.list[0].id;//对yearName初始化
		});
	}
	querySelectType();//首先调用,刷新页面
	//选择学年
	$scope.changeXn = function(id,data){
		$scope.cont.year = id.split('-')[0];//初始化cont.year
		if(id.split('-')[0] == (new Date()).getFullYear()){
			$scope.changeCssOfYear = ['has-green','']
			$scope.cont.yearname = '今年';
		}else if(id.split('-')[0] == ((new Date()).getFullYear()-1)){
			$scope.cont.yearname = '去年';
			$scope.changeCssOfYear = ['','has-green']
		}else{
			$scope.changeCssOfYear = ['','']
			$scope.cont.yearname = data.mc;
		}
		getInfoData();
	}
	//选择今年、去年
	$scope.changeYear=function(data){
		if(0==data){
			$scope.cont.yearname = '今年';
			$scope.cont.year = (new Date()).getFullYear();
			$scope.changeCssOfYear = ['has-green',''];
			service.querySelectType().then(function(data){
				if(data.list.length>0){//循环，取出第一条学年
					$scope.cont.yearName = data.list[0].mc;//对yearName初始化
				}
				getInfoData();//填充year查询条件后，刷新页面
				$scope.cont.yearName = data.list[0].id;//对yearName初始化
			});
			getInfoData();
		}else if(1==data){
			$scope.cont.yearname = '去年';
			$scope.cont.year = (new Date()).getFullYear()-1;
			$scope.changeCssOfYear = ['','has-green'];
			service.querySelectType().then(function(data){
				if(data.list.length>0){//循环，取出第一条学年
					$scope.cont.yearName = data.list[1].id;//对yearName初始化
				}
				getInfoData();//填充year查询条件后，刷新页面
				$scope.cont.yearName = data.list[1].id;//对yearName初始化
			});
			getInfoData();
		}
	};
	//页面查询
	var getInfoData = function(){
		getScoreInfo();
		getStus();
		//生源各科类详情
		getStuSub();
		//生源未报到人数
		service.queryStudentsNotReport($scope.cont.year,function(data){
			$scope.cont.XSBD = data;
		});
		//全部科类
		service.queryAllSub($scope.cont.year,function(data){
			$scope.allSub = data;
		});
		//生源调剂率
		getadjustInfo();
		//生源自主招生录取率
		getEnrollInfo();
		//生源未报到地址分布
		getStuFrom();
		//生源未报到原因分布
		getNRReason();
	}
	var getStus = function(){
		service.queryStudents($scope.cont.year,$scope.cont.sub,function(data){
			showLoading();
			$scope.cont.nowYear = data.nowYear;//本年
			if(data.lasYear.rsc>=0){//判断数据正负，添加格式、样式
				data.lasYear.rsc = '+'+data.lasYear.rsc;
				$scope.className1 = true;
			}else{
				$scope.className1 = false;
			};
			if(data.lasYear.pjfc>=0){
				data.lasYear.pjfc = '+'+data.lasYear.pjfc;
				$scope.className2 = true;
			}else{
				$scope.className2 = false;
			};
			if(data.lasYear.zgfc>=0){
				data.lasYear.zgfc = '+'+data.lasYear.zgfc;
				$scope.className3 = true;
			}else{
				$scope.className3 = false;
			};
			if(data.lasYear.zdfc>=0){
				data.lasYear.zdfc = '+'+data.lasYear.zdfc;
				$scope.className4 = true;
			}else{
				$scope.className4 = false;
			};
			$scope.cont.lasYear = data.lasYear;//去年
			//循环确定sub值，再调用查询方法，
		});
	}
	$scope.$watch("cont.point",function(newValue,oldValue){
	//监听输入框内数据变化
		if(oldValue != newValue){
			$scope.cont.point = newValue;
			getScoreInfo();
		}
    },true);
	/**
     * 生源各科类详情
	 */
	var getStuSub = function(){
		service.queryStudentsSub($scope.cont.year,$scope.cont.sub,function(data){
			$scope.detList = data;
			for (var i = 0; i < $scope.detList.length; i++) {//循环判断，添加样式
				$scope.detList[i].mask = true;
				if(i%2 == 0){//奇偶行判断
					$scope.detList[i].mask = false;
				}else{
					$scope.detList[i].mask = true;
				};
				if($scope.detList[i].slc >=0){
					$scope.detList[i].slc = "+"+$scope.detList[i].slc;
				};
				if($scope.detList[i].pjsc >=0){
					$scope.detList[i].pjsc = "+" + $scope.detList[i].pjsc;
				};
				if($scope.detList[i].zdsc>=0){
					$scope.detList[i].zdsc = "+" + $scope.detList[i].zdsc;		
				};
				if($scope.detList[i].zxsc >=0){
					$scope.detList[i].zxsc = "+"+$scope.detList[i].zxsc;
				};
			}
			
		});
	}
	//科类选择
	$scope.updateChose = function($event,id){
		$scope.ulclass1 = true;
		if($event.target.checked){
			$scope.cont.sub.push(id);
			if($scope.cont.sub.indexOf(id)%2 !=0){
				$scope.ulclass1 = true;
				$scope.ulclass2 = false;
			}else{
				$scope.ulclass1 = false;
				$scope.ulclass2 = true;
			}			 
				getStuSub();
	 	}else{
	 		$scope.cont.sub.splice($scope.cont.sub.indexOf(id),1);
	 		getStuSub();
	 	}
	 }
	 $scope.isChose = function(id){
		 return $scope.cont.sub.indexOf(id)>=0;
	 }
	 /**
	 * 今年超出分数线专业详情
	 */
	$scope.getscoreInfo = function(){
		var detail = $scope.cont.score_detail;
			detail.page.curpage = 1;
			detail.title = $scope.cont.yearname+'超出分数线'+$scope.cont.point+'分专业详情';
			detail.values= {};
			detail.values[''] = null;
			detail.headers = ['专业','人数','平均分','最高分','最低分','高出分数线最多（分）','相比上一年平均分'];
			detail.fields = ['name_','rs','pjf','zgf','zdf','fc','pjfc'];
			detail.detail_show = true;
			getScoreInfo();
	}
	//各省分数线点击改变样式
	$scope.scoreClick = function (param,id){
		$scope.scorecssClick = ['','','','','','','','','','']
		$scope.cont.flag = param;
		getScoreInfo();
		$scope.scorecssClick[id] = 'on';
	}
	var getScoreInfo = function(){
		service.queryStudentsScore(scoreDetailParam(),function(data){
			showLoading();
			var detail = $scope.cont.score_detail;
			$scope.cont.points =data.sl.fs;
			if(data.sl.slc >= 0){
				data.sl.slc = '增加 '+data.sl.slc;
			}else{
				data.sl.slc = '减少 '+data.sl.slc;
			}
			$scope.cont.sl = data.sl;
			$scope.cont.tabList= data.rows;
			for (var i = 0; i < $scope.cont.tabList.length; i++) {//循环判断，添加样式
				$scope.cont.tabList[i].mask = true;
				if(parseInt($scope.cont.tabList[i].pjfc.split("%")) >= 0){
					$scope.cont.tabList[i].pjfc = '+' + $scope.cont.tabList[i].pjfc;
					$scope.cont.tabList[i].mask = true;
				}else{
					$scope.cont.tabList[i].mask = false;
				}
			}
			detail.list = data.rows;
			detail.page.sumcount = data.total;
			detail.page.pagecount = data.pagecount;
			detail.mask = false;
			});
	}
	var scoreDetailParam = function(){
		var detail = $scope.cont.score_detail,
			param  = getParam(),
			page   = getJson(detail.page),
			year = $scope.cont.year,
			point = $scope.cont.point,
			flag = $scope.cont.flag,
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		if(point==null || isNaN(point)==true){
			point = '1';//如果输入控制或者非数字值，将point置零
			$scope.cont.point = '1';
		};
	Ice.apply(param, {page:page,year:year,point:point,flag:flag,fields:fields,headers:headers});
	return param;
	}
	/**
	 * '详情按钮'-->各省分数线详情查询
	 */
	//各省分数线点击弹框
	$scope.getScore = function(item){
		$scope.cont.majorId = item;
		var detail = $scope.cont.score_line;
			detail.page.curpage = 1;
			detail.title = '';
			detail.values= {};
			detail.values[''] = null;
			detail.headers = ['省份','分数线'];
			detail.fields = ['name_','enroll_score'];
		getScoreLine();
	}
	var getScoreLine = function(){
		service.queryScoreLineByPro(gsfsxDetailParam(),function(data){
			showLoading();
			var detail = $scope.cont.score_line;
				detail.list = data.rows;
				detail.page.sumcount = data.total;
				detail.page.pagecount = data.pagecount;
				detail.mask = false;
				detail.detail_show = true;
				detail.title = $scope.cont.yearname+data.rows[0].zymc+'专业在各省分数线';//弹框的标题
		})
	};
	var gsfsxDetailParam = function(){
		var detail = $scope.cont.score_line,
			param  = getParam(),
			page   = getJson(detail.page),
			flag = $scope.cont.flag,
			year = $scope.cont.year,
			majorId = $scope.cont.majorId,
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		Ice.apply(param, {page:page, flag:flag,year:year,majorId:majorId,fields:fields,headers:headers});
		return param;
	}
	/**
	 * 调剂率
	 */
	//调剂率表点击排序
	$scope.adjustClick = function(param,id){
		$scope.adjustcssClick = ['','','','','','']
		$scope.cont.flag = param;
		getadjustInfo();
		$scope.adjustcssClick[id] = 'on';
	}
	$scope.getAdjustInfo = function(){
		var detail = $scope.cont.adj_detail;
			detail.page.curpage = 1;
			detail.title = $scope.cont.yearname+'各专业调剂率详情';
			detail.values= {};
			detail.values[''] = null;
			detail.headers = ['专业','人数','调剂率','相比上一年'];
			detail.fields = ['name_','zrs','tjl','tjlc'];
			detail.detail_show = true;
		getadjustInfo();
	}
	var getadjustInfo = function(){
		service.queryStudentsAdjust(adjustDetailParam(),function(data){
			showLoading();
			var detail = $scope.cont.adj_detail;
				detail.list = data.rows;
				detail.page.sumcount = data.total;
				detail.page.pagecount = data.pagecount;
				detail.mask = false;
			$scope.cont.TJSL = data.sl;//
			$scope.cont.adjList= data.rows;//
			for (var i = 0; i < $scope.cont.adjList.length; i++) {//循环判断，添加样式
				$scope.cont.adjList[i].mask = true;
				if(parseFloat($scope.cont.adjList[i].tjlc.split("%")) >= 0){
					$scope.cont.adjList[i].tjlc = "+" + $scope.cont.adjList[i].tjlc;
					$scope.cont.adjList[i].mask = true;
				}else{
					$scope.cont.adjList[i].mask = false;
				}
			}
		});
	}
	var adjustDetailParam = function(){
		var detail = $scope.cont.adj_detail,
			param  = getParam(),
			page   = getJson(detail.page),
			year = $scope.cont.year,
			flag = $scope.cont.flag,
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		Ice.apply(param, {page:page,year:year,flag:flag,fields:fields,headers:headers});
		return param;
	}
	/**
	 * 自主招生
	 */
	//自主招生表点击排序
	$scope.enrClick = function(param,id){
		$scope.enrcssClick = ['','','','','','']
		$scope.cont.flag = param;
		getEnrollInfo();
		$scope.enrcssClick[id] = 'on';
	}
	$scope.getEnrollInfo = function(){
		var detail = $scope.cont.enroll_detail;
			detail.page.curpage = 1;
			detail.title = '';
			detail.values= {};
			detail.values[''] = null;
			detail.headers = ['专业','人数','录取率','相比上一年'];
			detail.fields = ['zymc','zyrs','lql','lqlc'];
			detail.detail_show = true;
		getEnrollInfo();
	}
	var getEnrollInfo = function(){
		service.queryStudentsEnroll(enrollDetailParam(),function(data){
			showLoading();
			$scope.cont.LQSL = data.sl;
			$scope.cont.enrList = data.rows;
			var detail = $scope.cont.enroll_detail;
				detail.list = data.rows;
				detail.page.sumcount = data.total;
				detail.page.pagecount = data.pagecount;
				detail.mask = false;
				detail.title = $scope.cont.yearname+'各专业自主招生录取详情';
			$scope.cont.LQSL = data.sl;
			$scope.cont.enrList = data.rows;
			for (var i = 0; i < $scope.cont.enrList.length; i++) {//循环判断，添加样式
				$scope.cont.enrList[i].mask = true;
				if(parseInt($scope.cont.enrList[i].lqlc.split("%")) >= 0){
					$scope.cont.enrList[i].lqlc = "+" + $scope.cont.enrList[i].lqlc;
					$scope.cont.enrList[i].mask = true;
				}else{
					$scope.cont.enrList[i].mask = false;
				}
			}
		});
	}
	var enrollDetailParam = function(){
		var detail = $scope.cont.enroll_detail,
			param  = getParam(),
			page   = getJson(detail.page),
			year = $scope.cont.year,
			flag = $scope.cont.flag,
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		Ice.apply(param, {page:page,year:year,flag:flag,fields:fields,headers:headers});
		return param;
	}
	/**
	 * 未报到学生地理分布
	 */ 
	var getStuFrom = function(){
		service.queryStudentsNotReportByLocal($scope.cont.year,$scope.cont.xzqh,$scope.updown,function(data) {
			showLoading();
			$scope.cont.fromCfg = data.fromCfg;
			$scope.cont.from = data.from;
			$scope.cont.level = data.cc;
			if(data.maptype=='中国'){
				$scope.cont.mapt = '全国';
			}else{
				$scope.cont.mapt =data.maptype;
			}
			$scope.cont.xzqh = data.from[0].pid;
		});
	}
	var stuWBDList = function(){
		service.queryWbdDetail(wbdDetailParam(),function(data){
			var detail = $scope.cont.wbd_detail;
				detail.list = data.rows;
				detail.page.sumcount = data.total;
				detail.page.pagecount = data.pagecount;
				detail.mask = false;
				detail.detail_show = true;
		})
	}
	var wbdDetailParam = function(){
		var detail = $scope.cont.wbd_detail,
			param  = getParam(),
			page   = getJson(detail.page),
			pid = $scope.cont.xzqh;
			year = $scope.cont.year,
			fields = getJson(detail.fields),
			headers= getJson(detail.headers),
		Ice.apply(param, {page:page, pid:pid,year:year,fields:fields, headers:headers});
		return param;
	}
 	$scope.mapClick = function(params) {
 		$scope.mapType = params;
		var list = $scope.cont.from;
			for (var i = 0; i < list.length; i++) {
				var mc = list[i].name;
				if ($scope.mapType == mc) {
					$scope.cont.xzqh = list[i].id;
				}
			}
		$scope.updown = true;
		getStuFrom();
	}
 	$scope.pageUp = function() {
			$scope.updown = false;
			var list = $scope.cont.from;
			$scope.cont.xzqh = list[0].id;
			getStuFrom();
	};
	//地图下  点击弹框
	$scope.stuListClick = function(){
		var detail = $scope.cont.wbd_detail;
			detail.page.curpage = 1;
			detail.title = $scope.cont.yearname +$scope.cont.mapt+ '未报到学生';
			detail.values= {};
			detail.headers= ['序号','姓名','学号','性别','入学年级','地区','院系','专业','未报到原因'];
			detail.fields = ['rn','name_','xh','sexmc','nj','sfmc','yxmc','zymc','wbdmc'];
		stuWBDList();
	};
	var getNRReason = function(){
		//生源未报到原因
		service.queryStudentsNotReportReason($scope.cont.year,function(reason){
			showLoading();
			$scope.cont.rea = reason;
			Ice.apply(reason, {config:{on:['CLICK', reasonDetail]}});
		});
	};
	/**
	 * 未报到原因详情
	 */
	//饼图展示
	var reasonDetail = function(param){
		var detail = $scope.cont.reason_detail;
		detail.headers= ['序号','考生号','姓名','专业','生源地','未报到原因'];
		detail.fields = ['nm','xsno','xsmc','zymc','sydmc','reasonmc'];
		notReportReasonDetailBefore(param, 'notReport');
	}
	var notReportReasonDetailBefore = function(param, type){
		var detail = $scope.cont.reason_detail;
		detail.title  = $scope.cont.yearname+'未报到（'+param.name+'）学生';
		detail.page.curpage = 1;
		detail.values = param.data.code;
		$scope.cont.values = param.data.code;
		notReportReasonDetail();
	}
	var notReportReasonDetail = function(){
		service.getNotReportReason(distributionDetailParam(), function(data){
			showLoading();
			var detail = $scope.cont.reason_detail;
			detail.list = data.rows;
			detail.page.sumcount = data.total;
			detail.page.pagecount = data.pagecount;
			detail.mask = false;
			detail.detail_show = true;
		});
	}
	var distributionDetailParam = function(){
		var detail = $scope.cont.reason_detail,
			param  = getParam(),
			page   = getJson(detail.page),
			values = $scope.cont.values,
			fields = getJson(detail.fields),
			headers= getJson(detail.headers),
		    year = $scope.cont.year;
		Ice.apply(param, {page:page, values:values, fields:fields, headers:headers,year:year});
		return param;
	}
	/** 翻页 */
	$scope.$watch("cont.score_line.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.cont.score_line.mask = true;
			$scope.cont.score_line.page.curpage = newVal;
			getScoreLine();
		}
	},true);
	$scope.$watch("cont.score_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.cont.score_detail.mask = true;
			$scope.cont.score_detail.page.curpage = newVal;
			getScoreInfo();
		}
	},true);
	$scope.$watch("cont.adj_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.cont.adj_detail.mask = true;
			$scope.cont.adj_detail.page.curpage = newVal;
			getadjustInfo();
		}
	},true);
	$scope.$watch("cont.enroll_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.cont.enroll_detail.mask = true;
			$scope.cont.enroll_detail.page.curpage = newVal;
			getEnrollInfo();
		}
	},true);
	$scope.$watch("cont.reason_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.cont.reason_detail.mask = true;
			$scope.cont.reason_detail.page.curpage = newVal;
			notReportReasonDetail();
		}
	},true);
	$scope.$watch("cont.wbd_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.cont.wbd_detail.mask = true;
			$scope.cont.wbd_detail.page.curpage = newVal;
			stuWBDList();
		}
	},true);
	/**
	 * 
	 */
	var getParam = function(){
		var obj = Ice.apply({},$scope.cont.param);
		obj.param = getAdvancedParam();
		obj.lx = $scope.cont.model.lx_id;
		return obj;
	}
	var getJson = function(obj){
		return JSON.stringify(obj);
	}
	/**
	 * 高级查询-切换show
	 */
	$scope.advanceShow = function(){
		$scope.cont.advance.show = !$scope.cont.advance.show;
	};
	/**
	 * 导出-下载
	 */
	$scope.listDown= function(item){
		if(item=='1'){
			var param = gsfsxDetailParam();
			param.fileName = $scope.cont.score_line.title;
		}else if(item=='2'){
			var param = scoreDetailParam();
			param.fileName = $scope.cont.score_detail.title;
		}else if(item=='3'){
			var param = adjustDetailParam();
			param.fileName = $scope.cont.adj_detail.title;
		}else if(item=='4'){
			var param = enrollDetailParam();
			param.fileName = $scope.cont.enroll_detail.title;
		}else if(item=='5'){
			var param = wbdDetailParam();
			param.fileName = $scope.cont.wbd_detail.title;
		}else if(item=='6'){
			var param = distributionDetailParam();
			param.fileName = $scope.cont.reason_detail.title;
		}
		param.flag = item;
		service.stuListDown(param,function(data){
			showLoading();
		})
	};
}]);
		