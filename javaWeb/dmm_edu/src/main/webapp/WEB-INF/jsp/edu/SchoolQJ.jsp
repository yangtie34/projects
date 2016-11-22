<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/edu/images"/>
<c:set var="edu" value="${pageContext.request.contextPath}/static/resource/edu"/>
<c:set var="schIco" value="${pageContext.request.contextPath}/static/resource/edu/schIcos"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>学校全景</title>
    <link rel="stylesheet" type="text/css" href="${edu}/css/font-awesome.min-3.2.1.css">
<link rel="stylesheet" type="text/css" href="${edu}/css/edu-style.css">
    <script type="text/javascript" src="${ctxStatic}/edu/index.js"></script>
    <script type="text/javascript" src="${ctx }/static/common/jsEchar/echartsUtil.js"></script>
        <script type="text/javascript" src="${ctx }/static/common/jsEchar/common.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/edu/SchoolQjController.js"></script>
	<script>
	var schoolId="<%=request.getAttribute("schoolId").toString()%>";
	</script>
</head>
  <body ng-controller="SchoolQjController">
<nav class="navbar-inverse">
  <div class="container">
    <div class="navbar-header"> <span class="edu-tit" href="#"> 学校全景<i class="edu-i-18"> School Panorama</i> </span> </div>
    <p class="edu-t-rt"><a href="${ctx}/logout" class="navbar-link"title="退出"><i class="icon-off icon-2x"></i></a></p>
  </div>
</nav>
<div class="edu-alert alert alert-warning " >
  <div class="container" style="padding: 0 25px;">
    <button type="button" class="close"  ng-click=" warning();"><span aria-hidden="true">×</span></button>
    <i class="icon-exclamation-sign"></i> 从学校的基本信息、在校生、招生数、毕业生数、教职工、专任教师 、资产方面展示指定学校的全景数据。 </div>
