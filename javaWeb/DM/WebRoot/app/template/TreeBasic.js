/**
 * 树形基础页面
 */
NS.define('Template.TreeBasic',{
	extend:'NS.mvc.Controller',
	modelConfig:{
		serviceConfig:{
			save:{service:'base_save'},
			del:{service:'base_deleteByIds'},
			queryHeader:{service:'base_header'},
			queryData:{service:'base_queryTabelContent'},
			update:{service:'base_update'}
		}
	},
	entityName:'TbJwJsys',
	tbar:null,
	grid:null,
	init:function(){
//		this.addEvents('gridcomponentsload','viewready');
//		this.on('gridcomponentsload',this.initComponent,this);
	    this.initData();
	},
	initData:function(){
		  var me = this;
//	       this.callService(['base_queryTableContent'],function(data){
//	       });
	       me.initComponent();
	},
	initComponent:function(data){
		 var grid = this.grid = this.initGrid(data);
	     var tbar =this.tbar = this.initTbar();
	     var tree = this.tree = this.initTree(data);
	     var anoterTbar = this.initAnoterTbar();
	     
	     var ycPanel = this.getYcPanel();
	     this.anoterPage =  new NS.container.Panel({
	    	 tbar:anoterTbar,
	    	 layout:'fit',
	    	 border:false,
	    	 width:'100%',
	    	 height:'100%',
	    	 items:ycPanel
	     });
	     
	     var gridPanel = this.gridPanel = new NS.container.Panel({
	    	 layout:'fit',
	    	 border:0,
	    	 tbar:tbar,
	    	 items:grid
	     });
	     var centerPanel = this.centerPanel = new NS.container.Panel({
	    	 width:'100%',
	    	 height:'100%',
	    	 border:false,
	    	 layout:'fit',
	    	 items:gridPanel
	     });
		 var component = new NS.container.Panel({
				layout : 'border',
				border:false,
				items : [ {
					region:'west',
					width:'20%',
					split : true,
		            collapsible : true,
					border:'1 1 0 1',
					margins: '5 0 0 5',
					autoScroll:true,
					items:tree
				}, {
					region:'center',
					width:'80%',
					border:false,
					margins: '5 5 0 0',
					layout:'fit',
					items:centerPanel
				} ]
			});
	       this.setPageComponent(component);//如果使用多个MVC嵌套,那这里这个方法只需要一个标志位来判断即可
//	       this.registerDataLoader('queryData',grid);
//	       this.fireEvent('viewready');
	},
	getYcPanel:function(){
		var grid = this.initGrid();
		// 暂由异议  
		//var grid = new Template.component.gridTemplate.BaseGrid();
		var panel = new NS.container.Panel({
			layout:'fit',
			border:false,
			items:grid
		});
		return panel;
	},
	initAnoterTbar:function(){
		var tbar = new NS.toolbar.Toolbar({
			items:[{xtype:'button',text:'返回',name:'reback'}]
		});
		tbar.findComponentByName('reback').on('click',this.reBack,this);
		return tbar;
	},
	/**
	 * 页面转换页面
	 * @param {NS.container.Container} container容器
	 * @param {NS.container.Container} from 切换掉的组件或容器，它必须是已包含在container中的
	 * @param {NS.container.Container} to 待切换的组件
	 */
	switchPage:function(container,from,to){
		container.remove(from,false);
		container.add(to);
	},
	reBack:function(){
		this.switchPage(this.centerPanel,this.anoterPage,this.gridPanel);
	},
	initTbar:function(){
		var tbar = new NS.toolbar.MultiLineTbar({
	           height : 80,
	           contentWidth : 600,
	           border:'1 1 0 1',
	           layout : 'column',
	           items : [
	               {xtype : 'button',text : '切页',name : 'changePage'},
	               {xtype : 'button',text : '新增',name : 'add'},
	               {xtype : 'button',text : '修改',name : 'update'},
	               {xtype : 'button',text : '删除',name : 'delete'},
	               {xtype : 'textfield',width : 130,labelWidth : 50,fieldLabel : '姓名',name : 'name'},
	               {xtype : 'textfield',width : 130,labelWidth : 50,fieldLabel : '性别',name : 'sex'},
	               {xtype : 'textfield',width : 130,labelWidth : 50,fieldLabel : '年龄',name : 'age'},
	               {xtype : 'textfield',width : 130,labelWidth : 50,fieldLabel : '班级',name : 'bj'},
	               {xtype : 'textfield',width : 130,labelWidth : 50,fieldLabel : '专业',name : 'zy'},
	               {xtype : 'button',text : '获取值',name : 'query',margin : '4 0 0 40'}]
	       });
		     
		   tbar.findComponentByName('add').on('click',this.addData,this);
		   tbar.findComponentByName('update').on('click',this.updateData,this);
	       tbar.findComponentByName('delete').on('click',this.deleteData,this);
	       tbar.findComponentByName('changePage').on('click',this.changePage,this);
	       
	       return tbar;	
	},
	//可以将form本身通用（除按钮分离）
	addData:function(){
		var form = this.form = NS.form.EntityForm.create({
            data : this.tranData,
            autoScroll : true,
            columns : 2,
            margin : '5px',
            modal:true,// 魔态，值为true是弹出窗口的。
            buttons : [{text : '提交',name : 'add'},{text : '取消',name : 'cancel'}],
            items : [
                {
                    xtype : 'fieldset',
                    columns : 2,
                    title : '分组1',
                    height : 250,
                    items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                },
                {xtype : 'fieldset',
                    title : '分组2',
                    columns : 1,
                    items : [
                        'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                    ]},
                {xtype : 'fieldset',
                    title : '分组2',
                    columns : 3,
                    colspan : 2,
                    items : [
                        'yzy','ybjId','ynj'
                    ]}
            ]
        });
        this.window = new NS.window.Window({
            width : 900,
            height : 600,
            layout : 'fit',
            items : form,
            autoShow: true
        });
        form.bindItemsEvent({
            'add' : {event : 'click',fn : this.submitAdd,scope : this},
            'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
        });
	},
	submitAdd:function(){
		NS.Msg.alert('==-==','submit');
	},
	cancelForm:function(){
		this.window.close();
	},
	updateData:function(){
		var raws = this.grid.getSelectRows().length;
		if(raws==1){
			var form = this.form = NS.form.EntityForm.create({
	            data : this.tranData,
	            autoScroll : true,
	            columns : 2,
	            margin : '5px',
	            modal:true,// 魔态，值为true是弹出窗口的。
	            buttons : [{text : '提交',name : 'update'},{text:'重置',name:'reset'},{text : '取消',name : 'cancel'}],
	            items : [
	                {
	                    xtype : 'fieldset',
	                    columns : 2,
	                    title : '分组1',
	                    height : 250,
	                    items : [{name : 'id',hidden : true},'xh','xm','yyxId']
	                },
	                {xtype : 'fieldset',
	                    title : '分组2',
	                    columns : 1,
	                    items : [
	                        'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
	                    ]},
	                {xtype : 'fieldset',
	                    title : '分组2',
	                    columns : 3,
	                    colspan : 2,
	                    items : [
	                        'yzy','ybjId','ynj'
	                    ]}
	            ]
	        });
	        var values = this.grid.getSelectRows()[0];
	        form.setValues(values);
	        this.window = new NS.window.Window({
	        	title:'修改窗口',
	            width : 900,
	            height : 600,
	            layout : 'fit',
	            items : form.getLibComponent(),
	            autoShow: true
	        });
	        form.bindItemsEvent({
	            'update' : {event : 'click',fn : this.submitUpdate,scope : this},
	            'cancel' : {event : 'click',fn : this.cancelForm,scope : this},
	            'reset':{event:'click',fn:this.resetForm,scope:this}
	        });
		}else{
			NS.Msg.warning({
				msg:'选中一行修改!'
			});
		}
	},
	resetForm:function(){
		this.form.reset();
	},
	submitUpdate:function(){
		NS.Msg.error({
			msg:'修改失败！'
		});
	},
	deleteData : function(){
	       var me = this,data = this.grid.getSelectRows(),ids = [],len = data.length;
	       if(len < 1){
	    	   NS.Msg.warning({
	    		   msg:'您尚未选择任何数据!'
	    	   });
	    	   return;
	       }
	       NS.Msg.changeTip('提示','您确定删除这  '+len+' 行数据吗?',function(btn){
	    		   ids = NS.Array.pluck(data,'id');
	    	       me.getData([{key : 'del',params : {entityName : me.entityName,ids : ids}}],function(backData){
	    	    	   if(backData.del.success){
	    	        	   NS.Msg.alert('提示','成功');
	    	        	   //me.grid.getStore().load();
	    	           }else{
	    	        	   NS.Msg.alert(backData.title,backData.info);
	    	           } 
	    	       });
	       });
	   },
	changePage:function(){
		this.switchPage(this.centerPanel,this.gridPanel,this.anoterPage);
	},
	initGrid:function(data){
		var columnData = this.tranData = NS.util.DataConverter.entitysToStandards(headerData);
	       var grid = new NS.grid.Grid({
	           plugins : [//new NS.grid.plugin.CellEditor(),
	               new NS.grid.plugin.HeaderQuery()],
	           columnData : columnData,
	           modelConfig : {
	               data : showData.data
	           },
//	           autoScroll : true,
	           multiSelect : true,
	           lineNumber : false,
	           columnConfig : [
	               {   text: '查看列',
	                   name :'see',
	                   xtype : 'buttoncolumn',
	                   buttons : [
	                       {
	                           buttonText : '查看',
	                           style : {
	                               color : 'red',
	                               font : '18px'
	                           }
	                       },
	                       {
	                           buttonText : '审核'
	                       }
	                   ]}
	           ]
	       });
	       return grid;
	},
	initTree:function(data){
		var obj = {
					data:window.treeData,
					border : false,
					rootVisible : true,
					border : false,
					margin : '0 0 0 0',
					autoScroll:true,
					checkable : true,
					multiple:	true,
					multyFields:[{dataIndex:"text"}]
		};
		var tree = new NS.container.Tree(obj);
		return tree;
	}
});