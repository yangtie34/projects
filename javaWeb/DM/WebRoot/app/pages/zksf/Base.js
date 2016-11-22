/**
 * 校园信息一览
 */
NS.define('Pages.zksf.Base', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'xyxxyl',path : 'app/pages/zksf/tpl/base.html'},
	                {fieldname : 't1',path : 'app/pages/zksf/tpl/text1.html'},
	                {fieldname : 't2',path : 'app/pages/zksf/tpl/text2.html'}
	              ],
	modelConfig : {},
    cssRequires : ['app/pages/zksf/css/base.css'],
    init : function() {
    	var page =this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            autoScroll : true,
	            html : this.xyxxyl,
	            autoShow : true
	    });
		page.on('click',function(event){
			if ($(event.getTarget()).attr("attr") == 'help') {
				$("#zksf_base_help").slideToggle();
			}
		});
		page.on('afterrender',function(){ 
	    	this.renderComp();
	    },this);
	    this.setPageComponent(page);
    }, 
    
    renderComp:function(){
    	this.render("zksf_base_t1",this.t1,{color:"green",title:"学生数量",value:300000000,unit:"人"});
    	this.render("zksf_base_t2",this.t2,{color:"orange",title:"学校共计学生3000人",texts:[{text:"研究生<br>300名<br>占比39%"},{text:"研究生<br>300名<br>占比39%"},{text:"研究生<br>300名<br>占比39%"}]});
    	
    	
    	var base = new Pages.zksf.comp.BaseComp({
			url :"<%=request.getContextPath()%>/exam/sess",
			params : {},
			isAsync:false,
			beforeLoad : function(){
				alert("the ajax will go on ");
			}
		});
		base.queryData();
		base.render("testComp");
		alert(base.id);
    },
    render:function(nodeId,tpl,data){
    	$("#"+nodeId).html(''); 
        var comp = new NS.Component({
            data : data,
            tpl : tpl
        });
        comp.render(nodeId);
    }
});
