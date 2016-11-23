/**
 * 低消费学生预警
 */
NS.define('Pages.sc.ykt.Pksyc',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'低消费学生预警',
            pageHelpInfo:'低消费学生预警'
        }
    }),
    modelConfig: {
        serviceConfig: {
            queryxfje : 'studentXfmxService?queryXfpje', // 查询学校的消费情况的平均值
        	queryyxxfqk:'studentXfmxService?queryYxxsxfqk',// 各院系消费情况
            querypkxsmd:'studentXfmxService?quertYxyspkxs'// 查询院系的贫困学生的名单
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
	top1 : new Ext.Component({
		tpl : '<div style="border-radius: 100%;background-color: #ff6666;width: 120px;height: 120px;color:#fff;text-align: center;">' +
            '<span style="font-size: 40px;line-height: 80px;">{je}</span>  <span style="font-size: 20px;">元</span>' +
            '<br/><span style="font-size: 12px;">低消费日均值</span>' +
            '</div>',
		data : [],
		margin : '0px 0px 10px 0px',
        columnWidth: 1/3
	}),

    top2 : new Ext.Component({
        tpl : '<div style="border-radius: 100%;background-color: #45DF45;width: 120px;height: 120px;color:#fff;text-align: center;">' +
        '<span style="font-size: 40px;line-height: 80px;">{je}</span>  <span style="font-size: 20px;">元</span>' +
        '<br/><span style="font-size: 12px;">消费日均值</span>' +
        '</div>',
        data : [],
        margin : '0px 0px 10px 0px',
        columnWidth: 1/3
    }),

    top3 : new Ext.Component({
        tpl : '<div style="border-radius: 100%;background-color: #34aaff;width: 120px;height: 120px;color:#fff;text-align: center;">' +
        '<span style="font-size: 40px;line-height: 80px;">{je}</span>  <span style="font-size: 20px;">元</span>' +
        '<br/><span style="font-size: 12px;">高消费日均值</span>' +
        '</div>',
        data : [],
        margin : '0px 0px 10px 0px',
        columnWidth: 1/3
    }),
    title1 : new Exp.chart.PicAndInfo({
        title : "各院系近三个月消费情况",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    middleTable : new Ext.Component({
        tpl : '<div style="border: 1px solid #ccc;background-color: #fffbd9;color: red;padding: 10px;margin-bottom: 10px;">' +
            '统计数据来自近三个月的学生消费数据，为了保证数据准确性，将近三个月消费天数小于 30 天的学生数据排除。</div>' +
            '<table class="table1"><thead><tr>'+
				'<th>院系</th>'+
				'<th>总人数</th>'+
				'<th>消费达标人数</th>'+
				'<th>平均消费</th>'+
				'<th>低于平均消费人数</th>'+
				'<th>低于平均消费人均额</th>'+
				'<th>高于平均消费人数</th>'+
				'<th>高于平均消费人均额</th>'+
				'<th>疑似贫困学生</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{yx}</td>'+
				'<td>{zrs}</td>'+
				'<td>{xfdbrs}</td>'+
				'<td>{pjxf}</td>'+
				'<td>{dypjxf}</td>'+
				'<td>{dypjxfje}</td>'+
				'<td>{gypjxf}</td>'+
				'<td>{gypjxfje}</td>'+
				'<td><a href="javascript:void(0);" style="text-decoration: none;color: blue;" zrs="{zrs}" yxmc="{yx}"  yxid ="{yxid}" yxpje="{dypjxfje}"> 查看 </a></td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		margin : '0px 0px 10px 0px'
	}),
	title2 : new Exp.chart.PicAndInfo({
        title : "低消费学生名单",
        margin : "10px 0px 0px 0px",
        onlyTitle : true
    }),
    bottomTable : new Ext.Component({
		tpl : '<div style="border: 1px solid #ccc;background-color: #fffbd9;color: red;padding: 10px;margin-bottom: 10px;">' +
        '名单列表取自消费日均额小于院系平均额的学生，数量为院系总人数的百分之五。</div>'+
        '<table class="table1"><thead><tr>'+
        		'<th>序号</th>'+
				'<th>学号</th>'+
				'<th>姓名</th>'+
				'<th>院系</th>'+
				'<th>专业</th>'+
				'<th>3月内消费天数</th>'+
				'<th>日均消费</th>'+
				'<th>户口类型</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{[xindex]}</td>'+
				'<td>{xh}</td>'+
				'<td>{xm}</td>'+
				'<td>{yx}</td>'+
				'<td>{zy}</td>'+
				'<td>{xfts}</td>'+
				'<td>{rjxf}</td>'+
				'<td>{hk}</td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		margin : '0px 0px 10px 0px'
	}),
	bottom : {},
    createComps : function(){
		var me = this;
        var top = new NS.container.Container({
            layout:"column",
            style:{
                "padding" : "5px",
                "margin-bottom" : "10px"
            },
            items : [me.top1,me.top2,me.top3]
        });
        
        me.bottom = new Ext.container.Container({
            items:[me.title2,me.bottomTable]
        });
        
        var result = new NS.container.Container({
            items:[top,me.title1,me.middleTable,me.bottom]
        });
        me.bottom.hide();
        return result;
	},
	fillCompData : function(){
		var me = this;
		$("#"+me.middleTable.id).html(me.loadingHtml);
		me.callService({key:'queryxfje',params:{}},function(data){
			var tt = data.queryxfje;
			me.top1.update({je : tt.dxfrje});
			me.top2.update({je : tt.pjxfje});
			me.top3.update({je : tt.gxfrje});
		});
		me.callService({key : "queryyxxfqk",params : {}},function(data){
			me.middleTable.update(data.queryyxxfqk);
		});
		
	},
	bindEvents : function(){
		var me = this;
        $('#'+me.middleTable.id).on('click',function(evt){
        	var tar = evt.target;
	    	if(tar.tagName == 'A'){
	    		/* js 动作 */
	    		me.title1.hide();
	    		me.middleTable.hide();
	    		$("#"+me.bottom.id).stop(true,true);
	    		$("#"+me.bottom.id).fadeIn();
	    		/* 选定行 */
	    		var tarRow = $(tar).parent().parent();
	    		$(tarRow).siblings().find("td").css("background-color","#FFF");
	    		$(tarRow).find("td").css("background-color","#BEFFBC");
	    		/* 内容变更 */
	            var yxmc = $(tar).attr("yxmc");
	            var yxid = $(tar).attr("yxid");
	            var yxpje = $(tar).attr("yxpje");
	            var zrs = $(tar).attr("zrs");
	            $("#"+me.title2.id).find("span").html(yxmc+" - 低消费学生名单" +
                    "<a href='javascript:void(0);' style='margin-left: 30px;background-color: rgb(0, 148, 255);color: white;" +
                    "border-radius: 5px; border: 1px solid #AAA;padding: 2px 5px;text-decoration: none;'>返回</a>");
	            $("#"+me.bottomTable.id).html(me.loadingHtml);
	            me.callService({key:"querypkxsmd",params:{yxid:yxid,yxpje:yxpje,zrs:zrs}},function(data){
	            	me.bottomTable.update(data.querypkxsmd);
	            });
            }
        });

        $('#'+me.title2.id).on('click',function(evt){
        	var tar = evt.target;
	    	if(tar.tagName == 'A'){
	    		me.title1.show();
	    		me.middleTable.show();
	    		$("#"+me.bottom.id).hide();
	    	}
        });
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>"
});