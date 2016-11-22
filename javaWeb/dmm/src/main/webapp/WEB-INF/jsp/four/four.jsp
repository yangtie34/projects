<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
	<title>我的校园生活</title>
	<link rel="stylesheet" href="${ctxStatic }/css/four/jquery.fullPage.css">
    <link rel="stylesheet" href="${ctxStatic }/css/four/usty-style.css">
    <link rel="stylesheet" href="${ctxStatic }/css/four/auto.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
    
    <style>
        .textP{
            text-align:center;
        }
        .title_a{
        	font-size: 14px;
		    margin-left: 10px;
		    margin-top: 5px;
        }
    </style>
<c:if test="${ isMe eq true  && isShare eq true}">
<script type="text/javascript" >
var jiathis_config={
	siteNum:9,
	sm:"email,tsina,weixin,renren,cqq,copy,feixin,tqq",
	summary:"轻轻地，我走了，正如我轻轻地来。挥一挥手，作别青青的校园，点一点分享，永留我在校园的点点滴滴！",
    title:"#我的校园生活全景#",
	boldNum:5,
	showClose:true,
	shortUrl:false,
	hideMore:false
}
</script>
</c:if>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" >
     <div class="container">
       <div class="navbar-header">
         <button data-target=".navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle collapsed">
           <span class="sr-only">Toggle navigation</span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a href="#" class="usty-user">${ue.realName}</a>
       </div>
       <div role="navigation" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
         <ul class="nav navbar-nav">
           <li><a class="title_a">${ue.userName}</a></li>
           <li><a class="title_a">${ue.deptName}</a></li>
           <c:if test="${ isMe eq true}">
           		<li><a class="title_a">上次登录：${ue.lastLoginTime}</a></li>
	           <c:if test="${ isShare eq true}">
	           		<li><a type="button" id="down_share_del" href="javascript:void(0)" class="title_a">取消分享</a></li>
	           </c:if>
	           <c:if test="${ isShare eq false}">
	           		<li><a type="button" id="down_share" href="javascript:void(0)" class="title_a">分享生活</a></li>
	           </c:if>
           </c:if>
           
         </ul>
         <c:if test="${ isMe eq true}">
	         <ul class="nav navbar-nav navbar-right hidden-sm">
	           <li><a class="title_a" href="${ctx}/logout">注销</a></li>
	         </ul>
         </c:if>
       </div>
     </div>
</div>
<div style="position: absolute; left: 50%; top: 50%;" id="loginLoging">
    <img src="/dmm/static/images/four/loading.gif"><br>
    <span style="font-size: 14px;">正在加载</span>
</div>
<div id="dowebok" style="display:none">
<%
	int a[]= new int[2];
	a[0]=2;
	a[1]=0;
