<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>图书借阅分析</title>
</head>
<body ng-controller="BookReadController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/book/js/Read.js"></script>    
    
   <div class="content">
        
        	<div class="xscz-head content_head">
                <h3 class="xscz-fff">图书借阅分析</h3> 
                <p class="xscz-default">对学校图书馆图书借阅流通进行分析，包括流通量、借阅者类型、借阅时段以及图书分类借阅情况。</p> 
            </div>
            
            <div class="content_main">
                  <div cg-combo-nyrtj result="date"></div>
            	<div class="booksjy_top_imges">
             <!--    	<div class="top_date">
                    	<div id="liub" class="fl"></div>
                    	<div class="fl date_line"></div>
                        <div id="liub_r" class="fr"></div>
                        <div class="date_con">
                        	<a href="#" class="date_active">本月</a>
                            <a href="#">近3月</a>
                            <a href="#">近半年</a>
                            <b>起始日<span>-</span>结束日</b>
                        </div>
                        <div class="clearfix"></div>
                    </div> -->
                    <div class="jieyue_top_img">
                    	<div class="fl">
                            <div id="octagon">
                                <div id="liub"><h4>流通量</h4></div>
                                <ul>
                                   <li class="text_bold" ng-click="getxqlb(10);"><a href="" class="colorr">{{vm.items[4].NUMS}}</a></li>
                                   <li>本</li>
                                </ul>
                            </div>
                        </div>
                        <div class="fl">
                            <div id="octagon">
                                <div id="liub"><h4>日均流通量</h4></div>
                                <ul>
                                   <li class="text_bold">{{vm.items[4].AVGNUMS}}</li>
                                   <li>本</li>
                                </ul>
                            </div>
                        </div>
                        <div class="fl">
                            <div id="octagon">
                                <div id="liub"><h4>流通率</h4></div>
                                <ul>
                                   <li class="text_bold">{{vm.items[4].NUMRATE}}</li>
                                   <li>%</li>
                                </ul>
                            </div>
                        </div>
                        <div class="fl">
                            <div id="octagon">
                                <div id="liub"><h4>续借率</h4></div>
                                <ul>
                                   <li class="text_bold">{{vm.items[4].RENEWRATE}}</li>
                                   <li>%</li>
                                </ul>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
               <!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------>
                <div class="fenbu">
                	<div class="shuliang fl">
                    	<div class="t">
                        	<h5>分类型统计人均借书量</h5>
                        </div>
                        <div class="shuliang_img">
                        	<span class="icon-time" ng-click="qushiClick(0);"></span>
                        	<div stu-chart config="vm.items[5]" style="height:310px;"class="img-top"> </div>
                        	<%-- <img style="padding-top:0;" src="${images}/books_images/xianyouduzhe.jpg" alt="" class="img-top"> --%>
                        </div>
                    </div>
                    <div class="duzheleix fl">
                    	<div class="t">
                        	<h5>分类型统计借书人数占比</h5>
                        </div>
                        <div class="duzheleix_img">
                        	<span class="icon-time"  ng-click="qushiClick(1);"></span>
                        	<div stu-chart config="vm.items[6]" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/xianyouduzhe.jpg" alt="" class="img-top"> --%>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!------------------------------------------------- 以上书籍对比  现有读者类型对比情况---------------------------------------------->

                <div class="guoqi">
                	<div class="t">
            			<h5>通过馆藏类别分析图书借阅情况</h5>
                	</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio01" id="liut" value="0" checked=""ng-model="radio6id">
                            <label for="liut" class="fw-nm">
                              流通量
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio01" id="ltl" value="1"ng-model="radio6id">
                            <label for="ltl" class="fw-nm">
                              流通率
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio01" id="lyl" value="2"ng-model="radio6id">
                            <label for="lyl" class="fw-nm">
                              利用率
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio01" id="xjl" value="3"ng-model="radio6id">
                            <label for="xjl" class="fw-nm">
                              续借率
                            </label>
                        </div>
                    </div>
                    <div class="flxzs">
                    	<!-- <h4 class="h4_16">分类型展示图书流通量<span class="icon-time span_icon"></span> --></h4>
                    	<div stu-chart config="radio6data" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/cangshuliang.png" class="img-top"> --%>
                    </div>
                   <!--  <p class="ps"><i class="icon-info-sign"></i>我校<span>医药卫生</span>图书<span>外借率较高</span>，然而藏书库存较低。<span>文学类</span>图书<span>外借率较低</span>，但藏书库存较高。所以还需进一步优化与调整图书馆进书方向。</p>
                     -->
                     <div class="clearfix"></div>
                </div>
                <!--------------------------------------------------以上通过馆藏类别分析图书借阅情况---------------------------------------------->
                <div class="guoqi">
                	<div class="t">
            			<h5>分学院分析学生借阅情况</h5>
                	</div>
                    <div>
                    	<!-- <span class="icon-time fr span_icon" style="margin-bottom:20px;"></span> -->
                    	<div stu-chart config="vm.items[8]" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/fenxueyuan.png" class="img-top"> --%>
                    </div> 
                   <!--  <p class="ps"><i class="icon-info-sign"></i>我校<span>外语学院</span>学生借书学习<span>利用率较高</span>，而<span>文学院</span>、<span>艺术学院</span>等则<span>偏低</span>，需要进一步加强学生学习意识。</p> 
                     -->
                    <div class="clearfix"></div>
                </div>
                <!---------------------------------------------------以上为分学院分析学生借阅情况----------------------------------------------->
                <div class="guoqi">
                	<div class="t">
            			<h5>各类人群借阅书籍时段情况</h5>
                	</div>
<!--                     <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="zk" value="zk" checked="">
                            <label for="zk" class="fw-nm">
                              专科生
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="bk" value="bk">
                            <label for="bk" class="fw-nm">
                              本科生
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="yjs" value="yjs">
                            <label for="yjs" class="fw-nm">
                              研究生
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="jsh" value="jsh">
                            <label for="jsh" class="fw-nm">
                              教师
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="qt" value="qt">
                            <label for="qt" class="fw-nm">
                              其他
                            </label>
                        </div>
                    </div> -->
                    <div>
                    	<div stu-chart config="vm.items[9]" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/zhuanke.jpg" class="img-top"> --%>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!-----------------------------------------------------以上为各类人群借阅书籍时段情况------------------------------------------>
                <div class="guoqi pdbtm_box">
                	<div class="t">
            			<h5>图书借阅历史趋势</h5>
                	</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio03" id="year" value="year" checked="" ng-model="radio3id">
                            <label for="year" class="fw-nm">
                              按年
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio03" id="month" value="month"ng-model="radio3id">
                            <label for="month" class="fw-nm">
                              按月
                            </label>
                        </div>
                    </div>
                    <div>
                    	<div stu-chart config="radio3data" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/annian.jpg" class="img-top"> --%>
                    </div>
                   <!--  <p class="ps"><i class="icon-info-sign"></i>我校图书<span>借阅高峰期</span>一般处于<span>开学前后</span>，说明学生阅读积极性比较高，大部分学生会选择在假期依然保持阅读习惯。</p> 
                     --><div class="clearfix"></div>
                </div>
            </div>              	
        </div>
  
  
  
    </div>
</body>
<div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
 <div stu-chart config="qushiData" class="qsdivc" ></div>
 </div>
  <div cg-combo-xz data="pagexq" type=''></div>
</html>