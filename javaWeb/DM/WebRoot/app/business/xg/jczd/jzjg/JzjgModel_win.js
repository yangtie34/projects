/**
 * 助学金审核页面
 */
NS.define('Business.xg.jczd.jzjg.JzjgModel_win', {
        /**
         * 创建提名win
         */
	createXqWin : function(xsId,pclxId,jzmcModel){
		this.jzmcModel = jzmcModel;
		NS.loadCss('app/business/xg/jczd/template/style/zxjjg-xq.css');
		this.callService([
						{key:'queryStuInfo',params:{xsId:xsId,lsmk:this.lsmk}},
						{key:'queryStuTmInfo',params:{pclxId:pclxId,lsmk:this.lsmk}}
		                  ],function(data){
				this.showXqWin(data);
			});
		},
	showXqWin : function(data){
		NS.loadFile('app/business/xg/jczd/template/jg/jg-xq.html', function(text) {
	        var tplXqCom = new NS.Component({
	            tpl : new NS.Template(text),
	            data : data
	        });
	        var window = new NS.window.Window({
	            layout : 'fit',
	            height : 360,
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
        var stuInfoCom=this.stuInfoCom = Business.xg.jczd.jzjg.JzjgModelCom.createStuInfoCom(data.queryStuInfo.data[0]);//学生基本信息组件
        data.queryStuTmInfo.data[0].jzmcModel = this.jzmcModel;
        var tmInfoCom =this.tmInfoCom= Business.xg.jczd.jzjg.JzjgModelCom.createZxjTmInfoCom(data.queryStuTmInfo.data[0]);//创建提名信息组件
        stuInfoCom.render('zxjjg-xq-stuinfo');
        tmInfoCom.render('zxjjg-xq-zxjxx');
    }
});

