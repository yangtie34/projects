/**
 * 1.6 定位目标-教学单位学科专业概览
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.dwmb.Jxdwxkzygl',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教学单位学科专业概览',
            pageHelpInfo:'教学单位学科专业概览'
        }
    }),
    tableName : "TB_JXPG_DWMB_JXDWXKZYGL",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/dwmb/tpl/jxdwxkzygl.html'}],
});