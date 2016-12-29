<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>教学活动</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/teacher/TeaTeachController.js"></script>
</head>
<jsp:include page="../top.jsp"></jsp:include>
<body ng-controller="TeaTeachController">
<!-- <section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">教学活动</h3>
      <div class="input-group input-group-sm">
        <div class="input-group-addon jiaozhi-date-icon">~~</div>
        <input type="text" class="form-control" id=" " placeholder="2016/02/04 ~ 2016/03/15">
      </div>
      <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    </form>
  </div>
</section> -->
<article class="jiaozhi-content">
  <div class="container">
<div class="row">
 <small class="text-danger" style="
    float: right;
"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：教务系统</small>

      <section class="col-xs-12">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under">今日课程<span class="badge jiaozhi-section-badge" style="position: relative;
    right: -0px;
    top: -13px;">{{vm.items[0].length}}</span></span> >></h4>
        <div class=" " ng-repeat="item in vm.items[0]" init="i=0;">
          <dl class="jiaozhi-biaoji-dl">
            <dt>{{$index+1}}、{{item.COURSE_NAME}} </dt>
            <dd>{{item.TIME_}}&nbsp;&nbsp;|&nbsp;&nbsp; 上课教室：{{item.CLASSROOM_NAME||'未维护'}}</dd>
          </dl>
          <ul class="jiaozhi-banji-ul" ng-repeat="items in item.list">
            <li>上课教室：{{items.CLASS_NAME}} <em class="jiaozhi-middle-dot">&#183;</em> 学生人数：{{items.STUS}}</li>
           <!--  <li><a class="jiaozhi-more-link" href="#">更多>></a></li> -->
          </ul>
        </div>
      </section>
    </div>
    <div class="row">
      <section  class="col-xs-12">
        <h4><span class="jiaozhi-alink-under">个人课表</span> >></h4>
        <div cg-kc-table source="vm.items[1]" class="jiaozhi-kebiao-table"></div>
      </section>
    </div>
    <div class="row">
      <section  class="col-xs-12">
        <h4><span class="jiaozhi-alink-under">授课进度</span> >></h4>
        <div class="jiaozhi-jindu-list">
          <div class="row" ng-repeat="item in vm.items[2]">   
            <div class="col-xs-12 col-sm-5"><span class="jiaozhi-icon-ling"></span>{{item.COURSE_NAME}}
            <div style="padding-left: 50px;"><p ng-repeat="ite in item.TEACHINGCLASS.split(',')">{{ite}}</p></div>
            </div>
            <div class="col-xs-12 col-sm-6">
              <div class="progress jiaozhi-progress">
                <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: {{item.ALREADY_COUNTS}}"> <span class=" ">{{item.ALREADY_COUNTS}} </span> </div>
              </div>
            </div>
          </div>
    
        </div>
      </section>
    </div>
    <div class="row">
      <section  class="col-xs-12">
        <h4><span class="jiaozhi-alink-under">授课成绩</span> >></h4>
        <div class="row">
 <div>
<table  class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
	            <tr border="0">
	                <th ng-repeat="item in skcjTitles">{{item}}</th>
	            </tr>
	         </thead>
	        <tbody>
	        <tr ng-repeat="item in vm.items[3]">{{item}}
	         
						<td ng-repeat="(key,value) in item" ng-show="key!='NUM'">{{value}}</td>
					</tr>
	        </tbody>
	</table>
	<span class="loading"ng-show="vm.items[3]==null">正在加载。。。<br/></span>
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
      </section>
    </div>
  </div>
</article>

</body>
</html>
