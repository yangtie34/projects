/**
 * 图书借阅TOP10
 */
NS.define('Pages.zksf.TsJYtop',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'图书借阅TOP10',
            pageHelpInfo:'图书借阅TOP10'
        }
    }),
    modelConfig: {
        serviceConfig: {
        	queryJxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构
        	bookTop : "tsJYService?queryBookTop", // 借出图书TOP10
	    	bookStuTop : "tsJYService?queryStudentTop", // 借书量高的学生的TOP10
        	stuInfo : 'wgwzstopService?stuInfo',// 学生info
        	stuZsinfo:'wgwzstopService?stuZsinfo',//住宿
        	stuTsjy:'wgwzstopService?stuTsjy',//图书
        	stuKscj : 'wgwzstopService?stuKscj'//成绩
        }
    },
    tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	params:[],
	init: function () {
		var me = this;
	    var comps = this.createComps();
	    var navigation = this.navigation = new Exp.component.Navigation({margin:'0 0 10 0'});
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[this.pageTitle,navigation,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    container.on("afterrender",function(){
	    	this.callService('queryJxzzjgTree',function(data){
                this.navigation.refreshTpl(data.queryJxzzjgTree);
                var i = 0;
                for(var key in data.queryJxzzjgTree){
                    if(i==0){
                        var nodeId = data.queryJxzzjgTree[key].id;
                        this.navigation.setValue(nodeId);
                        this.params.zzjgId = nodeId;
                    }
                    i++;
                }
            },this);
	    	this.fillCompData();
	    	this.bindEvents();
            navigation.on('click',function(){
                var data = this.getValue(),len = data.nodes.length;
                me.params.zzjgId = data.nodes[len-1].id;
                me.params.zzjgPid = data.nodes[len-1].pid;
				me.fillCompData();
				me.student.hide();
				me.top.show();
            });
	    },this);
	    this.setPageComponent(container);
	},
	dateSection :new NS.appExpand.DateSection(),
	title1 : new Exp.chart.PicAndInfo({
        title : "借出图书TOP10",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "借书量高的学生TOP10",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
    title3 : new Exp.chart.PicAndInfo({
        title : "住宿",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
    title4 : new Exp.chart.PicAndInfo({
        title : "一卡通消费",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
    title5 : new Exp.chart.PicAndInfo({
        title : "图书借阅",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
    title8 : new Exp.chart.PicAndInfo({
        title : "成绩",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
	table1 :  new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>序号</th>'+
				'<th>书名</th>'+
				'<th>被借阅次数</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{r}</td>'+
				'<td>{mc}</td>'+
				'<td>{sl}</td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		margin : '0px 10px 10px 0px'
	}),
	table2 :  new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>序号</th>'+
				'<th>学号</th>'+
				'<th>姓名</th>'+
				'<th>借阅次数</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{r}</td>'+
				'<td><a href="javascript:void(0);" name="xzlj" xh="{xh}">{xh}</a></td>'+
				'<td><a href="javascript:void(0);" name="xzlj" xh="{xh}">{mc}</a></td>'+
				'<td>{sl}</td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		margin : '0px 0px 10px 10px'
	}),
    stuInfo : new Ext.Component({
    	tpl : '<table style="width:500px;"><tr>'+
				'<td><img src="app/pages/sc/template/images/student/{xh}.jpg" width="90px" height="120px"/></td>'+
				'<td>'+
				"<table><tr>{xm},{xb}<td></td></tr>" +
				"<tr><td>{yx},{zy}</td></tr>" +
				"<tr><td>来自于{syd}</td></tr></table>"
				+'</td>'+
			'</tr></table>',
		data : [],
		margin : '10px 0px'
    }),
	stuZsinfo:new Ext.Component({
    	tpl : '<tpl if="values.length == 0">该生未住宿<tpl else>'+
            '<tpl for="."><div>{xm}住在 {ly},{fj}的{cw}床</div></tpl>'+
        '</tpl>',
		data : [],
		margin : '10px 15px'
	}),
	stuTsjy :  new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>序号</th>'+
				'<th>图书名称</th>'+
				'<th>借书时间</th>'+
				'<th>应还时间</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{[xindex]}</td>'+
				'<td>{tsmc}</td>'+
				'<td>{jsrq}</td>'+
				'<td>{yhrq}</td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		width:800,
		margin : '0px 0px 10px 10px'
	}),
	stuKscj:  new Ext.Component({
        tpl : '<table class="table1"><thead><tr>'+
            '<th>学年</th>'+
            '<th>学期</th>'+
            '<th>考试科目</th>'+
            '<th>课程学分</th>'+
            '<th>成绩绩点</th>'+
            '<th>百分制考试成绩</th>'+
            '<th>等级制考试成绩</th>'+
            '</tr></thead><tbody><tpl for="."><tr>'+
            '<td>{xn}学年</td>'+
            '<td>{xq}</td>'+
            '<td>{kcmc}</td>'+
            '<td>{kcxf}</td>'+
            '<td>{cjjd}</td>'+
            '<td>{bfzkscj}</td>'+
            '<td>{djzkscj}</td>'+
            '</tr></tpl>'+
            '</tbody></table>',
		data : [],
		width:800,
		margin : '0px 0px 10px 10px'
	}),
    createComps : function(){
		var me = this;
		today = new Date();
		me.dateSection.setValue({from : today - 15 * 3600* 24000,to : today});
		var xx = new Ext.Component({
			tpl : "☞选择开始时间和结束时间，如果时间正确，页面会自动刷新，统计该时间段内的学生借书情况信息。 ",
			data : {}
		});
		
		var dt = new NS.container.Container({
			layout : "column",
			items : [me.dateSection,xx],
			cls : "div-border",
			style : {
				"margin-bottom" : "10px"
			}
		});
		
		var comp1 = new NS.container.Container({
            items : [me.title1,me.table1],
    		columnWidth: 1/2
        });
		
		var comp2 = new NS.container.Container({
            items : [me.title2,me.table2],
    		columnWidth: 1/2
        });
		
		me.top = new NS.container.Container({
            layout:"column",
            style:{
                "padding" : "5px",
                "margin-bottom" : "10px"
            },
            items : [comp1,comp2]
        });
		me.student = new NS.container.Container({
			style:{
                "margin-bottom" : "10px"
            },
            items : [me.stuInfo,me.title3,me.stuZsinfo,
                     me.title5,me.stuTsjy,me.title8, me.stuKscj]
		});
		me.student.hide();
		var result = new NS.container.Container({
            style:{
                "margin-bottom" : "10px"
            },
            items : [dt,me.top,me.student]
        });
		
		return result;
	},
	fillCompData : function(){
		var me = this;
		$("#"+me.table1.id).html(me.loadingHtml);
		$("#"+me.table2.id).html(me.loadingHtml);
		me.params.fromDate=me.dateSection.getRawValue().fromDate;
		me.params.toDate=me.dateSection.getRawValue().toDate;
		me.callService([{key:'bookTop',params:this.params},{key:'bookStuTop',params:this.params}],function(data){
			me.table1.update(data.bookTop);
			me.table2.update(data.bookStuTop);
		});
	},
	bindEvents : function(){
		var me = this;
		me.dateSection.addListener('validatepass',function(){
			me.fillCompData();
		});
		$("#"+me.top.component.id).click(function(evt){
			var tar = evt.target;
			if($(tar).attr('name') == 'xzlj'){
				me.student.show();
				me.top.hide();
				var xh = $(tar).attr('xh');
				$("#" + me.stuInfo.id).html(me.loadingHtml);
				$("#" + me.stuTsjy.id).html(me.loadingHtml);
				$("#" + me.stuKscj.id).html(me.loadingHtml);
				$("#" + me.stuZsinfo.id).html(me.loadingHtml);
				me.callService([{key:'stuInfo',params:{xh : xh}}],function(data){
					me.stuInfo.update(data.stuInfo);
				});
				me.callService([{key:'stuTsjy',params:{xh : xh}},
				                {key:'stuKscj',params:{xh : xh}}],function(data){
					me.stuTsjy.update(data.stuTsjy);
					me.stuKscj.update(data.stuKscj);
				});

				me.callService([{key:'stuZsinfo',params:{num : 1,xh : xh}}],function(data){
					me.stuZsinfo.update(data.stuZsinfo);
				});
			}
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});