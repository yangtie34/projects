NS.define('NS.bussiness.CommonBussinessField',{
    singleton : true,
    /**
     *创建一个根据学号查询数据的formsearch组件
     */
    createSearchFieldByXh : function(){

    },
    /**
     *创建一个根据姓名查询数据的formsearch组件
     */
    createSearchFieldByXm : function(){

    },
    /**
     * 创建一个查询学生信息的field组件
     * @return {Object}
     */
    createXsxxSearchField : function(){
        var o = {
            name: '属性名没有给出',
            service: {
                serviceName: 'xg_getStuDataByXhOrXm'
            },
            fields: ['id', 'xm', 'xh', 'bj','yx','zy','dd','zd'],
            pageSize: 10,
            queryParam: 'xm',
            minChars: 1,
            displayField: 'xm',
            valueField: 'id',
            getInnerTpl: function () {
                return '<a class="search-item">姓名：<b>{xm}</b><br>学号：<b>{xh}</b><br>班级：<b>{bj}</b></a>';
            },
            emptyText: '请输入姓名查找...'};
        NS.apply(o,obj);
        return o;
    }
});