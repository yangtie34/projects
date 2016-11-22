<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images" />
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>教师上网排名</title>
</head>
<body ng-controller="netTeaRankController">
	<div id="wrapper">
		<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
		<script type="text/javascript"
			src="${ctx }/product/net/js/netTeaRank.js"></script>

		<div class="content">
			<div class="xscz-head content_head">
				<h3 class="xscz-fff">教师上网排名</h3>
				<p class="xscz-default">对学校教师上网记录进行统计排名</p>
			</div>
			<div class="content_main">
			            	<div class="jieyuepm_section">
                    	<div class="richangpm">
                        	<div class="fr">
                                <img src="${images}/books_images/border_blue_top.png" class="img-top" ng-show="!ndrypm">
                                <img src="${images}/border_15.png" class="img-top" ng-show="ndrypm">
                                <div ng-class="ndrypm?'xuanzhong_no':'xuanzhong'" ng-click="ndrypm=false;flowOrtimeClick('flow');">
                                	<a href=""><div id="liub"><h4>按流量</h4></div></a>
                                </div>
                                <img src="${images}/books_images/border_blue_bottom.png" class="img-top" ng-show="!ndrypm">
                                <img src="${images}/border_18.png" class="img-top" ng-show="ndrypm">
                                <p class="triangle_down" ng-show="!ndrypm"></p>
                            </div>      
                        </div>
                        <div class="niandupm">
                        	<div class="fl">
                                 <img src="${images}/books_images/border_blue_top.png" class="img-top" ng-show="ndrypm">
                                <img src="${images}/border_15.png" class="img-top" ng-show="!ndrypm">
                                <div ng-class="!ndrypm?'xuanzhong_no':'xuanzhong'" ng-click="ndrypm=true;flowOrtimeClick('time');">
                                	<a href=""><div id="liub"><h4>按时长</h4></div></a>
                                </div>
                                   <img src="${images}/books_images/border_blue_bottom.png" class="img-top" ng-show="ndrypm">
                                <img src="${images}/border_18.png" class="img-top" ng-show="!ndrypm">
                                 <p class="triangle_down" ng-show="ndrypm"></p>
                            </div>
                        </div>
                    </div>
                    
					<div class="booksjy_top_imges">

						<div class="clearfix"></div>
						<div cg-combo-nyrtj result="date" yid=1></div>
				<div cg-mul-query-comm source="mutiSource" result="deptResult"
									noborder="true"></div>

						<div class="clearfix"></div>
					</div>
					<!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------>
								<div class="t"
									style="
						    text-align: left;
						    padding-bottom: 0px;
						        border-bottom: 1px solid #7a8aa3;
						">
									<h4 >教师上网{{type}}排名</h4>
								</div>
								
				<div cg-report-table resource="tableData"
							class=" xscz-ft-18"></div>				
								
			</div>
			<div class="clearfix"></div>
		</div>
</body>

 <div cg-combo-xz data="pageXq" type=''></div>
</html>