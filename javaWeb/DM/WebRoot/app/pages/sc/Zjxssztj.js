/**
 * 在籍学生素质统计
 */
NS.define('Pages.sc.Zjxssztj', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'zjxsszmain',path : 'app/pages/sc/template/zjxssztj_main.html'},
	                {fieldname : 'tabletpl',path : 'app/pages/sc/template/zjxssztj_table.html'}
	              ],
	modelConfig : {
	      serviceConfig : {
	      	rxxl:'zjxssztjService?queryRxxl',	//入学学历
	      	pycc : 'zjxssztjService?queryPycc',
	      	xz : 'zjxssztjService?queryXz'
	      }
	 },
    mixins : [
        "Pages.sc.Scgj"		//输出工具类
    ],
    cssRequires : ['app/pages/sc/template/css/sc_base.css','app/pages/sc/template/css/zxxssztj.css'],
    init : function() {
       this.callService([{key:'rxxl'},{key:'pycc'},{key:'xz'}],function(data){
    	   this.data.d11 = data.rxxl;
    	   this.data.d21 = data.pycc;
    	   this.data.d31 = data.xz;
    	   this.initComponent();
       },this);
    }, 
    initComponent : function() {
    	var page =this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            autoScroll : true,
	            html : this.zjxsszmain,
	            autoShow : true
        });
    	page.on('click',function(event){
    		if ($(event.getTarget()).attr("attr") == 'help') {
				$("#sc_zjxssztj_help").slideToggle();
			}
    	});
        page.on('afterrender',function(){ 
        	this.renderChildComp();
        },this);
        this.setPageComponent(page);
    },
    //渲染各个小组件
    renderChildComp : function(){
    	this.data.d12.data = this.data.d11;
    	this.renderCommonChart('sc_zjxssztj_row11','入学前学历','数量',this.data.d11,'column');
    	this.render("sc_zjxssztj_row12", this.tabletpl, this.data.d12);

    	this.data.d22.data = this.changePiedata2Column(this.data.d21);
    	this.renderPieChart('sc_zjxssztj_row21','在籍学生培养层次','人数',this.data.d21);
    	this.render("sc_zjxssztj_row22", this.tabletpl, this.data.d22);
    	
    	this.data.d32.data = this.data.d31;
    	this.renderCommonChart('sc_zjxssztj_row31','在籍学生学制','数量',this.data.d31,'column','人',true);
    	this.render("sc_zjxssztj_row32", this.tabletpl, this.data.d32);
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
    	//入学前学历
    	d11 : [{field:"未知",name:"入学前学历",value:800},{field:"大专",name:"入学前学历",value:533},
    	       {field:"离校实习",name:"入学前学历",value:28},{field:"高中",name:"入学前学历",value:1647},
    	       {field:"高技",name:"入学前学历",value:44},{field:"初中",name:"入学前学历",value:1138},
    	       {field:"中技",name:"入学前学历",value:1737}] ,
    	d12 : {name : '入学前学历',data : {}},
    	//培养层次
    	d21 : [{y:801,name:"未知"},{y:1825,name:"高级工"},
    	       {y:61,name:"预备技师"},{y:3240,name:"中级工"}],
    	d22 : {name : '培养层次',data : {}},
    	//学制
    	d31 : [{field:"未知",name:"学制",value:793},{field:"2+1+1(3)",name:"学制",value:34},
    	       {field:"1",name:"学制",value:12},{field:"1+1(1)",name:"学制",value:469},
    	       {field:"3",name:"学制",value:1150},{field:"2+1+1(1)",name:"学制",value:1},
    	       {field:"2+1(1)",name:"学制",value:1658},{field:"2+1(2)",name:"学制",value:36},
    	       {field:"1+1+1(1)",name:"学制",value:1},{field:"2+1+1(2)",name:"学制",value:374},
    	       {field:"3+1+1(2)",name:"学制",value:923},{field:"2",name:"学制",value:442},
    	       {field:"3+1(1)",name:"学制",value:10},{field:"3+1+1(1)",name:"学制",value:24}],
    	d32 :{name : '学制',data : {}}
    },
    changePiedata2Column : function(data,name){
    	var result = [];
    	for(var i = 0; i < data.length;i++){
    		var y = data[i].y = parseFloat(data[i].y);
    		var item = {
				name : name || '',
				field : data[i].name,
				value : y
    		};
    		result.push(item);
    	}
    	return result;
    }
});
