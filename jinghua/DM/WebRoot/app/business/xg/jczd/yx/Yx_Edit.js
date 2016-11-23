/**
 * 助学金审核页面
 */
NS.define('Business.xg.jczd.yx.Yx_Edit', {
	/**
	 * 请求数据
	 */
	initEditWin : function(xsId,pclxId,zxjId){
		NS.loadCss('app/business/xg/jczd/template/style/zxjyx-infor.css');
		this.xsId = xsId;
		this.pclxId = pclxId;
		this.zxjId = zxjId;
		this.callService([
							{key:'queryStuInfo',params:{xsId:xsId}},
							{key:'queryStuHistory',params:{xsId:xsId,lx:'ZXJ',lsmk:this.lsmk}},
							{key:'queryStuTmInfo',params:{zxjId:zxjId,lsmk:this.lsmk}},
							{key:'queryShHistory',params:{id:zxjId,lsmk:this.lsmk}}
			                  ],function(data){
					this.createEditWin(data);
		});
	},
    /**
     * 创建编辑win
    */
	createEditWin : function(data){
	    NS.loadFile('app/business/xg/jczd/template/yx/zxjyx-edit.html', function(text) {
	        var tplEditCom = new NS.Component({
	            tpl : new NS.Template(text),
	            data : data
	        });
	        var window = this.window = new NS.window.Window({
	            layout : 'fit',
	            height : 450,
	            width : 690,
	            border : false,
	            autoShow : true,
	            modal : true,
	            resizable : false,
	            items : tplEditCom
	        },this);
	        this.CreateCom4TmWin(data);
	        tplEditCom.on('click',function(event,target){
	            //监听添加按钮
	            if(target.nodeName == 'BUTTON' && target.name == 'zxj_edit_tm'){
	                 this.saveTmInfo();//保存提名信息
	                   
	            }
	            if(target.nodeName == 'BUTTON' && target.name == 'zxj_edit_save'){
                    this.updateTmInfo();//保存提名信息
                   
                }
	            if(target.nodeName == 'BUTTON' && target.name == 'zxj_edit_gb'){
	                window.close();
	            }
	        },this);
	
	    },this);
	
	},


    /**
     *组装组件
     */
    CreateCom4TmWin : function(data){
//    	data.queryShHistory.data={HJMC : 'SFSFSDF',SHYJ:'TTTTTT'};
        var stuInfoCom=this.stuInfoCom = Business.xg.jczd.yx.Yx_Com.createStuInfoCom(data.queryStuInfo.data[0]);//学生基本信息组件
        var historyCom =this.historyCom= Business.xg.jczd.yx.Yx_Com.createZxjHistoryCom(data.queryStuHistory.data);//创建历史信息组件
        var tmInfoCom =this.tmInfoCom= Business.xg.jczd.yx.Yx_Com.createZxjTmInfoCom(data.queryStuTmInfo.data[0]);//创建提名信息组件
        var shInfoCom = this.shInfoCom = Business.xg.jczd.yx.Yx_Com.createShHistory(data.queryShHistory.data);//创建审核历史信息组件
        stuInfoCom.render('zxj_edit_stuInfo');
        historyCom.render('zxj_edit_history');
        tmInfoCom.render('zxj_edit_tmInfo');
        shInfoCom.render('zxj_sh_shInfo');
    },
    
    /***
     * 保存修改的预选信息
     */
    updateTmInfo:function(){
    	var zlqq =  $('#zxjyx-edit-zlsfqq').is(':checked');//资料是否齐全
    	var sqly =  $('#zxj_edit_sqly').val();//申请理由
        this.callService(
                {key:'saveZxjYxTm',params:{sqly:sqly,xsId:this.xsId,zxjId:this.zxjId,zlsfqq:zlqq}}
            ,function(data){
                if(data.saveZxjYxTm.success){
                    this.window.close();
                    this.refreshDataByPclx();
                    NS.Msg.info('保存成功！');
                }else{
                    NS.Msg.error(data.saveZxjYxTm.info);
                }
         },this);
    },
    /**
     * 保存提名信息
     */
    saveTmInfo : function(){
       var zlqy =  $('#zxjyx-edit-zlsfqq').is(':checked');//资料是否齐全
        if(!this.xsId){
            NS.Msg.error('请选择提名学生！');
            return;
        }
        if(!zlqy){
            NS.Msg.error('资料不齐全！');
            return;
        }
       var sqly =  $('#zxj_edit_sqly').val();//申请理由
       this.callService(
               {key:'zxjYxTm',params:{lsmk:this.lsmk,sqly:sqly,xsId:this.xsId,pcId:this.pcId,lxId:this.lxId,zxjId:this.zxjId,queryType:"END",popType:'edit'}}
           ,function(data){
               if(data.zxjYxTm.success){
                   this.window.close();
                   this.refreshDataByPclx();
//                   this.grid.refresh();
                   NS.Msg.info('提名成功！');
               }else{
                   NS.Msg.error(data.zxjYxTm.info);
               }
        },this);
    },
});

