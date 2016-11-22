/**
 * 学霸
 */
NS.define('Pages.sc.ykt.Xb',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学霸TOP5',
            pageHelpInfo:'大三以上学生，总成绩在3800以上，按综合平均绩点（GPA）从高到低查找学霸TOP5。等级制考试成绩不计入计算！<br>' +
                '<br><span style="margin-left: 10px;">公式一：综合平均绩点的计算公式为：(课程学分1*绩点+课程学分2*绩点+......+课程学分n*绩点)/(课程学分1+课程学分2+......+课程学分n) </span>'+
                '<br><span style="margin-left: 10px;">公式二：每门课的成绩与等级 及 绩点的对应关系是： </span>'+
                '<br><span style="margin-left: 60px;">90-100  =  A  = 4.0</span>'+
                '<br><span style="margin-left: 60px;">85-89   =  A- = 3.7</span>'+
                '<br><span style="margin-left: 60px;">82-84   =  B+ = 3.3</span>'+
                '<br><span style="margin-left: 60px;">78-81   =  B  = 3.0</span>'+
                '<br><span style="margin-left: 60px;">75-77   =  B- = 2.7</span>'+
                '<br><span style="margin-left: 60px;">71-74   =  C+ = 2.3</span>'+
                '<br><span style="margin-left: 60px;">66-70   =  C  = 2.0</span>'+
                '<br><span style="margin-left: 60px;">62-65   =  C- = 1.7</span>'+
                '<br><span style="margin-left: 60px;">60-61   =  D  = 1.3</span>'+
                '<br><span style="margin-left: 60px;">补考60  =  D-  = 1.0</span>'+
                '<br><span style="margin-left: 60px;">60以下  =  F   = 0 </span>'
        }
    }),
    modelConfig: {
        serviceConfig: {
        	queryJxzzjgTree: 'scService?getJxzzjgTree',// 获取导航栏数据，教学组织结构
        	wgtop10 :'wgwzstopService?getXbInfo',// 按平均成就排名获取学霸
        	wzstop10 :'wgwzstopService?getXbInfoByJd',// 按绩点排名获取学霸
        	stuInfo : 'wgwzstopService?stuInfo',// 学生info
        	stuWginfo:'wgwzstopService?stuWginfo',// 学生晚归info
        	stuWzsinfo:'wgwzstopService?stuWzsinfo',//学生未住宿info
        	stuXfinfo:'wgwzstopService?stuXfinfo',// 学生消费info
        	stuZsinfo:'wgwzstopService?stuZsinfo',//住宿
        	stuTsjy:'wgwzstopService?stuTsjy',//图书
        	stuKscj : 'wgwzstopService?stuKscj',//成绩
            zjpkInfo : 'wgwzstopService?zjpkInfo'//资助及贫困生信息
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
	    var navigation = this.navigation = new Exp.component.Navigation();
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
	    	this.params.num=1;
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
	monthChoose : new Ext.Component({
		tpl : "<div class='div-border'>" +
				"<input type='button' value='全校学霸' style='margin:0px 20px;padding:5px;background-color:orange;' 	name='mthch' num='1'>" +
				"</div>",
		num : 1
	}),
	
	title1 : new Exp.chart.PicAndInfo({
        title : "学霸top5",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "学霸top10",
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
    title9 : new Exp.chart.PicAndInfo({
        title : "奖惩助贷",
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
    title6 : new Exp.chart.PicAndInfo({
        title : "晚归",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
    title7 : new Exp.chart.PicAndInfo({
        title : "未住宿",
        margin : "10px 0px 10px 10px",
        onlyTitle : true
    }),
	table1 :  new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>序号</th>'+
				'<th>学号</th>'+
				'<th>姓名</th>'+
				'<th>性别</th>'+
				'<th>专业</th>'+
				'<th>年级</th>'+
				'<th>总成绩</th>'+
				'<th>平均成绩</th>'+
                '<th>综合平均绩点（GPA）</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{[xindex]}</td>'+
				'<td><a href="javascript:void(0);" name="xzlj" xh="{xh}">{xh}</a></td>'+
				'<td><a href="javascript:void(0);" name="xzlj" xh="{xh}">{xm}</a></td>'+
				'<td>{xb}</td>'+
				'<td>{zy}</td>'+
				'<td>{nj}</td>'+
				'<td>{zcj}</td>'+
				'<td>{pjcj}</td>'+
                '<td>{gpa}</td>'+
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
				'<th>未住宿天数</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{[xindex]}</td>'+
				'<td><a href="javascript:void(0);" name="xzlj" xh="{xh}">{xh}</a></td>'+
				'<td><a href="javascript:void(0);" name="xzlj" xh="{xh}">{xm}</a></td>'+
				'<td>{ts}</td>'+
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
				"</table>"
				+'</td>'+
			'</tr></table>',
		data : [],
		margin : '10px 0px'
    }),
    stuWginfo:new Ext.Component({
    	tpl : "该月共计晚归次数{[values.length]}次，日期分别为：<br>" +
    			"<tpl for='.'><span style='background-color:orange;text-align:center;" +
    			"float:left;width:30px;line-height:30px;margin:10px;'>" +
    			"{rq}</span></tpl><div style='clear:both;'></div>",
		data : [],
		margin : '10px 15px'
	}),
	stuWzsinfo:new Ext.Component({
		tpl : "该月共计未住宿次数{[values.length]}次，日期分别为：<br>" +
				"<tpl for='.'><span style='background-color:orange;text-align:center;" +
				"float:left;width:30px;line-height:30px;margin:10px;'>{rq}</span></tpl><div style='clear:both;'></div>",
		data : [],
		margin : '10px 15px'
	}),
    stuXfinfo:new Ext.Component({
        tpl : '该月共消费{z.xfcs}次，总金额{z.xfje}元<br>' +
            '<tpl if="pks.length == 0"><br>无数据...<tpl else><tpl for="pks"><br>近三月共计就餐 {cfcs} 次 ，不达标 {bdbs} 次 ,不达标率 {bdbl}%！</tpl></tpl>',
        data : [],
        margin : '10px 15px'
    }),// 资助、贫困生
    zjpkInfo : new Ext.Component({
        tpl :
            '<b>助学金</b> : <tpl if="zxj.length == 0"><br>没有获得助学金！<tpl else><tpl for="zxj"><br> {xn} 学年 获得 {zxjmc} 金额为 {zxje}元！</tpl></tpl>' +
            '<br><b>奖学金</b> : <tpl if="jxj.length == 0"><br>没有获得奖学金！<tpl else><tpl for="jxj"><br> {xn} 学年 获得 {jxjmc} 金额为 {jxje}元！</tpl></tpl>',
        data : [],
        margin : '10px 15px'
    }),// 学生消费info
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
		var sy = (new Date()).getMonth();
		me.monthChoose.update({sy : sy,ssy : sy-1});
		
		var comp1 = new NS.container.Container({
            items : [me.title1,me.table1],
    		columnWidth: 1
        });

		var comp2 = new NS.container.Container({
            items : [me.title2,me.table2],
    		columnWidth: 1
        });
		
		me.top = new NS.container.Container({
            layout:"column",
            style:{
                "padding" : "5px",
                "margin-bottom" : "10px"
            },
            items : [comp1]
        });
		me.student = new NS.container.Container({
			style:{
                "margin-bottom" : "10px"
            },
            items : [me.stuInfo,me.title6,me.stuWginfo,
                     me.title7,me.stuWzsinfo,me.title4,
                     me.stuXfinfo,me.title9,me.zjpkInfo,me.title3,me.stuZsinfo,
                     me.title5,me.stuTsjy,me.title8, me.stuKscj]
		});
		me.student.hide();
		var result = new NS.container.Container({
            style:{
                "margin-bottom" : "10px"
            },
   //         items : [me.monthChoose,me.top,me.student]
            items : [me.top,me.student]
        });
		
		return result;
	},
	fillCompData : function(){
		var me = this;
		$("#"+me.table1.id).html(me.loadingHtml);
		$("#"+me.table2.id).html(me.loadingHtml);
		me.callService([{key:'wgtop10',params:me.params},{key:'wzstop10',params:me.params}],function(data){
			me.table1.update(data.wzstop10);
			me.table2.update(data.wzstop10);
		});
	},
	bindEvents : function(){
		var me = this;
		$("#"+me.monthChoose.id).click(function(evt){
			var tar = evt.target;
			if($(tar).attr('name') == 'mthch'){
				var nm =$(tar).attr("num"); 
				me.params.num=nm;
				me.fillCompData();
				me.monthChoose.num = nm;
				$(tar).css('background-color',"orange");
				$(tar).siblings().css('background-color',"");
				me.student.hide();
				me.top.show();
			}
		});
		$("#"+me.top.component.id).click(function(evt){
			var tar = evt.target;
			if($(tar).attr('name') == 'xzlj'){
				me.student.show();
				me.top.hide();
				var num =me.monthChoose.num;
				var xh = $(tar).attr('xh');

                $("#" + me.stuInfo.id).html(me.loadingHtml);
                $("#" + me.stuWginfo.id).html(me.loadingHtml);
                $("#" + me.stuWzsinfo.id).html(me.loadingHtml);
                $("#" + me.stuXfinfo.id).html(me.loadingHtml);
                $("#" + me.stuTsjy.id).html(me.loadingHtml);
                $("#" + me.stuKscj.id).html(me.loadingHtml);
                $("#" + me.stuZsinfo.id).html(me.loadingHtml);

				me.callService([{key:'stuInfo',params:{num : num,xh : xh}}],function(data){
					me.stuInfo.update(data.stuInfo);
				});
                me.callService([
                    {key:'stuWginfo',params:{num : num,xh : xh}}],function(data){
                    me.stuWginfo.update(data.stuWginfo);
                });
                me.callService([
                    {key:'stuWzsinfo',params:{num : num,xh : xh}}],function(data){
                    me.stuWzsinfo.update(data.stuWzsinfo);
                });
                me.callService([
                    {key:'stuXfinfo',params:{num : num,xh : xh}},{key:'zjpkInfo',params:{xh : xh}}],function(data){
                    var dt = {z:data.stuXfinfo,pks:data.zjpkInfo.pks};
                    me.stuXfinfo.update(dt);
                });
                me.callService([
                    {key:'stuTsjy',params:{xh : xh}}],function(data){
                    me.stuTsjy.update(data.stuTsjy);
                });
                me.callService([
                    {key:'stuKscj',params:{xh : xh}}],function(data){
                    me.stuKscj.update(data.stuKscj);
                });
                me.callService([
                    {key:'stuZsinfo',params:{num : num,xh : xh}}],function(data){
                    me.stuZsinfo.update(data.stuZsinfo);
                });
                me.callService([
                    {key:'zjpkInfo',params:{xh : xh}}],function(data){
                    me.zjpkInfo.update(data.zjpkInfo);
                });
			}
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});