</div>
<div class="container edu-rela">
  <div class="edu-rt-menu">
    <ul class="edu-list">
    <li ng-repeat="ite in title">
    <a class="edu-list-a"ng-class="ite.now?'edu-green-bg':''" href="#edu-0{{$index+1}}" ng-click="titleNow(ite);">{{ite.name}}</a><a class="edu-up-hd" ng-click="titleShow(ite,$index);"><small>
    <i  ng-class="ite.show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{ite.show?'收起':'展开'}} </small></a>
    </li>
     <!--  <li> <a class="edu-list-a edu-green-bg" href="#edu-01">学校基本信息</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-02" class="edu-list-a">在校生（招生、在校、毕业）</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-03" class="edu-list-a">教职工情况</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-04" class="edu-list-a">校舍情况</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-05" class="edu-list-a">资产情况</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-06" class="edu-list-a edu-green-cor">教学维度</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-07" class="edu-list-a  edu-green-cor">科研维度</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
      <li> <a href="#edu-08" class="edu-list-a edu-green-cor">其他维度</a><a class="edu-up-hd" href="#"><small><i class=" icon-double-angle-up icon-large"></i> 收起</small></a></li>
     --></ul>
  </div>
  <div class="edu-lt-cont">
    <div class="edu-brt-box" >
      <h3 class="edu-ft-22 edu-mt-10" id="edu-01">学校基本信息<a class="pull-right" href="#" ng-click="titleShow(title[0],0);"><small>
    <i ng-class="title[0].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[0].show?'收起':'展开'}} </small></a>
        <!--icon-double-angle-down   展开样式-->
      </h3>
      <div class=" ">
        <div class="media">
          <div class="media-left media-middle"><img ng-src="${schIco}/{{vm.items[0].icoId}}.png"></div>
          <div class="media-body media-middle edu-pl-15">
            <h3>{{vm.items[0].chName}}&nbsp;&nbsp;<small>
            <span class="badge  edu-green-bg edu-mt-0-5">{{vm.items[1][1]['1131'].value}}</span></small></h3>
            <dl class="edu-tb-dl">
              <dd>{{vm.items[1][1]['11C'].out||'成立日期：yyyy-mm-dd'}} <span style=" cursor: pointer;" ng-click="lsygDiv=true;"class="badge edu-bg02 edu-mt-0-5">历史演革</span></dd>
              <dd> {{vm.items[1][1]['115'].out}}</dd>
              <dd>{{vm.items[1][1]['118'].out}} </dd>
              <dd> {{vm.items[1][1]['11A'].out}}</dd>
              <dd>{{vm.items[1][1]['119'].out}}</dd>
              <dt>{{vm.items[1][1]['114'].out}}</dt>
            </dl>
          </div>
        </div>
        <div class="edu-mar-tb">
          <h4 class="edu-btm-line"><span class="edu-green-cor edu-ft-16 edu-btm">学校基本信息</span></h4>
          <dl class="edu-tb-dl">
          
           <dd>{{vm.items[1][1]['112'].out}} </dd>
            <dd>{{vm.items[1][1]['1121'].out}} </dd>
            <dd>{{vm.items[1][1]['11221'].out}} </dd>
            <dd>{{vm.items[1][1]['11222'].out}} </dd>
            <dd>{{vm.items[1][1]['11223'].out}} </dd>
            <dd>{{vm.items[1][1]['11224'].out}} </dd>
            <dd>{{vm.items[1][1]['11225'].out}} </dd>
            <dd>{{vm.items[1][1]['11226'].out}} </dd>
            <dd>{{vm.items[1][1]['11227'].out}} </dd>
             <dd>{{vm.items[1][1]['1135'].out}} </dd>
            <dd>{{vm.items[1][1]['1131'].out}} </dd>
             <dd>{{vm.items[1][1]['1132'].out}} </dd>
              <dd>{{vm.items[1][1]['1133'].out}} </dd>
               <dd>{{vm.items[1][1]['1134'].out}} </dd>
           <!--  
            <dt>学校机构代码结构(46位)：4141010459410172160403410172000000811411011210</dt>
            <dd>学校(机构)标识码：4141010459 </dd>
            <dd>地址代码：410172160403</dd>
            <dd>属地管理教育行政部门代码：410172000000 </dd>
            <dd>举办者代码：811</dd>
            <dd>办学类型代码：811</dd>
            <dd>性质类别代码：411</dd>
            <dd>城乡分类代码：02</dd>
            <dd>独立设置少数民族校代码：121 </dd> -->
          </dl>
        </div>
   <!--      <div class="edu-mar-tb">
          <h4 class="edu-btm-line"><span class="edu-green-cor edu-ft-16 edu-btm">学校基本信息</span></h4>
          <dl class="edu-tb-dl">
           <dd>{{vm.items[1][1]['1135'].out}} </dd>
            <dd>{{vm.items[1][1]['1131'].out}} </dd>
             <dd>{{vm.items[1][1]['1132'].out}} </dd>
              <dd>{{vm.items[1][1]['1133'].out}} </dd>
               <dd>{{vm.items[1][1]['1134'].out}} </dd>
               
            <dd>举办者名称：省级教育部门 </dd>
            <dd>办别：公办 </dd>
            <dd> 举办者性质：地方</dd>
            <dd>举办者类型分组：教育部门 </dd>
            <dd> 举办者编码：811 </dd>
          </dl>
        </div> -->
      </div>
    </div>
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10" id="edu-02">在校生情况（招生、在校、毕业）<a class="pull-right" ng-click="titleShow(title[1],1);"><small>
    <i ng-class="title[1].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[1].show?'收起':'展开'}} </small></a></h3>
      <!--tools-->
      <div class=" ">
      <div cg-combo-nftj result="year"></div>
