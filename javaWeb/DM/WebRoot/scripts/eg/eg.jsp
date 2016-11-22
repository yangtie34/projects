<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<script type="text/javascript">
_EG_BASEPATH_="scripts/eg/";
</script>
<%
boolean src		=request.getParameter("src")!=null	?Boolean.valueOf(request.getParameter("src"))	:true;
%>
String skin     ="ext";
<script type="text/javascript" src="scripts/eg/eg<%=src?".src":""%>.js"></script>
<!-- 重新定义类变量应放在类加载之前 -->
<script type="text/javascript" src="scripts/eg/skins/ext/config.js"></script>
<script type="text/javascript" src="scripts/eg/eg-ui<%=src?".src":""%>.js"></script>
<script type="text/javascript">

EG.onError=function(sMsg, sUrl, sLine){
	var errorMsg = "<div style='text-align:left;font-size:12px'><b>原因</b>:" + sMsg + "<br/>";
	errorMsg += "<b>行数</b>:" + sLine + "<br/>";
	errorMsg += "<b>URL</b>:" + sUrl + "<br/></div>";
	EG.Locker._lock=true;
	EG.Locker.message({message:errorMsg,force:true});
	EG.Locker.setButtons([
   	    {xtype:"button",text:"提交BUG",cls:"eg_button_small",style:"margin-right:3px",click:function(){
   	    	$console.bugPopForm.open();
   	    	$console.bugForm.setData({
   	    		bugContent:"原因:" + sMsg+"\n"+
   	    			"URL:"+sUrl+"\n"+
   	    			"行数:"+sLine+"\n"+
   	    			"模块:"+$console.lastpageCode+"\n"+
   	    			"浏览器:"+navigator.userAgent
   	    	})
   	    }},
   		{xtype:"button",text:"刷新重进",cls:"eg_button_small",style:"margin-right:3px",click:function(){
   			document.location.reload();
   	    }}
   	]);
	EG.css(EG.Locker.dFoot,"text-align:right");
};

/** 设置登录超时处理器 */
EG.MMVC.exClassHandlers["org.nobject.apps.eos.core.exception.UnLoginException"]=function(){
	EG.Locker.message({
		message:"抱歉,您的登录已超时或因系统已重启,请重新登录",closeable:false
	});
	setTimeout(function(){
		document.location.href="";
	},2000);
};
/** 设置权限判断处理器 */
EG.MMVC.exClassHandlers["org.nobject.apps.eos.core.exception.NoPermissionException"]=function(ex){
	var message=ex.exMsg||"当前角色无法访问";
	EG.Locker.message(message);
};
/** 设置默认的处理器 */
EG.MMVC.defExHandler=function(ex){
    EG.Locker._lock=true;
    var m=ex.exMsg?ex.exMsg:"未定义错误，如不能正常使用请联系管理员或提交BUG";
	EG.Locker.message({
		message:m,closeable:true,force:true
	});
};
</script>
<link href="scripts/eg/skins/ext/ui.css" rel="stylesheet" rev="stylesheet" type="text/css" media="screen"/>
