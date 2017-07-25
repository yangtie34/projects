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
				
                            选择排名：
                                                        <div class="danxuan">
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="xs_053_41" value=10 checked="" ng-model="rank">
                                    <label for="xs_053_41" class="fw-nm">
                                      top10
                                    </label>
                                </div>
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="js_053_41" value=20 ng-model="rank">
                                    <label for="js_053_41" class="fw-nm">
                                     top20
                                    </label>
                                </div>
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="sj_053_41" value=100 ng-model="rank">
                                    <label for="sj_053_41" class="fw-nm">
                                      top100
                                    </label>
                                </div>
                                 <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="sj1_053_41" value=10000000 ng-model="rank">
                                    <label for="sj1_053_41" class="fw-nm">
                                      全部
                                    </label>
                                </div>
                            </div>
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
									<h4 >教师上网{{type=='flow'?'流量':'时长'}}排名</h4>
								</div>
								 <br>
								  <p class="pad-r-10 text-right"><a href="" ng-click="exportExcel();"title="导出列表"><img src="${images}/send.png" alt="">导出</a></p>
                                  <!--  -->
				                    <div class="tab">
                    <!-- 	<div class="tab_tit">
                        	<a href="" ng-class="queryTypeindex==0?'tab_active':''" ng-click="queryTypeClick(0);">个人排名</a>
                            <a href=""ng-class="queryTypeindex==1?'tab_active':''" ng-click="queryTypeClick(1);">单位排名</a>
                        </div> -->
                        <table class="table table-change"> 
                           <thead>
                              <tr>
                                 <th ng-repeat="item in titles.name">{{item}}</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr class="tab_dashed" ng-repeat="item in vm.items[0]">
                                 <td ng-repeat="ite in titles.code">{{item[ite]}}</td>
                              </tr>
                            <!--   <tr>
                                 <td>2</td>
                                 <td><a href="" class="colorr">124123</a></td>
                                 <td><a href="" class="colorr">刘佳媛</a></td>
                                 <td>科研室</td>
                                 <td><a href="" class="colorr">7</a>（贵：<a href="" class="colorr">5</a>）</td>
                                 <td>233万元</td>
                              </tr> -->
                           </tbody>
                        </table>
                    </div>
           <!--分页-->
      		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">当前查询：共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>                                                                                                                                                                           
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 10px;" class="page_numPerPage">
				<select ng-model="page.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div> 
<!--分页-->		
								
			</div>
			<div class="clearfix"></div>
		</div>
</body>

 <div cg-combo-xz data="pageXq" type=''></div>
</html>