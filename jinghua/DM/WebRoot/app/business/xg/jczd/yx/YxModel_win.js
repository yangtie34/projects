/**
 * 助学金审核页面
 */
NS.define('Business.xg.jczd.yx.YxModel_win', {
        /**
         * 创建提名win
         */
	createXqWin : function(xsId,pclxId,zxjId){
		NS.loadCss('app/business/xg/jczd/template/style/zxjjg-xq.css');
		this.callService([
						{key:'queryStuInfo',params:{xsId:xsId,lsmk:this.lsmk}},
						{key:'queryStuTmInfo',params:{pclxId:pclxId,lsmk:this.lsmk}},
						{key:'queryShHistory',params:{id:zxjId,lsmk:this.lsmk}}
		                  ],function(data){
				this.showXqWin(data);
			});
		},
	showXqWin : function(data){
		NS.loadFile('app/business/xg/jczd/template/yx/jg-xq.html', function(text) {
	        var tplXqCom = new NS.Component({
	            tpl : new NS.Template(text),
	            data : data
	        });
	        var window = new NS.window.Window({
	            layout : 'fit',
	            height : 450,
	            width : 490,
	            border : false,
	            autoShow : true,
	            modal : true,
	            resizable : false,
	            items : tplXqCom
	        },this);
	        this.CreateCom4JgWin(data);
	    },this);
	},
    /**
     *组装组件
     */
    CreateCom4JgWin : function(data){
        var stuInfoCom=this.stuInfoCom = Business.xg.jczd.yx.YxModelCom.createStuInfoCom(data.queryStuInfo.data[0]);//学生基本信息组件
        var tmInfoCom =this.tmInfoCom= Business.xg.jczd.yx.YxModelCom.createZxjTmInfoCom(data.queryStuTmInfo.data[0]);//创建提名信息组件
        var shInfoCom = this.shInfoCom = Business.xg.jczd.yx.Yx_Com.createShHistory(data.queryShHistory.data);//创建审核历史信息组件
        shInfoCom.render('zxj_sh_shInfo');
        stuInfoCom.render('zxjjg-xq-stuinfo');
        tmInfoCom.render('zxjjg-xq-zxjxx');
    }
});

