/**
 * 教学评估-各教学单位课程开设情况
 */
NS.define('Pages.sc.jxpg.jxzy.jxdwksqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各教学单位课程开设情况',
            pageHelpInfo:'各教学单位课程开设情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_JXDWKSQK",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/jxdwksqk.html'}]
});