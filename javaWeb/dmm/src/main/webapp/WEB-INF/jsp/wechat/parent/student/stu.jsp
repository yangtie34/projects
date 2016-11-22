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
<title>家长中心-孩子信息</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/wechat/style2.css" />
</head>
<body>
<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	var showDiv = function(){
		$("#btn").css("display","none");
		$("#mydiv").css("display","inline");

	}
	
</script>
<div class="index">
<c:set var="str" value="${'/'}${stuId }"></c:set>
  <div class="index_title">孩子信息</div>
     <input type="button"  value=" "  id="home" onclick="javascript:window.location.href='${ctx}/wechat/parent/${is_wechat}/main${is_wechat eq true ?'':str }' " />
  </div>
  
<div class="body_main">
  <!-- 内容-->
   <div class="in_content">
   		<div class="picture_box">
        	<img class="picture" src="${ctxStatic}/images/wechat/picture.png" alt="头像" />
            <div class="word">
            	<p class="name">
                	${stu.stu_name }
                </p>
                <p class="department">
                	${stu.dept_name }
                	${stu.major_name }<br/>
                	${stu.class_name }
                </p>
                <p class="student_id">
                	（${stu.stu_id } ）
                </p>
            </div>
        </div>
        
        <div class="kid_message">
            <div class="kid_m1">
            	<p>
               		辅导员：${stu.class_tea_name }<br/>
					手机号：${stu.class_tea_tel }<br/>
					<p>
						系部主任:<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${stu.dept_tea_name} : ${stu.dept_tea_tel }<br/><p>
					<c:if test="${!empty deptList }">
						<input class="s_button" type="button"  value="更多"  id="btn" onclick="showDiv();"/>
					</c:if>
				</p>
				<div style="display: none;" id="mydiv">
					 <c:forEach items="${deptList }" var="dept">${dept.name_ } :<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${dept.tea_name} : ${dept.tel }<br/></c:forEach> 
				</div>
			
			</div>
			
            <div class="kid_m2">
            	<p>
               		 住宿：${dorm.lou_name }号楼  ${dorm.ceng_name } ${dorm.dorm_name }室  ${dorm.berth_name }床位<br/>
					 室友：<c:forEach items="${roomList }" var="roommate">${roommate.roommate }  </c:forEach></p>
			</div>
        </div>
        <c:if test="${!empty awardList || !empty subsidyList  }">
	        <div class="score_box"  style="padding-bottom: 0px;padding-top: 0px;">
	        	<c:if test="${!empty awardList }">
		        	<div class="score_1" style="padding-bottom: 0px;padding-top: 0px;">
		            	<h3 class="score_title">奖学金</h3>
		                <p class="score_w">
		                	<c:forEach items="${awardList }" var="award">${award.batch }获得${award.award_name }${award.money }元<br/></c:forEach>
						</p>
		            </div>
	        	</c:if>
	        	<c:if test="${!empty subsidyList }">     
		            <div class="score_1" style="padding-bottom: 0px;padding-top: 0px;">
		            	<h3 class="score_title">助学金</h3>
		                <p class="score_w">
		                	<c:forEach items="${subsidyList }" var="subsidy">${subsidy.batch }获得${subsidy.subsidy_code }${subsidy.money }元<br/></c:forEach>
						</p>
		            </div>
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

