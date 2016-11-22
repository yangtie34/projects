/**
 * 在籍学生素质统计
 */
NS.define('Pages.sc.ys.a', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'zjxsszmain',path : 'app/pages/sc/ys/temp/ys.html'}
	              ],
	modelConfig : {
	 },
    tpmc:'a',
    mixins : [
    ],
    cssRequires : ['app/pages/sc/template/css/sc_base.css'],
    init : function() {
    	var page =this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            autoScroll : true,
	            tpl : this.zjxsszmain,
                data:{tpmc:this.tpmc},
	            autoShow : true
        });
        this.setPageComponent(page);
    },
    clearDiv : function(domId){
        var dom=document.getElementById(domId);
        dom.innerHTML="";
    },
    render:function(nodeId,tpl,data){
        this.clearDiv(nodeId);
        var comp = new NS.Component({
            data : data,
            tpl : tpl
        });
        comp.render(nodeId);
    },
});
