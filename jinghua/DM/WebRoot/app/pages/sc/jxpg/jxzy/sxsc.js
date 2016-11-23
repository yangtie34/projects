/**
 * 教学评估-教学资源-本科实验、实习、实训场所情况
 */
NS.define('Pages.sc.jxpg.jxzy.sxsc',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'本科实验、实习、实训场所情况',
            pageHelpInfo:'本科实验、实习、实训场所情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_SXCS",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/sxsc.html'}]
});