/**
 * 餐厅餐别消费情况
 */
NS.define('Pages.zksf.Ctcbxfqk', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'各餐厅餐别消费情况',
            pageHelpInfo:'各餐厅餐别消费情况'
         }
    }),
	modelConfig: {
	    serviceConfig: {
	    	sslist : "xsxfqktjService?queryCtcbxfqk", //餐厅餐别消费
	    }
	},
	tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	init: function () {
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
	    	this.fillCompData();
	    	this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	},
	topDataPicker : new Ext.form.field.Date({
		fieldLabel: '日期',
		anchor: '100%',
	    format : "Y-m-d",
	    value: new Date((new Date()) - 24000*3600),
	    maxValue: new Date(),
	    border : 10,
	    width : 200
	}),
	topTale : new Ext.Component({
			tpl : '<table class="table1"><thead><tr>'+
			'<th>餐厅</th>'+
			'<th>卡机</th>'+
			'<th>早餐人数</th>'+
			'<th>早餐金额</th>'+
			'<th>午餐人数</th>'+
			'<th>午餐金额</th>'+
			'<th>晚餐人数</th>'+
			'<th>晚餐金额</th>'+
			'<th>总人数</th>'+
			'<th>总金数</th>'+
			'<th>人均</th>'+
		'</tr></thead><tbody><tpl for="."><tr>'+
			'<td rowspan="{[values.length+1]}">{name}</td>'+
			"<td colspan='10'>" +
				" <tpl for='items'><tr>" +
					'<td>{kj}</td>'+
					'<td>{r1}</td>'+
					'<td>{f1}</td>'+
					'<td>{r2}</td>'+
					'<td>{f2}</td>'+
					'<td>{r3}</td>'+
					'<td>{f3}</td>'+
					'<td>{rz}</td>'+
					'<td>{fz}</td>'+
					'<td>{rj}</td>'+
				"</tr></tpl> " +
			"</td>" +
			
		'</tr></tpl>'+
		'</tbody></table>',
	data : {},
	margin : '0px 0px 10px 0px'
	}),
	createComps : function(){
		var me = this;
		
		var tr = new NS.container.Container({
			columnWidth : 1/2,
			items : [me.topDataPicker],
			cls : "div-border",
			style : {
				"background-color" : "#FFF",
				"padding" : "5px",
				"margin-bottom" : "10px"
			}
		});
		var result = new NS.container.Container({
			items : [tr,me.topTale]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.ssdatepickerclick(me.topDataPicker.getRawValue());
	},
	bindEvents : function(){
		var me = this;
		me.topDataPicker.on("select",function(obj,val,opts){
			me.ssdatepickerclick(obj.getRawValue());
		});
	},
	ssdatepickerclick : function(date){
		var me = this;
		$("#"+me.topTale.id).html(me.loadingHtml);
		me.callService({key:'sslist',params:{xfrq : date}},function(data){
			var dts = data.sslist;
			var rs = [];
			var ct = [],cto = {};
			for(var i= 0;i < dts.length;i++){
				var dt = dts[i];
				if(!cto[dt.bm]){
					cto[dt.bm] = true;
					ct.push(dt.bm);
				}
			}
			for(var j=0;j<ct.length;j++){
				var item = {name : ct[j],length:0,items : []};
				for(var k=0;k<dts.length;k++){
					var kk = dts[k];
					if(kk.bm == ct[j]){
						item.items.push(kk);
						item.length ++;
					}
				}
				rs.push(item);
			}
			
			
			me.topTale.update(rs);
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});
