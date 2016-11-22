/**
 * @class System.data.TreeMenuModel
 *   初始化和获取 前端菜单树整体数据
 */
NS.define('System.data.TreeMenuModel',{
    /**
     * 构造函数
     */
    constructor : function(treedata){
        this.initData(treedata);
    },
    /**
     * 初始化页面数据
     */
    initData : function(treedata){
        this.treeData = treedata;
        this.initNodeHash();
    },
    /**
     * 初始化节点Hash表
     */
    initNodeHash : function(){
        var me = this;
        var root = Ext.clone(me.treeData);
        var NodeHash = this.NodeHash = {};//创建一个以节点id为键，节点信息为值的HASH表
        var ClnHash = this.PageClassNameHash = {};//创建一个以类名为键，节点信息为值的HASH表
        NodeHash[root.id] = root;
        var iterator = root.children;
        var biter = [];
        while (iterator.length != 0) {
            for (var i = 0, len = iterator.length; i < len; i++) {
                var node = iterator[i];
                if(node){
                   NodeHash[node.id] = node;
                   ClnHash[node.url] = node;
                   biter = biter.concat(node.children);
                }
            }
            iterator = biter;
            biter = [];
        }
    },
    /**
     * 通过id获取树节点数据
     * @param id
     * @return {*}
     */
    getNodeById : function(id){
        return NS.clone(this.NodeHash[id]);
    },
    /**
     * 通过classname获取对应的树节点数据
     * @param classname
     * @return {*}
     */
    getNodeByClassName : function(classname){
        return NS.clone(this.PageClassNameHash[classname]);
    },
    /***
     * 获取叶子节点上按钮的权限信息
     * @param {Number} id 叶子节点id
     * @return {Object} 一个菜单的按钮权限数据
     */
    getButtonPermission : function(id){
        var node = this.NodeHash[id];
        if(node.leaf){
            return node.permiss;
        }
    }
});