/**
 * 实验教学示范中心
 * sunwg
 * 2015-01-14
 */
NS.define('Pages.sc.jxpg.pygc.Syjxsfzx',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'实验教学示范中心',
            pageHelpInfo:'实验教学示范中心'
        }
    }),
    tableName : "TB_JXPG_PYGC_SYJXSFZX",
    ignoreXn : true,
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Syjxsfzx.html'}]
});