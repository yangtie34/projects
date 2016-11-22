/**
 * @class Business.component.PageHeader
 * @extend 
 * 带学年学期的页面表头
 */
NS.define('Business.component.PageHeaderXnXq',{
    singleton : true,
    extend : 'Template.Page',
    cssRequires : ['app/business/component/css/page-header.css',
	               'app/business/component/css/firstname-style.css',
	               'app/business/component/css/wbh-common.css'],
    constructor : function(){
        this.basicData = {
        	xnid:'',
        	xqid:'',
            xnmc : MainPage.getXnMc(),
            xqmc : MainPage.getXqMc(),
            cdmc : '',
            xnxqList : '',
            xm : MainPage.getUserName()?MainPage.getUserName():MainPage.getLoginName(),
            bm : MainPage.getBmxx()?MainPage.getBmxx().mc : ''
        };
        this.callParent(arguments);
    },
    /**
     * 获取页面头组件实例
     * @param {String} cdmc
     * @return {NS.Component}
     */
    getInstance : function(cdm,xnxqList,onLoad,scope){
//    	console.log(xnxqList);
        var data = NS.clone(this.basicData);
        NS.apply(data,{cdmc : cdm,xnxqList:xnxqList});
        var tpl=this.tpl = new NS.Template('<h3 class="wbh-common-floatRight">'+
            ' <span class="wbh-common-term">{xnmc}<span class="wbh-common-margin-leftl">{xqmc}</span></span>'+
            ' <span class="wbh-common-history wbh-common-margin-leftl" >'+
            ' <a onmouseover=\'document.getElementById("{hisDiv}").style.display="block" \' onmouseout=\'document.getElementById("{hisDiv}").style.display="none"\' href="#" >历史记录</a>'+
            '<div id="{hisDiv}" class="wbh-common-history-beside" onmouseover=\'document.getElementById("{hisDiv}").style.display="block"\' onmouseout=\'document.getElementById("{hisDiv}").style.display="none"\'>'+
            '<div class="wbh-common-history-hover">'+
            ' <tpl  for="xnxqList">'+
            ' <h3 class="wbh-common-history-new"><a href="#" id="{xnid}--{xqid}--{xnmc}--{xqmc}" name="hisId">{xnmc}  {xqmc}</a></h3>'+
            ' </tpl>'+
            '</div>'+
            ' </span></h3> '+

            '<h1> ' +
            '<span class="pageheader-model">{cdmc}</span>' +
            '<span class="pageheader-user">{xm}</span> ' +
            '<span class="pageheader-dept">{bm}</span> ' +
            '</h1> '

        );
        data.hisDiv = this.hisDiv = this.getDivId('hisDiv');
        var component = this.component = new NS.Component({
            tpl : this.tpl,
            data : data
        });
        this.component.on('click',function(event,target){
			if(target.name == 'hisId'){
				var xnxqs = target.id;
				var xnId = xnxqs.split('--')[0];
				var xqId = xnxqs.split('--')[1];
				var xnMc = xnxqs.split('--')[2];
				var xnMc1 = xnxqs.split('--')[3];
				var xqMc = xnxqs.split('--')[4];				
				data.xnId = xnId;
				data.xqId = xqId;
				data.xnmc= xnMc;
				data.xqmc = xnMc1;
                data.hisDiv = this.hisDiv;
				this.component.refreshTplData(data);
				onLoad.call(scope||this,data);
			}
		},this);
        return component;
    },
    /**
     * 生成div的id
     */
    getDivId  : function(id){
        return id+NS.id();
    }

});