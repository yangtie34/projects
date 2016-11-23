/**
 * 教学评估-教学资源-教学、科研仪器设备情况
 */
NS.define('Pages.sc.jxpg.jxzy.xyw2ts',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'校园网、图书情况',
            pageHelpInfo:'校园网、图书情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_XYW2TS",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/xyw2ts.html'}]
});