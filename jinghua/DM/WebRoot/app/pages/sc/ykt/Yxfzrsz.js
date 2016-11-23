/**
 * 院系负责人设置
 */
NS.define('Pages.sc.ykt.Yxfzrsz',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'院系负责人设置',
            pageHelpInfo:'院系负责人设置'
        }
    }),
    modelConfig: {
        serviceConfig: {
            queryfzrmd:'yxfzrService?queryYxfzrList',// 获取院系负责人列表
            updateyxfzr:'yxfzrService?updateYxfzr',// 修改院系负责人
            queryJzg:'yxfzrService?queryJzglist',
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
	topTable : new Ext.Component({
		tpl : '<table class="table1"><thead><tr>'+
				'<th>院系</th>'+
				'<th>负责人</th>'+
				'<th>联系邮箱</th>'+
				'<th>修改</th>'+
			'</tr></thead><tbody><tpl for="."><tr>'+
				'<td>{mc}</td>'+
				'<td>{xm}</td>'+
				'<td>{dzxx}</td>'+
				'<td><a href="javascript:void(0);" yxmc="{mc}" yxid ="{yx_id}" zgh = "{zgh}" email="{dzxx}"> 修改 </a></td>'+
			'</tr></tpl>'+
			'</tbody></table>',
		data : [],
		margin : '0px 0px 10px 0px',
		width : 800
	}),
	comboStore : {
		fields : ['zgh','xmzgh','email'],
		data : null
	},
	updateWindow :  new Ext.window.Window({
		 title:'',
         layout:'anchor',
         modal:true,
         width:300,
         height:150,
         closeAction:'hide',
         items:[
            {
                xtype     : 'combo',
                fieldLabel: '负责人',
                store : null,
                margin : 6,
                queryMode: 'local',
        	    displayField: 'xmzgh',
        	    valueField: 'zgh',
        	    allowBlank: false
            },
            {
            	xtype     : 'textfield',
                fieldLabel: '联系邮箱',
                allowBlank: false,
                margin : 6,
                vtype : 'email'
            },
            {
            	xtype     : 'button',
            	text    : '提交',
                margin : 6
            }
         ]
	}),
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
		me.callService({key:'queryJzg',params:{}},function(data){
			me.comboStore.data = data.queryJzg;
		});
		},
	bindEvents : function(){
		var me = this;
		var yxId = null;
		var items = me.updateWindow.items.items;
		items[0].on('select',function(evt){
			var store = items[0].getStore();
			var model = store.findRecord('zgh',items[0].getValue());
			items[1].setValue(model.get('email'));
		});
		items[2].on('click',function(){
			var params = {
					yxid : yxId,
					zgh : items[0].getValue(),
					email : items[1].getValue()
			};
			me.callService({key:'updateyxfzr',params:params},function(data){
				me.updateWindow.close();
				me.fillCompData();
			});
		});
		$("#"+me.topTable.id).click(function(evt){
			var tar = evt.target;
			if(tar.tagName =="A"){
				yxId = $(tar).attr("yxid");
				var zgh = $(tar).attr("zgh");
				var email = $(tar).attr("email");
				items[0].bindStore(me.comboStore);
				items[0].setValue(zgh);
				me.updateWindow.setTitle('修改['+$(tar).attr("yxmc")+']负责人');
				items[1].setValue(email);
				me.updateWindow.show();
			}
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});