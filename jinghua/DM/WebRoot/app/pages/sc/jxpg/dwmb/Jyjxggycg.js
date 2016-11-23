/**
 * 1.4 定位目标-教育教学改革与成果
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.dwmb.Jyjxggycg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教育教学改革与成果',
            pageHelpInfo:'教育教学改革与成果'
        }
    }),
    tableName : "TB_JXPG_DWMB_JYJXGGYCG",
    beforeSaveInvokeService : "jxpgDwmbService?saveBeforeJyjxggycg", //无需校验
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/dwmb/tpl/jyjxggycg.html'}],
});