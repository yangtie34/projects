/**
 * 本专科生资助信息
 */
NS.define('Pages.zksf.BzksZzxx', 
{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
		data:{
			pageName:'本专科生资助信息',
			pageHelpInfo:'统计学校近年的奖学金发放、助学金发放、贷款及学费减免信息。'}
	}),
	modelConfig: {
	    serviceConfig: {
	    	jxjinfo : "bzksZzqjService?queryJxjInfoByYear", // 年度奖学金概况1
	    	jxjze  :  "bzksZzqjService?queryJxjzeByYear", //年度各院系奖学金总额
	    	jxjhdrs : "bzksZzqjService?queryJxjhdrsByYear", //年度奖学金获得人数
	    	jxjrs_lb : "bzksZzqjService?queryJxjalbhdrsByYear", // 年度各院系按奖学金类别分获得人数情况
	    	zxjinfo : "bzksZzqjService?queryZxjInfoByYear", // 年度助学金概况1
	    	zxjze  : "bzksZzqjService?queryZxjzeByYear", //年度各院系助学金总额
	    	zxjhdrs : "bzksZzqjService?queryZxjhdrsByYear", //年度助学金获得人数
	    	zxjrs_lb : "bzksZzqjService?queryZxjalbhdrsByYear", // 年度各院系按助学金类别分获得人数情况
	    	dkinfo : "bzksZzqjService?queryZxdkInfoByYear", // 年度贷款信息1
	    	dklvqk :  "bzksZzqjService?queryZxdkalbhdrsByYear", //年度各院系按贷款类别分申请通过情况
            queryJxzzjgTree: 'scService?getJxzzjgTree'// 获取导航栏数据，教学组织结构
	    }
	},
	tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
    params:{},
	init: function () {
        var me = this;
	    var comps = this.createComps();
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[comps]
	    });
        me.params.xn = me.yearPicker.getValue();
	    container.on("afterrender",function(){

            console.log(this.params.xn+"    "+me.params.xn);
            this.callService({key:'queryJxzzjgTree'},function(data){
                var me = this;
                this.navigation.refreshTpl(data.queryJxzzjgTree);
                var i = 0;
                for(var key in data.queryJxzzjgTree){

                    if(i==0){
                        var nodeId = data.queryJxzzjgTree[key].id;
                        this.navigation.setValue(nodeId);
                        this.params.zzjgId = nodeId;
                        this.params.zzjgmc = data.queryJxzzjgTree[key].mc;
                        this.params.cclx = data.queryJxzzjgTree[key].cclx;
                        this.params.qxm = data.queryJxzzjgTree[key].qxm;
                    }
                    i++;
                }

                this.navigation.on('click',function(){
                    var data = this.getValue(),len = data.nodes.length;
                    me.params.zzjgId = data.nodes[len-1].id;
                    me.params.zzjgmc = data.nodes[len-1].mc;
                    me.params.cclx = data.nodes[len-1].cclx;
                    me.params.qxm = data.nodes[len-1].qxm;
                    me.fillCompData();
                });

                this.bindEvents();

                this.fillCompData();
            },this);

	    },this);
	    this.setPageComponent(container);
	},
	yearPicker : new Exp.chart.YearPicker({margin:'10 0 0 0'}),
	title1 : new Exp.chart.PicAndInfo({
		title : "奖学金发放情况",
		picurl : "app/pages/zksf/res/jiangxuejin.jpg",
		tplHtml : "奖学金共发放金额 <span class='ft-green-bold'>{zje}</span> 元，获得奖学金人数共计 <span class='ft-green-bold'>{zrs}</span> 人，奖学金类别共计 <span class='ft-green-bold'> {types.length}</span> 种。" +
				"<tpl if='types.length &gt; 0'>其中<tpl for='types'>【{name}】奖励 <span class='ft-green-bold'>{value}</span> 元，获得人数<span class='ft-green-bold'> {num}</span>人；</tpl></tpl>" +
				"获得情况如下图所示：",
		data : {
		},
		margin : "10px 0px 0px 0px"
	}),
	combo1 : new Ext.form.field.ComboBox({
		fieldLabel: '统计内容',
	    queryMode: 'local',
	    store:{
    	    fields: ['id', 'name'],
    	    data :  [{"id":1,"name":"奖学金总额"},
		             {"id":2,"name":"奖学金获得人数"},
		             {"id":3,"name":"奖学金按类别获得人数"}]
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 300,
	    value : 1,
	    margin : 0
	}),
	chart1 : new Exp.chart.HighChart({
		height : 400,
		cls : 'div-border',
		padding :0 ,
	    margin : 0
	}),
	
	title2 : new Exp.chart.PicAndInfo({
		title : "助学金发放情况",
		picurl : "app/pages/zksf/res/zhuxuejin.jpg",
		tplHtml : "助学金共发放金额 <span class='ft-green-bold'>{zje}</span> 元，获得助学金人数共计 <span class='ft-green-bold'>{zrs}</span> 人，奖学金类别共计 <span class='ft-green-bold'>{types.length}</span> 种。" +
				"<tpl if='types.length &gt; 0'>其中<tpl for='types'>【{name}】补助金额<span class='ft-green-bold'> {value}</span> 元，获得人数<span class='ft-green-bold'> {num}</span>人；</tpl></tpl>" +
				"获得情况如下图所示：",
		data : {
		},
		margin : "10px 0px 0px 0px"
	}),
	combo2 : new Ext.form.field.ComboBox({
		fieldLabel: '统计内容',
	    queryMode: 'local',
	    store:{
    	    fields: ['id', 'name'],
    	    data :  [{"id":1,"name":"助学金总额"},
		             {"id":2,"name":"助学金获得人数"},
		             {"id":3,"name":"助学金按类别获得人数"}]
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 300,
	    value : 1,
	    margin : 0
	}),
	chart2 : new Exp.chart.HighChart({
		height : 400,
		cls : 'div-border',
		padding :0 ,
	    margin : 0
	}),
	
	title3 : new Exp.chart.PicAndInfo({
		title : "助学贷款情况",
		picurl : "app/pages/zksf/res/zhuxuedaikuan.jpg",
		tplHtml : "助学贷款申请人数 <span class='ft-green-bold'> {zrs}</span> 人,贷款类型共<span class='ft-green-bold'> {types.length}</span> 种。" +
				"<tpl if='types.length &gt; 0'>其中<tpl for='types'>【{name}】申请人数<span class='ft-green-bold'> {num}</span>人；</tpl></tpl>" +
				"获得情况如下图所示",
		data : {
		},
		margin : "10px 0px 0px 0px"
	}),
	combo3 : new Ext.form.field.ComboBox({
		fieldLabel: '统计内容',
	    queryMode: 'local',
	    store:{
    	    fields: ['id', 'name'],
    	    data :  [{"id":1,"name":"助学贷款申请通过情况"},
		             {"id":2,"name":"助学贷款按类别获得情况"}]
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 300,
	    value : 2,
	    margin : 0
	}),
	chart3 : new Exp.chart.HighChart({
		height : 400,
		cls : 'div-border',
		padding :0 ,
	    margin : 0
	}),
	
	title4 : new Exp.chart.PicAndInfo({
		title : "学费减免情况",
		picurl : "app/pages/zksf/res/xuefeijianmian.jpg",
		tplHtml : "设置学费减免名额共计<span class='ft-green-bold'> {zrs}</span>人 。获得情况如下图所示：",
		data : {
		},
		margin : "10px 0px 0px 0px"
	}),
	chart4 : new Exp.chart.HighChart({
		height : 400,
		cls : 'div-border',
		padding :0 ,
	    margin : 0
	}),
    navigation: new Exp.component.Navigation(),
	createComps : function(){
		var me = this;
		var combo1 = new NS.container.Container({
			padding:10,
	        items:[me.combo1],
	        cls : "div-border",
	        style : {'border-bottom' : '0px' }
		});

		var combo2 = new NS.container.Container({
			padding:10,
	        items:[me.combo2],
	        cls : "div-border",
	        style : {'border-bottom' : '0px' }
		});
		
		var combo3 = new NS.container.Container({
			padding:10,
	        items:[me.combo3],
	        cls : "div-border",
	        style : {'border-bottom' : '0px' }
		});
		
		var result = new NS.container.Container({
			items : [me.pageTitle,me.navigation,me.yearPicker,me.title1,combo1,me.chart1,me.title2,combo2,me.chart2,me.title3,combo3,me.chart3,me.title4,me.chart4]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		// 更新title
		me.title1.update(me.loadingHtml);
		me.callService([{key : 'jxjinfo',params : this.params}],function(data){
			me.title1.update(data.jxjinfo);
		});
		me.title2.update(me.loadingHtml);
		me.callService([{key : 'zxjinfo',params : this.params}],function(data){
			me.title2.update(data.zxjinfo);
		});
		me.title3.update(me.loadingHtml);
		me.callService([{key : 'dkinfo',params : this.params}],function(data){
			me.title3.update(data.dkinfo);
		});
		me.title4.update({zrs : me.noDataHtml});
		
		//更新chart
		me.exFunctions.fillPart1.call(me);
		me.exFunctions.fillPart2.call(me);
		me.exFunctions.fillPart3.call(me);
		me.exFunctions.fillPart4.call(me);
	},
	bindEvents : function(){
		var me = this; 
		me.yearPicker.on("change",function(year){
            me.params.xn = year;
		    me.fillCompData();
		});
		
		me.combo1.on("select",function(){
			me.exFunctions.fillPart1.call(me);
		});
		
		me.combo2.on("select",function(){
			me.exFunctions.fillPart2.call(me);
		});
		
		me.combo3.on("select",function(){
			me.exFunctions.fillPart3.call(me);
		});
	},
	exFunctions : {
		fillPart1 : function(){
			var me = this;
			me.chart1.update(me.loadingHtml);
			switch(me.combo1.getValue()){
				case 1 : 
					me.callService([{key : 'jxjze',params : me.params}],function(data){
						me.chart1.addChart(me.renderCommonChart({
							divId : me.chart1.id,
							title : me.params.xn + "奖学金总额获得情况",
							yAxis : "元",
							type :"column",
							data : data.jxjze
						}));
					});
					break;
				case 2 :
					me.callService([{key : 'jxjhdrs',params : me.params}],function(data){
						me.chart1.addChart(me.renderCommonChart({
							divId : me.chart1.id,
							title : me.params.xn + "奖学金获得人数情况",
							yAxis : "人次",
							type :"column",
							data : data.jxjhdrs
						}));
					});
					break;
				case 3 :
					me.callService([{key : 'jxjrs_lb',params : me.params}],function(data){
						me.chart1.addChart(me.renderCommonChart({
							divId : me.chart1.id,
							title : me.params.xn + "按奖学金类别区分获得人数情况",
							yAxis : "人次",
							type :"column",
							data : data.jxjrs_lb
						}));
					});
					break;
			}
		},
		
		fillPart2 : function(){
			var me = this;
			me.chart2.update(me.loadingHtml);
			switch(me.combo2.getValue()){
				case 1 : 
					me.callService([{key : 'zxjze',params : me.params}],function(data){
						me.chart2.addChart(me.renderCommonChart({
							divId : me.chart2.id,
							title : me.params.xn + "助学金总额获得情况",
							yAxis : "元",
							type :"column",
							data : data.zxjze
						}));
					});
					break;
				case 2 :
					me.callService([{key : 'zxjhdrs',params : me.params}],function(data){
						me.chart2.addChart(me.renderCommonChart({
							divId : me.chart2.id,
							title : me.params.xn + "助学金获得人数情况",
							yAxis : "人次",
							type :"column",
							data : data.zxjhdrs
						}));
					});
					break;
				case 3 :
					me.callService([{key : 'zxjrs_lb',params : me.params}],function(data){
						me.chart2.addChart(me.renderCommonChart({
							divId : me.chart2.id,
							title : me.params.xn + "按助学金类别区分获得人数情况",
							yAxis : "人次",
							type :"column",
							data : data.zxjrs_lb
						}));
					});
					break;
			}
		},
		
		fillPart3 : function(){
			var me = this;
			me.chart3.update(me.loadingHtml);
			switch(me.combo3.getValue()){
				case 1 :
					me.chart3.update(me.noDataHtml);
					break;
				case 2 :
					me.callService([{key : 'dklvqk',params : me.params}],function(data){
						me.chart3.addChart(me.renderCommonChart({
							divId : me.chart3.id,
							title : me.params.xn + "助学贷款按类别获得情况",
							yAxis : "人次",
							type :"column",
							data : data.dklvqk
						}));
					});
					break;
			}
		},
		
		fillPart4 : function(){
			var me = this;
			me.chart4.update(me.noDataHtml);
		}
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>数据未维护</span>"
});
