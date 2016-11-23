/**
 * 1.5 定位目标-专业布局概览
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.dwmb.Zybjgl',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'专业布局概览',
            pageHelpInfo:'专业布局概览'
        }
    }),
    tableName : "TB_JXPG_DWMB_ZYBJGL",
    beforeSaveInvokeService : "jxpgDwmbService?saveBeforeZybjgl",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/dwmb/tpl/zybjgl.html'}],
});