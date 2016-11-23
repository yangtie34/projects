/**
 * 教师队伍-学校实验系列人员结构
 */
NS.define('Pages.sc.jxpg.jsdw.Xxsyxlryjg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学校实验系列人员结构',
            pageHelpInfo:'学校实验系列人员结构'
        }
    }),
    tableName : "TB_JXPG_JSDW_XXSYXLRYJG",
    beforeSaveInvokeService : "jxpgJsdwService?saveBeforeXxsyxlryjg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/xxsyxlryjg.html'}]
});