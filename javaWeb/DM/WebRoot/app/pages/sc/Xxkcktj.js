/**
 * 学校课程库统计
 */
NS.define('Pages.sc.Xxkcktj', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'tplBhkCjlrPc',path : 'app/pages/sc/template/xxkcktj_main.html'},
	                {fieldname : 'top01',path : 'app/pages/sc/template/xxkcktj_top1.html'},
	                {fieldname : 'top02',path : 'app/pages/sc/template/xxkcktj_top2.html'},
	                {fieldname : 'top03',path : 'app/pages/sc/template/xxkcktj_top3.html'},
	                {fieldname : 'table',path : 'app/pages/sc/template/xxkcktj_table.html'}
	              ],
	modelConfig : {
	      serviceConfig : {
	      	kckgk:'scKcktjService?queryKckgk',	//课程库概况
	      	kcflqk:'scKcktjService?queryKcflqk',  // 课程分类情况
	      	kccddw:'scKcktjService?queryKccbdw',	//课程承担单位
	      	bxqkkqk:'scKcktjService?queryBxqkkqk',	//本学期开课情况
	      	bxqksl:'scKcktjService?queryBxqksl' 	//本学期课时量
	      }
	 },
    mixins : [
        "Pages.zksf.comp.Scgj"		//输出工具类
    ],
    cssRequires : ['app/pages/sc/template/css/xxkcktj.css','app/pages/sc/template/css/sc_base.css'],
    init : function() {
        this.callService([{key:'kckgk'},{key : 'bxqkkqk'},{key : "kcflqk"},{key : 'kccddw'},{key:'bxqksl'}],function(data){
        	this.data.top03 = data.bxqkkqk[0];
			this.data.sc_xxkcktj_top1 = data.bxqksl;
			for ( var i in data.bxqksl) {
				var t = data.bxqksl[i];
				t.zb = t.y;
				t.y = parseFloat(t.num);
			}
        	this.data.top02 = data.bxqksl;
        	this.data.top01 = data.kckgk[0];
        	this.data.chart = data.kcflqk;
        	this.data.table = data.kccddw;
        	
            this.initComponent();
        },this);
    }, 
    initComponent : function() {
    	var page =this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            autoScroll : true,
	            html : this.tplBhkCjlrPc,
	            autoShow : true
        });
    	page.on('click',function(event){
    		if ($(event.getTarget()).attr("attr") == 'help') {
				$("#sc_xxkcktj_help").slideToggle();
			}
    	});
        page.on('afterrender',function(){ 
        	this.renderChildComp();
        },this);
        this.setPageComponent(page);
    },
    //渲染各个小组件
    renderChildComp : function(){
    	//this.renderCommonChart('sc_xxkcktj_chart','课程分类数量','课程数',this.data.chart,'column');
		/*this.renderCommonChart({
			divId : 'sc_xxkcktj_chart',
			title : "课程分类数量",
			yAxis : "课程数",
			type :"column",
			data : this.data.chart
		})*/
    	this.render("sc_xxkcktj_top1", this.top01, this.data.top01);
    	this.render("sc_xxkcktj_top2", this.top02, this.data.top04);
    	this.render("sc_xxkcktj_top3", this.top03, this.data.top03);
        //this.renderPieChart('sc_xxkcktj_top4','','课程数占比',this.data.top02,false);
		this.renderPieChart({
			divId : 'sc_xxkcktj_top4',
			title : "",
			yAxis : "课程数占比",
			data : this.data.top02
		});
    	//this.render("sc_xxkcktj_table", this.table, this.data.table);
        //this.renderCommonChart('sc_xxkcktj_table','课程承担单位','课程数',this.data.table,'column');
		this.renderCommonChart({
			divId : 'sc_xxkcktj_table',
			title : "建设课程数",
			yAxis : "课程数",
			type :"column",
			data : this.data.table
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
    },
    data : {
    	top01 : {kczs:222,fqkc:0,jszkc:222} ,//课程总数、教授中课程、废弃课程
    	top02 : [{num:60456,name:"理论学时",y:55},{num:49464,name:"实践学时",y:45},{num:264,name:"上机学时",y:13.85},{num:0,name:"一体化学时",y:0.0}],//
    	top03 : {kczs:222,bxqkcs:94,bxqkkzb:42.34},
    	chart : [{field:"经济学",name:"学科门类",value:45},
			{field:"法学",name:"课程分类",value:27},
			{field:"教育学",name:"课程分类",value:31},
			{field:"文学",name:"课程分类",value:35},
			{field:"历史学",name:"课程分类",value:20},
			{field:"理学",name:"课程分类",value:7},
			{field:"工学",name:"课程分类",value:27},
			{field:"管理学",name:"课程分类",value:17},
			{field:"艺术学",name:"课程分类",value:4}],
    	table : []
    }
});
