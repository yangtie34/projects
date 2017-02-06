<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>高影响力期刊收录论文分析</title>
<base href="<%=root%>/ky/thesis/include/index.jsp"/>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body ng-controller="controller">
<div class="container-fluid">
    <div class="keyan-main">
    	<div class="header">
        	<div class="main-tit">
            	<h4 class="main-tit-b">高影响力期刊收录论文分析</h4>
                <h5 class="main-tit-s">根据论文发表期刊的影响力挑选出高影响力的期刊，分析每种期刊在所选时间内的收录总量和相对增量，
                并分析被这些期刊收录的论文在各科研单位的分布情况以及收录年份的分布情况。
                并针对SCI收录进行了影响因子分析和收录分区分析。</h5>
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

		<section>
        <div class="background-gray">
        	<ul class="list-unstyled background-gray-square">
            	<li class="square-pad" ng-repeat="it in periodicalTypes" style="width: {{100/periodicalTypes.length}}%">
                	<a href="" class="square" ng-class="{'active' : it.active}" ng-click="queryDetailOfPeriodical(it.code)">
                        <div>
                            <p> {{it.name}} 论文收录</p>
                            <div href=""  class="round round-70 text-24 color-ju round-t-b arrow-tri-up">{{it.current}}</div>
                            <small class="zengfu">
					                               较上年{{ condition.definedYear.start == condition.definedYear.end ? '度' : '段'}}新增 <b class="text-14 text-ju">{{it.current-it.last}}</b> 篇<br>
					                                增幅 <b class="text-14 text-ju" ng-if="it.last != 0"> {{ 100*(it.current - it.last)/ it.last | number:2 }}%</b>
	                                <b class="text-14 text-ju" ng-if="it.last == 0">——</b>
                            </small>
                        </div>
                        <div class="has-arrow"></div>
                    </a>
                </li>
            </ul>
        </div>

		<div class="ky-box">
        	<div class="ky-box-sec">
        		<div class="dis-tab ">
                	<div class="dis-t-cell wid-5 separate-l">
                    	<div echart config="chart1Config" height="350" on-click="chart1click($params)"></div>
                    </div>
                    <div class="dis-t-cell wid-5 border-l separate-r">
                    	<div echart config="chart2Config"  height="350" on-click="chart2click($params)"></div>
                    </div>
                </div>
            </div>
        </div>

		<div class="ky-box"  ng-if="showBottom">
        	<div class="ky-box-sec">
        		<div class="dis-tab ">
                	<div class="dis-t-cell wid-5 separate-l">
                    	<div echart config="chart3Config" height="350"></div>
                    </div>
                    <div class="dis-t-cell wid-5 border-l separate-r">
                    	 <div echart config="chart4Config" height="350" on-click="chart4click($params)"></div>
                    </div>
                </div>
            </div>
        </div>
		<div class="ky-box no-bottom-border ky-n"><small><span class="text-org">名词解释:</span>影响因子（Impact Factor，IF）是描述期刊论文平均被引率的重要指标。某期刊在某年的影响因子是指该年引证该期刊前两年论文的总次数与前两年该期刊发表
的论文总数之比，期刊IF值越大，相对来说其学术影响较大，载文的学术水平较高。</small></div>
		</section>
    </div><!--keyan-main  end-->
    
    <div modal-form config="formConfig"></div>
    </div>
</body>
</html>