/**
 * 学生发展-各专业毕业生情况
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzZybysqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各专业毕业生情况',
            pageHelpInfo:'学生发展-各专业毕业生情况'
        }
    }),
    tableName : "TB_JXPG_XSFZ_GZYBYSQK",
    beforeSaveInvokeService : "jxpgXsfzService?saveBeforeBysqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/zybysqk.html'}],
    requires:[]
});