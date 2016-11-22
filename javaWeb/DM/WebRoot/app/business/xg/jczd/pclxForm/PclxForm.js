/**
 * 学工-奖惩助贷-[批次设置类型form]
 * 组件 model
 */
NS.define('Business.xg.jczd.pclxForm.PclxForm',{
	extend : 'Template.Page',
    
    mixins : ['Pages.xg.Util'],
    
    requires:['Business.component.PageSubtitle'],
    
    tplRequires : [{fieldname:'gridTpl', path:'app/business/xg/jczd/pclxForm/template/xg_jz_pcsz_grid.html'},
                   {fieldname:'lxTpl',	 path:'app/business/xg/jczd/pclxForm/template/xg_jz_pcsz_lx.html'}],
	//css预加载
	cssRequires : ['app/business/xg/jczd/pclxForm/style/xg_jz_pcsz-common.css',
		           'app/business/xg/jczd/pclxForm/style/xg_jz_pcsz-info.css'],
	
	modelConfig:{
		serviceConfig:{
          	//查询批次详细信息
			queryJzPcLxGrid : 'jzPcszService?queryJzPcLxGrid',
	    	//更新批次-类型
	    	updateJzPclx : 'jzPcszService?updateJzPclx',
	    	//删除-批次[类型]
	    	deleteJzPclx : 'jzPcszService?deleteJzPclx',
		}
	},
	
    beforInit : function(){
    	//组件唯一标示ID前缀
    	this.idF  	 = NS.id()+'-pcsz_pclx_'; 
    	//grid
    	this.lxmcF   = this.idF+'lxmc_';
    	this.meF	 = this.idF+'me_';
    	this.blF	 = this.idF+'bl_';
    	this.updateF = this.idF+'update_';
    	this.deleteF = this.idF+'delete_';
    	//lx info
    	this.lxId	  = this.idF+'lxId';//类型ID
    	this.xssfksqId= this.idF+'xssfksq';//学生是否可申请ID
    	this.meblName = this.idF+'mebl'; //名额比例name
    	this.meId	  = this.idF+'me'; //名额ID
    	this.blId	  = this.idF+'bl'; //比例ID
    	//button
    	this.cancleBtnId = this.idF+'cancleBtn'; //取消本次修改
    	this.saveBtnId	 = this.idF+'save'; //保存类型
    	this.closeBtnId	 = this.idF+'close'; //关闭
    	//隐藏div
    	this.cancleDivId = this.idF+'cancleDiv'; //隐藏的div，不用设置名额
    	
    },
    
    show : function(data, sslx){
    	if(this.tplready){
    		this.initFirst(data, sslx);
    	}else{
    		this.on('tplready', function(){
    			this.initFirst(data, sslx);
    		},this);
    	}
    },
    initFirst : function(data, sslx){
    	this.beforInit();
    	if(sslx){
    		this.updateServiceParams('updateJzPclx', {sslx:sslx}); //需要调用系统的 updateServiceParams
    	}
    	this.initSecond(data);
    },
    initSecond : function(obj){
    	this.XgAjax('queryJzPcLxGrid', {id:obj.pcId}, function(data){
    		if(data.success){
    			obj.gridData = data.data;
    	    	this.initData(obj);
    	    	this.initTpl();
    	    	this.initComponent();
    		}else{
    			this.XgError();
    		}
    	},this);
    },
    
    initData : function(data){
    	// {pcId:'', gridData:data, lxLabel:'', lxOptions:data, isLimit:true}
    	this.pcId	  	 = data.pcId; //批次id
    	this.gridTplData = data.gridData; //grid数据
    	this.isLimit  	 = data.isLimit || true; //是否限制类型复选
    	this.lxLabel 	 = data.lxLabel;  //助学金类型
    	this.lxOptions	 = data.lxOptions;//类型options

    	//再处理 gridData
    	for(var i=0,len= this.gridTplData.length; i<len; i++){
    		var da	   = this.gridTplData[i];
    		da.lxmcF   = this.lxmcF;
    		da.meF	   = this.meF;
    		da.blF	   = this.blF;
    		da.updateF = this.updateF;
    		da.deleteF = this.deleteF;
    	}
    	//再处理 lxData
    	this.lxTplData	 = {
    		lxLabel   : this.lxLabel,
    		lxOptions : this.lxOptions,
    		lxId   	  : this.lxId,
    		xssfksqId : this.xssfksqId,
    		meblName  : this.meblName,
    		meId   	  : this.meId,
    		blId   	  : this.blId,
    		cancleBtn : this.cancleBtnId,
    		saveBtn   : this.saveBtnId,
    		closeBtn  : this.closeBtnId,
    		cancleDiv : this.cancleDivId
    	};
    },
    
    initTpl : function(){
    	//grid tpl
		this.gridTplCmp = new NS.Component({
			border : false,
			data   : this.gridTplData,
			tpl	   : this.gridTpl
		});
		//lx info tpl
		this.lxTplCmp = new NS.Component({
			border : false,
			data   : this.lxTplData,
			tpl    : this.lxTpl
		});
		//监听
		this.gridTplCmp.on('click', function(event, target){
			//grid-修改
			var reg = new RegExp('^'+this.updateF);
			if(reg.test(target.id)){
				var pclxId = target.id.substr(this.updateF.length);
				this.updateJzPclx(pclxId);
			};
			//grid-删除
			var reg = new RegExp('^'+this.deleteF);
			if(reg.test(target.id)){
				var pclxId = target.id.substr(this.deleteF.length);
				this.deleteJzPclx(pclxId);
			};
		},this);
		this.lxTplCmp.on('click', function(event, target){
			//radio切换
			if(target.name == this.meblName){
				var radioVal = $(target).val();
				this.listenerRadio(radioVal);
			}
			//保存
			if(target.id == this.saveBtnId){
				this.submit();
			}
			//关闭
			if(target.id == this.closeBtnId){
				this.close();
			}
			//取消本次修改
			if(target.id == this.cancleBtnId){
				this.resetJzlx();
			}
		},this);
    },
    deleteFn : NS.emptyFn,
    submitFn : NS.emptyFn,

    initComponent : function(){
    	//win 内容器组件
    	this.component = new NS.container.Container({
			style : {padding:'1.5em'},
			items : [Business.component.PageSubtitle.getInstance('类型信息列表'),
			         this.gridTplCmp, 
			         Business.component.PageSubtitle.getInstance('填写类型信息'),
			         this.lxTplCmp]
		});
    	this.win = new NS.window.Window({ //win
			width : 420,
			height: 415,
			modal : true,
			autoScroll : true,
			items : this.component
		});
		this.win.show();
    	this.baseRefresh();
		this.initEl();
    },
    baseRefresh : function(){
    	this.lxTplCmp.refreshTplData(this.lxTplData); //刷新lx tpl
    	this.refreshGrid(); //刷新grid tpl
    },
    
    /***********************组件操作***********************/
    /**
     * grid-修改
     */
    updateJzPclx : function(pclxId){
    	this.pclxId = pclxId;
    	var v = this.setJzPcInfo(pclxId);
    	this.listenerRadio(v);
    	this.listenerCancleDiv(v);
    	this.elO.cancleBtn.show();
    },
    setJzPcInfo : function(pclxId){
    	var v;
    	for(var i=0,len=this.gridTplData.length; i<len; i++){
    		var _data   = this.gridTplData[i];
    		if(_data.pclxId == pclxId){
		    	var	lxId    = _data.lxId,
		    		xssfksq = _data.xssfksq,
		    		me      = _data.me,
		    		bl	    = _data.bl;
		    	v = _data.sfzjtg=='1' ? true : false;
		    	this.elO.lx.val(lxId);
		    	this.elO.xssfksq.val(xssfksq);
		    	this.elO.me.val(me);
		    	this.elO.bl.val(bl);
		    	this.updateLxId = lxId; //ps 为了判断保存的类型是否已经存在
		    	break;
    		}
    	}
    	return v;
    },
    /**
     * grid-删除
     */
    deleteJzPclx : function(pclxId){
    	NS.Msg.changeTip('提示', '确定要删除这个类型吗？', function(){
    		this.XgAjax('deleteJzPclx', {id:pclxId}, function(data){
	    		if(data.success){
	    			NS.Msg.info('删除成功！');
    				//更新grid数据
	    			this.refreshGrid('DELETE', pclxId);
    				//重置lx
    				this.resetJzlx();
    				//调用开发者方法
	    	    	this.deleteFn();
	    		}else if(data.info){
    				this.XgInfo(data.info);
    			}else{
    				this.XgError();
    			}
	    	}, this);
    	},this);
    },

    /**
     * lx-提交-批次类型
     */
    submit : function(fn){
    	var params = this.getSubmitPar();
    	if(!params){
    		return;
    	}else{
    		//判断保存的类型是否存在
    		if(this.isLimit){
	    		var lxIdsHas = NS.Array.pluck(this.gridTplData,'lxId').toString(), //已经存在的lxId
	    			_updateLxId = params.lxId;
	    		if(!this.pclxId && -1!=lxIdsHas.indexOf(_updateLxId)){ //新增
	    			NS.Msg.error('您保存的类型已经存在，请选择其他类型');
	    			return ;
	    		} else if(this.updateLxId){ //update
		    		lxIdsHas = lxIdsHas.replace(new RegExp(this.updateLxId), '');
		    		if(-1!=lxIdsHas.indexOf(_updateLxId)){
		    			NS.Msg.error('您保存的类型已经存在，请选择其他类型');
		    			return ;
		    		}
	    		}
    		}
    		this.XgAjax('updateJzPclx', params, function(data){
    			if(data.success){
    				if(data.info){
    					this.XgInfo(data.info);
    					return;
    				}
	    			NS.Msg.info('保存成功！');
    				var pclxId = data.pclxId,
    					me = data.me,
    					bl = data.bl,
	    				o  = {
		    				pclxId : pclxId,
		    				lxId   : this.elO.lx.val(),
		    				lxmc   : this.elO.lx.find("option:selected").text(),
		    				xssfksq: this.elO.xssfksq.val(),
		    				me	   : me,
		    				bl	   : bl,
		    				sfzjtg : this.elO.lx.find("option:selected").attr('sfzjtg'),
		    			};
    				//更新grid数据
    				this.refreshGrid('UPDATE', o);
    				//重置lx
    				this.resetJzlx();
    				//调用开发者方法
    				this.submitFn();
    			}else{
    				this.XgError();
    			}
    		},this);
    	};
    },
    /**
     * lx-close
     */
    close : function(){
    	this.win.close();
    },
    /**
     * lx-取消本次修改
     */
    resetJzlx : function(){
    	//删除-批次类型id
    	delete this.pclxId;
    	//删除-类型id
    	delete this.updateLxId;
    	//清空-lx tpl
    	this.elO.lx.val('');
    	this.elO.xssfksq.val('');
    	this.elO.me.val('');
    	this.elO.bl.val('');
    	//初始化 radio
    	this.listenerRadio();
    	//隐藏div
    	this.listenerCancleDiv();
    	//隐藏-取消按钮
    	this.elO.cancleBtn.hide();
    },
    
    
    /***********************公共操作***********************/
    initEl : function(){
    	//针对类型tpl
    	this.elO = {
    		lx		 : $('#'+this.lxId),
    		xssfksq  : $('#'+this.xssfksqId),
    		me		 : $('#'+this.meId),
    		bl		 : $('#'+this.blId),
    		mebl	 : $('input:radio[name="'+this.meblName+'"]'),
    		cancleBtn: $('#'+this.cancleBtnId),
    		cancleDiv: $('#'+this.cancleDivId)
    	};
    	//类型-是否直接通过监听
    	var me = this;
    	this.elO.lx.change(function(){
    		var sfzjtg = $(this).find("option:selected").attr('sfzjtg'),
    			v;
    		if(sfzjtg == '1'){
    			v = true;
    		}
			me.listenerRadio(v);
			me.listenerCancleDiv(v);
		});
    },
    /**
     * 是否直接通过-监听	
     * @param value true:显示div，false：隐藏div
     */
    listenerCancleDiv : function(value){
    	if(value)
    		this.elO.cancleDiv.show();
    	else
    		this.elO.cancleDiv.hide();
    },
    /**
     * radio-监听
     * @param value=='me' {比例不可用}
     * @param value=='bl' {名额不可用}
     * @param value=='false' {名额比例都不可用}
     */
    listenerRadio : function(value){
    	this.elO.mebl.attr('disabled', false); 
    	var v = value || 'me';
    	if(v == 'me'){
    		this.elO.mebl.filter('[value="me"]')[0].checked = true;
    		this.elO.me.attr("disabled", false);
    		this.elO.bl.attr("disabled", true);
    	}else if(v == 'bl'){
    		this.elO.mebl.filter('[value="bl"]')[0].checked = true;
    		this.elO.bl.attr("disabled", false);
    		this.elO.me.attr("disabled", true);
    	}else if(v){
        	this.elO.mebl.attr('disabled', true); 
    		this.elO.me.attr("disabled", true);
    		this.elO.bl.attr("disabled", true);
    	}
    },
    refreshGrid : function(bz, o){
    	if(bz){
    		var li = this.gridTplData,
    			len= this.gridTplData.length;
    		if('DELETE' == bz){ //o=pclxId
				for(var i=0; i<len; i++){
					var d = li[i]; //被删除的元素
					if(o == d['pclxId']){
						NS.Array.removeAt(li, i);
						break;
					}
				}
			}else if('UPDATE' == bz){ //o={}
	    		var pclxId = o.pclxId;
				for(var i=0; i<len; i++){
		    		var da = li[i];
		    		if(da.pclxId == pclxId){
						NS.Array.removeAt(li, i);
		    			break;
		    		}
		    	}
	    		o.lxmcF   = this.lxmcF;
	    		o.meF	  = this.meF;
	    		o.blF	  = this.blF;
	    		o.updateF = this.updateF;
	    		o.deleteF = this.deleteF;
				this.gridTplData.push(o);
			}
    	}
    	this.gridTplCmp.refreshTplData(this.gridTplData);
    },
    
    //目前只支持保存一条数据
    getSubmitPar : function(){
    	var params = {
    		pcId	: this.pcId,
    		pclxId  : this.pclxId
    	};
    	var lxV	 	= this.elO.lx.val(),
    		xssfksqV= this.elO.xssfksq.val();
		//类型
    	if('' == lxV)	return this.XgInfo('类型不能为空!');
    	params.lxId = lxV;
    	//学生是否可申请
    	if('' == xssfksqV)	return this.XgInfo('学生是否可申请不能为空!');
    	params.xssfksq = xssfksqV;
    	//总名额
		if(!this.elO.me.attr("disabled")){
	    	var meV = this.elO.me.val(),
	    		b = false,
	    		s = '';
	    	if('' == meV){
	    		s = '总名额不能为空!';
	    	}else if(!/^[1-9]\d*$/.test(meV)){
	    		s = '总名额不是正整数！';
	    	}else{
	    		params.me = meV;
	    		b = true;
	    	}
	    	if(!b){
	    		this.elO.me[0].focus();
	    		return this.XgInfo(s);
	    	}
		}
		//比例
		if(!this.elO.bl.attr("disabled")){
	    	var blV = this.elO.bl.val(),
	    		b = false,
	    		s = '';
	    	if('' == blV){
	    		s = '名额比例不能为空!';
	    	}else if(!/^[1-9]\d*$/.test(blV)){ //浮点数 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$
	    		s = '名额比例不是正整数！';
	    	}else{
		    	params.bl = blV;
	    		b = true;
	    	}
	    	if(!b){
	    		this.elO.bl[0].focus();
	    		return this.XgInfo(s);
	    	}
		}
		
		return params;
    },
	

    /**
     * 绑定回调方法
     */
    listen : function(eventName, fn, scope){
    	if(typeof enentName == 'object'){
    		
    	}
    	this[eventName] =  function(){
    		fn.call(scope);
    	};
    },
});