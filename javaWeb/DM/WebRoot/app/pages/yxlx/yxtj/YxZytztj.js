/**
 * 迎新专业调整统计
 */
NS.define('Pages.yxlx.yxtj.YxZytztj', {
	extend : 'Pages.yxlx.yxtj.BaseModeTj',
	htmlstr :'app/pages/yxlx/template/count/zytztj.html',
	modelConfig : {
	serviceConfig : {
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			'queryContent' :'yxlx_queryZytztj'//查询专业调整统计
			}
	
	}
	
})