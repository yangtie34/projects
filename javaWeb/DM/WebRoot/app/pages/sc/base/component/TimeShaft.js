/**
 * 时间轴
 * User: Sunwg
 * Date: 14-8-15
 * Time: 下午5:00
 * 
 * 		var shaft = new Exp.chart.TimeShaft({
 * 			data : [{{date : '2012-12-12',html :"hellom,this is the test",picUrl:'app/pages/sc/base/component/images/tpic.png'}}]
 * 		});
 * 
 *		shaft.addItems([{{date : '2012-12-12',html :"hellom,this is the test",picUrl:'app/pages/sc/base/component/images/tpic.png'}}]);
 * 		
 * 		var number = shaft.total;
 */
Ext.define('Exp.chart.TimeShaft',{
    extend : 'Ext.Component',
    margin:0,
    listeners:{
    	readMore : function(){}
    },
    initComponent : function(){
    	this.callParent(arguments);
    	this.tpl = "<div id='"+this.id +"_content'></div><div id='"+this.id +"_more' style='border:1px solid #AAA;margin-top:30px;padding:10px;" +
    			"text-align:center;font-weight:bold;cursor: pointer;'> 点击加载更多...</div>";
    	this.on('afterrender',function(){
    		this.addItems(this.data);
    		this.bindEvent();
    	});
    },
    //data : [{date : '2012-12-12',html :"hellom,this is the test",picUrl:'app/pages/sc/base/component/images/tpic.png'}],
    data : [],
    modelHtml : "<div><table width='100%'><tr><td width='100px' style='vertical-align: top;padding-top: 40px;font-weight: bold;font-size:14px;'><div style='text-align:center;'>{0}</div></td>" +
    		"<td height='300px' width='80px'> <div style='width: 80px; height: 100%; margin-top: 20px; background: url(app/pages/sc/base/component/images/tsbz.png) repeat;'> " +
    		"<div style='height: 80px; width: 80px; padding: 20px; background: url(app/pages/sc/base/component/images/tsb.png) no-repeat;padding:20px;'> " +
    		"<div style='height:40px;width:40px;background-image: url({1});'></div>" +
    		"</div></div></td> " +
    		"<td height='300px'> <div style='position: relative; height: 100%;width: 100%; padding: 10px 0px 5px 15px;'>" +
    		" <div style='position: absolute; top: 30px; left: 1px; height: 15px; width: 18px; background: url(app/pages/sc/base/component/images/tsi.png) no-repeat;'></div>" +
    		" <div style='height: 100%; background-color: #afdcf8; border: 1px solid #000; border-radius: 10px;padding:10px;'>{2}" +
    		" </div></div></td></tr></table> </div>",
    addItems : function(data){
    	var me = this; 
    	me.total += data.length;
    	if(data.length == 0){
    		$("#"+me.id + "_more").hide();
    		return;
    	}else{
    		$("#"+me.id + "_more").show();
    	}
    	//遍历数据
    	for ( var i = 0; i < data.length; i++) {
			var item = data[i];
			var date =item.date || "",
				picUrl = item.picUrl || "app/pages/sc/base/component/images/tpic.png",
				html =item.html || "无内容";
			$("#" + me.id +"_content").append(Ext.String.format(me.modelHtml,date,picUrl,html));
		}
    },
    total : 0,
    bindEvent : function(){
    	var me = this;
    	$("#"+me.id + "_more").click(function(){
    		$("#" + me.id +"_more").html("<div class='loading-indicator'>正在加载....</div>");
    		me.fireEvent("readMore");
    		$("#" + me.id +"_more").html("点击加载更多..");
    	});
    },
    clear : function(){
    	this.total = 0;
    	$("#" + this.id +"_content").html("");
    }
});