NS.define('Business.xg.wjcf.tjbb.WjcfTjfx_tools',{
	/**
	 * 动态创建grid显示列
	 */
	getColumnArray : function(){
		var cArray = new Array();
		cArray.push("dwmc");
		cArray.push("zrs");
		var wjData = this.tplData.cflxData;
		for(var a in wjData){
			cArray.push(String(wjData[a].ID));
		}
		cArray.push("queryType");
		cArray.push("nodeId");
		cArray.push("id");
		cArray.push("xh");
		cArray.push("xm");
		cArray.push("yxmc");
		cArray.push("zymc");
		cArray.push("bjmc");
		cArray.push("wjlx");
		cArray.push("cflx");
		cArray.push("cfzt");
		return cArray;
	},
	/**
	 * 动态创建grid显示列名称
	 */
	getTextArray : function(){
		var mArray = new Array();
		mArray.push("学校");
		mArray.push("在校人数");
		var wjData = this.tplData.cflxData;
		for(var b in wjData){
			mArray.push(wjData[b].MC);
		}
		mArray.push("查询类型");
		mArray.push("节点ID");
		mArray.push("学生ID");
		mArray.push("学号");
		mArray.push("姓名");
		mArray.push("院系");
		mArray.push("专业");
		mArray.push("班级");
		mArray.push("违纪类型");
		mArray.push("处分类型");
		mArray.push("处分状态");
		return mArray;
	},
	/**
	 * 动态创建grid列宽度
	 */
	getWidthArray : function(){
		var wArray = new Array();
		wArray.push(160);
		wArray.push(100);
		var wjData = this.tplData.cflxData;
		for(var i=0;i<wjData.length;i++){
			wArray.push(100);
		}
		wArray.push(100);
		wArray.push(100);
		wArray.push(100);
		wArray.push(100);
		wArray.push(100);
		wArray.push(120);
		wArray.push(120);
		wArray.push(120);
		wArray.push(100);
		wArray.push(100);
		wArray.push(100);
		return wArray;
	},
	getHiddenArray : function(){
		var hArray = new Array();
		hArray.push(false);
		hArray.push(false);
		var wjData = this.tplData.cflxData;
		for(var i=0;i<wjData.length;i++){
			hArray.push(false);
		}
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		hArray.push(true);
		return hArray;
	},
	getTjColumn : function(){
		var tArray = new Array();
		tArray.push("dwmc");
		tArray.push("zrs");
		var wjData = this.tplData.cflxData;
		for(var a in wjData){
			tArray.push(wjData[a].ID);
		}
		return tArray;
	},
	getXsColumn : function(){
		var xArray = new Array();
		xArray.push("queryType");
		xArray.push("nodeId");
		xArray.push("id");
		xArray.push("xh");
		xArray.push("xm");
		xArray.push("yxmc");
		xArray.push("zymc");
		xArray.push("bjmc");
		xArray.push("wjlx");
		xArray.push("cflx");
		xArray.push("cfzt");
		return xArray;
	},
	getColor : function(){
		var colorArray = new Array();
		colorArray.push("AFD8F8");
		colorArray.push("F6BD0F");
		colorArray.push("FF00FF");
		colorArray.push("FF0099");
		colorArray.push("CC0033");
		colorArray.push("9900FF");
		colorArray.push("CC6600");
		colorArray.push("66CC00");
		colorArray.push("6633FF");
		colorArray.push("009933");
		
		return colorArray;
	},
	/**
	 * 初始化选择区域页面
	 */
	initSelectJzmc : function(){
		var mc = "";
		switch(this.lsmk){
        case "TbXgWjcfCflx" : mc="处分类型";
            break;
        case "TbXgWjcfWjlx" : mc="违纪类型";
            break;
		}
		return mc;
	},
});