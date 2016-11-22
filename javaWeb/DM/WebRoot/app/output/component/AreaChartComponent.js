/**
 * @class Output.AreaChartComponent
 * area图形组件
 */
Ext.define('Output.AreaChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.AreaChart',
            this.componentData);
    }
});