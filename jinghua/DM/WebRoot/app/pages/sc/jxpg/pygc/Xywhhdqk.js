/**
 * 校园文化活动情况
 * sunwg
 * 2015-01-16
 */
NS.define('Pages.sc.jxpg.pygc.Xywhhdqk',{
	extend:'Pages.sc.jxpg.exam.Examp',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'校园文化活动情况',
            pageHelpInfo:'校园文化活动情况'
        }
    }),
    tableName : "TB_JXPG_PYGC_XYWHHD",
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/pygc/tpl/Xywhhdqk.html'}]
});