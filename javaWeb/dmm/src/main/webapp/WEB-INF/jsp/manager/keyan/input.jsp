<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
		<c:if test="${forjs=='input'}">
            	 <c:set var="pageTitle" value="科研项目录入" />
            	 <c:set var="pageTitleState" value="选择录入类别进行导入或者手工录入。" />
         </c:if>
         <c:if test="${forjs=='view'}">
            	 <c:set var="pageTitle" value="科研录入项目查询" />
            	 <c:set var="pageTitleState" value="选择查询类别查看录入的相关信息。" />
         </c:if>
         <c:if test="${forjs=='check'}">
            	 <c:set var="pageTitle" value="科研录入项目审核" />
            	 <c:set var="pageTitleState" value="选择录入类别进行录入项目的审核。" />
         </c:if>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" type="text/css"
	　media="screen and (max-device-width: 400px)" 　href="tinyScreen.css" />
<link rel="stylesheet" type="text/css"
	　media="screen and (min-width: 400px) and (max-device-width: 600px)"
	　href="smallScreen.css" />
<jsp:include page="../topCssJs.jsp"></jsp:include>
<c:if test="${forjs=='input'}">
            	<script type="text/javascript"
	src="${ctxStatic }/product/manager/keyan/excel.js"></script>
         </c:if>
<script type="text/javascript"
	src="${ctxStatic }/product/manager/keyan/inputPub.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/product/manager/keyan/${forjs}.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctxStatic }/product/manager/keyan/keyanInput.css">
