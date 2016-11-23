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
	<title>家长中心-预警消息</title>
	
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/wechat/style2.css" />
</head>
<body>
<div class="index">
<c:set var="str" value="${'/'}${stuId }"></c:set>
  <div class="index_title">预警消息</div>
     <input type="button"  value=" "  id="home" onclick="javascript:window.location.href='${ctx}/wechat/parent/${is_wechat}/main${is_wechat eq true ?'':str }' " />
  </div>
<!--预警标题
<div class="yujing_title">
    <a class="yujing_t1_zhong" href="kid_yujing.html"> 行为预警</a>
    <a class="yujing_t1" style="border-right:1px solid #4e4e4e; border-left:1px solid #4e4e4e;" href="kid_xueye.html">学业类预警</a>
    <a class="yujing_t1" href="kid_xiaofei.html">消费类预警</a>
</div> -->
<!-- 内容-->
<div class="body_main">
	<div class="in_content">
   		<div class="yj_content" id="warn_main_div">
   		  	<c:if test="${empty warns}">
   		  		<div class="yj_r_01">没有预警消息！</div>
   		  	</c:if>
   			<c:forEach items="${warns }" var="warn">
	   			<div>
		            <div class="date_yj">
		           		<span class="month_yj">
		                	${warn.warn_date }
		                </span>
		                <span class="week_yj">
		                	${warn.warnWeak }
		                </span>
		                
		           </div> 
		        	<div class="yj_content_1">
		        	  <div class="yj_c_right">
		                <div class="yj_c_left"></div>
			                <p class="yj_r_01">
						     	${warn.warn_text }
			                </p>
		                </div>
		            </div>
		        </div>
            </c:forEach>
         </div>
         <c:if test="${page.currentPage < page.totalPages }">
         	<center id="lodingC"><img src="${ctxStatic }/images/wechat/onloading.gif"/></center>
         </c:if>
    </div>
</div>
   
	<!--尾部线条-->
	<p class="login-hr-line">
		<span class="login-hr-icon"></span>
	</p>
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var currentPage=Number("${page.currentPage}");
			var totalPages=Number("${page.totalPages}");
		    $(window).scroll(function() {
		        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
		        	
		        	if(currentPage < totalPages){
		        		
		        		$.ajax({
		         		     type: "POST",
		         		     url:"${ctx}/wechat/parent/warn/${stuId}/"+(currentPage+1),// 
		         		     success: function(data){
		         		    	var warns=data.data.warns;
		         		    	for(var i=0;i<warns.length;i++){
		         		    		var htmlDom="<div>                    "+
		         			        "    <div class='date_yj'>            "+
		         			        "   		<span class='month_yj'>   "+
		         			        warns[i].warn_date+
		         			        "        </span>                      "+
		         			        "        <span class='week_yj'>       "+
		         			        warns[i].warnWeak +
		         			        "        </span>                      "+
		         			        "                                     "+
		         			        "   </div>                            "+
		         			        "	<div class='yj_content_1'>        "+
		         			        "	  <div class='yj_c_right'>        "+
		         			        "        <div class='yj_c_left'></div>"+
		         				    "            <p class='yj_r_01'>      "+
		         				    warns[i].warn_text +
		         				    "            </p>                     "+
		         			        "        </div>                       "+
		         			        "    </div>                           "+
		         			        "</div>                               ";
		         		            $("#warn_main_div").append(htmlDom);
		         		            
		         		    	}
		         		    	currentPage=data.object.currentPage;
		         		    	totalPages=data.object.totalPages;
		         		    	if(currentPage>=totalPages){
		         		    		$("#lodingC").html("预警消息已加载完毕");
		         		    	}
		         		     }  
		         		 }); 
		        	}
		        }
		    });
		});
	
	</script>
</body>
</html>