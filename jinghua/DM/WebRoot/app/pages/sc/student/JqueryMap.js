/**
 *
 * User: zhangzg
 * Date: 14-8-8
 * Time: 上午10:42
 *
 */
NS.define('Pages.sc.student.JqueryMap',{

    createMap :function(dataStatus,divId,mapType){
        jQuery('#'+divId).vectorMap({
            map: mapType||'china_hen',
            enableZoom: false,// 是否能够放大缩小
            showTooltip: true,
//            values: sample_data,
//            selectedRegion: 'kaifeng',
            backgroundColor:'#FFFFFF',
            color:'#f4f3f0',
//            hoverColor:'#c9dfaf',
            onRegionClick: function(element, code, region)
            {
                var message = 'You clicked "'
                    + region
                    + '" which has the code: '
                    + code.toUpperCase();

                console.log(message);
            }


        });
        this.refreshMap(dataStatus,divId);

    },
    /**
     * 渲染map
     */
    refreshMap : function(dataStatus,divId){
        var me = this;
        //jQuery('#'+this.mkm+'_map').empty();
        $.each([], function (i, items) {

        });
        $.each(dataStatus, function (i, items) {
            if (items.ZS >= 1000) {//动态设定颜色，
                var josnStr = "{'" + items.QXM + "':'#095ba5'}";
                $('#'+divId).vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS >= 500 && items.ZS<1000) {//动态设定颜色，
                var josnStr = "{'" + items.QXM + "':'#136dce'}";
                $('#'+divId).vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS >= 250 && items.ZS<500) {//动态设定颜色，
                var josnStr = "{'" + items.QXM + "':'#6b9ad2'}";
                $('#'+divId).vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS < 250 && items.ZS>=100) {//动态设定颜色，
                var josnStr = "{'" + items.QXM + "':'#a1caff'}";
                $('#'+divId).vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS > 0 && items.ZS<100) {//动态设定颜色，
                var josnStr = "{'" + items.QXM + "':'#daedfe'}";
                $('#'+divId).vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }
            if (items.ZS == 0) {//动态设定颜色，
                var josnStr = "{'" + items.QXM + "':'#f4f3f0'}";
                $('#'+divId).vectorMap('set', 'colors', eval('(' + josnStr + ')'));
            }

        });
        jQuery('#'+divId).bind('labelShow.jqvmap',function(event, label, code){
                $.each(dataStatus, function (i, items) {
                    var str = (items.LBMC == '')?"":("("+items.LBMC+")");
                    if (code == items.QXM) {
                        label.html('<table><tr><th>'+items.MC +'</th></tr><tr><td>'
                            +items.XY+str+"</td><td>"+"   "+items.ZS
                            +'</td></tr><tr><td>占比</td><td>'+items.ZB+'%</td></tr></table>');
                    }
                });
            }
        );
    }
});