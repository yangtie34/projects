TreeData = {
    getRowList: function (rowdata, colIndexlist) {
        var rowlist = this.getColumnsList(rowdata);
        var datalist = this.getRows(rowlist, colIndexlist);
        return datalist;
    },
    /***
     *获取用于遍历表体内容的集合
     */
    getRows: function (rowlist, columnIndexList) {
        var ln = rowlist.length;
        var cil = columnIndexList;
        var cl = cil.length;
        var rownum = this.getRowNum(rowlist);//获取表内容最大行数
        var datalist = new Array(rownum);//设定长度为行数的数组
        for (var i = 0; i < rownum; i++) {//初始化数据数组
            datalist[i] = [];
        }
        for (var i = 0; i < ln; i++) {
            var data = [];
            var innum = 0;
            var array = rowlist[i];
            for (var j = 0, len = array.length; j < len; j++) {
                var obj = array[j];//获取一层的某个对象
                if (obj.items) {//如果其含有子元素，表明其为数据索引
                    datalist[innum].push(obj);
                    innum += obj.colspan;
                } else {
                    for (var k = 0; k < cl; k++) {
                        datalist[innum].push(obj[cil[k].dataIndex]);
                    }
                    innum += 1;//如果是数据的话每次只增加一
                }
            }
        }
        return datalist;
    },
    /**
     获取所有行数
     **/
    getRowNum: function (rowlist) {
        var rownum = 0;
        var array = rowlist[0];
        var ln = array.length;
        for (var i = 0; i < ln; i++) {
            var obj = array[i];
            rownum += obj.colspan;
        }
        return rownum;
    },
    /****
     获取列读取数据索引
     ****/
    getColumnIndexList: function (columnlist) {
        var indexlist = columnlist[columnlist.length - 1];
        var array = [];
        for (var i = 0, len = indexlist.length; i < len; i++) {
            var column = indexlist[i];
            array.push(column.dataIndex);
        }
        return array;
    },
    /***
     * 获取最终生成的根据层级的列的集合
     * @param {} root
     */
    getColumnsList: function (columns) {
        var data = {
            items: columns
        };
        var deep = this.getDeep(data) - 1;//获取表头树的最大深度
        /*****************设置每个节点的深度***************/
        this.setDeep(data);
        /*****************设置所有的叶子节点******************/
        this.setLeaf(data);
        /*******设置每个节点占行数**********/
        for (var i = 0; i < columns.length; i++) {
            this.setRowSpan(columns[i], deep);
        }
        /*******设置每个节点占列数**********/
        this.setColSpan(data);
        /********************/
        var columns = [];//根据深度生成深度表头数据
        for (var i = 1; i <= deep; i++) {
            columns.push(this.getDeepList(data, i));
        }
        return columns;
    },
    /***
     * 设置表头的行展开项
     */
    setColumsRowSpan: function (columns) {
        var data = {
            items: columns
        };
        var deep = this.getDeep(data);// 叶子节点的最大深度

    },
    /***
     * 设置表头跨列长度
     */
    setColSpan: function (root) {
        if (root.leaf) {
            root.colspan = 1;
        } else {
            root.colspan = this.getLeafNumber(root);
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setColSpan(root.items[i]);
            }
        }
    },
    /***************************************************************************
     * 根据跟节点以及树的深度设置每个节点行展开长度
     *
     * @param {}
     *            root
     * @param {}
     *            deep
     */
    setRowSpan: function (root, deep) {
        if (typeof root.items != 'undefined') {
            root.rowspan = 1;
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setRowSpan(root.items[i], deep);
            }
        } else {
            root.rowspan = (deep - root.deep) + 1;
        }
    },
    /** *获得所有叶子节点的数量*** */
    getLeafNumber: function (root) {// 获得一个节点叶子数
        var num = 0;
        if (root.items) {
            for (var i = 0, len = root.items.length; i < len; i++) {
                var node = root.items[i];
                if (node.items) {
                    num += this.getLeafNumber(node);
                } else {
                    num += 1;
                }
            }
        } else {
            num = 1;
        }
        return num;
    },
    /** *获得一棵树的深度*** */
    getDeep: function (root) {
        var deep = 1;
        if (root.items) {
            var maxdeep = 0;
            for (var i = 0, len = root.items.length; i < len; i++) {
                var d = this.getDeep(root.items[i]);
                maxdeep = d > maxdeep ? d : maxdeep;
            }
            deep += maxdeep;
        }
        return deep;
    },
    /***************************************************************************
     * 设置节点的深度
     *
     * @param {}
     *            root
     * @param {}
     *            deep
     */
    setDeep: function (root, dp) {
        var deep = dp || 0;
        root.deep = deep;// 设定当前节点的深度为deep,如果没有深度则为根节点深度为0
        if (root.items) {
            deep += 1;
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setDeep(root.items[i], deep);
            }
        }
    },
    /***************************************************************************
     * 查询深度为n的所有元素集合
     */
    getDeepList: function (root, deep) {
        var deeplist = [];
        if (root.deep < deep) {
            if (root.items) {
                for (var i = 0, len = root.items.length; i < len; i++) {
                    deeplist = deeplist
                        .concat(this.getDeepList(root.items[i], deep));
                }
            }
            return deeplist;
        } else if (root.deep == deep) {
            deeplist.push(root);
            return deeplist;
        } else {
            return deeplist;
        }
    },
    /***************************************************************************
     * 设置当前节点下的所有叶子节点
     *
     * @param {}
     *            root 需要设置是否为叶子节点的所有节点
     */
    setLeaf: function (root) {
        if (root.items) {
            root.leaf = false;
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setLeaf(root.items[i]);
            }
        } else {
            root.leaf = true;
        }
    },
    /***************************************************************************
     * 从根节点，获取叶子节点集合
     *
     * @param {}
     *            root
     */
    getLeafList: function (root) {
        var leaflist = [];
        if (root.leaf) {
            leaflist.push(root);
            return leaflist;
        } else {
            for (var i = 0, len = root.items.length; i < len; i++) {
                leaflist = leaflist.concat(this.getLeafList(root.items[i]));
            }
            return leaflist;
        }
    },
    /***
     * 报表数据转换为table数据格式
     */
    chartDataConvert: function (data) {
        var columns = [
        ];
        var rows = [];
        var model = data.model;
        var bodydata = data.data;
        for (var i = 0, len = model.length; i < len; i++) {
            columns.push({header: model[i], dataIndex: model[i]});
        }
        for (var i = 0, len = bodydata.length; i < len; i++) {
            var cdata = bodydata[i];
            var row = {header: cdata.seriesname};
            delete cdata.seriesname;
            row.items = [cdata];
            rows.push(row);
        }
        return {
            rows: rows,
            columns: columns
        };
    }
};