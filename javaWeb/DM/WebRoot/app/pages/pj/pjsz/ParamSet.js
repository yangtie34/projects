 NS.define('Pages.pj.pjsz.ParamSet',{
 	extend : 'Pages.pj.component.BaseModel',//'Template.Page',
 	entityName : 'TbPjParam',
 	modelConfig : {
 		//service 方法在此配置
 		serviceConfig : {  
        	saveOrUpdate:'base_saveOrUpdate', //保存或修改
        	save:'base_save',//新增保存Grid数据路径
        	//更新保存Grid数据路径
        	update:'base_update',
        	//删除，含批量Grid数据路径
        	deletes:'base_deleteByIds',
        	//查询Grid表头数据
            queryTableHeaderAllData: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: {service:"base_queryTableContent",params:{entityName:'TbPjParam',start:0,limit:25}},
            queryXspjData:'queryParam4Xspj', //查询学生评教数据
    		queryPjbjData:'queryBj4Pjfw', //评教范围-班级
    		queryPjyxData:'queryYx4Pjfw', // 评教范围-系部
    		queryPjzyData:'queryZyPjfw' //评教范围-专业
 		}
 	},
   //初始化
 	init : function(){
 		this.initData(); //初始化数据
 	},
 	baseFormItems:function(){
 		var me=this;
 		 var itemArr=[{name:"xnId",editable:false},{name:"xqId",editable:false},'pjMonth',"pjBegin","pjEnd",{name:"pjdx",editable:false},{name:"bpdx",editable:false},"sfcxcj","sfkcpj",
 		 	{name:"topicId",readOnly:true},
 		 	{name:"highScore",hidden:true},{name :'id',hidden :true},{name:"lowScore",hidden:true},{name :'cjr',hidden : true},
 		 	{name :'cjsj',hidden :true},'pjfwId',this.yxListCom,'pjnjIds',this.zyListCom,this.bjListCom];
    	return itemArr;
    },
 	 /**
     * rewrite-addFormItems
     * @return
     */
    addFormItems:function(){
    	return this.baseFormItems();
    },
     /**
     * rewrite-updateFormItems
     * @return
     */
    updateFormItems:function(){
    	return this.baseFormItems();
    },
 	//初始化数据
 	initData : function(){
 		var xnId=this.xnId=MainPage.getXnId();//当前学年ID
 		var xqId=this.xqId=MainPage.getXqId(); //当前学期ID
 		var params = {entityName: "TbPjParam",start:0,limit:25};
 		this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData', params: params}
        ],
            function (data) {
        		this.initComponent(data);
            });
 	},
  //自定义下拉组件-院系
 	initYXListComp:function(data){
 		var yxCbx = this.yxCbx=new NS.form.field.ComboBox({
 			name:'pjyxIds',
			width:270,
			labelWidth:70,
			fieldLabel:'评教院系',
			queryMode:'local',
			multiSelect:true
		});
		data = [{id:'root',mc:'选择全部'}].concat(data);
		yxCbx.loadData(data);
		yxCbx.on('select',function(com_,records){
		   var defalutValue =[];	
		   if(records[0]['id']=='root'){
		       for(var i=0,len=data.length;i<len;i++){
		          defalutValue.push(data[i]);
		       }
		       com_.setValue(defalutValue);
		   }
		},this);
		return this.yxCbx;
 	},
 	 //自定义下拉组件-专业列表
 	initZyListComp:function(data){
 		var zyCbx = this.zyCbx=new NS.form.field.ComboBox({
 			name:'pjzyIds',
			width:270,
			labelWidth:70,
			fieldLabel:'评教专业',
			queryMode:'local',
			multiSelect:true
		});
		data = [{id:'root',mc:'选择全部'}].concat(data);
		zyCbx.loadData(data);
		zyCbx.on('select',function(com_,records){
		   var defalutValue =[];	
		   if(records[0]['id']=='root'){
		       for(var i=0,len=data.length;i<len;i++){
		          defalutValue.push(data[i]);
		       }
		       com_.setValue(defalutValue);
		   }
		},this);
		return this.zyCbx;
 	},
    //自定义下拉组件-班级
 	initBjListComp:function(data){
 		var bjCbx = this.bjCbx=new NS.form.field.ComboBox({
 			name:'pjbjIds',
			width:270,
			labelWidth:70,
			fieldLabel:'评教班级',
			queryMode:'local',
			multiSelect:true
		});
		data = [{id:'root',mc:'选择全部'}].concat(data);
		bjCbx.loadData(data);
		bjCbx.on('select',function(com_,records){
		   var defalutValue =[];	
		   if(records[0]['id']=='root'){
		       for(var i=0,len=data.length;i<len;i++){
		          defalutValue.push(data[i]);
		       }
		       com_.setValue(defalutValue);
		   }
		},this);
		return this.bjCbx;
 	},
 	/**
 	 * --------------------------------------------------------------------------------
 	 * 初始化页面组件
 	 * @param {} tranData
 	 * @param {} tabledata
 	 *  --------------------------------------------------------------------------------
 	 */
 	initComponent : function(data){
 		this.tranData=data['queryTableHeaderAllData'];
 		this.tableData=data['queryGridData'];
 		this.initGrid(this.tranData,this.tableData);
 		
 		this.xspjData=data['queryXspjData'];
    	var pjbjData=this.pjbjData=data['queryPjbjData'];
    	var pjyxData=this.pjyxData=data['queryPjyxData'];
    	var pjzyData=this.pjzyData=data['queryPjzyData'];
    	if(!this.bjListCom){
    		this.bjListCom=this.initBjListComp(pjbjData);
    	}
    	if(!this.yxListCom){
    		this.yxListCom=this.initYXListComp(pjyxData);
    	}
    	if(!this.zyListCom){
    		this.zyListCom=this.initZyListComp(pjzyData);
    	}
        this.initTbar();
        this.initAndDoLayoutPage();
 	},
 	//初始化表格
     initGrid: function (tranData,tableData) {
    	//供form默认展现字段使用
    	this.defaultFormItems = NS.Array.pluck(tranData,'sx');//通过'sx'查找table里的各个字段名称
        //转换标准的表头数据
    	var columnData = this.tranData = NS.E2S(tranData);
        //如果要改变Grid的展现形式,请见updateGridBasicConfig
        var basic = this.updateGridBasicConfig();
        //Grid展现必须的配置项
        var mustCfg = {
        		columnData: columnData,
        		serviceKey:{
        			key:'queryGridData',
        			params:this.commonGridParams()
        		},
                modelConfig: {
                    data : tableData
                },
                proxy:this.model
        };
        NS.apply(mustCfg,basic);
        this.grid = new NS.grid.Grid(mustCfg);
         //给表格列增加绑定事件监听
        var column=this.grid.getColumn('operator');
        column.addListener('linkclick',function(text,rowindex,colindex,rowdata){
        	this.deleteTheRow(rowdata);//删除记录
        },this);
    },
    //删除一行
 	 deleteTheRow:function(rowdata){
    	var me = this;
		var param={entityName:'TbPjXspj',data:rowdata};
		this.callService({key:'queryXspjData',params:param},function(data){
			if(data['queryXspjData'].success==true){
				NS.Msg.info('提示','评教活动已进行,不能被删除');return;
			}else{
				var ids=rowdata.id;
    			this.deleteUtil('deletes',ids,function(backData){
    			if(backData.success){
    				this.grid.load();
    				NS.Msg.info('删除成功！');
    			}else{
    				NS.Msg.error('删除失败！');
    			} 
			});
		}
		},this);
    },
 	 /**
     * 执行业务删除的方法
     */
    deleteIds:function(){
    	var me = this,
    	data = this.grid.getSelectRows(),
    	ids = NS.Array.pluck(data,'id');
    	if(ids.length > 0){
    		var param={entityName:'TbPjXspj',data:data};
    		this.callService({key:'queryXspjData',params:param},function(data){
    			if(data['queryXspjData'].success==true){
    				NS.Msg.info('提示','评教活动已进行,不能被删除');return;
    			}else{
	    			this.deleteUtil('deletes',ids,function(backData){
	    			if(backData.success){
	    				this.grid.load();
	    				NS.Msg.info('删除成功！');
	    			}else{
	    				NS.Msg.error('删除失败！');
	    			} 
    			});
    		}
    		},this);
	    }else{
	    	 NS.Msg.warning({
	    		   msg:'您尚未选择任何数据!'
	    	   });
	    }
    },
     deleteUtil : function(key,ids,fn){
    	   var me = this;
    	   var len = ids.length;
    	   
	       NS.Msg.changeTip('提示','您确定删除这行数据吗?',function(){
	    	       me.callService({key:key, params: {entityName:me.entityName,ids:ids.toString()}}, function(backData){
	    	            fn.call(me,backData[key]);
	    	        });
	       });
    },
    commonGridParams:function(){
    	return {
    		entityName:this.entityName,
    		start:0,
    		limit:25
    	};
    },
    /**
 	 * reWrite-表格样式
 	 * @return {}
 	 */
 	  updateGridBasicConfig:function(){
    	var basic = {
                plugins: [new NS.grid.plugin.HeaderQuery()],
                autoScroll: true,
                border:false,
//                multiSelect: true,
                checked:true,
                lineNumber: true,
                columnConfig : [
               	 {name:'id',hidden:true},
               	  { text: '操作',
                  name :'operator',
                  xtype:'linkcolumn',
                  width:90,
                  renderer:function(){
                  	    var del_link="<a href='#' style='text-decoration:none' >删除</a>";
                  		return del_link; 
                  }
                }
            ]
           };
    	return basic;
    },
     /**--------------------------初始化工具栏--------------------------------*/
	initTbar :function(){
		var me=this;
		var basic={
			items:[{
	 					xtype :'button',
	 					name :'add',
	 					text : '新增评教活动',
	 					iconCls:'page-add'
 					}
 				]
		}
		//创建toolbar对象
 		this.tbar=new NS.toolbar.Toolbar(basic);
 		//绑定事件
 		this.tbar.bindItemsEvent({
 			'add': {event: 'click', fn: this.showAddForm, scope: this}
 		})
 		return this.tbar;
 	},
 	  /**
     * reWrite - showAddForm
     */
    showAddForm : function(){
    	var me=this;
    	var items = this.addFormItems();
		var pjdx=this.pjdx=this.getIdByDm("pjdx","1",this.tranData);
    	var bpdx=this.bpdx=this.getIdByDm("bpdx","2",this.tranData);
    	var ztid=this.getIdByDm('topicId','XSPJ',this.tranData);
    	var xx_fw=this.getIdByDm('pjfwId','QX',this.tranData);
    	var yx_fw=this.getIdByDm('pjfwId','YX',this.tranData);
    	var zy_fw=this.getIdByDm('pjfwId','ZY',this.tranData);
    	var nj_fw=this.getIdByDm('pjfwId','NJ',this.tranData);
    	var bj_fw=this.getIdByDm('pjfwId','BJ',this.tranData);
    	if(this.form){
    		this.form.reset();
    		this.form.show();
    		var pjxnCom=this.form.getField('xnId');
	    	var pjxqCom=this.form.getField('xqId');
	    	pjxnCom.setValue(this.xnId);
	    	pjxqCom.setValue(this.xqId);
		    this.form.getField('pjdx').setValue(pjdx);
	    	this.form.getField('bpdx').setValue(bpdx);
    		this.form.getField('pjfwId').setValue(xx_fw); //评教范围
	    	this.form.getField('topicId').setValue(ztid);//设置默认主题
    	}else{
    		var form = this.form = this.createBaseForm({
	    		title : '新增评教活动',
	    		width :630,
	    		height:350,
	    	//	formType : 'add',
	    		columns:2,
	    		closeAction:'hide', //关闭动作：是隐藏还是关闭
	    		padding : '10 10 10 10',
	    		buttons:[{text : '保存',name : 'add'},{text : '取消',name : 'cancel'}],
	    		items:items
	    		//buildForm:true
    		});
    		var pjxnCom=this.form.getField('xnId');
	    	var pjxqCom=this.form.getField('xqId');
	    	pjxnCom.setValue(this.xnId);
	    	pjxqCom.setValue(this.xqId);
    		var topicCom=this.form.getField('topicId');
	    	topicCom.setValue(ztid);//设置默认主题
	    	//this.form.getField('topics').setValue('学生评教');
	    	var fwCom=form.getField('pjfwId');
	    	fwCom.setValue(xx_fw); //评教范围
	    	this.njfwCom=this.form.getField('pjnjIds');//年级范围IDs
	    	if(!this.bjListCom){ //班级
	    		this.bjListCom=this.initBjListComp(pjbjData);
	    	}
	    	if(!this.yxListCom){
	    		this.yxListCom=this.initYXListComp(pjyxData);	//院系范围
	    	}
	    	if(!this.zyListCom){
	    		this.zyListCom=this.initZyListComp(pjzyData);//专业范围
	    	}
	    	this.bjListCom.setDisabled(true);
	    	this.yxListCom.setDisabled(true);
	    	this.zyListCom.setDisabled(true);
	    	this.njfwCom.setDisabled(true);
    	fwCom.on('change',function(){
    		var fwVal=fwCom.getValue();
    		if(fwVal==yx_fw){
    			this.yxListCom.setDisabled(false);
    		}else if(fwVal==zy_fw){
    			this.zyListCom.setDisabled(false);
    		}else if(fwVal==nj_fw){
    			this.njfwCom.setDisabled(false);
    		}else if(fwVal==bj_fw){
    			this.bjListCom.setDisabled(false);
    		}
    		else{
    			this.yxListCom.setDisabled(true);
    			this.zyListCom.setDisabled(true);
    			this.njfwCom.setDisabled(true);
    			this.bjListCom.setDisabled(true);
    		}
    	},this);
    	form.getField('pjdx').setValue(pjdx);
    	form.getField('bpdx').setValue(bpdx);
        form.bindItemsEvent({
            'add' : {event : 'click',fn : this.submitAdd,scope : this},
            'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
        });
        }
   },
     /**
     * 提交新增form
     */
    submitAdd : function(){
       var me = this;
       if(this.form.isValid()){
	       this.saveOrUpdate('save',function(backData){
	    	   //具体处理业务
	    	   if(backData.success){
	    	   	   this.grid.load();
	    		   NS.Msg.info('保存成功！');
	    		   this.cancelForm();
	    	   }else{
					NS.Msg.error('保存失败！');
				} 
	       },this)
       }else{
       		return;
       };
    },
     /**
     * 取消(关闭)form方法
     */
    cancelForm : function(){
	    	if(!this.form.isHidden()){//如果不隐藏
	    		this.form.hide();
	    	}
    },
 		/**
 	 * ----------------------------------------------------------------------------------
 	 * 整体页面
 	 * @param {} tranData
 	 * @param {} tabledata
 	 * @return {}
 	 * ----------------------------------------------------------------------------------
 	 */
    initAndDoLayoutPage:function(){
    	var component = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            border:false,
            items: this.grid
        });
        this.setPageComponent(component);
    }
 })