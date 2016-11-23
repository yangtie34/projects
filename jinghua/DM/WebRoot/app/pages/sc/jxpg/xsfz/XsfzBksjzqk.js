/**
 * 学生发展-本科生奖贷补情况
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzBksjzqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'本科生奖贷补情况',
            pageHelpInfo:'学生发展-本科生奖贷补情况'
        }
    }),
    tableName : "TB_JXPG_XSFZ_BKSJDBQK",
    afterSaveInvokeService : "jxpgService?invokeTest",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/bksjzqk.html'}],
    requires:[]
});