<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>藏书概要统计分析</title>
</head>
<body ng-controller="BookInfoController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/book/js/Info.js"></script>    
    
        <div class="content" >
        
        	<div class="xscz-head content_head">
                <h3 class="xscz-fff">藏书概要统计分析</h3> 
                <p class="xscz-default">对学校图书馆藏书数量、价值、类别以及读者和人均量进行分析。</p> 
            </div>
            <div class="content_main">
            	<div class="books_top_imges" >
                	<div class="books_l fl">
                    	<div class="bookimg fl">
                        	<div class="bookimg_top">
                                <ul class="fl">
                                    <li>共拥有</li>
                                    <li class="li_bold" ng-click="getxqlb(7);"><a href="" class="colorr">{{vm.items[0].NOWBOOKS/10000 | number :2}}</a></li>
                                    <li>万本</li>
                                </ul>
                                <ul class="fr">
                                    <li>总价值</li>
                                    <li class="li_bold">{{vm.items[0].NOWMONEYS/10000 | number :2}}</li>
                                    <li>万元</li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div id="liub"><h4>图书馆藏书</h4></div>
                        </div>
                        <div class="else fl">
                             <div id="liub"><p>其中</p></div>
                             <div class="clearfix"></div>
                    	</div>
                        <div class="xingou fl">
                            <div id="octagon">
                                <div id="liub"><h4>今年新购书籍</h4></div>
                                <ul class="ul_1">
                                    <li>数量</li>
                                    <li>价值</li>
                                </ul>
                                <ul class="ul_2">
                                    <li ng-click="getxqlb(9);"><span class="text_bold"><a class="colorr" href="">{{vm.items[0].NEWBOOKS/10000 | number :2}}</a></span>万本</li>
                                    <li><span class="text_bold">{{vm.items[0].NEWMONEYS/10000 | number :2}}</span>万元</li>
                                </ul>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="books_r fl">
                    	<div>
                            <div id="octagon">
                                <div id="liub"><h4>总读者数</h4></div>
                                <ul>
                                    <li class="text_bold" ng-click="getxqlb(8);"><a class="colorr" href="">{{vm.items[0].NOWREADERS}}</a></li>
                                    <li>人</li>
                                </ul>
                            </div>
                        </div>
                        <div>
                            <div id="octagon">
                                <div id="liub"><h4>人均保有量</h4></div>
                                <ul>
                                    <li class="text_bold">{{vm.items[0].PEOPLEHAS}}</li>
                                    <li>本</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
               <!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------>
                <div class="fenbu">
                	<div class="shuliang fl">
                    	<div class="t">
                        	<h5>书籍分状态数量对比</h5>
                        </div>
                        <div class="shuliang_img">
                        	<!-- <span class="icon-time" ng-click="qushiClick(2);"></span> -->
                        	
                        	<div stu-chart config="vm.items[1]" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/zhongwai.png" alt="" class="img-top"> --%>
                        </div>
                    </div>
                    <div class="duzheleix fl">
                    	<div class="t">
                        	<h5>现有读者类型对比</h5>
                        </div>
                        <div class="duzheleix_img">
                         <div tool-tip placement="left" hide-icon="true" class="icon-time"style="float:right" ng-click="qushiClick(4);">
									<div style="width: 60px">查看趋势</div>
								</div>
                        	<!-- <span class="icon-time"  ng-click="qushiClick(4);"></span> -->
                        	<div stu-chart config="vm.items[3]" style="height:310px;"class="img-top"> </div>
                        	<%-- <img src="${images}/books_images/xianyouduzhe.jpg" alt="" class="img-top"> --%>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!------------------------------------------------- 以上书籍对比  现有读者类型对比情况---------------------------------------------->

                <div class="guoqi" >
                	<div class="t t_mid">
            			<a href="" ng-click="vmitems5idex(0);"><h5 ng-class="vm5idex==0?'active':''">藏书数量</h5></a>
                        <a href="" ng-click="vmitems5idex(1);"><h5 ng-class="vm5idex==1?'active':''">藏书价值</h5></a>
                        <a href="" ng-click="vmitems5idex(2);"><h5 ng-class="vm5idex==2?'active':''">文献保障率</h5></a>
                	</div>
                    <div class="flxzs">
                    	<h4 class="h4_16">{{vmitems5title}}<!-- <span class="icon-time span_icon"></span> --></h4>
                    	<div stu-chart config="vmitems5" style="height:310px;"class="img-top"></div>
                    	<%-- <img src="${images}/books_images/cangshuliang.png" class="img-top"> --%>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!--------------------------------------------------以上各机构（单位）对比情况趋势---------------------------------------------->
                <div class="guoqi" >
                	<div class="t t_mid">
            			<a href=""ng-click="vmitems6idex(0);"><h5 ng-class="vm6idex==0?'active':''">藏书总数</h5></a>
                        <a href=""ng-click="vmitems6idex(1);"><h5 ng-class="vm6idex==1?'active':''">藏书增长量</h5></a>
                	</div>
                    <div class="flxzs">
                    	<h4 class="h4_16">藏书{{vmitems6title}}年度情况</h4>
                    	<div stu-chart config="vmitems6" style="height:310px;"class="img-top"></div>
                    	<%-- <img src="${images}/books_images/cangshumeinian.png" class="img-top" style="padding-bottom:50px;"> --%>
                    </div>  
                    <div class="clearfix"></div>
                </div>
            </div>              	
        </div>
        <div class="clearfix"></div>
    </div>
</body>
 <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
 <div stu-chart config="qushiData" class="qsdivc" ></div>
 </div>
  <div cg-combo-xz data="pagexq" type=''></div>
</html>