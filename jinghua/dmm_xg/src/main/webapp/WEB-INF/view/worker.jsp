<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生工作者基本概况</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/worker/controller.js"></script>
<script type="text/javascript" src="product/worker/service.js"></script>
<script type="text/javascript" src="product/worker/constant.js"></script>

</head>
<body ng-controller="controller">
<!-- 	  -->
<div class="xuegong-zhuti-content">
<header>
   	<div class="pull-right">
       	<a href="" ng-click="data.advance.show=!data.advance.show"><span></span><p>高级查询</p></a>
           <i></i>
           <a href="" class="tansuo disable"><span></span><p>探索</p></a>
           <i></i>
           <a href="" class="disable"><span></span><p>导出</p></a>
       </div>
       <div class="clearfix"></div>
</header>

<div class="xuegong-zhuti-content-main">

<div ng-show="data.advance.show" class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
	<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="true"></div>
</div>

<section class="xsgzz-basic">
	<table  class="separate-l-r">
        <tbody>
        <tr>
            <td class="xs-worker-xq">
                <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">学生工作者</b></div>
            </td>
            <td class="tab-cell"></td>
            <td>
                <div class="xuegong-zhuti-content-main-tit">
                    <b class="text-blue">专职辅导员与学生比</b>
                    <span class="question-icon pull-right" ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
                        <a class="question-img  pull-right"></a>
                        <div class="question-area-box question-area-box-top" ng-show="data.tip_show">
                            <div class="question-area">
                                <img src="static/resource/images/xues-gognzuozhe/question-k.png">
                                <p>教育部要求，<b>高校专职辅导员与学生师生比</b>要达到<span><b>1:{{data.scale_jyb}}</b></span></p>
                        		<p>当前师生比<span><b>1:{{data.scale_this}}</b></span>，{{(data.scale_this>data.scale_jyb || data.scale_this==0) ? '未' : ''}}达到教育部标准</p>
                            </div>
                        </div>
                    </span>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="xuegong-separate-con">
                    <div class="img-box">
                    	<div class="img-box-only">
                        	<div class="center-block" style="height:210px;" echart config="data.worker_base.option"></div>
                        </div>
                    </div>
                </div>
            </td>
            <td class="tab-cell"></td>
            <td class="zz-fudaoy-xsbi vertical">
                <div class="xuegong-separate-con">
                    <div class="img-box" 
                    	ng-class="{'img-zero':data.scale_this==0,'img-half':data.scale_this>300,'img-qita':data.scale_this>0&&data.scale_this<=300}">
                        <p>1:{{data.scale_jyb}}</p>
                        <p class="text-blue">1:{{data.scale_this}}</p>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
	
    <div class="xuegong-separate-tb">
     	<div class="zaiji-geyuanxi">
            <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">学生工作者人员结构</b></div>
            <div class="xuegong-separate-con">
            	<div class="img-box">
                    <div class="img-box-only">
                        <div class="col-md-6">
                            <div class="center-block img-normal" echart config="data.worker_distribute.wkZcCfg"></div>
                        </div>
                        <div class="col-md-6">
                            <div class="center-block" echart config="data.worker_distribute.wkDegreeCfg"></div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <div class="xuegong-l-r row">
            	<div class="col-md-6">
                	<p class="text-blue tit-second">年龄分布</p>
                    <div class="img-box">
                        <div class="center-block" echart config="data.worker_distribute.wkAgeCfg"></div>
                    </div>
                </div>
                <div class="col-md-6">
                	<p class="text-blue tit-second">性别分布</p>
                    <div class="img-box">
                        <div class="center-block" echart config="data.worker_distribute.wkSexCfg"></div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>  
	
	<div class="xuegong-separate-tb">
    	<div class="zaiji-geyuanxi">
           <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">专职辅导员人员结构</b></div>
           <div class="xuegong-separate-con">
              <div class="img-box">
                  <div class="img-box-only">
                      <div class="col-md-6">
                  		  <div class="center-block img-normal" echart config="data.instructors_distribute.wkZcCfg"></div>
                      </div>
                      <div class="col-md-6">
                  		  <div class="center-block" echart config="data.instructors_distribute.wkDegreeCfg"></div>
                      </div>
                      <div class="clearfix"></div>
                  </div>
               </div>
           </div>
           <div class="xuegong-l-r row">
           	   <div class="col-md-6">
               		<p class="text-blue tit-second">年龄分布</p><div class="img-box">
               		<div class="center-block" echart config="data.instructors_distribute.wkAgeCfg"></div>
               </div></div>
               <div class="col-md-6">
               		<p class="text-blue tit-second">性别分布</p><div class="img-box">
               		<div class="center-block" echart config="data.instructors_distribute.wkSexCfg"></div>
               </div></div>
               <div class="clearfix"></div>
           </div>
           <div class="xuegong-separate-con zzfdy-guanli">
               <div class="img-box"><p class="text-blue">各{{data.instructors_distribute.deptMc}}每个专职辅导员管理学生数</p><div class="img-box-only">
               		<div class="center-block" echart config="data.instructors_distribute.wkDeptCfg"></div>
               </div>
               <p class="text-center"><i class="fa fa-warning text-pink"></i>专职辅导员配置师生比<span>1:{{data.instructors_distribute.info.this_data}}</span>（教育部要求师生比<span>1:{{data.instructors_distribute.info.jyb_data}}</span>），其中达标 <b class="text-green">{{data.instructors_distribute.info.db_count}}</b> 个，<b>未达标 </b><b class="text-pink">{{data.instructors_distribute.info.bdb_count}}</b> 个</p></div>
           </div>
       </div>
   </div> 
</section>

    <div modal-form config="data.worker_detail.formConfig"></div>

</div>
</div>

</body>
</html>