<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
	<title>我的大学生活</title>
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
         <a href="#" class="usty-user">${sessionScope.USER_EXPAND_INFO.realName}</a>
       </div>
       <div role="navigation" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
         <ul class="nav navbar-nav">
           <li><a class="title_a">${sessionScope.USER_EXPAND_INFO.userName}</a></li>
           <li><a class="title_a">${sessionScope.USER_EXPAND_INFO.deptName}</a></li>
           <li><a class="title_a">上次登录：${sessionScope.USER_EXPAND_INFO.lastLoginTime}</a></li>
           <li><a type="button" id="down_button" href="javascript:void(0)" class="title_a">下载图片</a></li>
         </ul>
         <ul class="nav navbar-nav navbar-right hidden-sm">
           <li><a class="title_a" href="${ctx}/logout">注销</a></li>
         </ul>
       </div>
     </div>
</div>
<div id="dowebok">
	<div class="section index-page usty-first-bottom" >
    <!--第一屏的第一屏-->
    <div class="usty-menu-height"></div>
    <div class=" usty-common-auto usty-first">
      <div class="usty-first-imgbg">
        <div class="usty-first-p">
          <div class="usty-first-f14">2009-09-01进入</div>
          <div class="usty-first-f30">我的母校</div>
          <div class="usty-first-f14">开启了<span class="usty-first-f30">我</span>的</div>
          <div class="usty-first-f24">校园生活</div>
        </div>
        <div class="usty-first-left01">
          <article class="usty-first-leftimg01"><a href="#page8"> </a> </article>
        </div>
        <div class="usty-first-left02">
          <article class="usty-first-leftimg02"> <a href="#page7"> </a></article>
        </div>
        <div class="usty-first-left03">
          <article class="usty-first-leftimg03"> <a href="#page6"> </a></article>
        </div>
        <div class="usty-first-left04">
          <article class="usty-first-leftimg04"><a href=#page5> </a> </article>
        </div>
        <div class="usty-first-right01">
          <article class="usty-first-rightimg01"><a href="#page2"> </a> </article>
        </div>
        <div class="usty-first-right02">
          <article class="usty-first-rightimg02"><a href="#page3"> </a> </article>
        </div>
        <div class="usty-first-right03">
          <article class="usty-first-rightimg03"><a href="#page4"> </a> </article>
        </div>
        <div class="usty-first-right04">
          <article class="usty-first-rightimg04"><a href="#page1"> </a> </article>
        </div>
      </div>
      <div class="usty-first-text">
        <ul class="usty-first-ul">
          <li><a href="#page2">第一次</a></li>
          <li class="padd-left90"><a href="#page3">消费</a></li>
          <li class="padd-left130"><a href="#page4">图书馆</a></li>
          <li class="padd-left20"><a href="#page1">上网</a></li>
          <li class="padd-left110"><a href="#page5">成绩</a></li>
          <li class="padd-left170"><a href="#page6">人脉</a></li>
          <li class="padd-left100"><a href="#page7">奖惩</a></li>
          <li><a href="#page8">标签墙</a></li>
        </ul>
      </div>
    </div>
    <!--第一屏的第一屏-->
  </div>


				<div class="section" data-method="getFirstPayLog" id="getFirstPayLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-firstxf"> 
				      <div class="usty-firstxf-line">
				        <h3 class="usty-firstxf-h3">第一次消费</h3>
				        <ul>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt01">时间</dt>
				              <dd id="getFirstPayLogP1"></dd> 
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt02">金额</dt>
				              <dd id="getFirstPayLogP2"></dd> 
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt03">消费类型</dt>
				              <dd id="getFirstPayLogP3"></dd> 
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt  class="usty-firstxf-dt04">地点</dt>
				              <dd id="getFirstPayLogP4"></dd> 
				            </dl>
				          </li>
				        </ul>
				      </div> 
				    </div>
				</div>
		
				<div class="section" data-method="getFirstBookLog" id="getFirstBookLog">
				    <div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-firstxf-four">
				        <div  class="usty-firstxf-four-list01">
				          <article>
				            <dl>
				              <dt>刷卡</dt>
				              <dt>踏入图书馆 </dt>
				              <dd > <span class="color-time-imp"> 时间：</span> </dd>
				              <dd class="text-align-right" id="getFirstBookLogP1"> 2012-06-02 </dd>
				              <dd class="text-align-right-indent"> 12:08</dd>
				            </dl>
				            <dl>
				              <dt>借书 </dt>
				              <dd class=" "> <span class="color-time-imp"> 书名：</span><span id="getFirstBookLogP2">《围城》</span></dd>
				              <dd class=" text-align-float"  > <span class="color-time-imp"> 时间：</span></dd>
				              <dd class=" text-align-center-float" id="getFirstBookLogP3"> 2011-09-02 <br />
				                14:25</dd>
				            </dl>
				          </article>
				          <div class="usty-firstxf-four-bottom">第一次</div>
				        </div>
      				</div>
				</div>
			
				<div class="section" data-method="getFirstAwardLog" id="getFirstAwardLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-firstxf-three">
				        <article class="usty-firstxf-three-list01">
				          <dl>
				            <dt>第一次获得奖学金 </dt>
				            <dd  class="usty-firstxf-three-padright" id="getFirstAwardLogP1">金额：3000元 &nbsp;&nbsp;  时间：2012-06-02</dd>
				          </dl>
				        </article>
				        <article class="usty-firstxf-three-list02">
				          <dl>
				            <dt  class="heng-2color">第一次获得荣誉称号 </dt>
				            <dd id="getFirstAwardLogP2"> 时间：2012-06-02</dd>
				          </dl>
				        </article>
				        <article class="usty-firstxf-three-list03">
				          <dl>
				            <dt class="heng-3color">第一次获得助学金 </dt>
				            <dd  class="usty-firstxf-three-padright"  id="getFirstAwardLogP3">金额：3000元 &nbsp;&nbsp;  时间：2012-06-02</dd>
				          </dl>
				        </article>
				     <!--   <article class="usty-firstxf-three-list04">
				          <dl>
				            <dt class="heng-4color">第一次贷款 </dt>
				            <dd class="usty-firstxf-three-padleft">金额：3000元 &nbsp;&nbsp;  时间：2012-06-02</dd>
				          </dl>
				        </article> -->
				    </div>
				</div>
			
				<div class="section" data-method="getFirstDownLog" id="getFirstDownLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-firstxf-two">
				        <div class="usty-firstxf-two-img"> </div>
				        <div class="usty-firstxf-two-list">
				          <dl>
				            <dt>第一次挂科</dt>
				            <dd id="getFirstDownLogP1">科目：化学工程实践 &nbsp;&nbsp;  时间：2012-06-02</dd>
				          </dl>
				          <dl>
				            <dt class="blue-bg">第一次违纪</dt>
				            <dd class="blue-font"> 时间：2012-06-02</dd>
				          </dl>
				        </div>
				    </div>
				</div>

		
				<div class="section" data-method="getSumMoneyLog" id="getSumMoneyLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-xiaofei">
				      <div class="usty-xiaofei-mingxi "> <a class="usty-shu-line01-button usty-xiaofei-button-style " href="#" >消费明细</a> </div>
				      <div class="usty-xiaofei-line">
				        <ul>
				          <li>
				            <dl>
				              <dt>消费共计</dt>
				              <dd><span class="usty-xiaofei-lispan" id="getSumMoneyLogP1"> --</span>元，</dd>
				              <dd><span class="usty-xiaofei-lispan" id="getSumMoneyLogP2"> -- </span>笔</dd>
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt>日均消费</dt>
				              <dd><span class="usty-xiaofei-lispan" id="getSumMoneyLogP3"> -- </span>元，</dd>
				              <dd><span class="usty-xiaofei-lispan"> </span> </dd>
				            </dl>
				          </li>
				          <li>
				            <dl>
				              <dt>消费共计</dt>
				              <dd>超越了<span class="usty-xiaofei-lispan" id="getSumMoneyLogP4"> --% </span></dd>
				              <dd>的同届学生</dd>
				            </dl>
				          </li>
				        </ul>
				      </div>
				      <div class="usty-shu-line03"></div>
				    </div>
				</div>
				
				<div class="section" data-method="getPayCount" id="getPayCount">
                    <div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-xiaofei-two">
				        <div class="usty-xiaofei-two-line">
				          <div class="usty-xiaofei-two-left usty-echartPie" id="pie"></div>
				          <ul class="usty-xiaofei-two-right">
				            <li>
				              <h3 class="usty-xiaofei-two-h3">最爱这里就餐</h3>
				              <dl  class="usty-xiaofei-two-dl01"  id="getPayCountCT">
				                <dt > </dt>


				              </dl>
				            </li>
				            <li >
				              <dl class="usty-xiaofei-two-dl02">
				                <dt > </dt>
				                <dd><span class="usty-xiaofei-two-imp">20% <span class="usty-xiaofei-two-bold">￥2000</span></span></dd>
				              </dl>
				            </li>
				          </ul>
				        </div>
				    </div> 
					<div ></div>
				</div>
			
				<div class="section" data-method="getWashLog" id="getWashLog">
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-chengji03">
				        <div class="usty-chengji03-title clearfix">
				          <div class="usty-chengji03-icon usty-xiaofei03-icon usty-chengji03-float-left"> <dl><dt>洗浴</dt><dd></dd></dl></div>
				          <ul class="usty-chengji03-ul usty-xiaofei03-ul usty-chengji03-float-left">
				            <li class="usty-chengji03-yellow">我的四年共洗浴  <span class="usty-chengji03-color-yellow" id="getWashLogP1">23次</span></li>
				            <li class="usty-chengji03-green">洗浴次数最多的男生为  <span class="usty-chengji03-color-green" id="getWashLogP2">230次</span> </li>
				            <li class="usty-chengji03-red">我超越了 <span class="usty-chengji03-color-red" id="getWashLogP3">10%</span> 的男生</li>
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
			
				<div class="section" data-method="getRecCount" id="getRecCount">
					<h3>充值统计</h3>
					<p>滚动到第三屏后的回调函数执行的效果</p>
				</div>
			
				<div class="section" data-method="getCardHabit" id="getCardHabit">
					<h3>消费习惯统计</h3>
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-chengji03">
				        <div class="usty-chengji03-title clearfix">
				          <div class="usty-chengji03-icon usty-xiaofei04-icon usty-chengji03-float-left"> <dl><dt>餐饮分析</dt><dd></dd></dl></div>
				          <ul class="usty-chengji03-ul usty-xiaofei04-ul  usty-chengji03-float-left">
				            <li class="usty-chengji03-yellow">吃早餐<span id="getCardHabitP1">0</span>天--超越<span class="usty-chengji03-color-yellow" id="getCardHabitP2"> 0%  </span>男生 </li>
				            <li class="usty-chengji03-green">吃午餐<span id="getCardHabitP3">0</span>天--超越 <span class="usty-chengji03-color-green" id="getCardHabitP4">0%</span>男生 </li>
				            <li class="usty-chengji03-red">吃晚餐<span id="getCardHabitP5">0</span>天--超越 <span class="usty-chengji03-color-red" id="getCardHabitP6">0%</span> 男生</li>
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

				<div class="section section1" data-method="getAllBorrowLog" id="getAllBorrowLog">
					<!-- <p id="getAllBorrowLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
					    <div class="usty-common-auto usty-shu"> 
					      <div class="usty-shu-mingxi"> <a class="usty-shu-line01-button" href="${ctx}/four/book/detail/${id}"  target=_blank>借书明细</a> </div>
					      <div class="usty-shu-line">
					        <article class="usty-shu-left">
					          <p class="usty-shu-textp">共借图书：<span class="usty-shu-text" id="getAllBorrowLogP1"></span>本 </p>
					          <p class="usty-shu-textp">本届人均借书：<span class="usty-shu-text" id="getAllBorrowLogP2"></span>本 </p>
					          <p class="usty-shu-textp">超越了<span class="usty-shu-text" id="getAllBorrowLogP3"></span>的本届学生</p>
					        </article>
					        <!-- 
					        <article class="usty-shu-right"> 
					          <p class="usty-shu-textp">借书频率最高的书：</p> 
					          <p class="usty-shu-textp"><span class="usty-shu-text">《化学工艺》</span>，借了<span class="usty-shu-text">19</span>次 </p>
					        </article>  -->
					      </div>
					      <div class="usty-shu-line03 usty-echart" id="bookBorrowLine" ></div>
					  </div>
				</div>
			
				<div class="section" data-method="getAllRKELog" id="getAllRKELog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-shu">
				        <div class="usty-shu-line">
				          <article class="usty-shu02-left"> <img src="${ctxStatic}/images/four/tushu-ceng.png"  /> </article>
				          <article class="usty-shu02-right ">
				            <p class="usty-shu02-textp ">共进出图书馆：<span class="usty-shu02-text" id="getAllRKELogP1"> 0 </span> 次 </p>
				            <p class="usty-shu02-textp ">超越了 <span class="usty-shu02-text  " id="getAllRKELogP2">0% </span>的本届学生 </p>
				            <p class="usty-shu02-textp ">进出图书馆高峰期：<span class="usty-shu02-text  " id="getAllRKELogP3">  </span> </p>
				          </article>
				        </div>
				        <div class="usty-shu-line03"><img src="${ctxStatic}/images/four/quxian02.png" /></div>
				    </div>
                    <div id="bookRKELine"></div>
				</div>
			
	
				<div class="section" data-method="getAvgScoreLog" id="getAvgScoreLog">
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
				          <p class="usty-shu-textp usty-chengji-textp">平均成绩：<span class="usty-shu-text usty-chengji-text" id="getAvgScoreLogP1"></span>分</p>
				          <p class="usty-shu-textp usty-chengji-textp">本专业平均成绩：<span class="usty-shu-text usty-chengji-text" id="getAvgScoreLogP2"></span>分</p>
				          <p class="usty-shu-textp usty-chengji-textp">超过了：<span class="usty-shu-text usty-chengji-text" id="getAvgScoreLogP3"></span>的学生</p>
				        </article>
				      </div>
				      <div class="usty-shu-line03 usty-echart" id="score2Line"></div>
				    </div>
				</div>
			
				<div class="section" data-method="getBestRankLog" id="getBestRankLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-chengji02">
				        <div class="usty-chengji02-title">
				          <h3>我的最好名次</h3>
				          <p><span id="getBestRankLogP1"></span><br />
				            凭借平均成绩 <span class="usty-chengji02-span" id="getBestRankLogP2">0分</span> 取得了大学以来的最好名次：<span class="usty-chengji02-span"  id="getBestRankLogP3">0名</span></p>
				        </div>
				        <div class="usty-chengji02-line" id="getBestRankLogP4">
				        </div>
				    </div>
				</div>
			
				<div class="section" data-method="getScoreCountLog" id="getScoreCountLog">
					<div class="usty-menu-height"></div>
				    <div class="usty-common-auto usty-chengji03">
				        <div class="usty-chengji03-title clearfix">
				          <div class="usty-chengji03-icon usty-chengji03-float-left"> <dl><dt>成绩分析</dt><dd></dd></dl></div>
				          <ul class="usty-chengji03-ul usty-chengji03-float-left">
				            <li class="usty-chengji03-yellow">综合总成绩<span id="getScoreCountLogP1"></span>分--超越 <span class="usty-chengji03-color-yellow" id="getScoreCountLogP2">0% </span>本专业学生</li>
				            <li class="usty-chengji03-green">综合平均成绩<span id="getScoreCountLogP3"></span>分--超越 <span class="usty-chengji03-color-green" id="getScoreCountLogP4">0% </span>本专业学生</li>
				            <li class="usty-chengji03-red">综合绩点<span id="getScoreCountLogP5"></span>分--超越 <span class="usty-chengji03-color-red" id="getScoreCountLogP6">0% </span>本专业学生</li>
				          </ul>
				          <div class="usty-chengji03-zong usty-chengji03-float-left" id="getScoreCountLogP7">70%</div>
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
			
				<div class="section" data-method="getRoommateLog" id="getRoommateLog">
					<!-- <p id="getRoommateLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
					    <div class="usty-common-auto usty-shiyou">
					      <div class="usty-shiyou-imgbg">
					        <div class="usty-shiyou-left00">
					          <article class="usty-shiyou-leftimg00"><a href="javascript:void(0)">
					            <div class="usty-shiyou-name">室友名字</div>
					            </a> </article>
					        </div>
					        <div class="usty-shiyou-left01">
					          <article class="usty-shiyou-leftimg01"> <a href="javascript:void(0)">
					            <div class="usty-shiyou-name">室友名字</div>
					            </a></article>
					        </div>
					        <div class="usty-shiyou-left02">
					          <article class="usty-shiyou-leftimg02"> <a href="javascript:void(0)">
					            <div class="usty-shiyou-name">室友名字</div>
					            </a></article>
					        </div>
					        <div class="usty-shiyou-right01">
					          <article class="usty-shiyou-rightimg01"><a href="javascript:void(0)">
					            <div class="usty-shiyou-name">室友名字</div>
					            </a> </article>
					        </div>
					        <div class="usty-shiyou-right02">
					          <article class="usty-shiyou-rightimg02"><a href="javascript:void(0)">
					            <div class="usty-shiyou-name">室友名字</div>
					            </a> </article>
					        </div>
					      </div>
					      <p class="usty-shiyou-textp">我的大学室友</p>
					    </div>
				</div>
	
				<div class="section" data-method="getTutorLog" id="getTutorLog">
					<p id="getTutorLogP" style="display: none;" class="textP"></p>
				</div>
			
		
				<div class="section" data-method="getAwardTimesLog" id="getAwardTimesLog">
					<!-- <p id="getAwardTimesLogP" style="display: none;" class="textP"></p> -->
					<div class="usty-menu-height"></div>
				      <div class="usty-common-auto usty-jiang">
				        <div class="usty-jiang-line">
				          <div class="usty-jiang-left ">
				            <p class="usty-jiang-textp">获得<span class="usty-jiang-text" id="getAwardTimesLogP1">4</span>次奖学金 </p>
				            <p class="usty-jiang-textp">共计<span class="usty-jiang-text" id="getAwardTimesLogP2">8000</span>元 </p>
				          </div>
				          <div class="usty-jiang-right ">
				            <p class="usty-jiang-textp">本届得奖次数超越人数：<span class="usty-jiang-text" id="getAwardTimesLogP3">50%</span> </p>
				            <p class="usty-jiang-textp">获奖金额超越人数：<span class="usty-jiang-text">80%</span> </p>
				          </div>
				        </div>
				        <div class="usty-jiang-leftimg01"> <img src="${ctxStatic}/images/four/002.png"  /> </div>
				        <div class="usty-jiang-rightimg01 "> <img src="${ctxStatic}/images/four/001.png" /> </div>
				        <div class="usty-jiang-rightimg02 "> <img src="${ctxStatic}/images/four/003.png"  /> </div>
			      </div>
				</div>
	
				<div class="section" data-method="getSubsidyLog" id="getSubsidyLog">
					<p id="getSubsidyLogP" style="display: none;" class="textP"></p>
				</div>
			

        
        <div class="section  usty-first-bottom" id="getMyTag">
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
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-bgcolor01"></article>
		          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-shadow"> </article>
		        </div>
		      </div>
		    </div>
	  </div>

