/**
 * 质量保障-教学质量管理队伍结构
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.zlbz.ZlbzJxgljg',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教学质量管理队伍结构',
            pageHelpInfo:'教学质量管理队伍结构'
        }
    }),
    tableName : "TB_JXPG_ZLBZ_JXGLJG",
    beforeSaveInvokeService : "jxpgZlbzService?saveBeforeJxdwjg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/zlbz/tpl/jxgljg.html'}],
    requires:[]
});