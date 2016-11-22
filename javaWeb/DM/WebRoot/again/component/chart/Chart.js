/*****************************************************
 * 这里要做什么?
 *     一个图表的封装,包含的功能应该满足:
 *     1、图形的自动收缩(必须)
 *     2、对类型的控制(必须)
 *     3、大小的自定义
 *     4、是否支持HTML5类型(牵扯到浏览器类型判断,以及html定义的DOCTYPE)
 *     5、不仅仅满足于fusioncharts本身
 *     6、良好的扩展性,对其他图形报表的扩展支持
 *     7、格式的简易和统一????
 *     
 * 
 ****************************************************/

Ext.define('NS.chart.Chart', {
	extend : 'NS.container.Container',
	
	isHtml5 : false,//默认不支持html5样式的渲染
	chartType:'fusioncharts',
	renderType:null,//图形渲染类型
	readerType : 'JSON',//暂根据FusionCharts支持JSON、XML两种格式
	data : null,
	layout:'card',//card  fit下html5错误
	url : null,//url与data数据同时存在时,优先支持data.
	
	//样式调整
	initComponent : function() {
		this.initChart();//初始化图形
		this.addEvents('whenRender');
		//当想使用原生rennder事件的时候,该对象提供了额外的事件供使用者使用
		this.on({
			afterrender : function(con,eOpts){//不使用afterrender,考虑到该事件常被使用 render
				this.id = con.el.id;
				this._render();
				this.fireEvent('whenRender',con,eOpts);
			},
			scope : this
		});
		this.callParent(arguments);
	},
	initChart : function(){
		this.myChart = null;
		var chartType = this.chartType.toLowerCase(),
			data = this.data,
			url = this.url;
		switch(chartType){//方便今后扩展其他类型的图表
		    case 'fusioncharts':
		       	 this.myChart = this.createFusionCharts();
		       	 break;
			default :
				 this.myChart = this.createFusionCharts();
		}
		if(this.myChart == null){
		   throw  '创建图表过程中发生错误!请检查您传入的数据!或联系相关负责人员。';
		}
		console.log(this.myChart);
		if(data==null&&url==null){//如果均为空,直接渲染
			//throw '图表需要的数据为空!';
		}
		if(data!=null){//如果仅支持data方式,优先支持data
			this.loadData(data);return;	
		}
		if(url!=null){//如果仅支持url方式
			this.load(url);return;
		}
	},
	_render : function(){
		this.myChart.render(this.id);
	},
	/**
	 * 创建fusioncharts图表
	 * @returns
	 */
	createFusionCharts : function() {
		var chartId = 'myChartID_' + this.id,
		    myChart = null,
		    swf = this.translateSwfUrl(),
		    height = '100%',
		    width = '98%';
		    //height = this.height||'100%',
		    //width = this.width||'100%';
		if(swf!=null){
			if(this.isHtml5){//如果支持html5
				FusionCharts.setCurrentRenderer('javascript');
			}else{
				FusionCharts.setCurrentRenderer('flash');
			}
			myChart = new FusionCharts(swf,chartId,width,height,'0','1');
			//汉化部分
			myChart.configure({
				ChartNoDataText : '该区域内,无数据供图表展现!',
				InvalidXMLText : '数据格式有误!',
				LoadingText:'数据加载中...'
			});
			
		}
		return myChart;
	},
	/**
	 * 转换swf文件路径
	 * @returns {Boolean}
	 */
	translateSwfUrl:function(){
		var renderType = this.renderType.toLowerCase();
		//用于支持的渲染样样式对应的swf文件 可根据今后不同情况做相应的扩展
		var swfUrlMap = {
				column3d :'FusionCharts/charts/Column3D.swf',
				column2d:'FusionCharts/charts/Column2D.swf'
		};
		return swfUrlMap[renderType]||null;
	},
	
	loadData : function(data) {
		var readerType = this.readerType.toLocaleLowerCase();
		if(readerType=='json'){
			this._loadJSONData(data);
		}else if(readerType=='xml'){
			this._loadXMLData(data);
		}else{
			throw '您的图表未设置读取类型或类型设置超出系统支持范围.系统暂支持JSON、XML格式.';
		}
	},
	_loadJSONData : function(data) {
		//对data的转换-暂空方法
		data = this._translateJSONData(data);
		this.myChart.setJSONData(data);
	},
	_loadXMLData : function(data) {
		data = this._translateXMLData(data);
		this.myChart.setXMLData(data);
	},
	load : function(url) {
		var readerType = this.readerType.toLocaleLowerCase();
		if(readerType=='json'){
			this._loadJSONUrl(url);
		}else if(readerType=='xml'){
			this._loadXMLUrl(url);
		}else{
			throw '您的图表未设置读取类型或类型设置超出系统支持范围.系统暂支持JSON、XML格式.';
		}
	},
	_loadXMLUrl : function(url) {
		this.myChart.setXMLUrl(url);
	},
	_loadJSONUrl : function(url) {
		this.myChart.setJSONUrl(url);
	},
	/**
	 * 转换给予的json数据(为了今后统一图形风格,_translateXMLData同)
	 * @param data
	 * @returns
	 */
	_translateJSONData:function(data){
		return data;
	},
	/**
	 * 转化给予的xml数据
	 * @param data
	 * @returns
	 */
	_translateXMLData:function(data){
		return data;
	}
});