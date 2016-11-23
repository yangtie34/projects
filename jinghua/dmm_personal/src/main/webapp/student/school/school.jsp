<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/student/school/school.jsp"/>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>我的校园</title>
<link rel="stylesheet" type="text/css" href="../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body class="rxs-bg" ng-controller="controller">
<div class="center-block col-md-8" style="float: none;padding: 0px;">
<div class="rxs-menu-top   rxs-top-blue-bg">
  <div class="media">
    <div class="media-left media-middle"><img class="img-circle" width="80" ng-src="../images/{{school.school_img}}"></div>
    <div class="media-body media-middle rxs-pad-lft-10">
      <h4 class="rxs-ft-20"><b>{{school.schoolName}} </b></h4>
      <p class="rxs-ft-12">建校于{{school.schoolYear}}年，在校生{{stuCounts}}人。<br>
        校训：{{school.school_motto}}</p>
    </div>
  </div>
</div>
<div class="rxs-comn-list">
  <ul class="rxs-link-ul clearfix">
    <li class="rxs-width-50"><a style="text-decoration:none;" href="{{school.school_baike_url}}"><img src="../images/baike.png">校园百科</a></li>
    <li class="rxs-width-50"><a style="text-decoration:none;" href="/personal/wechat/map/map.jsp"><img src="../images/ditu.png">校园地图 </a></li>
  </ul>
  <ul class="rxs-link-ul clearfix">
    <li class="rxs-width-50">
      <div class="media">
        <div class="media-left media-middle rxs-text-nowrap"><img src="../images/lou-icon.png">
          <h5>教学楼</h5>
        </div>
        <div class="media-body media-middle rxs-cor-org">{{map.teachingbuilding}}</div>
      </div>
    </li>
    <li class="rxs-width-50">
      <div class="media">
        <div class="media-left media-middle rxs-text-nowrap"><img src="../images/sushe.png">
          <h5>宿舍楼</h5>
        </div>
        <div class="media-body media-middle rxs-cor-org">{{map.dormitorybuilding}}</div>
      </div>
    </li>
    <li class="rxs-width-50">
      <div class="media">
        <div class="media-left media-middle rxs-text-nowrap"><img src="../images/can-icon.png">
          <h5>餐厅</h5>
        </div>
        <div class="media-body media-middle rxs-cor-org">{{map.restaurant}}</div>
      </div>
    </li>
    <li class="rxs-width-50">
      <div class="media">
        <div class="media-left media-middle rxs-text-nowrap"><img src="../images/shop.png">
          <h5>商店</h5>
        </div>
        <div class="media-body media-middle rxs-cor-org">{{map.shop}}</div>
      </div>
    </li>
    <li class="rxs-width-50"> 
        <img src="../images/tushu.png">藏书 
        <h4 class="rxs-cor-org"> {{map.book}} </h4>
      
    </li>
    <li class="rxs-width-50"> 
        <img src="../images/teacher.png"> 教师团队 
        <h4 class="rxs-cor-org">{{map.teacher}}</h4> 
      
    </li>
  </ul>
</div>
</div>
</body>
</html>