<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/ky/thesis/total/index.jsp"/>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>科研论文发表量</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body ng-controller="controller">
<div class="container-fluid">
	<div modal-form config="formConfig"></div>
    <div class="keyan-main">
    	<div class="header">
        	<div class="main-tit">
            	<h4 class="main-tit-b">科研论文发表量</h4>
                <h5 class="main-tit-s">展示科研论文的发表总量和历年的变化情况。通过论文发表量、发表量增幅、以本校为第一单位发表量、各部门论文发表量及占比、各引文索引库发表量展示各学科门类的不同时间段的论文的发表情况。</h5>
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
                    	 <div cg-combo-tree source="queryCondition.deptTeah" result="condition.deptTeah" ></div> 
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

        <div class="background-gray">
        	<ul class="list-unstyled background-gray-square">
            	<li class="square-pad">
                	<div class="square">
                    	<p>发表各类论文/篇</p>
                        <a class="has-round-border active">
                            <span class="round round-70 text-24 color-ju round-t-b has-round-shadow has-round-shadow-ju"  ng-click="queryDetials($params)"><u>{{thesisNums.current}}</u></span>
                        </a>
                    </div>
                </li>
                <li class="square-pad">
                	<div class="square">
                    	<p>较上一年{{condition.definedYear.start == condition.definedYear.end ? '度' : '段'}}</p>
                        <a class="has-round-border active">
                            <span class="round round-70 text-24 color-blue round-t-b has-round-shadow has-round-shadow-blue"><u>{{thesisNums.last}}</u></span>
                        </a>
                        <small class="zengfu" ng-if="thesisNums.last != 0">增幅{{(thesisNums.current - thesisNums.last)*100/ thesisNums.last | number:2}}%</small>
                        <small class="zengfu" ng-if="thesisNums.last == 0">增幅——</small>
                    </div>
                </li>
                <li class="square-pad">
                	<div class="square">
                    	<p>以我校为第一单位发表/篇</p>
                        <a class="has-round-border active">
                            <span class="round round-70 text-24 color-green round-t-b has-round-shadow has-round-shadow-green"><u>{{firstDept.isFirst}}</u></span>
                        </a>
                    </div>
                </li>
                <li class="square-pad">
                	<div class="square">
                    	<p>非第一单位发表</p>
                        <a  class="has-round-border active">
                            <span class="round round-70 text-24 color-ju-l round-t-b has-round-shadow has-round-shadow-ju-l"><u>{{firstDept.notFirst}}</u></span>
                        </a>
                    </div>
                </li>
            </ul>
        </div>

        <div class="ky-box no-bottom-border no-bottom">
           <div echart config="mainchart" height="350" on-click="clickdept($params)"></div>
        </div>
        <div class="ky-box no-bottom-border no-bottom" ng-show="showYearChangeOfAll">
           <div echart config="yearChangeOfAllConfig" height="350" on-click="clickyear($params)"></div>
        </div>

		<section>
        <div class="section-head">
        	<span class="section-head-tit">各引文索引库论文发表量</span>
        </div>
        <div class="ky-box no-bottom-border">
        	<div class="ky-box-sec">
            	<div class="row clearfix">
                	<div class="col-md-5">
                    	<div echart config="piechart" height="400" on-click="clickgywlw($params)"></div>
                    </div>
                    <div class="col-md-7">
                    	<p class="section-tit text-center">各引文索引库发表量及占比排行</p>
                        <div class="wid-9 mar-center">
                            <table class="table order-circle-tab">
                                <tbody>
                                <tr ng-repeat="item in vm.items | paging:vm.index:vm.size">
									<td><span class="circle-out">{{item.order}}</span></td>
									<td> <u class="text-green" style="cursor:pointer;" ng-click="clickgywlw(item)">{{item.name}}</u> </td>
									<td> <u class="text-green" style="cursor:pointer;" ng-click="clickgywlw(item)">{{item.value}}</u></td>
									<td> {{item.persent| number:2}}%</td>
								</tr>
                                </tbody>
                            </table>
                           <div class="hidden"  pagination total-items="vm.items.length" ng-model="vm.index"
								max-size="0" items-per-page="vm.size"
								class="pull-right" boundary-links="true"></div>
                            <p ng-show="vm.items.length &gt; vm.size" class="ky-page-img pull-right">
                                <a href="javascript:void(0);" class="ky-pre-img " ng-click=" vm.index = vm.index - 1" ng-hide="vm.index == 1"></a>
                                <a href="javascript:void(0);" class="ky-next-img" ng-click=" vm.index = vm.index + 1" ng-hide="vm.index * vm.size >= vm.items.length"></a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </section>
    </div> 
</div> 
</body>
</html>