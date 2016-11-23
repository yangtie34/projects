/**
 *  各教学单位教育教学研究与改革情况  
 * sunwg
 * 2015-01-16
 */
NS.define('Pages.sc.jxpg.pygc.Gjxdwjyjxyjggqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各教学单位教育教学研究与改革情况  ',
            pageHelpInfo:'各教学单位教育教学研究与改革情况  '
        }
    }),
    tableName : "TB_JXPG_PYGC_GGQK",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Gjxdwjyjxyjggqk.html'}]
});