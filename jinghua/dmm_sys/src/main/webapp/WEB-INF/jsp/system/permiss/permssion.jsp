<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html ng-app="app" ng-init="perm_flag='${flag}';urole='${flag}'=='role'?{id:'${role.id}'}:{id:'${user.id}'};">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="Jophy" />
    <title>授权管理</title>
</head>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/product/system/permiss/js/permssionController.js"></script>  
    <!--[if lte IE 9]>
    <script src="${ctxStatic }/bootstrap-3.3.4-dist/js/respond.min.js"></script>
    <script src="${ctxStatic }/bootstrap-3.3.4-dist/js/html5shiv.min.js"></script>
    <![endif]-->
<body ng-controller="permssionController" >
<div class="content">
<!--左侧导航开始-->
		<jsp:include page="../../manager/menu.jsp"></jsp:include>
		<!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
            <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
       <h3>为${flag eq 'role'?'角色':'用户'}授权</h3>
      </div>
    </div>
    <hr class="quanxian-hr">
    <!--右侧头部结束-->
<div >

<div class="quanxian-header">
          <ul class="quanxian-select-group ">
            <li>
              <dl class="quanxian-select-group-dl clearfix">
                <dt>${flag eq 'role' ? '角色':'用户' }名称 :</dt>
                <dd>
                  <input type="text" class="quanxian-input-default" value="${flag eq 'role' ? role.description:user.username}" disabled=""> 
                  <!-- <span class="quanxian-span-qiyong"> </span> -->（不可编辑）
               <c:if test="${flag eq 'role' }">
			  ${role.istrue eq 1 ? '可用':'不可用' }<br/>
				</c:if>
                </dd>
              </dl>
              <dl class="quanxian-select-group-dl clearfix">
                <dt>资源信息：</dt>
                <dd>
                 <input class="quanxian-input-default" type="button" value="选择资源" ng-click="zyClick();"/>
<span ng-show="res_treeResultMcs.length>0">已选择：{{res_treeResultMcs|json | limitTo:46}}<span>...</span></span>

             <!--    <div cg-combo-box source="res_treeData" result="res_treeResult" height="32" on-select="boxSelect($data)"></div> -->
                </dd>
              </dl>
              <dl class="quanxian-select-group-dl clearfix">
                <dt>数据范围：</dt>
                <dd>
                 <div class="quanxian-btn-group">
                  <select type="button" style="outline:none;-webkit-appearance: none;" ng-model="perm_data.dataId" class="quanxian-btn-default quanxian-btn-bottom-icon">
                 		<option value="">---请选择---</option>
		<c:forEach items="${dataSeverList }" var="dataSever">
			<option value="${dataSever.id }">${dataSever.name_ }</option>
		</c:forEach>
	   			</select> 
                  <input style="display: -webkit-inline-box;width: auto;" class="form-control" type="button" value="查看行政组织机构" ng-show="perm_data.dataId==2" ng-click="open_zzjg();zzjgInfoWindow=true;"/>
	    <input style="display: -webkit-inline-box;width: auto;" class="form-control" type="button" value="查看教学组织机构" ng-show="perm_data.dataId==1" ng-click="open_jxjg();jxjgInfoWindow=true"/>
        <input type="hidden" value=""ng-model="zzjgText" id="zzjgText" /><input type="hidden" value=""ng-model="jxjgText" id="jxjgText" /></div>
                </dd>
              </dl>
              <dl class="quanxian-select-group-dl clearfix">
                <dt>操作权限：</dt>
                <dd>
                  <select type="button" style="outline:none;-webkit-appearance: none;" ng-model="perm_data.operId" class="quanxian-btn-default quanxian-btn-bottom-icon">
		<option value="">---请选择---</option>
		<c:forEach items="${operateList }" var="operate">
			<option value="${operate.id}">${operate.description }</option>
		</c:forEach>
	    </select>    
                </dd>
               </dl>
<!--                 <button type="button" style="outline:none;line-height: initial;
                    position: relative;
    left: 80px;" class="quanxian-btn-default" ng-click="add_perm();"> 添加 </button> -->
            </li>
          </ul>
        </div>


