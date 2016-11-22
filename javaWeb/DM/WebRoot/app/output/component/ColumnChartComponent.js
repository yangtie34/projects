/**
 * 柱状图形组件
 */
Ext.define('Output.ColumnChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 得到new chart对象
     * 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        var that = this;
        that.newChart = Ext.create('Output.ColumnChart', that.componentData);
    }
});