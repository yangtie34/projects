/**
 * 迎新院系报到报表统计
 */
NS.define('Pages.yxlx.yxtj.YxYxbdtj', {
	extend : 'Pages.yxlx.yxtj.BaseModeTj',
	htmlstr :'app/pages/yxlx/template/count/yxbdtj.html',
	modelConfig : {
	serviceConfig : {
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			//报表统计下转-获取各报到状态名单
			'queryBdztMd':'yxlx_queryBdztMd',
			'querTableHeader' :'base_queryForAddByEntityName',
			'queryContent' :'yxlx_queryYxbdtj'//查询各院系的报到统计
			}
	
	}
	
})