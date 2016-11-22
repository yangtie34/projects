/**
 * 教学评估-教学资源-教学行政用房
 */
NS.define('Pages.sc.jxpg.jxzy.jxxzyf',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教学行政用房情况',
            pageHelpInfo:'教学行政用房情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_JXXZYF",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/jxxzyf.html'}]
});