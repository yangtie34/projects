/**
 * 学生发展-生源情况
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.xsfz.XsfzZsbdqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各专业（大类）本科生招生报到情况',
            pageHelpInfo:'各专业（大类）本科生招生报到情况'
        }
    }),
    tableName : "TB_JXPG_XSFZ_ZSBDQK",
    beforeSaveInvokeService : "jxpgXsfzService?saveBeforeXsbdqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/xsfz/tpl/zsbdqk.html'}],
    requires:[]
});