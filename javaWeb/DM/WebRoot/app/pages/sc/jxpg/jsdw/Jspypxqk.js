/**
 * 教师队伍-教师培养培训情况
 */
NS.define('Pages.sc.jxpg.jsdw.Jspypxqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教师培养培训情况',
            pageHelpInfo:'教师培养培训情况'
        }
    }),
    tableName : "TB_JXPG_JSDW_JSPYPXQK",
    beforeSaveInvokeService :"jxpgJsdwService?saveBeforeJspypxqk",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/jspypxqk.html'}]
});