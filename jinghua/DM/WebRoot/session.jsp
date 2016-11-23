<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.tif-cont {
	color:#FF6600;
	font:bold 16px 'Microsoft YaHei';
	width:460px;
	left:50%;
	margin-left:-230px;
	height:200px;
	top:50%;
	margin-top:-100px;
	position:absolute;
	background:url(css/images/tic-bg.gif) left top no-repeat;
	padding-left:150px;
	padding-top:20px;
}
.tif-info {
	color:#FF6600;
	font:bold 28px 'Microsoft YaHei';
	text-shadow:2px 2px 1px  #ccc;
}
.tif-cha {
	color:#B3B3B3;
	font:bold 24px 'Microsoft YaHei';
	margin-top:10px;
}
</style>
</head>
<body>
<div class="tif-cont"> 
<div class="tif-info">登录会话失效</div>
<div class="tif-cha" id="content">3秒后跳转到登录页面……</div>
</div>
<script type="text/javascript">
	var i=2;
	setInterval(function(){
		document.getElementById('content').innerHTML = i+"秒后跳转到登录页面……";
		if(i>0)i--;
	},1000);
    setInterval(function(){
       location.href = "login2.jsp"
    },200)
</script>
</body>
</html>