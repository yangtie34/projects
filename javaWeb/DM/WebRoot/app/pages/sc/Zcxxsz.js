/**
 * 资产信息设置
 */
NS.define('Pages.sc.Zcxxsz',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'资产信息设置',
            pageHelpInfo:'资产信息设置'
        }
    }),
    modelConfig: {
        serviceConfig: {
            queryfzrmd:'yqsbxxService?queryZcxxList',// 获取资产信息列表
            updateyxfzr:'yqsbxxService?updateZcxx',// 修改资产信息
        }
    },
    tplRequires : [],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css'],
	mixins:[],
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
	topTable : new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>分类</th>'+
				'<th>名称</th>'+
				'<th>数量</th>'+
				'<th>修改</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{type}</td>'+
				'<td>{name}</td>'+
				'<td>{value}</td>'+
				'<td><a href="javascript:void(0);" type="{type}" name="{name}" code ="{code}" val="{value}"> 修改 </a></td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		margin : '0px 0px 10px 0px',
		width : 800
	}),
	updateWindow : null,
    createComps : function(){
		var me = this;
		return me.topTable;
	},
	fillCompData : function(){
		var me = this;
		$("#"+me.topTable.id).html(me.loadingHtml);
		me.callService({key:'queryfzrmd',params:{}},function(data){
			me.topTable.update(data.queryfzrmd);
		});
		},
	bindEvents : function(){
		var me = this;
		$("#"+me.topTable.id).click(function(evt){
			var tar = evt.target;
			if(tar.tagName =="A"){
				me.updateWindow = new Ext.window.Window({
					 title:'修改['+$(tar).attr("name")+']数量',
			         layout:'anchor',
			         modal:true,
			         width:300,
			         height:100,
			         items:[
		                {
		                	xtype     : 'numberfield',
		                    fieldLabel: '数量',
		                    allowBlank: true,
		                    margin : 6
		                },
		                {
		                	xtype     : 'button',
		                	text    : '提交',
		                    margin : 6
		                }
			         ]
				});
				var val = $(tar).attr("val");
				var code = $(tar).attr("code");
				var type = $(tar).attr("type");
				var items = me.updateWindow.items.items;
				items[0].setValue(parseFloat(val));
				items[1].on('click',function(){
					var params = {
						type : type,
						code : code,
						value : items[0].getValue()
					};
					me.callService({key:'updateyxfzr',params:params},function(data){
						console.log(data.updateyxfzr.success);
						if (!data.updateyxfzr.success) {
							alert("修改失败！");
						}
						me.updateWindow.close();
						me.fillCompData();
					});
					
				});
				me.updateWindow.show();
			}
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});