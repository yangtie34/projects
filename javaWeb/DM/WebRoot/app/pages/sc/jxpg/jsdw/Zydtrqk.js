/**
 * 教师队伍-专业带头人情况
 */
NS.define('Pages.sc.jxpg.jsdw.Zydtrqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'专业带头人情况',
            pageHelpInfo:'专业带头人情况'
        }
    }),
    tableName : "TB_JXPG_JSDW_ZYDTRQK",
    beforeSaveInvokeService : "jxpgJsdwService?saveBeforeZydtrqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/zydtrqk.html'}]
});