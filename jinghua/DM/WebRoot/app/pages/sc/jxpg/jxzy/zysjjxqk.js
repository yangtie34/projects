/**
 * 教学评估-各专业实践教学情况
 */
NS.define('Pages.sc.jxpg.jxzy.zysjjxqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各专业实践教学情况',
            pageHelpInfo:'各专业实践教学情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_ZYSJJXQK",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/zysjjxqk.html'}]
});