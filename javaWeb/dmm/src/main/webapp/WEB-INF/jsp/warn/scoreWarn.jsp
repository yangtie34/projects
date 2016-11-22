<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>我的学业预警</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/warn/style2.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/warn/style3.css" />

</head>
<body>

  <div class="index_title">我的学业预警</div>


  <!-- 内容-->
   <div class="in_content">
   	 <!--预警课业-->
     <div class="gua_box">
     	<p class="guake">我的挂科数：<span class="gua2">${counts }</span> 门</p>
     	<c:if test="${counts==0 }">
			<p class="guake2">恭喜你没有挂科！！！</p>
		</c:if>
		<c:if test="${counts>=7 }">
			<p class="guake2">你的挂科数已达到不发毕业证标准！！！</p>
		</c:if>
		<c:if test="${counts>0 && counts<7 }">
			<p class="guake2">如果再挂科<span class="gua3">${7-counts }</span>门将不给发毕业证！</p>
		</c:if>
	  </div>
     
     <!--挂过的科目-->
     <div class="guag_box">
     	<p class="red_gua">
        	我挂过的科目
        </p>
        <div class="table">
        	<c:forEach items="${scoreWarn }" var="warn">
        		<p class="guake">${warn.school_year }学年 ${warn.term_code }学期 <br/> ${warn.name_ } <span class="gua1">${warn.centesimal_score }</span>分</p> 
        	</c:forEach>
         </div>
      </div>
         <!--  注意-->
         <div class="gua_box">
         	<p class="red_zy">注意：</p>
         	<div class="zhuyi">
                
                <p class="guake3">挂科超过<span class="gua3">7</span>门将不给发毕业证！</p>
                <p class="guake2">为了我能顺利毕业，一定不要再挂科啦！</p>
            </div>
         </div>
     
      
   </div>
      
<!--尾部线条-->
<p class="login-hr-line">
	<span class="login-hr-icon"></span>
</p>
</body>
</html>
