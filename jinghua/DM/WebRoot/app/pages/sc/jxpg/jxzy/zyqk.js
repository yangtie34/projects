/**
 * 教学评估-专业情况概览
 */
NS.define('Pages.sc.jxpg.jxzy.zyqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'专业情况概览',
            pageHelpInfo:'专业情况概览'
        }
    }),
    tableName : "TB_JXPG_JXZY_ZYQK",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/zyqk.html'}]
});