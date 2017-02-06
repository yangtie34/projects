<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="app">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>无法毕业无学位学生</title>
	<jsp:include page="/static/base.jsp"></jsp:include>
	<script type="text/javascript" src="product/notGradDegree/controller.js"></script>
	<script type="text/javascript" src="product/notGradDegree/service.js"></script>
  </head>
  
  <body ng-controller="controller">
  <div class="xuegong-zhuti-content" >
<div class="xuegong-zhuti-wrapper">
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
			<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" no-border="true"></div>
		</div>
<!--         	<header>
            	<div class="xueyuan-head no-bottom-border">
            	郑州轻工业学院<span>-></span>计算机学院
                <a href="" class="pull-right down";><i class="fa fa-angle-down"></i></a>
                </div>
                <div class="xueyuan-section"></div>
            </header> -->
            
            <div class="xuegong-separate-tb">
            	<div class="xuegong-zhuti-content-main-tit">
                	<p><b class="text-blue">学生预警人数分布与比例</b></p>
                    <div class="xuegong-zhuti-dropdown-box not-graduate-degree">
                            <!-- 预警类型 (无学位证/无法毕业)-->
							<div class="xuegong-zhuti-dropdown-box">
								<div class="btn-group" cg-select data="data.bzdm_xw" on-change="changType($value,$data)" ng-model="data.value_xs" value-field="id" display-field="mc"></div>
							</div>