<meta name="description" content="">
<meta name="author" content="">
<title>科研系统-${pageTitle}</title>
</head>
<body>
	<div class="content">
		<!--左侧导航开始-->
		<jsp:include page="../menu.jsp"></jsp:include>
		<!--左侧导航结束-->
		<!--右侧内容开始-->
		<div class="con_right">
			<!--右侧头部开始-->
			<div class="right_title">
				<div class="boxx">
					<div class="title_left">
						<h3>${pageTitle}</h3>
						<p>${pageTitleState}</p>
					</div>
					<div class="title_right">
						<img class="face" src="images/face.png" alt="face" />
						<div class="styled-select">
							<select name="select" id="select">
								<option>张广生，艺术学院副院长</option>
								<option>修改密码</option>
								<option>注销</option>
								<option>退出</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<!--右侧头部结束-->


			<div class="zhibiao_box">
				<div class="zhi_title">
					<span>周口师范学院-信息技术学院</span>
				</div>
				<nav class="navbar navbar-default" style="margin: auto;">
				<div class="container-fluid">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">选择录入:</a>
					</div>

					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
							<li class="dropdown" style="padding-right:2em;"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-haspopup="true"
								aria-expanded="false">请选择 <span class="caret"></span></a>
								<ul class="dropdown-menu">
									<c:forEach items="${tabless}" var="tables">
									<c:set value="" var="tableFather" />
										<c:forEach items="${tables}" var="map" varStatus="status">
											<li><a class="table" tableFather="${tableFather}" tableFatherName="${tables[tableFather]}" tableName="${map.key}">${map.value}</a></li>
											<c:if test="${status.index==0}">
												<c:set value="${map.key}" var="tableFather" />
											</c:if>
										</c:forEach>
										<li role="separator" class="divider"></li>
									</c:forEach>
								</ul></li>
							<li><a class="noclick">首页</a></li>
						</ul>
					</div>
					<!-- /.navbar-collapse -->
				</div>
				<!-- /.container-fluid --> </nav>
				<div class="listBox" id="index">
				<p>这个啊 是这样录入的</p>
				</div>

				<div class="listBox" id="listBox">
					<div class="toolbar">
						<a id="modalInput" href="javascript:void(0)" class="btn" style="float: left;"
							> <span
							id="input" class="btn-left"> <span
								class="btn-text icon-add" style="padding-left: 20px;">添加</span></span>
						</a>
						<a  href="javascript:void(0)" class="btn toolView" style="float: left;"
							> <span
							id="input" class="btn-left"> <span
								class="btn-text icon-view" style="padding-left: 20px;">查看</span></span>
						</a>
						<div class="btn-separator"></div>
						<a href="javascript:void(0)" class="btn toolEdit"  style="float: left;">
							<span class="btn-left"> <span class="btn-text icon-edit"
								style="padding-left: 20px;">修改</span></span>
						</a>
						<div class="btn-separator"></div>
						<a href="javascript:void(0)" class="btn toolRemove" style="float: left;">
							<span class="btn-left"> <span class="btn-text icon-remove"
								style="padding-left: 20px;">删除</span></span>
						</a> <a href="javascript:void(0)" class="btn toolExcel" id="modalExcel" style="float: right;"
							> <span
							id="excel" class="btn-left"> <span
								class="btn-text icon-excel"
								style="padding-left: 20px;padding-right: 10px;">导出excel</span></span>
						</a>
					</div>

					<div class="view" style="width: 100%;">
							<div class="view-header" style="width: 100%; height: 25px;">
								<div class="header-inner" style="display: block;">
									<table border="0" cellspacing="0" cellpadding="0"
										style="height: 26px;">
										<tbody>
											<tr>
												<td rowspan="1"><div class="header-rownumber"></div></td>
												<td field="ck"><div class="header-check">
														<input type="checkbox">
													</div></td>
											</tr>

										</tbody>
									</table>
								</div>
								<div class="header-inner" style="display: block;">
									<table border="0" cellspacing="0" cellpadding="0"
										style="height: 26px;">
										<tbody class="tableBody">
											<tr >
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="view-body" style="width: 100%; height: 413px;">
								<div class="body-inner">
									<table cellspacing="0" cellpadding="0" border="0">
										<tbody>

										</tbody>
									</table>
								</div>
							
							
							<div class="body-inner">
								<table cellspacing="0" cellpadding="0" border="0">
									<tbody class="tableBody">

									</tbody>
								</table>
								</div>
							</div>
						
						<div class="resize-proxy"></div>
						<table id="t_student" style="display: none;"></table>
					</div>

					<div class="view-pager pagination" style="margin:0px">
						<table cellspacing="0" cellpadding="0" border="0">
							<tbody>
								<tr>
									<td><select class="pagination-page-list"><option>5</option>
											<option selected="selected">10</option>
											<option>15</option>
											<option>20</option>
											<option>50</option></select></td>
									<td><div class="btn-separator"></div></td>
									<td><a href="javascript:void(0)" icon="pagination-first"
										class="btn btn-plain btn-disabled"><span class="btn-left"><span
												class="btn-text"><span
													class="btn-empty pagination-first">&nbsp;</span></span></span></a></td>
									<td><a href="javascript:void(0)" icon="pagination-prev"
										class="btn btn-plain btn-disabled"><span class="btn-left"><span
												class="btn-text"><span
													class="btn-empty pagination-prev">&nbsp;</span></span></span></a></td>
									<td><div class="pagination-btn-separator"></div></td>
									<td><span style="padding-left:6px;">第</span></td>
									<td><input class="pagination-num" type="text" value="1"  disabled="disabled" 
										size="2"></td>
									<td><span style="padding-right:6px;">共<span class="allpagenums">1</span>页</span></td>
									<td><div class="pagination-btn-separator"></div></td>
									<td><a href="javascript:void(0)" icon="pagination-next"
										class="btn btn-plain"><span class="btn-left"><span
												class="btn-text"><span
													class="btn-empty pagination-next">&nbsp;</span></span></span></a></td>
									<td><a href="javascript:void(0)" icon="pagination-last"
										class="btn btn-plain"><span class="btn-left"><span
												class="btn-text"><span
													class="btn-empty pagination-last">&nbsp;</span></span></span></a></td>
									<td><div class="pagination-btn-separator"></div></td>
									<td><a href="javascript:void(0)" icon="pagination-load"
										class="btn btn-plain"><span class="btn-left"><span
												class="btn-text"><span
													class="btn-empty pagination-load">&nbsp;</span></span></span></a></td>
								</tr>
							</tbody>
						</table>
						<div class="pagination-info">共0条记录</div>
						<div style="clear:both;"></div>
					</div>
					<div class="submit">
						<!-- Button trigger modal -->
						<button type="button" style="margin-left: 100px"
							class="btn btn-primary btn-lg inputSub">提交</button>
						<button type="button" style="margin-left: 100px"
							class="btn btn-primary btn-lg inputAll">全部提交</button>
						<button type="button" style="margin-left: 100px"
							class="btn btn-primary btn-lg checkedSub">审核通过</button>
						<button type="button" style="margin-left: 100px"
							class="btn btn-primary btn-lg checkedNo">不通过</button>
					</div>
				</div>

			</div>


		</div>

		<!--右侧内容结束-->
	</div>

	<!-- Modal_excel -->
	<div class="modal fade bs-example-modal-sm" id="Modal_excel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modal_excel title</h4>
				</div>
				<form action="${ctx }/manager/keyan/subExcel" enctype="multipart/form-data" method="post">
				<div class="modal-body" style="margin-left:80px;">
					<div class="control-group">
						<!-- File Upload -->
						<div class="controls">
							<input name="excelFile" class="input-file" id="fileInput" type="file">
						</div>
					</div>
					
					<br />
					<div class="control-group" style="margin-left:200px;">
						<div class="controls">
							<a class="help-block">点击导出excel模板</a>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">提交</button>
				</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Modal_input -->
	<div class="modal fade" id="Modal_input" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modal_input title</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal">
						<fieldset>
							<div class="control-group">
								<label class="control-label">File Button</label>

								<!-- File Upload -->
								<div class="controls">
									<input class="input-file" id="fileInput" type="file">
								</div>
							</div>
							<div class="control-group">

								<!-- Text input-->
								<label class="control-label" for="input01">Text input</label>
								<div class="controls">
									<input type="text" placeholder="placeholder"
										class="input-xlarge">
									<p class="help-block">Supporting help text</p>
								</div>
							</div>

							<div class="control-group">

								<!-- Select Basic -->
								<label class="control-label">Select - Basic</label>
								<div class="controls">
									<select class="input-xlarge">
										<option>Enter</option>
										<option>Your</option>
										<option>Options</option>
										<option>Here!</option>
									</select>
								</div>

							</div>

							<div class="control-group">
								<label float="left" class="control-label">Inline radios</label>
								<div class="controls">

									<!-- Inline Radios -->
									<label class="radio inline"> <input type="radio"
										value="1" checked="checked" name="group"> 1
									</label> <label class="radio inline"> <input type="radio"
										value="2" name="group"> 2
									</label> <label class="radio inline"> <input type="radio"
										value="3"> 3
									</label>
								</div>
							</div>

							<div class="control-group">

								<!-- Textarea -->
								<label class="control-label">Textarea</label>
								<div class="controls">
									<div class="textarea">
										<textarea type="" class=""> </textarea>
									</div>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Button</label>

								<!-- Button -->
								<div class="controls">
									<button class="btn btn-success">Button</button>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label">Button</label>

								<!-- Button -->
								<div class="controls">
									<button class="btn btn-warning">Button</button>
								</div>
							</div>

						</fieldset>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button"  class="btn btn-default" >取消</button>
					<button type="button" class="btn btn-primary subForm" >确定</button>
					<button type="button" class="btn btn-primary alterForm">修改</button>
					<button type="button" class="btn btn-primary viewSubForm">提交</button>
					<button type="button" class="btn btn-primary checkYesForm">审核通过</button>
					<button type="button" class="btn btn-primary checkNoForm">不通过</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
