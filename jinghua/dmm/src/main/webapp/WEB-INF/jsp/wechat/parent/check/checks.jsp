<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>家长中心-考勤分析</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/wechat/style2.css" />


</head>
<body>
<c:set var="str" value="${'/'}${stuId }"></c:set> 
  <div class="index">
	<div class="index_title">考勤分析</div>
	<input type="button"  value=" "  id="home" onclick="javascript:window.location.href='${ctx}/wechat/parent/${is_wechat}/main${is_wechat eq true ?'':str }' " />
  </div>

<div class="body_main">
  <!-- 内容-->
   <div class="in_content">
   		<div class="kaoqin_box">
        	<h2 class="kao_title">本学期</h2>
            <div class="kaoqin">
            	<div class="kao_1">
                	<div class="kao_11">
                    	<h2 class="times"><c:if test="${empty check }">0</c:if><c:if test="${!empty check }">${check.class_late }</c:if></h2>
                    </div>
                    <h3 class="jishu">迟到/次</h3>
                </div>
                
                <div class="kao_1">
                	<div class="kao_11">
                    	<h2 class="times"><c:if test="${empty check }">0</c:if><c:if test="${!empty check }">${check.class_early }</c:if></h2>
                    </div>
                    <h3 class="jishu">早退/次</h3>
                </div>
                
                <div class="kao_1">
                	<div class="kao_11">
                    	<h2 class="times"><c:if test="${empty check }">0</c:if><c:if test="${!empty check }">${check.cut_class }</c:if></h2>
                    </div>
                    <h3 class="jishu">旷课/次</h3>
                </div>
                
                <div class="kao_1">
                	<div class="kao_11">
                    	<h2 class="times"><c:if test="${empty check }">0</c:if><c:if test="${!empty check }">${check.class_leave }</c:if></h2>
                    </div>
                    <h3 class="jishu">请假/次</h3>
                </div>
                
            </div>
            <p class="all">
            	本学期共<c:if test="${empty check }">0</c:if><c:if test="${!empty check }">${check.class_number }</c:if>节课
            </p>
        </div>
   		<c:if test="${!empty check_often }">
	   		<div class="score_box">
		   		<c:if test="${!empty check_often.often_late_class }">
		        	<p class="score_w">
		                经常迟到的课：${check_often.often_late_class }
		            </p>
				</c:if>	
				<c:if test="${!empty check_often.often_cut_class }">
		            <p class="score_w">
		                经常旷课的课：${check_often.often_cut_class }
		            </p>
		        </c:if>
	        </div>
        </c:if>
   
   </div>
</div>   
<!--尾部线条-->
<p class="login-hr-line">
	<span class="login-hr-icon"></span>
</p>
</body>
</html>

