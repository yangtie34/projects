/**
 * 区域组件。
 * User: zhangzg
 * Date: 14-7-24
 * Time: 下午3:03
 *
 */
Ext.define('Exp.component.ZonePart2',{
    extend:'Ext.Component',
    style:{
        background:'#fff'
    },
    width:'100%',
    listeners:{
        resize:function(){
            if(this._chartCompo){
                this._chartCompo.fireEvent('resize');
            }
        }
    },
    initComponent:function(){
        var tplStr = '<div class="student-sta-zonepart"><table style="width:100%;height: 100%"><tr><td style="width:150px;">' +
            '<div class="student-sta-left" style="background:#e4e4e4;height:100%;width:100%;border-radius:5px 0px 0px 5px;position:relative;">'+
            '<tpl for=".">'+
                '<div class="student-sta-ti-title student-sta-white student-sta-bg-{theme}" style="margin:10px 10px;">{title}</div>' +
                '<div class="student-sta-renshu student-sta-{theme}">{count}</div>' +
                '<div class="student-sta-liang student-sta-white student-sta-bg-{theme}">{axisname}</div>' +
            '</tpl>'+
            '<div class="student-sta-sanjiao"></div>' +
            '</div></td><td><div id="{0}_dsfChartRenderId" class="student-sta-left" style="height:100%;width:100%;padding: 10px;">' +
            '</div></td></tr></table></div>';
        this.tpl = Ext.String.format(tplStr,this.id);
        this.callParent(arguments);
    },
    /**
     * 添加图形组件。
     * @param Exp.chart.Chart
     */
    insertChart:function(compo){
        compo.render(this.id+"_dsfChartRenderId");
        this._chartCompo = compo;
    },
    /**
     * 获取图形组件。
     * @return {*}
     */
    getChart:function(){
        return this._chartCompo;
    }
});