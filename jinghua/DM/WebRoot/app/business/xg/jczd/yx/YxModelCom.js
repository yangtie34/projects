/**
 * 详情页面
 */
NS.define('Business.xg.jczd.yx.YxModelCom', {
    extend : 'Template.Page',
	singleton : true,
    /**
     * 创建学生基本信息组件
     */
    createStuInfoCom : function(data){
        var tpl = new NS.Template( '<h2><span class="zxj-yuxuan_title ">基本信息</span></h2>'+
        							'<table width="100%" border="0" class="zxj-yuxuan_table_gl">' +
                                     '<tr>'+
                                        '<td width="15%">{xm}</td>'+
                                        '<td width="35%">{xh}</td>'+
                                        '<td width="50%">{xbmc}</td>'+
                                     '</tr>'+
                                     '<tr>'+
                                        '<td  colspan="3">'+
                                             '<strong class="zxj-yuxuan_blue">{yxmc}-> {zymc}-> {bjmc}</strong>'+
                                        '</td>'+
                                     '</tr>'+
                                     '<tr>'+
                                        '<td colspan="3">身份证号：{sfzh}</td>'+
                                     '</tr>'+
                                     '<tr>'+
                                         '<td colspan="3">联系电话：{lxdh}</td>'+
                                      '</tr>'+
                                  '</table>' );

        var stuInfoCom = new NS.Component({
            tpl : tpl,
            data : data
        });
        return stuInfoCom;
    },
    /**
     *创建助学金提名信息组件
     */
    createZxjTmInfoCom : function(data){
        var tpl = new NS.Template('<h2><span class="zxj-yuxuan_title ">当前信息</span></h2>'+
        							'<table width="100%" border="0" class="zxj-yuxuan_table_gl" >'+
                                        '<tr>'+
                                             '<td width="20%" class="zxj-yuxuan_td-right">批次[类型]：</td>'+
                                             '<td width="30%"><tpl if="lxmc!=null">{pcmc}[{lxmc}]</tpl></td>'+
                                             '<td width="20%" class="zxj-yuxuan_td-right">金额(元)：</td>'+
                                             '<td width="30%" class="zxj-yuxuan_red">{je}</td>'+
                                       '</tr>'+
                                        '<tr>'+
	                                        '<td width="20%" class="zxj-yuxuan_td-right">学年：</td>'+
	                                        '<td width="30%">{xnmc}</td>'+
	                                        '<td width="20%" class="zxj-yuxuan_td-right">学期：</td>'+
	                                        '<td width="30%">{xqmc}</td>'+
                                        '</tr>'+
                                   '</table>');
        var tmInfoCom  = new NS.Component({
            tpl : tpl,
            data : data
        });
        return tmInfoCom;

    } ,
    
//    /**
//     * 创建审核历史组件
//     */
//    createShHistory : function(data){
//        var tpl = new NS.Template('<table width="100%" border="0" class="zxj-yuxuan_table_gl" >'+
//            '<tpl for="."><tr>'+
//                '<td width="30%" class="zxj-yuxuan_td-right">{HJMC}：</td>'+
//                '<td width="70%" class="zxj-yuxuan_td-left">{SHZTMC}{SHYJ}</td>'+
////                '<td width="40%"><span class="zxj-yuxuan_green">{SHZTMC}</span></td>'+
//            '</tr></tpl>'+
//            '</table>');
//
//        var hisCom  = new NS.Component({
//            tpl : tpl,
//            data : data
//        });
//        return hisCom;
//    }
});
