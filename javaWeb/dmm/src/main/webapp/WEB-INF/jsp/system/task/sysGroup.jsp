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
<title>业务系统管理</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/task/js/sysGroupController.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/xiala.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="sysGroupController">
<div class="content">
<!--左侧导航开始-->
<jsp:include page="../../manager/menu.jsp"></jsp:include>
  <!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>业务系统管理</h3>
      </div>
    </div>
    <!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--条件-->
        <div class="quanxian-header"> 
          <div class="quanxian-tools">
            <div class="quanxian-btn-group">
              <button type="button" style="outline:none;" ng-click="group={};createGroupDiv=true;" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">新增</span></button> 
            </div>
            <!--搜索-->
              <!-- 
            <div class="quanxian-block-search">
              <div class="quanxian-in-search"><span class="quanxian-fposition">
                <input class="quanxian-search-t" type="text" placeholder="计划名称 模糊匹配" ng-keyup="myKeyup($event)" ng-model="plan.name_">
                <a href="javascript:void(0)" class="quanxian-search-q" ng-click="getPlanByName();"></a> </span>
                 <div class="quanxian-search-tblock" style="display:block">
                   display:none:隐藏
                  <ul>
                    <li> 王苏 </li>
                    <li> 王文娟 </li>
                    <li> 王娟 </li>
                    <li> 王文 </li>
                  </ul>
                </div> 
              </div>
            </div>-->
            <!--搜索-->
          </div>
        </div>
        <!--条件-->
        <!--表格 -->
        <table class="quanxian-sub-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr> <th>编号</th> <th>系统类型</th> <th>系统地址</th><th>编辑</th> <th>删除</th></tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items" class="quanxian-bg-hover">
					<td>{{item.id}}</td>
					<td>{{codeMap[item.id]}}</td>
					<td>{{item.url_}}</td>
					<td>
					<a href="#" ng-click="update(item);" class="quanxian-icon-a shuju-img-xiugai-icon" title="编辑">编辑</a>
					</td>
					<td>
					<a href="javascript:void(0)" ng-click="delGroup(item)" class="quanxian-icon-a shuju-img-delete-icon" title="删除">删除</a> 
					</td>
				</tr>
			</tbody>
		</table>
        <!--表格-->

      </div>
    </div>
  </div>
  <!--右侧内容结束-->
</div>
<!--content-->
  <div cs-window show="createGroupDiv" autoCenter="true" offset="offset">
	 	<div>
	 	<dl class="quanxian-select-group-dl">
      <dt >系统类型：</dt>
      <dd ><select ng-model="group.id"  class="form-control">
	 				<option ng-repeat="code in codes" value="{{code.code_}}">{{code.name_}}</option>
				</select>
		</dd>
   	</dl>
	<dl class="quanxian-select-group-dl">
      <dt>系统地址：</dt>
      <dd><input type="text"  ng-model="group.url_" class="form-control" placeholder="系统地址"></dd>
   	</dl>
	 	</div>
	 	   	<div class="modal-footer">
	 		<button class="btn btn-primary" ng-click="createGroup()">确定</button>
	</div>
	 </div>	
	
</body>
</html>
