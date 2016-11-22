/**
 * 2.4 教师队伍-主讲教师本科授课情况
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.jsdw.Zjjsbkskqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'主讲教师本科授课情况',
            pageHelpInfo:'主讲教师本科授课情况'
        }
    }),
    tableName : "TB_JXPG_JSDW_ZJJSBKSKQK",
    beforeSaveInvokeService : "jxpgJsdwService?saveBeforeZjjsbkskqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/zjjsbkskqk.html'}],
});