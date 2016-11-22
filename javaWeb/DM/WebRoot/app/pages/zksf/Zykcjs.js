/**
 * 专业课程建设
 */
NS.define('Pages.zksf.Zykcjs', {
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'专业课程建设',
            pageHelpInfo:'从学校的专业类别、专业数量、课程数量、教材使用等方面分析学校的专业课程建设情况。'}
    }),
	modelConfig: {
	    serviceConfig: {
	    	  zylbs : 'zykcjsService?queryZylbs', // 专业类别数量
	    	  zys : 'zykcjsService?queryZys', // 专业数量
	    	  kcs : 'zykcjsService?queryKcs', // 课程数量
	    	  jcs : 'zykcjsService?queryJcs', // 教材数量
	    	  zyflsl : 'zykcjsService?queryZyflsl', // 专业分类数量情况
	    	  zyflxx : 'zykcjsService?queryZyflxx', // 专业分类详细信息
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
	        items:[this.pageTitle,comps]
	    });
	    container.on("afterrender",function(){
	    	this.fillCompData();
	    	this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	},
	createComps : function(){
		var me = this;
		var top = new NS.container.Container({
			padding : 15,
			cls : 'div-border',
			margin : "10px 0px" ,
			items : [me.t1,me.t2,me.t3,me.t4,me.t5,me.t6],
			layout : 'column',
			textalign : "center"
		});
		var middle = new NS.container.Container({
			padding : 10,
			cls : 'div-border',
			margin : "10px 0px" ,
			items : [me.middlechart,me.middleTable],
			layout : 'column'
		});
		
		var bottom = new NS.container.Container({
			padding : 10,
			cls : 'div-border',
			margin : "10px 0px",
			items : [me.bottomChart,me.bottomTable],
			layout : "column"
		});
		
		var result = new NS.container.Container({
			items : [top,middle,bottom,me.middleTooltip]
		});
		
		return result;
	},
	fillCompData : function(){
		var me = this;
		me.middleTooltip.hide();
		me.callService([{key:'zylbs',params : {}}],function(data){
			me.t1.update({			
				title:"专业类别数",
				value:data.zylbs.num,
				unit:"类"
			});
		});
		
		me.callService([{key:'zys',params : {}}],function(data){
			me.t2.update({			
				title:"专业数",
				value:data.zys.num,
				unit:"个"
			});
		});
		
		me.callService([{key:'kcs',params : {}}],function(data){
			me.t3.update({			
				title:"课程数",
				value:data.kcs.num,
				unit:"科"
			});
		});
		
		me.callService([{key:'jcs',params : {}}],function(data){
			me.t4.update({			
				title:"选用教材数",
				value:data.jcs.num,
				unit:"种"
			});
		});

		me.t5.update({title:"选用新教材数",value:"<font color='red'>未维护</font>",unit:"种"});
		
		me.t6.update({title:"选用优秀教材数",value:"<font color='red'>未维护</font>",unit:"种"});
	
		me.callService([{key : 'zyflsl',params:{}}],function(data){
			me.middlechart.addChart(
				me.renderPieChart({
					divId : me.middlechart.id,
					title : "各类专业占比",
					data : data.zyflsl
				})
			);
		});
		
		me.callService([{key : 'zyflxx',params : {}}],function(data){
			var dt = data.zyflxx;
			dt = me.exFunctions.changeZyflxx(dt);
			me.middleTable.update(dt);
		});
		
		
    	me.bottomChart.addChart(
    		me.renderCommonChart({
    			 divId : me.bottomChart.id,
	             type :"areaspline",
	    	     title : "近三年必修课使用多媒体课时量",
	    	     yAxis : "节次",
	    	     data : [] 
    		})
    	);
	},
	bindEvents : function(){
		this.middleTable.on('mouseover',function(event){
			 var tar = event.target;
			 this.middleTooltip.hide();
			 if(tar.tagName == 'A' && Ext.get(tar).getAttribute('name') == 'zysl'){
				 this.middleTooltip.show();
				 var data = this.middleTable.data;
				 for(var i= 0;i<data.length;i++){
					 if(Ext.get(tar).getAttribute('zylb') == data[i].name){
						 var be = event.browserEvent;
						 this.middleTooltip.update(data[i].zys);
						 this.middleTooltip.setPagePosition(be.pageX + 20,be.pageY,true);
					 }
				 }
			 }
		 },this ,{element:'el'});
	},
	t1 : new Exp.component.SimpleNumberWidget({
		color:"#33cc00",
		data : {
		},
		columnWidth : 0.166,
		style : {
			"text-align" : 'center'
		}
	}),
	t2 : new Exp.component.SimpleNumberWidget({
		color:"#0099ff",
		data : {
		},
		columnWidth : 0.166,
		style : {
			"text-align" : 'center'
		}
	}),
	t3 : new Exp.component.SimpleNumberWidget({
		color:"#33cc00",
		data : {
		},
		columnWidth : 0.166,
		style : {
			"text-align" : 'center'
		}
	}),
	t4 : new Exp.component.SimpleNumberWidget({
		color:"#0099ff",
		data : {
		},
		columnWidth : 0.166,
		style : {
			"text-align" : 'center'
		}
	}),
	t5 : new Exp.component.SimpleNumberWidget({
		color:"#33cc00",
		data : {
		},
		columnWidth : 0.166,
		style : {
			"text-align" : 'center'
		}
	}),
	t6 : new Exp.component.SimpleNumberWidget({
		color:"#0099ff",
		data : {
		},
		columnWidth : 0.166,
		style : {
			"text-align" : 'center'
		}
	}),
	middlechart : new Exp.chart.HighChart({
		columnWidth : 0.4
	}),
	middleTable : new Ext.Component({
		tpl : '<table class="table1"><thead><tr><th>专业类别</th> <th>专业数量</th> <th>学习人数</th> <th>占学生总量比</th> </tr></thead>'+
		   '<tbody><tpl for="."><tr><td>{name}</td><td><a name="zysl" zylb="{name}" href="javascript:void(0);">{num}</a></td><td>{xss}</td> <td>{xszb} </td> </tr></tpl></tbody></table> ',
	    columnWidth : 0.6,
	    data : {}
	}),
	middleTooltip : new Ext.Component({
		tpl : "<div style='width:200px;border:1px solid #AAA;background-color:#ccff66;padding:5px;font-size:12px;line-height:20px;'> <tpl for='.'>{name}<br/></tpl> </div>",
		data:{},
		style :{
			position : 'fixed',
			left : "900px",
			top : "450px"
		}
	}),
	bottomChart : new Exp.chart.HighChart({
		columnWidth : 0.4
	}),
	bottomTable : new Ext.Component({
		tpl :'<table class="table1"><thead><tr><th width="60">年份</th> <th>课时总量</th> <th>必修课课时量</th> <th>必修课课时量占比</th><th>必修课使用多媒体课时量</th> <th>必修课使用多媒体课时量占比</th> </tr></thead>'+
		   '<tbody><tpl if="values.length == 0"> <tr><td colspan="6" style="color:red">无数据</td></tr></tpl><tpl for="."><tr><td>{nf}</td><td>{kszl}</td><td>{bxkksl}</td> <td>{bxkkslzb} </td> <td>{bxksydmtksl} </td> <td>{bxksydmtkslzb} </td> </tr></tpl></tbody></table>' ,
		columnWidth :0.6,
		data : []
	}),
	
	exFunctions : {
		changeZyflxx : function(data){
			var result = []; //结果
			var rt = {},lbs = [] ;//类别名称
			var xssl = 0;//学生数量
			for(var i=0;i<data.length;i++){
				if(!rt[data[i].zylb]){
					lbs.push(data[i].zylb);
					rt[data[i].zylb] = true;
				}
				xssl += data[i].xssl;
			}
			xssl = (xssl == 0 ? 1 : xssl);
			for ( var j = 0; j < lbs.length; j++) {
				var zylb = {name : lbs[j],num : 0,zys:[],xss:0,xszb:0};
				for ( var k = 0; k < data.length; k++) {
					if(zylb.name == data[k].zylb){
						zylb.num ++ ;
						zylb.zys.push({name : data[k].zymc});
						zylb.xss += parseInt(data[k].xssl);
					}
				}
				zylb.xszb = (100*zylb.xss/xssl).toFixed(0) + "%";
				result.push(zylb);
			}
			return result;
		}
	}
});
