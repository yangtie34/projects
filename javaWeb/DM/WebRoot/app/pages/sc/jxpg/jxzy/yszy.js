/**
 * 教学评估-优势专业概览
 */
NS.define('Pages.sc.jxpg.jxzy.yszy',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'优势专业概览',
            pageHelpInfo:'优势专业概览'
        }
    }),
    tableName : "TB_JXPG_JXZY_YSZY",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/yszy.html'}]
});