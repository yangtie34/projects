app.service("marketService",['httpService',function(http){
    return {   
    	 //查询发布及已售商品数
         queryCommodityNums : function(){
        	return http.post({
                url : "social/market/queryCommodityNums",
                data : []
            })
        },
        //查询商品类型
        queryCommodityType : function(){
        	return http.post({
                url : "social/market/queryCommodityType",
                data : []
            })
        },
        //查询所有商品
        queryAllCommodity : function(page,keyword,type,issold){
        	return http.post({
                url : "social/market/queryAllCommodity",
                data : {
                	pagesize : page.pagesize,
					curpage : page.curpage,
					keyword : keyword,
					type_code : type,
					flag : issold
                }
            })
        },
        //查询商品详情
        queryCommodityDetails : function(commodityId){
        	return http.post({
                url : "social/market/queryCommodityDetails",
                data : {commodity_id:commodityId}
            })
        },
        //查询我发布的商品
        queryMyCommoditys : function(){
        	return http.post({
                url : "social/market/queryMyCommoditys",
                data : []
            })
        },
        //修改商品状态
        modifyCommodityState : function(id,is_sold){
        	return http.post({
                url : "social/market/modifyCommodityState",
                data : {commodity_id:id,is_sold:is_sold}
            })
        },
        //保存商品
        saveCommodity : function(commodity){
        	return http.post({
                url : "social/market/saveCommodity",
                data : commodity
            })
        }
       
    }
}]);
