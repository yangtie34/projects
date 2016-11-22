<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>家长中心</title>
		<script type="text/javascript">
			if("${is_wechat}"=="true"){
				//定义获取URL参数的方法
				 var thisHref=window.location.href;
				function getUrlParam(name) {
					var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
					var r = window.location.search.substr(1).match(reg); //匹配目标参数
					if (r != null){
						return unescape(r[2]);
					}
					return null;
				}
				var code = getUrlParam("code");
				if(code == null || typeof code =='undefined'){
					window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx14934742aa2b43a4&redirect_uri="+thisHref+
					"&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect"; //返回参数值
				}else{
					window.location.href="${ctx}${url}/"+code;
				} 
			}else{
				document.write("不同过微信的时候，需要在URL中后缀学号");
			}
		 </script>
	</head>
	<body>
	</body>
</html>