</div>

	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/js/fullPage/jquery.fullPage.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts-all.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/jsEchar/echartManager.js"></script>
	<script type="text/javascript" src="${ctxStatic }/common/jsEchar/js-echar.js"></script>
    <script type="text/javascript" src="${ctxStatic }/js/html2canvas/html2canvas.min.js"></script>
	
<script>
$(function(){
	var nowSlide=0;
	$('#dowebok').fullpage({
		sectionsColor: ['#1bbc9b', '#4BBFC3', '#7BAABE', '#f90','#f90','#f90','#f90','#f90','#f90'],
		anchors: ['page1', 'page2', 'page3', 'page4', 'page5', 'page6', 'page7', 'page8'],
		'navigation': true,
		menu: '#menu'
	});
	
    
    var myChart;
    function thisInit(){

    	$.ajax({
   		     type: "POST",
   		     async:true,
   		     url:"${ctx}/four/first/book/${id}",
   		     success: function(data){
    		    $("#getFirstBookLogP1").html(data.data.FirstRKE.TIM);
   		    	$("#getFirstBookLogP2").html("《"+data.data.FirstBorrow.BOOK_NAME+"》");
                $("#getFirstBookLogP3").html(data.data.FirstBorrow.BORROW_TIME);
   		     }
   		 });
    	
    	$.ajax({
  		     type: "POST",
  		     async:true,
  		     url:"${ctx}/four/card/pay/count/${id}",
  		     success: function(data){
                myChart=$("#pie").echartpie({pid:"pie", title_text:"", title_subtext:"",series_name:"消费统计", data:data.data.PayCount});
                var html="<dt > </dt>";
                for(var i=0;i<data.data.LikeEat.length;i++){
                    html+="<dd><span>"+data.data.LikeEat[i].NAME_+"</span> <span>"+data.data.LikeEat[i].SUM_+" 元/"+data.data.LikeEat[i].COUNT_+"次</span> <span>"+data.data.LikeEat[i].COUNT_RATIO+" </span></dd>";
                }
                $("#getPayCountCT").html(html);
  		     }
  		 });
    	
    	$.ajax({
 		     type: "POST",
 		     async:true,
 		     url:"${ctx}/four/card/rec/count/${id}",
 		     success: function(data){
          //    	debugger;
 		     }
 		 });
    	
    	$.ajax({
   		     type: "POST",
   		     async:true,
   		     url:"${ctx}/four/book/borrow/${id}",
   		     success: function(data){
   		    	$("#getAllBorrowLogP1").html(data.data.SUMS);
   		    	$("#getAllBorrowLogP2").html(data.data.AV);
   		    	$("#getAllBorrowLogP3").html(data.data.MORE_THAN);

                var bookBorrowLine = data.data.borrowLine;
                bookBorrowLine.pid = "bookBorrowLine";
                $("#bookBorrowLine").echartline(bookBorrowLine,'macarons');
   		     }
   		 });
    	
    	$.ajax({
   		     type: "POST",
   		     async:true,
   		     url:"${ctx}/four/book/rke/${id}",
   		     success: function(data){
   		    	$("#getAllRKELogP1").html(data.data.AllRKELog.SUMS);
   		    	$("#getAllRKELogP2").html(data.data.AllRKELog.MORE_THAN);
   		    	$("#getAllRKELogP3").html(data.data.LikeRKETimeLog.TIME_+":00-"+data.data.LikeRKETimeLog.TIME_+":59");
                var bookRKELine = data.data.bookRKELine;
                bookRKELine.pid = "bookRKELine";
                $('#bookRKELine').echartline(bookRKELine,'macarons');
   		     }
   		 });

	    $.ajax({
		    type: "POST",
		    async:true,
		    url:"${ctx}/four/wash/${id}",
		    success: function(data){
		    	$("#getWashLogP1").html(data.data.getWashTimes[0].TIMES);
		    	$("#getWashLogP2").html(data.data.getWashTimes[0].TIMES);
		    	$("#getWashLogP3").html(data.data.getWashTimes[0].MORE_THAN);
	   		}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/sum/moneyAndTimes/${id}",
			success: function(data){
                $("#getSumMoneyLogP1").html(data.data.getSumMoneyAndPayTimes[0].MONEY);
                $("#getSumMoneyLogP2").html(data.data.getSumMoneyAndPayTimes[0].TIMES);
                $("#getSumMoneyLogP3").html(data.data.getSumMoneyAndPayTimes[0].AVG_M);
                $("#getSumMoneyLogP4").html(data.data.getSumMoneyAndPayTimes[0].MORE_THAN);

		  	}
	  	});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/award/times/${id}",
			success: function(data){
				$("#getAwardTimesLogP1").html(data.data.getAwardTimes[0].TIMES);
				$("#getAwardTimesLogP2").html(data.data.getAwardTimes[0].MONEY);
				$("#getAwardTimesLogP3").html(data.data.getAwardTimes[0].MORE_THAN);
			}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/first/award/${id}",
			success: function(data){
                $("#getFirstAwardLogP1").html("金额："+data.data.getFirstAwardMsg[0].AWARD_MONEY+"元&nbsp;&nbsp;时间："+data.data.getFirstAwardMsg[0].AWARD_TIME);
                $("#getFirstAwardLogP3").html("金额："+data.data.getFirstAwardMsg[0].SUBSIDY_MONEY+"元&nbsp;&nbsp;时间："+data.data.getFirstAwardMsg[0].SUBSIDY_TIME);
			}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/award/subsidy/${id}",
			success: function(data){
				$("#getSubsidyLogP").html("共获得了"+data.data.getSubsidyTimesAndMoney[0].TIMES+"次助学金。一共金额"+data.data.getSubsidyTimesAndMoney[0].MONEY+"");
			}
		});

		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/firstpay/msg/${id}",
			success: function(data){
				$("#getFirstPayLogP1").html(data.data.getFirstPayMsg[0].TIME_);
				$("#getFirstPayLogP2").html(data.data.getFirstPayMsg[0].MONEY+"元");
				$("#getFirstPayLogP3").html(data.data.getFirstPayMsg[0].STYLE);
				$("#getFirstPayLogP4").html(data.data.getFirstPayMsg[0].WINDOW);
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
				$("#getAvgScoreLogP1").html(thisData.AvgScore.AVG_);
				$("#getAvgScoreLogP2").html(thisData.AvgScore.AVG_ALL);
				$("#getAvgScoreLogP3").html(thisData.AvgScore.MORE_THAN);
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/score/rank/${id}",
			success: function(data){
				var thisData=data.data;
				var list=thisData.ScoureList;
				var showS="";
				var colors=['red','blue','green'];
				var cnum=['01','02'];
				var i=0,j=0;
				for (var int = 0; int < list.length; int++) {
					if(i==3){
						i=0;
					}
					if(j==2){
						j=0;
					}
					showS+="<article class='usty-chengji02-"+colors[i]+cnum[j]+"'><dl><dt>"+list[int].CENTESIMAL_SCORE+"分</dt>"+
				 			"<dd>"+list[int].SCORE_NAME+"</dd></dl></article>";
				 			i++;j++;
				}
				$("#getBestRankLogP1").html("在"+thisData.BestRank.SCHOOL_YEAR+"学年第"+thisData.BestRank.TERM_CODE+"学期");
				$("#getBestRankLogP2").html(thisData.BestRank.AVG_);
				$("#getBestRankLogP3").html(thisData.BestRank.RANK+"名");
				$("#getBestRankLogP4").html(showS);
			}
		});
		
		$.ajax({
			type: "POST",
			async:true,
			url:"${ctx}/four/score/count/${id}",
			success: function(data){
				var thisData=data.data;
				var allBest=thisData.BsetCourseAll;
				var allBestS="";
				var colors=['red','blue','green'];
				var cnum=['01','02'];
				var i=0,j=0;
				for (var int = 0; int < allBest.length; int++) {
					if(i==3){
						i=0;
					}
					allBestS+="<article class='usty-chengji02-"+colors[i]+cnum[0]+"'><dl><dt>"+allBest[int].MAX_+"分</dt>"+
				 			"<dd>"+allBest[int].COURSE_NAME+"</dd></dl></article>";
				 			i++;
				}
				var best=thisData.BsetCourse;
				var bestS="";
				for (var int = 0; int < best.length; int++) {
					if(i==3){
						i=0;
					}
					bestS+="<article class='usty-chengji02-"+colors[i]+cnum[1]+"'><dl><dt>"+best[int].MAX_+"分</dt>"+
				 			"<dd>"+best[int].COURSE_NAME+"</dd></dl></article>";
				 			i++;
				}
				$("#getScoreCountLogP1").html(thisData.SumScore.SUM_);
				$("#getScoreCountLogP2").html(thisData.SumScore.MORE_THAN);
				$("#getScoreCountLogP3").html(thisData.AvgScore.AVG_);
				$("#getScoreCountLogP4").html(thisData.AvgScore.MORE_THAN);
				$("#getScoreCountLogP5").html(thisData.GpaScore.GPA);
				$("#getScoreCountLogP6").html(thisData.GpaScore.MORE_THAN);
				$("#getScoreCountLogP7").html(thisData.AllScore);
				$("#getScoreCountLogP8").html(allBestS);
				$("#getScoreCountLogP9").html(bestS);
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
			url:"${ctx}/four/relation/room/${id}",
			success: function(data){
				var thisData=data.data;
				var list=thisData.roommate;
				var roomNameDom=$(".usty-shiyou-name");
				for (var int = 0; int < list.length; int++) {
					$(roomNameDom[int]).html(list[int].NAME_);
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
				var showS="";
				for (var int = 0; int < list.length; int++) {
					showS+= list[int].SCHOOL_YEAR+"学年，名字："+list[int].TEA_NAME+"<br/>";
				}
				$("#getTutorLogP").html("我的辅导员：<br/>"+showS);
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
                });
                //$("#breakfastInstrumentPanel").html("早餐平均值"+stuData.AVG_ZC+"与本届男生/女生平均值"+endYearData.AVG_ZC_ALL);
                //$("#lunchInstrumentPanel").html("午餐平均值"+stuData.AVG_MIDDLE+"与本届男生/女生平均值"+endYearData.AVG_MIDDLE_ALL);
                //$("#dinnerInstrumentPanel").html("晚餐平均值"+stuData.AVG_WC+"与本届男生/女生平均值"+endYearData.AVG_WC_ALL);

            }
        });
		
	}
    
            $("#getFirstBookLogP1").slideDown();
            $("#getFirstBookLogP2").slideDown();
            $("#getFirstDownLogP").slideDown();
            $("#getAllBorrowLogP").slideDown();

			$("#getAllRKELogP1").slideDown();
	   		$("#getAllRKELogP2").slideDown();


            $("#getWashLogP").slideDown();

            $("#getSumMoneyLogP").slideDown();


			$('.usty-firstxf-line li').animate({"margin-top": '0' }, 500);

			var spend=500;
	    	$('.usty-jiang-rightimg01').animate({top:"10%",opacity:"1"},spend);
	    	$('.usty-jiang-rightimg02').animate({right:"10%",opacity:"1"},spend);

            var spend=500;
            $('.usty-firstxf-three-list01').delay(spend*0*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list01 dl').delay(spend*0*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list02').delay(spend*1*0.5).animate({"margin-right":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list02 dl').delay(spend*1*0.5).animate({"margin-right":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list03').delay(spend*2*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list03 dl').delay(spend*2*0.5).animate({"margin-left":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list04').delay(spend*3*0.5).animate({"margin-right":"0%",opacity:"1"},spend);
            $('.usty-firstxf-three-list04 dl').delay(spend*3*0.5).animate({"margin-right":"0%",opacity:"1"},spend);


			$("#getSubsidyLogP").slideDown();

			$("#getAvgScoreLogP").slideDown();

            var spend=500;
            $('.usty-shiyou-left00').animate({top: '0',opacity:"1" }, spend);
            $('.usty-shiyou-left01').animate({left: '0',opacity:"1" }, spend);
            $('.usty-shiyou-right01').animate({right: '0',opacity:"1" }, spend);
            $('.usty-shiyou-left02').animate({left: '14%',top:"72%",opacity:"1" }, spend);
            $('.usty-shiyou-right02').animate({right: '14%',top:"72%",opacity:"1" }, spend);


	    	$("#getTutorLogP").slideDown();

    thisInit();
    
    $.fn.fullpage.setAutoScrolling(false);
	$.fn.fullpage.reBuild(true);
	window.onresize = $.echartManager.resize;

	$("#down_button").click(function() {
		// $("#down_button").attr("disabled","disabled");
			html2canvas($('#dowebok'), {
				onrendered : function(canvas) {
					var myImage = canvas.toDataURL("image/png");
					//并将图片上传到服务器用作图片分享
					alert(myImage.length);
					$.ajax({
						type : "POST",
						url : '${ctx}/four/img/upload',
						data : {data:myImage},
						timeout : 60000,
						success : function(data){
							var imsSrc="${ctxStatic}/usertemp/"+data+".png";
			//				$("#down_button").removeAttr("disabled"); 
							window.location.href="${ctx}/four/img/down/"+data;
							
						}
					});
				}
			});
		});

});

</script>

</body>
</html>