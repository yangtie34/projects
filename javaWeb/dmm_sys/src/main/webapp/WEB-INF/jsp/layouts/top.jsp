<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<div class="navbar navbar-inverse navbar-fixed-top" style="background: rgba(0, 0, 0, 0) linear-gradient(to bottom , #3993ba 0%, #067ead 100%) repeat scroll 0 0; border-color:white;">
     <div class="container">
       <div class="navbar-header">
         <button data-target=".navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle collapsed">
           <span class="sr-only">Toggle navigation</span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a href="javascript:void(0);"  class="navbar-brand hidden-sm" style="color:white;">数据挖掘分析系统<!-- ${ue.realName}--></a>
       </div>
       <div role="navigation" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
         <ul class="nav navbar-nav">
            <li><a style="color:white;">${ue.userName}</a></li>
            <li><a style="color:white;">${ue.deptName}</a></li>
            <li class="dropdown">
         		<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0)" aria-expanded="false" style="color:white;background-image: linear-gradient(to bottom, #3993ba 0px, #067ead 100%);">个人信息<b class="caret"></b></a>
         		<ul class="dropdown-menu">
	                <li><a data-toggle="modal" data-target="#myModal1" href="javascript:void(0)">查看个人信息</a></li>
	                <li><a data-toggle="modal" data-target="#pwdDiv" href="javascript:void(0)">修改密码</a></li>
                </ul>
         	</li>
         	<li class="dropdown">
         		<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0)" aria-expanded="false" style="color:white;background-image: linear-gradient(to bottom, #3993ba 0px, #067ead 100%);">用户管理<b class="caret"></b></a>
         		<ul class="dropdown-menu">
	                <li><a target="_blank" href="${ctx}/XXX">账号管理</a></li>
	                <li><a target="_blank" href="${ctx}/XXX">角色管理</a></li>
	                <li><a target="_blank" href="${ctx}/XXX">权限管理</a></li>
	                <li><a target="_blank" href="${ctx}/XXX">菜单管理</a></li>
                </ul>
         	</li>
         	<li class="dropdown">
         		<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0)" aria-expanded="false" style="color:white;background-image: linear-gradient(to bottom, #3993ba 0px, #067ead 100%);">系统管理<b class="caret"></b></a>
         		<ul class="dropdown-menu">
	                <li><a target="_blank" href="${ctx}/XXX">任务调度</a></li>
                </ul>
         	</li>
         </ul>
         <ul class="nav navbar-nav navbar-right hidden-sm">
         	<li><a style="color:white;">上次登录：${ue.lastLoginTime}</a></li>
           <li><a class="title_a" href="${ctx}/logout" style="color:white;">注销</a></li>
         </ul>
       </div>
     </div>
</div>

<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">个人信息</h4>
      </div>
      <div class="modal-body">
        	姓名：${ue.userName}<br/>
        	单位：${ue.deptName}
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="pwdDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="clearPwd();"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改密码</h4>
      </div>
      <div class="modal-body">
	      <div class="row">
	      	<div class="col-md-8">
	      		<input type="password" class="form-control" id="oldpwd" placeholder="旧密码" onblur="checkOldPwd();"/>
	      	</div>
	      	<div class="col-md-4">
	      		<span style="color: red;display: none;" id="oldpwdSpan">密码错误</span>
	      	</div>
	      </div>
	      <div class="row" style="margin-top: 10PX">
	      	<div class="col-md-8">
	      		<input type="password" class="form-control" id="newpwd1" placeholder="新密码" onblur="checkNewPwd();"/>
	      	</div>
	      	<div class="col-md-4">
	      		<span style="color: red;display: none;" id="newpwd1Span"></span>
	      	</div>
	      </div>
	      <div class="row" style="margin-top: 10PX">
	      	<div class="col-md-8">
	      		<input type="password" class="form-control" id="newpwd2" placeholder="确认密码" onblur="checkNewPwd();">
	      	</div>
	      	<div class="col-md-4">
	      		<span style="color: red;display: none;" id="newpwd2Span"></span>
	      	</div>
	      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"  onclick="clearPwd();">取消</button>
        <button type="button" class="btn btn-default" onclick="changePwd();">确定</button>
      </div>
    </div>
  </div>
</div>