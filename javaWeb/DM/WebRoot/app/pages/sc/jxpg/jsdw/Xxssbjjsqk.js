/**
 * 2.1 教师队伍-学校生师比及教师情况
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.jsdw.Xxssbjjsqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学校生师比及教师情况',
            pageHelpInfo:'学校生师比及教师情况'
        }
    }),
    tableName : "TB_JXPG_JSDW_XXSSBJJSQK",
    beforeSaveInvokeService : "jxpgJsdwService?saveBeforeXxssbjjsqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/xxssbjjsqk.html'}],
});