<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<title>我的大学生活全景图片</title>
</head>
</head>
<body>
<div class="jiathis_style_32x32">
	<a class="jiathis_button_qzone"></a>
	<a class="jiathis_button_tsina"></a>
	<a class="jiathis_button_tqq"></a>
	<a class="jiathis_button_weixin"></a>
	<a class="jiathis_button_renren"></a>
	<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
	<a class="jiathis_counter_style"></a>
</div>
<img alt="大学生活" src="${img }">
<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
/* var jiathis_config = {
	    boldNum:0,
	    siteNum:7,
	    showClose:false,
	    sm:"t163,kaixin001,renren,douban,tsina,tqq,tsohu",
	    imageUrl:"http://v2.jiathis.com/code/images/r5.gif",
	    imageWidth:26,
	    marginTop:150,
	    url:"",
	    title:"自定义TITLE #微博话题#",
	    summary:"分享的文本摘要",
	    pic:"${img }",
	    data_track_clickback:true,
	    appkey:{
	        "tsina":"您网站的新浪微博APPKEY",
	        "tqq":"您网站的腾讯微博APPKEY",
	         "tpeople":"您网站的人民微博APPKEY"
	    },
	    "shortUrl":false
	} */
</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
</body>
</html>