%>
	<div class="section index-page usty-first-bottom" >
    <!--第一屏的第一屏-->
    <div class="usty-menu-height"></div>
    <div class=" usty-common-auto usty-first">
      <div class="usty-first-imgbg">
        <div class="usty-first-p">
          <div class="usty-first-f14">${fn:substring(ue.enrollDate, 0, 7)}进入</div>
          <div class="usty-first-f30" id="schoolName">我的母校</div>
          <div class="usty-first-f14">开启了我的校园生活</div>
        <!--  <div class="usty-first-f24">校园生活</div> -->
        </div>
        <div class="usty-first-left01">
          <article class="usty-first-leftimg01"><a href="javascript:void(0)"> </a> </article>
        </div>
        <div class="usty-first-left02">
          <article class="usty-first-leftimg02"> <a href="javascript:void(0)"> </a></article>
        </div>
        <div class="usty-first-left03">
          <article class="usty-first-leftimg03"> <a href="javascript:void(0)"> </a></article>
        </div>
        <div class="usty-first-left04">
          <article class="usty-first-leftimg04"><a href="javascript:void(0)"> </a> </article>
        </div>
        <div class="usty-first-right01">
          <article class="usty-first-rightimg01"><a href="javascript:void(0)"> </a> </article>
        </div>
        <div class="usty-first-right02">
          <article class="usty-first-rightimg02"><a href="javascript:void(0)"> </a> </article>
        </div>
        <div class="usty-first-right03">
          <article class="usty-first-rightimg03"><a href="javascript:void(0)"> </a> </article>
        </div>
        <div class="usty-first-right04">
          <article class="usty-first-rightimg04"><a href="javascript:void(0)"> </a> </article>
        </div>
        
      </div>
      <div class="usty-first-text">
        <ul class="usty-first-ul">
          <li><a href="#page2">第一次</a></li>
          <li class="padd-left90"><a href="#page3">消费</a></li>
          <li class="padd-left130"><a href="#page4">图书馆</a></li>
          <li class="padd-left20"><a href="#page5">上网</a></li>
          <li class="padd-left110"><a href="#page6">成绩</a></li>
          <li class="padd-left170"><a href="#page7">人脉</a></li>
          <li class="padd-left100"><a href="#page8">奖惩</a></li>
          <li><a href="#page9">标签墙</a></li>
        </ul>
      </div>
    </div>
    <!--第一屏的第一屏-->
  </div>


	<c:if test="${!empty getFirstPayLog || !empty getFirstBookLog || !empty getFirstAwardLog || !empty getFirstDownLog }">
		<div class="section section<%=a[0] %>">
			<c:if test="${!empty getFirstPayLog}">
				<div class="slide slide<%=a[1] %>" data-method="getFirstPayLog" id="getFirstPayLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-firstxf"> 
				      <div class="usty-firstxf-line">
				        <h3 class="usty-firstxf-h3">第一次消费</h3>
				        <ul>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt01">时间</dt>
				              <dd id="getFirstPayLogP1">没有数据</dd> 
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt02">金额</dt>
				              <dd id="getFirstPayLogP2">没有数据</dd> 
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt03">消费类型</dt>
				              <dd id="getFirstPayLogP3">没有数据</dd> 
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt04">地点</dt>
				              <dd id="getFirstPayLogP4">没有数据</dd> 
				            </dl>
				          </li>
				        </ul>
				      </div> 
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
		
			<c:if test="${!empty getFirstBookLog}">
				<div class="slide slide<%=a[1] %>" data-method="getFirstBookLog" id="getFirstBookLog">
				    <div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-firstxf-four">
				        <div  class="usty-firstxf-four-list01">
				          <article>
				            <dl>
				              <dt>刷卡</dt>
				              <dt>踏入图书馆 </dt>
				              <dd > <span class="color-time-imp"> 时间：</span> </dd>
				              <dd class="text-align-right" id="getFirstBookLogP1">没有数据</dd>
				              <dd class="text-align-right-indent" id="getFirstBookLogP11"></dd>
				            </dl>
				            <dl>
				              <dt>借书 </dt>
				              <dd class=" "> <span class="color-time-imp"> 书名：</span><span id="getFirstBookLogP2"></span></dd>
				              <dd class=" text-align-float"  > <span class="color-time-imp"> 时间：</span></dd>
				              <dd class=" text-align-center-float" id="getFirstBookLogP3">没有数据
				            </dl>
				          </article>
				          <div class="usty-firstxf-four-bottom">第一次</div>
				        </div>
      				</div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getFirstAwardLog}">
				<div class="slide slide<%=a[1] %>" data-method="getFirstAwardLog" id="getFirstAwardLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-firstxf-three">
				        <article class="usty-firstxf-three-list01">
				          <dl>
				            <dt>第一次获得奖学金 </dt>
				            <dd  class="usty-firstxf-three-padright" id="getFirstAwardLogP1">没有数据</dd>
				          </dl>
				        </article>
				        <article class="usty-firstxf-three-list02">
				          <dl>
				            <dt  class="heng-2color">第一次获得荣誉称号 </dt>
				            <dd id="getFirstAwardLogP2">没有数据</dd>
				          </dl>
				        </article>
				        <article class="usty-firstxf-three-list03">
				          <dl>
				            <dt class="heng-3color">第一次获得助学金 </dt>
				            <dd  class="usty-firstxf-three-padright"  id="getFirstAwardLogP3">没有数据</dd>
				          </dl>
				        </article>
				     <!--   <article class="usty-firstxf-three-list04">
				          <dl>
				            <dt class="heng-4color">第一次贷款 </dt>
				            <dd class="usty-firstxf-three-padleft">没有数据</dd>
				          </dl>
				        </article> -->
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getFirstDownLog}">
				<div class="slide slide<%=a[1] %>" data-method="getFirstDownLog" id="getFirstDownLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-firstxf-two">
				        <div class="usty-firstxf-two-img"> </div>
				        <div class="usty-firstxf-two-list">
				          <dl>
				            <dt>第一次挂科</dt>
				            <dd id="getFirstDownLogP1">没有数据</dd>
				          </dl>
				          <!-- 
				          <dl>
				            <dt class="blue-bg">第一次违纪</dt>
				            <dd class="blue-font">没有数据</dd>
				          </dl>
				          -->
				        </div>
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
		</div>
		<% a[0]++;a[1]=0; %>
	</c:if>

	<c:if test="${!empty getSumMoneyLog || !empty getPayCount || !empty getWashLog || !empty getRecCount || !empty getCardHabit }">
		<div class="section section<%=a[0] %>">
		
			<c:if test="${!empty getSumMoneyLog}">
				<div class="slide slide<%=a[1] %>" data-method="getSumMoneyLog" id="getSumMoneyLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-xiaofei">
				      <div class="usty-xiaofei-mingxi "> <a class="usty-shu-line01-button usty-xiaofei-button-style " href="${ctx}/four/card/detail/${id}"  target=_blank>消费明细</a> </div>
				      <div class="usty-xiaofei-line">
				        <ul>
				          <li>
				            <dl>
				              <dt>消费共计</dt>
				              <dd><span class="usty-xiaofei-lispan" id="getSumMoneyLogP1">--</span>元，</dd>
				              <dd><span class="usty-xiaofei-lispan" id="getSumMoneyLogP2">--</span>笔</dd>
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt>日均消费</dt>
				              <dd><span class="usty-xiaofei-lispan" id="getSumMoneyLogP3">--</span>元，</dd>
				              <dd><span class="usty-xiaofei-lispan"> </span> </dd>
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt>消费共计</dt>
				              <dd>超越了<span class="usty-xiaofei-lispan" id="getSumMoneyLogP4">--</span></dd>
				              <dd>的同届学生</dd>
				            </dl>
				          </li>
				        </ul>
				      </div>
				      <div class="usty-shu-line03"></div>
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
				
			<c:if test="${!empty getPayCount}">
				<div class="slide slide<%=a[1] %>" data-method="getPayCount" id="getPayCount">
                    <div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-xiaofei-two">
				        <div class="usty-xiaofei-two-line">
				          <div class="usty-xiaofei-two-left usty-echartPie" id="pie"></div>
				          <ul class="usty-xiaofei-two-right">
				            <li>
				              <h3 class="usty-xiaofei-two-h3">最爱这里消费</h3>
				              <dl  class="usty-xiaofei-two-dl01"  id="getPayCountCT">
				                <dt > </dt>
				              </dl>
				            </li>
				            <li >
				              <dl class="usty-xiaofei-two-dl02"   id="getPayCountCS">
				                <dt > </dt>
				              </dl>
				            </li>
				          </ul>
				        </div>
				    </div> 
					<div ></div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getWashLog}">
				<div class="slide slide<%=a[1] %>" data-method="getWashLog" id="getWashLog">
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-chengji03">
				        <div class="usty-chengji03-title clearfix">
				          <div class="usty-chengji03-icon usty-xiaofei03-icon usty-chengji03-float-left"> <dl><dt>洗浴</dt><dd></dd></dl></div>
				          <ul class="usty-chengji03-ul usty-xiaofei03-ul usty-chengji03-float-left">
				            <li class="usty-chengji03-yellow">我从使用一卡通洗浴消费以来共洗浴  <span class="usty-chengji03-color-yellow" id="getWashLogP1">--</span>次</li>
				            <li class="usty-chengji03-green">洗浴次数平均为  <span class="usty-chengji03-color-green" id="getWashLogP2">--</span> </li>
				            <li class="usty-chengji03-red">我超越了 <span class="usty-chengji03-color-red" id="getWashLogP3">--</span> 的学生</li>
				          </ul> 
				        </div> 
				        <!-- <div class="usty-wenjuan"><h5><a href="#">问卷调查</a></h5>
				          <div class="usty-wenjuan-content">
				            <ul>
				                 <li>环境不好，我不怎么在学校洗澡</li>
				                 <li>我跟好基友混用一张卡洗澡</li>
				                 <li>我喜欢在学校洗澡</li>
				           </ul>
				         </div>
				        </div> -->
				      </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getRecCount}">
				<div class="slide slide<%=a[1] %>" data-method="getRecCount" id="getRecCount">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-xiaofei05">          
				          <article class="usty-xiaofei05-fenxi">
				            <section class="usty-xiaofei05-section usty-xiaofei05-floatleft">
				              <h3>我的充值</h3>
				            </section>
				            <div class="usty-xiaofei05-tongji clearfix">
				               <div class="usty-echartPie" id="recPie"></div>
				            </div>
				          </article>  
				          <article class="usty-xiaofei05-fenxi">
				            <section class="usty-xiaofei05-section">
				              <h3>全校学生最爱充值方式排名</h3>
				            </section>
				            <div class="usty-xiaofei05-paiming clearfix">
				                <dl class="usty-xiaofei05-paiming-dl"><dt class="xiaofei05-01">1</dt><dd class="rec_all_name">没有数据</dd></dl>
				               <dl class="usty-xiaofei05-paiming-dl"><dt class="xiaofei05-02">2</dt><dd class="rec_all_name">没有数据</dd></dl>
				               <dl class="usty-xiaofei05-paiming-dl"><dt class="xiaofei05-03">3</dt><dd class="rec_all_name">没有数据</dd></dl>
				               <dl class="usty-xiaofei05-paiming-dl"><dt class="xiaofei05-04">4</dt><dd class="rec_all_name">没有数据</dd></dl>
				            </div>
				          </article> 
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getCardHabit}">
				<div class="slide slide<%=a[1] %>" data-method="getCardHabit" id="getCardHabit">
					<h3>消费习惯统计</h3>
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-chengji03">
				        <div class="usty-chengji03-title clearfix">
				          <div class="usty-chengji03-icon usty-xiaofei04-icon usty-chengji03-float-left"> <dl><dt>餐饮分析</dt><dd></dd></dl></div>
				          <ul class="usty-chengji03-ul usty-xiaofei04-ul  usty-chengji03-float-left">
				            <li class="usty-chengji03-yellow">吃早餐<span id="getCardHabitP1">--</span>天，超越<span class="usty-chengji03-color-yellow" id="getCardHabitP2">--</span>${ue.sex}生</li>
				            <li class="usty-chengji03-green">吃午餐<span id="getCardHabitP3">--</span>天，超越<span class="usty-chengji03-color-green" id="getCardHabitP4">--</span>${ue.sex}生</li>
				            <li class="usty-chengji03-red">吃晚餐<span id="getCardHabitP5">--</span>天，超越<span class="usty-chengji03-color-red" id="getCardHabitP6">--</span>${ue.sex}生</li>
				          </ul> 
				        </div> 
				        <!-- <div class="usty-wenjuan"><h5><a href="#">问卷调查</a></h5>
				          <div class="usty-wenjuan-content">
				            <ul>
				                 <li>我经常让同学帮我带饭</li>
				                 <li>我一直用现金就餐,啦啦啦... </li>
				                 <li>我与同学共用一张卡</li>
				           </ul>
				         </div>
				        </div> -->
				      </div>
                    <div>
                        <div id="breakfastInstrumentPanel" style="width:300px;height:300px;float:left;"></div>
                        <div id="lunchInstrumentPanel" style="width:300px;height:300px;float:left;"></div>
                        <div id="dinnerInstrumentPanel" style="width:300px;height:300px;float:left;"></div>
                    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
		</div>
		<%a[0]++;a[1]=0; %>
	</c:if>

	<c:if test="${!empty getAllBorrowLog || !empty getAllRKELog }">
		<div class="section section<%=a[0] %>">
		
			<c:if test="${!empty getAllBorrowLog}">
				<div class="slide slide<%=a[1] %> section1" data-method="getAllBorrowLog" id="getAllBorrowLog">
					<!-- <p id="getAllBorrowLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
					    <div class="usty-common-auto usty-shu"> 
					      <div class="usty-shu-mingxi"> <a class="usty-shu-line01-button" href="${ctx}/four/book/detail/${id}"  target=_blank>借书明细</a> </div>
					      <div class="usty-shu-line">
					        <article class="usty-shu-left">
					          <p class="usty-shu-textp">共借图书：<span class="usty-shu-text" id="getAllBorrowLogP1">--</span>本 </p>
					          <p class="usty-shu-textp">本届人均借书：<span class="usty-shu-text" id="getAllBorrowLogP2">--</span>本 </p>
					          <p class="usty-shu-textp">超越了<span class="usty-shu-text" id="getAllBorrowLogP3">--</span>的本届学生</p>
					        </article>
					        <!-- 
					        <article class="usty-shu-right"> 
					          <p class="usty-shu-textp">借书频率最高的书：</p> 
					          <p class="usty-shu-textp"><span class="usty-shu-text">没有数据</span>，借了<span class="usty-shu-text">--</span>次 </p>
					        </article>  -->
					      </div>
					      <div class="usty-shu-line03 usty-echart" id="bookBorrowLine" ></div>
					  </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getAllRKELog}">
				<div class="slide slide<%=a[1] %>" data-method="getAllRKELog" id="getAllRKELog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-shu">
				        <div class="usty-shu-line">
				          <article class="usty-shu02-left"> <img src="${ctxStatic}/images/four/tushu-ceng.png"  /> </article>
				          <article class="usty-shu02-right ">
				            <p class="usty-shu02-textp ">共进出图书馆：<span class="usty-shu02-text" id="getAllRKELogP1">--</span>次 </p>
				            <p class="usty-shu02-textp ">超越了 <span class="usty-shu02-text  " id="getAllRKELogP2">--</span>的本届学生 </p>
				            <p class="usty-shu02-textp ">进出图书馆高峰期：<span class="usty-shu02-text  " id="getAllRKELogP3">没有数据</span> </p>
				          </article>
				        </div>
				        <div class="usty-shu-line03 usty-echart" id="bookRKELine"></div>
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
		</div>
		<%a[0]++;a[1]=0; %>
	</c:if>
	
	<!-- 上网数据，暂时不加IF判断，开始=================== -->
	<div class="section section<%=a[0] %>" id="onLineLog">
			<div class="usty-menu-height"></div>
		    <div class="usty-common-auto usty-shangwang">
		      <div class="usty-shangwang-imgbg">
		        <div class="usty-shangwang-left00">
		          <article > <span class="usty-shangwang-span" id = "netMax">--</span>小时 </article>
		        </div>
		        <div class="usty-shangwang-left00-1">
		          <article > 最长在线时长 </article>
		        </div>
		        <div class="usty-shangwang-left01">
		          <article > <span class="usty-shangwang-span" id = "netAvg">--</span>小时 </article>
		        </div>
		        <div class="usty-shangwang-left01-1">
		          <article > 平均在线时长 </article>
		        </div>
		        <div class="usty-shangwang-left02">
		        
		          <article> <span  class="usty-shangwang-span"> 上网 </span>共计<span><span class="usty-shangwang-span" id = "netSum"> -- </span> 小时</span></article>
		        	
		        	<!-- <article  > <span  class="usty-shangwang-span">没有数据</span></article> -->
		        </div>
		        <div class="usty-shangwang-right01">
		          <article > <span class="usty-shangwang-span" id="njAvg">--</span> <br />
		            小时 </article>
		        </div>
		        <div class="usty-shangwang-right01-1">
		          <article > 本届人均在线 </article>
		        </div>
		      </div>
		      <p class="usty-shangwang-textp">经常上线时间：<span class="usty-shangwang-span" id="netJcsxsj">--</span>点</p>
		    </div>
		<%a[1]++; %>
	</div>
	<%a[0]++;a[1]=0; %>
	
	<!-- 上网数据，暂时不加IF判断 ,结束=========== -->
	
	<c:if test="${!empty getAvgScoreLog || !empty getBestRankLog || !empty getScoreCountLog }">
		<div class="section section<%=a[0] %>">
		
			<c:if test="${!empty getAvgScoreLog}">
				<div class="slide slide<%=a[1] %>" data-method="getAvgScoreLog" id="getAvgScoreLog">
					<!-- <p id="getAvgScoreLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-shu">
				      <div class="usty-shu-mingxi "> <a class="usty-shu-line01-button usty-chengji-mingxi" href="${ctx}/four/score/detail/${id}"  target=_blank>成绩明细</a> </div>
				      <div class="usty-shu-line usty-chengji-line">
				      <!-- 
				        <article class="usty-shu-left usty-chengji-left">
				          <p class="usty-shu-textp usty-chengji-textp">共修学科：<span class="usty-shu-text usty-chengji-text">24</span>门 </p>
				          <p class="usty-shu-textp usty-chengji-textp">挂科：<span class="usty-shu-text usty-chengji-text">1</span>门 </p> 
				        </article> -->
				        <article class="usty-shu-right usty-chengji-right">
				          <p class="usty-shu-textp usty-chengji-textp">平均成绩：<span class="usty-shu-text usty-chengji-text" id="getAvgScoreLogP1">--</span>分</p>
				          <p class="usty-shu-textp usty-chengji-textp">本专业平均成绩：<span class="usty-shu-text usty-chengji-text" id="getAvgScoreLogP2">--</span>分</p>
				          <p class="usty-shu-textp usty-chengji-textp">超过了：<span class="usty-shu-text usty-chengji-text" id="getAvgScoreLogP3">--</span>的学生</p>
				        </article>
				      </div>
				      <div class="usty-shu-line03 usty-echart" id="score2Line"></div>
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getBestRankLog}">
				<div class="slide slide<%=a[1] %>" data-method="getBestRankLog" id="getBestRankLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-chengji02">
				        <div class="usty-chengji02-title">
				          <h3>我的最好名次</h3>
				          <p><span id="getBestRankLogP1"></span><br />
				            凭借平均成绩 <span class="usty-chengji02-span" id="getBestRankLogP2">--分</span> 取得了入校以来本届本专业的最好名次：<span class="usty-chengji02-span"  id="getBestRankLogP3">--</span></p>
				        </div>
				        <div class="usty-chengji02-line" id="getBestRankLogP4">
				        </div>
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<c:if test="${!empty getScoreCountLog}">
				<div class="slide slide<%=a[1] %>" data-method="getScoreCountLog" id="getScoreCountLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-chengji03">
				        <div class="usty-chengji03-title clearfix">
				          <div class="usty-chengji03-icon usty-chengji03-float-left"> <dl><dt>成绩分析</dt><dd></dd></dl></div>
				          <ul class="usty-chengji03-ul usty-chengji03-float-left">
				            <li class="usty-chengji03-yellow">综合总成绩<span id="getScoreCountLogP1">--</span>分，超越 <span class="usty-chengji03-color-yellow" id="getScoreCountLogP2">--</span>本届本专业学生</li>
				            <li class="usty-chengji03-green">综合平均成绩<span id="getScoreCountLogP3">--</span>分，超越 <span class="usty-chengji03-color-green" id="getScoreCountLogP4">--</span>本届本专业学生</li>
				            <li class="usty-chengji03-red">综合绩点<span id="getScoreCountLogP5">--</span>分，超越 <span class="usty-chengji03-color-red" id="getScoreCountLogP6">--</span>本届本专业学生</li>
				          </ul>
				          <div class="usty-chengji03-zong usty-chengji03-float-left" id="getScoreCountLogP7">--</div>
				        </div>
				        <div class="usty-chengji03-left">
				         <h3 class="usty-chengji03-h3">本专业平均成绩最好的科目</h3>
				          <div class="usty-chengji02-line usty-chengji03-line" id="getScoreCountLogP8">
				        </div></div>
				        <div class="usty-chengji03-left">
				         <h3 class="usty-chengji03-h3">我的成绩最好的科目</h3>
				        <div class="usty-chengji02-line usty-chengji03-line" id="getScoreCountLogP9">
				        </div></div>
				    </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
		</div>
		<%a[0]++;a[1]=0; %>
	</c:if>

	<c:if test="${!empty getRoommateLog || !empty getTutorLog}">
		<div class="section section<%=a[0] %>">
		
			<c:if test="${!empty getRoommateLog}">
				<div class="slide slide<%=a[1] %>" data-method="getRoommateLog" id="getRoommateLog">
					<!-- <p id="getRoommateLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
					    <div class="usty-common-auto usty-shiyou">
					      <div class="usty-shiyou-imgbg">
					      	<div class="usty-shiyou-center00 usty-shiyou-centerimg${ue.sex eq '男'?'01':'00'}"></div>
					        <div class="usty-shiyou-left00">
					          <article class="usty-shiyou${ue.sex eq '男'?'':'-nv'}-leftimg00"><a href="javascript:void(0)">
					            <div class="usty-shiyou-name">没有数据</div>
					            </a> </article>
					        </div>
					        <div class="usty-shiyou-left01">
					          <article class="usty-shiyou${ue.sex eq '男'?'':'-nv'}-leftimg01"> <a href="javascript:void(0)">
					            <div class="usty-shiyou-name">没有数据</div>
					            </a></article>
					        </div>
					        <div class="usty-shiyou-left02">
					          <article class="usty-shiyou${ue.sex eq '男'?'':'-nv'}-leftimg02"> <a href="javascript:void(0)">
					            <div class="usty-shiyou-name">没有数据</div>
					            </a></article>
					        </div>
					        <div class="usty-shiyou-right01">
					          <article class="usty-shiyou${ue.sex eq '男'?'':'-nv'}-rightimg01"><a href="javascript:void(0)">
					            <div class="usty-shiyou-name">没有数据</div>
					            </a> </article>
					        </div>
					        <div class="usty-shiyou-right02">
					          <article class="usty-shiyou${ue.sex eq '男'?'':'-nv'}-rightimg02"><a href="javascript:void(0)">
					            <div class="usty-shiyou-name">没有数据</div>
					            </a> </article>
					        </div>
					      </div>
					      <p class="usty-shiyou-textp">我的室友</p>
					    </div>
				</div>
				<%a[1]++; %>
			</c:if>
	
			<c:if test="${!empty getTutorLog}">
				<div class="slide slide<%=a[1] %>" data-method="getTutorLog" id="getTutorLog">
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-renmai02">
				         <p class="usty-renmai-textp">我的辅导员</p>
				        <div class="usty-renmai02-line" id="getTutorLogP">
				           <dl class="usty-renmai02-dl"><dd>没有数据</dd></dl>
				          <!--   <dl class="usty-renmai02-dl"><dt>第一学年</dt> <dd class="usty-renmai02-touxiang usty-renmai02-touxiang-02"></dd><dd>辅导员A</dd> <dd>硕士</dd> <dd>毕业于XXX学院</dd></dl>
				           <dl class="usty-renmai02-dl"><dt>第一学年</dt> <dd class="usty-renmai02-touxiang usty-renmai02-touxiang-03"></dd><dd>辅导员A</dd> <dd>硕士</dd> <dd>毕业于XXX学院</dd></dl>
				           <dl class="usty-renmai02-dl"><dt>第一学年</dt> <dd class="usty-renmai02-touxiang usty-renmai02-touxiang-04"></dd><dd>辅导员A</dd> <dd>硕士</dd> <dd>毕业于XXX学院</dd></dl>
				         -->
				        </div>
				       
				      </div> 

				</div>
				<%a[1]++; %>
			</c:if>
			
		</div>
        
		<%a[0]++;a[1]=0; %>
	</c:if>

	<c:if test="${!empty getAwardTimesLog || !empty getSubsidyLog}">
		<div class="section section<%=a[0] %>">
		
			<c:if test="${!empty getAwardTimesLog}">
				<div class="slide slide<%=a[1] %>" data-method="getAwardTimesLog" id="getAwardTimesLog">
					<!-- <p id="getAwardTimesLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-jiang">
				        <div class="usty-jiang-line">
				          <div class="usty-jiang-left ">
				          	<p class="usty-jiang-textp">在校期间</p>
				            <p class="usty-jiang-textp">获得<span class="usty-jiang-text" id="getAwardTimesLogP1">--</span>次奖学金 </p>
				            <p class="usty-jiang-textp">共计<span class="usty-jiang-text" id="getAwardTimesLogP2">--</span>元 </p>
				          </div>
				          <div class="usty-jiang-right ">
				            <p class="usty-jiang-textp">本届得奖次数超越人数：<span class="usty-jiang-text" id="getAwardTimesLogP3">--</span> </p>
				          <!--  <p class="usty-jiang-textp">获奖金额超越人数：<span class="usty-jiang-text">80%</span> </p> -->
				          </div>
				        </div>
				        <div class="usty-jiang-leftimg01"> <img src="${ctxStatic}/images/four/002.png"  /> </div>
				        <div class="usty-jiang-rightimg01 "> <img src="${ctxStatic}/images/four/001.png" /> </div>
				        <div class="usty-jiang-rightimg02 "> <img src="${ctxStatic}/images/four/003.png"  /> </div>
			      </div>
				</div>
				<%a[1]++; %>
			</c:if>
	
			<c:if test="${!empty getSubsidyLog}">
				<div class="slide slide<%=a[1] %>" data-method="getSubsidyLog" id="getSubsidyLog">
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-jiang03"> 
				          <article class="usty-jiang03-line">
				            <dl class="usty-jiang03-dl clearfix">
				              <dd>在校期间<br/>获得<span class="usty-jiang03-span" id="getSubsidyLogP1">--</span>次助学金 <br /> 共计 <span class="usty-jiang03-span" id="getSubsidyLogP2">--</span>元</dd>
				              <dt> <img src="${ctxStatic}/images/four/jiang03-img.png"  /></dt>
				            </dl> 
				          </article> 
				      </div>
				</div>
				<%a[1]++; %>
			</c:if>
			
			<div class="slide slide<%=a[1] %>" data-method="getPunishLog" id="getPunishLog">
                <div class="usty-menu-height"></div>
			      <div class="usty-common-auto usty-chengji02">
			        <div class="usty-chengji02-title usty-jiang02-title">
			          <h3 id="getPunishLogP1">在校期间共违纪--次</h3>
			        </div> 
			          <article class="usty-jiang02-line">
			            <dl class="usty-jiang02-dl01">
			              <dt> </dt>
			              <dd  id="getPunishLogP2"></dd>
			            </dl>
			             <dl class="usty-jiang02-dl02">
			              <dt> </dt>
			              <dd  id="getPunishLogP3"></dd>
			            </dl>
			             <dl class="usty-jiang02-dl03">
			              <dt> </dt>
			              <dd  id="getPunishLogP4"></dd>
			            </dl>
			             <dl class="usty-jiang02-dl04">
			              <dt> </dt>
			              <dd  id="getPunishLogP5"></dd>
			            </dl>
			          </article> 
			      </div>
			</div>
			<%a[1]++; %>
			
			
		</div>
		<%a[0]++;a[1]=0; %>
	</c:if>

    <c:if test="${!empty getMyTag}">
        
        <div class="section section<%=a[0] %>  usty-first-bottom" id="getMyTag">
		    <div class="usty-menu-height"></div>
		    <div class="usty-common-auto usty-biaoqian">
		      <p class="usty-biaoqian-textp">我的标签墙</p>
		      <div class="usty-biaoqian-imgbg">
		        <div class="usty-biaoqian-left usty-biaoqian-scale-auto ">
		          <article class="usty-biaoqian-name-left" id="getMyTagP2"></article>
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-shadow"></article>
		        </div>
		        <div class="usty-biaoqian-left">
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-right"  id="getMyTagP1"></article>
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-shadow"> </article>
		        </div>
		        <div class="usty-biaoqian-left usty-biaoqian-scale-auto ">
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-bgcolor02"  id="getMyTagP3"></article>
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-shadow"> </article>
		        </div>
		        <div class="usty-biaoqian-left">
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-bgcolor01" id="getMyTagP4"></article>
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-shadow"> </article>
		        </div>
		      </div>
		    </div>
	  </div>
        <%a[0]++;a[1]=0; %>
    </c:if>
    
    
    
  

