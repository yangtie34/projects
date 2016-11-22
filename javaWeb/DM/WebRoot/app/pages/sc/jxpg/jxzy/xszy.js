/**
 * 教学评估-新设专业概览
 */
NS.define('Pages.sc.jxpg.jxzy.xszy',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'新设专业概览',
            pageHelpInfo:'新设专业概览'
        }
    }),
    tableName : "TB_JXPG_JXZY_XSZY",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/xszy.html'}]
});