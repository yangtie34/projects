/**
 *
 * User: zhangzg
 * Date: 14-8-1
 * Time: 下午2:12
 *
 */
Ext.define('Exp.component.TopBottom',{
    extend:'Ext.Component',

    listeners:{
        resize:function(){
            var data = this.translateData();
            this.update(data);
        }
    },
    initComponent:function(){
        this.tpl =
            '<div class="student-sta-marginauto" >' +
            '<div class="student-sta-leibietop-triangle"></div>' +
            '<div class="student-sta-leibietop">' +
            '<p class="student-sta-imporant" style="text-align: center;">{top.count}人</p><p style="text-align: right;">' +
                '{top.zb}%</p><p style="text-align: left;"> {top.lb}</p></div>' +
            '<div class="student-sta-leibiebottom">' +
            '<p style="text-align: left;">{bottom.lb}</p><p style="text-align: right;">{bottom.zb}%</p><p class="student-sta-imporant" style="text-align: center;">{bottom.count}人</p></div>' +
            '<div class="student-sta-leibiebottom-triangle"></div>' +
            '<div style="text-align:center;"><b>{xy}</b> ，人员学科组成多以 <b>{xk}</b> 学科人员组成。</div>' +
            '</div>';

        this.callParent(arguments);
    },
    afterRender:function(){
        var data = this.translateData();
        this.update(data);
    },
    /*转换数据*/
    translateData:function(){
        return {
            top:{
                count:0,
                zb:0.00,
                lb:'--'
            },
            bottom:{
                count:0,
                zb:0.00,
                lb:'--'
            },
            xy:'--',
            xk:'--',
            qxxk:'--'
        };
    }
});