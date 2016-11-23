NS.define('System.data.MenuHeaderModel',{
    /**
     * 构造函数
     */
     constructor : function(data){
         this.initData(data);
     },
    /**
     * 初始化数据
     */
     initData : function(data){
        this.menuHeaderData = data;
        this.initMenuHeader();
     },
    /**
     * 初始化帮助信息
     */
    initMenuHeader : function(){
        var data = this.menuHeaderData;
        var hash = this.MenuHeaderHash = {};
        for(var i=0,len=data.length;i<len;s++){
            var d = data[i];
            hash[d.cd_id] = d;
        }
    },
    /**
     * 根据节点id获取菜单顶部信息
     * @param {Number} id
     * @return {Object}
     */
    getHeaderById : function(id){
        return this.MenuHeaderHash[id];
    }
});