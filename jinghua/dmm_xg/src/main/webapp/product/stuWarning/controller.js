/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', '$timeout', 'dialog',function($scope, service, advancedService, $timeout,dialog){
	
	$scope.data = {
		mask : true,
		tab  : null,
		abstract_date : { firstLoad : true}, // 默认设置首次加载标识
		date     : null, // 顶部选择日期
		grid : {
			deptMc : '',
			list : [],
			list_all : [],
			count_all : 0,
//			mask : true,
			pagesize : 5, // 表格每次加载条数
			order : {
				all : 'desc', // 人数
				skipClasses : [null, null, null], // 人数，比例，变化
				stayLate    : [null, null, null],
				notStay     : [null, null, null],
				stayNotin   : [null, null, null]
			},
			order_type : 'all',
			order_valueType : 'count', // 默认人数排序
			order_asc  : 'desc' // 默认倒序
		},
		bzdm_xnxn:[], // 学年学期代码
		model : {
			xnxq : null,
			mold : null
		},
		param : {
			mold : null, // 页面查询类型  新疆地区
			schoolYear : null,
			termCode   : null
		},
		type  : { option : null, optionThis : null },
		distribution_info : null,
		distribution_timeout : null,
		distribution_mask    : true,
		distribution_fb_mask : false,
		grade : {},
		sex   : {},
		dept  : {},
		advance : {
			param : null
		},
		detailUrl : "pmsStuWarning/getDetail",
		exportUrl : 'pmsStuWarning/gridDetailDown',
		grid_detail : { // 表格数据分布参数
			page : {
				curpage  : 1,
				pagesize : 10,
				sumcount : null
			},
			title  : null,
			keyValue : {},
			fields : ['no','name','sexmc','deptmc','majormc','classmc','bz'],
			headers: ['学号','姓名','性别','院系','专业','班级',''],
			list   : null,
			mask   : false
		}
	};
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	
	/**
	 * { param:'', schoolYear:'', termCode:'' }
	 */
	var getParam = function(){
		var obj = {param : getAdvancedParam()};
		return Ice.apply(obj, $scope.data.param);
	}
	/** 获取JSON数据 */
	var getJson = function(obj){
		return JSON.stringify(obj);
	}
	
	/**
	 * 日期选择
	 */
	$scope.changeDate = function(){
		var $data = $scope.data,
			date  = $data.abstract_date.date;
		if(date != $data.date){
			service.isTermOver(date).then(function(data){
				if(data){
					alert(date+' 学期已结束，没有失联预警数据！');
					$data.abstract_date.date = $data.date;
				}else{
					$data.date = date; // 更新日期
					// 请求数据
					refreshTopData();
				}
			});
		}
	};
	/**
	 * 摘要
	 */
	var getAbstract = function(){
		var $data = $scope.data,
			param = getParam();
		param.date = $data.date;
		service.getAbstract(param, function(data){
			$data.abstract_date = data;
			$data.date = data.date; // 将当前显示日期其缓存
			if(data.mold){
				$data.tab = data.mold;
				$data.model.mold = data.mold[0].id;
				$data.param.mold = $data.model.mold;
			}
		});
	};
	/** 大类别切换 */
	$scope.changMold = function(value, data){
		var $data = $scope.data;
		$data.model.mold = value;
		$data.param.mold = value;
		init();
	};
	
	/**
	 * 表格
	 */
	var getDeptDataGrid = function(isOnly){
		var param = getParam(),
			$data = $scope.data,
			$grid = $data.grid;
		param.date = $data.date;
		param.type = $grid.order_type=='all' ? null : $grid.order_type;
		param.valueType = $grid.order_valueType;
		param.asc  = $grid.order_asc;
		
		$grid.mask = true;
		service.getDeptDataGrid(param, function(data, deptMc, count_all){
			$grid.mask = false;
			$grid.list_all = data;
			$grid.list = data.slice(0, $grid.pagesize);
			$grid.deptMc = deptMc;
			$grid.count_all = count_all;
			// 顺序执行 20161223
			if(!isOnly) refreshMiddleData();
		});
	};
	/** 表格加载更多 */
	$scope.unfoldGrid = function(){
		var $grid = $scope.data.grid,
			size  = $grid.list.length;
//		$grid.list = $grid.list_all.slice(0, size+$grid.pagesize);
		$grid.list = $grid.list_all;
	}
	/** 表格收缩 */
	$scope.shrinkGrid = function(){
		var $grid = $scope.data.grid,
			size  = $grid.list.length;
		$grid.list = $grid.list_all.slice(0, $grid.pagesize);
	}
	/** 表格排序 */
	$scope.order = function(type, valueType, asc){
		var $grid = $scope.data.grid;
		if(type==$grid.order_type && valueType==$grid.order_valueType && asc==$grid.order_asc) return
		// 设定排序样式
		// 清除
		for(var key in $grid.order){
			var val = null;
			if(key != 'all') val = [null, null, null];
			$grid.order[key] = val;
		}
		// 设置
		for(var key in $grid.order){
			if(key == type){
				if(key == 'all') $grid.order[key] = asc;
				else{
					if(valueType == 'count') $grid.order[key] = [asc, null, null];
					else if(valueType == 'scale') $grid.order[key] = [null, asc, null];
					else if(valueType == 'amp') $grid.order[key] = [null, null, asc];
				}
				// 设置缓存值
				$grid.order_type = type;
				$grid.order_valueType = valueType;
				$grid.order_asc = asc;
			}
		}
		// 刷新表格（只刷新表格）
		getDeptDataGrid(true);
	}

	/**
	 * 详情
	 */
	/** 摘要详情 */
	$scope.abstractDetailClick = function(type, valueType){
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title = $scope.data.date+' '+service.getTitleName(type);
		// 初始化keyValue
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue.type = type;
		keyValue.valueType = valueType;
		keyValue.date = $scope.data.date; // 哪一天的数据
		distributionDetail();
	}
	/** 表格详情点击 */
	$scope.gridDetailClick = function(type, valueType, obj){
		if(type != null){
			if(valueType == 'count' && obj[type+'_count'] == 0) return; // 数量为0的无需显示明细
		}
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title = obj.name+' '+$scope.data.date+' '+service.getTitleName(type)+"学生名单";
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue.type = type;
		keyValue.valueType = valueType;
		keyValue.date = $scope.data.date; // 哪一天的数据
		keyValue.DEPT_TEACH_ID = obj.id; // 教学机构中的招收学生单位
		distributionDetail();
	}
	
	/**
	 * 学年学期
	 */
	/** 学年学期编码 */
	var getBzdmXnXq = function(){
		service.getBzdmXnXq(function(data){
			var $data = $scope.data;
			$data.bzdm_xnxq = data;
			if(data.length > 0){
				$data.model.xnxq = data[0].id;
				var ary = $data.model.xnxq.split(',');
				$data.param.schoolYear = ary[0];
				$data.param.termCode   = ary[1];
			}
		});
	};
	/** 学年学期切换 */
	$scope.changXnXq = function(value, data){
		var ary   = value.split(','),
			schoolYear = ary[0],
			termCode   = ary[1];
		$timeout.cancel($scope.data.distribution_timeout);
		// 判断学年学期的开始结束日期是否初始化
		service.getIsSetStartEndDate(schoolYear, termCode, function(data){
			var $data = $scope.data,
				param = $data.param;
			if(data.status){
				param.schoolYear = schoolYear;
				param.termCode   = termCode;
				refreshMiddleData();
				$scope.data.distribution_info = null;
			}else{
				$data.distribution_info = getXnxqNullInfo(data.schoolYear, data.termCode);
				$data.model.xnxq = param.schoolYear+","+param.termCode;
				xnxqTimeout();
			}
		});
	};
	/** 学年学期info定时器 */
	var xnxqTimeout = function(){
		$scope.data.distribution_timeout = $timeout(function(){
			$scope.data.distribution_info = null;
		},4000);
	};
	/** 获取学年学期错误消息 */
	var getXnxqNullInfo = function(schoolYear, termCode){
		return schoolYear+'学年'+(termCode=='01'?'第一':'第二')+'学期没有设置开学时间、结束时间！请联系管理员';
	};
	
	/**
	 * 分布
	 */
	/**各类型预警分布详情*/
	var typeDetail = function(obj){ //各预警
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title = "失联预警（"+obj.name+"）学生名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','类别','日期'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','bz','date_'];
		// 初始化keyValue
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue['tag']  = true;
		keyValue['type'] = obj.data.code;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	/**各年级预警学生详情*/
	var gradeDetail = function(obj){ //各预警
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title ="失联预警（"+obj.name+"）学生名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','类别','日期'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','bz','date_'];
		// 初始化keyValue
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue['tag'] = true;
		keyValue['grade'] = obj.data.code;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	/**预警学生性别分布*/
	var sexDetail = function(obj){ //各预警
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title ="失联预警（"+obj.name+"）学生名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','类别','日期'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','bz','date_'];
		// 初始化keyValue
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue['tag'] = true;//设置进入后他下钻的另外一个方法
		keyValue['sex'] = obj.data.code;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	/**各机构学生预警详情*/
	var deptDetail = function(param,obj,data){ //各预警
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title =obj.name+"（"+obj.data.name+"）学生名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','类别','日期'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','bz','date_'];
		// 初始化keyValue
		detail.keyValue = {};
		var keyValue = detail.keyValue,obj2={};
		keyValue['tag']  = true,obj2.code=obj.data.code,obj2.deptId=obj.id,obj2.level=obj.level_;
		keyValue['dept'] = obj2;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	/**逃课学生详情*/
	//逃课分布详情（周几）
	var skipByWeekDetail = function(param,obj){ 
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title =obj.name+"逃课学生名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','日期','逃课节次'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','date_','bz'];
		// 初始化keyValue
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue['tag'] = true;
		keyValue['week'] = obj.code;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	//逃课节次分布详情
	var skipByClasDetail = function(param,obj){ 
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title =obj.name+"逃课学生名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','日期','逃课节次'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','date_','bz'];
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue['tag'] = true;
		keyValue['clas'] = obj.code;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	//上午逃课 学生下午逃课详情
	var skipByAgainDetail = function(param,obj){ 
		var detail = $scope.data.grid_detail;
		detail.page.curpage = 1;
		detail.title =obj.name+"名单";
		detail.headers=['学号','姓名','性别','院系','专业','班级','日期'];
		detail.fields =['no','name','sexmc','deptmc','majormc','classmc','date_'];
		detail.keyValue = {};
		var keyValue = detail.keyValue;
		keyValue['tag'] = true;
		keyValue['again'] = obj.code;
		keyValue.valueType = "rc";
		distributionDetail();
	};
	
	/** 基础分布 */
	var getDistribution = function(){
		var param = getParam();
		$scope.data.distribution_fb_mask = true;
		service.getDistribution(param, function(typeOption, gradeOption, sexOption){
			var $data = $scope.data;
			$data.distribution_fb_mask = false;
			Ice.apply(typeOption, {config:{on:['CLICK', typeDetail]}});
			Ice.apply(gradeOption, {config:{on:['CLICK', gradeDetail]}});
			Ice.apply(sexOption, {config:{on:['CLICK', sexDetail]}});
			$data.type.option  = typeOption;
			$data.grade.option = gradeOption;
			$data.sex.option   = sexOption;
		});
	};
	/** 各机构分布 */
	var getDeptDataList = function(){
		var param = getParam();
		$scope.data.distribution_mask = true;
		service.getDeptDataList(param, function(data, deptMc){
			$scope.data.distribution_mask = false;
			Ice.apply(data, {config:{on:['CLICK', deptDetail]}});
			$scope.data.dept.option = data;
			$scope.data.dept.deptMc = deptMc;
			// 顺序执行 20161223
			getDistribution();
		});
	};
	//逃课学生分布（新增）
	var getSkipClassByWeekDayOrClas=function(){
		var param = getParam();
		service.getSkipClassByWeekDayOrClas(param, function(weekOpt,clasOpt,whereOpt,msg,sclsOpt,sclsInfo){
			Ice.apply(weekOpt, {config:{on:['CLICK', skipByWeekDetail]}});
			Ice.apply(clasOpt, {config:{on:['CLICK', skipByClasDetail]}});
			Ice.apply(sclsOpt, {config:{on:['CLICK', skipByAgainDetail]}});
			$scope.data.week={ option:weekOpt };
			$scope.data.clas={ option:clasOpt };
			$scope.data.where={ option:whereOpt };
			$scope.data.scls={ option:sclsOpt };
			if(msg.tag==1){
				$scope.data.weekShow=true;
				$scope.data.msg1=msg.msg1;
				$scope.data.msg2=msg.msg2;
				$scope.data.sclsName=sclsInfo.name;
				$scope.data.sclsValue=sclsInfo.value;
			}else{
				$scope.data.weekShow=false;
				$scope.data.msg=msg.msg1;
			}
			
		});
	};
	
	
	/** refresh */
	// 顶部，切换日期执行刷新
	var refreshTopData = function(){
		getAbstract();
		getDeptDataGrid(); // query list
	};
	// 中部，切换学年学期刷新
	var refreshMiddleData = function(){
		var $data = $scope.data,
			param = $data.param;
		service.getIsSetStartEndDate(param.schoolYear, param.termCode, function(data){
			if(data.status){
				// 顺序执行 20161223
				getDeptDataList();
				getSkipClassByWeekDayOrClas();
				$data.distribution_info = null;
			}else{
				$data.distribution_info = getXnxqNullInfo(data.schoolYear, data.termCode);
			}
		})
	};
	/** 初始化 */
	var init = function(){
		refreshTopData(); // 顶部
		getBzdmXnXq(); // 查询标准代码
//		refreshMiddleData(); // 中部
	}
	init();
	
	
	/**
	 * 高级查询-change事件
	 */
	$scope.advanceChange = function(data){
		var param = advancedService.getParam(data);
		// 高级查询-参数
		setAdvancedParam(param);
//		refreshMiddleData();
		init();
	};
	service.getAdvance(function(data){
		$scope.data.advance.source = data;
	});
	
	/**
	 * 下钻
	 */
	var distributionDetail = function(){
		var detail = $scope.data.grid_detail;
		detail.formConfig = {
			title : detail.title,
			show  : true,
			url       : $scope.data.detailUrl,
			exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
			heads  : detail.headers,
			fields : detail.fields,
			params : gridDetailParam()
		};
		$timeout(function(){});
	};
	
	/** 获取参数 */
	var gridDetailParam = function(){
		var detail = $scope.data.grid_detail,
			param  = getParam(),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		Ice.apply(param, {keyValue:getJson(detail.keyValue), fields:fields, headers:headers});
		return param;
	};
	/** send */
	$scope.send = function(obj){
		if(!(obj.count > 0)){
			dialog.alert(obj.name+'没有预警数据，无须发送邮件！');
			return;
		}
		var $grid = $scope.data.grid,
			id    = obj.id;
		if(obj.status != 1){
			$grid.mask = true;
			service.send(id, $scope.data.date, function(reData){
				$grid.mask = false;
				if(reData.status==true){
					obj.status = 1;
					alert("发送成功");
				}else{
					alert("发送失败");
				}
			});
		}
	};
	/** 全部发送 */
	$scope.sendAll = function(){
		var $grid = $scope.data.grid,
			list  = $grid.list_all;
		var deptIds = [], obj;
		for(var i=0,len=list.length; i<len; i++){
			obj = list[i];
			if(obj.status != 1){
				deptIds.push(obj.id);
			}
		}
		if(deptIds.length > 0){
			service.send(deptIds.join(','), $scope.data.date, function(reData){
				$grid.mask = false;
				if(reData.status==true){
					obj.status = 1;
					alert(reData.info);
				}else{
					alert(reData.info);
				}
			});
		}
	};
	
}]);