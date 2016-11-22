<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/wechat/style2.css" />
	<title>${class_name }班—晚寝学生</title>
</head>
<body>
<div class="index">
  <div class="index_title">${class_name }班—晚寝学生</div>
  <input type="button" value=" " id="back" onClick="javascript:window.location.href='${ctx}/wechat/teacher/instructor/web/classwarn/latedorm/${tea.no_}' ">
</div>
<div class="body_main">
	<c:if test="${empty lateStu }">
		<div class="fd-content">
		    <div class="fd-border">
		      <p class="fd-p">没有晚寝晚归学生</p>
		    </div>
		  </div>
	</c:if>
	<c:forEach items="${lateStu }" var="stu">
	 <div class="fd-content">
	    <div class="fd-border${stu.sex_code=='01'?'-boy':'-girl'}">
	      <p class="fd-p ${stu.sex_code=='01'?'fd-boy-icon':'fd-girl-icon'}">${stu.stu_name}<span class="fd-imp">(${stu.stu_id})</span></p>
	      <hr class="fd-hr${stu.sex_code=='01'?'-boy':'-girl'} " />
	      <p class="fd-p"> 晚归时间：${stu.lateTimeFormat} </p>
	      <p class="fd-p"> 刷卡地点：${stu.address} </p>
	    </div>
	  </div>
	</c:forEach>
   
</div>
<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
//TODO 
var showhideP=function(t){
	var p=$(t).parent().next();
	if(p.css("display")=="none"){
		$(t).prev().html("▼");
		p.show(300);
	}else{
		$(t).prev().html("▶");
		p.hide(300);
	}
	
}
</script>
</body>
</html>