<div class="quanxian-title-term">
          <h3 class="quanxian-title-lock-icon" style="margin: 0px;line-height: 22px;">${flag eq 'role' ? role.description:user.username} 已分配的权限</h3>
          
           <div class="quanxian-block-search" style="position:inherit;float: right;">
		<div class="quanxian-in-search"><span class="quanxian-fposition">
            <input class="quanxian-search-t ng-pristine ng-valid" type="text" placeholder="输入资源名查询..." ng-keyup="myKeyup($event)" ng-model="resourcesName">
            <a href="javascript:void(0)" class="quanxian-search-q" ng-click="queryGridContent()"></a> 
            </span>
          </div>
          </div>
          
          <span class="XX_quanxian-btn-right" ng-click="add_perm();">
          <button type="button" style="outline:none;" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">添加</span></button>
          </span> </div>
          
<div>
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="quanxian-sub-table">
			<thead>
	            <tr border="0">
	                <th>资源名</th>
	                <th>数据范围</th>
	                <th>权限描述</th>
	                <th>删除</th>
	            </tr>
	         </thead>
	        <tbody>
	        <tr ng-repeat="item in vm.items">
						<td>{{item.RESNAME}}</td>
						<td ng-show="item.DATAID==3||item.DATAID==4">{{item.DATANAME}}</td>
						<td ng-show="item.DATAID!=3&&item.DATAID!=4"><a class="quanxian-alink" ng-click="item.DATAID==1?showJxjg(item.ID):showZzjg(item.ID);">{{item.DATANAME}}</a></td>
						<td>{{item.ROLEN}}对{{item.DATANAME}}的{{item.RESNAME}}有{{item.SODESC}}权限</td>
						<td>
						<a href="javascript:void(0)" ng-click="deletePerm(item.ID);" class="quanxian-icon-a shuju-img-delete-icon" title="删除权限">删除</a> 
						</td>
					</tr>
	        </tbody>
	</table>
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

<div cs-window show="zyInfoWindow"  offset="offset" autoCenter="true" style="position: absolute">
<span>*灰色为已分配权限</span>
				<div cg-check-map-tree source="res_treeData" result="res_treeResult" 
					type="'res'">
					<!-- code 是条件对应的字段代码 -->
				</div>
				<div class="modal-footer">
	 	<button type="button" class="btn btn-primary" ng-click="zyInfoWindow = false;">确定</button>
	</div>
</div>
<div cs-window show="resourceInfoWindow" autoCenter="true" style="position: absolute" offset="offset" >
<span>*灰色为已分配权限</span>
		<div cg-check-map-tree source="res_treeData" result="res_treeResult" 
			type="'res'"   >
			<!-- code 是条件对应的字段代码 -->
		</div>
			 	 <div class="modal-footer">
	 	<button type="button" class="btn btn-primary" ng-click="perm_data.resName=res_treeResult[0].mc;perm_data.resId=res_treeResult[0].id;resourceInfoWindow = false;">确定</button>

	</div>
</div>

<div cs-window show="zzjgInfoWindow"  offset="offset" autoCenter="true" style="position: absolute">
		<div cg-check-map-tree source="zzjg_treeData" result="zzjg_treeResult" type="'tree'">
			<!-- code 是条件对应的字段代码 -->
		</div>
			<div class="modal-footer">
	 	<button type="button" class="btn btn-primary" ng-click="save_zzjg();zzjgInfoWindow = false;">确定</button>
	</div>
</div>

<div cs-window show="jxjgInfoWindow"  offset="offset" autoCenter="true" style="position: absolute">
		<div cg-check-map-tree source="jxjg_treeData" result="jxjg_treeResult" type="'tree'">
			<!-- code 是条件对应的字段代码 -->
		</div>
				<div class="modal-footer">
	 	<button type="button" class="btn btn-primary" ng-click="save_jxjg();jxjgInfoWindow = false;">确定</button>
	</div>
</div>
	</div>
	</div>
</body>
<script type="text/javascript">

var res_treeData=${resJson };

var zzjg_treeData=${zzjgJson };

var jxjg_treeData=${jxjgJson };

</script>
</html>