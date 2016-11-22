/**
 * 学生发展-就业生去向分布
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzJyqxfb',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'就业生去向分布',
            pageHelpInfo:'学生发展-就业生去向分布'
        }
    }),
    tableName : "TB_JXPG_XSFZ_JYQXFB",
    beforeSaveInvokeService : "jxpgXsfzService?saveBeforeJyqxfb",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/jysqxfb.html'}],
    requires:[]
});