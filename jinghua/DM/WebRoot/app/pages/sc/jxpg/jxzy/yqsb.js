/**
 * 教学评估-教学资源-教学、科研仪器设备情况
 */
NS.define('Pages.sc.jxpg.jxzy.yqsb',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教学、科研仪器设备情况',
            pageHelpInfo:'教学、科研仪器设备情况'
        }
    }),
    tableName : "TB_JXPG_JXZY_YQSB",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jxzy/tpl/yqsb.html'}]
});