/**
 * 教学评估-全校课程开设情况
 */
NS.define('Pages.sc.jxpg.jxzy.qxkcksqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'全校课程开设情况',
            pageHelpInfo:'全校课程开设情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_QXKCKSQK",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/qxkcksqk.html'}]
});