<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html >
<html ng-app="app"> 
<head>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<base href="<%=root%>/social/liao/personal/mine.jsp"/>
<link rel="stylesheet" type="text/css" href="../../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<title>个人主页</title>
</head>
<body ng-controller="controller" class="rxs-bg">
			<input type="text" hidden="hidden" id="um" value="${username }"/>
				<div class="rxs-menu-top rxs-bg-f4 text-center"> 
				<img class="img-circle" width="85" ng-src="{{personalInfo.wechat_head_img}}">
				<i class="fa fa-user"  ng-if="personalInfo.wechat_head_img == '' " style="height: 60px;width: 60px;font-size: 40px;color: #fff;"></i>
			  <h4>{{personalInfo.real_name}} <i ng-show="personalInfo.sex == '女'" class="rxs-sex rxs-girl-icon"></i><i ng-show="personalInfo.sex == '男'" class="rxs-sex rxs-boy-icon"></i></h4>
			  {{personalInfo.username}}</div></a>
			<div class="rxs-comn-list">
			  <ul class="rxs-comn-ul">
			    <li ng-show="personalInfo.is_stu == '1'"><span class="rxs-text-ccc">院系</span>{{personalInfo.dept_name}}</li>
			    <li ng-show="personalInfo.is_stu == '0'"><span class="rxs-text-ccc">部门</span>{{personalInfo.dept_name}}</li>
			    <li ng-show="personalInfo.is_stu == '1'"><span class="rxs-text-ccc">专业班级</span>{{personalInfo.class_name}}</li>
			    <li ng-show="personalInfo.is_stu == '1'"><span class="rxs-text-ccc">入校年级</span>{{personalInfo.enroll_grade}}</li>
			    <li ng-show="personalInfo.is_stu == '0'"><span class="rxs-text-ccc">职称</span>{{personalInfo.zyjszw}}</li>
			    <li ng-show="personalInfo.is_stu == '0'"><span class="rxs-text-ccc">学位</span>{{personalInfo.degree_}}</li>
			    <li><span class="rxs-text-ccc">联系电话</span>{{personalInfo.tel}}</li>
			  </ul>
			  <div class="rxs-bg-fff">
			    <table class="table rxs-ft-15 rxs-tab-li" border="0" width="100%" cellpadding="0" cellspacing="0">
			      <tr>
			        <td rowspan="2"><img src="../../images/gr-01.png" width="24"><span>累计消费</span>
			          <h4 class="rxs-cor-org"> {{card.total_pay}}</h4></td>
			        <td  width="50%"> 单笔最高<span class="rxs-cor-org">{{card.pay_max}}</span> </td>
			      </tr>
			      <tr>
			        <td  width="50%"> 日均消费<span class="rxs-cor-org">{{card.day_avg}}</span></td>
			      </tr>
			    </table>
			   <h3 class="text-center rxs-ft-15">餐均消费</h3>
			    <ul class="rxs-link-ul rxs-mt-0 clearfix rxs-text-16">
			      <li class="rxs-width-50 rxs-width-3"> <img src="../../images/gr-02.png" width="24"><span class="rxs-cor-org">{{card.zao}}</span>
			        <h4  class="rxs-ft-15">早餐</h4>
			      </li>
			      <li class="rxs-width-50 rxs-width-3"> <img src="../../images/gr-03.png" width="24"><span class="rxs-cor-org">{{card.wu}}</span>
			        <h4  class="rxs-ft-15">午餐</h4>
			      </li>
			      <li class="rxs-width-50 rxs-width-3"> <img src="../../images/gr-04.png" width="22"><span class="rxs-cor-org">{{card.wan}}</span>
			        <h4  class="rxs-ft-15">晚餐</h4>
			      </li>
			    </ul>
			  </div>
			  <div class="rxs-bg-fff">
			    <table class="table rxs-ft-15 rxs-bg-fff rxs-mar-tb-10 rxs-tab-li" style="height:104px" border="0" width="100%" cellpadding="0" cellspacing="0">
			      <tr>
			        <td width="50%"><img src="../../images/gr-05.png" width="24"><span>累计借阅</span>
			          <h4 class="rxs-cor-org"> {{book.total_borrow}}</h4></td>
			        <td width="50%"><img src="../../images/liao-02.png" width="24"><span>在阅图书</span>
			          <h4 class="rxs-cor-org">{{book.in_borrow}}</h4></td>
			      </tr>
			    </table>
			    <h3 class="text-center rxs-ft-15">正在阅读图书</h3>
			     <div class="rxs-pad-lr">
			     <a class="rxs-link-br" ng-repeat = 'b in book.inBorrowList'>{{b.book_name}}</a></div> 
			  </div>
			  <div class="rxs-bg-fff">
			    <table class="table rxs-ft-15 rxs-bg-fff rxs-mar-tb-10 rxs-tab-li" style="height:104px" border="0" width="100%" cellpadding="0" cellspacing="0">
			      <tr>
			        <td width="50%"><img src="../../images/gr-06.png" width="24"><span>已修课程</span>
			          <h4 class="rxs-cor-org"> {{course.total_course}}</h4></td>
			        <td width="50%"><img src="../../images/gr-07.png" width="22"><span>绩点</span>
			          <h4 class="rxs-cor-org">{{course.gpa}}</h4></td>
			      </tr>
			    </table>
			    <h3 class="text-center rxs-ft-15">最优成绩</h3>
			    <table class="table rxs-excl-tab"  border="0" width="100%" cellpadding="0" cellspacing="0">
			    <thead><tr><td>课程</td><td>成绩</td><td>专业排名</td></tr></thead>
			      <tbody>
			      <tr ng-repeat = 'c in course.bestCourse'>
			        <td>{{c.course_name}}</td>
			        <td class="rxs-cor-ff9">{{c.centesimal_score}}</td>
			        <td class="rxs-cor-green">{{c.rn}}</td>
			      </tr>
			      </tbody>
			    </table>
			    <h3 class="text-center rxs-ft-15">未通过课程</h3>
			     <div class="rxs-pad-lr"><a ng-repeat = "cn in course.notPassCourse" class="rxs-link-br rxs-link-br-mei">{{cn.course_name}}</a></div> 
			  </div>
			</div>
</body>
</html>