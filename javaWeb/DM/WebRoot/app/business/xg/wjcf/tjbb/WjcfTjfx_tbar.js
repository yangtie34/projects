/**
 * 助学金统计分析 tbar
 **/
NS.define('Business.xg.wjcf.tjbb.WjcfTjfx_tbar',{
	/**
     * 初始化tbar[重写]
     */
    getTbar : function(data){
        if(data.queryGridData.success && data.queryGridData.data.length!=0){
            var data = data.queryGridData.data[0];
//            this.rangeArray = [{queryType:'XX',name : '首页'}];
            var queryType = data.queryType;
            switch(queryType){
	            case "END" : queryType="XS"
	                break;
	            case "XS" : queryType="BJ"
	                break;
	            case "BJ" : queryType="ZY"
	                break;
	            case "ZY" : queryType="YX"
	                break;
	            case "YX" : queryType="XX"
	                break;
	        }
            this.rangeArray = [{queryType:queryType,name : '首页'}];
            var tpl = new NS.Template(
            		"<button  class='buttondefault floatRight zxj-tongji_margintop' type='button' id='wjcftj-export' name='wjcftj-export'>导出</button>"+
                "<tpl for='.'> " +
                    "<a  class='zxj-tongji_index' style='text-decoration:none;' href='javascript:void(0);' " +
                         "index = '{[xindex-1]}' " +
                         "queryType='{queryType}' " +
                         "nodeId='{nodeId}'>{name}" +
                    "</a>" +
                    "<span style='color: #000000;'>>></span>" +
                    "</tpl>");
            var indexCom = this.indexCom = new NS.Component({
                tpl : tpl,
                style : {
                    fontSize : '14px'
                },
                padding: '0 0 2 4',
                height : 20,
                data : this.rangeArray
            });
            indexCom.on('click',function(event,htmlElement){
                if(htmlElement.nodeName == 'A'){
                    var el = NS.fly(htmlElement);
                    var queryType = el.getAttribute("queryType");
                    var nodeId = el.getAttribute("nodeId");
                    var index = el.getAttribute("index");
                    this.rangeArray = NS.Array.slice(this.rangeArray,0,parseInt(index));
                    if(queryType == 'null' || queryType==null){
                        queryType='XX';
                    }
                    if(nodeId=='null' || nodeId==null){
                        nodeId='';
                    }
                    this.loadGrid(queryType,nodeId);
                }
                if(htmlElement.type == 'BUTTON' && htmlElement.name == 'wjcftj-export'){

                }
                //改变显示列表字段
                this.setColumnText(queryType);
            },this);

            return indexCom;
        }
    },
    /**
     * 加载grid数据
     */
    loadGrid : function(queryTpye,nodeId){
//        if(nodeId != ''){
//            this.pageParams.queryStatus = this.rangeArray;
//        }
        this.pageParams.nodeId = nodeId;
        this.pageParams.queryType = queryTpye;
        this.pageParams.lsmk = this.lsmk;
        this.grid.load(this.pageParams);
    }
});