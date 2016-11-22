/**
 * 1.2 定位目标-校领导年龄和学位结构
 * xuebl
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.dwmb.Xldnlhxwjg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'校领导年龄和学位结构',
            pageHelpInfo:'校领导年龄和学位结构'
        }
    }),
    tableName : "TB_JXPG_DWMB_XLDNLHXWJG",
    beforeSaveInvokeService : "jxpgDwmbService?saveBeforeXldnlhxwjg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/dwmb/tpl/xldnlhxwjg.html'}],
});