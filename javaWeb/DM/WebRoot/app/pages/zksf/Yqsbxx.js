/**
 * 学校仪器设备信息
 */
NS.define('Pages.zksf.Yqsbxx', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'main',path : 'app/pages/zksf/tpl/yqsbxx_main.html'}
	              ],
    mixins : [
              "Pages.zksf.comp.Scgj"
    ],
    cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/yqsbxx.css','app/pages/zksf/css/table.css'],
    init : function() {
            this.initComponent();
    }, 
    modelConfig : {
	      serviceConfig : {
	    	  sbxxgk : 'yqsbxxService?queryYqgk', // 概况统计
	    	  sbly : 'yqsbxxService?queryYqly', // 来源概况
	    	  sblyByyx : 'yqsbxxService?queryYqlyByYx', //按院系查询设备来源情况
	    	  sbyt : 'yqsbxxService?queryYqyt', // 用途概况
	    	  sbytByYx : 'yqsbxxService?queryYqytByYx',//按院系查询设备用途情况
	    	  sbzt : 'yqsbxxService?queryYqzt',  // 状态概况	
	    	  sbztByYx : 'yqsbxxService?queryYqztByYx',//按院系查询设备用途情况
	    	  yxsbzk : 'yqsbxxService?queryYzzkByYx', //查询各院系仪器设备状况
	    	  sbjzqj : 'yqsbxxService?querySbjzqj', //设备价值区间统计
	    	  sbflxx : 'yqsbxxService?querySbflxx',// 国别大类
			  sbjfzc:'yqsbxxService?querySbjfzc',// 经费组成
			  sbdwlb:'yqsbxxService?querySbdwlb'// 单位类别统计设备分布
	      }
	 },
	topchart : new Exp.chart.HighChart({height : 300}),
    initComponent : function() {
    	var page =this.component= new NS.Component({
	            border : true,
	            autoScroll : true,
	            html : this.main,
	            autoShow : true
        });
    	page.on('click',function(event){
    		if ($(event.getTarget()).attr("attr") == 'help') {
				$("#zksf_yqsbxx_help").slideToggle();
			}
    	});
        page.on('afterrender',function(){
        	this.bindBtnEvent();
        	this.renderChildComp();
        },this);
        this.setPageComponent(page);
    },
    //渲染各个小组件
    renderChildComp : function(){
    	var me = this;
    	
    	me.topchart.render('zksf_yqsbxx_top_chart');
    	
    	me.callService([{key:'sbxxgk',params:{}}],function(data){
    		me.data.overview = data.sbxxgk[0];
    		me.render("zksf_yqsbxx_ow",me.overview,me.data.overview);
        },me);
    	
    	$("#zksf_yqsbxx_top_btn").find("input[name=sourceBtn]").click();
    	
    	me.callService([{key:"yxsbzk",params:{}}],function(data){
    		me.data.middle = data.yxsbzk;
    		me.render("zksf_yqsbxx_middle",me.middle,me.data.middle);
    	},me);
    	
    	me.callService([{key:'sbjzqj',params :{}}],function(data){
    		for ( var i = 0; i < data.sbjzqj.length; i++) {
    			data.sbjzqj[i].y = parseInt(data.sbjzqj[i].y);
			}
    		me.data.bottom.left = data.sbjzqj;
    		
    		me.pie = new Exp.chart.HighChart({height : 330});
    		me.pie.render("zksf_yqsbxx_bottom_pie");
    		me.pie.addChart(
	    		me.renderPieChart({
	    			divId :me.pie.id,
	    			title: "设备按价值区间分类数量占比",
	    			data : me.data.bottom.left,
	    			showLable: true
	    		})
	    	);
    		
    	});
    	me.callService([{key:'sbflxx',params :{}}],function(data){
			for ( var i = 0; i < data.sbflxx.length; i++) {
				data.sbflxx[i].y = parseInt(data.sbflxx[i].y);
			}
    		me.data.bottom.right = data.sbflxx;
    		//me.render("zksf_yqsbxx_bottom_grid", me.bottom, me.data.bottom.right);
			me.pie1 = new Exp.chart.HighChart({height : 330});
			me.pie1.render("zksf_yqsbxx_bottom_grid");
			me.pie1.addChart(
				me.renderPieChart({
					divId :me.pie1.id,
					title: "按国标大类统计设备价值分布",
					data : me.data.bottom.right,
					showLable: true
				})
			);
    	});


		//================================================
		me.callService([{key:'sbdwlb',params :{}}],function(data){
			for ( var i = 0; i < data.sbdwlb.length; i++) {
				data.sbdwlb[i].y = parseInt(data.sbdwlb[i].y);
			}
			me.data.bottom.left1 = data.sbdwlb;

			me.pie2 = new Exp.chart.HighChart({height : 330});
			me.pie2.render("zksf_yqsbxx_bottom_pie1");
			me.pie2.addChart(
				me.renderPieChart({
					divId :me.pie2.id,
					title: "按单位类别统计设备分布情况",
					data : me.data.bottom.left1,
					showLable: true
				})
			);

		});
		me.callService([{key:'sbjfzc',params :{}}],function(data){
			for ( var i = 0; i < data.sbjfzc.length; i++) {
				data.sbjfzc[i].y = parseInt(data.sbjfzc[i].y);
			}
			me.data.bottom.right1 = data.sbjfzc;
			//me.render("zksf_yqsbxx_bottom_grid", me.bottom, me.data.bottom.right);
			me.pie3 = new Exp.chart.HighChart({height : 330});
			me.pie3.render("zksf_yqsbxx_bottom_grid1");
			me.pie3.addChart(
				me.renderPieChart({
					divId :me.pie3.id,
					title: "按经费来源统计设备经费组成",
					data : me.data.bottom.right1,
					showLable: true
				})
			);
		});
    },
    /* 绑定按钮事件 */
    bindBtnEvent : function(){
    	var me = this;
    	$("#zksf_yqsbxx_top_btn").click(function(event){
    		var tar = event.target;
    		if(tar.tagName == 'INPUT'){
    			$(tar).siblings().removeClass("school-device-t-btn-foc");
    			$(tar).addClass("school-device-t-btn-foc");
    			$("#zksf_yqsbxx_top_ow").hide();
    			switch( $(tar).attr("name")){
					case "sourceBtn":
						me.callService([{key:'sbly',params:{}},{key :'sblyByyx',params:{}}],function(data){
							me.data.devices.source = data.sbly;
							me.render("zksf_yqsbxx_top_ow", me.cols, me.data.devices.source);
							me.topchart.addChart(
								me.renderCommonChart({
									divId : me.topchart.id,
									title : "各院系持有设备,来源统计",
									yAxis : "件",
									type :"column",
									data : data.sblyByyx
								})
							);
						},me);
						break;
					case "purposeBtn":
						me.callService([{key:'sbyt',params:{}},{key :'sbytByYx',params:{}}],function(data){
							me.data.devices.purpose = data.sbyt;
							me.render("zksf_yqsbxx_top_ow", me.cols, me.data.devices.purpose);
							me.topchart.addChart(
								me.renderCommonChart({
									divId : me.topchart.id,
									title : "各院系持有设备,用途统计",
									yAxis : "件",
									type :"column",
									data : data.sbytByYx
								})
							);
				        },me);
						
						
						break;
					case "stateBtn":
						me.callService([{key:'sbzt',params:{}},{key :'sbztByYx',params:{}}],function(data){
							me.data.devices.state = data.sbzt;
							me.render("zksf_yqsbxx_top_ow", me.cols, me.data.devices.state);
							me.topchart.addChart(	
								me.renderCommonChart({
									divId : me.topchart.id,
									title : "各院系持有设备,状态统计",
									yAxis : "件",
									type :"column",
									data : data.sbztByYx
								})
							);
				        },me);
						break;
				}
    			$("#zksf_yqsbxx_top_ow").fadeIn();
    		}
    	});
    },
    render : function(nodeId,tpl,data){
        $("#"+nodeId).html("");
        var comp = new NS.Component({
            data : data,
            tpl : tpl
        });
        comp.render(nodeId);
    },
    overview : new NS.Template('<div class="div-border school-device-ow">学校设备共计<span class="school-device-ow-num">{total}</span>件,'+
    				'总价值 <span class="school-device-ow-num">{worth}</span>元</div>'),
    cols : new NS.Template('<div class="school-device-t-ow div-border"><tpl for="."><div class="school-device-t-cl sd-color-{[xindex]}">'+
				'<div class="school-device-t-cl-h">{name}</div>'+
				'<div class="div-border school-device-t-cl-m">总量：<br><span style="font-size:20px">{total}</span></div>'+
				'<div class="div-border school-device-t-cl-b">占比：<br><span style="font-size:20px">{persent}%</span></div>'+
				'</div></tpl></div>'),
    middle : new NS.Template('<table class="table1"><thead><tr><th rowspan="2">院系</th> <th rowspan="2">设备总量</th> <th rowspan="2">总价值金额</th> ' +
		'<th colspan="3">1-5万元设备</th>' +
		'<th colspan="3">5-10万元设备</th> ' +
		'<th colspan="3">10-40万元设备</th> ' +
		'<th colspan="3">40万元以上设备</th>   ' +
		'<th rowspan="2">学生数</th>  <th rowspan="2">生均价值</th> </tr>' +
		'<tr> ' +
		'<th>数量</th><th>价值(万元)</th><th>价值占比(%)</th>' +
		'<th>数量</th><th>价值(万元)</th><th>价值占比(%)</th>' +
		'<th>数量</th><th>价值(万元)</th><th>价值占比(%)</th>' +
		'<th>数量</th><th>价值(万元)</th><th>价值占比(%)</th>' +
		'</tr>' +
		'</thead>',
		'<tbody><tpl for="."><tr><td>{yx}</td><td>{sbzl}</td><td>{zjzje}元</td> ' +
		'<td>{dj1_num}</td><td>{dj1_sum}</td><td>{dj1_zb}</td> ' +
		'<td>{dj2_num}</td><td>{dj2_sum}</td><td>{dj2_zb}</td>' +
		'<td style="color: darkgreen;">{dj3_num}</td><td style="color: darkgreen;">{dj3_sum}</td><td style="color: darkgreen;">{dj3_zb}</td>' +
		'<td style="color: red;">{dj4_num}</td><td style="color: red;">{dj4_sum}</td><td style="color: red;">{dj4_zb}</td> ' +
		'<td>{xsnums}</td> <td>{sjjz}</td> </tr></tpl></tbody></table> '),
    
    bottom : new NS.Template('<table class="table1"><thead><tr><th>设备类别</th> <th>设备总量</th> <th>总价值金额</th> </tr></thead>',
			   '<tbody><tpl for="."><tr><td>{sblb}</td><td>{sbzl}</td><td>{zjzje}</td> </tr></tpl></tbody></table> '),
    data :{
    	overview : {total : 450,worth : 3020000 },
    	devices :{"source":[{"name":"自研","total":"120","persent":"12%"},{"name":"采购","total":"121","persent":"12%"},{"name":"校外转借","total":"122","persent":"12%"}],
	    		  "purpose":[{"name":"办公","total":"121","persent":"12%"},{"name":"教学","total":"212","persent":"12%"},{"name":"其他","total":"223","persent":"12%"}],
	    		  "state":[{"name":"在用","total":"121","persent":"12%"},{"name":"废弃","total":"112","persent":"12%"},{"name":"库存","total":"120","persent":"12%"}]},
    	middle : [],
    	bottom : {left : [],right : []}
    }
});
