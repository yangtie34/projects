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
<title>业务管理</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/task/js/workController.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="workController">
<div class="content">
<!--左侧导航开始-->
<jsp:include page="../../manager/menu.jsp"></jsp:include>
  <!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>业务管理</h3>
      </div>
    </div>
    <!--右侧头部结束-->
    <div cg-mul-query-comm source="mutiSource" result="page.conditions" noborder="true" class="quanxian-content"></div>	
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--条件-->
        
        <div class="quanxian-header"> 
          <div class="quanxian-tools">
            <div class="quanxian-btn-group">
              <button type="button" style="outline:none;" ng-click="wTitle='新增';createWorkDiv=true;Work={}" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">新增</span></button> 
            </div>
            <!--搜索-->
            <div class="quanxian-block-search">
              <div class="quanxian-in-search"><span class="quanxian-fposition">
                <input class="quanxian-search-t" type="text" placeholder="业务名称 或service 模糊匹配" ng-keyup="myKeyup($event)" ng-model="querywork.name_">
                <a href="javascript:void(0)" class="quanxian-search-q" ng-click="getWorkByName()"></a> </span>
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
				<tr> <th>业务名称</th> <th>业务类型</th><th>业务服务</th><th>启/禁用</th><th>删除</th> <th></th><th></th><th></th> </tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items" class="quanxian-bg-hover">
					<td><a class="quanxian-alink" ng-click="update1(item);">{{item.name_}}</a></td>
					<td>{{codeMap[item.group_]}}</td>
					<td>{{item.service}}</td>
					<td ng-if="item.isTrue==1">
						<a ng-click="isTrueWork(item)" class="quanxian-icon-a shuju-img-qiyong-icon" title="当前 启用 状态，点击停用">停用</a> 
					</td>
					<td ng-if="item.isTrue==0">
						<a ng-click="isTrueWork(item)" class="quanxian-icon-a shuju-img-jinyong-icon" title="当前 停用 状态，点击启用">启动</a> 
					</td>
					<td><a href="javascript:void(0)" ng-click="delWork(item)" class="quanxian-icon-a shuju-img-delete-icon" title="删除">删除</a> </td>
					<td><a href="${ctx}/system/task/workVerify/{{item.id}}" target="_blank"><button type="button"  class="quanxian-btn-table" ng-click=""> 验证步骤 </button></a> </td>
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
  <div cs-window show="createWorkDiv" autoCenter="true" offset="offset"title="wTitle+'业务'">
	 	<div>
	<!--  		<dl class="quanxian-select-group-dl">
      <dt>计划名称：</dt>
      <dd><input type="text" ng-model="plan.name_" class="form-control" placeholder="计划名称"></dd>
   	</dl> -->
   	 		<dl class="quanxian-select-group-dl">
      <dt>业务名称：</dt>
      <dd><input ng-model="Work.name_" class="form-control"/></dd>
   	</dl>
   	  	<dl class="quanxian-select-group-dl">
      <dt >业务类型：</dt>
      <dd ><select ng-model="Work.group_"  class="form-control">
	 				<option ng-repeat="code in codes" value="{{code.code_}}">{{code.name_}}</option>
				</select>
		</dd>
   	</dl>
   	<dl class="quanxian-select-group-dl">
      <dt>业务代码：</dt>
      <dd><input ng-model="Work.service" class="form-control"/></dd>
   	</dl>
 
   		<dl class="quanxian-select-group-dl">
      <dt>是否可用：</dt>
      <dd>
        <div check-box-switch state="WorkisTrue" result="Work.isTrue" style="padding-top: 5px;">
  	</div>
      </dd>
    </dl>
	 	</div>
	 			<div class="modal-footer">
	 			<button  class="btn btn-primary"  ng-click="createWork();">确定</button>
	</div>
	 </div>	
	
</body>
</html>