<!--         <div class="edu-bdr edu-mar-tb clearfix"> <a class="edu-link" href="#">今年</a> <a class="edu-link edu-lk-st" href="#">去年</a> <a class="edu-link" href="#">近三年</a> <a class="edu-link" href="#">近十年</a>
          select
          <div class="btn-group pull-right">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 2014年 <span class="caret"></span> </button>
            <ul class="dropdown-menu">
              <li><a href="#">2014</a></li>
              <li><a href="#">2014</a></li>
              <li><a href="#">2014</a></li>
            </ul>
          </div>
          select
          select
          <div class="btn-group pull-right">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 2014年 <span class="caret"></span> </button>
            <ul class="dropdown-menu">
              <li><a href="#">2014</a></li>
              <li><a href="#">2014</a></li>
              <li><a href="#">2014</a></li>
            </ul>
          </div>
          select
        </div> -->
        <!--tools-->
       <table class="table table-bordered edu-table-dashed" border="0" cellpadding="0" cellspacing="0">
          <thead>
            <tr>
              <th colspan="2" rowspan="2"></th>
               <th colspan="3" class="edu-info">毕业数</th>
              <th colspan="3" class="edu-sucess">招生数</th>
              <th colspan="3" class="edu-warning">在校数</th>
            </tr>
            <tr>
              <th>男</th>
              <th>女</th>
              <th>合计</th>
              <th>男</th>
              <th>女</th>
              <th>合计</th>
              <th>男</th>
              <th>女</th>
              <th>合计</th>
            </tr>
          </thead>
          <tbody>
           <tr class="danger">
              <th rowspan="3" class="edu-danger">研究生</th>
              <td>硕</td>
              <td>{{vm.items[2][1]['1213141'].value-vm.items[2][1]['1213241'].value}}</td>
              <td>{{vm.items[2][1]['1213241'].value}}</td>
               <td>{{vm.items[2][1]['1213141'].value}}</td>
               <td>{{vm.items[2][1]['1212141'].value-vm.items[2][1]['1212241'].value}}</td>
              <td>{{vm.items[2][1]['1212241'].value}}</td>
              <td>{{vm.items[2][1]['1212141'].value}}</td>
              <td>{{vm.items[2][1]['1211141'].value-vm.items[2][1]['1211241'].value}}</td>
              <td>{{vm.items[2][1]['1211241'].value}}</td>
              <td>{{vm.items[2][1]['1211141'].value}}</td>
            </tr>
            <tr class="danger">
              <td>博</td>
              <td>{{vm.items[2][1]['1213142'].value-vm.items[2][1]['1213242'].value}}</td>
              <td>{{vm.items[2][1]['1213242'].value}}</td>
               <td>{{vm.items[2][1]['1213142'].value}}</td>
               <td>{{vm.items[2][1]['1212142'].value-vm.items[2][1]['1212242'].value}}</td>
              <td>{{vm.items[2][1]['1212242'].value}}</td>
              <td>{{vm.items[2][1]['1212142'].value}}</td>
              <td>{{vm.items[2][1]['1211142'].value-vm.items[2][1]['1211242'].value}}</td>
              <td>{{vm.items[2][1]['1211242'].value}}</td>
              <td>{{vm.items[2][1]['1211142'].value}}</td>
            </tr>
            <tr class="danger">
              <td>合计</td>
              <td>{{vm.items[2][1]['121314'].value-vm.items[2][1]['121324'].value}}</td>
              <td>{{vm.items[2][1]['121324'].value}}</td>
              <td>{{vm.items[2][1]['121314'].value}}</td>
              <td>{{vm.items[2][1]['121214'].value-vm.items[2][1]['121224'].value}}</td>
              <td>{{vm.items[2][1]['121224'].value}}</td>
              <td>{{vm.items[2][1]['121214'].value}}</td>
              <td>{{vm.items[2][1]['121114'].value-vm.items[2][1]['121124'].value}}</td>
              <td>{{vm.items[2][1]['121124'].value}}</td>
              <td>{{vm.items[2][1]['121114'].value}}</td>
            </tr>
            <tr class="success">
              <th rowspan="3" class="edu-sucess">普通本专生</th>
              <td>本</td>
              <td>{{vm.items[2][1]['1213112'].value-vm.items[2][1]['1213212'].value}}</td>
              <td>{{vm.items[2][1]['1213212'].value}}</td>
              <td>{{vm.items[2][1]['1213112'].value}}</td>
              <td>{{vm.items[2][1]['1212112'].value-vm.items[2][1]['1212212'].value}}</td>
              <td>{{vm.items[2][1]['1212212'].value}}</td>
              <td>{{vm.items[2][1]['1212112'].value}}</td>
              <td>{{vm.items[2][1]['1211112'].value-vm.items[2][1]['1211212'].value}}</td>
              <td>{{vm.items[2][1]['1211212'].value}}</td>
              <td>{{vm.items[2][1]['1211112'].value}}</td>
            </tr>
            <tr class="success">
              <td>专</td>
              <td>{{vm.items[2][1]['1213111'].value-vm.items[2][1]['1213211'].value}}</td>
              <td>{{vm.items[2][1]['1213211'].value}}</td>
              <td>{{vm.items[2][1]['1213111'].value}}</td>
              <td>{{vm.items[2][1]['1212111'].value-vm.items[2][1]['1212211'].value}}</td>
              <td>{{vm.items[2][1]['1212211'].value}}</td>
              <td>{{vm.items[2][1]['1212111'].value}}</td>
              <td>{{vm.items[2][1]['1211111'].value-vm.items[2][1]['1211211'].value}}</td>
              <td>{{vm.items[2][1]['1211211'].value}}</td>
              <td>{{vm.items[2][1]['1211111'].value}}</td>
            </tr>
            <tr class="success">
              <td>合计</td>
              <td>{{vm.items[2][1]['121311'].value-vm.items[2][1]['121321'].value}}</td>
              <td>{{vm.items[2][1]['121321'].value}}</td>
              <td>{{vm.items[2][1]['121311'].value}}</td>
              <td>{{vm.items[2][1]['121211'].value-vm.items[2][1]['121221'].value}}</td>
              <td>{{vm.items[2][1]['121221'].value}}</td>
              <td>{{vm.items[2][1]['121211'].value}}</td>
              <td>{{vm.items[2][1]['121111'].value-vm.items[2][1]['121121'].value}}</td>
              <td>{{vm.items[2][1]['121121'].value}}</td>
              <td>{{vm.items[2][1]['121111'].value}}</td>
            </tr>
            <tr class="warning">
              <th rowspan="3" class="edu-warning">成人本专生</th>
              <td>本</td>
              <td>{{vm.items[2][1]['121322'].value-vm.items[2][1]['1213221'].value}}</td>
              <td>{{vm.items[2][1]['1213221'].value}}</td>
              <td>{{vm.items[2][1]['121322'].value}}</td>
              <td>{{vm.items[2][1]['1212122'].value-vm.items[2][1]['1212222'].value}}</td>
              <td>{{vm.items[2][1]['1212222'].value}}</td>
              <td>{{vm.items[2][1]['1212122'].value}}</td>
              <td>{{vm.items[2][1]['1211122'].value-vm.items[2][1]['1211222'].value}}</td>
              <td>{{vm.items[2][1]['1211222'].value}}</td>
              <td>{{vm.items[2][1]['1211122'].value}}</td>
            </tr>
            <tr class="warning">
              <td>专</td>
              <td>{{vm.items[2][1]['1213121'].value-vm.items[2][1]['1213222'].value}}</td>
              <td>{{vm.items[2][1]['1213222'].value}}</td>
              <td>{{vm.items[2][1]['1213121'].value}}</td>
              <td>{{vm.items[2][1]['1212121'].value-vm.items[2][1]['1212221'].value}}</td>
              <td>{{vm.items[2][1]['1212221'].value}}</td>
              <td>{{vm.items[2][1]['1212121'].value}}</td>
              <td>{{vm.items[2][1]['1211121'].value-vm.items[2][1]['1211221'].value}}</td>
              <td>{{vm.items[2][1]['1211221'].value}}</td>
              <td>{{vm.items[2][1]['1211121'].value}}</td>
            </tr>
            <tr class="warning">
              <td>合计</td>
              <td>{{vm.items[2][1]['121312'].value-vm.items[2][1]['121322'].value}}</td>
              <td>{{vm.items[2][1]['121322'].value}}</td>
              <td>{{vm.items[2][1]['121312'].value}}</td>
              <td>{{vm.items[2][1]['121212'].value-vm.items[2][1]['121222'].value}}</td>
              <td>{{vm.items[2][1]['121222'].value}}</td>
              <td>{{vm.items[2][1]['121212'].value}}</td>
              <td>{{vm.items[2][1]['121112'].value-vm.items[2][1]['121122'].value}}</td>
              <td>{{vm.items[2][1]['121122'].value}}</td>
              <td>{{vm.items[2][1]['121112'].value}}</td>
            </tr>
            <tr class="info">
              <th rowspan="3" class="edu-info">网络本专生</th>
              <td>本</td>
              <td>{{vm.items[2][1]['1213132'].value-vm.items[2][1]['1213232'].value}}</td>
              <td>{{vm.items[2][1]['1213232'].value}}</td>
              <td>{{vm.items[2][1]['1213132'].value}}</td>
              <td>{{vm.items[2][1]['1212132'].value-vm.items[2][1]['1212232'].value}}</td>
              <td>{{vm.items[2][1]['1212232'].value}}</td>
              <td>{{vm.items[2][1]['1212132'].value}}</td>
              <td>{{vm.items[2][1]['1211132'].value-vm.items[2][1]['1211232'].value}}</td>
              <td>{{vm.items[2][1]['1211232'].value}}</td>
              <td>{{vm.items[2][1]['1211132'].value}}</td>
            </tr>
            <tr class="info">
              <td>专</td>
              <td>{{vm.items[2][1]['1213131'].value-vm.items[2][1]['1213231'].value}}</td>
              <td>{{vm.items[2][1]['1213231'].value}}</td>
              <td>{{vm.items[2][1]['1213131'].value}}</td>
              <td>{{vm.items[2][1]['1212131'].value-vm.items[2][1]['1212231'].value}}</td>
              <td>{{vm.items[2][1]['1212231'].value}}</td>
              <td>{{vm.items[2][1]['1212131'].value}}</td>
              <td>{{vm.items[2][1]['1211131'].value-vm.items[2][1]['1211231'].value}}</td>
              <td>{{vm.items[2][1]['1211231'].value}}</td>
              <td>{{vm.items[2][1]['1211131'].value}}</td>
            </tr>
            <tr class="info">
              <td>合计</td>
              <td>{{vm.items[2][1]['121313'].value-vm.items[2][1]['121323'].value}}</td>
              <td>{{vm.items[2][1]['121323'].value}}</td>
              <td>{{vm.items[2][1]['121313'].value}}</td>
              <td>{{vm.items[2][1]['121213'].value-vm.items[2][1]['121223'].value}}</td>
              <td>{{vm.items[2][1]['121223'].value}}</td>
              <td>{{vm.items[2][1]['121213'].value}}</td>
              <td>{{vm.items[2][1]['121113'].value-vm.items[2][1]['121123'].value}}</td>
              <td>{{vm.items[2][1]['121123'].value}}</td>
              <td>{{vm.items[2][1]['121113'].value}}</td>
            </tr>
                      <tr>
              <td colspan="2">总合计</td>
              <td>{{vm.items[2][1]['1213'].value-vm.items[2][1]['12132'].value}}</td>
              <td>{{vm.items[2][1]['12132'].value}}</td>
              <td>{{vm.items[2][1]['1213'].value}}</td>
              <td>{{vm.items[2][1]['1212'].value-vm.items[2][1]['12122'].value}}</td>
              <td>{{vm.items[2][1]['12122'].value}}</td>
              <td>{{vm.items[2][1]['1212'].value}}</td>
              <td>{{vm.items[2][1]['1211'].value-vm.items[2][1]['12112'].value}}</td>
              <td>{{vm.items[2][1]['12112'].value}}</td>
              <td>{{vm.items[2][1]['1211'].value}}</td>
            </tr> 
          </tbody>
        </table>
      </div>
    </div>
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10" id="edu-03">教职工情况 <a class="pull-right" ng-click="titleShow(title[2],2);"><small>
    <i ng-class="title[2].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[2].show?'收起':'展开'}} </small></a></h3>
      <div class=" ">
        <div class="edu-info-box row">
          <div class="col-xs-4">
            <table width="100%" class="edu-table-bg" border="0" cellpadding="0" cellspacing="0">
              <tbody><tr>
                <td class="edu-bg01"><h3>郑州大学</h3>
                  <p><small>教职工总人数</small></p>
                  <h2>{{vm.items[3][1]['1221'].value}}人</h2></td>
              </tr>
            </tbody></table>
          </div>
          <div class="col-xs-4">
            <table class="edu-table-bg" width="100%" border="0" cellpadding="0" cellspacing="0">
              <tbody><tr>
                <td class="edu-bg02"><h3 class="text-left">男</h3>
                  <p class="text-right">{{vm.items[3][1]['1221'].value-vm.items[3][1]['12211'].value}}人 <small><small>占比</small></small>
                  {{(vm.items[3][1]['1221'].value-vm.items[3][1]['12211'].value)/vm.items[3][1]['1221'].value*100 |number : 2}}%</p></td>
              </tr>
              <tr>
                <td class="edu-bg03"><h3 class="text-left">女</h3>
                  <p class="text-right">{{vm.items[3][1]['12211'].value}}人 <small>占比</small> {{vm.items[3][1]['12211'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</p></td>
              </tr>
            </tbody></table>
          </div>
          <div class="col-xs-4">
            <table class="edu-table-bg" width="100%" border="0" cellpadding="0" cellspacing="0">
              <tbody><tr>
                <td class="edu-bg04"><h4 class="text-left">本部教职工</h4>
                  <p class="text-right">{{vm.items[3][1]['12213'].value}}人 <small>占比</small> {{vm.items[3][1]['12213'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</p></td>
              </tr>
              <tr>
                <td class="edu-bg05"><h4 class="text-left">离退休人员</h4>
                  <p class="text-right">{{vm.items[3][1]['1222'].value}}人 <small>占比</small> {{vm.items[3][1]['1222'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</p></td>
              </tr>
              <tr>
                <td class="edu-bg04"><h4 class="text-left">外聘教师</h4>
                  <p class="text-right">{{vm.items[3][1]['1223'].value}}人 <small>占比</small> {{vm.items[3][1]['1223'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</p></td>
              </tr>
            </tbody></table>
          </div>
        </div>
        <!--职称-->
<div class="edu-zc-box">
                  <!---->
          <div class="row">
            <div class="col-xs-2">
              <div class="edu-bg04 edu-lft-tit"><i class="icon-cog icon-2x"></i><br>
                部门</div>
            </div>
            <div class="col-xs-10">
              <div class="row edu-xs-pad">
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">校本部</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['12213'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['12213'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">科研机构</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['12214'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['12214'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">校办企业</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['12215'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['12215'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">其他附设机构</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['12216'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['12216'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
              </div>
            </div>
          </div>
          <!---->
                  <!---->
          <div class="row">
            <div class="col-xs-2">
              <div class="edu-bg03 edu-lft-tit"><i class="icon-cog icon-2x"></i><br>
                人员</div>
            </div>
            <div class="col-xs-10">
              <div class="row edu-xs-pad">
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">聘请校外教师</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1222'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1222'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">离退休人员</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1223'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1223'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">附属中小学幼儿园教职工</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1224'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1224'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">集体所有制人员</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1225'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1225'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
              </div>
            </div>
          </div>
          <!---->
          <div class="row">
            <div class="col-xs-2">
              <div class="edu-bg01 edu-lft-tit"><i class="icon-cog icon-2x"></i><br>
                职称</div>
            </div>
            <div class="col-xs-10">
              <div class="row edu-xs-pad">
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">正高级</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1221331'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1221331'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">副高级</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1221332'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1221332'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">中级</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1221333'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1221333'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">初级</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1221334'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1221334'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
              </div>
            </div>
          </div>
          <!---->
          <div class="row">
            <div class="col-xs-2">
              <div class="edu-bg02 edu-lft-tit"><i class="icon-cog icon-2x"></i><br>
                学历</div>
            </div>
            <div class="col-xs-10">
              <div class="row edu-xs-pad">
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">博士研究生</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['122612'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['122612'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">硕士研究生</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['122613'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['122613'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">本科</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['122614'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['122614'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">专科及以下</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['122615'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['122615'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
              </div>
            </div>
          </div>
          <!---->
          <!---->
          <div class="row">
            <div class="col-xs-2">
              <div class="edu-bg03 edu-lft-tit"><i class="icon-cog icon-2x"></i><br>
                学位</div>
            </div>
            <div class="col-xs-10">
              <div class="row edu-xs-pad">
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">博士</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1226111'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1226111'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">硕士</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">{{vm.items[3][1]['1226112'].value}}人</td>
                    </tr>
                    <tr><td class="text-right">{{vm.items[3][1]['1226112'].value/vm.items[3][1]['1221'].value*100 |number : 2}}%</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">&nbsp;</td>
                    </tr>
                    <tr><td class="text-right">&nbsp;</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
                <div class="col-xs-3">
                  <table class="edu-table-zc" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody><tr>
                      <td class="text-left">&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="text-center edu-green-cor">&nbsp;</td>
                    </tr>
                    <tr><td class="text-right">&nbsp;</td>
                    </tr>
                    <tr>
                  </tr></tbody></table>
                </div>
              </div>
            </div>
          </div>
          <!---->


        </div>
        <!--职称-->
      </div>
    </div>
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10" id="edu-04">校舍情况 <a class="pull-right" ng-click="titleShow(title[3],3);"><small>
    <i ng-class="title[3].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[3].show?'收起':'展开'}} </small></a></h3>
      <div class=" ">
        <div class="row">
        <div stu-chart config="xsbzt" style="height:310px;"class="col-xs-6"> </div>
                        	
         <%--  <div class="col-xs-6"><img src="${ctxImg}/xs-qk.jpg"></div> --%>
          <div class="col-xs-6">
            <table class="table edu-space-table" width="100%" border="0" cellpadding="0" cellspacing="0">
              <thead>
                <tr>
                  <th colspan="2" class="text-center"> <i class="edu-green-cor">生活用房</i> 总计：{{vm.items[4][1]['12341'].value*0.0015|number:2}}亩</th>
                </tr>
              </thead>
              <tbody>
              <tr>
                <td> <span class="glyphicon glyphicon-stop" style="color:{{echarColor[0]}}"></span>学生宿舍公寓 </td>
                <td>{{vm.items[4][1]['12342'].value*0.0015|number:2}}亩</td>
              </tr>
              <tr>
                <td> <span class="glyphicon glyphicon-stop" style="color:{{echarColor[1]}}"></span>学生食堂 </td>
                <td>{{vm.items[4][1]['12342'].value*0.0015|number:2}}亩</td>
              </tr>
              <tr>
                <td> <span class="glyphicon glyphicon-stop" style="color:{{echarColor[2]}}"></span>教工宿舍公寓 </td>
                <td>{{vm.items[4][1]['12343'].value*0.0015|number:2}}亩</td>
              </tr>
              <tr>
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[3]}}"></span> 教工食堂 </td>
                <td>{{vm.items[4][1]['12344'].value*0.0015|number:2}}亩</td>
              </tr>
              <tr>
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[4]}}"></span> 生活福利及附属用房 </td>
                <td>{{vm.items[4][1]['12345'].value*0.0015|number:2}}亩</td>
              </tr>
            </tbody></table>
          </div>
        </div>
      </div>
    </div>
    <!--资产-->
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10" id="edu-05">资产情况 <a class="pull-right" ng-click="titleShow(title[4],4);"><small>
    <i ng-class="title[4].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[4].show?'收起':'展开'}} </small></a></h3>
      <div class=" ">
        <table class="edu-table-bg edu-table-block" width="100%" border="0" cellpadding="0" cellspacing="0">
          <tbody><tr>
            <td rowspan="2" class="edu-bg01" colspan="2"><div class="media">
                <div class="media-left"><img src="${ctxImg}/home.png"></div>
                <div class="media-body text-left">
                  <h3><b>占地面积</b></h3>
                  <h4>{{vm.items[5][1]['1241'].value*0.0015|number:2}}亩</h4>
                </div>
              </div>
              <ul class="edu-tb-ul edu-lft-p">
                <li class="edu-tb-li"> 绿化用地<br>
                  {{vm.items[5][1]['12411'].value*0.0015|number:2}}亩 </li>
                <li class="edu-tb-li"> 运动场地<br>
                  {{vm.items[5][1]['12412'].value*0.0015|number:2}}亩 </li>
              </ul></td>
            <td class="edu-bg02" colspan="2"><div class="media">
                <div class="media-left  media-middle"><img src="${ctxImg}/list-icon.png"></div>
                <div class="media-body  media-middle">
                  <ul class="edu-tb-ul">
                    <li class="edu-tb-li"> 计算机 <br>
                      {{vm.items[5][1]['1243'].value}}台 </li>
                    <li class="edu-tb-li"> 教学用机<br>
                      {{vm.items[5][1]['12431'].value}}台 </li>
                  </ul>
                </div>
              </div></td>
          </tr>
          <tr>
            <td class="edu-bg03"> 仪器设备<br>
              {{vm.items[5][1]['12451'].value|number:2}}万
              </td>
            <td class="edu-bg06"> 信息化设备<br>
              {{vm.items[5][1]['12452'].value|number:2}}万</td>
          </tr>
          <tr>
            <td colspan="2" class="edu-bg03"><div class="media">
                <div class="media-left media-middle"><img src="${ctxImg}/text-icon.png"></div>
                <div class="media-body media-middle text-left">
                  <h3><b>图书 {{vm.items[5][1]['1242'].value|number:2}}万册</b></h3>
                  <h4>其中:今年新增{{vm.items[5][1]['12421'].value|number:2}}万册</h4>
                </div>
              </div></td>
            <td colspan="2" class="edu-bg04"><div class="media">
                <div class="media-left media-middle"><img src="${ctxImg}/class-icon.png"></div>
                <div class="media-body  media-middle">
                  <ul class="edu-tb-ul">
                    <li class="edu-tb-li"> 教室<br>
                      {{vm.items[5][1]['1244'].value}}间 </li>
                    <li class="edu-tb-li"> 其中:多媒体教室<br>
                      {{vm.items[5][1]['12441'].value}}间 </li>
                  </ul>
                </div>
              </div></td>
          </tr>
        </tbody></table>
      </div>
    </div>
    <!--资产状况-->
    <!--教学维度-->
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10 edu-green-cor" id="edu-06">教学维度 <a class="pull-right" ng-click="titleShow(title[5],5);"><small>
    <i ng-class="title[5].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[5].show?'收起':'展开'}} </small></a></h3>
      <div class="hidden"> 内容 </div>
    </div>
    <!--教学维度-->
    <!--科研维度-->
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10 edu-green-cor" id="edu-07">科研维度 <a class="pull-right" ng-click="titleShow(title[6],6);"><small>
    <i ng-class="title[6].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[6].show?'收起':'展开'}} </small></a></h3>
      <div class="hidden"> 内容 </div>
    </div>
    <!--科研维度-->
    <!--其他维度-->
    <div class="edu-brt-box">
      <h3 class="edu-ft-22 edu-mt-10 edu-green-cor" id="edu-08">其他维度 <a class="pull-right" ng-click="titleShow(title[7],7);"><small>
    <i ng-class="title[7].show?'icon-double-angle-up':'icon-double-angle-down'" class="icon-large"></i>{{title[7].show?'收起':'展开'}} </small></a></h3>
      <div class="hidden"> 内容 </div>
    </div>
    <!--其他维度-->
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"></jsp:include>
<div cs-window show="lsygDiv" autoCenter="true" offset="offset" title="'学校历史演革'" style=" width:450px">
<h3 class="edu-ft-22 edu-mt-10" >无相关数据</h3>
 </div>
</body>

</html>
