/**
 * bar图形组件
 */
Ext.define('Output.BarChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 设置容器宽高
     *
     * @param {}
     *            me 容器本身
     */
    setContainerWidthAndHeight: function (me) {
        var templateType = this.componentData.templateType;
        var pe = me.el.parent(), pwidth = pe.getWidth(), height;
        if (templateType == 'single') {
            // 单分屏的容器宽高
            height = pwidth * 2 / 5;
            pwidth = pwidth * 0.8;
        } else if (templateType == 'classical') {
            // 在经典模板内是这种设置。如果换做其他模版的话，再做相应的细节调整
            pwidth = pwidth * 0.5 > 700 ? 700 : pwidth * 0.5;
            height = (pwidth * 2) / 3;// 高宽比 1:1.5
        } else if (templateType == 'quarters') {
            pwidth = pe.parent().getWidth() * 0.46;
            height = pwidth / 1.6;
        } else {
            // portal或者未知类型 则全部
            pwidth = "100%";
            height = this.componentData.height;
        }
        me.setWidth(pwidth);
        me.setHeight(height);
    },
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.BarChart',
            this.componentData);
    }
});