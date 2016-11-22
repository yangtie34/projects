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
<title>家长中心-成绩分析</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/wechat/style2.css" />
<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<style type="text/css">
.FGH {
 height:auto !important;
 height:344px;
 min-height:344px;
 }
</style>
</head>
<body>
<c:set var="str" value="${'/'}${stuId }"></c:set>
  <div class="index">
  <div class="index_title">成绩分析</div>
     <input type="button"  value=" "  id="home" onclick="javascript:window.location.href='${ctx}/wechat/parent/${is_wechat}/main${is_wechat eq true ?'':str }' " />
  </div>
<div class="body_main">
<h3 class="count_title" style="text-align:center;">${wechatScoreData.schoolYear }学年，第<c:choose>
<c:when test="${wechatScoreData.termCode=='01'}">一</c:when>
<c:when test="${wechatScoreData.termCode=='02'}">二</c:when></c:choose> 学期</h3>
  <!-- 内容-->
   <div class="in_content">
   		<div class="score_bbox">
        	
            <div class="score_c1">
            	<div class="score_1">
                	<div class="score_11">
                    	<h2 class="number">${wechatScoreData.classRanking }</h2>
                    </div>
                    <h3 class="jishu">班级排名</h3>
                    <h4 class="all_number">&lt;&nbsp;&nbsp;${wechatScoreData.classStuNum }人&nbsp;&nbsp;&gt;</h4>
                </div>
                
                <div class="score_1">
                	<div class="score_12">
                    	<h2 class="number">${wechatScoreData.majorRanking }</h2>
                    </div>
                    <h3 class="jishu">专业排名</h3>
                    <h4 class="all_number">&lt;&nbsp;&nbsp;${wechatScoreData.majorStuNum}人&nbsp;&nbsp;&gt;</h4>
                </div>
                
                <div class="score_1">
                	<div class="score_13">
                    	<h2 class="number">${wechatScoreData.scoreCount}</h2>
                    </div>
                    <h3 class="jishu">总成绩</h3>
                    <h4 class="all_number">&lt;&nbsp;&nbsp;-&nbsp;&nbsp;&gt;</h4>
                </div>
                
                <div class="score_1">
                	<div class="score_14">
                    	<h2 class="number">${wechatScoreData.scoreAvg }</h2>
                    </div>
                    <h3 class="jishu">平均成绩</h3>
                    <h4 class="all_number">&lt;&nbsp;&nbsp;-&nbsp;&nbsp;&gt;</h4>
                </div>
                
            </div>
           
        </div>
        
   		<!-- 高分科目，低分科目，分数数据还没填写 -->
   		
   		<div class="score_nbox">
        	<p class="score_n">
                高分科目：${wechatScoreData.gfkms[0] }&nbsp;&nbsp;<span class="red">${wechatScoreData.gfkmfs[0] }</span>&nbsp;分，专业最高分：${wechatScoreData.gfkmzgf[0] }分
            </p>
			
            <p class="score_n">
                低分科目：${wechatScoreData.dfkms[0] }&nbsp;&nbsp;<span class="red">${wechatScoreData.dfkmfs[0] }</span>&nbsp;分，专业最高分：${wechatScoreData.dfkmzgf[0] }分
            </p>
            
            <p class="score_n">
                不及格科目：<c:forEach items="${wechatScoreData.bjgkms }" var="bjgkm"> ${bjgkm }</c:forEach><span class="red"></span>
            </p>
        </div>
        
        <!--成绩折线图-->
   		<div class="count_box">
        	<h3 class="count_title">
            	往年总成绩、平均成绩曲线图
            </h3>
            <div id="wechatParentScore"  class="FGH" ></div>
        </div>
   </div>
</div>
<!--尾部线条-->
<p class="login-hr-line">
	<span class="login-hr-icon"></span>
</p>
	<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts-all.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/jsEchar/echartManager.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/jsEchar/js-echar.js"></script>
<script type="text/javascript">
	var stuId = "${wechatScoreData.stuId }";
    $.ajax({
        type: "POST",
        async:true,
        url:"${ctx}/wechat/parent/score/line/${wechatScoreData.stuId }",
        success: function(data){
            var scoreLine = data.object;
            scoreLine.pid = "wechatParentScore";
            $("#wechatParentScore").echartline(scoreLine,'macarons');
        }
    });


</script>
</body>
</html>
