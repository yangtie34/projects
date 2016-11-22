<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>
<title>计划管理</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/task/js/planController.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/task/js/cron.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/xiala.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="planController">
<div class="content">
<!--左侧导航开始-->
<jsp:include page="../../manager/menu.jsp"></jsp:include>
  <!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>计划管理</h3>
      </div>
    </div>
    <!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--条件-->
        <div class="quanxian-header"> 
          <div class="quanxian-tools">
            <div class="quanxian-btn-group">
              <button type="button" style="outline:none;" ng-click="createPlanDiv=true;plan={};up_=false;" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">新增</span></button> 
            </div>
            <!--搜索-->
            <div class="quanxian-block-search">
              <div class="quanxian-in-search"><span class="quanxian-fposition">
                <input class="quanxian-search-t" type="text" placeholder="计划名称 模糊匹配" ng-keyup="myKeyup($event)" ng-model="plan.name_">
                <a href="javascript:void(0)" class="quanxian-search-q" ng-click="getPlanByName();"></a> </span>
              <!-- 自动补全功能暂时没有做，之后完善
                 <div class="quanxian-search-tblock" style="display:block">
                   display:none:隐藏
                  <ul>
                    <li> 王苏 </li>
                    <li> 王文娟 </li>
                    <li> 王娟 </li>
                    <li> 王文 </li>
                  </ul>
                </div> -->
              </div>
            </div>
            <!--搜索-->
          </div>
        </div>
        <!--条件-->
        <!--表格 -->
        <table class="quanxian-sub-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr> <th>计划名称</th><th>所属类型</th> <th>执行规则</th> <th>启动/停止</th><th>删除</th> <th></th> <th></th><th></th></tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items" class="quanxian-bg-hover">
					<td><a class="quanxian-alink" ng-click="update(item);">{{item.name_}}</a></td>
					<td>{{codeMap[item.group_]}}</td>
					<td><span title={{item.desc_}} class="quanxian-alink">{{item.cron_expression}}</span></td>
					
					<td ng-if="item.isTrue==1">
						<a ng-click="isTruePlan(item)" class="quanxian-icon-a shuju-img-qiyong-icon" title="当前 启动 状态，点击暂停">暂停</a> 
					</td>
					<td ng-if="item.isTrue==0">
						<a ng-click="isTruePlan(item)" class="quanxian-icon-a shuju-img-jinyong-icon" title="当前 暂停 状态，点击启动">启动</a> 
					</td>
					<td><a href="javascript:void(0)" ng-click="delPlan(item)" class="quanxian-icon-a shuju-img-delete-icon" title="删除">删除</a> </td>
					<td><button type="button"  class="quanxian-btn-table" > <a href="${ctx}/system/task/planWork/{{item.id}}" target="_blank">执行步骤 </a></button></td>
					<td><button type="button"  class="quanxian-btn-table" ng-click="startPlan(item.id);"> 立即执行 </button></td>
					<td><button type="button"  class="quanxian-btn-table" ng-click=""><a href="${ctx}/system/task/planLog/{{item.id}}" target="_blank">日志 </a>  </button></td>
				</tr>
			</tbody>
		</table>
        <!--表格-->
        
        
		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
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
      	
      </div>
    </div>
  </div>
  <!--右侧内容结束-->
</div>
<!--content-->
  <div cs-window show="createPlanDiv" autoCenter="true" offset="offset" title="'设置计划'">
	 	<div>
	 		<dl class="quanxian-select-group-dl">
      <dt>计划名称：</dt>
      <dd><input type="text" ng-model="plan.name_" class="form-control" placeholder="计划名称"></dd>
   	</dl>
   		<dl class="quanxian-select-group-dl">
      <dt >所属类型：</dt>
      <dd ><select ng-model="plan.group_"  class="form-control">
	 				<option ng-repeat="code in codes" value="{{code.code_}}">{{code.name_}}</option>
				</select>
		</dd>
   	</dl>
   			<dl class="quanxian-select-group-dl"  ng-show="!cron_expression">
      <dt>执行规则：</dt>
      <dd><input type="text" ng-model="plan.cron_expression" class="form-control" placeholder="表达式"></dd>
      <a href="javascript:void(0)" ng-click="expression();" class="quanxian-alink">选择模式</a>
   	</dl>
   	<dl class="quanxian-select-group-dl" ng-show="cron_expression">
      <dt>设置规则：</dt>
      <dd> 
      <div quartz-cron source="cron_expressionsource" result="plan.cron_expression"></div>
      <button type="button" style="position: relative;top: -40px;left: 25px;"
       class="quanxian-btn-table" ng-click="cron_expression=false"> 设置完成</button>
      </dd>
   	</dl>
   	<dl class="quanxian-select-group-dl" ng-show="false">
      <dt>是否可用：</dt>
      <dd>
        <div check-box-switch state="planisTrue" result="plan.isTrue" style="padding-top: 5px;">
  	</div>
      </dd>
    </dl>
	 	</div>
	 	    <div class="modal-footer">
	 		<button class="btn btn-primary" ng-click="createPlan();">确定</button>
	</div>
	 </div>	
	
</body>
</html>
