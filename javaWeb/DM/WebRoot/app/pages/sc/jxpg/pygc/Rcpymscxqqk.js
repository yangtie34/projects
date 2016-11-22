/**
 * 人才培养模式创新区情况
 * sunwg
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.pygc.Rcpymscxqqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'人才培养模式创新区情况',
            pageHelpInfo:'人才培养模式创新区情况'
        }
    }),
    tableName : "TB_JXPG_PYGC_RCPYMSCXQK",
    ignoreXn : true,
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Rcpymscxqqk.html'}]
});