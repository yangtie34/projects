# Filed组件使用指南

##1、功能概述

叙述Filed是干什么用的,这的初衷是什么？
     
## 2、结构简介
  
Filed组件暂包含：基础Filed：{@link NS.form.field.BaseField}、复选框：{@link NS.form.field.Checkbox}、
下拉框：{@link NS.form.field.ComboBox}、日期组件：{@link NS.form.field.Date}、显示组件：{@link NS.form.field.Display}、
文件上传组件：{@link NS.form.field.File}、数字组件：{@link NS.form.field.Number}、单选框：{@link NS.form.field.Radio}、
文本域：{@link NS.form.field.TextArea}以及其他扩展组件：下拉树：{@link NS.form.ComboBoxTree}。

## 3、使用简介

###3.1、BaseField简介 
	
BaseField是field的基类，继承于{@link NS.Component}类。
	
###3.2、Checkbox简介 
	
Checkbox是复选框组件，继承于{@link NS.form.field.BaseField}类。
	
    @example
    var checkbox = new NS.form.field.Checkbox({
			fieldLabel : '复选框测试',//文本标签名称
			boxLabel : '复选框1',//显示值
			checked : true,//默认这里被选中
			inputValue : '1'//实际值
		});
    var panel = new NS.container.Panel({
             width : 300,
             height : 100,
             items : [checkbox],
             title : "Panel's Title",
             renderTo: Ext.getBody()
         });
    
###3.3、ComboBox简介 
下拉框组件,默认的fileds为['id','mc'],其中displayField默认为mc,valueField为id。在目前框架中使用者只需传递data即可。
    @example
	var combo = new NS.form.field.ComboBox({
			fieldLabel : '姓名',//文本标签名称
			fields:['value','display'],//域值
			//数据
			data : [ {
				"value" : "1",
				"display" : "Tom"
			}, {
				"value" : "2",
				"display" : "Lily"
			}, {
				"value" : "3",
				"display" : "Hank"
			} ],
			displayField:'display',//显示值
			valueField:'value',//实际值
			queryMode : 'local'//查询模式：local(本地)、remote(远程),默认remote
		});
		var panel = new NS.container.Panel({
             width : 300,
             height : 100,
             items : [combo],
             title : "Panel's Title",
             renderTo: Ext.getBody()
         });
		
###3.4、Date简介 
日期组件,供使用者针对一般日期处理时使用。
	@example
	var date = new NS.form.field.Date({
			fieldLabel : '日期框测试',
			allowBlank : false,
			format : 'Y-m-d',//格式化日期格式
			maxValue : new Date(),//设置最大值
			value : '2010-01-01'//设置默认值
		});
	var panel = new NS.container.Panel({
             width : 300,
             height : 100,
             items : [date],
             title : "Panel's Title",
             renderTo: Ext.getBody()
         });	

###3.5、Display简介 
作为显示组件，仅供用于在只可查看文本信息时使用的。
	@example
	var display = new NS.form.field.Display({
			fieldLabel : '显示组件',
			value : '组件值'
		});
	var panel = new NS.container.Panel({
             width : 300,
             height : 100,
             items : [display],
             title : "Panel's Title",
             renderTo: Ext.getBody()
         });	
###3.6、File简介 
	
###3.7、Number简介 

	@example
	var numField = new NS.form.field.Number({
			fieldLabel : '数字框',//文本标签名称
			allowBlank : false,//不允许为空
			maxValue : 2,//允许的最大值
			minValue:1,//允许的最小值
			step:0.1,//增减幅度
			emptyText : '请输入数字...'//组件空值时的提示信息
		});
	 var panel = new NS.container.Panel({
             width : 300,
             height : 100,
             items : [numField],
             title : "Panel's Title",
             renderTo: Ext.getBody()
         });
    
在panel中可以看到一个数字组件     	
###3.8、Radio简介 
	NOT OK
###3.9、TextArea简介 
	NOT OK
###3.10、ComboBoxTree简介 
	NOT OK


      