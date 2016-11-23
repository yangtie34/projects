/**
 * 迎新班级报到报表统计
 */
NS.define('Pages.yxlx.yxtj.YxBjbdtj', {
	extend : 'Pages.yxlx.yxtj.BaseModeTj',
	htmlstr :'app/pages/yxlx/template/count/bjbdtj.html',
	modelConfig : {
	serviceConfig : {
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			//报表统计下转-获取各报到状态名单
			'queryBdztMd':'yxlx_queryBdztMd',
			'querTableHeader' :'base_queryForAddByEntityName',
			'queryContent' :'yxlx_queryBjbdtj'//查询各班级的报到统计
			}
	
	}
	
})