<!--                         <div class="btn-group">
                              <a type="button" class="btn btn-noradius btn-shadow in-con dropdown-toggle" data-toggle="dropdown">无法毕业</a>
                          			  <a type="button" class="btn btn-noradius dropdown-toggle drown-icon" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="caret carett"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                              </a>
                              <ul class="dropdown-menu">
                                Dropdown menu links
                                <li role="presentation"><a href="">无法毕业</a></li>
                                <li role="presentation"><a href="">无学位</a></li>
                              </ul>
                        </div>  -->
                    </div>
                    <p class="p-marg-left">当前在籍学生<b ><a href="" class="text-pink" ng-click="abstractDetailClick('noDegree','无学位学生名单')">{{getxwCfg.DCOUNT_}}</a></b>人无学位，<b><a href="" class="text-pink" ng-click="abstractDetailClick('noGrade','无法毕业学生名单')">{{getxwCfg.GCOUNT_}}</a></b>人无法毕业</p>
                      <span class="question-icon pull-right"  ng-mouseenter="show = true" ng-mouseleave="show = false">
                       <a href="" class="question-img pull-right"> </a>
                       <div class="question-area-box question-area-box-top" ng-show="show">
                      <div class="question-area"> <!-- ng-show="show" -->
                        <img src="<%=basePath %>/static/resource/images/xues-gognzuozhe/question-k.png">
                       <!--  <p style="font-weight:bold;">无学位证：</p>普通本科学生4年（或5年）中累计有{{data.nedegreeCode}}学分（含{{data.nedegreeCode}}学分）者<br><p style="font-weight:bold;">无法毕业:</p>学分不够</p> -->
                    	<p><b>无学位证：</b>普通本科学生4年（或5年）中累计有{{data.nedegreeCode}}学分（含{{data.nedegreeCode}}学分）者</p>
                    	<p><b>无法毕业：</b>学分不够</p>
                    </div>
                    </div>  
                    </span>
 <%--                    <span class="question-icon pull-right">
                    	<a href="#"  class="question-img  pull-right"></a>
                        <div class="question-area-box">
                            <div class="question-area">
                                <img src="<%=basePath %>/static/resource/images/xues-gognzuozhe/question-k.png">
                                <p>说明：“变化”是指当前数据与上周平均数据环比变化。</p>
                            </div>
                        </div>
                    </span> --%>
                    
                </div>
                 <div part-modal show-modal="data.mask[0]">
                <div class="xuegong-separate-con" >
                <div class="img-box">
                	<div class="img-box-only"><div echart config="XffbCfg"></div></div>
                </div>
                </div>
                </div>
            </div>
            
            <div class="xuegong-separate-tb">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">无学位学生分析</b>
                    <span class="p-marg-left">无法毕业的学生可以通过补考再次获得毕业资格，所以现在只分析无学位学生</span>
                    <a href=""><!-- <span class="pull-right time-img"></span> --></a>
                </div>
                <div class="xuegong-separate-con not-graduate-degree-con" ng-show="changePie">
                	<!-- <img src="static/resource/images/student-source/xuegong-img-edit.jpg" class="pull-right" alt=""> -->
                    <div class="clearfix"></div>
                	 <div class="not-graduate-degree-con-img">
                         <div class="row">
                             <div class="col-md-6">
                                 <div class="img-box">
                                    <!--  <img src="static/resource/images/not-graduate-degree-con-img/not-graduate-degree-con-img-tl.jpg" class="center-block vertical-center">  -->
                                <div echart config="getXkfbCfg"></div>
                                 </div>
                             </div>
                             <div class="col-md-6">
                                 <div class="img-box">
                               <div echart config="getNjfbCfg"></div> 
                                    <!--  <img src="static/resource/images/not-graduate-degree-con-img/not-graduate-degree-con-img-tr.jpg" class="center-block vertical-center"> -->
                                 </div>
                                <!--  <p class="text-center"><i class="fa fa-warning fa-warning-normal text-pink"></i>有<a href="" class="text-pink"><b>10%</b></a>的学生在一年级时就已经不能获得学位了</p> -->
                              </div>
                              <div class="clearfix"></div>
                          </div>
                          <div class="exchange"><a href="" ng-click="changeType('line')"><span><img src="static/resource/images/student-xueji-yidong/xuegong-exchange.png" class="center-block"></span></a></div>
                          <div class="row">
                               <div class="col-md-6">
                                    <div class="img-box">
                                    <div echart config="getYyfbCfg"></div> 
                                        <!-- <img src="static/resource/images/not-graduate-degree-con-img/not-graduate-degree-con-img-bl.jpg" class="center-block vertical-center">  -->     
                                    </div>
                               </div>
                               <div class="col-md-6">
                                    <div class="img-box">
                                    <div echart config="getXbfbCfg"></div> 
                                        <!-- <img src="static/resource/images/not-graduate-degree-con-img/not-graduate-degree-con-img-br.jpg" class="center-block vertical-center"> -->
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                          </div>
                      </div>
                </div>
                <!-- 折现统计图 -->
                 <div class="xuegong-separate-tb">
                 <div class="xuegong-separate-con not-graduate-degree-con" ng-show="changeLine"><!-- ng-show="changeLine" -->
                 	<!-- <img src="static/resource/images/student-source/xuegong-img-edit.jpg" class="pull-right" alt="">  -->
                    <div class="clearfix"></div>
                	 <div class="not-graduate-degree-con-img not-graduate-degree-con-img-02">
                         <div part-modal show-modal="data.mask[1]">
                         <div class="row">
                             <div class="col-md-6">
                                 <div class="img-box">
                                <div echart config="getXkfbByYearCfg"></div>
                                 </div>
                             </div>
                             <div class="col-md-6">
                                 <div class="img-box">
                               <div echart config="getNjfbByYearCfg"></div> 
                                 </div>
                             </div>
                             <div class="clearfix"></div>
                             </div>
                          <div class="exchange"><a href="" ng-click="changeType('pie')"><span><img src="static/resource/images/student-xueji-yidong/xuegong-exchange.png" class="center-block"></span></a></div>
                          <div class="row">
                              <div class="col-md-6">
                                  <div class="img-box">
                                <div echart config="getStatefbByYearCfg"></div> 
                                  </div>
                               </div>
                               <div class="col-md-6">
                                   <div class="img-box">
                                 <div echart config="getXbfbByYearCfg"></div>
                                </div>
                               </div>
                           <div class="clearfix"></div>
                     </div>
                </div>
                 <div class="clearfix"></div>               
            </div>
            </div>
            </div>
        </div>
    </div>
</div>
</div>
<div modal-form config="data.abstract_detail.formConfig"></div>
</body>
</html>
