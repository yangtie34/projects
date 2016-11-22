/**
 * 2.5 教师队伍-教授、副教授讲授本科课程比例
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.jsdw.Jsfjsjsbkkcbl',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教授、副教授讲授本科课程比例',
            pageHelpInfo:'教授、副教授讲授本科课程比例'
        }
    }),
    tableName : "TB_JXPG_JSDW_JSFJSJSBKKCBL",
    beforeSaveInvokeService : "jxpgJsdwService?saveBeforeJsfjsjsbkkcbl",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/jsfjsjsbkkcbl.html'}],
});