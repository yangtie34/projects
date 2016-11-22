NS.define('Pages.permiss.menu.MenuManager',{
   singleton : true,
   successInfo : function(config){
       var successMsg = config.successMsg,failureMsg = confg.failureMsg,flag = config.flag;
       if(flag){
           NS.Msg.info(successMsg);
       }else{
           NS.Msg.info(failureMsg);
       }
   }
});