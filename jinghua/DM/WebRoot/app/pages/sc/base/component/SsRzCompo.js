/**
 * 宿舍入住统计组件
 * User: zhangzg
 * Date: 14-8-13
 * Time: 下午2:14
 *
 */
Ext.define('Exp.component.SsRzCompo',{
    extend:'Ext.Component',
    tpl : '<div class="rztj-titname">'+
    '<div class="student-sta-ti-title ">{MC}</div>'+
    '<div style=" padding:0 10px; ">'+
    '<div  class="student-sta-left-width">'+
    '<div class="student-sta-ti" >'+
    '<fieldset>'+
    '<legend>容量</legend>'+
    '<p style="text-align: center;">层 <span class="student-sta-count-green">{lcs} </span>房间 ' +
    '<span  class="student-sta-count-green">{fjs} </span>床位 ' +
    '<span  class="student-sta-count-green">{cws} </span></p>'+
    '</fieldset>'+
    '</div>'+
    '<div class="student-sta-ti"  >'+
    '<fieldset>'+
    '<legend>入住率</legend>'+
    '<p style="text-align: center;"><span  class="student-sta-count-green">{rzl}%</span>，空床位<span  class="student-sta-count-green"> {kcw} </span></p>'+
    '</fieldset>'+
    '</div>'+
    '<div class="student-sta-ti" >'+
    '<fieldset>'+
    '<legend>住宿标准</legend>'+
    '<p style="text-align: center;"><span  class="student-sta-count-green"><tpl if="zsbz.length == 0">--元/年<tpl else>' +
        '<tpl for="zsbz">{MC}元/年{[xindex==xcount?"":","]}</tpl></tpl></p>'+
    '</fieldset>'+
    '</div>'+
    '</div>'+
    '<div class="student-sta-right-width">'+
    '<div class="student-sta-ti-border"  >'+
    '<h3>{xy}</h3>'+
    '<p><span>居多,占总人数的 {rszb}%</span></p>'+
    '</div>'+
    '<div class="student-sta-ti"  style="margin-left:15px;" >'+
    '<fieldset>'+
    '<legend>住宿性别</legend>'+
    '<div style="width:130px; margin:0 auto;text-align: center">'+
    '<tpl if="nan.count &gt; 1"> <div class="student-sta-count-blue"><img style="float:left" src="app/pages/sc/base/component/images/boy.png"/> <span style="float: right;">{nan.count} 人</span><br>'+
    '<span style="float: right;">{nan.zb}%</span> </div> </tpl>'+
    '<div style="clear:both;"></div>'+
    '<tpl if="nv.count &gt; 1"> <div class="student-sta-count-red"> <img  style="float:left" src="app/pages/sc/base/component/images/girl.png"/> <span style="float: right;">{nv.count} 人</span><br>'+
    '<span style="float: right;">{nv.zb}%</span> </div> </tpl>'+
    '</div>'+
    '</fieldset>'+
    '</div>'+
    '</div>'+
    '</div>'+
    '<div style="clear:both;"></div>'+
    '<div style="text-align:right;font-size:12px;"  class="student-sta-green"><b> {ssllx}<b> </div>'+
    '</div>'
});