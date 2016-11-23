/**
 * 教师队伍-教师教育教学改革与成果
 */
NS.define('Pages.sc.jxpg.jsdw.Jsjyjxggycg',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'教师教育教学改革与成果',
            pageHelpInfo:'教师教育教学改革与成果'
        }
    }),
    beforeSaveInvokeService :"jxpgJsdwService?saveBeforeJsjyjxggycg",
    tableName : "tb_jxpg_jsdw_jsjyjxggycg",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/jsdw/tpl/jsjyjxggycg.html'}]
});