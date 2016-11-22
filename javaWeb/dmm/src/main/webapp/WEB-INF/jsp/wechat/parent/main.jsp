<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<title>家长中心</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/wechat/style1.css?v=1.2" />
</head>
<body>
<div class="index">
  <div class="index_title">${stu.name_ }</div>
	  <c:if test="${is_wechat eq true }">
	  	<div id="menudiv">
	  	 	<input type="button" onclick="btn()" id="btn" value=" " name="btn">
		  	<div id="menudiv2">
		  	 	<ul id="menuul">
		  	 		<li><a href="${ctx}/wechat/parent/pwd">修改密码</a></li>
		  	 		<li><a href="${ctx}/wechat/parent/loginout">退出</a></li>
		  	 	</ul>
	     	</div>
	  	 </div>
	  </c:if>
  </div>
  
  <div class="in_content">
  <c:set var="stuid" value="${'/'}${stu.no_ }"></c:set>   
  <c:set var="iswechat" value="${'parent/'}${is_wechat }"></c:set> 
   		<c:forEach items="${menus }" var="menu" varStatus="status">
   			<div class="module_box">
   				<c:if test="${menu.name_ eq '预警信息' && warncount !=0 }">
   					<span class="xiaoxi">${warncount} </span>
   				</c:if>
   				
   				<h4 class="module0${status.index+1 }"  style="border-radius:10px;">
               		<a href="${ctx}${fn:replace(menu.url_,'parent',iswechat)}${is_wechat eq true ?'':stuid }">${menu.name_ }</a>
            	</h4>
   			</div>
		</c:forEach>
  </div>

<!--尾部线条-->
<p class="login-hr-line">
	<span class="login-hr-icon"></span>
</p>
<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var btn= function (){
	$("#menuul").toggle(100);
}
</script>
  
</body>
</html>