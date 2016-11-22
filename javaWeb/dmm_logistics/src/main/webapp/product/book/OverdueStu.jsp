<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>学生逾期书籍分析</title>
</head>
<body ng-controller="OverdueStuController">
	<div id="wrapper">
	<%-- 	<jsp:include page="../../product/left.jsp"></jsp:include> --%>
		<script type="text/javascript" src="${ctx }/product/book/js/OverdueStu.js"></script>
    <div class="content">
        
        	<div class="xscz-head content_head">
                <h3 class="xscz-fff">逾期书籍分析</h3> 
                <p class="xscz-default">对逾期者人群类型、逾期书籍类型、逾期者所属院系分布以及逾期书籍历史变化趋势的分析对比。</p> 
            </div>
            
            <div class="content_main">
            
            	<div class="booksjy_top_imges">
                	<p class="weihuanps"><i class="icon-info-sign"></i>当前逾期未还书籍<a href="" ng-click="getxqlb(9)" class="colorr">{{vm.items[0]}}</a>本。</p>
                  <div cg-combo-nyrtj result="date"></div>
                   <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 
                    <div class="yuqi_top_img">
                    	<div class="fl">
                            <div id="octagon" class="fr">
                                <div id="liub"><h4>逾期已还书籍</h4></div>
                                <ul>
                                   <li class="text_bold"><a href="" ng-click="getxqlb(10)"class="colorr">{{vm.items[4].NUMS}}</a></li>
                                   <li>本</li>
                                </ul>
                            </div>
                        </div>
                        <div class="fl">
                            <div id="octagon" class="fl">
                                <div id="liub" class="liub_blue"><h4>逾期率 &nbsp;<i class="icon-info-sign" title="计算方式：逾期已还书籍/借书总书籍)"></i></h4></div>
                                <ul>
                                   <li class="text_bold">{{vm.items[4].NUMRATE}}</li>
                                   <li>‱</li>
                                </ul>
                            </div>
                        </div>
                        
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
               <!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------>
               <style>
               .h4_16 span {
					    margin-left: 10px;
					    color: #2eadb4;
					}
               </style>
                <div class="fenbu">
                	<div class="renqunleix">
                    	<div class="t">
                        	<h5>人群各类型对比情况</h5>
                        </div>
                        <div class="renyuan_fenbu_con">
                            <div class="fl leixing">
                                <h4 class="h4_16">已还书籍人数对比
                                	<span class="icon-time" ng-click="qushiClick(1);"></span>
                                	</h4>
                                 <div class="shuliang_img">
                        	 	<div stu-chart config="vm.items[5]" style="height:310px;"class="img-responsive img_marg img-top" style="padding-top:36px;"> </div>
                              <%--   <img src="${images}/books_images/whsjrsdb.jpg" class="img-responsive img_marg img-top" style="padding-top:36px;"> --%>
                           	 	</div>
                            </div>
                           
                            <div class="fl zhicheng">
                                <h4 class="h4_16">逾期率对比
                                <span class="icon-time" ng-click="qushiClick(2);"></span>
                                </h4>
                                 <div class="shuliang_img">
                        		
                                 <div stu-chart config="vm.items[6]" style="height:310px;"class="img-responsive img_marg img-top" style="padding-top:36px;"> </div>
                               <%--  <img src="${images}/books_images/yqldb.png" class="img-responsive img_marg img-top"> --%>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <!------------------------------------------------- 以上人群各类型对比情况---------------------------------------------->

                <div class="guoqi">
                	<div class="t">
            			<h5>各类型数据对比情况</h5>
                	</div>
                    <div class="danxuan"> 
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_1" id="sl_053_1" value="0" checked="" ng-model="radio1id">
                            <label for="sl_053_1" class="fw-nm">
                              数量
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_1" id="yql_053_1" value="1" ng-model="radio1id">
                            <label for="yql_053_1" class="fw-nm">
                              逾期率
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_1" id="psyqsc_053_1" value="2" ng-model="radio1id">
                            <label for="psyqsc_053_1" class="fw-nm">
                              平时逾期时长
                            </label>
                        </div>
                   <!--      <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_1" id="yqscfb_053_1" value="3">
                            <label for="yqscfb_053_1" class="fw-nm">
                              逾期时长分布
                            </label>
                        </div> -->
                    </div>
                    <div>
                      <div stu-chart config="radio1data" style="height:310px;"class="img-top"> </div>
                    <%-- 	<img src="${images}/books_images/cangshuliang.png" class="img-top"> --%>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!--------------------------------------------------以上各类型数据对比情况---------------------------------------------->
                <div class="guoqi">
                	<div class="t">
            			<h5>逾期书籍所属{{deptlname}}分布情况</h5>
                	</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_2" id="sl_053_2" value="0" checked="" ng-model="radio2id">
                            <label for="sl_053_2" class="fw-nm">
                              数量
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_2" id="yql_053_2" value="1"ng-model="radio2id">
                            <label for="yql_053_2" class="fw-nm">
                              逾期率
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_2" id="psyqsc_053_2" value="2"ng-model="radio2id">
                            <label for="psyqsc_053_2" class="fw-nm">
                              平时逾期时长
                            </label>
                        </div>
                       <!--  <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_2" id="yqscfb_053_2" value="3"ng-model="radio2id">
                            <label for="yqscfb_053_2" class="fw-nm">
                              逾期时长分布
                            </label>
                        </div> -->
                    </div>
                    <div>
                    <div stu-chart config="radio2data" style="height:310px;"class="img-top"> </div>
                    	<%-- <img src="${images}/books_images/yqzssyxfb_sl.png" class="img-top"> --%>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!---------------------------------------------------以上为逾期者所属院系分布情况----------------------------------------------->
                <div class="guoqi">
                	<div class="t">
            			<h5>逾期书籍按学年分布情况</h5>
                	</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_3" id="sl_053_3" value="0" checked="" ng-model="radio3id">
                            <label for="sl_053_3" class="fw-nm">
                              数量
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio053_3" id="yql_053_3" value="1" ng-model="radio3id">
                            <label for="yql_053_3" class="fw-nm">
                              逾期率
                            </label>
                        </div>
                    </div>
                    <div style="margin-bottom:100px;">
                      <div stu-chart config="radio3data" style="height:310px;"class="img-top"> </div>
                    <%-- 	<img src="${images}/books_images/yqzssyxfb_sl_01.jpg" class="img-top"> --%>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!-----------------------------------------------------以上逾期者所属院系分布情况------------------------------------------>
               
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