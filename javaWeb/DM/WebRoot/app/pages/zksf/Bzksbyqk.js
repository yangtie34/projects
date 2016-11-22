/**
 * 本专科生毕业情况
 */
NS.define('Pages.zksf.Bzksbyqk', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'本专科生毕业情况',
            pageHelpInfo:'分析每年的本专科生毕业情况，包括毕业人数统计和毕业去向统计等。'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	ybyqk : 'bzksbyqkService?queryYbyqk', // 应毕业情况
	    	sjbyqk : "bzksbyqkService?querySjbyqk", // 实际毕业情况
	    	xzkyqk : "bzksbyqkService?queryXzkyqk", //选择考研学生情况
            queryJxzzjgTree: 'scService?getJxzzjgTree'// 获取导航栏数据，教学组织结构
	    }
	},
	tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
    params:{},
    navigation: new Exp.component.Navigation({margin:'0 0 10 0'}),
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
	    this.setPageComponent(container);
	},
	yearPicker : new Exp.chart.YearPicker({
		margin : "0px 0px 10px 0px"
	}),
	t1 : new Exp.component.MetroTextWidget({
		color : '#6599ff',
		columnWidth : 1/3
	}),
	t2 : new Exp.component.MetroTextWidget({
		color : '#ff9934',
		columnWidth : 1/3
	}),
	t3 : new Exp.component.MetroTextWidget({
		color : '#00cd34',
		columnWidth : 1/3
	}),
	title1 : new Exp.chart.PicAndInfo({
		title : "就业学生工资统计",
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	middleChart : new Exp.chart.HighChart({
		cls : 'div-border',
		padding : 5 
	}),
	title2 : new Exp.chart.PicAndInfo({
		title : "考研学生录取学校统计",
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	bottomTable : new Ext.Component({
		tpl :'<table class="table1"><thead><tr><th >院系</th> <th>考研人数</th> <th>录取人数</th> <th>国家重点院校录取人数</th></tr></thead>'+
		   '<tbody><tpl if="values.length == 0"> <tr><td colspan="6"><span style="color : red;">数据未维护</span></td></tr></tpl><tpl for="."><tr><td>{yx}</td><td>{kyrs}</td><td>{lqrs}</td> <td>{zdyxlqrs} </td></tr></tpl></tbody></table>' ,
		data : []
	}),
	createComps : function(){
		var me = this;
		var top = new NS.container.Container({
			items : [me.t1,me.t2,me.t3],
			layout : 'column'
		});
		var result = new NS.container.Container({
			items : [me.yearPicker,top,me.title1,me.middleChart,me.title2,me.bottomTable]
		});
		
		return result;
	},
	fillCompData : function(){
		var me = this;
        me.params.xn= me.yearPicker.getValue();
		me.callService([{key : 'ybyqk',params:me.params}],function(data){
			me.t1.update(data.ybyqk);
		});
		
		me.callService([{key : 'sjbyqk',params : me.params}],function(data){
			me.t2.update(data.sjbyqk);
		});
		
		me.callService([{key : 'xzkyqk',params : me.params}],function(data){
			me.t3.update(data.xzkyqk);
		});
		
		$("#" + me.middleChart.id).html(me.noDataHtml);
		/*me.middleChart.addChart(me.renderCommonChart({
			divId : me.middleChart.id,
			title : year + "学年毕业学生工资情况",
			yAxis : "元",
			type :"column",
			data : [{name : '持工资学生数',field:'3000',value:1000},{name : '持工资学生数',field:'4000',value:1200},{name : '持工资学生数',field:'5000',value:300}]
		}));*/
	},
	bindEvents : function(){
		var me = this;
		me.yearPicker.on("change",function(){
			me.fillCompData();
		});
	},
	exFunctions : {
		
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>数据未维护</span>"
});
