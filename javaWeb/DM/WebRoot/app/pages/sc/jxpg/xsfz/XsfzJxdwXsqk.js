/**
 * 学生发展-各教学单位学生管理人员与学生情况
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzJxdwXsqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各教学单位学生管理人员与学生情况',
            pageHelpInfo:'各教学单位学生管理人员与学生情况'
        }
    }),
    tableName : "TB_JXPG_XSFZ_JXDWXSQK",
    beforeSaveInvokeService : "jxpgXsfzService?saveBeforeJxdwXsqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/jxdwxsqk.html'}],
    requires:[]
});