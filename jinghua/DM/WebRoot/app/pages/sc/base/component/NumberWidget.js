/**
 * 数字组件。
 * User: zhangzg
 * Date: 14-7-24
 * Time: 下午4:46
 *
 */
Ext.define('Exp.component.NumberWidget',{
    extend:'Ext.Component',
    tpl:'<div class="student-sta-tableft {[values.sfxz == true ? "student-sta-tabbg-grey" : ""]}">' +
        '<div class="student-sta-title student-sta-white student-sta-bg-{theme}" >{title}</div>' +
        '<div class="student-sta-renshu student-sta-{theme}" >{count}</div>' +
        '<div class="student-sta-liang student-sta-white student-sta-bg-{theme}">{axisname}</div>' +
        '<div class="student-sta-zhuanye">学历：{bz1}</div>' +
        '<div class="student-sta-zhuanye">学位：{bz2}</div></div>',
    initComponent:function(){
        /*增加事件*/
        this.addEvents('click');
        this.callParent(arguments);

        var me = this;
        this.on({
            click : {
                element : 'el',
                fn : function(event,el){

                    me.fireEvent('click');
                }
            }
        });
    },
    getData:function(){
        return this.data;
    }
});