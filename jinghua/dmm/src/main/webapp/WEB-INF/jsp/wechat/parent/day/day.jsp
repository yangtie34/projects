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
<title>家长中心-孩子一天</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/wechat/style2.css" />


</head>
<body>
	 <c:set var="str" value="${'/'}${stuId }"></c:set>
  <div class="index">
	<div class="index_title">孩子一天</div>
	<input type="button"  value=" "  id="home" onclick="javascript:window.location.href='${ctx}/wechat/parent/${is_wechat}/main${is_wechat eq true ?'':str }' " />
  </div>
<div class="body_main">
<!--预警标题-->
<div class="yujing_title">
	 
    <a class="yujing_t1${dayt eq 'yesterday' ? '_zhong' :'' }" href="${ctx }/wechat/parent/${is_wechat}/day/yesterday${is_wechat eq true ?'':str }">昨天</a>
    <a class="yujing_t1${dayt eq 'today' ? '_zhong' :'' }" style="border-right:1px solid #4e4e4e; border-left:1px solid #4e4e4e;" href="${ctx }/wechat/parent/${is_wechat}/day/today${is_wechat eq true ?'':str }">今天</a>
    <a class="yujing_t1${dayt eq 'tomorrow' ? '_zhong' :'' }" href="${ctx }/wechat/parent/${is_wechat}/day/tomorrow${is_wechat eq true ?'':str }">明天</a>
</div>
<!-- 内容-->
   <div class="in_content">
   		<!--孩子的一天-->
        <div class="oneday_box">
        	<div class="oneday_start">
                <img class="quan" src="${ctxStatic }/images/wechat/oneday_01.png">
                <span class="quan_text">一天校园生活开始</span>
            </div>
        	<div class="oneday_form_bj">
                
                <div class="oneday_form">
                    
                    <div class="oneday_content">
                    <!--上午活动-->
                     <div class="morning">
                           <c:forEach items="${days }" var="day">
                             <c:choose>
                             	<c:when test="${day.stu_id eq 'me' }">  
                             		<c:if test="${dayt eq 'today' }">
                             			<div class="one_content_01">
			                                <span class="one_line">
			                                    <img src="${ctxStatic }/images/wechat/hong.png">
			                                </span>
			                                <span class="one_time1">
			                                    ${day.start_time } 
			                                </span>
			                                <span class="one_text1">
				                                	&nbsp;&nbsp;&nbsp;&nbsp;当前
			                                </span>
		                           		</div>
                             		</c:if>
                             	</c:when> 
								<c:otherwise>
		                            <div class="one_content_01">
		                                <span class="one_line">
		                                    <img src="${ctxStatic }/images/wechat/lv.png">
		                                </span>  
		                                <span class="one_time1">
		                                    ${day.start_time } 
		                                </span>
		                                <c:choose>  
										    <c:when test="${day.action_code eq '01'}">  
										    	<span class="one_can">餐</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '02'}">  
										       <span class="one_chao">市</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '03'}">  
										       <span class="one_xi">浴</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '04'}">  
										       <span class="one_ke">课</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '05'}">  
										       <span class="one_ke">警</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '06'}">  
										       <span class="one_shu">借</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '07'}">  
										       <span class="one_shu">书</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '08'}">  
										       <span class="one_su">宿</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '09'}">  
										       <span class="one_ke">医</span>
										    </c:when> 
										    <c:when test="${day.action_code eq '10'}">  
										       <span class="one_ke">假</span>
										    </c:when>  
										</c:choose>
		                                <span class="one_text1">
		                                	${day.address }&nbsp;&nbsp;${day.action }
		                                </span>
		                                
		                           </div>
	                           </c:otherwise>
	                      </c:choose>
						</c:forEach>
                    </div> 
                  </div>
                </div>
             <!-- 一天结束 --> 
             <div class="oneday_over">
                <span class="quan_text">一天校园生活结束</span>
             </div>  
             
             </div>   
             <img class="quan1" src="${ctxStatic }/images/wechat/oneday_01.png">
        </div>
   </div>
     
</div>
<!--尾部线条-->
<p class="login-hr-line">
	<span class="login-hr-icon"></span>
</p>
</body>
</html>