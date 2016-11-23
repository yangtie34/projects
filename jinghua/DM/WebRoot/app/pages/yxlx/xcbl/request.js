/**
 * 迎新办理-请求后台服务
 */
NS.define('Pages.yxlx.xcbl.request',{
	
	/*********************请求后台服务配置*********************/
	modelConfig : {
		serviceConfig : {
			//查询表头数据
			'querTableHeader' :'base_queryForAddByEntityName',
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			//查询用户拥有的当前环节
			'queryDqhj' : 'yxlx_queryDqhj',
			//查询学生信息
			'queryXsxx' : {
				service:'yxlx_queryXsxx',
				params:{entityName:'TbYxlxHjzt'}
				},
			//环节统计
			'queryHjtj':"yxlx_queryHjtj",
			//办理情况
			'queryBlqkByXs':"yxlx_queryBlqkByXs",
			//环节办理
			'updateHjzt':"yxlx_updateHjZt", 
			//环节办理
			'queryHjblByXs':"yxlx_queryHjblByXs",
			//用户办理统计
			'queryBltj':"yxlx_queryBltj",
			//学生收费
			'querySfByXs':"yxlx_querySfByXs",
			//环节拥有功能
			'queryGnByHj':"yxlx_queryGnByHj",
			//查询性别下拉列表框
			'queryXbCombox':'yxlx_queryCombox',
			//查询性别下拉列表框
			'queryMzCombox':'yxlx_queryCombox',
			//查询院系下拉列表框
			'queryYxCombox':'yxlx_queryCombox',
			//查询专业下拉列表框
			'queryZyCombox':'yxlx_queryCombox',
			//查询生源地下拉列表框
			'querySydCombox':'yxlx_queryCombox',
			//查询年级下拉列表框
			'queryNjCombox':'yxlx_queryCombox',
			//查询户口性质下拉列表框
			'queryHkxzCombox':'yxlx_queryCombox',
			//查询学制下拉列表框
			'queryXzCombox':'yxlx_queryCombox',
			//拆分身份证转换成值
			'splitCard':'yxlx_splitCard',
			//保存学生基本信息
			'saveXsInfo':'yxlx_saveXsInfo',
			//保存学生财务基本信息
			'saveXsCwInfo':'yxlx_saveXsCwInfo',
			'saveXsCwMxInfo':'yxlx_saveXsCwMxInfo',
			'updateXsCwMxInfo':'yxlx_updateXsCwMxInfo',
			//查询班级列表-gaodj
			'queryBjxx':'yxlx_queryBjxx',
			//自动分班-hanq
			'randomFb':'fbbjgl_randomFb',
			//保存迎新宿舍分配信息 由入住未确认-入住已确认
			'updateDormFp':'dorm_updateDormFpqr',
			//查询宿舍分配树
			'queryTree':'dorm_queryTree',
			//保存宿舍分配
			'saveDormInfo' :"dorm_saveDormFp",
			//宿舍置为空
			'updateDormForYx':'dorm_updateDormForYx',
			//继续办理校验
			'insertBjJyHandler':'fbbjgl_insertBjJyHandler'
			
		}
	}

	
});