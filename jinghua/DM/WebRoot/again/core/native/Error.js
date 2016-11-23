/***
 * @class NS.Error
 * 错误报告类
 * @author wangyongtai
 */
NS.Error = (function(){
   var toString = function(err){
	   var msg;
	   if(err){
		  msg = (err.sourceClass||"") +"类:-"+(err.sourceMethod||"")+"方法:-"+(err.msg||'');
	   }
	   return msg||"";
   };
   return {
	 /***
	  * 报告错误
	  *   		   NS.Error.raise({
	  *  			sourceClass : 'ClassName'//报告错误出现的类
	  *  			sourceMethod : 'methodName'//报告错误出现的类的方法
	  *  			msg : '错误信息'//报告错误信息
	  *            })
	  */
	 raise : function(err){
		 var msg = toString(err);
		 throw new Error(msg);
	 }
   };
})();
/**
 * 别名 for {@link NS.Error#raise}
 * @member NS
 * @method error
 * @inheritdoc NS.Error#raise
 */
NS.error = NS.Function.alias(NS.Error,'raise');