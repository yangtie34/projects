<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>仪器设备管理者分析</title>

<style>
            .cursordiv{
            cursor: pointer;
            }
            </style>
</head>
</head>
<body ng-controller="AdminsController">
<div id="wrapper">
 <script type="text/javascript" src="${ctx }/product/equipment/js/Admins.js"></script>    

    <div class="content">
             <div class="xscz-head content_head">
    			<h3 class="xscz-fff">仪器设备管理者分析</h3>
    			<p class="xscz-default">对管理设备的人员进行类型分析，所属分析。(*注：仪器设备单价不下于{{gzbz/10000}}万以下值称谓为贵重设备)</p>
  			</div>
            <style>
            .cursordiv{
            cursor: pointer;
            }
            </style>
            <div class="content_main">
            
            	<div class="guanlizhe_top_imges">
                	<div class="managers_l fl">
                        <div class="manager_l">
                            <ul>
                                <li class="li_bold"><a href="" class="colorr" ng-click="getxq('all');">{{vm.items[0]}}</a></li>
                                <li class="li_zi">人</li>
                            </ul>
                            <div class="manager_colth">
                                <div id="liub" ng-class="DeptGroup=='all'?'':'cursordiv'"ng-click="cursordiv({CODE:'all',NAME:''});"><h4>设备管理者</h4></div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="else fl">
                        <div id="liub"><p>=</p></div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="manager_r fr">
                    <div ng-repeat="item in vm.items[1]">
                    	<div class="per_20" >
                            <div id="octagon">
                                <div id="liub" ng-class="DeptGroup==item.CODE?'':'cursordiv'"ng-click="cursordiv(item);"><h4>{{item.NAME}}</h4></div>
                                <ul>
                                    <li class="li_bold"><a href="" class="colorr" ng-click="getxq(item.CODE);">{{item.VALUE}}</a></li>
                                    <li class="li_zi">人</li>
                                </ul>
                            </div>
                        </div>
                        <p ng-show="$index<vm.items[1].length-1">+</p>
                    </div>
                      <!--   <div class="per_20">
                            <div id="octagon">
                                <div id="liub"><h4>行政机构</h4></div>
                                <ul>
                                    <li class="li_bold"><a href="" class="colorr">30</a></li>
                                    <li class="li_zi">人</li>
                                </ul>
                            </div>
                        </div>
                        <p>+</p>
                        <div class="per_20">
                            <div id="octagon">
                                <div id="liub"><h4>科研机构</h4></div>
                                <ul>
                                    <li class="li_bold"><a href="" class="colorr">30</a></li>
                                    <li class="li_zi">人</li>
                                </ul>
                            </div>
                        </div>
                        <p>+</p>
                        <div class="per_20">
                            <div id="octagon">
                                <div id="liub"><h4>其他机构</h4></div>
                                <ul>
                                    <li class="li_bold"><a href="" class="colorr">10</a></li>
                                    <li class="li_zi">人</li>
                                </ul>
                            </div>
                        </div> -->
                    </div>
                    <div class="clearfix"></div>
                </div>
               <!--------------------------------------------------- 以上为guanlizhe-top-imges内容------------------------------------------------>
                <div class="fenbu">
                	<div class="t">
            			<h5>{{alltitle}}人员类型分布情况</h5>
                	</div>
                    <div class="renyuan_fenbu_con">
                    	<div class="fl leixing">
                        	<h4 class="h4_16">学历对比<span class="icon-time span_icon" ng-click="qushiClick(0);"></span></h4>
                        	<div stu-chart config="vm.items[2][0]" style="height:310px;"
                        	class="img-responsive img_marg img-top"> </div>
                        	
                        	<%-- <img src="${images}/leixing.jpg" class="img-responsive img_marg img-top"> --%>
                        </div>
                       
                        <div class="fl zhicheng">
                        	<h4 class="h4_16">职称对比<span class="icon-time span_icon" ng-click="qushiClick(1);"></span></h4>
                        	<div stu-chart config="vm.items[2][1]" style="height:310px;"
                        	class="img-responsive img_marg img-top"> </div>
                        	<%-- <img src="${images}/zhicheng.jpg" class="img-responsive img_marg img-top"> --%>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <!------------------------------------------------- 以上人员类型分布情况---------------------------------------------->

                <div class="guoqi">
                	<div class="t">
            			<h5>{{alltitle}}各单位对比情况</h5>
                	</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio2" id="renshu" value="00" checked="" ng-model="radio0id">
                            <label for="renshu" class="fw-nm">
                              人数
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio2" id="shebeishu" value="0"ng-model="radio0id">
                            <label for="shebeishu" class="fw-nm">
                              管理设备数
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio2" id="shebeijiazhi" value="1"ng-model="radio0id">
                            <label for="shebeijiazhi" class="fw-nm">
                              管理设备价值
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue" ng-show="DeptGroup=='${sjcode}'">
                            <input type="radio" name="radio2" id="sjtouru" value="2"ng-model="radio0id">
                            <label for="sjtouru" class="fw-nm">
                              生均投入价值<!-- <span class="text-muted">（点击教学机构时出现）</span> -->
                            </label>
                        </div>
                     <!--    <span class="icon-time span_icon fr"></span> -->
                    </div>
                    <div stu-chart config="radio0data" style="height:310px;"class="img-responsive img-top"> </div>
                   
                   <%--  <img src="${images}/renshu.jpg" class="img-responsive img-top"> --%>
                    <div class="clearfix"></div>
                </div>
                <!--------------------------------------------------以上各机构（单位）对比情况趋势---------------------------------------------->
                <div class="guoqi pdbtm_box">
                	<div class="t">
            			<h5>{{alltitle}}管理设备数量Top10</h5>
                	</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio1" id="shebeizshu" value="0" checked="" ng-model="radio1id">
                            <label for="shebeizshu" class="fw-nm">
                              设备总数
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio1" id="guizhongsb" value="1"ng-model="radio1id">
                            <label for="guizhongsb" class="fw-nm">
                              贵重设备数
                            </label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio1" id="zongjiazhi" value="2"ng-model="radio1id">
                            <label for="zongjiazhi" class="fw-nm">
                              设备总价值
                            </label>
                        </div>
                    </div>
                    <div class="tab">
                    	<div class="tab_tit">
                        	<a href="" ng-class="queryTypeindex==0?'tab_active':''" ng-click="queryTypeClick(0);">个人排名</a>
                         <!--    <a href=""ng-class="queryTypeindex==1?'tab_active':''" ng-click="queryTypeClick(1);">单位排名</a> -->
                        </div>
                        <table class="table table-change"> 
                           <thead>
                              <tr>
                                 <th>排名</th>
                                 <th ng-show="!queryTypeindex==1">职工号</th>
                                 <th ng-show="!queryTypeindex==1">职工姓名</th>
                                 <th>所属单位</th>
                                 <th>管理设备总数</th>
                                 <th>管理设备总价值</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr class="tab_dashed" ng-repeat="item in vm.items[6]">
                                 <td>{{item.RANK_}}</td>
                                 <td ng-show="!queryTypeindex==1">{{item.TEA_ID}}</td>
                                 <td ng-show="!queryTypeindex==1">{{item.TEA_NAME}}</td>
                                 <td>{{item.DEPT_NAME}}</td>
                                 <td><a href="" class="colorr" ng-click="getxqById(item);">{{item.NUMS}}</a>（贵：<a href="" ng-click="getGzxqById(item);" class="colorr">{{item.VALNUM}}</a>）</td>
                                 <td>{{item.MONEYS}}万元</td>
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