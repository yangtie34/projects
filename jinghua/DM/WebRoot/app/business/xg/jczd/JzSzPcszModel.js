/**
 * 学工-奖助-设置[批次设置]
 * page model
 */
NS.define('Business.xg.jczd.JzSzPcszModel',{
	
	extend  : 'Business.xg.model.EntityModelNew',
	
	requires: ['Business.xg.jczd.pclxForm.PclxForm',
	           'Business.xg.jczd.pcInfoForm.PcInfoForm',
	           'Business.xg.XsfwSet'],
	//混合类
	mixins  : ['Pages.xg.zxj.zxjsz.pcsz.yhkh_import'], //银行卡号导入 
	          
	//加载TPL
	tplRequires : [{fieldname : 'winTpl1', path : 'app/business/xg/jczd/template/pcsz_win.html'}],
	//css预加载
	cssRequires : ['app/business/xg/template/style/xg-common.css', //这是公共的css
	               'app/pages/xg/zxj/template/style/zxj_pcsz-common.css',
	               'app/pages/xg/zxj/template/style/zxj_pcsz-infor.css',
	               'app/pages/xg/zxj/template/style/zxj_pcsz.css'],
	
    /**
	 * 更新serviceConfig
	 * @Override 
	 */
	addServiceConfig : {
    	//查询批次table
    	queryTableData 			: 'jzPcszService?queryJzPcTableData',
    	//查询新增批次所需数据
    	requestJzPcRequireData  : 'jzPcszService?queryAddJzPcRequireData',
    	//修改批次信息
    	updateJzPc 				: 'jzPcszService?updateJzPc',
    	//删除-批次
    	deleteJzPc 				: 'jzPcszService?deleteJzPc',
    	//处理是否启用
    	updateJzPcBySfqy 		: 'jzPcszService?updateJzPcBySfqy',
		//初始化数据-批次[类型]
    	updateJzPcInitData 		: 'jzPcszService?updateJzPcInitData'
	}, 
	/**
     * 更新serviceParams
     * @Override
     */
    addServiceParams : function(){
    	return { //自己的模板可以直接用 addServiceParams
		    		'queryTableData' 	  : {sslx:this.getSslx()},
		    		'updateJzPcBySfqy'	  : {sslx:this.getSslx()},
		    		'updateJzPcInitData'  : {sslx:this.getSslx()}
		    	};
    },

    /**
     * 初始化静态变量
     * 更新serviceParams
     * @Override
     */
    beforeInit : function(){
    	this.initConstant();
    },
   
	menuName  : '批次设置', //菜单名
	entityName: 'VbXgJzPc', //实体名
	lsmk	  : '',
	lxLabel	  : '类型', //类型设置中的label
	getGridParams : function(){
		return {start:0, limit:25, sslx:this.getSslx()};
    },
    getSslx : function(){ //获取所属类型
    	return this.lsmk;
    },
    getLxLabel : function(){
    	return this.lxLabel;
    },
    
    /**
     * 初始化常量
     */
    initConstant : function(){
    	this.idF = NS.id()+'_XgJzPcsz_', //id 前缀
    	//DIV
    	this.startDateDiv 	 = this.idF+'startDateDiv'; //批次开始时间
    	this.endDateDiv   	 = this.idF+'endDateDiv';   //批次结束时间
    	//批次信息
    	this.pcIdId        	 = this.idF+'id';   //批次id
    	this.pcMc        	 = this.idF+'mc';   //批次名称
    	this.pcXnId     	 = this.idF+'xnId'; //学年id
    	this.pcXqId     	 = this.idF+'xqId'; //学期id
    	this.pcSfqy     	 = this.idF+'sfqy'; //是否启用
    	//Button
    	this.pcSaveBtn  	 = this.idF+'add_save'; //保存类型
    	this.pcCloseBtn 	 = this.idF+'add_close';//关闭
    },
    
    /**
     * tbar 增加一行
     * @Override 
     */
    addTbarRow : function(){
    	return [new NS.Component({
	        	  cls  : 'xg-common-tbar-text',
	        	  html : '说明：对于一条批次数据，请先设置学生范围，然后设置类型，最后执行初始化，分配名额后将其启用，即可进行预选、提名等操作！'
    		    })];
    },
    /**
	 * 初始化tbar
     * @Override 
	 */
    addTbarConfig : function(){
    	var xsfwSz = this.xsfwSz = new Business.xg.XsfwSet();
    	return {
            items: [
                    {xtype: 'button', text: '新增', name: 'addPc',   iconCls:'page-add'},
               '-', {xtype: 'button', text: '修改', name: 'updatePc',iconCls:'page-update'},
               '-', {xtype: 'button', text: '删除', name: 'deletePc',iconCls:'page-delete'},
               '-', xsfwSz.getButton(),
               '-', {xtype: 'button', text: '设置类型', name: 'szlx', iconCls:'page-update'},
               new NS.grid.query.SingleFieldQuery({
                   data : this.tranData,
                   grid : this.grid
               }),
               '-', {xtype: 'button', text: '初始化数据', name: 'csh',iconCls:'page-update'}
               ]
       };
    },
    addTbarEvents : function(){
    	return {
            'addPc'   : {event: 'click', fn: this.addWinFn,     scope: this},
            'updatePc': {event: 'click', fn: this.updateWinFn,  scope: this},          
            'deletePc': {event: 'click', fn: this.showDeleteIds,scope: this},
            'szlx'	  : {event: 'click', fn: this.szlxFn,		scope: this},
            'csh'	  : {event: 'click', fn: this.doCshFn,		scope: this}
        }; 
    },
    /**
     * tbar - 删除
     */
    showDeleteIds : function(){ //删除
    	var rawsValues = this.grid.getSelectRows(),
			sfqyAry = NS.Array.pluck(rawsValues,'sfqy'),
    		len = sfqyAry.length,
			ids;
    	for(var i=0; i<len; i++){
    		if(sfqyAry[i] == '1'){
    			NS.Msg.error('您不能删除已经启用的批次!');
    			return;
    		};
    	}
    	NS.Msg.changeTip('提示','确定要删除这'+len+'条数据吗？',function(){
	    	ids = NS.Array.pluck(rawsValues,'id');
	    	this.XgAjax('deleteJzPc', {ids:ids.toString()}, function(data){
	    		if(data.success){
	    			NS.Msg.info('删除成功!');
		    		this.loadGrid();
	    		}else{
	    			this.XgError();
	    		}
	    	},this);
    	},this);	
    },
    /**
     * tbar - 初始化数据
     */
    doCshFn : function(){
    	var rawsValues = this.grid.getSelectRows();
    	if(rawsValues.length > 1){
    		NS.Msg.info('请仅选择一条数据执行初始化！');
    		return;
    	}else if(rawsValues.length == 0){
    		NS.Msg.info('请选择一条数据执行初始化！');
    		return;
    	}else{
    		if(rawsValues[0].sfqy=="0"){
	    		NS.Msg.changeTip('提示','初始化会持续一段时间，<br>您确定要执行初始化吗?',function(){
	        		this.mask.show();
	    			this.XgAjax('updateJzPcInitData',{id:rawsValues[0].id},function(data){
	    	    		this.mask.hide();
	    				if(data.success){
	    					NS.Msg.info('数据初始化成功！');
	    				}else{
	    					this.XgError();
	    				}
	    			},this,300000);
	    		},this);
    		}else{
    			NS.Msg.info('批次正在启用中，无法执行初始化数据操作！');
    		}
    	}
    },
    /**
     * 导入银行卡号
     */
    importYhkh : function(){
    	this.importForm();
    },
    
    /**
     * grid 列配置
     * @Override 
     */
    addColumnConfig : function(){
    		return [{
					    text : '操作',
					    name : 'cz',
					    align: 'center',
					    xtype: 'linkcolumn',
					    width: 90,
					    renderer : function(value, rowData){
					    	var xq = '<a href="javascript:void(0);">详情</a> | ';
					    	if(rowData.sfqy=='0')
					    		return xq+ '<a href="javascript:void(0);">启用</a>';
					    	else if(rowData.sfqy=='1')
					    		return xq+ '<a href="javascript:void(0);">关闭</a>';
					    }
					}];
    },
    /**
     * grid 绑定事件
     * @Override 
     */
    addGridEvents  : function(){
    	return {'cz': {event: 'linkclick', fn: this.gridClickFn, scope: this}};
    },
	bindGridEvents : function(){ 
        this.grid.on('select',function(rowmodel,record,rowindex){
            this.xsfwSz.getButtonData(this.lsmk,record.id);
        },this);
        this.grid.on('load',function(rowmodel,record,rowindex){
            this.xsfwSz.clearButtonData();
        },this);

    },
    gridClickFn : function(linkValue, recordIndex, cellIndex, data, eOpts){
    	if('详情' == linkValue){
    		var cmp = new Business.xg.jczd.pcInfoForm.PcInfoForm();
    		cmp.show(data.id, this.getSslx());
    	}
    	var cz = '', text='';
    	if('启用' == linkValue){
    		cz = 'y';
    		text = '您确定启用这个批次吗?';
    	}
    	if('关闭' == linkValue){
    		cz = 'n';
    		text = '关闭批次符合条件的学生数据会被清除，再次开启批次时需要先执行初始化数据操作，您确定关闭这个批次吗?';
    	}
    	if(cz != ''){
    		cz = cz=='y' ? '1' : '0';
			NS.Msg.changeTip('提示', text,function(){
	    		this.mask.show();
	    		this.XgAjax('updateJzPcBySfqy', {id:data.id, cz:cz}, function(data){
		    		this.mask.hide();
		    		if(data.success){
		    			NS.Msg.info('设置成功!');
		    			this.loadGrid();
		    		}else{
		    			this.XgError();
		    		}
	    		},this);
			},this);
    	}
    },
    
    /*****************************************TODO add win**********************************/
    /**
	 * 新增窗口
	 */
    addWinFn : function(){
		this.initWin('添加批次');
		this.addRefresh();
    },
    /**
     * 新增的准备-数据/组件
     */
    addRefresh : function(){
    	//判断是否需要请求编码数据
		var fn = function(data){
			//刷新win基础tpl数据
        	this.winRefreshDate(data);
    	};
    	this.requestJzPcRequireDataFn(fn, this);
    },
    
    /*****************************************TODO update win**********************************/
    /**
	 * 修改窗口
	 */
	updateWinFn : function(){
		var rawsValues = this.grid.getSelectRows();
     	if(rawsValues.length < 1){
     		NS.Msg.warning('请先选择一条数据!');
     		return;
     	}
     	if(rawsValues.length > 1){
     		NS.Msg.warning('请仅选择一条数据进行修改操作!');
     		return;
     	}
     	var data = rawsValues[0],
     		sfqy = data.sfqy;
     	if(sfqy == 1){
     		this.XgError('您选择的批次已经启用，您可以关闭后再修改！');
     	}else{
	     	this.initWin('修改批次');
			this.updateRefresh(data);
     	}
	},
	/**
	 * 修改的准备-数据/组件
	 */
	updateRefresh : function(rowData){
		var fn = function(){
			//刷新win基础tpl数据
			this.winRefreshDate();
			//setPcInfo 针对tpl1
			this.setPcInfo(rowData);
		};
    	this.requestJzPcRequireDataFn(fn, this);
	},
	/**
	 * tpl1 设置批次信息
	 */
    setPcInfo : function(rowData){
    	this.elO.id.val(rowData.id);
    	this.elO.mc.val(rowData.mc);
    	this.elO.xnId.val(rowData.xnId);
    	this.elO.xqId.val(rowData.xqId);
    	this.elO.sfqy.val(rowData.sfqy);
    	this.startDate.setValue(rowData.startDate);
    	this.endDate.setValue(rowData.endDate);
    },

    /*****************************************TODO win**********************************/

	/**
	 * 初始化win
	 */
	initWin : function(title){
		this.title = title;
		this.initTpl();
		this.winCtn = new NS.container.Container({ //win 内容器组件
			style : {padding:'1.5em'},
			items : this.winTplCmp1
		});
		this.win = new NS.window.Window({ //win
			title : this.title,
			width : 360,
			height: 340,
			modal : true,
			items : this.winCtn,
			autoScroll : true
		});
		this.win.show();
		this.win.on('close', function(){
			this.closeWin();
		}, this);
	},
    /**
     * 关闭win
     */
    closeWin : function(){
    	delete this.elO;
    	this.win.close();
    },
	/**
	 * 不用再判断-必须的请求数据
	 * 只写请求成功的方法
	 */
	requestJzPcRequireDataFn : function(fn, scope){
		var _scope = scope || this;
		if(!this.winRequireData){
			this.XgAjax('requestJzPcRequireData', {sslx:this.getSslx()}, function(data){
				if(data){
	    			this.winRequireData = {xn:data.xnL, xq:data.xqL, lx:data.lxL};
					fn.call(_scope, data);
	    		}else{
	    			this.XgError();
	    		};
			},_scope);
		}else{
			fn.call(_scope);
		}
	},
	/**
	 * 刷新win基础tpl数据
	 */
	winRefreshDate : function(){
		this.winRefreshTpl1();
		this.winRender();
		this.initEl();
    },
    /**
     * win render date组件
     */
    winRender : function(){
		this.startDate = new NS.form.field.Date({
			width : 110,
			format: 'Y-m-d',
			vtype : 'daterange',
			editable:false
		});
		this.endDate = new NS.form.field.Date({
			width : 110,
			format: 'Y-m-d',
			vtype : 'daterange',
			editable:false
		});
		this.endDate.setStartDateField(this.startDate);
		this.startDate.setEndDateField(this.endDate);
		this.startDate.render(this.startDateDiv);
		this.endDate.render(this.endDateDiv);
    },
    /**
     * 初始化win内element
     */
    initEl : function(){
    	this.elO = {
    		id   	  : $('#'+this.pcIdId), //批次id
			mc   	  : $('#'+this.pcMc),   //批次名称  $$('#add') $('#abc .add')
    		xnId 	  : $('#'+this.pcXnId), //学年id
    		xqId 	  : $('#'+this.pcXqId), //学期id
    		sfqy 	  : $('#'+this.pcSfqy), //是否启用
    		startDate : this.startDate, //开始时间
    		endDate   : this.endDate   //结束时间
    	};
    },
    /**
     * 获取win内数据并执行校验
     */
    getWinParams : function(){
    	var params = {
    		pcId   : this.elO.id.val(),
    		sslx   : this.getSslx()
    	};
    	var mc 	 = this.elO.mc.val(),
    		xnId = this.elO.xnId.val(),
    		xqId = this.elO.xqId.val(),
    		sfqy = this.elO.sfqy.val(),
    		startDate = this.elO.startDate.getRawValue(),
    		endDate   = this.elO.endDate.getRawValue();
    	if('' == mc){
    		return this.XgInfo('批次名称不能为空!');
    	}
    	params.mc = mc;
    	
    	if('' == xnId){
    		return this.XgInfo('评定学年不能为空!');
		}
    	params.xnId = xnId;
    	
    	if('' == xqId){
			return this.XgInfo('评定学期不能为空!');
		}
    	params.xqId = xqId;

    	if(''==startDate || ''==endDate){
			return this.XgInfo('申请时间不能为空!');
		}
    	params.startDate = startDate;
    	params.endDate = endDate;
    	
    	if('' == sfqy){
			return this.XgInfo('是否启用不能为空!');
		}
    	params.sfqy = sfqy;
    	
    	return params;
    },
    /**
     * add / update 批次
     */
    updateJzPc : function(){
    	var params = this.getWinParams();
    	if(params==undefined || params==null){
    		return;
    	}
    	this.XgAjax('updateJzPc', params, function(data){
    		if(data.success){
    			NS.Msg.info(this.title+'成功！');
    			this.closeWin();
	    		this.loadGrid();
    		}else{
    			this.XgError();
    		}
    	},this);
    },
    
    /*****************************************TODO tpl **********************************/
    /**
	 * 初始化tpl
	 */
    initTpl : function(){
    	//页面-左上
    	this.winTplData1 = {};
		this.winTplCmp1 = new NS.Component({
			border : false,
			data : this.winTplData1,
			tpl : this.winTpl1
		});
		//listener
		this.winTplCmp1.on('click',function(event, target){
			//保存类型
			if(target.id == this.pcSaveBtn){
				this.updateJzPc();
			}
			//关闭Win
			if(target.id == this.pcCloseBtn){
				this.closeWin();
			}
		},this);
    },
    /**
     * 刷新tpl1
     */
    winRefreshTpl1 : function(){
		this.winTplData1 = {
			xn		: this.winRequireData.xn, 
			xq		: this.winRequireData.xq,
			id		: this.pcIdId,
    		mc		: this.pcMc,
    		xnId	: this.pcXnId,
    		xqId	: this.pcXqId,
    		sfqy	: this.pcSfqy,
    		saveBtn : this.pcSaveBtn,
    		closeBtn: this.pcCloseBtn,
    		startDateDiv : this.startDateDiv,
    		endDateDiv	 : this.endDateDiv,
		};
		this.winTplCmp1.refreshTplData(this.winTplData1);
    },

    /*****************************************TODO 设置form **********************************/
	szlxFn : function(){
		var rawsValues = this.grid.getSelectRows();
     	if(rawsValues.length < 1){
     		NS.Msg.warning('请先选择一条数据!');
     		return;
     	}
     	if(rawsValues.length > 1){
     		NS.Msg.warning('请仅选择一条数据进行修改操作!');
     		return;
     	}
     	var data = rawsValues[0],
     		sfqy = data.sfqy;
     	if(sfqy == 1){
     		this.XgError('您选择的批次已经启用，您可以关闭后再设置类型！');
     	}else{
			this.initPclxCmp(data);
     	}
	},
	initPclxCmp : function(rowData){
		var fn = function(){
			var obj = {
				pcId 	  : rowData.id,
				lxLabel   : this.getLxLabel(),
				lxOptions : this.winRequireData.lx	
			};
			var cmp = new Business.xg.jczd.pclxForm.PclxForm();
			cmp.listen('submitFn', function(){this.loadGrid();}, this);
			cmp.listen('deleteFn', function(){this.loadGrid();}, this);
			cmp.show(obj, this.getSslx());
		};
    	this.requestJzPcRequireDataFn(fn, this);
	},
	
	
	/*************************修改基础条件********************/
	/**
	 * loadGrid
     * @Override
	 */
    loadGrid : function(params){
    	var _par = params || {};
    	_par.sslx = this.getSslx();
		this.grid.load(_par);
	}
	
});