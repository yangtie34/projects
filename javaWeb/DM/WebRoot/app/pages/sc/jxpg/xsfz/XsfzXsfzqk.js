/**
 * 学生发展-学生发展情况
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzXsfzqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生发展情况',
            pageHelpInfo:'学生发展-学生发展情况'
        }
    }),
    tableName : "TB_JXPG_XSFZ_XSFZQK",
    beforeSaveInvokeService : "jxpgXsfzService?saveBeforeXsfzqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/xsfzqk.html'}],
    requires:[]
});