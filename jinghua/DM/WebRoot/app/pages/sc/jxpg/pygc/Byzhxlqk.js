/**
 * 毕业综合训练情况
 * sunwg
 * 2015-01-15
 */
NS.define('Pages.sc.jxpg.pygc.Byzhxlqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'毕业综合训练情况',
            pageHelpInfo:'毕业综合训练情况'
        }
    }),
    beforeSaveInvokeService : "byzhxlqkService?invokeBefore",
    tableName : "TB_JXPG_PYGC_BYZHXLQK",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Byzhxlqk.html'}]
});