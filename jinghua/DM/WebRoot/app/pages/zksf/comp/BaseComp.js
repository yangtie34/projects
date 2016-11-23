NS.define("Pages.zksf.comp.BaseComp",{
	extend : Ext.Component,
	constructor :function(){
		this.callParent(arguments);
	},
	url : this.url || "",
	params : this.params || {},
	queryData:function(){
		var me = this;
		if(Ext.String.trim(me.url).length < 1){
			alert( me.$className + "'s url is wrong.");
			return;
		}
		$.ajax({
			type: 'post',
		    url: me.url ,
		    data : me.params,
		    dataType : "json",
		    beforeSend:me.beforeSend,
		    success: function(data){me.afterLoad(data);},
   	     	async:me.isAsync,
   	     	error : function(){
   	     		alert("error happend ");
   	     	}
		});
	},
	data : {},
	beforeSend : this.beforeLoad || function(data){
		
	},
	afterLoad : this.afterLoad||function(datas){
		this.data = datas;
	},
	isAsync : this.isAsync || false,
	html : "hello ,this is the test comp",
	width:690,
	height: 500,
	border : 1,
	style :{
		borderStyle : "solid",
		borderColor:"#aaa"
	}
});