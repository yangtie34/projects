/**
 * 教学评估-精品（优秀）课程（群）建设
 */
NS.define('Pages.sc.jxpg.jxzy.jpkcjs',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'精品（优秀）课程（群）建设',
            pageHelpInfo:'精品（优秀）课程（群）建设'
        }
    }),
    tableName : "TB_JXPG_JXZY_JPKCJS",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/jpkcjs.html'}]
});