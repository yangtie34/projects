/**
 *  学生国际交流情况  
 * sunwg
 * 2015-01-16
 */
NS.define('Pages.sc.jxpg.pygc.Xsgjjlqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生国际交流情况  ',
            pageHelpInfo:'学生国际交流情况  '
        }
    }),
    tableName : "TB_JXPG_PYGC_XSGJJL",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Xsgjjlqk.html'}]
});