/**
 * 学生发展-学生管理人员结构
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzXsglyjg',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生管理人员结构',
            pageHelpInfo:'学生发展-学生管理人员结构'
        }
    }),
    tableName : "TB_JXPG_XSFZ_XSGLYJG",
    beforeSaveInvokeService : "jxpgXsfzService?saveBeforeXsglyjg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/xsglyjg.html'}],
    requires:[]
});