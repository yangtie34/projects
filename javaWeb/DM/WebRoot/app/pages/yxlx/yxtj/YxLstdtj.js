/**
 * 迎新绿色通道报表统计
 */
NS.define('Pages.yxlx.yxtj.YxLstdtj', {
	extend : 'Pages.yxlx.yxtj.BaseModeTj',
	htmlstr :'app/pages/yxlx/template/count/lstdtj.html',
	modelConfig : {
	serviceConfig : {
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			'queryContent' :'yxlx_queryLstdtj'//查询各院系的绿色通道办理情况统计
			}
	
	}
	
})