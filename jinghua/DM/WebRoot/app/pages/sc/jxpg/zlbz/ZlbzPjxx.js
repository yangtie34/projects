/**
 * 质量保障-评教信息
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.zlbz.ZlbzPjxx',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'评教信息',
            pageHelpInfo:'课堂教学质量评估统计'
        }
    }),
    tableName : "TB_JXPG_ZLBZ_PJXX",
    afterSaveInvokeService : "jxpgService?invokeTest",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/zlbz/tpl/pjxx.html'}],
    requires:[]
});