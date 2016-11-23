/**
 * 1.1 定位目标-办学指导思想
 * xuebl
 * 2015-01-15
 */
NS.define('Pages.sc.jxpg.dwmb.Bxzdsx',{
	
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'办学指导思想',
            pageHelpInfo:'办学指导思想'
        }
    }),
    ignoreXn : true,
    tableName : "TB_JXPG_DWMB_BXZDSX",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/dwmb/tpl/bxzdsx.html'}],
});
