<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生成绩概况</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/failExamination/controller.js"></script>
<script type="text/javascript" src="product/failExamination/service.js"></script>

</head>
<body ng-controller="controller">
  <div class="xuegong-zhuti-content" >
<div class="ss-mark-wrapper">
    <div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>不及格补考分析</b>
            </header>
              <div ng-show="!data.advance.show" ng-click="advanceShow()" title="高级查询" class="bak-orange" align="center"><a href="" class="sets"></a></div>
        
        <div ng-show="data.advance.show" class="header-con last-performance" style="margin-top:18px;">
	        <div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
	    </div>
	    <div ng-show="data.advance.show" ng-click="advanceShow()" title="收起" class="bak-orange bak-orange-up" align="center"><a href="#" class="sets up"><i class="fa fa-angle-up"></i></a></div>
            
        </div>
        <div class="ul-no-center">
                	<ul class="list-unstyled top-center">
                    	<li>
                    	 <div class="ss-mark-drop year-drop">
			         <div class="xuegong-zhuti-dropdown-box">
						<div class="btn-group" cg-select data="data.bzdm_xn" on-change="changXn($value,$data)" ng-model="data.value_year" value-field="id" display-field="mc"></div>
					</div></div>
                    	</li>
                    	
		 			 	<!-- <li class="has-green"><a href="" ng-click="changXn(data.thisYear)" ng-class="{on:data.thisYear==data.value_year}" class="bg-green years">今年</a></li>
           	            <li><a href="" ng-click="changXn(data.lastYear)" ng-class="{on:data.lastYear==data.value_year}" class="bg-green years">去年</a></li>   -->
 	          	        <li class="{{yearType[0]}}"><a href="" ng-click="changeYear('0')" class="bg-green years">今年</a></li>
                        <li class="{{yearType[1]}}"><a href="" ng-click="changeYear('1')" class="bg-green years">去年</a></li>    
                        <li class="{{yearType[2]}}"><a href="" ng-click="changeYear('2')" class="bg-green years">近三年</a></li>
                        <li class="{{yearType[3]}}"><a href="" ng-click="changeYear('3')" class="bg-green years">近五年</a></li>
                        <li>
							<!-- 学历 -->
							<div class="ss-mark-drop ben-zhuan pull-left">
							<div class="xuegong-zhuti-dropdown-box">
								<div class="btn-group" cg-select data="data.bzdm_edu" on-change="changEdu($value,$data)" ng-model="data.value_edu"></div>
							</div>
							</div>
                        </li>
      <div style="margin-top:25px;" class="pull-right">
	        		    <div tool-tip placement="left" class="text-green">
			       		<p>不及格人数:指学生至少有一门课不及格的人数</p>
						<p> 不及格率:指当前不及格人数/当前在籍人数;</p>
						<p>环比变化:指（当前学年不及格人数-上一个学年不及格人数）/上一个学年不及格人数;</p>
						<p>平均不及格门数:指的是在不及格学生中，每个学生平均不及格的课程门数;</p>
	        	    </div></div> 
                        <div class="clearfix"></div>
                    </ul>
                </div>  
        
        <div class="bukao-separate">
        	<div class="bukao">
            	<div class="bukao-l">
                    	<h4>{{data.dateName}}</h4>
                        <div class="bukao-circle">
                                <div class="col-md-3">
                                	<p>不及格人数</p>
                                    <div class="round-box"><a href="">{{data.gkInfo_data.CL01}}</a></div>
                                </div>
                                <div class="col-md-3">
                                	<p>不及格率</p>
                                    <div class="round-box"><a href="" class="active bl-pur-circle">{{data.gkInfo_data.CL02}}%</a></div>
                                </div>
                                <div class="col-md-3">
                                	<p>环比上升</p>
                                    <div class="round-box"><a href="" class="green-circle">{{data.gkInfo_data.CL03}}</a></div>
                                </div>
                                <div class="col-md-3">
                                	<p>平均不及格门数</p>
                                    <div class="round-box"><a href="" class="pink-circle">{{data.gkInfo_data.CL04}}</a></div>
                                </div>
                                <div class="clearfix"></div>
                        </div>
                </div>
                <div class="cell"></div>
                <div class="bukao-r">
                	<div class="bu-kao-tab">
                        <table class="table">
                            <thead>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td><p class="bu-k-icon center-block"></p><span class="two-words">人数</span></td>
                                    <td><p class="bu-k-icon bu-k-icon-02 center-block"></p>不及格率</td>
                                    <td><p class="bu-k-icon bu-k-icon-03 center-block"></p><span class="two-words">环比变化</span></td>
                                </tr>
                            </thead>
                            <tbody>
                            	<tr ng-repeat="item in data.gkflInfo_data" init="i=0">
                                	<td >{{item.CL01}}</td>
                                	<td >{{item.CL02}}</td>
                                	<td >{{item.CL03}}</td>
                                	<td >{{item.CL04}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        
            <div class="separate-lr">
        	<div class="row">
            	<div class="col-md-6">
                	<p class="block-tit">各年级不及格分布<!-- <a href=""><span class="time-icon-green pull-right"></span></a> -->
                    <div class="img-box"><div echart config="GknjCfg"></div></div>
<%--                     <div class="img-box"><img src="<%=basePath %>/static/resource/images/gua-ke-grade.png" class="center-block" alt=""></div> --%>
                </div>
                <div class="col-md-6 last-child">
                	<p class="block-tit">男女生不及格分布<a href=""><!-- <span class="time-icon-green pull-right"></span></a> -->
<%--                     <div class="img-box"><img src="<%=basePath %>/static/resource/images/gua-ke-sex.png" class="center-block" alt=""></div> --%>
                    <div class="img-box"><div echart config="GkxbCfg"></div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
        <div class="separate-lr">
        	<div class="row">
            	<div class="col-md-6">
                	<p class="block-tit">不及格课程性质分布<!-- <a href=""><span class="time-icon-green pull-right"></span></a> -->
                 <div class="img-box"><div echart config="GkkcNatCfg"></div></div>
                  <%--   <div class="img-box"><img src="<%=basePath %>/static/resource/images/gua-ke-class-l.png" class="center-block" alt=""></div> --%>
                </div>
                <div class="col-md-6 last-child">
                	<p class="block-tit">不及格课程属性分布<!-- <a href=""><span class="time-icon-green pull-right"></span></a> -->
                   <div class="img-box"><div echart config="GkkcAttCfg"></div></div> 
                   <%--  <div class="img-box"><img src="<%=basePath %>/static/resource/images/gua-ke-class-r.png" class="center-block" alt=""> --%>
                </div>
                <div class="clearfix"></div>
            </div>
            </div>
        
        <div class="performance-change no-border no-bottom">
        	<p class="block-tit">各机构不及格分布<!-- <a href=""><span class="time-icon-green pull-right"></span></a> -->
				 <div class="img-box"><div echart config="GkjgCfg"></div></div>  
         <%--    <div class="img-box"><img src="<%=basePath %>/static/resource/images/gua-ke-img.png" class="center-block" alt=""></div>  --%>
         </div> 
        
        <section class="ss-mark-tab-box">
        	<div class="ss-mark-tab">
            	<div class="Top-h4 text-center"><h4>不及格TOP</h4></div>
        		<div class="ss-mark-tab-tit">
                	<a href="" ng-click="changeType('course')" class="{{cssType[0]}}"><b class="two-words">课程</b><span></span><div class="triangle"></div></a>
                    <a href="" ng-click="changeType('tea')"   class="{{cssType[1]}}"><b class="two-words">教师</b><span></span><div class="triangle"></div></a>
                    <a href="" ng-click="changeType('major')" class="{{cssType[2]}}"><b class="two-words">专业</b><span></span><div class="triangle"></div></a>
                    <a href="" ng-click="changeType('class')" class="{{cssType[3]}}"><b class="two-words">班级</b><span></span><div class="triangle"></div></a>
                </div>
                <div class="row">
                	<div class="col-md-8">
                        <div class="ss-mark-tab-con">
                            <table class="table table-bordered class-tab">
                                <thead>
                                    <tr>
                                        <td><span class="two-words">{{gktopTitlesInfo}}</span></td>
                                        <td>
                                            <div>
                                                <p><a href="" ng-click="gkSort(' CL02 ASC','0')" class="{{gkcssSort[0]}}"><i class="fa fa-sort-up"></i></a></p>
                                                <span>不及格率</span>
                                                <p><a href="" ng-click="gkSort(' CL02 DESC','1')" class="{{gkcssSort[1]}}"><i class="fa fa-sort-down"></i></a></p>
                                            </div>
                                        </td>
                                        <td>
                                            <div>
                                                <p><a href="" ng-click="gkSort(' CL03 ASC','2')" class="{{gkcssSort[2]}}"><i class="fa fa-sort-up"></i></a></p>
                                                <span>不及格人数</span>
                                                <p><a href="" ng-click="gkSort(' CL03 DESC','3')" class="{{gkcssSort[3]}}"><i class="fa fa-sort-down"></i></a></p>
                                            </div>
                                        </td>
                                    </tr>
                                </thead>
                                
                                <tbody>
                                    <tr ng-repeat="item in GkTopList" init="i=0" >
                                        <td><span class="badge-order">{{$index+turnPage}}</span>
                                            <p>{{item.CL01}}</p>
                                        </td>
                                         <td class="td-active">{{item.CL02}}%</td>
                                        <td>{{item.CL03}}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <p class="page pull-right">
                                    <a href="" ng-click="gkTrunPage('pre')"  class="{{turncssTop}}"></a> <!-- class="prevv disable" 禁用 -->
                                    <a href="" ng-click="gkTrunPage('nex')"  class="{{turnnexcssTop}}"></a>
                            </p>
                            <div class="clearfix"></div>
                        </div><!--end ss-mark-tab-con-->
                    </div>
                    <div class="col-md-4">
                    	<div class="ss-mark-tab-con">
                            <table class="table table-bordered shenglue">
                                <thead>
                                    <tr>
                                        <td><span class="two-words">学生</span></td>
                                        <td>
                                            <div>
                                                <p><a href="" ng-click="gkStuSort(' CL02 ASC','0')" class="{{gkcssStuSort[0]}}"><i class="fa fa-sort-up"></i></a></p>
                                                <span>不及格数</span>
                                                <p><a href="" ng-click="gkStuSort(' CL02 DESC','1')" class="{{gkcssStuSort[1]}}"><i class="fa fa-sort-down"></i></a></p>
                                            </div>
                                        </td>
                                        <td style="width:30%">
                                            <div>
                                                <p><a href="" ng-click="gkStuSort(' CL02 ASC','2')" class="{{gkcssStuSort[2]}}"><i class="fa fa-sort-up"></i></a></p>
                                                <span class="two-words" >班级</span>
                                                <p><a href="" ng-click="gkStuSort(' CL02 DESC','3')" class="{{gkcssStuSort[3]}}"><i class="fa fa-sort-down"></i></a></p>
                                            </div>
                                        </td>
                                    </tr>
                                </thead>
                                
                                <tbody>
                                    <tr ng-repeat="item in GkStuTopList" init="i=0" >
                                        <td><span class="badge-order">{{$index+turnStuPage}}</span>
                                            <p>{{item.CL01}}</p>
                                        </td>
                                         <td>{{item.CL02}}</td>
                                         <td class="jx-shenglue" title="{{item.CL03}}" >{{item.CL03}}</td> 
                                    </tr>
                                </tbody>
                            </table>
                            <p class="page pull-right">
                                    <a href="" ng-click="gkStuTrunPage('pre')" class="{{turncssTop1}}"></a>
                                    <a href="" ng-click="gkStuTrunPage('nex')" class="{{turnnexcssTop1}}"></a>
                            </p>
                            <div class="clearfix"></div>
                    	</div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
           
           <div class="ss-mark-tab">
            	<div class="Top-h4 text-center"><h4>人均补考TOP</h4></div>
        		<div class="ss-mark-tab-tit">
                	<a href="" ng-click="changebkType('course')"  class="{{bkcssType[0]}}" ><b class="two-words">课程</b><span></span><div class="triangle"></div></a>
                    <a href="" ng-click="changebkType('tea')"  class="{{bkcssType[1]}}">任课老师<span></span><div class="triangle"></div></a>
                    <a href="" ng-click="changebkType('major')"  class="{{bkcssType[2]}}"><b class="two-words">专业</b><span></span><div class="triangle"></div></a>
                </div>
                <div class="row">
                	<div class="col-md-6">
                        <div class="ss-mark-tab-con two-line-tab">
                            <table class="table table-bordered class-tab">
                                <thead>
                                    <tr>
                                        <td><span class="two-words">{{bktopTitlesInfo}}</span></td>
                                        <td>
                                            <div class="four-words tab-more-wds">
                                                <p><a href="" ng-click="bkSort(' CL02 ASC','0')" class="{{bkcssSort[0]}}"><i class="fa fa-sort-up"></i></a></p>
                                                <span>人均补考次数</span>
                                                <p><a href="" ng-click="bkSort(' CL02 DESC','1')" class="{{bkcssSort[1]}}"><i class="fa fa-sort-down"></i></a></p>
                                            </div>
                                        </td>
                                    </tr>
                                </thead>
                                
                                <tbody>
                                    <tr ng-repeat="item in bkTopList" init="i=0" ng-show="$index<10">
                                        <td><span class="badge-order">{{$index+bkturnPage}}</span>
                                            <p>{{item.CL01}}</p>
                                        </td>
                                         <td class="td-active">{{item.CL02}}</td>
                                    </tr>
                                </tbody>
                            </table>
                                     <span ng-show="show">暂无数据</span>
                            <p class="page pull-right">
                                    <a href="" ng-click="bkTrunPage('pre')" class="{{turncssTop2}}"></a>
                                    <a href="" ng-click="bkTrunPage('nex')" class="{{turnnexcssTop2}}"></a>
                            </p>
                            <div class="clearfix"></div>
                        </div><!--end ss-mark-tab-con-->
                    </div>
                    <div class="col-md-6">
                    	<div class="question-carton">
                            <p>
                                <img src="<%=basePath %>/static/resource/images/carton.jpg" alt="" class="pull-right">
                                <div class="clearfix"></div>
                                <a href="" class="pull-right">人均补考次数：不及格之后需要补考几次才能通过</a>
                            </p>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
           
        </section>
        
    </div><!--end ss-mark-main-->
   <!-------------------------------------------------------以上为主要内容区------------------------------------------------>
</div>
</body>
</html>