/**
 * 封装饼状图
 */
NS.define('Pages.sc.ky.pie.PieDisplay',{
	extend:'Template.Page',
    init: function() {	    
    	/*var me = this;
		var divElement = "<div id='pieMain' style='border:1px red solid;'></div>";
		var htmls = this.component = new NS.Component({
			html:divElement
		});
        var container = new NS.container.Container({
            padding:5,
            items:[htmls]
        });
        container.on("afterrender", function(){
	    me.createPie({
	        	divId : 'pieMain',
	        	title:'阿萨德发射点发',
	        	width:'450',
	        	height:'310',
	        	legendData:['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎'],
	        	seriesData:[{value : 335,name : '直接访问'}, {value : 310,name : '邮件营销'}, {value : 234,name : '联盟广告'}, {value : 135,name : '视频广告'}, {value : 1548, name : '搜索引擎'}],
				clickfn: function(param){
					console.info(param);
				}
	        });
        });
        this.setPageComponent(container);*/
    },
    
    /**创建一个饼图
     * @param {} params
     * params对象的属性有：
     * divId : 饼图所需的div的Id
     * title: 饼图的标题
     * width: 饼图的宽度，默认是450px
     * height: 饼图的高度，默认是310px
     * legendData: 饼图的图例，是一个数组，例如：['直接访问', '邮件营销']
     * seriesData: 饼图所需要的数据，是一个对象数组，例如：[{value : 335,name : '直接访问'}, {value : 310,name : '邮件营销'}]
     * clickfn:传入点击函数，函数的参数是param，param代表的是点击饼点击数据
     */
    createPie : function(params){
		    if(params){
				var option = {
					title : {
						text : params.title,
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{b} : {c} ({d}%)"
					},
					legend : {
						show:function(){return (params.legendData == '' || params.legendData == null || params.legendData == undefined || params.legendData.length<1)? false : true;}(),
						orient : 'horizontal',
						x : 'center',
						y:(params.legend_y == '' || params.legend_y == null || params.legend_y == undefined || params.legend_y.length<1)?'bottom':params.legend_y,
						data : function(){return (params.legendData == '' || params.legendData == null || params.legendData == undefined || params.legendData.length<1)? [] : params.legendData;}()
					},
					noDataLoadingOption:{
				    	text :params.title+"\n暂无数据",
				        effect : 'bubble',
				        effectOption:{effect: {n: 0}},
				        textStyle : {
				            fontSize : 20
				        }
				    },
					toolbox : {
						show : true,
						feature : {
							saveAsImage : {show : true}
						}
					},
					series : [{
						type : 'pie',
						radius : '40%',
						center : ['50%', '50%'],
						data : function(){return (params.seriesData == '' || params.seriesData == null || params.seriesData == undefined || params.seriesData.length<1)? [] : params.seriesData;}()
					}]
				};
				var divElement = document.getElementById(params.divId);
				divElement.style.width =  ((params.width == '' || params.width == null || params.width == undefined) ? '40' : params.width) + '%';
				divElement.style.height = ((params.height == '' || params.height == null || params.height == undefined) ? '310' : params.height) + 'px';
				var pieEcharts = echarts.init(divElement,'blue');
				pieEcharts.setOption(option);
				if(params.clickfn){   // 注册点击事件
					pieEcharts.on(echarts.config.EVENT.CLICK, params.clickfn);
				}
				return pieEcharts;
		    }
		    return null;
    }
});