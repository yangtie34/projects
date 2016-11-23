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
<title>家长中心-消费分析</title>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/wechat/style2.css" />


</head>
<body>
  <c:set var="str" value="${'/'}${stuId }"></c:set> 
  <div class="index">
	<div class="index_title">消费分析</div>
	<input type="button"  value=" "  id="home" onclick="javascript:window.location.href='${ctx}/wechat/parent/${is_wechat}/main${is_wechat eq true ?'':str }' " />
  </div>
  
<div class="body_main">
<!--预警标题-->
<div class="yujing_title">
    <!-- 时间类型数据还没定义 -->
     
    <a class="yujing_t1${timeType eq 'rxyl' ? '_zhong' :'' }" href="${ctx}/wechat/parent/${is_wechat}/card/rxyl${is_wechat eq true ?'':str }">入学以来</a>
    <a class="yujing_t1${timeType eq 'bxq' ? '_zhong' :'' }" href="${ctx}/wechat/parent/${is_wechat}/card/bxq${is_wechat eq true ?'':str }">本学期</a>
    <%--<a class="yujing_t1${timeType eq 'by' ? '_zhong' :'' }" href="${ctx}/wechat/parent/${is_wechat}/card/by${is_wechat eq true ?'':str }">本月</a>--%>
    <a class="yujing_t1${timeType eq 'bz' ? '_zhong' :'' }" href="${ctx}/wechat/parent/${is_wechat}/card/bz${is_wechat eq true ?'':str }">本周</a>
</div>
<!-- 内容-->
   <div class="in_content">
   		<!--消费分析-->
        <div class="consume_content">
        	
            <div class="money_box">
            	<!--金额左边-->
                <div class="money_left">
                	<img src="${ctxStatic}/images/wechat/xf_tb_01.png" alt="$" />
                    <h3 class="money_rmb">
                    	卡余额/元
                    </h3>
                    <h2 class="money_data">
                    	${cardAnalyzeData.balance }
                    </h2>
                </div>
                <!--金额右边-->
                <div class="money_right">
                	<img src="${ctxStatic}/images/wechat/xf_tb_01.png" alt="$" />
                    <h3 class="money_rmb">
                    	累计消费/元
                    </h3>
                    <h2 class="money_data">
                    	${cardAnalyzeData.total }
                    </h2>
                </div>
                <!--金额下面-->
                <div class="money_bottom">
                	<img src="${ctxStatic}/images/wechat/xf_tb_01.png" alt="$" />
                    <h3 class="money_day">
                    	日均消费/元
                    </h3>
                    <h2 class="money_dd">
                    	${cardAnalyzeData.avg_my }
                    </h2>
                </div>
            </div>
            
            <!-- 日均各消费类型数据还没填 -->

            <!--图标-->
            <div class="tubiao_box">

                        <div class="tubiao_01">
                            <div class="tubiao_011">
                                <img src="${ctxStatic}/images/wechat/xf_tb_02.png" alt="market" />
                                <h2 class="tb_name">超市/元</h2>
                                <h2 class="tb_money">
                                    <c:choose>
                                        <c:when test="${cardAnalyzeData.type_avg_my['02']==null}">0.00</c:when>
                                        <c:otherwise>${cardAnalyzeData.type_avg_my["02"]}</c:otherwise>
                                    </c:choose>
                                </h2>
                            </div>
                        </div>

                    <div class="tubiao_01">
                            <div class="tubiao_011">
                                <img src="${ctxStatic}/images/wechat/xf_tb_03.png" alt="dining" />
                                <h2 class="tb_name">餐厅/元</h2>
                                <h2 class="tb_money">
                                    <c:choose>
                                        <c:when test="${cardAnalyzeData.type_avg_my['01']==null}">0.00</c:when>
                                        <c:otherwise>${cardAnalyzeData.type_avg_my["01"]}</c:otherwise>
                                    </c:choose>
                                </h2>
                            </div>
                    </div>

                    <div class="tubiao_01">
                            <div class="tubiao_011">
                                <img src="${ctxStatic}/images/wechat/xf_tb_04.png" alt="water" />
                                <h2 class="tb_name">用水/元</h2>
                                <h2 class="tb_money">
                                    <c:choose>
                                        <c:when test="${cardAnalyzeData.type_avg_my['03']==null}">0.00</c:when>
                                        <c:otherwise>${cardAnalyzeData.type_avg_my["03"]}</c:otherwise>
                                    </c:choose>
                                </h2>
                            </div>
                    </div>

                    <div class="tubiao_01">
                            <div class="tubiao_011">
                                <img src="${ctxStatic}/images/wechat/xf_tb_05.png" alt="electric" />
                                <h2 class="tb_name">用电/元</h2>
                                <h2 class="tb_money">
                                    <c:choose>
                                        <c:when test="${cardAnalyzeData.type_avg_my['04']==null}">0.00</c:when>
                                        <c:otherwise>${cardAnalyzeData.type_avg_my["04"]}</c:otherwise>
                                    </c:choose>
                                </h2>
                            </div>
                    </div>
            </div>
        <!--    图标结束-->
        </div>
        <!--注释-->
        <div class="note">
        	<p class="note_word">
            	1.全校${cardAnalyzeData.stu.sex}生日均消费为 ${cardAnalyzeData.avg_all } 元，您的孩子的高于 ${cardAnalyzeData.avg_morethan}% 的学生。
				<%--<br/>2.全校学生普遍喜欢在超市消费，超市的消费占比为 ${cardAnalyzeData.val1 }%，您的孩子更加喜欢在超市消费，消费占比为${cardAnalyzeData.val2 }%。--%>
            </p>
        </div>
        
   </div>
     
</div>   
<!--尾部线条-->
<p class="login-hr-line">
	<span class="login-hr-icon"></span>
</p>
</body>
</html>
