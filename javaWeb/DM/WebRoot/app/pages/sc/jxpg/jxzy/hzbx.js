/**
 * 教学评估-合作办学情况
 */
NS.define('Pages.sc.jxpg.jxzy.hzbx',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'合作办学情况',
            pageHelpInfo:'合作办学情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_HZBX",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/hzbx.html'}]
});