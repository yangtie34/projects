/**
 * 任务调度管理
 * @author wangyt
 * @class  NS.util.TaskManager
 */
NS.define('NS.util.TaskManager',{
    singleton : true,
    constructor : function(){
        this.component = Ext.TaskManager;
    },
    /**
     * 开启任务
     *
     *      var task = {
     *          run : function(){console.log(11111)},
     *          interval : 1000,//执行时间间隔
     *          args : {num:121},//传给run方法的参数
     *          scope : this,//run方法执行的作用域
     *          duration : 1000000,//执行时间区间
     *          repeat : 123//重复执行次数
     *      };
     *      NS.util.TaskManager.start(task);
     *
     * @param {Object} task
     */
    start : function(task){
        this.component.start(task);
    },
    /**
     * 停止运行任务
     *
     *      var task = {
     *          run : function(){console.log(11111)},
     *          interval : 1000,//执行时间间隔
     *          args : {num:121},//传给run方法的参数
     *          scope : this,//run方法执行的作用域
     *          duration : 1000000,//执行时间区间
     *          repeat : 123//重复执行次数
     *      };
     *      NS.util.TaskManager.start(task);
     *      ....
     *      NS.util.TaskManager.stop(task);
     *
     * @param {Object} task
     */
    stop : function(task){
        this.component.stop(task);
    },
    /***
     * 停止运行所有任务
     */
    stopAll : function(){
        this.component.stopAll();
    }
});
