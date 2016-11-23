/**
 * 日期工具类
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午5:57
 *
 */
Ext.define('Exp.util.DateUtil',{
    singleton:true,
    /**
     * 获取日期字符串。今天、后天、昨天、几天前，几天后。
     * @param AddDayCount
     * @return {String}
     */
    getDateStr : function (dd,AddDayCount) {
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        return this.formatDate(dd);
    },
    /**
     * 获取本周起止时间
     * @return {String}
     */
    getWeekStartEnd : function(now){               //当前日期
        var nowDayOfWeek = now.getDay();         //今天本周的第几天
        var nowDay = now.getDate();              //当前日
        var nowMonth = now.getMonth();           //当前月
        var nowYear = now.getYear();             //当前年
        nowYear += (nowYear < 2000) ? 1900 : 0;  //
        var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
        var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
        return {
            from:this.formatDate(weekStartDate),
            to:this.formatDate(weekEndDate)
        };
    },
    /**
     * 获取上周起止时间
     * @return {String}
     */
    getPreWeekStartEnd : function(){
        var today = new Date();
        var bzstart = this.getWeekStartEnd(today).from;
        var szend = this.getDateStr(new Date(bzstart),-1);
        return this.getWeekStartEnd(new Date(szend));
    },
    /**
     * 格式化日期
     * @param dd
     * @return {String}
     */
    formatDate:function(dd){
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        if(m < 10){
            m = "0" + m;
        }
        if(d < 10){
            d = "0" + d;
        }
        return y+"-"+m+"-"+d;
    }
});