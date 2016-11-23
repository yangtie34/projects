Ext.define('Exp.util.NodeResolve',{
    singleton : true,
    /**
     * 提供一个遍历树的方法
     * @param {Object}root 根节点
     * @param {Function}process  处理函数，提供参数为Node
     * @returns {*}
     */
    traversalTree : function(root,process){
        var iterator = root.children,
            bakIterator = [],
            i = 0,
            len,
            item;
        process.call(this,root);
        while(iterator.length!=0){
            for(i=0, len = iterator.length;i<len;i++){
                item = iterator[i];
                process.call(this,item);
                bakIterator = bakIterator.concat(item.children);
            }
            iterator = bakIterator;
            bakIterator = [];
        }
        return root;
    }
});