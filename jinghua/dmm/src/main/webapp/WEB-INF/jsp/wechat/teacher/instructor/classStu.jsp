<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/wechat/style2.css" />
	<title>${class_name }班—学生名单</title>
</head>
<body>
<div class="index">
  <div class="index_title">${class_name }班—学生名单</div>
  <input type="button" value=" " id="back" onClick="javascript:window.location.href='${ctx}/wechat/teacher/instructor/web/classbase/${tea.no_}' ">
</div>
<div class="body_main">
	<c:if test="${empty stus }">
		<div class="fd-content">
		    <div class="fd-border">
		      <p class="fd-p">没有学生数据</p>
		    </div>
		  </div>
	</c:if>
	<c:forEach items="${stus }" var="stu">
	 <div class="fd-content">
	    <div class="fd-border${stu.SEX_CODE=='01'?'-boy':'-girl'}">
	      <p class="fd-p ${stu.SEX_CODE=='01'?'fd-boy-icon':'fd-girl-icon'}">${stu.NAME_}<span class="fd-imp">(${stu.NO_})</span></p>
	      <hr class="fd-hr${stu.SEX_CODE=='01'?'-boy':'-girl'} " />
	      ${stu.DUTIES}
	      <p class="fd-p"> 手机号：${stu.TEL} </p>
	      <p class="fd-p"> 寝室：${stu.ADDRESS} </p>
	      <p class="fd-p">紧急联系电话：${stu.HOME_TEL}</p>
	      <p class="fd-p  fd-fl-left"><samp class="fd-link">▶</samp><a class="fd-link" href="javascrpit:void(0);" onclick="showhideP(this);">学生详情</a></p>
	      <p class="fd-p  fd-fl-left" style="display: none;">
	      <a class="fd-link" href="${ctx }/wechat/parent/web/warn/${stu.NO_}">预警信息</a>
	      <a class="fd-link" href="${ctx }/wechat/parent/web/score/${stu.NO_}">学生成绩</a><br /><br />
	      <a class="fd-link" href="${ctx }/wechat/parent/web/day/today/${stu.NO_}">学生轨迹</a>
	      <a class="fd-link" href="${ctx }/wechat/parent/web/card/default/${stu.NO_}">消费信息</a></p>
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