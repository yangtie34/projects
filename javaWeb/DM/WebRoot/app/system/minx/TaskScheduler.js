/***
 *任务调度程序
 **/
var numFlag = 100;
NS.define('System.minx.TaskScheduler', {
    tasks:[
        {
            run:function () {
                this.callSingle({key :'ajaxPolling',params : {names : ['test']}},function(data){
                    this.msgEvent.pushMessage(data);
                });
            },
            name : 'ajaxPolling',
            interval:14400000, //执行时间间隔
            args:{}, //传给run方法的参数
            //scope:this, //run方法执行的作用域
            //duration:14400000, //执行时间区间
            repeat:100//重复执行次数
        }
    ],
    startTask:function () {
        var tasks = this.tasks,i = 0,task,len = tasks.length;
        for(;i<len;i++){
            task = tasks[i];
            NS.apply(task,{scope : this});
            NS.util.TaskManager.start(task);
        }
    },
    stopTask : function(name){
        var tasks = this.tasks,i = 0,task,len = tasks.length;
        for(;i<len;i++){
            task = tasks[i];
            if(task.name == name){
                NS.util.TaskManager.stop(task);
                break;
            }
        }
    },
    stopAllTask : function(){
        NS.util.TaskManager.stopAll();
    }
});