/**
 * 图书借阅信息
 */
NS.define('Pages.zksf.Tsjyxx', {
	extend:'Template.Page',
	modelConfig: {
	    serviceConfig: {
	    	tslbs : 'tsxxtjService?queryBookTypes', // 图书类别数量
	    	tssl : 'tsxxtjService?queryBookNumber', // 图书数量
	    	tszje : 'tsxxtjService?queryBookCosts', // 图书总金额
	    	tssl_type : 'tsxxtjService?queryBookNumberByType', // 图书数量按类别统计
	    	tstz_type : 'tsxxtjService?queryBookCostByType', // 图书投资按类别统计
	    	tstz_time : 'tsxxtjService?queryBookCostByTime', // 图书投资按时间统计
	    	tsjy_type : 'tsxxtjService?queryBookJyxxByType', // 图书借阅按类型统计
	    	tsjyl_day : 'tsxxtjService?queryBookJylxxByday', // 图书日均借阅量
	    }
	},
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css','app/pages/zksf/css/tsjyxx.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	init: function () {
	    var pageTitle = new Exp.component.PageTitle({
	        data:{
	            pageName:'图书统计分析',
	            pageHelpInfo:'统计学校图书馆藏书情况，通过对图书种类、图书数量等进行统计分析学校图书情况。'}
	    });
	    var combo = this.createComps();
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[pageTitle,combo]
	    });
	    container.on("afterrender",function(){
	    	this.fillCompData();
	    	this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	},
	t1 : new Exp.component.SimpleNumberWidget2({
		data : {},
		columnWidth : 1/3,
		border : 0
	}),
	t2 : new Exp.component.SimpleNumberWidget2({
		data : {},
		columnWidth : 1/3,
	}),
	t3 : new Exp.component.SimpleNumberWidget2({
		data : {},
		columnWidth : 1/3,
	}),
	middleCombo : new Ext.form.field.ComboBox({
		fieldLabel: '统计内容',
	    queryMode: 'local',
	    store:{
    	    fields: ['id', 'name'],
    	    data :  [{"id":1,"name":"按图书类别统计图书册数"},
		             {"id":2,"name":"按图书类别统计图书资金投入"},
		             {"id":3,"name":"按时间统计图书资金投入"}]
	    },
	    displayField: 'name',
	    valueField: 'id',
	    width : 300,
	    value : 3,
	    margin : 0
	}),
	middleChart : new Exp.chart.HighChart({
		height : 300,
		cls : 'div-border',
		padding : 0,
		margin : '0px 0px 10px 0px'
	}),
	bottomTable :new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
					'<th>馆藏类别</th>'+
					'<th>藏书总数</th>'+
					'<th>投入资金（元）</th>'+
					'<th>借阅册数</th>'+
					'<th>流通率</th>'+
				'</tr></thead><tbody><tpl for="."><tr>'+
					'<td>{lb}</td>'+
					'<td>{cs}</td>'+
					'<td>{trzj}</td>'+
					'<td>{jycs}</td>'+
					'<td>{ltl}%</td>'+
				'</tr></tpl>'+
				'<tr><td colspan="6">'+
				'<div style="background-color:#ffebf6;color:#cc0000;text-align:left;">'+
				' &nbsp;● 图书流通率计算公式：流通率=（ 图书借阅册数 / 图书馆藏总数 ） * 100%'+
				'</div>'+
				'</td></tr></tbody></table>',
		data : {},
		margin : '0px 0px 10px 0px'
	}),
	footChart : new Ext.Component({
		tpl : "<div class='tsjyxx-foot-container'><table class='tsjyxx-foot-table'>" +
				"<tr><td colspan='2'><img alt='' width='110px' height='90px' src='app/pages/zksf/css/tsg.jpg'></td></tr>" +
				"<tr><td colspan='2' style='font-size: 24px'>图书馆</td></tr>" +
				"<tr><td width='50%' align='right'>累计借阅量：</td><td align='left'>{fwzl}  册</td></tr>" +
				"<tr><td width='50%' align='right'>最高日借阅量：</td><td align='left'>{zgfwl}  册</td></tr>" +
				"<tr><td width='50%' align='right'>日均借阅量：</td><td align='left'>{rjfwl}  册</td></tr> </table></div>" +
				"<div class='tsjyxx-foot-container'><table class='tsjyxx-foot-table'>" +
				"<tr><td colspan='2'><img alt='' width='110px' height='90px' src='app/pages/zksf/css/dztsg.jpg'></td></tr>" +
				"<tr><td colspan='2' style='font-size: 24px'>图书馆门禁出入</td></tr>" +
				"<tr><td width='50%' align='right'>累计学生流量：</td><td align='left'>{fwzl2} 人次</td></tr>" +
				"<tr><td width='50%' align='right'>日均学生流量：</td><td align='left'>{rjfwl2} 人次</td></tr>" +
				"<tr><td width='50%' align='right'>最高学生流量：</td><td align='left'>{zgfwl2} 人次</td></tr>	</table></div>",
		cls : "div-border",
		style : {
			"text-align" : "center",
			"padding" : "0px"
		},
		data : {}
	}),
	createComps : function(){
		var top = new NS.container.Container({
			padding:5,
			margin:"0px 0px 10px 0px",
	        autoScroll:true,
	        items:[this.t1,this.t2,this.t3],
	        style : {
	        	"background-color" : "#fefaef",
	        	"border" : "1px solid #eaeaea"
	        },
	        layout : "column"
		});
		var middleCombo = new NS.container.Container({
			padding:10,
	        items:[this.middleCombo],
	        cls : "div-border",
	        style : {
	        	'border-bottom' : '0px'
	        }
		});
		var result = new NS.container.Container({
	        items:[top,middleCombo,this.middleChart,this.bottomTable,this.footChart]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		$("#" + me.t1.id).html(me.loadingHtml);
		$("#" + me.t2.id).html(me.loadingHtml);
		$("#" + me.t3.id).html(me.loadingHtml);
		$("#" + me.bottomTable.id).html(me.loadingHtml);
		$("#" + me.footChart.id).html(me.loadingHtml);
		me.callService([{key :"tslbs",params:{}}],function(data){
			me.t1.update({
				title : "学校藏书类别数量",
				value : data.tslbs.num,
				unit : "类"
			});
		});
		
		me.callService([{key :"tssl",params:{}}],function(data){
			me.t2.update({
				title : "学校藏书册数",
				value : data.tssl.num,
				unit : "册"
			});
		});
		
		me.callService([{key :"tszje",params:{}}],function(data){
			me.t3.update({
				title : "图书投入资金",
				value : data.tszje.num,
				unit : "元"
			});
		});
		me.exFunctions.setMiddleChart(me.middleCombo.getValue(),me);
		
		me.callService([{key : 'tsjy_type',params : {}}],function(data){
			me.bottomTable.update(me.exFunctions.changeBottomTableData(data.tsjy_type));
		});
		me.callService([{key : 'tsjyl_day',params : {}}],function(data){
			me.footChart.update(data.tsjyl_day);
		});
	},
	bindEvents : function(){
		var me = this;
		me.middleCombo.on('select',function(){
			me.exFunctions.setMiddleChart(me.middleCombo.getValue(), me);
		});
	},
	exFunctions : {
		setMiddleChart : function(num,me){
			$("#" + me.middleChart.id).html(me.loadingHtml);
			switch (num) {
			case 1:
				me.callService([{key : "tssl_type",params:{}}],function(data){
					me.middleChart.addChart(
						me.renderCommonChart({
							divId : me.middleChart.id,
							title : "图书数量按类别统计",
							yAxis : "册",
							isSort : true,
							type :"column",
							data : data.tssl_type
						})
					);
				});
				break;
			case 2:
				me.callService([{key : "tstz_type",params:{}}],function(data){
					me.middleChart.addChart(
						me.renderCommonChart({
							divId : me.middleChart.id,
							title : "图书资金投入按类别统计",
							yAxis : "元",
							isSort : true,
							type :"column",
							data : data.tstz_type
						})
					);
				});
				break;
			case 3:
				me.callService([{key : "tstz_time",params:{}}],function(data){
					me.middleChart.addChart(
						me.renderCommonChart({
							divId : me.middleChart.id,
							title : "图书资金投入按时间统计",
							yAxis : "元",
							isSort : true,
							type :"spline",
							data : data.tstz_time
						})
					);
				});
				break;
			default:
				break;
			}
			
		},
		changeBottomTableData : function(data){

			return data;
		}
	},
	loadingHtml : "<div  class='loading-indicator'>正在加载....</div>"
});
