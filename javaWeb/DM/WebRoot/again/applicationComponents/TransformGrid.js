/**
 * @class NS.grid.TransformGrid
 * @extends NS.Component
 * 无样式表格
 */
NS.define('NS.grid.TransformGrid',{
	extend:'NS.Component',
	initComponent:function(cfg){
		
		//1、基础的表格搭建--OK
		//2、数据的转换解析-->表头的转换和数据的转换:由数据抓变换器得到，再顺序执行，要支持添加移除？！
		//3、事件控制（暂时不做）
		//4、样式控制--O_O
		 var header = cfg.fields;//NS.util.DataConverter.entitysToStandards(cfg.fields);
		 var headerStr = '';
		 var tbodyStr = '';
		 var fnObj = {};
		 for(var i=0,len=header.length;i<len;i++){
			 //顺序暂时不写
			 headerStr+= '<td>'+header[i].nameCh+'</td>';
			 tbodyStr+='<td>{[this.'+header[i].name+'(values.'+header[i].name+')]}</td>';
			 fnObj[header[i].name] = function(value){
				 return value;
			 };
		 }
		 var obj = cfg.format;
		 Ext.apply(fnObj,obj);//有些许问题+","的产生
		 delete cfg.format;
		 var tpl = new Ext.XTemplate('<table class="transformgrid-base">',
			       '<tr>',
			      		headerStr,
		           '</tr>',
		       '<tpl for=".">',
		            '<tr>',
		            	tbodyStr,
		             '</tr>',
		       '</tpl>',
		    '</table>',fnObj);
		if(!cfg.tpl) cfg.tpl = tpl;
		this.component = Ext.create('Ext.Component',cfg);
	},
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			tpl:true,
			padding:true,
			fields:true,
			format:true,
			data:true
		});
	}
});