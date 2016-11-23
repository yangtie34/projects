/**
 * 教学评估-教学资源-教学经费投入
 */
NS.define('Pages.sc.jxpg.jxzy.jxjftr',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教学经费投入情况',
            pageHelpInfo:'教学经费投入情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_JXJFTR",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/jxjftr.html'}]
});