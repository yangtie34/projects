/**
 *  学生社团情况  
 * sunwg
 * 2015-01-16
 */
NS.define('Pages.sc.jxpg.pygc.Xsstqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生社团情况  ',
            pageHelpInfo:'学生社团情况  '
        }
    }),
    tableName : "TB_JXPG_PYGC_XSST",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Xsstqk.html'}]
});