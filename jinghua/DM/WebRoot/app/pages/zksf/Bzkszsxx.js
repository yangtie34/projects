/**
 * 本专科生招生信息
 */
NS.define('Pages.zksf.Bzkszsxx', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'本专科生招生信息',
            pageHelpInfo:'分析每年的本专科生招生情况。'
        }
    }),
	modelConfig: {
	    serviceConfig: {
	    	yxzsxx : 'bzkszsxxService?queryYxzsqk', //院系招生情况
	    	fsdzsqk : 'bzkszsxxService?queryFsdzsqk', //分数段招生情况
	    	lnzsrs : 'bzkszsxxService?queryYearszs', //历年招生人数
	    	lngkfs : 'bzkszsxxService?queryLngkfs', // 历年高考分数
            queryJxzzjgTree: 'scService?getJxzzjgTree'// 获取导航栏数据，教学组织结构
	   }
	},
	tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
    params:{},
	init: function () {
	    var comps = this.createComps();
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[this.pageTitle,this.navigation,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    this.setPageComponent(container);
	    container.on("afterrender",function(){
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
                this.params.xn = this.yearPicker.getValue();
                this.fillCompData();
            },this);
	    },this);
	},
	navigation: new Exp.component.Navigation(),
	title1 : new Exp.chart.PicAndInfo({
		title : "招生人数情况",
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	yearPicker : new Exp.chart.YearPicker({
	}),
	chart_t1 : new Exp.chart.HighChart({
		padding : 0,
		cls : 'div-border',
		height : 300,
		style : {
			"border-width" : "0px 1px 0px 1px" 
		}
	}),
	chart_t2 : new Exp.chart.HighChart({
		padding : 0,
		height : 300,
		cls : 'div-border'
	}),
	
	title2 : new Exp.chart.PicAndInfo({
		title : "历年录取人数变化",
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	chart_m : new Exp.chart.HighChart({
		padding : 0,
		height : 300,
		cls : 'div-border'
	}),
	title3 : new Exp.chart.PicAndInfo({
		title : '历年高考分数变化',
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	bottomTable : new Ext.Component({
		tpl :'<table class="table1"><thead><tr><th>年份</th><th>录取人数</th> <th>本科分数线</th><th>录取最高分</th><th>录取最低分</th></tr></thead>'+
					'<tbody><tpl if="values.length == 0"> <tr><td colspan="20"><span style="color : red;">数据未维护</span></td></tr></tpl>'+
					'<tpl for="."><tr><td>{nf}</td><td>{lqrs}</td><td>{bkfsx}</td><td>{lqzgf}</td><td>{lqzdf}</td></tr></tpl></tbody></table>' ,
		data : []
	}),
	createComps : function(){
		var me = this;
		var top = new NS.container.Container({
			items : [me.title1,me.yearPicker,me.chart_t1,me.chart_t2]
		});
		
		var middle = new NS.container.Container({
			items : [me.title2,me.chart_m]
		});
		var bottom = new NS.container.Container({
			items : [me.title3,me.bottomTable]
		});
		var result = new NS.container.Container({
			items : [top,middle]
		});
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.exFunctions.fillTopComp.call(me);
		
		$("#"+me.chart_m.id).html(me.loadingHtml);
		me.callService([{key : "lnzsrs",params:this.params}],function(data){
			me.chart_m.addChart(me.renderCommonChart({
				divId : me.chart_m.id,
				title : "历年招生人数情况",
				yAxis : "人次",
				type :"column",
				data : data.lnzsrs
			}));
		});
		$("#"+me.bottomTable.id).html(me.loadingHtml);
		me.callService([{key : "lngkfs",params:this.params}],function(data){
			me.bottomTable.update(data.lngkfs);
		});
	},
	bindEvents : function(){
		var me = this;
		me.yearPicker.on("change",function(year){
			me.exFunctions.fillTopComp.call(me);
		});
	},
	exFunctions : {
		fillTopComp : function(){
			var me = this;
			me.params.xn = me.yearPicker.getValue();
			$("#"+me.chart_t1.id).html(me.loadingHtml);
			$("#"+me.chart_t2.id).html(me.loadingHtml);
			me.callService([{key : "yxzsxx",params : me.params}],function(data){
				me.chart_t1.addChart(me.renderCommonChart({
					divId : me.chart_t1.id,
					title : me.params.xn + "学年各学院招生人数情况",
					yAxis : "人次",
					type :"column",
					data : data.yxzsxx
				}));
			});
			me.callService([{key : "fsdzsqk",params : me.params}],function(data){
				me.chart_t2.addChart(me.renderCommonChart({
					divId : me.chart_t2.id,
					title : me.params.xn + "学年招生各分数段人数情况",
					yAxis : "人次",
					type :"column",
					data : data.fsdzsqk
				}));
			});
		} 
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>数据未维护</span>"
});
