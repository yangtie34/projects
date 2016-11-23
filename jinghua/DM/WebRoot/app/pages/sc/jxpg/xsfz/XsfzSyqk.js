/**
 * 学生发展-生源情况
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzSyqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'生源情况',
            pageHelpInfo:'学生发展-生源情况'
        }
    }),
    tableName : "TB_JXPG_XSFZ_SYQK",
    afterSaveInvokeService : "jxpgService?invokeTest",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/syqk.html'}],
    requires:[]
});