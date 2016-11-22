/**
 * 教学评估-学校基本情况
 * sunwg
 * 2015-01-07
 */
NS.define('Pages.sc.jxpg.exam.Examp',{
	extend:'Template.Page',
	pageTitle : new Exp.component.PageTitle({
        data:{
            pageName:'学生基本情况',
            pageHelpInfo:'例子：以学生基本情况为例展示二维表的信息展示。'
        }
    }),
    tableName : "TB_JXPG_EXAMPLE", // 对应数据库的表名
    beforeSaveInvokeService : "",  // 保存数据之前需要调用的service
    afterSaveInvokeService : "",   // 保存数据之后需要调用的service
    ignoreXn : false, 			   // 是否忽略学年
    modelConfig: {
        serviceConfig: {
        	query : "jxpgService?queryGridContent", //查询页面数据
        	save : "jxpgService?saveInputItems" //保存修改的数据
        }
    },
    tplRequires : [{fieldname : 'maintpl',path : 'app/pages/sc/jxpg/exam/tpl/xxjbqk.html'}],  //网页模板
	cssRequires : ['app/pages/zksf/css/base.css','app/pages/sc/jxpg/tpl/xxjbqk.css'],
	mixins:['Pages.zksf.comp.Scgj'], // chart图渲染工具类
	requires:[],
	init: function () {
	    var comps = this.createComps();
	    var selfComps = this.createSelfComps();
	    var allComp = [];
	    if(selfComps != null){
	    	allComp = [this.pageTitle,comps,selfComps];
	    }else{
	    	allComp = [this.pageTitle,comps];
	    }
	    var container = new NS.container.Container({
	        padding:20,
	        autoScroll:true,
	        items:allComp,
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
	createSelfComps : function(){
		return null;
	},
	renderCharts : function(data){
		
	},
	xncomb : null,
	getXnComp : function(){
		return new Ext.form.field.ComboBox({
			fieldLabel: '学年',
			labelWidth : 40,
		    queryMode: 'local',
		    store:{
		    	fields: ['value', 'dis'],
	    	    data :  []
		    },
		    displayField: 'dis',
		    valueField: 'value',
		    width : 160,
		    value : 1,
			columnWidth : 1/2,
		    margin : "2px 30px 0px 0px"
		});
	},
	generateXn : function(){
		var today = new Date();
		var curYear = parseInt(today.getFullYear());
		curYear = (today.getMonth() < 7) ? (curYear - 1) : curYear;
	    var result = [];
	    for(var i = 0;i< 1;i++){
	    	var curXn = (curYear - i) + '-' + (curYear -i + 1);
	    	var item = {dis : curXn+"学年", value : curXn};
	    	result.push(item);
	    }
	    return result;
	},
	btntool : null,
	getBtntool : function(){
		var btnhtml = "",
			endhtml = "。";
		if(this.hasFun("mn")){
			btnhtml = "<input name='moni' type='button' value='模拟' style='padding:3px 5px;border-radius: 5px;margin: 0px 30px 0px 10px;'/>";
			endhtml = "。双击红色单元格可修改数据,点击保存或者回车可模拟数据演示。";
		}
		if (this.hasFun("bc")) {
			btnhtml = "<input name='save' type='button' value='保存' style='padding:3px 5px;border-radius: 5px;margin: 0px 30px 0px 10px;'/>";
			endhtml = "。双击红色单元格可修改数据,点击保存或者回车可保存数据。";
		}
		
		
		return new Ext.Component({
			tpl : btnhtml +
					"<span style='color:#666;'><span style='color:#0DBE00;'>绿色</span>为采集数据，" +
					"<span style='color:#E92929;'>红色</span>为上报数据，" +
					"<span style='color:#DFC400;'>黄色</span>为系统计算数据"+endhtml+"</span>",
			data:{}
		});
	},
	mainComp : {},
    createComps : function(){
    	var me = this;
    	me.mainComp = new Ext.Component({
    		tpl : me.maintpl.tpl,
    		data:{}
    	});
    	me.xncomb = me.getXnComp();
    	me.btntool = me.getBtntool();
    	var xnitems = [];
    	if(me.ignoreXn){
    		xnitems= [me.btntool];
    	}else{
    		xnitems= [me.xncomb,me.btntool];
    	}
    	var xn = new NS.container.Container({
    		layout : "column",
            style:{
                "margin-bottom" : "10px"
            },
            cls : "div-border",
            items : xnitems
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
		$("#"+me.mainComp.id).html(me.loadingHtml);
		me.callService({key:"query",params:{xn : xn, ignoreXn : me.ignoreXn,tableName : me.tableName}},function(data){
			var renderData = {
					xn : xn,
					items : data.query
			};
			me.mainComp.update(renderData);
			me.renderCharts(data.query);
		});
	},
	bindEvents : function(){
		var me = this;
		me.xncomb.on("select",function(){
			me.refreshComp(me.xncomb.getValue());
		});
		$("#"+me.mainComp.id).dblclick(function(evt){
			var tar = evt.target;
			var editAble = (me.hasFun("mn") || me.hasFun("bc"));
			if(editAble && $(tar)[0].tagName == "SPAN" && $(tar).attr("type") == "itemvalue"){
				$(tar).hide();
				var $input = $("<input type=\"text\"/>").attr("code",$(tar).attr("code"))
														  .attr("type","text")
														  .val($(tar).html())
														  .attr("rowid",$(tar).attr("rowid"))
														  .attr("style","width:90%;height:26px;margin:5px;").appendTo($(tar).next());
				$($input).appendTo($(tar).next());
				$input.focus();
				var reg = null;
				var err_msg = "";
				if ($(tar).attr("isnum") == 'float') {
					reg = new RegExp("^-?(\\d+)?(\\.)?(\\d+)?$");
					err_msg = "请输入数字";
				}else if ($(tar).attr("isnum") == 'int') {
					reg = new RegExp("^-?(\\d+)?$");
					err_msg = "请输入整数";
				}
				$input.on("keydown",function(evt){
					if (reg!=null && reg.test($input.val())) {
						$input.oldval = $input.val();
					}else{
						$input.oldval = "";
					}
				});
				
				function check(val){
					var reg = null;
					if ($(tar).attr("isnum") == 'float') {
						reg = new RegExp("^(-?\\d+)?(\\.\\d+)?$");
						err_msg = "请输入正确数字";
					}else if ($(tar).attr("isnum") == 'int') {
						reg = new RegExp("^(-?\\d+)?$");
						err_msg = "请输入正确整数";
					}
					if(reg == null || reg.test(val)){
						return true;
					}else{
						return false;
					}
				}
				$input.on("blur",function(evt){
					if (!check($input.val())) {
						alert(err_msg);
						$input.val("");
					}
				});
				
				$input.on("keyup",function(evt){
					if(evt.keyCode == 13){
						if (check($input.val())) {
							me.saveInputVals();
						}else{
							alert(err_msg);
						}
						
					}
					if(reg != null && $input.val()!=""){
						if(!reg.test($input.val())){
							alert(err_msg);
							$input.val($input.oldval);
							return;
						}
					}
				});
				
			}
		});
		
		$("#"+me.btntool.id+" input[name=save]") .click(function(evt){
			me.saveInputVals();
		});
		$("#"+me.btntool.id+" input[name=moni]") .click(function(evt){
			me.saveInputVals();
		});
		
		$("#"+me.btntool.id+" input[name=autofill]") .click(function(evt){
			
			// 调用自动填充数据接口，有待建设......
		
		});
	},
	/* 保存修改的数据 */ 
	saveInputVals : function(){
		var me = this;
		var inputs = $("#"+me.mainComp.id).find("input[type=text]");
		var items = [];
		for(var i=0;i<inputs.length;i++){
			var input = inputs[i];
			var rowid = $(input).attr("rowid");
			var itemCode = $(input).attr("code");
			var itemValue = $(input).val();
			if(itemCode != null && itemCode != ""){
				items.push({rowid : rowid,itemValue : itemValue,itemCode:itemCode});
			}
		}
		var method = "";
		if(me.hasFun("mn")){
			method = "moni";
		}
		if(me.hasFun("bc")){
			method = "save";
		}
		if(items.length > 0){
			me.callService({key:"save", params:{
				 xn : me.xncomb.getValue(),
				 tableName : me.tableName,
				 ignoreXn : me.ignoreXn,
				 afterSaveInvokeService : me.afterSaveInvokeService,
				 beforeSaveInvokeService : me.beforeSaveInvokeService,
				 items:items,
				 method : method
			 }},function(data)
			 {
				 var result = data.save;
				 if(result.success == "false"){
					 alert(result.error_message);
				 }else{
					 var renderData = {
						xn : me.xncomb.getValue(),
						items : data.save.items
				 	 };
					 me.mainComp.update(renderData);
					 me.renderCharts(data.save.items);
				 }
			});
		}else{
			alert("您未更改任何数据！");
		}
	},
	loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
	noDataHtml : "<span style='color : red;'>没有数据</span>"
});