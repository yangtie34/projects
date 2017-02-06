<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/ky/thesis/publish/index.jsp"/>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>高影响力期刊发文分析</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body ng-controller="controller">.
<div class="container-fluid">
	<div modal-form config="formConfig"></div>
   <div class="keyan-main">
   	<div class="header">
       	<div class="main-tit">
           	<h4 class="main-tit-b">高影响力期刊发文分析</h4>
               <h5 class="main-tit-s">根据论文发表期刊的影响力挑选出高影响力的期刊，展现了在这些期刊发表论文的总量、相对增量和增幅。并分析在这些期刊发表的论文在各单位的分布情况以及年度发表量的变化趋势，并分析了这些期刊的载文量。</h5>
           </div>
           <div class="keyan-condition">
                <dl class="clearfix">
                	<dt>学科门类：</dt>
                    <dd>
                    	<form action="" class="ky-form condition-radio">
                            <div class="radio-box radio-inline" ng-repeat ="it in queryCondition.subject" >
								<input type="radio" ng-model="condition.subject" ng-value="it" id="{{it}}">
                                <label for="{{it}}"> {{it.mc}}
								</label>
                            </div>
                        </form>
                    </dd>
                </dl>
                <dl class="clearfix">
                	<dt>完成人单位：</dt>
                    <dd>
                    	 <div cg-combo-tree source="queryCondition.dept" result="condition.dept" ></div> 
                    </dd>
                </dl>
                <dl class="clearfix">
                	<dt>统计时间：</dt>
                    <dd>
                    	<div self-defined-year source="queryCondition.definedYear" result='condition.definedYear'></div>
                    </dd>
                </dl>
            </div>
       </div>

       <div class="ky-box p-top-10">
       	<div class="dis-tab">
           	<div class="dis-t-cell wid-2">
               	<div class="round-section">
                       <div class="round-item">
                           <p class="sec-tit">我校以{{condition.dept.mc}}<br>为第一作者单位累计在<br>	SCI源期刊发文/篇</p>
                           <div><a href="" class="round color-ju round-70 text-18" ng-click="queryNums($params)">{{thesisNums.current}}</a></div>
                       </div>
                       <div class="round-item">
                           <p class="sec-tit">较上年{{ condition.definedYear.start == condition.definedYear.end ? '度' : '段'}}增长/篇</p>
                           <div><a href="" class="round color-purple round-70 text-18">{{thesisNums.current - thesisNums.last}}</a></div>
                       </div>
                       <div class="round-item">
                           <p class="sec-tit">增幅</p>
                           <div>
	                           <a href="" class="round color-purple round-70 text-18" ng-if="thesisNums.last != 0">
	                           	{{ 100*(thesisNums.current - thesisNums.last)/thesisNums.last |number:2 }}%
	                           </a>
	                           <a href="" class="round color-purple round-70 text-18" ng-if="thesisNums.last == 0">
	                           	——
	                           </a>
                           </div>
                       </div>
                   </div>
               </div>
               <div class="dis-t-cell wid-8">
               	<div class="section-tit tit-left-30">
					我校以{{condition.dept.mc}}为第一作者单位累计在SCI源期刊发文的变化趋势
				</div>
                   <div class="img-box  tit-left-30">
                   <div echart config='changeTrendConfig'  on-click="queryYear($params)"></div>
                   </div>
               </div>
           </div>
       </div>

       <div class="ky-box no-bottom">
       	<div echart config="deptSourceConfig" on_click="queryDept($params)"></div>
       </div>

       <div class="ky-box no-bottom">
       	<h4>
       		<b>发文期刊的载文量分析</b> 
       		<div tool-tip >
       			横坐标表示在期刊上发表论文的数量，纵坐标表示发表论文数量相同的期刊的数量。
       		</div>
       	</h4>
       	<div echart config="publishNumsConfig"></div>
       </div>
  </div>
  </div>
</body>
</html>