/**
 * 助学金组件创建[zhangyc]
 */
NS.define('Business.xg.jczd.yx.Yx_Com', {
    extend : 'Template.Page',
	singleton : true,
    /**
     * 创建学生基本信息组件
     */
    createStuInfoCom : function(data){
        var tpl = new NS.Template( '<table width="100%" border="0" class="zxj-yuxuan_table_gl">' +
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
     * 创建助学金历史组件
     */
    createZxjHistoryCom : function(data){
       var tpl = new NS.Template('<table width="100%" border="0" class="zxj-yuxuan_table_lishi">'+
                                    '<tr>'+
                                        '<td width="30%" class="zxj-yuxuan_greenbg">获奖名称</td>'+
                                        '<td width="30%" class="zxj-yuxuan_greenbg">金额（元）</td>'+
                                        '<td width="40%" class="zxj-yuxuan_greenbg">学年/学期</td>'+
                                    '</tr>'+
                                    ' <tpl for="."><tr>'+
                                        '<td class="zxj-yuxuan_greenbg">{lxmc}</td>'+
                                         '<td>{je}</td>'+
                                         '<td>{xnmc}{xqmc}</td>'+
                                    '</tr></tpl>'+
                                   '</table>');
        var historyCom = new NS.Component({
            tpl : tpl,
            data : data
        });
        return historyCom;
    },
    /**
     *创建助学金提名信息组件
     */
    createZxjTmInfoCom : function(data){
        var tpl = new NS.Template('<table width="100%" border="0" class="zxj-yuxuan_table_gl" >'+
                                        '<tr>'+
                                             '<td width="30%" class="zxj-yuxuan_td-right">批次[类型]：</td>'+
                                             '<td width="70%" colspan="3"><tpl if="lxmc!=null">{pcmc}[{lxmc}]</tpl></td>'+
                                       '</tr>'+
                                        '<tr>'+
                                            '<td class="zxj-yuxuan_td-right" >学年/学期：</td>'+
                                            '<td colspan="3">{xnmc} {xqmc}</td>'+
                                        '</tr>'+
                                        '<tr>'+
	                                        '<td  class="zxj-yuxuan_td-right">资料齐全[<span class="zxj-yuxuan_red">*</span>]：</td>'+
	                                      '<td>' +
	                                      '<input id="zxjyx-edit-zlsfqq" class="inputdefault" type="checkbox"  <tpl if="zlsfqq==1"> checked="checked"</tpl>  />' +
                                          '<label class="labeldefault zxj-yuxuan_bjleft">是</label>'+
                                            '<td  class="zxj-yuxuan_td-right">金额(元)：</td>'+
                                            '<td>{je}</td>'+
                                         '</tr>'+
//                                        '<tr>'+
////                                             '<td  class="zxj-yuxuan_td-right">资料齐全[<span class="zxj-yuxuan_red">*</span>]：</td>'+
////                                             '<td>' +
//                                            '<input id="zxjyx-edit-zlsfqq" class="inputdefault" type="checkbox"  <tpl if="zlsfqq==1"> checked="checked"</tpl>  />' +
//                                             '<label class="labeldefault zxj-yuxuan_bjleft">是</label>'+
//                                        '</tr>'+
                                        '<tr>'+
                                            '<td  class="zxj-yuxuan_td-right"  valign="top">申请理由：</td>'+
                                            '<td colspan="3"><textarea id="zxj_edit_sqly" class="zxj-yuxuan_textarea zxj-yuxuan_textarea-exp"  >{sqly}</textarea></td>'+
                                        '</tr>'+
                                   '</table>');
        var tmInfoCom  = new NS.Component({
            tpl : tpl,
            data : data
        });
        return tmInfoCom;

    },
    /**
     * 创建审核历史组件
     */
    createShHistory : function(data){
        var tpl = new NS.Template('<table width="100%" border="0" class="zxj-yuxuan_table_gl" >'+
            '<tpl for="."><tr>'+
                '<td width="20%" class="zxj-yuxuan_td-right">{HJMC}：</td>'+
                '<td width="80%" class="zxj-yuxuan_td-left "><span class="zxj-yuxuan_green">{SHZTMC}</span>&nbsp;&nbsp;&nbsp;{SHYJ}</td>'+
//                '<td width="40%"><span class="zxj-yuxuan_green">{SHZTMC}  {SHSJ}</span></td>'+
            '</tr></tpl>'+
            '</table>');

        var hisCom  = new NS.Component({
            tpl : tpl,
            data : data
        });
        return hisCom;

    }

});

