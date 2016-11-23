/**
 * 教学评估-学校《本科教学质量报告》支撑数据指标比较
 */
NS.define('Pages.sc.jxpg.gy.zbbj',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学校《本科教学质量报告》支撑数据指标比较',
            pageHelpInfo:'学校《本科教学质量报告》支撑数据指标比较'
        }
    }),
    tableName : "TB_JXPG_GY_ZBBJ",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/gy/tpl/zbbj.html'}]
});