</div>

	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/js/fullPage/jquery.fullPage.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts-all.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/jsEchar/echartManager.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/jsEchar/js-echar.js"></script>
<c:if test="${ isMe eq true && isShare eq true}">
	<script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_r.js?uid=1349532179262480&move=0" charset="utf-8"></script>
</c:if>
<script type="text/javascript">
var _subStr=function (str,sum){
	if(str.length>=sum){
		str=str.substring(0,sum-1)+"...";
	}
	return str;
}



$(function(){
	var nowSlide=0;
	$('#dowebok').fullpage({
		sectionsColor: ['#1bbc9b', '#4BBFC3', '#7BAABE', '#f90','#f90','#f90','#f90','#f90','#f90','#f90'],
		anchors: ['page1', 'page2', 'page3', 'page4', 'page5', 'page6', 'page7', 'page8', 'page9'],
		'navigation': true,
		menu: '#menu'
/*		afterLoad: function(anchorLink, index){
			if($('.section'+index+' .slide1').length==0){
				<%--console.log(".section !=0 "+index);--%>
				doMethod($('.section'+index+' .slide0'));
			}else{
				<%--console.log(".section =0 "+index);--%>
				goTo(index,0);
			}
		},
		onLeave: function(index, direction){
			$('.section'+index+' .textP').slideUp('fast');
			var now=$('.section'+index+' .slide'+nowSlide);
			LeaveMethod(now);
		},
		afterSlideLoad: function(anchorLink,index,slideIndex){
			nowSlide=slideIndex;
			var now=$('.section'+index+' .slide'+slideIndex);
			doMethod(now);
		},
		onSlideLeave: function(anchorLink,index,slideIndex){
			$('.section'+index+' .slide'+slideIndex+' .textP').slideUp('fast');
			var now=$('.section'+index+' .slide'+slideIndex);
			LeaveMethod(now);
		} */

	});
	
	$(window).resize(function(){
  //      autoScrolling();
    });
    function autoScrolling(){
        var $ww = $(window).width();
        if($ww < 1024){
            $.fn.fullpage.setAutoScrolling(false);
        } else {
            $.fn.fullpage.setAutoScrolling(true);
        }
    }

 //   autoScrolling();
    
    function goTo(a,b){
    	$.fn.fullpage.moveTo(a, b);
    }
    var myChart;
    var RecPieChart;
    function thisInit(){

    	$.ajax({
   		     type: "POST",
   		     async:true,
   		     url:"${ctx}/four/first/book/${id}",
   		     success: function(data){
   		     	if(data.data.FirstRKE != null){
   		     		$("#getFirstBookLogP1").html(data.data.FirstRKE.TIM.substring(0,10));
                	$("#getFirstBookLogP11").html(data.data.FirstRKE.TIM.substring(11,19));
                }
                if(data.data.FirstBorrow != null){
   		    		$("#getFirstBookLogP2").html("<span href='javascript:void(0)' title='"+data.data.FirstBorrow.BOOK_NAME+"' > 《"+_subStr(data.data.FirstBorrow.BOOK_NAME,5)+"》</span>");
                	$("#getFirstBookLogP3").html(data.data.FirstBorrow.BORROW_TIME.substring(0,10)+"<br />"+data.data.FirstBorrow.BORROW_TIME.substring(11,19));
   		     	}
   		     }
   		 });
    	
    	$.ajax({
  		     type: "POST",
  		     async:true,
  		     url:"${ctx}/four/card/pay/count/${id}",
  		     success: function(data){
                myChart=$("#pie").echartpie({pid:"pie", top_formatter:'{a} <br/>{b} : {c} ({d}次%)',title_text:"", title_subtext:"",series_name:"消费统计", data:data.data.PayCount});
                var html="<dt > </dt>";
                for(var i=0;i<data.data.LikeEat.length;i++){
                    html+="<dd><span>"+data.data.LikeEat[i].NAME_+"</span> <span>"+data.data.LikeEat[i].SUM_+" 元/"+data.data.LikeEat[i].COUNT_+"次</span> <span>"+data.data.LikeEat[i].COUNT_RATIO+" </span></dd>";
                }
                $("#getPayCountCT").html(html);
                
                var html2="";
                for(var i=0;i<data.data.LikeShop.length;i++){
                    html2+="<dd><span>"+data.data.LikeShop[i].NAME_+"</span> <span>"+data.data.LikeShop[i].SUM_+" 元/"+data.data.LikeShop[i].COUNT_+"次</span> <span>"+data.data.LikeShop[i].COUNT_RATIO+" </span></dd>";
                }
                $("#getPayCountCS").html(html2);
                
  		     }
  		 });
    	
    	$.ajax({
 		     type: "POST",
 		     async:true,
 		     url:"${ctx}/four/card/rec/count/${id}",
 		     success: function(data){
                    RecPieChart=$("#recPie").echartpie({pid:"recPie", title_text:"", title_subtext:"",series_name:"充值统计", data:data.data.RecCount});
                    var recClass=$(".rec_all_name");
                    for(var i=0;i<recClass.length;i++){
                    	if(data.data.AllRecCount[i]!= undefined){
                    		$(recClass[i]).html(data.data.AllRecCount[i].NAME_);
                    	}
                    }
 		     }
 		 });
    	
    	$.ajax({
   		     type: "POST",
   		     async:true,
   		     url:"${ctx}/four/book/borrow/${id}",
   		     success: function(data){
   		     	if(data.data != null){
   		     		$("#getAllBorrowLogP1").html(data.data.SUMS);
   		    		$("#getAllBorrowLogP2").html(data.data.AV);
   		    		$("#getAllBorrowLogP3").html(data.data.MORE_THAN);
   		    		var bookBorrowLine = data.data.borrowLine;
                	bookBorrowLine.pid = "bookBorrowLine";
                	$("#bookBorrowLine").echartline(bookBorrowLine,'macarons');
   		     	}
   		     }
   		 });
    	
    	$.ajax({
   		     type: "POST",
   		     async:true,
   		     url:"${ctx}/four/book/rke/${id}",
   		     success: function(data){
   		     	if(data.data.AllRKELog!=null){
   		     		$("#getAllRKELogP1").html(data.data.AllRKELog.SUMS);
   		    		$("#getAllRKELogP2").html(data.data.AllRKELog.MORE_THAN);
   		    		$("#getAllRKELogP3").html(data.data.LikeRKETimeLog.TIME_+":00-"+data.data.LikeRKETimeLog.TIME_+":59");
   		     	}
                var bookRKELine = data.data.bookRKELine;
                if(bookRKELine != undefined){
                 	bookRKELine.pid = "bookRKELine";
                	$('#bookRKELine').echartline(bookRKELine,'macarons');
                }
   		     }
   		 });

	    $.ajax({
		    type: "POST",
		    async:true,
		    url:"${ctx}/four/wash/${id}",
		    success: function(data){
		    	if(data.data.getWashTimes[0]!=undefined){
		    		$("#getWashLogP1").html(data.data.getWashTimes[0].TIMES);
		    		$("#getWashLogP2").html(data.data.getWashTimes[0].AVG_T);
		    		$("#getWashLogP3").html(data.data.getWashTimes[0].MORE_THAN);
		    	}
	   		}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/sum/moneyAndTimes/${id}",
			success: function(data){
				if(data.data.getSumMoneyAndPayTimes[0] != undefined){
					$("#getSumMoneyLogP1").html(data.data.getSumMoneyAndPayTimes[0].MONEY);
                	$("#getSumMoneyLogP2").html(data.data.getSumMoneyAndPayTimes[0].TIMES);
                	$("#getSumMoneyLogP3").html(data.data.getSumMoneyAndPayTimes[0].AVG_M);
                	$("#getSumMoneyLogP4").html(data.data.getSumMoneyAndPayTimes[0].MORE_THAN);
				}
		  	}
	  	});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/award/times/${id}",
			success: function(data){
				if(data.data.getAwardTimes[0] != undefined){
					$("#getAwardTimesLogP1").html(data.data.getAwardTimes[0].TIMES);
					$("#getAwardTimesLogP2").html(data.data.getAwardTimes[0].MONEY);
					$("#getAwardTimesLogP3").html(data.data.getAwardTimes[0].MORE_THAN);
				}else{
					$("#getAwardTimesLogP1").html(0);
					$("#getAwardTimesLogP2").html(0);
					$("#getAwardTimesLogP3").html(0);
				}
			}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/first/award/${id}",
			success: function(data){
				if(data.data.getFirstAwardMsg[0] != undefined && data.data.getFirstAwardMsg[0].AWARD_MONEY !="0"&& data.data.getFirstAwardMsg[0].AWARD_TIME !="0"
						&& data.data.getFirstAwardMsg[0].SUBSIDY_MONEY !=("0") && data.data.getFirstAwardMsg[0].SUBSIDY_TIME!="0"){
					$("#getFirstAwardLogP1").html("金额："+data.data.getFirstAwardMsg[0].AWARD_MONEY+"元&nbsp;&nbsp;时间："+data.data.getFirstAwardMsg[0].AWARD_TIME);
                	$("#getFirstAwardLogP3").html("金额："+data.data.getFirstAwardMsg[0].SUBSIDY_MONEY+"元&nbsp;&nbsp;时间："+data.data.getFirstAwardMsg[0].SUBSIDY_TIME);
				}else if (data.data.getFirstAwardMsg[0] != undefined && data.data.getFirstAwardMsg[0].AWARD_MONEY !="0"&& data.data.getFirstAwardMsg[0].AWARD_TIME !="0"
					&& data.data.getFirstAwardMsg[0].SUBSIDY_MONEY ==("0") && data.data.getFirstAwardMsg[0].SUBSIDY_TIME=="0"){
					$("#getFirstAwardLogP1").html("金额："+data.data.getFirstAwardMsg[0].AWARD_MONEY+"元&nbsp;&nbsp;时间："+data.data.getFirstAwardMsg[0].AWARD_TIME);
                	$("#getFirstAwardLogP3").html("没有获得");
				}else if (data.data.getFirstAwardMsg[0] != undefined && data.data.getFirstAwardMsg[0].AWARD_MONEY =="0"&& data.data.getFirstAwardMsg[0].AWARD_TIME =="0"
					&& data.data.getFirstAwardMsg[0].SUBSIDY_MONEY !=("0") && data.data.getFirstAwardMsg[0].SUBSIDY_TIME!="0"){
					$("#getFirstAwardLogP3").html("金额："+data.data.getFirstAwardMsg[0].AWARD_MONEY+"元&nbsp;&nbsp;时间："+data.data.getFirstAwardMsg[0].AWARD_TIME);
                	$("#getFirstAwardLogP1").html("没有获得");
				}else if (data.data.getFirstAwardMsg[0] != undefined && data.data.getFirstAwardMsg[0].AWARD_MONEY =="0"&& data.data.getFirstAwardMsg[0].AWARD_TIME =="0"
					&& data.data.getFirstAwardMsg[0].SUBSIDY_MONEY ==("0") && data.data.getFirstAwardMsg[0].SUBSIDY_TIME=="0"){
					$("#getFirstAwardLogP1").html("没有获得");
					$("#getFirstAwardLogP3").html("没有获得");
				}
			}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/award/subsidy/${id}",
			success: function(data){
				if(data.data.getSubsidyTimesAndMoney[0] != undefined){
					$("#getSubsidyLogP1").html(data.data.getSubsidyTimesAndMoney[0].TIMES);
					$("#getSubsidyLogP2").html(data.data.getSubsidyTimesAndMoney[0].MONEY);
				}else {
					$("#getSubsidyLogP1").html(0);
					$("#getSubsidyLogP2").html(0);
				}
			}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/firstpay/msg/${id}",
			success: function(data){
				if(data.data.getFirstPayMsg[0] != undefined){
					$("#getFirstPayLogP1").html(data.data.getFirstPayMsg[0].TIME_);
					$("#getFirstPayLogP2").html(data.data.getFirstPayMsg[0].MONEY+"元");
					$("#getFirstPayLogP3").html(data.data.getFirstPayMsg[0].STYLE);
					$("#getFirstPayLogP4").html(data.data.getFirstPayMsg[0].WINDOW);
				}
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/score/avg/${id}",
			success: function(data){
				var thisData=data.data;
                var avgScoreLine = thisData.AvgScoreLine;
                avgScoreLine.pid = "score2Line";
                $("#score2Line").echartline(avgScoreLine,'macarons');
                if(thisData.AvgScore!=null){
                	$("#getAvgScoreLogP1").html(thisData.AvgScore.AVG_);
					$("#getAvgScoreLogP2").html(thisData.AvgScore.AVG_ALL);
					$("#getAvgScoreLogP3").html(thisData.AvgScore.MORE_THAN);
                }
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/score/rank/${id}",
			success: function(data){
				var thisData=data.data;
				var list=thisData.ScoureList;
				if(list != null){
					var showS="";
					var colors=['red','blue','green'];
					var cnum=['01','02'];
					var i=0,j=0;
					for (var k = 0; k < list.length; k++) {
						if(i==3){
							i=0;
						}
						if(j==2){
							j=0;
						}
						showS+="<article class='usty-chengji02-"+colors[i]+cnum[j]+"'><dl><dt>"+list[k].CENTESIMAL_SCORE+"分</dt>"+
				 			"<dd>"+list[k].SCORE_NAME+"</dd></dl></article>";
				 			i++;j++;
					}
					$("#getBestRankLogP1").html("在"+thisData.BestRank.SCHOOL_YEAR+"学年第"+thisData.BestRank.TERM_CODE+"学期");
					$("#getBestRankLogP2").html(thisData.BestRank.AVG_);
					$("#getBestRankLogP3").html(thisData.BestRank.RANK+"名");
					$("#getBestRankLogP4").html(showS);
				}
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/score/count/${id}",
			success: function(data){
				var thisData=data.data;
				var allBest=thisData.BsetCourseAll;
				if(allBest != null){
					var allBestS="";
					var colors=['red','blue','green'];
					var cnum=['01','02'];
					var i=0,j=0;
					for (var k = 0; k < allBest.length; k++) {
						if(i==3){
							i=0;
						}
						allBestS+="<article class='usty-chengji02-"+colors[i]+cnum[0]+"'><dl><dt>"+allBest[k].MAX_+"分</dt>"+
				 			"<dd>"+allBest[k].COURSE_NAME+"</dd></dl></article>";
				 			i++;
					}
					$("#getScoreCountLogP8").html(allBestS);
				}
				
				var best=thisData.BsetCourse;
				if(best!=null){
					var bestS="";
					for (var k = 0; k < best.length; k++) {
						if(i==3){
						i=0;
						}
						bestS+="<article class='usty-chengji02-"+colors[i]+cnum[1]+"'><dl><dt>"+best[k].MAX_+"分</dt>"+
				 			"<dd>"+best[k].COURSE_NAME+"</dd></dl></article>";
				 			i++;
					}
					$("#getScoreCountLogP9").html(bestS);
				}
				if(thisData.SumScore != null){
					$("#getScoreCountLogP1").html(thisData.SumScore.SUM_);
					$("#getScoreCountLogP2").html(thisData.SumScore.MORE_THAN);
				}
				if(thisData.AvgScore != null){
					$("#getScoreCountLogP3").html(thisData.AvgScore.AVG_);
					$("#getScoreCountLogP4").html(thisData.AvgScore.MORE_THAN);
				}
				if(thisData.GpaScore != null){
					$("#getScoreCountLogP5").html(thisData.GpaScore.GPA);
					$("#getScoreCountLogP6").html(thisData.GpaScore.MORE_THAN);
				}
				if(thisData.AllScore != null){
					$("#getScoreCountLogP7").html(thisData.AllScore);
				}
				
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/score/down/${id}",
			success: function(data){
				var thisData=data.data;
				if(thisData.FirstDown==null){
					$("#getFirstDownLogP1").html("没有挂科");
				}else{
					$("#getFirstDownLogP1").html("科目："+thisData.FirstDown.COURSE_NAME+" &nbsp;&nbsp; 时间："+thisData.FirstDown.SCHOOL_YEAR+"学年"+thisData.FirstDown.TERM_CODE+"学期");
				}
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/punish/${id}",
			success: function(data){
				var thisData=data.data.getPunishMsg[0];
				if(thisData != undefined){
					$("#getPunishLogP1").html("共违纪"+thisData.TOTAL+"次");
                	$("#getPunishLogP2").html("警告"+thisData.JGAO+"次");
                	$("#getPunishLogP3").html("严重警告"+thisData.YZJGAO+"次");
                	$("#getPunishLogP4").html("记过"+thisData.JGUO+"次");
                	$("#getPunishLogP5").html("留校察看"+thisData.LXCKAN+"次");
				}
			}
		});
		
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/relation/room/${id}",
			success: function(data){
				var thisData=data.data;
				var list=thisData.roommate;
				var roomNameDom=$(".usty-shiyou-name");
				for (var k = 0; k < list.length; k++) {
					$(roomNameDom[k]).html(list[k].NAME_);
				}
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/relation/rutor/${id}",
			success: function(data){
				var thisData=data.data;
				var list=thisData.rutor;
				for (var i = 0; i < list.length; i++) {
				var htmlS="<dl class='usty-renmai02-dl'><dt>"+list[i].SCHOOL_YEAR+"学年</dt> "+
					"<dd class='usty-renmai02-touxiang usty-renmai02-touxiang-0"+list[i].SEX_CODE+"'></dd><dd  style='text-align: center;'>"+list[i].TEA_NAME+"老师</dd></dl>";
				$("#getTutorLogP").html(htmlS);
				}
				
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/wall/${id}",
			success: function(data){
				var thisData=data.object;
				for (var i = 1; i <= thisData.length; i++) {
					$("#getMyTagP"+i).html(thisData[(i-1)].NAME);
				}
			}
		});

        $.ajax({
            type: "POST",
            async:true,
            url:"${ctx}/four/schoolname/${id}",
            success: function(data){
                $("#schoolName").html(data.object);
            }
        });
        $.ajax({
            type: "POST",
            async:true,
            url:"${ctx}/four/net/netMax/${id}",
            success: function(data){
            	var thisData=data.data.getNetMaxTime[0];
                $("#netMax").html(thisData.MAXTIME);
                $("#njAvg").html(thisData.NJTIME);
            	
            }
        });
        $.ajax({
            type: "POST",
            async:true,
            url:"${ctx}/four/net/netBq/${id}",
            success: function(data){
            	var thisData=data.data.getNetBq[0];
                $("#getMyTagP4").html(thisData.BQ);
            	
            }
        });
        $.ajax({
            type: "POST",
            async:true,
            url:"${ctx}/four/net/netSum/${id}",
            success: function(data){
            	var thisData=data.data.getNetSumTime[0];
                $("#netSum").html(thisData.SUMTIME);
                $("#netAvg").html(thisData.AVGTIME);
            }
        });
        $.ajax({
            type: "POST",
            async:true,
            url:"${ctx}/four/net/netJc/${id}",
            success: function(data){
            	var thisData=data.data.getNetJcOnlineTime[0];
                $("#netJcsxsj").html(thisData.JCTIME);
            }
        });

        $.ajax({
            type: "POST",
            async:true,
            url:"${ctx}/four/card/habit/${id}",
            success: function(data){
                var thisData=data.data.MealHabit,
                    stuData = data.data.MealsAvgStu,
                    endYearData = data.data.MealsAvgEndyearSex;
                $("#getCardHabitP1").html(thisData.BREAKFAST_DAYS);
                $("#getCardHabitP2").html(thisData.BREAKFAST_MORE_THAN);
                $("#getCardHabitP3").html(thisData.LUNCH_DAYS);
                $("#getCardHabitP4").html(thisData.LUNCH_MORE_THAN);
                $("#getCardHabitP5").html(thisData.DINNER_DAYS);
                $("#getCardHabitP6").html(thisData.DINNER_MORE_THAN);

                var myChart = echarts.init(document.getElementById('breakfastInstrumentPanel'),'macarons');

                var option = {
                    tooltip : {
                        formatter: "{a} <br/>{b} : {c}"
                    },
                    series : [
                        {
                            name:'早餐',
                            type:'gauge',
                            max:10,
                            detail : {formatter:'早餐',
                            	textStyle: {
                                	color: 'greenyellow',
                                	fontSize : 18
                            }},
                            data:[{value: stuData.AVG_ZC, name: '平均消费'}]
                        }
                    ]
                };

                // 为echarts对象加载数据
                myChart.setOption(option);

                var myChart1 = echarts.init(document.getElementById('lunchInstrumentPanel'),'macarons');

                var option = {
                tooltip : {
                formatter: "{a} <br/>{b} : {c}"
                },
                series : [
                {
                name:'午餐',
                type:'gauge',
                max:10,
                detail : {
                	formatter:'午餐',
                	textStyle: {
                    	color: 'yellow',
                    	fontSize : 18
                }},
                data:[{value: stuData.AVG_MIDDLE, name: '平均消费'}]
                }
                ]
                };

                // 为echarts对象加载数据
                myChart1.setOption(option);

                var myChart2 = echarts.init(document.getElementById('dinnerInstrumentPanel'),'macarons');

                var option = {
                tooltip : {
                formatter: "{a} <br/>{b} : {c}"
                },
                series : [
                {
                name:'晚餐',
                type:'gauge',
                max:10,
                detail : {formatter:'晚餐',
                	textStyle: {
                    	color: 'black',
                    	fontSize : 18
                }},
                data:[{value: stuData.AVG_WC, name: '平均消费'}]
                }
                ]
                };

                // 为echarts对象加载数据
                myChart2.setOption(option);

                $(window).resize(function(){
                	//改变显示区域的大小
                	document.getElementById("dinnerInstrumentPanel").style.width='150px';
                    document.getElementById("breakfastInstrumentPanel").style.width='150px';
                    document.getElementById("lunchInstrumentPanel").style.width='150px';
					myChart.resize();
					myChart1.resize();
					myChart2.resize();
                    RecPieChart.resize();
                });
                //$("#breakfastInstrumentPanel").html("早餐平均值"+stuData.AVG_ZC+"与本届男生/女生平均值"+endYearData.AVG_ZC_ALL);
                //$("#lunchInstrumentPanel").html("午餐平均值"+stuData.AVG_MIDDLE+"与本届男生/女生平均值"+endYearData.AVG_MIDDLE_ALL);
                //$("#dinnerInstrumentPanel").html("晚餐平均值"+stuData.AVG_WC+"与本届男生/女生平均值"+endYearData.AVG_WC_ALL);

            }
        });
		
	}
    
    function doMethod(now){
    	if(now.data('method')=='getFirstBookLog'){
            $("#getFirstBookLogP1").slideDown();
            $("#getFirstBookLogP2").slideDown();
		}
    	if(now.data('method')=='getFirstDownLog'){
            $("#getFirstDownLogP").slideDown();
		}
		if(now.data('method')=='getAllBorrowLog'){
            $("#getAllBorrowLogP").slideDown();
		}
		if(now.data('method')=='getAllRKELog'){
			$("#getAllRKELogP1").slideDown();
	   		$("#getAllRKELogP2").slideDown();
		}
	    if(now.data('method')=='getWashLog'){
            $("#getWashLogP").slideDown();
	    }
	    if(now.data('method')=='getSumMoneyLog'){
            $("#getSumMoneyLogP").slideDown();
	    }
	    if(now.data('method')=='getFirstPayLog'){
			$('.usty-firstxf-line li').animate({"margin-top": '0' }, 500);
	    }
	    if(now.data('method')=='getAwardTimesLog'){
			var spend=500;
	    	$('.usty-jiang-rightimg01').animate({top:"10%",opacity:"1"},spend);
	    	$('.usty-jiang-rightimg02').animate({right:"10%",opacity:"1"},spend);
	    }
	    if(now.data('method')=='getFirstAwardLog'){
            var spend=500;
            $('.usty-firstxf-three-list01').delay(spend*0*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list01 dl').delay(spend*0*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list02').delay(spend*1*0.5).animate({"margin-right":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list02 dl').delay(spend*1*0.5).animate({"margin-right":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list03').delay(spend*2*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list03 dl').delay(spend*2*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list04').delay(spend*3*0.5).animate({"margin-right":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list04 dl').delay(spend*3*0.5).animate({"margin-right":"0%",opacity:"1"},spend);


	    }
	    if(now.data('method')=='getSubsidyLog'){
			$("#getSubsidyLogP").slideDown();
	    }
	    if(now.data('method')=='getAvgScoreLog'){
			$("#getAvgScoreLogP").slideDown();
	    }
	    if(now.data('method')=='getBestRankLog'){
	    	
	    }
	    if(now.data('method')=='getScoreCountLog'){
	    }
	    if(now.data('method')=='getRoommateLog'){
            var spend=500;
            $('.usty-shiyou-left00').animate({top: '0',opacity:"1" }, spend);
            $('.usty-shiyou-left01').animate({left: '0',opacity:"1" }, spend);
            $('.usty-shiyou-right01').animate({right: '0',opacity:"1" }, spend);
            $('.usty-shiyou-left02').animate({left: '14%',top:"72%",opacity:"1" }, spend);
            $('.usty-shiyou-right02').animate({right: '14%',top:"72%",opacity:"1" }, spend);
	    }
	    if(now.data('method')=='getTutorLog'){
	    	$("#getTutorLogP").slideDown();
	    }
	    if(now.data('method')=='getPayCount'){
            if(myChart!=undefined && myChart!= null && myChart!=''){
                myChart.restore();
            }
	    }
    }
    
    function LeaveMethod(now){
    	if(now.data('method')=='getRoommateLog'){
    		$('.usty-shiyou-left00').animate({top: '-120%',opacity:"0" }, "fast");
    		$('.usty-shiyou-left01').animate({left: '-120%',opacity:"0" }, "fast");
    		$('.usty-shiyou-right01').animate({right: '-120%',opacity:"0" }, "fast");
    		$('.usty-shiyou-left02').animate({left: '-120%',top:"140%" ,opacity:"0"}, 1000);
    		$('.usty-shiyou-right02').animate({right: '-120%',top:"140%",opacity:"0" }, 1000);
	    }
	    
    	if(now.data('method')=='getFirstPayLog'){
    		$('.usty-firstxf-line li').animate({"margin-top": '240%' }, "fast");
	    }

	    if(now.data('method')=='getAwardTimesLog'){
	        $('.usty-jiang-rightimg01').animate({top:"-48%",opacity:"0"},"fast");
	        $('.usty-jiang-rightimg02').animate({right:"-17%",opacity:"0"},"fast");
	    }
        if(now.data('method')=='getFirstAwardLog'){
            var spend=500;
            $('.usty-firstxf-three-list04').delay(spend*0*0.5).animate({"margin-right":"80%",opacity:"0"},spend);
            $('.usty-firstxf-three-list04 dl').delay(spend*0*0.5).animate({"margin-right":"-1000%",opacity:"0"},spend);
            $('.usty-firstxf-three-list03').delay(spend*1*0.5).animate({"margin-left":"80%",opacity:"0"},spend);
            $('.usty-firstxf-three-list03 dl').delay(spend*1*0.5).animate({"margin-left":"-1000%",opacity:"0"},spend);
            $('.usty-firstxf-three-list02').delay(spend*2*0.5).animate({"margin-right":"80%",opacity:"0"},spend);
            $('.usty-firstxf-three-list02 dl').delay(spend*2*0.5).animate({"margin-right":"-1000%",opacity:"0"},spend);
            $('.usty-firstxf-three-list01').delay(spend*3*0.5).animate({"margin-left":"80%",opacity:"0"},spend);
            $('.usty-firstxf-three-list01 dl').delay(spend*3*0.5).animate({"margin-left":"-1000%",opacity:"0"},spend);
        }
    }

    thisInit();

	$.fn.fullpage.reBuild(true);
	window.onresize = $.echartManager.resize;
	
	$("#down_share").click(function (){
		 if (confirm("你确定要将此网页连接分享至互联网吗？")) {
                 window.location.href="${ctx}/four/share";
	     }
	});
    $("#down_share_del").click(function (){
        if (confirm("你要取消分享吗？")) {
            window.location.href="${ctx}/four/share/del";
        }
    });
	
   $('#loginLoging').css('display','none');
   $('#dowebok').css('display','');
   
	
});
</script>

</body>
</html>