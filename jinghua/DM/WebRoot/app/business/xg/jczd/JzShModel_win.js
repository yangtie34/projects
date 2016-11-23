/**
 * 审核页面-弹出窗口
 */
NS.define('Business.xg.jczd.JzShModel_win', {


    getTitleMc : function(){
        var mc = "";
        switch(this.lsmk){
            case "TbXgJzZxj" : mc="助学金";
                break;
            case "TbXgJzJm" : mc="学费减免";
                break;
            case "TbXgJzJxj" : mc="奖学金";
                break;
            case "TbXgJzRych" : mc="荣誉称号";
                break;
        }
        return mc;
    },
        /**
         * 创建提名win
         */
	createTmWin : function(){
	    NS.loadFile('app/business/xg/jczd/template/sh/tm.html', function(text) {
            var data ={mc:this.getTitleMc()};
	        var tplCom = new NS.Component({
	            tpl : new NS.Template(text),
	            data : data
	        });
	        var window=this.tmWin = new NS.window.Window({
	            layout : 'fit',
	            height : 450,
	            width : 690,
	            border : false,
	            autoShow : true,
	            modal : true,
	            resizable : false,
	            items : tplCom
	        },this);
	        this.CreateCom4TmWin();
	        tplCom.on('click',function(event,target){
	            //监听添加按钮
	            if(target.nodeName == 'BUTTON' && target.name == 'zxj_tm_tm'){
	                    this.saveTmInfo();//保存提名信息
	            }
	            if(target.nodeName == 'BUTTON' && target.name == 'zxj_tm_gb'){
	                window.close();
	            }
	        },this);
	
	    },this);
	
	},


    /**
     *组装组件
     */
    CreateCom4TmWin : function(){
        var queryStuCom =  Business.xg.jczd.CreateJzCom.createQueryStuCom();//查询组件
        var stuInfoCom=this.stuInfoCom = Business.xg.jczd.CreateJzCom.createStuInfoCom(' ');//学生基本信息组件
        var historyCom =this.historyCom= Business.xg.jczd.CreateJzCom.createZxjHistoryCom(' ');//创建历史信息组件
        var tmInfoCom =this.tmInfoCom= Business.xg.jczd.CreateJzCom.createZxjTmInfoCom(' ');//创建提名信息组件
        queryStuCom.render('zxj_tm_queryStu');
        stuInfoCom.render('zxj_tm_stuInfo');
        historyCom.render('zxj_tm_history');
        tmInfoCom.render('zxj_tm_tmInfo');
        this.onQueryStuComSelect(queryStuCom);
    },
    /**
     * 添加监听事件
     */
    onQueryStuComSelect : function(queryStuCom){
        queryStuCom.on('select',function(combo,records){
            this.xsId = records[0].id;
            this.callService([
                {key:'queryStuInfo',params:{xsId:records[0].id}},
                {key:'queryStuHistory',params:{xsId:records[0].id,lx:'ZXJ',lsmk:this.lsmk}},
                {key:'queryStuTmInfo',params:{pclxId:this.pclxId,lsmk:this.lsmk}}
            ],function(data){
                if(data.queryStuInfo.success){
                    this.stuInfoCom.refreshTplData(data.queryStuInfo.data[0]);
                }
                if(data.queryStuHistory.success){
                    this.historyCom.refreshTplData(data.queryStuHistory.data);
                }
                if(data.queryStuTmInfo.success){
                    this.tmInfoCom.refreshTplData(data.queryStuTmInfo.data[0]);
                }
            },this);
        },this);
    },
    /**
     * 保存提名信息
     */
    saveTmInfo : function(){
       var zlsfqq =  $('#zxj_tm_zlsfqq').is(':checked');//资料是否齐全
        if(zlsfqq){
            zlsfqq=1;
        }else{
            zlsfqq=0;
        }
        if(!this.xsId){
            NS.Msg.error('请选择提名学生！');
            return;
        }
        if(!zlsfqq){
            NS.Msg.error('资料不齐全！');
            return;
        }
       var sqly =  $('#zxj_tm_sqly').val();//申请理由
       var params ={
            xsId:this.xsId,
            zlsfqq:zlsfqq,
            sqly:sqly,
            pcId:this.pcId,
            lxId:this.lxId,
            pclxId:this.pclxId,
            lsmk:this.lsmk
        }
        this.callService([
            {key:'saveTmInfo',params:params}
        ],function(data){
            if(data.saveTmInfo.success){
                NS.Msg.info('提名成功！');
                this.tmWin.close();
                this.grid.refresh();
            }else if(data.saveTmInfo=='4'){
                NS.Msg.info('该学生在本批次已经通过审核！');
            }else if(data.saveTmInfo=='3'){
                NS.Msg.info('该学生在本批次已经提名！');
            }else if(data.saveTmInfo=='0'){
                NS.Msg.info('没有设置名额,无法提名！');
            }else if(data.saveTmInfo=='2'){
                NS.Msg.info('名额超出设置最大名额数!');
        }
        },this);
    },
    /**
     * 创建审核win
     */
    createShWin : function(data){
        NS.loadFile('app/business/xg/jczd/template/sh/sh.html', function(text) {
            var datas ={mc:this.getTitleMc()};
            var tplCom = new NS.Component({
                tpl : new NS.Template(text),
                data : datas
            });
            var window=this.shWin = new NS.window.Window({
                layout : 'fit',
                height : 510,
                width : 690,
                border : false,
                autoShow : true,
                modal : true,
                resizable : false,
                items : tplCom
            },this);
            this.CreateCom4ShWin(data);
            tplCom.on('click',function(event,target){
                //监听添加按钮
                if(target.nodeName == 'BUTTON' && target.name == 'zxj_sh_qr'){
                         this.saveShInfo();
                }
                if(target.nodeName == 'BUTTON' && target.name == 'zxj_sh_gb'){
                    window.close();
                }
            },this);
        },this);
    },

    /**
     *组装组件
     */
    CreateCom4ShWin : function(data){
        var stuInfoCom = Business.xg.jczd.CreateJzCom.createStuInfoCom(data.queryStuInfo.data[0]);//学生基本信息组件
        var historyCom = Business.xg.jczd.CreateJzCom.createZxjHistoryCom(data.queryStuHistory.data);//创建历史信息组件
        var tmInfoCom = Business.xg.jczd.CreateJzCom.createZxjTmInfoCom(data.queryStuTmInfo.data[0]);//创建提名信息组件
        var shInfoCom = Business.xg.jczd.CreateJzCom.createShHistory(data.queryShHistory.data);//创建审核历史信息组件
        stuInfoCom.render('zxj_sh_stuInfo');
        historyCom.render('zxj_sh_history');
        tmInfoCom.render('zxj_sh_tmInfo');
        shInfoCom.render('zxj_sh_shInfo');
    },
    /**
     * 保存审核信息
     */
    saveShInfo : function(){
        var shjg =  $('[name="zxj_sh_shjg"]:checked');//审核结果
        var shdm = shjg.val();
        var shyj = $('#zxj_sh_shyj').val();//  审核意见
        this.callService(
            {key:'saveShInfo',params:{shdm:shdm,shyj:shyj,nodeId:this.zxjId,queryType:this.queryType,lsmk:this.lsmk}}
        ,function(data){
            if(data.saveShInfo.success){
                this.shWin.close();
                this.grid.refresh();
                NS.Msg.info('审核成功！');
            }else{
                NS.Msg.error('审核失败！');
            }
        },this);
    },
    /**
     *创建批量审核win
     */
    createPlShWin : function(){
        NS.loadFile('app/business/xg/jczd/template/sh/plsh.html', function(text) {
            var tplCom = new NS.Component({
                tpl : new NS.Template(text),
                data : ' '
            });
            var window=this.shWin = new NS.window.Window({
                layout : 'fit',
                height : 270,
                width : 315,
                border : false,
                autoShow : true,
                modal : true,
                resizable : false,
                items : tplCom
            },this);
            tplCom.on('click',function(event,target){
                //监听添加按钮
                if(target.nodeName == 'BUTTON' && target.name == 'zxj_plsh_qr'){
                    this.saveShInfo();
                }
                if(target.nodeName == 'BUTTON' && target.name == 'zxj_plsh_gb'){
                    window.close();
                }
            },this);
        },this);
    }

});

