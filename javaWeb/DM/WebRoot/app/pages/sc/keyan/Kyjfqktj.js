/**
 * 科研经费情况统计
 */
NS.define('Pages.sc.keyan.Kyjfqktj', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'科研经费情况统计',
            pageHelpInfo:'科研经费各项个人排名、单位排名、按分类、单位、立项年度、项目状态对项目数、项目经费、支出经费进行统计。'
        }
    }),
	modelConfig: {
	    serviceConfig: {
	    	queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，宿舍组织结构
            queryZyxms :'kyService?getZyxms',// 在研项目数
            queryHbjf:'kyService?getHbjf',// 划拨经费
            queryJfzc:'kyService?getJfzc',// 经费支出
            queryKyjfSyChartData:'kyService?getKyjfSyChartData',// 经费使用
            queryKyjfPmChartData:'kyService?getKyjfPmChartData',// 科研经费
            queryKyjfLyChartData:'kyService?getKyjfLyChartData',// 经费来源
            queryKyjfQxChartData:'kyService?getKyjfQxChartData',// 经费去向

	   }
	},
	tplRequires : [
	],
    params:{},
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	init: function () {
        this.callService('queryYxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryYxzzjgTree);
			var i = 0;
			for(var key in data.queryYxzzjgTree){
				if(i==0){
					var nodeId = data.queryYxzzjgTree[key].id;
					this.navigation.setValue(nodeId);
					this.params.zzjgId = nodeId;
				}
				i++;
			}
            this.initParams();
            this.fillCompData();
        });
        var comps = this.createComps();
        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[this.pageTitle,comps],
            style : {
                "min-width" : "1000px"
            }
        });
        container.on("afterrender",function(){
            this.bindEvents();
        },this);
        this.setPageComponent(container);

	},
	yearPicker : new Exp.component.SimpleYear({
        start:1900,
        cls : 'div-border',
        width : "100%",
        margin : '0px 0px 10px 0px'
    }),
    navigation : new Exp.component.Navigation({
        margin : '0px 0px 10px 0px'
    }),
    t1 : new Exp.component.SimpleNumberWidget2({
		columnWidth : 1/5,
		border : 0,
	}),
	
	t2 : new Exp.component.SimpleNumberWidget2({
		columnWidth : 1/5,
		border : 0,
	}),
    
	t3 : new Exp.component.SimpleNumberWidget2({
		columnWidth : 1/5,
		border : 0,
	}),
    
	t4 : new Exp.component.SimpleNumberWidget2({
		columnWidth : 1/5,
		border : 0,
	}),
    
	t5 : new Exp.component.SimpleNumberWidget2({
		columnWidth : 1/5,
		border : 0,
	}),
    
	title1 : new Exp.chart.PicAndInfo({
		title : "经费使用概况统计",
		margin : "10px 0px 0px 0px",
		onlyTitle : true
	}),
	combo1 : new Ext.form.field.ComboBox({
		fieldLabel: '统计类别',
	    queryMode: 'local',
	    store:{
    	    fields: ['id', 'name'],
    	    data :  [{"id":1,"name":"按项目分类"},
		             {"id":2,"name":"按科研机构"},
		             {"id":3,"name":"按项目状态"}]
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
    	padding : 0
    }),
	
	title2 : new Exp.chart.PicAndInfo({
		title : "经费使用情况排名",
		margin : "10px 0px 0px 0px",
		onlyTitle : true
	}),
	
	combo2 : new Ext.form.field.ComboBox({
		fieldLabel: '统计类别',
	    queryMode: 'local',
	    store:{
    	    fields: ['id', 'name'],
    	    data :  [{"id":1,"name":"划拨经费"},
		             {"id":2,"name":"配套经费"},
		             {"id":3,"name":"管理经费"},
		             {"id":4,"name":"支出经费"}]
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 300,
	    value : 1,
	    margin : 0
	}),
	table21 : new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>{cxlb}排名</th>'+
				'<th>研究机构</th>'+
				'<th>金额（万元）</th>'+
			'</tr></thead><tbody><tpl if="data.length == 0"> <tr><td colspan="6"><span style="color : red;">数据未维护</span></td></tr></tpl>' +
            '<tpl for="data"><tr>'+
				'<td>{ROWNUM}</td>'+
				'<td>{MC}</td>'+
				'<td>{BKS}</td>'+
			'</tr></tpl></tbody></table>',
		data : {},
		margin : '0px 0px 10px 0px',
		columnWidth : 1/2
	}),
	table22 : new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>{cxlb}排名</th>'+
				'<th>研究项目</th>'+
				'<th>金额（万元）</th>'+
			'</tr></thead><tbody><tpl if="data.length == 0"> <tr><td colspan="6"><span style="color : red;">数据未维护</span></td></tr></tpl>' +
            '<tpl for="data"><tr>'+
				'<td>{ROWNUM}</td>'+
				'<td>{MC}</td>'+
				'<td>{BKS}</td>'+
			'</tr></tpl></tbody></table>',
		data : {},
		margin : '0px 0px 10px 0px',
		columnWidth : 1/2,
		style : {
			"min-height" : "200px"
		}
	}),
	table23 : new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>{cxlb}排名</th>'+
				'<th>姓名</th>'+
				'<th>金额（万元）</th>'+
			'</tr></thead><tbody><tpl if="data.length == 0"> <tr><td colspan="6"><span style="color : red;">数据未维护</span></td></tr></tpl>' +
            '<tpl for="data"><tr>'+
				'<td>{ROWNUM}</td>'+
				'<td>{MC}</td>'+
				'<td>{BKS}</td>'+
			'</tr></tpl></tbody></table>',
		data : {},
		margin : '0px 0px 10px 10px',
		columnWidth : 1/2
	}),
	chart2 : new Exp.chart.HighChart({
		hight : 200,
		cls : 'div-border',
		columnWidth : 1/2,
		margin : "0px 0px 10px 10px",
    	padding : 0
	}),
	
	title3 : new Exp.chart.PicAndInfo({
		title : "经费来源情况统计",
		margin : "10px 0px 0px 0px",
		onlyTitle : true
	}),
	chart3 : new Exp.chart.HighChart({
		hight : 200,
		cls : 'div-border',
		columnWidth : 1/2,
    	padding : 0
	}),
	title4 : new Exp.chart.PicAndInfo({
		title : "经费使用去向统计",
		margin : "10px 0px 0px 0px",
		onlyTitle : true
	}),
	
	chart4 : new Exp.chart.HighChart({
		hight : 200,
		cls : 'div-border',
		columnWidth : 1/2,
    	padding : 0
	}),
	createComps : function(){
		var me = this;
		// 文字统计 
		var top = new NS.container.Container({
			items : [me.t1,me.t2,me.t3,me.t4,me.t5],
			layout : 'column',
			cls : 'div-border',
			padding : 0
		});
		//经费使用情况
		var combo1 = new NS.container.Container({
			cls : 'div-border',
			items : [me.combo1],
			style : {
				"border-bottom" : "0"
			}
		});
		var middle = new NS.container.Container({
			items : [me.title1,me.chart1]
		});
		//经费使用排名
		var combo2 = new NS.container.Container({
			cls : 'div-border',
			items : [me.combo2],
			margin : "0px 0px 10px 0px"
		});
		var jf1 = new NS.container.Container({
			layout : 'column',
			items : [me.table21,me.chart2]
		});
		var jf2 = new NS.container.Container({
			layout : 'column',
			items : [me.table22,me.table23]
		});
		var jfpm = new NS.container.Container({
			items : [me.title2,combo2,jf1,jf2]
		});
		
		//经费来源情况统计
		var bl = new NS.container.Container({
			items : [me.title3,me.chart3],
			columnWidth : 1/2,
		});
		
		// 经费使用去向统计
		var br = new NS.container.Container({
			items : [me.title4,me.chart4],
			columnWidth : 1/2,
			margin : '0px 0px 0px 10px',
				
		});
		var bottom = new NS.container.Container({
			layout : 'column',
			items : [bl,br],
		});
		var result = new NS.container.Container({
			items : [me.yearPicker,me.navigation,top,middle,jfpm,bottom]
		});
		return result;
	},
	
	fillCompData : function(){
		var me = this;
		var year = me.yearPicker.getValue();
        me.callService({key:'queryZyxms',params:me.params},function(respData){
            me.t1.update({
                title : "在研项目数",
                value : respData.queryZyxms,
                unit : "项"
            });
        });
        me.callService({key:'queryHbjf',params:me.params},function(respData){
            me.t2.update({
                title : "划拨经费",
                value : respData.queryHbjf,
                unit : "万元"
            });
        });

        me.callService({key:'queryJfzc',params:me.params},function(respData){
            me.t3.update({
                title : "支出经费",
                value : respData.queryJfzc,
                unit : "万元"
            });
        });
		me.t4.update({
			title : "项目管理费",
			value : me.noDataHtml,
			unit : ""
		});
	    
		me.t5 .update({
			title : "配套经费",
			value : me.noDataHtml,
			unit : ""
		});
		me.exFunctions.combo1Change.call(me);
		me.exFunctions.combo2Change.call(me);


        me.callService([{key:'queryKyjfLyChartData',params:me.params},
            {key:'queryKyjfLyChartData',params:me.params}],function(respData){
            me.chart3.addChart(me.renderPieChart({
                divId :me.chart3.id,
                title: "经费来源情况统计,单位（万元）",
                data : respData.queryKyjfLyChartData
            }));
            me.chart4.addChart(me.renderPieChart({
                divId :me.chart4.id,
                title: "经费去向情况统计,单位（万元）",
                data :  [{name:'经费支出科目未维护',y:0}]
            }));
        });


	},
	
	
	
	bindEvents : function(){
		var me = this;
		me.yearPicker.on('validatepass',function(){
            var data = this.getValue();
            me.params.from = data.from;
            me.params.to = data.to;
			me.fillCompData();
        });
		
		me.navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
			me.fillCompData();
        });

		me.combo2.on('change',function(){
			me.exFunctions.combo2Change.call(me);
		});
	},
	exFunctions : {
		combo1Change : function(){
			var me = this;

			$("#" + me.chart1.id).html(me.loadingHtml);
            me.callService({key:'queryKyjfSyChartData',params:me.params},function(respData){
                var dt = respData.queryKyjfSyChartData;
                var brs =[],zcs=[],gls=[],pts=[];
                for(var i=0;i<dt.brs.length;i++){
                    brs.push(Number(dt.brs[i]));
                }
                for(var j=0;j<dt.zcs.length;j++){
                    zcs.push(Number(dt.zcs[j]));
                }
                for(var k=0;k<dt.gls.length;k++){
                    gls.push(Number(dt.gls[k]));
                }
                for(var m=0;m<dt.pts.length;m++){
                    pts.push(Number(dt.pts[m]));
                }
                var config = {
                    chart: { },
                    title: { text: '' },
                    xAxis: { categories: dt.dws},
                    tooltip: {
                        valueSuffix:' ',
                        shared: true
                    },
                    credits : {// 不显示highchart标记
                        enabled : false
                    },
                    yAxis: [{ labels: { format: '{value}万元' ,
                        style: { color: Highcharts.getOptions().colors[2] }},
                        title: { text: '金额',
                            style: { color: Highcharts.getOptions().colors[2] }}
                    }],
                    labels: { },
                    series: [{ type: 'column', name: '划拨经费', data: brs,tooltip: { valueSuffix: ' 万元' }},
                        { type: 'column', name: '支出经费', data: zcs,tooltip: { valueSuffix: ' 万元' }},
                        { type: 'column', name: '管理经费', data: gls,tooltip: { valueSuffix: ' 万元' }},
                        { type: 'column', name: '配套经费', data: pts,tooltip: { valueSuffix: ' 万元' } }

                    ]};
                me.chart1.redraw(config,true);
            });
		},
		combo2Change : function(){
			var me = this;
			var year = me.yearPicker.getValue();
			$("#" + me.table21.id).html(me.loadingHtml);
			$("#" + me.table22.id).html(me.loadingHtml);
			$("#" + me.table23.id).html(me.loadingHtml);
			$("#" + me.chart2.id).html(me.loadingHtml);
            this.params.cxlb = me.combo2.getValue();
            var lbmc = me.combo2.getRawValue();
            me.callService({key:'queryKyjfPmChartData',params:this.params},function(respData){
                //划拨经费
                me.chart2.addChart(me.renderPieChart({
                    divId :me.chart2.id,
                    title: "",
                    data : respData.queryKyjfPmChartData.chartData
                }));
                me.table21.update({cxlb:lbmc,data:respData.queryKyjfPmChartData.jgpm});
                me.table22.update({cxlb:lbmc,data:respData.queryKyjfPmChartData.xmpm});
                me.table23.update({cxlb:lbmc,data:respData.queryKyjfPmChartData.rypm});

            });
		}
	},
    initParams:function(){
        var navdata = this.navigation.getValue();
        this.params.zzjgId = navdata.nodes[0].id;
        this.params.cxlb = this.combo2.getValue();
        this.params.lbmc = this.combo2.getRawValue();
        var year = this.yearPicker.getValue();
        Ext.apply(this.params,year);
    },
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>数据未维护</span>"
});
