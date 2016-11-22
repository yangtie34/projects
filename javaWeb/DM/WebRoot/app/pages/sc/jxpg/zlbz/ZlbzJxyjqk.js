/**
 * 质量保障-教学质量管理队伍结构
 * zhlc
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.zlbz.ZlbzJxyjqk',{
    extend:'Pages.sc.jxpg.exam.Examp',
    pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教学管理队伍教学研究情况',
            pageHelpInfo:'教学管理队伍教学研究情况'
        }
    }),
    tableName : "TB_JXPG_ZLBZ_JXYJQK",
    beforeSaveInvokeService : "jxpgZlbzService?saveBeforeJxyjqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/zlbz/tpl/jxyjqk.html'}],
    requires:[]
});