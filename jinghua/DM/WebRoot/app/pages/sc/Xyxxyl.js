/**
 * 校园信息一览
 */
NS.define('Pages.sc.Xyxxyl', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'xyxxyl',path : 'app/pages/sc/template/xyxxyl_main.html'}
	              ],
	modelConfig : {},
    cssRequires : ['app/pages/sc/template/css/xyxxyl.css','app/pages/sc/template/css/sc_base.css'],
    init : function() {
            this.initComponent();
    }, 
    initComponent : function() {
    	var page =this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            autoScroll : true,
	            html : this.xyxxyl,
	            autoShow : true
        });
    	page.on('click',function(event){
    		if ($(event.getTarget()).attr("attr") == 'help') {
				$("#sc_xyxxyl_help").slideToggle();
			}
    	});
        page.on('afterrender',function(){ 
        	this.renderChildComp();
        },this);
        this.setPageComponent(page);
    },
    renderChildComp : function(){
    	var anc,navs=[],ancs = $("#sc_xyxxyl_main").find($(".anchor"));
    	for ( var i = 0;i < ancs.length; i++) {
			anc = ancs[i];
			navs.push({
				name : $(anc).find("a").attr("name"),
				num : $(anc).find($(".sc_xyxxyl_tit_num")).html(),
				text : $(anc).find($(".sc_xyxxyl_tit_txt")).html()
			});
		}
    	var nav_html = $("<ul/>");
    	for ( var i = 0; i < navs.length; i++) {
			var nav = navs[i];
			var item = $("<li/>").append("<span class='sc_xyxxyl_nav_num'>"+
					nav.num +"</span><a href='#"+ nav.name+"' class='sc_xyxxyl_nav_txt'>"+
					nav.text + "</a>");
			nav_html.append(item);
		}
    	$("#sc_xyxxyl_nav").html(nav_html);
    	
    	var content = $("#sc_xyxxyl_main").parent().parent();
		$(content).scroll( function(){
			var y = $(content).scrollTop() - 50;
			for ( var k = 0; k < ancs.length; k++) {
				var anc = ancs[k],num;
				if(k < ancs.length - 1 ){
					var anc1 = ancs[k+1];
					if( ( y -$(anc)[0].offsetTop) >= 0 && ($(anc1)[0].offsetTop - y ) > 0){
						num = parseInt($(anc).find($(".sc_xyxxyl_tit_num")).html()) - 1;
					}
				}else if(k == ancs.length - 1 && ( y -$(anc)[0].offsetTop) >= 0 ){
					num = k;
				}
				$($("#sc_xyxxyl_nav").find("li")).removeClass('sc_xyxxyl_nav_click');
				$($("#sc_xyxxyl_nav").find("li")[num]).addClass('sc_xyxxyl_nav_click');
			}
		});
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
    }
});
