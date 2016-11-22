/**
 * 教学评估-学校基本情况-表2
 * sunwg
 * 2015-01-07
 */
NS.define('Pages.sc.jxpg.Xxjbqk2',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学校基本情况-表2',
            pageHelpInfo:'学校的基本情况信息，包含校址、联系方式、附设教学班等信息。'
        }
    }),
    modelConfig: {
        serviceConfig: {
        	xxjbqk : "jxpgXxjbxxService?queryXxjbqk", //查询学校基本情况
        	saveItems : "jxpgXxjbxxService?saveXxjbqk", //保存学校基本情况列表
        }
    },
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/tpl/xxjbqk2.html'}],
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/sc/jxpg/tpl/xxjbqk.css'],
	mixins:['Pages.zksf.comp.Scgj'],
	requires:[],
	init: function () {
	    var comps = this.createComps();
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:[this.pageTitle,comps],
			style : {
				"min-width" : "1000px"
			}
	    });
	    container.on("afterrender",function(){
	    	this.fillCompData(1);
	    	this.bindEvents();
	    },this);
	    this.setPageComponent(container);
	},
	xncomb : new Ext.form.field.ComboBox({
		fieldLabel: '学年',
	    queryMode: 'local',
	    store:{
	    	fields: ['value', 'dis'],
    	    data :  []
	    },
	    displayField: 'dis',
	    valueField: 'value',
	    width : 300,
	    value : 1,
		columnWidth : 1/2,
	    margin : 0
	}),
	
	generateXn : function(){
		var today = new Date();
		var curYear = parseInt(today.getFullYear());
		curYear = (today.getMonth() < 7) ? (curYear - 1) : curYear;
	    var result = [];
	    for(var i = 0;i< 5;i++){
	    	var curXn = (curYear - i) + '-' + (curYear -i + 1);
	    	var item = {dis : curXn+"学年", value : curXn};
	    	result.push(item);
	    }
	    return result;
	},
	btntool : new Ext.Component({
		tpl : "<input name='autofill' type='button' value='自动填充'  style='padding:3px 5px;border-radius: 5px;margin: 0px 0px 0px 100px;'/>" +
				"<input name='save' type='button' value='保存' style='padding:3px 5px;border-radius: 5px;margin: 0px 25px;'/>" +
				"<span style='color:#5AA155;'>双击单元格内容可以修改</span>",
		data:{}
	}),
	mainComp : {},
    createComps : function(){
    	var me = this;
    	me.mainComp = new Ext.Component({
    		tpl : me.maintpl.tpl,
    		data:{}
    	});
    	var xn = new NS.container.Container({
    		layout : "column",
            style:{
                "margin-bottom" : "10px"
            },
            cls : "div-border",
            items : [me.xncomb,me.btntool]
        });
		var result = new NS.container.Container({
            style:{
                "margin-bottom" : "10px"
            },
            items : [xn,me.mainComp]
        });
		return result;
	},
	fillCompData : function(num){
		var me = this;
		var xndata = me.generateXn();
		me.xncomb.getStore().loadData(xndata,false);
		me.xncomb.setValue(xndata[0].value);
		me.refreshComp(me.xncomb.getValue());
	},
	refreshComp : function(xn){
		var me = this;
		var pageCode = "Pages.sc.jxpg.Xxjbqk2";
		$("#"+me.mainComp.id).html(me.loadingHtml);
		me.callService({key:"xxjbqk",params:{xn : xn,pageCode : pageCode}},function(data){
			me.mainComp.update(data.xxjbqk);
		});
	},
	bindEvents : function(){
		var me = this;
		me.xncomb.on("select",function(){
			me.refreshComp(me.xncomb.getValue());
		});
		$("#"+me.mainComp.id).dblclick(function(evt){
			var tar = evt.target;
			if($(tar)[0].tagName == "SPAN" && $(tar).attr("type") == "itemvalue"){
				$(tar).hide();
				$(tar).prev().html("<input type='text' code='"+$(tar).attr("code")+
							"' value='"+$(tar).html()+"' name='"+$(tar).attr("name")+
							"' style='width:80%;height:26px;margin:5px;'>");
			}
		});
		
		$("#"+me.btntool.id+" input[name=save]") .click(function(evt){
			var inputs = $("#"+me.mainComp.id).find("input[type=text]");
			var items = [];
			for(var i=0;i<inputs.length;i++){
				var input = inputs[i];
				var itemName = $(input).attr("name");
				var itemCode = $(input).attr("code");
				var itemValue = $(input).val();
				if(itemCode != null && itemCode != ""){
					items.push({itemCode : itemCode,itemValue : itemValue,itemName:itemName});
				}
			}
			me.callService({key:"saveItems",
						 params:{
							 xn : me.xncomb.getValue(),
							 pageCode : "Pages.sc.jxpg.Xxjbqk2",
							 pageName : "学校基本情况-表2",
							 items:items
						 }},function(data){
				me.mainComp.update(data.saveItems);
			});
		});
		
		$("#"+me.btntool.id+" input[name=autofill]") .click(function(evt){
			
			// 调用自动填充数据接口，有待建设......
		
		});
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});