/**
 * HighChart图形组件，封装highcharts,依赖jquery
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午2:40
 */
Ext.define('Exp.chart.HighChart',{
    extend : 'Ext.Component',
    margin:0,
    listeners:{
        resize:function(){
            var width = this.getWidth() - 10,
                height = this.getHeight() - 10;
            if(this.chart){
                this.chart.setSize(width,height);
            }

        }
    },
    afterRender : function(){
        this.padding = 0;// 屏蔽padding属性。
        this.callParent(arguments);
        this.domId =this.getEl().id;
        Ext.EventManager.onWindowResize(function(){
            this.fireEvent('resize');
        },this);
    },
    /**
     * 更改图形组件的数据。
     */
    redraw:function(config,redraw){
        if(redraw){
            $('#'+this.domId).highcharts(config);
            this.chart = $('#'+this.domId).highcharts();
            this.fireEvent('resize');
        }
    },
    /**
     * 添加chart组件。
     * @param chart
     */
    addChart:function(chart){
        this.chart = chart;
    },
	height : 400,
    /**
     * 移除chart组件。
     */
    removeChart:function(){
        $('#'+this.domId).removeChild();
    }
});