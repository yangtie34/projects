/**
 * Created by Administrator on 2017/1/11.
 */
module.exports = function(app){
    app.service("service",function () {
        return {
            add : function(a,b){
                return a+b;
            }
        }
    })
}