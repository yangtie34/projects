/**
 * 区域图形组件。
 * User: zhangzg
 * Date: 14-7-24
 * Time: 下午3:03
 *
 */
Ext.define('Exp.component.ZonePart',{
    extend:'Ext.Component',
    style:{
        background:'#fff',
        padding:'10px'
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

        var tplStr = '<div class="student-sta-overFlow">' +
            '<div class="student-sta-floatLeft student-sta-bigfont">{title}</div>' +
            '<div class="student-sta-floatRight">{text} ' +
            '<span class="student-sta-weightbold student-sta-bigfont">{boldText}</span></div></div>' +
            '<div class="student-sta-clear"></div>' +
            '<div id="{0}_dsfChartRenderId" style="height: {chartHeight}px"></div>';

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