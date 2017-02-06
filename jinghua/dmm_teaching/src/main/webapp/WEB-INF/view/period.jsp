<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>课时分析</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/period/controller.js"></script>
<script type="text/javascript" src="product/period/service.js"></script>
</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper" >

<div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>课时分析</b>
            </header>
            <div class="ul-no-center">
                <ul class="list-unstyled top-center clearfix">
                <li ng-class="{'has-green':obj.id == data.edu}"
					ng-repeat="obj in data.edu_list">
					<a href=""class="bg-green years"
						ng-click="obj.id == data.edu||changeEdu(obj)">{{obj.mc}}
					</a>
				</li>
                </ul>
            </div>  
        </div>
        <div class="jx-driver-org"></div>
        <div class="performance-change no-border no-bottom">
              <div class="dis-inb">
                  <div class="btn-group" cg-select data="data.xnxq_list" on-change="changeXnxq($value,$data)" ng-model="data.model.xnxq" value-field="id" display-field="mc"></div>
              </div>
			  <div class="dis-inb jx-tip">当前学期开设总课时<span class="text-green jx-tip-n">{{data.abs}}</span>学时。（不包含选课信息）</div>
        </div>  
        
        <div class="performance-change" part-modal show-modal="data.courShow">
        	<div class="block-tit">
            	<p class="dis-inb">课时分布</p>
            </div>
            <div class="row jx-row-10">
            	<div class="col-md-4">
				<div class="img-box" echart config="data.attr.option"></div>
            </div>
            <div class="col-md-4">
				<div class="img-box" echart config="data.nat.option"></div>
            </div>
            <div class="col-md-4">
				<div class="img-box" echart config="data.cate.option"></div>
            </div>
                <div class="clearfix"></div>
            </div>
        </div>
        
        <div class="performance-change" part-modal show-modal="data.dept.mask">
        	<div class="block-tit">
            	<p class="dis-inb">各{{data.deptMc}}课时分布</p>
                <div class="dis-inb">
                  <div class="btn-group" cg-select data="data.course_list" on-change="changCourseBydept($value,$data)" ng-model="data.model.deptCourse" value-field="id" display-field="mc"></div>
              </div>
            </div>
           <div class="img-box" echart config="data.dept.option"></div>
        </div>
        
        <div class="performance-change no-border" part-modal show-modal="data.sub.mask">
        	<div class="block-tit">
            	<p class="dis-inb">各学科课时对比</p>
                <div class="dis-inb">
                  <div class="btn-group" cg-select data="data.course_list" on-change="changCourseBysub($value,$data)" ng-model="data.model.subCourse" value-field="id" display-field="mc"></div>
              </div>
            </div>
             <div class="img-box" echart config="data.subject.option"></div>
        </div>
        
        <div class="jx-driver-org"></div>
        
        <div class="performance-change" part-modal show-modal="data.deptHist.mask">
        	<div class="block-tit">
            	<p class="dis-inb">各{{data.deptMc}}课时发展趋势</p>
                <div class="dis-inb">
                  <div class="btn-group" cg-select data="data.course_list" on-change="changCourseBydeptHistory($value,$data)" ng-model="data.model.deptHisCourse" value-field="id" display-field="mc"></div>
              </div>
            </div>
            <div class="img-box" echart config="data.deptHistory.option"></div>
        </div>
        
        <div class="performance-change no-border" part-modal show-modal="data.subHist.mask">
        	<div class="block-tit">
            	<p class="dis-inb">各学科课时发展趋势</p>
                <div class="dis-inb">
                  <div class="btn-group" cg-select data="data.course_list" on-change="changCourseBysubHistory($value,$data)" ng-model="data.model.subHisCourse" value-field="id" display-field="mc"></div>
              </div>
            </div>
            <div class="img-box" echart config="data.subjectHistory.option"></div>
        </div>
    </div><!--end ss-mark-main-->
   <!-------------------------------------------------------以上为主要内容区------------------------------------------------>
</div>
</body>
</html>
